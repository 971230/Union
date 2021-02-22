package com.ztesoft.net.mall.plugin.standard.album;

import com.ztesoft.net.app.base.core.plugin.IOnSettingInputShow;
import com.ztesoft.net.app.base.core.service.ISettingService;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.image.ImageRuntimeException;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsDeleteEvent;
import com.ztesoft.net.mall.core.service.IGoodsAlbumManager;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * 相册后台管理
 * @author enation
 *
 */
public class GalleryAdminPlugin extends DefaultAlbumPlugin implements IOnSettingInputShow,IGoodsDeleteEvent{
 
	private ISettingService settingService ;
	private IGoodsAlbumManager goodsAlbumManager;
	private String getSettingValue(String code){
		return  settingService.getSetting(getSettingGroupName(), code);
	}
	
	/**
	 *  处理图片，包括商品新增时和修改时的图片数据<br/>
	 *  处理原则为向页面输出为全地址，保存在库里的为相对地址，以fs:开头，以区分网络远程图片。<br/>
	 *  在显示的时候将fs:替换为静态资源服务器地址
	 * 	页面中传递过来的图片地址为:http://<staticserver>/<image path>如:<br/>
	 *  http://static.enationsoft.com/attachment/goods/1.jpg<br/>
	 *  存在库中的为/attachment/goods/1.jpg
	 */
 
	@Override
	protected void proessPhoto(String[] picnames,Map goods) {
		
		if (picnames == null) {
			goods.put("image_file",  ""); //EopSetting.IMG_SERVER_DOMAIN+"/images/no_picture.jpg"
			goods.put("image_default", "");//EopSetting.IMG_SERVER_DOMAIN+"/images/no_picture.jpg"
			return;
		}
		
		String imgsStr = "";
		for (int i = 0; i < picnames.length; i++) {
			String filepath = picnames[i];
			 
			if (i != 0) {
				imgsStr += ",";
			}
			imgsStr += picnames[i];

			
			int thumbnail_pic_width =107;
			int thumbnail_pic_height =107;
			int detail_pic_width=320;
			int detail_pic_height=240;
			int album_pic_width =550;
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
				//add by wui商品信息略缩图需要单独的处理记录下单,后续用到分布式的门户界面也需要改造
				// 生成缩略图
				
				thumbName = getThumbPath(filepath, "_thumbnail");
				String imgFile = this.goodsAlbumManager.createThumb(filepath,thumbName, thumbnail_pic_width, thumbnail_pic_height);
				Thumb thumb = new Thumb(imgFile, "_thumbnail", String.valueOf(thumbnail_pic_width), String.valueOf(thumbnail_pic_height), (String)goods.get("goods_id"));
				this.goodsAlbumManager.saveThumb(thumb);
				
				// 生成小图
				thumbName = getThumbPath(filepath, "_small");
				imgFile = this.goodsAlbumManager.createThumb(filepath,thumbName, detail_pic_width, detail_pic_height);
				thumb = new Thumb(imgFile, "_small", String.valueOf(detail_pic_width), String.valueOf(detail_pic_height), (String)goods.get("goods_id"));
				this.goodsAlbumManager.saveThumb(thumb);
				// 生成大图
				thumbName = getThumbPath(filepath, "_big");
				imgFile = this.goodsAlbumManager.createThumb(filepath,thumbName,album_pic_width , album_pic_height);
				thumb = new Thumb(imgFile, "_big", String.valueOf(album_pic_width), String.valueOf(album_pic_height), (String)goods.get("goods_id"));
				this.goodsAlbumManager.saveThumb(thumb);
				
				
			} catch (ImageRuntimeException e) {
				//e.printStackTrace();
			}

		}
		//页面中传递过来的图片地址为:http://<staticserver>/<image path>如:
		imgsStr= imgsStr.replaceAll(EopSetting.IMG_SERVER_DOMAIN+EopContext.getContext().getContextPath(), EopSetting.FILE_STORE_PREFIX);
		goods.put("image_file", imgsStr);
		String image_default = (String)goods.get("image_default");
		if(image_default!=null){
			image_default= image_default.replaceAll(EopSetting.IMG_SERVER_DOMAIN+EopContext.getContext().getContextPath(), EopSetting.FILE_STORE_PREFIX);
			goods.put("image_default", image_default);
		}
	}
	
	private String getThumbPath(String filePath,String shortName){
		return UploadUtil.getThumbPath(filePath, shortName);
	}

	
	
	/**
	 * 响应设置的保存事件，保存各种图片的大小
	 * @param request
	 * @return
	 */
	public Map<String,String> beforeSettingSave(HttpServletRequest request) {
		
		Map<String,String> settingMap  = new HashMap<String,String>();
		
		String thumbnail_pic_width = request.getParameter("photo.thumbnail_pic_width");
		String thumbnail_pic_height = request.getParameter("photo.thumbnail_pic_height");
		String detail_pic_width = request.getParameter("photo.detail_pic_width");
		String detail_pic_height = request.getParameter("photo.detail_pic_height");
		String album_pic_height = request.getParameter("photo.album_pic_height");
		String album_pic_width = request.getParameter("photo.album_pic_width");
		
		settingMap.put("thumbnail_pic_width", thumbnail_pic_width);
		settingMap.put("thumbnail_pic_height", thumbnail_pic_height);
		
		settingMap.put("detail_pic_width", detail_pic_width);
		settingMap.put("detail_pic_height", detail_pic_height);
		
		settingMap.put("album_pic_height", album_pic_height);
		settingMap.put("album_pic_width", album_pic_width);
		
		return settingMap;
	}
	 
	/**
	 * 响应商品删除事件
	 * @param goodsid
	 */
	
	@Override
	public void onGoodsDelete(String[] goodsid) {
		this.goodsAlbumManager.delete(goodsid);
		 
	}
	
	
	@Override
	public String onShow() {
		
		return "setting";
	}

	@Override
	public String getSettingGroupName() {
		 
		return "photo";
	}

	@Override
	public String getAuthor() {
		return "kingapex";
	}

	@Override
	public String getId() {
		return "album";
	}

	@Override
	public String getName() {
		 
		return "jmagick商品相册插件";
	}

	@Override
	public String getType() {
		return "";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void perform(Object... params) {
		
	}
	public void setSettingService(ISettingService settingService) {
		this.settingService = settingService;
	}


	public void setGoodsAlbumManager(IGoodsAlbumManager goodsAlbumManager) {
		this.goodsAlbumManager = goodsAlbumManager;
	}






	
}
