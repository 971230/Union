package com.ztesoft.net.mall.plugin.standard.tag;

import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.mall.core.model.Tag;
import com.ztesoft.net.mall.core.plugin.goods.AbstractGoodsPlugin;
import com.ztesoft.net.mall.core.service.ITagManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 商品发布地区插件
 * @author kingapex
 * 2010-1-18下午02:56:43
 */
public class GoodsTagPlugin extends AbstractGoodsPlugin {
	private ITagManager tagManager;
	
	
	@Override
	public void addTabs() {
 
	}
	
	/*为添加商品和修改商品页面填充必要的数据*/
	
	@Override
	public String onFillGoodsAddInput(HttpServletRequest request) {
		List<Tag> taglist  = this.tagManager.listEdit();
		FreeMarkerPaser freeMarkerPaser = new FreeMarkerPaser(getClass());
		freeMarkerPaser.setPageName("tag");
		freeMarkerPaser.putData("tagList", taglist);
		return freeMarkerPaser.proessPageContent();
	}
	
	
	
	@Override
	public String onFillGoodsEditInput(Map goods, HttpServletRequest request) {
		List<Tag> taglist  = this.tagManager.listEdit(); //编辑页面设置
	 
		String goods_id =  goods.get("goods_id").toString();
		List<String> tagIds=this.tagManager.list(goods_id);
		FreeMarkerPaser freeMarkerPaser = new FreeMarkerPaser(getClass());
		freeMarkerPaser.setPageName("tag");
		freeMarkerPaser.putData("tagList", taglist);	
		freeMarkerPaser.putData("tagRelList", tagIds);
		return freeMarkerPaser.proessPageContent();
	}

	
	
	
	/*在保存添加和保存更新的时候，将tagid的数组和goodsid对应起来保存在库里*/
	
	
	
	@Override
	public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
			throws RuntimeException {
		this.save(goods, request);

	}

	
	@Override
	public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
			 {
		this.save(goods, request);

	}

	private void save(Map goods, HttpServletRequest request){
		String goods_id =  goods.get("goods_id").toString();
		
		String[] tagstr=  request.getParameterValues("tag_id");
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
	public void onBeforeGoodsEdit(Map goods, HttpServletRequest request){
		 

	}

	
	@Override
	public void onBeforeGoodsAdd(Map goods, HttpServletRequest request)
			 {
		 
		 
	}

	
	
	@Override
	public String getAuthor() {
		return "kingapex";
	}

	
	@Override
	public String getId() {
		return "goodstag";
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
