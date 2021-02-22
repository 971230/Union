package com.ztesoft.net.mall.core.utils;

import org.apache.log4j.Logger;




//这不是一个标准的单位转换，因此没有单位
public class MoneyUtil {
	private static Logger logger = Logger.getLogger(MoneyUtil.class);
	/**
	 * 从命令行接收一个数，在其中调用 checkNum() 方法对其进行 验证，并返回相应的值
	 * 
	 * @return 如果输入合法，返回输入的这个数
	 */
	private String getNum() {
		String s = null;
		logger.info("请输入一个数字（精确到小数点后两位）：");
		// 从命令行输入这个浮点数
		java.util.Scanner scanner = new java.util.Scanner(System.in);
		s = scanner.next();
		// 关闭这个Scanner
		scanner.close();
		// 判断用户输入是否合法
		// 若合法，返回这个值；若非法返回 "0"
		if (this.checkNum(s)) {
			return s;
		} else {
			return "";
		}
	}

	/**
	 * 判断用户输入的数据是否合法，用户只能输入大于零的数字，不能输入其它字符
	 * 
	 * @param s
	 *            String
	 * @return 如果用户输入数据合法，返回 true，否则返回 false
	 */
	private boolean checkNum(String s) {
		// 如果用户输入的数里有非数字字符，则视为非法数据，返回 false
		try {
			float f = Float.valueOf(s);
			// 如果这个数小于零则视为非法数据，返回 false
			if (f < 0) {
				logger.info("非法数据，请检查！");
				return false;
			} else {
				return true;
			}
		} catch (NumberFormatException e) {
			logger.info("非法数据，请检查！");
			return false;
		}
	}

	/**
	 * 把用户输入的数以小数点为界分割开来，并调用 numFormat() 方法 进行相应的中文金额大写形式的转换 注：传入的这个数应该是经过
	 * roundString() 方法进行了四舍五入操作的
	 * 
	 * @param s
	 *            String
	 * @return 转换好的中文金额大写形式的字符串
	 */
	private String splitNum(String s) {
		// 如果传入的是空串则继续返回空串
		if ("".equals(s)) {
			return "";
		}
		// 以小数点为界分割这个字符串
		int index = s.indexOf(".");
		// 截取并转换这个数的整数部分
		String intOnly = s.substring(0, index);
		String part1 = this.numFormat(1, intOnly);
		// 截取并转换这个数的小数部分
		String smallOnly = s.substring(index + 1);
		String part2 = this.numFormat(2, smallOnly);
		// 把转换好了的整数部分和小数部分重新拼凑一个新的字符串
		String newS = part1 + part2;
		return newS;
	}

	/**
	 * 对传入的数进行四舍五入操作
	 * 
	 * @param s String 从命令行输入的那个数
	 * @return 四舍五入后的新值
	 */
	private String roundString(String s) {
		// 如果传入的是空串则继续返回空串
		if ("".equals(s)) {
			return "";
		}
		// 将这个数转换成 double 类型，并对其进行四舍五入操作
		double d = Double.parseDouble(s);
		// 此操作作用在小数点后两位上
		d = (d * 100 + 0.5) / 100;
		// 将 d 进行格式化
		s = new java.text.DecimalFormat("##0.000").format(d);
		// 以小数点为界分割这个字符串
		int index = s.indexOf(".");
		// 这个数的整数部分
		String intOnly = s.substring(0, index);
		// 规定数值的最大长度只能到万亿单位，否则返回 "0"
		if (intOnly.length() > 13) {
			logger.info("输入数据过大！（整数部分最多13位！）");
			return "";
		}
		// 这个数的小数部分
		String smallOnly = s.substring(index + 1);
		// 如果小数部分大于两位，只截取小数点后两位
		if (smallOnly.length() > 2) {
			String roundSmall = smallOnly.substring(0, 2);
			// 把整数部分和新截取的小数部分重新拼凑这个字符串
			s = intOnly + "." + roundSmall;
		}
		return s;
	}

