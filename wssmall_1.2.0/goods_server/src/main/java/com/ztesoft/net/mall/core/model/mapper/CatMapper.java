package com.ztesoft.net.mall.core.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ztesoft.net.eop.sdk.utils.UploadUtilc;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Cat;

/**
 * 类别Mapper
 * 会将本地文件存储的图片地址前缀替换为静态资源服务器地址。
 * @author kingapex
 * 2010-7-16下午03:41:42
 */
public class CatMapper implements RowMapper {

	
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Cat cat =  new Cat();
		cat.setCat_id(rs.getString("cat_id"));
		cat.setCat_order(rs.getInt("cat_order"));
		cat.setCat_path(rs.getString("cat_path"));
		String floor_list_show  =rs.getString("floor_list_show");
		if(StringUtil.isEmpty(floor_list_show))
			floor_list_show ="1";
		cat.setFloor_list_show(rs.getString("floor_list_show"));
		String image = rs.getString("image");
		if(image!=null){
			image  =UploadUtilc.replacePath(image);
		}
		cat.setImage(image);
		cat.setList_show(rs.getString("list_show"));
		cat.setName(rs.getString("name"));
		cat.setFloor_list_show(rs.getString("floor_list_show"));
		cat.setParent_id(rs.getString("parent_id"));
		cat.setType_id(rs.getString("type_id"));
		cat.setType_name(rs.getString("type_name"));
		cat.setType(rs.getString("type"));
		try{
			cat.setLvid0(rs.getString("lvid0"));
			cat.setLvid1(rs.getString("lvid1"));
			cat.setLvid2(rs.getString("lvid2"));
			cat.setLvid3(rs.getString("lvid3"));
		}catch(Exception ex){
			
		}
		return cat;
	}

}
