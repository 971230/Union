package com.ztesoft.net.framework.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class DoubleMapper implements ParameterizedRowMapper {

	
	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Double dobule = new Double(rs.getDouble(1));
		return dobule;
	}

}
