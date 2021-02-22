package com.ztesoft.net.app.base.core.model;

import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.model.Image;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据日志mapper
 * @author kingapex
 * 2010-10-20上午09:59:22
 */
public class DataLogMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		DataLog datalog = new DataLog();
		datalog.setContent(rs.getString("content"));
		datalog.setDateline(rs.getString("dateline"));
		datalog.setDomain(rs.getString("domain"));
		datalog.setId(rs.getString("id"));
		datalog.setLogtype(rs.getString("logtype"));
		datalog.setOptype(rs.getString("optype"));
		String pics = rs.getString("pics");
		datalog.setPics(pics);
		datalog.setSitename(rs.getString("sitename"));
		datalog.setSiteid(rs.getString("siteid"));
		datalog.setUserid(rs.getString("userid") );
		datalog.setUrl(rs.getString("url"));
		
		//处理图片列表
		if(pics!=null && !"".equals(pics)){
			List<Image> imgList = new ArrayList<Image>();
			String[] picar = pics.split(",");
			
			for(String picstr:picar){
				String[] pic= picstr.split("\\|");
				
				String thumbpic  = pic[0];
				String originalpic = pic[1];
				
				if(thumbpic!=null){
					thumbpic = thumbpic.replaceAll(EopSetting.FILE_STORE_PREFIX,EopSetting.IMG_SERVER_DOMAIN+"/user/"+ datalog.getUserid()+"/"+ datalog.getSiteid() );
				}
				
				if(originalpic!=null){
					originalpic= originalpic.replaceAll(EopSetting.FILE_STORE_PREFIX,EopSetting.IMG_SERVER_DOMAIN+"/user/"+ datalog.getUserid()+"/"+ datalog.getSiteid() );
				}
				Image image  = new Image();
				image.setOriginal(originalpic);
				image.setThumb(thumbpic);
				imgList.add(image);
			}
			datalog.setPicList(imgList);
		}
		
		return datalog;
	}

}
