package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.app.base.core.model.Partner;
import com.ztesoft.net.app.base.core.service.ISettingService;
import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.image.IThumbnailCreator;
import com.ztesoft.net.framework.image.ImageRuntimeException;
import com.ztesoft.net.framework.image.ThumbnailCreatorFactory;
import com.ztesoft.net.framework.util.DateUtil;
import com.ztesoft.net.framework.util.FileBaseUtil;
import com.ztesoft.net.framework.util.ImageMagickMaskUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.service.IPartnerAlbumManager;
import com.ztesoft.net.mall.core.service.IPartnerManager;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分销商相册管理
 * @author kingapex
 * 
 */
public final class PartnerAlbumManager extends BaseSupport<Partner> implements IPartnerAlbumManager {
	
	private IPartnerManager partnerManager;
	/**
	 * 在修改时处理图片的信息
	 */
	@Override
	public String onFillGoodsEditInput(Map partners, HttpServletRequest request) {
		
		String contextPath = request.getContextPath();
		//设置需要传递到页面的数据	
		FreeMarkerPaser freeMarkerPaser =FreeMarkerPaser.getInstance();
		
		// 关于图片的处理
 		String image_default= null;
		if (partners.get("image_file") != null && !"".equals(partners.get("image_file"))) {
			String image_file = partners.get("image_file").toString();
			String[] thumbnail_images = image_file.split(",");

			image_default= (String)partners.get("image_default");
			if(!StringUtil.isEmpty(image_default)){
				image_default = UploadUtil.replacePath(image_default);
				
			}
			for(int i=0;i<thumbnail_images.length;i++){
				thumbnail_images[i]=UploadUtil.replacePath(thumbnail_images[i]);
			}
			
			freeMarkerPaser.putData("ctx", contextPath);
			freeMarkerPaser.putData("image_default", image_default );
			freeMarkerPaser.putData("thumbnail_images", thumbnail_images );	
		}
		
		freeMarkerPaser.setPageName("partnerAlbum");
		freeMarkerPaser.putData("partners",partners );
		freeMarkerPaser.setClz(PartnerAlbumManager.class);
		String html = freeMarkerPaser.proessPageContent();
		request.setAttribute("partnerAlbum", html);
		return html;
	}
/**
 * 在添加时处理图片
 */
	@Override
	public String onFillGoodsAddInput(HttpServletRequest request) {
		FreeMarkerPaser freeMarkerPaser =FreeMarkerPaser.getInstance();
		Map partners=new HashMap();
		freeMarkerPaser.setPageName("partnerAlbum");		
		freeMarkerPaser.putData("image_default", null );
		freeMarkerPaser.putData("thumbnail_images", null );	
		freeMarkerPaser.putData("partners",partners );
		freeMarkerPaser.setClz(PartnerAlbumManager.class);//
		String html = freeMarkerPaser.proessPageContent();
		request.setAttribute("partnerAlbum", html);
		return html;
	}
	private String getThumbPath(String filePath,String shortName){
		return UploadUtil.getThumbPath(filePath, shortName);
	}
	/**
	 * 生成商品缩略图<br>
	 * 传递的图片地址中包含有静态资源服务器地址，替换为本地硬盘目录，然后生成。<br>
	 * 如果是公网上的静态资源则不处理
	 */
	@Override
	public void createThumb1(String filepath,String thumbName,int thumbnail_pic_width,int thumbnail_pic_height) {
		
		String  imgDomain ="http://static.enationsoft.com/attachment/"; // EopSetting.IMG_SERVER_DOMAIN.startsWith("http")? EopSetting.IMG_SERVER_DOMAIN:"http://"+EopSetting.IMG_SERVER_DOMAIN;
		 if(filepath!=null && !filepath.startsWith(imgDomain  )){
			filepath=filepath.replaceAll( EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH );
			thumbName=thumbName.replaceAll( EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH );
			IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator( filepath, thumbName);
			thumbnailCreator.resize(thumbnail_pic_width, thumbnail_pic_height);				 
		 }
	  
	}
	@Override
	public void proessPhoto(String[] picnames,Map partners) {
		
		//if(true)
		//	return;

		
		if (picnames == null) {
		//	goods.put("image_file",  EopSetting.IMG_SERVER_DOMAIN+"/images/no_picture.jpg");
		//	goods.put("image_default", EopSetting.IMG_SERVER_DOMAIN+"/images/no_picture.jpg");
			return;
		}
		
		String imgsString = "";
		for (int i = 0; i < picnames.length; i++) {
			String filepath = picnames[i];
			 
			if (i != 0) {
				imgsString += ",";
			}
			imgsString += picnames[i];

			
			int thumbnail_pic_width =107;
			int thumbnail_pic_height =107;
			int detail_pic_width=320;
			int detail_pic_height=240;
			int  album_pic_width =550;
			int album_pic_height=412;
			
			/**
			 * 由配置中获取各种图片大小
			 */
			try{
				thumbnail_pic_width =Integer.valueOf(getSettingValue("thumbnail_pic_width").toString());
				thumbnail_pic_height =Integer.valueOf(getSettingValue("thumbnail_pic_height").toString());
				
				detail_pic_width =Integer.valueOf(getSettingValue("detail_pic_width").toString());			
				detail_pic_height =Integer.valueOf(getSettingValue("detail_pic_height").toString());
				
				album_pic_width =Integer.valueOf(getSettingValue("album_pic_width").toString());			
				album_pic_height =Integer.valueOf(getSettingValue("album_pic_height").toString());	
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
			
			String thumbName = null;
 
			try {
				
				// 生成缩略图
//				thumbName = getThumbPath(filepath, "_thumbnail");
//				this.createThumb1(filepath,thumbName, thumbnail_pic_width, thumbnail_pic_height);
//				
//
//				// 生成小图
//				thumbName = getThumbPath(filepath, "_small");
//				this.createThumb1(filepath,thumbName, detail_pic_width, detail_pic_height);
//				
//
//				// 生成大图
//				thumbName = getThumbPath(filepath, "_big");
//				this.createThumb1(filepath,thumbName,album_pic_width , album_pic_height);
				
			} catch (ImageRuntimeException e) {
				e.printStackTrace();
			}

		}
		//页面中传递过来的图片地址为:http://<staticserver>/<image path>如:
		//http://static.enationsoft.com/attachment/goods/1.jpg
		//存在库中的为fs:/attachment/goods/1.jpg
//		imgsString  =UploadUtil.replacePath(imgsString);
		imgsString= imgsString.replaceAll(EopSetting.IMG_SERVER_DOMAIN+EopContext.getContext().getContextPath(), EopSetting.FILE_STORE_PREFIX);
		partners.put("image_file", imgsString);
		String str []=(String []) partners.get("image_default");
		if(str!=null){
			String image_default = str[0];
			if(image_default!=null){
				image_default= image_default.replaceAll(EopSetting.IMG_SERVER_DOMAIN+EopContext.getContext().getContextPath(), EopSetting.FILE_STORE_PREFIX);
				partners.put("image_default", image_default);
			}
		}
		
	}
	
	/**
	 * 删除指定的图片<br>
	 * 将本地存储的图片路径替换为实际硬盘路径<br>
	 * 不会删除远程服务器图片
	 */
	@Override
	public void delete(String photoName) {
		 if(photoName!=null && !photoName.startsWith( "http://wssmall/attachment/") ){ //add by wui
					 photoName  =UploadUtil.replacePath(photoName);
					 photoName =photoName.replaceAll(EopSetting.IMG_SERVER_DOMAIN , EopSetting.IMG_SERVER_PATH);
					 FileBaseUtil.delete(photoName);
					 FileBaseUtil.delete(UploadUtil.getThumbPath(photoName, "_thumbnail"));
					 FileBaseUtil.delete(UploadUtil.getThumbPath(photoName, "_small"));
					 FileBaseUtil.delete(UploadUtil.getThumbPath(photoName, "_big"));
		 }
	}
	
	@Override
	public void delete(String[] goodsid){
		String id_str = StringUtil.arrayToString(goodsid, ",");
		String sql = "select image_file from partner where goods_id in ("+ id_str +")";
		List<Map> goodsList  = this.baseDaoSupport.queryForList(sql);
		for(Map goods:goodsList){
			String name =(String)goods.get("image_file");
			if(name!=null &&  !name.equals("")){
				String[] pname = name.split(",");
				for(String n:pname)
					this.delete(n);
			}
		}  
		
	}
	private ISettingService settingService ;
	/**
	 * 上传商品图片<br>
	 * 生成商品图片名称，并且在用户上下文的目录里生成图片<br>
	 * 返回以静态资源服务器地址开头+用户上下文路径的全路径<br>
	 * 在保存入数据库时应该将静态资源服务器地址替换为fs:开头，并去掉上下文路径,如:<br>
	 * http://static.enationsoft.com/user/1/1/attachment/goods/1.jpg，存库应该为:
	 * fs:/attachment/goods/1.jpg
	 */
	@Override
	public String[] upload(File file, String fileFileName) {
		String fileName = null;
		String filePath = "";

		String[] path = new String[2];
		
		if (file != null && fileFileName != null) {
			String ext = FileBaseUtil.getFileExt(fileFileName);
			fileName = DateUtil.toString(new Date(), "yyyyMMddHHmmss") + StringUtil.getRandStr(4) + "." + ext;
			filePath = EopSetting.IMG_SERVER_PATH + getContextFolder()+ "/attachment/goods/";
			path[0] = EopSetting.IMG_SERVER_DOMAIN + getContextFolder()+ "/attachment/goods/"+fileName;
			filePath += fileName;
			FileBaseUtil.createFile(file, filePath);
			
			String watermark = settingService.getSetting("photo", "watermark");
			String marktext = settingService.getSetting("photo", "marktext");
			String markpos = settingService.getSetting("photo", "markpos");
			String markcolor = settingService.getSetting("photo", "markcolor");
			String marksize = settingService.getSetting("photo", "marksize");
		
			marktext= StringUtil.isEmpty(marktext)? "水印文字":marktext;
			marksize= StringUtil.isEmpty(marksize)? "12":marksize;
			
			
			if(watermark!=null && watermark.equals("on")){
				ImageMagickMaskUtil magickMask = new ImageMagickMaskUtil();
				magickMask.mask(filePath, marktext,markcolor,Integer.valueOf(marksize),Integer.valueOf(   markpos ),
					EopSetting.EOP_PATH + "/fonts/st.TTF");	
				//"d:/webhome/eop/fonts/st.TTF");
			}
		} 
		return path;
	}

	public static String getContextFolder(){
		return EopContext.getContext().getContextPath();
	}


	
	@Override
	public int getTotal() {
		return this.partnerManager.list().size();
	}

	@Override
	public void recreate(int start,int end) {
		
		int thumbnail_pic_width =107;
		int thumbnail_pic_height =107;
		int detail_pic_width=320;
		int detail_pic_height=240;
		int  album_pic_width =550;
		int album_pic_height=412;
		
		/**
		 * 由配置中获取各种图片大小
		 */
		try{
			thumbnail_pic_width =Integer.valueOf(getSettingValue("thumbnail_pic_width").toString());
			thumbnail_pic_height =Integer.valueOf(getSettingValue("thumbnail_pic_height").toString());
			
			detail_pic_width =Integer.valueOf(getSettingValue("detail_pic_width").toString());			
			detail_pic_height =Integer.valueOf(getSettingValue("detail_pic_height").toString());
			
			album_pic_width =Integer.valueOf(getSettingValue("album_pic_width").toString());			
			album_pic_height =Integer.valueOf(getSettingValue("album_pic_height").toString());	
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		
		List<Map> goodsList = this.partnerManager.list();
		for(int i=start-1;i<end;i++){
			Map goods = goodsList.get(i);
			String imageFile = (String)goods.get("image_file"); //读取此商品图片
			if(imageFile!=null){
				String[] imgFileAr = imageFile.split(",");
				logger.info("Create thumbnail image, the index:" + (i + 1));
				for(String imgFile:imgFileAr){
					logger.info("Image file:" + imgFile);
					//生成缩略图
					String thumbName = UploadUtil.getThumbPath(imgFile, "_thumbnail");
					this.createThumb1(imgFile, thumbName, thumbnail_pic_width, thumbnail_pic_height);
					
					// 生成小图
					thumbName = UploadUtil.getThumbPath(imgFile, "_small");
					createThumb1(imgFile,thumbName, detail_pic_width, detail_pic_height);
					
					
					// 生成大图
					thumbName =UploadUtil.getThumbPath(imgFile, "_big");
					createThumb1(imgFile,thumbName,album_pic_width , album_pic_height);
					
				}
			}
			
		}
		
	}
	
	
	private String getSettingValue(String code){
		return  settingService.getSetting("photo", code);
	}
	
	
	
	
	public ISettingService getSettingService() {
		return settingService;
	}

	public void setSettingService(ISettingService settingService) {
		this.settingService = settingService;
	}

	public IPartnerManager getPartnerManager() {
		return partnerManager;
	}

	public void setPartnerManager(IPartnerManager partnerManager) {
		this.partnerManager = partnerManager;
	}

	

	
}
