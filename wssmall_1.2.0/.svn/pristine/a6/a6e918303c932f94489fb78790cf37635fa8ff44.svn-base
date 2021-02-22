/**
 * 
 */
package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

/**
 * TODO
 *
 * @author Musoon
 * 2014-4-20 下午8:47:28
 * 
 */
public class OrderQryBatchReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="批量ID",type="String",isNecessary="Y",desc="batchId：批量ID不能为空。")
	private String batchId;

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.getOrderByBatchId";
	}

}
