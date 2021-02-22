package params.adminuser.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

public class LoginLogReq extends ZteRequest {
	
//	private String seq;
	@ZteSoftCommentAnnotationParam(name="登陆号码",type="String",isNecessary="Y",desc="登陆号码。")
	private String mobile;
	private String staff_code;
	private String station;
	@ZteSoftCommentAnnotationParam(name="登陆城市",type="String",isNecessary="Y",desc="登陆城市。")
	private String city;
	@ZteSoftCommentAnnotationParam(name="登陆ip",type="String",isNecessary="Y",desc="登陆ip。")
	private String ip;
	private String create_time;
	@ZteSoftCommentAnnotationParam(name="操作类型",type="String",isNecessary="Y",desc="0 登录 1 退出 ")
	private String kind;
	@ZteSoftCommentAnnotationParam(name="登陆渠道来源",type="String",isNecessary="Y",desc="登陆渠道来源。")
	private String source_from;
	private String staff_name;
	private String lan_code;
/*	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}*/
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getStaff_code() {
		return staff_code;
	}
	public void setStaff_code(String staff_code) {
		this.staff_code = staff_code;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getStaff_name() {
		return staff_name;
	}
	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}
	public String getLan_code() {
		return lan_code;
	}
	public void setLan_code(String lan_code) {
		this.lan_code = lan_code;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(null == source_from || "".equals(source_from)){
			throw new ApiRuleException("-1","来源渠道不能为空");
		}
		
		if(null == kind || "".equals(kind)){
			throw new ApiRuleException("-1","操作类型不能为空");
		}
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "sysService.loginLog";
	}
}
