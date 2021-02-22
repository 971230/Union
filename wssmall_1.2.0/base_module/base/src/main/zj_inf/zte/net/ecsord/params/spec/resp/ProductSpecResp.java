package zte.net.ecsord.params.spec.resp;

import java.util.Map;

import params.ZteResponse;

public class ProductSpecResp extends ZteResponse {

	private Map productSpecMap;

	public Map getProductSpecMap() {
		return productSpecMap;
	}

	public void setProductSpecMap(Map productSpecMap) {
		this.productSpecMap = productSpecMap;
	}
}
