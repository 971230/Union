package com.ztesoft.soc.fastdfs;

import java.io.File;


public interface IDfsManager  {
	
	

	
	/**
	 * 
	 * @param fileBuff
	 * @param suffix 
	 * @return
	 */
	public String upload(byte[] fileBuff , String suffix);
	
	public String upload(File file , String suffix);
	
	public String getFileUrl(String fileId);
	
	/**
	 * 文件删除
	 * @param fileId 文件标识
	 * @return
	 */
	public boolean delete(String fileId);
	/**
	 * 读取/下载文件
	 * @param fileId
	 * @return
	 */
	public byte[] getFileById(String fileId);
	
	public byte[] downFileById(String fileId);
	
}
