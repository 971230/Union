package com.ztesoft.rop.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.rop.core.db.DBConnection;
import com.rop.core.db.SqlMapExe;
import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.rop.common.CurrentThreadVar;

/**
 * 修改ROP数据模型
 * modified by easonwu 2013-10-17
 */
public class RopDaoImpl {

    public static final String APP_DEF = "APP_DEF";
    public static final String APP_FUN_DEF = "APP_FUN_DEF";

    public static final String APP_REQ_LEVEL = "APP_REQ_LEVEL"; // 请求应用
    public static final String APP_SESSION_REQ_LEVEL = "APP_SESSION_REQ_LEVEL"; // 请求应用会话级

    public static final String APP_FUN_REQ_LEVEL = "APP_FNN_REQ_LEVEL"; // 请求应用功能级
    public static final String APP_FUN_SESSION_REQ_LEVEL = "APP_FUN_SESSION_REQ_LEVEL";// 请求应用会话功能级
    public static final String APP_ID = "APP_KEY";
    public static final String APP_LEVEL = "APP_LEVEL";
    public static final String FUN_ID = "FUN_ID";
    public static final String SESSION_ID = "SESSION_ID";

    private static String SELECT_APPLY_INFO = "select p.app_key,p.app_secret,p.validity_period,e.enterprice_code e_code,e.enterprice_name e_name " +
            " from PM_APP p , PM_APP_ACCOUNT a , PM_APP_ENTERPRICE e  " +
            "  where p.acct_id=a.acct_id and a.acct_id=e.acct_id " +
            "  and p.status_cd in ('00F','00A') and a.status_cd='00A' and  p.app_key=?  " +
            " union all " +
            " select p.app_key,p.app_secret,p.validity_period,e.user_name e_code,e.user_name e_name " +
            " from PM_APP p , PM_APP_ACCOUNT a , PM_APP_USER e  " +
            " where p.acct_id=a.acct_id and a.acct_id=e.acct_id " +
            "  and p.status_cd in ('00F','00A') and a.status_cd='00A'  and  p.app_key=?   ";

    private static String SELECT_APP_PRIVILEGE = " select count(1)  AS AL_NUM from ( " +
            " select p.app_id from PM_APP_LEVEL_PRIV a ,PM_APP p ,PM_APP_SERVICE s  where" +
            " a.app_level=p.app_level and s.fun_id=a.fun_id and a.status_cd='00A' and p.status_cd in ('00F','00A') and s.status_cd='00A'" +
            " and p.app_key=? and s.fun_code=? and s.fun_version=? " +
            " union all" +
            " select p.app_id from PM_APP_AUDIT_SERVICE a ,PM_APP p ,PM_APP_SERVICE s  where" +
            " a.app_id=p.app_id and s.fun_id=a.fun_id and a.status_cd='00F' and p.status_cd in ('00F','00A') and s.status_cd='00A'" +
            " and p.app_key=? and s.fun_code=? and s.fun_version=? )";

    private static String SELECT_APP_INVOKE_MAX = "select v.max_num,v.session_max from pm_app a ,pm_app_level_priv v where a.app_level=v.app_level and a.app_id=? and a.status_cd in('00F','00A') and v.status_cd='00A' ";
    private static String SELECT_APP_FUN_INVOKE_MAX = " select v.session_max,v.max_num from PM_APP_LEVEL_PRIV v where STATUS_CD='00A' and v.APP_LEVEL=? and  v.fun_id=? " +
            " union  " +
            " select v.session_max,v.max_num from PM_APP_AUDIT_SERVICE v  where v.status_cd='00F' and v.app_id=? and v.fun_id=?";


    // 应用级
//    private static String SELECT_APP_REQUESTED_NUM = "select count(1) as al_num from rop_req_log   where app_key=? and ((sysdate-req_date)*24*60*60-3600<100)";
//    private static String SELECT_APP_SESSION_REQUESTED_NUM = "select count(1) as al_num from rop_req_log  where app_key=? AND SESSION_ID=? and ((sysdate-req_date)*24*60*60-3600<100)";

    private static String SELECT_APP_REQUESTED_NUM = "select count(1) as al_num from PM_APP_LOG   where app_id=? and ((sysdate-req_date)*24*60*60-3600<100)";
    private static String SELECT_APP_SESSION_REQUESTED_NUM = "select count(1) as al_num from PM_APP_LOG  where app_id=? AND SESSION_ID=? and ((sysdate-req_date)*24*60*60-3600<100)";


