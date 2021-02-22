package com.ztesoft.net.mall.core.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.ZteError;
import params.coqueue.req.CoQueueAddReq;
import params.coqueue.resp.CoQueueAddResp;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.iservice.ICoQueueService;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Producto;
import com.ztesoft.net.mall.core.service.IProductoManager;
import com.ztesoft.net.mall.core.utils.GoodsManagerUtils;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;
import commons.CommonTools;

import consts.ConstsCore;
/**
 * Saas式货品业务管理
 * 
 * @author cc
 * 
 */
public class ProductoManager extends BaseSupport<Producto> implements IProductoManager {
	
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private ICoQueueService coQueueService;
	
	@Override
	public Page getFormList(int pageNo, int pageSize, Map<String, String> params) {

		StringBuilder temp = new StringBuilder(
				SF.productoSql("PRODUCTO_LIST"));
		String sql = wrapsql(temp, params);
		return baseDaoSupport.queryForPage(sql, pageNo, pageSize);
	}
	
	private String wrapsql(StringBuilder temp, Map<String, String> params) {
		String pid = params.get("productoId");
		if(StringUtils.isNotBlank(pid)){
			temp.append(" and pc.product_id ='"+pid+"'");
		}
		
		String source = ManagerUtils.getSourceFrom();
		temp.append(" and pc.source_from = '"+source+"'");
		
		return temp.toString();
	}

	/**
	 * 货品同步
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void liberacion(String org_ids, Map params) {
		
		//需要同步的货品串2323,2323,32232
		String product_id_str = (String)params.get("productos");
		
        if (StringUtils.isEmpty(product_id_str)) {
        	CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "需要同步的货品不能为空！"));
		}
        
        //需要同步的销售组织333,444,666
        if (StringUtils.isEmpty(org_ids) ) {
        	CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "销售组织不能为空！"));
		}
        
        //获取并过滤要同步的销售组织（新商城的）
      	String sql = SF.goodsSql("GOODS_ORG_ID");
      	sql += " and a.source_from = '" + ManagerUtils.getSourceFrom() + "'"
      		 + " and a.org_level = 3 and a.party_id in("+ org_ids +")";
      	//获取过滤新商城，淘宝之外的组织
      	String sql2=SF.goodsSql("GOODS_ORG_ID")+" and a.source_from = '" + ManagerUtils.getSourceFrom() + "'"
      		 + " and a.org_level = 2 and a.party_id in("+ org_ids +") and a.party_id not in(10008,10001,100082)" ;
      	List<String> goodsOrgLst = this.jdbcTemplate.queryForList(sql, String.class);
      	String org_id_str = GoodsManagerUtils.convertStrFromLst(goodsOrgLst, ",");
        
      	List<String> goodsOrgLst2 = this.jdbcTemplate.queryForList(sql2, String.class);
      	String org_id_str2 = GoodsManagerUtils.convertStrFromLst(goodsOrgLst2, ",");
      	
      	List<String> goodsOrgLst3=dispite(org_id_str);
      	
      	 //获取批次号
		//String batch_id = this.daoSupport.getSequences("S_ES_CO_BATCH_ID");
    	String batch_id = "";
    	
		String[] product_id_arr = product_id_str.split(",");  //货品标识串
		String[] org_id_arr = org_ids.split(",");  //销售组织标识串
		
		
		
		//写同步状态表es_product_co
		for (int i = 0; i < product_id_arr.length; i++) {
			
			String product_id = product_id_arr[i]; //货品标识
//			for (int j = 0; j < goodsOrgLst.size(); j++) {
//				String org_id = goodsOrgLst.get(j);  //销售组织标识				
//				this.doSaveCo(product_id, org_id, batch_id,String.valueOf(Consts.PUBLISH_STATE_2));
//			}			
			//写队列
			for(int q=0;q<goodsOrgLst3.size();q++){
				String org = goodsOrgLst3.get(q);
				//注释by zengixnalian 保持batch_id跟写product_co一致
				batch_id = this.daoSupport.getSequences("S_ES_CO_BATCH_ID");
				if(!"10001".equals(org)&&!"10003".equals(org)){
					this.doSaveCoQueue(batch_id, product_id, product_id,org);
				}
				String orgs[] = org.split(",");
				//写goods_co
				for(String id : orgs){
					if(!"10001".equals(org)&&!"10003".equals(org)){
						this.doSaveCo(product_id, id, batch_id,String.valueOf(Consts.PUBLISH_STATE_2));
					}
				}
			}

			List<Map> relations = CommonDataFactory.getInstance().getDictRelationListData(StypeConsts.ISTBORDER);
			if(relations!=null && relations.size()>0){
				for(Map relation : relations){
					String order_from = Const.getStrValue(relation, "other_field_value");
					if(org_ids.contains(order_from)){//淘宝的写队列
						batch_id = this.daoSupport.getSequences("S_ES_CO_BATCH_ID");
						//淘宝就改下状态......根本5需要同步比距
						this.doSaveCo(product_id, order_from, batch_id,String.valueOf(Consts.PUBLISH_STATE_1));
					}
				}
			}
//			if(org_ids.contains("10001")){//淘宝的写队列
//				batch_id = this.daoSupport.getSequences("S_ES_CO_BATCH_ID");
//				this.doSaveCo(product_id, "10001", batch_id);
//			}
			
			for(int k=0;k<goodsOrgLst2.size();k++){
				batch_id = this.daoSupport.getSequences("S_ES_CO_BATCH_ID");
				String org_id2=goodsOrgLst2.get(k);
				if(!"10003".equals(org_id2)){
					this.doSaveCo(product_id, org_id2, batch_id,String.valueOf(Consts.PUBLISH_STATE_2));
					this.doSaveCoQueueLv2(batch_id, product_id, product_id, org_id2);//写level2的队列
				}else{
					this.doSaveCo(product_id, org_id2, batch_id,String.valueOf(Consts.PUBLISH_STATE_1));
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
	
	private void doSaveCoQueueLv2(String batch_id, String object_id, String contents,String org_id) {
		// TODO Auto-generated method stub
		if(!StringUtils.isEmpty(org_id)){
			//写消息队列
			CoQueueAddReq coQueueAddReq = new CoQueueAddReq();
			coQueueAddReq.setCo_name("货品同步");
			coQueueAddReq.setBatch_id(batch_id);
			coQueueAddReq.setService_code(Consts.SERVICE_CODE_CO_HUOPIN);
			coQueueAddReq.setAction_code(Consts.ACTION_CODE_A);
			coQueueAddReq.setObject_id(object_id);
			coQueueAddReq.setObject_type("HUOPIN");
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
			coQueueAddReq.setCo_name("货品同步");
			coQueueAddReq.setBatch_id(batch_id);
			coQueueAddReq.setService_code(Consts.SERVICE_CODE_CO_HUOPIN);
			coQueueAddReq.setAction_code(Consts.ACTION_CODE_A);
			coQueueAddReq.setObject_id(object_id);
			coQueueAddReq.setObject_type("HUOPIN");
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
	 * @param product_id  货品标识
	 * @param org_id  组织标识
	 * @param batch_id  批次号
	 */
	private void doSaveCo(String product_id, String org_id, String batch_id) {
		
		String sql = "select count(0) from ES_PRODUCT_CO where product_id = ? and org_id = ?";
		int count = baseDaoSupport.queryForInt(sql, product_id, org_id);
		if (count < 1) {
			Producto eg = new Producto();
			eg.setProduct_id(product_id);
			eg.setOrg_id(org_id);
			eg.setBatch_id(batch_id);
			eg.setCreated_date(DBTUtil.getDBCurrentTime());
			eg.setStatus(String.valueOf(Consts.PUBLISH_STATE_1));
			eg.setOper_id(ManagerUtils.getAdminUser().getUserid());
			eg.setSource_from(ManagerUtils.getSourceFrom());
			
			this.baseDaoSupport.insert("ES_PRODUCT_CO", eg);
		}
	}
	
