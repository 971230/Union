package zte.net.ecsord.params.zb.vo.orderattr;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class SubProd implements Serializable{

	@ZteSoftCommentAnnotationParam(name="附加产品编码",type="String",isNecessary="Y",desc="SubProCode：附加产品编码")
	private String SubProCode;

	@ZteSoftCommentAnnotationParam(name="附加产品名称",type="String",isNecessary="Y",desc="SubProName：附加产品名称")
	private String SubProName;	

	@ZteSoftCommentAnnotationParam(name="附加产品说明",type="String",isNecessary="Y",desc="SubProDesc：附加产品说明")
	private String SubProDesc;	

	@ZteSoftCommentAnnotationParam(name="附加产品参数",type="String",isNecessary="N",desc="SubProPara：附加产品参数")
	private String SubProPara;

	public String getSubProCode() {
		return SubProCode;
	}

	public void setSubProCode(String subProCode) {
		SubProCode = subProCode;
	}

	public String getSubProName() {
		return SubProName;
	}

	public void setSubProName(String subProName) {
		SubProName = subProName;
	}

	public String getSubProDesc() {
		return SubProDesc;
	}

	public void setSubProDesc(String subProDesc) {
		SubProDesc = subProDesc;
	}

	public String getSubProPara() {
		return SubProPara;
	}

	public void setSubProPara(String subProPara) {
		SubProPara = subProPara;
	}	
	
}
