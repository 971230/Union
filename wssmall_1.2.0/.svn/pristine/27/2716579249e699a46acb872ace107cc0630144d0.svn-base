/**
 * <p>Title: UOS Flow工作流管理系统</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 中兴通讯运营支撑开发部</p>
 * @author UOS平台项目组
 * @version 1.0
 */
package com.powerise.ibss.framework;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;

/**
 * 访问数据源对象的Singleton帮助类
 * @version 1.0
 * @author guojun
 */
public class DataSourceHelper {

    /**Singleton的类实例*/
    private static DataSourceHelper instance = new DataSourceHelper();

    /**缓存数据源的HashMap*/
    private Map dsMap = Collections.synchronizedMap(new HashMap());

    /**
     * DataSourceHelper constructor comment.
     */
    private DataSourceHelper() {
        super();
    }

    /**
     * Insert the method's description here.
     * @param dsName the name of DataSource
     * @return javax.sql.DataSource
     * @throws NamingException The exception description.
     */
    public DataSource getDataSource(String dsName)
        throws NamingException {
        try {
            // See if we already have the DataSource
            DataSource ds = (DataSource) dsMap.get(dsName);

            if (ds == null) {
                ds = SpringContextHolder.getBean(dsName);
                dsMap.put(dsName, ds);
            }
            return ds;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Insert the method's description here.
     * @param dsName java.lang.String
     */
    public void isBad(String dsName) {
        // remove the DataSource from the Hashtable
        dsMap.remove(dsName);
    }

    /**
     * the only instance of DataSourceHelper because of singleton pattern
     * @return DataSourceHelper
     */
    public static DataSourceHelper singleton() {
        return instance;
    }

}
