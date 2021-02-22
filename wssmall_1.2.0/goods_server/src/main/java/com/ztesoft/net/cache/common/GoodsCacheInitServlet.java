package com.ztesoft.net.cache.common;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import zte.net.ecsord.utils.SpecUtils;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.service.IGoodsManager;

public class GoodsCacheInitServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {
		try{
			if(!SpecUtils.isGoodsHasCache()){
				IGoodsManager gm = SpringContextHolder.getBean("goodsManager");
				gm.initGoodsCache();
				SpecUtils.addHasGoodsCache();
			}
		}catch( Exception e){
			return;
		}
	}

	public void initGoodsCache() {
//		boolean canNext = true;
//		try{
//			SpringContextHolder.getBean("goodsManager");
//		}catch( Exception e){
//			canNext = false;
//			return;
//		}
//		if(!canNext)
//			return;
//		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
//		String planDBSourceFroms = cacheUtil.getConfigInfo("PLAN_SOURCE_FROM");
//		//配置的不匹配直接选用文件配置的,add by wui
//		if(!(planDBSourceFroms.indexOf(EopSetting.SOURCE_FROM)>-1))
//			planDBSourceFroms = EopSetting.SOURCE_FROM;
//		if(StringUtil.isEmpty(planDBSourceFroms))
//			planDBSourceFroms = ManagerUtils.getSourceFrom();
//		
//		GoodsNetCacheWrite write = new GoodsNetCacheWrite();
//		Class clazz = write.getClass();
//		Method[] methods = clazz.getMethods();
//		try {
//			for (Method method : methods) {
//				if ("loadAllGoods".equals(method.getName())
//						|| "loadAllProducts".equals(method.getName())
//						|| "loadAllGoodsTerminal".equals(method.getName())
//						|| "loadAllGoodsTC".equals(method.getName())
//						|| "loadAllGoodsContract".equals(method.getName())
//						|| "loadAllGoodsPromotion".equals(method.getName())
//						|| "loadAllGoodsTags".equals(method.getName())
//						|| "loadAllGoodsRelTags".equals(method.getName())
//						|| "loadGoodsComplex".equals(method.getName())
//						|| "loadGoodsAdjunct".equals(method.getName())
//						|| "loadCatComplex".equals(method.getName())
//						|| "loadGoodsType".equals(method.getName())
//						|| "loadBrand".equals(method.getName())
//						|| "loadGoodsCatByLvId".equals(method.getName())
//						|| "loadGoodsByCatLvI".equals(method.getName())
//						|| "loadAllGoodsByServ".equals(method.getName())
//						|| "loadAllTypes".equals(method.getName())
//						|| "loadBrandByTypeId".equals(method.getName())
//						|| "loadBrandModelByBrandId".equals(method.getName())
//						|| "loadGoodsCatByTypeId".equals(method.getName())) {
//					
//					for (String plan_dbsourcefrom :planDBSourceFroms.split(",")) {
//						ManagerUtils.CACHE_REFRESH_SOURCE_FROM = plan_dbsourcefrom;
//						Method m = clazz.getDeclaredMethod(method.getName());
//						m.invoke(write);
//					}
//				}
//
//			}
//			
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}finally{
//			ManagerUtils.CACHE_REFRESH_SOURCE_FROM ="";
//		}

	}

}
