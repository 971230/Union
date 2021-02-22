package com.ztesoft.face.frame;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.DBUtil;
import com.ztesoft.common.query.SqlMapExe;
import com.ztesoft.face.comm.AServ;
import com.ztesoft.face.comm.FaceContext;
import com.ztesoft.face.comm.FaceContext.ContextObj;
import com.ztesoft.face.comm.IServHandle;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;

/**
 * 通过java或者jsp直接执行IServHandle接口定义的模块中的函数和方法
 *
 * @author Reason.Yea
 * @version Created Feb 17, 2013
 */
public class ServExec extends AServ {
    private static Logger logger = Logger.getLogger(ServExec.class);

    public static int exec(ServDict dict) throws FrameException {

        String module = (String) dict.getValue(MODULE);
        if (StringUtils.isEmpty(module)) {
            return -1;
        }

        boolean commit = true;
        Connection conn = null;
        try {
            conn = dict.getConn();
            if (conn != null) {
                throw new FrameException(-20130217, module + "已经建立了数据库连接!");
            }
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            //IServHandle handle = FaceFactory.get(module);
            IServHandle handle = SpringContextHolder.getBean(module);
            dict.setConn(conn);
            setContext(dict);

            // 调用逻辑实现类exec 方法
            logger.debug("ServExec module:[" + module + "] begin excute!!");
            handle.exec();
            logger.debug("ServExec module:[" + module + "] end excute!!");
        } catch (FrameException fe) {
            logger.info("ServExec module:[" + module + "] excute error:" + fe.getErrorMsg());
            fe.printStackTrace();
            commit = false;
        } catch (java.lang.Throwable th) {
            logger.info("系统调用异常:ServExec module:[" + module + "] excute error:" + th.getMessage());
            th.printStackTrace();
            commit = false;
        } finally {
            try {
                // 统一关闭数据库连接
                if (conn != null) {
                    if (commit) {// 一切执行正常
                        conn.commit();
                    } else {// 出现异常
                        conn.rollback();
                    }
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                logger.info("关闭数据库连接时，出现异常:ServExec module:[" + module + "] excute error:" + e.getMessage());
                e.printStackTrace();
            }
        }
        dict.setConn(null);

        return 0;
    }

    /**
     * 线程绑定：包括数据源，数据库工具类和一个HashMap对象，以及request和session对象
     *
     * @param dict
     */
    private static void setContext(ServDict dict) {
        ContextObj context = new ContextObj();
        Connection conn = dict.getConn();
        context.setConn(conn);
        context.setSqlExe(SqlMapExe.getInstance());
        context.setRequest(dict.getRequest());
        context.setSession(dict.getSession());
        context.setMap(dict.m_values);
        FaceContext.set(context);
    }

}
