package com.ztesoft.common.consts;

public class KeyValuesLocal {
	 /************* 付费模式 *******************
	@FieldJudgeMeta(id = "PRI-0001", code = "PAYMENT_MODE_CD", name = "后付费", desc = "N1N2； 10～99的顺序数字")
	public final static String PAYMENT_MODE_CD_A = "12"; // 后付费
	@FieldJudgeMeta(id = "PRI-0001", code = "PAYMENT_MODE_CD", name = "预付费", desc = "N1N2； 10～99的顺序数字")
	public static final String PAYMENT_MODE_CD_B = "21";  //预付费
	@FieldJudgeMeta(id = "PRI-0001", code = "PAYMENT_MODE_CD", name = "准实时后付费", desc = "N1N2； 10～99的顺序数字")
	public static final String PAYMENT_MODE_CD_C = "22";  //准实时预付费
	@FieldJudgeMeta(id = "PRI-0001", code = "PAYMENT_MODE_CD", name = "OCS预付费", desc = "N1N2； 10～99的顺序数字")
	public static final String PAYMENT_MODE_CD_OCS = "13";  //ocs预付费
	************ 付费模式 ********************/
	
	
	/*
	 * 管理级别manage_grade
	 * 10	集团框架级
	 * 11	集团实例级
	 * 12	省级
	 * 13	本地网级
	 * 
	 * 新疆本地
65        省级
04        本地网
01        集团实例级
00        集团框架级
	 */
	public static final String GROUP_FRAME_MANAGE_XJ = "00";
	public static final String GROUP_INSTANCE_MANAGE_XJ  = "01";
	public static final String PROVINCE_MANAGE_XJ  = "65";
	public static final String LOCAL_MANAGE_XJ  = "65";
}
