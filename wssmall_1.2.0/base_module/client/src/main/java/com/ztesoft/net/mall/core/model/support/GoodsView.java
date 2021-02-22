package com.ztesoft.net.mall.core.model.support;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.utils.UploadUtilc;
import com.ztesoft.net.framework.util.CurrencyUtil;
import com.ztesoft.net.mall.core.model.Goods;

/**
 * 商品视图，对商品图片和属性做了处理,
 * 并且包含了一些冗余信息。
 * @author kingapex
 * 2010-7-16下午02:05:11
 */
public class GoodsView extends Goods  {

	private Double save_price;// 节省金额

	private Double agio; // 折扣

	private String brand_name; // 所属的品牌名称

	private Map propMap; // 商品属性，以p<index>为key，属性值为value的map

	private int hasSpec; // 是否有规格

	private List specList; // 规格列表
	
	private String productid; //如果没有规格时唯一的货品id
	
	private String cat_name;
	private String cat_image;
	
	private String agentname;
	private String staffno;
	private boolean isLast=false;//是否是列表中的最后一个
	private boolean isFirst=false;//是否是列表中的第一个
	

	public Double getAgio() {
//		agio = this.getPrice() / this.getMktprice();
		if(this.getMktprice()==0.0){
			agio = 0.0;
		}else{
			agio = CurrencyUtil.div(this.getPrice(), this.getMktprice());
		}
		
		return agio;
	}

	public void setAgio(Double agio) {
		this.agio = agio;
	}

	public Double getSave_price() {
//		save_price = this.getMktprice() - this.getPrice();
		save_price = CurrencyUtil.sub(this.getMktprice(), this.getPrice());
		return save_price;
	}

	public void setSave_price(Double save_price) {
		this.save_price = save_price;
	}

	/**
	 * 返回商品默认图片<br/>
	 * 如果商品没有图片返回"images/no_picture.gif"<br/>
	 * 如果商品图片是以本地文件形式存储，将商品图片的加上静态资源前缀
	 */
	@Override
	public String getImage_default() {
		String image_default = super.getImage_default();
		if (image_default == null || image_default.equals("")) {
			return  EopSetting.IMG_SERVER_DOMAIN +"/images/no_picture.jpg";
		}else{
			image_default  =UploadUtilc.replacePath(image_default); 
		} 
	 
		return image_default;
	}
	
	
	
	/**
	 * 返回商品默认图片的缩略图<br/>
	 * 如果商品没有图片返回"images/no_picture.gif"<br/>
	 * 如果商品图片是以本地文件形式存储，将商品图片的加上静态资源前缀
	 */
	public String getThumbnail_pic(){
		String thumbnail = super.getImage_default();
		if (thumbnail == null || thumbnail.equals("")) {
			return EopSetting.IMG_SERVER_DOMAIN +"/images/no_picture.jpg";
		} else{
			thumbnail= this.getImage_default();
		}
		thumbnail= UploadUtilc.getThumbPath(thumbnail, "_thumbnail");
		return thumbnail;		
	}
	
	

	@Override
	public String getBrand_name() {
		return brand_name;
	}

	@Override
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public Map getPropMap() {
		return propMap;
	}

	public void setPropMap(Map propMap) {
		this.propMap = propMap;
	}

	public List getSpecList() {
		return specList;
	}

	public void setSpecList(List specList) {
		this.specList = specList;
	}

	public int getHasSpec() {
		return hasSpec;
	}

	public void setHasSpec(int hasSpec) {
		this.hasSpec = hasSpec;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public boolean getIsLast() {
		return isLast;
	}

	public void setIsLast(boolean isLast) {
		this.isLast = isLast;
	}

	public boolean getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	@Override
	public String getCat_name() {
		return cat_name;
	}

	@Override
	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}

	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}

	public String getCat_image() {
		return cat_image;
	}

	public void setCat_image(String cat_image) {
		this.cat_image = cat_image;
	}

	public String getStaffno() {
		return this.staffno;
	}

	public void setStaffno(String staffno) {
		this.staffno = staffno;
	}



	
}
