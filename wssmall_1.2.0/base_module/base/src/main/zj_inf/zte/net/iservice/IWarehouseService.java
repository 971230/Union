/**
 * 
 */
package zte.net.iservice;

import zte.net.ecsord.params.warehouse.req.WareHouseMatchRandomReq;
import zte.net.ecsord.params.warehouse.req.WareHouseMatchReq;
import zte.net.ecsord.params.warehouse.resp.WareHouseMatchRandomResp;
import zte.net.ecsord.params.warehouse.resp.WareHouseMatchResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

/**
 * @author ZX
 *
 */
@ZteSoftCommentAnnotation(type="class", desc="统一仓库API", summary="统一仓库API")
public interface IWarehouseService {
	
	@ZteSoftCommentAnnotation(type="method",desc="仓库出库方案地市优先规则",summary="仓库出库方案地市优先规则")	
	public WareHouseMatchResp warehouseAreaPriorityRule(WareHouseMatchReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="仓库出库方案随机规则，所有出库规则最后都需要调用此规则",summary="仓库出库方案随机规则")	
	public WareHouseMatchRandomResp randomFilter(WareHouseMatchRandomReq req);
	
}
