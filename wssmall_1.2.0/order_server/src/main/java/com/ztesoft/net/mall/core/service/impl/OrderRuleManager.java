package com.ztesoft.net.mall.core.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import vo.Rule;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.GoodsUsers;
import com.ztesoft.net.mall.core.service.IOrderRuleManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;
import commons.CommonTools;

/**
 * Saas式Goods规则业务管理
 *
 * @author wui
 *         2011-10-11
 */
public class OrderRuleManager extends BaseSupport implements IOrderRuleManager {
    @Override
	public List<Rule> getRulesByGoodsId(String goods_id,String service_code) {
        String sql = SF.goodsSql("ORDER_RULE_MANAGER");
        return this.baseDaoSupport.queryForList(sql, Rule.class, goods_id,service_code);
    }
    
    @Override
	public List<Rule> getRulesByServiceId(String service_code){
    	String sql = SF.goodsSql("GET_RULES_BY_SERVICEID");
    	return this.baseDaoSupport.queryForList(sql, Rule.class, CommonTools.getSourceForm(), service_code);
    }

    @Override
	@SuppressWarnings("unchecked")
    public List<Rule> getRulesByGoodsIdAndRuleType(String goods_id, String rule_type,String service_code) {
        List<Rule> rules = getRulesByGoodsId(goods_id,service_code);
        List nRules = new ArrayList<Rule>();
        for (int i = 0; i < rules.size(); i++) {
            Rule rule = rules.get(i);
            if (rule_type.equals(rule.getRule_type()))
                nRules.add(rule);
        }
        return nRules;
    }

    /**
     * 获取商品归属工号信息，包括受理、发货、支付、确认
     */
	@Override
	public GoodsUsers getGoodsUsersByGoodsId(String goods_id,String source_from,String service_code) {
		String whereSql ="";
		if(StringUtils.isNotEmpty(service_code))
			whereSql+= "and service_code = '"+service_code+"'"; //source_from
		String querySql = SF.goodsSql("GOODS_USERS_SELECT_BY_GOODS_ID") + whereSql;
		GoodsUsers users =null;
		List<GoodsUsers>  usersl= this.baseDaoSupport.queryForList(querySql,GoodsUsers.class, goods_id);
		if(usersl.size() ==0)
		{
			/* querySql = SF.goodsSql("GOODS_USERS_SELECT_BY_GOODS_ID");
			 List<GoodsUsers> users2= this.baseDaoSupport.queryForList(querySql,GoodsUsers.class, goods_id);
			 if(!ListUtil.isEmpty(users2))
				 users = users2.get(0);*/
			return null;
		}else{
			users = usersl.get(0);
		}
		return users;
	}
	
	
	/**
	 * 工号转换器,判断是否指定父级
	 * @param goodsUser
	 * @return
	 */
	@Override
	public GoodsUsers dealTransferGoodsUsers(GoodsUsers goodsUser) {
		//受理
//		if(Consts.GROUD_USER_SUPER.equals(goodsUser.getAccept_user_id()))
//		{
//			AdminUser adminUser = ManagerUtils.getAdminUser();
//			goodsUser.setAccept_user_id(adminUser.getParuserid());
//		}else if(Consts.GROUD_USER_SELF.equals(goodsUser.getAccept_user_id()))
//		{
//			AdminUser adminUser = ManagerUtils.getAdminUser();
//			goodsUser.setAccept_user_id(adminUser.getUserid());
//		}
//		
//		//确认
//		if(Consts.GROUD_USER_SUPER.equals(goodsUser.getConfirm_user_id()))
//		{
//			AdminUser adminUser = ManagerUtils.getAdminUser();
//			goodsUser.setConfirm_user_id(adminUser.getParuserid());
//		}else if(Consts.GROUD_USER_SELF.equals(goodsUser.getConfirm_user_id()))
//		{
//			AdminUser adminUser = ManagerUtils.getAdminUser();
//			goodsUser.setConfirm_user_id(adminUser.getUserid());
//		}
//		
//		//支付
//		if(Consts.GROUD_USER_SUPER.equals(goodsUser.getPay_user_id()))
//		{
//			AdminUser adminUser = ManagerUtils.getAdminUser();
//			goodsUser.setPay_user_id(adminUser.getParuserid());
//		}else if(Consts.GROUD_USER_SELF.equals(goodsUser.getPay_user_id()))
//		{
//			AdminUser adminUser = ManagerUtils.getAdminUser();
//			goodsUser.setPay_user_id(adminUser.getUserid());
//		}
//		//发货
//		if(Consts.GROUD_USER_SUPER.equals(goodsUser.getShip_user_id()))
//		{
//			AdminUser adminUser = ManagerUtils.getAdminUser();
//			goodsUser.setShip_user_id(adminUser.getParuserid());
//		}else if(Consts.GROUD_USER_SELF.equals(goodsUser.getShip_user_id()))
//		{
//			AdminUser adminUser = ManagerUtils.getAdminUser();
//			goodsUser.setShip_user_id(adminUser.getUserid());
//		}
		return goodsUser;
	}

	@Override
	public List<Rule> queryRulesByService(String service_code, String type,
			String goods_id) {
		String sql1 = SF.goodsSql("GET_RULES_BY_SERVICEID_TYPE");
		String sql2 = SF.goodsSql("ORDER_RULE_MANAGER_TYPE");
		String sql = "select eee.* from ("+sql1 + " union " + sql2+") eee";
		return this.baseDaoSupport.queryForList(sql, Rule.class, type,ManagerUtils.getSourceFrom(),service_code,type,goods_id,service_code);
	}
	
}