package com.ztesoft.net.aspect;
import java.lang.reflect.InvocationTargetException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import params.ZteRequest;
import params.ZteResponse;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.OrderConsts;
import com.ztesoft.net.mall.core.consts.OrderQueueConsts;
import com.ztesoft.net.mall.core.service.IOrderQueueStdServices;
import consts.ConstsCore;

/**
 * @Description  MQ切面处理类。处理队列表状态和保存日志
 * @author  wanpeng
 * @date    2015-12-05
 * @version 1.0
 */
@Component
@Aspect
public class MqApiAspect{
	
	private IOrderQueueStdServices orderQueueStdServices;
	private void initBean(){
		if(orderQueueStdServices ==null)
			orderQueueStdServices = SpringContextHolder.getBean("orderQueueStdServices");
	}
	// 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Pointcut("execution(* com.ztesoft.api.mq.ExcuteReceiveMsg.executeDubbo(..))")
	public void aspect() {}
 
	
	// 配置后置返回通知,使用在方法aspect()上注册的切入点
	@AfterReturning(pointcut="aspect()", returning="retVal")
	public void afterReturn(JoinPoint joinPoint, ZteResponse retVal) {
		try{
			Object[] args = joinPoint.getArgs();
			ZteRequest request = (ZteRequest) (args[0]);
			if(null==request){return;}
			this.upateorderQueueState(request,retVal);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Before("aspect()")
	public void before(JoinPoint joinPoint) {
		//utils.SystemUtils.printLog("进来切面before");
	}


	// 配置抛出异常后通知,使用在方法aspect()上注册的切入点
	@AfterThrowing(pointcut = "aspect()", throwing = "ex")
	public void afterThrow(JoinPoint joinPoint,Throwable ex) throws IllegalAccessException, InvocationTargetException {
		//utils.SystemUtils.printLog("进来切面afterThrow");
	}
	
	
	/**
	 * MQ消费完后更新队列表的记录状态为'已处理'
	* @Description: 
	* @param    
	* @return void    
	* @throws
	 */
	private void upateorderQueueState(ZteRequest request, ZteResponse resp) {
		String order_id = request.getBase_order_id();
		String co_id = request.getBase_co_id();
		//add by wui暂时先移走,做性能调优,待调整后在提供整体方案
//		if(!"zte.service.orderqueue.orderCreate".equals(request.getApiMethodName())){
			if (!StringUtils.isEmpty(co_id)) {
				initBean();
				OrderQueueBusiRequest orderQueueBusiRequest = orderQueueStdServices.queryOrderQueueBusiRequest(order_id, co_id);
				if (null !=orderQueueBusiRequest && co_id.equals(orderQueueBusiRequest.getCo_id())) {
					String service_code = orderQueueBusiRequest.getService_code();
					String deal_desc = "";
					if (null != resp) {
						deal_desc = resp.getError_msg();
					}
					orderQueueBusiRequest.setDeal_desc(deal_desc);
					
					// MQ写历史记录，淘宝订单，写历史，如果成功标记 deal_code 为succeed 如果淘宝失败则标记未equal_succeed 
					if ((resp != null && ConstsCore.ERROR_SUCC.equals(resp.getError_code())) 
							|| OrderConsts.SERVICE_CODE_TAOBAOMALLORDERSTANDARD.equals(service_code)) {
						if (resp != null && ConstsCore.ERROR_SUCC.equals(resp.getError_code())) {
							orderQueueBusiRequest.setDeal_code(OrderQueueConsts.DEAL_SUCCEED);
						} else {
							orderQueueBusiRequest.setDeal_code(OrderQueueConsts.DEAL_EQUAL_SUCCEED);
						}
						try {
							orderQueueStdServices.saveOrderQueueToHis(orderQueueBusiRequest);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					} else {

						// 非淘宝订单MQ消费失败，写队列失败表
						orderQueueBusiRequest.setDeal_code(OrderQueueConsts.DEAL_FAIL);
						String error_stack_msg = "";
						if(resp!=null&&resp.getError_stack_msg()!=null&&!"".equals(resp.getError_stack_msg())){
							error_stack_msg = resp.getError_stack_msg();
							if(error_stack_msg.length()>1000){
								error_stack_msg = error_stack_msg.substring(0,1000);
							}
						}
						orderQueueBusiRequest.setDeal_desc("MQ消费失败！"+error_stack_msg);
						try {
							orderQueueStdServices.saveOrderQueueToFail(orderQueueBusiRequest);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
	
				}
			}
//		}
	}
	
	/**
	 * 写队列异常单系统
	 * @param co_id
	 * @return
	 */
//	public OrderExpWriteResp orderExpWrite(String co_id,ZteResponse resp) {
//		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
//		OrderExpWriteForBusReq req = new OrderExpWriteForBusReq();
//		req.setObj_id(co_id); //队列id
//		req.setObj_type("order_queue"); //对象类型（order、order_queue）
//		req.setSearch_id(EcsOrderConsts.EXP_STANDARD_CHECK_id); //搜索id
//		req.setSearch_code(EcsOrderConsts.EXP_STANDARD_CHECK_CODE);//搜索编码
//		if(null != resp){
//			req.setError_msg(resp.getError_stack_msg());//错误堆栈
//		}else{
//			req.setError_msg("MQ转dubbo执行调用出错,无返回对象");//错误堆栈
//		}
//		OrderExpWriteResp orderExpWriteResp = client.execute(req, OrderExpWriteResp.class);
//		return orderExpWriteResp;
//	}
	
}
