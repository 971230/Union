package com.ztesoft.net.mall.widget.member;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;

import services.FavoriteInf;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 我的收藏
 * 
 * @author kingapex
 * 
 */
public class MemberFavoriteAction extends WWAction {

   @Resource
   private FavoriteInf favoriteServ;
	
	private String json ="";

	@Override
	public String getJson() {
		return json;
	}
	
	public String colleccion(){
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		String goodsId = request.getParameter("goodsId");
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		
		if(member==null){
			 json = "{'state':2,'message':'请先登录'}";
		}else{
			this.favoriteServ.add(goodsId);
			json = "{'state':0,'message':'收藏成功'}";
		}
		return "json_message";
	}

}
