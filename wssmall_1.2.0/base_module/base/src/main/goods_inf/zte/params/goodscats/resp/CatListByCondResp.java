package zte.params.goodscats.resp;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class CatListByCondResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="分类列表",type="List",isNecessary="N",desc="cats：分类列表。")
	private List<Map<String, String>> cats;

	public List<Map<String, String>> getCats() {
		return cats;
	}

	public void setCats(List<Map<String, String>> cats) {
		this.cats = cats;
	}
}
