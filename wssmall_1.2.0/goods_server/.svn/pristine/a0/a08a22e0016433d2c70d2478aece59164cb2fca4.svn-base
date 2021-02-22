package com.ztesoft.net.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.mall.core.service.IGoodsAdjunctManager;

import services.ServiceBase;
import zte.net.iservice.IGoodsAdjunctService;
import zte.params.goods.req.GoodsAdjunctListReq;
import zte.params.goods.resp.GoodsAdjunctListResp;

public class GoodsAdjunctService extends ServiceBase implements
		IGoodsAdjunctService {

	private IGoodsAdjunctManager goodsAdjunctManager;
	
	public IGoodsAdjunctManager getGoodsAdjunctManager() {
		return goodsAdjunctManager;
	}

	public void setGoodsAdjunctManager(IGoodsAdjunctManager goodsAdjunctManager) {
		this.goodsAdjunctManager = goodsAdjunctManager;
	}

	@Override
	public GoodsAdjunctListResp list(GoodsAdjunctListReq req) {
		String goods_id = req.getGoods_id();
		List<Map> adjuncts = goodsAdjunctManager.list(goods_id);
		GoodsAdjunctListResp resp = new GoodsAdjunctListResp();
		if(adjuncts==null || adjuncts.size()==0){
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		else{
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		resp.setAdjuncts(adjuncts);
		addReturn(req,resp);
		return resp;
	}

}
