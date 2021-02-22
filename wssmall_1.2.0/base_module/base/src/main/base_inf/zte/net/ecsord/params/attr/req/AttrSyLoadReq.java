package zte.net.ecsord.params.attr.req;

import params.ZteRequest;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.model.Outer;

/**
 * 属性同步
 * @作者 wui
 * @创建日期 2014-9-29 
 * @版本 V 1.0
 */
public class AttrSyLoadReq extends ZteRequest<AttrInstLoadResp> {

	private Outer outer;
	private String key;
	private String value;
	private String hander_class;
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.ecsord.params.attr.req.attrsyloadReq";
	}

	public Outer getOuter() {
		return outer;
	}

	public void setOuter(Outer outer) {
		this.outer = outer;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getHander_class() {
		return hander_class;
	}

	public void setHander_class(String hander_class) {
		this.hander_class = hander_class;
	}
	
	
}
