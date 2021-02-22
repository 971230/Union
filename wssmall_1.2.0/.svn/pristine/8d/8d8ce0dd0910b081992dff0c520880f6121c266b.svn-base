package com.ztesoft.net.auto.rule.parser;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import org.apache.log4j.Logger;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.rule.workflow.WorkFlowFact;

import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.i.IAutoRule;
import com.ztesoft.net.auto.rule.vo.RuleConsts;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.RuleCondCache;
import com.ztesoft.net.rule.fact.OrderPageFact;

public class ExpressHelper implements IExpressHelper{
	private static Logger logger = Logger.getLogger(ExpressHelper.class);
	public static final Map<String,String> ruleExpress = new HashMap<String,String>();
	public static final String NEVER_RUN_COND = "never_run_cond";//始终不执行
	public static final String WITHOUT_COND = "without_cond";//始终执行
	
	public static final Map<String,Method> FACT_METHOD = new HashMap<String,Method>();
	
	public static final Map<String,FastMethod> FAST_FACT_METHOD = new HashMap<String,FastMethod>();
	
	//表达式
	private String express;
	//参数
	private List<Variable> variables;
	
	private List<RuleCondCache> condList;
	private AutoFact fact;
	private IAutoRule autoRuleImpl;
	private String rule_id;
	
	public ExpressHelper(String rule_id, AutoFact fact) throws Exception {
		super();
//		this.rule_id = rule_id;
//		//logger.info("规则ID==================>>"+rule_id);
//		autoRuleImpl = SpringContextHolder.getBean("autoRuleImpl");
//		//autoRuleImpl.refershCache();
//		this.condList = autoRuleImpl.getRuleCond(rule_id);
//		this.fact = fact;
//		if("201507225220000400".equals(rule_id))
//			logger.info("=========");
//		express = ruleExpress.get(rule_id);
//		if(true){
//			express = parseRuleExpress(condList);
//			ruleExpress.put(rule_id, express);
//		}
//		logger.info("规则【"+this.fact.getRule().getRule_id()+"】IK表达式=======================>>"+express);
//		long start = System.currentTimeMillis();
//		variables = packageVariable(condList, fact);
//		long end = System.currentTimeMillis();
//		logger.info("IK封装参数时间=====================>>"+(end-start));
		
		if("notneedexec".equals(fact.getRule().getRule_script())){
			this.rule_id = rule_id;
			this.fact = fact;
			if(autoRuleImpl ==null)
				autoRuleImpl = SpringContextHolder.getBean("autoRuleImpl");
			this.condList = autoRuleImpl.getRuleCond(rule_id);
		}else{
			this.rule_id = rule_id;
			//logger.info("规则ID==================>>"+rule_id);
			if(autoRuleImpl ==null)
				autoRuleImpl = SpringContextHolder.getBean("autoRuleImpl");
			//autoRuleImpl.refershCache();
			this.condList = autoRuleImpl.getRuleCond(rule_id);
			this.fact = fact;
			//if("201410254964000164".equals(rule_id))
			//	logger.info("=========");
//			express = ruleExpress.get(rule_id);
//			if(StringUtil.isEmpty(express)){
//				if("201412177509000441".equals(rule_id))
//					logger.info("1111");
			express = parseRuleExpress(condList);
//			ruleExpress.put(rule_id, express);
//			}
			if(fact instanceof TacheFact){
				TacheFact tacheFact =(TacheFact)fact;
				tacheFact.removeOrderTree();
			}else if(fact instanceof OrderPageFact){
				OrderPageFact pageFact =(OrderPageFact)fact;
				pageFact.removeOrderTree();
			}else  if(fact instanceof WorkFlowFact){
				WorkFlowFact workFlowFact =(WorkFlowFact)fact;
				workFlowFact.removeOrderTree();
			}
			logger.info("规则【"+this.fact.getRule().getRule_id()+"】IK表达式=======================>>"+express);
			long start = System.currentTimeMillis();
			variables = packageVariable(condList, fact);
		}
	}

