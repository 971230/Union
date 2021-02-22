package com.ztesoft.check.common;

public class Consts {
	
	public final static String IMPL_01 = "01";//编码实现
	public final static String IMPL_02 = "02";//sql实现
	public final static String IMPL_03 = "03";//存储过程实现
	
	public final static String FACTOR_TYPE_A = "001";//校验业务匹配的因子
	public final static String FACTOR_TYPE_B = "002";//要过来走校验系统的订单匹配因子
	
	
	public final static String FILTER_BIZ_FLAG = "-";//定义为要过滤的业务规则
	
	public final static String BIZ_EXE_METHOD_1 = "1";//校验列表执行方式1：串行 2：并行
	public final static String BIZ_EXE_METHOD_2 = "2";//校验列表执行方式1：串行 2：并行
	
	public final static String BIZ_TYPE_001 = "001";//过滤的业务，不走校验的场景
	public final static String BIZ_TYPE_002 = "002";//工作流匹配，现网割接数据的场景
	public final static String BIZ_TYPE_003 = "003";//自定义校验的业务
	
	
}
