package params.req;

import params.ZteRequest;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderSubmitReq  extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="内部订单号",type="String",isNecessary="Y",desc="内部订单号")
	private String notNeedReqStrOrderId;
    @ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="订单编号")
    private String orderId;
	
    @ZteSoftCommentAnnotationParam(name="商品模板id",type="String",isNecessary="Y",desc="商品模板id")
    private String tmplId;
	
    @ZteSoftCommentAnnotationParam(name="",type="String",isNecessary="Y",desc="")
    private String goodInstId;
	
    @ZteSoftCommentAnnotationParam(name="",type="String",isNecessary="Y",desc="")
    private String userTag;
	
    @ZteSoftCommentAnnotationParam(name="发票号",type="String",isNecessary="Y",desc="发票号")
    private String invoiceNo;
	
    @ZteSoftCommentAnnotationParam(name="USIM卡号",type="String",isNecessary="Y",desc="USIM卡号")
    private String simNo;
	
  /*  @ZteSoftCommentAnnotationParam(name="合计",type="String",isNecessary="Y",desc="合计")
    private String origTotalFee;
	
    @ZteSoftCommentAnnotationParam(name="应收卡费",type="String",isNecessary="Y",desc="应收卡费")
    private String usimRealFee;
	
    @ZteSoftCommentAnnotationParam(name="应收多缴预存款",type="String",isNecessary="Y",desc="应收多缴预存款")
    private String otherOrigFee;
	
    @ZteSoftCommentAnnotationParam(name="实收多缴预存款",type="String",isNecessary="Y",desc="实收多缴预存款")
    private String otherRealFee;*/
	
    @ZteSoftCommentAnnotationParam(name="手机号",type="String",isNecessary="Y",desc="手机号")
    private String preNum;
	
    @ZteSoftCommentAnnotationParam(name="是否成卡“1”：是 “0”",type="String",isNecessary="Y",desc="是否成卡“1”：是 “0”")
    private String isOldCard;
    
    @ZteSoftCommentAnnotationParam(name=" 是否是主副卡新带新且副卡带有号码的订单  1：是 0：不是",type="String",isNecessary="Y",desc=" 是否是主副卡新带新且副卡带有号码的订单  1：是 0：不是")
    private String isZFKNewOrder;

    @ZteSoftCommentAnnotationParam(name="费用信息",type="String",isNecessary="Y",desc="费用信息")
    private CrawlerFreeDesc freeDesc;

	@ZteSoftCommentAnnotationParam(name="总商订单编号(短订单号对应云订单系统外部订单号)",type="String",isNecessary="Y",desc="总商订单编号(短订单号对应云订单系统外部订单号)")
	private String orderNo;
    
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZteCrawlerOpenService.callSubmit";
	}

	
	public String getOrderNo() {
			return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTmplId() {
		return tmplId;
	}

	public void setTmplId(String tmplId) {
		this.tmplId = tmplId;
	}

	public String getGoodInstId() {
		return goodInstId;
	}

	public void setGoodInstId(String goodInstId) {
		this.goodInstId = goodInstId;
	}

	public String getUserTag() {
		userTag = "1";
		return userTag;
	}

	public void setUserTag(String userTag) {
		this.userTag = userTag;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getIsOldCard() {
		isOldCard = "0";
		return isOldCard;
	}

	public void setIsOldCard(String isOldCard) {
		this.isOldCard = isOldCard;
	}

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	

	public String getPreNum() {
		return preNum;
	}

	public void setPreNum(String preNum) {
		this.preNum = preNum;
	}

	public String getIsZFKNewOrder() {
		return isZFKNewOrder;
	}

	public void setIsZFKNewOrder(String isZFKNewOrder) {
		this.isZFKNewOrder = isZFKNewOrder;
	}

	

	public CrawlerFreeDesc getFreeDesc() {
		return freeDesc;
	}

	public void setFreeDesc(CrawlerFreeDesc freeDesc) {
		this.freeDesc = freeDesc;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	
    
}
