package zte.params.store.resp;


import params.ZteResponse;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.GoodsInventoryApplyLog;
import com.ztesoft.net.mall.core.model.Warehouse;
/**
 * 
 * @author deng.yx
 * 库存分配日志返回实体
 *
 */
public class InventoryApplyLogResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="库存分配日志信息", type="GoodsInventoryApplyLog", isNecessary="N", desc="库存分配日志信息")
	private GoodsInventoryApplyLog applyLog;
	
	@ZteSoftCommentAnnotationParam(name="仓库基本信息", type="Warehouse", isNecessary="N", desc="仓库基本信息")
	private Warehouse warehouse;

	public GoodsInventoryApplyLog getApplyLog() {
		return applyLog;
	}

	public void setApplyLog(GoodsInventoryApplyLog applyLog) {
		this.applyLog = applyLog;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}


	
}
