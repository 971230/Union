package zte.net.ecsord.params.bss.req;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.bss.resp.OrderFinAccountSyncResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;


public class OrderFinAccountSyncReq extends ZteRequest<OrderFinAccountSyncResp>{

	@ZteSoftCommentAnnotationParam(name="用户服务号码",type="String",isNecessary="Y",desc="用户服务号码")
	private String SERIAL_NUMBER;
	
	@ZteSoftCommentAnnotationParam(name="订单编号（订单系统订单号）",type="String",isNecessary="Y",desc="用户服务号码")
	private String ORDER_NO;
	
	@ZteSoftCommentAnnotationParam(name="订单类型【订单类别】",type="String",isNecessary="Y",desc="01:联通合约机 02:3G号卡 03:定制机裸机 04:社会机合约机 05:社会机裸机 06:2G号卡 07:老用户 08:上网卡【不含卡体】09:上网卡【含卡体】10:增值业务")
	private String ORDER_TYPE;
	
	@ZteSoftCommentAnnotationParam(name="ESS订单编号",type="String",isNecessary="Y",desc="ESS订单编号")
	private String ESS_ORDER_NO;
	
	@ZteSoftCommentAnnotationParam(name="终端串号",type="String",isNecessary="Y",desc="终端串号")
	private String IMEI;
	
	@ZteSoftCommentAnnotationParam(name="受理时间",type="String",isNecessary="Y",desc="受理时间")
	private String ACCEPT_DATE;
	
	@ZteSoftCommentAnnotationParam(name="订单金额(实收)单位元",type="String",isNecessary="Y",desc="订单金额(实收)单位元")
	private String REAL_MONEY;
	
	@ZteSoftCommentAnnotationParam(name="促销政策地",type="String",isNecessary="Y",desc="01:集团 02:省公司 03:地市公司")
	private String POLICY;
	
	@ZteSoftCommentAnnotationParam(name="订单来源",type="String",isNecessary="Y",desc="ZBSC:总部商城 GDTB:广东淘宝商城 GDPPSC:广东拍拍商城 GDPPWT:广东拍拍网厅 GDWO:广东沃商城 LCCTSC:地市本地商城 ZBTB:总部淘宝 WAPSC:WAP商城 YYTXT:营业厅协同 JKXT:集客协同 SHXT:社会渠道协同 QT:其他来源的订单")
	private String ORDER_ORIGIN;
	
	@ZteSoftCommentAnnotationParam(name="销售模式",type="String",isNecessary="Y",desc="5:购机送费 4:存费送机 3:存费送费 6:无")
	private String ACTIVITY_TYPE;
	
	@ZteSoftCommentAnnotationParam(name="原始订单号(商城前端订单号)",type="String",isNecessary="Y",desc="原始订单号(商城前端订单号)")
	private String ORIGIN_ORDER_NO;
	
	@ZteSoftCommentAnnotationParam(name="终端型号",type="String",isNecessary="Y",desc="终端型号")
	private String TERMINAL_TYPE;
	
	@ZteSoftCommentAnnotationParam(name="支付方式",type="String",isNecessary="Y",desc="01:在线支付 02:货到付款")
	private String PAY_MODE;
	
	@ZteSoftCommentAnnotationParam(name="第三方支付机构",type="String",isNecessary="Y",desc="01:财付通 02:支付宝 06:银联商务_总部MINI 07:沃支付 08:快钱 09:银联支付 10:北京通融通（易宝）")
	private String PAY_MECHANISM;
	
	@ZteSoftCommentAnnotationParam(name="物流单号",type="String",isNecessary="Y",desc="物流单号")
	private String LOGISTICS_NO;
	
	@ZteSoftCommentAnnotationParam(name="配送方式",type="String",isNecessary="Y",desc="08:物流发货 06:自提")
	private String DELIVERY_MODE;
	
	@ZteSoftCommentAnnotationParam(name="用户标识",type="String",isNecessary="Y",desc="01:新用户 02:老用户")
	private String USER_IDENTIFICATION;
	
	@ZteSoftCommentAnnotationParam(name="订单创建时间",type="String",isNecessary="Y",desc="yyyyMMdd hh:mm:ss")
	private String CREATE_TIME;
	
	@ZteSoftCommentAnnotationParam(name="订单支付时间",type="String",isNecessary="Y",desc="yyyyMMdd hh:mm:ss")
	private String PAY_TIME;
	
