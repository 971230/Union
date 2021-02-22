package com.ztesoft.net.mall.core.service.impl;


import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.model.GoodsLvPrice;
import com.ztesoft.net.mall.core.plugin.search.*;
import com.ztesoft.net.mall.core.service.IGoodsCatManager;
import com.ztesoft.net.mall.core.service.IGoodsSearchManager2;
import com.ztesoft.net.mall.core.utils.UrlUtils;
import com.ztesoft.net.sqls.SF;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsSearchManager2 extends BaseSupport implements IGoodsSearchManager2{
 
	private GoodsSearchPluginBundle goodsSearchPluginBundle;
	private GoodsDataFilterBundle goodsDataFilterBundle;
	
	private IGoodsCatManager goodsCatManager ;
	
	@Override
	public Map<String,Object> getSelector(String url) {
		
		/**
		 * 如果按类别搜索,则查询此类别,并传递给过虑器
		 * 如果未按类别搜索,则传递null
		 */
		Cat cat  =getCat(url);
		
		Map selectorMap = new HashMap<String ,String>();
		List<IGoodsSearchFilter > filterList  = goodsSearchPluginBundle.getPluginList();
		for(IGoodsSearchFilter filter:filterList){
			
			

			String urlFragment = UrlUtils.getParamStringValue(url, filter.getFilterId());
			String exSelfurl = UrlUtils.getExParamUrl(url, filter.getFilterId()).replaceAll(".html", ""); //获取排除此过滤器的url
			
			List<SearchSelector> selectorList= filter.createSelectorList(cat,exSelfurl, urlFragment);
			
			if(selectorList!=null)
				selectorMap.put(filter.getFilterId(), selectorList);
			
			
			/**
			 * 如果实现了多过滤选择器，调用多选择器生成接口
			 */
			if(filter instanceof IMultiSelector){
				Map multiSelector =  ((IMultiSelector)filter).createMultiSelector(cat, exSelfurl, urlFragment);
				if(multiSelector!=null)
					selectorMap.put(filter.getFilterId(), multiSelector);
			}
		}
		return selectorMap;
	}
	
	
	/**
	 * 调用插件向挂件参数中压入参数
	 */
	@Override
	public void putParams(Map<String,Object> params,String url){
		List<IGoodsSearchFilter > filterList  = goodsSearchPluginBundle.getPluginList();
		for(IGoodsSearchFilter filter:filterList){
			if(filter instanceof IPutWidgetParamsEvent){
				String urlFragment = UrlUtils.getParamStringValue(url, filter.getFilterId());
				IPutWidgetParamsEvent event = (IPutWidgetParamsEvent)filter;
				event.putParams(params, urlFragment);
			}
		}
	}
	
	/**
	 * 返回当前搜索的分类
	 * @param url
	 * @return
	 */
	private Cat getCat(String url){
		
		Cat cat  = null;
		String catidstr = UrlUtils.getParamStringValue(url,"cat");
		if(!StringUtil.isEmpty(catidstr) && !"0".equals(catidstr)){
			String catid= catidstr;
			cat = goodsCatManager.getById(catid); 
		}
		
		return cat;
	}
	
	@Override
	public Page search(int pageNo,int pageSize ,String url) {
		List list  = this.list(pageNo,pageSize,url);
		int count = this.count(url);
		Page webPage = new Page(0, count, pageSize, list);
		return webPage;
	}
	
	public List list(int pageNo,int pageSize,String url){
		
		StringBuffer sql  =new StringBuffer();
		sql.append(SF.goodsSql("GOODS_SELECT_BY_0_1"));
		
		/***
		 * --------------------------
		 * 过滤搜索条件
		 * -------------------------
		 */
		this.filterTerm(sql,url);
	 
		
		RowMapper mapper = new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				
				Map<String,Object> goodsMap  = new HashMap<String,Object>();
				
				goodsMap.put("name", rs.getString("name"));
				goodsMap.put("goods_id", rs.getInt("goods_id"));
				goodsMap.put("image_default", rs.getString("image_default"));
				goodsMap.put("mktprice", rs.getDouble("mktprice"));
				goodsMap.put("price", rs.getDouble("price"));
				goodsMap.put("create_time", rs.getLong("create_time"));
				goodsMap.put("last_modify",rs.getLong("last_modify") );
				goodsMap.put("type_id", rs.getInt("type_id"));
				goodsMap.put("store",rs.getInt("store") );
				goodsMap.put("have_spec", rs.getInt("have_spec"));
				goodsMap.put("sn",rs.getString("sn") );
				goodsMap.put("intro",rs.getString("intro") );
				goodsMap.put("cat_id",rs.getInt("cat_id" ));
				
				filterGoods(goodsMap,rs);
				
				return goodsMap;
			}

		};

		List goodslist = this.daoSupport.queryForList(sql.toString(), pageNo, pageSize, mapper); 
	//	logger.info(sql);
		return goodslist;
	}
	
	
	
	
	/**
	 * 过滤商品数据
	 * @param goods
	 * @param rs
	 */
	private final void filterGoods(Map<String,Object> goods,ResultSet rs){
		List<IGoodsDataFilter > filterList  = goodsDataFilterBundle.getPluginList();
		for(IGoodsDataFilter filter:filterList){
				filter.filter(goods,rs);
		}
	}
	
	
	
	
	/**
	 * 应用会员价
	 */
	private void applyMemPrice(List<Map>   proList,List<GoodsLvPrice> memPriceList,double discount ){
		for(Map pro : proList ){
			double price  =( (BigDecimal )pro.get("price")).doubleValue() *  discount;
			for(GoodsLvPrice lvPrice:memPriceList){
				if( (pro.get("product_id")).equals(lvPrice.getProductid())){
					price = lvPrice.getPrice();
				}
			}
			 
			pro.put("price", price);
		}
	}
	
	/**
	 * 计算搜索结果数量
	 * @param url
	 * @return
	 */
	private int count(String url){
		StringBuffer sql = new StringBuffer(SF.goodsSql("GOODS_COUNT_BY_0_1"));
		this.filterTerm(sql, url);
		return this.daoSupport.queryForInt(sql.toString());
	}
	
	
	/**
	 *过滤搜索条件 
	 * @param sql 要加条件的sql语句
	 */
	private void filterTerm(StringBuffer sql,String url){
		/**
		 * 如果按类别搜索,则查询此类别,并传递给过虑器
		 * 如果未按类别搜索,则传递null
		 */
		Cat cat  =getCat(url);
		
		
		//准备一个空的排序字串,单独交给IGoodsSortFilter(商品排序过滤器执行)
		StringBuffer sortStr = new StringBuffer();
		
		
		List<IGoodsSearchFilter > filterList  = goodsSearchPluginBundle.getPluginList();
		for(IGoodsSearchFilter filter:filterList){
			
			String urlFragment = UrlUtils.getParamStringValue(url, filter.getFilterId());
			if(filter instanceof IGoodsSortFilter){
				filter.filter(sortStr, cat, urlFragment); //商品排序过滤器执行独立的排序字串
			}else{
				filter.filter(sql,cat, urlFragment);
			}
		}
		
		sql.append(sortStr); //连接上排序字串
		
		
	}

	public GoodsSearchPluginBundle getGoodsSearchPluginBundle() {
		return goodsSearchPluginBundle;
	}

	public void setGoodsSearchPluginBundle(
			GoodsSearchPluginBundle goodsSearchPluginBundle) {
		this.goodsSearchPluginBundle = goodsSearchPluginBundle;
	}


	public IGoodsCatManager getGoodsCatManager() {
		return goodsCatManager;
	}


	public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
		this.goodsCatManager = goodsCatManager;
	}


	public GoodsDataFilterBundle getGoodsDataFilterBundle() {
		return goodsDataFilterBundle;
	}


	public void setGoodsDataFilterBundle(GoodsDataFilterBundle goodsDataFilterBundle) {
		this.goodsDataFilterBundle = goodsDataFilterBundle;
	}


	
	
}
