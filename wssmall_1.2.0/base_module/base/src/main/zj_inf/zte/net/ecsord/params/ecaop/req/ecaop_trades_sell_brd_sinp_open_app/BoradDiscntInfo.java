package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app;

import java.util.*;
import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.BoradDiscntAttr;

public class BoradDiscntInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "包年周期，MM格式（资费方式为包年时传）", type = "String", isNecessary = "N", desc = "包年周期，MM格式（资费方式为包年时传）")
	private String boradDiscntCycle;

	@ZteSoftCommentAnnotationParam(name = "开户选择的资费属性", type = "BoradDiscntAttr", isNecessary = "N", desc = "开户选择的资费属性")
	private BoradDiscntAttr boradDiscntAttr;

	@ZteSoftCommentAnnotationParam(name = "开户选择的资费ID", type = "String", isNecessary = "Y", desc = "开户选择的资费ID")
	private String boradDiscntId;

	public String getBoradDiscntCycle() {
		return this.boradDiscntCycle;
	}

	public void setBoradDiscntCycle(String v) {
		this.boradDiscntCycle = v;
	}

	public BoradDiscntAttr getBoradDiscntAttr() {
		return this.boradDiscntAttr;
	}

	public void setBoradDiscntAttr(BoradDiscntAttr v) {
		this.boradDiscntAttr = v;
	}

	public String getBoradDiscntId() {
		return this.boradDiscntId;
	}

	public void setBoradDiscntId(String v) {
		this.boradDiscntId = v;
	}

}