	/**
	 * 执行表达式
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object express() throws Exception{
//		boolean result = matchExpress();
//		if(result){
//			if(fact.getRule().getRule_id().equals("201501059402000023"))
//				logger.info("11111111111");
//			String action_id = this.condList.get(0).getAction_id();
////			long start = System.currentTimeMillis();
//			setResult(action_id);
//			long end2 = System.currentTimeMillis();
////			logger.info("IK设置执行结果执行时间=========================>"+(end2-start));
//			fact.execute(action_id);
//			long end = System.currentTimeMillis();
//			logger.info("规则【"+fact.getRule().getRule_id()+"】IK业务组件执行时间=========================>"+(end-end2));
//		}
//		if("201412177509000441".equals(rule_id))
//		if("201410109870000063".equals(rule_id))
//			logger.info("2222");
		if("notneedexec".equals(fact.getRule().getRule_script())){
			String action_id = this.condList.get(0).getAction_id();
			setResult(action_id);
			long end2 = System.currentTimeMillis();
			fact.execute(action_id);
			long end = System.currentTimeMillis();
			logger.info("规则【"+fact.getObj_id()+":"+fact.getRule().getRule_id()+"】IK业务组件执行时间=========================>"+(end-end2)+":express"+express);
		}else{
			boolean result = matchExpress();
			if(result){
				String action_id = "";
				if(!ListUtil.isEmpty(this.condList))
					action_id = this.condList.get(0).getAction_id();
				long start = System.currentTimeMillis();
				setResult(action_id);
				long end2 = System.currentTimeMillis();
				fact.execute(action_id);
				long end = System.currentTimeMillis();
				
				logger.info("规则【"+fact.getObj_id()+":"+fact.getRule().getRule_id()+"】业务组件执行时间=========================>"+(end-end2)+":express"+express);
			}
		}
		return null;
	}
	
	public void setResult(String action_id) throws IntrospectionException{
		if(!StringUtil.isEmpty(action_id)){
			List<RuleConsts> consts = autoRuleImpl.queryRuleConsts(rule_id, action_id);
			if(consts!=null && consts.size()>0){
				for(RuleConsts rc:consts){
					invoteWriter(rc.getAttr_code(), rc.getConst_value(), fact);
				}
			}
		}
	}
	
	/**
	 * 执行表达式
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @return
	 */
	public boolean matchExpress(){
		long start = System.currentTimeMillis();
		if(StringUtil.isEmpty(express))return true;
		Object result = null;
		if(variables==null || variables.size()==0){
			result = ExpressionEvaluator.evaluate(express);
		}else{
			result = ExpressionEvaluator.evaluate(express,variables);
		}
		long end = System.currentTimeMillis();
//		logger.info("IK计算时间============>>"+(end-start));
		return (Boolean) result;
	}

	public static Object invokeMethod(Object obj,String methodName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
		Class clazz = obj.getClass();
		String name = clazz.getName();
		String key = name + methodName;
		Method method = FACT_METHOD.get(key);
		if(method==null){
			method = clazz.getDeclaredMethod(methodName);
			//将此对象的 accessible 标志设置为指示的布尔值。值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。
			method.setAccessible(true);
			FACT_METHOD.put(key, method);
		}
		Object value = method.invoke(obj);
		return value;
	}

	/**
	 * 封装参数
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param list
	 * @param fact
	 * @return
	 * @throws IntrospectionException
	 */
	public static List<Variable> packageVariable(List<RuleCondCache> list,AutoFact fact) throws Exception{
		List<Variable> vaList = new ArrayList<Variable>();
		for(RuleCondCache c:list){
			try{
				String param = c.getAttr_code();
				if(!NEVER_RUN_COND.equals(param) && !WITHOUT_COND.equals(param)){
					String getMethodName = "get"+toUpperCaseFirstOne(param);
					
					Object value = invokeMethod(fact,getMethodName);
					
					//Object value = fastGetMethodInvoke(fact, getMethodName);
					
					//Object value = invoteGetter(param, fact);
					Variable va = Variable.createVariable(param, value);
					vaList.add(va);
				}
			}catch(Exception ex){
				ex.printStackTrace();
				throw ex;
			}
		}
		return vaList;
	}
	
	/**
	 * 拼装条件表达式
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param condList
	 * @return
	 */
	public static String parseRuleExpress(List<RuleCondCache> condList){
		StringBufferN express = new StringBufferN();
		if(condList==null)return express.toString();
		for(RuleCondCache c:condList){
			express.append(c.getPre_log()).append(c.getLeft_brackets());
			if(NEVER_RUN_COND.equals(c.getAttr_code())){
				express.append("false");
			}else if(WITHOUT_COND.equals(c.getAttr_code())){
				express.append("true");
			}else if(EQ.equals(c.getOpt_type())){
				express.append(appendEQ(c.getZ_value(), c.getAttr_code()));
			}else if(NE.equals(c.getOpt_type())){
				express.append(appendNE(c.getZ_value(), c.getAttr_code()));
			}else if(GE.equals(c.getOpt_type())){
				express.append(appendGE(c.getZ_value(), c.getAttr_code()));
			}else if(GT.equals(c.getOpt_type())){
				express.append(appendGT(c.getZ_value(), c.getAttr_code()));
			}else if(LT.equals(c.getOpt_type())){
				express.append(appendLT(c.getZ_value(), c.getAttr_code()));
			}else if(LE.equals(c.getOpt_type())){
				express.append(appendLE(c.getZ_value(), c.getAttr_code()));
			}else if(CONTAINS.equals(c.getOpt_type())){
				express.append(appendCONTAINS(c.getZ_value(), c.getAttr_code()));
			}else if(NOT_CONTAINS.equals(c.getOpt_type())){
				express.append(appendNOTCONTAINS(c.getZ_value(), c.getAttr_code()));
			}else if(MATCHES.equals(c.getOpt_type())){
				express.append(appendMATCHES(c.getZ_value(), c.getAttr_code()));
			}else if(NOT_MATCHES.equals(c.getOpt_type())){
				express.append(appendNOTMATCHES(c.getZ_value(), c.getAttr_code()));
			}else if(IN.equals(c.getOpt_type())){
				express.append(appendIN(c.getZ_value(), c.getAttr_code()));
			}else if(NOT_IN.equals(c.getOpt_type())){
				express.append(appendNOTIN(c.getZ_value(), c.getAttr_code()));
			}
			express.append(c.getRight_brackets());
		}
		return express.toString();
	}
	
