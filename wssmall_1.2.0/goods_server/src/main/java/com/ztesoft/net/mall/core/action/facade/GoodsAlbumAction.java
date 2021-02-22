package com.ztesoft.net.mall.core.action.facade;

import com.opensymphony.xwork2.Action;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.service.IGoodsManager;

/**
 * 商品相册action
 * 
 * @author kingapex 2010-4-27下午04:03:14
 */
public class GoodsAlbumAction extends WWAction {
	private String goodsid;
	private IGoodsManager goodsManager;
	private Goods goods;
	private StringBuffer albumXml;
	@Override
	public String execute() {
		
		goods = goodsManager.getGoods(goodsid);
		
		String image_file = goods.getImage_file()==null?"":goods.getImage_file().toString();
		 albumXml =new StringBuffer("<products name='"+goods.getName()+"' shopname='12580'>");
		if(image_file!=null && !image_file.equals("")){
			
			image_file = UploadUtil.replacePath(image_file);
			String[] imgs= image_file.split(",");
		
			int i=0;
			for(String img : imgs){
				albumXml.append("<smallpic");
				if(i==0)
					albumXml.append(" selected='selected'");
				albumXml.append(">");
				albumXml.append(UploadUtil.getThumbPath(img, "_small"));
				albumXml.append("</smallpic>");
				
				
				albumXml.append("<bigpic ");
				if(i==0)
					albumXml.append(" selected='selected'");
				albumXml.append(">");
				albumXml.append(UploadUtil.getThumbPath(img, "_big")  +"</bigpic>");
				//albumXml.append("<link>goods-"+goods.getGoods_id()+".html</link>");
				i++;
			}
			
		}
		albumXml.append("</products>");
		
		 
		return Action.SUCCESS;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public IGoodsManager getGoodsManager() {
		return goodsManager;
	}

	public void setGoodsManager(IGoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public StringBuffer getAlbumXml() {
		return albumXml;
	}

	public void setAlbumXml(StringBuffer albumXml) {
		this.albumXml = albumXml;
	}

}
