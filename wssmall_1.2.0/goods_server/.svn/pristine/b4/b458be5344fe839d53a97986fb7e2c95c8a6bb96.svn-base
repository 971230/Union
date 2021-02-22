package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import zte.params.goods.req.IncreaseInventoryNumReq;
import zte.params.goods.req.SubtractInventoryNumReq;
import zte.params.goods.resp.IncreaseInventoryNumResp;
import zte.params.goods.resp.SubtractInventoryNumResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.GoodsInventoryApply;
import com.ztesoft.net.mall.core.model.GoodsInventoryApplyLog;
import com.ztesoft.net.mall.core.model.GoodsInventoryM;
import com.ztesoft.net.mall.core.model.ProductCompany;
import com.ztesoft.net.mall.core.model.VirtualWarehouse;
import com.ztesoft.net.mall.core.model.Warehouse;

public interface IWarehouseManager {
	
	public List checkVistualStoreSize(String house_id);
	
	public List checkStoreSize(String house_id);
	
	public List getProvinceList();
	
	public List getFirstList(String id);
	
	public List getCityList();
	
	public List getCountryListById(String region_id);
	
	public List isExistsStore(String product_id,String house_id);
	
	public int checkNameOrCode(String house_name,String house_code,String comp_code,String house_id);
	public int vcheckNameOrCode(String house_name,String house_code);
	public int pcCheckNameOrCode(String company_name,String company_code,String company_id);
	
	public Page searchGoodsInventory(String house_id,String sku,int pageNo,int pageSize);
	
	public Page searchHouseList(int pageNo,int pageSize);
	
	public Page searchVirtualList(String house_name,String attr_inline_type,String status, int pageNo,int pageSize);
	
	public Page searchList(Map<String,String> query_params, int pageNo,int pageSize);
	
	public Page searchProductList(String product_name,String sn,String sku,int pageNo,int pageSize);
	
	public Page search(String house_name,int pageNo,int pageSize);
	
	public void addWarehouse(Warehouse warehouse);
	
	public void editGoodsInventory(GoodsInventoryM goodsInventoryM,String encoming,Integer changeStoreNum);
	
	public void addGoodsInventory(GoodsInventoryM goodsInventoryM);
	
	public void addVirtualHouse(VirtualWarehouse virtualWarehouse);
	
	public Warehouse findWarehouseByHoustId(String house_id);
	
	public void editWarehouse(Warehouse warehouse);
	
	public void editVirtualHouse(VirtualWarehouse virtualWarehouse);
	
	public int delete(String house_id);
	
	public boolean isWarehouseNameExits(String house_name);
	
	public boolean isWarehouseCodeExits(String house_code);
	
	public List warehouseList();
	
	/**
	 * 查询当前登录商家的仓库
	 * @作者 MoChunrun
	 * @创建日期 2013-11-26 
	 * @return
	 */
	public List<Warehouse> qrySupplierWareHouse();
	
	/**
	 * 查询待分配的货品
	 * @return
	 */
	public Page queryProductNotAssign(String house_id,String product_id, int pageNo, int pageSize);
	/**
	 * 查询待分配的商品
	 * @return
	 */
	public Page queryGoodsNotAssign(String house_id,String goods_id, int pageNo, int pageSize);
	
	/**
	 * 查询已分配的货品/商品
	 * @return
	 */
	public Page queryAssigned(String org_id,String goods_id,String house_id,String virtual_house_id,
			String type, int pageNo, int pageSize);
	
	/**
	 * 选择货品列表
	 * @return
	 */
	public Page getProductList(String name,String type, int pageNo, int pageSize);
	
	/**
	 * 选择货品列表
	 * @return
	 */
	public Page getGoodsList(String name,String type, int pageNo, int pageSize);
	
	/**
	 * 查询虚拟仓列表
	 * @return
	 */
	public Page getVWareList(String house_name,Integer attr_inline_type,int pageNo, int pageSize);
	
	/**
	 * 保存商品库存分配数据
	 */
	public void updateInventoryApply(GoodsInventoryApply ita,String action);
	
	public void recycleInventoryApply(GoodsInventoryApply ita);
	
	/**
	 * 库存分配日志信息
	 * @param ita
	 */
	public GoodsInventoryApplyLog queryInventoryApplyLog(String  log_id);
	
	/**
	 * 通过id查询仓库信息
	 * @param house_id
	 * @return
	 */
	public Warehouse getWareHouseById(String house_id);
	

	public void rollbackInventory(String product_id,String house_id,int rollback_num);
	
	public void updateInventory(String order_id);
	
	/**
	 * 获取某销售组织的商品配置的所有虚拟库存
	 * @param goods_id
	 * @param type
	 * @param house_id
	 * @param org_id
	 * @return
	 */
	public List getVirtualHouseForOrder(String goods_id, String type, String house_id, String org_id);
	
	/**
	 * 库存减少操作,如订单下单
	 * @param order_id
	 * @param goods_id
	 * @param house_id
	 * @param virtual_house_id
	 * @param num
	 */
	public String inventoryReduce(String order_id, String goods_id, 
			String house_id, String virtual_house_id, String is_share, String org_id_str, int num);
	
	/*
	 * 库存回滚操作
	 */
	public String inventoryRollback(String order_id, String goods_id,
			String house_id, String virtual_house_id, int num);
	

	@ZteSoftCommentAnnotation(type = "method", desc = "根据订单来源商城、固定的物理仓库编码、货品ID扣减库存", summary = "根据订单来源商城、固定的物理仓库编码、货品ID扣减库存")
	public SubtractInventoryNumResp subtractInventoryNum(SubtractInventoryNumReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "根据订单来源商城、固定的物理仓库编码、货品ID增加库存", summary = "根据订单来源商城、固定的物理仓库编码、货品ID增加库存")
	public IncreaseInventoryNumResp increaseInventoryNum(IncreaseInventoryNumReq req);

	/**
	 * 货主信息列表
	 * @param company_name	货主名称
	 * @param company_code	货主编码
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page productCompanyList(String company_name, String company_code, int pageNo, int pageSize);
	
	public void addProductCompany(ProductCompany productCompany);
	
	public void editProductCompany(ProductCompany productCompany);
	
	public Page searchPCList(String company_name,String company_code,int pageNo,int pageSize);
}
