package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsInventory;
import com.ztesoft.net.mall.core.model.WarhouseInventory;
import com.ztesoft.net.mall.core.service.IGoodsInventoryManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

import java.util.ArrayList;
import java.util.List;

/**
 * 盘点管理
 * @作者 MoChunrun
 * @创建日期 2013-11-25 
 * @版本 V 1.0
 */
public class GoodsInventoryManager extends BaseSupport implements IGoodsInventoryManager {

	@Override
	public void saveGoodsInventory(GoodsInventory goodsInventory) {
		this.baseDaoSupport.insert("es_warehouse_goods_inventory", goodsInventory);
	}

	@Override
	public WarhouseInventory getWarhouseInventory(String inventory_id) {
		String sql = SF.goodsSql("GET_WARHOUSEINVENTORY");
		return (WarhouseInventory) this.baseDaoSupport.queryForObject(sql, WarhouseInventory.class, inventory_id);
	}

	@Override
	public List<GoodsInventory> qryGoodsInventoryByInventoryId(String inventory_id) {
		String sql = SF.goodsSql("QRY_GOODSINVENTORY_BY_INVENTORYID");
		return this.baseDaoSupport.queryForList(sql,GoodsInventory.class, inventory_id);
	}

	@Override
	public List<WarhouseInventory> qryByHouseId(String house_id, Integer status) {
		String sql = SF.goodsSql("QRY_BY_HOUSEID");
		if(status!=null)sql += " and status="+status;
		return this.baseDaoSupport.queryForList(sql,WarhouseInventory.class, house_id);
	}

	@Override
	public void saveWarhouseInventory(WarhouseInventory warhouseInventory) {
		String inventory_id = this.baseDaoSupport.getSequences("s_es_warehouse_inventory");
		warhouseInventory.setInventory_id(inventory_id);
		this.baseDaoSupport.insert("es_warehouse_inventory", warhouseInventory);
	}

	@Override
	public Page qryWarhouseInventoryPage(int pageNo, int pageSize,
			Integer status, String name,String houseId) {
		List list = new ArrayList();
		String sql = SF.goodsSql("QRY_WARHOUSEINVENTORY_PAGE");
		if(status!=null)sql += " and wi.status="+status;
		if(!StringUtil.isEmpty(name))sql += " and upper(wi.name) like '%"+name.trim().toUpperCase()+"%'";
		if(!StringUtil.isEmpty(houseId)){
			sql += " and wi.house_id=?";
			list.add(houseId);
		}
		if(!ManagerUtils.isAdminUser()){
			AdminUser au = ManagerUtils.getAdminUser();
			String userid = au.getUserid();
			if(Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype())){
				userid = au.getParuserid();
			}else if(Consts.CURR_FOUNDER0==au.getFounder().intValue()){
				userid = "-1";
			}
			sql += " and w.user_id=?";
			list.add(userid);
		}
		String countSql = sql.replace("wi.*,w.house_name", "count(*)");
		sql += " order by wi.create_time desc";
		return this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, WarhouseInventory.class, countSql,list.toArray());
	}

	@Override
	public Page qryHouseGoods(String house_id,String goodsName,int pageNo,int pageSize) {
		String sql = SF.goodsSql("QRY_HOUSE_GOODS");
		if(!StringUtil.isEmpty(goodsName)){
			sql += " and upper(g.name) like '%"+goodsName.trim().toUpperCase()+"%'";
		}
		sql += " and g.source_from=?";
		String countSql = "select count(*) from ("+sql+")";
		return this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, Goods.class, countSql, house_id,ManagerUtils.getSourceFrom());
	}

	@Override
	public void delGoodsInventoryByInventoryId(String inventory_id) {
		String sql = SF.goodsSql("DEL_GOODSINVENTORY_BY_INVENTORYID");
		this.baseDaoSupport.execute(sql, inventory_id);
	}

	@Override
	public void updateInventoryName(String name, String inventory_id) {
		String sql = SF.goodsSql("UPDATE_INVENTORY_NAME");
		this.baseDaoSupport.execute(sql, name,inventory_id);
	}

	@Override
	public void updateWarhouseInventoryStatus(String inventory_id,
			Integer status, String remark) {
		if(remark==null)remark="";
		AdminUser au = ManagerUtils.getAdminUser();
		String sql = SF.goodsSql("UPDATE_WARHOUSEINVENTORY_STATUS");
		this.baseDaoSupport.execute(sql, status,remark,au.getUserid(),au.getUsername(),inventory_id);
	}

	@Override
	public boolean goodsIsInventory(String house_id,String goods_id) {
		String sql = SF.goodsSql("GOOD_SISINVENTORY");
		if(!StringUtil.isEmpty(house_id))
			sql += " and wi.house_id='"+house_id+"'";
		sql += " and wi.source_from=?";
		return this.baseDaoSupport.queryForInt(sql, goods_id,ManagerUtils.getSourceFrom())>0;
	}

}
