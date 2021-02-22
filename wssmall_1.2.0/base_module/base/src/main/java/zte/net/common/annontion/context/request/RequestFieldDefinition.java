package zte.net.common.annontion.context.request;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 实体属性字段定义
 */

public class RequestFieldDefinition implements Serializable {

	String dname;

	String xname;

	String qname;
	
	String need_xshow;
	
	String need_insert;
	
	String need_query;

	Field field;
	
	String desc;
	
	String service_name; //字段归属服务名
	
	String asy_query;
	String asy_store;
	
	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getXname() {
		return xname;
	}

	public void setXname(String xname) {
		this.xname = xname;
	}

	public String getNeed_xshow() {
		return need_xshow;
	}

	public void setNeed_xshow(String need_xshow) {
		this.need_xshow = need_xshow;
	}

	public String getNeed_insert() {
		return need_insert;
	}

	public void setNeed_insert(String need_insert) {
		this.need_insert = need_insert;
	}

	public String getNeed_query() {
		return need_query;
	}

	public void setNeed_query(String need_query) {
		this.need_query = need_query;
	}

	public String getQname() {
		return qname;
	}

	public void setQname(String qname) {
		this.qname = qname;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public String getAsy_query() {
		return asy_query;
	}

	public void setAsy_query(String asy_query) {
		this.asy_query = asy_query;
	}

	public String getAsy_store() {
		return asy_store;
	}

	public void setAsy_store(String asy_store) {
		this.asy_store = asy_store;
	}

}
