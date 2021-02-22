package params.adminuser.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

public class AccessLogReq extends ZteRequest{
//	private String id;
	@ZteSoftCommentAnnotationParam(name="访问ip",type="String",isNecessary="Y",desc="访问ip")
	private String ip;
	@ZteSoftCommentAnnotationParam(name="具体的url",type="String",isNecessary="Y",desc="具体的url")
	private String url; 
	@ZteSoftCommentAnnotationParam(name="访问的页面名",type="String",isNecessary="Y",desc="访问的页面名")
	private String page; 
	@ZteSoftCommentAnnotationParam(name="访问者地区",type="String",isNecessary="Y",desc="访问者地区")
	private String area; 
	@ZteSoftCommentAnnotationParam(name="访问时间",type="String",isNecessary="Y",desc="访问时间")
	private String access_time; 
	@ZteSoftCommentAnnotationParam(name="停留时间",type="String",isNecessary="Y",desc="停留时间")
	private int stay_time; 
	@ZteSoftCommentAnnotationParam(name="消耗积分",type="String",isNecessary="Y",desc="消耗积分")
	private int point ;
	@ZteSoftCommentAnnotationParam(name="会员名称，如果为空则为访客",type="String",isNecessary="Y",desc="会员名称，如果为空则为访客")
	private String membername; 
	private String source_from;

/*	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}*/

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAccess_time() {
		return access_time;
	}

	public void setAccess_time(String access_time) {
		this.access_time = access_time;
	}

	public int getStay_time() {
		return stay_time;
	}

	public void setStay_time(int stay_time) {
		this.stay_time = stay_time;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(null == source_from || "".equals(source_from)){
			throw new ApiRuleException("-1","来源渠道不能为空");
		}
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "sysService.accessLog";
	}

}
