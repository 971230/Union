package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Favorite;
import com.ztesoft.net.mall.core.service.IFavoriteManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

import java.util.List;

/**
 * 我的收藏
 * 
 * @author lzf<br/>
 *         2010-3-24 下午02:54:26<br/>
 *         version 1.0<br/>
 */
public class FavoriteManager extends BaseSupport implements IFavoriteManager {

	@Override
	public Page list(int pageNo, int pageSize) {
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		if (member == null)
			return new Page();
		// String sql = SF.goodsSql("GET_FAVORITE_BY_MEMBER_ID");
		String sql = "select g.*, f.favorite_id from es_favorite f left join es_goods g on g.goods_id = f.goods_id where f.member_id = ? AND f.source_from=?";
		Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize,
				member.getMember_id(), ManagerUtils.getSourceFrom());
		return page;
	}

	@Override
	public void delete(String favorite_id) {
		this.baseDaoSupport.execute(SF.goodsSql("FAVORITE_DEL"), favorite_id);
	}

	@Override
	public void add(String goodsid) {
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		Favorite favorite = new Favorite();
		favorite.setGoods_id(goodsid);
		favorite.setMember_id(member.getMember_id());
		this.baseDaoSupport.insert("favorite", favorite);
	}

	@Override
	public List list() {
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();

		return this.baseDaoSupport.queryForList(
				SF.goodsSql("GET_FAVORITE_BY_MEMBER_ID_0"), Favorite.class,
				member.getMember_id());
	}

	@Override
	public Page listFavPartner(String member_id, int pageNo, int pageSize) {
		String sql = SF.goodsSql("LIST_FAVPARTNER");
		Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize,
				member_id);
		return page;
	}

	@Override
	public Page listFavGoods(String member_id, int pageNo, int pageSize) {

		String sql = SF.goodsSql("LIST_FAV_GOODS");
		Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize,
				member_id);
		return page;
	}

	@Override
	public Page listFavSuppler(String member_id, int pageNo, int pageSize) {
		String sql = SF.goodsSql("LIST_FAVSUPPLER");
		Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize,
				member_id);
		return page;
	}

	@Override
	public int countFavByMemIdAndGoods(String member_id, String goods_id,String type) {
		String sql = SF.goodsSql("COUNT_FAVBY_MEM_IDANDGOODS_ID");
		int count = this.daoSupport.queryForInt(sql, member_id, goods_id,type);
		return count;
	}

	@Override
	public void addFav(String fav_rel_id, String member_id, String fav_type) {
		Favorite favorite = new Favorite();
		favorite.setGoods_id(fav_rel_id);
		favorite.setFavorite_type(fav_type);
		favorite.setMember_id(member_id);
		this.baseDaoSupport.insert("favorite", favorite);
	}

}
