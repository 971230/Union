package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app;

import java.util.*;
import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class IptvDiscntAttr implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "续包年资费id（到期资费方式为1：续约包年时传）", type = "String", isNecessary = "N", desc = "续包年资费id（到期资费方式为1：续约包年时传）")
	private String iptvDelayYearDiscntId;

	@ZteSoftCommentAnnotationParam(name = "包年周期，MM格式（资费方式为包年时传）", type = "String", isNecessary = "N", desc = "包年周期，MM格式（资费方式为包年时传）")
	private String iptvDiscntCycle;

	@ZteSoftCommentAnnotationParam(name = "到期资费id", type = "String", isNecessary = "N", desc = "到期资费id")
	private String iptvDelayDiscntId;

	@ZteSoftCommentAnnotationParam(name = "生效方式", type = "String", isNecessary = "N", desc = "生效方式")
	private String iptvDelayDiscntType;

	@ZteSoftCommentAnnotationParam(name = "到期资费方式", type = "String", isNecessary = "N", desc = "到期资费方式")
	private String iptvDelayType;

	public String getIptvDelayYearDiscntId() {
		return this.iptvDelayYearDiscntId;
	}

	public void setIptvDelayYearDiscntId(String v) {
		this.iptvDelayYearDiscntId = v;
	}

	public String getIptvDiscntCycle() {
		return this.iptvDiscntCycle;
	}

	public void setIptvDiscntCycle(String v) {
		this.iptvDiscntCycle = v;
	}

	public String getIptvDelayDiscntId() {
		return this.iptvDelayDiscntId;
	}

	public void setIptvDelayDiscntId(String v) {
		this.iptvDelayDiscntId = v;
	}

	public String getIptvDelayDiscntType() {
		return this.iptvDelayDiscntType;
	}

	public void setIptvDelayDiscntType(String v) {
		this.iptvDelayDiscntType = v;
	}

	public String getIptvDelayType() {
		return this.iptvDelayType;
	}

	public void setIptvDelayType(String v) {
		this.iptvDelayType = v;
	}

}