	@ZteSoftCommentAnnotationParam(name="订单应收金额",type="String",isNecessary="Y",desc="应收金额，单位元")
	private String ACCOUNT_RECEIVABLE;
	
	@ZteSoftCommentAnnotationParam(name="订单状态",type="String",isNecessary="Y",desc="1:正常 -1:退款")
	private String ORDER_STATUS;
	
	@ZteSoftCommentAnnotationParam(name="赠品",type="String",isNecessary="Y",desc="赠品")
	private String GIFTS;
	
	@ZteSoftCommentAnnotationParam(name="社会代理商名称",type="String",isNecessary="Y",desc="社会代理商名称")
	private String AGENT_NAME;
	
	@ZteSoftCommentAnnotationParam(name="代理商终端结算价格",type="String",isNecessary="Y",desc="代理商终端结算价格")
	private String AGENT_TERMINAL_PRICE;
	
	@ZteSoftCommentAnnotationParam(name="代收邮费",type="String",isNecessary="Y",desc="代收邮费")
	private String POSTAGE;
	
	@ZteSoftCommentAnnotationParam(name="代金券面值",type="String",isNecessary="Y",desc="代金券面值")
	private String VOUCHER_VALUE;
	
	@ZteSoftCommentAnnotationParam(name="优惠类型",type="String",isNecessary="Y",desc="01:代金券 02:积分 03:现金")
	private String PREFERENTIAL_TYPE;
	
	@ZteSoftCommentAnnotationParam(name="优惠券编号",type="String",isNecessary="Y",desc="优惠券编号")
	private String PREFERENTIAL_NO;
	
	@ZteSoftCommentAnnotationParam(name="优惠金额",type="String",isNecessary="Y",desc="单位元")
	private String DISCOUNT_AMOUNT;
	
	@ZteSoftCommentAnnotationParam(name="订单归属地市",type="String",isNecessary="Y",desc="0010:省公司 0099:其它 0020:广州 0755:深圳 0756:珠海 0754:汕头 0751:韶关 0762:河源 0753:梅州 0752:惠州 0660:汕尾 0769:东莞 0760:中山 0750:江门 0757:佛山 0662:阳江 0759:湛江 0668:茂名 0758:肇庆 0763:清远 0768:潮州 0663:揭阳 0766:云浮 ")
	private String ORDER_CITY;
	
	@ZteSoftCommentAnnotationParam(name="操作版本",type="String",isNecessary="Y",desc="0：表示新增操作 N：表示更新操作，大于等于1，每操作一次累加1。")
	private String OPER_VERSION;
	
	@ZteSoftCommentAnnotationParam(name="关联类型",type="String",isNecessary="Y",desc="Z1:定制机_存费送机、Z2:定制机_购机送费、Z3:3G后付费号卡、Z4:3G预付费号卡、Z5:定制机裸机、Z6:社会机购机送费、Z7:社会机裸机、Z8:2G号卡、Z9:老用户_存费送机、Z10:老用户_购机送费、Z11:老用户_存费送费、Z12:预付费上网卡【不含卡体】、Z13:预付费上网卡【包含卡体】、Z14:后付费上网卡【不含卡体】、Z15:后付费上网卡【包含卡体】、Z16:增值业务.")
	private String RELATION_TYPE;
	
	@ZteSoftCommentAnnotationParam(name="商品包编码",type="String",isNecessary="Y",desc="商品包编码")
	private String PACKAGE_NO;
	
	@ZteSoftCommentAnnotationParam(name="发送流水",type="String",isNecessary="Y",desc="1、相同流水的请求不做重复处理，防止网络原因s端成功接收，b端读取响应超时后重发报文出错。2、需要bss系统端做判断，如果发现接收到报文中的SERIAL_NO发送流水是相同的，则默认返回上一次处理的结果，这里是为了防止出现问题“报文已经传送至bss系统，bss系统已经接收并处理，但订单系统没有接收到正常的响应”。")
	private String SERIAL_NO;

	@ZteSoftCommentAnnotationParam(name="订单系统操作员",type="String",isNecessary="Y",desc="订单系统的操作工号，在业务受理环节进行操作的给业务受理环节的工号，否则给空。")
	private String OPER_NO;
	
	@ZteSoftCommentAnnotationParam(name="订单系统部门",type="String",isNecessary="Y",desc="订单系统部门")
	private String OPER_DEP;
	
