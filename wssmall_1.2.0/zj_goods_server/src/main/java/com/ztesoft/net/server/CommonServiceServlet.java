package com.ztesoft.net.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.ztesoft.net.outter.inf.model.BaseMessage;

/**
 * http post 通用servlet入口
 */
public class CommonServiceServlet extends HttpServlet {

	private static final long serialVersionUID = -7855816929879366224L;

	private final static Logger logger = Logger.getLogger(CommonServiceServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqId=null;
		String reqType=null;
		String resp ="";
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String _dealTime = df.format(new Date());
		try {
			// 请求参数
			String reqString = getReqContent(request);
			// 请求ID(请求方编码)
			reqId = request.getParameter("reqId");
			// 请求类型
			reqType = request.getParameter("reqType");
			// 打印请求日志
			final String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			//logger.info("reqId:"+reqId);
			//logger.info("reqType:"+reqType);
			//logger.info("reqString:"+reqString);
			logger.info("[" + dateStr + "]请求参数信息：" + request.getRequestURL() + ";reqId=" + reqId +  ";reqType=" + reqType + ";reqStr=" + reqString);
			// 业务处理(未做接口鉴权)
			final String resultJsonStr = CommonServletFactory.getServlet(reqType).service(reqString);
			// 打印返回日志
		//	logger.info("resultJsonStr:"+resultJsonStr);
			logger.info("请求参数返回信息：reqId=" + reqId +  ";reqType=" + reqType + ";respStr=" + resultJsonStr);
			// 返回
			response(response, resultJsonStr);
		} catch (Exception e) {
			e.printStackTrace();
			
			if("wyg_syn_order_status".equals(reqType)){
				resp = "{\"resp_code\":\"1\",\"serial_no\":\"wyg_syn_order_status["+_dealTime+"]\",\"resp_msg\":\""+e.getMessage()+"\"}";
			}else{
				BaseMessage base =new BaseMessage();
				ObjectMapper	mapper = new ObjectMapper();
				base.setErrorCode("406");
				base.setErrorMessage(e.getMessage());
				base.setReqId(reqId);
				base.setReqType(reqType);
				resp = mapper.writeValueAsString(base);
			}
			response(response, resp);
		}
	}

	/**
	 * 响应报文请求
	 * 
	 * @param response
	 *            HttpServletResponse对象
	 * @param jsonStr
	 *            json字符串形式的错误描述信息
	 */
	private void response(HttpServletResponse response, String jsonStr) {
		try {
			response.setContentType("text/json;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			OutputStream out = response.getOutputStream();
			out.write(jsonStr.getBytes("UTF-8"));
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.info("响应报文出错；错误信息：" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 获取请求的报文内存
	 * 
	 * @param request
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private String getReqContent(HttpServletRequest request) throws IOException, UnsupportedEncodingException {
		InputStream input;
		BufferedReader reader;
		// 获得请求报文内容
		input = request.getInputStream();
		reader = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
		StringBuilder buffer = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String buStr = buffer.toString();
		return buStr;
	}

}
