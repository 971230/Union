package com.ztesoft.common.exception;

import java.io.IOException;
import java.util.PropertyResourceBundle;

/**
 * 
 * <p>Description: 资源定位类文件</p>
 * <p>Copyright 2006 ZTEsoft Corp.</p>
 * @Create Date   : 2006-4-26
 * @Version       : 1.0
 */
public class Resources extends PropertyResourceBundle {
	
	/**
	 * 构造函数,从文本文件中导入文本属性
	 * @throws IOException
	 */
	public Resources() throws java.io.IOException {
		super(Resources.class.getResourceAsStream("Resources.properties"));
	}

}