	@ZteSoftCommentAnnotationParam(name="商品名称",type="String",isNecessary="Y",desc="商品名称")
	private String PRODUCT_NAME;
	
	@ZteSoftCommentAnnotationParam(name="预留字段1",type="String",isNecessary="Y",desc="预留字段1")
	private String RESERVE_FILE1;
	
	@ZteSoftCommentAnnotationParam(name="预留字段2",type="String",isNecessary="Y",desc="预留字段2")
	private String RESERVE_FILE2;
	
	@ZteSoftCommentAnnotationParam(name="预留字段3",type="String",isNecessary="Y",desc="预留字段3")
	private String RESERVE_FILE3;
	
	@ZteSoftCommentAnnotationParam(name="预留字段4",type="String",isNecessary="Y",desc="预留字段4")
	private String RESERVE_FILE4;
	
	@ZteSoftCommentAnnotationParam(name="预留字段5",type="String",isNecessary="Y",desc="预留字段5")
	private String RESERVE_FILE5;
	
	public String getSERIAL_NUMBER() {
		SERIAL_NUMBER = CommonDataFactory.getInstance().getAttrFieldValue(ORDER_NO, AttrConsts.PHONE_NUM);
		return SERIAL_NUMBER;
	}

	public void setSERIAL_NUMBER(String sERIAL_NUMBER) {
		SERIAL_NUMBER = sERIAL_NUMBER;
	}

	public String getORDER_NO() {
		return ORDER_NO;
	}

	public void setORDER_NO(String oRDER_NO) {
		ORDER_NO = oRDER_NO;
	}

