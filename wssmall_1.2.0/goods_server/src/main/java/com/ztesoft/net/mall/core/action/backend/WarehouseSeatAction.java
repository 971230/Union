package com.ztesoft.net.mall.core.action.backend;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.WarehouseSeat;
import com.ztesoft.net.mall.core.model.WarehouseSeatGoods;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.service.IWarehouseManager;
import com.ztesoft.net.mall.core.service.IWarehouseSeatManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class WarehouseSeatAction extends WWAction{

	private String seat_name;
	private String goods_code;
	private String house_name;
	private String seat_id;
	private String sn;
	private String name;
	private String disabled;
	private String flag;// add-增加 edit-修改 view-查看
	private String id;
	private String house_id;
	private List warehouseList;
	private List<WarehouseSeat> warehouseSeatList;
	private WarehouseSeat warehouseSeat;
	private IWarehouseManager warehouseManager;
	private IGoodsManager goodsManager;
	private WarehouseSeatGoods warehouseSeatGoods;
	
	private IWarehouseSeatManager warehouseSeatManager;
	
	public String isGoodsIdExits(){
		try {

			if (!StringUtil.isEmpty(sn)) {
				if (this.goodsManager.findGoodsByGoodsSn(sn)==null) {
					json = "{'result':2,'message':'商品货号不存在！'}";
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
	public String isExits() {

		try {

			if (!StringUtil.isEmpty(seat_name)) {
				try {
					seat_name = URLDecoder.decode(seat_name, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (this.warehouseSeatManager.isWarehouseNameExits(seat_name,house_id)) {
					json = "{'result':2,'message':'货位名称已存在！'}";
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
	
	public String search(){
		this.webpage=this.warehouseSeatManager.list(house_name,seat_name, this.getPage(), this.getPageSize());
		

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);

		return "list";
	}
	
	public String findGoodsSeat(){
		this.webpage=this.warehouseSeatManager.findGoodsSeat(seat_name, this.getPage(), this.getPageSize());
		

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);

		return "findGoodsSeat";
	}
	
	public String showWarehouseList(){
		this.webpage=this.warehouseManager.search(house_name, this.getPage(), this.getPageSize());
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);
		return "showWarehouseList";
	}
	
	public String detail(){
		
		warehouseList=this.warehouseSeatManager.warehouseList();
		warehouseSeat=this.warehouseSeatManager.findWarehouseSeatBySeat_id(seat_id);
		
		return "detail";
	}
	
	public String showAdd(){
		warehouseList=this.warehouseSeatManager.warehouseList();

		return "showAdd";
	}
	
	
	
	public String edit(){
		try {
			if("edit".equals(flag)){
				this.warehouseSeatManager.editWarehouseSeat(warehouseSeat);
				json = "{'result':0,'message':'修改成功！'}";
			}else{
				this.warehouseSeatManager.addWarehouseSeat(warehouseSeat);
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
			warehouseSeatManager.delete(idstr);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}
	
	public String arrangementSearch(){
		this.webpage=this.warehouseSeatManager.search(goods_code,sn,name,house_name,seat_name,disabled, this.getPage(), this.getPageSize());
		

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);

		return "arrangement_list";
	}
	
	public String arrangement() {
		try {
			String a[] = id.split(",");
			String idstr = "";
			for (int i = 0; i < a.length; i++) {
				idstr += a[i].replaceAll(a[i], "" + a[i] + "") + ',';
			}
			idstr = idstr.substring(0, idstr.length() - 1);
			warehouseSeatManager.arrangement(idstr);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}
	
	public String goodsShelves(){
		warehouseList=this.warehouseSeatManager.warehouseList();

		return "goodsShelves";
	}
	
	public String goodsShelvesNext(){
		warehouseList=this.warehouseSeatManager.warehouseList();
		return "goodsShelvesNext";
	}
	
	public String editGoodsShelves(){
		try {
			
			Goods goods=this.goodsManager.findGoodsByGoodsSn(sn);
			if(goods!=null){
				if(this.warehouseSeatManager.isHouseNameExits(warehouseSeatGoods.getHouse_id(),goods.getGoods_id(), warehouseSeatGoods.getSeat_id())){
					json = "{'result':0,'message':'该商品在货位上已经存在！'}";
				}else{
					warehouseSeatGoods.setGoods_id(goods.getGoods_id());
					this.warehouseSeatManager.addWarehouseSeatGoods(warehouseSeatGoods);
					json = "{'result':0,'message':'操作成功！'}";
				}
			}else{
				json = "{'result':0,'message':'商品不存在！'}";
			}
			
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}

	public String findWarehouseSeatByHouseId(){
		try {
			HttpServletResponse response=ServletActionContext.getResponse();
			warehouseSeatList=this.warehouseSeatManager.warehouseSeatList(house_id);
			JSONObject json=new JSONObject();
			JSONArray  jsonarr=new JSONArray();
			
			for(WarehouseSeat  m:warehouseSeatList)
			{ 
				json=JSONObject.fromObject(m);
				jsonarr.add(json);
			}
			PrintWriter out=response.getWriter();
			out.write(jsonarr.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getSeat_name() {
		return seat_name;
	}

	public void setSeat_name(String seat_name) {
		this.seat_name = seat_name;
	}

	public String getHouse_name() {
		return house_name;
	}

	public void setHouse_name(String house_name) {
		this.house_name = house_name;
	}

	public String getSeat_id() {
		return seat_id;
	}

	public void setSeat_id(String seat_id) {
		this.seat_id = seat_id;
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

	public WarehouseSeat getWarehouseSeat() {
		return warehouseSeat;
	}

	public void setWarehouseSeat(WarehouseSeat warehouseSeat) {
		this.warehouseSeat = warehouseSeat;
	}

	public IWarehouseSeatManager getWarehouseSeatManager() {
		return warehouseSeatManager;
	}

	public void setWarehouseSeatManager(IWarehouseSeatManager warehouseSeatManager) {
		this.warehouseSeatManager = warehouseSeatManager;
	}

	public List getWarehouseList() {
		return warehouseList;
	}

	public void setWarehouseList(List warehouseList) {
		this.warehouseList = warehouseList;
	}

	public IWarehouseManager getWarehouseManager() {
		return warehouseManager;
	}

	public void setWarehouseManager(IWarehouseManager warehouseManager) {
		this.warehouseManager = warehouseManager;
	}
	public String getGoods_code() {
		return goods_code;
	}
	public void setGoods_code(String goods_code) {
		this.goods_code = goods_code;
	}
	public String getHouse_id() {
		return house_id;
	}
	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}
	public List<WarehouseSeat> getWarehouseSeatList() {
		return warehouseSeatList;
	}
	public void setWarehouseSeatList(List<WarehouseSeat> warehouseSeatList) {
		this.warehouseSeatList = warehouseSeatList;
	}
	public IGoodsManager getGoodsManager() {
		return goodsManager;
	}
	public void setGoodsManager(IGoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}
	
	public WarehouseSeatGoods getWarehouseSeatGoods() {
		return warehouseSeatGoods;
	}
	public void setWarehouseSeatGoods(WarehouseSeatGoods warehouseSeatGoods) {
		this.warehouseSeatGoods = warehouseSeatGoods;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	
	
}
