package com.ztesoft.net.mall.core.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.coqueue.req.CoQueueAddReq;
import params.coqueue.req.CoQueueModifyReq;
import params.coqueue.resp.CoQueueAddResp;
import params.coqueue.resp.CoQueueModifyResp;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.ObjectNotFoundException;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.net.framework.util.ReflectionUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.ActivityAttr;
import com.ztesoft.net.mall.core.model.ActivityCo;
import com.ztesoft.net.mall.core.model.ActivityImportLog;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsOrg;
import com.ztesoft.net.mall.core.model.PmtGoos;
import com.ztesoft.net.mall.core.model.Promotion;
import com.ztesoft.net.mall.core.model.PromotionActivity;
import com.ztesoft.net.mall.core.service.IPromotionActivityManager;
import com.ztesoft.net.mall.core.utils.GoodsManagerUtils;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;
import commons.CommonTools;

/**
 * 促销活动
 * 
 * @author lzf<br/>
 *         2010-4-20下午04:56:34<br/>
 *         version 1.0
 */
public class PromotionActivityManager extends BaseSupport
		implements IPromotionActivityManager {
	
    @Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void doPublishActivity(String activity_id, String org_ids) {
		
		if (StringUtils.isNotBlank(activity_id)) {
			String[] activity_ids = activity_id.split(";");
			String[] org_ids_arr = org_ids.split(";");
			int size = activity_ids.length;
			for(int i=0;i<size;i++){
				activity_id = activity_ids[i];
				org_ids = org_ids_arr[i];
				//总商,淘宝,深圳商城,广州微店不需要同步活动,直接改状态---zengxianlian
				if("10003".equals(org_ids)||"10001".equals(org_ids)||"10013".equals(org_ids)||"10056".equals(org_ids)){
					//修改活动发布状态为发布完成
					editStatusECS(activity_id,Consts.PUBLISH_STATE_1+"",org_ids);
				}else{
					//写同步消息队列es_co_queue
					this.writeQueue(activity_id, org_ids,Consts.ACTION_CODE_A);
					
					//修改活动发布状态为发布中
					editStatusECS(activity_id,Consts.PUBLISH_STATE_2+"",org_ids);
				}
			}
		}
	}
	
	/**
	 * 写活动同步消息队列
	 * @param activityId
	 * @param org_ids
	 */
	private void writeQueue(String activityId, String org_ids,String action_code,String oper_id) {
		
    	String source_from = ManagerUtils.getSourceFrom();
    	
		if (StringUtils.isNotBlank(org_ids)) {
			
			//获取并过滤要同步的销售组织（新商城的）
			String sql = SF.goodsSql("GOODS_ORG_ID");
			sql += " and a.source_from = '" + ManagerUtils.getSourceFrom() + "'"
				 + " and a.org_level = 3 and a.party_id in("+ org_ids +")";
			List<String> goodsOrgLst = this.jdbcTemplate.queryForList(sql, String.class);
			String org_id_str = GoodsManagerUtils.convertStrFromLst(goodsOrgLst, ",");
			if(!StringUtils.isEmpty(org_id_str)){
				//获取批次号
				String batch_id = this.baseDaoSupport.queryForString(
						SF.goodsSql("QRY_BATCH_ID_BY_ID"), activityId);
				
				//写消息队列
				CoQueueAddReq coQueueAddReq = new CoQueueAddReq();
				coQueueAddReq.setCo_name("活动同步");
				coQueueAddReq.setBatch_id(batch_id);
				coQueueAddReq.setOrg_id_str(org_id_str);
				coQueueAddReq.setOrg_id_belong("10008");  //新商城
				coQueueAddReq.setService_code(Consts.SERVICE_CODE_CO_HUODONG);
				coQueueAddReq.setAction_code(action_code);
				coQueueAddReq.setObject_id(activityId);
				coQueueAddReq.setObject_type("HUODONG");
				coQueueAddReq.setContents(activityId);
				coQueueAddReq.setSourceFrom(source_from);
				coQueueAddReq.setOper_id(oper_id);
				ZteClient client = ClientFactory.getZteDubboClient(source_from);
				client.execute(coQueueAddReq, CoQueueAddResp.class);
			}
		}
	}
	
	/**
	 * 写活动同步消息队列
	 * @param activityId
	 * @param org_ids
	 */
	private void writeQueue(String activityId, String org_ids,String action_code) {
		
    	String source_from = ManagerUtils.getSourceFrom();
    	
		if (StringUtils.isNotBlank(org_ids)) {
			
			//获取并过滤要同步的销售组织（新商城的）
			String sql = SF.goodsSql("GOODS_ORG_ID");
			sql += " and a.source_from = '" + ManagerUtils.getSourceFrom() + "'"
				 + " and a.org_level = 3 and a.party_id in("+ org_ids +")";
			String sql2=SF.goodsSql("GOODS_ORG_ID")+" and a.source_from = '" + ManagerUtils.getSourceFrom() + "'"
		      		 + " and a.org_level = 2 and a.party_id in("+ org_ids +") and a.party_id not in(10008,10001)" ;
			List<String> goodsOrgLst = this.jdbcTemplate.queryForList(sql, String.class);
			String org_id_str = GoodsManagerUtils.convertStrFromLst(goodsOrgLst, ",");

			List<String> goodsOrgLst2 = this.jdbcTemplate.queryForList(sql2, String.class);
	      	String org_id_str2 = GoodsManagerUtils.convertStrFromLst(goodsOrgLst2, ",");
	      	
	      	List<String> goodsOrgLst3=dispite(org_id_str);
	      	
			if(!StringUtils.isEmpty(org_id_str)){
				for(int q=0;q<goodsOrgLst3.size();q++){
				//获取批次号,根据orgId---zengxianlian
					String batch_id = this.baseDaoSupport.queryForString(
							SF.goodsSql("QRY_BATCH_ID_BY_ID_ORG"), activityId,goodsOrgLst3.get(q));
//				String batch_id = this.baseDaoSupport.queryForString(
//						SF.goodsSql("QRY_BATCH_ID_BY_ID"), activityId);
				if(StringUtils.isEmpty(batch_id)){
					batch_id = this.baseDaoSupport.getSequences("S_ES_CO_BATCH_ID");
				}
				
				String org_belong="";
				String sqlBel="select parent_party_id from  es_goods_org where party_id=?  and rownum<2";
				String org_id=goodsOrgLst3.get(q).split(",")[0];
				org_belong=this.baseDaoSupport.queryForString(sqlBel,org_id);
				//写消息队列
				CoQueueAddReq coQueueAddReq = new CoQueueAddReq();
				coQueueAddReq.setCo_name("活动同步");
				coQueueAddReq.setBatch_id(batch_id);
				coQueueAddReq.setOrg_id_str(goodsOrgLst3.get(q));
				coQueueAddReq.setOrg_id_belong(org_belong);  //新商城
				coQueueAddReq.setService_code(Consts.SERVICE_CODE_CO_HUODONG);
				coQueueAddReq.setAction_code(action_code);
				coQueueAddReq.setObject_id(activityId);
				coQueueAddReq.setObject_type("HUODONG");
				coQueueAddReq.setContents(activityId);
				coQueueAddReq.setSourceFrom(source_from);
				coQueueAddReq.setOper_id(ManagerUtils.getAdminUser().getUserid());
				ZteClient client = ClientFactory.getZteDubboClient(source_from);
				client.execute(coQueueAddReq, CoQueueAddResp.class);
				}
			}
			
			if(!StringUtils.isEmpty(org_id_str2)){
				//获取批次号
				for(int k=0;k<goodsOrgLst2.size();k++){
					String org_id2=goodsOrgLst2.get(k);
					//获取批次号,根据orgId---zengxianlian
					String batch_id = this.baseDaoSupport.queryForString(
							SF.goodsSql("QRY_BATCH_ID_BY_ID_ORG"), activityId,goodsOrgLst2.get(k));
//					String batch_id = this.baseDaoSupport.queryForString(
//							SF.goodsSql("QRY_BATCH_ID_BY_ID"), activityId,);
					if(StringUtils.isEmpty(batch_id)){
						batch_id = this.baseDaoSupport.getSequences("S_ES_CO_BATCH_ID");
					}
					
					//写消息队列
					CoQueueAddReq coQueueAddReq = new CoQueueAddReq();
					coQueueAddReq.setCo_name("活动同步");
					coQueueAddReq.setBatch_id(batch_id);
					coQueueAddReq.setOrg_id_str(org_id2);
					coQueueAddReq.setOrg_id_belong(org_id2);  //新商城之外的商城
					coQueueAddReq.setService_code(Consts.SERVICE_CODE_CO_HUODONG);
					coQueueAddReq.setAction_code(action_code);
					coQueueAddReq.setObject_id(activityId);
					coQueueAddReq.setObject_type("HUODONG");
					coQueueAddReq.setContents(activityId);
					coQueueAddReq.setSourceFrom(source_from);
					coQueueAddReq.setOper_id(ManagerUtils.getAdminUser().getUserid());
					ZteClient client = ClientFactory.getZteDubboClient(source_from);
					client.execute(coQueueAddReq, CoQueueAddResp.class);
				}
				
			}
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

	public void editStatusECS(String activityId,String status,String orgId) {
		String sql = SF.goodsSql("UPDATE_ACTIVITY_STATUS_ECS");
		//this.baseDaoSupport.execute(sql, Consts.PUBLISH_STATE_1,activityId);
		//点击发布把状态该成发布中----zengxianlian
		this.baseDaoSupport.execute(sql, status,activityId,orgId);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(PromotionActivity activity, String[] tagids) {
		if(activity == null ) throw new  IllegalArgumentException("param activity is NULL");
		if(activity.getName() == null ) throw new  IllegalArgumentException("param activity.name is NULL");
		if(activity.getBegin_time() == null ) throw new  IllegalArgumentException("param activity.begin_time is NULL");
		if(activity.getEnd_time() == null ) throw new  IllegalArgumentException("param activity.end_time is NULL");
		
		AdminUser au = ManagerUtils.getAdminUser();
		String userid = au.getUserid();
		if(Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
			userid = au.getUserid();
		if(ManagerUtils.isAdminUser())
			userid="-1";
		activity.setUserid(userid);
		
		this.baseDaoSupport.insert("promotion_activity", activity);
		
		//插入标签关联表es_tag_rel
		if (tagids != null) {
			for (String tagid : tagids) {
				this.daoSupport.execute(SF.goodsSql("TAG_REL_INSERT"), tagid, activity.getId());
			}
		}
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(String[] idArray) {
		if (idArray != null && idArray.length > 0) {
			String ids = StringUtil.arrayToString(idArray, ",");
			this.baseDaoSupport.execute(SF.goodsSql("PROMOTION_ACTIVITY_DELETE") + " and id in (" + ids + ")");
			
			this.daoSupport.execute(SF.goodsSql("PMT_MEMBER_LV_DELETE").replace("replaceSql", ids));
			this.daoSupport.execute(SF.goodsSql("PMT_GOODS_DELETE").replace("replaceSql", ids));
			this.baseDaoSupport.execute(SF.goodsSql("PROMOTION_DELETE") + " and pmta_id in(" + ids + ")");
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void edit(PromotionActivity activity, String[] tagids) {
		
		if(activity.getId() == null ) throw new  IllegalArgumentException("param activity.id is NULL");
		if(activity.getName() == null ) throw new  IllegalArgumentException("param activity.name is NULL");
		if(activity.getBegin_time() == null ) throw new  IllegalArgumentException("param activity.begin_time is NULL");
		if(activity.getEnd_time() == null ) throw new  IllegalArgumentException("param activity.end_time is NULL");
		this.baseDaoSupport.update("promotion_activity", activity, "id="+ activity.getId());
		
		//清空原有引用
		this.daoSupport.execute(SF.goodsSql("TAG_REL_DEL_BY_ID"), activity.getId());
				
		//插入标签关联表es_tag_rel
		if (tagids != null) {
			for (String tagid : tagids) {
				this.daoSupport.execute(SF.goodsSql("TAG_REL_INSERT"), tagid, activity.getId());
			}
		}

	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void editEnable(String editEnable, String activity_id) {
		
		if(editEnable == null ) throw new  IllegalArgumentException("param editEnable is NULL");
		if(activity_id == null ) throw new  IllegalArgumentException("param editEnable is NULL");
		
	    this.baseDaoSupport.execute(SF.goodsSql("PROMOTION_ACTIVITY_ENABLE"), editEnable, activity_id);
	}
	
	@Override
	public PromotionActivity get(String id) {
		if(id == null ) throw new  IllegalArgumentException("param activity.id is NULL");
		PromotionActivity activity = (PromotionActivity) this.baseDaoSupport.queryForObject(SF.goodsSql("PROMOTION_ACTIVITY_SELECT"),
				PromotionActivity.class, id);
		if(activity == null) throw new ObjectNotFoundException("activity is NULL");
		
		//货品包名串解析
		if (StringUtils.isNotEmpty(activity.getRelation_id())) {
			String relation_name_str = "";
			String sql = SF.goodsSql("ACTIVITY_GOODS_PACKAGE_LIST");
			sql += " and a.relation_id in(" + activity.getRelation_id() + ")";
			List packageLst = this.baseDaoSupport.queryForList(sql);
			for (int i=0; packageLst != null && i<packageLst.size(); i++) {
				Map packageMap = (Map)packageLst.get(i);
				if (i == 0) {
					relation_name_str = (String)packageMap.get("name");
				} else {
					relation_name_str += "," + packageMap.get("name");
				}
			}
			activity.setRelation_name(relation_name_str);
		}
		
		return activity;
	}

	
	@Override
	public Page list(PromotionActivity activity,int pageNo, int pageSize) {
		AdminUser au = ManagerUtils.getAdminUser();
		String userid = au.getUserid();
		if(Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
			userid = au.getUserid();
		
		String sql = SF.goodsSql("PROMOTION_ACTIVITY_GET");
		
		if (!ManagerUtils.isNetStaff()) {
			sql += "  and a.userid = " + userid;
		}
		if(!StringUtil.isEmpty(activity.getName())){
			sql += " AND a.name like '%"+activity.getName()+"%'";
		}
		if(!StringUtil.isEmpty(activity.getReal_name())){
			sql += " AND b.realname like '%"+activity.getReal_name()+"%'";
		}
		if(!StringUtil.isEmpty(activity.getBegin_time())){
			sql += " AND a.begin_time >= "+ DBTUtil.to_date(activity.getBegin_time(), 1)+"";
		}
		if(!StringUtil.isEmpty(activity.getEnd_time())){
			sql += " AND a.end_time <= "+DBTUtil.to_date(activity.getEnd_time(), 1)+"";
		}
		sql += " order by a.begin_time desc";
		
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
	}
	/**
	 * 活动选择器选择活动列表
	 * @param name
	 * @param order
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page list(String name, String order, int page,
			int pageSize) {
//		order = order == null ? " fo_id desc" : order;
		String sql =  SF.goodsSql("PROMOTION_ACTIVITY_GET_1");
		name = name == null ? "": " a.name like '%" + name+ "%'";
		if(!StringUtils.isEmpty(name)){
			sql += " and " + name;
		}
		
		Page webpage = this.daoSupport.queryForPage(sql, page, pageSize);
		return webpage;
	}
	
	@Override
	public List list(String[] ids) {
		if (ids == null || ids.length == 0)
			return new ArrayList();
		StringBuffer sb = new StringBuffer("(");
		for(int i=0; i< ids.length; i++){
			if(i == ids.length -1){
				sb.append("'" + ids[i]+ "'");
			}else{
				sb.append("'" + ids[i]+ "',");
			}
		}
		sb.append(")");
		
		
		String sql = SF.goodsSql("PROMOTION_ACTIVITY_SELECT_1") + sb.toString();
		return this.baseDaoSupport.queryForList(sql);
	}
	@Override
	public Page pageList(String tagId, String userId, int pageNo, int pageSize) {
		
		String sql = SF.goodsSql("PROMOTION_ACTIVITY_GET_BY_TAG_ID");
        //String sql="select a.*  from ES_PROMOTION_ACTIVITY a, ES_TAG_REL b where 1 = 1 and a.id = b.rel_id  and begin_time < "+ DBTUtil.current()+" and end_time > "+ DBTUtil.current()+" and b.tag_id = ? AND b.source_from=?";
        List param = new ArrayList();
        param.add(ManagerUtils.getSourceFrom());
        param.add(tagId);
		if (!StringUtils.isEmpty(userId)) {
			sql += "  and a.userid = ?";
            param.add(userId);
		}
		sql += " order by a.rank";


        return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, param.toArray());
	}
/**
 * 查找promotion_activity 的主键
 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String queryActivityId(String name){
		String sql = SF.goodsSql("PROMOTION_ACTIVITY_ID");
	    List param = new ArrayList();
		if (!StringUtils.isEmpty(name)) {
			sql += "  and t.name = ?";
            param.add(name);
		}
		return this.baseDaoSupport.queryForString(sql);
	 }
	
	/**
	 * 根据活动标识获取活动基本信息及其活动规则（多个）和适用该活动的商品列表
	 * @param pmt_id
	 * @return
	 */
	@Override
	public Map getPromotionMap(String activity_id) {
		
		Map pmtMap = new HashMap();
		List goodsLst = new ArrayList();
		List giftsLst = new ArrayList();
		List paramLst = new ArrayList();
		String pmt_id = "-111111";  //活动规则标识
		String pmt_type = "";  //活动规则类型
		String source_from = ManagerUtils.getSourceFrom();
		String sql_goods = SF.goodsSql("GET_GOODS_PMT");  //获取适应该活动规则的商品列表或赠品列表
		
		paramLst.add(Consts.DC_PUBLIC_STYPE_PMT_TYPE);
		paramLst.add(source_from);
		paramLst.add(activity_id);
		paramLst.add(Consts.DC_PUBLIC_STYPE_PMT_TYPE);
		paramLst.add(source_from);
		paramLst.add(activity_id);
		this.logger.info("getPromotionMap activity_id = " + activity_id);
		
		//获取活动集活动规则的基本信息，包括：活动标识、编码、名称、开始时间、结束时间、活动描述、活动类型、活动规则
		String sql = SF.goodsSql("GET_POMOTION_MAP");
		List<Map> pmtBaseLst = this.baseDaoSupport.queryForList(
				sql, paramLst.toArray());
		
		if (pmtBaseLst == null || pmtBaseLst.size() == 0) {
			CommonTools.addFailError("没有找到对应的活动信息  activity_id = " + activity_id);
		}

		//获取参与该活动的商品列表开始
		int pmtBaseSize = pmtBaseLst.size();
		this.logger.info("获取参与该活动的商品列表开始   getPromotionMap pmtBaseSize = " + pmtBaseSize);
		
		for (Map tmpMap : pmtBaseLst) {
			
			pmt_type = (String)tmpMap.get("pmt_type");  //活动的规则类型
			pmt_id = (String)tmpMap.get("pmt_id");  //规则标识
			
			this.logger.info("getPromotionMap pmt_type = " + pmt_type);
			this.logger.info("getPromotionMap pmt_id = " + pmt_id);
			
			//如果某活动有两个活动规则，其中有赠品的，则获取赠品列表
			if (Consts.PMT_TYPE_GIFT.equalsIgnoreCase(pmt_type)) {
				
				paramLst.clear();
				paramLst.add(source_from);
				paramLst.add(pmt_id);
				paramLst.add(source_from);
				paramLst.add(pmt_id);
				giftsLst = this.baseDaoSupport.queryForList(sql_goods, paramLst.toArray());
				continue;
			}
			
			//设置活动基本信息
			pmtMap.putAll(tmpMap);
			
			//获取适应该活动规则的商品(赠品)列表
			paramLst.clear();
			paramLst.add(source_from);
			paramLst.add(pmt_id);
			paramLst.add(source_from);
			paramLst.add(pmt_id);
			
			//如果是满赠就放入赠品列表
			if (Consts.PMT_TYPE_MANZENG.equalsIgnoreCase(pmt_type)) {
				giftsLst = this.baseDaoSupport.queryForList(sql_goods, paramLst.toArray());
			} else {
				goodsLst = this.baseDaoSupport.queryForList(sql_goods, paramLst.toArray());
			}
		}
		
		pmtMap.put("goods_list", goodsLst);
		pmtMap.put("gifts_list", giftsLst);
		this.logger.info("getPromotionMap pmtMap = " + pmtMap);
		
		return pmtMap;
	}
	
	public Map getActivitySpecMap(String activity_code){
		Map pmtMap = new HashMap();
		List paramLst = new ArrayList();
		String source_from = ManagerUtils.getSourceFrom();
		
		paramLst.add(Consts.DC_PUBLIC_STYPE_PMT_TYPE);
		paramLst.add(source_from);
		paramLst.add(activity_code);
		this.logger.info("getActivitySpecMap activity_code = " + activity_code);
		
		//获取活动集活动规则的基本信息，包括：活动标识、编码、名称、开始时间、结束时间、活动描述、活动类型、活动规则
		String sql = SF.goodsSql("ACTIVITY_SPEC_MAP");
		List<Map> pmtBaseLst = this.baseDaoSupport.queryForList(sql, paramLst.toArray());
		
		if (pmtBaseLst == null || pmtBaseLst.size() == 0) {
			CommonTools.addFailError("没有找到对应的活动信息  activity_code = " + activity_code);
		}
		this.logger.info("getActivitySpecMap pmtMap = " + pmtMap);
		pmtMap = (pmtBaseLst!=null && pmtBaseLst.size()>0) ? pmtBaseLst.get(0) : pmtMap;
		return pmtMap;
	}

	@Override
	public void checkImport(File file,String fileFileName,HttpServletResponse response) throws Exception{
		String fileTitle = "校验结果";
		String[] content = new String [] {"pmt_code","name","enable","user_type","pmt_type","pmt_price","relation_name","package_class","min_price","max_price","region","modify_eff_time","org_id_str","available_period","brief"};
		String[] title = new String [] {"活动ID","活动名称","生效标识","用户类型","活动类型","金额","货品包名称","套餐分类","最低套餐档次","最高套餐档次","活动地市","活动修改生效时间","活动商城",
	    		"活动有效期","活动内容"};
		List list = searchRepeatActivity(file, fileFileName);
	    DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());
	}
	
	private List searchRepeatActivity(File file,String fileFileName) throws Exception{

		List<Map> activityList = new ArrayList<Map>();
		FileInputStream is = new FileInputStream(file);
		boolean flag;//表示是否有空行

		Workbook wb = null;
		if (!fileFileName.endsWith(".xlsx")){  
			wb = new HSSFWorkbook(is);  
		}else{  
			wb = new XSSFWorkbook(is);  
		}
		//遍历sheet
		for(int k=0;k<1;k++){//活动表因为附加了一张规则说明所以写定遍历的sheet数量

			Sheet sheet = wb.getSheetAt(k);

			//遍历行
			for (int i=1;i<=sheet.getLastRowNum();i++) {
				Map activity = new HashMap();
				Row row = sheet.getRow(i);

				if(row==null){
					//跳过空行
					continue;
				}

				flag=true;
				for (int j=0;j<row.getLastCellNum();j++ ) {
					Cell cell = row.getCell(j);
					if(cell!=null){
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if(!(StringUtils.equals(cell.getStringCellValue().trim(), ""))){
							flag=false;
							break;
						}
					}
				}
				if(flag){
					//跳过空行
					continue;
				}

				//遍历列
				for (int j=0;j<row.getLastCellNum();j++ ) {

					Cell cell = row.getCell(j);
					//					if(cell == null){
					//						continue;
					//					}
					// 读取当前单元格的值
					String title = "";
					if(sheet.getRow(0) != null && sheet.getRow(0).getCell(j) != null){
						title = sheet.getRow(0).getCell(j).getStringCellValue();
					}
					//					cell.setCellType(Cell.CELL_TYPE_STRING);
					//					String cellValue = cell.getStringCellValue();
					if(StringUtils.isNotBlank(title)){
						title = title.trim();
					}
					//					if(StringUtils.isNotBlank(cellValue)){
					//						cellValue = cellValue.trim();
					//					}
					if(title.trim().equals("活动ID")){
						if(cell==null)continue;
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						activity.put("pmt_code", cellValue);
					}else if(title.trim().equals("生效标识")){
						if(cell==null)continue;
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						cellValue = excEnable(cellValue);
						activity.put("enable", cellValue);
					}else if(title.trim().equals("货品包名称")){
						if(cell==null)continue;
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						activity.put("relation_name", cellValue);
					}else if(title.trim().equals("活动地市")){
						if(cell==null)continue;
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						activity.put("region", cellValue);
					}else if(title.trim().equals("活动商城")){
						if(cell==null)continue;
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						activity.put("org_id_str", cellValue);
					}else if(title.trim().equals("活动有效期")){
						if(cell==null)continue;
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						activity.put("available_period", cellValue);
					}
					if(cell!=null){
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						logger.info(title+" - "+cellValue);	
					}
				}
				activityList.add(activity);				
			}
		}
		Set<String> ids = repeatActivityId(activityList);
		List oldActivity = new ArrayList();
		for(String id:ids){
			String sql = SF.goodsSql("ACTIVITY_BY_ID");
			List<Map> activitys = this.daoSupport.queryForList(sql, id);
			for(Map map:activitys){
				map.put("relation_name", relationNameGet((String)map.get("relation_name")));//货品包名称
				map.put("region", RegionName((String)map.get("region")));//活动地市
			}
			oldActivity.addAll(activitys);
		}
		return oldActivity;
	}
	private Set repeatActivityId(List<Map> toImportActivity) throws Exception {
		//找到冲突的活动
		Set<String> p_a_ids = new HashSet<String>();//冲突的活动es_promotion_activity.id
		//Add activity when imported
		
		for(Map newActivity:toImportActivity){
			if("1".equals(newActivity.get("enable"))){
				
				//处理地市
				String region=newActivity.get("region").toString();
				String region_id = RegionId(region);		
				//处理日期
				String available_period=(String) newActivity.get("available_period");
				String start_time = "";
				String end_time = "";
				String[] time = available_period.split("-");
				if(time.length>=2) {
					start_time = time[0] ;
					start_time = DateUtil.convertDateFormat(start_time, "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
					end_time = time[1] ;
					end_time = DateUtil.convertDateFormat(end_time, "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
				}

				String relation_name =(String) newActivity.get("relation_name");
				String relation_id = relationIdGet(relation_name);
				String org_id_str=(String) newActivity.get("org_id_str");
				String act_org_ids = actOrgIds(org_id_str);
				
				String doubleActivitySQLString="select distinct d.id from es_pmt_goods a join es_relation_detail b on a.goods_id = b.object_id and b.relation_id = ? join es_promotion c on a.pmt_id = c.pmt_id join es_promotion_activity d on c.pmta_id = d.id and d.enable = 1 join es_activity_co e on e.activity_id = d.id and e.org_id = ? where a.source_from = '"+ManagerUtils.getSourceFrom()+"' and (d.region = '440000' or instr( d.region,?)>0 ) and ( to_char(d.begin_time,'yyyy-mm-dd') between '{BEGINTIME}' and '{ENDTIME}' or to_char(d.end_time,'yyyy-mm-dd') between '{BEGINTIME}' and '{ENDTIME}' or '{BEGINTIME}' between to_char(d.begin_time,'yyyy-mm-dd') and to_char(d.end_time,'yyyy-mm-dd') or '{ENDTIME}' between to_char(d.begin_time,'yyyy-mm-dd') and to_char(d.end_time,'yyyy-mm-dd') )";
				String doubleActivitySQLString1="select distinct d.id from es_pmt_goods a join es_relation_detail b on a.goods_id = b.object_id and b.relation_id = ? join es_promotion c on a.pmt_id = c.pmt_id join es_promotion_activity d on c.pmta_id = d.id and d.enable = 1 join es_activity_co e on e.activity_id = d.id and e.org_id = ? where a.source_from = '"+ManagerUtils.getSourceFrom()+"' and d.region is not null  and ( to_char(d.begin_time,'yyyy-mm-dd') between '{BEGINTIME}' and '{ENDTIME}' or to_char(d.end_time,'yyyy-mm-dd') between '{BEGINTIME}' and '{ENDTIME}' or '{BEGINTIME}' between to_char(d.begin_time,'yyyy-mm-dd') and to_char(d.end_time,'yyyy-mm-dd') or '{ENDTIME}' between to_char(d.begin_time,'yyyy-mm-dd') and to_char(d.end_time,'yyyy-mm-dd') )";
				doubleActivitySQLString=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString, "{BEGINTIME}", start_time);
				doubleActivitySQLString=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString, "{ENDTIME}", end_time);
				doubleActivitySQLString1=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString1, "{BEGINTIME}", start_time);
				doubleActivitySQLString1=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString1, "{ENDTIME}", end_time);
				com.ztesoft.net.eop.sdk.database.BaseJdbcDaoSupport dao=com.ztesoft.net.framework.context.spring.SpringContextHolder.getBean("baseDaoSupport");
				
				String[] relation_ids=org.apache.commons.lang.StringUtils.split(relation_id, ",");
				String[] orgs=org.apache.commons.lang.StringUtils.split(act_org_ids,",");
				String[] region_ids=org.apache.commons.lang.StringUtils.split(region_id, ",");
				if(StringUtils.equals(region_id, "440000")){//这次是全省活动,之前所有地市活动都冲突
					for(int i=0;i<relation_ids.length;++i)
					{
						for(int j=0;j<orgs.length;++j)
						{
							List<Map> ids = dao.queryForList(doubleActivitySQLString1, relation_ids[i], orgs[j]);
							for(Map map:ids){//找到之前的活动id(es_promotion_activity主键)
								p_a_ids.add((String)map.get("id"));
							}
						}
					}			
				}else {//这次活动是部分地市,相同地市的活动冲突
					for(int i=0;i<relation_ids.length;++i)
					{
						for(int j=0;j<orgs.length;++j)
						{
							for(int k=0;k<region_ids.length;k++){
								List<Map> ids = dao.queryForList(doubleActivitySQLString, relation_ids[i], orgs[j], region_ids[k]);
								for(Map map:ids){//找到之前的活动id(es_promotion_activity主键)
									p_a_ids.add((String)map.get("id"));
								}
							}
						}
					}
				}	
			}
		}
		return p_a_ids;		
	}
	@Override
	public Map importActivity(File file,String fileFileName) throws Exception{
		
		//读取活动信息
		Integer successCount = 0;
		Integer failureCount = 0;
		Integer batch_amount = 0;
		String batch_id = "";
		Map result = new HashMap();
		List<Map> activityList = new ArrayList<Map>();
			if (!StringUtils.isEmpty(fileFileName)) {
				String sql = SF.goodsSql("JUDGE_ACTIVITY_IMPORT_LOGS");
				sql += " and file_name = '" + fileFileName+ "'";
				String exists_flag = this.baseDaoSupport.queryForString(sql);
				if (!"0".equals(exists_flag)) {
					result.put("EXISTS_FILE", "yes");
					return result;
				}
			}
			
			activityList = readExcelInfo(file,fileFileName);
			if(activityList==null || activityList.size()==0){
				result.put("NO_DATA", "yes");
				return result;
			}
			batch_id = this.baseDaoSupport.getSequences("S_ES_ACTIVITY_IMPORT_BATCH","0",6);
			batch_amount = activityList.size();
			try{
			for(Map act:activityList){
				act.put("oper_id", ManagerUtils.getAdminUser().getUserid());
				act.put("batch_id", batch_id);
				act.put("file_name", fileFileName);
				act.put("batch_amount", batch_amount);
				act.put("deal_flag", 0);
				act.put("deal_num", 0);
				act.put("deal_desc", "文件批量导入");
				String log_id = this.baseDaoSupport.getSequences("S_ES_ACTIVITY_IMPORT_LOG", "3", 18);
				act.put("log_id", log_id);
				this.baseDaoSupport.insert("es_activity_import_logs", act);
				successCount++;
			}}catch(Exception e){
				e.printStackTrace();
			}
			failureCount = batch_amount - successCount;	
		result.put("successCount", successCount);
		result.put("failureCount", failureCount);
		result.put("totalCount", batch_amount);
		result.put("batch_id", batch_id);
		return result;
	}
	
	@Override
	public boolean importActivity(){
		try{
			String sql = SF.goodsSql("ACTIVITY_IMPORT_LOGS");
			sql += " and a.source_from='"+ManagerUtils.getSourceFrom()+"' and deal_flag=0 ";
						
			List activityList = this.baseDaoSupport.queryForList(sql, null);
			for(int i=0;i<activityList.size();i++){
				
				Map activityMap = (Map)activityList.get(i);
				String desc ="";
				try{
					desc= this.saveImportActivity(activityMap);
				}catch(Exception e){
					e.printStackTrace();
				}
				if(desc.equals("成功")){
					//成功
					activityMap.put("deal_flag", 1);
					activityMap.put("deal_desc", "导入成功");
				}else{
					//失败
					activityMap.put("deal_flag", 2);
					activityMap.put("deal_desc", desc);
				}
				activityMap.remove("realname");
				updateLogStatus(activityMap);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 修改商品批量导入日志状态
	 */
	public void updateLogStatus(Map log){
		log.put("status_date", DBTUtil.current());
		int deal_num = Integer.valueOf(log.get("deal_num").toString())+1;
		log.put("deal_num", deal_num);
		
		Map whereMap = new HashMap();
		whereMap.put("log_id", log.get("log_id"));
		this.baseDaoSupport.update("es_activity_import_logs", log, whereMap);
	}
	
	@Transactional
	public String saveImportActivity(Map activityMap) throws Exception{
		
		String pmt_code =activityMap.get("pmt_code").toString();
		String id = "";
		if(StringUtils.isNotEmpty(pmt_code)){
			String sqlActivityId="select id from es_promotion_activity where pmt_code =?";
			id =this.baseDaoSupport.queryForString(sqlActivityId,pmt_code);
			if(StringUtils.isEmpty(id)){
				return "失败";//没有指定活动id(pmt_code)的活动,不予修改与新增
			}
		}
		//处理地市
		String region=activityMap.get("region").toString();
		String region_id = RegionId(region);		
		//处理日期
		String available_period=(String) activityMap.get("available_period");
		String start_time = "";
		String end_time = "";
		if(StringUtils.isNotBlank((String) activityMap.get("oper_id"))){
			String[] time = available_period.split("-");
			if(time.length>=2) {
				start_time = time[0] ;
				start_time = DateUtil.convertDateFormat(start_time, "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
				end_time = time[1] ;
				end_time = DateUtil.convertDateFormat(end_time, "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
			}
		}
		String name=activityMap.get("name").toString();
		String gifts_zjzdb=(String) activityMap.get("gifts_zjzdb");
		String gifts_zsyw=(String) activityMap.get("gifts_zsyw");
		String gifts_lp=(String) activityMap.get("gifts_lp");
		String gifts_goods_ids = giftGoodsIds(gifts_zjzdb,gifts_zsyw,gifts_lp);
		String org_id_str=(String) activityMap.get("org_id_str");
		String act_org_ids = actOrgIds(org_id_str);
		String relation_name =(String) activityMap.get("relation_name");
		String relation_id = relationIdGet(relation_name);
		String pmt_goods_ids = pmtGoodsIds(relation_name);
		String no_class = activityMap.get("relief_no_class").toString();
		String relief_no_class = reliefNoClass(no_class);
		int user_type = 1;
		try{			
			user_type = Integer.parseInt(activityMap.get("user_type").toString());
		}catch(Exception e){
			throw new Exception("导入活动:"+name+",新老用户字段有错误！");
		}
		int enable = 0;
		try{			
			enable = Integer.parseInt(activityMap.get("enable").toString());
		}catch(Exception e){
			throw new Exception("导入活动:"+name+",生效标识字段有错误！");
		}

//		if(1==enable){//新导入的活动是生效的,停掉之前冲突的活动
//			Set<String> p_a_ids = new HashSet<String>();//需要停掉的以前的活动es_promotion_activity.id
//			//Add activity when imported
//			String doubleActivitySQLString="select distinct d.id from es_pmt_goods a join es_relation_detail b on a.goods_id = b.object_id and b.relation_id = ? join es_promotion c on a.pmt_id = c.pmt_id and c.pmt_type = '006' join es_promotion_activity d on c.pmta_id = d.id and d.enable = 1 {IFID} join es_activity_co e on e.activity_id = d.id and e.org_id = ? where a.source_from = 'ECS' and (d.region = '440000' or instr( d.region,?)>0 ) and ( to_char(d.begin_time,'yyyy-mm-dd') between '{BEGINTIME}' and '{ENDTIME}' or to_char(d.end_time,'yyyy-mm-dd') between '{BEGINTIME}' and '{ENDTIME}' or '{BEGINTIME}' between to_char(d.begin_time,'yyyy-mm-dd') and to_char(d.end_time,'yyyy-mm-dd') or '{ENDTIME}' between to_char(d.begin_time,'yyyy-mm-dd') and to_char(d.end_time,'yyyy-mm-dd') )";
//			String doubleActivitySQLString1="select distinct d.id from es_pmt_goods a join es_relation_detail b on a.goods_id = b.object_id and b.relation_id = ? join es_promotion c on a.pmt_id = c.pmt_id and c.pmt_type = '006' join es_promotion_activity d on c.pmta_id = d.id and d.enable = 1 {IFID} join es_activity_co e on e.activity_id = d.id and e.org_id = ? where a.source_from = 'ECS' and d.region is not null  and ( to_char(d.begin_time,'yyyy-mm-dd') between '{BEGINTIME}' and '{ENDTIME}' or to_char(d.end_time,'yyyy-mm-dd') between '{BEGINTIME}' and '{ENDTIME}' or '{BEGINTIME}' between to_char(d.begin_time,'yyyy-mm-dd') and to_char(d.end_time,'yyyy-mm-dd') or '{ENDTIME}' between to_char(d.begin_time,'yyyy-mm-dd') and to_char(d.end_time,'yyyy-mm-dd') )";
//			doubleActivitySQLString=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString, "{BEGINTIME}", start_time);
//			doubleActivitySQLString=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString, "{ENDTIME}", end_time);
//			doubleActivitySQLString1=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString1, "{BEGINTIME}", start_time);
//			doubleActivitySQLString1=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString1, "{ENDTIME}", end_time);
//			if(null==id || "".equalsIgnoreCase(id))
//			{
//				doubleActivitySQLString=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString, "{IFID}", "");
//				doubleActivitySQLString1=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString1, "{IFID}", "");
//			}
//			else
//			{
//				doubleActivitySQLString=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString, "{IFID}", " and d.id <> '"+id+"' ");
//				doubleActivitySQLString1=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString1, "{IFID}", " and d.id <> '"+id+"' ");
//			}
//			com.ztesoft.net.eop.sdk.database.BaseJdbcDaoSupport dao=com.ztesoft.net.framework.context.spring.SpringContextHolder.getBean("baseDaoSupport");
//
//			String[] relation_ids=org.apache.commons.lang.StringUtils.split(relation_id, ",");
//			String[] relation_names=org.apache.commons.lang.StringUtils.split(relation_name,"\\|");
//			String[] orgs=org.apache.commons.lang.StringUtils.split(act_org_ids,",");
//			String[] orgNames=org.apache.commons.lang.StringUtils.split(org_id_str, "\\|");
//			String[] region_ids=org.apache.commons.lang.StringUtils.split(region_id, ",");
//			String[] regionNames=org.apache.commons.lang.StringUtils.split(region, "\\|");
//			if(StringUtils.equals(region_id, "440000")){//这次是全省活动,之前所有地市活动都要停
//				for(int i=0;i<relation_ids.length;++i)
//				{
//					for(int j=0;j<orgs.length;++j)
//					{
//						List<Map> ids = dao.queryForList(doubleActivitySQLString1, relation_ids[i], orgs[j]);
//						for(Map map:ids){//找到之前的活动id(es_promotion_activity主键)
//							p_a_ids.add((String)map.get("id"));
//						}
//					}
//				}			
//			}else {//这次活动是部分地市,停掉之前相同地市的活动
//				for(int i=0;i<relation_ids.length;++i)
//				{
//					for(int j=0;j<orgs.length;++j)
//					{
//						for(int k=0;k<region_ids.length;k++){
//							List<Map> ids = dao.queryForList(doubleActivitySQLString, relation_ids[i], orgs[j], region_ids[k]);
//							for(Map map:ids){//找到之前的活动id(es_promotion_activity主键)
//								p_a_ids.add((String)map.get("id"));
//							}
//						}
//					}
//				}
//			}		
//			if(null!=p_a_ids&&p_a_ids.size()>0){//有需要停掉的活动
//				for(String to_stop_id:p_a_ids){
//					PromotionActivity activity = new PromotionActivity();
//					activity.setId(to_stop_id);
//					activity.setEnable(0);//停活动
//					ActivityAttr activityAttr = new ActivityAttr();
//					String sqlGetOrgIds = SF.goodsSql("ORG_ID_BY_ACTIVITY_ID");
//					List<Map> org_ids = dao.queryForList(sqlGetOrgIds, to_stop_id);
//					String toStopOrgs = "";
//					for(Map map:org_ids){
//						toStopOrgs = toStopOrgs + "'" + (String)map.get("org_id") + "',";
//					}
//					if(toStopOrgs.length()>0){//去最后一个逗号
//						toStopOrgs = toStopOrgs.substring(0, toStopOrgs.length()-1);
//					}
//					activityAttr.setAct_org_ids(toStopOrgs);
//					editEnableECS(activity,activityAttr);//停活动
//				}
//			}
//		}
		
		//保存活动
		PromotionActivity activity = new PromotionActivity();
		
		activity.setId(id);
		activity.setEnable(enable);
		activity.setUserid(activityMap.get("oper_id").toString());
		activity.setName(name);
		activity.setBegin_time(start_time);
		activity.setEnd_time(end_time);
		activity.setBrief(activityMap.get("brief").toString());
		activity.setDisabled(0);
		activity.setMax_price(activityMap.get("max_price").toString());
		activity.setMin_price(activityMap.get("min_price").toString());
		activity.setModify_eff_time(activityMap.get("modify_eff_time").toString().split("\\s+")[0]);
		activity.setPackage_class(activityMap.get("package_class").toString().replaceAll("\\|", ","));
		activity.setRegion(region_id);
		activity.setRelation_id(relation_id);
		activity.setRelation_name(org_id_str);
		activity.setRelief_no_class(relief_no_class);
		activity.setPmt_code(pmt_code);
		activity.setSource_from(ManagerUtils.getSourceFrom());
		activity.setStatus_date(DBTUtil.current());
		activity.setUser_type(user_type);
		
		//保存活动规则
		Promotion promotion = new Promotion();
		promotion.setPmt_solution(activityMap.get("pmt_price").toString());
		promotion.setPmta_id(id);
		String pmt_type=getPmtType(activityMap.get("brief").toString());
		promotion.setPmt_type(getPmtType(activityMap.get("pmt_type").toString()));
		promotion.setPmt_time_begin(activity.getBegin_time());
		promotion.setPmt_time_end(activity.getEnd_time());
		promotion.setPromotion_type(getPmtType(activityMap.get("pmt_type").toString()));
		promotion.setPmts_id(Consts.PMT_TYPES_ID_ACTIVITY);
		promotion.setDisabled("true");
		
		
		ActivityAttr activityAttr = new ActivityAttr();
		activityAttr.setAct_org_ids(act_org_ids);
		activityAttr.setGift_goods_ids(gifts_goods_ids);
		activityAttr.setPmt_goods_ids(pmt_goods_ids);
		
		this.saveOrUpdateActECS(activity, activityAttr, promotion);
				
		return "成功";
	}
	
	private String relationNameGet(String relation_id) {
		// TODO 根据货品包ID获取货品包名称
		String sql="select relation_name from es_relation where relation_id=?";
		String relation_names="";
		String[] id=relation_id.split(",");
		for(int i=0;i<id.length;i++){
			String name=this.baseDaoSupport.queryForString(sql,id[i]);
			if(!StringUtils.isEmpty(name))
				relation_names+="|"+name;
		}
		if(!StringUtils.isEmpty(relation_names))
			relation_names=relation_names.substring(1);
		return relation_names;
	}
	
	private String relationIdGet(String relation_name) {
		// TODO 根据货品包名称获取货品包ID
		String sql="select relation_id from es_relation where status_date in( select max(status_date) from es_relation where relation_name=?) and relation_name=?";
		String relation_ids="";
		String[] name=relation_name.split("\\|");
		for(int i=0;i<name.length;i++){
			String id=this.baseDaoSupport.queryForString(sql,name[i],name[i]);
			if(!StringUtils.isEmpty(id))
			relation_ids+=","+id;		
		}
		if(!StringUtils.isEmpty(relation_ids))
		relation_ids=relation_ids.substring(1);
		return relation_ids;
	}

	private String reliefNoClass(String no_class) {
		// 获得靓号减免类型的对应编码
		String reliefNoClass="";
		String[] noClass=no_class.split("\\|");
		for(int i=0;i<noClass.length;i++){
				if(noClass[i].equals("一类")){
					reliefNoClass+=",1";
				}
				else if(noClass[i].equals("二类")){
					reliefNoClass+=",2";
				}
				else if(noClass[i].equals("三类")){
					reliefNoClass+=",3";
				}
				else if(noClass[i].equals("四类")){
					reliefNoClass+=",4";
				}
				else if(noClass[i].equals("五类")){
					reliefNoClass+=",5";
				}
				else if(noClass[i].equals("+1类")){
					reliefNoClass+=",11";
				}
				else if(noClass[i].equals("+2类")){
					reliefNoClass+=",12";
				}
				else if(noClass[i].equals("+3类")){
					reliefNoClass+=",13";
				}
				else if(noClass[i].equals("+4类")){
					reliefNoClass+=",14";
				}
				else if(noClass[i].equals("+5类")){
					reliefNoClass+=",15";
				}
		}
		if(!StringUtils.isEmpty(reliefNoClass))
		reliefNoClass=reliefNoClass.substring(1);
		return reliefNoClass;
	}

//	private String RegionId(String region) {//这个方法应该暂时用不到了
//		// 根据活动地市查询地市ID
//		if(region.equals("全省")){
//			return "440000";
//		}
//		else{
//			String sql="select region_id from es_common_region where region_name=?";
//			return this.baseDaoSupport.queryForString(sql,region);
//		}
//	}

	private String RegionName(String region) {
		// 根据活动地市查询地市ID
		if(region.equals("440000")){
			return "全省";
		}
		else{
			String regionName = region.replace(",", "|");
			String sqlCitys = "select region_name,region_id from es_common_region where parent_region_id = '440000' and source_from = '"+ManagerUtils.getSourceFrom()+"' ";
			List list = this.baseDaoSupport.queryForList(sqlCitys);
			List listCitys = new ArrayList();//所有地市列表
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				regionName = regionName.replace((String)map.get("region_id"), (String)map.get("region_name"));
			}
//			if(regionId.charAt(0)==','){//去头尾','符号，这种情况不考虑
//				regionId = regionId.substring(1, regionId.length());
//			}
//			if(regionId.charAt(regionId.length()-1)==','){
//				regionId = regionId.substring(0, regionId.length()-1);
//			}
			return regionName;
		}
	}
	private String RegionId(String region) {
		// 根据活动地市查询地市ID
		if(region.equals("全省")){
			return "440000";
		}
		else{
			String regionId = region.replace("|", ",");
			String sqlCitys = "select region_name,region_id from es_common_region where parent_region_id = '440000' and source_from = '"+ManagerUtils.getSourceFrom()+"' ";
			List list = this.baseDaoSupport.queryForList(sqlCitys);
			List listCitys = new ArrayList();//所有地市列表
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				regionId = regionId.replace((String)map.get("region_name"), (String)map.get("region_id"));
			}
//			if(regionId.charAt(0)==','){//去头尾','符号，这种情况不考虑
//				regionId = regionId.substring(1, regionId.length());
//			}
//			if(regionId.charAt(regionId.length()-1)==','){
//				regionId = regionId.substring(0, regionId.length()-1);
//			}
			return regionId;
		}
	}

	private String getPmtType(String type) {
		// 根据活动类型返回响应的活动编码
		if(StringUtils.equals(type, "打折")){
			return Consts.PMT_TYPE_DISCOUNT;
		}
		if(StringUtils.equals(type, "直降")){
			return Consts.PMT_TYPE_ZHIJIANG;
		}
		if(StringUtils.equals(type, "预售")){
			return Consts.PMT_TYPE_PRE_SALE;
		}
		if(StringUtils.equals(type, "团购")){
			return Consts.PMT_TYPE_GROUP_BUY;
		}
		if(StringUtils.equals(type, "秒杀")){
			return Consts.PMT_TYPE_LIMIT_BUY;
		}
		if(StringUtils.equals(type, "促销")){
			return Consts.PMT_TYPE_SALES;
		}
		if(StringUtils.equals(type, "溢价")){
			return Consts.PMT_TYPE_OVERPRICE;
		}
		if(StringUtils.equals(type, "常规促销")){
			return Consts.PMT_TYPE_REGULARSALES;
		}
		if(StringUtils.equals(type, "月度主促")){
			return Consts.PMT_TYPE_MONTHSALES;
		}
		if(StringUtils.equals(type, "年度大促")){
			return Consts.PMT_TYPE_YEARSALES;
		}
		return null;
	}

	private String pmtGoodsIds(String relation_name) {
		// 根据货品包得到商品ID
		String pmt_goods_ids="";
		String[] name=relation_name.split("\\|");
		String sql=SF.goodsSql("GOODS_ID_RELATION");
		for(int i=0;i<name.length;i++){
			List<Map> id=this.baseDaoSupport.queryForList(sql,name[i],name[i]);
				for(int j=0;j<id.size();j++){
					if(!StringUtils.isEmpty(id.get(j).get("object_id").toString()))
					pmt_goods_ids+=","+id.get(j).get("object_id").toString();
				}
		}
		if(!StringUtils.isEmpty(pmt_goods_ids))
		pmt_goods_ids=pmt_goods_ids.substring(1);
		return pmt_goods_ids;
	}

	private String actOrgIds(String org_id_str) {
		// 根据商城名字查ID
		String act_org_ids="";
		String[] str=org_id_str.split("\\|");
		String sql=SF.goodsSql("ORG_ID_STR");
		for(int i=0;i<str.length;i++){
			String id=this.baseDaoSupport.queryForString(sql,str[i]);
			if(!StringUtils.isEmpty(id))
			act_org_ids+=","+id;		
		}
		if(!StringUtils.isEmpty(act_org_ids))
		act_org_ids=act_org_ids.substring(1);
		return act_org_ids;
	}

	private String giftGoodsIds(String gifts_zjzdb, String gifts_zsyw,
			String gifts_lp) {
		// 把礼品包SKU进行转换
		String gifts_skus="";
		if(!StringUtils.isEmpty(gifts_zjzdb)){
			gifts_skus+="|"+gifts_zjzdb;
		}
		if(!StringUtils.isEmpty(gifts_zsyw)){
			gifts_skus+="|"+gifts_zsyw;
		}
		if(!StringUtils.isEmpty(gifts_lp)){
			gifts_skus+="|"+gifts_lp;
		}
		String gifts_goods_ids="";
		String[] sku=gifts_skus.split("\\|");
		String sql=SF.goodsSql("GOODS_ID_SKU");
		for(int i=1;i<sku.length;i++){
				String id=this.baseDaoSupport.queryForString(sql,sku[i]);
				if(!StringUtils.isEmpty(id))
				gifts_goods_ids+=","+id;
		}
		if(!StringUtils.isEmpty(gifts_goods_ids))
		gifts_goods_ids=gifts_goods_ids.substring(1);
		return gifts_goods_ids;

	}

	@Override
	public Page listActivityImportLogsECS(String batch_id,String begin_date,String end_date,String deal_flag,int pageNum,int pageSize){
		String sql = SF.goodsSql("ACTIVITY_IMPORT_LOGS");
		List pList = new ArrayList();
		if(StringUtils.isNotEmpty(batch_id)){
			sql += " and a.batch_id=? ";
			pList.add(batch_id);
		}
		if(StringUtils.isNotEmpty(begin_date)){
			begin_date += " 00:00:00";
			sql += " and a.status_date>=to_date(?,'yyyy-mm-dd hh24:mi:ss') ";
			pList.add(begin_date);
		}
		if(StringUtils.isNotEmpty(end_date)){
			end_date += " 23:59:59";
			sql += " and a.status_date<=to_date(?,'yyyy-mm-dd hh24:mi:ss') ";
			pList.add(end_date);
		}
		if(StringUtils.isNotEmpty(deal_flag)){
			sql += " and a.deal_flag=? ";
			pList.add(deal_flag);
		}
		if(ManagerUtils.getAdminUser()!=null){
			sql += " and a.oper_id=? ";
			pList.add(ManagerUtils.getUserId());
		}
		sql += " and a.source_from='"+ManagerUtils.getSourceFrom()+"' ";
		String countSql = "select count(*) amount from("+sql+")";
		sql += " order by a.created_date desc ";
//		Page page = this.baseDaoSupport.qryForPage(sql, pageNum, pageSize, ActivityImportLog.class, pList.toArray());
		Page page = this.baseDaoSupport.queryForCPage(sql, pageNum, pageSize, ActivityImportLog.class, countSql, pList.toArray());
		return page;
	}


	/**
	 * 
	 * 读取excel活动信息
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> readExcelInfo(File file,String fileFileName)  throws Exception{
		
		List<Map> activityList = new ArrayList<Map>();
		FileInputStream is = new FileInputStream(file);
		boolean flag;//表示是否有空行

		Workbook wb = null;
        if (!fileFileName.endsWith(".xlsx")){  
            wb = new HSSFWorkbook(is);  
        }else{  
            wb = new XSSFWorkbook(is);  
        }
        //遍历sheet
		for(int k=0;k<1;k++){//活动表因为附加了一张规则说明所以写定遍历的sheet数量
			
			Sheet sheet = wb.getSheetAt(k);
			
			//遍历行
			for (int i=1;i<=sheet.getLastRowNum();i++) {
				Map activity = new HashMap();
				Row row = sheet.getRow(i);
				
				if(row==null){
					//跳过空行
					continue;
				}
				
				flag=true;
				for (int j=0;j<row.getLastCellNum();j++ ) {
					Cell cell = row.getCell(j);
					if(cell!=null){
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if(!(StringUtils.equals(cell.getStringCellValue().trim(), ""))){
							flag=false;
							break;
						}
					}
				}
				if(flag){
					//跳过空行
					continue;
				}
				
				//遍历列
				for (int j=0;j<row.getLastCellNum();j++ ) {
					
					Cell cell = row.getCell(j);
//					if(cell == null){
//						continue;
//					}
					// 读取当前单元格的值
					String title = "";
					if(sheet.getRow(0) != null && sheet.getRow(0).getCell(j) != null){
						 title = sheet.getRow(0).getCell(j).getStringCellValue();
					}
//					cell.setCellType(Cell.CELL_TYPE_STRING);
//					String cellValue = cell.getStringCellValue();
					if(StringUtils.isNotBlank(title)){
						title = title.trim();
					}
//					if(StringUtils.isNotBlank(cellValue)){
//						cellValue = cellValue.trim();
//					}
					if(title.trim().equals("活动ID")){
						if(cell==null){
							activity.put("pmt_code", "");
							continue;
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(!validateActivityId(cellValue)){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "，没有活动id为:"+cellValue+" 的活动！");
						}
						activity.put("pmt_code", cellValue);
					}else if(title.trim().equals("活动名称")){
						if(cell==null){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(StringUtils.isEmpty(cellValue)) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
//						if(!validateName(cellValue)){
//							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "已停用！");
//						}
						activity.put("name", cellValue);
					}else if(title.trim().equals("生效标识")){
						if(cell==null){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						cellValue = excEnable(cellValue);
						if(StringUtils.isEmpty(cellValue)) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误！");
						}
//						if(!validateName(cellValue)){
//							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "已停用！");
//						}
						activity.put("enable", cellValue);
					}else if(title.trim().equals("用户类型")){
						if(cell==null){
							activity.put("user_type", "1");
							continue;
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						cellValue = excUserType(cellValue);
						if(StringUtils.isEmpty(cellValue)) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误！");
						}
//						if(!validateName(cellValue)){
//							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "已停用！");
//						}
						activity.put("user_type", cellValue);
					}else if(title.trim().equals("活动类型")){
						if(cell==null){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(StringUtils.isEmpty(cellValue)) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						if(!validatePmtType(cellValue)){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误！");
						}
						activity.put("pmt_type", cellValue);
					}else if(title.trim().equals("货品包名称")){
						if(cell==null){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(StringUtils.isEmpty(cellValue)) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						if(!validateRelatinName(cellValue)){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误！");
						}
						activity.put("relation_name", cellValue);
					}else if(title.trim().equals("套餐分类")){
						if(cell==null){
							continue;
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(!StringUtils.isEmpty(cellValue)&&!validatePackageClass(cellValue)){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误！");
						}
						activity.put("package_class", cellValue);
					}else if(title.trim().equals("最低套餐档次")){
						if(cell==null){
							continue;
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						activity.put("min_price", cellValue);
					}else if(title.trim().equals("最高套餐档次")){
						if(cell==null){
							continue;
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						activity.put("max_price", cellValue);
					}else if(title.trim().equals("活动地市")){
						if(cell==null){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(StringUtils.isEmpty(cellValue)) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						if(!validateRegion(cellValue)){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误！");
						}//只要地市格式正确，就允许修改地市
//						if(validateRegionChange(activity.get("name").toString(),cellValue)){
//							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不可修改！");
//						}
						activity.put("region", cellValue);
					}else if(title.trim().equals("活动修改生效时间")){
						if(cell==null){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(StringUtils.isEmpty(cellValue)) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						if(!validateDate(cellValue)){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误！");
						}
						cellValue += " 00:00:00";						
						Date date=DateFormatUtils.parseStrToDate(cellValue,"yyyy/MM/dd HH:mm:ss");
						activity.put("modify_eff_time",date);						
					}else if(title.trim().equals("活动商城")){
						if(cell==null){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(StringUtils.isEmpty(cellValue)) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						if(!validateOrgName(cellValue)){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误！");
						}
						if(validateOrgNameChange(activity.get("pmt_code").toString(),cellValue)){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不可修改！");
						}
						activity.put("org_id_str", cellValue);
					}else if(title.trim().equals("活动条件")){
						if(cell==null){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(StringUtils.isEmpty(cellValue)) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						activity.put("pmt_condition", cellValue);
					}else if(title.trim().equals("金额")){
						if(cell==null){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(StringUtils.isEmpty(cellValue)) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						if(!cellValue.matches("(0.[0-9]{1,2})")&&!cellValue.equals("0")&&!cellValue.matches("^([1-9][0-9]*)+(.[0-9]{1,2})?$")||cellValue.length()>10){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误！");
						}
						activity.put("pmt_price", cellValue);
					}else if(title.trim().equals("活动有效期")){
						if(cell==null){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(StringUtils.isEmpty(cellValue)) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						if(!validatePeriod(cellValue)){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误！");
						}
						activity.put("available_period", cellValue);
					}else if(title.trim().equals("活动内容")){
						if(cell==null){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(StringUtils.isEmpty(cellValue)) {
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "不能为空！");
						}
						activity.put("brief", cellValue);
					}else if(title.trim().equals("直降转兑包")){
						if(cell==null){
							continue;
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(!StringUtils.isEmpty(cellValue)&&!validateSku(cellValue,"10010")){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误！");
						}
						activity.put("gifts_zjzdb", cellValue);						
					}else if(title.trim().equals("赠送业务")){
						if(cell==null){
							continue;
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						Map<String,String> mapTypes = new HashMap<String,String>();
						mapTypes.put("10050", "10050");
						mapTypes.put("10051", "10051");
						mapTypes.put("10009", "10009");
						if(!StringUtils.isEmpty(cellValue) && !validateSkuNew(cellValue, mapTypes)){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误！[" + cellValue + "]");
						}
						activity.put("gifts_zsyw", cellValue);
					}else if(title.trim().equals("礼品")){
						if(cell==null){
							continue;
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(!StringUtils.isEmpty(cellValue)&&!validateSku(cellValue,"10007")){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误！");
						}
						activity.put("gifts_lp", cellValue);
					}else if(title.trim().equals("靓号减免类型")){
						if(cell==null){
							continue;
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						if(!StringUtils.isEmpty(cellValue)&&!validateRelief(cellValue)){
							CommonTools.addFailError("第" + (i+1) + "行的" + title.trim() + "有错误！");
						}
						activity.put("relief_no_class", cellValue);
					}
					if(cell!=null){
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue().trim();
						logger.info(title+" - "+cellValue);	
					}
				}
					if (activity.get("relation_name")!=null) {
						PromotionActivity ac = new PromotionActivity();
						if(activity.get("max_price")!=null){
							ac.setMax_price(activity.get("max_price").toString());
						}
						if(activity.get("min_price")!=null){
							ac.setMin_price(activity.get("min_price").toString());
						}
						if(activity.get("package_class")!=null){
							ac.setPackage_class(activity.get("package_class").toString().replaceAll("\\|", ","));
						}
						ac.setRelation_id(relationIdGet(activity.get("relation_name").toString()));
						List pmtGoodsIdLst = this.pmtGoodsIdFilter(ac);
						String pmt_goods_ids = GoodsManagerUtils.convertStrFromLst(pmtGoodsIdLst, ",");
						if(StringUtils.isEmpty(pmt_goods_ids)){
							CommonTools.addFailError("第" + (i+1) + "行的货品包,套餐分类,套餐档次关联不出商品！");
						}
					}
					
					if(activity.get("pmt_type")!=null&&activity.get("pmt_type").toString().equals("直降")&&(activity.get("gifts_zjzdb")==null||activity.get("gifts_zjzdb").toString().equals(""))){
							CommonTools.addFailError("第" + (i+1) + "行的直降活动转兑包不能为空！");
					}
					
					if(activity.get("available_period")!=null){
						String ap=activity.get("available_period").toString();
//						String et=ap.split("-")[1];
//						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd");
//						Date dateEt = sdf.parse(et);
//						CommonTools.addFailError("第" + (i+1) + "行的活动有效期有错误！");
						String bt=ap.split("-")[0].replace("/", "").replace(" ", "").replace(":", "");
						String et=ap.split("-")[1].replace("/", "").replace(" ", "").replace(":", "");
						long bt1=Long.parseLong(bt);
						long et1=Long.parseLong(et);
						if(bt1>et1){
							CommonTools.addFailError("第" + (i+1) + "行的活动有效期有错误！");
						}
					}
					if(activity.get("name")==null){
						CommonTools.addFailError("活动信息有误，请检查模版！");
					}
					activityList.add(activity);				
			}
		}
		
		return activityList;
	}
	
	private String excEnable(String cellValue){
		if("生效".equals(cellValue))return "1";
		if("失效".equals(cellValue))return "0";
		return "";
	}

	private String excUserType(String cellValue){
		if(StringUtils.isEmpty(cellValue))return "1";
		if("新用户".equals(cellValue))return "2";
		if("老用户".equals(cellValue))return "3";
		return "";
	}
	
	private boolean validateOrgNameChange(String pmt_code, String cellValue) {
		// TODO 校驗是否修改商城
		String sql="select org_name from es_goods_org where org_code in( select org_id from es_activity_co where activity_id=(select id from es_promotion_activity where pmt_code=? and enable='1'))";
		List list=this.baseDaoSupport.queryForList(sql,pmt_code);
		List lista=new ArrayList<String>();
		for (int i=0;i<list.size();i++){
			Map m=(Map)list.get(i);
			lista.add(m.get("org_name").toString());
		}
		List listb=Arrays.asList(cellValue.split("\\|"));
		if(listb.containsAll(lista)){
			return false;
		}
		return true;
	}

	private boolean validateRegionChange(String name, String cellValue) {//这个方法暂时用不上
		// TODO 校驗是否修改活動地市
		if(StringUtils.equals(cellValue, "全省")){//这次导入全省，则修改地市
			return false;
		}
		String sql="select region from es_promotion_activity where name=? and enable='1'";
		String region=this.baseDaoSupport.queryForString(sql,name);
		List listOld = Arrays.asList(region.split(","));
		List listNew = Arrays.asList(cellValue.split("\\|"));
		if(listNew.containsAll(listOld)){//新导入的地市包含所有之前地市，则修改地市
			return false;
		}
		return true;
	}

	private boolean validateName(String cellValue) {
		// TODO 校驗活動是否還可用
		String sql="select enable from es_promotion_activity where name=?";
		int enable=this.baseDaoSupport.queryForInt(sql, cellValue);
		if(enable==1){
			return true;
		}
		return false;
	}

	private boolean validateRelatinName(String cellValue) {
		// TODO 检验货品包名称有效性
		String[] rel=cellValue.split("\\|");
		String sql="select count(*) from es_relation where relation_name=?";
		for(int i=0;i<rel.length;i++){
			int count=this.baseDaoSupport.queryForInt(sql,rel[i]);
			if(count==0)
			return false;
		}
		return true;
	}

	private boolean validateRelief(String cellValue) {
		// TODO 校验靓号
		String[] rel=cellValue.split("\\|");
		for(int i=0;i<rel.length;i++){
			if(!rel[i].equals("一类")&&!rel[i].equals("二类")&&!rel[i].equals("三类")&&!rel[i].equals("四类")
					&&!rel[i].equals("五类")&&!rel[i].equals("+1类")&&!rel[i].equals("+2类")
					&&!rel[i].equals("+3类")&&!rel[i].equals("+4类")&&!rel[i].equals("+5类")){
				return false;
			}
		}				
		return true;
	}

	private boolean validateSku(String cellValue,String code) {
		// TODO 校验赠品SKU
		String[] sku=cellValue.split("\\|");
		String sql="select count(*) from es_goods where sku=?";
		for(int i=0;i<sku.length;i++){
			int count=this.baseDaoSupport.queryForInt(sql,sku[i]);
			if(count==0)
			return false;
			else if(count>0){
				String sqlt="select distinct(type_id) from es_goods where sku=? and type='product'";
				String type_id=this.baseDaoSupport.queryForString(sqlt, sku[i]);
				if(!StringUtils.equals(type_id, code)){
					return false;
				}
			}
		}
		return true;
	}

	private boolean validatePeriod(String cellValue) {
		// TODO 检验有效日期区间		
		String[] period=cellValue.split("-");
		if(period.length==2&&validateDateToSec(period[0])&&validateDateToSec(period[1])){
			return true;
		}
		return false;
	}

	private boolean validateOrgName(String cellValue) {
		// TODO 检验商城有效性
		String[] str=cellValue.split("\\|");
		String sql="select count(*) from es_goods_org where org_name=?";
		for(int i=0;i<str.length;i++){
			int count=this.baseDaoSupport.queryForInt(sql,str[i]);
			if(count==0)
			return false;
		}
		return true;
	}

	private boolean validateRegion(String cellValue) {
//		String sqlGuangdongProvince = "select region_name from es_common_region where region_id='440000' and source_from = 'ECS' ";
//		String guangdongProvince = this.baseDaoSupport.queryForString(sqlGuangdongProvince);
		if(StringUtils.equals(cellValue, "全省")){//导入全省活动
			return true;
		}
		String sqlCitys = "select region_name from es_common_region where parent_region_id = '440000' and source_from = '"+ManagerUtils.getSourceFrom()+"' ";
		List list = this.baseDaoSupport.queryForList(sqlCitys);
		List listCitysImp = Arrays.asList(cellValue.split("\\|"));//导入活动的地市
		List listCitys = new ArrayList();//所有地市列表
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			listCitys.add(map.get("region_name"));
		}
		if(listCitys.containsAll(listCitysImp)){//导入活动的地市 在 所有地市列表 当中
			return true;
		}
		return false;
		// TODO 检验地市有效性 东莞,广州,深圳,佛山,茂名,肇庆,汕头,阳江,云浮,韶关,清远,河源,江门,潮州,湛江,揭阳,惠州,珠海,中山,梅州,汕尾
//		if(cellValue.equals("全省")||cellValue.equals("东莞")||cellValue.equals("广州")||cellValue.equals("深圳")
//				||cellValue.equals("佛山")||cellValue.equals("茂名")||cellValue.equals("肇庆")||cellValue.equals("汕头")
//				||cellValue.equals("阳江")||cellValue.equals("云浮")||cellValue.equals("韶关")||cellValue.equals("清远")
//				||cellValue.equals("河源")||cellValue.equals("江门")||cellValue.equals("潮州")||cellValue.equals("湛江")
//				||cellValue.equals("揭阳")||cellValue.equals("惠州")||cellValue.equals("珠海")||cellValue.equals("中山")
//				||cellValue.equals("梅州")||cellValue.equals("汕尾")){
//			return true;
//		}
//		return false;
	}

	private boolean validateDateToSec(String cellValue) {
		// TODO 检验日期有效性,精确到秒
//		String re="[0-9]{4}\\/[0-9]{2}\\/[0-9]{2}";	
		String re="^[1-9][0-9]{3}\\/(0[1-9]|1[0-2])\\/(0[1-9]|[1-2][0-9]|30|31) ([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
		if(cellValue.matches(re)){
			return true;
		}
		return false;
	}
	
	private boolean validateDate(String cellValue) {
		// TODO 检验日期有效性
//		String re="[0-9]{4}\\/[0-9]{2}\\/[0-9]{2}";	
		String re="^[1-9][0-9]{3}\\/(0[1-9]|1[0-2])\\/(0[1-9]|[1-2][0-9]|30|31)$";
		if(cellValue.matches(re)){
			return true;
		}
		return false;
	}

	private boolean validateActivityId(String cellValue) {
		//  检验活动id
		if(StringUtils.isEmpty(cellValue)){
		return true;
		}
		String sql = "select count(1) from es_promotion_activity where pmt_code=?";
		int count = this.daoSupport.queryForInt(sql, cellValue);
		if(count<1){
			return false;
		}
		return true;
	}
	
	private boolean validatePackageClass(String cellValue) {
		//  检验套餐分类
		if(cellValue.equals("3GA")||cellValue.equals("3GB")||cellValue.equals("3GC")||cellValue.equals("3GA|3GB")||
		   cellValue.equals("3GA|3GC")||cellValue.equals("3GB|3GC")||cellValue.equals("3GA|3GB|3GC")){
		return true;
		}
		return false;
	}

	private boolean validatePmtType(String cellValue) {
		// 检验活动类型
		if(cellValue.equals("打折")||cellValue.equals("积分翻倍")||cellValue.equals("满就送")||cellValue.equals("满就减")||
		   cellValue.equals("免运费")||cellValue.equals("直降")||cellValue.equals("预售")||cellValue.equals("团购")||
		   cellValue.equals("秒杀")||cellValue.equals("促销")||cellValue.equals("溢价")||cellValue.equals("常规促销")||
		   cellValue.equals("月度主促")||cellValue.equals("年度大促"))
		return true;
		else
		return false;
	}

	/**
	 * 根据活动信息，查询商品列表
	 * @param activity
	 * @return
	 */
	public List<Goods> getGoodsList(Map activity) {
		
		String  activity_type = (String)activity.get("activity_type");  //excel的活动类别-对应合约计划（货品）
		String  pro_type = (String)activity.get("pro_type");  //excel的产品类型-商品大类（商品）
		String  fee_type = (String)activity.get("fee_type");  //excel的资费类别-对应套餐分类（货品）
		String  terminal_source = (String)activity.get("terminal_source");
		String  goods_name = (String)activity.get("goods_name");
		String  terminal_branch = (String)activity.get("terminal_branch");
		String  terminal_model = (String)activity.get("terminal_model");
		String  price = (String)activity.get("price");
		String  stype_id = (String)activity.get("stype_id");  //excel适用套餐-对应套餐档次（货品）
		
		List sqlParams = new ArrayList(); 
		String sql = " select a.* from es_goods a, es_product b "
				+ " where a.goods_id = b.goods_id "
				+ " and a.source_from = b.source_from "
				+ " and a.type = 'goods' "
				+ " and a.source_from = ?";
		sqlParams.add(ManagerUtils.getSourceFrom());
		
		if ("合约机".equals(pro_type)) {
			
			sql += " and a.type_id = ?";
			sqlParams.add(Consts.GOODS_TYPE_CONTRACT_MACHINE);  //合约机商品
			
			//合约机包括终端合约套餐3个货品
			if(StringUtils.isNotBlank(terminal_model) && StringUtils.isNotBlank(activity_type)
					&& StringUtils.isNotBlank(fee_type)) {
				
				sql += " and exists (select 1 from es_goods_rel b where b.a_goods_id = a.goods_id "
				     + " and b.product_id in (select b.product_id from es_goods a, es_product b "
					 + " where a.goods_id = b.goods_id "
					 + " and a.source_from = b.source_from "
					 + " and a.source_from = ?"
				     + " and a.type_id= ? "
				     + " and a.model_code = ? ";
				
				sqlParams.add(ManagerUtils.getSourceFrom());
				sqlParams.add(Consts.GOODS_TYPE_TERMINAL);  //终端货品
				sqlParams.add(terminal_model);
				
				if ("社会机".equals(terminal_source)) {
				    sql += " and a.cat_id = ? ";
				    sqlParams.add(Consts.GOODS_CAT_SOCIAL_MACHINE);
				    
				} else if ("定制机".equals(terminal_source)) {
					sql += " and a.cat_id = ? ";
					sqlParams.add(Consts.GOODS_CAT_CUSTOM_MACHINE);
				}
				sql +=	" )) ";
				
				sql += " and exists (select 1 from es_goods_rel b "
				     + " where b.a_goods_id = a.goods_id "
				     + " and b.product_id in (select b.product_id from es_goods a, es_product b "
				     + " where a.goods_id = b.goods_id "
				     + " and a.source_from = b.source_from "
				     + " and a.source_from = ?"
				     + " and a.type_id = ?";
				sqlParams.add(ManagerUtils.getSourceFrom());
				sqlParams.add(Consts.GOODS_TYPE_CONTRACT);  //合约计划货品
				
				if ("购机送费".equals(activity_type)) {
					
				    sql += " and a.cat_id = ?";
				    sqlParams.add(Consts.GOODS_CAT_MACHINE_GIVE_FEE);
				    
				} else if ("存费送机".equals(activity_type)) {
					
				    sql += " and a.cat_id = ?";
				    sqlParams.add(Consts.GOODS_CAT_FEE_GIVE_MACHINE);
				}
				sql +=	" )) ";
						
				sql += " and exists (select 1 from es_goods_rel b "
				     + " where b.a_goods_id = a.goods_id "
				     + " and b.product_id in (select b.product_id from es_goods a, es_product b "
				     + " where a.goods_id = b.goods_id"
				     + " and a.source_from = b.source_from "
				     + " and a.source_from = ?"
				     + " and a.type_id = ? ";
				sqlParams.add(ManagerUtils.getSourceFrom());
				sqlParams.add(Consts.GOODS_TYPE_OFFER);  //包含的套餐
				
				if ("3G后付费".equals(fee_type)) {
					
				  sql += " and a.cat_id in("+Consts.GOODS_CAT_3G_AFTER_FEE+")";
				  
				} else if ("3G预付费".equals(fee_type)) {
					
				    sql += " and a.cat_id = ?";
				    sqlParams.add(Consts.GOODS_CAT_3G_PRE_FEE);
				    
				} else if ("4G后付费".equals(fee_type)) {
					
				    sql += " and a.cat_id = ? ";
				    sqlParams.add(Consts.GOODS_CAT_4G_AFTER_FEE);
				    
				} else if ("2G/3G融合".equals(fee_type)) {
					
				    sql += " and a.cat_id = ?";
				    sqlParams.add(Consts.GOODS_CAT_2G_3G_FUSE_);
				}
				
				if (StringUtils.isNotBlank(stype_id)){
				    sql += " and to_number(a.stype_id) >= ? ";
					sqlParams.add(stype_id);  //套餐（货品）档次
				}
				
				sql +=	" )) ";
				
			}
			
		} else if ("号卡".equals(pro_type)) {
			
			sql += " and a.type_id = ?";  
			sqlParams.add(Consts.GOODS_TYPE_NUM_CARD);  //号卡商品
			
			//号卡包括合约和套餐
			if( StringUtils.isNotBlank(activity_type)
					&& StringUtils.isNotBlank(fee_type)) {

				sql += " and exists (select 1 from es_goods_rel b "
					 + " where b.a_goods_id = a.goods_id "
					 + " and b.product_id in (select b.product_id from es_goods a, es_product b "
					 + " where a.goods_id = b.goods_id "
					 + " and a.source_from = b.source_from"
					 + " and a.source_from = ?"
					 + " and a.type_id = ?";
				sqlParams.add(ManagerUtils.getSourceFrom());
				sqlParams.add(Consts.GOODS_TYPE_CONTRACT);  //合约计划货品
				
				if ("存费送费".equals(activity_type)) {
				    sql += " and a.cat_id = ? ";
				    sqlParams.add(Consts.GOODS_CAT_FEE_GIVE_FEE);
				}
				sql += ")) ";
						
				sql += " and exists (select 1 from es_goods_rel b "
				     + " where b.a_goods_id = a.goods_id "
				     + " and product_id in (select b.product_id from es_goods a, es_product b "
				     + " where a.goods_id = b.goods_id "
				     + " and a.source_from = b.source_from"
					 + " and a.source_from = ? "
				     + " and a.type_id = ?";
				sqlParams.add(ManagerUtils.getSourceFrom());
				sqlParams.add(Consts.GOODS_TYPE_OFFER);  //套餐
				
				if ("3G后付费".equals(fee_type)) {
					
					sql += " and a.cat_id in("+Consts.GOODS_CAT_3G_AFTER_FEE+")";
					
				} else if ("3G预付费".equals(fee_type)) {
					
					sql += " and a.cat_id = ?";
					sqlParams.add(Consts.GOODS_CAT_3G_PRE_FEE);
					
				} else if ("4G后付费".equals(fee_type)) {
					
				    sql += " and a.cat_id = ?";
				    sqlParams.add(Consts.GOODS_CAT_4G_AFTER_FEE);
				    
				} else if("2G/3G融合".equals(fee_type)) {
					
				    sql += " and a.cat_id = ? ";
				    sqlParams.add(Consts.GOODS_CAT_2G_3G_FUSE_);
				}
				
				if (StringUtils.isNotBlank(stype_id)) {
					
			        sql += " and to_number(a.stype_id) >= ? ";
				    sqlParams.add(stype_id);  //套餐档次
				}
						
				sql +=	" )) ";
			}
				
		} else {
			
			if ("裸机".equals(pro_type)) {
				
				sql += " and a.type_id = ?";  
				sqlParams.add(Consts.GOODS_TYPE_PHONE);  //裸机商品
				
			} else if ("上网卡".equals(pro_type)) {
				
				sql += " and a.type_id = ?";  
				sqlParams.add(Consts.GOODS_TYPE_WIFI_CARD);  //上网卡商品
				
			} else if ("配件".equals(pro_type)) {
				
				sql += " and a.type_id = ?";  
				sqlParams.add(Consts.GOODS_TYPE_ADJUNCT);  //配件商品
			}
			
			sql += " and a.name = ? ";
			sqlParams.add(goods_name);
			
		}
		
		List<Goods> list = new ArrayList<Goods>();
		if (StringUtils.isNotBlank(sql)) {
			list = this.baseDaoSupport.queryForList(sql, Goods.class, sqlParams.toArray(new String[]{}));
		}
		
		return list;
	}
	
	@Override
	
	public String batchActivityPublish(PromotionActivity activity,ActivityAttr activityAttr,Promotion promotion){
		String name =  activity.getName();
		String pmt_type = promotion.getPmt_type();
		String pmt_goods_ids = activityAttr.getPmt_goods_ids();
		String begin_time = activity.getBegin_time();
		String end_time = activity.getEnd_time();
	    int batchNum = activity.getRank();
		String act_org_ids = activityAttr.getAct_org_ids();
		String sql = SF.goodsSql("PROMOTION_ACTIVITY_BATCH_PUBLISH");
		List sqlParams = new ArrayList();
		batchNum = batchNum>500?500:batchNum;//批量发布上限500
        //活动名称
		if(!StringUtil.isEmpty(name)){
			sql += " and a.name like ?";
			sqlParams.add("%"+name+"%");
		}
		
		//活动类型
		if(!StringUtil.isEmpty(pmt_type)){
			sql += " and b.pmt_type=?";
			sqlParams.add(pmt_type);
		}
		
		//活动状态为有效的才发布
		sql += " and a.enable=1";
		
		//适用商品
		if(!StringUtil.isEmpty(pmt_goods_ids)){
			sql += " and exists (select 1 from es_pmt_goods r where r.pmt_id=b.pmt_id and r.goods_id=?)";
			sqlParams.add(pmt_goods_ids);
		}
		   
		//商城
		if(!StringUtil.isEmpty(act_org_ids)){
			sql += " and exists (select 1 from es_activity_co r where r.activity_id=a.id and r.org_id=?)";
			sqlParams.add(act_org_ids);
		}
		
		if(!StringUtil.isEmpty(begin_time)){
			sql += " AND a.begin_time >= "+ DBTUtil.to_date(begin_time, 1)+"";
		}
		if(!StringUtil.isEmpty(end_time)){
			sql += " AND a.end_time <= "+DBTUtil.to_date(end_time, 1)+"";
		}
		sql += " order by a.begin_time desc";
		Page page = this.baseDaoSupport.queryForPage(sql, 1, batchNum,true,sqlParams.toArray(new String[]{}));
		String activityCoSql = SF.goodsSql("PROMOTION_ACTIVITY_GET_CO_ECS");
		
		List list = page.getResult();
		int len = list.size();
		for(int i=0;i<list.size();i++){
			Map result = (Map)list.get(i);
			String id = (String)result.get("id");
			List activityCoList = this.baseDaoSupport.queryForList(activityCoSql,id);
			String org_ids = "";
			String org_id = null;
			if(activityCoList.size()>0){
				for(int k=0;k<activityCoList.size();k++){
					Map co = (Map) activityCoList.get(k);
					org_id = (String) co.get("org_id");
					//总商,淘宝,深圳商城,广州微店不需要同步活动,直接改状态---zengxianlian
					if("10003".equals(org_id)||"10001".equals(org_id)||"10013".equals(org_id)||"10056".equals(org_id)){
						editStatusECS(id,Consts.PUBLISH_STATE_1+"",org_id);
					}else{
						if(k>0&&!"".equals(org_ids)){
							org_ids += ",";
						}
						org_ids += org_id;
					}
				}
			}
			if(!"".equals(org_ids)){
				//写同步消息队列es_co_queue
				this.writeQueue(id, org_ids,Consts.ACTION_CODE_A);
				//修改活动发布状态为发布中
				String[] oIds = org_ids.split(",");
				for(String oId : oIds){
					editStatusECS(id,Consts.PUBLISH_STATE_2+"",oId);
				}
			}else{
				len--;
			}
			
		}
		String message = "成功发布了"+len+"条活动！";
		return message;
	}
	

/**
	 * 根据赠品名称查询赠品id
	 * @param gift_offers
	 * @param i_channel_offers
	 * @return 多个赠品（商品）ID用逗号隔开
	 */
	public Map getGiftByName(String gift_offers,String i_channel_offers){
		String goods_ids = "";
		String goods_names = "";
		int k=0; 
		String giftName = gift_offers;
		if(StringUtils.isNotBlank(giftName) ){
			giftName += ","+i_channel_offers;
		}else{
			giftName = i_channel_offers;
		}
		
		String[] giftArray = giftName.split(",");
		String sql = "select * from es_goods where name=? and type='"+Consts.ECS_QUERY_TYPE_PRODUCT+"'";
		for(int i=0;i<giftArray.length;i++){
			String name = giftArray[i];
			if(StringUtils.isNotBlank(name)){
			    	
				List list = this.baseDaoSupport.queryForList(sql, name);
				if(list.size()>0){
					Map map = (Map)list.get(0);
					String goods_id = (String)map.get("goods_id");
					String goods_name = (String)map.get("name");
					if(k++>0){
						goods_ids += ",";
						goods_names += ",";
					}
					goods_ids += goods_id;
					goods_names += goods_name;
				}
			}
		}
		Map result = new HashMap();
		result.put("goods_ids", goods_ids);
		result.put("goods_names", goods_names);
		return result;
	}
	
	@Override
	public Page listECS(PromotionActivity activity,ActivityAttr activityAttr,Promotion promotion,int pageNo, int pageSize) {
		AdminUser au = ManagerUtils.getAdminUser();
		String userid = au.getUserid();
		String name =  activity.getName();
		String pmt_type = promotion.getPmt_type();
		int enable = activity.getEnable();
		String publish_status = activityAttr.getPublish_status();
		String pmt_goods_ids = activityAttr.getPmt_goods_ids();
		String act_org_ids = activityAttr.getAct_org_ids();
		String begin_time = activity.getBegin_time();
		String end_time = activity.getEnd_time();
		
		//货品包
		if (StringUtil.isEmpty(activity.getRelation_name())) {
			activity.setRelation_id("");
		}
		String relation_id = activity.getRelation_id();
		
		String sql = SF.goodsSql("PROMOTION_ACTIVITY_GET_ECS");
		
        List sqlParams = new ArrayList();
        //活动名称
		if(!StringUtil.isEmpty(name)){
			sql += " and a.name like ?";
			sqlParams.add("%"+name+"%");
		}
		
		//活动类型
		if(!StringUtil.isEmpty(pmt_type)){
			sql += " and b.pmt_type=?";
			sqlParams.add(pmt_type);
		}
		
		//活动状态
		if(enable != -1){
			sql += " and a.enable=?";
			sqlParams.add(""+enable);
		}
		
		//发布状态
		if(StringUtils.isNotBlank(publish_status)){
			if(Consts.PUBLISH_STATE_0.toString().equals(publish_status)){
				sql += " and (not exists (select 1 from es_activity_co r where r.activity_id=a.id) or " +
					   " exists (select 1 from es_activity_co r where r.status='"+Consts.PUBLISH_STATE_0+"' and r.activity_id=a.id))";
			}else if(Consts.PUBLISH_STATE_2.toString().equals(publish_status)){
				sql += " and exists (select 1 from es_activity_co r where r.status='"+Consts.PUBLISH_STATE_2+"' and r.activity_id=a.id)";
			}else if(Consts.PUBLISH_STATE_3.toString().equals(publish_status)){
				sql += " and exists (select 1 from es_activity_co r where r.status='"+Consts.PUBLISH_STATE_3+"' and r.activity_id=a.id)";
			}else if(Consts.PUBLISH_STATE_1.toString().equals(publish_status)){
				sql += " and exists (select 1 from es_activity_co r where r.status='"+Consts.PUBLISH_STATE_1+"' and r.activity_id=a.id)";
			}
		}
        //适用商品
		if(!StringUtil.isEmpty(relation_id)){
			sql += " and exists (select 1 from es_pmt_goods r, es_relation_detail dt "
					+ " where r.pmt_id = b.pmt_id "
					+ " and r.goods_id = dt.object_id"
					+ " and dt.relation_id in(" + Const.getInWhereCond(relation_id) + "))";
		}
		   
		//商城
		if(!StringUtil.isEmpty(act_org_ids)){
			sql += " and exists (select 1 from es_activity_co r where r.activity_id=a.id and r.org_id=?)";
			sqlParams.add(act_org_ids);
		}
		
		if(!StringUtil.isEmpty(begin_time)){
//			sql += " AND a.begin_time >= "+ DBTUtil.to_date(begin_time, 1)+"";
			begin_time += " 00:00:00";
			sql += " and a.begin_time>=to_date(?,'yyyy-mm-dd hh24:mi:ss') ";
			sqlParams.add(begin_time);
		}
		if(!StringUtil.isEmpty(end_time)){
//			sql += " AND a.end_time <= "+DBTUtil.to_date(end_time, 1)+"";
			end_time += " 23:59:59";
			sql += " and a.end_time<=to_date(?,'yyyy-mm-dd hh24:mi:ss') ";
			sqlParams.add(end_time);
		}
		sql += " order by a.status_date desc";
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize,true,sqlParams.toArray(new String[]{}));
		
		String pmtGoodsSql = SF.goodsSql("PROMOTION_ACTIVITY_GET_GOODS_ECS");
		String activityCoSql = SF.goodsSql("PROMOTION_ACTIVITY_GET_CO_ECS");
		
		List list = page.getResult();
		for(int i=0;i<list.size();i++){
			Map result = (Map)list.get(i);
			String id = (String)result.get("id");
			String pmt_id = (String)result.get("pmt_id");
			List pmtGoodsList = this.baseDaoSupport.queryForList(pmtGoodsSql,pmt_id);
			List activityCoList = this.baseDaoSupport.queryForList(activityCoSql,id);
			String goods_names = "";
			String org_names = "",org_ids = "";
			String pub_status = "";
			String pub_status_name = "";
			for(int j=0;j<pmtGoodsList.size();j++){
				Map pmtGoods = (Map) pmtGoodsList.get(j);
				if(j>0){
					goods_names += ",";
				}
				goods_names += pmtGoods.get("name");
			}
			if(activityCoList.size()>0){
				Integer oldStatus = 0;
				boolean flag = true;
				for(int k=0;k<activityCoList.size();k++){
					Map co = (Map) activityCoList.get(k);
					if(k>0){
						Integer status = (Integer)co.get("status");
						if(status!=oldStatus){
							flag = false;
							oldStatus = status;
						}
					}
					if(k==0){
						Integer status = (Integer)co.get("status");
						oldStatus = status;
						if(Consts.PUBLISH_STATE_0.equals(status)){
							pub_status_name = "未发布";
							pub_status = Consts.PUBLISH_STATE_0.toString();
							
						}else if(Consts.PUBLISH_STATE_1.equals(status)){
							pub_status_name = "已发布";
							pub_status = Consts.PUBLISH_STATE_1.toString();
							
						}else if(Consts.PUBLISH_STATE_2.equals(status)){
							pub_status_name = "发布中";
							pub_status = Consts.PUBLISH_STATE_2.toString();
							
						}else if(Consts.PUBLISH_STATE_3.equals(status)){
							pub_status_name = "发布失败";
							pub_status = Consts.PUBLISH_STATE_3.toString();
						}
					}
					if(Consts.PUBLISH_STATE_1.equals(oldStatus)){
						org_names += co.get("org_name")+"√,";
					}else if(Consts.PUBLISH_STATE_3.equals(oldStatus)){
						org_names += co.get("org_name")+"×,";
					}else{
						org_names += co.get("org_name")+",";
					}
					org_ids += co.get("org_id")+",";
//					Map co = (Map) activityCoList.get(k);
//					if(k>0){
//						org_names += ",";
//						org_ids += ",";
//					}
//					org_names += co.get("org_name");
//					org_ids += co.get("org_id");
//					if(k==0){
//						Integer status = (Integer)co.get("status");
//						if(Consts.PUBLISH_STATE_0.equals(status)){
//							pub_status_name = "未发布";
//							pub_status = Consts.PUBLISH_STATE_0.toString();
//							
//						}else if(Consts.PUBLISH_STATE_1.equals(status)){
//							pub_status_name = "已发布";
//							pub_status = Consts.PUBLISH_STATE_1.toString();
//							
//						}else if(Consts.PUBLISH_STATE_2.equals(status)){
//							pub_status_name = "发布中";
//							pub_status = Consts.PUBLISH_STATE_2.toString();
//							
//						}else if(Consts.PUBLISH_STATE_3.equals(status)){
//							//pub_status_name = "发布可修改";
//							pub_status_name = "发布失败";
//							pub_status = Consts.PUBLISH_STATE_3.toString();
//						}
//					}
				}
				org_ids = org_ids.substring(0, org_ids.length()-1);
				org_names = org_names.substring(0, org_names.length()-1);
				if(flag){
					org_names = org_names.replace("√", "").replace("×", "");
				}else{
					pub_status_name = "部分成功";
				}
			}else{
				pub_status_name = "未发布";
				pub_status = Consts.PUBLISH_STATE_0.toString();
			}
            String goods_names_view = goods_names;
			if(StringUtils.isNotBlank(goods_names) && goods_names.length()>=15){
				goods_names_view = goods_names.substring(0, 15) + "...";
			}
            String org_names_view = org_names;
			if(StringUtils.isNotBlank(org_names) && org_names.length()>=15){
				org_names_view = org_names.substring(0, 15) + "...";
			}
			result.put("goods_names", goods_names);
			result.put("org_names", org_names);
			result.put("goods_names_view", goods_names_view);
			result.put("org_names_view", org_names_view);
			result.put("pub_status", pub_status);
			result.put("pub_status_name", pub_status_name);
			result.put("org_ids", org_ids);
		}
		
		return page;
	}
	
	@Override
	public Page listActivityModifyLogsECS(PromotionActivity activity,ActivityAttr activityAttr,Promotion promotion,int pageNo, int pageSize){
		AdminUser au = ManagerUtils.getAdminUser();
		String userid = au.getUserid();
		String name =  activity == null ? null : activity.getName();
		String pmt_type = promotion == null ? null : promotion.getPmt_type();
		String publish_status = activityAttr == null ? null : activityAttr.getPublish_status();
		String pmt_goods_ids = activityAttr == null ? null : activityAttr.getPmt_goods_ids();
		String act_org_ids = activityAttr == null ? null : activityAttr.getAct_org_ids();
		String begin_time = activity == null ? null : activity.getBegin_time();
		String end_time = activity == null ? null : activity.getEnd_time();
		
		String sql = SF.goodsSql("PROMOTION_ACTIVITY_LOG_GET_ECS");
		
        List sqlParams = new ArrayList();
        //活动名称
		if(!StringUtil.isEmpty(name)){
			sql += " and a.name like ?";
			sqlParams.add("%"+name+"%");
		}
		
		//活动类型
		if(!StringUtil.isEmpty(pmt_type)){
			sql += " and b.pmt_type=?";
			sqlParams.add(pmt_type);
		}

        //适用商品
		if(!StringUtil.isEmpty(pmt_goods_ids)){
			sql += " and exists (select 1 from es_pmt_goods r where r.pmt_id=b.pmt_id and r.goods_id=?)";
			sqlParams.add(pmt_goods_ids);
		}
		   
		//商城
		if(!StringUtil.isEmpty(act_org_ids)){
			sql += " and exists (select 1 from es_activity_co r where r.activity_id=a.id and r.org_id=?)";
			sqlParams.add(act_org_ids);
		}
		
		if(!StringUtil.isEmpty(begin_time)){
			sql += " AND a.begin_time >= "+ DBTUtil.to_date(begin_time, 1)+"";
		}
		if(!StringUtil.isEmpty(end_time)){
			sql += " AND a.end_time <= "+DBTUtil.to_date(end_time, 1)+"";
		}
		sql += " order by a.begin_time desc";
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize,true,sqlParams.toArray(new String[]{}));
		
		String pmtGoodsSql = SF.goodsSql("PROMOTION_ACTIVITY_GET_GOODS_LOG_ECS");
		String activityCoSql = SF.goodsSql("PROMOTION_ACTIVITY_GET_CO_ECS");
		
		List list = page.getResult();
		for(int i=0;i<list.size();i++){
			Map result = (Map)list.get(i);
			String id = (String)result.get("id");
			String pmt_id = (String)result.get("pmt_id");
			List pmtGoodsList = this.baseDaoSupport.queryForList(pmtGoodsSql,pmt_id);
			List activityCoList = this.baseDaoSupport.queryForList(activityCoSql,id);
			String goods_names = "";
			String org_names = "",org_ids = "";
			String pub_status = "";
			String pub_status_name = "";
			for(int j=0;j<pmtGoodsList.size();j++){
				Map pmtGoods = (Map) pmtGoodsList.get(j);
				if(j>0){
					goods_names += ",";
				}
				goods_names += pmtGoods.get("name");
			}
			if(activityCoList.size()>0){
				for(int k=0;k<activityCoList.size();k++){
					Map co = (Map) activityCoList.get(k);
					if(k>0){
						org_names += ",";
						org_ids += ",";
					}
					org_names += co.get("org_name");
					org_ids += co.get("org_id");
				}
			}else{
				pub_status_name = "未发布";
				pub_status = Consts.PUBLISH_STATE_0.toString();
			}
            String goods_names_view = goods_names;
			if(StringUtils.isNotBlank(goods_names) && goods_names.length()>=15){
				goods_names_view = goods_names.substring(0, 15) + "...";
			}
            String org_names_view = org_names;
			if(StringUtils.isNotBlank(org_names) && org_names.length()>=15){
				org_names_view = org_names.substring(0, 15) + "...";
			}
			result.put("goods_names", goods_names);
			result.put("org_names", org_names);
			result.put("goods_names_view", goods_names_view);
			result.put("org_names_view", org_names_view);
			result.put("pub_status", pub_status);
			result.put("pub_status_name", pub_status_name);
			result.put("org_ids", org_ids);
		}
		
		return page;
	}
	
	@Override
	public Page getGoodsList(int pageNo, int pageSize,String name,String type,String type_id,String sku) {
		StringBuffer bufferSql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		String sql = SF.goodsSql("ACTIVITY_GOODS_LIST");
		if(!StringUtils.isEmpty(type)){
			sql += " and b.type = ?";
			params.add(type);
		}
		if(!StringUtils.isEmpty(name)){
			sql += " and upper(b.name) like ?";
			params.add("%"+name.toUpperCase()+"%");
		}
		if(!StringUtils.isEmpty(type_id)){
			//兼容SP产品和附加产品小类---zengxianlian
			/*if("10008".equals(type_id)){
				sql += " and (b.type_id = ? or b.type_id='10050' or b.type_id='10051' )";
			}else{
				sql += " and b.type_id = ?";
			}
			params.add(type_id);*/ //注释 by mo.chencheng 2016-05-03 
			
			//活动配置页面，赠送业务值只允许选择附加产品、SP服务、业务包的货品     add by mo.chencheng 2016-05-03
			if("10008".equals(type_id)){
				//sql += " and (b.type_id = ? or b.type_id='10050' or b.type_id='10051' )"; //注释by mo.chencheng 2016-05-03
				//只能加载SP产品、附加产品、业务包小类   add by mo.chencheng 2016-05-03
				sql += " and (b.type_id='10050' or b.type_id='10051' or b.type_id='10009' )"; 
			}else if("42000".equals(type_id)){
				sql += " and (b.type_id='42000' or b.type_id='43000' )"; 
			}
			else{
				sql += " and b.type_id = ?";
				params.add(type_id);
			}
		}
		if(!StringUtils.isEmpty(sku)){
			sql += " and b.sku = ?";
			params.add(sku);
		}
		Page webpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize,true,params.toArray(new String[]{}));
		return webpage;
	}
	
	@Override
	public Page getPackageList(int pageNo, int pageSize, String name, String sku) {
		StringBuffer bufferSql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		String sql = SF.goodsSql("ACTIVITY_GOODS_PACKAGE_LIST");
		if(!StringUtils.isEmpty(name)){
			sql += " and upper(a.relation_name) like ?";
			params.add("%"+name.toUpperCase()+"%");
		}
		if(!StringUtils.isEmpty(sku)){
			sql += " and c.sku like ?";
			params.add("%"+sku+"%");
		}
		Page webpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, true, 
				params.toArray(new String[]{}));
		
		return webpage;
	}
	
	private void deleteActivity(String activity_id){
		String sql = SF.goodsSql("DELETE_ACTIVITY_BY_ID");
		this.baseDaoSupport.execute(sql, activity_id);
	}
	
	private void deletePromotion(String activity_id){
		String sql = SF.goodsSql("PROMOTION_DELETE");
		sql += " and pmta_id = ?";
		this.baseDaoSupport.execute(sql, activity_id);
	}
	
	private void deletePmtGoods(String activity_id) {
		String sql = SF.goodsSql("DELETE_PMT_GOODS");
		sql += " and pmt_id in(select pmt_id from es_promotion where pmta_id = ?)";
		this.baseDaoSupport.execute(sql, activity_id);
	}
	
	@Override
	@Transactional
	public void saveOrUpdateActECS(PromotionActivity activity,
			ActivityAttr activityAttr, Promotion promotion) {
		
		String pmt_id = promotion.getPmt_id();
		String id = activity.getId();
		String pmt_type =  promotion.getPmt_type();
		String pmt_goods_ids = activityAttr.getPmt_goods_ids();
		String act_org_ids = activityAttr.getAct_org_ids();
		String gift_goods_ids = activityAttr.getGift_goods_ids();
		String isSaveOrg = activityAttr.getIsSaveOrg();
		String user_id="";
		try
		{
			user_id = ManagerUtils.getAdminUser().getUserid();
		}
		catch (Exception e)
		{
			user_id=activity.getUserid();
		}
			
		if(StringUtils.isNotBlank(activityAttr.getGoods_ids())){//页面选定商品
			pmt_goods_ids = activityAttr.getGoods_ids().replace(" ", "");
		}else if (StringUtils.isNotEmpty(activity.getRelation_id())) {//根据货品包及相关过滤条件获取活动适用商品
			List pmtGoodsIdLst = this.pmtGoodsIdFilter(activity);
			pmt_goods_ids = GoodsManagerUtils.convertStrFromLst(pmtGoodsIdLst, ",");
		}
		
		String reg = "\\d{4}-\\d{2}-\\d{2}";
		//使用修改时间作为生效时间
		String modify_eff_time = activity.getModify_eff_time();
		String startTime = activity.getBegin_time();
		String endTime = activity.getEnd_time();
		//开始时间增加  00:00:00 结束时间增加 23:59:59
//		if(modify_eff_time!=null && modify_eff_time.matches(reg))modify_eff_time += " 00:00:00";
//		if(startTime!=null && startTime.matches(reg))startTime += " 00:00:00";
//		if(endTime!=null && endTime.matches(reg))endTime += " 23:59:59";
		activity.setBegin_time(startTime);
		activity.setEnd_time(endTime);
		activity.setModify_eff_time(modify_eff_time);
		
		if(StringUtils.isNotBlank(activity.getId())&&isable(activity.getId())){//更新
			
			String activity_id = activity.getId();
			
			//修改前备份该活动信息及其相关信息
			this.backUpActivity(activity_id);
			
			//删除老活动纪录
			this.deletePmtGoods(activity_id);  //活动的适用商品和赠品
			this.deletePromotion(activity_id);  //活动规则
			this.deleteActivity(activity_id);  //活动基本信息
			
			//保存最新的活动信息
			//活动基本信息
			id = this.baseDaoSupport.getSequences("S_ES_PROMOTION");
			activity.setId(id);
			this.baseDaoSupport.insert("es_promotion_activity", activity);
			
			//活动规则信息
			pmt_id = this.baseDaoSupport.getSequences("S_ES_PROMOTION_ACTIVITY"); 
			promotion.setPmt_id(pmt_id);
			promotion.setPmta_id(id);
			promotion.setPmt_time_begin(activity.getBegin_time());
			promotion.setPmt_time_end(activity.getEnd_time());
			promotion.setPromotion_type(promotion.getPmt_type());
			promotion.setPmts_id(Consts.PMT_TYPES_ID_ACTIVITY);
			this.baseDaoSupport.insert("es_promotion", promotion);

			//保存活动商品
			this.savePmtGoods(pmt_goods_ids, pmt_id);
			
			/**
			 * 保存赠品
			 * 如果规则类型为满赠，则保存赠品为活动商品
			 * 如果规则类型不为满赠，则创建赠品规则，并保存赠品
			 */
			if (Consts.PMT_TYPE_MANZENG.equals(pmt_type)) {
				
				this.savePmtGoods(gift_goods_ids, pmt_id);
			} else {
				if (StringUtils.isNotBlank(gift_goods_ids)) {
					String gift_pmt_id = this.getPromotionGift(activity).getPmt_id();
					this.savePmtGoods( gift_goods_ids, gift_pmt_id);
				}
			}
			
			//已经发布的活动需要写队列
			Integer publish_status = this.getActivityPubStatus(activity_id);
			if (publish_status != -1 && (publish_status == Consts.PUBLISH_STATE_3 
					|| publish_status == Consts.PUBLISH_STATE_1)) {
				
				//保存发布组织--已发布状态
				//this.saveActivityCo(act_org_ids, id, user_id, Consts.PUBLISH_STATE_1);
				//保存发布组织--发布中状态--zengxianlian
				this.saveActivityCo(act_org_ids, id, user_id, Consts.PUBLISH_STATE_2);
				//写同步消息队列es_co_queue
				if (StringUtils.isNotBlank(act_org_ids)) {
					this.writeQueue(id, act_org_ids, Consts.ACTION_CODE_M);
				}
				
			} else  {
				//保存发布组织--未发布状态
				this.saveActivityCo(act_org_ids, id, user_id, Consts.PUBLISH_STATE_0);
			}
			
		} else {//新增
			
			id = this.baseDaoSupport.getSequences("S_ES_PROMOTION");
			pmt_id = this.baseDaoSupport.getSequences("S_ES_PROMOTION_ACTIVITY"); 
			
			//保存活动
			activity.setId(id);
			activity.setPmt_code(id);
			activity.setUserid(user_id);
//			activity.setEnable(1);
			this.baseDaoSupport.insert("es_promotion_activity", activity);
			
			//保存活动规则
			promotion.setPmt_id(pmt_id);
			promotion.setPmta_id(id);
			
			promotion.setPmt_time_begin(activity.getBegin_time());
			promotion.setPmt_time_end(activity.getEnd_time());
			promotion.setPromotion_type(promotion.getPmt_type());
			promotion.setPmts_id(Consts.PMT_TYPES_ID_ACTIVITY);
			this.baseDaoSupport.insert("es_promotion", promotion);
			
			//保存活动商品
			this.savePmtGoods(pmt_goods_ids, pmt_id);
			
			/**
			 * 保存赠品
			 * 如果规则类型为满赠，则保存赠品为活动商品
			 * 如果规则类型不为满赠，则创建赠品规则，并保存赠品
			 * 
			 */
			if(Consts.PMT_TYPE_MANZENG.equals(pmt_type)){
				this.savePmtGoods( gift_goods_ids, pmt_id);
			}else{
				if(StringUtils.isNotBlank(gift_goods_ids)){
					String gift_pmt_id = this.getPromotionGift(activity).getPmt_id();
					this.savePmtGoods( gift_goods_ids, gift_pmt_id);
				}
			}
			
			//保存发布组织
			this.saveActivityCo(act_org_ids, id, user_id, Consts.PUBLISH_STATE_0);
		}
		
	}
	
	private boolean isable(String id) {
		// TODO 检验活动是否启用
		int enable=1;
		String sql="select enable from es_promotion_activity where id = ?";
		enable=this.baseDaoSupport.queryForInt(sql, id);
		if(enable==1){
			return true;
		}
		return false;
	}

	//保存活动商品
	public void savePmtGoods(String pmt_goods_ids,String pmt_id){
		//保存活动商品之前先删除
		String deleteSql = SF.goodsSql("DELETE_PROMOTION_GOODS");
		this.baseDaoSupport.execute(deleteSql, pmt_id);
		if(StringUtils.isNotEmpty(pmt_goods_ids)){
			String [] array = pmt_goods_ids.split(",");
			for(int i=0;i<array.length;i++){
				String goods_id = array[i];
				PmtGoos pmtGoods = new PmtGoos();
				pmtGoods.setPmt_id(pmt_id);
				pmtGoods.setGoods_id(goods_id);
				this.baseDaoSupport.insert("es_pmt_goods", pmtGoods);
			}
		}
	}
	
	//保存活动发布信息
	public void saveActivityCo(String act_org_ids,String activity_id,String user_id, Integer status){
		
		//保存发布组织之前先删除
		String deleteSql = SF.goodsSql("DELETE_PROMOTION_CO");
		this.baseDaoSupport.execute(deleteSql, activity_id);
		if (status == null) {
			status = Consts.PUBLISH_STATE_0;
		}
		
		if(StringUtils.isNotEmpty(act_org_ids)){
			//String batch_id = this.daoSupport.getSequences("S_ES_CO_BATCH_ID");
			String [] array = act_org_ids.split(",");
			for(int i=0;i<array.length;i++){
				//修改每条记录每个batch_id---zengxianlian
				String batch_id = this.daoSupport.getSequences("S_ES_CO_BATCH_ID");
				String org_id = array[i];
				ActivityCo co = new ActivityCo();
				String id = this.baseDaoSupport.getSequences("S_ES_ACTIVITY_CO");
				co.setId(id);
				co.setActivity_id(activity_id);
				co.setOrg_id(org_id);
				co.setOper_id(user_id);
				co.setStatus(status);
				co.setCreated_date(DBTUtil.current());
				co.setStatus_date(DBTUtil.current());
				co.setBatch_id(batch_id);
				this.baseDaoSupport.insert("es_activity_co", co);
			}
		}
	}
	
	/**
	 * 新增赠品规则
	 * @return
	 */
	public Promotion getPromotionGift(PromotionActivity activity){
		String pmt_id = "";
		Promotion promoGift = null;
		String sql = SF.goodsSql("PROMOATION_GIFT");
		List<Promotion> promoGiftList =  this.baseDaoSupport.queryForList(sql, Promotion.class, activity.getId());
		if(promoGiftList == null || promoGiftList.size()<=0){
			promoGift = new Promotion();
			promoGift.setPmta_id(activity.getId());
			promoGift.setPmt_time_begin(activity.getBegin_time());
			promoGift.setPmt_time_end(activity.getEnd_time());
			promoGift.setPromotion_type("010");
			promoGift.setPmt_type(Consts.PMT_TYPE_GIFT);
			promoGift.setPmts_id(Consts.PMT_TYPES_ID_GIFT);
			promoGift.setPmt_solution("0");
			promoGift.setPmt_describe("商品赠送");
			pmt_id = this.baseDaoSupport.getSequences("S_ES_PROMOTION_ACTIVITY"); 
			promoGift.setPmt_id(pmt_id);
			promoGift.setDisabled("1");
			this.baseDaoSupport.insert("es_promotion", promoGift);
		}else{
			promoGift = promoGiftList.get(0);
		}

		return promoGift;
	}
	@Override
	public Promotion getPromotion(String activity_id) {
		String sql = SF.goodsSql("PROMOATION_ACTIVITY");
		List<Promotion> promoList =  this.baseDaoSupport.queryForList(sql, Promotion.class, activity_id);
		Promotion promotion = new Promotion();
		if(promoList != null && promoList.size()>0){
			promotion = promoList.get(0);
		}
		
		return promotion;
	}
	@Override
	public Promotion getGiftPromo(String activity_id) {
		Promotion promoGift = new Promotion();
		String sql = SF.goodsSql("PROMOATION_GIFT");
		List<Promotion> promoGiftList =  this.baseDaoSupport.queryForList(sql, Promotion.class, activity_id);
		if(promoGiftList != null && promoGiftList.size()>0){
			promoGift = promoGiftList.get(0);
		}
		return promoGift;
	}
	@Override
	public List<Goods> getGoodsByPmtId(String pmt_id) {
		String sql = SF.goodsSql("ACTIVITY_PROMO_GOODS");
		List<Goods> goodsList =  this.baseDaoSupport.queryForList(sql, Goods.class, pmt_id);
		return goodsList;
	}
	@Override
	public List<ActivityCo> getActivityCo(String activity_id) {
		String sql = SF.goodsSql("ACTIVITY_CO_BY_ACTIVITY_ID");
		List<ActivityCo> coList =  this.baseDaoSupport.queryForList(sql, ActivityCo.class, activity_id);
		return coList;
	}
	@Override
	public String checkPmtCode(String pmt_code) {
		String sql = "select * from es_promotion_activity a where a.pmt_code=?"; //ACTIVITY_CHECK_PMTCODE
		List list = this.baseDaoSupport.queryForList(sql, pmt_code);
		if(list!=null && list.size()>0){
			return "true";
		}else{
			return "false";
		}
	}
	@Override
	@Transactional
	public Map editEnableECS(PromotionActivity activity,ActivityAttr activityAttr) {
		if(activity != null){
			
			String sql = SF.goodsSql("UPDATE_ACTIVITY_ENABLE_ECS");
			int enable = activity.getEnable();
			this.baseDaoSupport.execute(sql, enable,activity.getId());
			//写消息队列
			String activity_id = activity.getId();
			String org_ids =  activityAttr.getAct_org_ids();
			Integer publish_status = this.getActivityPubStatus(activity_id);
			
			//已经发布的活动需要写队列
			if(publish_status != -1 && (publish_status == Consts.PUBLISH_STATE_3 || publish_status == Consts.PUBLISH_STATE_1)){
				//写同步消息队列es_co_queue
				if(StringUtils.isNotBlank(org_ids)){
					if(enable == 0){
						this.writeQueue(activity_id, org_ids,Consts.ACTION_CODE_D);
					}else if(enable == 1){
						this.writeQueue(activity_id, org_ids,Consts.ACTION_CODE_A);
					}
					
				}
			}
		}
		return new HashMap();
	}
	@Override
	public Integer getActivityPubStatus(String activity_id) {
		Integer publish_status = -1;
		String sql = SF.goodsSql("ACTIVITY_PUBLISH_STATUS_ECS");
		List<ActivityCo>  colist =  this.baseDaoSupport.queryForList(sql, ActivityCo.class, activity_id);
		if(colist !=null && colist.size()>0){
			ActivityCo co = colist.get(0);
			publish_status = co.getStatus();
		}else{
			publish_status = Consts.PUBLISH_STATE_0;
		}
		 
		return publish_status;
	}
	
	/**
	 * 通过销售组织名称查找销售组织ID
	 * @param org_name
	 * @return
	 */
	public List<GoodsOrg> getOrgIdByName(String org_names){
		List<GoodsOrg>  orglist = new ArrayList<GoodsOrg>();
		if(StringUtils.isNotBlank(org_names)){
			String sql = SF.goodsSql("GOODS_ORG_BY_NAME");
			List sqlParams = new ArrayList();
			String [] orgArray = org_names.split("、");
			for(int i=0;i<orgArray.length;i++){
				if(i==0){
					sql += " and a.org_name in(";
				}else{
					sql += ",";
				}
				sql += " ?";
				sqlParams.add(orgArray[i]);
			}
			if(orgArray.length>0){
				sql += ")";
			}
			orglist =  this.baseDaoSupport.queryForList(sql, GoodsOrg.class, sqlParams.toArray());
		}
		return orglist;
	}
	
	/**
	 * 根据货品包过滤出商品
	 * @param activity
	 * @return
	 */
	private List pmtGoodsIdFilter(PromotionActivity activity) {
		
		List pmtGoodsIdLst = new ArrayList();
		String relation_id = activity.getRelation_id();  //货品包标识
		String package_class = activity.getPackage_class();  //套餐类别串
		String min_price = activity.getMin_price();  //套餐最低档次
		String max_price = activity.getMax_price();  //套餐最高档次
		List params = new ArrayList();
		
		String sql = "select a.goods_id from es_goods a, es_relation b, es_relation_detail c "
				+ " where 1=1"
				+ " and a.goods_id = c.object_id"
				+ " and b.relation_id = c.relation_id"
				+ " and a.source_from = '" + ManagerUtils.getSourceFrom() + "'"
				+ " and b.relation_id in(" + activity.getRelation_id() +")";
		
		if (StringUtils.isNotEmpty(package_class) || StringUtils.isNotEmpty(min_price)
				|| StringUtils.isNotEmpty(max_price)) {
			
			sql += " and exists(select 1 from es_goods_rel rel, es_goods gds"
					+ " where 1=1"
					+ " and rel.a_goods_id = a.goods_id "
					+ " and rel_type = '" + Consts.PRO_REL_GOODS + "'"
					+ " and rel.z_goods_id = gds.goods_id"
					+ " and gds.type_id = '"+ Consts.GOODS_TYPE_OFFER +"'"
					+ " and gds.source_from = '" + ManagerUtils.getSourceFrom() + "'"
					+ " and gds.stype_id is not null";
			
			if (StringUtils.isNotEmpty(package_class)) {
				sql += " and gds.model_code in(" + Const.getInWhereCond(package_class) + ")";
			}
					
			if (StringUtils.isNotEmpty(min_price)) {
				sql += " and to_number(gds.stype_id) >= ? ";
				params.add(min_price);
			}
			
			if (StringUtils.isNotEmpty(max_price)) {
				sql += " and to_number(gds.stype_id) <= ?";
				params.add(max_price);
			}
			
			sql += ")";
		}
		
		pmtGoodsIdLst = this.jdbcTemplate.queryForList(sql, String.class, params.toArray());
		
		return pmtGoodsIdLst;
	}
	
	/**
	 * 活动及其相关信息备份
	 * @param activity_id  活动ID
	 */
	private void backUpActivity(String activity_id) {
		
		/**
		 * 操作工号
		 */
		AdminUser user = ManagerUtils.getAdminUser();
		String oper_id = "-1";
		if (user != null) {
        	oper_id = user.getUserid();
		}
		
		//首先，备份活动基本信息【ES_PROMOTION_ACTIVITY】
		Map poMap = null;
		String sql = SF.goodsSql("ACTIVITY_BASE_SQL");
		sql += " and id = ?";
		PromotionActivity activity = (PromotionActivity)this.baseDaoSupport.queryForObject(sql, 
				PromotionActivity.class, activity_id);
		poMap = ReflectionUtil.po2Map(activity);
		String log_id = this.baseDaoSupport.getSequences("S_ES_PROMOTION_ACTIVITY_LOG");
		poMap.put("log_id", log_id);
		poMap.put("oper_id", oper_id);
		this.baseDaoSupport.insert("es_promotion_activity_log", poMap, poMap);
		
		//其次，备份活动规则信息【ES_PROMOTION】和适合该活动规则的商品及活动赠品信息【ES_PMT_GOODS】
		String pmt_id_str = "";
		Promotion pmt = null;
		sql = SF.goodsSql("PROMOTION_BASE_SQL");
		sql += " and pmta_id = ?";
		List<Promotion> pmtLst = this.baseDaoSupport.queryForList(sql, Promotion.class, activity_id);
		for (int i = 0; pmtLst != null && i< pmtLst.size(); i++) {
			pmt = pmtLst.get(i);
			poMap = ReflectionUtil.po2Map(pmt);
			poMap.put("log_id", log_id);
			poMap.put("oper_id", oper_id);
			this.baseDaoSupport.insert("es_promotion_log", poMap, poMap);
			
			if (i == 0) {
				pmt_id_str = pmt.getPmt_id();
			} else {
				pmt_id_str += "," + pmt.getPmt_id();
			}
		}
		
		//最后，适合该活动规则的商品及活动赠品信息【ES_PMT_GOODS】
		PmtGoos pmtGoods = null;
		if (!StringUtils.isEmpty(pmt_id_str)) {
			sql = SF.goodsSql("PMT_GOODS_BASE_SQL");
			sql += " and pmt_id in(" + pmt_id_str + ")";
			List<PmtGoos> pmtGoodsLst = this.baseDaoSupport.queryForList(sql, PmtGoos.class);
			for (int i = 0; pmtGoodsLst != null && i< pmtGoodsLst.size(); i++) {
				pmtGoods = pmtGoodsLst.get(i);
				poMap = ReflectionUtil.po2Map(pmtGoods);
				poMap.put("log_id", log_id);
				poMap.put("oper_id", oper_id);
				this.baseDaoSupport.insert("es_pmt_goods_log", poMap, poMap);
			}
		}
		
	}

	@Override
	public Page searchActivitySynLogs(Map params,int pageNum,int pageSize) {
		String pmt_code = Const.getStrValue(params, "pmt_code");
		String pmt_name = Const.getStrValue(params, "pmt_name");
		String batch_id = Const.getStrValue(params, "batch_id");
		String status = Const.getStrValue(params, "status");
		String start_date = Const.getStrValue(params, "start_date");
		String end_date = Const.getStrValue(params, "end_date");

		
		StringBuilder sql = new StringBuilder(SF.goodsSql("ACTIVITY_SYNCH_LOGS"));//失败日志
		List pList = new ArrayList();
		if(!StringUtils.isEmpty(status)&&status.equals("XYCG")){
			sql.append("es_co_queue_bak b where a.id=b.object_id and b.service_code='CO_HUODONG' and a.source_from='"+ManagerUtils.getSourceFrom()+"'");	
		}
		else if(!StringUtils.isEmpty(status)&&status.equals("XYSB")){
			sql.append("es_co_queue b where a.id=b.object_id and b.service_code='CO_HUODONG' and a.source_from='"+ManagerUtils.getSourceFrom()+"'");	
		}else{
			sql.append("(select * from es_co_queue_bak union all select * from es_co_queue) b where a.id=b.object_id and b.service_code='CO_HUODONG' and a.source_from='"+ManagerUtils.getSourceFrom()+"'");	
		}
		if(!StringUtils.isEmpty(batch_id)){
			sql.append(" and b.batch_id=? ");
			pList.add(batch_id);
		}
		if(StringUtils.isNotEmpty(start_date)){
			start_date += " 00:00:00";
			sql.append(" and b.created_date>=to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			pList.add(start_date);
		}
		if(StringUtils.isNotEmpty(end_date)){
			end_date += " 23:59:59";
			sql.append(" and b.created_date<=to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			pList.add(end_date);
		}
		if(!StringUtils.isEmpty(pmt_code)){
			sql.append(" and upper(a.pmt_code) like ? ");
			pList.add("%"+pmt_code.toUpperCase()+"%");
		}
		if(!StringUtils.isEmpty(pmt_name)){
			sql.append(" and upper(a.name) like ? ");
			pList.add("%"+pmt_name.toUpperCase()+"%");
		}
		
		sql.append(" order by b.created_date DESC");
		Page page = this.baseDaoSupport.queryForPage(sql.toString(), pageNum, pageSize, pList.toArray());
	
		List<Map> logs = page.getResult();
		if(logs!=null && logs.size()>0){
			for(Map log:logs){
				String org_id_str = Const.getStrValue(log, "org_id_str");
				if(!StringUtils.isEmpty(org_id_str)){
					String org_name_str = this.baseDaoSupport.queryForString(SF.goodsSql("SYNCH_ORG_GET")+" and PARTY_ID IN("+org_id_str+")", null);
					log.put("org_name_str", org_name_str);
				}
			}
		}
		return page;
	}

	@Override
	public int publishAgain(Map params) {
		String pmt_code = Const.getStrValue(params, "pmt_code");
		String pmt_name = Const.getStrValue(params, "pmt_name");
		String batch_id = Const.getStrValue(params, "batch_id");
		String status = Consts.CO_QUEUE_STATUS_XYSB;
		
		StringBuilder sql = new StringBuilder(SF.goodsSql("ACTIVITY_SYNCH_LOGS"));
		List pList = new ArrayList();
		if(!StringUtils.isEmpty(batch_id)){
			sql.append(" and b.batch_id=? ");
			pList.add(batch_id);
		}
		if(!StringUtils.isEmpty(pmt_code)){
			sql.append(" and upper(a.pmt_code) like ? ");
			pList.add("%"+pmt_code.toUpperCase()+"%");
		}
		if(!StringUtils.isEmpty(pmt_name)){
			sql.append(" and upper(a.name) like ? ");
			pList.add("%"+pmt_name.toUpperCase()+"%");
		}
		if(!StringUtils.isEmpty(status)){
			sql.append(" and b.status=? ");
			pList.add(status);
		}
		List<Map> queues = this.baseDaoSupport.queryForList(sql.toString(), pList.toArray());
		int count = 0;
		if(queues!=null && queues.size()>0){
			List ids = new ArrayList();
			int size = queues.size();
			for(int i=0;i<size;i++){
				count++;
				Map queue = queues.get(i);
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
	public Map getActivitySpecs(String activity_code) {
		StringBuffer activeStr = new StringBuffer();
		Map specMap = new HashMap();
		activeStr.append("[");
		String activity_desc = "";
		String disacount_num = "0";
		try {
			//获取活动同步信息
			Map pmt_map = this.getActivitySpecMap(activity_code);
			specMap.put("activity_id", Const.getStrValue(pmt_map, "id"));
			specMap.put("activity_code", Const.getStrValue(pmt_map, "pmt_code"));
			specMap.put("activity_name", Const.getStrValue(pmt_map, "pmt_name"));
			specMap.put("active_type", Const.getStrValue(pmt_map, "pmt_type"));
			specMap.put("disacount_range", Const.getStrValue(pmt_map, "pmt_solution"));
			specMap.put("disacount_num", Const.getStrValue(pmt_map, "pmt_solution"));
			specMap.put("buy_mode", Const.getStrValue(pmt_map, "buy_mode"));
			if (null != pmt_map && pmt_map.size() > 0) {
				activeStr.append("{");
				activeStr.append("\"activity_code\":\""+ pmt_map.get("pmt_code") +"\",");
				activeStr.append("\"activity_name\":\""+ pmt_map.get("pmt_code") +"\",");
				activity_desc = pmt_map.get("pmt_describe").toString();
				if (StringUtils.isEmpty(activity_desc)) {
					activity_desc = Const.getStrValue(pmt_map, "pmt_name");
				}
				specMap.put("activity_desc", activity_desc);
				//以元为单位，不用转换为厘
				disacount_num = Const.getStrValue(pmt_map, "pmt_solution");
				activeStr.append("\"activity_desc\":\""+ activity_desc +"\",");					
				activeStr.append("\"activity_type\":\"02\",");
				activeStr.append("\"disacount_range\":\"\",");
				activeStr.append("\"disacount_num\":\""+disacount_num+"\",");
				activeStr.append("\"disacount_unit\":\"02\"");
				activeStr.append("}");
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		activeStr.append("]");
		specMap.put("activity_list", activeStr);
		return specMap;
	}

	@Override
	public List queryActivityExport(PromotionActivity activity) {
		// TODO 导出活动的数据
		List list=null;
		List data=new ArrayList<Map>();
		String sql=SF.goodsSql("ACTIVITY_EXPORT_DATA");
		List params = new ArrayList();
		if(!StringUtils.isEmpty(activity.getName())){
			String activity_name=activity.getName();
			sql+=" and a.name like '%"+activity_name+"%'";
		}
		if(!StringUtils.isEmpty(activity.getBegin_time())){
			String begin_time=activity.getBegin_time();
			begin_time+=" 00:00:00";
			sql+=" and a.begin_time>=to_date(?,'yyyy-mm-dd hh24:mi:ss') ";
			params.add(begin_time);
		}
		if(!StringUtils.isEmpty(activity.getEnd_time())){
			String end_time=activity.getEnd_time();
			end_time+=" 23:59:59";
			sql+=" and a.end_time<=to_date(?,'yyyy-mm-dd hh24:mi:ss') ";
			params.add(end_time);
		}
		list= this.baseDaoSupport.queryForList(sql,params.toArray());//查询活动与活动规则表的基础数据
		for(int i=0;i<list.size();i++){
			Map m=(Map) list.get(i);
			//活动编码获取活动类型
			if(m.get("pmt_type_code")!=null){
				String pmt_type_code=m.get("pmt_type_code").toString();
				if(pmt_type_code.equals("006")){
					m.put("pmt_type","直降");
				}else{
					m.put("pmt_type",pmt_type_code);//预留提示活动编码
				}
			}
			//表示活动有效状态
			if(m.get("enable")!=null){
				String enable=m.get("enable").toString();
				if(enable.equals("1")){
					m.put("activity_enable","有效");
				}else{
					m.put("activity_enable","失效");
				}
			}
			//转换活动地市
			if(m.get("region")!=null){
				String region=m.get("region").toString();
				String region_name="";
				if(region.equals("440000")){
					region_name="全省";
				}else{
					String sqlreg="select region_name from es_common_region where region_id=?";
					region_name=this.baseDaoSupport.queryForString(sqlreg,region);
				}
				m.put("region_name", region_name);
			}
			//截取时间
			if(m.get("modify_eff_time")!=null){
				String modify_eff_time=m.get("modify_eff_time").toString();
				modify_eff_time=modify_eff_time.substring(0,10).replace('-', '/');
				m.remove("modify_eff_time");
				m.put("modify_eff_time", modify_eff_time);
			}
			//活动起止时间
			if(m.get("begin_time")!=null&&m.get("end_time")!=null){
				String begin_time=m.get("begin_time").toString();
				begin_time=begin_time.substring(0,10).replace('-','/');
				String end_time=m.get("end_time").toString();
				end_time=end_time.substring(0,10).replace('-','/');
				String begin_end_time=begin_time+"-"+end_time;
				m.put("begin_end_time", begin_end_time);
			}
			
			//货品包ID获取货品包名称
			if(m.get("relation_id")!=null){
				String relation_id=m.get("relation_id").toString();
				String relation_names="";
				String[] reId=relation_id.split(",");
				for(int j=0;j<reId.length;j++){
					String sqlreid="select relation_name from es_relation where relation_id=? and rownum<2";
					String relation_name=this.baseDaoSupport.queryForString(sqlreid,reId[j]);
					relation_names+="|"+relation_name;
				}
				if(!StringUtils.isEmpty(relation_names))
				relation_names=relation_names.substring(1);
				m.put("relation_name", relation_names);
			}
			//套餐分类
			if(m.get("package_class")!=null){
				String package_class=m.get("package_class").toString();
				package_class=package_class.replace(',', '|');
				m.remove("package_class");
				m.put("package_class", package_class);
			}
			
			//活动ID获取适用商品与礼品
			if(m.get("activity_id")!=null){
				//适用商品
				String activity_id=m.get("activity_id").toString();
				Promotion goodsPro=this.getPromotion(activity_id);
				List goodslist=this.getGoodsByPmtId(goodsPro.getPmt_id());
				String goods="";
				for(int j=0;j<goodslist.size();j++){
					Goods gd=(Goods)goodslist.get(j);
					String goodsname=gd.getName();
					goods+="|"+goodsname;
				}
				if(!StringUtils.isEmpty(goods))
					goods=goods.substring(1);
					m.put("goods", goods);
				//适用礼品	
				Promotion giftPro=this.getGiftPromo(activity_id);
				List giftslist=this.getGoodsByPmtId(giftPro.getPmt_id());
				String gifts="";
				String zjzdbnames="";
				String zjzdbskus="";
				String zsywnames="";
				String zsywskus="";
				String lpnames="";
				String lpskus="";
				for(int k=0;k<giftslist.size();k++){
					Goods gd=(Goods)giftslist.get(k);
					String type_id=gd.getType_id();
					String giftsname=gd.getName();
					String giftsku=gd.getSku();
					if(type_id.equals("10010")){
						zjzdbnames+="|"+giftsname;
						zjzdbskus+="|"+giftsku;
						continue;
					}
					if(type_id.equals("10008")){
						zsywnames+="|"+giftsname;
						zsywskus+="|"+giftsku;
						continue;
					}
					if(type_id.equals("10007")){
						lpnames+="|"+giftsname;
						lpskus+="|"+giftsku;
						continue;
					}
				}	
				//直降转兑包名字
				if(!StringUtils.isEmpty(zjzdbnames))
					zjzdbnames=zjzdbnames.substring(1);
					m.put("gifts_zjzdb", zjzdbnames);
				//直降转兑包SKU
				if(!StringUtils.isEmpty(zjzdbskus))
					zjzdbskus=zjzdbskus.substring(1);
					m.put("skus_zjzdb", zjzdbskus);
				//赠送业务名字
				if(!StringUtils.isEmpty(zsywnames))
					zsywnames=zsywnames.substring(1);
					m.put("gifts_zsyw", zsywnames);
				//赠送业务SKU
				if(!StringUtils.isEmpty(zsywskus))
					zsywskus=zsywskus.substring(1);
					m.put("skus_zsyw", zsywskus);
				//礼品的名字
				if(!StringUtils.isEmpty(lpnames))
					lpnames=lpnames.substring(1);
					m.put("gifts_lp", lpnames);
				//礼品的SKU
				if(!StringUtils.isEmpty(lpskus))
					lpskus=lpskus.substring(1);
					m.put("skus_lp", lpskus);
				//获取活动的组织
				List<ActivityCo> coList = this.getActivityCo(activity_id);
				String act_org_names = "" ; //活动商城name,多个用逗号隔开
				for(int l=0;l<coList.size();l++){
					ActivityCo activityCo = coList.get(l);
					act_org_names +="|"+activityCo.getOrg_name();
				}
				if(!StringUtils.isEmpty(act_org_names))
					act_org_names=act_org_names.substring(1);
					m.put("org_id_str", act_org_names);
			}
			//靓号减免类型
			if(m.get("relief_no_class")!=null){
				String[] reNo=m.get("relief_no_class").toString().split(",");
				String relief_no_class="";
				for(int ii=0;ii<reNo.length;ii++){
					if(reNo[ii].equals("1")){
						relief_no_class+="|"+"一类";
					}
					else if(reNo[ii].equals("2")){
						relief_no_class+="|"+"二类";
					}
					else if(reNo[ii].equals("3")){
						relief_no_class+="|"+"三类";
					}
					else if(reNo[ii].equals("4")){
						relief_no_class+="|"+"四类";
					}
					else if(reNo[ii].equals("5")){
						relief_no_class+="|"+"五类";
					}
					else if(reNo[ii].equals("11")){
						relief_no_class+="|"+"+1类";
					}
					else if(reNo[ii].equals("12")){
						relief_no_class+="|"+"+2类";
					}
					else if(reNo[ii].equals("13")){
						relief_no_class+="|"+"+3类";
					}
					else if(reNo[ii].equals("14")){
						relief_no_class+="|"+"+4类";
					}
					else if(reNo[ii].equals("15")){
						relief_no_class+="|"+"+5类";
					}
				}
				if(!StringUtils.isEmpty(relief_no_class))
					relief_no_class=relief_no_class.substring(1);
					m.remove("relief_no_class");
					m.put("relief_no_class", relief_no_class);
				}
			data.add(m);
		}
		return data;
		
	}

	@Override
	public List<HashMap> listAllActivitySpecs() {
		List<HashMap> acts = this.baseDaoSupport.queryForList(SF.goodsSql("ACTIVITY_SPEC_MAP_LIST"), Consts.DC_PUBLIC_STYPE_PMT_TYPE);
		return acts;
	}

	@Override
	public Map<String, String> checkPublishAct(Map<String,String> params) throws Exception {
		List<Goods> goodsList = new ArrayList<Goods>(0);
		List<String> rmKey = new ArrayList<String>(0);
		for(String key : params.keySet()){
			StringBuffer noSynGoods = new StringBuffer();
			StringBuffer isSynAct = new StringBuffer();
			String org = params.get(key);
			goodsList = getGoodsById(key);
			String[] orgIdName = org.split("@");
			String[] orgIds = orgIdName[0].split(",");
			String[] orgNames = orgIdName[1].split(",");
			String actName = orgIdName[2];
			for(int i=0;i<orgIds.length;i++){
				String orgId = orgIds[i];
				int countAct = this.baseDaoSupport.queryForInt(SF.goodsSql("Act_Is_Sync"), key,orgId);
				if(countAct<1){//已经同步成功的活动不需要再同步
					for(Goods good : goodsList){
						String goodId = good.getGoods_id();
						String goodType = good.getType_id();
						//总商跟淘宝,深圳商城,广州微店的商品不需要同步,商品4大类型才需要同步商城....
						if(!"10003".equals(orgId) && !"10001".equals(orgId) && !"10013".equals(orgId) && !"10056".equals(orgId)  && ("20000".equals(goodType) || "20001".equals(goodType) || "20002".equals(goodType) || "20003".equals(goodType))){
							int count = this.baseDaoSupport.queryForInt(SF.goodsSql("Goods_Is_Sync"), goodId,orgIds[i]);
							if(count<1){
								noSynGoods.append("sku="+good.getSku()+"商品没用同步到"+orgNames[i]+"商城,");
							}
						}
					}
				}else{
					isSynAct.append("【"+actName+"】活动已曾经同步成功!,");
				}
			}
			if(!"".equals(noSynGoods.toString())){
				params.put(key, noSynGoods.toString());
			}else if(!"".equals(isSynAct.toString())){
				params.put(key, isSynAct.toString());
			}else{
				rmKey.add(key);
				//params.remove(key);
			}
		}
		if(rmKey.size()>0){
			for(String keys : rmKey){
				params.remove(keys);
			}
		}
		return params;
	}

	@Override
	public List<Goods> getGoodsById(String id) {
		String sql = SF.goodsSql("ACTIVITY_PROMO_GOODS_BY_ID");
		List<Goods> goodsList =  this.baseDaoSupport.queryForList(sql, Goods.class, id);
		return goodsList;
	}

	@Override
	public String publishAct(List<String> ids) throws Exception {
		String activityCoSql = SF.goodsSql("PROMOTION_ACTIVITY_GET_CO_ECS");
		int len = ids.size();
		for(int i=0;i<ids.size();i++){
			String id = ids.get(i);
			List activityCoList = this.baseDaoSupport.queryForList(activityCoSql,id);
			String org_ids = "";
			String org_id = null;
			int status = 0;
			if(activityCoList.size()>0){
				for(int k=0;k<activityCoList.size();k++){
					Map co = (Map) activityCoList.get(k);
					org_id = (String) co.get("org_id");
					status =  (Integer) co.get("status");
					//总商,淘宝,深圳商城,广州微店不需要同步活动,直接改状态---zengxianlian
					if("10003".equals(org_id)||"10001".equals(org_id)||"10013".equals(org_id)||"10056".equals(org_id)){
						editStatusECS(id,Consts.PUBLISH_STATE_1+"",org_id);
					}else{
						if(k>0&&!"".equals(org_ids)){
							org_ids += ",";
						}
						org_ids += org_id;
					}
				}
			}
			if(!"".equals(org_ids)){
				//写同步消息队列es_co_queue
				if(Consts.PUBLISH_STATE_1==status){
					this.writeQueue(id, org_ids,Consts.ACTION_CODE_M);
				}else{
					this.writeQueue(id, org_ids,Consts.ACTION_CODE_A);
				}
				///修改活动发布状态为发布中
				String[] oIds = org_ids.split(",");
				for(String oId : oIds){
					editStatusECS(id,Consts.PUBLISH_STATE_2+"",oId);
				}
			}else{
				len--;
			}
			
		}
		String message = "成功发布了"+len+"条活动！";
		return message;
	}
	
	/**
	 * 检查赠品sku是否存在，及type_id是否为10050、10051、10009
	 * @param cellValue
	 * @param map
	 * @return
	 */
	private boolean validateSkuNew(String cellValue,Map<String,String> map) {
		// TODO 校验赠品SKU
		String[] sku=cellValue.split("\\|");
		String sql="select count(*) from es_goods where sku=?";
		for(int i=0;i<sku.length;i++){
			int count=this.baseDaoSupport.queryForInt(sql,sku[i]);
			if(count==0)
			return false;
			else if(count>0){
				String sqlt="select distinct(type_id) from es_goods where sku=? and type='product'";
				String type_id=this.baseDaoSupport.queryForString(sqlt, sku[i]);
				if(!map.containsKey(type_id)){
					return false;
				}
			}
		}
		return true;
	}


}
