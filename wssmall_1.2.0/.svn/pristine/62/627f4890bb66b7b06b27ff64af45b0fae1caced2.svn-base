package com.ztesoft.ibss.common.valueobject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;



/**
 * @Classname : VO
 * @Description : 公共数据对象接口,对于每个表值对象， 相应的接口函数使用代码生成器进行生产
 * @Copyright ?? 2006 ZTEsoft.
 * @Author : llh
 * @Create Date : 2006-3-22
 * 
 * @Last Modified :
 * @Modified by :
 * @Version : 1.0
 */
public interface VO extends Serializable {
	// 把数据导出到HashMap中， HashMap的关键字为对应的表字段名字
	// 使用代码生产器生产
	public HashMap unloadToHashMap();

	// 从HashMap中读取数据, 使用代码生产器生成
	public void loadFromHashMap(HashMap map);

	// 获取关键字列表， 使用代码生产器生成
	public List getKeyFields();

	// 获取对应的表名， 使用代码生产器生成
	String getTableName();
}
