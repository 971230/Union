package com.ztesoft.ibss.ct.vo;

/**
 * 一次受理的总费用对象，存入session中<br>
 * 提交银行付款时取此值，而不是页面上的费用值，防止页面信息遭篡改
 * @author Reason
 * @version Created Feb 18, 2012
 */
public class FeeVo {
	String strPreMemOrdNo;
	public FeeVo(String strPreMemOrdNo){
		this.strPreMemOrdNo =strPreMemOrdNo;
	}
	public final static String  FEE_CONST ="fee";
	public final static String  CARD_FEE_CONST ="fee";
	public void setValue(String field_name, String field_value) {
		User.getSession().setAttribute(User.getSession().getId()+"_"+this.strPreMemOrdNo+"_"+field_name, field_value);
	}
	public String getValue(String field_name) {
		return (String) User.getSession().getAttribute(User.getSession().getId()+"_"+this.strPreMemOrdNo+"_"+field_name);
	}
	public void reMoveValue(String feeConst) {
		User.getSession().removeAttribute(feeConst);
		
	}
}

