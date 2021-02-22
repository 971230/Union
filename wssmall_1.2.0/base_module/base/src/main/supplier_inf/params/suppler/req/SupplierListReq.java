package params.suppler.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;

import params.ZteRequest;
import params.ZteResponse;



/**
 * 供货商列表查询参数
 * @author hu.yi
 * @date 2013.12.25
 */
public class SupplierListReq extends ZteRequest<ZteResponse>{

	private String catid;

	public String getCatid() {
		return catid;
	}

	public void setCatid(String catid) {
		this.catid = catid;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(catid)){
			throw new ApiRuleException("-1","catid不能为空");
		}
	}

	@Override
	public String getApiMethodName() {
		return "supplierServ.getSupplierByCatId";
	}
}
