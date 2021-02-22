package zte.net.ecsord.params.pub.req;

import zte.net.ecsord.params.busi.req.AttrPackageSubProdBusiRequest;

import com.ztesoft.api.ApiRuleException;

public class OrderSubPackageList extends AttrPackageSubProdBusiRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9010559211360504918L;
	private String subProdCode;///副驾产品编码
	private String subProdName;//附加产品名称
	

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "";
	}

	public String getSubProdName() {
		return subProdName;
	}

	public void setSubProdName(String subProdName) {
		this.subProdName = subProdName;
	}

	public String getSubProdCode() {
		return subProdCode;
	}

	public void setSubProdCode(String subProdCode) {
		this.subProdCode = subProdCode;
	}

}
