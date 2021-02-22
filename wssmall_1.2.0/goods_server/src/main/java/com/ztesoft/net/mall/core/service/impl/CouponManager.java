package com.ztesoft.net.mall.core.service.impl;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.app.base.core.service.ISettingService;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Coupons;
import com.ztesoft.net.mall.core.model.CouponsSearch;
import com.ztesoft.net.mall.core.service.ICouponManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lzf
 * version 1.0<br/>
 * 2010-6-13&nbsp;上午11:24:50
 */
public class CouponManager extends BaseSupport<Coupons> implements
		ICouponManager {

	private ISettingService settingService;
	
	public ISettingService getSettingService() {
		return settingService;
	}


	public void setSettingService(ISettingService settingService) {
		this.settingService = settingService;
	}


	@Override
	public Page list(CouponsSearch couponsSearch,int pageNo, int pageSize, String order) {
		
		AdminUser au = ManagerUtils.getAdminUser();
		String userid = null;
		if(au!=null){
			userid = au.getUserid();
			if(Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
				userid = au.getUserid();
		}
		
		
		order = order == null ? " cpns_id desc" : order;
		String sql = SF.goodsSql("GOODS_COUPONS");
		if(!ManagerUtils.isAdminUser() && userid!=null){
			sql += " and c.userid = "+ userid ;
		}
		if(!StringUtil.isEmpty(couponsSearch.getCpns_name())){
			sql += " and c.cpns_name like '%"+couponsSearch.getCpns_name()+"%'";
		}
		if(!StringUtil.isEmpty(couponsSearch.getCpns_prefix())){
			sql += " and c.cpns_prefix like '%"+couponsSearch.getCpns_prefix()+"%'";
		}
		if(!StringUtil.isEmpty(couponsSearch.getCpns_type())){
			sql += " and c.cpns_type = '"+couponsSearch.getCpns_type()+"'";
		}
		if(!StringUtil.isEmpty(couponsSearch.getCpns_status())){
			sql += " and c.cpns_status = '"+couponsSearch.getCpns_status()+"'";
		}
		if(!StringUtil.isEmpty(couponsSearch.getPmt_time_begin())){
			sql += " and p.pmt_time_begin >= "+DBTUtil.to_date(couponsSearch.getPmt_time_begin(), 1)+"";
		}
		if(!StringUtil.isEmpty(couponsSearch.getPmt_time_end())){
			sql += " and p.pmt_time_end <= "+DBTUtil.to_date(couponsSearch.getPmt_time_end(), 1)+"";
		}
	 
		sql += " order by " + order;
		Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize);
		return page;
	}

	
	@Override
	public void add(Coupons coupons) {
		if (coupons.getCpns_name() == null)
			throw new IllegalArgumentException(
					"param coupons.cpns_name is NULL");
		if (coupons.getCpns_prefix() == null)
			throw new IllegalArgumentException(
					"param coupons.cpns_prefix is NULL");
//		if (coupons.getPmt_id().equals("0"))
//			throw new IllegalArgumentException("param coupons.pmt_id is 0");
		coupons.setDisabled("false");
		AdminUser au = ManagerUtils.getAdminUser();
		String userid = au.getUserid();
		if(Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
			userid = au.getUserid();
		if(ManagerUtils.isAdminUser())
			userid="-1";
		coupons.setUserid(userid);
		this.baseDaoSupport.insert("coupons", coupons);
	}

	
	@Override
	public void saveExchange(String cpnsId, Integer point) {
		AdminUser au = ManagerUtils.getAdminUser();
		String userid = au.getUserid();
		if (cpnsId == null)
			throw new IllegalArgumentException(
					"param cpnsId is NULL");
		if (point == null)
			throw new IllegalArgumentException(
					"param point is NULL");
		String sql = SF.goodsSql("SAVE_EXCHANGE");
		
		if(Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
			userid = au.getUserid();
		if(ManagerUtils.isAdminUser())
			userid = "-1";
		sql+= ",userid="+userid;
		sql+=" where cpns_id = ?";
		this.baseDaoSupport.execute(sql, point, cpnsId);

	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(String[] cpnsIdArray, String[] pmtIdArray) {
		if ((cpnsIdArray != null) && (pmtIdArray != null)) {
			if (cpnsIdArray.length != pmtIdArray.length)
				throw new IllegalArgumentException(
						"param cpnsIdArray.length and pmtIdArray.length is not equals");
			if (cpnsIdArray.length > 0) {
				String ids = StringUtil.arrayToString(cpnsIdArray, ",");
				this.baseDaoSupport.execute(SF.goodsSql("GOODS_COUPONS_DEL") + " and cpns_id in (" + ids + ")");
				ids = StringUtil.arrayToString(pmtIdArray, ",");
				if(ids!=null && !"".equals(ids))
					this.baseDaoSupport.execute(SF.goodsSql("GOODS_COUPONS_DEL_0") + " and pmt_id in (" + ids +")");
				
			}
		}
	}

	
	@Override
	public void edit(Coupons coupons) {
		if (coupons.getCpns_id().equals("0"))
			throw new IllegalArgumentException("param coupons.cpns_id is 0");
		if (coupons.getCpns_name() == null)
			throw new IllegalArgumentException(
					"param coupons.cpns_name is NULL");
		if (coupons.getCpns_prefix() == null)
			throw new IllegalArgumentException(
					"param coupons.cpns_prefix is NULL");
		if (coupons.getPmt_id().equals("0"))
			throw new IllegalArgumentException("param coupons.pmt_id is 0");
		coupons.setDisabled("false");
		this.baseDaoSupport.update("coupons", coupons, "cpns_id="
				+ coupons.getCpns_id());

	}

	
	@Override
	public List listCanExchange() {
		String sql = SF.goodsSql("LIST_CAN_EXCHANGE");
		List list = this.baseDaoSupport.queryForList(sql);
		return list;
	}

	
	@Override
	public Page listExchange(int pageNo, int pageSize) {
		//Long curTime = (new Date()).getTime();
		String sql = SF.goodsSql("LIST_EXCHANGE");
		
		AdminUser au = ManagerUtils.getAdminUser();
		String userid = au.getUserid();
		if(Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype())){
			userid = au.getUserid();
		}
		if(!ManagerUtils.isAdminUser()){
			sql+=" and c.userid = "+userid;
		}
		
		Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize);
		
		return page;
	}

	
	@Override
	public Coupons get(String cpnsid) {
		if (cpnsid == null)
			throw new IllegalArgumentException("param cpnsid is null");
		Coupons coupons = this.baseDaoSupport.queryForObject(SF.goodsSql("GET_COUPONS_BY_ID"), Coupons.class,
				cpnsid);
		if (coupons == null)
			throw new IllegalArgumentException("coupons is null");
		return coupons;
	}

	
	@Override
	public void deleteExchange(String[] cpnsIdArray) {
		if(cpnsIdArray != null && cpnsIdArray.length>0){
			String ids = StringUtil.arrayToString(cpnsIdArray, ",");
			this.baseDaoSupport.execute(SF.goodsSql("COUPONS_UPDATE") + " and cpns_id in (" + ids + ")");
		}

	}


	@Override
	public void updateUseTimes(String member_id, String cpns_code, int addUseTimes) {
		String sql = SF.goodsSql("UPDATE_USETIMES");
		this.baseDaoSupport.execute(sql, addUseTimes,cpns_code,member_id);
	}


	@Override
	public boolean hascouponUseTimes(String coupon_code, String memberr_id) {
		String str_mc_use_times = settingService.getSetting("coupons","coupon.mc.use_times");
		int mc_use_times = str_mc_use_times == null ? 1 : Integer.valueOf(str_mc_use_times);
		String sql = SF.goodsSql("HAS_COUPONUSE_TIMES");
		int count = this.baseDaoSupport.queryForInt(sql, mc_use_times,coupon_code,memberr_id);
		return count>0;
	}


	@Override
	public void updateCouponName(Coupons coupons) {
		String sql = SF.goodsSql("UPDATE_COUPON_NAME");
		this.baseDaoSupport.execute(sql, coupons.getCpns_name(),coupons.getCpns_status(),coupons.getCpns_id());
	}

}
