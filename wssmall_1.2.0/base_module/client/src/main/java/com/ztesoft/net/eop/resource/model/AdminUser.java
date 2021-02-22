package com.ztesoft.net.eop.resource.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.ztesoft.net.framework.database.NotDbField;


/**
 * 用户管理员
 * 
 * @author lzf
 *         <p>
 *         created_time 2009-11-27 下午01:40:54
 *         </p>
 * @version 1.0
 * 
 * @version 2.0
 * 重构站点独立表，产且字段进行了变更
 */
public class AdminUser implements Serializable{
	
	@JSONField(name="userid")
	private String userid;
	private String username; //必填
	private String reltype;
	private String password;
	private int state;
	private String realname; //真实姓名，必填
	private String userno;
	private String userdept;
	private String remark;
	private String dateline;
	private String[] roleids;
	private Integer founder; //必填
	private Integer siteid; //子站点id
	private String create_time;
	private String lan_id;
	private String paruserid;
	private String relcode;
    private String cur_logintime;
    private String last_logintime;
    private int    fail_logincount;
    private int    success_logincount;
    private String org_id;
    private String dep_name;
    private String relno;
    private String source_from;
    
    private String phone_num;
    private String city;
    private String lan_name;
    private String last_loginfail_time;
    
    private String current_sessionid;
    private int login_status;
    
    private String org_name;
    private String user_level;
    private String apply_time;
    private String open_time;
    private String boss_user_type;
    private String email;
    private String desks;
    
    private String city_id;//城市Id --(city_name(mbl_staff)
    private String sex;//性别
    private String birthday;//生日
    private String user_pid;//身份证号
    
    private String menu_jsons;
    
    private String theme_source_from;
   // private String supplierAdminUserId ;//如果是供应商,该属性取值为供应商对应的管理员用户标识
    private String type_name;
    
    private String contact_tel; //联系号码
    private String col1;
    private String col2;
    private String col3;
    private String col4;
    private String col5;
    private int col6;
    private int col7;
    private Timestamp col8;
    private String col9;
    private String col10;
    private String is_login_first;
    private Integer usertype;
    //数据权限
    private String tacheAuth;//环节权限
    private String receiveUserAuth;//领取工号权限
    private String lockUserAuth;//锁定工号权限
    private String busicityAuth;//授权县份
    
    private String syn_dept_id;
	private String syn_hq_dept_id;
	private String syn_dept_type;
	private String syn_county_id;
	private String syn_user_id;
	private String syn_employee_id;
	private String syn_use_domain;
	private String is_syn;
	private String syn_date;
	private String syn_remark;
	
	//自定义流程人员层级和地市、县分权限
	private String permission_level;//权限层级：1-省，2-地市，3-县分
	private List<String> permission_region;//权限地市
	private List<String> permission_county;//权限县分
	private List<String> perm_busi_county;//权限营业县分
	
	private String sms_receive;//短信接收开关
	
