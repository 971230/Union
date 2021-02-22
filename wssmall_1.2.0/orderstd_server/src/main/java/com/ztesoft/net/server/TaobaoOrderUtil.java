package com.ztesoft.net.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import params.req.TaoBaoMallOrderStandardReq;
import params.resp.TaoBaoMallOrderStandardResp;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.iservice.IEcsServices;
import zte.net.iservice.IOrderstdService;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;

import com.taobao.api.domain.Trade;
import com.taobao.api.response.TradeGetResponse;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.outter.inf.model.OuterError;
import commons.CommonTools;

import consts.ConstsCore;

public class TaobaoOrderUtil extends AbsTaobaoService{
	private static Logger logger = Logger.getLogger(TaobaoOrderUtil.class);
	private static IOrderstdService orderstdService;
	private IEcsServices ecsServices ;
	
	public IEcsServices getEcsServices(){
		return ecsServices;
	}
	public void setEcsServices(IEcsServices ecsServices){
		this.ecsServices = ecsServices;
	}
	
	public TaoBaoMallOrderStandardResp getOrderContent(TaoBaoMallOrderStandardReq req) throws Exception{
		orderstdService = SpringContextHolder.getBean("orderstdService");
		TaoBaoMallOrderStandardResp resp =  new TaoBaoMallOrderStandardResp();
		Map param = JsonUtil.fromJson(req.getParams(), Map.class);
		Map dyn_field = req.getDyn_field();
		String order_from = "";
		if (dyn_field != null) {
			order_from = dyn_field.get("order_from")==null?order_from:dyn_field.get("order_from").toString();
		}
		String req_content = getReqContent(req.getBase_co_id(),req.isIs_exception());
		logger.info("报文==================="+req_content);
		if(StringUtils.isEmpty(req_content)){
			OuterError ng = new OuterError("", "", "", "", order_from, req.getTaobaoOrderId()+"", "sysdate","orderinfoerror");
			outterStdTmplManager.insertOuterError(ng);
			resp.setError_msg("未获取到请求报文");
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		List<Outer> out_order_List = executeInfService(req_content,param,order_from,req.getBase_co_id());
        if(out_order_List==null||out_order_List.size()==0){
        	
			resp.setError_msg("同步失败,接口报文为空.");
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("同步订单成功");
		resp.setOut_order_List(out_order_List);
		return resp;
	}
    //获取归集系统请求报文
	private static  String getReqContent(String co_id,boolean is_exception) {
			String content = "";
			OrderQueueBusiRequest orderQueue = new OrderQueueBusiRequest();
			if(is_exception){
				orderQueue = CommonDataFactory.getInstance().getOrderQueueFor(null, co_id);
				logger.info("从失败队列获取请求报文============es_order_queue_fail");
			}else{
				orderQueue = CommonDataFactory.getInstance().getOrderQueue(null, co_id);
				logger.info("从正常队列获取请求报文============es_order_queue");
			}
			if(orderQueue!=null){
				content = orderQueue.getContents();
			}
			return content;
	}

	public List<Outer> executeInfService(String req_content,Map param,String order_from,String co_id){
		orderstdService = SpringContextHolder.getBean("orderstdService");
		String inJson = req_content;
		List<Outer> out_order_List = new ArrayList<Outer>();
		try{
		
			Trade t = CommonTools.jsonToBean(inJson, Trade.class);
			
			String appkey = String.valueOf(param.get("appkey"));
			String secret = String.valueOf(param.get("secret"));
			String sessionKey = String.valueOf(param.get("sessionKey"));
			String url = String.valueOf(param.get("url"));
			String jushitaUrl = param.get("jushitaUrl")==null?"":param.get("jushitaUrl").toString();
			String jushitaAppId = param.get("jushitaAppId")==null?"":param.get("jushitaAppId").toString();
			String isJushita = param.get("isJushita")==null?"":param.get("isJushita").toString();
			StringBuffer sb = new StringBuffer();
			String strParams = sb.append("{").append("appkey:'").append(appkey)
					.append("',secret:'").append(secret)
					.append("',sessionKey:'").append(sessionKey)
					.append("',url:'").append(url)
					.append("',jushitaUrl:'").append(jushitaUrl)
					.append("',jushitaAppId:'").append(jushitaAppId)
					.append("',isJushita:'").append(isJushita).append("'").append("}").toString();
			
			String end_time = DF.format(new Date());
			if("SELLER_CONSIGNED_PART".equals(t.getStatus())
					|| "WAIT_SELLER_SEND_GOODS".equals(t.getStatus()) || "WAIT_BUYER_CONFIRM_GOODS".equals(t.getStatus())
					|| "TRADE_BUYER_SIGNED".equals(t.getStatus()) || "TRADE_FINISHED".equals(t.getStatus())){
				TradeGetResponse tradeInfo = null;
				try{
					tradeInfo = getTrade(t.getTid(),strParams);
				}catch(Exception ex){
					OuterError ng = new OuterError("", "", "", "", order_from, t.getTid()+"", "sysdate","orderinfoerror");
					outterStdTmplManager.insertOuterError(ng);
				}
				if(tradeInfo==null){
					logger.info("订单"+t.getTid()+"未获取到其余信息");
					return out_order_List;
				}
				Outer o = packageTaobaoOrder(t, end_time,strParams,order_from,tradeInfo,false,null,null);
				if(o!=null){
					logger.info("----淘宝单"+o.getOut_tid()+"报文转订单成功------");
					out_order_List.add(o);
					
				}
			}
		}catch (Exception e) {		
			logger.info(e.getMessage(), e);
		}
		return out_order_List;
	}
	@Override
	public void callback(List<Outer> list) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Outer> executeInfService(String start_time, String end_time,
			Map params, String order_from) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}