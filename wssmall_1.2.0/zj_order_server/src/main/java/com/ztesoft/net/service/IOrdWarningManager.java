package com.ztesoft.net.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.FieldVO;
import com.ztesoft.net.model.InvoiceModeFieldParams;
import com.ztesoft.net.model.InvoiceModeFieldVO;
import com.ztesoft.net.model.InvoiceModeVO;
import com.ztesoft.net.model.ModeSelectFieldVO;
import com.ztesoft.net.model.OrderWarning;

/**
 * 订单预警配置
 * @author jun
 *
 */

public interface IOrdWarningManager {

	/**
	 * 列表查询
	 * @param orderWarning
	 * @param pageNo
	 * @param pageSize
	 */
	public Page queryList(OrderWarning orderWarning,int pageNo, int pageSize) ;
	
	
	/**
	 * 根据ID查询详情
	 * @param invoiceModeVO
	 */
	public OrderWarning queryOrderWarnById(String  warningId);
	
	/**
	 * 更新
	 * @param orderWarning
	 */
	public void update(OrderWarning orderWarning);
	
	/**
	 * 新增
	 * @param orderWarning
	 */
	public void save(OrderWarning orderWarning);

	
	/**
	 * 
	 * 从老系统的库搬预警规则数据到现系统的库里
	 */
	public void inserDataFromOldSys( ); 

	public Page getDevelopmentList(String developmentCode,String developmentName,int pageIndex,int pageSize);
	
	/**
	 * 根据县分名称查询行政县份
	 * @param countyName
	 * @param pageIndex
	 * @param pageSize
	 */
	public Page getXcountyList(String countyName,String regionId,int pageIndex,int pageSize);
	
	/**
	 * 根据内部单号查询订单市区编码
	 * @param order_id
	 */
	public String qryAreaIdByOrderId(String order_id);
	
	public List<Map> getDcSqlByDcName(String dcName);
	
	public Page queryGoodsList(String goodsName,String order_id,String line_type,String service_type,int pageNo,int pageSize);


	public String changeNewGoods(String new_goods_id, String stdOrderId);

}