	/**
	 * 把传入的数转换为中文金额大写形式
	 * 
	 * @param flag
	 *            int 标志位，1 表示转换整数部分，0 表示转换小数部分
	 * @param s
	 *            String 要转换的字符串
	 * @return 转换好的带单位的中文金额大写形式
	 */
	private String numFormat(int flag, String s) {
		int sLength = s.length();
		// 货币大写形式
		String bigLetter[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		// 货币单位
		String unit[] = { "元", "拾", "佰", "仟", "万",
		// 拾万位到仟万位
				"拾", "佰", "仟",
				// 亿位到万亿位
				"亿", "拾", "佰", "仟", "万" };
		String small[] = { "分", "角" };
		// 用来存放转换后的新字符串
		String newS = "";
		// 逐位替换为中文大写形式
		for (int i = 0; i < sLength; i++) {
			if (flag == 1) {
				// 转换整数部分为中文大写形式（带单位）
				newS = newS + bigLetter[s.charAt(i) - 48]
						+ unit[sLength - i - 1];
			} else if (flag == 2) {
				// 转换小数部分（带单位）
				newS = newS + bigLetter[s.charAt(i) - 48]
						+ small[sLength - i - 1];
			}
		}
		return newS;
	}

	/**
	 * 把已经转换好的中文金额大写形式加以改进，清理这个字 符串里面多余的零，让这个字符串变得更加可观 注：传入的这个数应该是经过 splitNum()
	 * 方法进行处理，这个字 符串应该已经是用中文金额大写形式表示的
	 * 
	 * @param s String 已经转换好的字符串
	 * @param flag Boolean 是否去掉角和分
	 * @return 改进后的字符串
	 */
	private String cleanZero(String s,Boolean flag) {
		// 如果传入的是空串则继续返回空串
		if ("".equals(s)) {
			return "";
		}
		// 如果用户开始输入了很多 0 去掉字符串前面多余的'零'，使其看上去更符合习惯
		while (s.charAt(0) == '零') {
			// 将字符串中的 "零" 和它对应的单位去掉
			s = s.substring(2);
			// 如果用户当初输入的时候只输入了 0，则只返回一个 "零"
			if (s.length() == 0) {
				return "零";
			}
		}
		// 字符串中存在多个'零'在一起的时候只读出一个'零'，并省略多余的单位
		/* 由于本人对算法的研究太菜了，只能用4个正则表达式去转换了，各位大虾别介意哈... */
		String regex1[] = { "零仟", "零佰", "零拾" };
		String regex2[] = { "零亿", "零万", "零元" };
		String regex3[] = { "亿", "万", "元" };
		String regex4[] = { "零角", "角壹", "贰角", "叁角", "肆角", "伍角", "陆角", "柒角", "捌角", "玖角",
							"零分", "壹分", "贰分", "叁分", "肆分", "伍分", "陆分", "柒分", "捌分", "玖分"};
		// 第一轮转换把 "零仟", 零佰","零拾"等字符串替换成一个"零"
		for (int i = 0; i < 3; i++) {
			s = s.replaceAll(regex1[i], "零");
		}
		// 第二轮转换考虑 "零亿","零万","零元"等情况
		// "亿","万","元"这些单位有些情况是不能省的，需要保留下来
		for (int i = 0; i < 3; i++) {
			// 当第一轮转换过后有可能有很多个零叠在一起
			// 要把很多个重复的零变成一个零
			s = s.replaceAll("零零零", "零");
			s = s.replaceAll("零零", "零");
			s = s.replaceAll(regex2[i], regex3[i]);
		}
		if(flag){
			// 第三轮转换把"零角","零分"字符串省略
			for (int i = 0; i < 20; i++) {
				s = s.replaceAll(regex4[i], "");
			}
		}
		// 当"万"到"亿"之间全部是"零"的时候，忽略"亿万"单位，只保留一个"亿"
		s = s.replaceAll("亿万", "亿");
		return s;
	}
	
	/**
	 * 把已经转换好的中文金额大写形式加以改进
	 * 
	 * @param s String 已经转换好的字符串
	 * @param flag Boolean 是否去掉角和分
	 * @return 改进后的字符串
	 */
	private String holdZero(String s,Boolean flag) {
		// 如果传入的是空串则继续返回空串
		if ("".equals(s)) {
			return "";
		}
		if(flag){
			// 字符串中存在多个'零'在一起的时候只读出一个'零'，并省略多余的单位
			/* 由于本人对算法的研究太菜了，只能用4个正则表达式去转换了，各位大虾别介意哈... */
			String regex1[] = { "零角", "角壹", "贰角", "叁角", "肆角", "伍角", "陆角", "柒角", "捌角", "玖角",
								"零分", "壹分", "贰分", "叁分", "肆分", "伍分", "陆分", "柒分", "捌分", "玖分"};
			String regex2[] = { "元", "拾", "佰", "仟", "万", "角", "分", "亿"};
			// 第1轮转换把"零角","零分"字符串省略
			for (int i = 0; i < 20; i++) {
				s = s.replaceAll(regex1[i], "");
			}
			// 第1轮转换把"零角","零分"字符串省略
			for (int i = 0; i < 8; i++) {
				s = s.replaceAll(regex2[i], "  ");
			}
		}
		return s;
	}
	
	/**
	 * 把单位去掉留出空位供打印发票使用
	 * 
	 * @param s String 已经转换好的字符串
	 * @return 改进后的字符串
	 */
	private String holdZeroCleans(String s) {
		// 如果传入的是空串则继续返回空串
		if ("".equals(s)) {
			return "";
		}
		// 字符串中存在多个'零'在一起的时候只读出一个'零'，并省略多余的单位
		/* 由于本人对算法的研究太菜了，只能用4个正则表达式去转换了，各位大虾别介意哈... */
		String regex1[] = { "元", "拾", "佰", "仟", "万", "角", "分", "亿"};
		// 第1轮转换把"零角","零分"字符串省略
		for (int i = 0; i < 8; i++) {
			s = s.replaceAll(regex1[i], "  ");
		}
		return s;
	}
	
	/**
	 * 数字转金额接口
	 * 
	 * @param s String 带转换的字符串
	 * @param flag int    “1”：   100.00=壹佰零元零角零分；
	 * 					  “2”：   100.00=壹佰零拾零元零角零分；
	 * 					  “20”： 100.00=壹   零   零  零  零；
	 * 					  “3”：   100.00=壹佰元
	 * 					  “4”：   100.00=壹佰零拾零元
	 * 					  “40”：100.00=壹   零   零   
	 * @return 改进后的字符串
	 */
	public static String toRmb(String s,int flag){
		MoneyUtil t2r = new MoneyUtil();
		String rs = "";
		switch (flag) {
		case 1: rs = t2r.cleanZero(t2r.splitNum(t2r.roundString(s)),false);break;
		case 2: rs = t2r.holdZero(t2r.splitNum(t2r.roundString(s)),false);break;
		case 20: rs = t2r.holdZeroCleans(t2r.holdZero(t2r.splitNum(t2r.roundString(s)),false));break;
		case 3: rs = t2r.cleanZero(t2r.splitNum(t2r.roundString(s)),true);break;
		case 4: rs = t2r.holdZero(t2r.splitNum(t2r.roundString(s)),true);break;
		case 40: rs = t2r.holdZeroCleans(t2r.holdZero(t2r.splitNum(t2r.roundString(s)),true));break;
		default:
			 rs = t2r.cleanZero(t2r.splitNum(t2r.roundString(s)),false);
			break;
		}
		return rs;		
	}

	// main
	public static void main(String[] args) {

		logger.info("\n--------将数字转换成中文金额的大写形式------------\n");
		MoneyUtil t2r = new MoneyUtil();
		String s = t2r.holdZero(t2r.splitNum(t2r.roundString("100.01")),true);
		String s2 = t2r.holdZero(t2r.splitNum(t2r.roundString("100.01")),false);
		// 如果转换过后是一个空串，则不输出屏幕
		if (!"".equals(s)) {
			logger.info("转换成中文后为：" + s);
			;
		}
		if (!"".equals(s2)) {
			logger.info("转换成中文后为：" + s2);
			;
		}
		logger.info("\n---------------------------------------------");
	}

}
