package com.rop.core.db;


import com.rop.core.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.*;


public class DAOSQLUtils {

    private static Logger logger = Logger.getLogger(DAOSQLUtils.class);

    private static DAOSQLUtils instance = null;

    public static int CRM_DB = 1;

    public static int BSN_DB = 2;

    private int DB_FLAG = 1;

    private static String KEY_DBTYPE_INFORMIX = "informix";

    private static String KEY_DBTYPE_ORACLE = "oracle";

    private static String KEY_UPDATE = "update";

    private static String KEY_FROM = " from ";

    private static String KEY_FROM_NOSPACE = "from";

    private static String KEY_INTO = "into";

    private static String KEY_JOIN = "join";

    private static String KEY_SET = " set ";

    private static String KEY_SET_NOSPACE = "set";

    private static String KEY_WHERE = "where";

    private static String KEY_DOT = ",";

    private static String KEY_ON = "on";

    private static String KEY_OUTER = "outer";

    private static String S_SPACE = " ";

    private static String S_DOT = ",";

    private static String[] SQL_KEY_SET = {KEY_UPDATE, KEY_SET_NOSPACE,
            KEY_FROM_NOSPACE, KEY_INTO, KEY_JOIN, KEY_WHERE, KEY_ON};

    private static String dbType = null;

    private static String defaultUser = null;

    private static String defaultDatabase = null;

    private static String defaultDatabaseServer = null;

    private static Properties properties = new Properties();

    private static Properties b_properties = new Properties();

    private static String b_dbType = null;

    private static String b_defaultUser = null;

    private static String b_defaultDatabase = null;

    private static String b_defaultDatabaseServer = null;


