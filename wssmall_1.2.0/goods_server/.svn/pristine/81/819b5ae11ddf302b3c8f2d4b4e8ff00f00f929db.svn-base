package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.model.support.GoodsView;

import java.util.List;
import java.util.Map;

/**
 * 商品类别管理
 * @author kingapex
 * 2010-1-6上午12:39:09
 */
public interface IGoodsCatManager {
	
	
	
	/**
	 * 根据parentid查询它的字类型
	 * @param parentid
	 * @return
	 */
	public List<Cat> findCatNodesByParentid(String parentid);
	
	/**
	 * 根据cat_id修改商品是否在楼层中展示
	 * @param cat_id
	 * @return
	 */
	public int editFloorListShowById(String id,String floor_list_show);
	
	/**
	 * 获取热词分类
	 * @param catId
	 * @return
	 */
	public List getHotCatsByCatId(String cat_id);
	/**
	 * 获取关联商品
	 * @param catId
	 * @return
	 */
	public List<GoodsView> getComplexGoodsByCatId(String catId);
	public String _getComplexGoodsByCatId(String cat_id);
	/**
	 * 检测类名是否存在
	 * @param name
	 * @return 存在返回真，不存在返回假
	 */
	public boolean checkname(String name,String catid);
	
	
	public List getCatsByGoodsId(String goodsId);
	
	/**
	 * 根据类别id获取类别
	 * @param cat_id
	 * @return
	 */
	public Cat getById(String cat_id);
	
	
	public String getCatPathById(String catId);
	
	
	
	/**
	 * 添加商品类别
	 * @param cat
	 */
	public void saveAdd(Cat cat);
	
	
	public List<Map<String, String>> getCatsByCond(String cond);
	
	public List<Cat> listCatsByUserid(String userid);
	
	public List getCatsByWhereCond(String whereCond);
	
	/**
	 * 更新商品类别
	 * @param cat
	 */
	public void update(Cat cat);
	
	
	
	
	
	/**
	 * 删除商品类别
	 * @param cat_id
	 * @return 1删除失败有子类别，0删除成功
	 */
	public int delete(String cat_id);
	
	
	
	
	
	
	/**
	 * 获取某个类别的所有子类，所有的子孙
	 * @param cat_id
	 * @return
	 */
	public List<Cat> listAllChildren(String cat_id);
	
	/**
	 * 获取某个类别的所有子类，所有的子孙
	 * @param cat_id
	 * @return
	 */
	public List<Cat> listAllChildrenEcs(String cat_id,String type);
	
	
	/**
	 * 获取某个类别的所有子类，所有的子孙
	 * @param cat_id
	 * @return
	 */
	public List<Cat> listAllChildrenByAgentNo(String cat_id);
	
	
	
	
	
	

	/**
	 * 获取二级节点数据
	 * @param cat_id
	 * @return
	 */
	public List<Cat> listAllChildrenForSecond(String cat_id);
	
	
	
	
	/**
	 * 读取某类别下的子类别，只是儿子
	 * @param cat_id
	 * @return
	 */
	public List listChildren(String cat_id);
	
	
	//获取父级目录
	public String getParentIdById(String cat_id);
	
	
	
	
	
	/**
	 * 保存排序
	 * @param cat_ids
	 * @param cat_sorts
	 */
	public void saveSort(String[] cat_ids, int[] cat_sorts) ;
	
	
	public List getNavpath(String cat_id);
	 
	
	public List<Cat> getHotList();
	
	
	public Cat getCatByGoodId(String goodId);
	
	/**
	 * 删除该等级可见
	 * @param id 类别ID
	 * @param lv_id 等级ID
	 * @return
	 */
	public void deleteMemberLvById(String id, String lv_id);
	
	/**
	 * 添加该等级可见
	 * @param id 类别ID
	 * @param lv_id 等级ID
	 * @return
	 */
	public void addMemberLvById(String id, String lv_id);
	
	public List listMemberLv();
	
	public List listCatGoodsCount(String agn,String parent_cat_id);
	
	public List<Cat> listCats(String cat_id);
	
	public List<Cat> listCatsByTypeId(String type_id);
	
	public List<Cat> listCacheCatByTypeId(String type_id);
	
	/**
	 * 分类发布
	 * @param params
	 */
	public void doPublish(Map params);
}
