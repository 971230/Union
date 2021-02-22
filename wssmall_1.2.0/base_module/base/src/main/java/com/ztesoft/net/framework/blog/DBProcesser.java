package com.ztesoft.net.framework.blog;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.utils.UploadUtils;
import com.ztesoft.soc.fastdfs.IDfsManager;

/**
 * 
 * @author wu.i
 * 2014-06-03
 * BLOB/CLOB存储处理器
 *
 */
public class DBProcesser implements IStoreProcesser {

	@Resource
	IDfsManager dfsManager;
	@Override
	public String getFileUrl(String content){
		return content;
	}
	
	@Override
	public void del(String fileId) {
		dfsManager.delete(fileId);
	}
	
	@Override
	public void delLocaoFile(String path) {
		
	}

	public static final DBProcesser inst = new DBProcesser();
	
	public static IStoreProcesser getNetBlog(){
		return inst;
	}

	@Override
	public String[] upload(File file, String fileFileName, String subFolder,
			int width, int height) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String upload(File file, String fileFileName, String subFolder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String upload(InputStream in, String fileFileName, String subFolder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String upload(byte[] in, String fileFileName, String subFolder) {
		try {
			return new String(in,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String replaceUrl(String path) {
		return path.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_DOMAIN+UploadUtils.getEopContextPath());
	}

	@Override
	public String getStroeType() {
		return "DB";
	}


}