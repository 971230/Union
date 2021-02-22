package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.LimitBuy;
import com.ztesoft.net.mall.core.model.LimitBuyGoods;

import java.util.List;
import java.util.Map;

public interface ILimitBuyManager {
	
	public void add(LimitBuy limitBuy );
	public void edit(LimitBuy limitBuy );
	public Page list(int pageNo,int pageSize);
	public   List<LimitBuy> listEnable();
	public List<Map>  listEnableGoods();
	public void delete(String id);
	public LimitBuy get(String id);
	public void updateBuyGoods(LimitBuyGoods limitBuyGoods);
	public Page queryList(Map<String,String> map, int pageNo, int pageSize);
	/**逻辑删除与恢复
	 * @param id
	 * @param disabled
	 */
	public void editLimitState(String id,int disabled);
	/**查询单个秒杀活动相关的商品信息
	 * @param map
	 * @return
	 */
	public List<Map> queryLimitBuyGoods(Map<String,String> map);
}
