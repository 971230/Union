package com.ztesoft.net.service;

import java.util.List;
import services.ServiceBase;
import zte.net.iservice.IGoodsTagsService;
import zte.params.tags.req.PackageGetReq;
import zte.params.tags.req.TagGoodsListReq;
import zte.params.tags.req.TagListReq;
import zte.params.tags.resp.PackageGetResp;
import zte.params.tags.resp.TagGoodsListResp;
import zte.params.tags.resp.TagListResp;

import com.ztesoft.net.cache.common.GoodsNetCacheRead;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.service.ITagManager;

public class GoodsTagsService extends ServiceBase implements IGoodsTagsService {

	private ITagManager tagManager;

	public void setTagManager(ITagManager tagManager) {
		this.tagManager = tagManager;
	}

	/**
	 * @title 查询标签列表
	 * @param listReq
	 */
	@Override
	public TagListResp listTag(TagListReq listReq) {
		List tags = tagManager.list();
		TagListResp resp = new TagListResp();
		resp.setTagList(tags);
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(listReq,resp);
		return resp;
	}

	/**
	 * @title 查询标签关联商品
	 * @param 
	 */
	@Override
	public TagGoodsListResp listGoodsByTagId(TagGoodsListReq listReq) {
		String tag_id = listReq.getTag_id();
		GoodsNetCacheRead reader = new GoodsNetCacheRead();
		List<Goods> goods = reader.getCacheGoodsRelTags(tag_id);
		TagGoodsListResp resp = new TagGoodsListResp();
		resp.setGoodsList(goods);
		if(goods!=null && goods.size()>0){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		addReturn(listReq,resp);
		return resp;
	}

	@Override
	public PackageGetResp getPackage(PackageGetReq req) {
		String goods_id = req.getGoods_id();
		List<String> packages = tagManager.list(goods_id);
		
		PackageGetResp resp = new PackageGetResp();
		if(packages!=null && packages.size()>0){
			String package_id = packages.get(0);
			resp.setError_code("0");
			resp.setError_msg("成功");
			resp.setResult(true);
			resp.setPackage_id(package_id);
		}
		else{
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
			resp.setResult(false);
		}
		addReturn(req,resp);
		return resp;
	}

}
