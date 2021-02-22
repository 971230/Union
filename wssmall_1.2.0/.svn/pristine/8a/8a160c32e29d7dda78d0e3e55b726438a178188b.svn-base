package zte.net.ecsord.params.zb.resp;

import java.util.List;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;
import zte.net.ecsord.params.zb.vo.Order;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class StateSynchronizationToSystemResponse extends ZteBusiResponse {
	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="ActiveNo：访问流水")
	private String ActiveNo;
	
	@ZteSoftCommentAnnotationParam(name="应答编码",type="String",isNecessary="Y",desc="RespCode：应答编码")
	private String RespCode;
	
	@ZteSoftCommentAnnotationParam(name="变更失败的订单信息",type="String",isNecessary="Y",desc="Orders：变更失败的订单信息")
	private List<Order> Orders;
	
	public String getActiveNo() {
		return ActiveNo;
	}
	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}
	public String getRespCode() {
		return RespCode;
	}
	public void setRespCode(String respCode) {
		RespCode = respCode;
	}
	
}
