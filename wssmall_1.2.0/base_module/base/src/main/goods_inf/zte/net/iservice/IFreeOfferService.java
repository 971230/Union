package zte.net.iservice;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

import zte.params.coupon.req.FreeOfferCategoryGetReq;
import zte.params.coupon.req.FreeOfferGetReq;
import zte.params.coupon.req.FreeOfferPageListReq;
import zte.params.coupon.resp.FreeOfferCategoryGetResp;
import zte.params.coupon.resp.FreeOfferGetResp;
import zte.params.coupon.resp.FreeOfferPageListResp;

//@ZteSoftCommentAnnotation(type="class",desc="赠品管理",summary="赠品管理API（解耦）")
public interface IFreeOfferService {

	/**
	 * 获取赠品
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="class",desc="获取赠品",summary="货物赠品")
	public FreeOfferGetResp getFreeOffer(FreeOfferGetReq req);
	
	/**
	 * 获取赠品
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="class",desc="获取赠品列表",summary="获取赠品列表")
	public FreeOfferPageListResp listFreeOffer(FreeOfferPageListReq req);
	
	
	@ZteSoftCommentAnnotation(type="class",desc="获取赠品分类",summary="获取赠品分类")
	public FreeOfferCategoryGetResp getFreeOfferCategory(FreeOfferCategoryGetReq req);
}
