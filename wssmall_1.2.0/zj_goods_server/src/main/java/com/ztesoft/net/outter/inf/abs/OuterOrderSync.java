package com.ztesoft.net.outter.inf.abs;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import zte.net.iservice.IOrderServices;
import zte.params.order.req.OrderStandardPushReq;
import zte.params.order.resp.OrderStandardPushResp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.outter.inf.iservice.IOrderSyncService;
import com.ztesoft.net.outter.inf.iservice.IOutterECSTmplManager;
import com.ztesoft.net.outter.inf.iservice.OuterInf;
import com.ztesoft.net.outter.inf.model.OutterTmpl;

/**
 * 订单同步基类  同步各系统的service必须继承该类
 * @作者 MoChunrun
 * @创建日期 2014-3-13 
 * @版本 V 1.0
 */
public class OuterOrderSync extends BaseSupport implements IOrderSyncService {
	
	public static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	protected IOutterECSTmplManager outterECSTmplManager;
	protected IOrderServices orderServices;
	
	/**
	 * 执行调用接口方法
	 * @作者 MoChunrun
	 * @创建日期 2014-3-13 
	 * @param start_time 调接口获取数据的开始时间 yyyy-MM-dd HH:mm:ss (第一次调用可为空。
	 * @param end_time 调接口获取数据的结束时间 yyyy-MM-dd HH:mm:ss 不能为空（为当前时间）。
	 * @return
	 */
	//public abstract List<? extends AbsOrder> executeInfService(String start_time,String end_time,Map params) throws Exception;
	
	/**
	 * 封装受理对象
	 * @作者 MoChunrun
	 * @创建日期 2014-3-13 
	 * @param order
	 * @return
	 */
	//public abstract List<? extends AbsOrderAccept> packageAccept(AbsOrder order,OrderOuter outer);
	
	/**
	 * 执行完每一条记录后会执行该方法实现各系统接口的业务处
	 * @作者 MoChunrun
	 * @创建日期 2014-3-13 
	 * @param order
	 * @param outer_order_accept
	 */
	//public abstract void row_business(OrderCollect outerOrderAndAccept);
	
	/**
	 * 执行完所有任务后执行该方法
	 * @作者 MoChunrun
	 * @创建日期 2014-3-13 
	 * @param outerAndacceptList
	 * @param infOrders
	 */
	//public abstract void end_business(List<OrderCollect> outerAndacceptList);
	
	/**
	 * 是否检查外系统订单是否存在，如果存在侧不同步
	 * @作者 MoChunrun
	 * @创建日期 2014-4-3 
	 * @return
	 */
	//public abstract boolean checkOrderExists();
	
	
	/**
	 * 执行接口
	 * @作者 MoChunrun
	 * @创建日期 2014-3-12 
	 * @param order_from
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void perform(OutterTmpl tmpl) throws Exception{
		logger.info("=====["+tmpl.getOrder_from()+"]订单执行开始============");
		//Date now = DF.parse(DBTUtil.getDBCurrentTime());
		String data_end_time = DBTUtil.getDBCurrentTime();//DF.format(new Date());
		String start_time = null;
		if(!StringUtils.isEmpty(tmpl.getData_end_time()))
			start_time = tmpl.getData_end_time().substring(0,19);
		if(-1==tmpl.getExecute_min().intValue()){
			start_time = tmpl.getLast_execute().substring(0,19);
			data_end_time = tmpl.getData_end_time().substring(0,19);
		}
		try{
			Map params = null;
			if(!StringUtils.isEmpty(tmpl.getParams())){
				try{
					params = JSON.parseObject(tmpl.getParams(), Map.class);
				}catch(Exception ex){
					throw new Exception("参数配置有误");
				}
			}
			OuterInf service = SpringContextHolder.getBean(tmpl.getBean_name());
			List<Outer> list = service.executeInfService(start_time, data_end_time, params,tmpl.getOrder_from());
			int sync_num = 0;
			if(list!=null && list.size()>0){
				sync_num = list.size();
				//ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
				OrderStandardPushReq req = new OrderStandardPushReq();
				req.setOuterList(list);
				OrderStandardPushResp resp = orderServices.pushOrderStandard(req);
				//OrderStandardPushResp resp = client.execute(req, OrderStandardPushResp.class);
				//outerService.perform(list);
			}
			service.callback(list);
			if(-1==tmpl.getExecute_min().intValue()){
				outterECSTmplManager.updateRunStatus("00E", "success",sync_num, tmpl.getTmpl_id());
			}else{
				outterECSTmplManager.updateTmplExecuteInfo(tmpl.getTmpl_id(), sync_num, 0, tmpl.getExecute_min(), data_end_time,"success");
			}
			logger.info("=====["+tmpl.getOrder_from()+"]订单执行完成============");
		}catch(Exception ex){
			ex.printStackTrace();
			logger.info("=====["+tmpl.getOrder_from()+"]订单执行失败============");
			throw new RuntimeException("["+tmpl.getOrder_from()+"]订单执行失败");
		}
	}
	
	
	/**
	 * 首字母转大写
	 * @作者 MoChunrun
	 * @创建日期 2014-2-24 
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFirstOne(String s) {
		if(Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return(new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	
	/**
	 * 获取对象所有属性
	 * @作者 MoChunrun
	 * @创建日期 2014-3-12 
	 * @param obj
	 * @return
	 */
	public static PropertyDescriptor[] getObjectProperty(Object obj) throws IntrospectionException{
		return getClassProperty(obj.getClass());
	}
	
