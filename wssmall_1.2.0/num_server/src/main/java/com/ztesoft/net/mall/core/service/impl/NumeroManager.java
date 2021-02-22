package com.ztesoft.net.mall.core.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.coqueue.req.CoQueueAddReq;
import params.coqueue.req.CoQueueModifyReq;
import params.coqueue.resp.CoQueueAddResp;
import params.coqueue.resp.CoQueueModifyResp;
import zte.params.number.resp.NumberSynInfo;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.EsCoNoLimitEntity;
import com.ztesoft.net.mall.core.model.NumberCo;
import com.ztesoft.net.mall.core.model.Numero;
import com.ztesoft.net.mall.core.model.NumeroBak;
import com.ztesoft.net.mall.core.model.NumeroImportLog;
import com.ztesoft.net.mall.core.model.NumeroRegla;
import com.ztesoft.net.mall.core.service.INumeroBakManager;
import com.ztesoft.net.mall.core.service.INumeroGrupoManager;
import com.ztesoft.net.mall.core.service.INumeroManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.core.utils.NumManagerUtils;
import com.ztesoft.net.mall.core.utils.ReadWriteExcelUtil;
import com.ztesoft.net.mall.core.utils.Regx;
import com.ztesoft.net.sqls.SF;
/**
 * 
 * Saas式号码分组业务管理
 * 
 * @author cc
 * 
 */
@SuppressWarnings("all")
public class NumeroManager extends BaseSupport implements INumeroManager {
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	private static final String TABLE = "ES_NO";
	private static final String ID = "NO_ID";

	private NumeroReglaManager reglaManager;
	private INumeroGrupoManager ngManager;
	private CiudadManager cManager;
	private INumeroBakManager nbManager;

	/**
	 * 根据号码查询号码基本信息
	 * @param no
	 * @return
	 */
	@Override
	public Map queryNoInfoByNo(String no) {
		
		Map noMap = new HashMap();
		String sql = SF.numeroSql("NO_INFO");
		sql += " and a.no = ? and a.source_from = ?";
		List<NumberSynInfo> list = baseDaoSupport.queryForList(sql, NumberSynInfo.class, 
				no, ManagerUtils.getSourceFrom());
		
		noMap.put("noList", list);
		return noMap;
	}
	
	/**
	 * 号码发布到多个组织
	 * @param number 号码
	 * @param org_ids 组织11,22,33
	 * @param batch_id 批次号
	 * @param region_id 号码归属地
	 * @param source_from 
	 * @param is_Lucky 号码类型1：靓号，0：普通号
	 */
	private void dealAlreadyPublishNum(String number,String org_ids,String batch_id,String region_id,String source_from,String is_Lucky){
		for(String org_id:org_ids.split(",")){
			String numSql = SF.numeroSql("QRY_NUMBER_BY_ID");
			numSql+=" and a.org_id_str like '%"+ org_id + "%' and b.region_id = ? and b.source_from=? group by is_lucky order by is_lucky asc ";
			//查询号码的发布数
			List<Numero> listNum = this.baseDaoSupport.queryForList(numSql, Numero.class,region_id,source_from);
			//查询组织的门限数
			List<EsCoNoLimitEntity> limitList = this.baseDaoSupport
					.queryForList(SF.numeroSql("QRY_NUMBRE_LIMIT_BY_ORGID"),
							EsCoNoLimitEntity.class, org_id, region_id,
							source_from);
			int maxLuck=0,maxOrdi=0,alreadyPubLuckNum=0,alreadyPubOrdiNum=0,maxPublishNum=0;
			
			//有门限设置的
			if(limitList.size()>0){
				maxLuck = Integer.valueOf(limitList.get(0).getMax_lucky());
				maxOrdi = Integer.valueOf(limitList.get(0).getMax_ordinary());
				int len = listNum.size();
				if(len>0){
					String isLucky = listNum.get(0).getIs_lucky();//是否靓号
					if(len==1){//此组织只查询到靓号或普通号两者之一
						if(is_Lucky.equals(isLucky)){
							alreadyPubLuckNum = Integer.valueOf(listNum.get(0).getRule_id());//已发布靓号数量
						}else{
							alreadyPubOrdiNum = Integer.valueOf(listNum.get(0).getRule_id());//已发布普通号数量
						}
					}else if(len==2){//此组织查询到靓号和普通号两者
						if(is_Lucky.equals(isLucky)){
							alreadyPubLuckNum = Integer.valueOf(listNum.get(0).getRule_id());//已发布靓号数量
						}else{
							alreadyPubOrdiNum = Integer.valueOf(listNum.get(1).getRule_id());//已发布普通号数量
						}
					}
				}
				//根据门限计算此组织最多还能接收发布号码的数量
				if(Consts.IS_LUCKY_NUMBER_1.equals(is_Lucky)){//靓号
					maxPublishNum = maxLuck - alreadyPubLuckNum;
				}else{
					maxPublishNum = maxOrdi - alreadyPubOrdiNum;
				}
				if(maxPublishNum<=0){//到达门限上限
					//不发布，去除
					org_ids.replaceAll(org_id+",","").replaceAll(org_id, "");
					continue;
				}
//				else{//继续发布
//					writeNoCo(number,source_from,batch_id,org_id);
//				}
			}//else{}//没有门限的直接发布
		}
		//修改只保存一条记录，org_id逗号隔开
		this.writeNoCo(number,source_from,batch_id,org_ids);

	}
	
	private void writeNoCo(String number,String source_from,String batch_id,String org_id){
		NumberCo nc = new NumberCo();
		nc.setNo(number);
		nc.setCreated_date(DBTUtil.getDBCurrentTime());
		nc.setSource_from(source_from);
		nc.setStatus(Consts.PUBLISH_STATE_1);
		nc.setStatus_date(DBTUtil.getDBCurrentTime());
		nc.setOper_id(ManagerUtils.getUserId());
		nc.setBatch_id(batch_id);
		nc.setOrg_id_str(org_id);
		baseDaoSupport.insert("ES_NO_CO", nc);
	}
	
	private void doPublish(String number,String org_ids,String org_id_belong,String batch_id,String region_id,String is_Lucky,String source_from){
		
		//处理组织已经发布的号码量
		this.dealAlreadyPublishNum(number,org_ids,batch_id,region_id,source_from,is_Lucky);
		
		//修改状态
		//this.baseDaoSupport.execute(SF.numeroSql("UPDATE_ES_NO_CO_STATE"), Consts.PUBLISH_STATE_1,number,source_from);
	}
	
