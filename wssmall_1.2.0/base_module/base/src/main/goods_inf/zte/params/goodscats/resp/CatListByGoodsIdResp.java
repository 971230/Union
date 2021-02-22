package zte.params.goodscats.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class CatListByGoodsIdResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="分类列表",type="List",isNecessary="N",desc="cats：分类列表。")
	private List cats;

	public List getCats() {
		return cats;
	}

	public void setCats(List cats) {
		this.cats = cats;
	}
}
