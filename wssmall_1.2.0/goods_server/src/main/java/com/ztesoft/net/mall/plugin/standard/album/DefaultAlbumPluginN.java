package com.ztesoft.net.mall.plugin.standard.album;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.plugin.page.JspPageTabs;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.params.GoodsExtData;
import com.ztesoft.net.mall.core.plugin.goods.AbstractGoodsPluginN;
import com.ztesoft.net.mall.core.utils.ManagerUtils;


/**
 * 默认商品相册插件
 * @author enation
 *
 */
public class DefaultAlbumPluginN extends AbstractGoodsPluginN{
 
	@Override
	public void addTabs(){
		 JspPageTabs.addTab("setting",2, "相册设置");
	}
 
	@Override
	public void onBeforeGoodsAdd(Map goods, GoodsExtData goodsExtData) {
		String[] picnames =  goodsExtData.getPicnames();
		proessPhoto(picnames,goods); 
	}

	@Override
	public void onBeforeGoodsEdit(Map goods, GoodsExtData goodsExtData)  {
		String[] picnames =  goodsExtData.getPicnames();
		proessPhoto(picnames,goods); 
	}
	
	/**
	 * 在修改时处理图片的信息
	 */
	@Override
	public String onFillGoodsEditInput(Map goods, GoodsExtData goodsExtData) {
		HttpServletRequest request =ThreadContextHolder.getHttpRequest();
		String contextPath = request.getContextPath();
		//设置需要传递到页面的数据	
		FreeMarkerPaser freeMarkerPaser =FreeMarkerPaser.getInstance();
		
		// 关于图片的处理
 		String image_default= null;
		if (goods.get("image_file") != null && goods.get("image_file")!="") {
			String image_file = goods.get("image_file").toString();
			String[] thumbnail_images = image_file.split(",");

			image_default= (String)goods.get("image_default");
			if(!StringUtil.isEmpty(image_default)){
				image_default = UploadUtil.replacePath(image_default);
				
			}
			
			freeMarkerPaser.putData("ctx", contextPath);
			freeMarkerPaser.putData("image_default", image_default );
			freeMarkerPaser.putData("thumbnail_images", thumbnail_images );	
		}
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
			freeMarkerPaser.setPageName("album_ecs");
		}
		else{
			freeMarkerPaser.setPageName("album");
		}
		String html = freeMarkerPaser.proessPageContent();
		return html;
	}

	@Override
	public String onFillGoodsAddInput() {
		FreeMarkerPaser freeMarkerPaser =FreeMarkerPaser.getInstance();
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
			freeMarkerPaser.setPageName("album_ecs");
		}
		else{
			freeMarkerPaser.setPageName("album");
		}
		freeMarkerPaser.putData("image_default", null );
		freeMarkerPaser.putData("jsessionid", ThreadContextHolder.getSessionContext().getSession().getId());
		
		freeMarkerPaser.putData("thumbnail_images", null );	
		String html = freeMarkerPaser.proessPageContent();
		return html;
	}

	@Override
	public void onAfterGoodsAdd(Map goods, GoodsExtData goodsExtData)  {
		
	}

	@Override
	public void onAfterGoodsEdit(Map goods, GoodsExtData goodsExtData)  {
		
	}

	protected void proessPhoto(String[] picnames,Map goods) {
		
	}

	@Override
	public String getAuthor() {
		return "kingapex";
	}

	@Override
	public String getId() {
		return "default_album";
	}

	@Override
	public String getName() {
		return "默认商品相册插件";
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
	
}
