package com.ztesoft.remote.params.adv.resp;

import com.ztesoft.net.framework.database.Page;

import params.ZteResponse;


/**
 * 广告分页对象
 * @author hu.yi
 * @date 2014.04.23
 */
public class AdvPageResp extends ZteResponse{

	
	private Page page;

	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}
