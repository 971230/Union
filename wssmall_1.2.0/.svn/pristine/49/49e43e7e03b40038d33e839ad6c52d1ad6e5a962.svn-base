package services;

import params.ZteResponse;
import params.member.req.*;
import params.member.resp.CommentListResp;
import params.member.resp.OrderInfoResp;
import params.member.resp.OrderListResp;
import params.member.resp.RegisterResp;

/**
 * 个人中心
* @作者 MoChunrun 
* @创建日期 2013-9-27 
* @版本 V 1.0
 */
public interface MemberCenterInf {

	/**
	 * 查询用户订单列表
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-25 
	 * @param req
	 * @return
	 */
	public OrderListResp qryOrderList(OrderListReq req);
	
	/**
	 * 查询订单信息
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-25 
	 * @param req
	 * @return
	 */
	public OrderInfoResp qryOrderInfo(OrderInfoReq req);
	
	/**
	 * 查询用户评论与咨询
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-25 
	 * @param req
	 */
	public CommentListResp commentList(CommentListReq req);
	
	/**
	 * 发表评论
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-25 
	 * @param req
	 * @return
	 */
	public ZteResponse commentAdd(CommentAddReq req);
	
	/**
	 * 用户注册
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-27 
	 * @param req
	 * @return
	 */
	public RegisterResp register(RegisterReq req);
	
	/**
	 * 修改密码
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-27 
	 * @param req
	 * @return
	 */
	public ZteResponse modifyPwd(PwdModifyReq req);
}
