package com.ztesoft.component.ui.taglib;

import java.util.ArrayList;

public class PermissionVo {

	private ArrayList securityVo;

	public ArrayList getSecurityVo() {
		return securityVo;
	}

	public void setSecurityVo(ArrayList securityVo) {
		this.securityVo = securityVo; 
	}
	
	public String getControlResult(String controlId){
				
		String result = "";
		
		if(controlId!=null && securityVo!=null){
			for(int i=0; i<securityVo.size(); i++){
				SecurityVo security = (SecurityVo)securityVo.get(i);
				if(controlId.equalsIgnoreCase( security.getControlId() )){
					if("dsEnable".equalsIgnoreCase(security.getControlType())){
						result = "disabled";						
					}
					else{
						result = "invisible";
					}
					break;
				}
			}			
		}		
		
		return result;
	}
	
	
}
