package com.ztesoft.net.auto.rule.parser;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

import com.ztesoft.net.auto.rule.fact.AutoFact;

public class IKExpressionUtil {
	private static Logger logger = Logger.getLogger(IKExpressionUtil.class);
	public static void main(String[] args) {
		for(int i=0;i<1;i++){
			final String b = i+"";
			new Thread(new Runnable() {
				
				@Override
				public void run() {
				    //定义表达式  
				    //给表达式中的变量 "用户名" 付上下文的值  
				    List<Variable> variables = new ArrayList<Variable>();  
				    variables.add(Variable.createVariable("order_type", "01")); 
				    variables.add(Variable.createVariable("region", "440100")); 
				    variables.add(Variable.createVariable("card_type", "1")); 
				    variables.add(Variable.createVariable("net_type", "3G")); 
				    variables.add(Variable.createVariable("goods_type", "200001")); 
				    variables.add(Variable.createVariable("shop_code", "100301")); 
				    variables.add(Variable.createVariable("shipping_type", "XCTH1")); 
				    //执行表达式  
				    for(int k=0;k<1;k++){
				    	//String expression = "$CONTAINS(\"ABCD\",\"ABC\") && $IN(\"Q\",\"Q,W,S\") && true && 10>"+k;  //$CONTAINS(\"ABC\",\"ABC\") && 
				    	//String expression = "!$IN(order_type,\"正常单,订购\")";
				    	String expression = "$IN(order_type,\"01,03\")&&$IN(region,\"440100\")&&$IN(card_type,\"0,1\")&&$IN(net_type,\"2G,3G,4G\")&&!$IN(goods_type,\"20000\")&&!$IN(shop_code,\"10030,10032\")&&!$IN(shipping_type,\"XCTH,XJ,XMPS\")";
				    	expression = "false";
				    	long start = System.currentTimeMillis();
				    	//Object result = ExpressionEvaluator.evaluate(expression);
				    	Object result = ExpressionEvaluator.evaluate(expression,variables);
			    	    logger.info("Result = " + result);  
			    	    long end = System.currentTimeMillis();
				        logger.info(end-start);
				    }
				    
				}
			}).start();
			
		}
	}
	
	/**
	 * 创建订单表达式
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param rule_id
	 * @param fact
	 * @return
	 * @throws IntrospectionException 
	 */
	public static IExpressHelper createOrderRuleExpress(String rule_id,AutoFact fact) throws Exception{
		IExpressHelper help = new ExpressHelper(rule_id,fact);
		return help;
	}
	
	/**
	 * 清空规则表达式缓存
	 * @作者 MoChunrun
	 * @创建日期 2014-12-8
	 */
	public static void clearRuleExpress(){
		ExpressHelper.ruleExpress.clear();
		ExpressHelper.FACT_METHOD.clear();
		ExpressHelper.FAST_FACT_METHOD.clear();
	}
	
}