    // 方法级
//    private static String SELECT_APP_FUN_REQUESTED_NUM = "select count(1) as al_num from rop_req_log   where app_key=? and fun_code=? and fun_version=? and ((sysdate-req_date)*24*60*60-3600<100)";
    private static String SELECT_APP_FUN_REQUESTED_NUM = "select count(1) as al_num from PM_APP_LOG where app_id=? and fun_id=? and ((sysdate-req_date)*24*60*60-3600<100)";
//    private static String SELECT_APP_FUN_SESSION_REQUESTED_NUM = "select count(1) as al_num from rop_req_log  where app_key=? AND SESSION_ID=? fun_code=? and fun_version=? and ((sysdate-req_date)*24*60*60-3600<100)";

    private static String SELECT_APP_FUN_SESSION_REQUESTED_NUM = "select count(1) as al_num from PM_APP_LOG where app_id=? and fun_id=? AND SESSION_ID=? and ((sysdate-req_date)*24*60*60-3600<100)";


    /**
     * **********************************新增SQL 2013-10-17 *********************************************************
     */
    private static String qryAppInfoSql = "select p.app_id, p.app_key,p.SOURCE_FROM,p.app_name,p.app_secret,p.validity_period,p.app_level,e.enterprice_id owner_id,e.enterprice_code owner_code,e.enterprice_name owner_name,a.acct_name,a.acct_code,a.is_enterprice acct_type  from PM_APP p , PM_APP_ACCOUNT a , PM_APP_ENTERPRICE e   where p.acct_id=a.acct_id and a.acct_id=e.acct_id and p.status_cd in('00F','00A') and a.status_cd='00A'  and p.app_key=? \n" +
            "union all\n" +
            "select p.app_id,p.app_key,p.SOURCE_FROM,p.app_name,p.app_secret,p.validity_period,p.app_level,e.user_id owner_id,e.user_name owner_code,e.user_name owner_name,a.acct_name,a.acct_code,a.is_enterprice acct_type  from PM_APP p , PM_APP_ACCOUNT a , PM_APP_USER e   where p.acct_id=a.acct_id and a.acct_id=e.acct_id  and p.status_cd in('00F','00A') and a.status_cd='00A' and p.app_key=?";


    private static String qryServiceSql = "select s.fun_id,s.fun_code,s.fun_name,s.fun_version from pm_app_service s where s.fun_code=? and s.fun_version=?";

    private static final String insertLogSql = " insert into pm_app_log(log_id,app_id,fun_code,fun_version,session_id,request_id,req_date, " +
            " req_content,res_date,res_content,req_ip,data,exception_msg,failure,success,elapsed,fun_id,req_path,resp_path) " +
            " values(seq_app_log.nextval,?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'), " +
            " ?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,?,?,?,?) ";

    /***********************************************************************************************/


    private final String sql="select p.app_id, P.APP_KEY, p.SOURCE_FROM, p.THEME_SOURCE_FROM,  p.app_name,p.app_secret, p.validity_period, p.app_level, p.APP_ADDRESS,'0' acct_code, '0' acct_name,'0' acct_type,'0' owner_id,'0' owner_code,'0' owner_name from PM_APP p where p.app_key=? and p.app_key=?  ";

    private final String sql_list="select p.app_id, P.APP_KEY, p.SOURCE_FROM, p.THEME_SOURCE_FROM,  p.app_name,p.app_secret, p.validity_period, p.app_level, p.APP_ADDRESS,'0' acct_code, '0' acct_name,'0' acct_type,'0' owner_id,'0' owner_code,'0' owner_name  from PM_APP p where p.app_key=?  and p.app_key=? ";

    
    
    /**
     *
     */
    public RopDaoImpl() {
        int yy = 0;
    }

    /**
     * @return
     */
    public static RopDaoImpl getInstance() {
        return new RopDaoImpl();
    }

