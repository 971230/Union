package com.ztesoft.net.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.model.BusiDealResult;

import params.order.req.Iphone6sReq;
import params.order.req.OrderExceptionLogsReq;
import params.order.req.OrderHandleLogsReq;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.zb.resp.NumberStateChangeZBResponse;

public interface IOrderFlowManager {
	
	/** 查询省列表*/
	public List getProvList();
	/**
	 * 根据省级Id获取市
	 * @param prov_id
	 * @return
	 */
   public List getCityList(Long prov_id);
   
   /**
    * 根据城市Id获取区县列表
    * @param city_id 
    * @return
    */
   public List getDistrictList(Long city_id);
   
   public Map getBuyInfoByMemberId(String member_id);
   
   public void updateBuyInfoByMemberId(String[] str);
   //记录处理信息
   public void insertOrderHandLog(OrderHandleLogsReq req);
   //记录异常信息
   public void insertOrderExceptionLog(OrderExceptionLogsReq req);
   //更新异常信息
   public void updateOrderExceptionLog(String order_id);
   
   public String getSpecErrorMsg(String order_id);
   
   public List getDevelopmentName(String code,String order_from);
   
   public String getCompanyNameByCode(String code);
   //执行退单规则并记录日志
   public Boolean executeOrderReRule(String orderId,OrderHandleLogsReq req);
   
   public BusiDealResult offLineOpenAccount(String order_id);
   
   /**
    * 重新启动工作流
    * @作者 MoChunrun
    * @创建日期 2015-2-6 
    * @param order_id
    * @return
    */
   public BusiCompResponse startNewOrderFlow(String order_id)throws Exception;
   
   /**
    * 转回预拣货环节
    * @作者 MoChunrun
    * @创建日期 2015-2-6 
    * @param order_id
    * @return
    */
   public BusiCompResponse reBackToPrepick(String order_id)throws Exception ;
   
   public void ruleExeLogArchive(String order_id);
   
   public NumberStateChangeZBResponse changePhone(String order_id,String occupiedFlag,String changeTag,String old_phone_num,String old_pro_key,String proKey,String resourceCode)throws ApiBusiException;
   /**
    * 转回预处理环节
    * @作者 zengxianlian
    * @创建日期 2015-8-24 
    * @param order_id
    * @return
    */
   public BusiCompResponse reBackToVisit(String order_id)throws Exception ;
   
   public List<OrderExceptionLogsReq> queryExceptionLogsList(String order_id);
   
   /**
    * 根据查询同来源+同证件+未绑定过的预售订单
    * @param orderid
    * @param orderFrom 来源
    * @param certName 证件姓名
    * @param certNum 证件号码
    * @return
    */
   public List<Iphone6sReq> getRelationOrders(String orderid,String orderFrom,String certName,String certNum);
   //订单外呼申请
   public void insertOrderOutCallLog(Map req);
   /**
    * 记录处理信息
    * @param order_id  内部订单号
    * @param comments  处理备注
    * @param handler_type  处理类型
    */
   public void addOrderHandlerLog(String order_id,String comments,String handler_type);
   
   public BusiCompResponse reBackToVisitNEW(String order_id) throws Exception;
   /**
    * 
    * <p>Title: getServiceCodeByObjectId</p>
    * <p>Description: 从es_order_queue_his表中取出service_code</p>
    * @author sgf
    * @time 2018年7月17日 下午7:22:57
    * @version 1.0
    * @param out_tid 外部订单编号
    * @return
    */
   public List<Map<String, String>> getServiceCodeByObjectId(String out_tid);
   /**
    * 
    * <p>Title: getTearminalObjectQuery</p>
    * <p>Description: sgf</p>
    * @author 查询终端型号信息
    * @time 2019年3月8日 下午2:49:27
    * @version 1.0
    * @return
    */
   public List<Map> getTearminalObjectQuery(String order_id);
   
   /**
      * 获取营业县份 先获取宽带表  如果宽带表没有就获取订单信息扩展表
    * @author GL
    * @return
    */
   public String getCountyName(String districtCode,String countyId);
   /**
    * 回退到库管环节
    * <p>Title: reBackToVisitNEW</p>
    * <p>Description: flow_tran_id  想要回到的环节</p>
    * @author Name
    * @time 2019年4月28日 下午1:16:48
    * @version 1.0
    * @param order_id  flow_tran_id
    * @return
    * @throws Exception
    */
   public BusiCompResponse reBackToInventoryKeeper(String order_id,String flow_tran_id) throws Exception;

   /**
       * 获取环节编码
    * @author GL
    * @return
    */
   public String flowGetNodeCode(String order_id);
   
}
