package com.ztesoft.net.mall.core.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.EsGoodsCo;
import com.ztesoft.net.mall.core.service.IEsGoodsCoManager;
import com.ztesoft.net.mall.core.utils.GoodsManagerUtils;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

import commons.CommonTools;
import consts.ConstsCore;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import params.ZteError;
import params.coqueue.req.CoQueueAddReq;
import params.coqueue.resp.CoQueueAddResp;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.MessageSyncReq;
import zte.net.ecsord.params.ecaop.resp.MessageSyncResp;
import zte.net.iservice.ICoQueueService;

@SuppressWarnings("deprecation")
public class EsGoodsCoManager extends BaseSupport<EsGoodsCo> implements
		IEsGoodsCoManager {
	
	@Resource
	private ICoQueueService coQueueService;
	
	@Resource
	private JdbcTemplate jdbcTemplate;

	/**
	 * 商品发布
	 * */	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void liberacion(String org_ids, Map params) {
		//需要同步的商品串
		String goods_id_str = (String) params.get("esgoodscos");
		
        if (StringUtils.isEmpty(goods_id_str)) {
        	CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "需要同步的商品不能为空！"));
		}
        
        //需要同步的销售组织
        if (StringUtils.isEmpty(org_ids) ) {
        	CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "销售组织不能为空！"));
		}
        
        //获取并过滤要同步的销售组织（新商城的）
      	String sql = SF.goodsSql("GOODS_ZJ_ORG_ID");
      	sql += " and a.source_from = '" + ManagerUtils.getSourceFrom() + "'"
      		 + " and a.org_level = 2 and a.party_id in("+ org_ids +")";
      	
