package com.ztesoft.net.app.base.core.model;

import com.ztesoft.net.eop.sdk.utils.UploadUtilc;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 友情连接Mapper
 * @author kingapex
 * 2010-7-17上午11:28:05
 */
public class FriendsLinkMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		FriendsLink friendsLink = new FriendsLink();
		friendsLink.setLink_id(rs.getString("link_id"));
		String logo  = rs.getString("logo");
		if(logo!=null) logo  =UploadUtilc.replacePath(logo);
		friendsLink.setLogo(logo);
		friendsLink.setName(rs.getString("name"));
		friendsLink.setSort(rs.getInt("sort"));
		friendsLink.setUrl(rs.getString("url"));
		return friendsLink;
	}

}