	/**
	 * 写同步消息队列
	 * @param batch_id  批次号
	 * @param object_id  
	 * @param contents
	 * @param org_id_str  销售组织标识串
	 * @param source_from 
	 */
	private void doSaveCoQueue(String batch_id, String object_id, String contents, String org_id_str,String source_from,String action_code) {
		if(!StringUtils.isEmpty(org_id_str)){
			//写消息队列
			CoQueueAddReq coQueueAddReq = new CoQueueAddReq();
			coQueueAddReq.setCo_name("号码同步");
			coQueueAddReq.setBatch_id(batch_id);
			coQueueAddReq.setService_code(Consts.SERVICE_CODE_CO_HAOMA);
			coQueueAddReq.setAction_code(action_code);
			coQueueAddReq.setObject_id(object_id);
			coQueueAddReq.setObject_type("HAOMA");
			coQueueAddReq.setContents(contents);
			coQueueAddReq.setOrg_id_str(org_id_str);
			coQueueAddReq.setOrg_id_belong("10008");  //新商城
			coQueueAddReq.setSourceFrom(source_from);
			coQueueAddReq.setOper_id(ManagerUtils.getAdminUser().getUserid());
			
			ZteClient client = ClientFactory.getZteDubboClient(source_from);
			client.execute(coQueueAddReq, CoQueueAddResp.class);
		}
	}
	
