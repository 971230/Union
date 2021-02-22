package zte.params.goodscats.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

/**
 * @title 返回商品分类列表
 * @author zou.qinghua
 *
 */
public class ChildCatsListResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="商品分类列表",type="List",isNecessary="Y",desc="List<Cat> goodsCats：商品分类列表 注Cat中含 分类商品列表catGoodsList")
	private List goodsCats;

	public List getGoodsCats() {
		return goodsCats;
	}

	public void setGoodsCats(List goodsCats) {
		this.goodsCats = goodsCats;
	}
}
