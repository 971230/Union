package com.ztesoft.net.eop.sdk.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;

import com.ztesoft.net.eop.processor.MultipartRequestWrapper;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.blog.StoreProcesser;
import com.ztesoft.net.framework.blog.IStoreProcesser;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.StringUtil;

public class UploadUtil {
	private static Logger logger = Logger.getLogger(UploadUtil.class);
	private UploadUtil(){
		
	}
	/**
	 * 通过request上传文件 ，表单要声明  enctype="multipart/form-data" 
	 * @param name file控件的name
	 * @param subFolder 要上传的子文件夹
	 * @return
	 */
	public static String uploadUseRequest(String name,String subFolder){
		if( name==null) throw new IllegalArgumentException("file or filename object is null");
		if(subFolder == null)throw new IllegalArgumentException("subFolder is null");
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		if (!MultipartFormDataRequest.isMultipartFormData(request)) throw new RuntimeException("request data is not MultipartFormData"); 
	
		try {
			String encoding =EopSetting.ENCODING;
			if(StringUtil.isEmpty(encoding)){
				encoding = "UTF-8";
			}
			
			MultipartFormDataRequest mrequest  = new MultipartFormDataRequest(request,null,1000*1024*1024,MultipartFormDataRequest.COSPARSER,encoding);
			request = new MultipartRequestWrapper(request,mrequest);
			ThreadContextHolder.setHttpRequest(request);
			
			Hashtable files = mrequest.getFiles();
			UploadFile file = (UploadFile) files.get(name);
			if(file.getInpuStream() == null) return null;
			
			
			String fileFileName = file.getFileName();
			IStoreProcesser netBlog = StoreProcesser.getFileProcesser();
			return netBlog.upload(file.getInpuStream(), fileFileName, subFolder);
			
		} catch (UploadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 上传图片<br/>
	 * 图片会被上传至用户上下文的attacment文件夹的subFolder子文件夹中<br/>
	 * 
	 * @param file  图片file对象
	 * @param fileFileName 上传的图片原名
	 * @param subFolder  子文件夹名
	 * @return 
	 * @since 上传后的本地路径，如:fs:/attachment/goods/2001010101030.jpg<br/>
	 * 
	 */
	public static String upload(File file,String fileFileName,String subFolder ) {
		IStoreProcesser netBlog = StoreProcesser.getFileProcesser();
		String path =netBlog.upload(file, fileFileName, subFolder);
		return path;
	}
	
	
	/**
	 * 
	 * 文件上传<br/>
	 * 图片会被上传至用户上下文的attacment文件夹的subFolder子文件夹中<br/>
	 * 
	 * @param file  图片file对象
	 * @param fileFileName 上传的图片原名
	 * @param subFolder  子文件夹名
	 * @return 
	 * @since 上传后的本地路径，如:fs:/attachment/goods/2001010101030.jpg<br/>
	 * 
	 */
	public static String uploadFiles(File file,String fileFileName,String subFolder,String rootFolderName) {
		IStoreProcesser netBlog = StoreProcesser.getFileProcesser();
		return netBlog.upload(file, fileFileName, subFolder);
	}
	
	
	public static String replacePath(String path){
		if(StringUtil.isEmpty(path)) return path;
		IStoreProcesser netBlog = StoreProcesser.getFileProcesser();
		return  netBlog.replaceUrl(path);
	}
	
	private static String getEopContextPath(){
		String pathStr = EopContext.getContext() != null ? EopContext.getContext().getContextPath() : "/";
		return pathStr;
		
	}
	
	/**
	 * 上传图片并生成缩略图
	 * 图片会被上传至用户上下文的attacment文件夹的subFolder子文件夹中<br/>
	 * 
	 * @param file  图片file对象
	 * @param fileFileName 上传的图片原名
	 * @param subFolder  子文件夹名
	 * @param width 缩略图的宽
	 * @param height 缩略图的高
	 * @return 上传后的图版全路径，如:http://static.eop.com/user/1/1/attachment/goods/2001010101030.jpg<br/>
	 * 返回值为大小为2的String数组，第一个元素为上传后的原图全路径，第二个为缩略图全路径
	 */
	public static String[] upload(File file,String fileFileName,String subFolder,int width,int height ){
		
		IStoreProcesser netBlog = StoreProcesser.getFileProcesser();
		return netBlog.upload(file, fileFileName, subFolder, width, height);
	}
	
	
	
	
	/**
	 * 删除某个上传的文件
	 * @param filePath 文件全路径如：http://static.eop.com/user/1/1/attachment/goods/2001010101030.jpg
	 */
	public static void deleteFile(String filePath){
		IStoreProcesser netBlog = StoreProcesser.getFileProcesser();
		netBlog.del(filePath);		
	}
	
	
	
	/**
	 * 删除某个上传的文件
	 * @param filePath 文件全路径如：http://static.eop.com/user/1/1/attachment/goods/2001010101030.jpg
	 */
	public static void deleteFileAndThumb(String filePath){
		IStoreProcesser netBlog = StoreProcesser.getFileProcesser();
		netBlog.del(filePath);	
		netBlog.del(getThumbPath(filePath,"_thumbnail"));	
	}
	
	
	public static String getContextFolder(){
		return EopContext.getContext().getContextPath();
	}
	
	/**
	 * 转换图片的名称
	 * @param filePath  如：http://static.eop.com/user/1/1/attachment/goods/2001010101030.jpg
	 * @param shortName 
	 * @return
	 */
	public static  String getThumbPath(String filePath, String shortName) {
		String pattern = "(.*)([\\.])(.*)";
		String thumbPath = "$1" + shortName + "$2$3";

		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(filePath);
		if (m.find()) {
			String s = m.replaceAll(thumbPath);

			return s;
		}
		return null;
	}	
	
	/**
	 * 文件路径转换
	 * @param key 文件路径的字段名
	 * @param mapList list对象
	 */
	public static void convertFilePath(String key, List<Map> mapList) {
		if (mapList == null) return;
		
		for (Map map: mapList) {
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String keyTmp = (String) it.next();
				if (key.equalsIgnoreCase(keyTmp)) {
					String attUrl = replacePath((String)map.get(keyTmp));
					map.put(keyTmp, attUrl);
					break;
				}
			}
		}
	}
	
	public static void main(String[] args){
	 logger.info(getThumbPath("http://static.eop.com/user/1/1/attachment/goods/2001010101030.jpg","_thumbnail"));	
	 logger.info(getThumbPath("/user/1/1/attachment/goods/2001010101030.jpg","_thumbnail"));	
	 
	 List lst = new ArrayList();
	 Map  map = new HashMap();
	 map.put("a", "fs:");
	 map.put("b", 2);
	 map.put("c", 3);
	 lst.add(map);
	 map = new HashMap();
	 map.put("a", "fs:");
	 map.put("b", 22);
	 map.put("c", 33);
	 lst.add(map);
	 
	 
	 convertFilePath("a", lst);
	 
	 lst.size();
	}
	
}
