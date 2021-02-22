package params.req;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class CrawlerWriteCardSucReq extends ZteRequest<ZteResponse>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2469592184769456789L;
	@ZteSoftCommentAnnotationParam(name="总商订单编号(短订单号对应云订单系统外部订单号)",type="String",isNecessary="Y",desc="总商订单编号(短订单号对应云订单系统外部订单号)")
	private String orderNo;
	@ZteSoftCommentAnnotationParam(name="总商订单ID",type="String",isNecessary="Y",desc="总商订单ID")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name="ICCID",type="String",isNecessary="Y",desc="ICCID")
	private String iccid;
	@ZteSoftCommentAnnotationParam(name="SIM卡号",type="String",isNecessary="Y",desc="SIM卡号")
	private String imsi;
	@ZteSoftCommentAnnotationParam(name="是否是老用户便捷换卡订单 1:是  0:不是",type="String",isNecessary="Y",desc="是否是老用户便捷换卡订单 1:是  0:不是")
	private String isCardChange;
	@ZteSoftCommentAnnotationParam(name=" 是否是主副卡新带新且副卡带有号码的订单  1：是 0：不是",type="String",isNecessary="Y",desc=" 是否是主副卡新带新且副卡带有号码的订单  1：是 0：不是")
	private String isZFKNewOrder;
	@ZteSoftCommentAnnotationParam(name="手机卡号，上网卡号",type="String",isNecessary="Y",desc="手机卡号，上网卡号")
	private String preNum;

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getIsCardChange() {
		return isCardChange;
	}

	public void setIsCardChange(String isCardChange) {
		this.isCardChange = isCardChange;
	}

	public String getIsZFKNewOrder() {
		return isZFKNewOrder;
	}

	public void setIsZFKNewOrder(String isZFKNewOrder) {
		this.isZFKNewOrder = isZFKNewOrder;
	}

	public String getPreNum() {
		return preNum;
	}

	public void setPreNum(String preNum) {
		this.preNum = preNum;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZteCrawlerOpenService.writeCardSuc";
	}
	
	

}
