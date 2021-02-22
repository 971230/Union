package com.ztesoft.net.mall.core.model.mapper;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.mall.core.model.support.TypeArea;
import com.ztesoft.net.mall.core.model.support.TypeAreaConfig;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 配送地区-配送方式关联表mapper
 * @author kingapex
 *2010-3-30上午08:58:02
 */
public class TypeAreaMapper implements RowMapper {

	
	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TypeArea typeArea = new TypeArea();
		typeArea.setArea_id_group(rs.getString("area_id_group"));
		typeArea.setArea_name_group(rs.getString("area_name_group"));
		typeArea.setConfig(rs.getString("config"));
		typeArea.setExpressions(rs.getString("expressions"));
		typeArea.setHas_cod(rs.getInt("has_cod"));
		typeArea.setType_id(rs.getString("type_id"));
		typeArea.setTypeAreaConfig( JSON.parseObject(typeArea.getConfig(),TypeAreaConfig.class));
		return typeArea;
	}

}
