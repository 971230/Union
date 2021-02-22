package com.ztesoft.rop.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.ztesoft.rop.route.ServiceRouter;

public class ZRopServlet extends HttpServlet {
    protected final Logger logger = Logger.getLogger(getClass());
    private ServiceRouter serviceRouter = null;

    /**
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        serviceRouter.service(req, resp);
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ApplicationContext ctx = getApplicationContext(servletConfig);
        try{
	        this.serviceRouter = ctx.getBean(ServiceRouter.class);
	        if (this.serviceRouter == null) {
	            logger.info("在Spring容器中未找到" + ServiceRouter.class.getName()
	                    + "的Bean,请在Spring配置文件中通过<aop:annotation-driven/>安装rop框架。");
	        }
        }catch(Exception e){
        	e.printStackTrace();
        	
        }
    }

    private ApplicationContext getApplicationContext(ServletConfig servletConfig) {
        return (ApplicationContext) servletConfig.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
    }
}
