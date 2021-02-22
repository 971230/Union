package com.ztesoft.net.service;

import java.util.HashMap;
import java.util.List;

import services.ServiceBase;
import zte.net.iservice.IGoodsPromotionService;
import zte.params.goods.req.GoodsPromotionGetReq;
import zte.params.goods.resp.GoodsPromotionGetResp;

import com.ztesoft.net.cache.common.GoodsNetCacheRead;

public class GoodsPromotionService extends ServiceBase implements
		IGoodsPromotionService {

	/**
	 * @title 查询商品促销优惠
	 * @param req
	 */
	@Override
	public GoodsPromotionGetResp getPromotion(GoodsPromotionGetReq req) {
		String pmt_id = req.getPmt_id();
		GoodsNetCacheRead reader = new GoodsNetCacheRead();
		List<HashMap> promotion = reader.getCachesGoodsPromotion(pmt_id);
		
		GoodsPromotionGetResp resp = new GoodsPromotionGetResp();
		resp.setPromotions(promotion);
		if(promotion.isEmpty()){
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		else{
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		addReturn(req,resp);
		return resp;
	}

}
