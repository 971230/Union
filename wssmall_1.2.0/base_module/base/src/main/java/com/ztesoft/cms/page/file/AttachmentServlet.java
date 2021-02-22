package com.ztesoft.cms.page.file;
/**
 * 	<servlet>
 <servlet-name>AttachmentServlet</servlet-name>
 <servlet-class>com.ztesoft.cms.page.file.AttachmentServlet</servlet-class>
 </servlet>
 <servlet-mapping>
 <servlet-name>AttachmentServlet</servlet-name>
 <url-pattern>/AttachmentServlet</url-pattern>
 </servlet-mapping>
 */

import com.ztesoft.ibss.common.util.CommonUpLoadInf;
import com.ztesoft.ibss.common.util.Const;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AttachmentServlet extends HttpServlet {

    private static final long serialVersionUID = -2359977199371613077L;
    private static Logger logger = Logger.getLogger(AttachmentServlet.class);

    //select a.*,a.rowid from CT_CONFIG a where a.ct_key='CT_ATTACHMENT_PATH'  or a.ct_key like 'FTP_CONGFIG_SERVER%';
    private List ftpConfig = null;
    //文件大小，K为单位
    private String max_file_size = "";
    private static final String MODE_UPLOAD = "upload"; // 上传
    private static final String MODE_DOWNLOAD = "download"; // 下载
    private static final String MODE_PARA_NAME = "mode"; // 操作模式参数名称

    @Override
	public void init() throws ServletException {
        super.init();
        ftpConfig = uploadTools.getFtpConfig();
        max_file_size = uploadTools.getConfigMax_file_size();
    }

    @Override
	public void destroy() {
        super.destroy();
    }

    /**
     * 在doGet里处理【下载】
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode = request.getParameter(MODE_PARA_NAME);
        //从服务器硬盘下载
        try {
            if ("view_img".equals(mode))
                showImg(request, response);
            else
                downloadFromHardDisk(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            this.dealReturn("下载文件出错：" + e, response);
        }
    }

    /**
     * 在doPost里处理【上传】
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException      返回参数：
     *                          flag -1 失败  - 成功
     *                          msg 描述信息
     */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("GBK");
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setServletContext(getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        String backUrl = multipartRequest.getParameter("backUrl");//servlet返回的路劲
        RequestDispatcher nextDispatcher = request.getRequestDispatcher(backUrl);
        long maxSize = Long.parseLong(max_file_size) * 1024;
        long fileSize = request.getContentLength();

        if (fileSize > maxSize) {
            request.setAttribute("flag", "-1");
            request.setAttribute("msg", "附件过大！");
            nextDispatcher.forward(request, response);
            return;
        }

        Map fileMap = null;
        //上传到服务器硬盘
        try {
            FileUpLoadImpl fileLoadInf = new FileUpLoadImpl();
            fileMap = fileLoadInf.upLoad(multipartRequest);
        } catch (Exception e) {
            e.printStackTrace();
            dealReturn("上传文件失败：" + e, response);
            return;
        }

        //执行文件同步到ftp服务器
        UpLoadInf upLoadInf = new CommonUpLoadInf();
        try {
            fileMap = upLoadInf.upLoad(fileMap);
        } catch (Exception e) {
            dealReturn("FTP同步文件失败：" + e, response);
            return;
        }

        String file_no = "";
        resolver.cleanupMultipart(multipartRequest);
        //上传后返回
        if (fileMap != null) {
            request.setAttribute("flag", "0");
            request.setAttribute("msg", "上传成功");
            request.setAttribute("fileNo", file_no);
            request.setAttribute("fileName", Const.getStrValue(fileMap, "FILE_NAME"));
            request.setAttribute("filePath", Const.getStrValue(fileMap, "ACCESS_PATH"));
            request.setAttribute("fileSize", Const.getStrValue(fileMap, "FILE_SIZE"));
            if (StringUtils.isEmpty(backUrl)) {
                response.getWriter().print("{'original':'" + Const.getStrValue(fileMap, "FILE_NAME") + "','url':'" + Const.getStrValue(fileMap, "ACCESS_PATH") + "','title':'" + Const.getStrValue(fileMap, "FILE_NAME") + "','state':'SUCCESS'}");
                return;
            }
            nextDispatcher.forward(request, response);
        }
    }

    UploadTools uploadTools = new UploadTools();
    private void showImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String local_img_src = request.getParameter("local_img_src");
        OutputStream out = null;
        InputStream in = null;
        response.setContentType("image/jpeg");
        try {
            out = response.getOutputStream();
            in = new FileInputStream(local_img_src);
            FileCopyUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        } finally {
            out.flush();
            out.close();
            in.close();
        }
    }

    private void downloadFromHardDisk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    	OutputStream out = null;
//    	try {
//    		out = response.getOutputStream();
//    		response.setContentType("text/plain");

//            String file_id = request.getParameter("fileId");
//            String vsave_dir = request.getParameter("vsave_dir");//保存路径
//            String file_url = request.getParameter("file_url");//原始绝对路径
//            String file_name = request.getParameter("save_file_name");//文件保存名称
//            
//            if(file_url==null ||file_url.equals("")){// 通过file_id下载
//            	Map param=new HashMap();
//            	param.put("file_id", file_id);
//            	Map fileInfo = DataTranslate._Map(
//            			ServiceManager.callJavaBeanService("COMMONBO", "getFile", param));
//            	if (fileInfo == null) {
//            		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            		return;
//            	}
//            	file_name = Const.getStrValue(fileInfo, "down_file_name");
//            	int fileSize = Const.getIntValue(fileInfo, "file_size");
//                response.setContentLength(fileSize);
//                //file_url = file_foder+"/"+ vsave_dir + "/" +   Const.getStrValue(fileInfo, "file_name");
//            }
//            if(file_name==null || file_name.equals("") ){//得到原始文件名称
//            	file_name = getGenerateFilename(file_url);
//            }
//            response.setHeader("Content-Disposition",
//            		"attachment; filename=\"" +
//            		StringUtils.toUtf8String(file_name) + "\"");
//            response.setHeader("Location",StringUtils.toUtf8String(file_name));
//            response.setContentType("application/octet-stream");
//            InputStream in = new FileInputStream(file_url);
//            FileCopyUtils.copy(in, out);
//            out.flush();
//            out.close();
//            in.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw new ServletException(ex);
//        }
//        finally{
//    		if(out!=null){
//    			out.close();
//    		}
//        }
    }

    private String getGenerateFilename(String fileUrl) {
        if (fileUrl == null || fileUrl.equals("")) {
            return "";
        }
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuffer prefix = new StringBuffer(32);
        prefix.append(sFormat.format(new Date())).append('-');

        int num = new Random(System.currentTimeMillis()).nextInt(1000);
        prefix.append(num);

        String type = fileUrl.substring(fileUrl.indexOf("."), fileUrl.length());

        return prefix + type;
    }


    private void dealReturn(String msg, HttpServletResponse response) throws IOException {
        String returnValue = "<html><head><meta http-equiv='Content-Type' content='text/html;charset=GBK'></head><body></body></html><script language='javascript'>alert('" +
                msg + "');window.close();</script>";
        response.setContentType("text/html;charset=GBK");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(returnValue);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
