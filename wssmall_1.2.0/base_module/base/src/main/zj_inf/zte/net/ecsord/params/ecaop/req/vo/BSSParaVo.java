/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @version BSS克隆产品
 *
 */
public class BSSParaVo implements Serializable  {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "保留字段ID", type = "String", isNecessary = "Y", desc = "保留字段ID")
	private String ParaID;
	@ZteSoftCommentAnnotationParam(name = "保留字段值", type = "String", isNecessary = "Y", desc = "保留字段值")
	private String ParaValue;
	
	public String getParaID() {
		return ParaID;
	}
	public void setParaID(String ParaID) {
		this.ParaID = ParaID;
	}
	public String getParaValue() {
		return ParaValue;
	}
	public void setParaValue(String ParaValue) {
		this.ParaValue = ParaValue;
	}
	
}
