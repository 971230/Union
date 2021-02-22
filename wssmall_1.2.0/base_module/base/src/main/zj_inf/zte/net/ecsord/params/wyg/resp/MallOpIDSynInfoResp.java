package zte.net.ecsord.params.wyg.resp;

import java.util.ArrayList;
import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.wyg.vo.StaffInfoResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class MallOpIDSynInfoResp  extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="返回代码",type="String",isNecessary="Y",desc="resp_code：返回代码")
	private String resp_code;	
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="Y",desc="resp_msg：返回描述")
	private String resp_msg;
	@ZteSoftCommentAnnotationParam(name="员工信息",type="String",isNecessary="Y",desc="StaffInfo：员工信息")
	private List<StaffInfoResp> StaffInfo;
	public String getResp_code() {
		return resp_code;
	}
	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}
	public String getResp_msg() {
		return resp_msg;
	}
	public void setResp_msg(String resp_msg) {
		this.resp_msg = resp_msg;
	}
	public List<StaffInfoResp> getStaffInfo() {
		if(null==StaffInfo){
			StaffInfo = new ArrayList<StaffInfoResp>();
		}
		return StaffInfo;
	}
	public void setStaffInfo(List<StaffInfoResp> staffInfo) {
		StaffInfo = staffInfo;
	}
	
}
