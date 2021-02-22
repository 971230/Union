package zte.params.supplier.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.Supplier;

import params.ZteResponse;

public class SupplierGetResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="商家信息",type="String",isNecessary="N",desc="supplier：商家信息")
	private Supplier supplier;

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
}
