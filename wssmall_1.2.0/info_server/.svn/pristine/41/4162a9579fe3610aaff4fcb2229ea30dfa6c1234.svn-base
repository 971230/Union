package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.app.base.core.model.Article;
import com.ztesoft.net.framework.database.Page;

/**
 * 文章管理
 * 
 * @author lzf<br/>
 *         2010-4-1 下午05:00:10<br/>
 *         version 1.0<br/>
 */
public interface IArticleManager {
	
	public void saveAdd(Article article);
	
	public void saveEdit(Article article);
	
	public Article get(String id);
	
	public List listByCatId(Integer cat_id);
	
	public Page pageByCatId(Integer pageNo, Integer pageSize, String cat_id);
	
	public List topListByCatId(Integer cat_id, Integer top_num);
	
	public String getCatName(Integer cat_id);
	
	public void delete(String id);
	/**
	 * 根据工号删除文章信息并归档到历史表
	 * 
	 * @param staff_no
	 */
	public void delArticleByStaffNo(String staff_no);

}
