package zte.net.iservice.impl;

import zte.net.iservice.IOrderCenterService;
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

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;

public class ZteOrderCenterOpenService implements IOrderCenterService {
	
//	@Resource
	private IOrderCenterService orderCenterService;
	
	private void init(){
		orderCenterService = SpringContextHolder.getBean("orderCenterService");
	}

	@Override
	@ServiceMethod(method="zte.orderService.user.order.page.query",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public UserOrderPageQueryResp queryUserOrderPage(UserOrderPageQueryReq req) {
		this.init();
		return orderCenterService.queryUserOrderPage(req);
	}

	@Override
	@ServiceMethod(method="zte.orderService.user.ordercenter.deal",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public UserOrderdealResp dealUserOrder(UserOrderdealReq req)
			throws Exception {
		this.init();
		return orderCenterService.dealUserOrder(req);
	}

	@Override
	@ServiceMethod(method="zte.orderService.user.ordercenter.edit",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public UserOrderEditResp editUserOrder(UserOrderEditReq req) {
		this.init();
		return orderCenterService.editUserOrder(req);
	}

	@Override
	@ServiceMethod(method="zte.orderService.ordercenter.member.order.page.query",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberOrderPageQueryResp queryMemberOrderPage(
			MemberOrderPageQueryReq req) {
		this.init();
		return orderCenterService.queryMemberOrderPage(req);
	}
	
	@Override
	@ServiceMethod(method="zte.orderService.center.coupon.use",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CouponsUseResp useCoupons(CouponsUseReq req){
		this.init();
		return orderCenterService.useCoupons(req);
	}

}
