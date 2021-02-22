package zte.net.iservice;

import params.member.req.CommentAddReq;
import params.member.req.MemberAddReq;
import params.member.req.MemberCouponsReq;
import params.member.req.MemberEditReq;
import params.member.req.MemberLVCanBuyReq;
import params.member.req.MemberLvListReq;
import params.member.req.MemberLvPriceReq;
import params.member.req.MemberOrderIsBuyReq;
import params.member.req.MemberPriceByPIdReq;
import params.member.req.MemberPriceListByGoodsIdReq;
import params.member.req.MemberQryPageReq;
import params.member.req.MemberQryReq;
import params.member.req.PointConsumeReq;
import params.member.req.PointHistoryReq;
import params.member.resp.MemberAddResp;
import params.member.resp.MemberCouponsResp;
import params.member.resp.MemberEditResp;
import params.member.resp.MemberLVCanBuyResp;
import params.member.resp.MemberLvListResp;
import params.member.resp.MemberLvPriceResp;
import params.member.resp.MemberOrderIsBuyResp;
import params.member.resp.MemberPriceByPIdResp;
import params.member.resp.MemberPriceListByGoodsIdResp;
import params.member.resp.MemberQryResp;
import params.member.resp.PointConsumeResp;
import params.member.resp.PointHistoryResp;
import params.order.req.MemberOrderGiftReq;
import params.order.req.MemberOrderLogReq;
import params.order.resp.MemberOrderGiftResp;
import params.order.resp.MemberOrderLogResp;
import zte.params.member.req.AgentPageListReq;
import zte.params.member.req.AskAddReq;
import zte.params.member.req.AskGetReq;
import zte.params.member.req.AskPageListReq;
import zte.params.member.req.CommentPageListReq;
import zte.params.member.req.FavAddReq;
import zte.params.member.req.FavCancelReq;
import zte.params.member.req.FavPageListReq;
import zte.params.member.req.GoodsMemberPriceListReq;
import zte.params.member.req.GuestBookAddReq;
import zte.params.member.req.GuestBookPageListReq;
import zte.params.member.req.MemberByMobileReq;
import zte.params.member.req.MemberIsExistsReq;
import zte.params.member.req.MemberLoginReq;
import zte.params.member.req.MemberLvGetReq;
import zte.params.member.req.MemberPwdModifyReq;
import zte.params.member.req.MemberRegisterReq;
import zte.params.member.resp.AgentPageListResp;
import zte.params.member.resp.AskAddResp;
import zte.params.member.resp.AskGetResp;
import zte.params.member.resp.AskPageListResp;
import zte.params.member.resp.CommentAddResp;
import zte.params.member.resp.CommentPageListResp;
import zte.params.member.resp.FavAddResp;
import zte.params.member.resp.FavCancelResp;
import zte.params.member.resp.FavPageListResp;
import zte.params.member.resp.GoodsMemberPriceListResp;
import zte.params.member.resp.GuestBookAddResp;
import zte.params.member.resp.GuestBookPageListResp;
import zte.params.member.resp.MemberByMobileResp;
import zte.params.member.resp.MemberIsExistsResp;
import zte.params.member.resp.MemberLoginResp;
import zte.params.member.resp.MemberLvGetResp;
import zte.params.member.resp.MemberPwdModifyResp;
import zte.params.member.resp.MemberRegisterResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

/**
 * 会员API
 * 
 * @作者 MoChunrun
 * @创建日期 2014-2-13
 * @版本 V 1.0
 */
@ZteSoftCommentAnnotation(type = "class", desc = "会员管理API", summary = "提供会员相关信息操作")
public interface IMemberService {

	/**
	 * 代理商查询
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-13
	 * @param req
	 * @return
	 */
//	@ZteSoftCommentAnnotation(type = "method", desc = "代理商查询", summary = "代理商查询")
	// @ServiceMethod(method="zte.memberService.agent.page",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	public AgentPageListResp queryAgentForPage(AgentPageListReq req);

