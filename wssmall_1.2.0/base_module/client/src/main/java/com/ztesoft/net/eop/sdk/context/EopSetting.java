package com.ztesoft.net.eop.sdk.context;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.ztesoft.net.eop.sdk.utils.UploadUtilc;
import com.ztesoft.net.framework.image.ThumbnailCreatorFactory;
import com.ztesoft.net.framework.util.StringUtil;

public class EopSetting {
	private static Logger logger = Logger.getLogger(EopSetting.class);
	//是否为测试模式
	public static boolean TEST_MODE =false; 
	
	
	//EOP服器根
	public static String EOP_PATH ="";
	
	
	/*
	 * 图片服务器域名
	 */
	public static String IMG_SERVER_DOMAIN = "localhost";

	/*
	 * 
	 * 图片服务器地址
	 */
	public static String IMG_SERVER_PATH="";
	
	/*
	 * 应用数据存储地址
	 */
	public static String APP_DATA_STORAGE_PATH ="e:/eop";
	
	/*
	 * 产品存储目录
	 */	
	public static String PRODUCTS_STORAGE_PATH ="E:/workspace/eop3/eop/src/products";
	
	
	/*
	 * 服务器域名
	 */
	public static String APP_DOMAIN_NAME = "eop.com";
	
	public static String SERVICE_DOMAIN_NAME="";
	
	/*
	 * 用户数据存储路根径
	 */
	public static String USERDATA_STORAGE_PATH = "/user";
	
	
	public static String SOURCE_FROM ="";
	
	public static String DB_SOURCE_FROM ="";
	
	
	
	/*
	 * 后台主题存储路径
	 * 包括公共资源和用户资源的
	 * '/'代表当时的上下文
	 */
	public static String ADMINTHEMES_STORAGE_PATH = "/mgWebthemes";
	
	
	
	
	/*
	 * 前台主题存储路径
	 * 包括公共资源和用户资源的
	 * '/'代表当时的上下文
	 */	
	public static String THEMES_STORAGE_PATH = "/themes";
	
	
	public static String THEMES_STORAGE_PATH_F = "/themes";
	
	/*
	 * EOP虚拟目录
	 */
	public static String CONTEXT_PATH ="/";
	
	//资源模式
	public static String RESOURCEMODE="1";
	
	//运行模式
	public static String RUNMODE ="1";
	
	//数据库类型
	public static String DBTYPE ="2" ;
	
	//CLOB|BLOG用数据库存储
	public static String DBBLOGTYPE = "1";
	
	//扩展名
	public static String EXTENSION ="do";
	
	//是否使用默认eop的引擎,on为使用，off为不使用
	public static String TEMPLATEENGINE="on";
	
	public static String  THUMBNAILCREATOR ="javaimageio";
	
	public static String  FILE_STORE_PREFIX ="fs:"; //本地文件存储前缀
	
	public static String VERSION =""; //版本
	public static String PRODUCTID ="";
	
	public static String INSTALL_LOCK ="NO"; //是否已经安装
	
	public static List<String> safeUrlList = new ArrayList<String>();;
	public static String BACKEND_PAGESIZE = "10";
	public static  String ENCODING;
	public static  String ROP_VALI ="no";
	
	public static String DEFAULT_IMG_URL =IMG_SERVER_DOMAIN+"/images/no_picture.jpg";
	
	public static String DB_CONNECT = "Y";
	
