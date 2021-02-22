package com.ztesoft.net.mall.cmp;

import com.ztesoft.net.framework.action.WWAction;

public class CBAction extends WWAction{
	
	private String inParam;							//入参 args[0] = "20081201" + "104"; 
	private String flag;
	private String msg;
	
	private ICheckAcctManager compBillPro;
	private ICompChargePro compChargePro;
	//http://localhost:8080/wssmall/shop/admin/cmpbill!cmpBill.do?inParam=20130528104
	public String cmpBill(){
		if(null !=inParam  && !inParam.equals("")){
			compBillPro.checkAcctByDateAndSystemId(inParam);
		}else {
			compBillPro.checkAcct();
		}
		
		flag = "1";
		return "result";
	}
	//http://localhost:8080/wssmall/shop/admin/cmpbill!cmpCharge.do?inParam=20130528BXW
	public void cmpCharge(){
		if(inParam != null && !inParam.equals("")){
			compChargePro.compChargeHand(inParam);
		}else {
			compChargePro.compCharge();
		}
	}

	public String getInParam() {
		return inParam;
	}

	public void setInParam(String inParam) {
		this.inParam = inParam;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ICheckAcctManager getCompBillPro() {
		return compBillPro;
	}

	public void setCompBillPro(ICheckAcctManager compBillPro) {
		this.compBillPro = compBillPro;
	}

	public ICompChargePro getCompChargePro() {
		return compChargePro;
	}

	public void setCompChargePro(ICompChargePro compChargePro) {
		this.compChargePro = compChargePro;
	}
}
