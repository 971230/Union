package com.ztesoft.net.framework.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * json数据结果生成成工具
 * 结果总是返回result 为1表示成功 为0表示失败
 *
 * @author kingapex
 */
public class JsonMessageUtil {
    private static final String RESULT="result";
    private static final String DATA="data";
    private static final String MESSAGE="message";


    public static String getObjectJson(Object object) {
        if (object == null) {
            return getErrorJson("object is null");
        }
        JSONObject json = new JSONObject();
        json.put(RESULT, 1);
        json.put(DATA,JSON.toJSONString(object));

        return json.toString();
    }

    public static String getListJson(List list) {
        if (list == null) {
            return getErrorJson("list is null");
        }
        JSONObject json=new JSONObject();
        json.put(RESULT,1);
        json.put(DATA, JSON.toJSONString(list));

        return json.toString();
    }

    public static String getErrorJson(String message) {
        JSONObject json=new JSONObject();
        json.put(RESULT,0);
        json.put(MESSAGE, message);
        return json.toString();
    }

    public static String getSuccessJson(String message) {
        JSONObject json=new JSONObject();
        json.put(RESULT,1);
        json.put(MESSAGE, message);
        return json.toString();
    }
}
