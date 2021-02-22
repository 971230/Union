package zte.params.goodscats.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class CatBrandListResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="分类列表",type="List",isNecessary="N",desc="listCat：分类列表，以及每个分类下相关品牌列表。")
	private List listCat;

	public List getListCat() {
		return listCat;
	}

	public void setListCat(List listCat) {
		this.listCat = listCat;
	}
}
