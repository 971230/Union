package com.ztesoft.remote.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import services.ServiceBase;
import zte.params.goods.req.InventoryApplyRollbackReq;
import zte.params.goods.resp.InventoryApplyRollbackResp;
import zte.params.store.req.GoodsInventoryCHGReq;
import zte.params.store.req.InventoryApplyLogReq;
import zte.params.store.req.InventoryReduceReq;
import zte.params.store.req.WareHouseAddReq;
import zte.params.store.req.WareHouseDeleteReq;
import zte.params.store.req.WareHouseModifyReq;
import zte.params.store.resp.GoodsInventoryCHGResp;
import zte.params.store.resp.InventoryApplyLogResp;
import zte.params.store.resp.InventoryReduceResp;
import zte.params.store.resp.WareHouseAddResp;
import zte.params.store.resp.WareHouseDeleteResp;
import zte.params.store.resp.WareHouseModifyResp;

import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.GoodsInventoryApplyLog;
import com.ztesoft.net.mall.core.model.GoodsInventoryM;
import com.ztesoft.net.mall.core.model.Warehouse;
import com.ztesoft.net.mall.core.service.IWarehouseManager;
import com.ztesoft.remote.inf.IWarehouseService;
import com.ztesoft.rop.annotation.ServiceMethodBean;
import commons.CommonTools;

/**
* 活动service
* @作者 wu.i 
* @创建日期 2013-9-23 
* @版本 V 1.0
* 
 */
@SuppressWarnings("unchecked")
@ServiceMethodBean(version = "1.0")
public class WarehouseService extends ServiceBase implements IWarehouseService {

	@Resource
	private IWarehouseManager warehouseManager;

