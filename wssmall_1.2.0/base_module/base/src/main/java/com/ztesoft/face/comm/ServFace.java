package com.ztesoft.face.comm;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.IAction;
import com.ztesoft.common.query.SqlMapExe;
import com.ztesoft.face.comm.FaceContext.ContextObj;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;

/**
 * insert into tfm_services (SERVICE_NAME, MODULE_ID, SERVICE_DESC, CLASS_NAME)
 * values ('SERV', 'WSSNET', '服务提供', 'com.ztesoft.face.comm.ServFace');
 *
 * @author Reason.Yea
 * @version Created Feb 5, 2013
 */
public class ServFace extends AServ implements IAction {

    @Override
	public int perform(DynamicDict dict) throws FrameException {
        String module = (String) dict.getValueByName(MODULE);
        if (StringUtils.isEmpty(module)) {
            return -1;
        }
        //IServHandle handle = FaceFactory.get(module);
        IServHandle handle = SpringContextHolder.getBean(module);
        this.setContext(dict);
        handle.exec();
        return 0;
    }


    /**
     * 线程绑定：包括数据源，数据库工具类和一个HashMap对象，以及request和session对象
     *
     * @param dict
     */
    private void setContext(DynamicDict dict) {
        ContextObj context = new ContextObj();
        Connection conn = dict.GetConnection();
        context.setConn(conn);
        context.setMap(dict.m_Values);
        context.setSqlExe(SqlMapExe.getInstance());

        HttpServletRequest request = null;
        HttpServletResponse response = null;
        HttpSession session = null;
        WebContext ctx = WebContextFactory.get();
        if (ctx != null) {
            request = ctx.getHttpServletRequest();
            response = ctx.getHttpServletResponse();
            session = ctx.getSession();
        }
        context.setRequest(request);
        context.setResponse(response);
        context.setSession(session);

        FaceContext.set(context);
    }

}