	/**
	 * 大于
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param value
	 * @param param
	 * @return
	 */
	public static String appendGT(String value,String param){
		return value+GT+param;
	}
	
	/**
	 * 等于
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param value
	 * @param param
	 * @return
	 */
	public static String appendEQ(String value,String param){
		return value+EQ+param;
	}
	
	/**
	 * 不等于
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param value
	 * @param param
	 * @return
	 */
	public static String appendNE(String value,String param){
		return value+NE+param;
	}
	
	/**
	 * 大于等于
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param value
	 * @param param
	 * @return
	 */
	public static String appendGE(String value,String param){
		return value+GE+param;
	}
	
	/**
	 * 小于
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param value
	 * @param param
	 * @return
	 */
	public static String appendLT(String value,String param){
		return value+LT+param;
	}
	
	/**
	 * 小于等于
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param value
	 * @param param
	 * @return
	 */
	public static String appendLE(String value,String param){
		return value+LE+param;
	}
	
	/**
	 * CONTAINS
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param value
	 * @param param
	 * @return
	 */
	public static String appendCONTAINS(String value,String param){
		return IK_CONTAINS+"("+param+",\""+value+"\")";
	}
	
	/**
	 * CONTAINS
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param value
	 * @param param
	 * @return
	 */
	public static String appendNOTCONTAINS(String value,String param){
		return "!"+IK_CONTAINS+"("+param+",\""+value+"\")";
	}
	
	/**
	 * CONTAINS
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param value
	 * @param param
	 * @return
	 */
	public static String appendIN(String value,String param){
		return IK_IN+"("+param+",\""+value+"\")";
	}
	
	/**
	 * CONTAINS
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param value
	 * @param param
	 * @return
	 */
	public static String appendNOTIN(String value,String param){
		return "!"+IK_IN+"("+param+",\""+value+"\")";
	}
	
	/**
	 * MATCHES
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param value
	 * @param param
	 * @return
	 */
	public static String appendMATCHES(String value,String param){
		return IK_MATCHES+"("+param+",\""+value+"\")";
	}
	
	/**
	 * NOT MATCHES
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param value
	 * @param param
	 * @return
	 */
	public static String appendNOTMATCHES(String value,String param){
		return "!"+IK_MATCHES+"("+param+",\""+value+"\")";
	}
	
	public static Object fastGetMethodInvoke(Object obj,String methodName) throws SecurityException, NoSuchMethodException, InvocationTargetException{
		Class clazz = obj.getClass();
		String name = clazz.getName();
		String key = name + methodName;
		FastMethod method = FAST_FACT_METHOD.get(key);
		if(method==null){
			Method m = clazz.getDeclaredMethod(methodName);
			FastClass cglibBeanClass = FastClass.create(clazz);
			method = cglibBeanClass.getMethod(m);
			FAST_FACT_METHOD.put(key, method);
		}
		return method.invoke(obj, null);
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
	
	/**
	 * 获取属性
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param clazz
	 * @return
	 * @throws IntrospectionException
	 */
	public static PropertyDescriptor[] getClassProperty(Class clazz) throws IntrospectionException{
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz); // 获取类属性
		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		return propertyDescriptors;
	}
	
	/**
	 * 调用get方法
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param proName
	 * @param obj
	 * @return
	 * @throws IntrospectionException
	 */
	public static Object invoteGetter(String proName,Object obj) throws IntrospectionException{
		PropertyDescriptor[] pors = getObjectProperty(obj);
		return invoteGetter(pors, proName, obj);
	}
	
	/**
	 * 调用get方法
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param proName
	 * @param obj
	 * @return
	 * @throws IntrospectionException
	 */
	public static Object invoteGetter(PropertyDescriptor[] propertyDescriptors,String proName,Object obj){
		for(PropertyDescriptor property:propertyDescriptors){
			String mName = property.getName();
			if(mName.equalsIgnoreCase(proName)){
				try{
					Method get = property.getReadMethod();
					if (get !=null){
						Object returnobj = get.invoke(obj);
						return returnobj;
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
				break ;
			}
		}
		return null;
	}
	
	public static void invoteWriter(String proName,Object value,Object obj) throws IntrospectionException{
		PropertyDescriptor[] pors = getObjectProperty(obj);
		invoteWriter(pors, proName, value, obj);
	}
	
	/**
	 * 执行set方法
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param propertyDescriptors
	 * @param proName
	 * @param value
	 * @param obj
	 */
	public static void invoteWriter(PropertyDescriptor[] propertyDescriptors,String proName,Object value,Object obj){
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
	
}
