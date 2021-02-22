package com.ztesoft.net.eop.sdk.context;

import java.io.InputStream;
import java.util.Properties;

import com.ztesoft.net.eop.sdk.utils.UploadUtilc;
import com.ztesoft.net.framework.util.StringUtil;

public class FileConfigSetting {
	
	//文件存储方式
	public static String FILE_STORE_TYPE ="FTP";  
	
	//分布式文件配置属性
	
	public static String FAST_DFS_HOSTNAME =""; 
	public static String FAST_DFS_HOSTPORT ="80"; 
	public static String FAST_DFS_DOWNLOAD_HOSTPORT =""; 
	public static String FAST_DFS_PORT =""; 
	public static String FAST_DFS_POOL_SIZE =""; 
	public static String FAST_DFS_HEART_BEAT_TIME =""; 
	public static String FAST_DFS_SAVE_DIR =""; 
	
	
	
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
		FILE_STORE_TYPE = props.getProperty("file_store_type");
		if(StringUtil.isEmpty(FILE_STORE_TYPE))
			FILE_STORE_TYPE = "FTP";
		FAST_DFS_HOSTNAME = props.getProperty("fast_dfs_hostname");//192.45.47.200
		FAST_DFS_PORT = props.getProperty("fast_dfs_port");//22122
		FAST_DFS_POOL_SIZE = props.getProperty("fast_dfs_pool_size");//3
		FAST_DFS_HEART_BEAT_TIME = props.getProperty("fast_dfs_heart_beat_time");//15
		FAST_DFS_SAVE_DIR = props.getProperty("fast_dfs_save_dir");//15
		
		if(props.getProperty("fast_dfs_hostport") == null){
			FAST_DFS_HOSTPORT = "80";
		}else{
			FAST_DFS_HOSTPORT = props.getProperty("fast_dfs_hostport");
		}
		
		FAST_DFS_DOWNLOAD_HOSTPORT = props.getProperty("fast_dfs_down_hostname");
		if(StringUtil.isEmpty(FAST_DFS_DOWNLOAD_HOSTPORT))
			FAST_DFS_DOWNLOAD_HOSTPORT = FAST_DFS_HOSTNAME;
	}
}
