package com.ztesoft.net.app.base.core.action;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;

import com.ztesoft.net.app.base.core.model.RoleData;
import com.ztesoft.net.app.base.core.service.IRoleAuthManager;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.common.util.JsonUtil;

public class RoleAuthAction extends WWAction{
	//静态页面begin	
	//授权环节
	private List nodeList ;
	//授权地市
	private List regionList ;	
	//授权来源
	private List originList ;
	//授权子来源
	private List sonOrderTypeList ;
	//授权特殊商品类型
	private List specialprodTypeList ;
	//授权锁定状态
	private List lockStatusList ;
	//授权配送方式
	private List carryModeList ;
	//是否正常单
	private List normalFlagList ;
	//授权预约单
	private List bespokeFlagList ;
	//授权社会机
	private List societyFlagList ;
	//授权产品类型
	private List productSubTypeList ;
	//授权支付类型
	private List payModeList ;
	//授权处理类型
	private List handleTypeList ;
	//授权合约类型
	private List productBusiTypeList ;
	//授权归属渠道
	private List developAttributeList ;
	//授权商品品牌
	private List productBrandList ;
	//授权生产模式
	private List order_model_list ;
	//异常单归类
	private List orderexp_sprc_catalog;
	//订单领取工号
	private List receiveOrderUsers;
	//订单锁定工号
	private List lockOrderUsers;
	//订单区县
	private List orderBusicty;
	//静态页面end
	
	
	//es_role_data[id]
	private String actid;
	private RoleData roleData;
	private String roleDataJSONStr;
	
	private IRoleAuthManager roleAuthManager;
	
	public String roleAuthList(){
		this.nodeList = CommonDataFactory.getInstance().listDcPublicData("DIC_ORDER_NODE");
		this.regionList = CommonDataFactory.getInstance().listDcPublicData("BSS_ACCOUNT_OPERATOR");//DIC_ZONE_REGION_CD
		this.originList = CommonDataFactory.getInstance().getDictRelationListData(StypeConsts.DIC_ORDER_ORIGIN);
//		this.sonOrderTypeList = CommonDataFactory.getInstance().listDcPublicData("DIC_SOURCE_FROM_SYSTEM");
		this.specialprodTypeList = CommonDataFactory.getInstance().listDcPublicData("SPECIAL_PROD_TYPE");
		this.lockStatusList = CommonDataFactory.getInstance().listDcPublicData("LOCK_STATUS");
		this.carryModeList = CommonDataFactory.getInstance().listDcPublicData("shipping_type");
		this.normalFlagList = CommonDataFactory.getInstance().listDcPublicData("abnormal_status");
		this.bespokeFlagList = CommonDataFactory.getInstance().listDcPublicData("DIC_BESPOKE_FLAG");
		this.societyFlagList = CommonDataFactory.getInstance().listDcPublicData("DIC_CUSTOMIZATION");
		this.productSubTypeList = CommonDataFactory.getInstance().getDictRelationListData(StypeConsts.PACK_TYPE);
		this.payModeList = CommonDataFactory.getInstance().listDcPublicData(StypeConsts.PAY_TYPE);
		this.handleTypeList = CommonDataFactory.getInstance().listDcPublicData("shipping_quick");
		this.productBusiTypeList = CommonDataFactory.getInstance().listDropDownData("DC_PROD_BUSI_TYPE");
		this.developAttributeList = CommonDataFactory.getInstance().listDcPublicData("channel_mark");
		this.productBrandList = CommonDataFactory.getInstance().listDropDownData("DC_BRAND");
		this.order_model_list = CommonDataFactory.getInstance().listDcPublicData(StypeConsts.DIC_OPER_MODE);
		this.orderexp_sprc_catalog = CommonDataFactory.getInstance().getOrderExpCatalogs("ORDEREXP_SPEC_CATALOG");
		this.receiveOrderUsers = this.roleAuthManager.getOrdReceiveUser();
		this.lockOrderUsers = this.roleAuthManager.getLockOrderUser();
		this.orderBusicty = this.roleAuthManager.getDcSqlByDcName("DC_ORDER_BUSICTY");
		if(!StringUtils.isEmpty(actid)){//138
			HashMap param = new HashMap();
			param.put("id", actid);
			roleData = this.roleAuthManager.getRoleData(param);
			roleDataJSONStr = JsonUtil.toJson(roleData);
			roleDataJSONStr = roleDataJSONStr.replace("\"", "'");
		}
		return "list";
	}

	public List getNodeList() {
		return nodeList;
	}

