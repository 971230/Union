package com.ztesoft.net.mall.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.ecsord.params.ecaop.req.PreCommitAPPReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.PreCommitAPPResp;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 开户预提交接口        
 * 
 * @author liu.quan68@ztesoft.com
 *
 * @date 2017年2月25日
 */
public class PreCommitServlet extends HttpServlet {

	private static final long serialVersionUID = 3411911039617039211L;
	
	private static Logger logger = Logger.getLogger(PreCommitServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		execute(request, response);
	}
	
	/**
	 *  开户预提交接口                                                                                                  
	 *
	 *@param request
	 *@param response
	 *@throws ServletException
	 *@throws IOException
	 *
	 * @author huang.zs 
	 *
	 * @date 2017年2月27日
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String respJsonStr = "";//返回json字符串
		String requJsonStr = "";//请求json字符串
		PreCommitAPPResp resp = new PreCommitAPPResp();
		PreCommitAPPReq requ = new PreCommitAPPReq();
		PrintWriter out = null;
		try {
			//获取请求的json数据
			requJsonStr = getRequestJson(request, response);
			logger.info("[PreCommitServlet] 请求报文-request:" + requJsonStr);
			//解析请求参数
			JSONObject requJson = JSONObject.fromObject(requJsonStr);
			//请求入参
			JSONObject writeCardInfoJson = requJson.getJSONObject("bss_order_pre_submit_req");
			requ = (PreCommitAPPReq) JSONObject.toBean(writeCardInfoJson, PreCommitAPPReq.class);
			//根据ICCID和收货人号码查询订单信息
			logger.info(requ.toMap());
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			resp = client.execute(requ, PreCommitAPPResp.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("resp_code", resp.getResp_code());
			map.put("resp_msg", resp.getResp_msg());
			map.put("bus_resp", resp.getBus_resp());
			map.put("bss_pre_order_id", resp.getBss_pre_order_id());
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("bss_order_pre_submit_rsq", map);
			//将返回数据转成json格式的字符串
			respJsonStr = JSONObject.fromObject(respMap).toString();
			//返回接口调用结果
			logger.info("[PreCommitServlet] 响应报文-response:"+respJsonStr);
			out = response.getWriter();
			out.print(respJsonStr);
		} catch (Exception e) {
			resp = new PreCommitAPPResp(); 
//			resp.setRes_code("1");
//			resp.setRes_message("异常错误：" + e.getMessage() == null? e.toString():e.getMessage());
			e.printStackTrace();
			logger.info(e, e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 获取请求的json数据
	 *
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月27日
	 */
	private String getRequestJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletInputStream in = null;
    	String requString = "";
		try {
		    //获取流
			in = request.getInputStream();
	        //转为string
	        requString = IOUtils.toString(in, "utf-8");
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				
			}
		}
		return requString;
	}

	/**
	 * 把request请求参数转为map
	 *
	 *@param request
	 *@return
	 *@throws Exception
	 *
	 * @author huang.zhisheng@ztesoft.com
	 *
	 * @date 2017年2月27日
	 */
	public static Map toMap(HttpServletRequest request){
		 Map properties = request.getParameterMap();
	        Map returnMap = new HashMap();
	        Iterator entries = properties.entrySet().iterator();
	        String name = "";
	        String value = "";
	        for(; entries.hasNext(); returnMap.put(name, value))
	        {
	            java.util.Map.Entry entry = (java.util.Map.Entry)entries.next();
	            name = (String)entry.getKey();
	            Object valueObj = entry.getValue();
	            if(valueObj == null)
	                value = "";
	            else
	            if(valueObj instanceof String[])
	            {
	                String values[] = (String[])valueObj;
	                for(int i = 0; i < values.length; i++)
	                    value = (new StringBuilder(String.valueOf(values[i]))).append(",").toString();

	                value = value.substring(0, value.length() - 1);
//	                try {
//						value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
//					} catch (UnsupportedEncodingException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
	            } else 
	            {
	                value = valueObj.toString();
//	                try {
//						value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
//					} catch (UnsupportedEncodingException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
	            }
	        }
	        return returnMap;
	}
}
