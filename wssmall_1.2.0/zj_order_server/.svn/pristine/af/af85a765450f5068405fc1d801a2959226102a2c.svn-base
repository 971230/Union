package com.ztesoft.net.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.model.InvoiceModeFieldVO;



public interface IOrdPostModePrintManager {
	
	
	/**
	 * @param order_id  --订单id
	 * @param es_delivery  --物流表es_delivery  ID
	 * @param delvery_print_id  --物流打印表es_delivery_prints ID
	 * @param reissueIds  --勾选的补寄品es_delivery_item   id
	 * @param postType  --物流类型 0-正常 发货  1-补寄 2-重发
	 * @param wlnum  --物流ID
	 * @param postId  --物流公司ID
	 * @param cxt  -跟路径
	 * @param order_is_his  --是否是历史数据 1-是
	 */
	public String doPrintInvoice(String order_id,String delivery_id,String delvery_print_id,String reissueIds,String postType,String wlnum,String postId,String cxt,String order_is_his); 

	/**
	 * 根据mode_cd查询域集合(表es_invoice_model_field)
	 * @param mode_cd
	 * @return
	 */
	public List<InvoiceModeFieldVO> getModeList (String mode_cd);
	
	
	/**
	 * 更新补寄品表es_delivery_item的打印状态
	 * @param post_type 
	 * @param delivery_id
	 * @param itemIds 
	 * @param isHis -true 历史表 
	 * @return
	 */
	public void updateItemsPrintStatus (String post_type,String delivery_id ,String itemIds,boolean isHis);
	
	/**
	 * @param order_id  --订单id
	 * @param es_delivery  --物流表es_delivery  ID
	 * @param delvery_print_id  --物流打印表es_delivery_prints ID
	 * @param reissueIds  --勾选的补寄品es_delivery_item   id
	 * @param postType  --物流类型 0-正常 发货  1-补寄 2-重发
	 * * @param wlnum  --物流ID
	 * * @param postId  --物流公司ID
	 * * @param cxt  -跟路径
	 * @param order_is_his  --是否是历史数据 1-是
	 */
	public String doHotFreeModel(String order_id,String delivery_id,String delvery_print_id,String reissueIds,String postType,String wlnum,String postId,String cxt,String order_is_his,String is_receipt);
	
	/**
	 * 华盛B2C装箱单
	 */
	public String hsB2CPacking(Map<String,String> params);

	public String doPrintHotFee(String order_id, String print_type,String contextPath);

	public String doHotFreeModelNew(String order_id, String delivery_id,
			String delvery_print_id, String itmesIds, String post_type,
			String wlnum, String postId, String contextPath,
			String order_is_his, String is_receipt);

	
}
