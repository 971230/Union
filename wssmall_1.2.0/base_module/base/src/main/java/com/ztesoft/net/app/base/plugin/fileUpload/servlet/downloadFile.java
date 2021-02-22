package com.ztesoft.net.app.base.plugin.fileUpload.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ztesoft.cms.page.file.SftpUpLoadImpl;
import com.ztesoft.net.eop.sdk.context.SftpConfigSetting;

/**
 * 解决方案管理下载Servlet
 * @author Lin.xuerui
 * Servlet implementation class downloadFile
 */
public class downloadFile extends HttpServlet {
	private static Logger logger = Logger.getLogger(downloadFile.class);
	private static final long serialVersionUID = 1L;
       
    public downloadFile() {
        super();
    }

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		String solutionName = request.getParameter("solutionName");
		fileName = fileName.substring(fileName.lastIndexOf("/")+1,fileName.length());
        String realPath = request.getSession().getServletContext().getRealPath("");
        
        try {
        	File file = new File(realPath + "/" + fileName);
        	if(!file.exists())file.createNewFile();
        	logger.info("absolutePath:" + realPath + "/" + fileName);
        	
        	SftpUpLoadImpl sftpImp = new SftpUpLoadImpl();
        	HashMap config = new HashMap();
        	config.put("server", SftpConfigSetting.SFTP_SERVER);
        	config.put("username", SftpConfigSetting.SFTP_USERNAME);
        	config.put("password", SftpConfigSetting.SFTP_PASSWORD);
        	Map param = new HashMap();
        	param.put("path", SftpConfigSetting.SFTP_PATH);
        	param.put("fileName", fileName);
        	param.put("saveFile", file);
        	
        	sftpImp.download(config, param);
        	
			if (file.exists()) {
			    response.reset(); 
			    String postfix = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
			    if (postfix != null) {
			        if (("xls".equals(postfix)) || ("xlsx".equals(postfix))) {
			          response.setContentType("application/msexcel");
			        }
			        else if (("doc".equals(postfix)) || ("docx".equals(postfix))) {
			          response.setContentType("application/msword");
			        }
			        else if (("zip".equals(postfix)) || ("rar".equals(postfix))) {
			          response.setContentType("application/zip");
			        }
			        else if (("gif".equals(postfix)) || ("jpg".equals(postfix)) || ("png".equals(postfix)) || ("jpeg".equals(postfix))) {
			          response.setContentType("image/gif");
			        }
			        else if ("pdf".equals(postfix)) {
			          response.setContentType("image/pdf");
			        }else if("txt".equals(postfix)){
			        	response.setContentType("text/plain");
			        }
			        else{
			        	response.setContentType("application/text");
			        }
			      
			    }
			    solutionName = java.net.URLEncoder.encode(solutionName, "UTF-8");
			    response.addHeader("Content-Disposition","attachment; filename=\""+solutionName+"."+postfix+"\"");
//            response.setHeader("Content-disposition", "attachment;filename=\""+java.net.URLEncoder.encode(fileName, "UTF-8")+"\"");
			    int fileLength = (int) file.length();  
			    response.setContentLength(fileLength);  
			    if (fileLength != 0) {
			        InputStream inStream = new FileInputStream(file);  
			        byte[] buf = new byte[4096];  
			        ServletOutputStream servletOS = response  
			                .getOutputStream();  
			        int readLength;  
			        while (((readLength = inStream.read(buf)) != -1)) {  
			            servletOS.write(buf, 0, readLength);  
			        }  
			        inStream.close();  
			        servletOS.flush();  
			        servletOS.close();
			    } 
			    file.delete();
			}else{
				//文件不存在
				response.sendRedirect("/shop/admin/solutionManage!fileError.do");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
