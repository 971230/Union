package com.ztesoft.form;



/**
 * @author copy wui
 * @version 1.0
 */
public abstract class RequestFactory {




	public static Request getRequest(int model){
		
		
		Request request = null;
		
		if(model==ConnectType.remote)
			request = new RemoteRequest();
		
		if(model==ConnectType.local)
			request = new LocalRequest();
		
		return request;
	}

}
