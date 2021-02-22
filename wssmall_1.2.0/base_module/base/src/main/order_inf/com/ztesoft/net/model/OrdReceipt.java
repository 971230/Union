/**
 * 
 */
package com.ztesoft.net.model;

import java.io.Serializable;

/**
 * @author ZX
 * OrdReceipt.java
 * 2014-10-25
 */
public class OrdReceipt implements Serializable {


    private String material_retrieve;//订单需要回收的凭证
    private String user_recieve_time;// 用户收货时间
	private String is_upload;//是否上传
	private String file_return_type;//资料回收方式
	
	private String county_name ;
	private String archive_num;//归档编号
    private String net_certi;// 入网证件
	private String accept_agree;//受理协议
	private String liang_agree;//靓号协议
	
	
	public String getCounty_name() {
		return county_name;
	}
	public void setCounty_name(String county_name) {
		this.county_name = county_name;
	}
	public String getMaterial_retrieve() {
		return material_retrieve;
	}
	public void setMaterial_retrieve(String material_retrieve) {
		this.material_retrieve = material_retrieve;
	}
	public String getUser_recieve_time() {
		return user_recieve_time;
	}
	public void setUser_recieve_time(String user_recieve_time) {
		this.user_recieve_time = user_recieve_time;
	}
	public String getIs_upload() {
		return is_upload;
	}
	public void setIs_upload(String is_upload) {
		this.is_upload = is_upload;
	}
	public String getFile_return_type() {
		return file_return_type;
	}
	public void setFile_return_type(String file_return_type) {
		this.file_return_type = file_return_type;
	}
	
	public String getArchive_num() {
		return archive_num;
	}
	public void setArchive_num(String archive_num) {
		this.archive_num = archive_num;
	}
	public String getNet_certi() {
		return net_certi;
	}
	public void setNet_certi(String net_certi) {
		this.net_certi = net_certi;
	}
	public String getAccept_agree() {
		return accept_agree;
	}
	public void setAccept_agree(String accept_agree) {
		this.accept_agree = accept_agree;
	}
	public String getLiang_agree() {
		return liang_agree;
	}
	public void setLiang_agree(String liang_agree) {
		this.liang_agree = liang_agree;
	}
	
}
