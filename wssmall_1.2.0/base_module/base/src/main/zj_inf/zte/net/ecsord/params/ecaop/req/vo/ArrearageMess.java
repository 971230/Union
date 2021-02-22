package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ArrearageMess implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8785692653955633128L;

	@ZteSoftCommentAnnotationParam(name = "欠费号码", type = "String", isNecessary = "Y", desc = "serialNumber：欠费号码")
	private String serialNumber;
	
	@ZteSoftCommentAnnotationParam(name = "欠费金额", type = "String", isNecessary = "Y", desc = "arrearageFee：欠费金额（单位：分）")
	private String arrearageFee;
	
	
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getArrearageFee() {
		return arrearageFee;
	}

	public void setArrearageFee(String arrearageFee) {
		this.arrearageFee = arrearageFee;
	}

}
