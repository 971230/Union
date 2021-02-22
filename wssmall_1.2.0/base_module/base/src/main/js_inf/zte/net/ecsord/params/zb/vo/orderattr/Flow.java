package zte.net.ecsord.params.zb.vo.orderattr;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Flow implements Serializable{

	@ZteSoftCommentAnnotationParam(name="操作模式",type="String",isNecessary="N",desc="OperMode：AUTO：自动化仓库 MAN：地市人工 LIVE：现场生产 EXP：物流生产")
	private String OperMode;

	@ZteSoftCommentAnnotationParam(name="定制流程",type="String",isNecessary="N",desc="OrderFlow：定制流程：P：拣货 O：开户 W：写卡 D：发货 C：归档   P-O-W-D-C：拣货+开户+写卡+发货+归档 P-O-D-C：拣货+开户+发货+归档 P-D-C：拣货+发货+归档 （例如：配件、裸终端） O-C：开户+归档（例如：老用户） P-O-W-C：拣货+开户+写卡+归档   （例如：上门自提）")
	private String OrderFlow;
	
	
	//带notNeedReq前缀的在生成报文过程中将会过滤掉
	private String notNeedReqStrOrderId;
	
	public String getOperMode() {
		return OperMode;
	}

	public void setOperMode(String operMode) {
		OperMode = operMode;
	}

	public String getOrderFlow() {
		return OrderFlow;
	}

	public void setOrderFlow(String orderFlow) {
		OrderFlow = orderFlow;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	
}
