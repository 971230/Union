package zte.params.store.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import params.ZteRequest;
import zte.params.store.resp.InventoryApplyLogResp;

/**
 * 
 * @author deng.yx
 * 库存分配日志请求实体
 *
 */
public class InventoryApplyLogReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="库存分配日志标识", type="String", isNecessary="Y", desc="log_id：库存分配日志标识")
	private String log_id;
	
	public Class<InventoryApplyLogResp> getResponseClass() {
		return InventoryApplyLogResp.class;
	}


	@Override
    public void check() throws ApiRuleException {
        if (ApiUtils.isBlank(log_id)) {
            throw new ApiRuleException("-1","库存分配日志标识【log_id】不能为空!");
        }
    }

    @Override
    public String getApiMethodName() {
        return "com.ztesoft.remote.warehouse.queryApplyLogById";
    }


	public String getLog_id() {
		return log_id;
	}


	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}



}
