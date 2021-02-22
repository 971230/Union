package zte.params.region.req;

import params.ZteError;
import params.ZteRequest;
import zte.params.region.resp.RegionsListResp;

import com.ztesoft.api.ApiRuleException;
import org.apache.commons.lang3.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

public class RegionsListReq extends ZteRequest<RegionsListResp> {
	
	public static final String PROVICE = "0";//省
	public static final String CITY = "1";//市
	public static final String REGION = "2";//区
	public static final String CHILDREN = "3"; //子
	@ZteSoftCommentAnnotationParam(name="查询方式",type="String",isNecessary="Y",desc="查询方式：0省、1市、2区")
	private String region_type;
	@ZteSoftCommentAnnotationParam(name="父级编号",type="String",isNecessary="N",desc="父级编号：查市或区的时需要填写")
	private String region_parent_id;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(region_type))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "region_type不能为空"));
		if(!PROVICE.equals(region_type) && StringUtils.isEmpty(region_parent_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "region_parent_id不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.regionService.region.list";
	}

	public String getRegion_type() {
		return region_type;
	}

	public void setRegion_type(String region_type) {
		this.region_type = region_type;
	}

	public String getRegion_parent_id() {
		return region_parent_id;
	}

	public void setRegion_parent_id(String region_parent_id) {
		this.region_parent_id = region_parent_id;
	}

}
