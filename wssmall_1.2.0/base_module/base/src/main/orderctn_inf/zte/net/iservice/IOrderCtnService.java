package zte.net.iservice;

import zte.net.ecsord.params.ecaop.resp.ChannelConvertQrySubResp;
import zte.net.ecsord.params.order.resp.StartWorkflowRsp;
import zte.net.ecsord.params.order.resp.WorkflowMatchRsp;
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

/**
 * 
 * @Package zte.net.iservice
 * @Description: 订单归集系统服务
 * @author zhouqiangang
 * @date 2015年11月18日 下午5:20:02
 */
public interface IOrderCtnService {

	/**
	 * 
	 * @Description: 订单归集
	 * @param req
	 * @return
	 * @author zhouqiangang
	 * @date 2015年11月18日 下午5:38:34
	 */
	public OrderCtnResp orderCtn(OrderCtnReq req) throws Exception;

	/**
	 * 
	 * @Description: 根据数据库配置的路径从报文Map对象解析对外的接口编码
	 * @param map
	 * @return   
	 * @author zhouqiangang
	 * @date 2015年11月30日 下午3:44:13
	 */
//	public String getOutServiceCode(Map<String, Object> map);
	
	/**
	 * 
	 * @Description: 根据外部编码获取OpenService配置
	 * @param req
	 * @return   
	 * @author zhouqiangang
	 * @date 2015年11月30日 下午4:03:05
	 */
//	public Map<String, String> getOpenServiceCfgByOutServiceCode(OrderCtnReq req) ;
	
	/**
	 * 订单处理
	 * @param req
	 * @return
	 */
//	public OrderCollectionCtnResp orderStd(OrderQueueFailBusiRequest orderQueueReq, OrderCollectionCtnReq req) throws Exception;

	/*
	 * 华盛收单
	 */
	public HSOrderCtnResp hsOrderCtn(HSOrderCtnReq req) throws Exception;
	/*
	 * 华盛收单(B2B)
	 */
	public HSOrderCtnB2BResp hsOrderB2BCtn(HSOrderCtnB2BReq req) throws Exception;
	
	public TbTianjiTestResp testTbTianji(TbTianjiTestReq req);
	
	public WorkflowMatchRsp doWorkflowMatch(CtnWorkflowMatchReq req) throws Exception;
	
	public StartWorkflowRsp startWorkflow(CtnStartWorkflowReq req) throws Exception;
    public ChannelConvertQrySubResp channelConvertCtn(ChannelConvertQryCtnReq req)  throws Exception ;

}
