package com.ztesoft.net.mall.core.model.mapper;

import com.ztesoft.net.mall.core.model.GoodsType;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodsTypeMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		GoodsType goodsType = new GoodsType();
		goodsType.setType_id(rs.getString("type_id"));
		goodsType.setName(rs.getString("name"));
		goodsType.setHave_parm(rs.getInt("have_parm"));
		goodsType.setHave_prop(rs.getInt("have_prop"));
		goodsType.setIs_physical(rs.getInt("is_physical"));
		goodsType.setJoin_brand(rs.getInt("join_brand"));
		goodsType.setProps(rs.getString("props"));
		goodsType.setParams(rs.getString("params"));
		
		return goodsType;
		
	}

}
