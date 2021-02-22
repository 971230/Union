package com.ztesoft.net.app.base.core.model;

import java.sql.ResultSet;
import java.sql.SQLException;



import org.springframework.jdbc.core.RowMapper;

public class NewsMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		NewsVO news=new NewsVO();
		news.setNews_id(rs.getString("news_id"));
		news.setTitle(rs.getString("title"));
		news.setContent(rs.getString("content"));
		news.setEnd_time(rs.getString("end_time"));
		news.setState(rs.getInt("state"));
		news.setUser_id(rs.getString("user_id"));
		news.setCreate_time(rs.getString("create_time"));
		news.setBrowser_limit(rs.getInt("browser_limit"));
		return news;
	}

}
