package zte.params.tags.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class PackageGetResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="商品包",type="String",isNecessary="Y",desc="商品包")
	private String package_id;

	public String getPackage_id() {
		return package_id;
	}

	public void setPackage_id(String package_id) {
		this.package_id = package_id;
	}
}
