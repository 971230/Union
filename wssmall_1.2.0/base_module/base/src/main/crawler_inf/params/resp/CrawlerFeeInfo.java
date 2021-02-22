package params.resp;

import java.io.Serializable;

/*****************************************
 * 
 * @author lzg
 * 总商返回的费用明细
 */
public class CrawlerFeeInfo  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4551384904884388228L;
	//收费项编码
	private String feeID = "";
	//收费项描述
	private String feeDes = "";
	//应收金额，单位为元
	private String origFee = "";
	//减免金额，单位为元(注：可以编辑)
	private String reliefFee = "";
	//减免原因(注：可以编辑)
	private String reliefResult = "";
	//实收金额，单位为元
	private String realFee = "";
	//费用类型
	private String feeCategory;
	//最大减免金额 单位为元(修改减免金额不能超过最大减免金额)（如果是北六订单最大减免金额是不做校验的）
	private String maxRelief;
	
	public String getFeeID() {
		return feeID;
	}
	public void setFeeID(String feeID) {
		this.feeID = feeID;
	}
	public String getFeeDes() {
		return feeDes;
	}
	public void setFeeDes(String feeDes) {
		this.feeDes = feeDes;
	}
	public String getOrigFee() {
		return origFee;
	}
	public void setOrigFee(String origFee) {
		this.origFee = origFee;
	}
	public String getReliefFee() {
		return reliefFee;
	}
	public void setReliefFee(String reliefFee) {
		this.reliefFee = reliefFee;
	}
	public String getReliefResult() {
		return reliefResult;
	}
	public void setReliefResult(String reliefResult) {
		this.reliefResult = reliefResult;
	}
	public String getRealFee() {
		return realFee;
	}
	public void setRealFee(String realFee) {
		this.realFee = realFee;
	}
	public String getFeeCategory() {
		return feeCategory;
	}
	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
	}
	public String getMaxRelief() {
		return maxRelief;
	}
	public void setMaxRelief(String maxRelief) {
		this.maxRelief = maxRelief;
	}
}
