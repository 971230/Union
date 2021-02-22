package zte.params.goodscats.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Cat;

import params.ZteResponse;

public class CatGetResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="商品分类",type="String",isNecessary="Y",desc="cat：商品分类")
	private Cat cat;

	public Cat getCat() {
		return cat;
	}

	public void setCat(Cat cat) {
		this.cat = cat;
	}
}
