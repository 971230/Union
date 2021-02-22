package com.ztesoft.net.cache.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * create table es_dstore_config
(
  TABLE_NAME VARCHAR2(50),
  FIELD_NAME VARCHAR2(50),
  store_type VARCHAR2(15),
  SOURCE_FROM VARCHAR2(15)
);
comment on table es_dstore_config is '大字段存储方式';
comment on column es_dstore_config.TABLE_NAME is '表名';
comment on column es_dstore_config.FIELD_NAME is '字段名';
comment on column es_dstore_config.store_type is '存储方式：db_数据库,lfile_本地文件,rfile_分布式文件存储';

 * @author wui
 * 2014-06-03
 *
 */
public class SerializeList implements Serializable {
	List obj = new ArrayList();

	public List getObj() {
		return obj;
	}

	public void setObj(List obj) {
		this.obj = obj;
	}

	
	
	
	
}
