package com.rop.core.memcached.cache;

import java.io.Serializable;
import java.util.Map;

public interface IBean extends Serializable {
	//获取序列编码for Memcached,数据库可直接入库
	public String getSerialno() ;
	//db tablename
	public String getDBTableName() ;
	
	//转换为key/value模式，方便数据插入,key与数据库字段对应起来
	public Map toMap() ;
	
	//数据库主键、序列值
	public String[] dbKeySeq() ;
}
