package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.WarehouseGoodsStore;
import com.ztesoft.net.mall.core.model.WarhouseGoodsStore;
import com.ztesoft.net.mall.core.service.IWarhouseGoodsStoreManager;
import com.ztesoft.net.sqls.SF;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 仓库商品库存管理类
 * 
 * @作者 MoChunrun
 * @创建日期 2013-11-14
 * @版本 V 1.0
 */
public class WarhouseGoodsStoreManager extends BaseSupport implements
		IWarhouseGoodsStoreManager {

	@Override
	public int sumStoreByGoodsId(String goods_id, String house_id) {
		String sql = SF.goodsSql("SUM_STORE_BY_GOODSID");
		return this.baseDaoSupport.queryForInt(sql, goods_id, house_id);
	}

	@Override
	public List<WarhouseGoodsStore> sumStoreByGoodsAndHouse(String goodsIds,
			String hous_id) {
		String sql = SF.goodsSql("SUM_STORE_BY_GOODSANDHOUSE").replace("replaceSql", goodsIds);
		return this.baseDaoSupport.queryForList(sql, WarhouseGoodsStore.class, hous_id);
	}

	
	@Override
	public void addStore(String goods_id, String house_id, int addStore) {
		String qrySql = SF.goodsSql("WARHOUSE_GOODS_STORE");
		List list = this.baseDaoSupport
				.queryForList(qrySql, goods_id, house_id);
		if (list != null && list.size() > 0) {
			String sql = SF.goodsSql("warhouse_goods_store_update");
			this.baseDaoSupport.execute(sql, addStore, goods_id, house_id);
		} else {
			WarhouseGoodsStore wg = new WarhouseGoodsStore();
			wg.setFreeze_store(0);
			wg.setGoods_id(goods_id);
			wg.setHouse_id(house_id);
			wg.setStore(addStore);
			this.baseDaoSupport.insert("es_warehouse_goods_store", wg);
		}
	}

	@Override
	public void descStore(String goods_id, String house_id, int descStore) {
		String sql = SF.goodsSql("DESC_STORE");
		this.baseDaoSupport.execute(sql, descStore, goods_id, house_id);
	}

	@Override
	public void updateGoodsStore(String goods_id, int num) {
		String sql = SF.goodsSql("UPDATE_GOODS_STORE");
		this.baseDaoSupport.execute(sql,num,goods_id);
	}

	@Override
	public Page goodsStoreList(String goods_id,String goods_name, String sn,String house_name, int pageNo,
			int pageSize) {
		String sql = SF.goodsSql("GOODS_STORE_LIST");
		StringBuffer sqlbuf = new StringBuffer(" ");
		ArrayList partm = new ArrayList();

		if (StringUtils.isNotEmpty(goods_name)) {
			sqlbuf.append(" AND d.name like ?");
			partm.add("%" + goods_name + "%");
		}
		
		if (StringUtils.isNotEmpty(house_name)) {
			sqlbuf.append(" AND c.house_name like ?");
			partm.add("%" + house_name + "%");
		}
		
		if (StringUtils.isNotEmpty(goods_id)) {
			sqlbuf.append(" AND d.goods_id = ?");
			partm.add(goods_id.trim());
		}

		if (StringUtils.isNotEmpty(sn)) {
			sqlbuf.append(" AND d.sn like ?");
			partm.add("%" + sn + "%");
		}

		sql=sql.replaceAll("#cont", sqlbuf.toString());
		String countSql = SF.goodsSql("GOODS_STORE_COUNT");
		countSql = countSql.replaceAll("#cont", sqlbuf.toString());
		return this.daoSupport.queryForCPage(sql, pageNo, pageSize,WarehouseGoodsStore.class, countSql, partm.toArray());
	}

	@Override
	public Page warhouseStoreList(String house_name,String goods_name, String sn,
			String transit_store_compare, String transit_store,
			String store_compare, String store, int page, int pageSize) {
		String sql = SF.goodsSql("WARHOUSE_STORE_LIST");

		StringBuffer sqlbuf = new StringBuffer(" ");
		ArrayList partm = new ArrayList();

		if (StringUtils.isNotEmpty(house_name)) {
			sqlbuf.append(" AND b.house_name like ?");
			partm.add("%" + house_name + "%");
		}
		
		if (StringUtils.isNotEmpty(goods_name)) {
			sqlbuf.append(" AND d.name like ?");
			partm.add("%" + goods_name + "%");
		}

		if (StringUtils.isNotEmpty(sn)) {
			sqlbuf.append(" AND d.sn like ?");
			partm.add("%" + sn + "%");
		}

//		if (StringUtils.isNotEmpty(transit_store)) {
//			if ("0".equals(freeze_store_compare)) {// =
//				sqlbuf.append(" AND a.freeze_store = ?");
//				partm.add(transit_store);
//			} else if ("1".equals(freeze_store_compare)) {// >
//				sqlbuf.append(" AND a.freeze_store > ?");
//				partm.add(transit_store);
//			} else if ("2".equals(freeze_store_compare)) {// <
//				sqlbuf.append(" AND a.freeze_store < ?");
//				partm.add(transit_store);
//			}
//		}

		if (StringUtils.isNotEmpty(store)) {
			if ("0".equals(store_compare)) {// =
				sqlbuf.append(" AND a.store = ?");
				partm.add(store);
			} else if ("1".equals(store_compare)) {// >
				sqlbuf.append(" AND a.store > ?");
				partm.add(store);
			} else if ("2".equals(store_compare)) {// <
				sqlbuf.append(" AND a.store < ?");
				partm.add(store);
			}
		}

		sql = sql.replaceAll("#cont", sqlbuf.toString());
		String countSql = SF.goodsSql("WARHOUSE_GOODS_STORE_COUNT");
		countSql = countSql.replaceAll("#cont", sqlbuf.toString());
		return this.baseDaoSupport.queryForCPage(sql, page, pageSize,
				WarehouseGoodsStore.class, countSql, partm.toArray());
	}

	@Override
	public Page sumStoreList(String sn,String goods_name,int pageNo, int pageSize) {
		String sql = SF.goodsSql("SUM_STORE_LIST");

		StringBuffer sqlbuf = new StringBuffer(" ");
		ArrayList partm = new ArrayList();

		if (StringUtils.isNotEmpty(sn)) {
			sqlbuf.append(" AND d.sn like ?");
			partm.add("%" + sn + "%");
		}

		if (StringUtils.isNotEmpty(goods_name)) {
			sqlbuf.append(" AND d.name like ?");
			partm.add("%" + goods_name + "%");
		}
		
		sql = sql.replaceAll("#cont", sqlbuf.toString());
		String countSql = SF.goodsSql("SUM_STORE_COUNT");
		countSql = countSql.replaceAll("#cont", sqlbuf.toString());
		return this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize,
				WarehouseGoodsStore.class, countSql, partm.toArray());
	}

}
