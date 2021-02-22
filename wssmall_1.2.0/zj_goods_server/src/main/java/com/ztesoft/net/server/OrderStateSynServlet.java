package com.ztesoft.net.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import zte.params.goods.req.InventoryApplyRollbackReq;
import zte.params.goods.resp.InventoryApplyRollbackResp;
import zte.params.order.req.OrderStatusEditReq;
import zte.params.order.resp.OrderStatusEditResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IOrderServiceLocal;

public class OrderStateSynServlet extends HttpServlet{
	private static final String LOCAL_CHARSET = "utf-8";
    private static Logger logger = Logger.getLogger(OrderStateSynServlet.class);
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
  logger.info("=============总部商城订单信息同步接口====== 入口=========================================");
		request.setCharacterEncoding(LOCAL_CHARSET);
        response.setContentType("text/html; charset=" + LOCAL_CHARSET);

        String returnValue = null;
        try {
            returnValue = execute(request, response);
        }
        catch (Exception ex) {
          	logger.info("服务调用异常："+ex, ex);
           returnValue = "{\"ActiveNo\": \"\",\"RespCode\": \"0001\",\"RespDesc\": \"同步失败，接口报文为空\",\"OrderId\": \"\"}"; 

        }
        PrintWriter out = response.getWriter();
        out.print(returnValue);
        out.close();
	}
	
	protected static IOrderServiceLocal goodServicesLocal;
	
	public static String execute(HttpServletRequest request,
            HttpServletResponse respose) throws Exception {
		goodServicesLocal = SpringContextHolder.getBean("orderServiceLocal");
		String inJson = null;
	    String outJson = null;
	    inJson = getInJson(request);  
	    
	    try {
	    	JSONObject jsonObject =  JSONObject.fromObject(inJson.toString());
	    	String orderId = jsonObject.get("OutOrderId").toString(); //外部定单ID
	    	String orderState = jsonObject.get("OrderState").toString(); //外部定单状态
	    	String activeNo = jsonObject.get("ActiveNo").toString(); //访问流水
	    	outJson = "{\"ActiveNo\": \""+activeNo+"\",\"RespCode\": \"0000\",\"RespMsg\": \"同步成功\"}";
	    	if("05".equalsIgnoreCase(orderState)){
	    		try {
	    			Map m = goodServicesLocal.queryMap("select * from gdlt_v_INVENTORY_APPLY a where a.out_tid = ? and rownum < 2", orderId);
		    		
		    		InventoryApplyRollbackReq req = new InventoryApplyRollbackReq();
		    		req.setGoods_id(m.get("goods_id").toString());
		    		req.setHouse_id(m.get("house_id").toString());
		    		req.setVirtual_house_id(m.get("virtual_house_id").toString());
		    		req.setOrder_id(m.get("order_id").toString());
		    		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		    		InventoryApplyRollbackResp response=client.execute(req, InventoryApplyRollbackResp.class);
		    		logger.info("异常单返回---"+response.getError_msg());
		    		if(!"0".equalsIgnoreCase(response.getError_code())){
		    			outJson = "{\"ActiveNo\": \""+activeNo+"\",\"RespCode\": \"0001\",\"RespMsg\": \""+response.getError_msg()+"\"}";
		    		}else{
		    			OrderStatusEditReq osereq = new OrderStatusEditReq();
		    			osereq.setOrder_id(m.get("order_id").toString());
		    			osereq.setOrder_status("-10");
		    			OrderStatusEditResp osersp = client.execute(osereq,OrderStatusEditResp.class);
		    			if(!"0".equalsIgnoreCase(osersp.getError_code())){
			    			outJson = "{\"ActiveNo\": \""+activeNo+"\",\"RespCode\": \"0001\",\"RespMsg\": \""+osersp.getError_msg()+"\"}";
			    		}
		    		}
				} catch (Exception e) {
					e.printStackTrace();
					outJson = "{\"ActiveNo\": \""+activeNo+"\",\"RespCode\": \"0001\",\"RespMsg\": \""+e.getMessage()+"\"}";
					// TODO: handle exception
				}
	    		
	    	}
		} catch (Exception e) {
			// TODO: handle exception
		}
	    return outJson;
	}
	
	// 获得请求的报文,并作简单的校验  
	public static String getInJson(HttpServletRequest request) throws IOException {  

	    byte buffer[] = new byte[64 * 1024];  
	    InputStream in = request.getInputStream();// 获取输入流对象  

	    int len = in.read(buffer);  
	    // 必须对数组长度进行判断，否则在new byte[len]会报NegativeArraySizeException异常  
	    if (len < 0) {  
	        throw new IOException("请求报文为空");  
	    }  

	    String encode = request.getCharacterEncoding();// 获取请求头编码  
	    // 必须对编码进行校验,否则在new String(data, encode);会报空指针异常  
	    if (null == encode || encode.trim().length() < 0) {  
	        throw new IOException("请求报文未指明请求编码");  
	    }  

	    byte data[] = new byte[len];  

	    // 把buffer数组的值复制到data数组  
	    System.arraycopy(buffer, 0, data, 0, len);  

	    // 通过使用指定的 charset 解码指定的 byte 数组，构造一个新的 String  
	    String inJson = new String(data, encode);  

	    return inJson;  
	}  

}
