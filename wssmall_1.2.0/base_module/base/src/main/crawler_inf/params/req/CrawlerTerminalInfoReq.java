package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class CrawlerTerminalInfoReq extends ZteRequest{
	
    @ZteSoftCommentAnnotationParam(name="总商订单ID（长订单号）",type="String",isNecessary="Y",desc="总商订单ID（长订单号）")
	private String orderId;
    @ZteSoftCommentAnnotationParam(name="总商订单编号(短订单号对应云订单系统外部订单号)",type="String",isNecessary="Y",desc="总商订单编号(短订单号对应云订单系统外部订单号)")
  	private String orderNo;
    @ZteSoftCommentAnnotationParam(name="总商模板ID",type="String",isNecessary="Y",desc="总商模板ID")
	private String tmplId;
    @ZteSoftCommentAnnotationParam(name="总商终端串号",type="String",isNecessary="Y",desc="总商终端串号")
	private String imeiCode;
    @ZteSoftCommentAnnotationParam(name="总商终端调拨标识 (1：标识调拨否则不调拨)",type="String",isNecessary="N",desc="总商终端调拨标识 (1：标识调拨否则不调拨)")
    private String allocationFlag;
    
	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZteCrawlerOpenService.checkTerminalInfo";
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTmplId() {
		return tmplId;
	}

	public void setTmplId(String tmplId) {
		this.tmplId = tmplId;
	}

	public String getImeiCode() {
		return imeiCode;
	}

	public void setImeiCode(String imeiCode) {
		this.imeiCode = imeiCode;
	}

	public String getAllocationFlag() {
		return allocationFlag;
	}

	public void setAllocationFlag(String allocationFlag) {
		this.allocationFlag = allocationFlag;
	}

	
}
