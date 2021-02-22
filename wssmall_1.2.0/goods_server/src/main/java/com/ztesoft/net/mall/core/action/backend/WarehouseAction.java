package com.ztesoft.net.mall.core.action.backend;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.GoodsInventoryM;
import com.ztesoft.net.mall.core.model.ProductCompany;
import com.ztesoft.net.mall.core.model.VirtualWarehouse;
import com.ztesoft.net.mall.core.model.Warehouse;
import com.ztesoft.net.mall.core.service.IWarehouseManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class WarehouseAction extends WWAction{
	
	private Warehouse warehouse;
	private GoodsInventoryM goodsInventoryM;
	private VirtualWarehouse virtualhouse;
	private ProductCompany productCompany;
	private String flag;// add-增加 edit-修改 view-查看
	private String operation;
	private String id;
	private String house_name;
	private String house_id;
	private String house_code;
	private String attribution;
	private String status;
	private String sku;
	private Integer changeStoreNum;
	private IWarehouseManager warehouseManager;
	private String product_name;
	private String company_name;
	private String company_code;
	private String comp_code_sel;
	private String comp_name_sel;
	
	//校验虚拟仓名和编码
	public String vcheckNameOrCode(){
		try {
			int i = this.warehouseManager.vcheckNameOrCode(house_name,house_code);
			if(i==1){
				json = "{'result':1,'message':'虚拟仓名称已经存在，不能重复添加！'}";
			}else if(i==2){
				json = "{'result':2,'message':'虚拟仓编码已经存在，不能重复添加！'}";
			}else{
				json = "{'result':0,'message':'虚拟仓名称和编码未被使用！'}";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON_MESSAGE;
	}
	
	//校验仓库名和编码:1、所有物理仓库名称不允许相同；2、同一货主下，物理仓库编码不允许相同
	public String checkNameOrCode(){
		try {
			int i = this.warehouseManager.checkNameOrCode(house_name,house_code,company_code,house_id);
			if(i==1){
				json = "{'result':1,'message':'已经存在此仓库名，不能重复！'}";
			}else if(i==2){
				json = "{'result':2,'message':'"+company_name+"货主已经存在此仓库编码，不能重复！'}";
			}else{
				json = "{'result':0,'message':'"+company_name+"货主未使用此仓库名和编码！'}";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON_MESSAGE;
	}
	
	//校验货主名和编码
	public String pcCheckNameOrCode(){//pc:product company
		try {
			
			int i = this.warehouseManager.pcCheckNameOrCode(house_name,house_code,house_id);
			if(i==1){
				json = "{'result':1,'message':'货主名已经存在，不能重复添加/修改！'}";
			}else if(i==2){
				json = "{'result':2,'message':'货主编码已经存在，不能重复添加/修改！'}";
			}else{
				json = "{'result':0,'message':'货主名和编码未被使用！'}";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON_MESSAGE;
	}
	
	//检测货品库存是否存在
	public String isExistsStore(){
		List list = this.warehouseManager.isExistsStore(id,house_id);
		if(list.size()>0){
			json = "{'result':1,'message':'此货品仓库已创建，不能进行此操作！'}";
		}else{
			json = "{'result':0,'message':'此货品仓库已创建！'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String prodStoreManage(){
		this.webpage=this.warehouseManager.searchGoodsInventory(house_id,product_name,this.getPage(), this.getPageSize());
		return "prodStoreManage";
	}
	
	public String searchHouseList(){
		this.webpage=this.warehouseManager.searchHouseList(this.getPage(), this.getPageSize());
		return "searchHouseList";
	}
	
	public String searchPCList(){
		try {
			if(company_name!=null){
				company_name = URLDecoder.decode(company_name,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.webpage=this.warehouseManager.searchPCList(company_name, company_code, this.getPage(), this.getPageSize());
		return "searchPCList";
	}
	
	public String checkStoreSize(){
		List list = warehouseManager.checkStoreSize(house_id);
		if(list.size()>0){
			json = "{'result':1,'message':'此物理仓已分配了库存且库存大于0，不能进行此操作！'}";
		}else{
			json = "{'result':0,'message':'此物理仓未使用！'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String checkVistualStoreSize(){
		List list = warehouseManager.checkVistualStoreSize(house_id);
		if(list.size()>0){
			json = "{'result':1,'message':'此虚拟仓已经存在发布的商品，不能进行此操作！'}";
		}else{
			json = "{'result':0,'message':'此虚拟仓未使用！'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String findProvinceList(){
		List list = warehouseManager.getProvinceList();
		json = JSONArray.fromObject(list).toString();
		return WWAction.JSON_MESSAGE;
	}
	
	public String findFirstList(){
		List list = warehouseManager.getFirstList(id);
		json = JSONArray.fromObject(list).toString();
		return WWAction.JSON_MESSAGE;
	}
	
	public String findCityList(){
		List list = warehouseManager.getCityList();
		json = JSONArray.fromObject(list).toString();
		return WWAction.JSON_MESSAGE;
	}
		
	public String findCountryList(){
		List list = warehouseManager.getCountryListById(house_id);
		json = JSONArray.fromObject(list).toString();
		return WWAction.JSON_MESSAGE;
	}
	
	public String isExits() {

		try {

			if (!StringUtil.isEmpty(house_name)) {
				try {
					house_name = URLDecoder.decode(house_name, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (this.warehouseManager.isWarehouseNameExits(house_name)) {
					json = "{'result':2,'message':'仓库名称已存在！'}";
				} else {
					json = "{'result':0,'message':'可以添加！'}";
				}
			} else if (!StringUtil.isEmpty(house_code)) {
				if (this.warehouseManager.isWarehouseCodeExits(house_code)) {
					json = "{'result':2,'message':'仓库编号已存在！'}";
				} else {
					json = "{'result':0,'message':'可以添加！'}";
				}
			}
		} catch (RuntimeException e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}
	
	//虚拟仓
	public String virtualList(){
		this.webpage=this.warehouseManager.searchVirtualList(house_name,attribution,status, this.getPage(), this.getPageSize());
		return "virtualList";
	}
	
	public String searchListDialog(){
		try {
			if(house_name!=null){
				house_name = URLDecoder.decode(house_name,"utf-8");
			}
			if(comp_name_sel!=null){
				comp_name_sel = URLDecoder.decode(comp_name_sel,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Map<String,String> query_params = new HashMap<String,String>();
		query_params.put("house_name", house_name);
		query_params.put("attribution", attribution);
		query_params.put("status", status);
		query_params.put("comp_name_sel", comp_name_sel);
		this.webpage=this.warehouseManager.searchList(query_params, this.getPage(), this.getPageSize());
		return "searchListDialog";
	}
	
	public String searchProductList(){
		try {
			if(product_name!=null){
				product_name = URLDecoder.decode(product_name,"utf-8");
			}
			if(attribution!=null){
				attribution = URLDecoder.decode(attribution,"utf-8");
			}
			if(null!=sku){
				sku = URLDecoder.decode(sku,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.webpage=this.warehouseManager.searchProductList(product_name,attribution,sku, this.getPage(), this.getPageSize());
		return "searchProductList";
	}
	
	public String searchList(){
		Map<String,String> query_params = new HashMap<String,String>();
		query_params.put("house_name", house_name);
		query_params.put("attribution", attribution);
		query_params.put("status", status);
		query_params.put("comp_code_sel", comp_code_sel);
		this.webpage=this.warehouseManager.searchList(query_params, this.getPage(), this.getPageSize());
		
		/**
		 * 以下代码是为了导出excel功能所编写 title:excel表头，传递需要打印的表头注释
		 * content:内容字段，输入实体对象所对应表头字段的属性 set的时候勿更改属性名，否则将出现错误
		 * fileTitle为导出excel文件的标题，根据自己导出的内容设定
		 * 
		 * 若需要导出功能，请在jsp页面的grid属性里面添加 excel="yes"
		 */
//		String[] title = { "仓库名称", "联系人姓名", "联系人手机", "联系人电话", "仓库编号", "发货属性", "仓库归属", "创建时间" };
//		String[] content = { "house_name", "contact_name", "telephone", "phone_num", "house_code",
//				"attr_inline_type", "attribution", "create_time" };
//		String fileTitle = "仓库数据导出";
//
//		HttpServletRequest request = ServletActionContext.getRequest();
//		request.setAttribute("webpage", this.webpage);
//		request.setAttribute("title", title);
//		request.setAttribute("content", content);
//
//		request.setAttribute("webpage", this.webpage);
//		if (excel != null && !"".equals(excel)) {
//			/**
//			 * 修改退出方式 mochunrun 20130917
//			 */
//			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
//			return null;
//			//return "export_excel_list";
//		} else {
			return "searchList";
//		}
	}
	
	public String search(){
		this.webpage=this.warehouseManager.search(house_name, this.getPage(), this.getPageSize());
		
		/**
		 * 以下代码是为了导出excel功能所编写 title:excel表头，传递需要打印的表头注释
		 * content:内容字段，输入实体对象所对应表头字段的属性 set的时候勿更改属性名，否则将出现错误
		 * fileTitle为导出excel文件的标题，根据自己导出的内容设定
		 * 
		 * 若需要导出功能，请在jsp页面的grid属性里面添加 excel="yes"
		 */
		String[] title = { "仓库名称", "联系人姓名", "联系人手机", "联系人电话", "仓库编号", "发货属性", "仓库归属", "创建时间" };
		String[] content = { "house_name", "contact_name", "telephone", "phone_num", "house_code",
				"attr_inline_type", "attribution", "create_time" };
		String fileTitle = "仓库数据导出";

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);
		request.setAttribute("title", title);
		request.setAttribute("content", content);

		request.setAttribute("webpage", this.webpage);
		if (excel != null && !"".equals(excel)) {
			/**
			 * 修改退出方式 mochunrun 20130917
			 */
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
			//return "export_excel_list";
		} else {
			return "list";
		}
	}
	
	public String detail(){
		warehouse=this.warehouseManager.findWarehouseByHoustId(house_id);
		
		return "detail";
	}
	
	public String showAdd(){
		return "showAdd";
	}
	
	public String selectAddr(){
		return "selectAddr";
	}
	
	public String selectShop(){
		return "selectShop";
	}
	
	public String pcEdit(){//pc:product company
		try {
			if("edit".equals(flag)){
				this.warehouseManager.editProductCompany(productCompany);
				json = "{'result':0,'message':'修改成功！'}";
			}else{
				this.warehouseManager.addProductCompany(productCompany);
				json = "{'result':0,'message':'添加成功！'}";
			}
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String virtualHouseEdit(){
		try {
			if("edit".equals(operation)){
				this.warehouseManager.editVirtualHouse(virtualhouse);
				json = "{'result':0,'message':'修改成功！'}";
			}else{
				this.warehouseManager.addVirtualHouse(virtualhouse);
				json = "{'result':0,'message':'添加成功！'}";
			}
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String edit(){
		try {
			if("edit".equals(flag)){
				this.warehouseManager.editWarehouse(warehouse);
				json = "{'result':0,'message':'修改成功！'}";
			}else if("add".equals(flag)){
				AdminUser user=ManagerUtils.getAdminUser();
				warehouse.setUser_id(user.getUserid());
				this.warehouseManager.addWarehouse(warehouse);
				json = "{'result':0,'message':'添加成功！'}";
			}
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String editProdStore(){
		try {
			if("edit".equals(flag)){
				this.warehouseManager.editGoodsInventory(goodsInventoryM,attribution,changeStoreNum);
				json = "{'result':0,'message':'修改成功！'}";
			}else{
				this.warehouseManager.addGoodsInventory(goodsInventoryM);
				json = "{'result':0,'message':'添加成功！'}";
			}
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String delete() {
		try {
			String a[] = id.split(",");
			String idstr = "";
			for (int i = 0; i < a.length; i++) {
				idstr += a[i].replaceAll(a[i], "" + a[i] + "") + ',';
			}
			idstr = idstr.substring(0, idstr.length() - 1);
			warehouseManager.delete(idstr);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	public String productCompanyList(){
		this.webpage=this.warehouseManager.productCompanyList(company_name, company_code, this.getPage(), this.getPageSize());

		return "productCompanyList";
	}
	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHouse_name() {
		return house_name;
	}

	public void setHouse_name(String house_name) {
		this.house_name = house_name;
	}

	public String getHouse_code() {
		return house_code;
	}

	public void setHouse_code(String house_code) {
		this.house_code = house_code;
	}

	public IWarehouseManager getWarehouseManager() {
		return warehouseManager;
	}

	public void setWarehouseManager(IWarehouseManager warehouseManager) {
		this.warehouseManager = warehouseManager;
	}

	public String getHouse_id() {
		return house_id;
	}

	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}

	public String getAttribution() {
		return attribution;
	}

	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public VirtualWarehouse getVirtualhouse() {
		return virtualhouse;
	}

	public void setVirtualhouse(VirtualWarehouse virtualhouse) {
		this.virtualhouse = virtualhouse;
	}

	public GoodsInventoryM getGoodsInventoryM() {
		return goodsInventoryM;
	}

	public void setGoodsInventoryM(GoodsInventoryM goodsInventoryM) {
		this.goodsInventoryM = goodsInventoryM;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getChangeStoreNum() {
		return changeStoreNum;
	}

	public void setChangeStoreNum(Integer changeStoreNum) {
		this.changeStoreNum = changeStoreNum;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany_code() {
		return company_code;
	}

	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	public ProductCompany getProductCompany() {
		return productCompany;
	}

	public void setProductCompany(ProductCompany productCompany) {
		this.productCompany = productCompany;
	}

	public String getComp_code_sel() {
		return comp_code_sel;
	}

	public void setComp_code_sel(String comp_code_sel) {
		this.comp_code_sel = comp_code_sel;
	}

	public String getComp_name_sel() {
		return comp_name_sel;
	}

	public void setComp_name_sel(String comp_name_sel) {
		this.comp_name_sel = comp_name_sel;
	}

	
}
