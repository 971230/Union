package com.ztesoft.net.mall.core.service.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.app.base.core.service.ISettingService;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.PointHistory;
import com.ztesoft.net.mall.core.service.IMemberLvManager;
import com.ztesoft.net.mall.core.service.IMemberManager;
import com.ztesoft.net.mall.core.service.IMemberPointManger;
import com.ztesoft.net.mall.core.service.IPointHistoryManager;
import com.ztesoft.net.sqls.SF;

/**
 * 会员积分管理
 * @author kingapex
 *
 */
public class MemberPointManger extends BaseSupport implements IMemberPointManger {
	
	private IPointHistoryManager pointHistoryManager;
	private IMemberManager memberManager;
	private IMemberLvManager memberLvManager;
	private ISettingService settingService ;
	
	@Override
	public boolean checkIsOpen(String itemname) {
		String value = settingService.getSetting("point", itemname);
		
		return "1".equals(value);
	}

	 
	@Override
	public int getItemPoint(String itemname) {
		String value = settingService.getSetting("point", itemname);
		if(StringUtil.isEmpty(value)){
			return 0;
		}
		return Integer.valueOf(value);
	}

	/**
	 * 消费积分
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)  
	public void useMarketPoint(String memberid,int point,String reson,String relatedId){
		PointHistory pointHistory = new  PointHistory();
		pointHistory.setMember_id(memberid);
		pointHistory.setOperator("member");
		pointHistory.setPoint(point);
		pointHistory.setReason(reson);
		pointHistory.setType(0);
		pointHistory.setPoint_type(1);
		pointHistory.setTime(DBTUtil.current());
		pointHistory.setRelated_id(relatedId);
		
		pointHistoryManager.addPointHistory(pointHistory);
		String sql = SF.memberSql("MEMBER_USE_MARKET_POINT");
		this.baseDaoSupport.execute(sql, point,memberid); 
	}
	
	/**
	 * 等级积分
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)  
	public void add(String memberid, int point, String itemname,String relatedId) {
		PointHistory pointHistory = new  PointHistory();
		pointHistory.setMember_id(memberid);
		pointHistory.setOperator("member");
		pointHistory.setPoint(point);
		pointHistory.setReason(itemname);
		pointHistory.setType(1);
		pointHistory.setPoint_type(0);
		pointHistory.setTime(DBTUtil.current());
		pointHistory.setRelated_id(relatedId);
		
		pointHistoryManager.addPointHistory(pointHistory);
		 
		Member member  = this.memberManager.get(memberid);
		Integer memberpoint  = member.getPoint();

		String sql = SF.memberSql("MEMBER_ADD_POINT_GRADE");
		this.baseDaoSupport.execute(sql, point,memberid); 
		
		//改变会员等级
		/*if(memberpoint!=null ){
			MemberLv lv =  this.memberLvManager.getByPoint(memberpoint);
			if(lv!=null ){
				if(member.getLv_id()==null ||!(lv.getLv_id()).equals(member.getLv_id())){
					this.memberManager.updateLv(memberid, lv.getLv_id());
					
				}
			}
		}*/
	}


	public IPointHistoryManager getPointHistoryManager() {
		return pointHistoryManager;
	}


	public void setPointHistoryManager(IPointHistoryManager pointHistoryManager) {
		this.pointHistoryManager = pointHistoryManager;
	}


	public ISettingService getSettingService() {
		return settingService;
	}


	public void setSettingService(ISettingService settingService) {
		this.settingService = settingService;
	}


	public IMemberManager getMemberManager() {
		return memberManager;
	}


	public void setMemberManager(IMemberManager memberManager) {
		this.memberManager = memberManager;
	}


	public IMemberLvManager getMemberLvManager() {
		return memberLvManager;
	}


	public void setMemberLvManager(IMemberLvManager memberLvManager) {
		this.memberLvManager = memberLvManager;
	}

}
