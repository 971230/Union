package com.ztesoft.net.eop.sdk.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.context.FileConfigSetting;
import com.ztesoft.net.framework.util.StringUtil;

public class UploadUtilc {
	
	private UploadUtilc(){
		
	}
	public static String replacePath(String path){
		if(StringUtil.isEmpty(path)) return path;
		String store_type = FileConfigSetting.FILE_STORE_TYPE;
		if("DFS".equals(store_type.toUpperCase())){ //分布式文件 
			return  path; //add by wui此处需要DFS处理
		}else if("FTP".equals(store_type.toUpperCase())){ //FTP文件
			return  path.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_DOMAIN+getEopContextPath() );
		}
		return path;
		
	}
	
	private static String getEopContextPath(){
		String pathStr = EopContext.getContext() != null ? EopContext.getContext().getContextPath() : "/";
		return pathStr;
		
	}
	
	public static InputStream getResourceAsStream(String resource) {
		try {
			File file = new File(System.getProperty("CONFIG")+resource);
			InputStream stream = null;
			if(file.exists()){
				FileInputStream fileIS  = new FileInputStream(file);
				stream = fileIS;
			}
			if(null == stream){
				String stripped = resource.startsWith("/") ? resource.substring(1)
						: resource;
				stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(stripped);
			}
			return stream;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
//	
//	/**
//	 * 上传图片并生成缩略图
//	 * 图片会被上传至用户上下文的attacment文件夹的subFolder子文件夹中<br/>
//	 * 
//	 * @param file  图片file对象
//	 * @param fileFileName 上传的图片原名
//	 * @param subFolder  子文件夹名
//	 * @param width 缩略图的宽
//	 * @param height 缩略图的高
//	 * @return 上传后的图版全路径，如:http://static.eop.com/user/1/1/attachment/goods/2001010101030.jpg<br/>
//	 * 返回值为大小为2的String数组，第一个元素为上传后的原图全路径，第二个为缩略图全路径
//	 */
//	public static String[] upload(File file,String fileFileName,String subFolder,int width,int height ){
//		if(file==null || fileFileName==null) throw new IllegalArgumentException("file or filename object is null");
//		if(subFolder == null)throw new IllegalArgumentException("subFolder is null");
//		String fileName = null;
//		String filePath = "";
//		String [] path = new String[2];
// 
//		String ext = FileUtil.getFileExt(fileFileName);
//		fileName = DateUtil.toString(new Date(), "yyyyMMddHHmmss") + StringUtil.getRandStr(4) + "." + ext;
//		
//		filePath =  EopSetting.IMG_SERVER_PATH+ getContextFolder()+ "/attachment/";
//		if(subFolder!=null){
//			filePath+=subFolder +"/";
//		}
//		
//		path[0]  = EopSetting.IMG_SERVER_DOMAIN+ getContextFolder()+ "/attachment/" +(subFolder==null?"":subFolder)+ "/"+fileName;
//		filePath += fileName;
//		FileUtil.createFile(file, filePath);
//		String thumbName= getThumbPath(filePath,"_thumbnail");
//		 
//		IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator( filePath, thumbName);
//		thumbnailCreator.resize(width, height);	
//		path[1] = getThumbPath(path[0], "_thumbnail");
//		return path;
//	}
//	
//	
//	
//	
//	/**
//	 * 删除某个上传的文件
//	 * @param filePath 文件全路径如：http://static.eop.com/user/1/1/attachment/goods/2001010101030.jpg
//	 */
//	public static void deleteFile(String filePath){
//		filePath =filePath.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
//		FileUtil.delete(filePath);		
//	}
//	
//	
//	
//	/**
//	 * 删除某个上传的文件
//	 * @param filePath 文件全路径如：http://static.eop.com/user/1/1/attachment/goods/2001010101030.jpg
//	 */
//	public static void deleteFileAndThumb(String filePath){
//		filePath =filePath.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
//		FileUtil.delete(filePath);		
//		FileUtil.delete(getThumbPath(filePath,"_thumbnail"));		
//	}
//	
//	
//	private static String getContextFolder(){
//		 
//		return EopContext.getContext().getContextPath();
//	}
//	
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
//	
//	public static void main(String[] args){
//	 logger.info(getThumbPath("http://static.eop.com/user/1/1/attachment/goods/2001010101030.jpg","_thumbnail"));	
//	 logger.info(getThumbPath("/user/1/1/attachment/goods/2001010101030.jpg","_thumbnail"));	
//	}
	
}
