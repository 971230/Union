package zte.net.ecsord.params.ecaop.resp.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ArrearageMessVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -805688868139008592L;
	@ZteSoftCommentAnnotationParam(name = "欠费号码", type = "String", isNecessary = "N", desc = "ArrearageNum")
	private String ArrearageNum;
	@ZteSoftCommentAnnotationParam(name = "欠费金额（单位：厘）", type = "String", isNecessary = "N", desc = "ArrearageFee")
	private String ArrearageFee ;
	public String getArrearageNum() {
		return ArrearageNum;
	}
	public void setArrearageNum(String arrearageNum) {
		ArrearageNum = arrearageNum;
	}
	public String getArrearageFee() {
		return ArrearageFee;
	}
	public void setArrearageFee(String arrearageFee) {
		ArrearageFee = arrearageFee;
	}
	
}
