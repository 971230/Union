package com.ztesoft.net.param.inner;

import java.io.Serializable;

/**
 * 批量异常实例处理入参
 * @author qin.yingxiong
 */
public class ExpInstBatchProcessedInner implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//需要处理的异常单ID集合
	private String[] excepInstIds;
	//员工号
	private String staff_no;
	//处理状态
	private String deal_status;
	//处理结果
	private String deal_result;

	public String[] getExcepInstIds() {
		return excepInstIds;
	}

	public void setExcepInstIds(String[] excepInstIds) {
		this.excepInstIds = excepInstIds;
	}

	public String getStaff_no() {
		return staff_no;
	}

	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}

	public String getDeal_status() {
		return deal_status;
	}

	public void setDeal_status(String deal_status) {
		this.deal_status = deal_status;
	}

	public String getDeal_result() {
		return deal_result;
	}

	public void setDeal_result(String deal_result) {
		this.deal_result = deal_result;
	}
}