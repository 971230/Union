package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;



/**
 *
 * @author wu.i
 * 对账实体对象
 *
 */
public class CheckAcctConfig implements java.io.Serializable {


    private String system_id;
    private String a_file_remote_path;
    private String a_file_local_path;
    private String a_file_tail;
    private String a_shop_no;


    private String city_no;
    private String staff_id;

    //平台对账后文件信息
    private String bns_local_file_path;
    private String bns_remote_file_path;
    private String bsn_filehead;
    private String bsn_filetail;


    private String up_ftp_addr;
    private String up_ftp_port;
    private String up_ftp_user;
    private String up_ftp_passwd;

    private String boname;
    private String beginTime;
    private String endTime;


    private String billdzfilename;
    private String accounts_id;

    private String a_file_name; //对账文件名

    private int bill_error_num;//异常单预警数量
    private double bill_error_money;//金额异常预警，单位为分
    private int charge_error_num;//流量卡、充值卡异常预警数量


    private String bns_filehead;
    private String bns_filetail;
    private String state;
    private String banknmae;
    private String reciv_nums;
    private String source_from;
    private String system_name;
    private String job_time;
    private String create_time;

    public int getBill_error_num() {
        return bill_error_num;
    }
    public void setBill_error_num(int bill_error_num) {
        this.bill_error_num = bill_error_num;
    }
    public double getBill_error_money() {
        return bill_error_money;
    }
    public void setBill_error_money(double bill_error_money) {
        this.bill_error_money = bill_error_money;
    }
    public int getCharge_error_num() {
        return charge_error_num;
    }
    public void setCharge_error_num(int charge_error_num) {
        this.charge_error_num = charge_error_num;
    }
    public String getA_file_remote_path() {
        return a_file_remote_path;
    }
    public void setA_file_remote_path(String a_file_remote_path) {
        this.a_file_remote_path = a_file_remote_path;
    }
    public String getA_file_local_path() {
        return a_file_local_path;
    }
    public void setA_file_local_path(String a_file_local_path) {
        this.a_file_local_path = a_file_local_path;
    }

    public String getA_file_tail() {
        return a_file_tail;
    }
    public void setA_file_tail(String a_file_tail) {
        this.a_file_tail = a_file_tail;
    }
    public String getA_shop_no() {
        return a_shop_no;
    }
    public void setA_shop_no(String a_shop_no) {
        this.a_shop_no = a_shop_no;
    }
    public String getCity_no() {
        return city_no;
    }
    public void setCity_no(String city_no) {
        this.city_no = city_no;
    }
    public String getStaff_id() {
        return staff_id;
    }
    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }
    public String getBns_local_file_path() {
        return bns_local_file_path;
    }
    public void setBns_local_file_path(String bns_local_file_path) {
        this.bns_local_file_path = bns_local_file_path;
    }
    public String getBns_remote_file_path() {
        return bns_remote_file_path;
    }
    public void setBns_remote_file_path(String bns_remote_file_path) {
        this.bns_remote_file_path = bns_remote_file_path;
    }
    public String getBsn_filehead() {
        return bsn_filehead;
    }
    public void setBsn_filehead(String bsn_filehead) {
        this.bsn_filehead = bsn_filehead;
    }
    public String getBsn_filetail() {
        return bsn_filetail;
    }
    public void setBsn_filetail(String bsn_filetail) {
        this.bsn_filetail = bsn_filetail;
    }
    public String getUp_ftp_addr() {
        return up_ftp_addr;
    }
    public void setUp_ftp_addr(String up_ftp_addr) {
        this.up_ftp_addr = up_ftp_addr;
    }
    public String getUp_ftp_port() {
        return up_ftp_port;
    }
    public void setUp_ftp_port(String up_ftp_port) {
        this.up_ftp_port = up_ftp_port;
    }
    public String getUp_ftp_user() {
        return up_ftp_user;
    }
    public void setUp_ftp_user(String up_ftp_user) {
        this.up_ftp_user = up_ftp_user;
    }
    public String getUp_ftp_passwd() {
        return up_ftp_passwd;
    }
    public void setUp_ftp_passwd(String up_ftp_passwd) {
        this.up_ftp_passwd = up_ftp_passwd;
    }
    public String getBoname() {
        return boname;
    }
    public void setBoname(String boname) {
        this.boname = boname;
    }
    public String getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @NotDbField
    public String getBilldzfilename() {
        return billdzfilename;
    }
    @NotDbField
    public void setBilldzfilename(String billdzfilename) {
        this.billdzfilename = billdzfilename;
    }
    public String getAccounts_id() {
        return accounts_id;
    }
    public void setAccounts_id(String accounts_id) {
        this.accounts_id = accounts_id;
    }


    public String getSystem_id() {
        return system_id;
    }
    public void setSystem_id(String system_id) {
        this.system_id = system_id;
    }
    @NotDbField
    public String getA_file_name() {
        return a_file_name;
    }
    @NotDbField
    public void setA_file_name(String a_file_name) {
        this.a_file_name = a_file_name;
    }
    public String getBns_filehead() {
        return bns_filehead;
    }
    public void setBns_filehead(String bns_filehead) {
        this.bns_filehead = bns_filehead;
    }
    public String getBns_filetail() {
        return bns_filetail;
    }
    public void setBns_filetail(String bns_filetail) {
        this.bns_filetail = bns_filetail;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getBanknmae() {
        return banknmae;
    }
    public void setBanknmae(String banknmae) {
        this.banknmae = banknmae;
    }
    
    public String getReciv_nums() {
        return reciv_nums;
    }
    public void setReciv_nums(String reciv_nums) {
        this.reciv_nums = reciv_nums;
    }
    public String getSource_from() {
        return source_from;
    }
    public void setSource_from(String source_from) {
        this.source_from = source_from;
    }
    public String getSystem_name() {
        return system_name;
    }
    public void setSystem_name(String system_name) {
        this.system_name = system_name;
    }
    public String getJob_time() {
        return job_time;
    }
    public void setJob_time(String job_time) {
        this.job_time = job_time;
    }
    public String getCreate_time() {
        return create_time;
    }
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

}