package com.ztesoft.net.mall.core.action.backend;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.net.mall.core.service.IWarehouseManager;
import com.ztesoft.net.mall.core.service.IWarhouseGoodsStoreManager;
import com.ztesoft.net.mall.core.service.impl.WarehouseSeatManager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class WarehouseGoodsStoreAction extends WWAction {

	private String goods_name;
	private String goods_id;
	private String sn;
	private String house_id;
	private String transit_store_compare;
	private String transit_store;
	private String store_compare;
	private String store;
	private String house_name;
	private WarehouseSeatManager warehouseSeatManager;
	
	private IWarhouseGoodsStoreManager warhouseGoodsStoreManager;
	private IWarehouseManager warehouseManager;

	public String search() {
		
		this.webpage = this.warhouseGoodsStoreManager.goodsStoreList(goods_id,
				goods_name, sn,house_name, this.getPage(), this.getPageSize());
		
		/**
		 * 以下代码是为了导出excel功能所编写 title:excel表头，传递需要打印的表头注释
		 * content:内容字段，输入实体对象所对应表头字段的属性 set的时候勿更改属性名，否则将出现错误
		 * fileTitle为导出excel文件的标题，根据自己导出的内容设定
		 * 
		 * 若需要导出功能，请在jsp页面的grid属性里面添加 excel="yes"
		 */
		String[] title = { "商品货号", "商品名称","规格","仓库名称", "库存" };
		String[] content = { "sn", "name", "specifications", "house_name", "store"};
		String fileTitle = "商品库存数据导出";
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);
		request.setAttribute("title", title);
		request.setAttribute("content", content);
		if (excel != null && !"".equals(excel)) {
			/**
			 * 修改退出方式 
			 */
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
			//return "export_excel_list";
		} else {
			return "goods_store_list";
		}
	}

	public String warhouseStoreList() {
		this.webpage = this.warhouseGoodsStoreManager.warhouseStoreList(
				house_name,goods_name, sn, transit_store_compare, transit_store,
				store_compare, store, this.getPage(), this.getPageSize());
		
		/**
		 * 以下代码是为了导出excel功能所编写 title:excel表头，传递需要打印的表头注释
		 * content:内容字段，输入实体对象所对应表头字段的属性 set的时候勿更改属性名，否则将出现错误
		 * fileTitle为导出excel文件的标题，根据自己导出的内容设定
		 * 
		 * 若需要导出功能，请在jsp页面的grid属性里面添加 excel="yes"
		 */
		String[] title = { "仓库名称", "商品名称","商品货号","规格", "在途库存","库存" };
		String[] content = { "house_name", "name", "sn", "specifications", "transit_store", "store"};
		String fileTitle = "商品库存数据导出";
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);
		request.setAttribute("title", title);
		request.setAttribute("content", content);
		if (excel != null && !"".equals(excel)) {
			/**
			 * 修改退出方式 
			 */
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
			//return "export_excel_list";
		} else {
			return "warhouse_store_list";
		}
	}

	public String showWarehouse(){
		this.webpage=this.warehouseManager.search(house_name, this.getPage(), this.getPageSize());
		return "showWarehouse";
	}
	
	public String warehouseSeatList(){
		try {
			house_name = URLDecoder.decode(house_name, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.webpage=this.warehouseSeatManager.warhouseSeatList(goods_id,house_name, this.getPage(), this.getPageSize());
		return "warehouse_seat_list";
	}
	
	public String sumeStoreList(){
		this.webpage=this.warhouseGoodsStoreManager.sumStoreList(sn, goods_name, this.getPage(), this.getPageSize());
		return "sumeStoreList";
	}
	
	public String showStoreDescList(){
		this.webpage=this.warhouseGoodsStoreManager.goodsStoreList(goods_id, goods_name, sn,house_name, this.getPage(), this.getPageSize());
		return "showStoreDescList";
	}
	
	public String outStorage(){
		return "outStorage";
	}
	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public IWarhouseGoodsStoreManager getWarhouseGoodsStoreManager() {
		return warhouseGoodsStoreManager;
	}

	public void setWarhouseGoodsStoreManager(
			IWarhouseGoodsStoreManager warhouseGoodsStoreManager) {
		this.warhouseGoodsStoreManager = warhouseGoodsStoreManager;
	}

	public String getHouse_id() {
		return house_id;
	}

	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}

	

	public String getTransit_store_compare() {
		return transit_store_compare;
	}

	public void setTransit_store_compare(String transit_store_compare) {
		this.transit_store_compare = transit_store_compare;
	}

	

	public String getTransit_store() {
		return transit_store;
	}

	public void setTransit_store(String transit_store) {
		this.transit_store = transit_store;
	}

	public String getStore_compare() {
		return store_compare;
	}

	public void setStore_compare(String store_compare) {
		this.store_compare = store_compare;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getHouse_name() {
		return house_name;
	}

	public void setHouse_name(String house_name) {
		this.house_name = house_name;
	}

	public IWarehouseManager getWarehouseManager() {
		return warehouseManager;
	}

	public void setWarehouseManager(IWarehouseManager warehouseManager) {
		this.warehouseManager = warehouseManager;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public WarehouseSeatManager getWarehouseSeatManager() {
		return warehouseSeatManager;
	}

	public void setWarehouseSeatManager(WarehouseSeatManager warehouseSeatManager) {
		this.warehouseSeatManager = warehouseSeatManager;
	}
	
	
}