	public String getORDER_TYPE() {
		String tid_category = CommonDataFactory.getInstance().getAttrFieldValue(ORDER_NO, AttrConsts.TID_CATEGORY);
		ORDER_TYPE = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.BSS_TID_CATEGORY, tid_category);
		return ORDER_TYPE;
	}

	public void setORDER_TYPE(String oRDER_TYPE) {
		ORDER_TYPE = oRDER_TYPE;
	}

	public String getESS_ORDER_NO() {
		return ESS_ORDER_NO;
	}

	public void setESS_ORDER_NO(String eSS_ORDER_NO) {
		ESS_ORDER_NO = eSS_ORDER_NO;
	}

	public String getIMEI() {
		IMEI = CommonDataFactory.getInstance().getAttrFieldValue(ORDER_NO, AttrConsts.TERMINAL_NUM);
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}

	public String getACCEPT_DATE() {
		ACCEPT_DATE = CommonDataFactory.getInstance().getOrderTree(ORDER_NO).getOrderBusiRequest().getAccept_time();
		return ACCEPT_DATE;
	}

	public void setACCEPT_DATE(String aCCEPT_DATE) {
		ACCEPT_DATE = aCCEPT_DATE;
	}

	public String getREAL_MONEY() {
		REAL_MONEY = CommonDataFactory.getInstance().getAttrFieldValue(ORDER_NO, AttrConsts.ORDER_REAL_FEE);
		return REAL_MONEY;
	}

	public void setREAL_MONEY(String rEAL_MONEY) {
		REAL_MONEY = rEAL_MONEY;
	}

	public String getPOLICY() {
		return POLICY;
	}

	public void setPOLICY(String pOLICY) {
		POLICY = pOLICY;
	}

	public String getORDER_ORIGIN() {
		return ORDER_ORIGIN;
	}

	public void setORDER_ORIGIN(String oRDER_ORIGIN) {
		ORDER_ORIGIN = oRDER_ORIGIN;
	}

	public String getACTIVITY_TYPE() {
		ACTIVITY_TYPE = CommonDataFactory.getInstance().getAttrFieldValue(ORDER_NO, AttrConsts.ACTIVE_TYPE);
		if(StringUtils.isEmpty(ACTIVITY_TYPE)){
			ACTIVITY_TYPE = "6";
		}
		return ACTIVITY_TYPE;
	}

	public void setACTIVITY_TYPE(String aCTIVITY_TYPE) {
		ACTIVITY_TYPE = aCTIVITY_TYPE;
	}

	public String getORIGIN_ORDER_NO() {
		ORIGIN_ORDER_NO = CommonDataFactory.getInstance().getAttrFieldValue(ORDER_NO, AttrConsts.OUT_TID);
		return ORIGIN_ORDER_NO;
	}

	public void setORIGIN_ORDER_NO(String oRIGIN_ORDER_NO) {
		ORIGIN_ORDER_NO = oRIGIN_ORDER_NO;
	}

	public String getTERMINAL_TYPE() {
		TERMINAL_TYPE = CommonDataFactory.getInstance().getAttrFieldValue(ORDER_NO, AttrConsts.SPECIFICATION_CODE);
		return TERMINAL_TYPE;
	}

	public void setTERMINAL_TYPE(String tERMINAL_TYPE) {
		TERMINAL_TYPE = tERMINAL_TYPE;
	}

	public String getPAY_MODE() {
		String pay_type = CommonDataFactory.getInstance().getAttrFieldValue(ORDER_NO, AttrConsts.PAY_TYPE);
		PAY_MODE = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.BSS_PAY_TYPE, pay_type);
		return PAY_MODE;
	}

	public void setPAY_MODE(String pAY_MODE) {
		PAY_MODE = pAY_MODE;
	}

	public String getPAY_MECHANISM() {
		return PAY_MECHANISM;
	}

	public void setPAY_MECHANISM(String pAY_MECHANISM) {
		PAY_MECHANISM = pAY_MECHANISM;
	}

	public String getLOGISTICS_NO() {
		LOGISTICS_NO = CommonDataFactory.getInstance().getAttrFieldValue(ORDER_NO, AttrConsts.LOGI_NO);
		return LOGISTICS_NO;
	}

	public void setLOGISTICS_NO(String lOGISTICS_NO) {
		LOGISTICS_NO = lOGISTICS_NO;
	}

	public String getDELIVERY_MODE() {
		DELIVERY_MODE = CommonDataFactory.getInstance().getAttrFieldValue(ORDER_NO, AttrConsts.SENDING_TYPE);
		if(StringUtils.isEmpty(DELIVERY_MODE)){
			DELIVERY_MODE = "08";//物流发货
		}
		return DELIVERY_MODE;
	}

	public void setDELIVERY_MODE(String dELIVERY_MODE) {
		DELIVERY_MODE = dELIVERY_MODE;
	}

	public String getUSER_IDENTIFICATION() {
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(ORDER_NO, AttrConsts.IS_OLD);
		USER_IDENTIFICATION = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.BSS_IS_OLD, is_old);
		return USER_IDENTIFICATION;
	}

	public void setUSER_IDENTIFICATION(String uSER_IDENTIFICATION) {
		USER_IDENTIFICATION = uSER_IDENTIFICATION;
	}

	public String getCREATE_TIME() {
		CREATE_TIME = CommonDataFactory.getInstance().getOrderTree(ORDER_NO).getOrderBusiRequest().getCreate_time();
		return CREATE_TIME;
	}

	public void setCREATE_TIME(String cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}

	public String getPAY_TIME() {
		PAY_TIME = CommonDataFactory.getInstance().getAttrFieldValue(ORDER_NO, AttrConsts.PAY_TIME);
		return PAY_TIME;
	}

	public void setPAY_TIME(String pAY_TIME) {
		PAY_TIME = pAY_TIME;
	}

	public String getACCOUNT_RECEIVABLE() {
		return ACCOUNT_RECEIVABLE;
	}

	public void setACCOUNT_RECEIVABLE(String aCCOUNT_RECEIVABLE) {
		ACCOUNT_RECEIVABLE = aCCOUNT_RECEIVABLE;
	}

	public String getORDER_STATUS() {
		return ORDER_STATUS;
	}

	public void setORDER_STATUS(String oRDER_STATUS) {
		ORDER_STATUS = oRDER_STATUS;
	}

	public String getGIFTS() {
		return GIFTS;
	}

	public void setGIFTS(String gIFTS) {
		GIFTS = gIFTS;
	}

	public String getAGENT_NAME() {
		return AGENT_NAME;
	}

	public void setAGENT_NAME(String aGENT_NAME) {
		AGENT_NAME = aGENT_NAME;
	}

	public String getAGENT_TERMINAL_PRICE() {
		return AGENT_TERMINAL_PRICE;
	}

	public void setAGENT_TERMINAL_PRICE(String aGENT_TERMINAL_PRICE) {
		AGENT_TERMINAL_PRICE = aGENT_TERMINAL_PRICE;
	}

	public String getPOSTAGE() {
		return POSTAGE;
	}

	public void setPOSTAGE(String pOSTAGE) {
		POSTAGE = pOSTAGE;
	}

	public String getVOUCHER_VALUE() {
		return VOUCHER_VALUE;
	}

	public void setVOUCHER_VALUE(String vOUCHER_VALUE) {
		VOUCHER_VALUE = vOUCHER_VALUE;
	}

	public String getPREFERENTIAL_TYPE() {
		return PREFERENTIAL_TYPE;
	}

	public void setPREFERENTIAL_TYPE(String pREFERENTIAL_TYPE) {
		PREFERENTIAL_TYPE = pREFERENTIAL_TYPE;
	}

	public String getPREFERENTIAL_NO() {
		return PREFERENTIAL_NO;
	}

	public void setPREFERENTIAL_NO(String pREFERENTIAL_NO) {
		PREFERENTIAL_NO = pREFERENTIAL_NO;
	}

	public String getDISCOUNT_AMOUNT() {
		return DISCOUNT_AMOUNT;
	}

	public void setDISCOUNT_AMOUNT(String dISCOUNT_AMOUNT) {
		DISCOUNT_AMOUNT = dISCOUNT_AMOUNT;
	}

	public String getORDER_CITY() {
		String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(ORDER_NO, AttrConsts.ORDER_CITY_CODE);
		ORDER_CITY = CommonDataFactory.getInstance().getLanCode(order_city_code);
		if(StringUtils.isEmpty(ORDER_CITY)){
			ORDER_CITY = "0099";
		}
		return ORDER_CITY;
	}

	public void setORDER_CITY(String oRDER_CITY) {
		ORDER_CITY = oRDER_CITY;
	}

	public String getOPER_VERSION() {
		return OPER_VERSION;
	}

	public void setOPER_VERSION(String oPER_VERSION) {
		OPER_VERSION = oPER_VERSION;
	}

	public String getRELATION_TYPE() {
		return RELATION_TYPE;
	}

	public void setRELATION_TYPE(String rELATION_TYPE) {
		RELATION_TYPE = rELATION_TYPE;
	}

	public String getPACKAGE_NO() {
		return PACKAGE_NO;
	}

	public void setPACKAGE_NO(String pACKAGE_NO) {
		PACKAGE_NO = pACKAGE_NO;
	}

	public String getSERIAL_NO() {
		return SERIAL_NO;
	}

	public void setSERIAL_NO(String sERIAL_NO) {
		SERIAL_NO = sERIAL_NO;
	}

	public String getOPER_NO() {
		return OPER_NO;
	}

	public void setOPER_NO(String oPER_NO) {
		OPER_NO = oPER_NO;
	}

	public String getOPER_DEP() {
		return OPER_DEP;
	}

	public void setOPER_DEP(String oPER_DEP) {
		OPER_DEP = oPER_DEP;
	}

	public String getPRODUCT_NAME() {
		PRODUCT_NAME = CommonDataFactory.getInstance().getAttrFieldValue(ORDER_NO, AttrConsts.GOODSNAME);
		return PRODUCT_NAME;
	}

	public void setPRODUCT_NAME(String pRODUCT_NAME) {
		PRODUCT_NAME = pRODUCT_NAME;
	}

	public String getRESERVE_FILE1() {
		return RESERVE_FILE1;
	}

	public void setRESERVE_FILE1(String rESERVE_FILE1) {
		RESERVE_FILE1 = rESERVE_FILE1;
	}

	public String getRESERVE_FILE2() {
		return RESERVE_FILE2;
	}

	public void setRESERVE_FILE2(String rESERVE_FILE2) {
		RESERVE_FILE2 = rESERVE_FILE2;
	}

	public String getRESERVE_FILE3() {
		return RESERVE_FILE3;
	}

	public void setRESERVE_FILE3(String rESERVE_FILE3) {
		RESERVE_FILE3 = rESERVE_FILE3;
	}

	public String getRESERVE_FILE4() {
		return RESERVE_FILE4;
	}

	public void setRESERVE_FILE4(String rESERVE_FILE4) {
		RESERVE_FILE4 = rESERVE_FILE4;
	}

	public String getRESERVE_FILE5() {
		return RESERVE_FILE5;
	}

	public void setRESERVE_FILE5(String rESERVE_FILE5) {
		RESERVE_FILE5 = rESERVE_FILE5;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		
		return "com.zte.unicomService.bss.orderFinAccountSync";
	}

}
