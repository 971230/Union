package com.ztesoft.net.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.coqueue.resp.CoQueueAddResp;
import utils.CoreThreadLocalHolder;
import zte.params.order.req.OrderStandardPushReq;
import zte.params.order.resp.OrderStandardPushResp;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.outter.inf.iservice.IOuterService;

public class OrderStandingUtil {
	private static Logger logger = Logger.getLogger(OrderStandingUtil.class);
	@Transactional(propagation = Propagation.REQUIRED)
	public static OrderStandardPushResp pushOrderStandard(OrderStandardPushReq pushReq)
			throws Exception {
        IOuterService stdOuterService = SpringContextHolder.getBean("stdOuterService");
		// add by wui
		CoreThreadLocalHolder.getInstance().getZteCommonData()
				.setZteRequest(pushReq);
		if (StringUtils.isEmpty(pushReq.userSessionId)) {
			pushReq.setUserSessionId(pushReq.getUserSessionId());
		}

		long start = System.currentTimeMillis();
		OrderStandardPushResp resp = new OrderStandardPushResp();
		List<Outer> outerList = pushReq.getOuterList();

		Map<String, List<Outer>> pushMap = new HashMap<String, List<Outer>>();

		for (Outer oo : outerList) {
			List<Outer> oList = pushMap.get(Consts.SERVICE_CODE_CO_GUIJI_NEW);
			if (oList == null) {
				oList = new ArrayList<Outer>();
				pushMap.put(Consts.SERVICE_CODE_CO_GUIJI_NEW, oList);
			}
			oList.add(oo);
		}

		Iterator it = pushMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String service_name = "订单归集";
			if (Consts.SERVICE_CODE_CO_GUIJI_NEW.equals(key)) {
				service_name = "新订单归集";
			}
			List<Outer> oList = pushMap.get(key);
			List respList = null;
			if (oList != null && oList.size() > 0) {
				List<CoQueueAddResp> coQueueAddResps = stdOuterService.perform(
						oList, key, service_name);
				if (ListUtil.isEmpty(coQueueAddResps)) {
					resp.setError_code(Consts.ERROR_FAIL);
					resp.setError_msg("订单重复执行");
					throw new RuntimeException("订单重复执行");
				}
				OrderOuter orderOuter = null;
				if (!ListUtil.isEmpty(coQueueAddResps)
						&& coQueueAddResps.size() > 0
						&& coQueueAddResps.get(0).getCo() != null
						&& coQueueAddResps.get(0).getCo().getOrderOuterList() != null
						&& coQueueAddResps.get(0).getCo().getOrderOuterList()
								.size() > 0)
					orderOuter = coQueueAddResps.get(0).getCo()
							.getOrderOuterList().get(0);
				String send_type = oList.get(0).getSending_type(); // 现场交付订单实时处理失败则插入队列，作为日志跟踪
				// 异常捕获，有问题不影响订单同步
				long end = System.currentTimeMillis();
				logger.info("订单同步结束，总耗时========================="
						+ (end - start));
				// logger.info("订单标准化开始==================================>,co_qid"+
				// coQueueAddResps.get(0).getCoQueue().getCo_id());
				coQueueAddResps.get(0).setUserSessionId(
						pushReq.getUserSessionId());
				respList = stdOuterService.orderStanding(coQueueAddResps, key,
						service_name);
				resp.setOrderAddRespList(respList);
				long end1 = System.currentTimeMillis();
				logger.info("订单标准化结束，总耗时===============================>"
								+ (end1 - end) + "订单操作总耗时：" + (end1 - start));

			}
		}
		return resp;
	}
	
}
