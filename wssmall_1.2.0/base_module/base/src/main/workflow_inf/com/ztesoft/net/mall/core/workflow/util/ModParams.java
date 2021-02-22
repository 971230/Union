package com.ztesoft.net.mall.core.workflow.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModParams {
	//动作类型
	public static final String ACTION_ADD = "A" ;//新增
	public static final String ACTION_MOD = "M" ;//修改
	public static final String ACTION_DEL = "D" ;//删除
	
	
	
	private String flow_code ;//流程编码
	private String applyUserId ;//申请人
	private String pkVal ;//对象主键标识
	
	private String op_time = null ;//申请发起时间,默认为当前修改时间
	
	
	List<ModVO> modInfos = new ArrayList<ModVO>() ;
	
//	private String action ;//动作类型[必须]
//	private String refObjId  ; //主键标识[必须]
//	private String refPKId  ;//暂时不支持多表情形
//	private String extAttr  ;//扩展属性[可选]
	
	
	
	private String formatDate(Date date) {

		if (date == null)
			return "";

		SimpleDateFormat dateFormator = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = dateFormator.format(date);
		

		return str;
	}
	
	
	public String getApplyUserId() {
		return applyUserId;
	}


	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}


	public void setFlow_code(String flow_code) {
		this.flow_code = flow_code;
	}


	public String getFlow_code() {
		return flow_code;
	}



	public String getOp_time() {
		return op_time;
	}


	/**
	 * 
	 * @param action 动作类型[必须]
	 * @param tableName 表名[必须]
	 * @param refObjId 主键标识[必须]
	 * @param extAttr 扩展属性[可选]
	 * @param instObj 变更对象[必须]
	 */
	public ModParams(String flowCode , String currentUserId,String pkVal ,List<ModVO> modInfos ){
//		this.action = action ;
//		this.tableName = tableName ;
//		this.refObjId = refObjId ;
//		this.extAttr = extAttr ;
//		this.obj = instObj ;
		this.applyUserId = currentUserId ;
		this.pkVal = pkVal ;
		this.modInfos = modInfos ;
		this.flow_code = flowCode ;
		this.op_time = formatDate(new Date())  ;
	}
	
	
	public ModParams(){
		this.op_time = formatDate(new Date())  ;
	}


	public List<ModVO> getModInfos() {
		return modInfos;
	}


	public void setModInfos(List<ModVO> modInfos) {
		this.modInfos = modInfos;
	}


	public String getPkVal() {
		return pkVal;
	}


	public void setPkVal(String pkVal) {
		this.pkVal = pkVal;
	}
	
}
