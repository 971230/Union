package zte.net.ecsord.params.pub.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class DictRelationListResp extends ZteResponse{
	@ZteSoftCommentAnnotationParam(name="订单外部状态属性列表",type="List",isNecessary="Y",desc="attrList：订单外部状态属性列表")
	public List attrList;

	public List getAttrList() {
		return attrList;
	}

	public void setAttrList(List attrList) {
		this.attrList = attrList;
	}
}
