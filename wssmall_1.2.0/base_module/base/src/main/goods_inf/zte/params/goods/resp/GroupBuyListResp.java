package zte.params.goods.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class GroupBuyListResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="团购信息列表",type="List",isNecessary="N",desc="list：团购信息列表。")
	private List list;

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
}
