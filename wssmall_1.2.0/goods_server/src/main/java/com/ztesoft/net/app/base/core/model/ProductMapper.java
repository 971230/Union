package com.ztesoft.net.app.base.core.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ztesoft.net.eop.resource.model.EopProduct;
import com.ztesoft.net.eop.sdk.utils.UploadUtilc;

/**
 * eop解决方案mapper
 * @author kingapex
 * 2010-9-26上午12:49:26
 */
public class ProductMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		EopProduct product = new EopProduct();
		product.setId(rs.getInt("id"));
		product.setProductid(rs.getString("productid"));
		product.setProduct_name(rs.getString("product_name"));
		product.setAuthor(rs.getString("author"));
		product.setCatid(rs.getString("catid"));
		product.setColorid(rs.getString("colorid"));
		product.setCreatetime(rs.getString("createtime"));
		product.setDescript(rs.getString("descript"));
		product.setTypeid(rs.getString("typeid"));
		String preview  = rs.getString("preview");
		
		/**
		 * 将本地字串替换为静态资源服务器地址
		 */
		preview  = UploadUtilc.replacePath(preview);
		product.setPreview(preview);
		product.setVersion(rs.getString("version"));
		product.setSort( rs.getInt("sort") );
		
		return product;
	}

}
