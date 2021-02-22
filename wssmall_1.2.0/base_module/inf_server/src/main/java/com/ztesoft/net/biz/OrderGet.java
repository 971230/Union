package com.ztesoft.net.biz;

import com.ztesoft.net.util.SendInfoUtil;
import com.ztesoft.service.impl.OrderGetManager;

public class OrderGet {
	public String orderInfoSend(String orderId,String url,String toSysName,String serial_no){
		String result = null;
		OrderGetManager ogm = new OrderGetManager();
		SendInfoUtil sender = new SendInfoUtil();
		String msg = ogm.taobaoOrderSync(orderId, toSysName, serial_no);
		result = SendInfoUtil.postHttpReq(url, msg);
		return result;
	}
}
