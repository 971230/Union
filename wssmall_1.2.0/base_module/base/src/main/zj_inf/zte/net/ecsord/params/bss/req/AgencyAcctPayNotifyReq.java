package zte.net.ecsord.params.bss.req;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.ZteError;
import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.bss.vo.ACCT_REFUND_INFO;
import zte.net.ecsord.params.bss.vo.AGENCY_ACCT_PAY_NOTIFY_REQ;
import zte.net.ecsord.params.bss.vo.COM_BUS_INFO;
import zte.net.ecsord.params.bss.vo.PARA;
import zte.net.ecsord.params.bss.vo.ROUTING;
import zte.net.ecsord.params.bss.vo.SP_RESERVE;
import zte.net.ecsord.params.bss.vo.UNI_BSS_HEAD;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import commons.CommonTools;
import consts.ConstsCore;

public class AgencyAcctPayNotifyReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="订单号",type="String",isNecessary="Y",desc="扣减：传约定的订单号 返销：按照规则新生成一个返销订单ID")
	private String ORDER_ID;//需要设值

	@ZteSoftCommentAnnotationParam(name="订单号",type="String",isNecessary="Y",desc="扣减：为空 返销：扣款时约定的ID")
	private String ORG_ORDER_ID;//需要设值
	
	@ZteSoftCommentAnnotationParam(name="费用",type="String",isNecessary="Y",desc="费用")
	private String PAY_FEE;//需要设值
	
	@ZteSoftCommentAnnotationParam(name="订单号",type="String",isNecessary="Y",desc="电子商城订单系统订单号")
	private String notNeedReqStrOrderId;//需要设值
	
	@ZteSoftCommentAnnotationParam(name="交易类型",type="String",isNecessary="Y",desc="交易类型 0：扣款；1：返销；2：押金清退")
	private String notNeedReqTRADE_TYPE;//需要设值
	
	@ZteSoftCommentAnnotationParam(name = "头", type = "String", isNecessary = "Y", desc = "头")
	private UNI_BSS_HEAD UNI_BSS_HEAD;	
	
	@ZteSoftCommentAnnotationParam(name = "实体", type = "String", isNecessary = "Y", desc = "实体")
	private AGENCY_ACCT_PAY_NOTIFY_REQ AGENCY_ACCT_PAY_NOTIFY_REQ;
	
	public String getORDER_ID() {
		return ORDER_ID;
	}

	public void setORDER_ID(String oRDER_ID) {
		ORDER_ID = oRDER_ID;
	}

	public String getORG_ORDER_ID() {
		return ORG_ORDER_ID;
	}

	public void setORG_ORDER_ID(String oRG_ORDER_ID) {
		ORG_ORDER_ID = oRG_ORDER_ID;
	}

	public String getPAY_FEE() {
		return PAY_FEE;
	}

	public void setPAY_FEE(String pAY_FEE) {
		PAY_FEE = pAY_FEE;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getNotNeedReqTRADE_TYPE() {
		return notNeedReqTRADE_TYPE;
	}

	public void setNotNeedReqTRADE_TYPE(String notNeedReqTRADE_TYPE) {
		this.notNeedReqTRADE_TYPE = notNeedReqTRADE_TYPE;
	}

	public UNI_BSS_HEAD getUNI_BSS_HEAD() {
		UNI_BSS_HEAD = new UNI_BSS_HEAD();
		UNI_BSS_HEAD.setORIG_DOMAIN("ULTE");
		UNI_BSS_HEAD.setSERVICE_NAME("AgencyAcctPaySer");
		UNI_BSS_HEAD.setOPERATE_NAME("agencyAcctPayNotify");
		UNI_BSS_HEAD.setACTION_CODE("0");//0：请求，1：应答
		UNI_BSS_HEAD.setACTION_RELATION("0");//与其他交易关联；0：无关联；1：有关联。例如：在一卡充系统中对于异地卡充异地用户且卡和帐号归属不同省时接入省分缴费卡系统发送的扣款请求和缴费请求交易需要设置此标记为1
		ROUTING routing = new ROUTING();
		routing.setROUTE_TYPE("00");//固定值00:路由类型 参见路由类型编码，如按手机号码路由等 
		routing.setROUTE_VALUE("36");//固定值51:路由关键值 路由类型对应的关键值，参见路由类型说明。如路由类型为手机号码则此字段应填写手机号码；若路由类型为充值卡号码则此字段应填写充值卡号码
		UNI_BSS_HEAD.setROUTING(routing);
		UNI_BSS_HEAD.setPROC_ID(notNeedReqStrOrderId);//发起方业务流水号 发起方填写的业务流水号，一个业务流程中所有服务调用使用同一个业务流水号。
		UNI_BSS_HEAD.setTRANS_IDO(this.notNeedReqStrOrderId);
		SimpleDateFormat dateFormatyyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
		UNI_BSS_HEAD.setPROCESS_TIME(dateFormatyyyyMMddHHmmss.format(new Date()));
		COM_BUS_INFO info = new COM_BUS_INFO();
		
		
		List<OrderLockBusiRequest> orderLockRequestList = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderLockBusiRequests();
		String trace_id = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getFlow_trace_id();
		String lock_user_id = "";
		for (OrderLockBusiRequest orderLockRequest : orderLockRequestList) {
			if(trace_id.equals(orderLockRequest.getTache_code())) {
				lock_user_id = orderLockRequest.getLock_user_id();
				break;
			}
		}
		
		String operatorID = lock_user_id;//CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getLock_user_id();
		
		if(StringUtils.isEmpty(operatorID)){
			String oper_code = CommonDataFactory.getInstance().getOperatorCode(notNeedReqStrOrderId);
			info.setOPER_ID(oper_code);
		}else{
			info.setOPER_ID(operatorID);
		}
		info.setPROVINCE_CODE("36");
		info.setEPARCHY_CODE(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.AGENT_CITY));
		info.setCITY_CODE(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.AGENT_DISTRICT));
		info.setCHANNEL_ID(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.AGENT_CODE));
		info.setCHANNEL_TYPE(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CHANNEL_TYPE));//渠道类型 参见渠道类型编码
		info.setACCESS_TYPE("01");//接入类型:01 WEB； 02 短信； 03 WAP；04 IVR； 05 自助终端；06 MINI营业厅；07客户端；08 第三方平台；99 其他
		info.setORDER_TYPE("00");//00:直接提交 01:预提交
		UNI_BSS_HEAD.setCOM_BUS_INFO(info);
		SP_RESERVE sp = new SP_RESERVE();
		String trans_idc = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
		sp.setTRANS_IDC(trans_idc);
		SimpleDateFormat dateFormatyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
		sp.setCUTOFFDAY(dateFormatyyyyMMdd.format(new Date()));
		sp.setOSNDUNS("9900");//发起方代码
		sp.setHSNDUNS("5100");//归属方代码
		sp.setCONV_ID("");//处理标识(可以为空) 最后的17位为总部平台的处理时间，YYYYMMDDHHMISSsss精确到毫秒
		UNI_BSS_HEAD.setSP_RESERVE(sp);
		UNI_BSS_HEAD.setTEST_FLAG("0");//0：非测试交易，1：测试交易；
		UNI_BSS_HEAD.setMSG_SENDER("0003");//消息发送方代码 发起消息的应用系统代码，参见系统代码表
		UNI_BSS_HEAD.setMSG_RECEIVER("0000");//消息直接接收方代码 该消息送往的下一方代码
		return UNI_BSS_HEAD;
	}

	public void setUNI_BSS_HEAD(UNI_BSS_HEAD uNI_BSS_HEAD) {
		UNI_BSS_HEAD = uNI_BSS_HEAD;
	}

	public AGENCY_ACCT_PAY_NOTIFY_REQ getAGENCY_ACCT_PAY_NOTIFY_REQ() {
		AGENCY_ACCT_PAY_NOTIFY_REQ = new AGENCY_ACCT_PAY_NOTIFY_REQ();
//		String order_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
		AGENCY_ACCT_PAY_NOTIFY_REQ.setORDER_ID(ORDER_ID);//约定的订单号 约定的订单号规则：		1.不能带字母		2.16位		3.开头两位不要是各地市区号的后两位，例如20,55等等打头是不行的		4.也不要以51打头
		String servie_class_code = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		if(EcsOrderConsts.NET_TYPE_2G.equals(servie_class_code)){
			servie_class_code = "0016";
		}else if(EcsOrderConsts.NET_TYPE_3G.equals(servie_class_code)){
			servie_class_code = "0017";
		}else if(EcsOrderConsts.NET_TYPE_4G.equals(servie_class_code)){
			servie_class_code = "0050";//4G业务 0050	4G预付费 0051
		}else{
			servie_class_code = "";
		}
		AGENCY_ACCT_PAY_NOTIFY_REQ.setSERVICE_CLASS_CODE(servie_class_code);//网别名称 编码值	 0010	GSM移动业务  0016	OCS业务(2G) 0017	OCS业务(3G) 0033	WCDMA 0040	互联网接入业务 0050	4G业务 0051	4G预付费 00XN	虚拟用户 00CP	融合业务 00WV	集团业务 0030	固话业务 
		AGENCY_ACCT_PAY_NOTIFY_REQ.setAREA_CODE(CommonDataFactory.getInstance().getAttrFieldValue(this.notNeedReqStrOrderId, AttrConsts.CITY_CODE));//TOD区号
		AGENCY_ACCT_PAY_NOTIFY_REQ.setSERIAL_NUMBER(CommonDataFactory.getInstance().getAttrFieldValue(this.notNeedReqStrOrderId, AttrConsts.PHONE_NUM));//服务号码
		AGENCY_ACCT_PAY_NOTIFY_REQ.setTRADE_TYPE(notNeedReqTRADE_TYPE);//
		SimpleDateFormat dateFormatyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
		AGENCY_ACCT_PAY_NOTIFY_REQ.setTRADE_DATE(dateFormatyyyyMMdd.format(new Date()));
		SimpleDateFormat dateFormatyyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
		AGENCY_ACCT_PAY_NOTIFY_REQ.setTRADE_TIME(dateFormatyyyyMMddHHmmss.format(new Date()));
		AGENCY_ACCT_PAY_NOTIFY_REQ.setCHANNEL_ID(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.AGENT_CODE));
		AGENCY_ACCT_PAY_NOTIFY_REQ.setCHANNEL_NAME(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.AGENT_NAME));
		AGENCY_ACCT_PAY_NOTIFY_REQ.setEPARCHY_CODE(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.AGENT_CITY));
		AGENCY_ACCT_PAY_NOTIFY_REQ.setCITY_CODE(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.AGENT_DISTRICT));
