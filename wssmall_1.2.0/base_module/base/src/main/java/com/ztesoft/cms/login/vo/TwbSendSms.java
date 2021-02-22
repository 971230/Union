package com.ztesoft.cms.login.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-20
 * Time: 下午2:59
 * To change this template use File | Settings | File Templates.
 */
public class TwbSendSms implements Serializable {
    private String send_no="";//唯一标识

    private String send_num;//发送号码

    private String recv_num;//接收号码

    private String smgp_charge_nbr;//计费号码

    private String send_content;//发送内容

    private Date plan_send_date=new Date(System.currentTimeMillis());//计划发送时间

    private String send_date;//发送时间

    private String acct_num;//账单号码

    private int send_count=1;//发送次数

    private String acct_cityno;

    private String sms_opertype="";//类型

    private String state="0";// 0 1 2

    private String deal_count;

    private String acct_month;//帐期

    private String notes;

    private Date create_time=new Date(System.currentTimeMillis());//创建时间

    private String resend_count;

    private String feetype;//付费类型

    private String area_code;//本地网编码

    private String rand_type;

    private String resend_flag;


    public String getSend_no() {
        return send_no;
    }

    public void setSend_no(String send_no) {
        this.send_no = send_no;
    }

    public String getSend_num() {
        return send_num;
    }

    public void setSend_num(String send_num) {
        this.send_num = send_num;
    }

    public String getRecv_num() {
        return recv_num;
    }

    public void setRecv_num(String recv_num) {
        this.recv_num = recv_num;
    }

    public int getSend_count() {
        return send_count;
    }

    public void setSend_count(int send_count) {
        this.send_count = send_count;
    }

    public String getSmgp_charge_nbr() {
        return smgp_charge_nbr;
    }

    public void setSmgp_charge_nbr(String smgp_charge_nbr) {
        this.smgp_charge_nbr = smgp_charge_nbr;
    }

    public String getSend_content() {
        return send_content;
    }

    public void setSend_content(String send_content) {
        this.send_content = send_content;
    }


    public String getSend_date() {
        return send_date;
    }

    public void setSend_date(String send_date) {
        this.send_date = send_date;
    }

    public String getAcct_num() {
        return acct_num;
    }

    public void setAcct_num(String acct_num) {
        this.acct_num = acct_num;
    }


    public String getAcct_cityno() {
        return acct_cityno;
    }

    public void setAcct_cityno(String acct_cityno) {
        this.acct_cityno = acct_cityno;
    }

    public String getSms_opertype() {
        return sms_opertype;
    }

    public void setSms_opertype(String sms_opertype) {
        this.sms_opertype = sms_opertype;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDeal_count() {
        return deal_count;
    }

    public void setDeal_count(String deal_count) {
        this.deal_count = deal_count;
    }

    public String getAcct_month() {
        return acct_month;
    }

    public void setAcct_month(String acct_month) {
        this.acct_month = acct_month;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getResend_count() {
        return resend_count;
    }

    public void setResend_count(String resend_count) {
        this.resend_count = resend_count;
    }

    public String getFeetype() {
        return feetype;
    }

    public void setFeetype(String feetype) {
        this.feetype = feetype;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getRand_type() {
        return rand_type;
    }

    public void setRand_type(String rand_type) {
        this.rand_type = rand_type;
    }

    public String getResend_flag() {
        return resend_flag;
    }

    public void setResend_flag(String resend_flag) {
        this.resend_flag = resend_flag;
    }
}
