package com.ztesoft.remote.params.adv.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.Adv;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.remote.params.adv.resp.AdvPageResp;

/**
 * 广告分页对象
 * @author hu.yi
 * @date 2014.04.23
 */
public class AdvPageReq extends ZteRequest<AdvPageResp>{

	@ZteSoftCommentAnnotationParam(name="广告实体",type="Adv",isNecessary="N",desc="广告实体")
	private Adv adv;
	private int pageNo = 1;
	private int pageSize = 10;
	private String isPublic;
	
	public Adv getAdv() {
		return adv;
	}

	public void setAdv(Adv adv) {
		this.adv = adv;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	@NotDbField
	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.ztesoft.remote.adv.listAdvPage";
	}

}
