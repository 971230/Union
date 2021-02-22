package com.ztesoft.net.mall.core.action.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import params.onlinepay.req.OnlinePayCallbackReq;
import params.onlinepay.req.OnlinePayRedirectReq;
import params.onlinepay.req.OnlinePayReq;
import params.onlinepay.resp.OnlinePayCallbackResp;
import params.onlinepay.resp.OnlinePayRedirectResp;
import params.onlinepay.resp.OnlinePayResp;
import services.AbsCommonPay;
import services.OnlinePaymentInf;

import com.powerise.ibss.framework.Const;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.PaymentList;
import com.ztesoft.net.mall.core.service.IPaymentListManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.PayResult;
import commons.CommonTools;

/**
 * 公共支付action
 * @作者 MoChunrun
 * @创建日期 2013-12-3 
 * @版本 V 1.0
 */
public class CommonPaymentAction extends WWAction{

	private PayResult result;
	private String beanName;// = "orderPaymentServ";
	private String payment_cfg_id;
	private String bankId;
	private OnlinePaymentInf onlinePaymentServ;
	private IPaymentListManager paymentListManager;
	
	private Map<String,String> getParams(){
		Map<String,String> map = new HashMap<String,String>();
		Map<String,String[]> param = ServletActionContext.getRequest().getParameterMap();
		Iterator it = param.keySet().iterator();
		while(it.hasNext()){
			String key = (String) it.next();
			String [] ss = param.get(key);
			if(ss!=null && ss.length>0){
				map.put(key, ss[0]);
			}
		}
		return map;
	}
	