    /**
     * @param pramas
     * @return
     * @throws Exception
     */
    public int findRquestNumber(Map<String, String> pramas, String level,
                                boolean neddSession) throws Exception {
        if (pramas == null || pramas.isEmpty()) {
            return 0;
        }
        Connection conn = null;
//        String appKey = pramas.get(APP_KEY);
        List<String> sqlParamsList = new ArrayList<String>();
        sqlParamsList.add(String.valueOf(CurrentThreadVar.getVar().getAppId()));

        String sql = SELECT_APP_REQUESTED_NUM;

        if ((!StringUtils.isEmpty(level)) && (level.equals(APP_SESSION_REQ_LEVEL))) {
            sql = SELECT_APP_SESSION_REQUESTED_NUM;
        } else if ((!StringUtils.isEmpty(level)) && (level.equals(APP_FUN_REQ_LEVEL))) {
            sql = SELECT_APP_FUN_REQUESTED_NUM;
        } else if ((!StringUtils.isEmpty(level)) && (level.equals(APP_FUN_SESSION_REQ_LEVEL))) {
            sql = SELECT_APP_FUN_SESSION_REQUESTED_NUM;
        }

        if ((!StringUtils.isEmpty(level)) && (level.equals(APP_FUN_REQ_LEVEL) || level.equals(APP_FUN_SESSION_REQ_LEVEL))) {
            sqlParamsList.add(String.valueOf(CurrentThreadVar.getVar().getFunId()));
        }
        if (neddSession) {
            sqlParamsList.add(pramas.get(SESSION_ID));
        }

        int rquestNum = 0;
        try {
            conn = DBConnection.getConnection();
            SqlMapExe sqlMapExe = new SqlMapExe(conn);
            String returnValue = sqlMapExe.querySingleValue(sql, sqlParamsList);

            if (!StringUtils.isEmpty(returnValue)) {
                try {
                    rquestNum = Integer.parseInt(returnValue);
                } catch (NumberFormatException exv) {
                    rquestNum = 0;
                }
            }
            return rquestNum;
        } catch (Exception ex) {
            return 0;
        } finally {
            if (conn != null) {
                conn.close();
            }
            conn = null;
        }

    }


