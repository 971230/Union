package params.resp;

import java.util.Map;

import params.ZteResponse;

/**
 * 商店信息出参实体
 * @author hu.yi
 * @date 2012.12.25
 */
public class ShopMappingResp extends ZteResponse{

	
	private Map shopMap;

	public Map getShopMap() {
		return shopMap;
	}

	public void setShopMap(Map shopMap) {
		this.shopMap = shopMap;
	}
}
