package zte.net.ecsord.params.wms.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


/**
 * 通知订单状态到WMS
 * 订单信息对象
 */
public class NotifyStatusToWMSOrderInfoVo implements Serializable{

	private static final long serialVersionUID = -6244477929869366267L;
	
	@ZteSoftCommentAnnotationParam(name="内部订单Id",type="String",isNecessary="Y",desc="内部订单Id")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name="处理状态",type="String",isNecessary="Y",desc="处理状态")
	private String status;
	@ZteSoftCommentAnnotationParam(name="处理结果描述",type="String",isNecessary="Y",desc="处理结果描述")
	private String desc;
	@ZteSoftCommentAnnotationParam(name="状态变更时间",type="String",isNecessary="Y",desc="状态变更时间（格式：yyyy-mm-dd HH:mi:ss）")
	private String statusUpdateTime;
	@ZteSoftCommentAnnotationParam(name="预留字段1",type="String",isNecessary="N",desc="预留字段1")
	private String userDef1;
	@ZteSoftCommentAnnotationParam(name="预留字段2",type="String",isNecessary="N",desc="预留字段2")
	private String userDef2;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStatusUpdateTime() {
		return statusUpdateTime;
	}

	public void setStatusUpdateTime(String statusUpdateTime) {
		this.statusUpdateTime = statusUpdateTime;
	}

	public String getUserDef1() {
		return userDef1;
	}

	public void setUserDef1(String userDef1) {
		this.userDef1 = userDef1;
	}

	public String getUserDef2() {
		return userDef2;
	}

	public void setUserDef2(String userDef2) {
		this.userDef2 = userDef2;
	}
}
