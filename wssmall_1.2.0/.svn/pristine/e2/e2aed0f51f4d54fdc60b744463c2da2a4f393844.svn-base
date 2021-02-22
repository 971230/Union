package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;

import params.ZteRequest;
import params.resp.ShopMappingResp;

/**
 * 商店信息入参
 * @author hu.yi
 * @date 2013.12.25
 */
public class ShopMappingReq extends ZteRequest<ShopMappingResp>{

	private String shopId;

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(shopId)){
			throw new ApiRuleException("-1", "店铺id不能为空！");
		}
		
	}

	@Override
	public String getApiMethodName() {
		return "partnerServ.getUserInfoByShopCode";
	}
}
