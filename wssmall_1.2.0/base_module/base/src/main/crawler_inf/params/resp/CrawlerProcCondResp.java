package params.resp;

import java.util.List;
import java.util.Map;

import params.ZteResponse;
import params.req.CrawlerProcCondSettingReq;
import zte.net.model.CrawlerUrlConfig;

public class CrawlerProcCondResp extends ZteResponse{
	
	private String cfg_id;//爬虫配置信息id
	
	private String crawlerAddress;//爬虫目标地址
	
	private String crawlerPort;//爬虫目标端口
	
	private String crawlerProxyType;//是否走代理
	
	private String crawlerProxyAddress;//代理地址
	
	private String crawlerProxyPort;//代理端口
	
	private String crawlerProxyUser;//代理用户名
	
	private String crawlerProxyPwd;//代理密码
	
	private String crawlerIsOutIdCheck;//是否开启单号匹配校验
	
	private Map<String,List<CrawlerProcCondSettingReq>> respMap;
	
	private List<CrawlerUrlConfig> urlList;

	
	public String getCfg_id() {
		return cfg_id;
	}

	public void setCfg_id(String cfg_id) {
		this.cfg_id = cfg_id;
	}

	public Map<String, List<CrawlerProcCondSettingReq>> getRespMap() {
		return respMap;
	}

	public void setRespMap(Map<String, List<CrawlerProcCondSettingReq>> respMap) {
		this.respMap = respMap;
	}

	public String getCrawlerAddress() {
		return crawlerAddress;
	}

	public void setCrawlerAddress(String crawlerAddress) {
		this.crawlerAddress = crawlerAddress;
	}

	public String getCrawlerPort() {
		return crawlerPort;
	}

	public void setCrawlerPort(String crawlerPort) {
		this.crawlerPort = crawlerPort;
	}

	public String getCrawlerProxyType() {
		return crawlerProxyType;
	}

	public void setCrawlerProxyType(String crawlerProxyType) {
		this.crawlerProxyType = crawlerProxyType;
	}

	public String getCrawlerProxyAddress() {
		return crawlerProxyAddress;
	}

	public void setCrawlerProxyAddress(String crawlerProxyAddress) {
		this.crawlerProxyAddress = crawlerProxyAddress;
	}

	public String getCrawlerProxyPort() {
		return crawlerProxyPort;
	}

	public void setCrawlerProxyPort(String crawlerProxyPort) {
		this.crawlerProxyPort = crawlerProxyPort;
	}

	public String getCrawlerProxyUser() {
		return crawlerProxyUser;
	}

	public void setCrawlerProxyUser(String crawlerProxyUser) {
		this.crawlerProxyUser = crawlerProxyUser;
	}

	public String getCrawlerProxyPwd() {
		return crawlerProxyPwd;
	}

	public void setCrawlerProxyPwd(String crawlerProxyPwd) {
		this.crawlerProxyPwd = crawlerProxyPwd;
	}

	public String getCrawlerIsOutIdCheck() {
		return crawlerIsOutIdCheck;
	}

	public void setCrawlerIsOutIdCheck(String crawlerIsOutIdCheck) {
		this.crawlerIsOutIdCheck = crawlerIsOutIdCheck;
	}

	public List<CrawlerUrlConfig> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<CrawlerUrlConfig> urlList) {
		this.urlList = urlList;
	}
	
	
}
