package com.ztesoft.net.template.busi;

import org.springframework.stereotype.Service;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteBusiRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.treeInst.RequestStoreExector;

/** 
 * 
 * Copyright (c) 2015, 南京中兴软创科技股份有限公司 All rights reserved
 * @author      : 张金叶
 * @createTime  : 2015-3-3
 * @version     : 1.0 
 * @documentPath:
 */
@RequestBeanAnnontion(primary_keys="ATTR_SPEC_ID",table_name="es_order_template",table_archive="yes",cache_store="yes",service_desc="订单树对象，数据库存储完整的json数据值")
@Service
public class OrdTemplateBusiRequest extends ZteBusiRequest<ZteResponse>  implements IZteBusiRequestHander {
	 
	private static final long serialVersionUID = 1L;


	@RequestFieldAnnontion(dname="FIELD_NAME_NAME_S",desc="FIELD_NAME_NAME_S_S",need_query="no")
	String FIELD_NAME_NAME_S;
	
	@RequestFieldAnnontion(dname="FIELD_11",desc="FIELD_11",need_query="no")
	String FIELD_11;
	
	
	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<OrdTemplateBusiRequest> updateRequest = new ZteInstUpdateRequest<OrdTemplateBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instParam) {
//		QueryResponse<OrdTemplateBusiRequest> resp = new QueryResponse<OrdTemplateBusiRequest>();
//		T t =  RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,this); //为空，走历史归档表
//		return t;
		return null;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return null;
	}

	public String getFIELD_NAME_NAME_S() {
		return FIELD_NAME_NAME_S;
	}

	public void setFIELD_NAME_NAME_S(String fIELD_NAME_NAME_S) {
		FIELD_NAME_NAME_S = fIELD_NAME_NAME_S;
	}

	public String getFIELD_11() {
		return FIELD_11;
	}

	public void setFIELD_11(String fIELD_11) {
		FIELD_11 = fIELD_11;
	}

	 
}