	public static PropertyDescriptor[] getClassProperty(Class clazz) throws IntrospectionException{
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz); // 获取类属性
		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		return propertyDescriptors;
	}
	
	public static void invote(PropertyDescriptor[] propertyDescriptors,String proName,Object value,Object obj){
		if(value==null)return ;
		for(PropertyDescriptor property:propertyDescriptors){
			String mName = property.getName();
			if(mName.equalsIgnoreCase(proName)){
				Class propertyType = property.getPropertyType();
				Object propValue = value;
				if (propertyType.getName().equals("boolean") || propertyType.getName().equals("java.lang.Boolean")) {
					propValue = Boolean.valueOf(String.valueOf(propValue));
				}else if (propertyType.getName().equals("int") || propertyType.getName().equals("java.lang.Integer")) {
					propValue = Integer.valueOf(String.valueOf(propValue));
				}else if (propertyType.getName().equals("long") || propertyType.getName().equals("java.lang.Long")) {
					propValue = Long.valueOf(String.valueOf(propValue));
				}else if (propertyType.getName().equals("float") || propertyType.getName().equals("java.lang.Float")) {
					propValue = Float.valueOf(String.valueOf(propValue));
				}else if (propertyType.getName().equals("double") || propertyType.getName().equals("java.lang.Double")) {
					propValue = Double.valueOf(String.valueOf(propValue));
				}else if (propertyType.getName().equals("java.lang.String")) {
					propValue = String.valueOf(propValue);
				}
				try{
				if (propValue != null && property.getWriteMethod() !=null)
					property.getWriteMethod().invoke(obj,new Object[] { propValue });
				}catch(Exception ex){
					ex.printStackTrace();
				}
				break ;
			}
		}
	}
	
	public static String beanToJson(Object src){
		SerializerFeature[] features = { SerializerFeature.WriteMapNullValue, // 输出空置字段
			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullStringAsEmpty  // 字符类型字段如果为null，输出为""，而不是null
		};
		return JSON.toJSONString(src,features);
	}


	public IOrderServices getOrderServices() {
		return orderServices;
	}


	public void setOrderServices(IOrderServices orderServices) {
		this.orderServices = orderServices;
	}


	public IOutterECSTmplManager getOutterECSTmplManager() {
		return outterECSTmplManager;
	}


	public void setOutterECSTmplManager(IOutterECSTmplManager outterECSTmplManager) {
		this.outterECSTmplManager = outterECSTmplManager;
	}
	
	/*public void field(Object obj){
		PropertyDescriptor[] propertyDescriptors = getObjectProperty(obj);
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (paramMap.containsKey(propertyName)) {
				Class propertyType = descriptor.getPropertyType();
				Object propValue = paramMap.get(propertyName);
				if (propValue == null)
					continue;
				if (propertyType.getName().equals("boolean")) {
					propValue = Boolean.valueOf((String) propValue);
				}
				if (propertyType.getName().equals("int")) {
					propValue = Integer.valueOf((String) propValue);
				}
				if (propertyType.getName().equals("long")) {
					propValue = Long.valueOf((String) propValue);
				}
				if (propertyType.getName().equals("float")) {
					propValue = Float.valueOf((String) propValue);
				}
				if (propertyType.getName().equals("double")) {
					propValue = Double.valueOf((String) propValue);
				}
				try {
					if (propValue != null && descriptor.getWriteMethod() !=null)
						descriptor.getWriteMethod().invoke(object,new Object[] { propValue });
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}*/
	
}
