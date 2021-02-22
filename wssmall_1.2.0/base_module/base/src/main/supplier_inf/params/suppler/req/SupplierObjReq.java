package params.suppler.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;

import params.ZteRequest;
import params.ZteResponse;


/**
 * 供货商实体查询传参
 * @author hu.yi
 * @date 2013.12.25
 */
public class SupplierObjReq extends  ZteRequest<ZteResponse>{

	
	private String supplier_id;

	public String getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(supplier_id)){
			throw new ApiRuleException("-1","supplier_id不能为空");
		}
	}

	@Override
	public String getApiMethodName() {
		return "supplierServ.findSupplierById";
	}
}
