package com.ztesoft.common.util;
//package com.ztesoft.common.util;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Pattern;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//
//import com.powerise.ibss.system.TsmStaff;
//
//
//public class WebUtils {
//    private Logger logger = Logger.getLogger(WebUtils.class);
//
//    public static String getRandomPwd(HttpSession session) {
//        String rval = null;
//        if (null != session) {
//            Object obj = session.getAttribute(LoginAction.KEY.RANDOM_PWD);
//            if (null != obj) {
//                rval = obj.toString();
//            }
//        }
//        return rval;
//    }
//
//
//    /*
//    * 得到当前用户
//    * */
//    public static TsmStaff getCurrentUser(HttpServletRequest request) {
//        if (null == request) return null;
//        TsmStaff staff = null;
//        HttpSession session = request.getSession(false);
//        if (null != session) {
//            Object object = session.getAttribute(LoginAction.KEY.CURRENT_USER);
//            if (null != object) {
//                staff = (TsmStaff) object;
//            }
//        }
//
//        return staff;
//    }
//
//    public static TsmStaff getCurrentUser(HttpSession session) {
//        TsmStaff staff = null;
//        if (null != session) {
//            Object object = session.getAttribute(LoginAction.KEY.CURRENT_USER);
//            if (null != object) {
//                staff = (TsmStaff) object;
//            }
//        }
//        return staff;
//    }
//
//
//    /*
//    * 得到用户角色
//    * */
//    public static List getRole(HttpServletRequest request) {
//        if (null == request) return null;
//        HttpSession session = request.getSession(false);
//        List list = null;
//        if (null != session) {
//            Object object = session.getAttribute(LoginAction.KEY.CURRENT_USER_ROLE);
//            if (null != object) {
//                list = (List) object;
//            }
//        }
//        return list;
//    }
//
//    /*
//     * 得到用户功能点
//     * */
//    public static List getFunctions(HttpServletRequest request) {
//        if (null == request) return null;
//        HttpSession session = request.getSession(false);
//        List list = null;
//        if (null != session) {
//            Object object = session.getAttribute(LoginAction.KEY.CURRENT_USER_FUNCTIONS);
//            if (null != object) {
//                list = (List) object;
//            }
//        }
//        return list;
//    }
//
//    /*
//   * 得到用户待审核列表
//   * */
//    public static List getVeruifyList(HttpServletRequest request) {
//        if (null == request) return null;
//        List list = null;
//        HttpSession session = request.getSession(false);
//        if (null != session) {
//            Object object = session.getAttribute(LoginAction.KEY.CURRENT_USER_VERIFY_LIST);
//            if (null != object) {
//                list = (List) object;
//            }
//        }
//
//        return list;
//    }
//
//
//    /**
//     * 校验管理员ip是否在ibss.xml的配置允许的ip段范围，两个ip之间用"|"竖线分割<br>
//     * 例如：<ADMIN_IP>134.224.0.0|134.224.255.255</ADMIN_IP>
//     *
//     * @param request
//     * @return
//     */
//    public static boolean checkAdminIP(HttpServletRequest request) {
//        String adminIp = SysIbssUtils.getAdminIP();
//
//        if (StringUtils.isNotEmpty(adminIp) || adminIp.split("\\|").length != 2) return false;
//
//        String ips[] = adminIp.split("\\|");
//        long beginIp = translateIP(ips[0]);
//        long endIp = translateIP(ips[1]);
//        long userIp = translateIP(getRequestIp(request));
//        if (userIp > beginIp && userIp < endIp) {
//            return true;
//        }
//
//        return true;
//    }
//
//    /*获取用户ip*/
//    public static String getRequestIp(HttpServletRequest request) {
//        // 取IP地址
//        String ip = request.getRemoteAddr();
//        return StringUtils.isNotEmpty(ip) ? ip : "";
//    }
//
//    /*ip转为数字**/
//    protected static long translateIP(String ip) {
//        String regex = "\\.";
//        String[] str = ip.split(regex);
//        long result = 0;
//        for (int i = 0; i < str.length; i++) {
//            int temp = Integer.parseInt(str[i]);
//            //int size = str[i].length();
//
//            result += temp;
//            result *= 1000;
//        }
//
//        return result / 1000;
//    }
//
//
//    /**
//     * 获取URI的路径,如路径为http://www.babasport.com/action/post.htm?method=add, 得到的值为"/action/post.htm"
//     *
//     * @param request
//     * @return
//     */
//    public static String getRequestURI(HttpServletRequest request) {
//        return request.getRequestURI();
//    }
//
//    /**
//     * 获取完整请求路径(含内容路径及请求参数)
//     *
//     * @param request
//     * @return
//     */
//    public static String getRequestURIWithParam(HttpServletRequest request) {
//        return getRequestURI(request) + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
//    }
//
//    /**
//     * 添加cookie
//     *
//     * @param response
//     * @param name     cookie的名称
//     * @param value    cookie的值
//     * @param maxAge   cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 如果值为0,cookie将随浏览器关闭而清除)
//     */
//    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
//        Cookie cookie = new Cookie(name, value);
//        cookie.setPath("/");
//        if (maxAge > 0) cookie.setMaxAge(maxAge);
//        response.addCookie(cookie);
//    }
//
//    /**
//     * 获取cookie的值
//     *
//     * @param request
//     * @param name    cookie的名称
//     * @return
//     */
//    public static String getCookieByName(HttpServletRequest request, String name) {
//        Map cookieMap = WebUtils.readCookieMap(request);
//        if (cookieMap.containsKey(name)) {
//            Cookie cookie = (Cookie) cookieMap.get(name);
//            return cookie.getValue();
//        }
//        return null;
//    }
//
//    /*
//     * 读取cookie
//     * **/
//    protected static Map readCookieMap(HttpServletRequest request) {
//        Map cookieMap = new HashMap();
//        Cookie[] cookies = request.getCookies();
//        if (null != cookies) {
//            for (int i = 0; i < cookies.length; i++) {
//                cookieMap.put(cookies[i].getName(), cookies[i]);
//            }
//        }
//        return cookieMap;
//    }
//
//
//    /**
//     * 去除html代码
//     *
//     * @param inputString
//     * @return
//     */
//    public static String HtmltoText(String inputString) {
//        String htmlStr = inputString; //含html标签的字符串
//        String textStr = "";
//        java.util.regex.Pattern p_script;
//        java.util.regex.Matcher m_script;
//        java.util.regex.Pattern p_style;
//        java.util.regex.Matcher m_style;
//        java.util.regex.Pattern p_html;
//        java.util.regex.Matcher m_html;
//        java.util.regex.Pattern p_ba;
//        java.util.regex.Matcher m_ba;
//
//        try {
//            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
//            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
//            String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
//            String patternStr = "\\s+";
//
//            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
//            m_script = p_script.matcher(htmlStr);
//            htmlStr = m_script.replaceAll(""); //过滤script标签
//
//            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
//            m_style = p_style.matcher(htmlStr);
//            htmlStr = m_style.replaceAll(""); //过滤style标签
//
//            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
//            m_html = p_html.matcher(htmlStr);
//            htmlStr = m_html.replaceAll(""); //过滤html标签
//
//            p_ba = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
//            m_ba = p_ba.matcher(htmlStr);
//            htmlStr = m_ba.replaceAll(""); //过滤空格
//
//            textStr = htmlStr;
//
//        } catch (Exception e) {
//            System.err.println("Html2Text: " + e.getMessage());
//        }
//        return textStr;//返回文本字符串
//    }
//
//    //写入cookie值 以‘-’进行分割
//    public static void addCookie(String key, String value, HttpServletResponse response, HttpServletRequest request,int maxAge) {
//       try{
//           String result = getCookieByName(request, key);
//           if(StringUtils.isBlank(result)){
//               value=value+"-";
//               addCookie(response,key,value,maxAge);
//           }else {
//               List<String> list= Arrays.asList(result.split("\\-"));
//               List<String> history=new ArrayList<String>(list);
//               if(history.size()>5){
//                   history.remove(0);
//               }
//               if(!history.contains(value)){
//                   history.add(value);
//                   StringBuilder builder=new StringBuilder(200);
//                   for(String val : history){
//                       builder.append(val);
//                       builder.append("-");
//                   }
//                   builder.deleteCharAt(builder.length()-1);
//                   addCookie(response,key,builder.toString(),maxAge);
//               }
//           }
//       }catch (Exception e){
//           e.printStackTrace();
//       }
//    }
//}