    /**
     * 将传递进来的where条件重新组合成key=? 的形式，并返回?的参数
     * 例如：传进来一个a.accept_id='10000000' and a.state='2' and a.product_id=b.product_id
     * 那么返回一个HashMap，里面的一个key:strSql装的是a.accept_id=? and a.state=? and a.product_id=b.product_id
     * 还有一个key:whereCond装的是一个ArrayList whereCond，里面装的值是10000000......
     *
     * @param sql
     */
    //add by RyoUehara 090702把一个where字符串转换成参数形式的where字符串，并返回参数列表
    public static HashMap getWhereCond(String whereCond) {
        HashMap vo = new HashMap();
        if (whereCond.toLowerCase().indexOf("dual") >= 0) {
            vo.put("strSql", whereCond);
            vo.put("whereCond", null);
            return vo;
        }
        StringBuffer allStrSql = new StringBuffer();
        char[] charWhereCond = whereCond.toCharArray();
        String singleQuotation = "'";
        String isNumber = "1234567890";
        String isChar = "abcdefghijklmnopqrstuvwxyz";
        String tmpVal = "";
        String nextVal = "";
        String preVal = "";
        int singleQuoStartIdx = 0;
        int singleQuoEndIdx = 0;
        boolean isSingelStart = false;
        int isNumberStartIdx = 0;
        int isNumberEndIdx = 0;
        boolean isNumberStart = false;
        int singleQuoCount = 0;
        ArrayList lstWhereCond = new ArrayList();
        boolean isOw = false;
        int charWhereCondSize = charWhereCond.length;
        for (int i = 0; i < charWhereCondSize; i++) {
            tmpVal = String.valueOf(charWhereCond[i]);

            if ((i + 1) < charWhereCond.length) {
                nextVal = String.valueOf(charWhereCond[i + 1]);
            } else {
                nextVal = "";
            }

            if (tmpVal.equals(singleQuotation) && isSingelStart == false) {//收集单引号开始
                singleQuoStartIdx = i;
                singleQuoEndIdx = i;
                isSingelStart = true;
                singleQuoCount++;
                continue;
            }
            if (tmpVal.equals(singleQuotation) && isSingelStart == true) {//对每个收集的单引号计数
                singleQuoCount++;
            }
            if (tmpVal.equals(singleQuotation) && !nextVal.equals(singleQuotation) && isSingelStart == true && singleQuoCount % 2 == 0) {//收集单引号结束
                singleQuoEndIdx = i;
                String val = whereCond.substring(singleQuoStartIdx + 1, singleQuoEndIdx);
                //如果有%那么认为是like，这个类型暂时不处理
                if (val.indexOf("%") >= 0) {
                    val = whereCond.substring(singleQuoStartIdx, singleQuoEndIdx);
                    //复位状态
                    singleQuoStartIdx = 0;
                    singleQuoEndIdx = 0;
                    singleQuoCount = 0;
                    isSingelStart = false;
                    allStrSql.append(val);
                    allStrSql.append(tmpVal);
                    continue;
                }
                //收集需要的值
                allStrSql.append("?");
                lstWhereCond.add(val);
                //复位状态
                singleQuoStartIdx = 0;
                singleQuoEndIdx = 0;
                singleQuoCount = 0;
                isSingelStart = false;
                continue;
            }
            //如果是负数
            if (tmpVal.equals("-") && isSingelStart == false) {
                //需要查找前面是否有非数字的和字母，如果有，那么就证明这个操作符是操作符，如果没有是其他的一些+=（）等就证明是负数,空格不算
                for (int j = i - 1; j >= 0; j--) {
                    preVal = String.valueOf(charWhereCond[j]);
                    if (!preVal.equals("")) {
                        if (isChar.indexOf(preVal.toLowerCase()) >= 0 || isNumber.indexOf(preVal) >= 0) {//操作符
                            //allStrSql.append(tmpVal);
                            break;
                        } else {
                            isOw = true;
                            break;
                        }
                    }
                }
                if (isOw)
                    continue;
            }
            if ((i - 1) < charWhereCond.length && (i - 1) >= 0) {
                preVal = String.valueOf(charWhereCond[i - 1]);
            } else {
                preVal = "";
            }
            if (isChar.indexOf(preVal.toLowerCase()) < 0 && isNumber.indexOf(tmpVal) >= 0 && isNumberStart == false && isSingelStart == false) {//收集数字开始
                boolean isCanStart = true;
                for (int j = i - 1; j >= 0; j--) {//向前找，如果能找到一个字母，那么就不算是数字,
                    preVal = String.valueOf(charWhereCond[j]);
                    if (preVal == null || preVal.equals(" ")) {//如果是空格，那么就认为是数字
                        break;
                    } else if (isChar.indexOf(preVal.toLowerCase()) >= 0) {//如果有字母
                        isCanStart = false;
                        break;
                    }
                    if (isNumber.indexOf(preVal) < 0) {//如果前面还是数字，那么才继续往前找，不然就认为是数字了
                        break;
                    }
                }
                if (isCanStart) {
                    isNumberStartIdx = i;
                    isNumberEndIdx = i;
                    isNumberStart = true;
                    continue;
                }
            }

            //如果已经在收集数字的状态，但是后面一位是.,并且.的后面一位还是数字，那么不认为数字结束了
            if (isNumberStart == true && isSingelStart == false && tmpVal.equals(".") && isNumber.indexOf(nextVal) >= 0) {
                //
            } else {
                if (isNumber.indexOf(tmpVal) < 0 && isNumberStart == true && isSingelStart == false) {//收集数字结束
                    isNumberEndIdx = i;
                    String val = whereCond.substring(isNumberStartIdx, isNumberEndIdx);
                    //收集需要的值
                    allStrSql.append("?");
                    if (isOw)//如果是负数，那么
                        val = "-" + val;
                    isOw = false;
                    lstWhereCond.add(val);
                    //复位状态
                    isNumberEndIdx = 0;
                    isNumberStart = false;
                }
            }
            if (isSingelStart == false && isNumberStart == false && isOw == false) {
                allStrSql.append(tmpVal);
            }
        }

        if (isNumber.indexOf(tmpVal) >= 0 && isNumberStart == true && isSingelStart == false) {//数字结尾结束
            isNumberEndIdx = charWhereCondSize;
            String val = whereCond.substring(isNumberStartIdx, isNumberEndIdx);
            //收集需要的值
            allStrSql.append("?");
            if (isOw)//如果是负数，那么
                val = "-" + val;
            isOw = false;
            lstWhereCond.add(val);
            //复位状态
            isNumberEndIdx = 0;
            isNumberStart = false;
        }

        vo.put("strSql", allStrSql.toString());
        vo.put("whereCond", lstWhereCond);
        return vo;
    }


