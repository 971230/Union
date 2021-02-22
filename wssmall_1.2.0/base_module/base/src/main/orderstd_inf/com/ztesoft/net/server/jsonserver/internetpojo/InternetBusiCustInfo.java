package com.ztesoft.net.server.jsonserver.internetpojo;

public class InternetBusiCustInfo {

	private String cust_name;//客户姓名
	
	private String certi_num;//证件号码
	
	private String certi_type;//证件类型

	private String cert_num2;//证件号码
    
	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getCerti_num() {
		return certi_num;
	}

	public void setCerti_num(String certi_num) {
		this.certi_num = certi_num;
	}

	public String getCerti_type() {
		return certi_type;
	}

	public void setCerti_type(String certi_type) {
		this.certi_type = certi_type;
	}

    public String getCert_num2() {
        return cert_num2;
    }

    public void setCert_num2(String cert_num2) {
        this.cert_num2 = cert_num2;
    }

}
