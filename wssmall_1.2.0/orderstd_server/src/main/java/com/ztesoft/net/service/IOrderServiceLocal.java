package com.ztesoft.net.service;

import java.util.Map;

import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.resp.GoodsGetResp;

public interface IOrderServiceLocal {
	public GoodsGetResp getGoods(GoodsGetReq req) ;
	
	/**
	 * 根据地市编码查询地市名称
	 * @author GL
	 * @param cityId
	 * @return
	 */
	public String getCityname(String cityId) ;
	
	/**
	 * 根据地市编码查询省份编码
	 * @author GL
	 * @param CountryId
	 * @return
	 */
	public String getProviceId(String CountryId) ;
	/**
	 * 根据地市编码查询省份名称
	 * @author GL
	 * @param CountryId
	 * @return
	 */
	public String getProvicename(String CountryId) ;
	
	public String queryString(String sql);
	
	public Map queryMap(String sql,Object... args);
	
	public boolean updateHSMatnrByGoodsId(String goods_id , String matnr);
	public GoodsGetResp getHSGoodsByMatnr(GoodsGetReq req) ;

	/**
	 * 根据县分编码查询县分名称
	 * @author GL
	 * @param ship_country
	 * @return
	 */
	public String getCountryName(String ship_country);
	
}
