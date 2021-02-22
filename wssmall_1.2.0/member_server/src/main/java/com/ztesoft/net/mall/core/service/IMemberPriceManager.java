package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.GoodsLvPrice;

/**
 * 会员价格管理接口
 * @author kingapex
 *
 */
public interface IMemberPriceManager {
	
	public List<Map> getSpecPrices(String gid , String pid ) ;
	
	public List loadPricePrivList(String goodsid)  ;
	
	public List loadMemberLvPriceList(String gid) ;
	
	/**
	 * 添加会员价格
	 * @param goodsPrice
	 */
	public void save(List<GoodsLvPrice> goodsPrice);
	
	
	/**
	 * 读取某个商品的所有规格的会员价格。
	 * @param 
	 * @return
	 */
	public List<GoodsLvPrice> listPriceByGid(String goodsid );
	
	/**
	 * 读取某会员级别的商品价格
	 * @param lvid
	 * @return
	 */
	public List<GoodsLvPrice> listPriceByLvid(String lvid);
	
	/**
	 * 按产品ID与会员等级查询商品价
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-17 
	 * @param productID
	 * @param lvid
	 * @return
	 */
	public GoodsLvPrice qryPriceByPid(String productID,String lvid);
	
	/**
	 * 会员角色静态数据加载
	 * @return
	 */
	public List loadMemberLvList()  ;
	
	/**
	 * 調價申請
	 * @param sn
	 * @return
	 */
	public Page goodsLvList(int pageNo, int pageSize, String sn);
}
