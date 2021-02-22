package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app;

import java.util.*;
import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.IptvDiscntInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.IptvServiceAttr;

public class IptvInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "iptv服务", type = "String", isNecessary = "Y", desc = "iptv服务")
	private String IptvService;

	@ZteSoftCommentAnnotationParam(name = "iptv资费信息", type = "IptvDiscntInfo", isNecessary = "N", desc = "iptv资费信息")
	private List<IptvDiscntInfo> IptvDiscntInfo;

	@ZteSoftCommentAnnotationParam(name = "iptv服务属性", type = "IptvServiceAttr", isNecessary = "N", desc = "iptv服务属性")
	private List<IptvServiceAttr> IptvServiceAttr;

	public String getIptvService() {
		return this.IptvService;
	}

	public void setIptvService(String v) {
		this.IptvService = v;
	}

	public List<IptvDiscntInfo> getIptvDiscntInfo() {
		return this.IptvDiscntInfo;
	}

	public void setIptvDiscntInfo(List<IptvDiscntInfo> v) {
		this.IptvDiscntInfo = v;
	}

	public List<IptvServiceAttr> getIptvServiceAttr() {
		return this.IptvServiceAttr;
	}

	public void setIptvServiceAttr(List<IptvServiceAttr> v) {
		this.IptvServiceAttr = v;
	}

}
