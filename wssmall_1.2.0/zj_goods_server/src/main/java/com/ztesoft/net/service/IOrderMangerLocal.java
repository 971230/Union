package com.ztesoft.net.service;

import java.sql.SQLException;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;

public interface IOrderMangerLocal {
	public Map<String,String> getGoodsInfo(String package_id);
	public String getCityname(String cityId) throws FrameException, SQLException;
	public String getProviceId(String CountryId) throws FrameException, SQLException;
	public String getProvicename(String CountryId) throws FrameException, SQLException;
	public String queryString(String sql) throws FrameException, SQLException;
	public Map queryForMap(String sql,Object... args) throws FrameException, SQLException;
}
