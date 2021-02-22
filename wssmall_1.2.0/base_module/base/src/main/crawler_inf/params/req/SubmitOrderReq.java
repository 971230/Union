package params.req;

import params.ZteRequest;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class SubmitOrderReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="内部订单号",type="String",isNecessary="Y",desc="内部订单号")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="外部订单号")
	private String orderId;

	@ZteSoftCommentAnnotationParam(name="商品模板id",type="String",isNecessary="Y",desc="商品模板id")
	private String tmplId;

	@ZteSoftCommentAnnotationParam(name="",type="String",isNecessary="Y",desc="")
	private String goodsInstId;

	@ZteSoftCommentAnnotationParam(name="证件类型",type="String",isNecessary="Y",desc="证件类型")
	private String certType;

	@ZteSoftCommentAnnotationParam(name="证件号",type="String",isNecessary="Y",desc="证件号")
	private String certNum;

	@ZteSoftCommentAnnotationParam(name="开户商品类型(2G/3G/4G)",type="String",isNecessary="Y",desc="开户商品类型(2G/3G/4G)")
	private String goodsType;

	@ZteSoftCommentAnnotationParam(name="",type="String",isNecessary="Y",desc="")
	private String zfkNumList;

	@ZteSoftCommentAnnotationParam(name="终端串码",type="String",isNecessary="Y",desc="终端串码")
	private String manualImeiCode;

	@ZteSoftCommentAnnotationParam(name="上网卡号码、手机号码",type="String",isNecessary="Y",desc="上网卡号码、手机号码")
	private String manualNetCardNum;

	@ZteSoftCommentAnnotationParam(name="USIM卡号",type="String",isNecessary="Y",desc="USIM卡号")
	private String manualUsimCardNum;

	@ZteSoftCommentAnnotationParam(name="开户单号(BSS/ESS/CBSS)",type="String",isNecessary="Y",desc="开户单号(BSS/ESS/CBSS)")
	private String manualOrderNo;
	
	@ZteSoftCommentAnnotationParam(name="是否是主副卡新带新且副卡带有号码的订单  1：是 0：不是",type="String",isNecessary="Y",desc="是否是主副卡新带新且副卡带有号码的订单  1：是 0：不是")
	private String isZFKNewOrder;

	@ZteSoftCommentAnnotationParam(name="是否是主副卡新带新手动卡户的页面  1：是 0：不是",type="String",isNecessary="Y",desc="是否是主副卡新带新手动卡户的页面  1：是 0：不是")
	private String ismanual4zfkv;

	@ZteSoftCommentAnnotationParam(name="共享套餐标识",type="String",isNecessary="Y",desc="共享套餐标识")
	private String isCombineOrder;

	@ZteSoftCommentAnnotationParam(name="共享套餐手动开户页面的标识1：是 0：不是",type="String",isNecessary="Y",desc="共享套餐手动开户页面的标识1：是 0：不是")
	private String ismanual4Combine;
	
	@ZteSoftCommentAnnotationParam(name="总商订单编号(短订单号对应云订单系统外部订单号)",type="String",isNecessary="Y",desc="总商订单编号(短订单号对应云订单系统外部订单号)")
	private String orderNo;

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

	public String getGoodsInstId() {
		return goodsInstId;
	}

	public void setGoodsInstId(String goodsInstId) {
		this.goodsInstId = goodsInstId;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getZfkNumList() {
		return zfkNumList;
	}

	public void setZfkNumList(String zfkNumList) {
		this.zfkNumList = zfkNumList;
	}

	public String getManualImeiCode() {
		return manualImeiCode;
	}

	public void setManualImeiCode(String manualImeiCode) {
		this.manualImeiCode = manualImeiCode;
	}

	public String getManualNetCardNum() {
		return manualNetCardNum;
	}

	public void setManualNetCardNum(String manualNetCardNum) {
		this.manualNetCardNum = manualNetCardNum;
	}

	public String getManualUsimCardNum() {
		return manualUsimCardNum;
	}

	public void setManualUsimCardNum(String manualUsimCardNum) {
		this.manualUsimCardNum = manualUsimCardNum;
	}

	public String getManualOrderNo() {
		return manualOrderNo;
	}

	public void setManualOrderNo(String manualOrderNo) {
		this.manualOrderNo = manualOrderNo;
	}

	public String getIsZFKNewOrder() {
		return isZFKNewOrder;
	}

	public void setIsZFKNewOrder(String isZFKNewOrder) {
		this.isZFKNewOrder = isZFKNewOrder;
	}

	public String getIsmanual4zfkv() {
		return ismanual4zfkv;
	}

	public void setIsmanual4zfkv(String ismanual4zfkv) {
		this.ismanual4zfkv = ismanual4zfkv;
	}

	public String getIsCombineOrder() {
		return isCombineOrder;
	}

	public void setIsCombineOrder(String isCombineOrder) {
		this.isCombineOrder = isCombineOrder;
	}

	public String getIsmanual4Combine() {
		return ismanual4Combine;
	}

	public void setIsmanual4Combine(String ismanual4Combine) {
		this.ismanual4Combine = ismanual4Combine;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZteCrawlerOpenService.submitOrder";
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	
	
	
}