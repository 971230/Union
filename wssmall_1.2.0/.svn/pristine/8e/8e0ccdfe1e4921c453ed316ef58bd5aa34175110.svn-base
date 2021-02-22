package com.ztesoft.net.utils;

import com.ztesoft.ibss.ct.config.CTConfig;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.context.webcontext.WebSessionContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-08-21 10:57
 * To change this template use File | Settings | File Templates.
 */
public class EopUtils {
    private final static String ADMIN_USER_KEY = "admin_user_key";

    //返回javashop后台登陆用户
    public static AdminUser getCurrentAdminUser() {
        WebSessionContext<AdminUser> sessonContext = ThreadContextHolder.getSessionContext();
        if (null == sessonContext)
        	sessonContext= ThreadContextHolder.getHttpSessionContext();
        if (null == sessonContext)
        	return null;
        return sessonContext.getAttribute("admin_user_key");
    }

    //是否为超级管理员
    public static boolean checkSuper() {
        AdminUser user = getCurrentAdminUser();
        if (null == user) return false;

        if (user.getFounder() != 1) return false;

        return true;
    }

    //是否为省级管理员
    public static boolean checkProviceSuper() {
        AdminUser user = getCurrentAdminUser();
        if (null == user) return false;

        if (user.getFounder() != 2) return false;

        return true;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("PRoxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return StringUtils.isNotBlank(ip) ? ip : "";
    }


    /**
     * 正则验证
     */
    public static boolean check(String num, String exp) {
        if (StringUtils.isBlank(num) || StringUtils.isBlank(exp)) return false;

        Pattern pattern = Pattern.compile(exp);
        Matcher isNum = pattern.matcher(num);
        return isNum.matches();
    }

    /**
     * 获取随机数
     *
     * @param sublen
     * @return
     */
    public static int genRandom(int sublen) {
        double r = Math.random();
        double max = Math.pow(10.0, sublen);
        double min = Math.pow(10.0, sublen - 1);
        r = Math.floor(r * max);
        if (r < min) {
            return genRandom(sublen);
        }
        return (int) r;
    }


    public static boolean getMode() {


        return CTConfig.getInstance().getValue("MODE").equals("true") ? true : false;
    }

    public static String getRefer(HttpServletRequest request) {
        if (null == request) return "";

        String refer = request.getHeader("referer");
        if (StringUtils.isEmpty(refer)) refer = request.getHeader("Referer");

        return StringUtils.isEmpty(refer) ? "" : refer;
    }

    //按长度截取字符串
    public static List<String> subString(String str, int len) {
        int count = (str.length() % len) == 0 ? str.length() / len : (str.length() / len + 1);
        int start = 0, end = 0;
        List<String> list = new ArrayList<String>();
        for (start = 1; start <= count; start++) {
            end = start * len;
            if (end > str.length()) {
                end = str.length();
            }
            list.add(str.substring((start - 1) * len, end));
        }
        return list;
    }

    public static String htmlToText(String message) {
        if (StringUtils.isBlank(message)) {
            return (null);
        }
        char content[] = new char[message.length()];
        message.getChars(0, message.length(), content, 0);
        StringBuffer result = new StringBuffer(content.length + 50);
        for (int i = 0; i < content.length; i++) {
            switch (content[i]) {
                case '<':
                    result.append("&lt;");
                    break;
                case '>':
                    result.append("&gt;");
                    break;
                case '&':
                    result.append("&amp;");
                    break;
                case '"':
                    result.append("&quot;");
                    break;
                default:
                    result.append(content[i]);
            }
        }
        return (result.toString());

    }

    public static String decodeString(String strData)
    {
        strData = replaceString(strData, "&lt;", "<");
        strData = replaceString(strData, "&gt;", ">");
        strData = replaceString(strData, "&apos;", "&apos;");
        strData = replaceString(strData, "&quot;", "\"");
        strData = replaceString(strData, "&amp;", "&");
        strData=replaceString(strData,"＝","=");
        return strData;
    }

    private static String replaceString(String strData, String regex, String replacement) {
        if (strData == null) {
            return null;
        }
        int index;
        index = strData.indexOf(regex);
        String strNew = "";
        if (index >= 0) {
            while (index >= 0) {
                strNew += strData.substring(0, index) + replacement;
                strData = strData.substring(index + regex.length());
                index = strData.indexOf(regex);
            }
            strNew += strData;
            return strNew;
        }
        return strData;
    }

    /**
     * 获取完整请求路径(含内容路径及请求参数)
     * @param request
     * @return
     */
    public static String getRequestURIWithParam(HttpServletRequest request){
        return getRequestURI(request) + (StringUtils.isBlank(request.getQueryString()) ? "" : "?"+ request.getQueryString());
    }

    /***
     * 获取URI的路径,如路径为http://www.babasport.com/action/post.htm?method=add, 得到的值为"/action/post.htm"
     * @param request
     * @return
     */
    public static String getRequestURI(HttpServletRequest request){
        return request.getRequestURI();
    }
}
