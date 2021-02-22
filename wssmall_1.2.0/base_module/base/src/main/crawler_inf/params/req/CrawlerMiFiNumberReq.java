package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class CrawlerMiFiNumberReq extends ZteRequest{
	
    @ZteSoftCommentAnnotationParam(name="总商订单ID（长订单号）",type="String",isNecessary="Y",desc="总商订单ID（长订单号）")
	private String orderId;
    @ZteSoftCommentAnnotationParam(name="总商订单编号(短订单号对应云订单系统外部订单号)",type="String",isNecessary="Y",desc="总商订单编号(短订单号对应云订单系统外部订单号)")
  	private String orderNo;
    @ZteSoftCommentAnnotationParam(name="上网卡号码",type="String",isNecessary="Y",desc="上网卡号码")
	private String netCardNum;
    
    
	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZteCrawlerOpenService.checkMiFiNumber";
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

	public String getNetCardNum() {
		return netCardNum;
	}

	public void setNetCardNum(String netCardNum) {
		this.netCardNum = netCardNum;
	}
	
}
