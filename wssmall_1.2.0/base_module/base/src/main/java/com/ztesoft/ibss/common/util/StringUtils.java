package com.ztesoft.ibss.common.util;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * @author Reason
 * @version Created Aug 22, 2011
 */
public class StringUtils {
	public static Logger logger = Logger.getLogger(StringUtils.class);
    public static String leftPad(String str, int size) {
        return org.apache.commons.lang3.StringUtils.leftPad(str, size);
    }

    public static String leftPad(String str, int size, char padChar) {
        return org.apache.commons.lang3.StringUtils.leftPad(str, size, String
                .valueOf(padChar));
    }


	/**
	 * 将字符串的数据，转换成中文的大写货币值
	 * 
	 * @param pValue
	 * @return
	 */
	public static String convertToBigMoney(String pValue) {
		double S = 0;

		try {
			S = Double.parseDouble(pValue);
		} catch (Exception e) {
		}

		S = S + 0.005; // 把它加0.005,为了预防浮点数的四舍五入造成的误差
		String Result = "", odxs, odxc, temp1, temp2;
		int integer, Point, ormb;
		odxs = "零壹贰叁肆伍陆柒捌玖";
		odxc = "分角圆拾佰仟万拾佰仟亿拾佰仟万拾佰仟亿拾佰仟";
		try {
			integer = (int) S; // 取得他的整数部分
			Point = (int) (100 * (S - integer)); // 取得他的小数部分
			if (integer == 0)
				Result = "零圆"; // 如果整数为0,则显示零圆
			for (int i = 1; integer > 0; i++) {
				ormb = (integer % 10); // 取得他的个位
				temp1 = odxs.substring(ormb, ormb + 1);// 根据相应的值取得他的大写
				temp2 = odxc.substring(i + 1, i + 2); // 根据循环次数确定他的单位
				Result = temp1 + temp2 + Result;
				integer = integer / 10;
			}
			ormb = (Point / 10); // 取得角
			for (int i = 1; i > -1; i--) {
				temp1 = odxs.substring(ormb, ormb + 1);// 根据相应的值取得他的大写
				temp2 = odxc.substring(i, i + 1); // 根据循环次数确定他的单位
				Result = Result + temp1 + temp2;
				ormb = Point % 10; // 取得分
			}
			 //System.out.print("Result frist: "+Result);
            Result= Result.replaceAll("零仟","零");
            Result= Result.replaceAll("零佰","零");
            Result= Result.replaceAll("零拾","零");
            while(Result.indexOf("零零")>-1)
            {
            	Result= Result.replaceAll("零零","零");
            }
            Result = Result.replaceAll("零圆","圆");
            Result = Result.replaceAll("零万","万");
            if("圆零角零分".equals(Result))
            {
            	Result="零圆零角零分";
            }
	          //  System.out.print("Result second: "+Result);
		} catch (Exception se) {
			se.printStackTrace();
		}
		return Result;
	}

	/**
	 * 将数字串的数据，转换成中文的大写货币值
	 * 
	 * @param pValue
	 * @return
	 */
	public static String convertToBigMoney(double pValue) {
		double S = pValue;
		S = S + 0.005; // 把它加0.005,为了预防浮点数的四舍五入造成的误差
		String Result = "", odxs, odxc, temp1, temp2;
		int integer, Point, ormb;
		odxs = "零壹贰叁肆伍陆柒捌玖";
		odxc = "分角圆拾佰仟万拾佰仟亿拾佰仟万拾佰仟亿拾佰仟";
		try {
			integer = (int) S; // 取得他的整数部分
			Point = (int) (100 * (S - integer)); // 取得他的小数部分
			if (integer == 0)
				Result = "零圆"; // 如果整数为0,则显示零圆
			for (int i = 1; integer > 0; i++) {
				ormb = (integer % 10); // 取得他的个位
				temp1 = odxs.substring(ormb, ormb + 1);// 根据相应的值取得他的大写
				temp2 = odxc.substring(i + 1, i + 2); // 根据循环次数确定他的单位
				Result = temp1 + temp2 + Result;
				integer = integer / 10;
			}
			ormb = (Point / 10); // 取得角
			for (int i = 1; i > -1; i--) {
				temp1 = odxs.substring(ormb, ormb + 1);// 根据相应的值取得他的大写
				temp2 = odxc.substring(i, i + 1); // 根据循环次数确定他的单位
				Result = Result + temp1 + temp2;
				ormb = Point % 10; // 取得分
			}
			//System.out.print("Result frist: "+Result);
            Result= Result.replaceAll("零仟","零");
            Result= Result.replaceAll("零佰","零");
            Result= Result.replaceAll("零拾","零");
            while(Result.indexOf("零零")>-1)
            {
            	Result= Result.replaceAll("零零","零");
            }
            Result = Result.replaceAll("零圆","圆");
            Result = Result.replaceAll("零万","万");
            if("圆零角零分".equals(Result))
            {
            	Result="零圆零角零分";
            }
            //System.out.print("Result second: "+Result);
			
		} catch (Exception se) {
			se.printStackTrace();
		}
		return Result;
	}

