package com.ztesoft.net.mall.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.soc.fastdfs.IDfsManager;

/**
 * 订单图片展示
 * 
 * @author liu.quan68@ztesoft.com
 *
 * @date 2017年2月25日
 */
public class OrderShowPhotoServlet extends HttpServlet {

	private static final long serialVersionUID = 5432928609424863676L;
	
	private static Logger logger = Logger.getLogger(OrderShowPhotoServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		request.setCharacterEncoding("UTF-8");
        response.setContentType("image/*; charset=UTF-8");
		execute(request, response);
	}
	
	/**
	 * 订单图片展示
	 *
	 *@param request
	 *@param response
	 *@throws ServletException
	 *@throws IOException
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月27日
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		ServletOutputStream out = null;
		try {
			//图片id
			String file_path = request.getParameter("file_path");
			IDfsManager dfsManager = SpringContextHolder.getBean("dfsManager");
			byte[] buff = dfsManager.getFileById(file_path);
			out = response.getOutputStream();
			out.write(buff);
		} catch (Exception e) {
			logger.info(e, e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
