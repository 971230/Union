package com.ztesoft.net.service;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.FreeOffer;
import com.ztesoft.net.mall.core.model.FreeOfferCategory;
import com.ztesoft.net.mall.core.service.IFreeOfferCategoryManager;
import com.ztesoft.net.mall.core.service.IFreeOfferManager;

import services.ServiceBase;
import zte.net.iservice.IFreeOfferService;
import zte.params.coupon.req.FreeOfferCategoryGetReq;
import zte.params.coupon.req.FreeOfferGetReq;
import zte.params.coupon.req.FreeOfferPageListReq;
import zte.params.coupon.resp.FreeOfferCategoryGetResp;
import zte.params.coupon.resp.FreeOfferGetResp;
import zte.params.coupon.resp.FreeOfferPageListResp;

public class FreeOfferService extends ServiceBase implements IFreeOfferService {

	private IFreeOfferManager freeOfferManager;
	private IFreeOfferCategoryManager freeOfferCategoryManager;
	public IFreeOfferManager getFreeOfferManager() {
		return freeOfferManager;
	}

	public void setFreeOfferManager(IFreeOfferManager freeOfferManager) {
		this.freeOfferManager = freeOfferManager;
	}

	public IFreeOfferCategoryManager getFreeOfferCategoryManager() {
		return freeOfferCategoryManager;
	}

	public void setFreeOfferCategoryManager(
			IFreeOfferCategoryManager freeOfferCategoryManager) {
		this.freeOfferCategoryManager = freeOfferCategoryManager;
	}

	@Override
	public FreeOfferGetResp getFreeOffer(FreeOfferGetReq req) {
		int fo_id = req.getFo_id();
		FreeOffer fo = freeOfferManager.get(fo_id);
		
		FreeOfferGetResp resp = new FreeOfferGetResp();
		if(fo==null){
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
			resp.setResult(false);
		}
		else{
			resp.setError_code("0");
			resp.setError_msg("成功");
			resp.setResult(true);
		}
		resp.setFo(fo);
		addReturn(req,resp);
		return resp;
	}

	@Override
	public FreeOfferPageListResp listFreeOffer(FreeOfferPageListReq req) {
		Page page = freeOfferManager.list(1, 20);
		FreeOfferPageListResp resp = new FreeOfferPageListResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setPage(page);
		addReturn(req,resp);
		return resp;
	}

	@Override
	public FreeOfferCategoryGetResp getFreeOfferCategory(FreeOfferCategoryGetReq req) {
		String fo_category_id = req.getFo_category_id();
		FreeOfferCategory cat = freeOfferCategoryManager.get(fo_category_id);
		FreeOfferCategoryGetResp resp = new FreeOfferCategoryGetResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setCat(cat);
		addReturn(req,resp);
		return resp;
	}
	
	
}
