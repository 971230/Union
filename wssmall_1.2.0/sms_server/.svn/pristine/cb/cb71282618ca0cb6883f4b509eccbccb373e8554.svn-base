package com.ztesoft.net.config;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.ibss.common.util.Const;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-10-10 20:44
 * To change this template use File | Settings | File Templates.
 */
public class SmsTempleteConfig {
    private static Map<String, String> map = new HashMap<String, String>();

    private static class SmsTempleteConfigClassLoader {
        public static SmsTempleteConfig config = new SmsTempleteConfig();
    }

    public static SmsTempleteConfig instance() {
        return SmsTempleteConfigClassLoader.config;
    }

    private SmsTempleteConfig() {
    }

    public String getTemplete(String key) {
        return Const.getStrValue(map, key);
    }

    public void clear() {
        map.clear();
    }

    public void setTemplete(String key,String vlalue){
        map.put(key,vlalue);
    }


}
