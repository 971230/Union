package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.WarehouseSeat;
import com.ztesoft.net.mall.core.model.WarehouseSeatGoods;

import java.util.List;

public interface IWarehouseSeatManager {
	
	public Page search(String goods_id,String sn,String name,String house_name,String seat_name,String disabled, int pageNo, int pageSize);
	
	public Page findGoodsSeat(String seat_name,int pageNo,int pageSize);
	
	public void addWarehouseSeat(WarehouseSeat warehouseSeat);
	
	public WarehouseSeat findWarehouseSeatBySeat_id(String seat_id);
	
	public void editWarehouseSeat(WarehouseSeat WarehouseSeat);
	
	public int delete(String seat_id);
	
	public boolean isWarehouseNameExits(String seat_name,String house_id);
	
	public List warehouseList();
	
	public int arrangement(String seat_id);
	
	public List warehouseSeatList(String house_id);
	
	public void addWarehouseSeatGoods(WarehouseSeatGoods warehouseSeatGoods);
	
	public boolean isHouseNameExits(String house_id,String goods_id,String seat_id);
	
	public Page list(String house_name,String seat_name, int pageNo, int pageSize);
	
	public Page warhouseSeatList(String goods_id,String name, int pageNo, int pageSize);
}
