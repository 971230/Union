package services;

import params.login.req.LoginReq;
import params.login.resp.LoginResp;
import params.member.req.MemberAddReq;
import params.member.req.MemberByIdReq;
import params.member.req.MemberEditReq;
import params.member.req.MemberLevelListReq;
import params.member.req.MemberOrderIsBuyReq;
import params.member.req.MemberPasswordReq;
import params.member.req.MemberQryPageReq;
import params.member.req.MemberQryReq;
import params.member.req.PointConsumeReq;
import params.member.req.PointHistoryReq;
import params.member.resp.MemberAddResp;
import params.member.resp.MemberByIdResp;
import params.member.resp.MemberEditResp;
import params.member.resp.MemberLevelListResp;
import params.member.resp.MemberOrderIsBuyResp;
import params.member.resp.MemberPasswordResp;
import params.member.resp.MemberQryResp;
import params.member.resp.PointConsumeResp;
import params.member.resp.PointHistoryResp;
import params.order.req.MemberOrderGiftReq;
import params.order.req.MemberOrderLogReq;
import params.order.resp.MemberOrderGiftResp;
import params.order.resp.MemberOrderLogResp;

public interface MemberInf {

	/**
	 * 用户列表查询
	 * @作者 MoChunrun
	 * @创建日期 2013-10-28 
	 * @param req
	 * @return
	 */
	public MemberQryResp qryMemberList(MemberQryPageReq req);
	
	
	
	
	/**
	 * 会员等级查询
	 * @作者 MoChunrun
	 * @创建日期 2013-10-28 
	 * @param req
	 * @return
	 */
	public MemberLevelListResp qryMemberLvList(MemberLevelListReq req);
	
	
	/**
	 * 根据member_id查询member
	 * 
	 * */
	public MemberByIdResp getMemberById(MemberByIdReq req);
	
	/**
	 * 登录验证
	 * 
	 * */
	public LoginResp loginValid(LoginReq req);
	/**
	 * 新增会员
	 * 
	 * */
	public MemberAddResp addNewMember(MemberAddReq req);
	
	/**
	 * 修改会员密码
	 * */
	public MemberPasswordResp updateMemberPassword(MemberPasswordReq req);
	
	public MemberEditResp editMemberInfo(MemberEditReq req);
	
	public MemberQryResp qryMember(MemberQryReq req);
	/****
	 * 查询订单日志
	 * @param req
	 * @return
	 */
	public MemberOrderLogResp qryOrderLog(MemberOrderLogReq req);
	
	/****
	 * 根据订单号查询赠品
	 * @param req
	 * @return
	 */
	public MemberOrderGiftResp qryGiftByOrderId(MemberOrderGiftReq req);
	
	/*****
	 * 会员积分日志
	 * @param req
	 * @return
	 */
	public PointHistoryResp qryPointHistoryPage(PointHistoryReq req);
	
	/*****
	 * 会员累计积分
	 * @param req
	 * @return
	 */
	public PointConsumeResp qryPointConsume(PointConsumeReq req);
	
	/*******
	 * 判断当前会员是否购买过此商品
	 * @param req
	 * @return
	 */
	public MemberOrderIsBuyResp isBuy(MemberOrderIsBuyReq req);
}
