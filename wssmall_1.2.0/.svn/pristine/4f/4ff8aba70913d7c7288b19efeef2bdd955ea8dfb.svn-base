package com.ztesoft.ibss.ct.config;

import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.SysSet;
import com.powerise.ibss.util.WBCacheUtil;
import com.ztesoft.ibss.common.util.ConnectionManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qin.guoquan
 * @desc 系统启动时读取库表静态数据
 * @date 2010-12-17 上午03:10:08
 */
public class CTConfig {
    private Logger m_Logger = Logger.getLogger(CTConfig.class);
    private static final long serialVersionUID = 8256030739092404414L;
    private static String m_DefaultDBName = "dataSource";
    private static CTConfig instance = new CTConfig();

    private CTConfig() {
    }

    public static CTConfig getInstance() {
        return instance;
    }

    public void init() {
        initConfigData();
    }

    /**
     * @author Reason.Yea
     * 重新加载数据缓存，此处的缓存信息不通过ActionDispatch.dispatch来操作，需要手动控制事务和关闭链接
     */
    private void initConfigData() {
        //加载缓存前首先清掉缓存
        clearCache();
        Connection conn = null;
        try {
            ConnectionManager.beginTrans(false);
            conn = ConnectionManager.getConnection();
            doCache(conn);
        } catch (Exception e) {
            m_Logger.debug("获取框架配置错误:" + e);
        } finally {
           try{
               ConnectionManager.close();
           }catch (Exception e){
               e.printStackTrace();
           }
        }
    }

    private void doCache(Connection conn) {
        //此处可以添加需要cache的数据
        try {
            ConfigUtil.cacheCtConfig(conn);
            m_Logger.debug("cacheCtConfig success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isTestMode() {
        String mode = "";
        try {
            mode = SysSet.getSystemSet("TEST_MODE");
        } catch (FrameException e) {
            e.printStackTrace();
        }
        if (mode == null || mode.equals("")) return false;
        else return mode.equals("true");
    }


    /**
     * 清理掉对应的缓存
     */
    private void clearCache() {
        WBCacheUtil.clearCache(ConfigUtil.CtConfigCatalog);
    }


    /**
     * ct_coinfig表数据获取
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        Object o = WBCacheUtil.getCache(ConfigUtil.CtConfigCatalog, key);
        if (null == o) {
            CTConfig.getInstance().init();
            o = WBCacheUtil.getCache(ConfigUtil.CtConfigCatalog, key);
        }
        return o == null ? "" : (String) o;

    }

    public String getValueDesc(String key) {
        key += "_DESC";
        Object o = WBCacheUtil.getCache(ConfigUtil.CtConfigCatalog, key);
        if (null == o) {
            CTConfig.getInstance().init();
            o = WBCacheUtil.getCache(ConfigUtil.CtConfigCatalog, key);
        }
        return o == null ? "" : (String) o;

    }

    /**
     * 获取规则数据
     *
     * @param rule_action
     * @param rule_sql_type
     * @param precess_type
     * @return
     */
    public List getRule(String rule_action, String rule_sql_type, String precess_type) {
        String key = ConfigUtil.getRuleKey(rule_action, rule_sql_type, precess_type);
        Object o = WBCacheUtil.getCache(ConfigUtil.ActionRuleCatalog, key);
        return o == null ? new ArrayList() : (List) o;

    }
}

