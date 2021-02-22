package zte.net.ecsord.params.busi.req;

import java.util.ArrayList;
import java.util.List;

import params.ZteBusiRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.treeInst.RequestStoreExector;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * 
 * @author wu.i 
 * 订单数对象
 * 
 */
@RequestBeanAnnontion(primary_keys="attr_inst_id",table_name="es_attr_inst",depency_keys="order_id",service_desc="订单扩展信息，根据规格id区分商品、货品、支付、物流单属性值")
public class AttrInstBusiRequest extends ZteBusiRequest<ZteResponse>  implements IZteBusiRequestHander{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String attr_inst_id;
	@RequestFieldAnnontion()
	private String order_item_id;
	@RequestFieldAnnontion()
	private String inst_id;
	@RequestFieldAnnontion()
	private String attr_spec_id;
	@RequestFieldAnnontion()
	private String field_attr_id;
	@RequestFieldAnnontion()
	private String field_name;
	@RequestFieldAnnontion()
	private String filed_desc;
	@RequestFieldAnnontion()
	private String field_value;
	@RequestFieldAnnontion()
	private String field_value_desc;
	@RequestFieldAnnontion()
	private String modify_field_value;
	@RequestFieldAnnontion()
	private Integer sequ;
	@RequestFieldAnnontion()
	private String sec_field_value;
	@RequestFieldAnnontion()
	private String sec_filed_value_desc;
//	@RequestFieldAnnontion()
//	private String col1;
//	@RequestFieldAnnontion()
//	private String col2;
//	@RequestFieldAnnontion()
//	private String col3;
	@RequestFieldAnnontion()
	private String create_date;
	@RequestFieldAnnontion(need_insert="no")
	private String spec_is_null; //缓存规格数据，提高性能
	@RequestFieldAnnontion(need_insert="no")
	private String spec_is_edit; //缓存规格数据，提高性能
	
	@RequestFieldAnnontion(need_insert="no")
	private String t_table_name; //映射表名
	
	@RequestFieldAnnontion(need_insert="no")
	private String t_field_name; //映射字段名
	
	@RequestFieldAnnontion(need_insert="no")
	private String sub_attr_spec_type; //映射字段名,归属订单、商品、或者货品属性
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getAttr_inst_id() {
		return attr_inst_id;
	}

	public void setAttr_inst_id(String attr_inst_id) {
		this.attr_inst_id = attr_inst_id;
	}

	public String getOrder_item_id() {
		return order_item_id;
	}

	public void setOrder_item_id(String order_item_id) {
		this.order_item_id = order_item_id;
	}

	public String getInst_id() {
		return inst_id;
	}

	public void setInst_id(String inst_id) {
		this.inst_id = inst_id;
	}

	public String getAttr_spec_id() {
		return attr_spec_id;
	}

	public void setAttr_spec_id(String attr_spec_id) {
		this.attr_spec_id = attr_spec_id;
	}

	public String getField_attr_id() {
		return field_attr_id;
	}

	public void setField_attr_id(String field_attr_id) {
		this.field_attr_id = field_attr_id;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public String getFiled_desc() {
		return filed_desc;
	}

	public void setFiled_desc(String filed_desc) {
		this.filed_desc = filed_desc;
	}

	public String getField_value() {
		return field_value;
	}

	public void setField_value(String field_value) {
		this.field_value = field_value;
	}

	public String getField_value_desc() {
		return field_value_desc;
	}

	public void setField_value_desc(String field_value_desc) {
		this.field_value_desc = field_value_desc;
	}

	public String getModify_field_value() {
		return modify_field_value;
	}

	public void setModify_field_value(String modify_field_value) {
		this.modify_field_value = modify_field_value;
	}

	public Integer getSequ() {
		return sequ;
	}

	public void setSequ(Integer sequ) {
		this.sequ = sequ;
	}

	public String getSec_field_value() {
		return sec_field_value;
	}

	public void setSec_field_value(String sec_field_value) {
		this.sec_field_value = sec_field_value;
	}

	public String getSec_filed_value_desc() {
		return sec_filed_value_desc;
	}

	public void setSec_filed_value_desc(String sec_filed_value_desc) {
		this.sec_filed_value_desc = sec_filed_value_desc;
	}
	@Override
	@NotDbField
	public <T>T store() {
		ZteInstUpdateRequest<AttrInstBusiRequest> updateRequest = new ZteInstUpdateRequest<AttrInstBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		//构造参数
		QueryResponse<List<AttrInstBusiRequest>> resp = new QueryResponse<List<AttrInstBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,new ArrayList<AttrInstBusiRequest>());
	}
	
	
	public String getZBValue() {
		return CommonDataFactory.getInstance().getZbDictCodeValue(EcsOrderConsts.OTHER_SYSTEM_ZB, field_name, field_attr_id, field_value);
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getSpec_is_null() {
		return spec_is_null;
	}

	public void setSpec_is_null(String spec_is_null) {
		this.spec_is_null = spec_is_null;
	}

	public String getSpec_is_edit() {
		return spec_is_edit;
	}

	public void setSpec_is_edit(String spec_is_edit) {
		this.spec_is_edit = spec_is_edit;
	}

	public String getT_table_name() {
		return t_table_name;
	}

	public void setT_table_name(String t_table_name) {
		this.t_table_name = t_table_name;
	}

	public String getT_field_name() {
		return t_field_name;
	}

	public void setT_field_name(String t_field_name) {
		this.t_field_name = t_field_name;
	}

	public String getSub_attr_spec_type() {
		return sub_attr_spec_type;
	}

	public void setSub_attr_spec_type(String sub_attr_spec_type) {
		this.sub_attr_spec_type = sub_attr_spec_type;
	}

	

	

}
