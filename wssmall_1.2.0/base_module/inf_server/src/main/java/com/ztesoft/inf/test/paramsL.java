package com.ztesoft.inf.test;

import java.util.List;

public class paramsL {
	/*{"name":"套餐参数",
		"paramList":[
		             {"attrcode":"","attrtype":"goodsparam","attrvaltype":"0","ename":"bss_code","name":"BSS套餐编码","value":"50000151"},
		             {"attrcode":"","attrtype":"goodsparam","attrvaltype":"0","ename":"ess_code","name":"ESS套餐编码","value":"99002305"},
		             {"attrcode":"","attrtype":"goodsparam","attrvaltype":"0","ename":"month_fee","name":"套餐档次","value":"126"}],
		     "paramNum":3}*/
	
  private String name;
  private int paramNum;
  private List<paramsenum> paramList;
  public String getName(){
	  return name;
  }
  public int getParamNum(){
	  return  paramNum;
  }
  public List<paramsenum>getParamList(){
	  return paramList;
  }
  public void setName(String name){
	  this.name=name;
  }
  public void setParamNum(int paramNum){
	  this.paramNum=paramNum;
  }
  public void setListParamList(List<paramsenum> paramList){
     this.paramList=paramList;
}
}
