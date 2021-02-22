package com.ztesoft.remote.params.adv.req;

import params.ZteRequest;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.params.adv.resp.AdvResp;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
/**
 * 
 * @author wui
 * 广告请求实体
 *
 */
public class AdvReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="广告位标识",type="String",isNecessary="Y",desc="acid：广告位标识")
	private String acid;
	
	@ZteSoftCommentAnnotationParam(name="用户标识",type="String",isNecessary="N",desc="user_id：用户(商家)标识")
	private String user_id;
	
	@ZteSoftCommentAnnotationParam(name="显示的宽度",type="String",isNecessary="N",desc="显示的宽度")
	private String width;
	
	@ZteSoftCommentAnnotationParam(name="显示的高度",type="String",isNecessary="N",desc="显示的高度")
	private String height;
	
	public AdvReq(String acid,String width,String height){
		this.acid = acid;
		this.width = width;
		this.height = height;
	}
	
	public Class<AdvResp> getResponseClass() {
		return AdvResp.class;
	}
	
	public AdvReq(){
	}
	public String getAcid() {
		return acid;
	}
	public void setAcid(String acid) {
		this.acid = acid;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}


	@Override
    public void check() throws ApiRuleException {
        if (ApiUtils.isBlank(acid)) {
            throw new ApiRuleException("-1","广告位标识【acid】不能为空!");
        }
    }

    @Override
    public String getApiMethodName() {
        return "com.ztesoft.remote.adv.queryAdByAcId";
    }
}
