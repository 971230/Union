package com.ztesoft.net.sqls;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SF {
	
	public static Map<String,Sqls> instMap = new HashMap<String,Sqls>();
	public static Map<String,Sqls> coreMap = new HashMap<String,Sqls>();
	/**
	 * 反射类的get方法
	 * @作者 MoChunrun
	 * @创建日期 2014-3-31 
	 * @param core_class
	 * @param local_class
	 * @param name
	 * @return
	 */
	public static String getSqls(String core_class,String local_class,String name){
		String getName = "get"+name;
		Sqls inst = instMap.get(local_class);
		if(inst==null)inst = instMap.get(core_class);
		if(inst==null){
			try{
				inst = (Sqls) Class.forName(local_class).newInstance();
				instMap.put(local_class, inst);
			}catch(Exception ex){
				//ex.printStackTrace();
				try {
					inst = (Sqls) Class.forName(core_class).newInstance();
					instMap.put(core_class, inst);
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		}
		
		//coreInst设置
		Sqls cInst = coreMap.get(core_class);
		if(cInst == null)
		try {
			cInst = (Sqls) Class.forName(core_class).newInstance();
			coreMap.put(core_class, cInst);
		} catch (Exception e) {
		}
		
		try {
			Class clazz = inst.getClass();
			Method method = clazz.getMethod("getSql",String.class,Sqls.class);
			Object sqlObj = method.invoke(inst, getName,cInst);
			return String.valueOf(sqlObj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	// 订单工具
	public static String orderSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Order","com.ztesoft.net.sqls.Sqls_Order_Local", name);
	}

	// 异常单工具
	public static String orderExpSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Orderexp","com.ztesoft.net.sqls.Sqls_Orderexp_Local", name);
	}
	
	// 任务工具
	public static String taskSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Task","com.ztesoft.net.sqls.Sqls_Task_Local", name);
	}

	// 商品工具
	public static String goodsSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Goods","com.ztesoft.net.sqls.Sqls_Goods_Local", name);
	}

	// 系统工具
	public static String sysSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Sys","com.ztesoft.net.sqls.Sqls_Sys_Local", name);
	}

	// 会员工具
	public static String memberSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Member","com.ztesoft.net.sqls.Sqls_Member_Local", name);
	}

	// 打印工具
	public static String printSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Print","com.ztesoft.net.sqls.Sqls_Print_Local", name);
	}

	// 支付工具
	public static String paymentSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Payment","com.ztesoft.net.sqls.Sqls_Payment_Local", name);
	}

	// 工作流工具
	public static String workflowSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Flow","com.ztesoft.net.sqls.Sqls_Flow_Local", name);
	}

	// 基础类工具
	public static String baseSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Base","com.ztesoft.net.sqls.Sqls_Base_Local", name);
	}

	// 分销商工具
	public static String partnerSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Partner","com.ztesoft.net.sqls.Sqls_Partner_Local", name);
	}

	// 供应商工具
	public static String supplierSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Supplier","com.ztesoft.net.sqls.Sqls_Supplier_Local", name);
	}

	// 规则工具
	public static String ruleSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Rule","com.ztesoft.net.sqls.Sqls_Rule_Local", name);
		//return SqlsFactory.getSqls_Rule().getSql(name);
	}

	// 佣金工具
	public static String commissionSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Commiss","com.ztesoft.net.sqls.Sqls_Commiss_Local", name);
	}

	//获取广告信息SQL
	public static String infoSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Info","com.ztesoft.net.sqls.Sqls_Info_Local", name);
	}
	
	// 号码
	public static String numeroSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Numeros","com.ztesoft.net.sqls.Sqls_Numeros_Local", name);
	}
	
	//货品
	public static String productoSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Productos","com.ztesoft.net.sqls.Sqls_local_Productos", name);
	}
	
	//CMS管理
	public static String cmsSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Cms","com.ztesoft.net.sqls.Sqls_local_Cms", name);
	}

	//ECSORD
	public static String ecsordSql(String name){
		return getSqls("com.ztesoft.net.sqls.Sqls_Ecsord","com.ztesoft.net.sqls.Sqls_Ecsord", name);
	}
	
	//SHPORD
	public static String shpOrdSql(String name){
		return getSqls("com.ztesoft.net.sqls.Sqls_ShpOrd","com.ztesoft.net.sqls.Sqls_ShpOrd", name);
	}
	
	//JSORD
	public static String jsOrdSql(String name){
		return getSqls("com.ztesoft.net.sqls.Sqls_JsOrd","com.ztesoft.net.sqls.Sqls_JsOrd", name);
	}
	
	//CMS管理
	public static String infSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Inf","com.ztesoft.net.sqls.Sqls_local_Inf", name);
	}
	
	//归档管理
	public static String archivesSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Archives","com.ztesoft.net.sqls.Sqls_Archives", name);
	}
	public static String orderstdSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Orderstd","com.ztesoft.net.sqls.Sqls_Orderstd", name);
	}
	
	//ECC校验系统
	public static String eccSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_Ecc","com.ztesoft.net.sqls.Sqls_Ecc", name);
	}
	public static String esearchSql(String name) {
		return getSqls("com.ztesoft.net.sqls.Sqls_esearch","com.ztesoft.net.sqls.Sqls_esearch", name);
	}
}
