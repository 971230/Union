package zte.net.iservice;

import zte.params.ordercenter.req.CouponsUseReq;
import zte.params.ordercenter.req.MemberOrderPageQueryReq;
import zte.params.ordercenter.req.UserOrderEditReq;
import zte.params.ordercenter.req.UserOrderPageQueryReq;
import zte.params.ordercenter.req.UserOrderdealReq;
import zte.params.ordercenter.resp.CouponsUseResp;
import zte.params.ordercenter.resp.MemberOrderPageQueryResp;
import zte.params.ordercenter.resp.UserOrderEditResp;
import zte.params.ordercenter.resp.UserOrderPageQueryResp;
import zte.params.ordercenter.resp.UserOrderdealResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

/**
 * 订单管理中心api
 * @作者 MoChunrun
 * @创建日期 2015-2-2 
 * @版本 V 1.0
 */
@ZteSoftCommentAnnotation(type="class",desc="商家订单处理API",summary="商家订单处理API")
public interface IOrderCenterService {

	@ZteSoftCommentAnnotation(type="method",desc="商家订单查询",summary="商家订单查询")
	public UserOrderPageQueryResp queryUserOrderPage(UserOrderPageQueryReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="用户订单处理",summary="用户订单处理")
	public UserOrderdealResp dealUserOrder(UserOrderdealReq req) throws Exception;
	
	@ZteSoftCommentAnnotation(type="method",desc="修改订单信息",summary="修改订单信息")
	public UserOrderEditResp editUserOrder(UserOrderEditReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="查询会员订单",summary="查询会员订单")
	public MemberOrderPageQueryResp queryMemberOrderPage(MemberOrderPageQueryReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="使用优惠券",summary="使用优惠券")
	public CouponsUseResp useCoupons(CouponsUseReq req);
	
}
