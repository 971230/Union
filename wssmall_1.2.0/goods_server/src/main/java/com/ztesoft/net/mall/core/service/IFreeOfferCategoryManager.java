package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.FreeOfferCategory;

import java.util.List;

/**
 * 赠品分类管理
 * 
 * @author 李志富 lzf<br/>
 *         2010-1-18 上午10:10:57<br/>
 *         version 1.0<br/>
 * <br/>
 */
public interface IFreeOfferCategoryManager {
	public FreeOfferCategory get(String cat_id);

	public void saveAdd(FreeOfferCategory freeOfferCategory);

	public void update(FreeOfferCategory freeOfferCategory);

	public void delete(String bid);

	public void revert(String bid);

	public void clean(String bid);

	public List getFreeOfferCategoryList();

	public Page searchFreeOfferCategory(String name, String order, int page,
			int pageSize);

	public Page pageTrash(String name, String order, int page, int pageSize);

}
