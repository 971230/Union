package com.ztesoft.net.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.ztesoft.net.framework.database.Page;

public interface IVOX2WAVManager {

	public void voxConvert(String inFile, String outFile, int voxFormat,int voxRate, int bit_rate) throws Exception;
	
	public void downLoad(String filePath, HttpServletResponse response, boolean isOnLine) throws Exception;

	public String getUserPhone(String userid) throws Exception;
	
	public Map getParams(String cf_id) throws Exception;

	public Page queryCalllog(String order_id,int pageNo, int pageSize);
	
	public Page queryIntentCalllog(String order_id,int pageNo, int pageSize);
	
	public String playVoiceFile() throws Exception;

	public void upload(String voxFileName,String wavFileName);
	
	public String queryShipTel(String order_id);
	
	public Map qryCallFileIntent(String file_id);
}
