package com.ztesoft.remote.inf;


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

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;


@ZteSoftCommentAnnotation(type="class", desc="库存类API", summary="库存类API")
public interface IWarehouseService {
	
	@ZteSoftCommentAnnotation(type="method",desc="根据活动标识获取活动信息",summary="根据活动标识获取活动信息")
	public InventoryApplyLogResp queryApplyLogById(InventoryApplyLogReq logReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="库存减少操作",summary="库存减少操作（订单下单时）")
	public InventoryReduceResp inventoryReduce(InventoryReduceReq req);
     
	@ZteSoftCommentAnnotation(type="method",desc="回滚仓库存，包括货品库存和分配库存",summary="回滚仓库存，包括货品库存和分配库存")	
	public InventoryApplyRollbackResp rollbackInventoryApply(InventoryApplyRollbackReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="物理仓新增",summary="物理仓新增")	
	public WareHouseAddResp wareHouseAdd(WareHouseAddReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="物理仓修改",summary="物理仓修改")	
	public WareHouseModifyResp wareHouseModify(WareHouseModifyReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="物理仓删除",summary="物理仓删除")	
	public WareHouseDeleteResp wareHouseDelete(WareHouseDeleteReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="货品库存变动",summary="货品库存变动，包括：入库、出库")	
	public GoodsInventoryCHGResp goodsInventoryCHG(GoodsInventoryCHGReq req);
}