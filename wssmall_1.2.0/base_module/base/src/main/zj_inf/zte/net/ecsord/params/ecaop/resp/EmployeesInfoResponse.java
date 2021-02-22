package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;
import zte.net.ecsord.params.ecaop.resp.vo.StaffInfoVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class EmployeesInfoResponse  extends ZteResponse{
	private static final long serialVersionUID = 1L;
	
	
	@ZteSoftCommentAnnotationParam(name = "操作员ID（ESS员工号）", type = "String", isNecessary = "Y", desc = "操作员ID（ESS员工号）")
	private String operatorId;
	
	@ZteSoftCommentAnnotationParam(name = "省分", type = "String", isNecessary = "N", desc = "province:省分")
	private String province;
	
	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "N", desc = "city:地市")
	private String city;
	
	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "N", desc = "district:区县")
	private String district;
	
	
	@ZteSoftCommentAnnotationParam(name = "部门编码", type = "String", isNecessary = "N", desc = "departId:部门编码")
	private String departId;
	
	@ZteSoftCommentAnnotationParam(name = "部门名称", type = "String", isNecessary = "N", desc = "departName:部门名称")
	private String departName;
	
	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "N", desc = "channelId:渠道编码")
	private String channelId;
	
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "N", desc = "channelType:渠道类型")
	private String channelType;
	
	@ZteSoftCommentAnnotationParam(name = "渠道名称", type = "String", isNecessary = "N", desc = "channelName渠道名称")
	private String channelName;
	
	@ZteSoftCommentAnnotationParam(name="员工信息",type="List",isNecessary="Y",desc="staffInfo：员工信息")
	private StaffInfoVo staffInfo;

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String detail;
	
	
	@ZteSoftCommentAnnotationParam(name="保留字段",type="String",isNecessary="N",desc="paras：保留字段")
	private List<ParamsVo> paras;


	public String getOperatorId() {
		return operatorId;
	}


	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	public String getDepartId() {
		return departId;
	}


	public void setDepartId(String departId) {
		this.departId = departId;
	}


	public String getDepartName() {
		return departName;
	}


	public void setDepartName(String departName) {
		this.departName = departName;
	}


	public String getChannelId() {
		return channelId;
	}


	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}


	public String getChannelType() {
		return channelType;
	}


	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}


	public String getChannelName() {
		return channelName;
	}


	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}


	public StaffInfoVo getStaffInfo() {
		return staffInfo;
	}


	public void setStaffInfo(StaffInfoVo staffInfo) {
		this.staffInfo = staffInfo;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getDetail() {
		return detail;
	}


	public void setDetail(String detail) {
		this.detail = detail;
	}


	public List<ParamsVo> getParas() {
		return paras;
	}


	public void setParas(List<ParamsVo> paras) {
		this.paras = paras;
	}

	
	
}
