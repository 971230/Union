package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app;

import java.util.*;
import java.io.Serializable;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.Para;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.MachineInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.BindInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.NewUserInfo;

public class AopBrdOpenAppReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name = "宽带账户名称", type = "String", isNecessary = "N", desc = "宽带账户名称")
	private String userName;

	@ZteSoftCommentAnnotationParam(name = "省分", type = "String", isNecessary = "Y", desc = "省分")
	private String province;

	@ZteSoftCommentAnnotationParam(name = "发展人名称", type = "String", isNecessary = "N", desc = "发展人名称")
	private String recomPersonName;

	@ZteSoftCommentAnnotationParam(name = "宽带认证账号", type = "String", isNecessary = "Y", desc = "宽带认证账号")
	private String authAcctId;

	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "地市")
	private String city;

	@ZteSoftCommentAnnotationParam(name = "客户标识，老客户传", type = "String", isNecessary = "N", desc = "客户标识，老客户传")
	private String custId;

	@ZteSoftCommentAnnotationParam(name = "发展人地市", type = "String", isNecessary = "N", desc = "发展人地市")
	private String recomPersonCityCode;

	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "订单编号")
	private String orderNo;

	@ZteSoftCommentAnnotationParam(name = "0：不共线，", type = "String", isNecessary = "N", desc = "0：不共线，")
	private String shareFalg;

	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "操作员ID")
	private String operatorId;

	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "Y", desc = "区县")
	private String district;

	@ZteSoftCommentAnnotationParam(name = "宽带密码", type = "String", isNecessary = "N", desc = "宽带密码")
	private String userPasswd;

	@ZteSoftCommentAnnotationParam(name = "联系人（不能全数字）", type = "String", isNecessary = "Y", desc = "联系人（不能全数字）")
	private String contactPerson;

	@ZteSoftCommentAnnotationParam(name = "新老客户标识", type = "String", isNecessary = "N", desc = "新老客户标识")
	private String custType;

	@ZteSoftCommentAnnotationParam(name = "发展人县市", type = "String", isNecessary = "N", desc = "发展人县市")
	private String recomPersonDistrict;

	@ZteSoftCommentAnnotationParam(name = "共线号码区号", type = "String", isNecessary = "N", desc = "共线号码区号")
	private String shareAreaCode;

	@ZteSoftCommentAnnotationParam(name = "是否行销装备，0：否，1：是", type = "String", isNecessary = "N", desc = "是否行销装备，0：否，1：是")
	private String markingTag;

	@ZteSoftCommentAnnotationParam(name = "发展人渠道", type = "String", isNecessary = "N", desc = "发展人渠道")
	private String recomPersonChannelId;

	@ZteSoftCommentAnnotationParam(name = "扣款标示", type = "String", isNecessary = "N", desc = "扣款标示")
	private String deductionTag;

	@ZteSoftCommentAnnotationParam(name = "办理业务系统：", type = "String", isNecessary = "N", desc = "办理业务系统：")
	private String opeSysType;

	@ZteSoftCommentAnnotationParam(name = "联系人电话>6位", type = "String", isNecessary = "Y", desc = "联系人电话>6位")
	private String contactPhone;

	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "Para", isNecessary = "N", desc = "保留字段")
	private List<Para> para;

	@ZteSoftCommentAnnotationParam(name = "通讯地址", type = "String", isNecessary = "N", desc = "通讯地址")
	private String contactAddress;

	@ZteSoftCommentAnnotationParam(name = "终端信息", type = "MachineInfo", isNecessary = "N", desc = "终端信息")
	private List<MachineInfo> machineInfo;

	@ZteSoftCommentAnnotationParam(name = "冰淇淋融合套餐绑定信息", type = "BindInfo", isNecessary = "N", desc = "冰淇淋融合套餐绑定信息")
	private List<BindInfo> bindInfo;

	@ZteSoftCommentAnnotationParam(name = "宽带统一编码", type = "String", isNecessary = "Y", desc = "宽带统一编码")
	private String serialNumber;

	@ZteSoftCommentAnnotationParam(name = "销售模式类型", type = "String", isNecessary = "N", desc = "销售模式类型")
	private String saleModType;

	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "渠道类型")
	private String channelType;

	@ZteSoftCommentAnnotationParam(name = "共线固话号码，当ShareFalg为1时必须填", type = "String", isNecessary = "N", desc = "共线固话号码，当ShareFalg为1时必须填")
	private String shareSerialNumber;

	@ZteSoftCommentAnnotationParam(name = "订单备注信息", type = "String", isNecessary = "N", desc = "订单备注信息")
	private String orderRemarks;

	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "渠道编码")
	private String channelId;

	@ZteSoftCommentAnnotationParam(name = "客户资料", type = "NewUserInfo", isNecessary = "N", desc = "客户资料")
	private NewUserInfo newUserInfo;

	@ZteSoftCommentAnnotationParam(name = "发展人标识", type = "String", isNecessary = "N", desc = "发展人标识")
	private String recomPersonId;

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String v) {
		this.userName = v;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String v) {
		this.province = v;
	}

	public String getRecomPersonName() {
		return this.recomPersonName;
	}

	public void setRecomPersonName(String v) {
		this.recomPersonName = v;
	}

	public String getAuthAcctId() {
		return this.authAcctId;
	}

	public void setAuthAcctId(String v) {
		this.authAcctId = v;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String v) {
		this.city = v;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String v) {
		this.custId = v;
	}

	public String getRecomPersonCityCode() {
		return this.recomPersonCityCode;
	}

	public void setRecomPersonCityCode(String v) {
		this.recomPersonCityCode = v;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String v) {
		this.orderNo = v;
	}

	public String getShareFalg() {
		return this.shareFalg;
	}

	public void setShareFalg(String v) {
		this.shareFalg = v;
	}

	public String getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(String v) {
		this.operatorId = v;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String v) {
		this.district = v;
	}

	public String getUserPasswd() {
		return this.userPasswd;
	}

	public void setUserPasswd(String v) {
		this.userPasswd = v;
	}

	public String getContactPerson() {
		return this.contactPerson;
	}

	public void setContactPerson(String v) {
		this.contactPerson = v;
	}

	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String v) {
		this.custType = v;
	}

	public String getRecomPersonDistrict() {
		return this.recomPersonDistrict;
	}

	public void setRecomPersonDistrict(String v) {
		this.recomPersonDistrict = v;
	}

	public String getShareAreaCode() {
		return this.shareAreaCode;
	}

	public void setShareAreaCode(String v) {
		this.shareAreaCode = v;
	}

	public String getMarkingTag() {
		return this.markingTag;
	}

	public void setMarkingTag(String v) {
		this.markingTag = v;
	}

	public String getRecomPersonChannelId() {
		return this.recomPersonChannelId;
	}

	public void setRecomPersonChannelId(String v) {
		this.recomPersonChannelId = v;
	}

	public String getDeductionTag() {
		return this.deductionTag;
	}

	public void setDeductionTag(String v) {
		this.deductionTag = v;
	}

	public String getOpeSysType() {
		return this.opeSysType;
	}

	public void setOpeSysType(String v) {
		this.opeSysType = v;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String v) {
		this.contactPhone = v;
	}

	public List<Para> getPara() {
		return this.para;
	}

	public void setPara(List<Para> v) {
		this.para = v;
	}

	public String getContactAddress() {
		return this.contactAddress;
	}

	public void setContactAddress(String v) {
		this.contactAddress = v;
	}

	public List<MachineInfo> getMachineInfo() {
		return this.machineInfo;
	}

	public void setMachineInfo(List<MachineInfo> v) {
		this.machineInfo = v;
	}

	public List<BindInfo> getBindInfo() {
		return this.bindInfo;
	}

	public void setBindInfo(List<BindInfo> v) {
		this.bindInfo = v;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String v) {
		this.serialNumber = v;
	}

	public String getSaleModType() {
		return this.saleModType;
	}

	public void setSaleModType(String v) {
		this.saleModType = v;
	}

	public String getChannelType() {
		return this.channelType;
	}

	public void setChannelType(String v) {
		this.channelType = v;
	}

	public String getShareSerialNumber() {
		return this.shareSerialNumber;
	}

	public void setShareSerialNumber(String v) {
		this.shareSerialNumber = v;
	}

	public String getOrderRemarks() {
		return this.orderRemarks;
	}

	public void setOrderRemarks(String v) {
		this.orderRemarks = v;
	}

	public String getChannelId() {
		return this.channelId;
	}

	public void setChannelId(String v) {
		this.channelId = v;
	}

	public NewUserInfo getNewUserInfo() {
		return this.newUserInfo;
	}

	public void setNewUserInfo(NewUserInfo v) {
		this.newUserInfo = v;
	}

	public String getRecomPersonId() {
		return this.recomPersonId;
	}

	public void setRecomPersonId(String v) {
		this.recomPersonId = v;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "ecaop.trades.sell.brd.sinp.open.app_aop";
	}

}
