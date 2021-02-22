package com.ztesoft.net.sms.service;

import com.ztesoft.net.model.SendSms;
import com.ztesoft.net.model.SmsUser;

import java.util.List;
import java.util.Map;

/**
 * 短信接口
 *
 * @author chen.zewen
 */
public interface ISmsManager {
    public static final String SYSDATE = "sysdate";
    public static final String RANDOM_TIME = "RANDOM_TIME";//短信随机码有效期
    public static final String SMS_USER_PWD="SMS_USER_PWD";//短信用户密码

    //获取短信模板
    public String getSMSTemplate(String key, Map param);

    //添加短信表
    public String save(SendSms sms);

    public String save(String msgStr, String acc_nbr);
    
    public String save(String msgStr, String acc_nbr, String code);

    /**
     * 短信发送次数超过6次后   将state改为0
     *
     * @param arg
     * @return
     */
    public boolean updateState(String arg);

    public boolean updateState(List<String> list);

    //修改发送次数
    public boolean updateSendCount(String key);

    public boolean updateSendCount(List<String> list);


    //删除短信
    public boolean delete(String send_no);

    public boolean delete(List<String> param);

    /**
     * 获取要发送短信列表
     *
     * @return
     */
    public List<SendSms> getSendList();

    public String getSmsNo();

    //修改历史表中的数据
    public boolean updateHisState(String arg);

    //获取短信对象
    public SendSms querySmsById(String send_no);

    //获取验证码短信对象
    public SendSms querySmsCodeById(String send_no);

    //获取验证码短信对象
    public SendSms querySmsCodeById(String send_no,int time);


    //获取短信用户名 密码
    public SmsUser getSmsUser() throws Exception;
}
