package com.ztesoft.net.mall.core.action.facade;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.service.IFavoriteManager;

/**
 * 商品收藏action
 * @author kingapex
 *2010-4-27下午05:42:45
 */
public class FavoriteAction extends WWAction {
	
	private IFavoriteManager favoriteManager;
	private String goodsid;
	
	@Override
	public String execute(){
		Member member  = UserServiceFactory.getUserService().getCurrentMember();
		if(member!=null){
			this.favoriteManager.add(goodsid);
			this.json="{result:1,message:'收藏成功'}";
		}else{
			this.json="{result:0,message:'您尚未登陆，不能使用收藏功能'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	public IFavoriteManager getFavoriteManager() {
		return favoriteManager;
	}
	public void setFavoriteManager(IFavoriteManager favoriteManager) {
		this.favoriteManager = favoriteManager;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	
	
}