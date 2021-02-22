package com.ztesoft.net.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import params.ZteResponse;
import params.req.ZbBackfillLogisticsReq;
import params.req.ZbOrderDeliveryCodeQueryReq;
import params.req.ZbOrderDeliveryReq;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.taobao.req.LogisticsTAOBAORequset;
import zte.net.ecsord.params.taobao.req.TbTianjiOrderDeliverySyncReq;
import zte.net.ecsord.params.taobao.resp.LogisticsTAOBAOResponse;
import zte.net.ecsord.params.taobao.resp.TbTianjiOrderStatusNoticeResp;
import zte.net.ecsord.params.taobao.vo.LogisticsTAOBAOOnline;
import zte.net.ecsord.params.wms.req.NotifyOrderStatusFromWMSReq;
import zte.net.ecsord.params.wms.req.NotifyOrderStatusToWMSReq;
import zte.net.ecsord.params.wms.resp.NotifyOrderStatusToWMSResp;
import zte.net.ecsord.params.wms.vo.NotifyStatusFromWMSOrderInfoVo;
import zte.net.ecsord.params.wyg.req.NotifyOrderInfoWYGRequset;
import zte.net.ecsord.params.wyg.req.Sms3NetSendReq;
import zte.net.ecsord.params.wyg.resp.NotifyOrderInfoWYGResponse;
import zte.net.ecsord.params.wyg.resp.Sms3NetSendResp;
import zte.net.ecsord.params.zb.req.QryPhoneUrlRequest;
import zte.net.ecsord.params.zb.req.StateSynToZBRequest;
import zte.net.ecsord.params.zb.resp.QryPhoneUrlResponse;
import zte.net.ecsord.params.zb.resp.StateSynToZBResponse;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.utils.AttrUtils;
import zte.params.order.req.SmsSendReq;
import zte.params.order.resp.SmsSendResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IOrdShipTacheManager;

import consts.ConstsCore;

/**
 * 发货环节处理类
 * @author xuefeng
 */
public class OrdShipTacheManager extends BaseSupport implements IOrdShipTacheManager {
	
