package com.ztesoft.net.server.jsonserver.msgpojo;

/**
 * 浙江联通单宽带收单接口cust_info节点
 * @author fan.qijie
 *
 */
public class CodePurchaseMallCustInfoa {

	private String customer_name;//客户姓名
	
	private String user_type;//用户类型
	
	private String customer_type;//客户类型
	
	private String cert_type;//证件类型
	
	private String cert_num;//证件号码
	
	private String cert_addr;//证件地址 -非必填
	
	private String cert_nation;//名族
	
	private String cert_sex;//性别 0：男，1：女
	
	private String cust_birthday;//客户生日
	
	private String cert_issuedat;//签证机关
	
	private String cert_expire_date;//证件失效时间
	
	private String cert_effected_date;//证件生效时间

	private String cust_tel;//客户电话
	
	private String cert_num2;//出入境号
	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public String getCert_type() {
		return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getCert_num() {
		return cert_num;
	}

	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}

	

	public String getCert_sex() {
		return cert_sex;
	}

	public void setCert_sex(String cert_sex) {
		this.cert_sex = cert_sex;
	}

	public String getCust_birthday() {
		return cust_birthday;
	}

	public void setCust_birthday(String cust_birthday) {
		this.cust_birthday = cust_birthday;
	}

	public String getCert_issuedat() {
		return cert_issuedat;
	}

	public void setCert_issuedat(String cert_issuedat) {
		this.cert_issuedat = cert_issuedat;
	}

	public String getCert_effected_date() {
		return cert_effected_date;
	}

	public void setCert_effected_date(String cert_effected_date) {
		this.cert_effected_date = cert_effected_date;
	}

	public String getCust_tel() {
		return cust_tel;
	}

	public void setCust_tel(String cust_tel) {
		this.cust_tel = cust_tel;
	}

	public String getCert_addr() {
		return cert_addr;
	}

	public void setCert_addr(String cert_addr) {
		this.cert_addr = cert_addr;
	}

	public String getCert_nation() {
		return cert_nation;
	}

	public void setCert_nation(String cert_nation) {
		this.cert_nation = cert_nation;
	}

	public String getCert_expire_date() {
		return cert_expire_date;
	}

	public void setCert_expire_date(String cert_expire_date) {
		this.cert_expire_date = cert_expire_date;
	}

    public String getCert_num2() {
        return cert_num2;
    }

    public void setCert_num2(String cert_num2) {
        this.cert_num2 = cert_num2;
    }
}