	public void setNodeList(List nodeList) {
		this.nodeList = nodeList;
	}

	public List getRegionList() {
		return regionList;
	}

	public void setRegionList(List regionList) {
		this.regionList = regionList;
	}

	public List getOriginList() {
		return originList;
	}

	public void setOriginList(List originList) {
		this.originList = originList;
	}

	public List getSonOrderTypeList() {
		return sonOrderTypeList;
	}

	public void setSonOrderTypeList(List sonOrderTypeList) {
		this.sonOrderTypeList = sonOrderTypeList;
	}

	public List getSpecialprodTypeList() {
		return specialprodTypeList;
	}

	public void setSpecialprodTypeList(List specialprodTypeList) {
		this.specialprodTypeList = specialprodTypeList;
	}

	public List getLockStatusList() {
		return lockStatusList;
	}

	public void setLockStatusList(List lockStatusList) {
		this.lockStatusList = lockStatusList;
	}

	public List getCarryModeList() {
		return carryModeList;
	}

	public void setCarryModeList(List carryModeList) {
		this.carryModeList = carryModeList;
	}

	public List getNormalFlagList() {
		return normalFlagList;
	}

	public void setNormalFlagList(List normalFlagList) {
		this.normalFlagList = normalFlagList;
	}

	public List getBespokeFlagList() {
		return bespokeFlagList;
	}

	public void setBespokeFlagList(List bespokeFlagList) {
		this.bespokeFlagList = bespokeFlagList;
	}

	public List getSocietyFlagList() {
		return societyFlagList;
	}

	public void setSocietyFlagList(List societyFlagList) {
		this.societyFlagList = societyFlagList;
	}

	public List getProductSubTypeList() {
		return productSubTypeList;
	}

	public void setProductSubTypeList(List productSubTypeList) {
		this.productSubTypeList = productSubTypeList;
	}

	public List getPayModeList() {
		return payModeList;
	}

	public void setPayModeList(List payModeList) {
		this.payModeList = payModeList;
	}

	public List getHandleTypeList() {
		return handleTypeList;
	}

	public void setHandleTypeList(List handleTypeList) {
		this.handleTypeList = handleTypeList;
	}

	public List getProductBusiTypeList() {
		return productBusiTypeList;
	}

	public void setProductBusiTypeList(List productBusiTypeList) {
		this.productBusiTypeList = productBusiTypeList;
	}

	public List getDevelopAttributeList() {
		return developAttributeList;
	}

	public void setDevelopAttributeList(List developAttributeList) {
		this.developAttributeList = developAttributeList;
	}

	public List getProductBrandList() {
		return productBrandList;
	}

	public void setProductBrandList(List productBrandList) {
		this.productBrandList = productBrandList;
	}

	public IRoleAuthManager getRoleAuthManager() {
		return roleAuthManager;
	}

	public void setRoleAuthManager(IRoleAuthManager roleAuthManager) {
		this.roleAuthManager = roleAuthManager;
	}

	public String getActid() {
		return actid;
	}

	public void setActid(String actid) {
		this.actid = actid;
	}

	public RoleData getRoleData() {
		return roleData;
	}

	public void setRoleData(RoleData roleData) {
		this.roleData = roleData;
	}

	public String getRoleDataJSONStr() {
		return roleDataJSONStr;
	}

	public void setRoleDataJSONStr(String roleDataJSONStr) {
		this.roleDataJSONStr = roleDataJSONStr;
	}

	public List getOrder_model_list() {
		return order_model_list;
	}

	public void setOrder_model_list(List order_model_list) {
		this.order_model_list = order_model_list;
	}

	public List getOrderexp_sprc_catalog() {
		return orderexp_sprc_catalog;
	}

	public void setOrderexp_sprc_catalog(List orderexp_sprc_catalog) {
		this.orderexp_sprc_catalog = orderexp_sprc_catalog;
	}

	public List getReceiveOrderUsers() {
		return receiveOrderUsers;
	}

	public void setReceiveOrderUsers(List receiveOrderUsers) {
		this.receiveOrderUsers = receiveOrderUsers;
	}

	public List getLockOrderUsers() {
		return lockOrderUsers;
	}

	public void setLockOrderUsers(List lockOrderUsers) {
		this.lockOrderUsers = lockOrderUsers;
	}

	public List getOrderBusicty() {
		return orderBusicty;
	}

	public void setOrderBusicty(List orderBusicty) {
		this.orderBusicty = orderBusicty;
	}
	
}
