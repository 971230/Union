package com.ztesoft.crm.pass.ecsord_server;

import java.security.NoSuchAlgorithmException;
import com.ztesoft.common.util.MD5Util;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;


/**
 * 订单预警老数据插入
 */
public class WarningDataInsert {
	
	
	public static void main(String[] args) {
		String user_id="admin";
		String out_tid="5130724909";//外部单id
		String myToken;
		try {
			myToken = MD5Util.MD5(user_id+out_tid+EcsOrderConsts.APP_KEY);
			System.out.println("我的加密："+myToken);
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
		
		
	}
}