	/*
	 * 从配置文件中读取相关配置<br/>
	 * 如果没有相关配置则使用默认
	 */
	static {
		try{
			InputStream in  = UploadUtilc.getResourceAsStream("eop.properties");
			Properties props = new Properties();
			props.load(in);
			init(props);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	 
	
	public static void init(Properties props ){
		
		String encoding =  props.getProperty("encoding");
		ENCODING  = StringUtil.isEmpty(encoding) ? "": encoding;
		
		String rop_val =  props.getProperty("rop_val");
		ROP_VALI  = StringUtil.isEmpty(rop_val) ? "no": rop_val; //默认不需要做rop功能验证
		
		
		SOURCE_FROM = props.getProperty("source_from");
		String theme_source_from =props.getProperty("theme_source_from");
		if(SOURCE_FROM==null)SOURCE_FROM = "";
		if(StringUtil.isEmpty(theme_source_from))
			theme_source_from = SOURCE_FROM;
		
		if(!SOURCE_FROM.equals(theme_source_from) && !StringUtil.isEmpty(theme_source_from))
			SOURCE_FROM = theme_source_from;
		
		DB_SOURCE_FROM = props.getProperty("db_source_from");
		if(StringUtil.isEmpty(DB_SOURCE_FROM))
			DB_SOURCE_FROM = SOURCE_FROM;
		
		
		String domain = props.getProperty("domain.imageserver");
		IMG_SERVER_DOMAIN = StringUtil.isEmpty(domain) ? IMG_SERVER_DOMAIN: domain;
		IMG_SERVER_DOMAIN = IMG_SERVER_DOMAIN;//IMG_SERVER_DOMAIN.startsWith("http://") ? IMG_SERVER_DOMAIN: "http://" + IMG_SERVER_DOMAIN;
		
		

		String userdata_storage_path = props.getProperty("storage.userdata");
		USERDATA_STORAGE_PATH = StringUtil.isEmpty(userdata_storage_path) ? USERDATA_STORAGE_PATH: userdata_storage_path;
		
		
		String adminthemes_storage_path = props.getProperty("storage.adminthemes");
		ADMINTHEMES_STORAGE_PATH = StringUtil.isEmpty(adminthemes_storage_path) ? "/mgWebthemes" + theme_source_from: adminthemes_storage_path;
			
		String themes_storage_path = props.getProperty("storage.themes");
		THEMES_STORAGE_PATH = StringUtil.isEmpty(themes_storage_path) ? THEMES_STORAGE_PATH: themes_storage_path;
		
		THEMES_STORAGE_PATH_F = StringUtil.isEmpty(themes_storage_path) ? "/themes" + SOURCE_FROM: themes_storage_path; //add by wui
		
		
		String eop_path = props.getProperty("storage.EOPServer");
		EOP_PATH = StringUtil.isEmpty(eop_path) ? EOP_PATH: eop_path;
		

		String imageserver_path = props.getProperty("path.imageserver");
		IMG_SERVER_PATH = StringUtil.isEmpty(imageserver_path) ? IMG_SERVER_PATH: imageserver_path;
 
		
		String app_data = props.getProperty("storage.app_data");
		APP_DATA_STORAGE_PATH = StringUtil.isEmpty(app_data) ? APP_DATA_STORAGE_PATH: app_data;
 		
		
		String context_path = props.getProperty("path.context_path");
		CONTEXT_PATH = StringUtil.isEmpty(context_path) ? CONTEXT_PATH: context_path;	
		

		String products_path = props.getProperty("storage.products");
		PRODUCTS_STORAGE_PATH = StringUtil.isEmpty(products_path) ? PRODUCTS_STORAGE_PATH: products_path;	
		
		//资源模式
		String resoucemode = props.getProperty("resourcemode");
		RESOURCEMODE=  StringUtil.isEmpty(resoucemode)?RESOURCEMODE:resoucemode;
		
		//运行模式
		String runmode = props.getProperty("runmode");
		RUNMODE=  StringUtil.isEmpty(runmode)?RUNMODE:runmode;

		//数据库类型
		String dbtype = props.getProperty("dbtype");
		DBTYPE=  StringUtil.isEmpty(dbtype)?DBTYPE:dbtype;

		//大字段存储方式
		String dbblogtype = props.getProperty("dbblogtype");
		DBBLOGTYPE=  StringUtil.isEmpty(dbblogtype)?DBBLOGTYPE:dbblogtype;
		
		//扩展名
		String extension = props.getProperty("extension");
		EXTENSION=  StringUtil.isEmpty(extension)?EXTENSION:extension;
		
		
		String templateengine = props.getProperty("templateengine");
		TEMPLATEENGINE=  StringUtil.isEmpty(templateengine)?TEMPLATEENGINE:templateengine;		

		String thumbnailcreator = props.getProperty("thumbnailcreator");
		THUMBNAILCREATOR=  StringUtil.isEmpty(thumbnailcreator)?THUMBNAILCREATOR:thumbnailcreator;
		ThumbnailCreatorFactory.CREATORTYPE = THUMBNAILCREATOR;

		VERSION = props.getProperty("version");
		if(VERSION==null) VERSION="";
		
		PRODUCTID = props.getProperty("productid");
		if(PRODUCTID==null) PRODUCTID="";
		
		File installLockFile = new File(StringUtil.getRootPath()+"/install/install.lock");
		if( installLockFile.exists() ){
			INSTALL_LOCK = "YES"; //如果存在则不能安装
		}else{
			INSTALL_LOCK = "NO"; //如果不存在，则认为是全新的，跳到install页
		}
		
		String servicedomain = props.getProperty("domain.service");
		SERVICE_DOMAIN_NAME = StringUtil.isEmpty(servicedomain)?SERVICE_DOMAIN_NAME:servicedomain;
		
		DB_CONNECT = props.getProperty("dbconnect");
		
		String backend_pagesize = props.getProperty("backend.pagesize");
		BACKEND_PAGESIZE = StringUtil.isEmpty(backend_pagesize)?BACKEND_PAGESIZE:backend_pagesize;
		initSafeUrl();
	}
	
	
	
	/**
	 * 初始化安全url
	 * 这些url不用包装 safeRequestWrapper
	 */
	private static void initSafeUrl(){
		
		try{
			//加载url xml 配置文档
			SAXReader saxReader = new SAXReader();
			File file = new File(System.getProperty("CONFIG")+"safeurl.xml");
			if(file.exists()){
				Document document = saxReader.read(file);
			    Element root = document.getRootElement();
			    List urls = root.elements();
			    for( int i=0;i<urls.size();i++){
			    	Element url = (Element) urls.get(i);
			    	String str = url.getText();
			    	safeUrlList.add(str);
			    	logger.info("safeurl:"+str);
			    }
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
