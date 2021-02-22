package zte.params.region.req;

import params.ZteRequest;
import zte.params.region.resp.RegionsGetResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class RegionsGetReq extends ZteRequest<RegionsGetResp> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3987009847937209690L;
	public static final String ID_COND_TYPE = "0";//标识
	public static final String NAME_COND_TYPE = "1";//名称
	@ZteSoftCommentAnnotationParam(name="查询条件方式",type="String",isNecessary="N",desc="查询方式：0【ID】、1【NAME】默认0")
	private String region_cond_type = ID_COND_TYPE;
	@ZteSoftCommentAnnotationParam(name="父级编号",type="String",isNecessary="N",desc="父级编号：查市或区的时需要填写")
	private String region_parent_id;
	@ZteSoftCommentAnnotationParam(name="区域标识",type="String",isNecessary="Y",desc="区域标识")
	private String region_id;
	@ZteSoftCommentAnnotationParam(name="区域本地化名称",type="String",isNecessary="N",desc="区域本地化名称")
	private String local_name;
	@ZteSoftCommentAnnotationParam(name="等级",type="String",isNecessary="N",desc="等级")
	private String region_grade;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {	
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getLocal_name() {
		return local_name;
	}

	public void setLocal_name(String local_name) {
		this.local_name = local_name;
	}

	public String getRegion_grade() {
		return region_grade;
	}

	public void setRegion_grade(String region_grade) {
		this.region_grade = region_grade;
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.regionService.region.get";
	}
	

	public String getRegion_cond_type() {
		return region_cond_type;
	}

	public void setRegion_cond_type(String region_cond_type) {
		this.region_cond_type = region_cond_type;
	}

	public String getRegion_parent_id() {
		return region_parent_id;
	}

	public void setRegion_parent_id(String region_parent_id) {
		this.region_parent_id = region_parent_id;
	}

}
