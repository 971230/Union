package com.ztesoft.net.mall.core.service.impl;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.LimitBuy;
import com.ztesoft.net.mall.core.model.LimitBuyGoods;
import com.ztesoft.net.mall.core.service.ILimitBuyManager;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.remote.params.activity.req.PromotionActEditReq;
import com.ztesoft.remote.params.activity.req.PromotionActReq;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import zte.net.iservice.IOrderServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

public class LimitBuyManager extends BaseSupport implements ILimitBuyManager {
	@Resource
	private IOrderServices orderServices;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)  
	public void add(LimitBuy limitBuy) {
		List<LimitBuyGoods> limitBuyGoodsList = limitBuy.getLimitBuyGoodsList();
	
		limitBuy.setAdd_time(DBTUtil.current());
		this.baseDaoSupport.insert("limitbuy", limitBuy);
		String limitBuyId = limitBuy.getId();
		this.addGoods(limitBuyGoodsList, limitBuyId);
		addDataToPromtion(limitBuyId);
	}
	
	private void addGoods(List<LimitBuyGoods> limitBuyGoodsList,String limitBuyId){
		if(limitBuyGoodsList == null || limitBuyGoodsList.isEmpty()) throw new RuntimeException("添加限时购买的商品列表不能为空");
		for(LimitBuyGoods limitBuyGoods :limitBuyGoodsList){
			//this.baseDaoSupport.execute(SF.goodsSql("ADD_GOODS"), limitBuyId,limitBuyGoods.getGoodsid(),limitBuyGoods.getPrice());
			limitBuyGoods.setLimitbuyid(limitBuyId);
			this.baseDaoSupport.insert("limitbuy_goods", limitBuyGoods);
			this.updateGoodsLimit(limitBuyId, 1);
		}
	}
	
	/**调用远端，将数据写入活动表
	 * @param id
	 */
	private void addDataToPromtion(String id){
		Map<String,String> param = new HashMap<String, String>();
		LimitBuy limitBuy = this.get(id);
		param.put("name", limitBuy.getName());
		param.put("enable", "1");
		param.put("begin_time", limitBuy.getStart_time());
		param.put("end_time", limitBuy.getEnd_time());
		param.put("pmt_code", "");
		param.put("brief", "秒杀活动");
		param.put("pmt_time_begin", limitBuy.getStart_time());
		param.put("pmt_time_end", limitBuy.getEnd_time());
		param.put("pmt_solution", limitBuy.getId());
		param.put("pmt_describe", "秒杀活动");
		param.put("a_flag", "2");//设置活动类型：1：团购活动  2：秒杀活动
		PromotionActReq req = new PromotionActReq();
		req.setMap(param);
		orderServices.addPromtionActivity(req);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)  
	public void delete(String id) {
		this.updateGoodsLimit(id, 0);
		this.baseDaoSupport.execute(SF.goodsSql("DELETE_LIMITBUY_GOODS"), id);
		this.baseDaoSupport.execute(SF.goodsSql("DELETE_LIMITBUY"), id);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)  
	public void updateBuyGoods(LimitBuyGoods limitBuyGoods) {
		this.baseDaoSupport.update("ES_LIMITBUY_GOODS", limitBuyGoods, "GOODSID = '"+limitBuyGoods.getGoodsid()+"' and LIMITBUYID='"+limitBuyGoods.getLimitbuyid()+"'");
	}
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED)  
	public void edit(LimitBuy limitBuy) {
		List<LimitBuyGoods> limitBuyGoodsList = limitBuy.getLimitBuyGoodsList();
		this.baseDaoSupport.update("limitbuy", limitBuy,"id="+limitBuy.getId());
		String limitBuyId = limitBuy.getId();
		this.baseDaoSupport.execute(SF.goodsSql("DELETE_LIMITBUY_GOODS_0"), limitBuyId);
		
		this.updateGoodsLimit(limitBuyId, 0);
		this.addGoods(limitBuyGoodsList, limitBuyId);
	}

	/**
	 * 更新商品是否是限时购买
	 * @param limitid
	 * @param islimit
	 */
	private void updateGoodsLimit(String limitid,int islimit){
		this.daoSupport.execute(SF.goodsSql("UPDATE_GOODS_LIMIT"),islimit ,limitid);
	}
	
	@Override
	public Page list(int pageNo, int pageSize) {
		String sql = SF.goodsSql("GET_LIMITBUY_LIST");
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, LimitBuy.class);
	}

	/**
	 *根据条件查询秒杀活动信息
	 */
	@Override
	public Page queryList(Map<String,String> map, int pageNo, int pageSize){
		String sql = SF.goodsSql("GET_LIMITBUY_LIST_0");
		StringBuffer whereBuf = new StringBuffer();
		if(map != null){
			if(!StringUtil.isEmpty(map.get("name"))){
				whereBuf.append(" and a.name like '%"+map.get("name")+"%'");
			}
			if(!StringUtil.isEmpty(map.get("disabled")) && !"-1".equals(map.get("disabled")+"")){
				whereBuf.append(" and a.disabled=:disabled");
			}
			if(!StringUtil.isEmpty(map.get("goodsid"))){
				whereBuf.append(" and b.goodsid=:goodsid");
			}
			if(!StringUtil.isEmpty(map.get("start_time"))){
				whereBuf.append(" and to_char(a.start_time,'yyyy-MM-dd') >=:start_time");
			}
			if(!StringUtil.isEmpty(map.get("end_time"))){
				whereBuf.append(" and to_char(a.end_time,'yyyy-MM-dd') <=:end_time");
			}
		}
		sql += whereBuf.append(" order by a.add_time desc ").toString();
		Page page = this.baseDaoSupport.queryForPageByMap(sql, pageNo, pageSize, map);
		return page;
	}
	
	@Override
	public List<Map> queryLimitBuyGoods(Map<String,String> map){
		String sql = SF.goodsSql("LIMITBUY_GOODS");
		StringBuffer whereBuf = new StringBuffer();
		if(map != null){
			if(!StringUtil.isEmpty(map.get("id"))){
				whereBuf.append(" and a.limitbuyid="+map.get("id"));
			}
		}
		sql += whereBuf.toString();
		List queryForList = this.baseDaoSupport.queryForList(sql);
		return queryForList;
	}
	
	@Override
	public List<LimitBuy> listEnable() {
		//首先读出可用的限时抢购列表
		String sql = SF.goodsSql("LIST_ENABLE");
		List<LimitBuy> limitBuyBuyList = this.baseDaoSupport.queryForList(sql, LimitBuy.class,DBTUtil.current(),DBTUtil.current());
		
		
		//读取出相应的商品列表
		sql = SF.goodsSql("LIST_ENABLE_1");
		List<Map> goodsList  = this.daoSupport.queryForList(sql,DBTUtil.current(),DBTUtil.current());
		for(LimitBuy limitBuy:limitBuyBuyList){
			List<Map> list  = this.findGoods(goodsList, limitBuy.getId());
			limitBuy.setGoodsList(list);
		}
		return limitBuyBuyList;
	}
	@Override
	public List<Map> listEnableGoods(){
		//读取出相应的商品列表
		String sql = SF.goodsSql("LIST_ENABLE_GOODS");
		List<Map> goodsList  = this.daoSupport.queryForList(sql,null);
		return goodsList;
	}
	private List<Map> findGoods(List<Map> goodsList,String limitbuyid){
		List<Map> list  = new ArrayList<Map>();
		for(Map goods : goodsList){
			if(limitbuyid.equals(goods.get("limitbuyid"))){
				list.add(goods);
			}
			 
		}
		return list;
	}

	@Override
	public LimitBuy get(String id) {
		String sql = SF.goodsSql("LIMITBUY_SELECT_BY_ID");
		LimitBuy limitBuy =(LimitBuy)this.baseDaoSupport.queryForObject(sql,LimitBuy.class,id );
		sql = SF.goodsSql("LIMIT_BUY_SELECT_0");
		List<Map> goodsList  = this.daoSupport.queryForList(sql, id);
		limitBuy.setGoodsList(goodsList);
		String limitGoodsSql = SF.goodsSql("LIMITBUYGOODS_SELECT_BY_ID");
		List<LimitBuyGoods> limitBuyGoodsList = this.daoSupport.queryForList(limitGoodsSql, LimitBuyGoods.class, id);
		limitBuy.setLimitBuyGoodsList(limitBuyGoodsList);
		return limitBuy;
	}
	
	/**逻辑删除与恢复
	 * @param id
	 * @param disabled
	 */
	@Override
	public void editLimitState(String id,int disabled){
		LimitBuy limitBuy = this.get(id);
		this.baseDaoSupport.execute(SF.goodsSql("LIMIT_BUY_UPDATE"), disabled, limitBuy.getId());
		PromotionActEditReq req = new PromotionActEditReq();
		req.setPmt_solution(limitBuy.getId());
		if(1 == disabled){
			this.updateGoodsLimit(limitBuy.getId(), 0);
			req.setEnable("0");
			orderServices.promotionActEdit(req);
		}else if(0 == disabled){
			this.updateGoodsLimit(limitBuy.getId(), 1);
			req.setEnable("1");
			orderServices.promotionActEdit(req);
		}
	}
}
