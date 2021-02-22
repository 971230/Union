//Source file: D:\\WLSApp\\Eclipse\\workspace\\ibss\\src\\com\\powerise\\ibss\\framework\\STR_ACTION_INFO.java

package com.powerise.ibss.framework;

import org.w3c.dom.Element;

public class STR_ACTION_INFO 
{
		public int Seq;				//序号，因输入XML中入参按名字指定，执行SQL
									//时需按顺序绑定，因此需要用到seq-name对照

		public String Type;			//类型
		public String Name;			//变量名称
		public Element Elem;		//相关节点
		public String Env_id;      //数据库DataSource,主要考虑支持多个数据库的连接
		
		/**
		@since 2002
		 */
		public STR_ACTION_INFO() 
		{
			
		}
}
