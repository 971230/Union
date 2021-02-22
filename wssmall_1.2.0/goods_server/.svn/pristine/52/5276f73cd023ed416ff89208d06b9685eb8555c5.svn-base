package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Tag;

/**
 * 标签管理
 * @author kingapex
 * 2010-1-17下午01:03:41
 */
public interface ITagManager {
	
	/**
	 * 检测标签名是否同名
	 * @param name 标签名
	 * @param tagid 要排除的标签id,编辑时判断重名所用
	 * @return 存在重名返回真，不存在返回假
	 */
	public boolean checkname(String name,String tagid);
	
	
	/**
	 * 检测某些标签是否已经关联商品
	 * @param tagids 标签id数组
	 * @return 有关联返回真，否则返回假
	 */
	public boolean checkJoinGoods(String[] tagids);
	
	
	public Tag getById(String tagid);
	
	public void add(Tag tag);
	
	public void update(Tag tag);
	
	public void delete(String[] tagids);
	
	public Page list(int pageNo,int pageSize);
	
	public Page goodlist(int pageNo,int pageSize);
	
	public Page shoplist(int pageNo,int pageSize);
	
	public Page searchTag(String table,int pageNo,int pageSize,String tag_name,String tag_type);
	
	public Page showTag(String table,int pageNo,int pageSize);
	
	public Page showTagList(String table,int pageNo,int pageSize);
	
	public List<Tag> list();
	public List<Tag> listEdit();
	
	/**
	 * 读取某个引用的标签id集合
	 * @param relid
	 * @return
	 */
	public List<String> list(String relid); 
	
	
	//向标签表中添加标签
	public void insertTag(String table,Tag tag) ;
	
	//判断标签编码是否存在
	public long countTagByCode(String table,String tag_code);
	
	//删除标签
	public void deleteTag(String table,String tag_code);
	
	//更新标签
	public void updateTagById(String table,String tag_id,Map tagMap);
	
	public List qryTree();
	
	//失效某标签
	public void invaliTag(String table,String[] tag_codes);
	
	//同步标签
	public void syncTag(String table,String[] tag_ids);
	
	public Tag getByCode(String table,String tag_code);
	/**
	 * 某个引用设置标签
	 * @param relid
	 * @param tagids
	 */
	public void saveRels(String relid,String[] tagids);
	
	/**
	 * 根据标签分类（商品或活动或其他）
	 * @param catType
	 * @return
	 */
	public List<Tag> ListByCatType(String catType);
	
}
