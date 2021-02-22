package com.ztesoft.remote.inf;

import com.ztesoft.net.model.SendSms;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-01-23 14:59
 * To change this template use File | Settings | File Templates.
 */
public interface IRemoteSmsService {

    //获取短信模板
    public String getSMSTemplate(String key, Map param);

    //添加短信表
    public String save(SendSms sms);

    public String save(String msgStr, String acc_nbr);
    
    public String save(String msgStr, String acc_nbr, String code);
    
    public boolean updateState(String sendNo);
    
    public SendSms querySmsById(String sendNo);
    
    public boolean deleteSms(List<String> list);

}
