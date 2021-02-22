/**
 * 
 */
package com.ztesoft.net.service;

import java.util.List;
import java.util.Map;

/**
 * @author zengjunhua
 *
 */
public interface ISystemPageManager {
	 //查询系统公告
    public List getNoticeList();
    //查询待处理订单
    public List getPendingOrderList();
    //查询订单审核环节
    public List getOrderList(List<String> trace_id);
  //查询稽核环节
    public List getOrderFList();
  //查询配货环节
    public List getOrderCList();
  //查询发货环节
    public List getOrderHList();
    public List<Map> getOrderListCount();
    
}
