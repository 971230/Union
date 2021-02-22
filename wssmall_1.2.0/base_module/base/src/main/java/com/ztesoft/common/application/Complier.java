package com.ztesoft.common.application;

import java.util.HashMap;

public class Complier {
	
public static void main(String[]args){
	Complier bo=new Complier();
//	logger.info(bo.trimSpace("   public    int a   =  12345   "));
	try {
		bo.getFieldInfo("   public    int a   =  12345   ");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
private String strTwoSpace="  ";
private String strOneSpace=" ";
private String strSpaceEquals=" = ";
private String strEquals="=";
private String strSpaceLK=" (";
private String strSpaceRK="( ";
private String strK="(";
public String trimSpace(String inputVal){
	String strNewVal=inputVal;
	//去掉2个空格
	while(strNewVal.indexOf(strTwoSpace)>=0){
		strNewVal=strNewVal.replaceAll(strTwoSpace, strOneSpace);
	}
	//去掉空格=空格 
	while(strNewVal.indexOf(strSpaceEquals)>=0){
		strNewVal=strNewVal.replaceAll(strSpaceEquals, strEquals);
	}
	/*//去掉括号左的空格
	while(strNewVal.indexOf(strSpaceLK)>=0){
		strNewVal=strNewVal.replaceAll(strSpaceLK, strK);
	}
	//去掉括号右的空格
	while(strNewVal.indexOf(strSpaceRK)>=0){
		strNewVal=strNewVal.replaceAll(strSpaceLK, strK);
	}*/
	//去掉首尾空格
	return strNewVal.trim();
}

public HashMap getFieldInfo(String inFieldInfo) throws Exception{
	HashMap hsFieldsInfo=new HashMap();
	String fieldInfo=this.trimSpace(inFieldInfo);
	if(fieldInfo!=null && !fieldInfo.equals("")){
		String[]_strFieldInfo=fieldInfo.split(" ");
		//判断有效性
		if(_strFieldInfo==null || _strFieldInfo.length==0)throw new Exception("fieldDefinedError:non");
		//如果是以private,public开头的
		String strPrivilege=_strFieldInfo[0].trim();
		String[]_strSubFieldInfo=null;
		if(strPrivilege.equals("private") || strPrivilege.equals("public")){
			//set field_privilege,field_type
			hsFieldsInfo.put("fieldPrivilege", strPrivilege);
			String strFieldType=_strFieldInfo[1].trim();
			hsFieldsInfo.put("fieldType", strFieldType);
			//设置后半段
			_strSubFieldInfo=_strFieldInfo[2].trim().split("=");
		}else{
			//set field_privilege,field_type
			hsFieldsInfo.put("fieldPrivilege", "private");
			String strFieldType=_strFieldInfo[0].trim();
			hsFieldsInfo.put("fieldType", strFieldType);
			//设置后半段
			_strSubFieldInfo=_strFieldInfo[1].trim().split("=");
		}

		//开始设置字段名和默认值
		if(_strSubFieldInfo==null || _strSubFieldInfo.length==0)throw new Exception("subfieldDefinedError:non");
		if(_strSubFieldInfo.length==2){
			hsFieldsInfo.put("fieldName", _strSubFieldInfo[0]);
			String strDefaultVal=_strSubFieldInfo[1];
			hsFieldsInfo.put("defaultValue", strDefaultVal);
		}else{
			if(_strSubFieldInfo.length==1){
				hsFieldsInfo.put("fieldName", _strSubFieldInfo[0].trim());
			}
		}
	}
	return hsFieldsInfo;
}


}
