package com.rop.core.cache.job;

import java.sql.Connection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.rop.core.cache.AppInfoCache;
import com.rop.core.db.SqlMapExe;
import com.rop.core.utils.ConvertUtils;
import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.remote.app.service.impl.AppLocalServiceImpl.PM_APP;
import com.ztesoft.remote.pojo.AppInfo;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-07 10:05
 * To change this template use File | Settings | File Templates.
 */
public class InitCache {
    private Logger logger= LoggerFactory.getLogger(getClass());

//    public void execute() {
//    	//ip验证处理 add by wui所有定时任务都需要加上限制条件
//		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"execute"))
//			return ;
//        this.cacheAppInfo();
//
//    }

    static{
    	cacheAppInfo();
    }
    //初始化app信息
    public static void cacheAppInfo() {
        AppInfoCache.instance().clear();

       // String sql="select p.app_id, P.APP_KEY,p.SOURCE_FROM,p.app_name,p.app_secret,p.validity_period,p.app_level,e.enterprice_id owner_id,e.enterprice_code owner_code,e.enterprice_name owner_name,a.acct_name,a.acct_code,a.is_enterprice acct_type  from PM_APP p , PM_APP_ACCOUNT a , PM_APP_ENTERPRICE e   where p.acct_id=a.acct_id and a.acct_id=E.ACCT_ID  \n" +
        //        "union all\n" +
        //        "select p.app_id,p.app_key,P.SOURCE_FROM,p.app_name,p.app_secret,p.validity_period,p.app_level,e.user_id owner_id,e.user_name owner_code,e.user_name owner_name,a.acct_name,a.acct_code,a.is_enterprice acct_type  from PM_APP p , PM_APP_ACCOUNT a , PM_APP_USER e   where p.acct_id=a.acct_id and a.acct_id=E.ACCT_ID ";
        String sql="select p.app_id, P.APP_KEY, p.SOURCE_FROM, p.THEME_SOURCE_FROM,  p.app_name,p.app_secret, p.validity_period, p.app_level, p.APP_ADDRESS,'0' acct_code, '0' acct_name,'0' acct_type,'0' owner_id,'0' owner_code,'0' owner_name  from PM_APP p";
        Connection connection = null;
        JdbcTemplate jdbcTemplate = null;
        try{
           // ConnectionManager.beginTrans(false);
          //  Connection connection=ConnectionManager.getConnection();
            jdbcTemplate = (JdbcTemplate) ApiContextHolder.getBean("jdbcTemplate");
            connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
            
            SqlMapExe sqlMapExe=new SqlMapExe(connection);
            List<PM_APP> list= sqlMapExe.queryForMapList(sql);
            PM_APP app=null;
            AppInfo info=null;
            for(Object obj : list){
               info= ConvertUtils.convert(app);
               AppInfoCache.instance().set(info.getAppKey(),info);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
        	if (connection != null) {
                try {
                	DataSourceUtils.doReleaseConnection(connection, jdbcTemplate.getDataSource());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        	connection = null;
        }
    }
}
