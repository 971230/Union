package com.zte.cbss.autoprocess;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

public abstract class AbsHttpRequest<T,R> {
	
	protected HttpLoginClient client;
	protected List<NameValuePair> body = new ArrayList<NameValuePair>();
	
	public AbsHttpRequest(HttpLoginClient client){
		this.client = client;
	}
	
	protected abstract boolean pack(T data);
	
	protected abstract R unpack(PageHttpResponse response);
	
	public abstract String getUrl();
	
	public abstract String getReferer();
	
	public abstract boolean isXMLHttpRequest();
	
	public abstract boolean isPost();
	
	public R send(T data){
		if(pack(data)){ //包装请求
			PageHttpResponse response = client.send(getUrl(),getReferer(),isPost(),isXMLHttpRequest(),body);
			if(response != null){
				return unpack(response);
			}
		}
		return null;
	}

}