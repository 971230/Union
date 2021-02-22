package com.ztesoft.net.server;

import org.apache.log4j.Logger;

import com.ztesoft.net.server.servlet.ICommonServlet;
import com.ztesoft.net.server.servlet.impl.AdmissibleBroadbandGoodsQryServlet;
import com.ztesoft.net.server.servlet.impl.EMSLogisticsInfoServlet;
import com.ztesoft.net.server.servlet.impl.JKZFInfServlet;
import com.ztesoft.net.server.servlet.impl.MallOperatorIDSynServlet;
import com.ztesoft.net.server.servlet.impl.NDOrderCompleteNoticeService;
import com.ztesoft.net.server.servlet.impl.NDOrderStatusNoticeServlet;
import com.ztesoft.net.server.servlet.impl.NDSdsOperToOrderServlet;
import com.ztesoft.net.server.servlet.impl.QuerySFRouteServlet;
import com.ztesoft.net.server.servlet.impl.RceiveICCIDServlet;
import com.ztesoft.net.server.servlet.impl.RefreshGoodsCacheServlet;
import com.ztesoft.net.server.servlet.impl.SFArtificialSelectServlet;
import com.ztesoft.net.server.servlet.impl.SFLogisticsInfoServlet;
import com.ztesoft.net.server.servlet.impl.SimulationResultReceiveServlet;
import com.ztesoft.net.server.servlet.impl.WYGAcceptanceQueryServlet;
import com.ztesoft.net.server.servlet.impl.WYGOrderStatusSynServlet;

/**
 * servlet工厂类
 */
public class CommonServletFactory {

	private final static Logger log = Logger.getLogger(CommonServletFactory.class);
	
	public static ICommonServlet getServlet(String reqType){
		if(null == reqType){
			log.info("参数错误，分发处理请求错误中断.");
			return null;
		}
		if ("wl_result_to_order".equals(reqType)) {
			//顺丰-推送物流配送信息至订单系统
			return new SFLogisticsInfoServlet();
		} else if ("sf_artificial_select".equals(reqType)) {
			//顺风-筛单信息处理
			return new SFArtificialSelectServlet();
		}else if ("wl_syn_status".equals(reqType)) {
			//南都-南都订单状态通知 
			return new NDOrderStatusNoticeServlet(reqType);
		}else if("wl_oper_to_order".equals(reqType)){
            //南都-闪电送派工号接口 
            return new NDSdsOperToOrderServlet();
        }else if("wl_order_deal_sucess".equals(reqType)){
            //南都-订单完成通知接口 
            return new NDOrderCompleteNoticeService(reqType);
        }else if("wyg_syn_order_status".equals(reqType)){
        	//沃云购-订单状态同步 
        	return new WYGOrderStatusSynServlet(reqType);
        }else if ("jkzf_inf".equals(reqType)){
        	//接口转发封装
        	return new JKZFInfServlet(reqType);
        }else if("wyg_syn_operator_id".equals(reqType)){
        	//商城工号信息同步 
        	return new MallOperatorIDSynServlet(reqType);
        }else if("simulation_result_notify".equals(reqType)){
        	//森锐通知仿真回单结果 
        	return new SimulationResultReceiveServlet(reqType);
        }else if("sf_route_query".equals(reqType)){
        	return new QuerySFRouteServlet(reqType);
        }else if("sp_refresh_goods_cache".equals(reqType)){
        	return new RefreshGoodsCacheServlet(reqType);
        }else if("wyg_acceptance_query".equals(reqType)){
        	return new WYGAcceptanceQueryServlet(reqType);
        }else if("ems_route_push".equals(reqType)){
        	return new EMSLogisticsInfoServlet();
        }else if("syn_available_iccid".equals(reqType)){
        	//接收森锐单笔推送ICCID
        	return new RceiveICCIDServlet(reqType);
        }else if("broadband_goods_query".equals(reqType)){
        	return new AdmissibleBroadbandGoodsQryServlet();
        }
		return null;
	}
}
