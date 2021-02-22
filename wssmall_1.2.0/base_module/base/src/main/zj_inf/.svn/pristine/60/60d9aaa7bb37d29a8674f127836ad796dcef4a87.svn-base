package zte.net.ecsord.params.ems.req;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.cookie.DateUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.mq.client.common.DateUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ems.vo.EmsPrintData;

public class LogisticsInfoSyncReq extends ZteRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2701165935786499868L;
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "账号（大客户号）", type = "String", isNecessary = "Y", desc = "账号（大客户号）")
	private String sysAccount;
	@ZteSoftCommentAnnotationParam(name = "密码（对接密码）", type = "String", isNecessary = "Y", desc = "密码（对接密码）")
	private String passWord;
	@ZteSoftCommentAnnotationParam(name = "对接授权码", type = "String", isNecessary = "Y", desc = "对接授权码")
	private String appKeySub;
	@ZteSoftCommentAnnotationParam(name = "数据类型", type = "String", isNecessary = "Y", desc = "数据类型：1-预制详情单，2-热敏标签式详情单")
	private String printKind;
	@ZteSoftCommentAnnotationParam(name = "邮件数据", type = "List", isNecessary = "Y", desc = "邮件数据")
	private List<EmsPrintData> printDatas;
	
	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.ems.syncLogisticsInfo";
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getSysAccount() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		sysAccount = cacheUtil.getConfigInfo("ems_syn_sysAccount");
		//return "A1234567890Z";
		return sysAccount;
	}

	public void setSysAccount(String sysAccount) {
		this.sysAccount = sysAccount;
	}

	public String getPassWord() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		passWord = cacheUtil.getConfigInfo("ems_syn_passWord");
		//return "e10adc3949ba59abbe56e057f20f883e";
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getAppKeySub() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		appKeySub = cacheUtil.getConfigInfo("ems_syn_appkey");
		return appKeySub;
	}

	public void setAppKeySub(String appKeySub) {
		this.appKeySub = appKeySub;
	}

	public String getPrintKind() {
		printKind = "2";
		return printKind;
	}

	public void setPrintKind(String printKind) {
		this.printKind = printKind;
	}

	public List<EmsPrintData> getPrintDatas() {
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		List<OrderDeliveryBusiRequest> orderDeliveryBusiRequests = orderTree.getOrderDeliveryBusiRequests();
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		//取正常发货记录
		OrderDeliveryBusiRequest delivery = null;
		for(OrderDeliveryBusiRequest deli:orderDeliveryBusiRequests){
			if(EcsOrderConsts.LOGIS_NORMAL.equals(deli.getDelivery_type())){
				delivery = deli;
				break;
			}
		}
		String region_id = null;
		String city_id = null;
		String province_id = null;
		if(null!=delivery){
			region_id = delivery.getRegion_id().toString();
			city_id = delivery.getCity_id().toString();
			province_id = delivery.getProvince_id().toString();
		}
		//获取物流公司联系人
		String shipping_company = delivery.getShipping_company();//delivery.getShipping_company();
		String order_city_code = orderExtBusiRequest.getOrder_city_code();
		Map logi_company = CommonDataFactory.getInstance().getLogiCompPersonData(shipping_company, order_city_code);
		//获取寄件方省市区信息
		Map postRegion = CommonDataFactory.getInstance().getPostRegion(order_city_code);
		
		printDatas = new ArrayList<EmsPrintData>();
		EmsPrintData printData = new EmsPrintData();
		printData.setBigAccountDataId(notNeedReqStrOrderId);
		//printData.setBusinessType("1");//固定值1标准快递
		if(EcsOrderConsts.PAY_TYPE_HDFK.equals(orderExtBusiRequest.getPay_type())){
			printData.setBusinessType("2");//快递业务类型，1为标准快递，2为代收货款，3为收件人付费，4为经济快递，9:快递包裹，不可传空   --songqi
			printData.setAddser_dshk("1");//代收货款（附加服务）1:代收货款		0:非代收货款
		}else{
			printData.setBusinessType("1");//快递业务类型，1为标准快递，2为代收货款，3为收件人付费，4为经济快递，9:快递包裹，不可传空   --songqi
			printData.setAddser_dshk("0");//代收货款（附加服务）1:代收货款		0:非代收货款
		}
		printData.setBillno(delivery.getLogi_no());
		printData.setDateType("1");//时间类型
		printData.setProcdate(DateUtil.formatDate(new Date()));//时间YYYY-MM-DD hh:mi:ss
		printData.setScontactor((String)logi_company.get("post_linkman"));//寄件人姓名
		printData.setScustMobile((String)logi_company.get("post_tel"));//寄件人电话
		printData.setScustTelplus("");//寄件人电话2
		printData.setScustPost("");//寄件人邮编
		printData.setScustAddr((String)logi_company.get("post_address"));//寄件人地址
		printData.setScustComp("");//寄件人公司名称
		printData.setScustProvince((String) postRegion.get("province"));//寄件人所在省
		printData.setScustCity((String) postRegion.get("city"));//寄件人所在市
		printData.setScustCounty((String) postRegion.get("district"));//寄件人所在区县
		printData.setTcontactor(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIP_NAME));//收件人姓名
		printData.setTcustMobile(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.REFERENCE_PHONE));//收件人电话
		printData.setTcustTelplus(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.REVEIVER_MOBILE));//收件人电话2
		printData.setTcustPost(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIP_ZIP));//收件人邮编
		printData.setTcustAddr(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIP_ADDR));//收件人地址
		printData.setTcustComp("");//收件人公司名称
		printData.setTcustProvince(CommonDataFactory.getInstance().getLocalName(province_id));//收件人所在省
		printData.setTcustCity(CommonDataFactory.getInstance().getLocalName(city_id));//收件人所在市
		printData.setTcustCounty(CommonDataFactory.getInstance().getLocalName(region_id));//收件人所在区县
		printData.setWeight(0);//货物重量
		printData.setLength(0);//货物长度
		printData.setInsure(0);//保价金额
		printData.setInsurance(0);//保险金额
		Double fee = orderTree.getOrderBusiRequest().getPaymoney()==null?0:orderTree.getOrderBusiRequest().getPaymoney();
		printData.setFee(fee);//货款金额    //快递业务类型  2为代收货款      --songqi
		printData.setFeeUppercase("");//大写货款金额
		printDatas.add(printData);
		return printDatas;
	}

	public void setPrintDatas(List<EmsPrintData> printDatas) {
		this.printDatas = printDatas;
	}

}
