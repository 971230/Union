package com.ztesoft.net.mall.core.service.impl;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.GoodsActionRule;
import com.ztesoft.net.mall.core.model.GoodsRule;
import com.ztesoft.net.mall.core.model.GoodsRules;
import com.ztesoft.net.mall.core.model.GoodsUsers;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.service.IGoodsUserManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;
import org.apache.commons.lang.StringUtils;

import vo.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsUserManagerImpl extends BaseSupport implements IGoodsUserManager {
    private IGoodsManager goodsManager;

    @Override
    public void add(GoodsUsers goodsUser, GoodsRules goodsRules) {
        // TODO Auto-generated method stub
        goodsUser.setConfirm_user_id(goodsUser.getQr_user_id());
        goodsUser.setConfirm_group_id(goodsUser.getQr_group_id());
        AdminUser adminUser = ManagerUtils.getAdminUser();
        int user_type = adminUser.getFounder();
        String userid = adminUser.getUserid();
        String[] goodsId = goodsUser.getGoodsIds();

        for (int i = 0; i < goodsId.length; i++) {
            String id = goodsId[i];
            goodsUser.setGoods_id(id);

            String userType = user_type + "";
            int count = this.getGoodsUserCount(id, goodsUser.getService_code());
            if (count > 0) {
                this.baseDaoSupport.update("es_goods_users", goodsUser, "goods_id=" + id + " and service_code=" + goodsUser.getService_code());
            } else {
                goodsUser.setCreate_time(DBTUtil.current());
                goodsUser.setDisabled("0");
                goodsUser.setUser_type(userType);
                goodsUser.setOper_id(userid);
                this.baseDaoSupport.insert("es_goods_users", goodsUser);

            }
            if (goodsRuleCount(id, goodsUser.getService_code()) > 0) {
                goodsManager.editGoodsRules(goodsRules, id, goodsUser.getService_code());
            } else {
                goodsManager.saveGoodsRules(goodsRules, id, goodsUser.getService_code());
            }


        }
    }

    public int goodsRuleCount(String goods_id, String service_code) {
        int count = 0;
        String sql = SF.goodsSql("GOODS_RULE_COUNT");
        count = this.baseDaoSupport.queryForInt(sql, goods_id, service_code);
        return count;
    }

    @Override
    public void editSave(GoodsUsers goodsUser) {
        // TODO Auto-generated method stub
        this.baseDaoSupport.update("es_goods_users", goodsUser, "goods_id=" + goodsUser.getGoods_id() + " and user_type=" + goodsUser.getUser_type());

    }

    @Override
	public GoodsUsers edit(String goods_id, String user_type, String service_code) {
        GoodsUsers goodsUser = null;
        String sql = SF.goodsSql("GOODS_USERS_SELECT");
        goodsUser = (GoodsUsers) this.baseDaoSupport.queryForObject(sql, GoodsUsers.class, goods_id, user_type, service_code);
        this.setGroupName(goodsUser);
        return goodsUser;

    }

    public void setGroupName(GoodsUsers goodsUser) {
        goodsUser.setQr_group_name(this.getGroupName(goodsUser.getConfirm_group_id()));
        goodsUser.setPay_group_name(this.getGroupName(goodsUser.getPay_group_id()));
        goodsUser.setShip_group_name(this.getGroupName(goodsUser.getShip_group_id()));
        goodsUser.setAccept_group_name(this.getGroupName(goodsUser.getAccept_group_id()));
        goodsUser.setQuery_group_name(this.getGroupName(goodsUser.getQuery_group_id()));
    }

    public String getGroupName(String group_id) {
        String group_name = "";
        String sql = SF.goodsSql("GET_GROUP_NAME");
        if (group_id != null && !"".equals(group_id)) {
            group_name = this.baseDaoSupport.queryForString(sql, group_id);
        }
        return group_name;
    }

    @Override
    public List getGroupUser(String group_id) {
        // TODO Auto-generated method stub
        String sql = SF.goodsSql("ORDER_GROUP_ROLE_REL_SELECT");
        String userSql = SF.goodsSql("ADMINUSER_SELECT");
        List list = this.baseDaoSupport.queryForList(sql, group_id);
        Map<String, String> map = new HashMap<String, String>();
        System.out.print(list);
        if (list.size() != 0) {
            map = (Map<String, String>) list.get(0);
        } else {
            return null;
        }
        //Map<String,String> map = this.baseDaoSupport.queryForMap(sql, group_id);
        if (!StringUtils.isEmpty(map.get("userid"))) {
            String userIdStr = map.get("userid");
            userSql += "where userid in (" + userIdStr + ")";
            if (!StringUtils.isEmpty(map.get("role_id"))) {
                String roleIsStr = map.get("role_id");
                userSql += " or userid in (select distinct(userid) from  es_user_role where roleid in(" + roleIsStr + "))";
            }
        } else {
            if (!StringUtils.isEmpty(map.get("role_id"))) {
                String roleIsStr = map.get("role_id");
                userSql += " where  userid in (select distinct(userid) from  es_user_role where roleid in(" + roleIsStr + "))";
            }
        }

        return this.baseDaoSupport.queryForList(userSql);
    }

    @Override
    public Page goodsList(String goods_id, String goods_name, int pageSize,
                          int pageNo) {
        // TODO Auto-generated method stub
        String sql = SF.goodsSql("GOODS_ALL_LIST_SELECT");
        if (goods_id != null && !"".equals(goods_id)) {
            sql += "and goods_id like '%" + goods_id + "%' ";
        }
        if (goods_name != null && !"".equals(goods_name)) {
            sql += "and name like '%" + goods_name + "%'";
        }

        return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
    }

    @Override
    public Page groupList(String group_type, int pageSize, int pageNo) {
        // TODO Auto-generated method stub
        int founder = ManagerUtils.getFounder();
        String sql = SF.goodsSql("ORDER_GROUP_DEF_SELECT");
        if (founder == 1) {
            return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, group_type);
        } else {
            String oper_id = ManagerUtils.getUserId();
            sql += " and oper_id = ?";
            return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, group_type, oper_id);
        }
    }

    @Override
    public Page goodsUserList(String goods_name, String user_type, String service_code,
                              int pageSize, int pageNo) {
        String countSql = SF.goodsSql("GOODS_USER_COUNT");
        String sql = SF.goodsSql("GOODS_USER_LIST");
        if (StringUtils.isNotBlank(goods_name)) {
            sql += " and g.name like '%" + goods_name + "%'";
            countSql += " and g.name like '%" + goods_name + "%'";
        }
        if (StringUtils.isNotBlank(user_type) && !("-1").equals(user_type)) {
            sql += " and u.user_type =" + user_type;
            countSql += " and u.user_type =" + user_type;
        }
        if (StringUtils.isNotBlank(service_code) && !("-1").equals(service_code)) {
            sql += " and u.service_code =" + service_code;
            countSql += " and u.service_code =" + service_code;
        }
        sql += " order by u.create_time desc";
        return this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, GoodsUsers.class, countSql);
    }

    public String getRuleSql(String group_type) {
        String sql = " (select status from es_goods_action_rule goodsActionRule where goodsActionRule.Goods_Id = goodsUser.Goods_Id and goodsActionRule.service_code = goodsUser.service_code and goodsActionRule.Action_Code ='" + group_type + "' ) ";
        return sql;
    }

    @Override
    public Map<String, Object> getListMap(GoodsUsers goodsUser) {
        // TODO Auto-generated method stub
        Map<String, Object> map = new HashMap<String, Object>();
        String confirm = goodsUser.getConfirm_group_id();
        String pay = goodsUser.getPay_group_id();
        String accept = goodsUser.getAccept_group_id();
        String ship = goodsUser.getShip_group_id();
        String query = goodsUser.getQuery_group_id();
        String confirmUserId = goodsUser.getConfirm_user_id();
        String payUserId = goodsUser.getPay_user_id();
        String shipUserId = goodsUser.getShip_user_id();
        String acceptUserId = goodsUser.getAccept_user_id();
        String queryUserId = goodsUser.getQuery_user_id();
        List confirmList = null;
        List payList = null;
        List acceptList = null;
        List shipList = null;
        List queryList = null;

        String[] confirmUserList = null;
        String[] payUserList = null;
        String[] shipUserList = null;
        String[] acceptUserList = null;
        String[] queryUserList = null;
        if (confirmUserId != null && !"".equals(confirmUserId)) {
            confirmUserList = confirmUserId.split(",");
            // changeString(confirmUserList);
        }
        if (payUserId != null && !"".equals(payUserId)) {
            payUserList = payUserId.split(",");
            //changeString(payUserList);
        }
        if (shipUserId != null && !"".equals(shipUserId)) {
            shipUserList = shipUserId.split(",");
            //changeString(shipUserList);
        }
        if (acceptUserId != null && !"".equals(acceptUserId)) {
            acceptUserList = acceptUserId.split(",");
            //changeString(acceptUserList);
        }
        if (queryUserId != null && !"".equals(queryUserId)) {
            queryUserList = queryUserId.split(",");
            //changeString(queryUserList);
        }


        if (confirm != null && !"".equals(confirm)) {
            confirmList = this.getGroupUser(confirm);
        }
        if (pay != null && !"".equals(pay)) {
            payList = this.getGroupUser(pay);
        }
        if (accept != null && !"".equals(accept)) {
            acceptList = this.getGroupUser(accept);
        }
        if (ship != null && !"".equals(ship)) {
            shipList = this.getGroupUser(ship);
        }
        if (query != null && !"".equals(query)) {
            queryList = this.getGroupUser(query);
        }
        map.put("qrList", confirmList);
        map.put("payList", payList);
        map.put("acceptList", acceptList);
        map.put("shipList", shipList);
        map.put("queryList", queryList);
        map.put("confirmUserList", confirmUserList);
        map.put("payUserList", payUserList);
        map.put("shipUserList", shipUserList);
        map.put("acceptUserList", acceptUserList);
        map.put("queryUserList", queryUserList);
        return map;
    }

    public void changeString(String[] str) {
        for (int i = 0; i < str.length; i++) {
            int str1 = str[i].length();
            if ("self".equals(str[i])) {
                str[i] = "mine";
            }
            if ("parent".equals(str[i])) {
                str[i] = "upper";
            }
        }
    }

    @Override
    public GoodsRules getGoodsRules(String goods_id, String service_code) {
        List<GoodsActionRule> ruleList = new ArrayList<GoodsActionRule>();
        ruleList = this.goodsManager.queryGoodsRules(goods_id, service_code);
        GoodsRules goodsRules = new GoodsRules();

        if (ruleList != null && !ruleList.isEmpty()) {
            for (int i = 0; i < ruleList.size(); i++) {
                GoodsActionRule goodsActionRule = ruleList.get(i);
                String action_code = goodsActionRule.getAction_code();
                if (Consts.GOODS_RULES_CONFIRM.equals(action_code)) {
                    goodsRules.setInsure(goodsActionRule.getStatus());
                } else if (Consts.GOODS_RULES_PAY.equals(action_code)) {
                    goodsRules.setPay(goodsActionRule.getStatus());
                } else if (Consts.GOODS_RULES_ACCEPT.equals(action_code)) {
                    goodsRules.setAccept(goodsActionRule.getStatus());
                } else if (Consts.GOODS_RULES_DELIVERY.equals(action_code)) {
                    goodsRules.setDelivery(goodsActionRule.getStatus());
                } else if (Consts.GOODS_RULES_QUERY.equals(action_code)) {
                    goodsRules.setQuery(goodsActionRule.getStatus());
                }
            }
        }
        return goodsRules;
    }

    public IGoodsManager getGoodsManager() {
        return goodsManager;
    }

    public void setGoodsManager(IGoodsManager goodsManager) {
        this.goodsManager = goodsManager;
    }

    @Override
    public void delGoodsUser(String goods_id, String user_type, String service_code) {
        // TODO Auto-generated method stub
        String sql = SF.goodsSql("DEL_GOODS_USER");
        String sql_1 = SF.goodsSql("DEL_GOODS_RULE");
        this.baseDaoSupport.execute(sql_1, goods_id, service_code);
        this.baseDaoSupport.execute(sql, goods_id, user_type, service_code);

    }

    @Override
    public int getGoodsUserCount(String goods_id, String service_code) {
        // TODO Auto-generated method stub
        int count = 0;
        String sql = SF.goodsSql("GET_GOODS_USERCOUNT");
        count = this.baseDaoSupport.queryForInt(sql, goods_id, service_code);
        return count;
    }
    
    
    @Override
	public List qryServiceList(){
    	String sql = SF.goodsSql("QRT_SERVICE_CODE_LIST");
    	
    	return this.baseDaoSupport.queryForList(sql);
    }
    
    @Override
	public Page qryRuleList(Rule rule,int pageNo,int pageSize){
		
		StringBuffer sql = new StringBuffer(SF.goodsSql("QRY_RULE_LIST"));
		StringBuffer cSql = new StringBuffer(SF.goodsSql("QRY_RULE_LIST_COUNT"));
		StringBuffer where = new StringBuffer();
		
		if(StringUtils.isNotEmpty(rule.getRule_name())){
			where.append(" AND a.rule_name LIKE '%"+rule.getRule_name()+"%'");
		}
		if(StringUtils.isNotEmpty(rule.getRule_type())){
			where.append(" AND a.rule_type = '"+rule.getRule_type()+"'");
		}
		if(StringUtils.isNotEmpty(rule.getRule_time_begin())){
			where.append(" AND a.rule_time_begin >= "+DBTUtil.to_date(rule.getRule_time_begin(), 1)+"");
		}
		if(StringUtils.isNotEmpty(rule.getRule_time_end())){
			where.append(" AND a.rule_time_end <= "+DBTUtil.to_date(rule.getRule_time_end(), 1)+"");
		}
		if(StringUtils.isNotEmpty(rule.getService_code())){
			where.append(" AND EXISTS (SELECT a.rule_id");
			where.append(" FROM es_service_offer b, es_service_offer_rule c");
			where.append(" WHERE b.service_id = c.service_id");
			where.append(" AND b.source_from = '"+ManagerUtils.getSourceFrom()+"'");
			where.append(" AND c.source_from = '"+ManagerUtils.getSourceFrom()+"'");
			where.append(" AND a.rule_id = c.rule_id");
			where.append(" AND b.service_code = '"+rule.getService_code()+"')");
		}
		
		
		where.append(" AND a.disabled = '0' ORDER BY a.create_time DESC");
		
		return this.baseDaoSupport.queryForCPage(sql.append(where).toString(), pageNo, pageSize, 
													Rule.class, cSql.append(where).toString());
	}
    
    
    
    @Override
	public void saveGoodsServiceRule(GoodsUsers goodsUsers,String[] rule_ids){
    	
    	String[] goods_ids = goodsUsers.getGoodsIds();
    	for (int i = 0; i < goods_ids.length; i++) {
			String goods_id = goods_ids[i];
			String sql = "DELETE FROM es_goods_rule WHERE goods_id = ?";
			this.baseDaoSupport.execute(sql, goods_id);
			if(rule_ids != null){
				for (int j = 0; j < rule_ids.length; j++) {
					String rule_id = rule_ids[j];
					GoodsRule goodsRule = new GoodsRule();
					goodsRule.setRule_id(rule_id);
					goodsRule.setService_code(goodsUsers.getService_code());
					goodsRule.setSource_from(ManagerUtils.getSourceFrom());
					goodsRule.setGoods_id(goods_id);
					this.baseDaoSupport.insert("es_goods_rule", goodsRule);
				}
			}
		}
    	
    }
    
    
    @Override
	public List<Rule> listRule(String goods_id,String service_code){
    	
    	String sql = SF.goodsSql("QRY_RULE_BY_GOODS_SERVICE");
    	
    	return this.baseDaoSupport.queryForList(sql, Rule.class, goods_id, service_code);
    }

}
