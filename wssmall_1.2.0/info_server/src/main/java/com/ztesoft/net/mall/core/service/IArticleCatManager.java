package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.app.base.core.model.ArticleCat;

/**
 * 文章分类管理
 * 
 * @author lzf<br/>
 *         2010-4-1 下午05:00:40<br/>
 *         version 1.0<br/>
 */
public interface IArticleCatManager {
	
	public ArticleCat getById(String cat_id);
	
	public void saveAdd(ArticleCat cat);
	
	public void update(ArticleCat cat);
	
	public int delete(String cat_id);
	
	public void saveSort(String[] cat_ids, int[] cat_sorts);
	
	public List listChildById(String cat_id);
	
	public List listHelp(String cat_id);
}
