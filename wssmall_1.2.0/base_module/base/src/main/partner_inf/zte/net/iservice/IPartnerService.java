package zte.net.iservice;

import params.adminuser.resp.UserWdLoginResp;
import params.req.PartnerByIdReq;
import params.req.PartnerLogOffReq;
import params.req.PartnerSSOReq;
import params.req.PartnerShopListReq;
import params.req.PartnerWdAddReq;
import params.req.PartnerWdEditReq;
import params.resp.PartnerByIdResp;
import params.resp.PartnerLogOffResp;
import params.resp.PartnerShopListResp;
import params.resp.PartnerWdAddResp;
import params.resp.PartnerWdEditResp;
import zte.params.req.GetPartnerReq;
import zte.params.resp.GetPartnerResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

public interface IPartnerService {
	
	@ZteSoftCommentAnnotation(type="method",desc="查询代理商列表",summary="查询代理商列表")
	public GetPartnerResp getPartnerList(GetPartnerReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="添加微店代理商",summary="添加微店代理商")
	public PartnerWdAddResp addPartnerWd(PartnerWdAddReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="修改微店代理商店铺信息",summary="修改微店代理商店铺信息")
	public PartnerWdEditResp editPartnerWd(PartnerWdEditReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取分销商信息(包括店铺信息)",summary="获取分销商信息(包括店铺信息)")
	public PartnerByIdResp getPartnerById(PartnerByIdReq req);
   @ZteSoftCommentAnnotation(type="method",desc="获取分销商店铺列表",summary="获取分销商店铺列表")
	public PartnerShopListResp getPartnerShopPage(PartnerShopListReq req);

	@ZteSoftCommentAnnotation(type="method",desc="分销商免登陆验证",summary="分销商免登陆验证")
	public UserWdLoginResp partnerSSO(PartnerSSOReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="分销商注销",summary="分销商注销")
	public PartnerLogOffResp partnerLogoff(PartnerLogOffReq req);
}
