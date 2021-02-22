package com.ztesoft.remote.params.notice.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.remote.params.notice.resp.NoticePageResp;

import params.ZteRequest;

/**
 * 信息分页对象
 * @author hu.yi
 * @date 2014.04.23
 */
public class NoticePageReq extends ZteRequest<NoticePageResp>{

	private String title;
	private int pageNo = 1;
	private int pageSize = 10;
	
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.ztesoft.remote.notice.listNoticePage";
	}

}
