package com.rop.core.utils;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-08-30 11:37
 * To change this template use File | Settings | File Templates.
 * 常用正则表达式定义
 */
public interface ReguExp {
   /* 非负数："^([1-9]\d*|\d+\.\d+)$"
    只能输入数字："^[0-9]*$"。
    只能输入n位的数字："^\d{n}$"。
    只能输入至少n位的数字："^\d{n,}$"。
    只能输入m~n位的数字：。"^\d{m,n}$"
    只能输入零和非零开头的数字："^(0|[1-9][0-9]*)$"。
    只能输入有两位小数的正实数："^[0-9]+(.[0-9]{2})?$"。
    只能输入有1~3位小数的正实数："^[0-9]+(.[0-9]{1,3})?$"。
    只能输入非零的正整数："^\+?[1-9][0-9]*$"。
    只能输入非零的负整数："^\-[1-9][]0-9"*$。
    只能输入长度为3的字符："^.{3}$"。
    只能输入由26个英文字母组成的字符串："^[A-Za-z]+$"。
    只能输入由26个大写英文字母组成的字符串："^[A-Z]+$"。
    只能输入由26个小写英文字母组成的字符串："^[a-z]+$"。
    只能输入由数字和26个英文字母组成的字符串："^[A-Za-z0-9]+$"。
    只能输入由数字、26个英文字母或者下划线组成的字符串："^\w+$"。
    验证用户密码："^[a-zA-Z]\w{5,17}$"正确格式为：以字母开头，长度在6~18之间，只能包含字符、数字和下划线。
    验证是否含有^%&',;=?$\"等字符："[^%&',;=?$\x22]+"。
    只能输入汉字："^[\u4e00-\u9fa5]{0,}$"
    验证Email地址："^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$"。
    验证InternetURL："^http://%28[/\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$"。
    验证电话号码："^(\(\d{3,4}-)|\d{3.4}-)?\d{7,8}$"正确格式为："XXX-XXXXXXX"、"XXXX-XXXXXXXX"、"XXX-XXXXXXX"、"XXX-XXXXXXXX"、"XXXXXXX"和"XXXXXXXX"。
    验证身份证号（15位或18位数字）："^\d{15}|\d{18}$"。
    验证一年的12个月："^(0?[1-9]|1[0-2])$"正确格式为："01"～"09"和"1"～"12"。
    验证一个月的31天："^((0?[1-9])|((1|2)[0-9])|30|31)$"正确格式为；"01"～"09"和"1"～"31"。*/

    public static final String NO_NG_NUM = "^([1-9]\\d*|\\d+\\.\\d+)$";//非负数

    public static final String NO_NG_NUM_2_DECIMAL="(([1-9][\\d]*)(\\.[\\d]{1,2})?)|(0\\.[\\d]{1,2})";//非负数 含两位小数

    public static final String NUM = "^[0-9]*$";//数字

    public static final String ZERO_NUM="^(0|[1-9]\\d*)$";//0或大于零的正整数

    public static final String UP_ZERO_NUM="^\\+?[1-9][0-9]*$";//正整数

    public static final String DOWN_ZERO_NUM="^\\-[1-9][]0-9*$";//负整数

    public static final String EMAIL="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";//email

    public static final String INTERNET_URL="^http://%28[/\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$"; //internet url

    public static final String CHINESE_CHARACTER="^[\u4e00-\u9fa5]{0,}$"; //中文
}
