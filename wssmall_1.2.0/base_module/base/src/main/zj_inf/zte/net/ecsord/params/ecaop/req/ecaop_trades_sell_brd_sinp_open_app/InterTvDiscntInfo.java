package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app;

import java.util.*;
import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.InterTvDiscntAttr;

public class InterTvDiscntInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "互联网电视资费（免费的话传0）", type = "String", isNecessary = "Y", desc = "互联网电视资费（免费的话传0）")
	private String interTvDiscntId;

	@ZteSoftCommentAnnotationParam(name = "互联网电视的资费属性", type = "InterTvDiscntAttr", isNecessary = "N", desc = "互联网电视的资费属性")
	private InterTvDiscntAttr interTvDiscntAttr;

	public String getInterTvDiscntId() {
		return this.interTvDiscntId;
	}

	public void setInterTvDiscntId(String v) {
		this.interTvDiscntId = v;
	}

	public InterTvDiscntAttr getInterTvDiscntAttr() {
		return this.interTvDiscntAttr;
	}

	public void setInterTvDiscntAttr(InterTvDiscntAttr v) {
		this.interTvDiscntAttr = v;
	}

}
