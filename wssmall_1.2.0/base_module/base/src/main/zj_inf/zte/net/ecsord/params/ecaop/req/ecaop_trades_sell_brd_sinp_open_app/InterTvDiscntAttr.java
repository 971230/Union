package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app;

import java.util.*;
import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class InterTvDiscntAttr implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "续包年资费id（到期资费方式为1：续约包年时传）", type = "String", isNecessary = "N", desc = "续包年资费id（到期资费方式为1：续约包年时传）")
	private String interDelayYearDiscntId;

	@ZteSoftCommentAnnotationParam(name = "包年周期，MM格式（资费方式为包年时传）", type = "String", isNecessary = "N", desc = "包年周期，MM格式（资费方式为包年时传）")
	private String interDiscntCycle;

	@ZteSoftCommentAnnotationParam(name = "到期资费方式", type = "String", isNecessary = "N", desc = "到期资费方式")
	private String interTvDelayType;

	@ZteSoftCommentAnnotationParam(name = "到期资费id", type = "String", isNecessary = "N", desc = "到期资费id")
	private String interTvDelayDiscntId;

	@ZteSoftCommentAnnotationParam(name = "生效方式", type = "String", isNecessary = "N", desc = "生效方式")
	private String interTvDelayDiscntType;

	public String getInterDelayYearDiscntId() {
		return this.interDelayYearDiscntId;
	}

	public void setInterDelayYearDiscntId(String v) {
		this.interDelayYearDiscntId = v;
	}

	public String getInterDiscntCycle() {
		return this.interDiscntCycle;
	}

	public void setInterDiscntCycle(String v) {
		this.interDiscntCycle = v;
	}

	public String getInterTvDelayType() {
		return this.interTvDelayType;
	}

	public void setInterTvDelayType(String v) {
		this.interTvDelayType = v;
	}

	public String getInterTvDelayDiscntId() {
		return this.interTvDelayDiscntId;
	}

	public void setInterTvDelayDiscntId(String v) {
		this.interTvDelayDiscntId = v;
	}

	public String getInterTvDelayDiscntType() {
		return this.interTvDelayDiscntType;
	}

	public void setInterTvDelayDiscntType(String v) {
		this.interTvDelayDiscntType = v;
	}

}
