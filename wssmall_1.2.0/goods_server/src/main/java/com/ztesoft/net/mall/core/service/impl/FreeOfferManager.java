package com.ztesoft.net.mall.core.service.impl;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.FreeOffer;
import com.ztesoft.net.mall.core.model.mapper.GiftMapper;
import com.ztesoft.net.mall.core.service.IFreeOfferManager;
import com.ztesoft.net.sqls.SF;

import java.util.ArrayList;
import java.util.List;

/**
 * 赠品管理
 * @author 李志富 lzf<br/>
 *         2010-1-15 下午01:31:08<br/>
 *         version 1.0<br/>
 * @since v2.1.2:将赠品图片存储改为相对，即fs:开头。读取后加上静态资源服务器地址。
 * <br/>
 */
public class FreeOfferManager extends BaseSupport<FreeOffer> implements
		IFreeOfferManager {
	
	
	@Override
	public void delete(String bid) {
		if (bid == null || bid.equals(""))
			return;
		String sql = SF.goodsSql("FREEOFFER_DELETE") + " and fo_id in (" + bid + ")";
		this.baseDaoSupport.execute(sql);
	}

	
	@Override
	public void revert(String bid) {
		if (bid == null || bid.equals(""))
			return;
		String sql = SF.goodsSql("FREEOFFER_REVERT") + " and fo_id in (" + bid +")";
		this.baseDaoSupport.execute(sql);
	}
	
	
	@Override
	public void clean(String bid){
		if (bid == null || bid.equals(""))
			return;
		String sql = SF.goodsSql("FREEOFFER_DELETE_BY_IDS") + " and fo_id in (" + bid + ")";
		this.baseDaoSupport.execute(sql);
	}

	
	@Override
	public FreeOffer get(int fo_id) {
		String sql = SF.goodsSql("GET_FREEOFFER_LIST");
		FreeOffer freeOffer = baseDaoSupport.queryForObject(sql, FreeOffer.class, fo_id);
		String pic  = freeOffer.getPic();
		if(pic!=null){
			pic  =UploadUtil.replacePath(pic);
		}
		freeOffer.setPic(pic);
		return freeOffer;
	}

	
	@Override
	public Page list(int pageNo,int pageSize) { 
		long now =System.currentTimeMillis();
		String sql = SF.goodsSql("GET_FREEOFFER_LIST_BY_DATE");
		String cond = " and startdate<="+DBTUtil.current()+" and enddate>="+DBTUtil.current();
		sql = sql.replace("#cond", sql);
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize,new GiftMapper());
		return page;
	}

	
	@Override
	public void saveAdd(FreeOffer freeOffer) {
		this.baseDaoSupport.insert("freeoffer", freeOffer);

	}

	
	@Override
	public void update(FreeOffer freeOffer) {
		this.baseDaoSupport.update("freeoffer", freeOffer, "fo_id="
				+ freeOffer.getFo_id());
	}
	
	private String getListSql(){
		String sql = SF.goodsSql("GET_FREEOFFER_BY_CATALOG");
		return sql;
	}

	
	@Override
	public Page pageTrash(String name, String order, int page, int pageSize) {
		order = order == null ? " fo_id desc" : order;
		String sql = getListSql();
		name = name == null ? " fo.disabled=1 ": " fo.disabled=1 and fo_name like '%" + name+ "%'";
		sql += " and " + name;
		sql += " order by  " + order;
		Page webpage = this.daoSupport.queryForPage(sql, page, pageSize,new GiftMapper());
		return webpage;
	}

	
	@Override
	public Page list(String name, String order, int page,
			int pageSize) {
		order = order == null ? " fo_id desc" : order;
		String sql =  SF.goodsSql("GET_FREEOFFER_BY_CATALOG_0");
		name = name == null ? " fo.disabled=0 ": " fo.disabled=0 and fo_name like '%" + name+ "%'";
		sql += " and " + name;
		sql += " order by  " + order;
		Page webpage = this.daoSupport.queryForPage(sql, page, pageSize,new GiftMapper());
		return webpage;
	}

	
	@Override
	public List getOrderGift(String orderId) {
		String sql = SF.goodsSql("GET_ORDER_GIFT");
		return this.baseDaoSupport.queryForList(sql, orderId);
	}

	/**
	 * @author kingapex
	 */
	
	@Override
	public List list(Long[] ids) {
		if(ids==null || ids.length == 0) return new ArrayList();
		String sql = SF.goodsSql("GET_FREEOFFER_BY_FO_ID") + " and fo_id in(" + StringUtil.arrayToString(ids, ",") +")";
		return this.baseDaoSupport.queryForList(sql ,new GiftMapper());
	}

}