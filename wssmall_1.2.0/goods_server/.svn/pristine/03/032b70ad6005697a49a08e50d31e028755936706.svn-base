package com.ztesoft.net.mall.core.service.impl;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.WarehouseSeat;
import com.ztesoft.net.mall.core.model.WarehouseSeatGoods;
import com.ztesoft.net.mall.core.service.IWarehouseSeatManager;
import com.ztesoft.net.sqls.SF;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseSeatManager extends BaseSupport<WarehouseSeat> implements IWarehouseSeatManager {

	@Override
	public void addWarehouseSeat(WarehouseSeat warehouseSeat) {
		this.baseDaoSupport.insert("es_warehouse_seat", warehouseSeat);
	}
	
	@Override
	public void addWarehouseSeatGoods(WarehouseSeatGoods warehouseSeatGoods) {
		warehouseSeatGoods.setCreate_date(DBTUtil.current());
		this.baseDaoSupport.insert("es_warehouse_seat_goods", warehouseSeatGoods);
	}

	@Override
	public int delete(String seat_id) {
		if (seat_id == null || seat_id.equals("")) {
			return 0;
		} else {
			String sql = SF.goodsSql("WAREHOUSE_SEAT_DEL_BY_IDS") + " and seat_id in (" + seat_id + ")";
			this.baseDaoSupport.execute(sql);
			return 1;
		}
	}
	
	@Override
	public int arrangement(String seat_id) {
		if (seat_id == null || seat_id.equals("")) {
			return 0;
		} else {
			String[] id=seat_id.split(",");
			String sql = SF.goodsSql("WAREHOUSE_SEAT_GOODS_UPDATE");
			this.baseDaoSupport.execute(sql,id[0].trim(),id[1].trim());
			return 1;
		}
	}

	@Override
	public void editWarehouseSeat(WarehouseSeat WarehouseSeat) {
		Map where=new HashMap();
		
		if(StringUtils.isNotEmpty(WarehouseSeat.getSeat_id())){
			where.put("seat_id",  WarehouseSeat.getSeat_id());
		}else{
			where.put("seat_name",  WarehouseSeat.getSeat_name());
		}
		this.baseDaoSupport.update("es_warehouse_seat", WarehouseSeat, where);
	}

	@Override
	public WarehouseSeat findWarehouseSeatBySeat_id(String seat_id) {
		String sql = SF.goodsSql("FIND_WAREHOUSE_SEAT_BY_SEAT_ID");
		return this.baseDaoSupport.queryForObject(sql, WarehouseSeat.class, seat_id);
	}

	@Override
	public boolean isWarehouseNameExits(String seat_name,String house_id) {
		String sql = SF.goodsSql("IS_WAREHOUSENAME_EXITS");
		List list = this.baseDaoSupport.queryForList(sql, seat_name,house_id);
		return list.size() > 0 ? true : false;
	}
	
	@Override
	public boolean isHouseNameExits(String house_id,String goods_id,String seat_id) {
		String sql = SF.goodsSql("IS_HOUSE_NAME_EXITS");
		List list = this.baseDaoSupport.queryForList(sql, goods_id,seat_id,house_id);
		return list.size() > 0 ? true : false;
	}

	@Override
	public Page search(String goods_id,String sn,String name,String house_name,String seat_name,String disabled, int pageNo, int pageSize) {
		String sql = SF.goodsSql("WAREHOUSE_SEAT_SELECT");
		ArrayList partm = new ArrayList();
		
		StringBuffer sqlAccount = new StringBuffer();
		if(StringUtils.isNotEmpty(house_name)){
			sqlAccount.append(" and b.house_name like ?");
			partm.add("%"+house_name+"%");
		}
		
		if(StringUtils.isNotEmpty(seat_name)){
			sqlAccount.append(" and a.seat_name like ?");
			partm.add("%"+seat_name+"%");
		}
		
		if(StringUtils.isNotEmpty(goods_id)){
			sqlAccount.append(" and g.goods_id = ?");
			partm.add(goods_id);
		}
		
		if(StringUtils.isNotEmpty(sn)){
			sqlAccount.append(" and g.sn like ?");
			partm.add("%"+sn+"%");
		}
		
		if(StringUtils.isNotEmpty(name)){
			sqlAccount.append(" and g.name like ?");
			partm.add("%"+name+"%");
		}
		
		if(StringUtils.isNotEmpty(disabled)){
			sqlAccount.append(" and h.disabled = ?");
			partm.add(disabled);
		}
		
		sqlAccount.append(" order by create_date desc");
		sql=sql+sqlAccount.toString();
		
		return this.daoSupport.queryForPage(sql, pageNo, pageSize, partm.toArray());
	}
	
	@Override
	public Page warhouseSeatList(String goods_id,String house_name, int pageNo, int pageSize) {
		String sql = SF.goodsSql("WARHOUSE_SEAT_LIST");
		ArrayList partm = new ArrayList();
		
		StringBuffer sqlAccount = new StringBuffer();
		
		if(StringUtils.isNotEmpty(goods_id)){
			sqlAccount.append(" and a.goods_id = ?");
			partm.add(goods_id);
		}
		
		if(StringUtils.isNotEmpty(house_name)){
			sqlAccount.append(" and b.house_name like ?");
			partm.add("%"+house_name+"%");
		}
		
		sql=sql+sqlAccount.toString();
		
		String sqlCount = SF.goodsSql("WARHOUSE_SEAT_COUNT");
		sqlCount = sqlCount+sqlAccount.toString();
		return this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, WarehouseSeat.class, sqlCount, partm.toArray());
	}
	
	@Override
	public Page list(String house_name,String seat_name, int pageNo, int pageSize) {
		String sql = SF.goodsSql("WAREHOUSE_SEAT_BY_WAREHOUSE");
		ArrayList partm = new ArrayList();
		
		StringBuffer sqlAccount = new StringBuffer();
		if(StringUtils.isNotEmpty(house_name)){
			sqlAccount.append(" and b.house_name like ?");
			partm.add("%"+house_name+"%");
		}
		
		if(StringUtils.isNotEmpty(seat_name)){
			sqlAccount.append(" and a.seat_name like ?");
			partm.add("%"+seat_name+"%");
		}
		
		sql=sql+sqlAccount.toString();
		
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, partm.toArray());
	}

	@Override
	public List warehouseList() {
		String sql = SF.goodsSql("WAREHOUSE_LIST_PART");
		return this.baseDaoSupport.queryForList(sql);
	}
	
	@Override
	public List<WarehouseSeat> warehouseSeatList(String house_id) {
		String sql = SF.goodsSql("WAREHOUSE_SEAT_LIST");
		return this.baseDaoSupport.queryForList(sql, WarehouseSeat.class, house_id);
	}

	@Override
	public Page findGoodsSeat(String seat_name, int pageNo, int pageSize) {
		String sql = SF.goodsSql("FIND_GOODS_SEAT");
		ArrayList partm = new ArrayList();
		
		StringBuffer sqlAccount = new StringBuffer();
		if(StringUtils.isNotEmpty(seat_name)){
			sqlAccount.append(" and a.seat_name like ?");
			partm.add("%"+seat_name+"%");
		}
		
		sql=sql+sqlAccount.toString();
		
		return this.daoSupport.queryForPage(sql, pageNo, pageSize, partm.toArray());
	}

}
