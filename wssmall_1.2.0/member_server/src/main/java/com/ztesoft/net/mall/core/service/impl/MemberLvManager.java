package com.ztesoft.net.mall.core.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis.utils.StringUtils;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.app.base.core.model.MemberLv;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.service.IMemberLvManager;
import com.ztesoft.net.mall.core.service.IMemberManager;
import com.ztesoft.net.sqls.SF;
 

/**
 * 会员级别管理
 * @author kingapex
 *2010-4-29下午11:04:43
 */
public class MemberLvManager extends BaseSupport<MemberLv> implements IMemberLvManager{

	private IMemberManager memberManager;
	public static final String GOODS_LV_STATUS = "00A";
	@Override
	public void add(MemberLv lv) {
		
		this.baseDaoSupport.insert("member_lv", lv);
	}

	
	@Override
	public void edit(MemberLv lv) {
		this.baseDaoSupport.update("member_lv", lv, "lv_id=" + lv.getLv_id());
	}

	
	@Override
	public String getDefaultLv() {
		String sql = SF.memberSql("SERVICE_GET_MEMBER_LV_1");
		List<MemberLv> lvList  = this.baseDaoSupport.queryForList(sql, MemberLv.class);
		if(lvList==null || lvList.isEmpty()){
			return null;
		}
		
		return lvList.get(0).getLv_id();
	}

	
	@Override
	public Page list(String order, int page, int pageSize) {
		order = order == null ? " lv_id desc" : order;
		
        String sql = SF.memberSql("SERVICE_MEMBER_LV_LIST") + " ORDER BY " + order;
        StringBuffer buffer=new StringBuffer(300);
        buffer.append(sql);
		Page webpage = this.baseDaoSupport.queryForPage(buffer.toString(), page, pageSize);
		return webpage;
	}

	
	@Override
	public MemberLv get(String lvId) {
		if(!StringUtil.isEmpty(lvId)){
			String sql = SF.memberSql("SERVICE_GET_MEMBER_LV_BY_ID");
			MemberLv lv = this.baseDaoSupport.queryForObject(sql, MemberLv.class, lvId);
			return lv;
		}else{
			return null;
		}
	}

	
	@Override
	public void delete(String id) {
		if (id == null || id.equals(""))
			return;
		String sql = SF.memberSql("SERVICE_DEL_MEMBER_LV_BY_ID") + " and lv_id in (" + id + ")";
		this.baseDaoSupport.execute(sql);
	}

	
	@Override
	public List<MemberLv> list() {
		String sql = SF.memberSql("SERVICE_MEMBER_LV_ORDER_BY_LV_ID");
		List lvlist = this.baseDaoSupport.queryForList(sql, MemberLv.class);
		return lvlist;
	}

	@Override
	public List<MemberLv> listByLv_idsAndGoods_id(String lvIDs, String goods_id){
		String sql = SF.memberSql("MEMBER_QRYMLV_BY_IDS_AND_GOODSID").replace(":instr", lvIDs);
        List<MemberLv> list = this.baseDaoSupport.queryForList(sql, MemberLv.class, new String[]{GOODS_LV_STATUS, goods_id});
		return list;
	}	
	@Override
	public List<MemberLv> list(String ids) {
		
		if( StringUtil.isEmpty(ids)) return new ArrayList();
		
		String sql = SF.memberSql("SERVICE_GET_MEMBER_LV_BY_IDS").replace("replaceSql", ids);
		List lvlist = this.baseDaoSupport.queryForList(sql, MemberLv.class);
		return lvlist;
		 
	}

	@Override
	public MemberLv getByPoint(int point) {
		//add by wui 个人客户才允许更新等级， 普通客户、银牌客户、金牌客户
		String sql = SF.memberSql("SERVICE_GET_MEMBER_LV_BY_GRADE");
		List<MemberLv> list =this.baseDaoSupport.queryForList(sql, MemberLv.class,point);
		if(list==null || list.isEmpty())
		return null;
		else
			return list.get(0);
	}
	
	@Override
	public List qryMemberLvList(String member_id) {
		
		String sql = SF.memberSql("SERVICE_GET_MEMBER_LIST_ALL");
		if(!StringUtils.isEmpty(member_id)){
			Member member = memberManager.get(member_id);
			sql += " and lv_id in ("+member.getLv_ids()+" ) ";
		}
		List<MemberLv> list = this.baseDaoSupport.queryForList(sql, MemberLv.class);
		if(list == null) list = new ArrayList();
		return list;
	}


	public IMemberManager getMemberManager() {
		return memberManager;
	}


	public void setMemberManager(IMemberManager memberManager) {
		this.memberManager = memberManager;
	}

}
