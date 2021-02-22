package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.DlyTypePriceListResp;

public class DlyTypePriceListReq extends ZteRequest<DlyTypePriceListResp> {

	@ZteSoftCommentAnnotationParam(name="商品金额",type="String",isNecessary="Y",desc="商品金额")
	private double goodsprice;
	@ZteSoftCommentAnnotationParam(name="商品重量",type="String",isNecessary="Y",desc="商品重量")
	private double weight;
	@ZteSoftCommentAnnotationParam(name="区、县ID",type="String",isNecessary="Y",desc="区、县ID")
	private String regionid;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.dlyTypeAddressService.dlytype.pricelist";
	}

	public double getGoodsprice() {
		return goodsprice;
	}

	public void setGoodsprice(double goodsprice) {
		this.goodsprice = goodsprice;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

}
