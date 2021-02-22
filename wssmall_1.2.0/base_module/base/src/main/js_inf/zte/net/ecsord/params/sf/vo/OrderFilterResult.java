package zte.net.ecsord.params.sf.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderFilterResult implements Serializable{
	
	private static final long serialVersionUID = -6702611608963662702L;

	@ZteSoftCommentAnnotationParam(name="订单号",type="String",isNecessary="Y",desc="订单号，如果需要顺丰提供人工筛单，需要提供此项值")
	private String orderid;
	
	@ZteSoftCommentAnnotationParam(name="运单号",type="String",isNecessary="Y",desc="运单号  注：不可收派时，无该字段")
	private String mailno;
	
	@ZteSoftCommentAnnotationParam(name="筛单结果",type="String",isNecessary="Y",desc="筛单结果：   2-可收派   3-不可以收派")
	private Integer filterResult;
	
	@ZteSoftCommentAnnotationParam(name="目的地代码",type="String",isNecessary="Y",desc="目的地代码,如果可收派，此项不能为空")
	private String destCode;
	
	@ZteSoftCommentAnnotationParam(name="原寄地代码",type="String",isNecessary="Y",desc="原寄地代码（目前为空）")
	private String origincode;
	
	@ZteSoftCommentAnnotationParam(name="签回单号",type="String",isNecessary="Y",desc="签回单号，当筛单结果为可到，且下单时申请生成签回单号，则会推送该属性；注：不可收派或未申请签回单时，无该字段")
	private String returnTrackingNo;
	
	@ZteSoftCommentAnnotationParam(name="备注",type="String",isNecessary="Y",desc="1-收方超范围，2-派方超范围，3-其他原因注：在可收派时，无该字段")
	private String remark;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getMailno() {
		return mailno;
	}

	public void setMailno(String mailno) {
		this.mailno = mailno;
	}

	public Integer getFilterResult() {
		return filterResult;
	}

	public void setFilterResult(Integer filterResult) {
		this.filterResult = filterResult;
	}

	public String getDestCode() {
		return destCode;
	}

	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}

	public String getOrigincode() {
		return origincode;
	}

	public void setOrigincode(String origincode) {
		this.origincode = origincode;
	}

	public String getReturnTrackingNo() {
		return returnTrackingNo;
	}

	public void setReturnTrackingNo(String returnTrackingNo) {
		this.returnTrackingNo = returnTrackingNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
