package zte.net.common.params.req;

import params.ZteBusiRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;


/**
 * 
 * @author wu.i 
 * 订单环节对象
 * @param <T>
 * 
 */
public class ZteInstUpdateRequest<T extends ZteBusiRequest> extends ZteBusiRequest {

	
	private T updateRequest ;

	public T getUpdateRequest() {
		return updateRequest;
	}

	public void setUpdateRequest(T updateRequest) {
		this.updateRequest = updateRequest;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.zteinst.update";
	}

}
