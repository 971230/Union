package zte.params.store.req;

import params.ZteError;
import params.ZteRequest;
import zte.params.store.resp.WareHouseModifyResp;

import com.ztesoft.api.ApiRuleException;
import org.apache.commons.lang3.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import commons.CommonTools;
import consts.ConstsCore;

public class WareHouseModifyReq extends ZteRequest<WareHouseModifyResp> {
	
	@ZteSoftCommentAnnotationParam(name="仓库标识", type="String", isNecessary="Y", desc="物理仓标识")
	private String house_id;
	
	@ZteSoftCommentAnnotationParam(name="仓库名称", type="String", isNecessary="N", desc="物理仓名称")
	private String house_name;
	
	@ZteSoftCommentAnnotationParam(name="仓库描述", type="String", isNecessary="N", desc="物理仓详细描述")
	private String house_desc;
	
	@ZteSoftCommentAnnotationParam(name="仓库地址", type="String", isNecessary="N", desc="物理仓地址信息")
	private String address;
	
	@ZteSoftCommentAnnotationParam(name="发货范围", type="String", isNecessary="N", desc="配送范围")
	private String scope_code;
	
	@ZteSoftCommentAnnotationParam(name="仓库状态", type="String", isNecessary="N", desc="仓库状态：00A-正常；00X-停用")
	private String status;
	
	public String getHouse_id() {
		return house_id;
	}


	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}


	public String getHouse_name() {
		return house_name;
	}


	public void setHouse_name(String house_name) {
		this.house_name = house_name;
	}


	public String getHouse_desc() {
		return house_desc;
	}


	public void setHouse_desc(String house_desc) {
		this.house_desc = house_desc;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getScope_code() {
		return scope_code;
	}


	public void setScope_code(String scope_code) {
		this.scope_code = scope_code;
	}
	
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public void check() throws ApiRuleException {
		
		if (StringUtils.isEmpty(house_id)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "物理仓标识不能为空"));
		}
	}

	@Override
	public String getApiMethodName() {
		return "zte.service.warehouse.modify";
	}

}
