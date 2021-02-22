package com.ztesoft.api.annotation.tool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class ApiDocInitServlet extends HttpServlet {
	
	@Override
	public void init() throws ServletException {
        boolean run =Boolean.valueOf(this.getInitParameter("run"));
        if(run){
            String path = getServletContext().getRealPath("/");
            AnnotationTest test = new AnnotationTest();
            test.f2(path);
        }
	}


}
