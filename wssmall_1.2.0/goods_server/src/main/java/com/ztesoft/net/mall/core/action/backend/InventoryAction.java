package com.ztesoft.net.mall.core.action.backend;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.GoodsInventory;
import com.ztesoft.net.mall.core.model.Warehouse;
import com.ztesoft.net.mall.core.model.WarhouseInventory;
import com.ztesoft.net.mall.core.service.IGoodsInventoryManager;
import com.ztesoft.net.mall.core.service.IWarehouseManager;
import com.ztesoft.net.mall.core.service.IWarhouseGoodsStoreManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 盘点
 * @作者 MoChunrun
 * @创建日期 2013-11-25 
 * @版本 V 1.0
 */
public class InventoryAction extends WWAction {

	private IGoodsInventoryManager goodsInventoryManager;
	private IWarehouseManager warehouseManager;
	private IWarhouseGoodsStoreManager warhouseGoodsStoreManager;
	private Page houseInventoryPage;
	private String name;
	private Integer status;
	private String house_id;
	private String action = "0"; //0添加 1修改
	private String inventory_id;
	private List<Warehouse> wareHouseList;
	private List<GoodsInventory> goodsInventory;
	private WarhouseInventory warhouseInventory;
	private Page goodsPage;
	private String goodsName;
	private String [] goods_idArray;
	private String [] goods_numArray;
	private String remark;
	
