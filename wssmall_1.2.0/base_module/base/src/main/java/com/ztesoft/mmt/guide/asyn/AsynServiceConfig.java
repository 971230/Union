package com.ztesoft.mmt.guide.asyn;

import params.ZteBusiRequest;

public class AsynServiceConfig implements java.io.Serializable{

	public ZteBusiRequest zteRequest;
	
	public BusinessHandler businessHandler;

	private AsynServiceConfig(){
		
		
	}
	
	public AsynServiceConfig(BusinessHandler businessHandler){
		
		this.businessHandler=businessHandler;
	}
	

	public BusinessHandler getBusinessHandler() {
		return businessHandler;
	}

	public void setBusinessHandler(BusinessHandler businessHandler) {
		this.businessHandler = businessHandler;
	}

	public ZteBusiRequest getZteRequest() {
		return zteRequest;
	}

	public void setZteRequest(ZteBusiRequest zteRequest) {
		this.zteRequest = zteRequest;
	}

	
}
