package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.app.base.plugin.fileUpload.BatchResult;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.action.order.OrderResult;
import com.ztesoft.net.mall.core.model.Cloud;


/**
 *  资料返档处理类
 * 
 * @author wui
 */
public interface ICloudManager {
	
	//云卡审核预占用
	public void transfer_cloud_for_collect(OrderRequst orderRequst,OrderResult orderResult);
	
	
	//云卡调拨预占
	public void transfer_cloud_for_accept(OrderRequst orderRequst,OrderResult orderResult);
	
	
	public Page transfer_list(int pageNO, int pageSize,Object... args);
	
	public List<Cloud> getCloudList(String cloudIds);
	
	public List list(String orderId);
	
	public void updateCloud(Cloud cloud);
	
	//获取调拨卡的价格，价格做验证处理
	public String  getTransferPrice(String cloudIds);
	
	public Map listc(Cloud cloud);
	
    public Page transfer_list_query(int pageNO, int pageSize,Object... args);
	//重置云卡
	public void resetCloudByOrderId(String orderId);
	
	//查询云卡
	public Cloud  getCloudByAccNbr(String phone_num);
	
	public void updateCloudByOrderId(String orderId);
	
	public void updateCloudByOrderId(String orderId,String ship_state);
	
	//导入云卡
	public BatchResult importCloud(List inList);
	
	public Integer getCloudCountByOrderId(String orderId,String ship_state);

	public List  getLanCounts(String begin_accnbr,String end_accnbr,Cloud cloud);
	
	//判断调拨号码金额是否不一致
	public List  getPayMoneyLevel(String begin_accnbr,String end_accnbr);
	
	

	//对于号码段之间的号码没有一个可用的，直接设置为垃圾云卡,因为此号段在电信已经被使用掉
	public void resetUnValidUsedCloudOrderId(String orderId);
	
	
	/**
	 * 判断数据库中是否存在相同的记录
	 * @param list
	 * @return
	 */
	public int isExistsCloud(List list);
	
	//云卡重置
	public void resetUnUsedCloudOrderId(String orderId);

	/**
	 * 提供给CRM接口调用，电信调拨云卡给一级代理商
	 * @param refCode 关联工号
	 * @param orgId CRM组织id
	 * @param orgName CRM组织名称
	 * @param orderId 订单id
	 * @param cloudList list中的Map的Key为： {offer_name:销售品名称, phone_num:业务号码, pay_money:批开金额, uim， lan_id:本地网id, offer_id:销售品id} <br/>
	 * 注释：销售品ID和销售品名称填写CRM系统的套餐id和名称；支付金额填写批开云卡的预存款费用；本地网id参考长沙：731，湘潭：732，株洲：733等
	 * @return 返回Map结构，{result:"SUCCESS/ERROR", message,"描述", return_list:"未使用卡的列表" } return_list只有result=SUCCESS才返回 
	 */
	public Map<String, Object> importCloudFromCRM(String refCode, String orgId, String orgName, String orderId, List<Map<String,Object>> cloudList);
	
	
}
