package com.ztesoft.ibss.common.util;

import com.powerise.ibss.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-09-11 18:49
 * To change this template use File | Settings | File Templates.
 *
 * 链接管理
 */
public class ConnectionManager {
    private static ThreadLocal<Connection> manager = new ThreadLocal<Connection>();

    private static Connection conn = null;

    public static void beginTrans(boolean beginTrans) throws Exception {
        if (manager.get() == null || manager.get().isClosed()) {
            conn = DBUtil.getConnection();
            if (beginTrans) {
                conn.setAutoCommit(false);
            }
            manager.set(conn);
        }
    }

    public static Connection getConnection() throws Exception {
        return manager.get();
    }

    public static void close() throws SQLException {
        try {
            manager.get().setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        manager.get().close();
        manager.set(null);
    }

    public static void commit() throws SQLException {
        try {
            manager.get().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            manager.get().setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rollback() throws SQLException {
        try {
            manager.get().rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            manager.get().setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