	@Override
	public InventoryApplyLogResp queryApplyLogById(InventoryApplyLogReq logReq){
		
		InventoryApplyLogResp resp = new InventoryApplyLogResp();
		List activityList = new ArrayList();
		String log_id = logReq.getLog_id();
		
		try {
			
			GoodsInventoryApplyLog applyLog = warehouseManager.queryInventoryApplyLog(log_id);
			String id = null!=applyLog.getHouse_id()?applyLog.getHouse_id():applyLog.getHouse_id_out();
			Warehouse warehouse = warehouseManager.getWareHouseById(id);
			resp.setApplyLog(applyLog);
			resp.setWarehouse(warehouse);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}catch(RuntimeException e){
			e.printStackTrace();
			CommonTools.addFailError(e.getMessage());
		}
		return resp;
	}
	
	
	@Override
	public InventoryApplyRollbackResp rollbackInventoryApply(InventoryApplyRollbackReq req) {
		String order_id = req.getOrder_id();
		String house_id = req.getHouse_id();
		String goods_id = req.getGoods_id();
		String virtual_house_id = req.getVirtual_house_id();
		String log_id = warehouseManager.inventoryRollback(order_id,goods_id,house_id,virtual_house_id,req.getRollback_num());
		
		InventoryApplyRollbackResp resp = new InventoryApplyRollbackResp();
		resp.setLog_id(log_id);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req,resp);
		return resp;
	}
	
	

	@Override
	public InventoryReduceResp inventoryReduce(InventoryReduceReq req) {
		
		InventoryReduceResp resp = new InventoryReduceResp();
		String virtual_house_id = "-999";
		String is_share = ""; //虚拟仓类型 ：0-独享；1-共享
		String org_id_str = "";  //虚拟仓的销售组织
		
		try {
			
			//确定具体的虚拟库存
			List<Map> vLst = warehouseManager.getVirtualHouseForOrder(
					req.getGoods_id(), Consts.ECS_QUERY_TYPE_GOOD, req.getHouse_id(), req.getOrg_id());
			
			//优先该组织拥有独享的虚拟仓库
			for (Map vMap : vLst) {
				is_share = String.valueOf(vMap.get("attr_inline_type"));
				if (Consts.ATTR_INLINE_TYPE_DUXIANG.equals(is_share)) {
					virtual_house_id = (String)vMap.get("virtual_house_id");
					org_id_str = (String)vMap.get("org_id_str");
					break;
				}
			}
			
			//没有独享的，就随机取共享的虚拟仓库
			if ("-999".equals(virtual_house_id)) {
				Map vMap = vLst.get(0);
				virtual_house_id = (String)vMap.get("virtual_house_id");
				is_share = String.valueOf(vMap.get("attr_inline_type"));
				org_id_str = (String)vMap.get("org_id_str");
			}
			
			//库存更新（减少）
			String log_id = warehouseManager.inventoryReduce(req.getOrder_id(), req.getGoods_id(), 
					req.getHouse_id(), virtual_house_id, is_share, org_id_str, req.getNum());
			
			resp.setLog_id(log_id);
			resp.setError_code("0");
			resp.setError_msg("成功");
			this.addReturn(req, resp);
			
		}catch(Exception e) {
			e.printStackTrace();
			CommonTools.addFailError(e.getMessage());
		}

		
		return resp;
	}


	@Override
	public WareHouseAddResp wareHouseAdd(WareHouseAddReq req) {
		
		WareHouseAddResp resp = new WareHouseAddResp();
		Warehouse wareHouse = new Warehouse();
		wareHouse.setHouse_code(req.getHouse_code());  //仓库编码
		wareHouse.setHouse_name(req.getHouse_name());  //仓库名称
		wareHouse.setRemarks(req.getHouse_desc());  //仓库描述
		wareHouse.setAddress(req.getAddress());  //仓库地址
		wareHouse.setScope_code(req.getScope_code());  //配送范围
		wareHouse.setUser_id(Consts.CURR_FOUNDER_1);  //超级管理员
		
		try {
			warehouseManager.addWarehouse(wareHouse);
			
			resp.setError_code("0");
			resp.setError_msg("成功");
			this.addReturn(req, resp);
			
		} catch (Exception e) {
			e.printStackTrace();
			CommonTools.addFailError(e.getMessage());
		}
		
		return resp;
	}


	@Override
	public WareHouseModifyResp wareHouseModify(WareHouseModifyReq req) {
		
		WareHouseModifyResp resp = new WareHouseModifyResp();
		
		Warehouse wareHouse = new Warehouse();
		wareHouse.setHouse_id(req.getHouse_id());
		wareHouse.setHouse_name(req.getHouse_name());  //仓库名称
		wareHouse.setRemarks(req.getHouse_desc());  //仓库描述
		wareHouse.setAddress(req.getAddress());  //仓库地址
		wareHouse.setScope_code(req.getScope_code());  //配送范围
		wareHouse.setStatus(req.getStatus());  //仓库状态：00A-正常；00X-停用
		wareHouse.setStatus_date(DateFormatUtils.getFormatedDateTime());
		
		try {
			
			warehouseManager.editWarehouse(wareHouse);
			
			resp.setError_code("0");
			resp.setError_msg("成功");
			this.addReturn(req, resp);
			
		} catch (Exception e) {
			e.printStackTrace();
			CommonTools.addFailError(e.getMessage());
		}
		
		return resp;
	}


	@Override
	public WareHouseDeleteResp wareHouseDelete(WareHouseDeleteReq req) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public GoodsInventoryCHGResp goodsInventoryCHG(GoodsInventoryCHGReq req) {
		
		GoodsInventoryCHGResp resp = new GoodsInventoryCHGResp();
		
		String action_code = req.getAction();  //操作编码：0-出库；1-入库；2-回收
		String product_id = req.getProduct_id();
		String company_code = req.getCompany_code();  //货主
		Map map = getProductMapBySku(req.getSku());
		if (map != null && map.size()>0) {
			product_id = map.get("PRODUCT_ID").toString();
			req.setProduct_id(product_id);
		}
		String house_id = req.getHouse_id();
//		if("WH".equals(house_id)){
//			house_id = "201509105761001474";
//		} else {
			Map warehouseMap = getWarehouseByHouseCode(house_id,company_code);
			house_id = (warehouseMap!=null&&warehouseMap.get("HOUSE_ID")!=null)?warehouseMap.get("HOUSE_ID").toString():"";
//		}
		logger.info("action_code = " + action_code);
		logger.info("product_id = " + product_id);
//		logger.info("company_code = " + company_code);
		logger.info("house_id = " + house_id);
		if(StringUtils.isEmpty(house_id)){
			resp.setError_code("-1");
			resp.setError_msg("未查到物理仓库，请核对货品编码和货主编码");
			this.addReturn(req, resp);
			return resp;
		}
		try {
			
			//货品库存信息
			GoodsInventoryM gim = new GoodsInventoryM();
			gim.setProduct_id(product_id);
			gim.setHouse_id(house_id);
			gim.setSku(req.getSku());
			gim.setChange_reason(action_code+":"+req.getChg_reason());
			
			//判断货品库存是否存在
			List lst = this.warehouseManager.isExistsStore(product_id, house_id);
			if (lst.size() > 0) {
				int chg_num = Integer.valueOf(req.getChg_num());
				String action = Consts.INVENTORY_ACTION_1;
				if(chg_num<0){
					action = Consts.INVENTORY_ACTION_0;
				}
				//更新库存信息
				this.warehouseManager.editGoodsInventory(gim, action, 
						Integer.valueOf(req.getChg_num()));
				
			} else if ("PI".equals(action_code)) {
				
				//如果不存在货品库存记录，且是入库操作，则创建货品库存信息，入库数量就是库存数量
				gim.setInventory_num(Integer.valueOf(req.getChg_num()));  
				
				//创建货品库存信息
				this.warehouseManager.addGoodsInventory(gim);
			}else{
				resp.setError_code("-1");
				resp.setError_msg("未记录库存，请核对操作编码和货品编码");
				this.addReturn(req, resp);
				return resp;
			}
			
			resp.setError_code("0");
			resp.setError_msg("成功");
			this.addReturn(req, resp);
			
		} catch (Exception e) {
			e.printStackTrace();
			CommonTools.addFailError(e.getMessage());
		}

		return resp;
	}


	private Map getProductMapBySku(String sku) {
		String sql = "select t.* from es_product t where t.sku='"+sku+"'";
		List<Map> list = baseDaoSupport.queryForList(sql);
		return CollectionUtils.isNotEmpty(list)?list.get(0):null;
	}
	private Map getWarehouseByHouseCode(String house_code,String comp_code) {
		String sql = "select t.* from es_warehouse t where t.house_code=" +
				"(select case when exists (select 1 from es_dc_public e where e.stype='20160601000' and e.pkey='"+house_code+"') " +
				"then (select e.pname from es_dc_public e where e.stype='20160601000' and e.pkey='"+house_code+"')" +
				" else '"+house_code+"' end from dual)"+
				" and t.comp_code='"+comp_code+"'";
		List<Map> list = baseDaoSupport.queryForList(sql);
		return CollectionUtils.isNotEmpty(list)?list.get(0):null;
	}
	
	public IWarehouseManager getWarehouseManager() {
		return warehouseManager;
	}


	public void setWarehouseManager(IWarehouseManager warehouseManager) {
		this.warehouseManager = warehouseManager;
	}

}