package com.ztesoft.net.mall.core.action.backend;

import com.opensymphony.xwork2.Action;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.service.IGoodsAlbumManager;

/**
 * 商品相册缩略图生成action
 * @author kingapex
 *
 */
public class GalleryAction extends WWAction {
	private IGoodsAlbumManager goodsAlbumManager;
	private int total;
	private int start;
	private int end;
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String recreate(){
		try{
			this.goodsAlbumManager.recreate(start,end);
			this.showSuccessJson("生成商品相册缩略图成功 ");
		}catch(RuntimeException e){
			this.logger.info("生成商品相册缩略图错误", e);
			this.showErrorJson("生成商品相册缩略图错误"+e.getMessage());
		}
		return  WWAction.JSON_MESSAGE;
	}
	
	@Override
	public String execute(){
		total = this.goodsAlbumManager.getTotal();
		return Action.INPUT;
	}
		
	public IGoodsAlbumManager getGoodsAlbumManager() {
		return goodsAlbumManager;
	}
	public void setGoodsAlbumManager(IGoodsAlbumManager goodsAlbumManager) {
		this.goodsAlbumManager = goodsAlbumManager;
	}	

}
