package com.ztesoft.net.mall.core.service.impl.batchimport;

import com.ztesoft.net.mall.core.model.ImportDataSource;
import com.ztesoft.net.mall.core.service.batchimport.IGoodsDataImporter;

import org.w3c.dom.Element;

import java.util.Map;

public class GoodsPriceImporter implements IGoodsDataImporter {

	@Override
	public void imported(Object value, Element node, ImportDataSource importDs,
			Map goods) {
		if( value ==null || value.equals("")) value=0;
		
		if(importDs.isNewGoods()){
			if("mkprice".equals(node.getAttribute("type"))){
				goods.put("mktprice", value) ;
			}else
			goods.put("price", value) ;
		}
	}

}
