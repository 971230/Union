package com.ztesoft.net.mall.core.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.cms.page.file.UploadTools;
import com.ztesoft.net.app.base.core.service.ISettingService;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.context.FileConfigSetting;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.blog.StoreProcesser;
import com.ztesoft.net.framework.blog.IStoreProcesser;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.net.framework.image.IThumbnailCreator;
import com.ztesoft.net.framework.image.ThumbnailCreatorFactory;
import com.ztesoft.net.framework.util.FileBaseUtil;
import com.ztesoft.net.framework.util.ImageMagickMaskUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.service.IGoodsAlbumManager;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.plugin.standard.album.Thumb;
import com.ztesoft.net.sqls.SF;

/**
 * 商品相册管理
 * @author kingapex
 * 2010-2-21上午12:40:05
 */
public final class GoodsAlbumManager extends BaseSupport<Goods> implements IGoodsAlbumManager {
	private IGoodsManager goodsManager;
	/**
	 * 删除指定的图片<br>
	 * 将本地存储的图片路径替换为实际硬盘路径<br>
	 * 不会删除远程服务器图片
	 */
	@Override
	public void delete(String photoName) {
			 photoName  =UploadUtil.replacePath(photoName);
			 photoName =photoName.replaceAll(EopSetting.IMG_SERVER_DOMAIN , EopSetting.IMG_SERVER_PATH);
			 FileBaseUtil.delete(photoName);
			 
			 FileBaseUtil.delete(UploadUtil.getThumbPath(photoName, "_thumbnail"));
			 FileBaseUtil.delete(UploadUtil.getThumbPath(photoName, "_small"));
			 FileBaseUtil.delete(UploadUtil.getThumbPath(photoName, "_big"));
			 
			 ICacheUtil util = (ICacheUtil)SpringContextHolder.getBean("cacheUtil");
			 
			 String p_filePath = util.getConfigInfo("CT_ACCESS_PATH");
			 
			 String filePath = p_filePath.replace("/temp", "");
			 
			 
			 String img = UploadUtil.getThumbPath(photoName, "");
			 String thumbnail_img = UploadUtil.getThumbPath(photoName, "_thumbnail");
			 String small_img = UploadUtil.getThumbPath(photoName, "_small");
			 String big_img = UploadUtil.getThumbPath(photoName, "_big");
			 
			 img=img.replaceAll(EopSetting.IMG_SERVER_PATH,"");
			 thumbnail_img=thumbnail_img.replaceAll(EopSetting.IMG_SERVER_PATH,"");
			 small_img=small_img.replaceAll(EopSetting.IMG_SERVER_PATH,"");
			 big_img=big_img.replaceAll(EopSetting.IMG_SERVER_PATH,"");
			 
			 String store_type = FileConfigSetting.FILE_STORE_TYPE;
			 if("FTP".equals(store_type.toUpperCase())){ //FTP文件
				 UploadTools.delete(filePath+img);
				 UploadTools.delete(filePath + thumbnail_img);
				 UploadTools.delete(filePath+small_img);
				 UploadTools.delete(filePath+big_img);
			}
	}
	
