package com.ztesoft.net.mall.plugin.standard.tag;

import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.mall.core.model.Tag;
import com.ztesoft.net.mall.core.params.GoodsExtData;
import com.ztesoft.net.mall.core.plugin.goods.AbstractGoodsPluginN;
import com.ztesoft.net.mall.core.service.ITagManager;

import java.util.List;
import java.util.Map;

/**
 * 商品标签插件
 * @author zou.qh
 * 2015-1-12
 */
public class GoodsTagPluginN extends AbstractGoodsPluginN {
	private ITagManager tagManager;
	
	@Override
	public void addTabs() {
 
	}
	
	/**
	 * 为添加商品页面填充标签信息
	 */
	@Override
	public String onFillGoodsAddInput() {
		List<Tag> taglist  = this.tagManager.listEdit();
		FreeMarkerPaser freeMarkerPaser = new FreeMarkerPaser(getClass());
		freeMarkerPaser.setPageName("tagn");
		freeMarkerPaser.putData("tagList", taglist);
		return freeMarkerPaser.proessPageContent();
	}
	
	/**
	 * 为修改商品页面填充标签信息
	 */
	@Override
	public String onFillGoodsEditInput(Map goods, GoodsExtData goodsExtData) {
		List<Tag> taglist  = this.tagManager.listEdit(); //编辑页面设置
	 
		String goods_id =  goods.get("goods_id").toString();
		List<String> tagIds=this.tagManager.list(goods_id);
		FreeMarkerPaser freeMarkerPaser = new FreeMarkerPaser(getClass());
		freeMarkerPaser.setPageName("tagn");
		freeMarkerPaser.putData("tagList", taglist);	
		freeMarkerPaser.putData("tagRelList", tagIds);
		return freeMarkerPaser.proessPageContent();
	}
	
	/*在保存添加和保存更新之后，将tagid的数组和goodsid对应起来保存在库里*/
	@Override
	public void onAfterGoodsAdd(Map goods, GoodsExtData goodsExtData)throws RuntimeException {
		this.save(goods, goodsExtData);
	}
	/*在保存添加和保存更新之后，将tagid的数组和goodsid对应起来保存在库里*/
	@Override
	public void onAfterGoodsEdit(Map goods, GoodsExtData goodsExtData) {
		this.save(goods, goodsExtData);
	}

	private void save(Map goods, GoodsExtData goodsExtData){
		String goods_id =  goods.get("goods_id").toString();
		
		String[] tagstr=  goodsExtData.getTag_id();
		String[] tagids = null;
		if(tagstr!=null){
			tagids = new String[tagstr.length];
			for(int i=0;i<tagstr.length;i++){
				tagids[i]=	tagstr[i] ;
			}
		}
		this.tagManager.saveRels(goods_id, tagids);
	}
	
	@Override
	public void onBeforeGoodsEdit(Map goods, GoodsExtData goodsExtData){
		
	}

	@Override
	public void onBeforeGoodsAdd(Map goods, GoodsExtData goodsExtData){
		 
	}
	
	@Override
	public String getAuthor() {
		return "zou.qh";
	}
	
	@Override
	public String getId() {
		return "goodstagN";
	}
	
	@Override
	public String getName() {
		return "商品标签";
	}
	
	@Override
	public String getType() {
		return null;
	}
	
	@Override
	public String getVersion() {
		return "1.0";
	}
	
	@Override
	public void perform(Object... params) {

	}

	public void setTagManager(ITagManager tagManager) {
		this.tagManager = tagManager;
	}

}