    /**
     * 直接转化表名
     *
     * @param tableName
     * @return
     */
    public static String getTableName(String tableName) {
        return getTableName(tableName, CRM_DB);
    }

    /**
     * 直接转化表名
     *
     * @param tableName
     * @param dbFlag
     * @return
     */
    public static String getTableName(String tableName, int dbFlag) {
        return tableName;
    }

    /**
     * 获取类的实例
     *
     * @return
     */
    public static DAOSQLUtils singleton() {
        return SingletonHolder.instance;
    }


    private DAOSQLUtils() {
    }

    static class SingletonHolder {
        static DAOSQLUtils instance = new DAOSQLUtils();
    }

    /**
     * 获取表名的完整写法。复杂语句可以单独使用此函数转换表名
     *
     * @param tableName String
     * @return String
     */
    protected String getTotalTableName(String tableName, int dbFlag) {

        String newValue = null;
        if (tableName != null && !tableName.trim().equals("")) {

            Object key = tableName.toLowerCase().trim();
            String value = null;
            try {
                if (dbFlag == CRM_DB) {
                    value = properties.getProperty(key.toString());
                } else if (dbFlag == BSN_DB && b_properties != null) {
                    value = b_properties.getProperty(key.toString());
                }

            } catch (MissingResourceException ex) {
                // 没有资源的错误
            }

            if (value == null || value.trim().equals("")) {
                newValue = convertTableStr(getDefaultUser(),
                        getDefaultDatabase(), getDefaultDatabaseServer(), key
                        .toString());
            } else {

                String[] values = StringUtil.split(value, KEY_DOT);
                if (values != null && values.length == 3) {
                    newValue = convertTableStr(values[0], values[1], values[2],
                            key.toString());
                } else {
                    newValue = key.toString();
                }

            }

        }

        return newValue;

    }

    private String getDbType() {
        if (this.DB_FLAG == DAOSQLUtils.BSN_DB) {
            return b_dbType;
        } else
            return dbType;
    }

    private String getDefaultDatabase() {
        if (this.DB_FLAG == DAOSQLUtils.BSN_DB) {
            return b_defaultDatabase;
        } else
            return defaultDatabase;
    }

    private String getDefaultUser() {
        if (this.DB_FLAG == DAOSQLUtils.BSN_DB) {
            return b_defaultUser;
        } else
            return defaultUser;
    }

    private String getDefaultDatabaseServer() {
        if (this.DB_FLAG == DAOSQLUtils.BSN_DB) {
            return b_defaultDatabaseServer;
        } else
            return defaultDatabaseServer;
    }

    static class Counter {
        int num = 0;
    }

    /**
     * 分析语句，得到所有表名
     *
     * @param sqlStr String
     * @return HashMap
     */
    public HashMap analyseSql(String sqlStr) {
        HashMap hm = new HashMap();

        // 词法分析
        String[] words = splitWord(sqlStr);

        HashMap tables = new HashMap();


        // 语法分析
        tableAnalyse(new Counter(), words, tables);

        return tables;
    }

    /**
     * 分词，在做SQL分析之前，先进行词法分析，找出SQL里面有意义的词汇
     *
     * @param sql
     * @return
     * @author leadyu
     */
    private String[] splitWord(String sql) {
        ArrayList words = new ArrayList();

        String[] spaceSplit = StringUtils.split(sql, " ");

        for (int i = 0; i < spaceSplit.length; i++) {
            String word = spaceSplit[i];
            word = replaceAll(word, ",", "#,#");
            word = replaceAll(word, "(", "#(#");
            word = replaceAll(word, ")", "#)#");

            String[] ws = StringUtils.split(word, "#");
            for (int c = 0; c < ws.length; c++) {
                if (ws[c] != null && !"".equals(ws[c].trim())) {
                    words.add(ws[c]);
                }
            }

        }

        String[] result = new String[words.size()];
        words.toArray(result);
        return result;
    }