	private Map<String,String> getStreamParams(){
		Map<String, String> map = new HashMap<String, String>();
		BufferedReader reader = null;
//      StringBuilder sb = new StringBuilder("{\"bank_code\":\"03080000\",\"dt_order\":\"20140426221126\",\"money_order\":\"0.01\",\"no_order\":\"201404266553001299\",\"oid_partner\":\"201103171000000000\",\"oid_paybill\":\"2014042603065576\",\"pay_type\":\"3\",\"result_pay\":\"SUCCESS\",\"settle_date\":\"20140426\",\"sign\":\"T021G6OAEYpFRM/2rqnIjPntY3lav9BaiZZgHbbxbB0EeDa2V/DjNp+v0UFZzDqf55cpyjdth/4wemh9haYrK+fun+zYe2ugsSFlLoch0gI9JB0+CgX7ssXSk6bWN6IPkAzv/tfsXOCW18Cu/6IWqIscxJbR4pWrtobv2w4O174=\",\"sign_type\":\"RSA\"}");
		StringBuilder sb = new StringBuilder();
        try{
            reader = new BufferedReader(new InputStreamReader(ServletActionContext.getRequest().getInputStream(), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally{
            try{
                if (null != reader){
                    reader.close();
                }
            }catch (IOException e){}
        }
        logger.info("==========================>>> bankResp:" + sb.toString());
        if(null != sb && !sb.toString().equals("")){
        	map = JSONObject.fromObject(sb.toString());
            map.put("reqStr", sb.toString());
        }
		return map;
	}
	
	/**
	 * 跳转到支付页面
	 * @作者 MoChunrun
	 * @创建日期 2013-12-3 
	 * @return
	 */
	public String pay(){
		AbsCommonPay absPay = null;
		try{
			absPay = SpringContextHolder.getBean(beanName);
		}catch(Exception ex){
			json = "{result:0,message:'支付错误'}";
		}
		if(absPay!=null){
			try{
				Map<String,String> reqMap = getParams();
				absPay.init(reqMap);
				double money = absPay.getPayMoney();
				String orderid = absPay.getOrderId();
				OnlinePayReq preq = new OnlinePayReq();
				preq.setBean_name(beanName);
				preq.setBank_id(bankId);
				preq.setClient_ip(StringUtil.getIpAddr());
				preq.setOrder_id(orderid);
				preq.setPay_money(money);
				payment_cfg_id = reqMap.get("payment_cfg_id");
				preq.setPayment_cfg_id(payment_cfg_id);
				preq.setStaff_user_id(absPay.getPayToUserId());
				preq.setUserSessionId(CommonTools.getUserSessionId());
				preq.setType_code(absPay.getTypeCode());
				OnlinePayResp paramResp = onlinePaymentServ.goToPay(preq);
				absPay.payBusiness(paramResp.getTransaction_id(), paramResp.getOnline_flag(), money, orderid);
				Map<String,String> map = paramResp.getResultMap();
				String params = "";
				if("0".equals(paramResp.getError_code())){
					if("0".equals(paramResp.getOnline_flag())){
						if(map!=null){
							Iterator it = map.keySet().iterator();
							while(it.hasNext()){
								String key = (String) it.next();
								params += "{key:'"+key+"',value:'"+map.get(key)+"'},";
							}
							if(params.length()>1)
								params = params.substring(0,params.length()-1);
						}
					}
					json = "{result:1,message:'支付成功',onlineflag:'"+paramResp.getOnline_flag()+"',bankUrl:'"+paramResp.getBank_url()+"',attrs:["+params+"]}";
				}else{
					json = "{result:0,message:'支付失败'}";
				}
			}catch(RuntimeException e){
				e.printStackTrace();
				if(e.getMessage()!=null && !"".equals(e.getMessage())){
					json = "{result:0,message:'"+e.getMessage()+"'}";
				}else{
					json = "{result:0,message:'支付失败'}";
				}
			}catch(Exception ex){
				ex.printStackTrace();
				json = "{result:0,message:'支付失败'}";
			}
		}
		
		
		/*PaymentLog paymentLog = orderManager.qryNotPayPaymentLog(payType, orderId);
		if(paymentLog!=null){
			PayCfg payCfg = paymentManager.getPayConfig(paymentLog.getPay_method());
			if(payCfg!=null){
				PayReq req = new PayReq();
				req.setBankId(bankId);
				req.setObjectId(orderId);
				req.setPayType(payType);
				req.setPaySource("");
				req.setPaymentCfg(payCfg);
				req.setPaymentLog(paymentLog);
				//IBankPayment absOrderPayment = SpringContextHolder.getBean(payCfg.getType());
				try{
					PayResp resp = absOrderPayment.pay(req);
					if("0".equals(resp.getError_code())){
						String params = "";
						if("0".equals(resp.getPayCfg().getOnline_flag())){
							Map<String,String> map = resp.getBankParams();
							if(map!=null){
								Iterator it = map.keySet().iterator();
								while(it.hasNext()){
									String key = (String) it.next();
									params += "{key:'"+key+"',value:'"+map.get(key)+"'},";
								}
								if(params.length()>1)
									params = params.substring(0,params.length()-1);
							}
						}
						json = "{result:1,message:'支付成功',onlineflag:'"+resp.getPayCfg().getOnline_flag()+"',bankUrl:'"+resp.getPayCfg().getBank_adss()+"',attrs:["+params+"]}";
					}else{
						json = "{result:0,message:'支付失败'}";
					}
				}catch(Exception e){
					String msg = "支付失败";
					if(!StringUtil.isEmpty(e.getMessage()))msg = e.getMessage();
					json = "{result:0,message:'"+msg+"'}";
				}
			}else{
				//config没有找到
				json = "{result:0,message:'支付方式不存在'}";
			}
		}else{
			//没有找到支付单
			json = "{result:0,message:'没找到需要支付的订单'}";
		}*/
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(this.json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 银关回调
	 * @作者 MoChunrun
	 * @创建日期 2013-12-3 
	 * @return
	 */
	public String callback(){
		String msg = "error";
		HttpServletRequest request = ServletActionContext.getRequest();
		String servletName = request.getServletPath();
		String name = servletName.substring(servletName.indexOf("callback_")+"callback_".length(),servletName.indexOf(".do"));
		String transaction_id = request.getParameter(name)==null?"":request.getParameter(name);
		PaymentList paymentList = paymentListManager.getPaymentById(transaction_id);
		beanName = paymentList.getBean_name();
		AbsCommonPay absPay = null;
		try{
			absPay = SpringContextHolder.getBean(beanName);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(absPay!=null){
			try{
				Map<String,String> map = getParams();
				OnlinePayCallbackReq creq = new OnlinePayCallbackReq();
				creq.setTransaction_id(transaction_id);
				creq.setReqParams(map);
				OnlinePayCallbackResp cresp = onlinePaymentServ.payCallback(creq);
				PayResult result = cresp.getPayResult();
				absPay.init(map);
				boolean flag = absPay.callbackBusiness(transaction_id, result.getDealResult());
				if(flag){
					msg = result.getResultMsg();
				}
			}catch(RuntimeException e){
				e.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		//IBankPayment absOrderPayment = SpringContextHolder.getBean(payCfg.getType());
		/*try{
			PayCallbackReq req = new PayCallbackReq();
			req.setTransaction_id(transaction_id);
			req.setReqParams(map);
			PayCallbackResp resp = absOrderPayment.payCallback(req);
			msg = resp.getRespMsg();
		}catch(Exception ex){
			ex.printStackTrace();
		}*/
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 银通回调处理,连连科技
	 * 为了不影响其他版本功能，暂时没找到合适的区分方法,所以copy取流的方法
	 */
	public String stream_callback(){
		String msg = "error";
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String,String> map = getStreamParams();
		String servletName = request.getServletPath();
		String name = servletName.substring(servletName.indexOf("st_callback_") + "st_callback_".length(),servletName.indexOf(".do"));
		String transaction_id = Const.getStrValue(map, name);
		
		logger.info("=====================================>>>>transaction_id:" + transaction_id);
		String sourceFrom = ManagerUtils.getSourceFrom();
		PaymentList paymentList = paymentListManager.getPaymentById(transaction_id);
		beanName = paymentList.getBean_name();
		AbsCommonPay absPay = null;
		try{
			absPay = SpringContextHolder.getBean(beanName);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(absPay!=null){
			try{
				OnlinePayCallbackReq creq = new OnlinePayCallbackReq();
				creq.setTransaction_id(transaction_id);
				creq.setReqParams(map);
				OnlinePayCallbackResp cresp = onlinePaymentServ.payCallback(creq);
				PayResult result = cresp.getPayResult();
				absPay.init(map);
				boolean flag = absPay.callbackBusiness(transaction_id, result.getDealResult());
				if(flag){
					msg = result.getResultMsg();
				}
			}catch(RuntimeException e){
				e.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			logger.info("==========================>>> msg:" + msg);
			response.getWriter().print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 银联返回
	 * @作者 MoChunrun
	 * @创建日期 2013-12-3 
	 * @return
	 */
	public String redirect(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String servletName = request.getServletPath();
		logger.info("com.ztesoft.net.mall.core.action.backend.CommonPaymentAction.redirect()->"+"servletName:"+servletName);
		String name = servletName.substring(servletName.indexOf("redirect_")+"redirect_".length(),servletName.indexOf(".do"));
		logger.info("com.ztesoft.net.mall.core.action.backend.CommonPaymentAction.redirect()->"+"name:"+name);
		String transaction_id = request.getParameter(name)==null?"":request.getParameter(name);
		PaymentList paymentList = paymentListManager.getPaymentById(transaction_id);
		beanName = paymentList.getBean_name();
		AbsCommonPay absPay = null;
		try{
			absPay = SpringContextHolder.getBean(beanName);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(absPay!=null){
			try{
				Map<String,String> map = getParams();
				OnlinePayRedirectReq rreq = new OnlinePayRedirectReq();
				rreq.setTransaction_id(transaction_id);
				rreq.setReqParams(map);
				OnlinePayRedirectResp rresp = onlinePaymentServ.payRedirect(rreq);
				absPay.init(map);
				boolean flag = absPay.redirectBusiness(transaction_id, rresp.getResult().getDealResult());
				result = rresp.getResult();
			}catch(Exception ex){
				result = new PayResult();
				result.setDealResult(-1);
				result.setResultMsg("失败");
			}
			result.setMoney(StringUtils.fen2Yuan(String.valueOf(paymentList.getPay_amount())));
			result.setTransaction_id(transaction_id);
		}
		
		/*Map<String,String> map = getParams();
		try{
			//IBankPayment absOrderPayment = SpringContextHolder.getBean(payCfg.getType());
			PayRedirectReq req = new PayRedirectReq();
			req.setTransaction_id(transaction_id);
			req.setParams(map);
			PayRedirectResp resp = absOrderPayment.payRedirect(req);
			if("0".equals(resp.getError_code())){
				result = resp.getResult();
			}else{
				result = new PayResult();
				result.setDealResult(-1);
				result.setResultMsg("失败");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			result = new PayResult();
			result.setDealResult(-1);
			result.setResultMsg("失败");
		}*/
		return "pay_redirect";
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public PayResult getResult() {
		return result;
	}

	public void setResult(PayResult result) {
		this.result = result;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public OnlinePaymentInf getOnlinePaymentServ() {
		return onlinePaymentServ;
	}

	public void setOnlinePaymentServ(OnlinePaymentInf onlinePaymentServ) {
		this.onlinePaymentServ = onlinePaymentServ;
	}

	public IPaymentListManager getPaymentListManager() {
		return paymentListManager;
	}

	public void setPaymentListManager(IPaymentListManager paymentListManager) {
		this.paymentListManager = paymentListManager;
	}

	public String getPayment_cfg_id() {
		return payment_cfg_id;
	}

	public void setPayment_cfg_id(String payment_cfg_id) {
		this.payment_cfg_id = payment_cfg_id;
	}
	
}
