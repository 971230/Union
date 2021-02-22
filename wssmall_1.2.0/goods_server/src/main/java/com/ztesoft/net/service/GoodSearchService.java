package com.ztesoft.net.service;


import java.util.Map;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.service.IGoodsSearchManager2;

import services.ServiceBase;
import zte.net.iservice.IGoodSearchService;
import zte.params.goods.req.GoodsSearchPageListReq;
import zte.params.goods.req.ParamsPutReq;
import zte.params.goods.req.SelectorGetReq;
import zte.params.goods.resp.GoodsSearchPageListResp;
import zte.params.goods.resp.ParamsPutResp;
import zte.params.goods.resp.SelectorGetResp;

public class GoodSearchService extends ServiceBase implements
		IGoodSearchService {

	private IGoodsSearchManager2 goodsSearchManager2;
	
	public IGoodsSearchManager2 getGoodsSearchManager2() {
		return goodsSearchManager2;
	}

	public void setGoodsSearchManager2(IGoodsSearchManager2 goodsSearchManager2) {
		this.goodsSearchManager2 = goodsSearchManager2;
	}

	@Override
	public GoodsSearchPageListResp searchGoods(GoodsSearchPageListReq req) {
		String uri = req.getUri();
		int pageNo = req.getPageNo();
		int pageSize = req.getPageSize();
		Page page = goodsSearchManager2.search(pageNo, pageSize, uri);
		GoodsSearchPageListResp resp = new GoodsSearchPageListResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setPage(page);
		addReturn(req,resp);
		return resp;
	}

	@Override
	public SelectorGetResp getSelector(SelectorGetReq req) {
		String uri = req.getUri();
		Map selector = goodsSearchManager2.getSelector(uri);
		SelectorGetResp resp = new SelectorGetResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setSelectorMap(selector);
		addReturn(req,resp);
		return resp;
	}

	@Override
	public ParamsPutResp putParams(ParamsPutReq req) {
		Map<String,Object> params = req.getPluginParams();
		String uri = req.getUri();
		goodsSearchManager2.putParams(params, uri);
		ParamsPutResp resp = new ParamsPutResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req,resp);
		return resp;
	}

}
