package com.ztesoft.net.mall.core.service.impl.cache;

import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.framework.cache.AbstractCacheProxy;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.service.IGoodsCatManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 商品分类缓存代理
 * 
 * @author kingapex 2010-5-25上午10:52:51
 */
public class GoodsCatCacheProxy extends AbstractCacheProxy<List<Cat>> implements
		IGoodsCatManager {

	private IGoodsCatManager goodsCatManager;
	private static Logger logger = Logger.getLogger(GoodsCatCacheProxy.class);
	public static final String CACHE_KEY = "goods_cat";

	public GoodsCatCacheProxy(IGoodsCatManager goodsCatManager) {
		super(CACHE_KEY);
		this.goodsCatManager = goodsCatManager;
	}

	private void cleanCache() {
		EopSite site = EopContext.getContext().getCurrentSite();
		this.cache.remove(CACHE_KEY + "_" + site.getUserid() + "_"
				+ site.getId() + "_0");
	}

	@Override
	public int delete(String catId) {
		int r = this.goodsCatManager.delete(catId);
		if (r == 0) {
			this.cleanCache();
		}
		return r;
	}

	@Override
	public Cat getById(String catId) {
		return goodsCatManager.getById(catId);
	}

	@Override
	public List<Cat> listAllChildren(String catId) {
		
		/**
		EopSite site = EopContext.getContext().getCurrentSite();
		String agt_id = StringUtil.getAgnId();
		List<Cat> catList = this.cache.get(CACHE_KEY + "_" + site.getUserid()
				+ "_" + site.getId() + "_" + catId + "_" + agt_id);
		if (catList == null) {
			catList = this.goodsCatManager.listAllChildren(catId);
			this.cache.put(CACHE_KEY + "_" + site.getUserid() + "_"
					+ site.getId() + "_" + catId + "_" + agt_id, catList);
			if (this.logger.isDebugEnabled()) {
				this.logger.debug("load goods cat from database");
			}
		} else {
			if (this.logger.isDebugEnabled()) {
				this.logger.debug("load goods cat from cache");
			}
		}
		return catList; */
		return  this.goodsCatManager.listAllChildren(catId);
	}

	@Override
	public List<Cat> listChildren(String catId) {
		return this.goodsCatManager.listChildren(catId);
	}

	@Override
	public void saveAdd(Cat cat) {
		this.goodsCatManager.saveAdd(cat);
		this.cleanCache();
	}

	@Override
	public void saveSort(String[] catIds, int[] catSorts) {
		this.goodsCatManager.saveSort(catIds, catSorts);
		this.cleanCache();
	}

	@Override
	public void update(Cat cat) {
		this.goodsCatManager.update(cat);
		this.cleanCache();
	}

	@Override
	public boolean checkname(String name, String catid) {
		return this.goodsCatManager.checkname(name, catid);
	}

	@Override
	public List getNavpath(String catId) {
		List list = new ArrayList();
		Map map = new HashMap();
		map.put("name", "首页");
		map.put("value", "0");
		list.add(map);
		Cat cat = getById(catId);
		String path = cat.getCat_path();
		path = path.substring(2, path.length() - 1);
		path = path.replace("|", ",");
		String[] ids = path.split(",");
		for (String id : ids) {
			Cat pcat = getById(id);
			Map pmap = new HashMap();
			pmap.put("name", pcat.getName());
			pmap.put("value", id);
			list.add(pmap);
		}
		return list;
	}

	public static void main(String[] args) {
		String path = "0|1|2|3|4|";
		path = path.substring(2, path.length() - 1);
		path = path.replace("|", ",");
		logger.info(path);
	}

	@Override
	public List<Cat> getHotList() {
		return this.goodsCatManager.getHotList();
	}

	@Override
	public String getParentIdById(String cat_id) {
		return this.goodsCatManager.getParentIdById(cat_id);
	}
	@Override
	public String getCatPathById(String cat_id) {
		return this.goodsCatManager.getCatPathById(cat_id);
	}
	
	@Override
	public List<Cat> listAllChildrenForSecond(String cat_id) {
		return this.goodsCatManager.listAllChildrenForSecond(cat_id);
	}

	@Override
	public List<Cat> listAllChildrenByAgentNo(String cat_id) {
		return this.goodsCatManager.listAllChildrenByAgentNo(cat_id);
	}

	@Override
	public List getCatsByGoodsId(String goodsId) {
		return this.goodsCatManager.getCatsByGoodsId(goodsId);
	}

	@Override
	public int editFloorListShowById(String id, String floor_list_show) {
		// TODO Auto-generated method stub
		return this.goodsCatManager.editFloorListShowById(id, floor_list_show);
	}
	
	@Override
	public List getComplexGoodsByCatId(String catId) {
		return this.goodsCatManager.getComplexGoodsByCatId(catId);
	}

	@Override
	public List getCatsByCond(String whereCond) {
		return this.goodsCatManager.getCatsByCond(whereCond);
	}

	@Override
	public Cat getCatByGoodId(String goodId) {
		return this.goodsCatManager.getCatByGoodId(goodId);
	}

	@Override
	public List<Cat> findCatNodesByParentid(String parentid) {
		return this.goodsCatManager.findCatNodesByParentid(parentid);
	}
	
	@Override
	public void addMemberLvById(String id, String lv_id) {
		this.goodsCatManager.addMemberLvById(id, lv_id);
		
	}

	@Override
	public void deleteMemberLvById(String id, String lv_id) {
		this.goodsCatManager.deleteMemberLvById(id, lv_id);
	}

	@Override
	public List listMemberLv() {
		return this.goodsCatManager.listMemberLv();
	}

	@Override
	public String _getComplexGoodsByCatId(String cat_id) {
		// TODO Auto-generated method stub
		return this.goodsCatManager._getComplexGoodsByCatId(cat_id);
	}

	@Override
	public List getHotCatsByCatId(String cat_id) {
		// TODO Auto-generated method stub
		return this.goodsCatManager.getHotCatsByCatId(cat_id);
	}

	@Override
	public List listCatGoodsCount(String agn,String parent_cat_id) {
		// TODO Auto-generated method stub
		return this.goodsCatManager.listCatGoodsCount(agn,parent_cat_id);
	}

	@Override
	public List getCatsByWhereCond(String whereCond) {
		// TODO Auto-generated method stub
		return this.goodsCatManager.getCatsByWhereCond(whereCond);
	}

	@Override
	public List<Cat> listAllChildrenEcs(String cat_id, String type) {
		// TODO Auto-generated method stub
		return this.goodsCatManager.listAllChildrenEcs(cat_id, type);
	}

	@Override
	public List<Cat> listCats(String cat_id) {
		// TODO Auto-generated method stub
		return this.goodsCatManager.listCats(cat_id);
	}

	@Override
	public void doPublish(Map params) {
		this.goodsCatManager.doPublish(params);
	}

	@Override
	public List<Cat> listCatsByTypeId(String type_id) {
		return this.goodsCatManager.listCatsByTypeId(type_id);
	}

	@Override
	public List<Cat> listCacheCatByTypeId(String type_id) {
		return this.goodsCatManager.listCacheCatByTypeId(type_id);
	}

	@Override
	public List<Cat> listCatsByUserid(String userid) {
		return this.goodsCatManager.listCatsByUserid(userid);
	}
}