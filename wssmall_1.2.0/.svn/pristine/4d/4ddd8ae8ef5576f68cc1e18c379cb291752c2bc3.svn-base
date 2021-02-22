package com.ztesoft.common.util;


import com.ztesoft.common.util.Clazz;
import com.ztesoft.common.util.StringUtils;

/**
 * @author xiaof
 * @date 2011-12-23
 * @file ObjectToken.java
 * @desc 类型携带器
 */
public class ObjectToken {
	private  String _object_name;
	private  String _object_type;
	private  String value;

	public String get_object_name() {
		return _object_name;
	}

	public void set_object_name(String objectName) {
		_object_name = objectName;
	}
	public  String get_object_type() {
		return _object_type;
	}
	public  void set_object_type(String _object_type) {
		this._object_type = _object_type;
	}
	public  String getValue() {
		return value;
	}
	public  void setValue(String value) {
		this.value = value;
	}
	public Class getToken() throws ClassNotFoundException{
		if(StringUtils.isEmpty(this._object_type)){
			return null;
		}
		Class TokenClass=Clazz.forName(this._object_type);
		return TokenClass;
	}
}
