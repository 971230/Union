package com.ztesoft.ibss.common.util;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StrTools {
	private static Logger logger = Logger.getLogger(StrTools.class);
	private StrTools() {
	}

	/**
	 * 用16进制表示给定的byte数组
	 * @param b byte[]
	 * @return String
	 */
	public static String bytes2HexString(byte[] b) {
		String hexStr = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = "0" + hex;
			}
			hexStr += hex;
		}
		return hexStr.toUpperCase();
	}

	/**
	 * 在字符串str左边补齐0直到长度等于length
	 *
	 * @param str
	 * @param len
	 * @return
	 */
	public static String enoughZero(String str, int len) {
		while (str.length() < len) {
			str = "0" + str;
		}
		return str;
	}

	public static String join(String seperator, String[] strings) {
		if (strings == null)
			return "";
		int length = strings.length;
		if (length == 0)
			return "";
		StringBuffer buf = new StringBuffer(length * strings[0].length())
				.append(strings[0]);
		for (int i = 1; i < length; i++) {
			buf.append(seperator).append(strings[i]);
		}
		return buf.toString();
	}

	public static String join(String seperator, Iterator objects) {
		StringBuffer buf = new StringBuffer();
		if (objects.hasNext())
			buf.append(objects.next());
		while (objects.hasNext()) {
			buf.append(seperator).append(objects.next());
		}
		return buf.toString();
	}

	public static String joinWithQMarks(String seperator, String[] strings) {

		List list = Arrays.asList(strings);
		return joinWithQMarks(seperator, list.iterator());
	}

	public static String joinWithQMarks(String seperator, Iterator objects) {
		StringBuffer buf = new StringBuffer();
		if (objects.hasNext())
			buf.append("'").append(objects.next()).append("'");
		while (objects.hasNext()) {
			buf.append(seperator).append("'").append(objects.next())
					.append("'");
		}
		return buf.toString();
	}

	public static boolean booleanValue(String tfString) {
		String trimmed = tfString.trim().toLowerCase();
		return trimmed.equals("true") || trimmed.equals("t");
	}

	public static boolean isEqual(String o, boolean c) {

		if (o == null || "".equals(o))
			return false;
		return o.equals(String.valueOf(c).toLowerCase());

	}

	public static boolean isEqual(String o, String c) {
		if (StrTools.isEmpty(o)) {
			o = "";
		}
		if (StrTools.isEmpty(c)) {
			c = "";
		}
		return o.equals(c);
	}

	public static String toString(Object[] array) {
		int len = array.length;
		if (len == 0)
			return "";
		StringBuffer buf = new StringBuffer(len * 12);
		for (int i = 0; i < len - 1; i++) {
			buf.append(array[i]).append(", ");
		}
		return buf.append(array[len - 1]).toString();
	}

	public static String[] toArray(List list) {
		int len = list.size();
		if (len == 0)
			return null;
		String[] array = new String[len];
		for (int i = 0; i < len; i++) {
			array[i] = list.get(i).toString();
		}
		return array;
	}

	public static boolean isNotEmpty(String string) {
		return string != null && string.length() > 0;
	}

	public static boolean isEmpty(String string) {
		return string == null || string.length() == 0;
	}

	public static String truncate(String string, int length) {
		if (string.length() <= length) {
			return string;
		} else {
			return string.substring(0, length);
		}
	}

	public static String getStrValue(Map m, String name) {
		Object t = m.get(name);
		if (t == null)
			return "";
		return ((String) m.get(name)).trim();
	}

	public static String toUpperCase(String str) {
		return str == null ? null : str.toUpperCase();
	}

	public static String toLowerCase(String str) {
		return str == null ? null : str.toLowerCase();
	}

	public static HashMap toMap(String[] array) {

		if (array == null)
			return null;
		int len = array.length;

		if (len == 0)
			return null;

		HashMap map = new HashMap();

		for (int i = 0; i < len; i++) {
			map.put(array[i], array[i]);
		}
		return map;

	}/*--repl将被with替换 -1为替换字符串text中所有的repl--*/

	public static String replace(String text, String repl, String with) {
		return replace(text, repl, with, -1);
	}

	/*--max为被替换的个数--*/
	public static String replace(String text, String repl, String with, int max) {
		if (text == null || repl == null || with == null || max == 0) {
			return text;
		}

		StringBuffer buf = new StringBuffer(text.length());
		int start = 0, end = 0;
		while ((end = text.indexOf(repl, start)) != -1) {
			buf.append(text.substring(start, end)).append(with);
			start = end + repl.length();

			if (--max == 0) {
				break;
			}
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	/**
	 * 将两个字符串转换成数字类型 返回大的那个
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String max(String str1, String str2) {
		int num1 = Integer.parseInt(str1);
		int num2 = Integer.parseInt(str2);
		if (num1 > num2) {
			return str1;
		} else {
			return str2;
		}
	}

	/**
	 * 将两个字符串转换成数字类型 返回小的那个
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String min(String str1, String str2) {
		int num1 = Integer.parseInt(str1);
		int num2 = Integer.parseInt(str2);
		if (num1 > num2) {
			return str2;
		} else {
			return str1;
		}
	}

	/**
	 * 校验是否为数字
	 * */
	public static boolean isNum(String num) {

		if (num == null || num.equals(""))
			return false;

		if (num.startsWith("-")) {
			return isNum(num.substring(1));
		}

		Pattern pattern = Pattern.compile("^\\d+$");
		Matcher isNum = pattern.matcher(num);
		return isNum.matches();
	}

	/**
	 * 校验字符串是否匹配正则表达式
	 * */
	public static boolean isMatche(String checkStr, String regex) {

		if (checkStr == null || checkStr.equals(""))
			return false;

		Pattern pattern = Pattern.compile(regex);
		Matcher isMathe = pattern.matcher(checkStr);
		return isMathe.matches();
	}

	//浮点数
	public static boolean isFloat(String num) {
		if (isNum(num))
			return true;
		String reg = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";

		if (num == null || num.equals(""))
			return false;

		if (num.startsWith("-")) {
			return isMatche(num.substring(1), reg);
		}
		return isMatche(num, reg);
	}

	//手机号码
	public static boolean isPhoneNo(String checkStr) {
		String regex = "^1[3|4|5|8][0-9]\\d{8}$";
		return isMatche(checkStr, regex);
	}

	//防止如包含误判. 如判断"1234,222"是否包含"123",如果直接用String.indexOf()方法,会认为包含
	//我们在源字符串和子字符串的前后都加上分隔标志,变成判断",1234,222,"中是否包含",123,",这样就不会误判了

	/**
	 * @Title: isIndexOf
	 * @Description: 判断包含关系
	 * @param   str  源字符串
	 * @param   subStr 子字符串
	 * @param   splitFlag 源字符串的分隔标志
	 * @return  true 包含   false  不包含
	 * @throws
	 */
	public static boolean isIndexOf(String str, String subStr, String splitFlag) {

		if (isEmpty(str)) {
			return false;
		}

		//子字符串为空,认为包含
		if (isEmpty(subStr)) {
			return true;
		}

		if (null == splitFlag) {
			splitFlag = "";
		}

		String tmpStr = splitFlag + str + splitFlag;
		String tmpSubStr = splitFlag + subStr + splitFlag;

		return tmpStr.indexOf(tmpSubStr) >= 0;

	}

	public static void main(String[] args) {
		logger.info(StrTools.isFloat("0"));
		logger.info(StrTools.isFloat("4.3"));
		logger.info(StrTools.isFloat("43.4"));
		logger.info(StrTools.isFloat("4.3.4"));
		logger.info(StrTools.isFloat("-2.3"));

		//		logger.info(isNum("-2"));
	}

	/**
	 * 克隆String
	 * @param warn
	 * @return
	 */
	public static Object cloneMySelf(Object object) {
		Object cloneObj = null;
		ObjectOutputStream oo = null;
		ObjectInputStream oi = null;
		ByteArrayInputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			oo = new ObjectOutputStream(out);
			oo.writeObject(object);
			in = new ByteArrayInputStream(out.toByteArray());
			oi = new ObjectInputStream(in);
			cloneObj = oi.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oo != null) {
					oo.close();
				}
				if (oi != null) {
					oi.close();
				}
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return cloneObj;
	}

	//把一个HashMap拼凑成key:val|..的格式
	public static String hashMapToString(HashMap sqlGetKeyVals) {
		StringBuffer data = new StringBuffer("");
		if (sqlGetKeyVals != null)
			for (Iterator it = sqlGetKeyVals.entrySet().iterator(); it
					.hasNext();) {
				Map.Entry map = (Map.Entry) it.next();
				String key = (String) map.getKey();
				String val = (String) map.getValue();
				data.append(key);
				data.append(":");
				data.append(val);
				data.append("|");
			}
		return data.toString();
	}

	//把一个key:val|..格式的String拼凑成HashMap
	public static HashMap StringToHashMap(String str) {
		if (str == null || str.equals(""))
			return null;
		String[] _hashmap = str.split("\\|");
		if (_hashmap == null || _hashmap.length == 0)
			return null;
		HashMap ret = new HashMap();
		for (int i = 0; i < _hashmap.length; i++) {
			String it = _hashmap[i];
			if (it == null || it.equals(""))
				continue;
			String[] _maps = it.split(":");
			if (_maps == null || _maps.length < 1)
				continue;
			String key = _maps[0];
			String val = "";
			if (_maps.length == 2)
				val = _maps[1];
			ret.put(key, val);
		}
		return ret;
	}

	/**
	 * 判断s 是否包含 value
	 * @param s
	 * @param value
	 * @return boolean
	 */
	public static boolean isIncluded(String[] s, String value) {
		boolean result = false;
		if (s == null || s.length == 0) {
			return false;
		}
		for (int i = 0; i < s.length; i++) {
			if (value.equals(s[i])) {
				result = true;
				return result;
			}
		}
		return result;
	}

	/**
	 * //根据随机数长度生成随机数
	 * @param pwdLen
	 * @return
	 */
	public static String genRandowNum(int pwdLen) {

		if (pwdLen <= 0) {
			return "";
		}
		int maxNumber = 1;
		int minNumber = 0;
		int randomValue = 0;

		//生成随机数能取的最大值+1, 如1000000
		for (int i = 0; i < pwdLen; i++) {
			maxNumber = maxNumber * 10;
		}

		//生成随机数能取的最小数-1: 如 99999;
		if (pwdLen > 1) {
			minNumber = 1;
			for (int i = 0; i < pwdLen - 1; i++) {
				minNumber = minNumber * 10;
			}
			minNumber = minNumber - 1;
		}

		while (true) {
			randomValue = (int) Math.ceil(Math.random() * maxNumber);
			if ((randomValue > minNumber) && (randomValue < maxNumber)) {
				return randomValue + "";
			}
		}
	}

	public static boolean isMatcheIP(String ip) {
		String patterStr = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		return isMatche(ip, patterStr);
	}
	
	public static String getIpAddress(){
		String ipAddress="127.0.0.1";
		MessageContext mc = MessageContext.getCurrentContext();   
		HttpServletRequest request = (HttpServletRequest)mc.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
		ipAddress=request.getRemoteAddr();
		return ipAddress;
	}
}
