package com.ztesoft.net.auto.rule;

import org.apache.log4j.Logger;

import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.fact.OrderTestFact;


public class Test {
	private static Logger logger = Logger.getLogger(Test.class);
	public static void main(String[] args) {
		//过滤不需要转json的字段
		/*ExcludesProFilter filter = new ExcludesProFilter("hasExeRuleList","hasExeRuleLogs","responses");
		SerializerFeature[] features = { SerializerFeature.WriteMapNullValue, // 输出空置字段
				SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
				SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
				SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
				SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
				SerializerFeature.WriteClassName
			};
		OrderTestFact src = new OrderTestFact();
		String ss =  JSON.toJSONString(src,filter,features);
		logger.info(ss);*/
		
		OrderTestFact fact = AutoFact.jsonToBean("{\"@type\":\"com.ztesoft.net.auto.rule.fact.OrderTestFact\",\"flow_id\":\"1111\",\"obj_id\":\"1\"}", OrderTestFact.class);
		logger.info(fact);
	}
	
	public void printClass(){
		String class_path = this.getClass().getName();
		logger.info(class_path);
	}
}
