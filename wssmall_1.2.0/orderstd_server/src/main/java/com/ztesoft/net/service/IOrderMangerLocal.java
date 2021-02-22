package com.ztesoft.net.service;

import java.sql.SQLException;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;

public interface IOrderMangerLocal {
	public Map<String,String> getGoodsInfo(String package_id);
	
	/**
	 * 根据地市编码查询地市名称
	 * @author GL
	 * @param cityId
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public String getCityname(String cityId) throws FrameException, SQLException;
	/**
	 * 根据地市编码查询省份编码
	 * @author GL
	 * @param CountryId
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public String getProviceId(String cityId) throws FrameException, SQLException;
	
	/**
	 * 根据地市编码查询省份名称
	 * @author GL
	 * @param CountryId
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public String getProvicename(String CountryId) throws FrameException, SQLException;
	
	public String queryString(String sql) throws FrameException, SQLException;
	public Map queryForMap(String sql,Object... args) throws FrameException, SQLException;
	public boolean updateHSMatnrByGoodsId(String goods_id , String matnr);
	public Map<String,String> getHSGoodsByMatnr(String matnr);

	/**
	 * 根据先份编码查询县分名称
	 * @author GL
	 * @param ship_country
	 * @return
	 */
	public String getCountyName(String ship_country) throws FrameException, SQLException;
	
}
