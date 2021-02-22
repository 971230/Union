package com.ztesoft.component.common.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;

//import com.ztesoft.webservice.core.processor.InterfaceCache;

public class CrmInitServlet extends HttpServlet {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CrmInitServlet.class);

	@Override
	public void init() throws ServletException {
		String file_sepa = System.getProperty("file.separator");
		// String web_inf_path = getServletContext().getRealPath("/") +
		// file_sepa
		String web_inf_path = getServletContext().getRealPath("") + file_sepa
				+ "WEB-INF" + file_sepa;
		System.setProperty("WEB_INF_PATH", web_inf_path);
		CrmConstants.WEB_INF_PATH = web_inf_path;
		logger.debug("-----------------1111111111111111112222222222222222222222222");
		// init log4j
		// org.apache.log4j.xml.DOMConfigurator.configure(CrmConstants.WEB_INF_PATH+"classes"+file_sepa+"log4j.xml");
		// org.apache.log4j.PropertyConfigurator.configure(CrmConstants.WEB_INF_PATH+"classes"+file_sepa+"log4j.properties");
		/*
		 * BrandCache caches = new BrandCache(); caches.loadData();
		 */
		// 初始化
		try {

		
			CrmParamsConfig.getInstance().initParams(null);
			// SysSet.initSystem(3);//web + app
			// 加载数据源
//			ConnectionPool.getInstance().createPool();
		} catch (Exception e) {
			System.err.println("配置参数载入失败: " + e);
			logger.info("配置参数载入失败: ", e);
		}

		try {
			// 静态数据载入
//			StaticAttrCache.getInstance().initStaticAttr();

		} catch (Exception e) {
			System.err.println("静态数据载入失败: " + e);
			logger.info("静态数据载入失败: ", e);
		}

		// try {
		// InterfaceCache.getInst().initUosConfig();
		// } catch (Exception e) {
		// //e.printStackTrace();
		// System.err.println("UOS_CONFIG 配置参数载入失败: " + e);
		// logger.info("UOS_CONFIG 配置参数载入失败: ", e);
		// }

		// try {
		// SooCacheUtil.getInstance().loadData();
		// } catch (Exception e) {
		// //e.printStackTrace();
		// System.err.println("SOO 配置参数载入失败: " + e);
		// logger.info("SOO 配置参数载入失败: ", e);
		// }

		// try {
		//
		// // 初始化Service拦截器载入
		// ServiceLibrary.getInst().initlize();
		//
		// } catch (Exception e) {
		// logger.info("初始化Service拦截器载入失败", e);
		// }
		// try {
		//
		// // 初始化页面元素配置数据
		// PageTagUtils.getInstance().initlize();
		//
		// } catch (Exception e) {
		// logger.info("初始化Service拦截器载入失败", e);
		// }

	}

	/**
	 * 服务方法
	 */
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
