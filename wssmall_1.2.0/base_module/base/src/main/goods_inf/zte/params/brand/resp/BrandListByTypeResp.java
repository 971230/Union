package zte.params.brand.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Brand;

import params.ZteResponse;

public class BrandListByTypeResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="品牌列表",type="List",isNecessary="N",desc="品牌列表。")
	private List<Brand> brandList;

	public List<Brand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}
}
