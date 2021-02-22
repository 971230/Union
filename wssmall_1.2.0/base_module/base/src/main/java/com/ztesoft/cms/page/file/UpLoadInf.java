package com.ztesoft.cms.page.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class UpLoadInf{
	public UploadTools uploadTools = new UploadTools();
	public String file_foder ="";
	public String access_path_folder ="";
	public List ftpConfig = new ArrayList();
	public final String seperator = "/";
	
	
	public UpLoadInf() {
		this.file_foder  = uploadTools.getConfigFile_foder();
		this.ftpConfig  = uploadTools.getFtpConfig();
		this.access_path_folder =  uploadTools.getConfigAccessFile_foder();
	}
	public Map upLoad(Object object) throws Exception {
		return new HashMap();
	}
	
	public Map uploadIndexFile(Object object) throws Exception {
		return new HashMap();
	}
	
	public void downLoad() throws Exception{
		
	}
	
	public void delete(String pathname) throws Exception{
		
	}
	
	
}