	public String confirmInventory(){
		if(status==1){
			warhouseInventory = goodsInventoryManager.getWarhouseInventory(inventory_id);
			goodsInventory = goodsInventoryManager.qryGoodsInventoryByInventoryId(inventory_id);
			for(GoodsInventory gi:goodsInventory){
				int oldStore = warhouseGoodsStoreManager.sumStoreByGoodsId(gi.getGoods_id(), warhouseInventory.getHouse_id());
				int errorStore = gi.getStore().intValue()-oldStore;
				if(errorStore!=0){
					if(errorStore>0){
						warhouseGoodsStoreManager.addStore(gi.getGoods_id(), warhouseInventory.getHouse_id(), errorStore);
					}else{
						warhouseGoodsStoreManager.descStore(gi.getGoods_id(), warhouseInventory.getHouse_id(), -errorStore);
					}
					warhouseGoodsStoreManager.updateGoodsStore(gi.getGoods_id(), errorStore);
				}
			}
		}
		goodsInventoryManager.updateWarhouseInventoryStatus(inventory_id, status, remark);
		json = "{status:0,msg:'成功'}";
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(this.json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String showConfirmDialog(){
		return "inventoryconfirm";
	}
	
	public String showDetial(){
		warhouseInventory = goodsInventoryManager.getWarhouseInventory(inventory_id);
		goodsInventory = goodsInventoryManager.qryGoodsInventoryByInventoryId(inventory_id);
		return "house_inventory_info";
	}
	
	/**
	 * 保存盘点
	 * @作者 MoChunrun
	 * @创建日期 2013-11-26 
	 * @return
	 */
	public String saveAddOrEdit(){
		boolean flag = false;
		if("0".equals(action)){
			if(goodsInventoryManager.qryByHouseId(house_id, 0).size()>0){
				json = "{status:1,msg:'该仓库正在盘点中,请进行修改'}";
			}else if(goods_idArray!=null && goods_idArray.length!=0){
				AdminUser user = ManagerUtils.getAdminUser();
				warhouseInventory.setHouse_id(house_id);
				warhouseInventory.setCreate_time(DBTUtil.current());
				warhouseInventory.setStatus(0);
				warhouseInventory.setOp_id(user.getUserid());
				warhouseInventory.setOp_name(user.getUsername());
				goodsInventoryManager.saveWarhouseInventory(warhouseInventory);
				flag = true;
			}else{
				json = "{status:1,msg:'没有选择商品'}";
			}
		}else{
			if(goods_idArray!=null && goods_idArray.length!=0){
				goodsInventoryManager.updateInventoryName(warhouseInventory.getName(), warhouseInventory.getInventory_id());
				goodsInventoryManager.delGoodsInventoryByInventoryId(warhouseInventory.getInventory_id());
				flag = true;
			}else{
				json = "{status:1,msg:'没有选择商品'}";
			}
		}
		if(flag){
			for(int i=0;i<goods_idArray.length;i++){
				GoodsInventory gi = new GoodsInventory();
				gi.setCreate_time(DBTUtil.current());
				gi.setGoods_id(goods_idArray[i]);
				gi.setInventory_id(warhouseInventory.getInventory_id());
				gi.setStore(Long.valueOf(goods_numArray[i]));
				gi.setOriginal_store((long)warhouseGoodsStoreManager.sumStoreByGoodsId(goods_idArray[i], house_id));
				goodsInventoryManager.saveGoodsInventory(gi);
			}
			json = "{status:0,msg:'成功'}";
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(this.json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询仓库的商品
	 * @作者 MoChunrun
	 * @创建日期 2013-11-26 
	 * @return
	 */
	public String qryHouseGoods(){
		goodsPage = goodsInventoryManager.qryHouseGoods(house_id,goodsName, getPage(), 8);
		return "house_goods";
	}
	
	/**
	 * 检查是否有商品在该仓库盘点
	 * @作者 MoChunrun
	 * @创建日期 2013-11-29 
	 * @return
	 */
	public String checkHasGoodsInventory(){
		List<WarhouseInventory> list = goodsInventoryManager.qryByHouseId(house_id, 0);
		if(list.size()>0){
			WarhouseInventory gi = list.get(0);
			json = "{status:1,msg:'该仓库正在盘点中，是否进入修改。',inventoryid:'"+gi.getInventory_id()+"'}";
		}else{
			json = "{status:0,msg:'可以添加'}";
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(this.json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 添加或修改
	 * @作者 MoChunrun
	 * @创建日期 2013-11-26 
	 * @return
	 */
	public String showInventoryEdit(){
		if(!"0".equals(action) && !StringUtil.isEmpty(inventory_id)){
			warhouseInventory = goodsInventoryManager.getWarhouseInventory(inventory_id);
			goodsInventory = goodsInventoryManager.qryGoodsInventoryByInventoryId(inventory_id);
		}else{
			Warehouse wh = warehouseManager.findWarehouseByHoustId(house_id);
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			String name = df.format(new Date())+wh.getHouse_name();
			warhouseInventory = new WarhouseInventory();
			warhouseInventory.setName(name);
		}
		return "inventory_goods_edit";
	}
	
	public String list(){
		wareHouseList = warehouseManager.qrySupplierWareHouse();
		Warehouse wh = new Warehouse();
		wh.setHouse_name("请选择");
		wareHouseList.add(0,wh);
		this.houseInventoryPage = goodsInventoryManager.qryWarhouseInventoryPage(getPage(), getPageSize(), status, name,house_id);
		return "list";
	}
	
	public String wareHouseList(){
		wareHouseList = warehouseManager.qrySupplierWareHouse();
		return "warehouse";
	}

	public IGoodsInventoryManager getGoodsInventoryManager() {
		return goodsInventoryManager;
	}

	public void setGoodsInventoryManager(
			IGoodsInventoryManager goodsInventoryManager) {
		this.goodsInventoryManager = goodsInventoryManager;
	}

	public Page getHouseInventoryPage() {
		return houseInventoryPage;
	}

	public void setHouseInventoryPage(Page houseInventoryPage) {
		this.houseInventoryPage = houseInventoryPage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public IWarehouseManager getWarehouseManager() {
		return warehouseManager;
	}

	public void setWarehouseManager(IWarehouseManager warehouseManager) {
		this.warehouseManager = warehouseManager;
	}

	public List<Warehouse> getWareHouseList() {
		return wareHouseList;
	}

	public void setWareHouseList(List<Warehouse> wareHouseList) {
		this.wareHouseList = wareHouseList;
	}

	public String getHouse_id() {
		return house_id;
	}

	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getInventory_id() {
		return inventory_id;
	}

	public void setInventory_id(String inventory_id) {
		this.inventory_id = inventory_id;
	}

	public List<GoodsInventory> getGoodsInventory() {
		return goodsInventory;
	}

	public void setGoodsInventory(List<GoodsInventory> goodsInventory) {
		this.goodsInventory = goodsInventory;
	}

	public WarhouseInventory getWarhouseInventory() {
		return warhouseInventory;
	}

	public void setWarhouseInventory(WarhouseInventory warhouseInventory) {
		this.warhouseInventory = warhouseInventory;
	}

	public Page getGoodsPage() {
		return goodsPage;
	}

	public void setGoodsPage(Page goodsPage) {
		this.goodsPage = goodsPage;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String[] getGoods_idArray() {
		return goods_idArray;
	}

	public void setGoods_idArray(String[] goods_idArray) {
		this.goods_idArray = goods_idArray;
	}

	public String[] getGoods_numArray() {
		return goods_numArray;
	}

	public void setGoods_numArray(String[] goods_numArray) {
		this.goods_numArray = goods_numArray;
	}

	public IWarhouseGoodsStoreManager getWarhouseGoodsStoreManager() {
		return warhouseGoodsStoreManager;
	}

	public void setWarhouseGoodsStoreManager(
			IWarhouseGoodsStoreManager warhouseGoodsStoreManager) {
		this.warhouseGoodsStoreManager = warhouseGoodsStoreManager;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