	@Override
	public BusiDealResult notifyQCStatusToZB(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		StateSynToZBRequest req = new StateSynToZBRequest();
		req.setNotNeedReqStrOrderId(order_id);
		req.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_CHECKED);
		req.setNotNeedReqStrStateChgReason(EcsOrderConsts.STATE_CHG_REASON_OTHEOR);
		req.setNotNeedReqStrStateChgDesc("稽核完成");
		StateSynToZBResponse infResp = client.execute(req,
				StateSynToZBResponse.class);
		if (infResp.getRespCode().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000)) {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.ZB_STATUS}, 
					new String[]{EcsOrderConsts.ZB_ORDER_STATE_N08});
		} else {
			result.setError_msg("错误编码：" + infResp.getRespCode() + ";错误信息：" + infResp.getError_msg());
		}
		result.setError_code(infResp.getRespCode());
		return result;
	}
	
	/**
	 * 通知WMS进行发货
	 * @param order_id
	 * @return  BusiDealResult
	 * @throws Exception 
	 */
	@Override
	public BusiDealResult notifyCheckFinishToWMS(String order_id) throws Exception {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		
		NotifyOrderStatusToWMSReq req = new NotifyOrderStatusToWMSReq();
		req.setOrderId(order_id);
		req.setNotNeedReqStrWms_status(EcsOrderConsts.ORDER_STATUS_WMS_7);//稽核完成，通知WMS发货
		NotifyOrderStatusToWMSResp infResp = client.execute(req, NotifyOrderStatusToWMSResp.class);
		
		if (!infResp.getErrorCode().equals(EcsOrderConsts.WMS_INF_RESP_CODE_0000)){
			result.setError_msg("错误编码：" + infResp.getErrorCode() + ";错误信息："+ infResp.getErrorMessage());
			result.setError_code(infResp.getErrorCode());
		}else{
			String [] name={AttrConsts.WMS_STATUS};
			String [] value={EcsOrderConsts.WMS_STATUS_1};
			String date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
			logger.info("========================>>>>>开始时间:" + date1);
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,name, value);
			String date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
			logger.info("========================>>>>>结束时间:" + date2);
		}
		
		return result;
	}

	
	/**
	 * 接收WMS出货通知
	 * @param busiCompRequest
	 * @return
	 */
	@Override
	public BusiDealResult recShipFromWMS(BusiCompRequest busiCompRequest) {
		BusiDealResult result = new BusiDealResult();
		// 获取规则对象
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		NotifyOrderStatusFromWMSReq req =  (NotifyOrderStatusFromWMSReq)fact.getRequest();
		if(null == req){
			return result;
		}
		NotifyStatusFromWMSOrderInfoVo notifyStatus = req.getOrderInfo();
		String order_id = notifyStatus.getOrderId();
		if(EcsOrderConsts.ORDER_STATUS_WMS_9.equals(notifyStatus.getStatus())){
			String [] name={AttrConsts.WMS_STATUS,AttrConsts.TOTE_ID,AttrConsts.STATIONNO};
			String [] value={EcsOrderConsts.WMS_STATUS_2,notifyStatus.getToteId(),notifyStatus.getStationNo()};
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,name, value);
		}
		fact.setRequest(null);						//清空入参，这里必须清除
		return result;
	}
	
	/**
	 * 接收WMS发货结果通知
	 * @param busiCompRequest
	 * @return
	 */
	@Override
	public BusiDealResult recShipFinshFromWMS(BusiCompRequest busiCompRequest) {
		BusiDealResult result = new BusiDealResult();
		// 获取规则对象
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		NotifyOrderStatusFromWMSReq req =  (NotifyOrderStatusFromWMSReq)fact.getRequest();
		if(null == req){
			return result;
		}
		NotifyStatusFromWMSOrderInfoVo notifyStatus = req.getOrderInfo();
		String order_id = notifyStatus.getOrderId();
		if(EcsOrderConsts.ORDER_STATUS_WMS_10.equals(notifyStatus.getStatus())){
			String [] name={AttrConsts.WMS_STATUS,AttrConsts.TOTE_ID,AttrConsts.STATIONNO};
			String [] value={EcsOrderConsts.WMS_STATUS_3,notifyStatus.getToteId(),notifyStatus.getStationNo()};
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,name, value);
		}
		return result;
	}
	
	/**
	 * 发货通知短信
	 * @param busiCompRequest
	 * @return
	 */
	@Override
	public BusiDealResult smsSendNotify(String order_id) {
		BusiDealResult result = new BusiDealResult();
		String acc_nbr = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFERENCE_PHONE);
		String smsContent = "";		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		SmsSendReq smsSendReq = new SmsSendReq();
