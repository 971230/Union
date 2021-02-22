package services;

import java.util.Map;

public abstract class AbsCommonPay{
	
	public static final int status_0 = 0;//支付成功
	public static final int status_1 = 1;//支付异常
	public static final int status_2 = 2;//支付失败
	public static final int status_3 = 3;//用户签名验证失败
	
	/**
	 * 0线上支付  1线下支付
	 */
	public static final String ONLINE = "0";
	public static final String OFFLINE = "1";

	protected Map<String,String> requestParams;
	
	public void init(Map<String,String> requestParams){
		this.requestParams = requestParams;
	}
	
	/**
	 * 需要去付到的账户的用户ID 调用顺序(4)
	 * @作者 MoChunrun
	 * @创建日期 2013-12-26 
	 * @return
	 * @throws RuntimeException
	 */
	public abstract String getPayToUserId() throws RuntimeException;
	
	/**
	 * 获取支付总金额    调用顺序(1)
	 * @作者 MoChunrun
	 * @创建日期 2013-12-26 
	 * @return
	 * @throws RuntimeException
	 */
	public abstract double getPayMoney() throws RuntimeException;
	
	/**
	 * 获取订单号  调用顺序(2)
	 * @作者 MoChunrun
	 * @创建日期 2013-12-26 
	 * @return
	 * @throws RuntimeException
	 */
	public abstract String getOrderId() throws RuntimeException;
	
	/**
	 * 支付时调用该方法处理页务  调用顺序(3)
	 * onlineFlag 0线上支付  1线下支付
	 * @作者 MoChunrun
	 * @创建日期 2013-12-26 
	 * @throws RuntimeException
	 */
	public abstract void payBusiness(String transactionId,String onlineFlag,double pay_money,String order_id) throws RuntimeException;
	
	/**
	 * 业务类型  调用顺序(4)
	 * @作者 MoChunrun
	 * @创建日期 2013-12-26 
	 * @return
	 */
	public abstract String getTypeCode();
	
	/**
	 * 支付银联回调 单独调用
	 * @作者 MoChunrun
	 * @创建日期 2013-12-26 
	 * @param transactionId
	 * @param status 0成功 1异常 2失败
	 * @return
	 * @throws RuntimeException
	 */
	public abstract boolean callbackBusiness(String transactionId,int status) throws RuntimeException;
	
	/**
	 * 返回成功后跳转的地址  单独调用
	 * @作者 MoChunrun
	 * @创建日期 2013-12-26 
	 * @return
	 * @throws RuntimeException
	 */
	public abstract boolean redirectBusiness(String transactionId,int status) throws RuntimeException;
	
	
}
