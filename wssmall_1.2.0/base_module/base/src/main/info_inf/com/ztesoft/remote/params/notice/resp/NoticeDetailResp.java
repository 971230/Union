package com.ztesoft.remote.params.notice.resp;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.Member;

import params.ZteResponse;
/**
 * 
 * @author wui
 *  公告、消息详情信息
 *
 */
@SuppressWarnings("unchecked")
public class NoticeDetailResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="广告信息列表",type="List",isNecessary="N",desc="广告信息列表Lis<Map>",hasChild=true)
	List<Map> dataList;

	@ZteSoftCommentAnnotationParam(name="是否登录标识",type="boolean",isNecessary="N",desc="是否登录标识")
	boolean isLogin;
	
	@ZteSoftCommentAnnotationParam(name="会员信息",type="Member",isNecessary="N",desc="会员信息")
	Member member;
	
	@ZteSoftCommentAnnotationParam(name="页面文章信息数据",type="Map",isNecessary="N",desc="页面文章信息数据")
	Map data;
	
	public List<Map> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map> dataList) {
		this.dataList = dataList;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}
	
	
}
