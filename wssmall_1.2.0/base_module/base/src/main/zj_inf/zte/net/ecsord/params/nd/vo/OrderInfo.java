package zte.net.ecsord.params.nd.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderInfo implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7966577693519646861L;

	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="origOrderId：外部订单号")
	private String origOrderId;

	@ZteSoftCommentAnnotationParam(name="需要补寄的内容",type="String",isNecessary="Y",desc="reissueInfo：需要补寄的内容")
	private String reissueInfo;	

	@ZteSoftCommentAnnotationParam(name="商品信息",type="String",isNecessary="Y",desc="goodInfo：商品信息")
	private List<GoodInfo> goodInfo;

	@ZteSoftCommentAnnotationParam(name="物流信息",type="String",isNecessary="Y",desc="postInfo：物流信息")
	private List<PostInfo> postInfo;

	public String getOrigOrderId() {
		return origOrderId;
	}

	public void setOrigOrderId(String origOrderId) {
		this.origOrderId = origOrderId;
	}

	public String getReissueInfo() {
		return reissueInfo;
	}

	public void setReissueInfo(String reissueInfo) {
		this.reissueInfo = reissueInfo;
	}

	public List<GoodInfo> getGoodInfo() {
		return goodInfo;
	}

	public void setGoodInfo(List<GoodInfo> goodInfo) {
		this.goodInfo = goodInfo;
	}

	public List<PostInfo> getPostInfo() {
		return postInfo;
	}

	public void setPostInfo(List<PostInfo> postInfo) {
		this.postInfo = postInfo;
	}	
	
	
}