	private Map checkLimitNum(String org_ids,String isLuck){
		Map m = new HashMap();
		Map map = new HashMap();
		String publish_str = org_ids;
		if (StringUtils.isNotBlank(org_ids)) {
			String source_from = ManagerUtils.getSourceFrom();
			// 查看号码的门限数
			String limitSql = "";
			if (Consts.IS_LUCKY_NUMBER_0.equals(isLuck)) {// 不是靓号
				limitSql = SF.numeroSql("NUMBER_LIMIT_SUM_ORDINARY");
			} else {
				limitSql = SF.numeroSql("NUMBER_LIMIT_SUM_LUCKY");
			}
			// 检测号码是否能发布在此组织（门限量）
			//for (String orgid : org_ids.split(",")) {
			String [] orgArray =org_ids.split(",");
			for (int i=0,j=orgArray.length;i<j;i++) {
				//查看是否无门限限制
				//select * from es_no_co_limit  t where t.org_id='10033' and t.source_from='ECS'
				List hasLimitlist=this.baseDaoSupport.queryForList("select t.org_id,t.region_id from es_no_co_limit  t where t.org_id=? and t.source_from=? ", orgArray[i], source_from);
				if(hasLimitlist.size()>0){//有门限
					// 查询门限容量
					int limitCount = this.baseDaoSupport.queryForInt(limitSql,orgArray[i], source_from);
					// 查询已用量
					String sqlAlready = SF.numeroSql("NUMBER_LIMIT_AREALY_COUNT");
					sqlAlready += " and t.org_id_str like '%" + orgArray[i] + "%'";
					int limitArealyCount = this.baseDaoSupport.queryForInt(sqlAlready, source_from);
					if (limitCount <= limitArealyCount) {//到达门限上限
						if(publish_str.indexOf(orgArray[i])!=-1){
							m.put("org_id", orgArray[i]);
							//获取到达上限的发布组织名
							//m.put("org_name",this.baseDaoSupport.queryForString(SF.numeroSql("GET_ORG_NAME_ID"), orgArray[i],source_from));
							// 已到门限上限，不能发布在此组织,表es_no_co的org_id_str字段,删除发布组织org_id
							publish_str = publish_str.replaceAll(orgArray[i] + ",", "").replaceAll(orgArray[i],"");
							//判断"XX,XX,"
							if(!StringUtils.isEmpty(publish_str) && publish_str.lastIndexOf(",")==publish_str.length()-1){
								publish_str = publish_str.substring(0,publish_str.length()-1);
							}
							//判断",XX,XX"
							if(publish_str.indexOf(",")==0){
								publish_str = publish_str.substring(1,publish_str.length());
							}
						}
					}
					if (!m.isEmpty()) {
						map.put("orgid"+i, m);
					}
				}//else{}//没有门限设置
			}
		}
		map.put("orgStrs", publish_str);
		return map;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String publish(String org_ids,String numbers,String org_id_belong){
		List list = new ArrayList();
		Map map = new HashMap();
		String num_t = "";
		if(StringUtils.isNotBlank(numbers)){
			String source_from=ManagerUtils.getSourceFrom();
			String[] nums = numbers.split(",");
			String batch_id  = this.daoSupport.getSequences("S_ES_CO_BATCH_ID");
			
			//1、写号码发布状态表es_no_co
			for(String num:nums){
				if(StringUtils.isNotBlank(num)){
					
					//获取一个代表号码用于该批次
					if (StringUtils.isEmpty(num_t)) {
						num_t = num;
					}
					
					//查询号码信息
					List<Numero> listNumeros = this.baseDaoSupport.queryForList(SF.numeroSql("QRY_NUMBERINFO_BY_ID"), Numero.class, num,source_from);
					map=checkLimitNum(org_ids,listNumeros.get(0).getIs_lucky());//到达门限的组织不再发布，删除对应的org_id
					org_ids=map.get("orgStrs").toString();
					map.remove("orgStrs");
					if(!map.isEmpty()){
						list.add(map);
					}
					if(!StringUtils.isEmpty(org_ids)){
						this.doPublish(num,org_ids,org_id_belong,batch_id,listNumeros.get(0).getRegion_id(),listNumeros.get(0).getIs_lucky(),source_from);
					}
				}
				
			}
			
			//2、写队列表
			//获取并过滤要同步的销售组织（新商城的）
			if(!StringUtils.isEmpty(org_ids)){
		      	String sql_dis = SF.goodsSql("GOODS_ORG_ID");
		      	sql_dis += " and a.org_level = 3 and a.party_id in("+ org_ids +")";
		      	List<String> goodsOrgLst = this.jdbcTemplate.queryForList(sql_dis, String.class);
		      	String org_id_str = NumManagerUtils.convertStrFromLst(goodsOrgLst, ",");
		      	if (StringUtils.isNotEmpty(org_id_str)) {
		      		this.doSaveCoQueue(batch_id, num_t, org_ids, org_id_str, source_from,Consts.ACTION_CODE_A);
		      	}
			}
		}
		
		return JSONArray.fromObject(list).toString();
	}
	
	
	@Override
	public Page getFormList(int pageNo, int pageSize,Map<String,String> params) {
		
		StringBuilder temp = new StringBuilder(SF.numeroSql("NUMEROS_LIST"));
		String sql = wrapSql(temp,params);
		String countSql = "select count(*) from("+sql+")";
		Page page =  baseDaoSupport.queryForCPage(sql, pageNo, pageSize,Numero.class,countSql, null);
		String sql2 = "select c.org_id_str from es_no_co c join es_no n on c.no = n.dn_no where c.source_from ='"+ManagerUtils.getSourceFrom()+"' and n.dn_no = ?";
		String sql3 = "select o.org_name from es_goods_org o  where o.source_from ='"+ManagerUtils.getSourceFrom()+"' and o.party_id = ?";
		List list = page.getResult();
		for (int i = 0; i < list.size(); i++) {
			Numero num = (Numero)list.get(i);
			String numero = num.getDn_no();
			String org_str=baseDaoSupport.queryForSingleResult(sql2,"org_id_str", numero);
			
			String [] orgs = org_str.split(",");
			String grupos = "";
			
			for (int j = 0; j < orgs.length; j++) {
				String s =baseDaoSupport.queryForSingleResult(sql3,"org_name",orgs[j]);
				grupos += s+" , ";
			}
			grupos = grupos.substring(0,grupos.length()-2);
			num.setGrupos(grupos);
			//map.put("grupos",grupos);
		}
		return page;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String listBatchPublish(Map<String,String> params){
		//查询未发布的号码
		StringBuilder temp = new StringBuilder(SF.numeroSql("NUMEROS_LIST_BATCH_PUBLISH"));
		String sql = "select * from ( ";
		String sqltemp = wrapBatchSql(temp,params);
		sql+=sqltemp;
		String batchNum = params.get("batchNum");
		if(!StringUtils.isEmpty(batchNum)){
			int pubCount = Integer.parseInt(batchNum);
			/*if(pubCount>500){
				pubCount=500;
			}*/
			sql+=" ) where rownum<"+(pubCount+1);
		}else{
			return "发布数量异常！";
		}
		String numbers = "",message="";
		String org_ids = params.get("orgIds");
		List<Numero> list= baseDaoSupport.queryForList(sql, Numero.class);
		int lucky=0,normal=0;
		if(!StringUtils.isEmpty(org_ids)){
			for(Numero num:list){
				if("1".equals(num.getIs_lucky())){
					lucky++;
				}else{
					normal++;
				}
				numbers += num.getDn_no()+",";
			}
			if(!StringUtils.isEmpty(numbers)){
				numbers = numbers.substring(0, numbers.length()-1);
			}
			//调发布方法
			this.publish(org_ids,numbers,"");
			message = "成功发布靓号"+lucky+"个，普通号码"+normal+"个！";
		}
		return message;
	}
	
	@Override
	public Page getBatchList(int pageNo, int pageSize,Map<String,String> params) {
		
		StringBuilder temp = new StringBuilder(SF.numeroSql("NUMEROS_LIST_BATCH"));
		String sql = wrapBatchSql(temp,params);
		String countSql = "select count(distinct(n.no_id)) from " +sql.substring(sql.lastIndexOf("from es_no  n")+4);
		Page page =  baseDaoSupport.queryForCPage(sql, pageNo, pageSize,Numero.class,countSql);
		String sql2 = "select o.org_name from es_goods_org o "
				+ " where 1=1 "
				+ " and o.source_from = '" + ManagerUtils.getSourceFrom() + "'";
				
		List list = page.getResult();
		for (int i = 0; list != null && i < list.size(); i++) {
//			Map map = (Map) list.get(i);
			Numero num = (Numero)list.get(i);
			//String numero=num.getDn_no();//(String) map.get("dn_no");
			String org_id_str = num.getOrg_id_str();
			String grupos = "";
			
			if (!StringUtils.isEmpty(org_id_str)) {
				
				List orgs = baseDaoSupport.queryForList(sql2 + " and o.party_id in("+ org_id_str +")");
				
				for (int j = 0; j < orgs.size(); j++) {
					Map m = (Map) orgs.get(j);
					String g = (String) m.get("org_name");
					if(j!=orgs.size()-1){
						grupos += g+" , ";	
					}else{
						grupos += g;
					}
				}
			}
			num.setGrupos(grupos);
			//map.put("grupos",grupos);
		}
		return page;
	}

	private String wrapBatchSql(StringBuilder temp, Map<String, String> params) {
		String isLucky = params.get("isLucky");
		String internet = params.get("internet");
		String group = params.get("group");
		String contract = params.get("contract");
		String beforeSeven = params.get("beforeSeven");
		String lastFour = params.get("lastFour");
		String lowest = params.get("lowest");
		String deposit_min = params.get("deposit_min");
		String deposit_max = params.get("deposit_max");
		String address = params.get("address");
		String no_statu = params.get("no_statu");
		String flag = params.get("flag");
		String batch_id = params.get("batch_id");
		String start_date = params.get("start_date");
		String end_date = params.get("end_date");
		if("search".equals(flag)){//搜索
			if(!StringUtils.isEmpty(no_statu)){
				if(Consts.PUBLISH_0.toString().equals(no_statu)){
					temp.append("  and (e.status = "+no_statu+" or e.status is null) ");
				}else{
					temp.append("  and e.status = "+no_statu+" ");	
				}
			}
		}else if("publish".equals(flag)){//发布里处理 sql中distinct对rownum会有所影响
			temp.append(" and rownum<1000 ");
		}
		if(!StringUtils.isEmpty(isLucky)){
			temp.append(" and n.is_lucky = "+isLucky);
		}
		if(!StringUtils.isEmpty(internet)){
			temp.append(" and n.no_gen = '"+internet+"'");
		}
		if(!StringUtils.isEmpty(group)){
			temp.append(" and n.group_id= '"+group+"'");
		}
		if(!StringUtils.isEmpty(contract)){
			temp.append(" and n.period = "+contract);
		}
		if(!StringUtils.isEmpty(beforeSeven)){
			temp.append(" and substr(n.dn_no,0,7)='"+beforeSeven+"'");
		}
		if(!StringUtils.isEmpty(lastFour)){
			temp.append(" and substr(n.dn_no,8,11)='"+lastFour+"'");
		}
		if(!StringUtils.isEmpty(lowest)){
			temp.append(" and n.lowest="+lowest);
		}
		if(!StringUtils.isEmpty(deposit_min)){
			temp.append(" and n.deposit>"+deposit_min);
		}
		if(!StringUtils.isEmpty(deposit_max)){
			temp.append(" and n.deposit<"+deposit_max);
		}
		if(!StringUtils.isEmpty(batch_id)){
			temp.append(" and n.batch_id ='"+batch_id+"'");
		}
		if(!StringUtils.isEmpty(start_date)){
			temp.append(" and n.created_date >= to_date('"+start_date+"', 'yyyy-MM-dd')");
		}
		if(!StringUtils.isEmpty(end_date)){
			temp.append(" and n.created_date <= to_date('"+end_date+"', 'yyyy-MM-dd')");
		}
		if(!StringUtils.isEmpty(address)){
			temp.append(" and n.region_id ='"+address+"'");
		}
		
		String source = ManagerUtils.getSourceFrom();
		temp.append(" and n.source_from = '"+source+"'");
		temp.append(" order by n.created_date desc");
		return temp.toString();
	}

	private String wrapSql(StringBuilder temp, Map<String, String> params) {
		
		//com.ztesoft.common.util.StringUtils.repalceSpecialCharacter()
		//20150603
		//XMJ
		//com.ztesoft.common.util.StringUtils.repalceSpecialCharacter(
		if(StringUtils.isNotBlank(params.get("lote"))){
			String lote=com.ztesoft.common.util.StringUtils.repalceSpecialCharacter(params.get("lote"));
			params.put("lote", lote);
			temp.append(" and n.batch_id like '%"+lote+"%'");
		}
		String startDate = params.get("start_date");
		String endDate = params.get("end_date");
		
		
		if(StringUtils.isNotBlank(params.get("estado"))){
			String estado =com.ztesoft.common.util.StringUtils.repalceSpecialCharacter(params.get("estado"));
			params.put("estado", estado);
			temp.append(" and n.status = '"+estado+"'");
		}
		
		if(StringUtils.isNotBlank(params.get("internet"))){
			temp.append(" and n.no_gen = '"+params.get("internet")+"'");
		}
		if(StringUtils.isNotBlank(params.get("ciudad"))){
			temp.append(" and n.region_id = '"+params.get("ciudad")+"'");
		}
		if(StringUtils.isNotBlank(params.get("grupo"))){
			temp.append(" and g.group_id = '"+params.get("grupo")+"'");
		}
		if(StringUtils.isNotBlank(params.get("liberacions"))){
			if(params.get("liberacions").equals("0")){
				temp.append(" and (not exists(select 1 from es_no_co n1 where n1.no = n.dn_no) " 
                  + " or exists(select 1 from es_no_co n1 where n1.no = n.dn_no and n1.status = 0))");
			}else {
				temp.append(" and e.status = '"+params.get("liberacions")+"'");
			}
		}
		
		String source = ManagerUtils.getSourceFrom();
		temp.append(" and n.source_from = '"+source+"'");
		
		
		if (StringUtils.isNotBlank(startDate)) {
			temp.append(" and to_date('");
			temp.append(startDate + "','yyyy-mm-dd') <= n.created_date ");
			
			if (StringUtils.isNotBlank(endDate)&&!startDate.equals(endDate)){
				temp.append("and n.created_date <= ");
				temp.append("to_date('"+endDate+"','yyyy-MM-dd')");
			}
		} else {
			if (StringUtils.isNotBlank(endDate)){
				temp.append(" and n.created_date <= ");
				temp.append(" to_date('"+endDate+"','yyyy-MM-dd')");
			}
		}
		
		String number = Const.getStrValue(params, "number");
		if(!StringUtils.isEmpty(number)){
			number=com.ztesoft.common.util.StringUtils.repalceSpecialCharacter(Const.getStrValue(params, "number"));
			params.put("number", number);
			temp.append(" and n.dn_no='"+number+"' ");
			
		}
		
		temp.append(" order by  n.no_id desc");
		
		return temp.toString();
	}

	public boolean isValidInput(String str) 
	{
	if(str.matches("[a-z0-9]+")) return true; 
	else return false; 
	}

	
	@Override
	public Numero get(String id) {
		String sql   = "select * from ES_NO where no_id =?";
		return (Numero) daoSupport.queryForObject(sql, Numero.class,id);
	}
	
	public Numero getByNo(String no) {
		String sql   = "select * from ES_NO where dn_no =?";
		List<Numero> numeros= daoSupport.queryForList(sql, Numero.class,no);
		if(!ListUtil.isEmpty(numeros))
			return numeros.get(0);
		return null;
	}

	@Override
	public String importacion(File file,Map<String, String> params) {
		
		int contar = 0;  //成功导入个数
		int total = 0;
		String batch_id = "";
		try{
			ReadWriteExcelUtil reu = new ReadWriteExcelUtil();
			List list = reu.readExcel(file,"numero",new HashMap());
			if (list == null) {
				return "0";
			}
			total = list.size();
			Map metodos = getMetodo();
			batch_id = this.baseDaoSupport.getSequences("S_ES_NO_IMPORT_BATCH","0",6);
			String oper_id = ManagerUtils.getUserId();
			for (int i = 0; i < list.size(); i++) {
				NumeroImportLog log = (NumeroImportLog) list.get(i);
				//Map number = (Map) list.get(i);
				String log_id = this.baseDaoSupport.getSequences("S_ES_NO_IMPORT_LOG","3",18);
				log.setLog_id(log_id);
				log.setBatch_id(batch_id);
				log.setBatch_amount(list.size());
				log.setDeal_num(0);
				log.setDeal_desc("文件批量导入");
				log.setFile_name(params.get("file_name"));
				log.setOper_id(oper_id);
				log.setAction_code(params.get("action_code"));
				this.baseDaoSupport.insert("es_no_import_logs", log);
				contar++;
			}
			String action_code = Const.getStrValue(params, "action_code");
			if(Consts.ACTION_CODE_M.equals(action_code)){
				this.batchModify(batch_id);
			}
			else if(Consts.ACTION_CODE_B.equals(action_code)){
				String org_ids = Const.getStrValue(params, "orgIds");
				this.batchPublish(batch_id, org_ids);
			}
			else if(Consts.ACTION_CODE_D.equals(action_code)){
				this.batchRecycle(batch_id);
			}
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
		return total + "#" + contar + "#" + (total-contar) + "#"+batch_id;
	}
	
	@Override
	public void importNumero(){
		//导入号码条数
		int contar = 0;  //成功导入个数
		String batch_id = "-1";  //此批次
		String dn_no_success = ""; //记录第一个成功的号码
		String sql = SF.numeroSql("NUMERO_TEMP");
		String logSql = SF.numeroSql("NUMERO_IMPORT_LOGS")+" and deal_flag=0 and action_code='A'";
		
		List<NumeroImportLog> list = this.baseDaoSupport.queryForList(logSql, NumeroImportLog.class, null);
		int total = list.size();
		Map metodos = getMetodo();
		for (int i = 0; i < total; i++) {
			NumeroImportLog log = list.get(i);
			Numero n = new Numero();
			copyValues(log,n);
			if(StringUtils.isBlank(n.getDn_no())){
				this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
						"2", "号码为空！", log.getLog_id());
				continue;
			}
			
			int count = baseDaoSupport.queryForInt(sql, n.getDn_no());
			if (count > 0){
				this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
						"2", "号码已经存在！", log.getLog_id());
				continue;
			}
			if(!checkReg(n.getDn_no())){
				this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
						"2", "号码号段归属地没有设置！", log.getLog_id());
				continue;
			}
			
			if(log.getGroup_code()!=null){
			if(validateGroup_code(log.getGroup_code())){
				String group_id=this.baseDaoSupport.queryForString(SF.numeroSql("NO_GROUP_ID"),log.getGroup_code());
				n.setGroup_id(group_id);
			}else{
				this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
						"2", "号码分组编码不存在！", log.getLog_id());
				continue;
			}}
			
			String s = log.getCharge_type();
			String value  = (String) metodos.get(s);
			n.setCharge_type(value);
			n.setStatus("000");
			String id = baseDaoSupport.getSequences("SEQ_NO_MID");
			n.setNo_id(id);
			baseDaoSupport.insert("es_no_mid", n);

			this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
					"1", "号码导入成功！", log.getLog_id());
			contar++;
			
		}
		
		if (contar > 0) {
			
			//存储过程从es_no_mid中间表移到正式号码表es_no
	   		this.baseDaoSupport.executeProceduce(" call P_NO_TEMP_DATA_PROCESS()");
		}
	}
	
	private boolean checkReg(String num) {
		// 校验号码归属地
		String num1=num.substring(0,8);
		String num2=num.substring(0,7);
		String num3=num.substring(0,6);
		String countSql="select count(*) from es_no_seg where seg_no =? or seg_no=? or seg_no=?";
		int count=this.baseDaoSupport.queryForInt(countSql, num1,num2,num3);
		if (count>0)
		return true;
		else
		return false;
	}

	private void copyValues(NumeroImportLog source,Numero target){
		target.setBatch_id(source.getBatch_id());
		target.setDn_no(source.getDn_no());
		target.setNo_gen(source.getNo_gen());
		target.setDeposit(source.getDeposit()!=null?String.valueOf(source.getDeposit()):"");
		target.setPeriod(source.getPeriod()!=null?String.valueOf(source.getPeriod()):"");
		target.setLowest(source.getLowest()!=null?String.valueOf(source.getLowest()):"");
		target.setCharge_type(source.getCharge_type());
		target.setFee_adjust(source.getFee_adjust()+"");
	}
	
	
	private void setRegla(Numero numero, Map<String, String> params) {
		List<NumeroRegla> reglas  = reglaManager.getFormList(params);
		Regx rg = new Regx();
		NumeroRegla ng = new NumeroRegla();
		if(rg.regx51(numero)){
			setRegla(numero,"15","100800","36","886");
		}else if(rg.regx50(numero)){
			setRegla(numero,"14","49968","36","586");
		}else if(rg.regx49(numero)){
			setRegla(numero,"14","49968","36","586");
		}else if(rg.regx48(numero)){
			setRegla(numero,"14","49968","36","586");
		}else if(rg.regx47(numero)){
			setRegla(numero,"14","49968","36","586");
		}else if(rg.regx46(numero)){
			setRegla(numero,"13","31968","36","586");
		}else if(rg.regx45(numero)){
			setRegla(numero,"13","31968","36","586");
		}else if(rg.regx44(numero)){
			setRegla(numero,"13","31968","36","586");
		}else if(rg.regx43(numero)){
			setRegla(numero,"13","31968","36","586");
		}else if(rg.regx42(numero)){
			setRegla(numero,"13","31968","36","586");
		}else if(rg.regx41(numero)){
			setRegla(numero,"13","31968","36","586");
		}else if(rg.regx40(numero)){
			setRegla(numero,"13","31968","36","586");
		}else if(rg.regx39(numero)){
			setRegla(numero,"13","31968","36","586");
		}else if(rg.regx38(numero)){
			setRegla(numero,"13","31968","36","586");
		}else if(rg.regx37(numero)){
			setRegla(numero,"13","31968","36","586");
		}else if(rg.regx36(numero)){
			setRegla(numero,"12","20196","36","586");
		}else if(rg.regx35(numero)){
			setRegla(numero,"12","20196","36","586");
		}else if(rg.regx34(numero)){
			setRegla(numero,"12","20196","36","586");
		}else if(rg.regx33(numero)){
			setRegla(numero,"12","20196","36","586");
		}else if(rg.regx32(numero)){
			setRegla(numero,"12","20196","36","586");
		}else if(rg.regx31(numero)){
			setRegla(numero,"12","20196","36","586");
		}else if(rg.regx30(numero)){
			setRegla(numero,"12","20196","36","586");
		}else if(rg.regx29(numero)){
			setRegla(numero,"12","20196","36","586");
		}else if(rg.regx28(numero)){
			setRegla(numero,"12","20196","36","586");
		}else if(rg.regx27(numero)){
			setRegla(numero,"12","20196","36","586");
		}else if(rg.regx26(numero)){
			setRegla(numero,"12","20196","36","586");
		}else if(rg.regx25(numero)){
			setRegla(numero,"12","20196","36","586");
		}else if(rg.regx24(numero)){
			setRegla(numero,"11","10548","18","586");
		}else if(rg.regx23(numero)){
			setRegla(numero,"11","10548","18","586");
		}else if(rg.regx22(numero)){
			setRegla(numero,"11","10548","18","586");
		}else if(rg.regx21(numero)){
			setRegla(numero,"11","10548","18","586");
		}else if(rg.regx20(numero)){
			setRegla(numero,"11","10548","18","586");
		}else if(rg.regx19(numero)){
			setRegla(numero,"11","10548","18","586");
		}else if(rg.regx18(numero)){
			setRegla(numero,"11","10548","18","586");
		}else if(rg.regx17(numero)){
			setRegla(numero,"11","10548","18","586");
		}else if(rg.regx16(numero)){
			setRegla(numero,"11","10548","18","586");
		}else if(rg.regx15(numero)){
			setRegla(numero,"11","10548","18","586");
		}else if(rg.regx14(numero)){
			setRegla(numero,"11","10548","18","586");
		}else if(rg.regx13(numero)){
			setRegla(numero,"11","10548","18","586");
		}else if(rg.regx12(numero)){
			setRegla(numero,"11","10548","18","586");
		}else if(rg.regx11(numero)){
			setRegla(numero,"11","10548","18","586");
		}else if(rg.regx10(numero)){
			setRegla(numero,"11","10548","18","586");
		}else if(rg.regx9(numero)){
			setRegla(numero,"1","7032","12","586");
		}else if(rg.regx8(numero)){
			setRegla(numero,"2","4632","12","96");
		}else if(rg.regx7(numero)){
			setRegla(numero,"2","4632","12","286");
		}else if(rg.regx6(numero)){
			setRegla(numero,"3","3432","12","286");
		}else if(rg.regx5(numero)){
			setRegla(numero,"3","3432","12","286");
		}else if(rg.regx4(numero)){
			setRegla(numero,"4","720","12","96");
		}else if(rg.regx3(numero)){
			setRegla(numero,"4","720","12","96");
		}else if(rg.regx2(numero)){
			setRegla(numero,"5","360","12","96");
		}else if(rg.regx1(numero)){
			setRegla(numero,"5","360","12","96");
		}
		
	}

	private void setRegla(Numero numero,String tipo,String deposit,String period,String lowest) {
		numero.setNo_classify(tipo);
		numero.setDeposit(deposit);
		numero.setPeriod(period);
		numero.setLowest(lowest);
	}

	@Override
	public List getEstatos() {
		String sql = SF.numeroSql("NUMEROS_ESTATOS");
		return baseDaoSupport.queryForList(sql);
	}


	@Override
	public List getCiudads() {
		String sql = SF.numeroSql("NUMEROS_REGIONS");
		return baseDaoSupport.queryForList(sql);
	}


	@Override
	public List getGrupos() {
		String sql = SF.numeroSql("NUMEROS_GRUPOS");
		return baseDaoSupport.queryForList(sql);
	}

	@Override
	public List getInternet() {
		String sql = SF.numeroSql("NUMEROS_INTERNET");
		return baseDaoSupport.queryForList(sql);
	}
	
	@Override
	public List getLiberacions() {
		String sql = SF.numeroSql("NUMEROS_LIBERACION");
		return baseDaoSupport.queryForList(sql);
	}
	
	public Map getMetodo() {
		String sql = SF.numeroSql("NUMEROS_METODO");
		return baseDaoSupport.queryForMaps(sql);
	}
	
	public void setNgManager(INumeroGrupoManager ngManager) {
		this.ngManager = ngManager;
	}
	
	
	
	@Override
	public void modificar(String nos, String status_old,String status_new,String orderId) throws Exception {
		String sql = "";
		if(status_old.equals("-999")){
			
			 sql = "update ES_NO set status = ? where dn_no = ?";
			if(status_new.equals("000")|status_new.equals("111")){
				sql = "update ES_NO set status = ?, order_id='' where dn_no = ?";
				
			}
		}else{
		 sql = "update ES_NO set status = ? where dn_no = ? and status=  '"+status_old+"'";
			if(status_new.equals("000")|status_new.equals("111")){
				sql  = "update ES_NO set status = ?, order_id='' where dn_no = ? and status= '"+status_old+"'";
			}
		}
		if(StringUtils.isNotBlank(orderId)){
			sql +=" and order_id ='"+orderId+"'";
		}
		String []noss = nos.split(",");
		baseDaoSupport.execute(sql,status_new,nos);
		nbManager.delByNo(nos, new HashMap());
		for (int i = 0; i < noss.length; i++) {
			Numero n = getByNo(noss[i]);
			if(n ==null)
				continue;
			NumeroBak nb=new NumeroBak();
			BeanUtils.copyProperties(nb, n);
			nb.setAction_code("M");
			nbManager.save(nb);
		}
	}
	
	
	@Override
	public void modificar(String ids, Map<String, String> params) throws Exception {
		String sql = "update ES_NO set group_id = ? ,status = '"+params.get("estado")+"' where no_id in ("+ids+")";
		String estado = params.get("estado");
		if(estado.equals("000")|estado.equals("111")){
			sql = "update ES_NO set group_id = ? ,status = '"+params.get("estado")+"', order_id='' where no_id in ("+ids+")";
		}
		String []idss = ids.split(",");
		baseDaoSupport.execute(sql,params.get("grupo"));
		nbManager.del(ids, params);
		for (int i = 0; i < idss.length; i++) {
			Numero n = get(idss[i]);
			NumeroBak nb=new NumeroBak();
			nb.setAction_code("M");
			BeanUtils.copyProperties(nb, n);
			nbManager.save(nb);
		}
	}
	
	
	@Override
	public List<NumberSynInfo> queryNumberSynInfo(String batch_id){
		String sql = SF.numeroSql("NUMBER_SYN_INFO");
		List<NumberSynInfo> list = baseDaoSupport.queryForList(sql, NumberSynInfo.class, batch_id);
		return list;
	}


	public INumeroBakManager getNbManager() {
		return nbManager;
	}

	public void setNbManager(INumeroBakManager nbManager) {
		this.nbManager = nbManager;
	}
	

	public void setcManager(CiudadManager cManager) {
		this.cManager = cManager;
	}

	public void setReglaManager(NumeroReglaManager reglaManager) {
		this.reglaManager = reglaManager;
	}


	
	@Override
	public String queryForEstado(String ids) {
		String sql = "select status from es_no where source_from = '"+ManagerUtils.getSourceFrom()+"' and no_id = ?";
		return baseDaoSupport.queryForSingleResult(sql, "status", ids);
	}
	
	
	@Override
	public List getFirstList(String id){
		String sql = SF.goodsSql("GOODS_ORG_FIRST");
		List list = this.baseDaoSupport.queryForList(sql,id);//-1代表根节点
		list = list == null ? new ArrayList() : list;
		return list;
	}
	
	@Override
	public Page searchNumeroSynchLogs(Map params, int pageNum, int pageSize) {
		String batch_id = Const.getStrValue(params, "batch_id");
		String number = Const.getStrValue(params, "number");
		String sql = SF.numeroSql("NUMERO_SYNCH_LOG");
		List pList = new ArrayList();
		if(StringUtils.isEmpty(batch_id) && !StringUtils.isEmpty(number)){
			batch_id = this.baseDaoSupport.queryForString(SF.numeroSql("NUMERO_SYNCH_BATCH"), number);
		}
		if(!StringUtils.isEmpty(batch_id)){
			sql += " and batch_id=? ";
			pList.add(batch_id);
		}
		
		//如果是超级管理员则查全部工号的
		if(!Consts.CURR_FOUNDER_1.equals(Integer.toString(ManagerUtils.getAdminUser().getFounder()))){
			String userid = ManagerUtils.getAdminUser().getUserid();
			sql += " and oper_id=? ";
			pList.add(userid);
		}
		Page page = this.baseDaoSupport.queryForPage(sql, pageNum, pageSize, pList.toArray());
		List<Map> pageList = page.getResult();
		if(pageList!=null && pageList.size()>0){
			for(Map data : pageList){
				batch_id = Const.getStrValue(data, "batch_id");
				String countSql = SF.numeroSql("NUMERO_SYNCH_NUM");
				List<Map> counts = this.baseDaoSupport.queryForList(countSql, new String[]{batch_id});
				Map countMap = new HashMap();
				int batch_amount = 0;
				for(int i=0;i<counts.size();i++){
					Map result = counts.get(i);
					String amount = result.get("amount").toString();
					//data.put(Const.getStrValue(result, "status"), amount);
					batch_amount += Integer.valueOf(amount);
				}
				data.put("batch_amount", batch_amount);
				String org_id_str = Const.getStrValue(data, "org_id_str");
				if(!StringUtils.isEmpty(org_id_str)){
					String org_name_str = this.baseDaoSupport.queryForString(SF.goodsSql("SYNCH_ORG_GET")+" and PARTY_ID IN("+org_id_str+")", null);
					data.put("org_name_str", org_name_str);
				}
			}
		}
		return page;
	}

	@Override
	public Page searchNumeroImportLogsECS(Map params, int pageNum, int pageSize) {
		String batch_id = Const.getStrValue(params, "batch_id");
		String start_date = Const.getStrValue(params, "start_date");
		String end_date = Const.getStrValue(params, "end_date");
		String deal_flag = Const.getStrValue(params, "deal_flag");
		String action_code = Const.getStrValue(params, "action_code");
		String sql = SF.numeroSql("NUMERO_IMPORT_LOGS");
		List pList = new ArrayList();
		if(!StringUtils.isEmpty(batch_id)){
			sql += " and a.batch_id=? ";
			pList.add(batch_id);
		}
		if(!StringUtils.isEmpty(start_date)){
			start_date += " 00:00:00";
			sql += " and a.created_date>=to_date(?,'yyyy/mm/dd hh24:mi:ss') ";
			pList.add(start_date);
		}
		if(!StringUtils.isEmpty(end_date)){
			end_date += " 23:59:59";
			sql += " and a.created_date<=to_date(?,'yyyy/mm/dd hh24:mi:ss') ";
			pList.add(end_date);
		}
		if(!StringUtils.isEmpty(deal_flag)){
			sql += " and a.deal_flag=? ";
			pList.add(deal_flag);
		}
		if(!StringUtils.isEmpty(action_code)){
			sql += " and a.action_code=? ";
			pList.add(action_code);
		}
		if(!ManagerUtils.isAdminUser() && ManagerUtils.getAdminUser()!=null 
				&& !StringUtils.isEmpty(ManagerUtils.getUserId())){
			sql += " and a.oper_id=? ";
			pList.add(ManagerUtils.getAdminUser().getUserid());
		}
		String countSql = "select count(*) from("+sql+")";
		sql += " order by a.created_date desc ";
		Page page = this.baseDaoSupport.queryForCPage(sql, pageNum, pageSize, NumeroImportLog.class, countSql, pList.toArray());
		return page;
	}

	@Override
	public void batchModify(String batch_id) {
		String sql = SF.numeroSql("NUMERO_TEMP")+" and status='000' ";
		String logSql = SF.numeroSql("NUMERO_IMPORT_LOGS")+" and deal_flag=0 and action_code='M' and batch_id=?";
		
		List<NumeroImportLog> list = this.baseDaoSupport.queryForList(logSql, NumeroImportLog.class, batch_id);
		int total = list.size();
		Map metodos = getMetodo();
		for (int i = 0; i < total; i++) {
			NumeroImportLog log = list.get(i);

			if(StringUtils.isBlank(log.getDn_no())){
				this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
						"2", "号码为空！", log.getLog_id());
				continue;
			}
			
			String updateSql = SF.numeroSql("NUMERO_UPDATE_DATA");
			List<Numero> numList = this.baseDaoSupport.queryForList(updateSql, Numero.class, log.getDn_no());
			if(numList==null || numList.size()==0){
				this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
						"2", "需要修改的号码不存在，或处于不可修改状态！", log.getLog_id());
				continue;
			}
			if(numList.size()>1){
				this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
						"2", "es_no有多条号码为"+log.getDn_no()+"的数据！", log.getLog_id());
				continue;
			}
			Numero n = numList.get(0);
			n.setNo_gen(log.getNo_gen());
			n.setDeposit(log.getDeposit()!=null?String.valueOf(log.getDeposit()):"");
			n.setPeriod(log.getPeriod()!=null?String.valueOf(log.getPeriod()):"");
			n.setLowest(log.getLowest()!=null?String.valueOf(log.getLowest()):"");
			n.setFee_adjust(log.getFee_adjust()+"");
			n.setOper_id(log.getOper_id());
			
			String s = log.getCharge_type();
			String value  = (String) metodos.get(s);
			n.setCharge_type(value);
			
			Map where = new HashMap();
			where.put("no_id", n.getNo_id());
			this.baseDaoSupport.update("es_no", n, where);
			this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
					"1", "号码修改成功！", log.getLog_id());
			numeroModifyPublish(log.getDn_no());
			
		}
		
	}
	
	/**
	 * 号码修改
	 * 1、检查号码是否已经发布，没有发布返回false
	 * 2、已经发布，获取发布组织，写消息队列
	 * @param dn_no
	 * @return
	 */
	public boolean numeroModifyPublish(String dn_no){
		String sql = SF.numeroSql("FIND_NOCO_BY_NUMBER")+" order by created_date desc";
		String source_from = ManagerUtils.getSourceFrom();
		List<Map> list = this.baseDaoSupport.queryForList(sql, dn_no,source_from);
		if(list==null || list.size()==0){
			return false;
		}
		Map noco = list.get(0);
		String org_id_str = Const.getStrValue(noco, "org_id_str");
		String batch_id = Const.getStrValue(noco, "batch_id");
		this.doSaveCoQueue(batch_id, dn_no, "", org_id_str, source_from,Consts.ACTION_CODE_M);
		return true;
	}

	@Override
	public void batchPublish(String batch_id,String org_ids) {
		String logSql = SF.numeroSql("NUMERO_IMPORT_LOGS")+" and deal_flag=0 and action_code='B' and batch_id=?";
		
		List<NumeroImportLog> logs = this.baseDaoSupport.queryForList(logSql, NumeroImportLog.class, batch_id);
		int total = logs.size();
		for (int i = 0; i < total; i++) {
			NumeroImportLog log = logs.get(i);

			if(StringUtils.isBlank(log.getDn_no())){
				this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
						"2", "号码为空！", log.getLog_id());
				continue;
			}
			
			String updateSql = SF.numeroSql("NUMERO_UPDATE_DATA");
			List<Numero> numList = this.baseDaoSupport.queryForList(updateSql, Numero.class, log.getDn_no());
			if(numList==null || numList.size()==0){
				this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
						"2", "号码不存在，无法发布", log.getLog_id());
				continue;
			}
			if(numList.size()>1){
				this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
						"2", "es_no有多条号码为"+log.getDn_no()+"的数据！", log.getLog_id());
				continue;
			}
			
			String sql = SF.numeroSql("FIND_NOCO_BY_NUMBER");
			String source_from = ManagerUtils.getSourceFrom();
			List<Map> list = this.baseDaoSupport.queryForList(sql, log.getDn_no(),source_from);
			if(list!=null && list.size()>0){
				this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
						"2", "号码已发布，无法再次发布！", log.getLog_id());
				continue;
			}
			this.publish(org_ids,log.getDn_no(),"");
			this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
					"1", "号码发布，写入消息队列！", log.getLog_id());
			
		}
	}
	
	public void batchRecycle(String import_batch_id){
		String logSql = SF.numeroSql("NUMERO_IMPORT_LOGS")+" and deal_flag=0 and action_code='D' and batch_id=?";
		
		List<NumeroImportLog> logs = this.baseDaoSupport.queryForList(logSql, NumeroImportLog.class, import_batch_id);
		int total = logs.size();
		String recycle_batch_id = this.daoSupport.getSequences("S_ES_CO_BATCH_ID");
		for (int i = 0; i < total; i++) {
			NumeroImportLog log = logs.get(i);

			if(StringUtils.isBlank(log.getDn_no())){
				this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
						"2", "号码为空！", log.getLog_id());
				continue;
			}
			
			String updateSql = SF.numeroSql("NUMERO_UPDATE_DATA");
			List<Numero> numList = this.baseDaoSupport.queryForList(updateSql, Numero.class, log.getDn_no());
			if(numList==null || numList.size()==0){
				this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
						"2", "号码不存在，无法回收", log.getLog_id());
				continue;
			}
			
			String sql = SF.numeroSql("FIND_NOCO_BY_NUMBER");
			String source_from = ManagerUtils.getSourceFrom();
			List<Map> list = this.baseDaoSupport.queryForList(sql, log.getDn_no(),source_from);
			if(list==null || list.size()==0){
				this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
						"2", "号码未发布！", log.getLog_id());
				continue;
			}
			Map numMap = list.get(0);
			String org_id_str = Const.getStrValue(numMap, "org_id_str");
			this.doSaveCoQueue(recycle_batch_id, log.getDn_no(), "", org_id_str, source_from, Consts.ACTION_CODE_D);
			this.baseDaoSupport.execute(SF.numeroSql("UPDATE_NUMERO_IMPORT_LOGS"), 
					"1", "号码回收，写入消息队列！", log.getLog_id());
			
		}
	}

	@Override
	public void deleteNoCoData(String dn_no) {
		String sql = SF.numeroSql("NO_CO_DELETE");
		this.baseDaoSupport.execute(sql, dn_no);
	}

	@Override
	public int publishAgain(Map params) {
		String batch_id = Const.getStrValue(params, "batch_id");
		String number = Const.getStrValue(params, "number");
		String sql = SF.numeroSql("NUMERO_SYNCH_LOG");
		List pList = new ArrayList();
		if(StringUtils.isEmpty(batch_id) && !StringUtils.isEmpty(number)){
			batch_id = this.baseDaoSupport.queryForString(SF.numeroSql("NUMERO_SYNCH_BATCH"), number);
		}
		if(!StringUtils.isEmpty(batch_id)){
			sql += " and batch_id=? ";
			pList.add(batch_id);
		}
		
		//如果是超级管理员则查全部工号的
		if(!Consts.CURR_FOUNDER_1.equals(Integer.toString(ManagerUtils.getAdminUser().getFounder()))){
			String userid = ManagerUtils.getAdminUser().getUserid();
			sql += " and oper_id=? ";
			pList.add(userid);
		}
		sql += " and status='"+Consts.CO_QUEUE_STATUS_XYSB+"' ";
		List<Map> pageList = this.baseDaoSupport.queryForList(sql, pList.toArray());
		int count = 0;
		if(pageList!=null && pageList.size()>0){
			List ids = new ArrayList();
			int size = pageList.size();
			for(int i=0;i<size;i++){
				count++;
				Map queue = pageList.get(i);
				ids.add(Const.getStrValue(queue, "co_id"));
			}
			CoQueueModifyReq req = new CoQueueModifyReq();
			req.setCo_ids(ids);
			
			String source_from=ManagerUtils.getSourceFrom();
			ZteClient client = ClientFactory.getZteDubboClient(source_from);
			client.execute(req, CoQueueModifyResp.class);
		}
		return count;
	}

	@Override
	public Map getNumberSpec(String dn_no) {
		String sql = SF.numeroSql("NUMBER_SPEC");
		
		List pList = new ArrayList();
		StringBuilder where = new StringBuilder();
		if(!StringUtils.isEmpty(dn_no)){
			where.append(" and dn_no=? ");
			pList.add(dn_no);
		}else{
			sql+="and rownum<2";
		}
		
		String source_from = ManagerUtils.getSourceFrom();
		if(!StringUtils.isEmpty(source_from)){
			where.append(" and source_from=? ");
			pList.add(source_from);
		}
		
		List<Map> numeros = this.baseDaoSupport.queryForList(sql+where.toString(), pList.toArray());
		Map numero = (numeros!=null && numeros.size()>0) ? numeros.get(0):null;
		return numero;
	}
	
	public boolean validateGroup_code(String group_code){
		String sql="select count(*) from es_no_group where group_code=?";
		int count=this.baseDaoSupport.queryForInt(sql, group_code);
		if(count>0)
		return true;
		else
		return false;
	}
	
}