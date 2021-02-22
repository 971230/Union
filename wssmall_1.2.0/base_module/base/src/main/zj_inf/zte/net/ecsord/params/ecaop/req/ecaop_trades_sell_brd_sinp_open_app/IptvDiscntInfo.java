package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app;

import java.util.*;
import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.IptvDiscntAttr;

public class IptvDiscntInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "iptv资费（免费的话传0）", type = "String", isNecessary = "Y", desc = "iptv资费（免费的话传0）")
	private String IptvDiscntId;

	@ZteSoftCommentAnnotationParam(name = "iptv资费属性", type = "IptvDiscntAttr", isNecessary = "N", desc = "iptv资费属性")
	private IptvDiscntAttr iptvDiscntAttr;

	public String getIptvDiscntId() {
		return this.IptvDiscntId;
	}

	public void setIptvDiscntId(String v) {
		this.IptvDiscntId = v;
	}

	public IptvDiscntAttr getIptvDiscntAttr() {
		return this.iptvDiscntAttr;
	}

	public void setIptvDiscntAttr(IptvDiscntAttr v) {
		this.iptvDiscntAttr = v;
	}

}
