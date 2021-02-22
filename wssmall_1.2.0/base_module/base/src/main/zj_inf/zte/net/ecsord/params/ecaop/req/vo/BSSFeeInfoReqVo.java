package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @version bss克隆产品
 *
 */
public class BSSFeeInfoReqVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4672040844388601533L;
	@ZteSoftCommentAnnotationParam(name="收费项编码",type="String",isNecessary="Y",desc="feeId：收费项编码，以省分现有编码为准")
	private String FeeID;	
	@ZteSoftCommentAnnotationParam(name="收费项科目",type="String",isNecessary="Y",desc="feeCategory：收费项科目")
	private String FeeCategory;		
	@ZteSoftCommentAnnotationParam(name="应收费用正整数",type="String",isNecessary="Y",desc="origFee：应收费用正整数，单位：厘")
	private String OrigFee;		
	@ZteSoftCommentAnnotationParam(name="减免金额",type="String",isNecessary="Y",desc="reliefFee：减免金额，单位：厘")
	private String ReliefFee;		
	@ZteSoftCommentAnnotationParam(name="减免原因",type="String",isNecessary="Y",desc="reliefResult：减免原因")
	private String ReliefResult;		
	@ZteSoftCommentAnnotationParam(name="实收金额",type="String",isNecessary="Y",desc="realFee：实收金额，单位：厘")
	private String RealFee;
	public String getFeeID() {
		return FeeID;
	}
	public void setFeeID(String feeID) {
		FeeID = feeID;
	}
	public String getFeeCategory() {
		return FeeCategory;
	}
	public void setFeeCategory(String feeCategory) {
		FeeCategory = feeCategory;
	}
	public String getOrigFee() {
		return OrigFee;
	}
	public void setOrigFee(String origFee) {
		OrigFee = origFee;
	}
	public String getReliefFee() {
		return ReliefFee;
	}
	public void setReliefFee(String reliefFee) {
		ReliefFee = reliefFee;
	}
	public String getReliefResult() {
		return ReliefResult;
	}
	public void setReliefResult(String reliefResult) {
		ReliefResult = reliefResult;
	}
	public String getRealFee() {
		return RealFee;
	}
	public void setRealFee(String realFee) {
		RealFee = realFee;
	}
		
	
}
