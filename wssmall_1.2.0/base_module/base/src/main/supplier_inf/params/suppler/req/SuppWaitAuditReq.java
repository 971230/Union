package params.suppler.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import params.suppler.resp.SupplierCountResp;


/**
 * 查询待审核供货商
 * @author hu.yi
 * @date 2013.12.31
 */
public class SuppWaitAuditReq extends ZteRequest<SupplierCountResp>{

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "supplierServ.waitAuditSupplier";
	}

}