//		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
//		String acc_nbr = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CARRY_PERSON_MOBILE);
//		String smsContent = cacheUtil.getConfigInfo(EcsOrderConsts.SHIP_NOTIFY);
		smsSendReq.setAcc_nbr(acc_nbr);
		smsSendReq.setSmsContent(smsContent);
		SmsSendResp smsSendResp = client.execute(smsSendReq, SmsSendResp.class);
		return result;
	}
	
	/**
	 * 订单变更通知总部(发货)
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult notifyShipToZB(String order_id) {
		BusiDealResult result = new BusiDealResult();
		//测试联调使用
		/*ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		NotifyDeliveryZBRequest req = new NotifyDeliveryZBRequest();
		req.setNotNeedReqStrOrderId(order_id);
		NotifyDeliveryResponse infResp = client.execute(req, NotifyDeliveryResponse.class);
		if (infResp.getRespCode().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000)){
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.ZB_STATUS}, 
					new String[]{EcsOrderConsts.ZB_ORDER_STATE_N09});
			//add by mo.chencheng
			result.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
			result.setError_msg("执行成功,后台将会自动同步发货信息至总部商城");
		}else {
			result.setError_msg("错误编码：" + infResp.getRespCode() + ";错误信息："
					+ infResp.getRespDesc());
			result.setError_code(infResp.getRespCode());
		}*/
		//生产使用
		try {
			// 添加到定时任务队列
			CoQueue queBak = new CoQueue();
			queBak.setService_code("CO_DELIVERYNOTIFY_ZB");			//service_code改为老系统
			queBak.setCo_id("");
			queBak.setCo_name("订单发货信息同步总部商城");
			queBak.setObject_id(order_id);
			queBak.setObject_type("DINGDAN");
			queBak.setStatus(Consts.CO_QUEUE_STATUS_WFS);
			String create_date= DateUtil.addDate(DateUtil.getTime2(), DateUtil.DATE_FORMAT_2, ZjEcsOrderConsts.ZS_ES_CO_QUEUE_TIME_NOTIFY_SHIP, "second");//开户完成通知、发货状态同步+5秒、发货信息同步+10秒，定时任务执行的时候按时间排序执行
			queBak.setCreated_date(create_date);
			this.baseDaoSupport.insert("co_queue", queBak);
			
			result.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
			result.setError_msg("执行成功,后台将会自动同步发货信息至总部商城");
		} catch (Exception e) {
			e.printStackTrace();
			result.setError_code("-1");
			result.setError_msg("es_co_queue定时任务插入失败;"+e.getMessage());
		}
		return result;
	}
	/**
	 * [爬虫]物流单号回填总商
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult orderUpdateLogiNoToZS(String order_id) {
		BusiDealResult result = new BusiDealResult();
		///////////////////需要改成调用爬虫的代码
		OrderDeliveryBusiRequest delivery = CommonDataFactory.getInstance().getDeliveryBusiRequest(order_id, EcsOrderConsts.LOGIS_NORMAL);
		String logi_no = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.LOGI_NO);
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZbBackfillLogisticsReq req = new ZbBackfillLogisticsReq();
		req.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID));
		req.setOrderId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_NUM));
		req.setLogisticType("2");//固定第三方（物流公司）配送
		String shipping_company = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIPPING_COMPANY);
		String companyName = null;
		if(EcsOrderConsts.LOGI_COMPANY_SFFYZQYF.equals(shipping_company)){
			companyName = "顺丰速运";
		}
		else if(EcsOrderConsts.LOGI_COMPANY_EMS0001.equals(shipping_company)){
			companyName = "邮政EMS";
		}
		String zbshipping_company=CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ZB_SHIPPING_COMPANY, shipping_company);
		req.setCompanyId(zbshipping_company);
		req.setCompanyName(companyName);
		String needReceipt = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.NEED_RECEIPT);
		req.setIsNeedLgtsRtn(needReceipt);
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(needReceipt)){
			req.setLgtsRtnOrderDesc("此快件需要回单");
		}
		req.setLgtsRtnOrder(delivery.getReceipt_no());
		req.setLgtsRemark("");
		req.setInsureFlag(String.valueOf(delivery.getIs_protect()));
		req.setInsureMoney(String.valueOf(delivery.getProtect_price()));
		req.setLgtsOrder(logi_no);
		
		//设置寄件人信息
		//寄件省份
		String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);
		Map postRegion = CommonDataFactory.getInstance().getPostRegion(order_city_code);
		String j_province = (String) postRegion.get("province_code");
		req.setProvinceCode(j_province);
		//寄件地市
		String j_city = (String) postRegion.get("city_code");
		req.setCityCode(j_city);
		
		String district_code = (String) postRegion.get("district_code");
		req.setDistrictCode(district_code);
		Map logi_company = CommonDataFactory.getInstance().getLogiCompPersonData(shipping_company, order_city_code);
		String j_address = (String) logi_company.get("post_address");
		req.setAddress(j_address);
		
		String j_contact = (String) logi_company.get("post_linkman");
		req.setContact(j_contact);
		//寄件人联系电话
		String j_tel = (String) logi_company.get("post_tel");
		req.setTelphone(j_tel);
		
		ZteResponse infResp = client.execute(req, ZteResponse.class);
		if (StringUtils.equals(infResp.getError_code(), ConstsCore.ERROR_SUCC)){
			result.setError_code(ConstsCore.ERROR_SUCC);
			result.setError_msg("执行成功");
		}else {
			result.setError_msg("错误编码：" + infResp.getError_code() + ";错误信息：" + infResp.getError_msg());
			result.setError_code(infResp.getError_code());
		}
		return result;
	}
	/**
	 * 订单变更通知新商城(发货)
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult notifyShipToOuterShop(String order_id) {
		String field_value="";
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		NotifyOrderInfoWYGRequset req = new NotifyOrderInfoWYGRequset();
		req.setTrace_code(EcsOrderConsts.NEW_SHOP_ORDER_STATE_03);		//通知外部商城发货（新商城）
		req.setNotNeedReqStrOrderId(order_id);
		
		NotifyOrderInfoWYGResponse infResp = client.execute(req, NotifyOrderInfoWYGResponse.class);
		
		if (!infResp.getResp_code().equals(EcsOrderConsts.NEW_SHOP_INF_SUCC_CODE)){
			result.setError_msg("错误编码：" + infResp.getResp_code() + ";错误信息："
					+ infResp.getResp_msg());
			result.setError_code(infResp.getResp_code());
			field_value=EcsOrderConsts.NEW_SHOP_STATUS_0;
		}else{
			result.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
			field_value=EcsOrderConsts.NEW_SHOP_STATUS_1;
		}
		String [] name={ AttrConsts.NEW_SHOP_STATUS};
		String [] value={field_value};
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id,name, value);
		
		
		return result;
	}
	
	/**
	 * 订单变更通知天猫天机(发货)
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult syncOrderDeliveryToTaobaoTianji(String order_id) {
		BusiDealResult result = new BusiDealResult();
		TbTianjiOrderDeliverySyncReq req = new TbTianjiOrderDeliverySyncReq();
		req.setNotNeedReqStrOrderId(order_id);
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		TbTianjiOrderStatusNoticeResp resp = client.execute(req, TbTianjiOrderStatusNoticeResp.class);
		
		result.setError_code(resp.getError_code());
		result.setError_msg(resp.getError_msg());
		return result;
	}
	
	/**
	 * 订单变更通知淘宝(发货)
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult notifyShipToTB(String order_id) {
		String field_value="";
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		LogisticsTAOBAORequset req = new LogisticsTAOBAORequset();
		req.setNotNeedReqStrOrderId(order_id);
		String is_physics = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_PHISICS);
		if("1".equals(is_physics)){
			req.setMark(EcsOrderConsts.TB_SHIPPING_MARK_ONLINE);
			LogisticsTAOBAOOnline online = new LogisticsTAOBAOOnline();
			online.setTid(order_id);
			online.setSeller_ip("127.1.1.1");
			online.setFeature("11111");
			req.setOnline(online);
		}else{
			req.setMark(EcsOrderConsts.TB_SHIPPING_MARK_VIRTUAL);
		}
		LogisticsTAOBAOResponse infResp = client.execute(req, LogisticsTAOBAOResponse.class);
		if(null!=infResp&&"0000".equals(infResp.getError_code())){
			if (!infResp.getIs_success().equals(EcsOrderConsts.TB_INF_RESP_CODE_TRUE)){
				result.setError_msg("错误编码：" + infResp.getIs_success() + ";错误信息："
						+ infResp.getError_msg());
				result.setError_code(infResp.getIs_success());
				field_value=EcsOrderConsts.TB_STATUS_0;
			}else{
				result.setError_code(infResp.getError_code());
				result.setError_msg(infResp.getError_msg());
				field_value=EcsOrderConsts.TB_STATUS_1;
			}
		}else{
			result.setError_code(infResp.getError_code());
			result.setError_msg(infResp.getError_msg());
			field_value=EcsOrderConsts.TB_STATUS_0;
		}
		
		String [] name={AttrConsts.TB_STATUS};
		String [] value={field_value};
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id,name, value);
		return result;
	}

	public BusiDealResult notifyWMSShipFinish(String order_id){
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		
		NotifyOrderStatusToWMSReq req = new NotifyOrderStatusToWMSReq();
		req.setOrderId(order_id);
		req.setNotNeedReqStrWms_status(EcsOrderConsts.ORDER_STATUS_WMS_10);//通知WMS已发货
		NotifyOrderStatusToWMSResp infResp = client.execute(req, NotifyOrderStatusToWMSResp.class);
		
		if (!infResp.getErrorCode().equals(EcsOrderConsts.WMS_INF_RESP_CODE_0000)){
			result.setError_msg("错误编码：" + infResp.getErrorCode() + ";错误信息："+ infResp.getErrorMessage());
			result.setError_code(infResp.getErrorCode());
		}else{
			result.setError_code(infResp.getErrorCode());
		}
		return result;
	}

	@Override
	public BusiDealResult send3NetSms(String order_id,String smsTemplateName) {
		BusiDealResult result = new BusiDealResult();
		String reference_phone = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REVEIVER_MOBILE);
//		String logi_no = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.LOGI_NO);
//		Map<String,String> params = new HashMap<String, String>(0);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS");
//		String time = sdf.format(new Date());
//		params.put("logi_no", logi_no);
//		params.put("time", time);
		String content = CommonDataFactory.getInstance().get3NetSmsTemplate(smsTemplateName, order_id);
		if ("ZB_SMS3NETSEND".equals(smsTemplateName)) { // 总部短信接口才调用证件上传地址获取接口
			QryPhoneUrlResponse res = setPhoneUrl(order_id);
			if(EcsOrderConsts.INF_RESP_CODE_0000.equals(res.getRespCode())){
				content = content.replace("phoneurl", res.getUrlInfo());
			}else{
				result.setError_msg("错误编码：" + res.getRespCode() + ";错误信息："+ res.getRespDesc());
				result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);//业务组件失败
				return result;
			}
		}
		Sms3NetSendReq req = new Sms3NetSendReq();
		req.setNotNeedReqStrOrderId(order_id);
		req.setMobile_number(reference_phone);
		req.setSms_content(content);
		req.setSource_system("10011");
		req.setPriority_level("1");
		req.setRepeat_time("9");
		req.setSend_gate_type("1");
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		Sms3NetSendResp resp = client.execute(req, Sms3NetSendResp.class);
		
		if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getError_code())){//接口级别错误(业务级别错误也被封装到接口级别)
			result.setError_msg("错误编码：" + resp.getError_code() + ";错误信息："+ resp.getError_msg());
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);//业务组件失败
		}else{
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);//业务组件成功
		}
		return result;
	}
	
	private QryPhoneUrlResponse setPhoneUrl(String order_id){
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		QryPhoneUrlRequest req = new QryPhoneUrlRequest();
		req.setNotNeedReqStrOrderId(order_id);
		logger.info("开始查询上传地址:"+order_id);
		QryPhoneUrlResponse resp = client.execute(req, QryPhoneUrlResponse.class);
		return resp;
	}

	@Override
	public BusiDealResult noticeDeliveryInfoToZS(String order_id) {
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZbOrderDeliveryReq req = new ZbOrderDeliveryReq();
		OrderDeliveryBusiRequest delivery = CommonDataFactory.getInstance().getDeliveryBusiRequest(order_id, EcsOrderConsts.LOGIS_NORMAL);
		req.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID));
		req.setOrderId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_NUM));
		req.setLogisticType("2");//固定第三方（物流公司）配送
		String shipping_company = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIPPING_COMPANY);
		shipping_company=CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ZB_SHIPPING_COMPANY, shipping_company);
		req.setCompanyId(shipping_company);
		req.setCompanyName(AttrUtils.getInstance().getOtherDictCodeValueDesc(StypeConsts.ZB_SHIPPING_COMPANY, shipping_company));
		String needReceipt = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.NEED_RECEIPT);
		req.setIsNeedLgtsRtn(needReceipt);
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(needReceipt)){
			req.setLgtsRtnOrderDesc("此快件需要回单");
		}
		req.setLgtsRtnOrder(delivery.getReceipt_no());
		req.setLgtsRemark("快递大哥辛苦了,此客户对我很重要,请多担待");
		req.setIccid(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID));
		req.setResourceCode(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.TERMINAL_NUM));
		
		logger.info("开始查询上传地址:"+order_id);
		ZteResponse resp = client.execute(req, ZteResponse.class);
		
		BusiDealResult result = new BusiDealResult();
		if (StringUtils.equals(resp.getError_code(), ConstsCore.ERROR_SUCC)){
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.ZB_STATUS}, 
					new String[]{EcsOrderConsts.ZB_ORDER_STATE_N09});
			
			result.setError_code(ConstsCore.ERROR_SUCC);
			result.setError_msg("执行成功");
		}else {
			result.setError_msg("错误编码：" + resp.getError_code() + ";错误信息：" + resp.getError_msg());
			result.setError_code(resp.getError_code());
		}
		return result;
	}
	@Override
	public BusiDealResult getZbLogiNoByCrawler(String order_id) {
		ZbOrderDeliveryCodeQueryReq req = new ZbOrderDeliveryCodeQueryReq();
		req.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID));
		req.setOrderId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_NUM));
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZteResponse resp = client.execute(req, ZteResponse.class);
		
		BusiDealResult result = new BusiDealResult();
		if (StringUtils.equals(resp.getError_code(), ConstsCore.ERROR_SUCC)){
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.LOGI_NO}, 
					new String[]{resp.getBody()});
			
			OrderDeliveryBusiRequest deliverBusiReq = CommonDataFactory.getInstance().getDeliveryBusiRequest(order_id, EcsOrderConsts.LOGIS_NORMAL);
			
//			String shipping_company = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIPPING_COMPANY);
			String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);
//			Map logi_company = CommonDataFactory.getInstance().getLogiCompPersonData(shipping_company, order_city_code);
//			String postCode = logi_company.get("post_code").equals("")?order_city_code:logi_company.get("post_code").toString();
//			String orgLanCode = CommonDataFactory.getInstance().getLanCode(postCode.toString());
			String orgLanCode = CommonDataFactory.getInstance().getLanCode(order_city_code);
			Long city_id = deliverBusiReq.getCity_id();//收货地市
			String descLanCode = CommonDataFactory.getInstance().getLanCode(city_id.toString());
			
			deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			deliverBusiReq.setLogi_no(resp.getBody());
			deliverBusiReq.setOrigin_code(orgLanCode.substring(1));
			deliverBusiReq.setDest_code(descLanCode.substring(1));
			deliverBusiReq.store(); //属性数据入库
			result.setError_code(ConstsCore.ERROR_SUCC);
			result.setError_msg("执行成功");
		}else {
			logger.info("=====OrdShipTacheManager getZbLogiNoByCrawler 总商获取电子物流单号接口调用出错【orderId："+ order_id+",errorMsg:"+resp.getError_msg()+"】");

			result.setError_msg("错误编码：" + resp.getError_code() + ";错误信息：" + resp.getError_msg());
			result.setError_code(resp.getError_code());
		}
		return result;
	}
}
