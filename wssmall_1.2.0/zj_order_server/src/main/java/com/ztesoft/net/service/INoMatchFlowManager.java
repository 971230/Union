package com.ztesoft.net.service;

import java.util.Map;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.AuditQueryParame;
import com.ztesoft.net.model.OrderWarning;


/**
 * @Description 无匹配流水
 * @author  yanPengJun
 * @date    2016年10月18日
 */
public interface INoMatchFlowManager {
	/**
	 * 无匹配流水列表
	 * @param orderWarning
	 * @param pageNo
	 * @param pageSize
	 */

	public Page queryList(String is_zero, String bss_or_cbss, String import_date, int pageNo, int pageSize);
	

	public Page queryOrderListByPhoneNum(String order_id , String user_phone_num , int pageNo, int pageSize);

	public Page queryOrderListById(String order_id, String user_phone_num, int page, int pageSize);




	public Map updateFlow(String order_id, String audit_note,String serial_number,String flg);
	
}
