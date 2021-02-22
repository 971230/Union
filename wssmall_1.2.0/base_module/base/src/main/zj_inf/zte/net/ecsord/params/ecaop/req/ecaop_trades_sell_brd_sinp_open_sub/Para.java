package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_sub;

import java.util.*;
import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Para implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "保留字段ID", type = "String", isNecessary = "Y", desc = "保留字段ID")
	private String paraId;
	
	@ZteSoftCommentAnnotationParam(name="保留字段值",type="String",isNecessary="Y",desc="paraValue：保留字段值")
	private String paraValue;

	public String getParaId() {
		return this.paraId;
	}

	public void setParaId(String v) {
		this.paraId = v;
	}

	public String getParaValue() {
		return paraValue;
	}

	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}

}
