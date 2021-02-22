package com.ztesoft.remote.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import params.ZteRequest;
import services.ServiceBase;
import zte.net.iservice.ISysBaseService;
import zte.net.iservice.params.bill.req.QryEopBillReq;
import zte.net.iservice.params.bill.req.QryLLPBillReq;
import zte.net.iservice.params.bill.req.QryNetTraReq;
import zte.net.iservice.params.bill.resp.QryEopBillResp;
import zte.net.iservice.params.bill.resp.QryLLPBillResp;
import zte.net.iservice.params.bill.resp.QryNetTraResp;
import zte.net.iservice.params.goodsOrg.req.GoodsOrgReq;
import zte.net.iservice.params.goodsOrg.resp.GoodsOrgResp;
import zte.net.iservice.params.serv.req.AcceptServReq;
import zte.net.iservice.params.serv.req.QryNetProReq;
import zte.net.iservice.params.serv.req.QryOffDetReq;
import zte.net.iservice.params.serv.resp.AcceptServResp;
import zte.net.iservice.params.serv.resp.QryNetProResp;
import zte.net.iservice.params.serv.resp.QryOffDetResp;
import zte.net.iservice.params.sms.req.SendMsgReq;
import zte.net.iservice.params.sms.req.SendOfflineMsgReq;
import zte.net.iservice.params.sms.resp.SendMsgResp;
import zte.net.iservice.params.sms.resp.SendOfflineMsgResp;
import zte.net.iservice.params.user.req.AuthPwdReq;
import zte.net.iservice.params.user.req.QryCustInfoReq;
import zte.net.iservice.params.user.req.QryCustTypeReq;
import zte.net.iservice.params.user.req.ResetPwdReq;
import zte.net.iservice.params.user.req.UserLoginReq;
import zte.net.iservice.params.user.resp.AuthPwdResp;
import zte.net.iservice.params.user.resp.QryCustInfoResp;
import zte.net.iservice.params.user.resp.QryCustTypeResp;
import zte.net.iservice.params.user.resp.ResetPwdResp;
import zte.net.iservice.params.user.resp.UserLoginResp;
import zte.params.order.req.OrderAddReq;
import zte.params.order.resp.OrderAddResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.mall.core.cache.common.SysNetCacheRead;
import com.ztesoft.net.mall.core.model.GoodsOrg;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version="1.0")
public class SysBaseService extends ServiceBase implements ISysBaseService {
	
	@Resource
	private SysNetCacheRead sysNetCacheRead;
	
	@Override
	public UserLoginResp userLogin(UserLoginReq loginReq) {
		return (UserLoginResp)this.getOrderResp(loginReq).getZteResponse();
	}

	@Override
	public SendOfflineMsgResp sendOfflineMsg(SendOfflineMsgReq sendOfflineMsgReq) {
		return (SendOfflineMsgResp)this.getOrderResp(sendOfflineMsgReq).getZteResponse();
	}

	@Override
	public QryCustInfoResp qryCustInfo(QryCustInfoReq qryCustInfoReq) {
		return (QryCustInfoResp)this.getOrderResp(qryCustInfoReq).getZteResponse();
	}

	@Override
	public QryCustTypeResp qryCustType(QryCustTypeReq qryCustTypeReq) {
		return (QryCustTypeResp)this.getOrderResp(qryCustTypeReq).getZteResponse();
	}

	@Override
	public QryNetTraResp qryNetTra(QryNetTraReq qryNetTraReq) {
		return (QryNetTraResp)this.getOrderResp(qryNetTraReq).getZteResponse();
	}

	@Override
	public QryEopBillResp qryEopBill(QryEopBillReq qryEopBillReq) {
		return (QryEopBillResp)this.getOrderResp(qryEopBillReq).getZteResponse();
	}

	@Override
	public QryOffDetResp qryOffDet(QryOffDetReq qryOffDetReq) {
		return (QryOffDetResp)this.getOrderResp(qryOffDetReq).getZteResponse();
	}

	@Override
	public QryNetProResp qryNetPro(QryNetProReq qryNetProReq) {
		return (QryNetProResp)this.getOrderResp(qryNetProReq).getZteResponse();
	}

	@Override
	public AuthPwdResp authPwd(AuthPwdReq authPwdReq) {
		return (AuthPwdResp)this.getOrderResp(authPwdReq).getZteResponse();
	}

	@Override
	public ResetPwdResp resetPwd(ResetPwdReq resetPwdReq) {
		return (ResetPwdResp)this.getOrderResp(resetPwdReq).getZteResponse();
	}

	@Override
	public QryLLPBillResp qryLlpBill(QryLLPBillReq qryLLPBillReq) {
		return (QryLLPBillResp)this.getOrderResp(qryLLPBillReq).getZteResponse();
	}

	@Override
	public AcceptServResp acceptServ(AcceptServReq acceptServReq) {
		return (AcceptServResp)this.getOrderResp(acceptServReq).getZteResponse();
	}

	@Override
	public SendMsgResp sendMsg(SendMsgReq sendMsgReq) {
		return (SendMsgResp)this.getOrderResp(sendMsgReq).getZteResponse();
	}
	
	/**
	 * 调用订单规则
	 * @param request
	 * @return
	 */
	private OrderAddResp getOrderResp(ZteRequest request){
		
		OrderAddReq orderAddReq = new OrderAddReq();
		OrderAddResp resp = new OrderAddResp();
		List<Map> paramsl =new ArrayList<Map>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		try{
			
			Class reqClass = request.getClass();
			Method reqProduct = reqClass.getMethod("getProduct_id");
			String product_id = (String)reqProduct.invoke(request);
			
			map.put("product_id", product_id); //所定义的充值商品
			map.put("userid", "1");
			map.put("app_code", request.getAppKey());
			paramsl.add(map);
			
			Method reqCode = reqClass.getMethod("getService_code");
			String service_code = (String)reqCode.invoke(request);
			orderAddReq.setService_code(service_code);			//所定义的编码 es_service_offer表
			
			orderAddReq.setParamsl(paramsl);
			orderAddReq.setZteRequest(request);
			ZteClient zteClient = ClientFactory.getZteDubboClient(request.getSourceFrom() == null ? ManagerUtils.getSourceFrom() : request.getSourceFrom());
			resp = zteClient.execute(orderAddReq, OrderAddResp.class);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return resp;
	}

	public SysNetCacheRead getSysNetCacheRead() {
		return sysNetCacheRead;
	}

	public void setSysNetCacheRead(SysNetCacheRead sysNetCacheRead) {
		this.sysNetCacheRead = sysNetCacheRead;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "根据活动标识获取活动信息", summary = "根据活动标识获取活动信息")
	public GoodsOrgResp queryGoodsOrg(GoodsOrgReq goodsOrgReq) {
		GoodsOrgResp resp = new GoodsOrgResp();
		try{
			GoodsOrg goodsOrg = sysNetCacheRead.getCachesGoodsOrg(goodsOrgReq.getParty_id());
			resp.setGoodsOrg(goodsOrg);
			resp.setError_code("0");
		}catch(Exception e){
			e.printStackTrace();
		}

		return resp;
	}

}
