package services;

import java.lang.reflect.InvocationTargetException;

import params.ZteError;
import params.order.req.OrderSyReq;
import params.order.resp.OrderSyResp;
import utils.CoreThreadLocalHolder;

import com.ztesoft.api.consts.ApiConsts;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.rule.SyOrderRule;
import commons.CommonTools;


/**
 * 订单同步服务接口
* @作者 MoChunrun 
* @创建日期 2013-9-24 
* @版本 V 1.0
 */
public class OrderSyServ extends ServiceBase implements OrderSyInf{

	@Override
	public OrderSyResp syNOrder(OrderSyReq syReq){
		try {
			 SyOrderRule sy = SpringContextHolder.getBean("syOrderRule");
			 return sy.syNOrder(syReq);
		} catch (Throwable e) {
			//add by wui异常改造处理
			e.printStackTrace();
			ZteError et =  CoreThreadLocalHolder.getInstance().getZteCommonData().getZteError();
			if(et!=null){
				CommonTools.addError(et);
			}else{
				 if (e instanceof InvocationTargetException) {
		             InvocationTargetException inve = (InvocationTargetException) e;
		             CommonTools.addError(new ZteError(ApiConsts.ERROR_FAIL,inve.getTargetException().getMessage()));
		         }else{
	                 CommonTools.addError(new ZteError(ApiConsts.ERROR_FAIL,e.getMessage()));
		         }
			}
		}
		return null;
	}
	
}