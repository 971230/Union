package zte.net.ecsord.params.sf.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;
import zte.net.ecsord.params.sf.vo.OrderInfo;
import zte.net.ecsord.params.sf.vo.SFBodyVO;

public class OrderSearchServiceResponse extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="订单信息列表 ",type="String",isNecessary="Y",desc="订单信息列表")
	private String service;
	
	@ZteSoftCommentAnnotationParam(name="返回代码 ",type="String",isNecessary="Y",desc="返回代码 ")
	private String Head;
	
	@ZteSoftCommentAnnotationParam(name="返回代码 ",type="String",isNecessary="Y",desc="返回代码 ")
	private SFBodyVO BodyJson;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getHead() {
		return Head;
	}

	public void setHead(String head) {
		Head = head;
	}

	public SFBodyVO getBodyJson() {
		return BodyJson;
	}

	public void setBodyJson(SFBodyVO bodyJson) {
		BodyJson = bodyJson;
	}

	
}