    /**
     * 查询应用或应用方法定义的一天最大调用次数
     *
     * @param pramas
     * @return
     * @throws Exception
     */
    public Map qryAppInfoBy(String appKey, String method, String version) {
        if (StringUtils.isEmpty(appKey)) {
            return null;
        }

        if(EopSetting.ROP_VALI.equals("no")){
        	qryAppInfoSql = sql_list;
        	qryServiceSql = sql_list;
        }
        Connection conn = null;
        Map resultMap = null;
        try {
            conn = DBConnection.getConnection();
            SqlMapExe sqlMapExe = new SqlMapExe(conn);
            List list = sqlMapExe.queryForMapListBySql(qryAppInfoSql, new String[]{appKey, appKey});
            if (null == list || list.isEmpty()) return null;
            resultMap = (Map) list.get(0);

            list = sqlMapExe.queryForMapListBySql(qryServiceSql, new String[]{method, version});
            if (list != null && !list.isEmpty()) {
                resultMap.putAll((Map) list.get(0));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            conn = null;
        }
        return resultMap;
    }


    /**
     * 查询应用或应用方法定义的一天最大调用次数
     *
     * @param pramas
     * @return
     * @throws Exception
     */
    public Map findDefineInvokeTimes(Map<String, String> pramas, String level)
            throws Exception {
        if (pramas == null || pramas.isEmpty()) {
            return null;
        }
        Connection conn = null;
        String appId = pramas.get(APP_ID);
        String sql = "select v.max_num,v.session_max,a.app_key from pm_app a ,pm_app_level_priv v where a.app_level=v.app_level and a.app_key=? and a.status_cd in('00F','00A') and v.status_cd='00A'";
        String[] sqlParams = new String[]{appId};
        if ((!StringUtils.isEmpty(level)) && level.equals(APP_FUN_DEF)) {
            sqlParams = new String[]{pramas.get(APP_LEVEL), pramas.get(FUN_ID), pramas.get(APP_ID), pramas.get(FUN_ID)};
            sql = SELECT_APP_FUN_INVOKE_MAX;
        }
        Map resultMap = null;
        try {
            conn = DBConnection.getConnection();
            SqlMapExe sqlMapExe = new SqlMapExe(conn);
            List list = sqlMapExe.queryForMapListBySql(sql, sqlParams);
            if (list != null && !list.isEmpty()) {
                resultMap = (Map) list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            conn = null;
        }
        return resultMap;
    }

    /**
     * 根据申请id取对应的密码
     *
     * @param pramas
     * @return
     * @throws Exception
     */
    public Map findAppSecretByAppkey(Map<String, String> pramas)
            throws Exception {
        if (pramas == null || pramas.isEmpty()) {
            return null;
        }
        Connection conn = null;
        String appKey = pramas.get("APP_KEY");
        Map resultMap = null;
        if(EopSetting.ROP_VALI.equals("no")){
        	SELECT_APPLY_INFO = sql_list;
        }
        
        try {
            conn = DBConnection.getConnection();
            SqlMapExe sqlMapExe = new SqlMapExe(conn);
            List list = sqlMapExe.queryForMapListBySql(SELECT_APPLY_INFO,
                    new String[]{appKey, appKey});

            if (list != null && !list.isEmpty()) {
                resultMap = (Map) list.get(0);
            }
        } catch (Exception ex) {

        } finally {
            if (conn != null) {
                conn.close();
            }
            conn = null;
        }
        return resultMap;
    }

    /**
     * 是否有权限
     *
     * @param pramas
     * @return
     * @throws Exception
     */
    public String hasAppFunPrivilege(Map<String, String> pramas)
            throws Exception {
        if (pramas == null || pramas.isEmpty()) {
            return "0";
        }
        Connection conn = null;
        String appKey = pramas.get("APP_KEY");
        String funCode = pramas.get("FUN_CODE");
        String funVersion = pramas.get("FUN_VERSION");
        Map resultMap = null;
        try {
            conn = DBConnection.getConnection();
            SqlMapExe sqlMapExe = new SqlMapExe(conn);
            List list = sqlMapExe.queryForMapListBySql(SELECT_APP_PRIVILEGE,
                    new String[]{appKey, funCode, funVersion, appKey, funCode, funVersion});

            if (list != null && !list.isEmpty()) {
                resultMap = (Map) list.get(0);
                //
                String al_num = (String) resultMap.get("AL_NUM".toLowerCase());
                return al_num;
            }
        } catch (Exception ex) {

        } finally {
            if (conn != null) {
                conn.close();
            }
            conn = null;
        }
        return "0";
    }

    public static final String defaultPattern = "yyyy-MM-dd HH:mm:ss";


    public static String formatDate(Date date) {
        return formatDate(date, defaultPattern);
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null)
            return null;
        if (pattern == null) {
            throw new IllegalArgumentException("pattern is null");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            return formatter.format(date);
        }
    }
    /**
     * 记录请求的日志
     *
     * @param ropRequestContext
     */
    public void writeServiceLog(long app_id, String fun_code, String fun_version, String session_id, String request_id,
                                Date req_date, String req_content, Date res_date, String res_content,
                                String req_ip, long data, String exception_msg, long failure, long success, long elapsed, long fun_id) {
        Connection conn = null;
        JdbcTemplate jdbcTemplate = null;
        try {
        	jdbcTemplate = (JdbcTemplate) ApiContextHolder.getBean("jdbcTemplate");
    		conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
    		SqlMapExe sqlMapExe = new SqlMapExe(conn);
            List<String> sqlParams = new ArrayList<String>();
            sqlParams.add(String.valueOf(app_id));
            sqlParams.add(fun_code);
            sqlParams.add(fun_version);
            sqlParams.add(session_id);
            sqlParams.add(request_id);
            sqlParams.add(formatDate(req_date));
            sqlParams.add(req_ip);
            sqlParams.add(formatDate(res_date));
            sqlParams.add(String.valueOf(data));
            sqlParams.add(exception_msg);
            sqlParams.add(String.valueOf(failure));
            sqlParams.add(String.valueOf(success));
            sqlParams.add(String.valueOf(elapsed));
            sqlParams.add(String.valueOf(fun_id));
            if(!StringUtils.isEmpty(res_content) && res_content.length()>3000)
            	res_content= res_content.substring(0, 3000);
            if(!StringUtils.isEmpty(req_content) && req_content.length()>3000)
            	req_content= req_content.substring(0, 3000);
            sqlParams.add(req_content);
            sqlParams.add(res_content);
            try{
            	String insertLogSql ="insert into pm_app_log(log_id,app_id,fun_code,fun_version,session_id,request_id,req_date,req_ip," +
		            "res_date,data,exception_msg,failure,success,elapsed,fun_id,req_content,res_content) " +
		            " values(seq_app_log.nextval,?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'), " +
		            " ?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),  ?,?,     ?,?,?,?,?,?) ";
            	sqlMapExe.excuteUpdate(insertLogSql, sqlParams);
            }catch(Exception e){}

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        	
            if (conn != null) {
                try {
                	DataSourceUtils.doReleaseConnection(conn, jdbcTemplate.getDataSource());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            conn = null;
        }
    }
   

}
