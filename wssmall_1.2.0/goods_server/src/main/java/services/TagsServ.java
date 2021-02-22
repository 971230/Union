package services;

import com.ztesoft.net.mall.core.model.Tag;
import com.ztesoft.net.mall.core.model.support.GoodsView;
import com.ztesoft.net.mall.core.service.IGoodsCatManager;
import com.ztesoft.net.mall.core.service.ITagManager;
import com.ztesoft.net.mall.widget.goods.list.GoodsDataProvider;
import com.ztesoft.net.mall.widget.goods.list.Term;

import params.tags.req.TagReq;
import params.tags.resp.TagResp;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品标签服务类
 * 
 * @author wui
 * 
 * 
 */
public class TagsServ extends ServiceBase implements TagsInf{

	private GoodsDataProvider goodsDataProvider;
	private IGoodsCatManager goodsCatManager;
	private ITagManager tagManager;
	
	/**
	 * 查询所有标签商品
	 * @param json
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public TagResp queryAllTagsGoods(TagReq tagReq) {

		TagResp tagResp = new TagResp();
		List<Tag>  tags= tagManager.list();
		for (Tag tag:tags) {
			// 获取二级节点
			Term term = new Term();
			term.setTagid(tag.getTag_id());
			List<GoodsView> goodsList = goodsDataProvider.list(term, new Integer(tagReq.getGoods_num()).intValue());// 设置显示的数量
			tag.setTagGoodsList(goodsList);
		}
		tagResp.setTags(tags);
		
		addReturn(tagReq, tagResp);
		
		return tagResp;
	}

	/**
	 * 查询指定标签商品
	 * @param json
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public TagResp queryTagsGoodsByTagId(TagReq tagReq) {

		TagResp tagResp = new TagResp();
		Tag tag= tagManager.getById(tagReq.getTag_id());
		List<Tag> tags = new ArrayList<Tag>();
		Term term = new Term();
		term.setTagid(tag.getTag_id());
		List<GoodsView> goodsList = goodsDataProvider.list(term, new Integer(tagReq.getGoods_num()).intValue());// 设置显示的数量
		tag.setTagGoodsList(goodsList);
		tags.add(tag);
		tagResp.setTags(tags);
		
		addReturn(tagReq, tagResp);
		return tagResp;
	}

	
	
	public GoodsDataProvider getGoodsDataProvider() {
		return goodsDataProvider;
	}

	public void setGoodsDataProvider(GoodsDataProvider goodsDataProvider) {
		this.goodsDataProvider = goodsDataProvider;
	}

	public IGoodsCatManager getGoodsCatManager() {
		return goodsCatManager;
	}

	public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
		this.goodsCatManager = goodsCatManager;
	}

	public ITagManager getTagManager() {
		return tagManager;
	}

	public void setTagManager(ITagManager tagManager) {
		this.tagManager = tagManager;
	}
	
	
}