//      	String sql = "";
      	//获取过滤新商城，淘宝之外的组织
      	String sql2=SF.goodsSql("GOODS_ZJ_ORG_ID")+" and a.source_from = '" + ManagerUtils.getSourceFrom() + "'"
      		 + " and a.org_level = 2 and a.party_id in("+ org_ids +") and a.party_id not in(10008,10001,100082)" ;
      	
      	List<String> goodsOrgLst = this.jdbcTemplate.queryForList(sql, String.class);
      	String org_id_str = GoodsManagerUtils.convertStrFromLst(goodsOrgLst, ",");
      	
      	List<String> goodsOrgLst2 = this.jdbcTemplate.queryForList(sql2, String.class);
      	String org_id_str2 = GoodsManagerUtils.convertStrFromLst(goodsOrgLst2, ",");
        
      	List<String> goodsOrgLst3=dispite(org_id_str);
      	 //获取批次号
		//String batch_id = this.daoSupport.getSequences("S_ES_CO_BATCH_ID");
      	String batch_id = this.baseDaoSupport.getSequences("S_ES_CO_BATCH_ID");
       
		String[] goods_id_arr = goods_id_str.split(",");  //商品标识串
		String[] org_id_arr = org_ids.split(",");  //销售组织标识串
		
		//写同步状态表es_goods_co
		for (int i = 0; i < goods_id_arr.length; i++) {
			String enable = MapUtils.getString(params, "status_cd");
			String goods_id = goods_id_arr[i]; //商品标识
			
			
			for(int j = 0;j<org_id_arr.length;j++) {
				//如果是发布，插入同步表es_goods_co，如果是下架，更新es_goods_co
				this.doSaveCoZJ(goods_id, org_id_arr[j], enable,batch_id);
				
			}
			
			
			
			
//			if(Consts.MARKET_ENABLE_1.equals(market_enable)){				
//				
//				//写es_goods_co
////				for (int j = 0; j < goodsOrgLst.size(); j++) {
////					//保持goods_co跟co_queue的batch_id一致,并且根据不同商品不同商城batch_id不同---zengxianlian
////					batch_id = this.daoSupport.getSequences("S_ES_CO_BATCH_ID");
////					String org_id = goodsOrgLst.get(j);  //销售组织标识				
////					//this.doSaveCo(goods_id, org_id, batch_id);
////					this.doSaveCo(goods_id, org_id, batch_id,String.valueOf(Consts.PUBLISH_STATE_2));
////				}			
//				for(int q=0;q<goodsOrgLst3.size();q++){
//					String org = goodsOrgLst3.get(q);
//					//保持goods_co跟co_queue的batch_id一致,并且根据不同商品不同商城batch_id不同---zengxianlian
//					batch_id = this.daoSupport.getSequences("S_ES_CO_BATCH_ID");
//					if(!"10001".equals(org)&&!"10003".equals(org)){
//					//注释by zengixnalian 保持batch_id跟写goods_co一致,写队列
//						this.doSaveCoQueue(batch_id, goods_id, goods_id, org);
//					}
//					String orgs[] = org.split(",");
//					//写goods_co
//					for(String id : orgs){
//						if(!"10001".equals(org)&&!"10003".equals(org)){
//							this.doSaveCo(goods_id, id, batch_id,String.valueOf(Consts.PUBLISH_STATE_2));
//						}
//					}
//				}
//
//				List<Map> relations = CommonDataFactory.getInstance().getDictRelationListData(StypeConsts.ISTBORDER);
//				if(relations!=null && relations.size()>0){
//					for(Map relation : relations){
//						String order_from = Const.getStrValue(relation, "other_field_value");
//						if(org_ids.contains(order_from)){//淘宝的写队列
//							batch_id = this.daoSupport.getSequences("S_ES_CO_BATCH_ID");
//							//淘宝就改下状态......根本5需要同步比距
//							this.doSaveCo(goods_id, order_from, batch_id,String.valueOf(Consts.PUBLISH_STATE_1));
//						}
//					}
//				}
////				if(org_ids.contains("10001")){//淘宝的写队列
////					batch_id = this.daoSupport.getSequences("S_ES_CO_BATCH_ID");
////					this.doSaveCo(goods_id, "10001", batch_id);
////				}
//				
//				for(int k=0;k<goodsOrgLst2.size();k++){
//					batch_id = this.daoSupport.getSequences("S_ES_CO_BATCH_ID");
//					String org_id2=goodsOrgLst2.get(k);
//					if(!"10003".equals(org_id2)){
//						this.doSaveCo(goods_id, org_id2, batch_id,String.valueOf(Consts.PUBLISH_STATE_2));
//						this.doSaveCoQueueLv2(batch_id, goods_id, goods_id, org_id2);//写level2的队列
//					}else{
//						this.doSaveCo(goods_id, org_id2, batch_id,String.valueOf(Consts.PUBLISH_STATE_1));
//					}
//				}
//			}
		}
	}
	
	
	private List<String> dispite(String org_id_str) {
		// 把三级商城根据lv2的商城进行划分
		ArrayList<String> list=new ArrayList<String>();
		if (!StringUtils.isEmpty(org_id_str)){
			Map<String,String> data=new HashMap<String, String>();
			String[] org_belongs=new String[100];
			String org_belong="";
			String[] orgs=org_id_str.split(",");
			for(int i=0;i<orgs.length;i++){
				String sql="select parent_party_id from es_goods_org where party_id=? and rownum<2";
				org_belong=this.baseDaoSupport.queryForString(sql,orgs[i]);
				if(data.get(org_belong)==null){
					data.put(org_belong,orgs[i]);
				}else{
					String org=data.get(org_belong).toString()+","+orgs[i];
					data.remove(org_belong);
					data.put(org_belong, org);
				}
			}
			list=new ArrayList<String>(data.values());
		}
		return list;
	}

	private void doSaveCoQueueLv2(String batch_id, String object_id,
			String contents, String org_id) {
		// TODO Auto-generated method stub
		if(!StringUtils.isEmpty(org_id)){
			//写消息队列
			CoQueueAddReq coQueueAddReq = new CoQueueAddReq();
			coQueueAddReq.setCo_name("商品同步");
			coQueueAddReq.setBatch_id(batch_id);
			coQueueAddReq.setService_code(Consts.SERVICE_CODE_CO_SHANGPIN);
			coQueueAddReq.setAction_code(Consts.ACTION_CODE_A);
			coQueueAddReq.setObject_id(object_id);
			coQueueAddReq.setObject_type("SHANGPIN");
			coQueueAddReq.setContents(contents);
			coQueueAddReq.setOrg_id_str(org_id);
			coQueueAddReq.setOrg_id_belong(org_id);  //新商城，淘宝之外的level2组织
			coQueueAddReq.setSourceFrom(ManagerUtils.getSourceFrom());
			coQueueAddReq.setOper_id(ManagerUtils.getAdminUser().getUserid());
			
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			client.execute(coQueueAddReq, CoQueueAddResp.class);
		}
	}

	/**
	 * 写同步消息队列
	 * @param batch_id  批次号
	 * @param object_id  
	 * @param contents
	 * @param org_id_str  销售组织标识串
	 */
	private void doSaveCoQueue(String batch_id, String object_id, String contents, String org_id_str) {
		if(!StringUtils.isEmpty(org_id_str)){
			String org_belong="";
			String sql="select parent_party_id from  es_goods_org where party_id=?  and rownum<2";
			String org_id=org_id_str.split(",")[0];
			org_belong=this.baseDaoSupport.queryForString(sql,org_id);
			//写消息队列
			CoQueueAddReq coQueueAddReq = new CoQueueAddReq();
			coQueueAddReq.setCo_name("商品同步");
			coQueueAddReq.setBatch_id(batch_id);
			coQueueAddReq.setService_code(Consts.SERVICE_CODE_CO_SHANGPIN);
			coQueueAddReq.setAction_code(Consts.ACTION_CODE_A);
			coQueueAddReq.setObject_id(object_id);
			coQueueAddReq.setObject_type("SHANGPIN");
			coQueueAddReq.setContents(contents);
			coQueueAddReq.setOrg_id_str(org_id_str);
			coQueueAddReq.setOrg_id_belong(org_belong);  //新商城
			coQueueAddReq.setSourceFrom(ManagerUtils.getSourceFrom());
			coQueueAddReq.setOper_id(ManagerUtils.getAdminUser().getUserid());
						
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			client.execute(coQueueAddReq, CoQueueAddResp.class);
		}
	}
	
	/**
	 * 写同步状态表
	 * @param goods_id  商品标识
	 * @param org_id  组织标识
	 * @param batch_id  批次号
	 */
	private void doSaveCo(String goods_id, String org_id, String batch_id) {
		
		String sql = "select count(0) from ES_GOODS_CO where goods_id = ? and org_id = ?";
		int count = baseDaoSupport.queryForInt(sql, goods_id, org_id);
		if (count < 1) {
			EsGoodsCo eg  = new EsGoodsCo();
			eg.setGoods_id(goods_id);
			eg.setOrg_id(org_id);
			eg.setBatch_id(batch_id);
			eg.setCreated_date(DBTUtil.getDBCurrentTime());
			//eg.setStatus(String.valueOf(Consts.PUBLISH_STATE_1));
			//点击发布后,状态改为发布中---zengxianlian
			eg.setStatus(String.valueOf(Consts.PUBLISH_STATE_2));
			eg.setOper_id(ManagerUtils.getAdminUser().getUserid());
			eg.setSource_from(ManagerUtils.getSourceFrom());
			
			this.baseDaoSupport.insert("ES_GOODS_CO", eg);
		}
	}
	
	private void doSaveCoZJ(String goods_id, String org_id,String status, String batch_id) {
		//如果传入的status=1 ，则先判断es_goods_co表记录中是否存在status=4的记录，有则表示下架后第二次发布，更新字段即可。
		
		if(StringUtils.equals("1", status)) {
			List coLists = this.baseDaoSupport.queryForList("select goods_id from es_goods_co where goods_id=? and org_id = ? and status='4'", goods_id,org_id);
			if(null != coLists && coLists.size() > 0) {
				//直接更新数据
				String sql = "update es_goods_co set batch_id=? , status='1' where goods_id=? and org_id=?";
				this.baseDaoSupport.execute(sql, batch_id,goods_id,org_id);
			}else {
				EsGoodsCo eg  = new EsGoodsCo();
				eg.setGoods_id(goods_id);
				eg.setOrg_id(org_id);
				eg.setBatch_id(batch_id);
				eg.setCreated_date(DBTUtil.getDBCurrentTime());
				//点击发布后,状态改为已发布
				eg.setStatus(status);
				eg.setOper_id(ManagerUtils.getAdminUser().getUserid());
				eg.setSource_from(ManagerUtils.getSourceFrom());
			
				this.baseDaoSupport.insert("ES_GOODS_CO", eg);
			}
		}else if (StringUtils.equals("4", status)) {
			String sql = "update es_goods_co set batch_id=?,status=? where goods_id = ? and org_id=? and status='1'";
			this.baseDaoSupport.execute(sql,batch_id,status,goods_id,org_id);
		}
		
	}

	/**
	 * 写同步状态表
	 * @author zengxianlian
	 * @param goods_id  商品标识
	 * @param org_id  组织标识
	 * @param batch_id  批次号
	 * @param status 插入初始状态
	 */
	private void doSaveCo(String goods_id, String org_id, String batch_id,String status) {
		
		String sql = "select count(0) from ES_GOODS_CO where goods_id = ? and org_id = ?";
		int count = baseDaoSupport.queryForInt(sql, goods_id, org_id);
		if (count < 1) {
			EsGoodsCo eg  = new EsGoodsCo();
			eg.setGoods_id(goods_id);
			eg.setOrg_id(org_id);
			eg.setBatch_id(batch_id);
			eg.setCreated_date(DBTUtil.getDBCurrentTime());
			eg.setStatus(status);
			eg.setOper_id(ManagerUtils.getAdminUser().getUserid());
			eg.setSource_from(ManagerUtils.getSourceFrom());
			
			this.baseDaoSupport.insert("ES_GOODS_CO", eg);
		}else{//同步更新批次号,为co_queue保持一致---zengxianlian
			String s = "update ES_GOODS_CO set batch_id=? where goods_id = ? and org_id = ?";
			this.baseDaoSupport.execute(s, batch_id,goods_id,org_id);
		}
	}

	@Override
	public Page getFormList(int pageNo, int pageSize, Map<String, String> params) {
		StringBuilder temp = new StringBuilder(
				SF.goodsSql("ECGOODSCO_LIST"));
		String sql = wrapsql(temp, params);
		return baseDaoSupport.queryForPage(sql, pageNo, pageSize);
	}
	private String wrapsql(StringBuilder temp, Map<String, String> params) {
		String id = params.get("goodsId");
		if(StringUtils.isNotBlank(id)){
			temp.append(" and pc.goods_id ='"+id+"'");
		}		
		String source = ManagerUtils.getSourceFrom();		
		temp.append(" and pc.source_from = '"+source+"'");
		return temp.toString();
	}

	public void setCoQueueService(ICoQueueService coQueueService) {
		this.coQueueService = coQueueService;
	}


	@Override
	public void tagLiberacion(String ids, Map params) {
		// TODO Auto-generated method stub
		logger.info("ids="+ids+"params"+params);
		String tag_codes = (String) params.get("esgoodscos");
		String[] codes = tag_codes.split(",");
		
		for (String tag_code : codes) {
			Map<String,Object>result = this.baseDaoSupport.queryForMap("select * from es_sale_tag where tag_code = ?", tag_code);
			Map<String,Object>tag_req = new HashMap<String,Object>();
			tag_req.put("serial_no", this.baseDaoSupport.getSequences("tag_serial_no"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			tag_req.put("time",sdf.format(new Date()));
			tag_req.put("source_system", "10009");
			tag_req.put("receive_system", ids);
			tag_req.put("action", "A");
			tag_req.put("tag_name", MapUtils.getString(result, "tag_name"));
			tag_req.put("tag_code", tag_code);
			//标签与标签组关系表中找
			tag_req.put("parent_tag_code", "-1");
			
			JSONObject lastResult = JSONObject.fromObject(tag_req);
	        
	        Map<String,Object> fields = new HashMap<String,Object>();
	        
	        fields.put("serial_no", this.baseDaoSupport.getSequences("serial_no"));
	        fields.put("source_from", ManagerUtils.getSourceFrom());
	        fields.put("request", lastResult.toString()); 
	        
	        fields.put("create_date", new Date());
	        
	        this.baseDaoSupport.insert("es_goods_req", fields, null);
			
		}
	}
	
	@Override
	public void salegoodsLiberacion(String oids, Map params) {
		String orderids = (String) params.get("esgoodscos");
		// 外围系统总数量
		Long agents_count = this.baseDaoSupport
				.queryForLong("select count(*) from es_goods_zj_org where org_level = '2'");
		// this.baseDaoSupport.
		// this.baseDaoSupport.queryforint
		
		String[] ids = orderids.split(",");
		for (String id : ids) {
			String[] oidss = oids.split(",");
			for (String oid : oidss) {
				String enable = MapUtils.getString(params, "status_cd");
				Map salegoodsMap = this.baseDaoSupport
						.queryForMap("select * from es_sale_goods where 1=1 and sale_gid = ?", id);
				String serial_no = this.baseDaoSupport.getSequences("serial_no");
				Map<String, Object> sale_goods = new LinkedHashMap<String, Object>();
				sale_goods.put("serial_no", serial_no);

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				try {
					date = sdf.parse(MapUtils.getString(salegoodsMap, "create_time"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				sale_goods.put("time", sdf.format(date));
				sale_goods.put("source_system", "10009");
				sale_goods.put("receive_system", oid);

				
				String market_enable = MapUtils.getString(salegoodsMap, "market_enable");
				// 已同步过的外围系统
				logger.info(oid);
				String published_shops = MapUtils.getString(salegoodsMap, "publish_shop");
				
				/**
				 * ①MARKET_ENABLE=0；
				        ②（MARKET_ENABLE =3 or MARKET_ENABLE =2）&PUBLISH_SHOP不包含入参;
				 */
				String saleGoodsAction = "";
				if (market_enable.equals("0") || ((market_enable.equals("2") || market_enable.equals("3")) &&checkAgentByid(oid, params).equals(""))) {
					saleGoodsAction = "A";
				} 
				String saleGoodsSku = MapUtils.getString(salegoodsMap, "sku");
				String actionSql = "SELECT A.SKU,A.PUBLISH_SHOP,A.MARKET_ENABLE,B.RECEIVE_SYSTEM,B.STATUS" +
									" FROM ES_SALE_GOODS A LEFT JOIN ES_GOODS_REQ B ON A.SKU = B.SKU WHERE A.SKU=? AND A.SOURCE_FROM='ECS' AND B.RECEIVE_SYSTEM=? AND B.STATUS='2'";
				List<Map> saleGoodsActionList = this.baseDaoSupport.queryForList(actionSql, saleGoodsSku,oid);
				
				if((market_enable.equals("3") || market_enable.equals("2")) && (null != saleGoodsActionList && saleGoodsActionList.size()>0)){
					saleGoodsAction = "M";
				}
				
				if(enable.equals("2") || enable.equals("4")) {
					saleGoodsAction = "D";
				}
				
				sale_goods.put("action", saleGoodsAction);
				sale_goods.put("goods_offer_code", MapUtils.getString(salegoodsMap, "sale_gid"));
				sale_goods.put("goods_offer_name", MapUtils.getString(salegoodsMap, "sale_gname"));
				sale_goods.put("channel", MapUtils.getString(salegoodsMap, "channel_type"));
				sale_goods.put("goods_offer_type", MapUtils.getString(salegoodsMap, "package_type"));
				sale_goods.put("goods_offer_intro", MapUtils.getString(salegoodsMap, "intro"));
				if (enable.equals("1") || enable.equals("3")) {
					sale_goods.put("goods_offer_state", "0");
				} else if (enable.equals("2") || enable.equals("4")) {
					sale_goods.put("goods_offer_state", "1");
				}

				List<Map> tagcodes = this.baseDaoSupport
						.queryForList("select * from es_goods_tag_rel a where a.sale_goods_id = ?", id);
				String codes = new String();
				int i = 0;
				for (Map map : tagcodes) {
					String tag_group_id = MapUtils.getString(map, "tag_group_id");
					if (i == 0) {
						codes += tag_group_id;
					} else {
						codes += "," + tag_group_id;
					}
					i++;
				}
				sale_goods.put("goods_tag_code", codes);

				// 销售商品中存在的多个商品
				List<Map> goodsIdList = this.baseDaoSupport
						.queryForList("select z_goods_id from es_goods_rel where a_goods_id = ?", id);

				List<Map<String, Object>> goodsList = new ArrayList<Map<String, Object>>();
				for (Map map : goodsIdList) {
					String goodsid = MapUtils.getString(map, "z_goods_id");
					Map<String, Object> resultGood = this.baseDaoSupport
							.queryForMap("select * from es_goods where goods_id = ?", goodsid);

					Map<String, Object> goodsMap = new LinkedHashMap<String, Object>();

					goodsMap.put("prod_offer_code", MapUtils.getString(resultGood, "goods_id"));
					goodsMap.put("prod_offer_name", MapUtils.getString(resultGood, "name"));
					goodsMap.put("channel", "中国联通");
					goodsMap.put("prod_offer_type", MapUtils.getString(salegoodsMap, "package_type"));
					String goods_id = MapUtils.getString(resultGood, "goods_id");
					String cat_id = CommonDataFactory.getInstance().getGoodSpec(null,
							goods_id, "cat_id");
					String effect_date = MapUtils.getString(resultGood, "effect_date");
					String fail_date = MapUtils.getString(resultGood, "fail_date");
					try {
						effect_date = StringToDateToString(effect_date);
						fail_date   = StringToDateToString(fail_date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					goodsMap.put("effect_date", effect_date);
					goodsMap.put("fail_date"  , fail_date);
					goodsMap.put("prod_offer_cat", cat_id);
					goodsMap.put("prod_offer_state", MapUtils.getString(sale_goods, "goods_offer_state"));

					goodsMap.put("prod_offer_price", MapUtils.getString(resultGood, "price"));

					// 商品新增4个新增
					goodsMap.put("cust_id", getRelaByTableName("es_goods_rel_cust", goodsid));
					goodsMap.put("staff_id", getRelaByTableName("es_goods_rel_staff", goodsid));
					goodsMap.put("office_rela_id", getRelaByTableName("es_goods_rel_office", goodsid));
					goodsMap.put("develop_rela_id", getRelaByTableName("es_goods_rel_develop", goodsid));

					String goodsSql = SF.goodsSql("GOODS_PACKAGE_QUERY");
					goodsSql += " and goods_id = ?";
					List<Map> goodsPkgLst = this.baseDaoSupport.queryForList(goodsSql, goodsid);

					if (goodsPkgLst != null && goodsPkgLst.size() == 1) {
						goodsMap.put("prod_hq_act_code", MapUtils.getString(goodsPkgLst.get(0), "p_code"));
						goodsMap.put("prod_hq_code", MapUtils.getString(goodsPkgLst.get(0), "sn"));
					} else {
						goodsMap.put("prod_hq_act_code", "");
						goodsMap.put("prod_hq_code", "");
					}

					// 关联县分
					List<Map> countyList = this.baseDaoSupport
							.queryForList("select * from es_goods_rel_county where goods_id = ?", goodsid);
					// 关联小区
					List<Map> communityList = this.baseDaoSupport
							.queryForList("select * from es_goods_rel_community where  goods_id = ? ", goodsid);
					if (countyList != null && countyList.size() >= 1) {// 县分编码
						String countyids = "";
						int m = 0;
						for (Map<String, Object> map2 : countyList) {
							String countys = MapUtils.getString(map2, "countyid");
							if (m == 0) {
								countyids += countys;
							} else {
								countyids += "," + countys;
							}
							m++;
						}
						String countyid = MapUtils.getString(countyList.get(0), "countyid");
						Map<String, Object> countyMap = this.baseDaoSupport
								.queryForMap("select * from es_county  where countyid = ?", countyid);
						goodsMap.put("county", countyids);
						goodsMap.put("region_type", MapUtils.getString(countyMap, "region_type"));
						
					} else if (communityList != null && communityList.size() >= 1) {// 小区编码
						String communitys = "";
						int m = 0;
						for (Map map2 : communityList) {
							if (m == 0) {
								communitys += MapUtils.getString(map2, "community_code");
							} else {
								communitys += "," + MapUtils.getString(map2, "community_code");
							}
							m++;
						}
						goodsMap.put("county", communitys);
						goodsMap.put("region_type", "community");
					} else {
						goodsMap.put("county", "");
						goodsMap.put("region_type", "");
					}

					List<Map> scheme_list = this.baseDaoSupport
							.queryForList("select t.scheme_id,t.scheme_name,t.element_id,t.element_name,t.element_type,t.mobile_type,t.terminal_name,to_char(t.syn_time,'yyyy-mm-dd hh24:mi:ss') as syn_time from es_goods_action_element t where t.goods_id= ?", goodsid);
					Map bss_action_element_info = null;
					if(scheme_list.size()>0){
						Map sch_map = scheme_list.get(0);
						String scheme_id = Const.getStrValue(sch_map, "scheme_id");
						String scheme_name = Const.getStrValue(sch_map, "scheme_name");
						bss_action_element_info = new HashMap();
						bss_action_element_info.put("scheme_id",scheme_id);
						bss_action_element_info.put("scheme_name",scheme_name);
						bss_action_element_info.put("element_num", 1);
						for (int j = 0; j < scheme_list.size(); j++) {
							Map scheme_map = scheme_list.get(j);
							scheme_map.remove("scheme_id");
							scheme_map.remove("scheme_name");
						}
						bss_action_element_info.put("element_info", scheme_list);
						bss_action_element_info.put("must_element_info", new ArrayList());
						goodsMap.put("bss_action_element_info", bss_action_element_info);
					}
					
					String goodsParams = MapUtils.getString(resultGood, "params");
					if (goodsParams != null && !goodsParams.equals("") && !goodsParams.equals("[]")) {
						JSONObject json = JSONObject.fromObject(goodsParams.substring(1, goodsParams.length() - 1));
						// json转jsonArray
						net.sf.json.JSONArray jsonArr = json.getJSONArray("paramList");
						// jsonArray转List
						List<Map> goods_attr_params_list = (List<Map>) JSONArray.toCollection(jsonArr, Map.class);
						List<Map> result_goods_attr = new ArrayList<Map>();

						for (Map map2 : goods_attr_params_list) {
							Map<String, Object> new_goods_attr = new LinkedHashMap<String, Object>();
							if (map2 != null) {
								if (MapUtils.getString(map2, "value") != null
										&& !MapUtils.getString(map2, "value").equals("")) {
									new_goods_attr.put("param_code", MapUtils.getString(map2, "ename"));
									new_goods_attr.put("param_name", MapUtils.getString(map2, "name"));
									new_goods_attr.put("param_value_code", MapUtils.getString(map2, "attrcode"));
									List goods_param_value = getDcSqlByDcName(MapUtils.getString(map2, "attrcode"));

									if (goods_param_value == null || goods_param_value.size() == 0) {
										new_goods_attr.put("param_value", MapUtils.getString(map2, "value"));
										new_goods_attr.put("param_value_id", "");
									} else {
										for (int j = 0; j < goods_param_value.size(); j++) {
											String value = Const.getStrValue((Map) goods_param_value.get(j), "value");
											String value_desc = Const.getStrValue((Map) goods_param_value.get(j),
													"value_desc");
											if (StringUtil.equals(value, MapUtils.getString(map2, "value"))) {
												new_goods_attr.put("param_value", value_desc);
												break;
											}
										}
										new_goods_attr.put("param_value_id", MapUtils.getString(map2, "value"));
									}
									result_goods_attr.add(new_goods_attr);
								}
							}
						}
						goodsMap.put("prod_offer_param", result_goods_attr);
					} else {
						goodsMap.put("prod_offer_param", new ArrayList());
					}
					goodsList.add(goodsMap);
					//
				}
				// 商品信息
				sale_goods.put("prod_offer", goodsList);

				Map finishMap = new HashMap();
				finishMap.put("goods_offer_req", sale_goods);
				JSONObject lastResult = JSONObject.fromObject(finishMap);
				String seq_no = this.baseDaoSupport.getSequences("req_serial_no");
				Map<String, Object> fields = new HashMap<String, Object>();

				fields.put("serial_no", seq_no);
				fields.put("source_from", ManagerUtils.getSourceFrom());
				fields.put("request", lastResult.toString());
				fields.put("sku", MapUtils.getString(salegoodsMap, "sku"));
				fields.put("req_time", new Date());
				fields.put("receive_system", oid);
				if (enable.equals("3")) {
					enable = "1";
				} else if (enable.equals("4")) {
					enable = "2";
				}
				fields.put("status", enable);

				this.baseDaoSupport.insert("es_goods_req", fields, null);

				// 取当前销售商品同步的组织
				String countSql = "select publish_shop from es_sale_goods where sale_gid =" + id;
				String publish_shop = this.baseDaoSupport.queryForString(countSql);
				int publishshops_count = 0;
				String[] publish_shops = new String[] {};

				if (publish_shop == null) {
					publish_shop = "";
					publishshops_count = 0;
				} else {
					publish_shops = publish_shop.split(",");
					publishshops_count = publish_shops.length;
				}

				// 同步过的系统数量小于总外围系统数量且不是下架操作
				if ((publish_shops.length < agents_count) && !enable.equals("2") && !enable.equals("4")) {
					if (publish_shop.equals("")) {
						publish_shop = oid;
					} else {
						publish_shop += "," + oid;
					}

					this.baseDaoSupport.execute("update es_sale_goods t set t.publish_shop ='" + publish_shop
							+ "' where t.sale_gid =" + id);
					publishshops_count += 1;
				}
				// 同步后的系统数量小于所有外围系统数量，状态为3可同步
				if (publishshops_count < agents_count && !enable.equals("2") && !enable.equals("4")) {
					enable = "3";
				} else if (!enable.equals("2") && publishshops_count == agents_count && !enable.equals("4")) {// 同步后的系统数量等于外围系统数量，状态为1已同步
					enable = "1";
				} else if (enable.equals("2") || enable.equals("4")) {// 处理下架操作,删除对应的外围系统
					List<String> temp = new ArrayList();
					for (String shop : publish_shops) {
						if (oid.equals(shop)) {
							continue;
						}
						temp.add(shop);
					}

					String tempString = StringUtils.join(temp, ",");
					this.baseDaoSupport.execute(
							"update es_sale_goods t set t.publish_shop ='" + tempString + "' where t.sale_gid =" + id);

					// 判断同步过的系统数量，如果为0，则置为2下架状态，如果小于外围系统数量则是3可同步状态
					enable = temp.size() == 0 ? "2" : "3";
				}

				// 更新销售商品状态
				String sql = " update es_sale_goods t set t.market_enable = " + enable + " where t.sale_gid in (" + id
						+ ")";
				this.baseDaoSupport.execute(sql);

				// 消息同步
				MessageSyncReq req = new MessageSyncReq();

				req.setSerial_no(this.baseDaoSupport.getSequences("serial_no"));
				req.setAction(MapUtils.getString(sale_goods, "action"));
				req.setSeq_no(seq_no);
				req.setSource_system("10009");
				req.setReceive_system(oid);
				req.setType("goods_offer");
				req.setTime(sdf.format(new Date()));

				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				client.execute(req, MessageSyncResp.class);
			}
		}
	}
	
	public List<Map> getDcSqlByDcName(String dcName) {
		List<Map> list = new ArrayList<Map>();
		StringBuilder sql = new StringBuilder("select t.dc_sql from es_dc_sql t where t.dc_name='"+dcName+"'");
		List<Map> dc_sql_list = baseDaoSupport.queryForList(sql.toString());
		if (dc_sql_list != null && dc_sql_list.size() > 0) {
			String dc_sql = (dc_sql_list.get(0).get("dc_sql")).toString();
			list = baseDaoSupport.queryForList(dc_sql);
		}
		return list;
	}
	
	private String getRelaByTableName(String tablename,String goods_id) {
		String fields = "";
		if(tablename.equals("es_goods_rel_staff")) {
			fields = "staff_id";
		}else if(tablename.equals("es_goods_rel_cust")) {
			fields = "cust_id";
		}else if(tablename.equals("es_goods_rel_develop")) {
			fields = "develop_rela_id";
		}else if (tablename.equals("es_goods_rel_office")) {
			fields = "office_rela_id";
		}
		
		String sql = "select "+fields+" from " +tablename+ " where goods_id = ?";
		List<Map> arrayList = this.baseDaoSupport.queryForList(sql, goods_id);
		String finalAttr = "";
		if(arrayList != null && arrayList.size() >= 1) {
			int i = 0;
			for (Map attrFiled : arrayList) {
				String attr = MapUtils.getString(attrFiled, fields);
				if(i == 0) {
					finalAttr += attr;
				}else {
					finalAttr += ","+attr;
				}
				i++;
			}
		}
		
		return finalAttr;
	}
	
	@Override
	public String checkAgentByid(String oids,Map params) {
		String orderids = (String) params.get("esgoodscos");
		
		String enable = MapUtils.getString(params, "status_cd");
		String[] ids = orderids.split(",");
		
		for (String salegoodid : ids) {
			String sql = "select publish_shop from es_sale_goods where sale_gid ="+salegoodid;
			String publish_shop = this.baseDaoSupport.queryForString(sql);
			
			if(publish_shop == null || "".equals(publish_shop)) {
				return "";
			}
			
			//需要操作的oids
			String[] agents = oids.split(",");
			//销售商品的oids
			String[] saleAgents = publish_shop.split(",");
			
			if(enable.equals("2")) {
				for (String string : agents) {
					if(publish_shop.contains(string)) {
						continue;
					} else {
						String agent_name = this.baseDaoSupport.queryForString("select org_name from es_goods_zj_org where org_code ="+string);
						
						return agent_name;
					}
				}
			} else {
				for(int i = 0;i<agents.length;i++) {
					for(int j = 0;j<saleAgents.length;j++) {
						if(agents[i].equals(saleAgents[j])) {
							String agent_name = this.baseDaoSupport.queryForString("select org_name from es_goods_zj_org where org_code in ("+agents[i] +")");
							return agent_name;
						}
					}
				}
			}
		}
		return "";
	}
	/**
	 * oids 外围系统
	 * params 商品ID
	 */
	@Override
	public String checkGoodsAgentByid(String oids, Map params) {
		String goodsIds = (String) params.get("esgoodscos");
		String[] goodsIDArr = goodsIds.split(",");
		String[] oidsArr = oids.split(",");
		String status_cd = (String) params.get("status_cd");

		for (int i = 0; i < goodsIDArr.length; i++) {
			String goodId = goodsIDArr[i];
			for (int j = 0; j < oidsArr.length; j++) {
				String oid = oidsArr[j];

				String checkSql = "select ego.org_name " + " from es_goods_co egc " + " left join es_goods_zj_org ego "
						+ " on egc.org_id = ego.party_id " + " where egc.status = '" + status_cd + "'"
						+ " and egc.source_from = '" + ManagerUtils.getSourceFrom() + "'" + " and egc.goods_id ='"
						+ goodId + "'" + " and egc.org_id ='" + oid + "'";
				List orgArr = this.baseDaoSupport.queryForList(checkSql, null);

				if (null != orgArr && orgArr.size() > 0) {
					Map orgName = (Map) orgArr.get(0);
					return "外围系统：--" + MapUtils.getString(orgName, "org_name") + "--已发布或下架，无法重复执行。";
				}
			}
		}
		return "";
	}
	
	private String StringToDateToString(String dateString) throws ParseException {
		if(StringUtils.isEmpty(dateString)) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(dateString);

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		String dateS = sdf1.format(date);
		
		return dateS;
	}
}
