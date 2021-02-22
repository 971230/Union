package zte.params.goodscats.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

/**
 * @title 返回商品分类列表
 * @author zou.qinghua
 *
 */
public class CatsListResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="商品分类列表",type="List",isNecessary="Y",desc="goodsCats:商品分类列表")
	private List goodsCats;

	public List getGoodsCats() {
		return goodsCats;
	}

	public void setGoodsCats(List goodsCats) {
		this.goodsCats = goodsCats;
	}
}
