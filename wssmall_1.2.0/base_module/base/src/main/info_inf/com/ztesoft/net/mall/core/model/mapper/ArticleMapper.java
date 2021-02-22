package com.ztesoft.net.mall.core.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import com.ztesoft.net.app.base.core.model.Article;

public class ArticleMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {

		Article article = new Article();
		article.setTitle(rs.getString("title"));
		article.setContent(rs.getString("content"));
		article.setId(rs.getInt("id"));
		SimpleDateFormat dateFormator = new SimpleDateFormat("yyyy-MM-dd HH24:mi:ss");
		article.setCreate_time(dateFormator.format(rs.getDate("create_time")));

		return article;
	}

}