    /**
     * 对表名的语法分析
     *
     * @param words
     * @return
     */
    private void tableAnalyse(Counter i, String[] words, Map tables) {

        for (; i.num < words.length; i.num++) {
            //如果遇到'('跳入下一个子句语法分析
            if ("(".equals(words[i.num].trim().toLowerCase())) {
                i.num++;
                tableAnalyse(i, words, tables);
                continue;
            }
            // 如果遇到')'退出当前子句语法分析
            if (")".equals(words[i.num].trim().toLowerCase())) {
                return;
            }

            // 如果遇到from
            if ("from".equals(words[i.num].trim().toLowerCase())) {
                //System.out.print("");
                for (i.num++; i.num < words.length; i.num++) {
                    String fromNext = words[i.num];

                    // 如果遇到'('跳入下一个子句语法分析
                    if ("(".equals(fromNext.trim().toLowerCase())) {
                        i.num++;
                        tableAnalyse(i, words, tables);
                        continue;
                    }
                    // 如果遇到')'退出当前子句语法分析
                    if (")".equals(fromNext.trim().toLowerCase())) {
                        return;
                    }
                    //如果遇到'where,into'from表达式结束
                    if ("where".equals(fromNext.trim().toLowerCase()) || "into".equals(fromNext.trim().toLowerCase())) {
                        break;
                    }
                    // 如果是,则前面的就是表名
                    if (",".equals(fromNext.trim())) {
                        //什么也不做
                    } else {
                        //如果单词前面是from或者,以及join说明是表名
                        if ("from".equals(words[i.num - 1]) || ",".equals(words[i.num - 1]) || "join".equals(words[i.num - 1])) {
                            //有一种情况除外:如果单词后面是from，当前的from表达式结束 add by RyoUehara 090914
                            if (i.num + 1 < words.length) {
                                String currentNext = words[i.num + 1];
                                if ("from".equals(currentNext.toLowerCase())) {
                                    break;
                                }
                            }
                            tables.put(words[i.num], words[i.num]);
                            //System.out.print("");
                        }
                    }
                }
            }

            if (i.num >= words.length) break;

            // 如果遇到update
            if ("update".equals(words[i.num].trim().toLowerCase())) {
                i.num++;
                if (i.num >= words.length) break;
                tables.put(words[i.num], words[i.num]);

            }

            if (i.num >= words.length) break;

            // 如果遇到into
            if ("into".equals(words[i.num].trim().toLowerCase())) {
                i.num++;
                if (i.num >= words.length) break;
                tables.put(words[i.num], words[i.num]);

            }

        }

    }

    private void replaceTableNames(HashMap tableNamesHM, int dbFlag) {
        if (tableNamesHM != null && tableNamesHM.keySet() != null) {
            Iterator it = tableNamesHM.keySet().iterator();
            Object key = null;
            String newValue = null;
            while (it.hasNext()) {
                key = it.next();

                newValue = getTotalTableName(key.toString(), dbFlag);

                tableNamesHM.put(key, newValue);

            }
        }

    } // replaceTableNames

    /**
     * 处理语句，替换语句中需要用于分割的关键字为小写，表名前的,需要加空格
     *
     * @param tableNamesHM HashMap
     */

    private String sqlKeyToLowerCase(String sqlStr) {
        String[] strs = StringUtils.split(sqlStr, " ");
        String value = null;
        for (int k = 0; k < strs.length; k++) {

            value = strs[k].trim();

            for (int i = 0; i < SQL_KEY_SET.length; i++) {
                if (value.equalsIgnoreCase(SQL_KEY_SET[i])) {
                    strs[k] = SQL_KEY_SET[i];
                    break;
                }
            }

        }
        String newStr = null;
        StringBuffer sb = new StringBuffer(S_SPACE);
        for (int k = 0; k < strs.length; k++) {
            sb.append(S_SPACE).append(strs[k]).append(S_SPACE);

        }
        newStr = sb.toString();
        newStr = replaceAll(newStr, ",", " , ");

        newStr = replaceAll(newStr, "(", " (");

        return newStr;
    } // sqlKeyToLowerCase

