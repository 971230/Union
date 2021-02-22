/**
 * 
 */
package zte.net.ecsord.params.bss.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * BSS激活响应
 * @author duan.shaochu
 *
 */
public class BSSActivateOperRsp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String RespCode;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String RespDesc;
	@ZteSoftCommentAnnotationParam(name = "备用1", type = "String", isNecessary = "Y", desc = "备用1")
	private String Param_Value1;
	@ZteSoftCommentAnnotationParam(name = "备用2", type = "String", isNecessary = "Y", desc = "备用2")
	private String Param_Value2;
	@ZteSoftCommentAnnotationParam(name = "备用3", type = "String", isNecessary = "Y", desc = "备用3")
	private String Param_Value3;
	@ZteSoftCommentAnnotationParam(name = "备用4", type = "String", isNecessary = "Y", desc = "备用4")
	private String Param_Value4;
	
	@ZteSoftCommentAnnotationParam(name = "备用5", type = "String", isNecessary = "Y", desc = "备用5")
	private String Param_Value5;

	public String getRespCode() {
		return RespCode;
	}

	public void setRespCode(String respCode) {
		RespCode = respCode;
	}

	public String getRespDesc() {
		return RespDesc;
	}

	public void setRespDesc(String respDesc) {
		RespDesc = respDesc;
	}

	public String getParam_Value1() {
		return Param_Value1;
	}

	public void setParam_Value1(String param_Value1) {
		Param_Value1 = param_Value1;
	}

	public String getParam_Value2() {
		return Param_Value2;
	}

	public void setParam_Value2(String param_Value2) {
		Param_Value2 = param_Value2;
	}

	public String getParam_Value3() {
		return Param_Value3;
	}

	public void setParam_Value3(String param_Value3) {
		Param_Value3 = param_Value3;
	}

	public String getParam_Value4() {
		return Param_Value4;
	}

	public void setParam_Value4(String param_Value4) {
		Param_Value4 = param_Value4;
	}

	public String getParam_Value5() {
		return Param_Value5;
	}

	public void setParam_Value5(String param_Value5) {
		Param_Value5 = param_Value5;
	}
	

}
