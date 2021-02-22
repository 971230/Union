package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app;

import java.util.*;
import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class BindInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "冰淇淋融合套餐绑定号码", type = "String", isNecessary = "Y", desc = "冰淇淋融合套餐绑定号码")
	private String bindSerialNumber;

	@ZteSoftCommentAnnotationParam(name = "0：是指绑定的cbss手机；", type = "String", isNecessary = "Y", desc = "0：是指绑定的cbss手机；")
	private String bindSrc;

	@ZteSoftCommentAnnotationParam(name = "关系类型", type = "String", isNecessary = "Y", desc = "关系类型")
	private String bindType;

	@ZteSoftCommentAnnotationParam(name = "用户ID", type = "String", isNecessary = "Y", desc = "用户ID")
	private String bindUserId;

	public String getBindSerialNumber() {
		return this.bindSerialNumber;
	}

	public void setBindSerialNumber(String v) {
		this.bindSerialNumber = v;
	}

	public String getBindSrc() {
		return this.bindSrc;
	}

	public void setBindSrc(String v) {
		this.bindSrc = v;
	}

	public String getBindType() {
		return this.bindType;
	}

	public void setBindType(String v) {
		this.bindType = v;
	}

	public String getBindUserId() {
		return this.bindUserId;
	}

	public void setBindUserId(String v) {
		this.bindUserId = v;
	}

}
