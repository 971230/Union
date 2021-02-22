package zte.net.ecsord.params.busiopen.ordinfo.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 配送信息
 * 
 */
public class PostInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "物流公司名称", type = "String", isNecessary = "N", desc = "物流公司名称")
	private String postName;

	@ZteSoftCommentAnnotationParam(name = "物流公司代码", type = "String", isNecessary = "N", desc = "物流公司代码")
	private String postId;

	@ZteSoftCommentAnnotationParam(name = "配送类型", type = "String", isNecessary = "N", desc = "配送类型")
	private String POST_TYPE;

	@ZteSoftCommentAnnotationParam(name = "物流单编号", type = "String", isNecessary = "N", desc = "物流单编号")
	private String POST_NO;

	@ZteSoftCommentAnnotationParam(name = "发货时间", type = "String", isNecessary = "N", desc = "发货时间")
	private String CARRY_TIME;

	@ZteSoftCommentAnnotationParam(name = "发货状态", type = "String", isNecessary = "N", desc = "发货状态")
	private String POST_STATUS;

	@ZteSoftCommentAnnotationParam(name = "地址关联", type = "String", isNecessary = "N", desc = "地址关联carryInfo.carryId")
	private String ORDER_CARRY_ID;

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getPOST_TYPE() {
		return POST_TYPE;
	}

	public void setPOST_TYPE(String pOST_TYPE) {
		POST_TYPE = pOST_TYPE;
	}

	public String getPOST_NO() {
		return POST_NO;
	}

	public void setPOST_NO(String pOST_NO) {
		POST_NO = pOST_NO;
	}

	public String getCARRY_TIME() {
		return CARRY_TIME;
	}

	public void setCARRY_TIME(String cARRY_TIME) {
		CARRY_TIME = cARRY_TIME;
	}

	public String getPOST_STATUS() {
		return POST_STATUS;
	}

	public void setPOST_STATUS(String pOST_STATUS) {
		POST_STATUS = pOST_STATUS;
	}

	public String getORDER_CARRY_ID() {
		return ORDER_CARRY_ID;
	}

	public void setORDER_CARRY_ID(String oRDER_CARRY_ID) {
		ORDER_CARRY_ID = oRDER_CARRY_ID;
	}

}
