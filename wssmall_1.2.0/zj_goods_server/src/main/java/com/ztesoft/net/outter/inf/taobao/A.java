package com.ztesoft.net.outter.inf.taobao;

import org.apache.log4j.Logger;

import zte.params.order.req.OrderCollect;

import com.ztesoft.net.model.Outer;
import com.ztesoft.net.outter.inf.model.LocalOrderAttr;

import commons.CommonTools;

public class A {

	private String baidu_id;
	private String freeze_tran_no;
	private String freeze_free;
	private String reserve0;
	private String reserve1;
	private String reserve2;
	private String reserve3;
	private String reserve4;
	private String reserve5;
	private String reserve6;
	private String reserve7;
	private String reserve8;
	private String reserve9;
	private static Logger logger = Logger.getLogger("com.ztesoft.net.outter.inf.taobao.A");
	public static void main(String[] args) {
		/*int i = 20000;
		Field [] fs = A.class.getDeclaredFields();
		String temp = "";
		for(Field f:fs){
			String sql = "insert into es_attr_def (ATTR_SPEC_ID, ATTR_SPEC_TYPE, SUB_ATTR_SPEC_TYPE, FIELD_NAME, FIELD_DESC, FIELD_ATTR_ID, FIELD_TYPE, SOURCE_FROM, REL_TABLE_NAME, OWNER_TABLE_FIELDS)"+
					" values ('20002', 'goodstype', 'accept', '"+f.getName()+"', '"+f.getName()+"', '"+i+"', '1', 'ECS', 'es_outer_accept', 'yes');";
			logger.info(sql);
			temp += ",{e_name:\""+f.getName()+"\",field_attr_id:\""+i+"\"}";
			i++;
		}
		logger.info(temp);*/
		/*DateFormat df = new SimpleDateFormat("d");
		String str = df.format(new Date());
		logger.info(str);
		logger.info(Integer.parseInt("09"));*/
		
		OrderCollect oc = new OrderCollect();
		
		Outer outer = new Outer();
		LocalOrderAttr la = new LocalOrderAttr();
		la.setBank_code("sdfsdfsdf");
		outer.setLocalObject(la);
		oc.setOuter(outer);
		
		String str = CommonTools.beanToJson(oc);
		logger.info(str);
	}
}
