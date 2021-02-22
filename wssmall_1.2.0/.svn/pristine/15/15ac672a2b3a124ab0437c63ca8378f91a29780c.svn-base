package com.ztesoft.net.framework.blog;

import java.io.File;
import java.io.InputStream;


/**
 * 
 * @author wu.i
 * 2014-06-03
 * 数据存储处理器
 *
 */
public interface IStoreProcesser {

	//获取文件内容
	public String getFileUrl(String fileId);
	//删除文件内容
	public void del(String fileId);
	public void delLocaoFile(String path);
	//更新文件内容
	public String[] upload(File file,String fileFileName,String subFolder,int width,int height );
	public String upload(File file,String fileFileName,String subFolder);
	public String upload(InputStream in ,String fileFileName,String subFolder);
	public String upload(byte [] in, String fileFileName, String subFolder);
	
	public String replaceUrl(String path);
	
	public String getStroeType();
	
}