    public static final String[] split(String line, String separator) {
        int index = 0;
        ArrayList matchList = new ArrayList();

        int start = line.indexOf(separator, index);

        if (start == -1)
            return new String[] { line.toString() };

        while (start > -1) {
            String match = line.subSequence(index, start).toString();
            matchList.add(match);
            index = start + separator.length();
            start = line.indexOf(separator, index);
        }

        matchList.add(line.subSequence(index, line.length()).toString());

        
        int resultSize = matchList.size();

        while (resultSize > 0 && matchList.get(resultSize - 1).equals(""))
            resultSize--;
        
        String[] result = new String[resultSize];
        return (String[]) matchList.subList(0, resultSize).toArray(result);
    }
	
	
	public static final String[] splitWithoutSpace(String line, String separator) {
		LinkedList list = new LinkedList();
		if (line != null) {
			int start = 0;
			int end = 0;
			int separatorLen = separator.length();
			while ((end = line.indexOf(separator, start)) >= 0) {
				String str = line.substring(start, end).trim();
				if (str != null && !str.equals(""))
					list.add(str);
				start = end + separatorLen;
			}
			if (start < line.length()) {
				String str = line.substring(start, line.length()).trim();
				if (str != null && !str.equals(""))
					list.add(str);
			}
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

    public static final String replaceAll(String line, String str1, String str2) {
        StringBuffer newStr = new StringBuffer();
        int lastAppendIndex=0;
        
        int start=line.indexOf(str1,lastAppendIndex);
        
        if(start==-1)return line;
        
        while(start>-1){
            newStr.append(line.subSequence(lastAppendIndex, start));
            newStr.append(str2);
            lastAppendIndex=start+str1.length();
            start=line.indexOf(str1,lastAppendIndex);
        }
        newStr.append(line.subSequence(lastAppendIndex, line.length()));
        
        return newStr.toString();

    }
	
	public static String join(Iterator iterator, String separator) throws Exception {
		if (iterator == null) {
			return null;
		}
		StringBuffer buf = new StringBuffer(256);
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (obj != null) {
				buf.append("'"+obj+"'");
			}
			if (iterator.hasNext()) {
				buf.append(separator);
			}
		}
		return buf.toString();
	}
	
	/**
	 * 把字符转换为钱数0.00的格式
	 * @param temp
	 * @return
	 */
	public static String formatToMoney(String str) {
		int place = 0;
		int len = 0;
		if (str == null || "".equals(str)) {
			return "0.00";
		}
		place = str.indexOf(".");
		if (place == -1) {
			return str = str + ".00";
		}
		len = str.length();
		if ((len - place) == 2) {
			str = str + "0";
		} else if ((len - place) == 1) {
			str = str + "00";
		} else {
			str = str.substring(0, place + 3);
		}
		return str;
	}
	
    /**
     *function :校验是否为货币性
     * time :2009-8-24 
     * */
	public static boolean isMoney(String money){
		
				if(money==null ||money.equals(""))return false;
		   Pattern pattern = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
		   Matcher isNum = pattern.matcher(money);
		   return isNum.matches();
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

	public static String join(String seperator, String[] strings) {
		if(strings==null)
			return "";
		int length = strings.length;
		if (length == 0)
			return "";
		StringBuffer buf = new StringBuffer(length * strings[0].length()).append(strings[0]);
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
		
		List list=Arrays.asList(strings);
		return joinWithQMarks(seperator,list.iterator());
	}
	
	public static String joinWithQMarks(String seperator, Iterator objects) {
		StringBuffer buf = new StringBuffer();
		if (objects.hasNext())
			buf.append("'").append(objects.next()).append("'");
		while (objects.hasNext()) {
			buf.append(seperator).append("'").append(objects.next()).append("'");
		}
		return buf.toString();
	}

	public static boolean booleanValue(String tfString) {
		String trimmed = tfString.trim().toLowerCase();
		return trimmed.equals("true") || trimmed.equals("t");
	}
	
	public static boolean isEqual(String o, boolean c){
		
		if(o==null||"".equals(o))
			return false;
		return o.equals(String.valueOf(c).toLowerCase());
		
	}
	
	public static boolean isEqual(String o, String c){
		if(isEmpty(o)){
			o = ""; 
		}
		if(isEmpty(c)){
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

	public static String truncate(String string, int length) {
		if (string.length() <= length) {
			return string;
		} else {
			return string.substring(0, length);
		}
	}

	
	public static  String getStrValue(Map m , String name) {
		Object t = m.get(name) ;
		if(t == null )
			return "" ;
		return ((String)m.get(name)).trim() ;
	}
	
	public static String toUpperCase(String str) {
		return str == null ? null : str.toUpperCase();
	}

	public static String toLowerCase(String str) {
		return str == null ? null : str.toLowerCase();
	}

	public static HashMap toMap(String[] array) {

		if(array==null)
			return null;
		int len = array.length;
		
		
		if (len == 0)
			return null;
		
		HashMap map=new HashMap();
		
		for (int i = 0; i < len; i++) {
			map.put(array[i],array[i]);
		}
		return map;
		
	}/*--repl将被with替换 -1为替换字符串text中所有的repl--*/
	public static String replace(String text, String repl, String with) {
        return replace(text, repl, with, -1);
    }
	/*--max为被替换的个数--*/
	public static String replace(String text, String repl, String with, int max) {
        if (text == null || repl==null || with == null || max == 0) {
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
	public static String max(String str1,String str2){
		int num1 = Integer.parseInt(str1);
		int num2 = Integer.parseInt(str2);
		if(num1>num2){
			return str1;
		}else{
			return str2;
		} 
	}
	
	/**
	 * 将两个字符串转换成数字类型 返回小的那个
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String min(String str1,String str2){
		int num1 = Integer.parseInt(str1);
		int num2 = Integer.parseInt(str2);
		if(num1>num2){
			return str2;
		}else{
			return str1;
		} 
	}
	/**
	 * 校验是否为数字
	 * */
	public static  boolean isNum(String num) {		
		
	   if(num==null ||num.equals(""))return false;
	   
	   if(num.startsWith("-")){
		   return isNum(num.substring(1));
	   }
	   
	   Pattern pattern = Pattern.compile("^\\d+$");
	   Matcher isNum = pattern.matcher(num);
	   return isNum.matches();
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
		
		String tmpStr = splitFlag+str+splitFlag;
		String tmpSubStr = splitFlag+subStr+splitFlag;
		
		return tmpStr.indexOf(tmpSubStr) >= 0;
		
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
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			oo = new ObjectOutputStream(out);
			oo.writeObject(object);
			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			oi = new ObjectInputStream(in);
			cloneObj = oi.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(oo!=null){
				try {
					oo.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if(oi!=null){
				try {
					oi.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}	
		}
		return cloneObj;
	}
	
	public static List stringToList(String []str){
		List ls = new ArrayList();
		if(str!=null && str.length>0){
			for (int i = 0; i < str.length; i++) {
				ls.add(str[i]);
			}
		}
		return ls;
	}
	public static String escapeXMLStringValue(Object objToStr){
    	String ret = (objToStr == null ? "":objToStr.toString() );
    	ret = ret.replaceAll( "&", "&amp;") ;//替换掉特殊字符&
    	ret = ret.replaceAll("'", "&apos;") ;//替换掉特殊字符单引号
    	ret = ret.replaceAll( "<", "&lt;") ;//替换掉特殊字符小于号
    	ret = ret.replaceAll( ">", "&gt;") ;//替换掉特殊字符大于号
    	ret = ret.replaceAll( "\"", "&quot;") ;//替换掉特殊字符双引号
    	ret = ret.replaceAll( "\"", "&quot;") ;//替换掉特殊字符双引号
    	ret = ret.replaceAll("\\r\\n|\\n|\\r", "&lt;br/&gt;");//替换掉换行符
    	return ret ;
        //return objToStr==null ? "":objToStr.toString();
	}
	
	//获取URL中的参数
	public static String getUrlQryStr(String []urlParts,String key){
		String ret="";
		try{
			key+="=";
			if(urlParts.length>1 && urlParts[1].indexOf(key)>-1){
				String qryStr = (urlParts[1]+"&");
				qryStr= qryStr.substring(qryStr.indexOf(key)+key.length(),qryStr.length());
				ret = qryStr.substring(0,qryStr.indexOf("&"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	//将IP解析为数字，比如198.126.0.1变成198126000001
	public static long translateIP(String ip){
		String regex = "\\."; 
		String[] str = ip.split(regex);
		long result = 0;
		for(int i=0;i<str.length;i++){	
			int temp = Integer.parseInt(str[i]);
			//int size = str[i].length();
	
			result += temp;
			result *= 1000;
		}
		
		return result/1000;
	}
	
	/**
	 * 获取随机数
	 * @param sublen
	 * @return
	 */
	public static String  genRandom(int sublen){
		String random =System.currentTimeMillis()+"";
		random = random.substring(random.length()-sublen);
		return random;
	}
	
	public static String fen2yuan(String fen){
		if(fen==null || fen.equals(""))return "";
		float f = Float.parseFloat(fen);
		f = f/100;
		return f+"";
	}
	
	public static String yuan2fen(String yuan){
		if(yuan==null || yuan.equals(""))return "";
		float f = Float.parseFloat(yuan);
		int i = (int)(f*100);
		return i+"";
	}
	
	
	public static void main(String[] args) {
		
		logger.info(yuan2fen("99.02"));
	}


	public static boolean hasLength(String cdata) {
		if(cdata==null ||cdata.equals(""))return false;
		return true;
	}
	
	public static String substr(String string, int len) {
		if (string == null)
			return "";
		return string.substring(0, Math.min(string.length(), len)).trim();
	}

	public static String subStr(String str, int startIndex, int size) {
		if (str == null) {
			return "";
		}
		String strs = str.substring(startIndex, Math.min(str.length(), size));
		return strs.trim();
	}

	/**
	 * 字符串按字节截取
	 * 
	 * @param str
	 *            原字符
	 * @param startIndex
	 *            开始坐标
	 * @param len
	 *            截取长度
	 * 
	 * @return String
	 * @author kinglong
	 * @throws UnsupportedEncodingException
	 * @since 2006.07.20
	 */
	public static String splitStr(String str, int startIndex, int len) {
		if (str == null || "".equals(str) || startIndex > str.getBytes().length) {
			return "";
		}
		byte[] strByte = str.getBytes();
		int strLen = strByte.length;
		if (len >= strLen || len < 1) {
			return "";
		}
		if ((startIndex + len) > strLen) {
			len = strLen - startIndex;
		}
		String retStr = new String(strByte, startIndex, len).trim();
		return retStr;
	}

	// public static String splitString(String str, int startIndex, int len) {
	// if (str == null || "".equals(str) || startIndex > str.length()) {
	// return "";
	// }
	// byte[] strByte = str.getBytes();
	// int strLen = strByte.length;
	// if (len >= strLen || len < 1) {
	// return "";
	// }
	// int count = startIndex;
	// for (int i = startIndex; i < len; i++) {
	// int value = (int) strByte[i];
	// if (value < 0) {
	// count++;
	// }
	// }
	// if (count % 2 != 0) {
	// len = (len == 1) ? len + 1 : len - 1;
	// }
	// return new String(strByte, startIndex, len);
	// }

//	public static void main(String[] args) throws UnsupportedEncodingException {
//		// String str = "0 13.29 徐勇 10.61 20120110 18970897432 总费用 13.29
//		// 18970897432 话费划拨款 -65.00 18970897432 来电显示 5.00 18970897432 套餐费 39.00
//		// 18970897432 区内话费 18.12 18970897432 国内长途 23.87 18970897432 语音信息费 3.00
//		// 18970897432 优惠/补收 -39.00 18970897432 短信通信费 10.80 18970897432 漫游费
//		// 17.50 1 18970897432 天翼畅聊套餐 13.29 ";
//		String str = "0   13.29       徐勇                                                        10.61       20120110   ";
//		int size = 0;
//		HashMap retMap = new HashMap();
//		String retCode = StringUtils.splitStr(str, size, 4);
//		retMap.put("str", retCode);
//		String sumFee = StringUtils.splitStr(str, size += 4, 12);
//		retMap.put("sumFee", sumFee);
//		String name = StringUtils.splitStr(str, size += 12, 60);
//		retMap.put("name", name);
//		String leftFee = StringUtils.splitStr(str, size += 60, 12);
//		retMap.put("leftFee", leftFee);
//		String month = StringUtils.splitStr(str, size += 12, 6);
//		retMap.put("month", month);// 年月
//		String feenum = StringUtils.splitStr(str, size += 6, 6);
//
//		logger.info(retCode + "//");
//		logger.info(sumFee + "//");
//		logger.info(name + "//");
//		logger.info(leftFee + "//");
//		logger.info(month + "//");
//		logger.info(feenum + "//");
//		// logger.info(retMap);
//	}

	public static Map splitLinesIntoMap(String lines, String delimiter) {
		if (lines == null)
			return null;
		String[] lineArr = lines.split("\r|\n|\r\n");
		java.util.List list = new ArrayList();
		for (int i = 0; i < lineArr.length; i++) {
			String trim = lineArr[i].trim();
			if (!"".equals(trim))
				list.add(trim);
		}
		String[] array = (String[]) list.toArray(new String[list.size()]);
		return org.springframework.util.StringUtils
				.splitArrayElementsIntoProperties(lineArr, delimiter);
//		return (Map) splitArrayElementsIntoProperties(array, delimiter);
	}

	private static Pattern linePattern = Pattern.compile("\r|\n|\r\n");

	public static String[] splitLines(String lines) {
		return linePattern.split(lines);
	}

	public static String trim(String text) {
		if (text == null)
			return null;
		return text.trim();
	}

	public static String rightPad(String str, int size, char padChar) {
		return org.apache.commons.lang3.StringUtils.rightPad(str, size, String
				.valueOf(padChar));
	}

	public static boolean equals(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equals(str2);
	}

	public static boolean matcher(String regex, String value) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	public static boolean isEmpty(String obj) {
		if (null == obj || obj.trim().length() == 0
				|| "null".equalsIgnoreCase(obj)) {
			return true;
		}
		return false;
	}

	public static void printInfo(String obj) {
		printInfo(obj, null);
	}

	public static void printInfo(Object obj) {
		printInfo(obj, null);
	}

	/**
	 * 对字str符串加len入个空字符
	 * 
	 * @param str
	 *            需要在后面加入的字符串
	 * @param len
	 *            字符串的长度
	 * @return
	 */
	public static String appendEmpty(String str, int len) {
		StringBuffer sb = new StringBuffer();
		if (str == null || str.length() <= 0) {
			return sb.toString();
		}
		sb.append(str);
		int length = len - str.length();
		for (int i = 0; i < length; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * 对字str符串加len入个sign字符
	 * 
	 * @param str
	 *            需要在后面加入的字符串
	 * @param len
	 *            字符串的长度
	 * @return
	 */
	public static String appendSign(String str, int len, String sign) {
		StringBuffer sb = new StringBuffer();
		if (str == null || str.length() <= 0) {
			return sb.toString();
		}
		sb.append(str);
		int length = len - str.length();
		for (int i = 0; i < length; i++) {
			sb.append(sign);
		}
		return sb.toString();
	}

	public static void printInfo(Object obj, Throwable e) {
		String errStack = e.getLocalizedMessage();
		logger.debug("\n---->printData:" + obj + "\n\n "
				+ (null == e ? "" : "errStack:" + errStack));
	}
}
