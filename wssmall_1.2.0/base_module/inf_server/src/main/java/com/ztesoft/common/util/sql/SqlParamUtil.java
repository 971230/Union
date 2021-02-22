package com.ztesoft.common.util.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ztesoft.common.util.StringUtils;

public class SqlParamUtil {

	public static final String MISSION_MONTH = "[mission_month]";
	public static final String MISSION_HOLIDAY = "[mission_holiday]";
	private static List<String> paramList = new ArrayList<String>();

	static {
		paramList.add(MISSION_MONTH);
		paramList.add(MISSION_HOLIDAY);
	}

	public static String parseAllSingleSqlParam(String sql, String code,
			String value) {
		while (sql.indexOf(code) >= 0) {
			sql = sql.replace(code, value);
		}
		return sql;
	}

	// 使用中括号“[]”括起来的为可选SQL语句，当可选语句里面的#{var1}或者${var1}为空时，则认为是不需要此查询条件，直接去掉
	public static final String FIND_SQL_OPTIONAL_REXP = "\\[[\\S\\s]*?\\]";
	// 查找变量的正则表达式，如${var1}这样的格式
	public static final String FIND_QUERY_VARIABLE_REXP = "\\u0024\\u007B\\S+?}";
	// 查找SQL中需要直接替换的地方，如#{var1}，与${var1}的区别为，${var1}替换成问号，#{var1}直接替换成具体的字符
	public static final String FIND_SQL_REPLACE_REXP = "#\\u007B\\S+?}";
	public static final String FIND_SQL_WHERE_COND_REXP = "\\[where_cond\\]";
	public static final String QUERY_SQL = "query_sql";
	public static final String QUERY_PARAMS = "query_params";

	public static Map parseSqlContent(String queryContent, Map params,
			String where_cond) throws Exception {
		Map resultMap = new HashMap();
		List varList = new ArrayList(); // 记录SQL语句中的变量
		if (params == null) {
			params = new HashMap<String, String>();
		}
		try {
			queryContent = replaceWhereCond(queryContent, where_cond);
			// 分析可选SQL语句
			queryContent = replaceOptionSqlByParam(queryContent, params);

			// 分析直接替换的变量
			queryContent = replaceSqlByParam(queryContent, params);

			// 用正则解析出变量
			Pattern p = Pattern.compile(FIND_QUERY_VARIABLE_REXP);
			Matcher m = p.matcher(queryContent);
			while (m.find()) {
				int start = m.start();
				int end = m.end();
				String variable = queryContent.substring(start, end);
				// 去掉首尾的多余字符串
				varList.add(variable.substring(2, variable.length() - 1));
			}

			// 取得变量的值，供查询使用
			List queryParamsList = new ArrayList();

			for (int i = 0; i < varList.size(); i++) {
				String varName = (String) varList.get(i);
				String varNameBuf = varName;

				if (varName.startsWith("%") || varName.endsWith("%")) {
					varNameBuf = varName.replaceAll("%", "");
				}

				String value = StringUtils.getStringFromMap(params, varNameBuf);
				if (StringUtils.isEmpty(value)) {
					throw new RuntimeException("未能找到入参变量："
							+ varName.toUpperCase());
				}
				queryParamsList.add(varName.replaceAll(varNameBuf,
						params.get(varNameBuf.toLowerCase()).toString()));
			}

			// 将查询语句中的变量替换成问号
			resultMap.put(QUERY_SQL,
					queryContent.replaceAll(FIND_QUERY_VARIABLE_REXP, "?"));
			// 解析后的查询变量值
			resultMap.put(QUERY_PARAMS, queryParamsList);

			return resultMap;
		} catch (Exception e) {

			throw e;
		}

	}

	/**
	 * 替换[where_cond]
	 * 
	 * @param queryContent
	 * @param where_cond
	 * @return
	 */
	private static String replaceWhereCond(String queryContent,
			String where_cond) {

		String result = queryContent.replaceAll(FIND_SQL_WHERE_COND_REXP,
				where_cond);
		return result;
	}

	/**
	 * 找出可选SQL语句，如果里面的变量为空，去掉此段可选SQL语句
	 * 
	 * @param queryContent
	 * @param params
	 * @return
	 */
	private static String replaceOptionSqlByParam(String queryContent,
			Map params) {
		String queryContentBuf = queryContent.toString();
		// 用正则解析出变量
		Pattern p = Pattern.compile(FIND_SQL_OPTIONAL_REXP);
		Matcher m = p.matcher(queryContent);
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			String optionSql = queryContent.substring(start, end);

			// // 去掉首尾的多余字符串
			// optionSql = optionSql.substring(1, optionSql.length() - 1);

			boolean delete = false;

			Pattern pBuf = Pattern.compile(FIND_SQL_REPLACE_REXP);
			Matcher mBuf = pBuf.matcher(optionSql);
			while (mBuf.find()) {
				int startBuf = mBuf.start();
				int endBuf = mBuf.end();

				String variable = optionSql.substring(startBuf, endBuf);
				// 去掉首尾的多余字符串
				variable = variable.substring(2, variable.length() - 1);
				if (variable.startsWith("%") || variable.endsWith("%")) {
					// variable = variable.replace("%", "");
					variable = variable.replaceAll("%", "");
				}
				// 判断里面的变量是否存在，不存在则不需要这个查询语句
				String value = StringUtils.getStringFromMap(params, variable);
				if (StringUtils.isEmpty(value)) {
					delete = true;
					break;
				}
			}

			// 删除可选SQL语句
			if (delete) {
				// queryContentBuf = queryContentBuf.replace(optionSql, "");
				queryContentBuf = StringUtils.replaceAll(queryContentBuf,
						optionSql, "");
			} else {
				// queryContentBuf = queryContentBuf.replace(optionSql,
				// optionSql.substring(1, optionSql.length() - 1));
				queryContentBuf = StringUtils.replaceAll(queryContentBuf,
						optionSql,
						optionSql.substring(1, optionSql.length() - 1));
			}
		}

		return queryContentBuf;
	}

	/**
	 * 根据将SQL语句中#{var1}替换成对应变量，直接替换，不使用？预编译字符
	 * 
	 * @param queryContent
	 * @param params
	 */
	private static String replaceSqlByParam(String queryContent, Map params) {
		String queryContentBuf = queryContent.toString();
		// 用正则解析出变量
		Pattern p = Pattern.compile(FIND_SQL_REPLACE_REXP);
		Matcher m = p.matcher(queryContent);
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			String variable = queryContent.substring(start, end);

			// 去掉首尾的多余字符串
			variable = variable.substring(2, variable.length() - 1);
			String variableBuf = variable;
			if (variable.startsWith("%") || variable.endsWith("%")) {
				// variableBuf = variable.replace("%", "");
				variableBuf = variable.replaceAll("%", "");
			}
			String value = StringUtils.getStringFromMap(params, variable);
			if (StringUtils.isEmpty(value)) {
				throw new RuntimeException("未能找到入参变量："
						+ variableBuf.toUpperCase());
			}

			// 直接替换字符
			queryContentBuf = queryContentBuf.replaceAll("#\\u007B" + variable
					+ "}", value);
		}

		return queryContentBuf;
	}

}
