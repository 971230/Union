package zte.net.ecsord.params.wyg.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 
 * @author duan.shaochu
 * 附属业务列表
 *
 */
public class StatusSyncToWYGAttchList implements Serializable {

	@ZteSoftCommentAnnotationParam(name="货品sku",type="String",isNecessary="Y",desc="sku：货品sku")
	private String ATTCH_SKU;
	@ZteSoftCommentAnnotationParam(name="货品名称",type="String",isNecessary="Y",desc="sku：货品名称")
	private String ATTCH_NAME;
	@ZteSoftCommentAnnotationParam(name="结果编码",type="String",isNecessary="Y",desc="sku：结果编码")
	private String RESULT_CODE;
	@ZteSoftCommentAnnotationParam(name="错误描述",type="String",isNecessary="Y",desc="sku：错误描述")
	private String RESULT_DES;
	public String getATTCH_SKU() {
		return ATTCH_SKU;
	}
	public void setATTCH_SKU(String aTTCH_SKU) {
		ATTCH_SKU = aTTCH_SKU;
	}
	public String getATTCH_NAME() {
		return ATTCH_NAME;
	}
	public void setATTCH_NAME(String aTTCH_NAME) {
		ATTCH_NAME = aTTCH_NAME;
	}
	public String getRESULT_CODE() {
		return RESULT_CODE;
	}
	public void setRESULT_CODE(String rESULT_CODE) {
		RESULT_CODE = rESULT_CODE;
	}
	public String getRESULT_DES() {
		return RESULT_DES;
	}
	public void setRESULT_DES(String rESULT_DES) {
		RESULT_DES = rESULT_DES;
	}
	
	
}
