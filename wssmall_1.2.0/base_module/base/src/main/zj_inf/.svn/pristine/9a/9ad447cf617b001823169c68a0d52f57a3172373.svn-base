package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version 2015-05-05
 *
 */
public class FeeInfoReqVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name="收费项编码",type="String",isNecessary="Y",desc="feeId：收费项编码，以省分现有编码为准")
	private String feeId;	
	@ZteSoftCommentAnnotationParam(name="收费项科目",type="String",isNecessary="Y",desc="feeCategory：收费项科目")
	private String feeCategory;		
	@ZteSoftCommentAnnotationParam(name="收费项描述",type="String",isNecessary="Y",desc="feeDes：收费项描述")
	private String feeDes;		
	@ZteSoftCommentAnnotationParam(name="应收费用正整数",type="String",isNecessary="Y",desc="origFee：应收费用正整数，单位：厘")
	private String origFee;		
	@ZteSoftCommentAnnotationParam(name="减免金额",type="String",isNecessary="Y",desc="reliefFee：减免金额，单位：厘")
	private String reliefFee;		
	@ZteSoftCommentAnnotationParam(name="减免原因",type="String",isNecessary="Y",desc="reliefResult：减免原因")
	private String reliefResult;		
	@ZteSoftCommentAnnotationParam(name="实收金额",type="String",isNecessary="Y",desc="realFee：实收金额，单位：厘")
	private String realFee;
	
	public String getFeeId() {
		return feeId;
	}
	public void setFeeId(String feeId) {
		this.feeId = feeId;
	}
	public String getFeeCategory() {
		return feeCategory;
	}
	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
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
		if(reliefResult==""){
			this.reliefResult=null;
		}
	}
	public String getRealFee() {
		return realFee;
	}
	public void setRealFee(String realFee) {
		this.realFee = realFee;
	}		
	
}
