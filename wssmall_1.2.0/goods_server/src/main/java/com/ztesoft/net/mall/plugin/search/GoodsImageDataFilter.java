package com.ztesoft.net.mall.plugin.search;

import java.sql.ResultSet;
import java.util.Map;

import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.mall.core.plugin.search.IGoodsDataFilter;

/**
 * 商品图片数据过滤器
 * @author kingapex
 *
 */
public class GoodsImageDataFilter extends AutoRegisterPlugin implements IGoodsDataFilter{

	@Override
	public void filter(Map<String, Object> goods, ResultSet rs) {
		String  image_file =(String) goods.get("image_file");
		if(image_file !=null ){
			image_file = UploadUtil.replacePath(image_file);
			goods.put("image_file", image_file);
		}
		
		String image_default = (String) goods.get("image_default");
		if (image_default == null || image_default.equals("")) {
			image_default =  EopSetting.IMG_SERVER_DOMAIN +"/images/no_picture.jpg";
		}else{
			image_default  =UploadUtil.replacePath(image_default); 
		}
		
		goods.put("image_default", image_default);
		
	}
	
	@Override
	public String getAuthor() {
		
		return "kingapex";
	}

	
	@Override
	public String getId() {
		
		return "goodsImageDataFilter";
	}

	
	@Override
	public String getName() {
		
		return "商品图片数据过滤器";
	}

	
	@Override
	public String getType() {
		
		return "searchFilter";
	}

	
	@Override
	public String getVersion() {
		
		return "1.0";
	}

	
	@Override
	public void perform(Object... params) {
		

	}

	@Override
	public void register() {
		

	}


}
