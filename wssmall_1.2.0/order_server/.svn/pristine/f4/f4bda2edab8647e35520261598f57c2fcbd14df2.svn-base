package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.app.base.plugin.fileUpload.BatchResult;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.action.order.OrderResult;
import com.ztesoft.net.mall.core.model.AccNbr;


/**
 *  资料返档处理类
 * 
 * @author wui
 */
public interface IAccNbrManager {
	
	public void transfer_acc(OrderRequst orderRequst,OrderResult orderResult);
	
	public Page transfer_list(int pageNO, int pageSize,Object... args);
	
	public Page transfer_list_forSearch(int pageNO, int pageSize,Object... args);
	
	/**
	 * 合约卡审核预占用
	 * @param orderRequst
	 * @param orderResult
	 */
	public void transfer_phone_no_for_collect(OrderRequst orderRequst,OrderResult orderResult);
	
	/**
	 * 合约卡调拨预占
	 * @param orderRequst
	 * @param orderResult
	 */
	public void transfer_phone_no_for_accept(OrderRequst orderRequst,OrderResult orderResult);
	
	
	//根据号码编码获取号码信息
	public AccNbr getAccNbr(String accnbr);
	
	//资料反档成功后，更新号码订单
	public void updateAccNbr(AccNbr accNbr);
	
	//根据号码id获取号码信息
	public List<AccNbr> getAccNbrList(String numIds);
	
	
	public void updateAccNbrByOrderId(String orderId,String ship_state);
	
	
	public Map listc(AccNbr accNbr);
	/**
	 * 判断调拨号码是否跨本地网	
	 * @param begin_accnbr
	 * @param end_accnbr
	 * @return
	 */
	public List  getLanCounts(String begin_accnbr,String end_accnbr,AccNbr p_accNbr);
	
	
	//导入号码
	public BatchResult importPhoneNum(List inList);
	

	public Integer getAccNbrCountByOrderId(String orderId,String ship_state);
	
	//判断数据库中是否存在重复记录
	public int isExistsPhoneNo(List list);
	
	
	//取消、撤单资源重置
	public void resetAccNbrByOrderId(String orderId);
	
	//调拨成功后，将已预占、且没有处理成功的释放掉
	public void resetUnUsedAccNbrOrderId(String orderId);
	
	
}
