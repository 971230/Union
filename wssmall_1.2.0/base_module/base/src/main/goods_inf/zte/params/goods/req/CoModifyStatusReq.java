package zte.params.goods.req;

import params.ZteRequest;
import zte.params.goods.resp.CoModifyStatusResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

public class CoModifyStatusReq extends ZteRequest<CoModifyStatusResp> {

	@ZteSoftCommentAnnotationParam(name="主键",type="String",isNecessary="Y",desc="主键", hasChild=false)
	private String id;
	
	@ZteSoftCommentAnnotationParam(name="业务编码",type="String",isNecessary="Y",desc="CO_HUOPIN-货品同步；CO_SHANGPIN-商品同步；CO_HAOMA-号码同步", hasChild=false)
	private String service_code;
	
	@ZteSoftCommentAnnotationParam(name="新状态",type="String",isNecessary="Y",desc="新状态", hasChild=false)
	private String status_new;
	
	@ZteSoftCommentAnnotationParam(name="旧状态",type="String",isNecessary="N",desc="旧状态", hasChild=false)
	private String status_old;

	@Override
	public void check() throws ApiRuleException {
		
		if (StringUtils.isEmpty(id)) {
			CommonTools.addFailError("主键【id】不能为空！");
        }
		
		if (StringUtils.isEmpty(service_code)) {
			CommonTools.addFailError("业务编码【service_code】不能为空！");
        }
		
		if (StringUtils.isEmpty(status_new)) {
			CommonTools.addFailError("目标状态【status_new】不能为空！");
        }

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goods.modifyStatus";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	
	public String getStatus_new() {
		return status_new;
	}

	public void setStatus_new(String status_new) {
		this.status_new = status_new;
	}

	public String getStatus_old() {
		return status_old;
	}

	public void setStatus_old(String status_old) {
		this.status_old = status_old;
	}

}
