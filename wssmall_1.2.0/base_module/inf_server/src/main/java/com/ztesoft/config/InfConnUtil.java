package com.ztesoft.config;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
/**
 * 接口数据源工具类，支持直连和数据源连接
 * @author ReasonYea
 *
 */
public class InfConnUtil {
	/**
	 * 接口使用的数据源连接
	 */
	private static final String INF_DATASOURCE="infdb";
	private static ApplicationContext applicationContext;
	private static InfConnUtil InfConnUtil = null;
	private static DataSource db = null;

	static{
		if (InfConnUtil == null) {
			InfConnUtil = new InfConnUtil();
			
			if(SpringContextHolder.isConfig()){//web 容器启动
				applicationContext=SpringContextHolder.getApplicationContext();
			}else{//main函数启动
				String springXml = ParamsConfig.getInstance().getParamValue("SpringXml");
				applicationContext = new ClassPathXmlApplicationContext(springXml);
			}
			
		}
	}

	
	/**
	 * 获取数据源
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		return getDatasourceConn();
	}
	
	/**
	 * 通过spring配置DataSource
	 * @return
	 */
	private static Connection getDatasourceConn(){
		try {
			if (db == null) {
				db = applicationContext.getBean(INF_DATASOURCE, DataSource.class);
			}
			return db.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
