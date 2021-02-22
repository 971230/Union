package zte.net.iservice.impl;


import zte.net.ecsord.params.ecaop.resp.ChannelConvertQrySubResp;
import zte.net.ecsord.params.order.resp.StartWorkflowRsp;
import zte.net.ecsord.params.order.resp.WorkflowMatchRsp;
import zte.net.iservice.IOrderCtnService;
import zte.params.orderctn.req.ChannelConvertQryCtnReq;
import zte.params.orderctn.req.CtnStartWorkflowReq;
import zte.params.orderctn.req.CtnWorkflowMatchReq;
import zte.params.orderctn.req.HSOrderCtnB2BReq;
import zte.params.orderctn.req.HSOrderCtnReq;
import zte.params.orderctn.req.OrderCtnReq;
import zte.params.orderctn.req.TbTianjiTestReq;
import zte.params.orderctn.resp.HSOrderCtnB2BResp;
import zte.params.orderctn.resp.HSOrderCtnResp;
import zte.params.orderctn.resp.OrderCtnResp;
import zte.params.orderctn.resp.TbTianjiTestResp;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;

/**
 * 
 * @Package zte.net.iservice.impl
 * @Description: 订单归集系统开放服务类 
 * @author zhouqiangang 
 * @date 2015年11月18日 下午5:20:09
 */
@ServiceMethodBean(version="1.0")
public class ZteOrderCtnOpenService implements IOrderCtnService{
	
	private IOrderCtnService orderCtnService;
	
	 private void init(){
		 orderCtnService= SpringContextHolder.getBean("orderCtnService");
	 }

	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteOrderCtnOpenService.orderCtn",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderCtnResp orderCtn(OrderCtnReq req) throws Exception {
		init();
		return orderCtnService.orderCtn(req);
	}

//	@Override
//	@ServiceMethod(method="com.zte.unicomService.zb.notifyOrderInfo",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
//	public String getOutServiceCode(Map<String, Object> map) {
//		init();
//		return orderCtnService.getOutServiceCode(map);
//	}

//	@Override
//	@ServiceMethod(method="com.zte.unicomService.zb.notifyOrderInfo",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
//	public Map<String, String> getOpenServiceCfgByOutServiceCode(OrderCtnReq req) {
//		init();
//		return orderCtnService.getOpenServiceCfgByOutServiceCode(req);
//	}

//	@Override
//	@ServiceMethod(method="zte.net.iservice.impl.ZteOrderCtnOpenService.orderStd",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
//	public OrderCollectionCtnResp orderStd(OrderQueueFailBusiRequest orderQueueReq, OrderCollectionCtnReq req)
//			throws Exception {
//		init();
//		return orderCtnService.orderStd(orderQueueReq,req);
//	}

	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteOrderCtnOpenService.hsOrderCtn",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public HSOrderCtnResp hsOrderCtn(HSOrderCtnReq req) throws Exception {
		init();
		return orderCtnService.hsOrderCtn(req);
	}

	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteOrderCtnOpenService.hsOrderCtnB2B",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public HSOrderCtnB2BResp hsOrderB2BCtn(HSOrderCtnB2BReq req) throws Exception {
		init();
		return orderCtnService.hsOrderB2BCtn(req);
	}
	
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteOrderCtnOpenService.testTbTianji",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public TbTianjiTestResp testTbTianji(TbTianjiTestReq req){
		init();
		return orderCtnService.testTbTianji(req);
	}

	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteOrderCtnOpenService.workflowMatch",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public WorkflowMatchRsp doWorkflowMatch(CtnWorkflowMatchReq req)
			throws Exception {
		init();
		return orderCtnService.doWorkflowMatch(req);
	}

	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteOrderCtnOpenService.startWorkflow",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public StartWorkflowRsp startWorkflow(CtnStartWorkflowReq req)
			throws Exception {
		init();
		return orderCtnService.startWorkflow(req);
	}
	@Override
    @ServiceMethod(method="ecaop.trades.query.channelCtn.convert.qry",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
    public ChannelConvertQrySubResp channelConvertCtn(ChannelConvertQryCtnReq req)  throws Exception {
        init();
        return orderCtnService.channelConvertCtn(req);
    }
}
