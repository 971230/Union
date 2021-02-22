package zte.net.iservice;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import zte.params.coupon.req.CouponUseTimesEditReq;
import zte.params.coupon.req.CouponUserTimeCheckReq;
import zte.params.coupon.resp.CouponUseTimesEditResp;
import zte.params.coupon.resp.CouponUserTimeCheckResp;

//@ZteSoftCommentAnnotation(type="class",desc="优惠促销管理API",summary="优惠促销管理API（解耦）")
public interface ICouponService {

	/**
	 * 检查优惠券使用次数，为0返回false，大于0返回true
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="class",desc="检查优惠卷使用的次数",summary="检查优惠券使用次数，为0返回false，大于0返回true")
	public CouponUserTimeCheckResp checkCouponUserTime(CouponUserTimeCheckReq req);
	
	/**
	 * 更新优惠券使用次数
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="class",desc="更新优惠卷使用的次数",summary="跟新优惠券使用次数")
	public CouponUseTimesEditResp editCouponUseTime(CouponUseTimesEditReq  req);
}
