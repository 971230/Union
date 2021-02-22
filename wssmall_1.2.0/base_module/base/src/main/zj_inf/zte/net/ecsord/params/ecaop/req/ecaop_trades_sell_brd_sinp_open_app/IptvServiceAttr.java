package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app;

import java.util.*;
import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class IptvServiceAttr implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "iptv服务属性value", type = "String", isNecessary = "Y", desc = "iptv服务属性value")
	private String value;

	@ZteSoftCommentAnnotationParam(name = "iptv服务属性code", type = "String", isNecessary = "Y", desc = "iptv服务属性code")
	private String code;

	public String getValue() {
		return this.value;
	}

	public void setValue(String v) {
		this.value = v;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String v) {
		this.code = v;
	}

}
