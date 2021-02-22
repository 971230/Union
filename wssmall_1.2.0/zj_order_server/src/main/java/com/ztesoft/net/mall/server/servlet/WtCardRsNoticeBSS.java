package com.ztesoft.net.mall.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderInfoUpdateResp;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;

import consts.ConstsCore;
import net.sf.json.JSONObject;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.RuleTreeExeResp;

/**
 * 写卡结果通知接口
 * 
 * @author xiag.yangbo@ztesoft.com
 *
 * @date 2017年4月5日
 */
public class WtCardRsNoticeBSS extends HttpServlet {

	private static final long serialVersionUID = 3411911039617039211L;
	
	private static Logger logger = Logger.getLogger(WtCardRsNoticeBSS.class);

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
	 * 等待支付结果接口
	 * @author huang.zs
	 * @date 2017年4月27日
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String respJsonStr = "";//返回json字符串
		PrintWriter out = null;
		OrderInfoUpdateResp resp = new OrderInfoUpdateResp();
		try {
			String orderId = request.getParameter("orderId");
			if("".equals(orderId) || orderId == null){
				throw new Exception("订单参数为空.");
			}
			//测试
			OrderInfoUpdateReq requ = new OrderInfoUpdateReq();
			requ.setOrder_id(orderId);
			requ.setPay_result(ZjEcsOrderConsts.MA_PAY_SUCCESS);
			//根据ICCID和收货人号码查询订单信息
			//调用规则
			RuleTreeExeReq req = new RuleTreeExeReq();
			TacheFact fact = new TacheFact();
			fact.setRequest(requ);
			fact.setOrder_id(requ.getOrder_id());
			req.setFact(fact);
			req.setRule_id(EcsOrderConsts.BUSI_CARD_BSS_WTRSTOBSS_RULE_ID);
			req.setCheckAllRelyOnRule(false);
			req.setCheckCurrRelyOnRule(false);
			ZteClient client1 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			RuleTreeExeResp ruleResp = client1.execute(req, RuleTreeExeResp.class);
			resp.setResp(ruleResp.getResp());
			//如果开户预提交规则调用失败
			if (!ConstsCore.ERROR_SUCC.equals(ruleResp.getError_code())) {
				logger.info("写卡结果通知失败：" + ruleResp.getError_msg());
				resp.setError_code("1");
				resp.setError_msg("异常错误2：" + (ruleResp.getError_msg()));
			}
		} catch (Exception e) {
			resp = new OrderInfoUpdateResp(); 
			resp.setResp_code("1");
			resp.setResp_msg("异常错误：" + (e.getMessage() == null? e.toString():e.getMessage()));
			e.printStackTrace();
			logger.info(e, e);
		}
		//返回接口调用结果
		logger.info("[BusiHandleCheckServlet] 响应报文-response:"+resp);
		respJsonStr = JSONObject.fromObject(resp).toString();
		out = response.getWriter();
		out.print(respJsonStr);
		out.close();
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

}
