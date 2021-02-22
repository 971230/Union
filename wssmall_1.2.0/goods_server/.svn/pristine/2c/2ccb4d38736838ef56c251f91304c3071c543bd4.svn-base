package com.ztesoft.net.service;

import com.ztesoft.net.mall.core.service.ICouponManager;

import services.ServiceBase;
import zte.net.iservice.ICouponService;
import zte.params.coupon.req.CouponUseTimesEditReq;
import zte.params.coupon.req.CouponUserTimeCheckReq;
import zte.params.coupon.resp.CouponUseTimesEditResp;
import zte.params.coupon.resp.CouponUserTimeCheckResp;

public class CouponService extends ServiceBase implements ICouponService{

	private ICouponManager couponManager;

	public ICouponManager getCouponManager() {
		return couponManager;
	}

	public void setCouponManager(ICouponManager couponManager) {
		this.couponManager = couponManager;
	}

	@Override
	public CouponUserTimeCheckResp checkCouponUserTime(CouponUserTimeCheckReq req) {
		String memc_code = req.getMemc_code();
		String member_id = req.getMember_id();
		boolean result = couponManager.hascouponUseTimes(memc_code, member_id);
		
		CouponUserTimeCheckResp resp = new CouponUserTimeCheckResp();
		resp.setHasTimes(result);
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req,resp);
		return resp;
	}

	@Override
	public CouponUseTimesEditResp editCouponUseTime(CouponUseTimesEditReq req) {
		String memc_code = req.getMemc_code();
		String member_id = req.getMember_id();
		int addUseTimes = req.getAddUseTimes();
		couponManager.updateUseTimes(member_id, memc_code, addUseTimes);
		
		CouponUseTimesEditResp resp = new CouponUseTimesEditResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req,resp);
		return resp;
	}
}
