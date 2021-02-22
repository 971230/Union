package com.ztesoft.net.mall.widget.goods.list;

import com.ztesoft.net.eop.sdk.widget.AbstractWidget;
import com.ztesoft.net.mall.core.model.Tag;
import com.ztesoft.net.mall.core.service.IGoodsCatManager;
import com.ztesoft.net.mall.core.service.ITagManager;
import com.ztesoft.net.mall.widget.goods.list.Term;

import java.util.List;
import java.util.Map;

/**
 * 楼层数据挂件
 * 
 * @author wui
 * 
 */
public class TagsGoods extends AbstractWidget {
	private GoodsDataProvider goodsDataProvider;
	private IGoodsCatManager goodsCatManager;
	private ITagManager tagManager;

	/**
	 * 
	 * 1、获取一级分类 2、获取一级分类的二级分类 3、获取二级分类的三级分类 4、获取一级分类的广告 5、获取二级分类的商品
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void display(Map<String, String> params) {
		List<Tag>  tags= tagManager.list();
		for (Tag tag:tags) {
			// 获取二级节点
			Term term = new Term();
			term.setTagid(tag.getTag_id());
			List goodsList = goodsDataProvider.list(term, new Integer(params.get("goods_num")).intValue());// 设置显示的数量
			tag.setTagGoodsList(goodsList);
		}
		this.putData("tags", tags);
	}

	@Override
	protected void config(Map<String, String> params) {

	}

	public IGoodsCatManager getGoodsCatManager() {
		return goodsCatManager;
	}

	public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
		this.goodsCatManager = goodsCatManager;
	}

	public GoodsDataProvider getGoodsDataProvider() {
		return goodsDataProvider;
	}

	public void setGoodsDataProvider(GoodsDataProvider goodsDataProvider) {
		this.goodsDataProvider = goodsDataProvider;
	}

	public ITagManager getTagManager() {
		return tagManager;
	}

	public void setTagManager(ITagManager tagManager) {
		this.tagManager = tagManager;
	}

}
