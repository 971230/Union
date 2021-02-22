package zte.params.store.req;

import org.apache.commons.lang3.StringUtils;

import params.ZteError;
import params.ZteRequest;
import zte.params.store.resp.WareHouseAddResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

public class WareHouseAddReq extends ZteRequest<WareHouseAddResp> {
	
	@ZteSoftCommentAnnotationParam(name="仓库编码", type="String", isNecessary="Y", desc="物理仓编码")
	private String house_code;
	
	@ZteSoftCommentAnnotationParam(name="仓库名称", type="String", isNecessary="Y", desc="物理仓名称")
	private String house_name;
	
	@ZteSoftCommentAnnotationParam(name="仓库描述", type="String", isNecessary="Y", desc="物理仓详细描述")
	private String house_desc;
	
	@ZteSoftCommentAnnotationParam(name="仓库地址", type="String", isNecessary="Y", desc="物理仓地址信息")
	private String address;
	
	@ZteSoftCommentAnnotationParam(name="发货范围", type="String", isNecessary="Y", desc="配送范围")
	private String scope_code;
	
	public String getHouse_code() {
		return house_code;
	}


	public void setHouse_code(String house_code) {
		this.house_code = house_code;
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
	
	
	@Override
	public void check() throws ApiRuleException {
		
		if (StringUtils.isEmpty(house_code)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "物理仓编码不能为空"));
		}
		
		if (StringUtils.isEmpty(house_name)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "物理仓名称不能为空"));
		}
		
		if (StringUtils.isEmpty(address)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "物理仓地址信息不能为空"));
		}
		
		if (StringUtils.isEmpty(scope_code)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "配送范围不能为空"));
		}
	}

	
	@Override
	public String getApiMethodName() {
		return "zte.service.warehouse.add";
	}

}
