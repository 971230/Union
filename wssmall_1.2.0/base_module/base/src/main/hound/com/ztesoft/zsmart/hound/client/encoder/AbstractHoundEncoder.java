package com.ztesoft.zsmart.hound.client.encoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ztesoft.net.auto.rule.json.filter.ExcludesProFilter;

import zte.net.params.req.PlanRuleTreeExeReq;

public abstract class AbstractHoundEncoder implements HoundEventEncoder {


	public String toJSONString(Object arg0,String... properties){
		// TODO Auto-generated method stub
			
			ExcludesProFilter filter = new ExcludesProFilter(properties);
			SerializerFeature[] features = { SerializerFeature.WriteMapNullValue, // 输出空置字段
					SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
					SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
					SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
					SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
					SerializerFeature.WriteClassName
				};
			
		return JSON.toJSONString(arg0,filter,features);
			
	}

}
