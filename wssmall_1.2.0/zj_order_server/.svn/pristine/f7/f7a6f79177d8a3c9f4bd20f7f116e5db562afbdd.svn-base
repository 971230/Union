package com.ztesoft.net.service.impl;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.bss.req.OrderFinAccountSyncReq;
import zte.net.ecsord.params.wms.req.NotifyOrderStatusToWMSReq;
import zte.net.ecsord.params.wms.resp.NotifyOrderStatusToWMSResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IOrdQualityTacheManager;

/**
 * 质检稽核处理类
 * @author xuefeng
 */
public class OrdQualityTacheManager extends BaseSupport implements IOrdQualityTacheManager {
	/**
	 * 物流处理完成签收
	 * @param order_id
	 * @return
	 * @throws Exception 
	 */
	@Override
	public BusiDealResult wlFinishSignIn(String order_id) throws Exception {
		BusiDealResult result = new BusiDealResult();
		//清空锁定人工号信息
		String [] name={AttrConsts.LOCK_USER_ID};
		String [] value={""};
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id,name, value);
		//调用订单可见性业务组件
		BusiCompRequest bcr=new BusiCompRequest();
		bcr.setEnginePath("zteCommonTraceRule.setOrderVisible");
		bcr.setOrder_id(order_id);
		CommonDataFactory.getInstance().execBusiComp(bcr);		 
		return result;
	}
	
	/**
	 * 通知WMS质检稽核
	 * @param order_id
	 * @return
	 * @throws Exception 
	 */
	@Override
	public BusiDealResult notifyWMSBeginQualityCheck(String order_id) throws Exception {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		NotifyOrderStatusToWMSReq req = new NotifyOrderStatusToWMSReq();
		req.setOrderId(order_id);
		req.setNotNeedReqStrWms_status(EcsOrderConsts.ORDER_STATUS_WMS_6);
		NotifyOrderStatusToWMSResp infResp = client.execute(req, NotifyOrderStatusToWMSResp.class);
		
		if(!infResp.getErrorCode().equals(EcsOrderConsts.WMS_INF_RESP_CODE_0000)){
			result.setError_msg("错误编码：" + infResp.getErrorCode() + ";错误信息：" + infResp.getErrorMessage());
			result.setError_code(infResp.getErrorCode());
		}else{//记录WMS状态
			String [] name={AttrConsts.WMS_STATUS};
			String [] value={EcsOrderConsts.WMS_STATUS_4};
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,name,value);
		}
		return result;
	}
	
	/**
	 * Bss对账信息同步
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult generateBssBillInfo(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		OrderFinAccountSyncReq req = new OrderFinAccountSyncReq();
		req.setORDER_NO(order_id);
//		//还没填写参数
//		OrderFinAccountSyncResp infResp = client.execute(req,OrderFinAccountSyncResp.class);
//		if(!infResp.getError_code().equals(EcsOrderConsts.INF_RESP_CODE_0000))
//			result.setError_msg("错误编码：" + infResp.getError_code() + ";错误信息：" + infResp.getError_msg());
//		result.setError_code(infResp.getError_code());
		return result;
	}
	
}
