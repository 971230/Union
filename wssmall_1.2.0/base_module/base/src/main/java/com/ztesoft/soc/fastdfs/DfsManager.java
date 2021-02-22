package com.ztesoft.soc.fastdfs;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztesoft.net.eop.sdk.context.FileConfigSetting;

public class DfsManager implements IDfsManager {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private DfsManager(){
		
	}
	
	
	private FileServerImpl s =null;
/*	{
		try {
			s=new FileServerImpl(FileConfigSetting.FAST_DFS_HOSTNAME,
					Integer.parseInt(FileConfigSetting.FAST_DFS_PORT),
					Integer.parseInt(FileConfigSetting.FAST_DFS_POOL_SIZE),
					Integer.parseInt(FileConfigSetting.FAST_DFS_HEART_BEAT_TIME));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static DfsManager inst = new DfsManager() ;
	
	public static  DfsManager getInst(){
		return inst ;
	}*/
	
	private FileServerImpl getFileSer(){
		
		if(null == s){
			try {
				s=new FileServerImpl(FileConfigSetting.FAST_DFS_HOSTNAME,
						Integer.parseInt(FileConfigSetting.FAST_DFS_PORT),
						Integer.parseInt(FileConfigSetting.FAST_DFS_POOL_SIZE),
						Integer.parseInt(FileConfigSetting.FAST_DFS_HEART_BEAT_TIME),
						FileConfigSetting.FAST_DFS_SAVE_DIR);
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		return s;
	}
	
	/**
	 * 
	 * @param fileBuff
	 * @param suffix 
	 * @return
	 */
	@Override
	public String upload(byte[] fileBuff , String suffix){
//		logger.info("upload--------->") ;
		logger.info("upload--------->")  ;
		try {
			String fileId = this.getFileSer().uploadFile(fileBuff, suffix) ;
//			logger.info("upload--------->"+fileId) ;
			logger.info("upload--------->"+fileId)  ;
			return fileId ;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		}
	}
	
	/**
	 * 
	 * @param fileBuff
	 * @param suffix 
	 * @return
	 */
	@Override
	public String upload(File fileBuff, String suffix){
//		logger.info("upload--------->") ;
		logger.info("upload--------->")  ;
		try {
			String fileId = this.getFileSer().uploadFile(fileBuff, suffix) ;
//			logger.info("upload--------->"+fileId) ;
			logger.info("upload--------->"+fileId)  ;
			return fileId ;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		}
	}
	
	
	@Override
	public String getFileUrl(String fileId){
		
		if(StringUtils.isEmpty(fileId))
			return "" ;
		
//		group1/M00/00/57/Ci0vqFLCJ8Xy11F-ADA66_jzVpA608.pdf
		String address ="";// CrmParamsConfig.getInstance().getParamValue("FAST_DFS_NGINX_ADDR")  ;
		fileId = fileId.startsWith("group") ? "/"+fileId : fileId ;
		fileId = address+fileId ;
		logger.info("getFileUrl--------->"+fileId)  ;
//		logger.info("getFileUrl--------->"+fileId) ;
		return fileId ;
	}
	public static void main(String[] a ){
//		logger.info(getFileUrl("group1/M00/00/57/Ci0vqFLCJ8Xy11F-ADA66_jzVpA608.pdf")) ;
	}
	
	/**
	 * 文件删除
	 * @param fileId 文件标识
	 * @return
	 */
	@Override
	public boolean delete(String fileId){
//		logger.info("delete--------->"+fileId) ;
		logger.info("delete--------->"+fileId)  ;
		try {
			if(fileId.startsWith("http://"+FileConfigSetting.FAST_DFS_HOSTNAME+":"+FileConfigSetting.FAST_DFS_HOSTPORT+"/")){
				fileId = fileId.replace("http://"+FileConfigSetting.FAST_DFS_HOSTNAME+":"+FileConfigSetting.FAST_DFS_HOSTPORT+"/","");
			}
			boolean result = this.getFileSer().deleteFile(fileId) ;
			logger.info("delete--------->"+result)  ;
//			logger.info("delete--------->"+result) ;
			return result ;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		}
	}
	
	/**
	 * 读取/下载文件
	 * @param fileId
	 * @return
	 */
	@Override
	public byte[] getFileById(String fileId){
//		logger.info("getFileById--------->"+fileId) ;
		logger.info("getFileById--------->"+fileId)  ;
		try {
			FileServerImpl fi = this.getFileSer();
			return fi.getFileByID(fileId) ;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		}
	}
	
	@Override
	public byte[] downFileById(String fileId){
//		logger.info("getFileById--------->"+fileId) ;
		logger.info("downFileById--------->"+fileId)  ;
		try {
			return this.getFileSer().downFileByID(fileId) ;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		}		
	}
	
}