	/**
	 * 增加用户提问
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-13
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "增加用户提问", summary = "增加用户提问")
	// @ServiceMethod(method="zte.memberService.ask.add",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	public AskAddResp addAsk(AskAddReq req);

	/**
	 * 查询我的问题
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-13
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "查询我的问题", summary = "查询我的问题")
	// @ServiceMethod(method="zte.memberService.ask.page",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	public AskPageListResp queryAskForPage(AskPageListReq req);

	/**
	 * 查询我的问题
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-13
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "按ID查询我的问题", summary = "按ID查询我的问题")
	// @ServiceMethod(method="zte.memberService.ask.get",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	public AskGetResp getAsk(AskGetReq req);

	/**
	 * 添加收藏夹
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-13
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "添加收藏夹", summary = "添加收藏夹")
	// @ServiceMethod(method="zte.memberService.fav.add",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	public FavAddResp addFav(FavAddReq req);

	/**
	 * 查询我的收藏夹
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-13
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "查询我的收藏夹", summary = "查询我的收藏夹")
	// @ServiceMethod(method="zte.memberService.fav.page",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	public FavPageListResp queryFavForPage(FavPageListReq req);

	/**
	 * 取消收藏
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-13
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "取消收藏", summary = "取消收藏")
	// @ServiceMethod(method="zte.memberService.fav.cancel",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	public FavCancelResp cancelFav(FavCancelReq req);

	/**
	 * 添加留言
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-13
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "添加留言", summary = "添加留言")
	// @ServiceMethod(method="zte.memberService.guestbook.add",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	public GuestBookAddResp addGuestBook(GuestBookAddReq req);

	/**
	 * 分页查询留言
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-13
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "分页查询留言", summary = "分页查询留言")
	// @ServiceMethod(method="zte.memberService.guestbook.page",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	public GuestBookPageListResp queryGuestBookForPage(GuestBookPageListReq req);

	/**
	 * 修改用户密码
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-13
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "修改用户密码", summary = "修改用户密码")
	// @ServiceMethod(method="zte.memberService.password.modify",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	public MemberPwdModifyResp modifyMemberPwd(MemberPwdModifyReq req);

	/**
	 * 分页查询用户评论
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-13
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "分页查询用户评论", summary = "分页查询用户评论")
	// @ServiceMethod(method="zte.memberService.comment.page",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	public CommentPageListResp queryCommoentForPage(CommentPageListReq req);

	/**
	 * 添加评论
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-13
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "添加评论", summary = "添加评论")
	// @ServiceMethod(method="zte.memberService.comment.add",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	public CommentAddResp addComment(CommentAddReq req);

	/**
	 * 用户注册
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-13
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "用户注册", summary = "用户注册")
	// @ServiceMethod(method="zte.memberService.member.register",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	public MemberRegisterResp memberRegister(MemberRegisterReq req);

	/**
	 * 按ID查询会员等级
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-13
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "按ID查询会员等级", summary = "按ID查询会员等级")
	// @ServiceMethod(method="zte.memberService.memberlv.get",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	public MemberLvGetResp getMemberLv(MemberLvGetReq req);

	/**
	 * 查询会员等级列表
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-13
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "查询会员等级列表", summary = "查询会员等级列表")
	// @ServiceMethod(method="zte.memberService.memberlv.list",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	public MemberLvListResp listMemberLv(MemberLvListReq req);

	/**
	 * 会员登录
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-14
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "会员登录", summary = "会员登录")
	// @ServiceMethod(method="zte.memberService.member.login",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	public MemberLoginResp memberLogin(MemberLoginReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="查询会员信息",summary="查询会员信息")
	public MemberQryResp qryMemberList(MemberQryPageReq req);

	/**
	 * 获取会员价
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-14
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "获取会员价", summary = "获取会员价")
	// @ServiceMethod(method="zte.memberService.member.login",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	public MemberLvPriceResp getMemberLvPrice(MemberLvPriceReq req);

	/**
	 * 判断会员是否能购买
	 * 
	 * @author Administrator
	 * @date 2014-3-6
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "判断会员是否能购买", summary = "判断会员是否能购买")
	public MemberLVCanBuyResp isMemberLvCanBy(MemberLVCanBuyReq req);
	
	/**
	 * 通过会员等级获取会员价格
	 * 
	 * @author Administrator
	 * @date 2014-3-6
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "获取会员价格", summary = "获取会员价格")
	public MemberPriceByPIdResp qryPriceByPid(MemberPriceByPIdReq req);
	
	/**
	 * 读取某个商品的所有规格的会员价格
	 * @author Administrator
	 * @date 2014-3-6
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "读取某个商品的所有规格的会员价格", summary = "读取某个商品的所有规格的会员价格")
	public MemberPriceListByGoodsIdResp qryPriceList(MemberPriceListByGoodsIdReq req);
	
	/**
	 * 获取会员信息页
	 * @author Administrator
	 * @date 2014-3-6
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "获取会员信息页", summary = "获取会员信息页")
	public MemberQryResp qryMemberPage(MemberQryReq req);
	
	
	/**
	 * 增加会员信息
	 * @author Administrator
	 * @date 2014-3-6
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "增加会员信息", summary = "增加会员信息")
	public MemberAddResp addMember(MemberAddReq req);
	
	/**
	 * 修改会员信息
	 * @author Administrator
	 * @date 2014-3-6
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "修改会员信息", summary = "修改会员信息")
	public MemberEditResp editMember(MemberEditReq req);
	
	/**
	 * 查询订单日志 es_order_log
	 * @author Administrator
	 * @date 2014-3-6
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "查询订单日志", summary = "查询订单日志")
	public MemberOrderLogResp qryOrderLog(MemberOrderLogReq req);
	
	/**
	 * 根据订单号查询赠品
	 * @author Administrator
	 * @date 2014-3-6
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "查询订单号对应的赠品", summary = "查询订单号对应的赠品")
	public MemberOrderGiftResp qryOrderGiftByOrderId(MemberOrderGiftReq req);
	
	/**
	 * 根据订单号查询赠品
	 * @author Administrator
	 * @date 2014-3-6
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "查询会员积分日志", summary = "查询会员积分日志")
	public PointHistoryResp pagePointHistory(PointHistoryReq req);
	
	/**
	 * 根据订单号查询赠品
	 * @author Administrator
	 * @date 2014-3-6
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "会员累计积分", summary = "会员累计积分")
	public PointConsumeResp qryConsumePoint(PointConsumeReq req);
	
	
	/**
	 * 判断当前会员是否购买过此商品
	 * @author Administrator
	 * @date 2014-3-6
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "判断当前会员是否购买过此商品", summary = "判断当前会员是否购买过此商品")
	public MemberOrderIsBuyResp memberIsBuy(MemberOrderIsBuyReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "判断用户是否存在", summary = "判断用户是否存在")
	public MemberIsExistsResp memberIsExists(MemberIsExistsReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "根据用户号码查会员", summary = "根据用户号码查会员")
	public MemberByMobileResp getMemberByMobile(MemberByMobileReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "获取商品的会员价格", summary = "获取商品的会员价格")
	public GoodsMemberPriceListResp listGoodsMemberPrice(GoodsMemberPriceListReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "获取会员优惠券", summary = "获取会员优惠券")
	public MemberCouponsResp listMemberCoupons(MemberCouponsReq req);
}
