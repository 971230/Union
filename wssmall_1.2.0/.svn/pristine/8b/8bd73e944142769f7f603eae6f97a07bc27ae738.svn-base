/**
 * 日期：12-2-10
 */
package com.ztesoft.rop.common;

/**
 * 支持的响应的格式类型
 */
public enum MessageFormat {

    xml, json,fjson,httpjson,httpxml;

    public static MessageFormat getFormat(String value) {
        if ("json".equalsIgnoreCase(value)) {
            return json;
        } if ("fjson".equalsIgnoreCase(value)) {
            return fjson;
        }if ("httpjson".equalsIgnoreCase(value)) {
            return httpjson;
        } if("httpxml".equalsIgnoreCase(value)){
        	return httpxml;
        } else {
            return xml;
        }
    }

    public static boolean isValidFormat(String value) {
        return xml.name().equalsIgnoreCase(value) || json.name().equalsIgnoreCase(value) 
        		|| fjson.name().equalsIgnoreCase(value) || httpjson.name().equalsIgnoreCase(value) || httpxml.name().equalsIgnoreCase(value);
    }

}