    public String getLast_loginfail_time() {
		return last_loginfail_time;
	}
	public String getCurrent_sessionid() {
		return current_sessionid;
	}
	public void setCurrent_sessionid(String current_sessionid) {
		this.current_sessionid = current_sessionid;
	}
	public int getLogin_status() {
		return login_status;
	}
	public void setLogin_status(int login_status) {
		this.login_status = login_status;
	}
	public void setLast_loginfail_time(String last_loginfail_time) {
		this.last_loginfail_time = last_loginfail_time;
	}
	//@NotDbField
//	public String getSupplierAdminUserId() {
//		return supplierAdminUserId;
//	}
//	public void setSupplierAdminUserId(String supplierAdminUserId) {
//		this.supplierAdminUserId = supplierAdminUserId;
//	}
	public String getRelcode() {
		return relcode;
	}
	public void setRelcode(String relcode) {
		this.relcode = relcode;
	}
	public String getLan_id() {
		return lan_id;
	}
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public String getUserdept() {
		return userdept;
	}
	public void setUserdept(String userdept) {
		this.userdept = userdept;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDateline() {
		return dateline;
	}
	public void setDateline(String dateline) {
		this.dateline = dateline;
	}
	
	public Integer getFounder() {
		return founder;
	}
	public void setFounder(Integer founder) {
		this.founder = founder;
	}
	
	@NotDbField
	public String[] getRoleids() {
		return roleids;
	}
	public void setRoleids(String[] roleids) {
		this.roleids = roleids;
	}
	public Integer getSiteid() {
		return siteid;
	}
	public void setSiteid(Integer siteid) {
		this.siteid = siteid;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getParuserid() {
		return paruserid;
	}
	public void setParuserid(String paruserid) {
		this.paruserid = paruserid;
	}
	public String getCur_logintime() {
		return cur_logintime;
	}
	public void setCur_logintime(String cur_logintime) {
		this.cur_logintime = cur_logintime;
	}
	public String getLast_logintime() {
		return last_logintime;
	}
	public void setLast_logintime(String last_logintime) {
		this.last_logintime = last_logintime;
	}
	public int getFail_logincount() {
		return fail_logincount;
	}
	public void setFail_logincount(int fail_logincount) {
		this.fail_logincount = fail_logincount;
	}
	public int getSuccess_logincount() {
		return success_logincount;
	}
	public void setSuccess_logincount(int success_logincount) {
		this.success_logincount = success_logincount;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public String getReltype() {
		return reltype;
	}
	public void setReltype(String reltype) {
		this.reltype = reltype;
	}
	public String getRelno() {
		return relno;
	}
	public void setRelno(String relno) {
		this.relno = relno;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLan_name() {
		return lan_name;
	}
	public void setLan_name(String lan_name) {
		this.lan_name = lan_name;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getUser_level() {
		return user_level;
	}
	public void setUser_level(String user_level) {
		this.user_level = user_level;
	}
	public String getApply_time() {
		return apply_time;
	}
	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}
	public String getOpen_time() {
		return open_time;
	}
	public void setOpen_time(String open_time) {
		this.open_time = open_time;
	}
	public String getBoss_user_type() {
		return boss_user_type;
	}
	public void setBoss_user_type(String boss_user_type) {
		this.boss_user_type = boss_user_type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDesks() {
		return desks;
	}
	public void setDesks(String desks) {
		this.desks = desks;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getUser_pid() {
		return user_pid;
	}
	public void setUser_pid(String user_pid) {
		this.user_pid = user_pid;
	}
	@NotDbField
	public String getMenu_jsons() {
		return menu_jsons;
	}
	@NotDbField
	public void setMenu_jsons(String menu_jsons) {
		this.menu_jsons = menu_jsons;
	}
	
	@NotDbField
	public String getTheme_source_from() {
		return theme_source_from;
	}
	
	@NotDbField
	public void setTheme_source_from(String theme_source_from) {
		this.theme_source_from = theme_source_from;
	}
	@NotDbField
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	/**
	 * @return the contact_tel
	 */
	public String getContact_tel() {
		return contact_tel;
	}
	/**
	 * @param contact_tel the contact_tel to set
	 */
	public void setContact_tel(String contact_tel) {
		this.contact_tel = contact_tel;
	}
	/**
	 * @return the col1
	 */
	public String getCol1() {
		return col1;
	}
	/**
	 * @param col1 the col1 to set
	 */
	public void setCol1(String col1) {
		this.col1 = col1;
	}
	/**
	 * @return the col2
	 */
	public String getCol2() {
		return col2;
	}
	/**
	 * @param col2 the col2 to set
	 */
	public void setCol2(String col2) {
		this.col2 = col2;
	}
	/**
	 * @return the col3
	 */
	public String getCol3() {
		return col3;
	}
	/**
	 * @param col3 the col3 to set
	 */
	public void setCol3(String col3) {
		this.col3 = col3;
	}
	/**
	 * @return the col4
	 */
	public String getCol4() {
		return col4;
	}
	/**
	 * @param col4 the col4 to set
	 */
	public void setCol4(String col4) {
		this.col4 = col4;
	}
	/**
	 * @return the col5
	 */
	public String getCol5() {
		return col5;
	}
	/**
	 * @param col5 the col5 to set
	 */
	public void setCol5(String col5) {
		this.col5 = col5;
	}
	/**
	 * @return the col6
	 */
	public int getCol6() {
		return col6;
	}
	/**
	 * @param col6 the col6 to set
	 */
	public void setCol6(int col6) {
		this.col6 = col6;
	}
	/**
	 * @return the col7
	 */
	public int getCol7() {
		return col7;
	}
	/**
	 * @param col7 the col7 to set
	 */
	public void setCol7(int col7) {
		this.col7 = col7;
	}
	/**
	 * @return the col8
	 */
	public Timestamp getCol8() {
		return col8;
	}
	/**
	 * @param col8 the col8 to set
	 */
	public void setCol8(Timestamp col8) {
		this.col8 = col8;
	}
	/**
	 * @return the col9
	 */
	public String getCol9() {
		return col9;
	}
	/**
	 * @param col9 the col9 to set
	 */
	public void setCol9(String col9) {
		this.col9 = col9;
	}
	/**
	 * @return the col10
	 */
	public String getCol10() {
		return col10;
	}
	/**
	 * @param col10 the col10 to set
	 */
	public void setCol10(String col10) {
		this.col10 = col10;
	}
	public String getIs_login_first() {
		return is_login_first;
	}
	public void setIs_login_first(String is_login_first) {
		this.is_login_first = is_login_first;
	}
	public Integer getUsertype() {
		return usertype;
	}
	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}
	@NotDbField
	public String getTacheAuth() {
		return tacheAuth;
	}
	public void setTacheAuth(String tacheAuth) {
		this.tacheAuth = tacheAuth;
	}
	@NotDbField
	public String getReceiveUserAuth() {
		return receiveUserAuth;
	}
	public void setReceiveUserAuth(String receiveUserAuth) {
		this.receiveUserAuth = receiveUserAuth;
	}
	@NotDbField
	public String getLockUserAuth() {
		return lockUserAuth;
	}
	public void setLockUserAuth(String lockUserAuth) {
		this.lockUserAuth = lockUserAuth;
	}
	public String getBusicityAuth() {
		return busicityAuth;
	}
	public void setBusicityAuth(String busicityAuth) {
		this.busicityAuth = busicityAuth;
	}
	public String getSyn_dept_id() {
		return syn_dept_id;
	}
	public void setSyn_dept_id(String syn_dept_id) {
		this.syn_dept_id = syn_dept_id;
	}
	public String getSyn_hq_dept_id() {
		return syn_hq_dept_id;
	}
	public void setSyn_hq_dept_id(String syn_hq_dept_id) {
		this.syn_hq_dept_id = syn_hq_dept_id;
	}
	public String getSyn_dept_type() {
		return syn_dept_type;
	}
	public void setSyn_dept_type(String syn_dept_type) {
		this.syn_dept_type = syn_dept_type;
	}
	public String getSyn_county_id() {
		return syn_county_id;
	}
	public void setSyn_county_id(String syn_county_id) {
		this.syn_county_id = syn_county_id;
	}
	public String getSyn_user_id() {
		return syn_user_id;
	}
	public void setSyn_user_id(String syn_user_id) {
		this.syn_user_id = syn_user_id;
	}
	public String getSyn_employee_id() {
		return syn_employee_id;
	}
	public void setSyn_employee_id(String syn_employee_id) {
		this.syn_employee_id = syn_employee_id;
	}
	public String getSyn_use_domain() {
		return syn_use_domain;
	}
	public void setSyn_use_domain(String syn_use_domain) {
		this.syn_use_domain = syn_use_domain;
	}
	public String getIs_syn() {
		return is_syn;
	}
	public void setIs_syn(String is_syn) {
		this.is_syn = is_syn;
	}
	public String getSyn_date() {
		return syn_date;
	}
	public void setSyn_date(String syn_date) {
		this.syn_date = syn_date;
	}
	public String getSyn_remark() {
		return syn_remark;
	}
	public void setSyn_remark(String syn_remark) {
		this.syn_remark = syn_remark;
	}
	public String getPermission_level() {
		return permission_level;
	}
	public void setPermission_level(String permission_level) {
		this.permission_level = permission_level;
	}
	public List<String> getPermission_region() {
		return permission_region;
	}
	public void setPermission_region(List<String> permission_region) {
		this.permission_region = permission_region;
	}
	public List<String> getPermission_county() {
		return permission_county;
	}
	public void setPermission_county(List<String> permission_county) {
		this.permission_county = permission_county;
	}
	public List<String> getPerm_busi_county() {
		return perm_busi_county;
	}
	public void setPerm_busi_county(List<String> perm_busi_county) {
		this.perm_busi_county = perm_busi_county;
	}
	public String getSms_receive() {
		return sms_receive;
	}
	public void setSms_receive(String sms_receive) {
		this.sms_receive = sms_receive;
	}
}
