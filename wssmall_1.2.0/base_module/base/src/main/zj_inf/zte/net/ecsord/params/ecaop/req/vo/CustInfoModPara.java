/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author song.qi
 */
public class CustInfoModPara implements Serializable {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "保留字段ID", type = "String", isNecessary = "Y", desc = "String(20)	保留字段ID")
	private String paraId;
	@ZteSoftCommentAnnotationParam(name = "保留字段值", type = "String", isNecessary = "Y", desc = "String(60)	保留字段值")
	private String paraValue;

	public String getParaId() {
		return paraId;
	}

	public void setParaId(String paraId) {
		this.paraId = paraId;
	}

	public String getParaValue() {
		return paraValue;
	}

	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}

}
