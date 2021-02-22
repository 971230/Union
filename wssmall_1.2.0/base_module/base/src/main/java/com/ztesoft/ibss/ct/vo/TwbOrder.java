package com.ztesoft.ibss.ct.vo;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import org.apache.commons.lang.StringUtils;



public class TwbOrder {
	DynamicDict aDict;
	public void set(DynamicDict dict){
		this.aDict = dict;
	}
	public String getPreMemOrdNo() throws FrameException{
		return (String)this.aDict.getValueByName("PRE_MEM_ORD_NO",false);
	}
	
	public String getPreOrdNo() throws FrameException{
		return (String)this.aDict.getValueByName("PRE_ORD_NO",false);
	}
	
	public String getFieldValue(String field_name) throws FrameException{
		String value =(String)this.aDict.getValueByName(field_name,false);
		if(StringUtils.isEmpty(value))
			value = (String)this.aDict.getValueByName(field_name.toUpperCase(),false);
		return value;
	}
	
	public void setFieldValue(String field_name,String field_value) throws FrameException{
		this.aDict.setValueByName(field_name, field_value);
	}
}
