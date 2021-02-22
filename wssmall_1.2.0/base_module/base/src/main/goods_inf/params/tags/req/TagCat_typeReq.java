package params.tags.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class TagCat_typeReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name = "标签类型", type = "String", isNecessary = "Y", desc = "Tag 字段。")
	private String cat_type;

	public String getCat_type() {
		return cat_type;
	}

	public void setCat_type(String cat_type) {
		this.cat_type = cat_type;
	}

	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.tag.cat_type";
	}
}
