package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * cust_info节点 （客户信息）
 * 
 * @author song.qi 2017年12月26日
 */
public class CustInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "is_real_name", type = "String", isNecessary = "N", desc = "是否实名校验")
	private String is_real_name;// 0 – 未实名；1 – 已实名。
	@ZteSoftCommentAnnotationParam(name = "customer_name", type = "String", isNecessary = "N", desc = "客户姓名")
	private String customer_name;// 客户姓名 
	@ZteSoftCommentAnnotationParam(name = "cert_type", type = "String", isNecessary = "N", desc = "证件类型")
	private String cert_type;// 详见附录证件类型
	@ZteSoftCommentAnnotationParam(name = "cert_num", type = "String", isNecessary = "N", desc = "证件号码")
	private String cert_num;// 证件号码
	@ZteSoftCommentAnnotationParam(name = "cert_addr", type = "String", isNecessary = "N", desc = "证件地址")
	private String cert_addr;// 证件地址
	@ZteSoftCommentAnnotationParam(name = "cert_nation", type = "String", isNecessary = "N", desc = "名族")
	private String cert_nation;// 名族
	@ZteSoftCommentAnnotationParam(name = "cert_sex", type = "String", isNecessary = "N", desc = "性别")
	private String cert_sex;// 性别，固定长度1位， M：男， F：女
	@ZteSoftCommentAnnotationParam(name = "cust_birthday", type = "String", isNecessary = "N", desc = "客户生日")
	private String cust_birthday;// 客户生日
	@ZteSoftCommentAnnotationParam(name = "cert_issuedat", type = "String", isNecessary = "N", desc = "签发机关")
	private String cert_issuedat;// 签发机关
	@ZteSoftCommentAnnotationParam(name = "cert_expire_date", type = "String", isNecessary = "N", desc = "证件失效时间")
	private String cert_expire_date;// 证件失效时间yyyymmdd
	@ZteSoftCommentAnnotationParam(name = "cert_expire_date", type = "String", isNecessary = "N", desc = "证件生效时间")
	private String cert_effected_date;// 证件生效时间yyyymmdd
	@ZteSoftCommentAnnotationParam(name = "cust_tel", type = "String", isNecessary = "N", desc = "客户电话")
	private String cust_tel;// 客户电话
	@ZteSoftCommentAnnotationParam(name = "customer_type", type = "String", isNecessary = "N", desc = "客户类型")
	private String customer_type;// 客户类型100：个人客户(联通)，不传时默认值 50：个人企业客户（小微）
	@ZteSoftCommentAnnotationParam(name = "cust_id", type = "String", isNecessary = "N", desc = "客户编号")
	private String cust_id;// 客户编号 老客户传BSS系统客户ID
	@ZteSoftCommentAnnotationParam(name = "group_code", type = "String", isNecessary = "N", desc = "收入归集集团")
	private String group_code;// 收入归集集团 集团15位编码
	@ZteSoftCommentAnnotationParam(name = "req_swift_num", type = "String", isNecessary = "N", desc = "拍照流水")
	private String req_swift_num;// 拍照流水 实名制拍照流水
	
	/*
	 * add by wcl
	 * 认证类型	check_type	N	String(20)	返档订单必传
01：本地认证
02：公安认证
03：二代证读卡器

	 */
	@ZteSoftCommentAnnotationParam(name = "认证类型", type = "String", isNecessary = "N", desc = "01 01：本地认证 02：公安认证 03：二代证读卡器")
	private String check_type ;
	@ZteSoftCommentAnnotationParam(name = "cert_num2", type = "String", isNecessary = "N", desc = "出入境号")
    private String cert_num2;
	@ZteSoftCommentAnnotationParam(name = "chip_illumination", type = "String", isNecessary = "N", desc = "芯片照")
    private String chip_illumination;
	
	
	public String getChip_illumination() {
		return chip_illumination;
	}

	public void setChip_illumination(String chip_illumination) {
		this.chip_illumination = chip_illumination;
	}

	public String getCert_type() {
		return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getIs_real_name() {
		return is_real_name;
	}

	public void setIs_real_name(String is_real_name) {
		this.is_real_name = is_real_name;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCert_num() {
		return cert_num;
	}

	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
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

	public String getCert_expire_date() {
		return cert_expire_date;
	}

	public void setCert_expire_date(String cert_expire_date) {
		this.cert_expire_date = cert_expire_date;
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

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getGroup_code() {
		return group_code;
	}

	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

	public String getReq_swift_num() {
		return req_swift_num;
	}

	public void setReq_swift_num(String req_swift_num) {
		this.req_swift_num = req_swift_num;
	}

	public String getCheck_type() {
		return check_type;
	}

	public void setCheck_type(String check_type) {
		this.check_type = check_type;
	}

    public String getCert_num2() {
        return cert_num2;
    }

    public void setCert_num2(String cert_num2) {
        this.cert_num2 = cert_num2;
    }
	
}
