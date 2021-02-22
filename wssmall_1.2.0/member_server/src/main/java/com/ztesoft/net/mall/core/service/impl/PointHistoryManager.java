package com.ztesoft.net.mall.core.service.impl;

import java.util.List;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.PointHistory;
import com.ztesoft.net.mall.core.service.IPointHistoryManager;
import com.ztesoft.net.sqls.SF;

/**
 * 会员积分日志
 * 
 * @author lzf<br/>
 *         2010-3-22 上午11:27:23<br/>
 *         version 1.0<br/>
 */
public class PointHistoryManager extends BaseSupport implements
		IPointHistoryManager {

	
	@Override
	public Page pagePointHistory(int pageNo, int pageSize,int pointType) {
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		String sql = SF.memberSql("MEMBER_PAGE_POINT_HISTORY");
		Page webpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize,
				member.getMember_id(),pointType);
		return webpage;
	}

	
	@Override
	public Long getConsumePoint(int pointType) {
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		//select SUM(point) from point_history where  member_id = ?  and  type=0  and point_type=?
		String sql = SF.memberSql("MEMBER_GET_CONSUME_POINT");
		Long result = this.baseDaoSupport.queryForLong(sql, member.getMember_id(),pointType);
		return result;
	}

	
	@Override
	public Long getGainedPoint(int pointType) {
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		//select SUM(point) from point_history where    member_id = ? and  type=1  and point_type=?
		String sql = SF.memberSql("MEMBER_GET_GAINED_POINT");
		Long result = this.baseDaoSupport
				.queryForLong(sql, member.getMember_id(),pointType);
		return result;
	}

	
	@Override
	public void addPointHistory(PointHistory pointHistory) {
		this.baseDaoSupport.insert("point_history", pointHistory);
	}

	
	@Override
	public List listPointHistory(String member_id) {
		String sql = SF.memberSql("MEMBER_LIST_POINT_HISTORY");
		List list = this.baseDaoSupport.queryForList(sql,PointHistory.class, member_id);
		return list;
	}


	

}
