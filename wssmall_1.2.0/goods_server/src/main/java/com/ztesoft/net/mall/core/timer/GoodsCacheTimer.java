package com.ztesoft.net.mall.core.timer;

import com.ztesoft.net.cache.common.GoodsNetCacheWrite;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 商品缓存刷新
 * @author wui
 *
 */
public class GoodsCacheTimer {
	
	public void run() {
		

	    //ip验证处理 add by wui所有定时任务都需要加上限制条件
  		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"run"))
  			return ;
	  		
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String planDBSourceFroms = cacheUtil.getConfigInfo("PLAN_SOURCE_FROM");
		if(StringUtil.isEmpty(planDBSourceFroms))
			planDBSourceFroms = ManagerUtils.getSourceFrom();
		GoodsNetCacheWrite write = SpringContextHolder.getBean("goodsNetCacheWrite");//new GoodsNetCacheWrite();
		try{
			for (String plan_dbsourcefrom :planDBSourceFroms.split(",")) {
				ManagerUtils.CACHE_REFRESH_SOURCE_FROM = plan_dbsourcefrom;
				write.loadAllGoods();
				write.loadAllGoodsBySku();
				write.loadAllGoodsTerminal();
				write.loadAllGoodsTC();
				write.loadAllGoodsContract();
				write.loadAllGoodsPromotion();
				write.loadAllGoodsTags();
				write.loadAllGoodsRelTags();
				write.loadGoodsComplex();
				write.loadGoodsAdjunct();
				write.loadCatComplex();
				write.loadGoodsType();
				write.loadProductType();
				write.loadAllVproductsByName();
				write.loadBrand();
				write.loadGoodsCatByLvId();
				write.loadGoodsByCatLvI();
				write.loadAllTypes();
				write.loadAllCats();
				write.loadBrandByTypeId();
				write.loadBrandModelByBrandId();
				write.loadAllProducts();
				write.loadGoodsRelProducts();//商品的货品
				write.loadBrandModel();
				write.loadTerminalNumList();
				write.loadCatGoods();
			}
		}catch(Exception e){
			
		}finally{
			ManagerUtils.CACHE_REFRESH_SOURCE_FROM ="";
		}
		
	}
}