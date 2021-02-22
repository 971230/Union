package com.ztesoft.net.service;

import java.util.Map;

import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;

import com.zte.cbss.autoprocess.HttpLoginClient;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.model.BusiDealResult;

public interface IOrdBSSTacheManager {

	/**
	 * BSS办理状态设置（未办理）
	 * 
	 * @param order_id
	 * @return
	 */
	public BusiDealResult setStatusNoFinish(String order_id);

	/**
	 * BSS社会机TAC码、商品折扣包录入
	 * 
	 * @param order_id
	 * @return
	 */
	public BusiDealResult actionRecv(String order_id);

	/**
	 * BSS办理状态设置（已办理）
	 * 
	 * @param order_id
	 * @return
	 */
	public BusiDealResult setStatusFinish(String order_id);

	/**
	 * BSS办理状态设置（已办理已竣工）
	 * 
	 * @param order_id
	 * @return
	 */
	public BusiDealResult setStatusFinishComplete(String order_id);

	/**
	 * BSS办理状态设置（已办理未竣工）
	 * 
	 * @param order_id
	 * @return
	 */
	public BusiDealResult setStatusFinishNotComplete(String order_id);

	/**
	 * 代理商资金扣减
	 * 
	 * @param order_id
	 * @return
	 */
	public BusiDealResult agentDeduct(String order_id);

	/**
	 * 代理商资金返销
	 * 
	 * @param order_id
	 * @return
	 */
	public BusiDealResult agentRefund(String order_id);

	/**
	 * 通过模拟办理cbss业务
	 * 
	 * @param dealRequest
	 * @return
	 */
	public BusiDealResult orderCBSSDeal(AttrGiftInfoBusiRequest attrGiftInfo, HttpLoginClient client);

	/**
	 * 获取CBSS系统登陆client
	 * 
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	public HttpLoginClient getHttpLoginClient(String order_id) throws Exception;

	/**
	 * 更改礼品Bss状态，对应ES_ATTR_GIFT_INFO bss_status字段
	 * 
	 * @param order_id
	 * @return
	 */
	public boolean toChanageGiftBssStatus(String order_id);

	/**
	 * 通过广东AOP办理老用户存费送费业务提交
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult cunFeeSongFeeGDAop(String order_id) throws ApiBusiException;

	/**
	 * (AOP)换机提交
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult changeMachineSubmit(String order_id) throws ApiBusiException;

	/**
	 * (AOP)流量包订购
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult trafficOrder(String order_id) throws ApiBusiException;

	/**
	 * 流量包订购/退订 日包/夜猫包/月包: ecaop.trades.sell.mob.comm.traffic.sub
	 * 
	 * @author songqi
	 * @param order_id
	 * @return
	 */
	public BusiDealResult flowPacket(String order_id) throws ApiBusiException;

	/**
	 * 视频包： ecaop.trades.sell.mob.sp.order.app
	 * 
	 * @author songqi
	 * @param order_id
	 * @return
	 */
	public BusiDealResult spPacket(String order_id) throws ApiBusiException;

	/**
	 * 订单系统访问森锐仿真系统
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult simulation(String order_id) throws ApiBusiException;

	/**
	 * 裸机销售
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult bareMachineSale(String order_id) throws ApiBusiException;

	/**
	 * SP 服务办理业务 params 中的参数
	 * 
	 * @param params
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult handleSpServices(String orderId) throws ApiBusiException;

	/**
	 * 业务补办(目前暂有SP服务)
	 * 
	 * @param params
	 * @return
	 */

	public String rehandleBusiness(Map<String, String> params);

	/**
	 * 业务办理环节状态通知商城
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult businessStatusSync(String order_id) throws ApiBusiException;

	/**
	 * 附加产品订购(广东AOP->BSS)
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	public BusiDealResult subProOrder(String order_id) throws ApiBusiException;

	/**
	 * 续约活动校验和受理(浙江）
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult businessAcceptenceAndVerification(String order_id);

	public BusiDealResult paymentByBSS(String order_id);

}
