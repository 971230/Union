package zte.params.goodscats.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Cat;

import params.ZteResponse;

public class CatHotListResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="热词分类列表",type="List",isNecessary="N",desc="hotCatList：热词分类列表")
	private List<Cat> hotCatList;

	public List<Cat> getHotCatList() {
		return hotCatList;
	}

	public void setHotCatList(List<Cat> hotCatList) {
		this.hotCatList = hotCatList;
	}
}
