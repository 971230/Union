package com.ztesoft.net.service;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.ecaop.req.CustInfoModReq;
import zte.net.ecsord.params.ecaop.resp.CustInfoModRsp;

import com.ztesoft.net.ecsord.params.ecaop.req.GeneralOrderQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoListQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderResultQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.PayWorkOrderUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.QueryGoodsListReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WorkOrderListQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WorkOrderMixOrdUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WorkOrderUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.GeneralOrderQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderInfoListQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderResultQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.PayWorkOrderUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WorkOrderListQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WorkOrderMixOrdUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WorkOrderUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.orderInfoBackfill.OrderInfoBackfillRsp;
import com.ztesoft.net.ecsord.params.ecaop.vo.GoodsInfoVO;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.inf.InfCompareReq;

public interface IOrderInfManager {

	public String getinfContent(String orderId, String ruleId, String opCode);

	public String compareInfContent(InfCompareReq cmpVo);

	public Page getInfList(String rec_id, String order_id, String out_id, String op_code, Integer pageIndex,
			Integer pageSize);

	public Page getInfDetail(String rec_id, String qry_cond, Integer pageIndex, Integer pageSize);

	public List getBlobList(String table_name);

	public List getReqBlobList();

	public List getRespBlobList();

	public List getEndpointList();

	public List getOperationList();

	/**
	 * 根据iccid和收货人电话查询订单id
	 *
	 * @param iccid
	 * @param shipMobile
	 * @return
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月27日
	 */
	public String queryOrderInfo(String iccid, String shipMobile);

	/**
	 * 根据order_id 查询订单
	 * 
	 * @return
	 * @author 宋琪
	 * @date 2017年6月14日 20:15:59
	 */
	public Map getOrderInfo(String order_id);
	
	
	/**
	 * 根据order_id 查询靓号预存
	 * 
	 * @return
	 * @author wjq
	 * @date 2019年6月5日 20:15:59
	 */
	public String getLiangPrice(String order_id);

	/**
	 * 根据 查询订单列表
	 *
	 * @param
	 * @param
	 * @return
	 *
	 * @author 宋琪
	 * @throws Exception 
	 *
	 * @date 2017年6月2日
	 */
	public OrderInfoListQueryResp queryOrderInfoList(OrderInfoListQueryReq requ) throws Exception;

	/**
	 * 工单列表查询
	 * 
	 * @author 宋琪
	 * @date 2017年12月1日
	 */
	public WorkOrderListQueryResp queryWorkOrderListByRequ(WorkOrderListQueryReq requ);

	/**
	 * 工单状态同步接口
	 * 
	 * @author 宋琪
	 * @date 2017年12月1日
	 */
	public WorkOrderUpdateResp updateWorkOrderUpdateByRequ(WorkOrderUpdateReq requ) throws Exception;
	/**
	 * 收费单同步接口
	 * 
	 * @author 宋琪
	 * @date 2017年12月1日
	 */
	public PayWorkOrderUpdateResp updatePayWorkOrderUpdateByRequ(PayWorkOrderUpdateReq requ);

	/**
	 * 2.3.8. 订单收单结果查询接口
	 * 
	 * @author 宋琪
	 * @date 2017年6月29日
	 */
	public OrderResultQueryResp queryOrderResult(OrderResultQueryReq requ);

	// 根据序列号查询商品报文
	public String queryGoodsInfo(String serial_no, String source_system);

	public void addRspTime(String seq_no, String source_system);
	
	public List<GoodsInfoVO> queryGoodsList(QueryGoodsListReq req);

	public Map qryGoodsDtl(String order_id);

	public String checkRefund(String order_id, String trace_id);

	public String getRSASign(Map map);

	public String decodeInfo(String str);
	/**
	 * 获取规则执行记录
	 * @param order_id
	 * @param rule_id	
	 * @param exeResult 0成功 1/-1失败
	 * @return
	 */
	public List getRuleExeLog(String order_id,String rule_id,String exeResult); 
	
	public CustInfoModRsp custInfoMod(CustInfoModReq req);
	
	/**
	 * 更新固移融合单状态
	 * @param requ 请求数据封装类
	 * @return
	 */
	public WorkOrderMixOrdUpdateResp doWorkOrderMixOrdUpdate(WorkOrderMixOrdUpdateReq requ) throws Exception;

    public Map getOrderStateAndLockName(String orderId);

	public GeneralOrderQueryResp generalOrderQuery(GeneralOrderQueryReq req);

	public List<Map<String, String>> orderinfoBack(String order_id, String string);

}
