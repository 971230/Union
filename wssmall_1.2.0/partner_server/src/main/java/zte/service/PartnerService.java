package zte.service;

import javax.annotation.Resource;

import params.adminuser.resp.UserWdLoginResp;
import params.req.PartnerByIdReq;
import params.req.PartnerLogOffReq;
import params.req.PartnerShopListReq;
import params.req.PartnerSSOReq;
import params.req.PartnerWdAddReq;
import params.req.PartnerWdEditReq;
import params.resp.PartnerByIdResp;
import params.resp.PartnerLogOffResp;
import params.resp.PartnerShopListResp;
import params.resp.PartnerWdAddResp;
import params.resp.PartnerWdEditResp;
import services.AdminUserInf;
import services.PartnerInf;
import services.ServiceBase;
import zte.net.iservice.IPartnerService;
import zte.params.req.GetPartnerReq;
import zte.params.resp.GetPartnerResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

public class PartnerService extends ServiceBase implements IPartnerService {
	@Resource
	private PartnerInf partnerServ;

	@Resource
	private AdminUserInf adminUserServ;

	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询代理商列表", summary = "查询代理商列表")
	public GetPartnerResp getPartnerList(GetPartnerReq req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PartnerWdAddResp addPartnerWd(PartnerWdAddReq req) {
		PartnerWdAddResp resp = partnerServ.addPartnerWd(req);
		return resp;
	}

	@Override
	public PartnerWdEditResp editPartnerWd(PartnerWdEditReq req) {
		PartnerWdEditResp resp = partnerServ.editPartnerWd(req);
		return resp;
	}

	@Override
	public PartnerByIdResp getPartnerById(PartnerByIdReq req) {
		PartnerByIdResp resp = partnerServ.getPartnerById(req);
		return resp;
	}

    @Override
	public PartnerShopListResp getPartnerShopPage(PartnerShopListReq req) {
	    return partnerServ.getPartnerShopPage(req);
	}
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "分销商免登陆验证", summary = "分销商免登陆验证")
	public UserWdLoginResp partnerSSO(PartnerSSOReq req) {
		UserWdLoginResp userWdLoginResp = adminUserServ.userWdLoginSSO(req);
		return userWdLoginResp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "分销商注销", summary = "分销商注销")
	public PartnerLogOffResp partnerLogoff(PartnerLogOffReq req) {
		PartnerLogOffResp logOffResp = adminUserServ.partnerLogoff(req);
		
		return logOffResp;
	}


}