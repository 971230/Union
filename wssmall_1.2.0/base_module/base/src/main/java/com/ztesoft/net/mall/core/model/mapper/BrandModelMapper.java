package com.ztesoft.net.mall.core.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ztesoft.net.mall.core.model.BrandModel;

/**
 * 型号Mapper<br>
 * 会将本地文件存储的图片地址前缀替换为静态资源服务器地址。
 * @author kingapex
 * 2010-7-16下午03:17:28
 */
public class BrandModelMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BrandModel brandModel = new BrandModel();
		brandModel.setBrand_model_id(rs.getString("brand_model_id"));
		brandModel.setBrand_name(rs.getString("brand_name"));
		brandModel.setBrand_code(rs.getString("brand_code"));
		brandModel.setModel_name(rs.getString("model_name"));
		brandModel.setModel_code(rs.getString("model_code"));
		brandModel.setMachine_name(rs.getString("machine_name"));
		brandModel.setMachine_code(rs.getString("machine_code"));
		return brandModel;
	}

}
