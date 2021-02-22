package services;

import params.member.req.MemberPointAddReq;
import params.member.req.MemberPointCheckReq;
import params.member.req.MemberPointReq;
import params.member.resp.MemberPointAddResp;
import params.member.resp.MemberPointCheckResp;
import params.member.resp.MemberPointResp;

public interface MemberPointInf {
	
	/**
	 * 获取积分项常量
	 */
	String TYPE_REGISTER= "register";
	String TYPE_EMIAL_CHECK="email_check";
	String TYPE_BUYGOODS ="buygoods";
	String TYPE_ONLINEPAY="onlinepay";
	String TYPE_LOGIN="login";
	String TYPE_COMMENT="comment";
	String TYPE_COMMENT_IMG="comment_img";
	String TYPE_REGISTER_LINK="register_link";
	
	public MemberPointCheckResp checkIsOpen(MemberPointCheckReq mpReq);
	
	public MemberPointResp getItemPointByName(MemberPointReq req);
	
	public MemberPointAddResp addNewPointHistory(MemberPointAddReq req);
}
