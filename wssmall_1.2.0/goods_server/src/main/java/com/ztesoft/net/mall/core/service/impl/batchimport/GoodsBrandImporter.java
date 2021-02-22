package com.ztesoft.net.mall.core.service.impl.batchimport;

import com.ztesoft.net.mall.core.model.Brand;
import com.ztesoft.net.mall.core.model.ImportDataSource;
import com.ztesoft.net.mall.core.service.batchimport.IGoodsDataImporter;

import org.w3c.dom.Element;

import java.util.List;
import java.util.Map;

/**
 * 商品品牌导入器
 * @author kingapex
 *
 */
public class GoodsBrandImporter implements IGoodsDataImporter {
 
	@Override
	public void imported(Object name,Element node,ImportDataSource importDs,Map goods){
		
		if(!importDs.isNewGoods())return;
		
		String brandname = (String)name;
		if(brandname==null) brandname="";
		brandname=brandname.trim();
		
		List<Brand> brandList  = importDs.getBrandList();
		for(Brand brand:brandList){
			if(brand.getName().equals(brandname)){
				goods.put("brand_id", brand.getBrand_id());
			}
		}
	}

}