    /**
     * 给表名加前缀 iom1@crm:informix.
     *
     * @param user           String
     * @param database       String
     * @param databaseServer String
     * @param tableName      String
     * @return String
     */
    private String convertTableStr(String user, String database,
                                   String databaseServer, String tableName) {
        StringBuffer sb = new StringBuffer();

        if (tableName.indexOf('.') >= 0) {
            return tableName;
        }

        if (this.getDbType().equals(DAOSQLUtils.KEY_DBTYPE_INFORMIX)) {
            sb.append(database).append("@").append(databaseServer).append(":")
                    .append(user).append(".").append(tableName);
            return sb.toString();
        } else if (this.getDbType().equals(DAOSQLUtils.KEY_DBTYPE_ORACLE)) {
            sb.append(user).append(".").append(tableName);
            return sb.toString();
        } else {
            return tableName;

        }

    } // convertTableStr

    /**
     * 重新组装sql语句
     *
     * @param sqlStr       String
     * @param tableNamesHM HashMap
     * @return String
     */
    private String assembleSql(String sqlStr, HashMap tableNamesHM) {
        String newSqlStr = sqlStr + S_SPACE;
        Iterator it = tableNamesHM.keySet().iterator();
        String key = null;
        String value = null;
        while (it.hasNext()) {
            key = (String) it.next();
            value = (String) tableNamesHM.get(key);
            newSqlStr = replaceAll(newSqlStr, S_SPACE + key + S_SPACE, S_SPACE
                    + value + S_SPACE);

        }
        newSqlStr = replaceAll(newSqlStr, S_SPACE + "," + S_SPACE, ",");
        return newSqlStr;

    } // assembleSql

    /**
     * @param sqlStr String
     * @return String
     */
    protected String convertSql(String sqlStr, int dbFlag) {
        HashMap tableNamesHM = null;

        sqlStr = sqlKeyToLowerCase(sqlStr);

        // 分析sqlStr,得到需要增加前缀的表名集合
        tableNamesHM = analyseSql(sqlStr);

        // 替换所有前缀
        replaceTableNames(tableNamesHM, dbFlag);

        // 组装新的语句
        String newStr = assembleSql(sqlStr, tableNamesHM);
        tableNamesHM.clear();
        newStr = replaceAll(newStr, " (", "(");

        return newStr;

    }

    public static void main(String[] args) {
//		/**
        String sql = "select 1 from t5 left join T6 on w=d and w in(select 1 from t8) and w in(12323) left join t7) d from t1,t2,(select * from t3,t4 where t3.s=t4.d and t3.s in (11212)) tt where tt.e in (12123) into t9";
        String sql2 = "delete from t1 where tt.e in (select * from t2,t3)";
        String sql3 = "insert into t1(t,y)values(a,1)";

        String sql4 = "update t1 set d=f,e=f where tt.e in (select * from t2,t3)";
        HashMap tables = null;
        long t = 0;
        try {
            long begin = System.currentTimeMillis();
            tables = DAOSQLUtils.singleton().analyseSql(sql);

            long end = System.currentTimeMillis();
            t = end - begin;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        long begin = System.currentTimeMillis();
        sql = sql.replaceAll("t", "wrf");
        long end = System.currentTimeMillis();
    }


    public static String doPreFilter(boolean filterTableName, String sql, int pageIndex, int pageSize) {
        int startIndex = (pageIndex - 1) * pageSize + 1;
        int endIndex = startIndex + pageSize;
        sql = "select * from (select my_table.*,rownum as my_rownum from( " +
                sql +
                " ) my_table where rownum< " + endIndex +
                ") where my_rownum>= " + startIndex;
        return sql;
    }

    public static final String replaceAll(String line, String str1, String str2) {
        StringBuffer newStr = new StringBuffer();
        int lastAppendIndex = 0;

        int start = line.indexOf(str1, lastAppendIndex);

        if (start == -1) return line;

        while (start > -1) {
            newStr.append(line.subSequence(lastAppendIndex, start));
            newStr.append(str2);
            lastAppendIndex = start + str1.length();
            start = line.indexOf(str1, lastAppendIndex);
        }
        newStr.append(line.subSequence(lastAppendIndex, line.length()));

        return newStr.toString();

    }
}

