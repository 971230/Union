package services;

import params.suppler.req.AgentTodayNewReq;
import params.suppler.req.AgentWaitAuditReq;
import params.suppler.req.AgentYestodayNewReq;
import params.suppler.req.SuppCooperateReq;
import params.suppler.req.SuppEndReq;
import params.suppler.req.SuppTodayNewReq;
import params.suppler.req.SuppWaitAuditReq;
import params.suppler.req.SuppYestodayNewReq;
import params.suppler.req.SupplierExamReq;
import params.suppler.req.SupplierListReq;
import params.suppler.req.SupplierObjReq;
import params.suppler.req.SuppliersListReq;
import params.suppler.req.SuppliersReq;
import params.suppler.resp.SupplierCountResp;
import params.suppler.resp.SupplierExamResp;
import params.suppler.resp.SupplierListResp;
import params.suppler.resp.SupplierObjResp;
import params.suppler.resp.SupplierResp;
import params.suppler.resp.SuppliersListResp;

/**
 * 供货商service
 * 
 * @作者 wu.i
 * @创建日期 2013-9-23
 * @版本 V 1.0
 *	
 * 举例
 * TagReq tagReq = new TagReq("50");
   String json = CommonTools.beanToJson(tagReq);
   String str = HttpUtil.readPostAsString(URL+"TagsServ/queryAllTagsGoods", "param_json="+json, "utf-8", "utf-8", 100000, 100000);
   TagResp tagResp = CommonTools.jsonToBean(str, TagResp.class);
 */
public interface SupplierInf {
	/**
	 * 查询标签信息所有商品
	 * @param json
	 */
	public SupplierResp querySuppers(SuppliersReq supplersReq);
	
	
	/**
	 * 查询供货商列表
	 * @param supplerReq
	 * @return
	 */
	public SuppliersListResp supplierList(SuppliersListReq suppliersListReq);
	
	
	/**
	 * 根据id查询供货商实体
	 * @param supplierObjReq
	 * @return
	 */
	public SupplierObjResp findSupplierById(SupplierObjReq supplierObjReq);
	
	
	/**
	 * 返回分页查询对象
	 * @param supplierReq
	 * @return
	 */
	public SupplierExamResp findExamineSupplier(SupplierExamReq supplierExamReq);
	
	
	/**
	 * 查询供货商列表
	 * @param supplierListReq
	 * @return
	 */
	public SupplierListResp getSupplierByCatId(SupplierListReq supplierListReq);
	
	
	/**
	 * 查询待审核供货商
	 * @return
	 */
	public SupplierCountResp waitAuditSupplier(SuppWaitAuditReq suppWaitAuditReq);
	

	/**
	 * 查询待审核经销商
	 * @return
	 */
	public SupplierCountResp waitAuditAgency(AgentWaitAuditReq agentWaitAuditReq);
	
	
	/**
	 * 查询合作中供货商
	 * @return
	 */
	public SupplierCountResp cooperationSupplier(SuppCooperateReq suppCooperateReq);
	
	
	/**
	 * 查询今已终止供货商
	 * @return
	 */
	public SupplierCountResp endSupplier(SuppEndReq suppEndReq);
	
	
	/**
	 * 查询今日新增供货商
	 * @return
	 */
	public SupplierCountResp todayNewSupplier(SuppTodayNewReq suppTodayNewReq);
	
	
	/**
	 * 查询昨日新增供货商
	 * @return
	 */
	public SupplierCountResp yestodayNewSupplier(SuppYestodayNewReq suppYestodayNewReq);
	
	
	/**
	 * 查询今日新增经销商
	 * @return
	 */
	public SupplierCountResp todayNewAgency(AgentTodayNewReq agentTodayNewReq);
	
	
	/**
	 * 查询昨日新增经销商
	 * @return
	 */
	public SupplierCountResp yestodayNewAgency(AgentYestodayNewReq agentYestodayNewReq);
	
}