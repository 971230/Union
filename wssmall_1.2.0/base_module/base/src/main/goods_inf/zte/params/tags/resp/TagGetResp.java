package zte.params.tags.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Tag;

import params.ZteResponse;

public class TagGetResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="商品标签",type="Tag",isNecessary="Y",desc="tag：商品标签",hasChild=true)
	private Tag tag;

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
}
