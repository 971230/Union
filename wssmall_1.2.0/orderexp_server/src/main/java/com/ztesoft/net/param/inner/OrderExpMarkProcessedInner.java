package com.ztesoft.net.param.inner;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.model.EsearchExpInst;

/**
 * 异常单标记入参
 * @author qin.yingxiong
 */
public class OrderExpMarkProcessedInner implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//需要标记的异常单ID集合
	private List<EsearchExpInst> excepInsts;
	//处理工号
	private String deal_staff_no;

	public List<EsearchExpInst> getExcepInsts() {
		return excepInsts;
	}

	public void setExcepInsts(List<EsearchExpInst> excepInsts) {
		this.excepInsts = excepInsts;
	}

	public String getDeal_staff_no() {
		return deal_staff_no;
	}

	public void setDeal_staff_no(String deal_staff_no) {
		this.deal_staff_no = deal_staff_no;
	}
}