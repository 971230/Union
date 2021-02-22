package zte.net.iservice.params.goodsOrg.resp;


import params.ZteResponse;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.GoodsOrg;
/**
 * 
 * @author deng.yx
 * 发布组织返回实体
 *
 */
public class GoodsOrgResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="发布组织信息", type="GoodsOrg", isNecessary="N", desc="发布组织信息")
	private GoodsOrg goodsOrg;

	public GoodsOrg getGoodsOrg() {
		return goodsOrg;
	}

	public void setGoodsOrg(GoodsOrg goodsOrg) {
		this.goodsOrg = goodsOrg;
	}




	
}
