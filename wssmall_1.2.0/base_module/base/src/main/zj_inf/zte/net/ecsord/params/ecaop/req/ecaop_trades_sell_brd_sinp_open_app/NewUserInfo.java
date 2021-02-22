package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app;

import java.util.*;
import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.ActivityInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.InterTvInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.ProductInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.WoExchInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.ExchInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.UserExchInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.BoradDiscntInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.IptvInfo;

public class NewUserInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "使用人性质", type = "String", isNecessary = "N", desc = "使用人性质")
	private String userProperty;

	@ZteSoftCommentAnnotationParam(name = "接入方式（bss），BXX格式", type = "String", isNecessary = "N", desc = "接入方式（bss），BXX格式")
	private String accessMode;

	@ZteSoftCommentAnnotationParam(name = "是否继承账户标示", type = "String", isNecessary = "N", desc = "是否继承账户标示")
	private String createOrExtendsAcct;

	@ZteSoftCommentAnnotationParam(name = "期望施工时间类型：", type = "String", isNecessary = "Y", desc = "期望施工时间类型：")
	private String constructionTime;

	@ZteSoftCommentAnnotationParam(name = "开户时选择的活动信息", type = "ActivityInfo", isNecessary = "N", desc = "开户时选择的活动信息")
	private List<ActivityInfo> activityInfo;

	@ZteSoftCommentAnnotationParam(name = "服务渠道ID", type = "String", isNecessary = "N", desc = "服务渠道ID")
	private String chnlCode;

	@ZteSoftCommentAnnotationParam(name = "地域标识", type = "String", isNecessary = "N", desc = "地域标识")
	private String cityMark;

	@ZteSoftCommentAnnotationParam(name = "互联网电视信息（0-5个）", type = "InterTvInfo", isNecessary = "N", desc = "互联网电视信息（0-5个）")
	private List<InterTvInfo> interTvInfo;

	@ZteSoftCommentAnnotationParam(name = "账户付费方式", type = "String", isNecessary = "N", desc = "账户付费方式")
	private String accountPayType;

	@ZteSoftCommentAnnotationParam(name = "受理方式", type = "String", isNecessary = "N", desc = "受理方式")
	private String acceptMode;

	@ZteSoftCommentAnnotationParam(name = "开户时选择的产品信息", type = "ProductInfo", isNecessary = "N", desc = "开户时选择的产品信息")
	private List<ProductInfo> productInfo;

	@ZteSoftCommentAnnotationParam(name = "证件类型，见附录", type = "String", isNecessary = "Y", desc = "证件类型，见附录")
	private String certType;

	@ZteSoftCommentAnnotationParam(name = "工单属性", type = "WoExchInfo", isNecessary = "N", desc = "工单属性")
	private List<WoExchInfo> woExchInfo;

	@ZteSoftCommentAnnotationParam(name = "网格名称", type = "String", isNecessary = "N", desc = "网格名称")
	private String gridName;

	@ZteSoftCommentAnnotationParam(name = "证件地址", type = "String", isNecessary = "Y", desc = "证件地址")
	private String certAddress;

	@ZteSoftCommentAnnotationParam(name = "端局", type = "String", isNecessary = "N", desc = "端局")
	private String pointExchId;

	@ZteSoftCommentAnnotationParam(name = "标准地址名称", type = "String", isNecessary = "N", desc = "标准地址名称")
	private String addressName;

	@ZteSoftCommentAnnotationParam(name = "网格ID", type = "String", isNecessary = "N", desc = "网格ID")
	private String gridCode;

	@ZteSoftCommentAnnotationParam(name = "iptv产品", type = "String", isNecessary = "N", desc = "iptv产品")
	private String iptvProductId;

	@ZteSoftCommentAnnotationParam(name = "付费模式", type = "String", isNecessary = "N", desc = "付费模式")
	private String payMode;

	@ZteSoftCommentAnnotationParam(name = "区局", type = "String", isNecessary = "N", desc = "区局")
	private String areaExchId;

	@ZteSoftCommentAnnotationParam(name = "互联网电视产品", type = "String", isNecessary = "N", desc = "互联网电视产品")
	private String interTvProductId;

	@ZteSoftCommentAnnotationParam(name = "格式：yyyymmdd", type = "String", isNecessary = "Y", desc = "格式：yyyymmdd")
	private String certExpireDate;

	@ZteSoftCommentAnnotationParam(name = "服务区", type = "String", isNecessary = "N", desc = "服务区")
	private String serviceArea;

	@ZteSoftCommentAnnotationParam(name = "选址信息（要求包含EXCH_CODE）", type = "ExchInfo", isNecessary = "N", desc = "选址信息（要求包含EXCH_CODE）")
	private List<ExchInfo> exchInfo;

	@ZteSoftCommentAnnotationParam(name = "用户属性", type = "UserExchInfo", isNecessary = "N", desc = "用户属性")
	private List<UserExchInfo> userExchInfo;

	@ZteSoftCommentAnnotationParam(name = "局向编码", type = "String", isNecessary = "N", desc = "局向编码")
	private String exchCode;

	@ZteSoftCommentAnnotationParam(name = "证件名称", type = "String", isNecessary = "Y", desc = "证件名称")
	private String certName;

	@ZteSoftCommentAnnotationParam(name = "服务渠道名称", type = "String", isNecessary = "N", desc = "服务渠道名称")
	private String chnlName;

	@ZteSoftCommentAnnotationParam(name = "合帐号码区号", type = "String", isNecessary = "N", desc = "合帐号码区号")
	private String debutyAreaCode;

	@ZteSoftCommentAnnotationParam(name = "入网方式", type = "String", isNecessary = "N", desc = "入网方式")
	private String netMode;

	@ZteSoftCommentAnnotationParam(name = "装机地址", type = "String", isNecessary = "Y", desc = "装机地址")
	private String installAddress;

	@ZteSoftCommentAnnotationParam(name = "合帐号码(继承老帐户必填)", type = "String", isNecessary = "N", desc = "合帐号码(继承老帐户必填)")
	private String debutySerialNumber;

	@ZteSoftCommentAnnotationParam(name = "标准地址编码", type = "String", isNecessary = "N", desc = "标准地址编码")
	private String addressCode;

	@ZteSoftCommentAnnotationParam(name = "yyyymmddhhmmss", type = "String", isNecessary = "N", desc = "yyyymmddhhmmss")
	private String hopeDate;

	@ZteSoftCommentAnnotationParam(name = "速率", type = "String", isNecessary = "N", desc = "速率")
	private String speedLevel;

	@ZteSoftCommentAnnotationParam(name = "模块局", type = "String", isNecessary = "N", desc = "模块局")
	private String moduleExchId;

	@ZteSoftCommentAnnotationParam(name = "开户选择的资费信息", type = "BoradDiscntInfo", isNecessary = "N", desc = "开户选择的资费信息")
	private List<BoradDiscntInfo> boradDiscntInfo;

	@ZteSoftCommentAnnotationParam(name = "用户类型", type = "String", isNecessary = "N", desc = "用户类型")
	private String userType;

	@ZteSoftCommentAnnotationParam(name = "接入方式（cbss），AXX格式", type = "String", isNecessary = "N", desc = "接入方式（cbss），AXX格式")
	private String cbssAccessMode;

	@ZteSoftCommentAnnotationParam(name = "本地网编码", type = "String", isNecessary = "N", desc = "本地网编码")
	private String cityArea;

	@ZteSoftCommentAnnotationParam(name = "合帐号码类型", type = "String", isNecessary = "N", desc = "合帐号码类型")
	private String serviceClassCode;

	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "Y", desc = "证件号码")
	private String certNum;

	@ZteSoftCommentAnnotationParam(name = "iptv信息（0-5个）", type = "IptvInfo", isNecessary = "N", desc = "iptv信息（0-5个）")
	private List<IptvInfo> iptvInfo;

	public String getUserProperty() {
		return this.userProperty;
	}

	public void setUserProperty(String v) {
		this.userProperty = v;
	}

	public String getAccessMode() {
		return this.accessMode;
	}

	public void setAccessMode(String v) {
		this.accessMode = v;
	}

	public String getCreateOrExtendsAcct() {
		return this.createOrExtendsAcct;
	}

	public void setCreateOrExtendsAcct(String v) {
		this.createOrExtendsAcct = v;
	}

	public String getConstructionTime() {
		return this.constructionTime;
	}

	public void setConstructionTime(String v) {
		this.constructionTime = v;
	}

	public List<ActivityInfo> getActivityInfo() {
		return this.activityInfo;
	}

	public void setActivityInfo(List<ActivityInfo> v) {
		this.activityInfo = v;
	}

	public String getChnlCode() {
		return this.chnlCode;
	}

	public void setChnlCode(String v) {
		this.chnlCode = v;
	}

	public String getCityMark() {
		return this.cityMark;
	}

	public void setCityMark(String v) {
		this.cityMark = v;
	}

	public List<InterTvInfo> getInterTvInfo() {
		return this.interTvInfo;
	}

	public void setInterTvInfo(List<InterTvInfo> v) {
		this.interTvInfo = v;
	}

	public String getAccountPayType() {
		return this.accountPayType;
	}

	public void setAccountPayType(String v) {
		this.accountPayType = v;
	}

	public String getAcceptMode() {
		return this.acceptMode;
	}

	public void setAcceptMode(String v) {
		this.acceptMode = v;
	}

	public List<ProductInfo> getProductInfo() {
		return this.productInfo;
	}

	public void setProductInfo(List<ProductInfo> v) {
		this.productInfo = v;
	}

	public String getCertType() {
		return this.certType;
	}

	public void setCertType(String v) {
		this.certType = v;
	}

	public List<WoExchInfo> getWoExchInfo() {
		return this.woExchInfo;
	}

	public void setWoExchInfo(List<WoExchInfo> v) {
		this.woExchInfo = v;
	}

	public String getGridName() {
		return this.gridName;
	}

	public void setGridName(String v) {
		this.gridName = v;
	}

	public String getCertAddress() {
		return this.certAddress;
	}

	public void setCertAddress(String v) {
		this.certAddress = v;
	}

	public String getPointExchId() {
		return this.pointExchId;
	}

	public void setPointExchId(String v) {
		this.pointExchId = v;
	}

	public String getAddressName() {
		return this.addressName;
	}

	public void setAddressName(String v) {
		this.addressName = v;
	}

	public String getGridCode() {
		return this.gridCode;
	}

	public void setGridCode(String v) {
		this.gridCode = v;
	}

	public String getIptvProductId() {
		return this.iptvProductId;
	}

	public void setIptvProductId(String v) {
		this.iptvProductId = v;
	}

	public String getPayMode() {
		return this.payMode;
	}

	public void setPayMode(String v) {
		this.payMode = v;
	}

	public String getAreaExchId() {
		return this.areaExchId;
	}

	public void setAreaExchId(String v) {
		this.areaExchId = v;
	}

	public String getInterTvProductId() {
		return this.interTvProductId;
	}

	public void setInterTvProductId(String v) {
		this.interTvProductId = v;
	}

	public String getCertExpireDate() {
		return this.certExpireDate;
	}

	public void setCertExpireDate(String v) {
		this.certExpireDate = v;
	}

	public String getServiceArea() {
		return this.serviceArea;
	}

	public void setServiceArea(String v) {
		this.serviceArea = v;
	}

	public List<ExchInfo> getExchInfo() {
		return this.exchInfo;
	}

	public void setExchInfo(List<ExchInfo> v) {
		this.exchInfo = v;
	}

	public List<UserExchInfo> getUserExchInfo() {
		return this.userExchInfo;
	}

	public void setUserExchInfo(List<UserExchInfo> v) {
		this.userExchInfo = v;
	}

	public String getExchCode() {
		return this.exchCode;
	}

	public void setExchCode(String v) {
		this.exchCode = v;
	}

	public String getCertName() {
		return this.certName;
	}

	public void setCertName(String v) {
		this.certName = v;
	}

	public String getChnlName() {
		return this.chnlName;
	}

	public void setChnlName(String v) {
		this.chnlName = v;
	}

	public String getDebutyAreaCode() {
		return this.debutyAreaCode;
	}

	public void setDebutyAreaCode(String v) {
		this.debutyAreaCode = v;
	}

	public String getNetMode() {
		return this.netMode;
	}

	public void setNetMode(String v) {
		this.netMode = v;
	}

	public String getInstallAddress() {
		return this.installAddress;
	}

	public void setInstallAddress(String v) {
		this.installAddress = v;
	}

	public String getDebutySerialNumber() {
		return this.debutySerialNumber;
	}

	public void setDebutySerialNumber(String v) {
		this.debutySerialNumber = v;
	}

	public String getAddressCode() {
		return this.addressCode;
	}

	public void setAddressCode(String v) {
		this.addressCode = v;
	}

	public String getHopeDate() {
		return this.hopeDate;
	}

	public void setHopeDate(String v) {
		this.hopeDate = v;
	}

	public String getSpeedLevel() {
		return this.speedLevel;
	}

	public void setSpeedLevel(String v) {
		this.speedLevel = v;
	}

	public String getModuleExchId() {
		return this.moduleExchId;
	}

	public void setModuleExchId(String v) {
		this.moduleExchId = v;
	}

	public List<BoradDiscntInfo> getBoradDiscntInfo() {
		return this.boradDiscntInfo;
	}

	public void setBoradDiscntInfo(List<BoradDiscntInfo> v) {
		this.boradDiscntInfo = v;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String v) {
		this.userType = v;
	}

	public String getCbssAccessMode() {
		return this.cbssAccessMode;
	}

	public void setCbssAccessMode(String v) {
		this.cbssAccessMode = v;
	}

	public String getCityArea() {
		return this.cityArea;
	}

	public void setCityArea(String v) {
		this.cityArea = v;
	}

	public String getServiceClassCode() {
		return this.serviceClassCode;
	}

	public void setServiceClassCode(String v) {
		this.serviceClassCode = v;
	}

	public String getCertNum() {
		return this.certNum;
	}

	public void setCertNum(String v) {
		this.certNum = v;
	}

	public List<IptvInfo> getIptvInfo() {
		return this.iptvInfo;
	}

	public void setIptvInfo(List<IptvInfo> v) {
		this.iptvInfo = v;
	}

}