	@Override
	public void delete(String[] goodsid){
		String id_str = StringUtil.arrayToString(goodsid, ",");
		String sql = SF.goodsSql("GET_IMAGE_FILE_Y_ID") + " and goods_id in (" + id_str + ")";
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
			IStoreProcesser netBlog = StoreProcesser.getFileProcesser();
			path[0] = netBlog.upload(file, fileFileName, "goods");
			path[0] = netBlog.replaceUrl(path[0]);
			String watermark = settingService.getSetting("photo", "watermark");
			String marktext = settingService.getSetting("photo", "marktext");
			String markpos = settingService.getSetting("photo", "markpos");
			String markcolor = settingService.getSetting("photo", "markcolor");
			String marksize = settingService.getSetting("photo", "marksize");
		
			marktext= StringUtil.isEmpty(marktext)? "水印文字":marktext;
			marksize= StringUtil.isEmpty(marksize)? "12":marksize;
			
			if(watermark!=null && watermark.equals("on")){
				ImageMagickMaskUtil magickMask = new ImageMagickMaskUtil();
				magickMask.mask(path[0], marktext,markcolor,Integer.valueOf(marksize),Integer.valueOf(   markpos ),
					EopSetting.EOP_PATH + "/fonts/st.TTF");	
				//"d:/webhome/eop/fonts/st.TTF");
			}
		} 
		return path;
	}


	public static String getContextFolder(){
		return EopContext.getContext().getContextPath();
	}

	/**
	 * 生成商品缩略图<br>
	 * 传递的图片地址中包含有静态资源服务器地址，替换为本地硬盘目录，然后生成。<br>
	 * 如果是公网上的静态资源则不处理
	 */
	@Override
	public String createThumb(String filepath,String thumbName,int thumbnail_pic_width,int thumbnail_pic_height) {
		String thump_path = thumbName;
		String  imgDomain ="http://static.enationsoft.com/attachment/"; // EopSetting.IMG_SERVER_DOMAIN.startsWith("http")? EopSetting.IMG_SERVER_DOMAIN:"http://"+EopSetting.IMG_SERVER_DOMAIN;
		 if(filepath!=null && !filepath.startsWith(imgDomain  )){
			filepath=filepath.replaceAll( EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH );
			thumbName=thumbName.replaceAll( EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH );
			IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator( filepath, thumbName);
			thumbnailCreator.resize(thumbnail_pic_width, thumbnail_pic_height);		
			thump_path = thumbnailCreator.getDestFile();//获取目标文件路径，用户后续保存入库
			String file_name = thumbName.substring(thumbName.lastIndexOf("/")+1);
			String store_type = FileConfigSetting.FILE_STORE_TYPE;
			if("FTP".equals(store_type.toUpperCase()))//分布式文件 
				UploadTools.syToFtpFile(file_name, thumbName,"/attachment/goods/");
		 }
		 return thump_path;
	}
	
	@Override
	public int getTotal() {
		return this.goodsManager.list().size();
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
		
		List<Map> goodsList = this.goodsManager.list();
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
					this.createThumb1(imgFile, thumbName, thumbnail_pic_width, thumbnail_pic_height);// 此处需要改造完善
					
					Thumb thumb = new Thumb();
					thumb.setSeq(baseDaoSupport.getSequences("ES_GOODS_THUMBIMGS_SEQ"));
					thumb.setEs_goods_id((String)goods.get("goods_id"));
					thumb.setThumb_path(imgFile);
					thumb.setThumb_type("_thumbnail");
					thumb.setWidth(String.valueOf(thumbnail_pic_width));
					thumb.setHeight(String.valueOf(thumbnail_pic_height));
					thumb.setFile_path(imgFile);
					thumb.setCreate_time(DBTUtil.current());
					thumb.setSource_from(ManagerUtils.getSourceFrom());
					this.saveThumb(thumb);
					
					// 生成小图
					thumbName = UploadUtil.getThumbPath(imgFile, "_small");
					createThumb1(imgFile,thumbName, detail_pic_width, detail_pic_height); // 此处需要改造完善
					
					thumb = new Thumb();
					thumb.setSeq(baseDaoSupport.getSequences("ES_GOODS_THUMBIMGS_SEQ"));
					thumb.setEs_goods_id((String)goods.get("goods_id"));
					thumb.setThumb_path(imgFile);
					thumb.setThumb_type("_small");
					thumb.setWidth(String.valueOf(detail_pic_width));
					thumb.setHeight(String.valueOf(detail_pic_height));
					thumb.setFile_path(imgFile);
					thumb.setCreate_time(DBTUtil.current());
					thumb.setSource_from(ManagerUtils.getSourceFrom());
					this.saveThumb(thumb);
					
					// 生成大图
					thumbName =UploadUtil.getThumbPath(imgFile, "_big");
					createThumb1(imgFile,thumbName,album_pic_width , album_pic_height);// 此处需要改造完善
					
					thumb = new Thumb();
					thumb.setSeq(baseDaoSupport.getSequences("ES_GOODS_THUMBIMGS_SEQ"));
					thumb.setEs_goods_id((String)goods.get("goods_id"));
					thumb.setThumb_path(imgFile);
					thumb.setThumb_type("_big");
					thumb.setWidth(String.valueOf(album_pic_width));
					thumb.setHeight(String.valueOf(album_pic_height));
					thumb.setFile_path(imgFile);
					thumb.setCreate_time(DBTUtil.current());
					thumb.setSource_from(ManagerUtils.getSourceFrom());
					this.saveThumb(thumb);
				}
			}
			
		}
		
	}
	
	private void saveThumb1(Thumb thumb){
		TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(thumb) {
			@Override
			public ZteResponse execute(ZteRequest inst) {
				try {
					Thumb thumbn = (Thumb)inst;
					//插入新数据
					baseDaoSupport.insert("es_goods_thumbimgs", thumbn);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return new ZteResponse();
			}
		});		
	}
	
	private String getSettingValue(String code){
		return  settingService.getSetting("photo", code);
	}
	
	
	/**
	 * 创建一个缩略图
	 * @param filepath
	 * @param thumbName
	 * @param thumbnail_pic_width
	 * @param thumbnail_pic_height
	 */
	private void createThumb1(String filepath,String thumbName,int thumbnail_pic_width,int thumbnail_pic_height){
		 if(!StringUtil.isEmpty(filepath)){
			String ctx =  EopContext.getContext().getContextPath();
			filepath=filepath.replaceAll( EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_PATH +ctx);
			thumbName=thumbName.replaceAll( EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_PATH +ctx);
			IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator( filepath, thumbName);
			thumbnailCreator.resize(thumbnail_pic_width, thumbnail_pic_height);	 
		 }		
	}
 
	
	@Override
	public void saveThumb(Thumb thumb){
		thumb.setSeq(baseDaoSupport.getSequences("ES_GOODS_THUMBIMGS_SEQ"));
		thumb.setFile_path(thumb.getThumb_path());
		thumb.setCreate_time(DBTUtil.current());
		thumb.setSource_from(ManagerUtils.getSourceFrom());
		baseDaoSupport.insert("es_goods_thumbimgs", thumb);
	}
	
	public ISettingService getSettingService() {
		return settingService;
	}

	public void setSettingService(ISettingService settingService) {
		this.settingService = settingService;
	}

	public IGoodsManager getGoodsManager() {
		return goodsManager;
	}

	public void setGoodsManager(IGoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}
	
}
