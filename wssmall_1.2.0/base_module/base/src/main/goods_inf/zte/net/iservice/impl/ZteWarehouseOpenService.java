package zte.net.iservice.impl;

import org.springframework.stereotype.Service;

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

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.remote.inf.IWarehouseService;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-01-22 15:54
 * To change this template use File | Settings | File Templates.
 */
@ServiceMethodBean(version="1.0")
@Service
public class ZteWarehouseOpenService {

//    @Resource
    private IWarehouseService warehouseService;
    
    private void init() {
    	if (null == warehouseService) warehouseService = ApiContextHolder.getBean("warehouseService");
    }
    
	@ServiceMethod(method="com.ztesoft.remote.warehouse.queryApplyLogById",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public InventoryApplyLogResp queryApplyLogById(InventoryApplyLogReq logReq){
		this.init();
		return warehouseService.queryApplyLogById(logReq);
	}
	
	

	@ServiceMethod(method="com.warehouseService.inventory.apply.rollback",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public InventoryApplyRollbackResp rollbackInventoryApply(InventoryApplyRollbackReq req){
		this.init();
		req.setRopRequestContext(null);
		return warehouseService.rollbackInventoryApply(req);
	}
	
	@ServiceMethod(method="com.warehouseService.inventory.reduce",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public InventoryReduceResp inventoryReduce(InventoryReduceReq req){
		this.init();
		req.setRopRequestContext(null);
		return warehouseService.inventoryReduce(req);
	}
	
	@ServiceMethod(method="zte.service.warehouse.add",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public WareHouseAddResp wareHouseAdd(WareHouseAddReq req) {
		this.init();
		req.setRopRequestContext(null);
		return warehouseService.wareHouseAdd(req);
	}
	
	@ServiceMethod(method="zte.service.warehouse.modify",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public WareHouseModifyResp wareHouseAdd(WareHouseModifyReq req) {
		this.init();
		req.setRopRequestContext(null);
		return warehouseService.wareHouseModify(req);
	}
	
	@ServiceMethod(method="zte.service.warehouse.delete",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public WareHouseDeleteResp wareHouseAdd(WareHouseDeleteReq req) {
		this.init();
		req.setRopRequestContext(null);
		return warehouseService.wareHouseDelete(req);
	}
	
	@ServiceMethod(method="zte.service.inventory.chg",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsInventoryCHGResp goodsInventoryCHG(GoodsInventoryCHGReq req) {
		this.init();
		req.setRopRequestContext(null);
		return warehouseService.goodsInventoryCHG(req);
	}
}
