package com.ztesoft.cms.page.query.impl;

import com.ztesoft.cms.page.query.CmsQuery;

/**
 * @author Reason.Yea
 * @version Created Oct 25, 2012
 */
public class CommonQueryImpl extends CmsQuery {
	
	public static String QRY_ONLINE_SQL="select INFO_ID,FUN_ID,INFO_CODE,INFO_NAME,INFO_KIND,INFO_CHANEL,INFO_TYPE,ORDER_ID,AREA_CODE,CREATE_TIME,EDIT_TIME,EFF_TIME,EXP_TIME,STATE,FILE_ID," +
	" COL1,COL2,COL3,COL4,COL5,COL6,COL7,COL8,(select file_path from CMS_CONTENT b where a.file_id=b.file_id ) as file_path from CMS_INFO a where eff_time<=sysdate and exp_time>sysdate ";
	
	public static String QRY_OFFLINE_SQL= QRY_ONLINE_SQL.replaceAll("CMS_CONTENT b where a.file_id=b.file_id", "CMS_CONTENT_EDIT b where a.file_id=b.file_id and b.sequ=0 ").replaceAll("CMS_INFO", "CMS_INFO_EDIT");
	
	
	@Override
	public String getOfflineSql() {
		return QRY_OFFLINE_SQL+" and sequ=0";
	}

	@Override
	public String getOnlineSql() {
		return QRY_ONLINE_SQL+ " and state='1'";
	}

	
}
