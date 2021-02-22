package com.ztesoft.cms.page.file;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author wu.i 文件上传工具类
 * 
 */
public class FileUpLoadMgr {
	
	/**
	 * 删除文件
	 * 
	 * @param pathname
	 * @return
	 * @throws Exception
	 */
	public Map deleteFile(String local_path_name,String ftp_path_name) throws Exception {
		Map retMap = new HashMap();
		UpLoadInf upLoadInf = new FileUpLoadImpl();
		upLoadInf.delete(local_path_name);
		try {
			upLoadInf = new FtpUpLoadImpl();
			upLoadInf.delete(ftp_path_name);
		} catch (Exception e) {
			try{
				upLoadInf = new SftpUpLoadImpl();
				upLoadInf.delete(ftp_path_name);
			}catch (Exception ee) {
				throw new Exception(ee);
			}
			
		}
		return retMap;
	}
	
}
