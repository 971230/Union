package com.ztesoft.net.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import zte.net.ecsord.params.busi.req.OrderDeliveryItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.DeliveryPrints;
import com.ztesoft.net.mall.core.model.Logi;
import com.ztesoft.net.mall.core.model.LogiCompanyPerson;
import com.ztesoft.net.model.OrdReceipt;
import com.ztesoft.net.model.OrderBtn;
import com.ztesoft.net.model.OrderQueryParams;

public interface IOrdPostPrintManager {

	/**
	 * 查询订单货品、价格等等数据
	 * @param order_id 订单id
	 * @param order_ids 多个订单的id
	 * @param logi_no 物流单ID
	 * @param sending_type 配送方式
	 * @param n_shipping_amount 运费
	 * @param post_type 物流类型 0正常发货、1补寄、2重发
	 * @param delivery_id  物流单id
	 * @param order_is_his  1-是历史单据
	 * retrun  DeliveryPrints 
	 */
	public DeliveryPrints queryPostDefaultInfo(String order_id,String order_ids,String sending_type,String n_shipping_amount,String post_type,String delivery_id,String order_is_his,String logi_company) ;
	
	
	/**
	 * 获取物流公司详细信息
	 * @param logi_company 物流公司ID
	 * @return  Map  （key是表es_logi_company_person的字段名）
	 */
	public Map<String,String>  queryPostModel(String logi_company, String order_id) ;
	/**
	 * 接收自定义标签的参数 
	 * @param name属性名称
	 * @param order_id 
	 * retrun 参数的值
	 */
	public String  getParamByLikeName(String order_id,String name) ;
	


	/**
	 * 保存物流打印补充信息
	 */
	public String doAddOne(DeliveryPrints prints); 
	
	/**
	 * 质检稽核界面添加礼品到补寄品表
	 */
	public OrderDeliveryItemBusiRequest addDeliveryItem(OrderDeliveryItemBusiRequest item);
	/**
	 * 获取订单实物名称和sku
	 * @param order_id
	 */
	public String queryEntityProdInfos(String order_id);

	/**
	 * 根据物流单号找到物流打印单中的保价金额
	 * */
	public Map queryInsureValue(String order_id);
}
