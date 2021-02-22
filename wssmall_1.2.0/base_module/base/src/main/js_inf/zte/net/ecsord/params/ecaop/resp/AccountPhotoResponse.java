package zte.net.ecsord.params.ecaop.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class AccountPhotoResponse extends ZteResponse{

	@ZteSoftCommentAnnotationParam(name="证件照图片路径",type="String",isNecessary="Y",desc="urls：证件照图片路径")
	private String urls;
	
	public String getUrls(){
		return urls;
	}
	public void setUrls(String urls){
		this.urls = urls;
	}
}
