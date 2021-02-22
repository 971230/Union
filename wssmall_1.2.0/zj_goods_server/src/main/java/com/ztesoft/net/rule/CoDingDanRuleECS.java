package com.ztesoft.net.rule;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import rule.impl.CoQueueBaseRule;
import rule.params.coqueue.req.CoQueueRuleReq;
import rule.params.coqueue.resp.CoQueueRuleResp;
import zte.params.order.resp.OrderTaobaoSyncResp;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.model.OrderItemAddAccept;
import com.ztesoft.net.server.DingDanCommon;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.net.util.SendInfoUtil;
import com.ztesoft.remote.inf.IActivityService;



/**
 * 订单同步规则
 * @author suns
 *
 */
public class CoDingDanRuleECS extends CoQueueBaseRule {
	
	private Logger logger = Logger.getLogger(CoDingDanRuleECS.class);
	private String out_order_id = "";
	@Resource
	IActivityService activityService;
	
	@Override
	public CoQueueRuleResp coQueue(CoQueueRuleReq coQueueRuleReq) {
		
    	logger.debug("订单同步["+coQueueRuleReq.getObject_id()+"]");
		CoQueueRuleResp coQueueRuleResp = new CoQueueRuleResp();
		//订单标识
		String order_id = coQueueRuleReq.getObject_id();		
		coQueueRuleResp.setResp_code(Consts.RESP_CODE_000);
		
		
		//序列号
		String seq = this.baseDaoSupport.getSequences("seq_goods");
		try {
			//获取json数据
			String rtnStr = null;
			synchronized (this) {
				rtnStr = standardOrderSync(order_id,coQueueRuleReq.getCoQueue().getOrg_id_belong(),seq);
				
				if (StringUtils.isEmpty(rtnStr)) {
					coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
					coQueueRuleResp.setResp_msg("订单同步失败");
					coQueueRuleResp.setError_code("1");
					coQueueRuleResp.setError_msg("订单同步失败");
					return coQueueRuleResp;
				}else if ("development_name_error".equals(rtnStr)) {
					//发展人名称为空时把队列表中的订单挂起
					coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_DDGQ);
					coQueueRuleResp.setResp_msg("发展人名称为空,订单挂起");
					coQueueRuleResp.setError_code("1");
					coQueueRuleResp.setError_msg("发展人名称为空,订单挂起");
					return coQueueRuleResp;
				}
				
				SendInfoUtil siu = new SendInfoUtil();
		        String serviceCode="CO_DINGDAN";
				String sqlStr="SELECT e.addr FROM inf_addr e where e.service_code='"+serviceCode +"'";
				String url=baseDaoSupport.queryForString(sqlStr);
				
				String arrURL[] = url.split("[，|,]");

				logger.info("完整的报文："+rtnStr);
				String  dingdan_fanghui = "";
				String ret_val = "";
				String resp_msg = "";
				
				logger.info("URL：" + arrURL[0]);

				dingdan_fanghui = SendInfoUtil.postHttpReq(rtnStr, arrURL[0]);
				if (null == dingdan_fanghui || "".equals(dingdan_fanghui)) {
					dingdan_fanghui = "连接订单系统失败";
					resp_msg = "连接订单系统失败";
				}else {
					resp_msg = JSONObject.fromObject(dingdan_fanghui).getJSONObject("standard_order_resp").get("resp_msg").toString();
					ret_val = JSONObject.fromObject(dingdan_fanghui).getJSONObject("standard_order_resp").get("resp_code").toString();
					
					//送订单系统报文异常导致修改es_co_queue状态失败,返回结果太长，返回信息无法入库
					if (resp_msg.indexOf("Expected") != -1 && resp_msg.indexOf("character") != -1) {
						resp_msg = "请求json报文异常，请查看是否多了双引号";
					}
				}
				logger.info("订单["+order_id+"]======外部订单号["+out_order_id+"]=======订单标准化同步返回信息=" + dingdan_fanghui);
				
				if ("0".equalsIgnoreCase(ret_val)) {
					coQueueRuleResp.setError_code("0");
					coQueueRuleResp.setError_msg("成功");
					coQueueRuleResp.setResp_code(Consts.RESP_CODE_000);
					coQueueRuleResp.setResp_msg("成功");
				}
				else{
					coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
					coQueueRuleResp.setResp_msg(resp_msg);
					coQueueRuleResp.setError_code("1");
					coQueueRuleResp.setError_msg(resp_msg);
					//CommonTools.addError("-1", JSONObject.fromObject(dingdan_fanghui).getJSONObject("standard_order_resp").get("resp_msg").toString());
					
				}
				//往第二个地址发送数据,测试时会往订单系统的测试及UAT发送订单数据
				if (arrURL.length > 1) {
					dingdan_fanghui = SendInfoUtil.postHttpReq(rtnStr, arrURL[1]);
					logger.info("URL：" + arrURL[0]);
					logger.info("订单["+order_id+"]======外部订单号["+out_order_id+"]=======订单标准化同步返回信息=" + dingdan_fanghui);
				}
			}
			
			
		} catch (Exception e) {
			coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
			coQueueRuleResp.setResp_msg(e.getMessage());
			coQueueRuleResp.setError_code("1");
			coQueueRuleResp.setError_msg(e.getMessage());
			logger.info("外部订单["+out_order_id+"]自动化失败.", e);
			//CommonTools.addError("-1", "发送订单系统失败:"+e.getMessage());
		}
		
		return coQueueRuleResp;
	}
	
	
	/**
	 * 获取送给订单系统的标准化json信息
	 * @param orderId
	 * @param toSysName
	 * @param serial_no
	 * @return
	 * @throws Exception
	 */
	public String standardOrderSync(String orderId,String toSysName,String serial_no) throws Exception {
		OrderTaobaoSyncResp resp = new OrderTaobaoSyncResp();
		DingDanCommon orderStandard = new DingDanCommon(this.baseDaoSupport,activityService);
		//es_order信息
		String orderSql = "select * from es_order t where exists (select 1 from es_outer_accept a " +
				"where a.order_id = t.order_id and a.pro_type is not null) ";
		//es_order_items信息
		String itemSql = SF.orderSql("OrderItmeForOut") + " order by item_id";
		//es_outer_accept信息
		String acceptSql = SF.orderSql("OrderAcceptForOut") + "  order by item_id";
		List<Order> orderlist = new ArrayList<Order>();

		orderSql += " and  t.order_id = ?";
		logger.debug("orderId == " + orderId);
		//获取es_order订单信息
		Page webpage = this.baseDaoSupport.queryForPage(orderSql, 1, 100,
				Order.class, orderId);
		orderlist = webpage.getResult();
		
		for (int i = 0; i < orderlist.size(); i++) {
			Order order = orderlist.get(i);
			String order_id = order.getOrder_id();
			if (StringUtils.isNotEmpty(order_id)) {
				
				//获取订单的es_order_items信息
				List<OrderItem> itemList = baseDaoSupport.queryForList(itemSql,
						OrderItem.class, new String[] { order_id });
				
				//获取订单的es_outer_accept信息
				List<OrderItemAddAccept> acceptList = baseDaoSupport
						.queryForList(acceptSql, OrderItemAddAccept.class,
								new String[] { order_id });
				
				out_order_id = acceptList.get(0).getOut_tid();
				order.setOrderItemList(itemList);
				order.setOrderItemAcceptList(acceptList);
			}
		}

		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setOrderSyncList(orderlist);
		return orderStandard.orderToStandardJsonValue(resp, toSysName,serial_no);
	}
	
}
