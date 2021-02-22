package zte.net.ecsord.params.zb.vo.orderattr;

import java.io.Serializable;

import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Operator implements Serializable{

	@ZteSoftCommentAnnotationParam(name="总部商城操作员编码",type="String",isNecessary="N",desc="OperatorCode：总部商城操作员编码")
	private String OperatorCode;

	@ZteSoftCommentAnnotationParam(name="总部商城操作员名称",type="String",isNecessary="N",desc="OperatorName：总部商城操作员名称")
	private String OperatorName;
	@ZteSoftCommentAnnotationParam(name="商城订单编号",type="String",isNecessary="Y",desc="OrderId：商城订单编号")
	private String OrderId;
	public String getOperatorCode() {
		return OperatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		OperatorCode = operatorCode;
	}
	public String getOperatorName() {
		return OperatorName;
	}
	public void setOperatorName(String operatorName) {
		OperatorName = operatorName;
	}
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	
}