	/**
	 * 写同步状态表
	 * @author zengxianlian
	 * @param product_id  货品标识
	 * @param org_id  组织标识
	 * @param batch_id  批次号
	 * @param status 初始状态
	 */
	private void doSaveCo(String product_id, String org_id, String batch_id,String status) {
		
		String sql = "select count(0) from ES_PRODUCT_CO where product_id = ? and org_id = ?";
		int count = baseDaoSupport.queryForInt(sql, product_id, org_id);
		if (count < 1) {
			Producto eg = new Producto();
			eg.setProduct_id(product_id);
			eg.setOrg_id(org_id);
			eg.setBatch_id(batch_id);
			eg.setCreated_date(DBTUtil.getDBCurrentTime());
			//eg.setStatus(String.valueOf(Consts.PUBLISH_STATE_1));
			eg.setStatus(status);
			eg.setOper_id(ManagerUtils.getAdminUser().getUserid());
			eg.setSource_from(ManagerUtils.getSourceFrom());
			
			this.baseDaoSupport.insert("ES_PRODUCT_CO", eg);
		}else{//同步更新批次号,为co_queue保持一致---zengxianlian
			String s = "update ES_PRODUCT_CO set batch_id=? where product_id = ? and org_id = ?";
			this.baseDaoSupport.execute(s, batch_id,product_id,org_id);
		}
	}


	@Override
	public List getEstados() {
		String sql = SF.productoSql("PRODUCTO_ESTADOS");
		return baseDaoSupport.queryForList(sql);
	}

	public ICoQueueService getCoQueueService() {
		return coQueueService;
	}

	public void setCoQueueService(ICoQueueService coQueueService) {
		this.coQueueService = coQueueService;
	}

}