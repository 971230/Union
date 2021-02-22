package com.ztesoft.net.mall.plugin.payment.deposit;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.PayCfg;
import com.ztesoft.net.mall.core.plugin.payment.AbstractPaymentPlugin;
import com.ztesoft.net.mall.core.plugin.payment.IPaymentEvent;

/**
 * 预存款支付
 * @author kingapex
 * 2010-5-26下午02:29:09
 */
public class DepositPlugin extends AbstractPaymentPlugin implements IPaymentEvent {
	
	
	
	@Override
	public String onCallBack() {
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		String ordersn = request.getParameter("ordersn");
		
		try{
			this.paySuccess(ordersn);
		}catch(RuntimeException e){
			if(this.logger.isDebugEnabled()){
				this.logger.debug(e.getMessage(),e);
			}
			return e.getMessage();
		}
		
		return "支付成功";
	}

	
	@Override
	public String onPay(PayCfg payCfg, Order order) {
		IUserService userService = UserServiceFactory.getUserService();
		Member member  = userService.getCurrentMember();
		if(member== null ){
			 
			String url="";
			try {
				url = URLEncoder.encode("widget?type=paywidget&orderid="+order.getOrder_id()+"&paymentid="+payCfg.getId(),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		 
			return "<script>location.href='user_login.html?forward="+url+"'</script>";
		}
		
		return "<script>location.href='"+this.getReturnUrl(payCfg)+"?ordersn="+order.getSn()+"';</script>正在支付...";
	
	}

	
	@Override
	public String getAuthor() {
		
		return "kingapex";
	}

	
	@Override
	public String getId() {
		
		return "deposit";
	}

	
	@Override
	public String getName() {
		
		return "预存款支付";
	}

	
	@Override
	public String getType() {
		
		return "payment";
	}

	
	@Override
	public String getVersion() {
		
		return "1.0";
	}

	
	@Override
	public void perform(Object... params) {
		

	}
	
	@Override
	public void register() {

	}

	@Override
	public String onReturn() {
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		String ordersn = request.getParameter("ordersn");
		
		
		try{
			this.paySuccess(ordersn);
		}catch(RuntimeException e){
			if(this.logger.isDebugEnabled()){
				this.logger.debug(e.getMessage(),e);
				throw new RuntimeException(e.getMessage());
			}
			return e.getMessage();
		}
		
		return ordersn;
	}


}