//		//分 -> 元
//		String pay_fee_str = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_REAL_FEE);//订单实收总价
//		double pay_fee_d = Float.parseFloat(pay_fee_str);
//		Double pay_fee_D = new Double(pay_fee_d*100); 
//		int pay_fee_i = pay_fee_D.intValue();
		AGENCY_ACCT_PAY_NOTIFY_REQ.setPAY_FEE(PAY_FEE);//属性为元 扣款或返销金额	 单位：分 金额为正值，当返销时，金额与原业务金额一致。
		AGENCY_ACCT_PAY_NOTIFY_REQ.setACTIVITY_TYPE("1");//营销活动类型：0 存费送机 1其他
		ACCT_REFUND_INFO info = new ACCT_REFUND_INFO();
//		String trans_idc = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);//总部原订单ID
		info.setORG_ORDER_ID(ORG_ORDER_ID);
		info.setORG_PROVINCE_ORDER_ID(notNeedReqStrOrderId);//省分原订单ID
		AGENCY_ACCT_PAY_NOTIFY_REQ.setACCT_REFUND_INFO(info);
//		PARA para = new PARA();
//		para.setPARA_ID("");
//		para.setPARA_VALUE("");
//		AGENCY_ACCT_PAY_NOTIFY_REQ.setPARA(para);
		
		return AGENCY_ACCT_PAY_NOTIFY_REQ;
	}

	public void setAGENCY_ACCT_PAY_NOTIFY_REQ(
			AGENCY_ACCT_PAY_NOTIFY_REQ aGENCY_ACCT_PAY_NOTIFY_REQ) {
		AGENCY_ACCT_PAY_NOTIFY_REQ = aGENCY_ACCT_PAY_NOTIFY_REQ;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if (StringUtils.isEmpty(this.notNeedReqStrOrderId)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "order_id不能为空！"));
        }
		if (StringUtils.isEmpty(notNeedReqTRADE_TYPE)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "交易类型不能为空！"));
        }
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.bss.agencyAcctPayNotifyReq";
	}

}
