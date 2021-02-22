package zte.net.ecsord.params.busi.req;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteBusiRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.treeInst.RequestStoreExector;

@RequestBeanAnnontion(primary_keys="package_inst_id",table_name="es_attr_package",depency_keys="order_id",service_desc="总部可选包，新商城可选包信息")
public class AttrPackageBusiRequest extends ZteBusiRequest<ZteResponse>  implements IZteBusiRequestHander {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	private String package_inst_id;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String inst_id;
	@RequestFieldAnnontion(dname="package_code")
	private String PackageCode;
	@RequestFieldAnnontion(dname="package_name")
	private String PackageName;
	@RequestFieldAnnontion(dname="product_code")
	private String ProductCode;
	@RequestFieldAnnontion(dname="element_code")
	private String ElementCode;
	@RequestFieldAnnontion(dname="element_type")
	private String ElementType;
	@RequestFieldAnnontion(dname="element_name")
	private String ElementName;
	@RequestFieldAnnontion(dname="oper_type")
	private String OperType;
	@RequestFieldAnnontion(dname="change_type")
	private String ChageType;
	@RequestFieldAnnontion(dname="biz_type")
	private String BizType;
	
	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<AttrPackageBusiRequest> updateRequest = new ZteInstUpdateRequest<AttrPackageBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<AttrPackageBusiRequest>> resp = new QueryResponse<List<AttrPackageBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,new ArrayList<AttrPackageBusiRequest>());
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPackage_inst_id() {
		return package_inst_id;
	}

	public void setPackage_inst_id(String package_inst_id) {
		this.package_inst_id = package_inst_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getInst_id() {
		return inst_id;
	}

	public void setInst_id(String inst_id) {
		this.inst_id = inst_id;
	}

	public String getPackageCode() {
		return PackageCode;
	}

	public void setPackageCode(String packageCode) {
		PackageCode = packageCode;
	}

	public String getPackageName() {
		return PackageName;
	}

	public void setPackageName(String packageName) {
		PackageName = packageName;
	}

	public String getProductCode() {
		return ProductCode;
	}

	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}

	public String getElementCode() {
		return ElementCode;
	}

	public void setElementCode(String elementCode) {
		ElementCode = elementCode;
	}

	public String getElementType() {
		return ElementType;
	}

	public void setElementType(String elementType) {
		ElementType = elementType;
	}

	public String getElementName() {
		return ElementName;
	}

	public void setElementName(String elementName) {
		ElementName = elementName;
	}

	public String getOperType() {
		return OperType;
	}

	public void setOperType(String operType) {
		OperType = operType;
	}

	public String getChageType() {
		return ChageType;
	}

	public void setChageType(String chageType) {
		ChageType = chageType;
	}

	public String getBizType() {
		return BizType;
	}

	public void setBizType(String bizType) {
		BizType = bizType;
	}

}
