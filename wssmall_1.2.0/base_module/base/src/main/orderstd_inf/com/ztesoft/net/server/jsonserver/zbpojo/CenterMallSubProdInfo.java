package com.ztesoft.net.server.jsonserver.zbpojo;

import java.util.List;

import zte.net.ecsord.params.zb.vo.orderattr.SubPackage;

/******************************************************
 * 
 * @author duanshaochu
 * 附加产品信息
 */
public class CenterMallSubProdInfo {

//	附加产品编码
	private String subProCode = "";
//	附加产品名称
	private String subProName = "";
//	附加产品说明
	private String subProDesc = "";
//	附加产品参数
//	private String subProPara = ""; /*ZX add 2016-01-08 注释*/
//  附加产品可选包
	private List<SubPackage> subPackage;
	
	public String getSubProCode() {
		return subProCode;
	}
	public void setSubProCode(String subProCode) {
		this.subProCode = subProCode;
	}
	public String getSubProName() {
		return subProName;
	}
	public void setSubProName(String subProName) {
		this.subProName = subProName;
	}
	public String getSubProDesc() {
		return subProDesc;
	}
	public void setSubProDesc(String subProDesc) {
		this.subProDesc = subProDesc;
	}
	public List<SubPackage> getSubPackage() {
		return subPackage;
	}
	public void setSubPackage(List<SubPackage> subPackage) {
		this.subPackage = subPackage;
	}
	
}
