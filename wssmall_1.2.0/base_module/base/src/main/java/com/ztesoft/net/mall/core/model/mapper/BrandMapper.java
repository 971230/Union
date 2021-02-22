package com.ztesoft.net.mall.core.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ztesoft.net.eop.sdk.utils.UploadUtilc;
import com.ztesoft.net.mall.core.model.Brand;

/**
 * 品牌Mapper<br>
 * 会将本地文件存储的图片地址前缀替换为静态资源服务器地址。
 * @author kingapex
 * 2010-7-16下午03:17:28
 */
public class BrandMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Brand brand = new Brand();
		brand.setBrand_id(rs.getString("brand_id"));
		brand.setName(rs.getString("name"));
		brand.setBrand_code(rs.getString("brand_code"));
		brand.setBrief(rs.getString("brief"));
		String logo = rs.getString("logo");
		if(logo!=null){
			logo  =UploadUtilc.replacePath(logo);
		}
		brand.setLogo(logo);
		brand.setUrl(rs.getString("url"));
		return brand;
	}

}
