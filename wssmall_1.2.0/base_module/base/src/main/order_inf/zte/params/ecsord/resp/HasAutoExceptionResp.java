package zte.params.ecsord.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class HasAutoExceptionResp extends ZteResponse{

	@ZteSoftCommentAnnotationParam(name="是否有未恢复的自动化异常",type="String",isNecessary="Y",desc="0为没有，1为有")
	private String hasAutoException;
	
	public String getHasAutoException(){
		return hasAutoException;
	}
	public void setHasAutoException(String hasAutoException){
		this.hasAutoException = hasAutoException;
	}
}
