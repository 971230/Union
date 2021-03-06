package com.ztesoft.net.mall.core.action.backend;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.GoodsInventoryApply;
import com.ztesoft.net.mall.core.model.GoodsInventoryM;
import com.ztesoft.net.mall.core.model.VirtualWarehouse;
import com.ztesoft.net.mall.core.model.Warehouse;
import com.ztesoft.net.mall.core.service.IWarehouseManager;

/**
 * 库存分配管理
 * @author deng.yingxiang
 *
 */
public class WarehouseAssignAction extends WWAction{
	
	private Warehouse warehouse;               //仓库
	private String name;                       //商品/货品名称
	private String virtual_house_name;          //虚拟仓名称
	//private String type;                       //类型、货品product,商品:goods
	private GoodsInventoryM goodsInventory;    //库存
	private IWarehouseManager warehouseManager;
	private VirtualWarehouse  virtualWarehouse;  //虚拟仓
	private GoodsInventoryApply apply; //库存分配
	private GoodsInventoryApply recover; //库存回收
	
	/**
	 * 库存货品列表
	 * @return
	 */
	public String wareProductList(){
        String house_id = "";
        String product_id = "";
        String product_name = "";
        if(goodsInventory != null){
        	house_id = goodsInventory.getHouse_id();
        	product_id = goodsInventory.getProduct_id();
        	product_name = goodsInventory.getProduct_name();
        }
		this.webpage=this.warehouseManager.queryProductNotAssign(house_id,product_name,this.getPage(),this.getPageSize());
		return "ware_product_list";
	}
	
	/**
	 * 库存商品列表
	 * @return
	 */
	public String wareGoodsList(){
        String house_id = "";
        String goods_id = "";
        if(goodsInventory != null){
        	house_id = goodsInventory.getHouse_id();
        	goods_id = goodsInventory.getGoods_id();
        }
		this.webpage=this.warehouseManager.queryGoodsNotAssign(house_id,goods_id,this.getPage(),this.getPageSize());
		return "ware_goods_list";
	}
	
    /**
     * 选择货品列表
     * @return
     */
    public String selectProductList(){

		String type = "product";
    	webpage = this.warehouseManager.getProductList(name,type,this.getPage(), this.getPageSize());
    	return  "sel_pro_list";
    }
    /**
     * 选择商品列表
     * @return
     */
    public String selectGoodsList(){
		String type = "goods";
    	webpage = this.warehouseManager.getGoodsList(name,type,this.getPage(), this.getPageSize());
    	return  "sel_goods_list";
    }
    
    /**
     * 库存分配--选择虚拟仓
     * @return
     */
    public String selectVWareList(){
    	String house_name = "";
    	Integer attr_inline_type  = -1;
		if(virtualWarehouse != null){
		    house_name = virtualWarehouse.getHouse_name();
		    attr_inline_type = virtualWarehouse.getAttr_inline_type();
		}
    	webpage = this.warehouseManager.getVWareList(house_name,attr_inline_type,this.getPage(), this.getPageSize());
    	return  "sel_virtual_ware_list";
    }
    
    /**
     * 已分配货品页面--选择虚拟仓
     * @return
     */
    public String selectVirList(){
    	String house_name = "";
    	Integer attr_inline_type  = -1;
		if(virtualWarehouse != null){
		    house_name = virtualWarehouse.getHouse_name();
		    attr_inline_type = virtualWarehouse.getAttr_inline_type();
		}
    	webpage = this.warehouseManager.getVWareList(house_name,attr_inline_type,this.getPage(), this.getPageSize());
    	return  "query_sel_vir_list";
    }
    
	/**
	 * 已分配货品
	 * @return
	 */
	public String assignedProductList(){
        String house_id = "";
        String goods_id = "";
        String org_id = "";
        String virtual_house_id = "";
        if(apply != null){
        	house_id = apply.getHouse_id();
        	goods_id = apply.getGoods_id();
        	org_id = apply.getOrg_id();
        	virtual_house_id = apply.getVirtual_house_id();
        }
        String type = "product";
		this.webpage=this.warehouseManager.queryAssigned(org_id, name, house_id, 
				virtual_house_id, type,this.getPage(),this.getPageSize());
		return "assigned_product_list";
	}
	
	/**
	 * 已分配商品
	 * @return
	 */
	public String assignedGoodsList(){
        String house_id = "";
        String goods_id = "";
        String org_id = "";
        String virtual_house_id = "";
        if(apply != null){
        	house_id = apply.getHouse_id();
        	goods_id = apply.getGoods_id();
        	org_id = apply.getOrg_id();
        	virtual_house_id = apply.getVirtual_house_id();
        }
        String type = "goods";
		this.webpage=this.warehouseManager.queryAssigned(org_id, name, house_id, 
				virtual_house_id, type,this.getPage(),this.getPageSize());
		return "assigned_goods_list";
	}
	
	/**
	 * 保存商品货品库存分配
	 * @param ita
	 */
	public String saveInventoryApply(){
		String action = Consts.INVENTORY_ACTION_1;
		warehouseManager.updateInventoryApply(apply, action);
		json = "{'result':'true'}";
		
		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 商品货品库存回收
	 * @param ita
	 */
	public String recycleInventoryApply(){
		String action = Consts.INVENTORY_ACTION_2;
		warehouseManager.updateInventoryApply(recover,action);
		json = "{'result':'true'}";
		
		return WWAction.JSON_MESSAGE;
	}
    
	/**
	 * 选择销售组织
	 * @return
	 */
	public String selectSaleOrg(){
		return "select_sale_org";
	}
	
	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public IWarehouseManager getWarehouseManager() {
		return warehouseManager;
	}

	public void setWarehouseManager(IWarehouseManager warehouseManager) {
		this.warehouseManager = warehouseManager;
	}


	public GoodsInventoryM getGoodsInventory() {
		return goodsInventory;
	}

	public void setGoodsInventory(GoodsInventoryM goodsInventory) {
		this.goodsInventory = goodsInventory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public VirtualWarehouse getVirtualWarehouse() {
		return virtualWarehouse;
	}

	public void setVirtualWarehouse(VirtualWarehouse virtualWarehouse) {
		this.virtualWarehouse = virtualWarehouse;
	}

	public GoodsInventoryApply getApply() {
		return apply;
	}

	public void setApply(GoodsInventoryApply apply) {
		this.apply = apply;
	}

	public GoodsInventoryApply getRecover() {
		return recover;
	}

	public void setRecover(GoodsInventoryApply recover) {
		this.recover = recover;
	}

	public String getVirtual_house_name() {
		return virtual_house_name;
	}

	public void setVirtual_house_name(String virtual_house_name) {
		this.virtual_house_name = virtual_house_name;
	}

}
