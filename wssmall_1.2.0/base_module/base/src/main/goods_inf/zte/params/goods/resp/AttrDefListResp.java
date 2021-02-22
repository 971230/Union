package zte.params.goods.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class AttrDefListResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="商品业务属性列表",type="List",isNecessary="Y",desc="attrList：某个商品的商品业务属性列表查询结果，字段有：sub_attr_spec_type：属性归属（发货、受理等），field_name：属性名（col1、col2等），field_desc：属性描述，field_attr_id：属性ID，field_type：属性类型（下拉框、文本框等），default_value：默认值，default_value_desc：默认值说明，sec_field_name：对应本系统名，sec_field_desc：对应本系统描述，is_edit：是否可编辑，is_null：是否可为空（0可控，1不可空），check_message：校验信息，rel_table_name：附属表名，owner_table_fields：为yes时附属表字段，为no非附属表字段")
	public List attrList;

	public List getAttrList() {
		return attrList;
	}

	public void setAttrList(List attrList) {
		this.attrList = attrList;
	}
}
