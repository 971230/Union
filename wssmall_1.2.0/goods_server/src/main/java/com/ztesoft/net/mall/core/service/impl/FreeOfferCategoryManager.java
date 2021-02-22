package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.FreeOfferCategory;
import com.ztesoft.net.mall.core.service.IFreeOfferCategoryManager;
import com.ztesoft.net.sqls.SF;

import java.util.List;

/**
 * 赠品分类管理
 * 
 * @author 李志富 lzf<br/>
 *         2010-1-18 上午10:14:22<br/>
 *         version 1.0<br/>
 * <br/>
 */
public class FreeOfferCategoryManager extends BaseSupport<FreeOfferCategory>
		implements IFreeOfferCategoryManager {

	
	@Override
	public void clean(String bid) {
		if (bid == null || bid.equals(""))
			return;
		String sql = SF.goodsSql("FREEOFFER_CATEGORY_CLEAN") + " and cat_id in (" + bid + ")";
		this.baseDaoSupport.execute(sql);
	}

	
	@Override
	public void delete(String bid) {
		if (bid == null || bid.equals(""))
			return;
		String sql = SF.goodsSql("FREEOFFER_CATEGORY_UPDATE") + " and cat_id in (" + bid + ")";
		this.baseDaoSupport.execute(sql);

	}

	
	@Override
	public FreeOfferCategory get(String cat_id) {
		String sql = SF.goodsSql("FREE_OFFER_CATEGORY");
		return baseDaoSupport.queryForObject(sql, FreeOfferCategory.class,
				cat_id);
	}

	
	@Override
	public List getFreeOfferCategoryList() {
		String sql=SF.goodsSql("GET_FREE_OFFER_CATEGORY_LIST");
		return baseDaoSupport.queryForList(sql);
	}

	
	@Override
	public Page pageTrash(String name, String order, int page, int pageSize) {
		order = order == null ? " cat_id desc" : order;
		String sql = SF.goodsSql("FREEOFFER_CATEGORY");
		name = name == null ? " disabled=1 ": " disabled=1 and cat_name like '%" + name+ "%'";
		sql += " where " + name;
		sql += " order by  " + order;
		Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize);
		return webpage;
	}

	
	@Override
	public void revert(String bid) {
		if (bid == null || bid.equals(""))
			return;
		String sql = SF.goodsSql("FREEOFFER_CATEGORY_REVERT") + " and cat_id in (" + bid + ")";
		this.baseDaoSupport.execute(sql);
	}

	
	@Override
	public void saveAdd(FreeOfferCategory freeOfferCategory) {
		this.baseDaoSupport.insert("freeoffer_category", freeOfferCategory);
	}

	
	@Override
	public Page searchFreeOfferCategory(String name, String order, int page,
			int pageSize) {
		order = order == null ? " cat_id desc" : order;
		String sql = SF.goodsSql("FREEOFFER_CATEGORY");
		name = name == null ? " disabled=0 ": " disabled=0 and cat_name like '%" + name+ "%'";
		sql += " where " + name;
		sql += " order by  " + order;
		Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize);
		return webpage;
	}

	
	@Override
	public void update(FreeOfferCategory freeOfferCategory) {
		this.baseDaoSupport.update("freeoffer_category",
				freeOfferCategory, "cat_id="
						+ freeOfferCategory.getCat_id());

	}

}
