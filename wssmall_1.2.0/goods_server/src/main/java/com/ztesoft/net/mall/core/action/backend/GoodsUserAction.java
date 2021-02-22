package com.ztesoft.net.mall.core.action.backend;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.GoodsRules;
import com.ztesoft.net.mall.core.model.GoodsUsers;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IGoodsUserManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import params.adminuser.req.UserFounderReq;
import params.adminuser.resp.UserFounderResp;
import services.AdminUserInf;
import vo.Rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsUserAction extends WWAction {
    private IGoodsUserManager goodsUserManager;
    private IDcPublicInfoManager dcPublicInfoManager;
    private AdminUserInf adminUserServ;
    private GoodsUsers goodsUser;
    private GoodsRules goodsRules;
    private String group_id;
    private String goods_id;
    private String goods_name;
    private String group_type;
    private List userList;
    private List typeList;
    private List groupList;
    private String qr_group_id;
    private String qr_user_id;
    private String goodsName;
    private String groupName;
    private String user_type;
    private Map<String, Object> groupUserMap;
    private Map<String, String> typeMap;
    private String[] goodsIds;
    private String[] goodsNames;
    private String[] qrUserIds;
    private String[] payUserIds;
    private String[] delveryUserIds;
    private String[] acceptUserIds;
    private String[] queryUserIds;
    private String service_code;
    private String cur_userId;
    private Rule rule;
    private List<Rule> ruleList;
    private List ruleTypeList;
    private List serviceCodeList;
    private String[] rule_ids;
    private int founder;

    public String goodsUserList() {
        this.cur_userId = ManagerUtils.getUserId();
        this.founder = ManagerUtils.getAdminUser().getFounder();
        DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
        this.typeList = dcPublicCache.getList("111102");
        this.serviceCodeList = this.goodsUserManager.qryServiceList();

        UserFounderResp userFounderResp = new UserFounderResp();
        UserFounderReq userFounderReq = new UserFounderReq();
        userFounderResp = this.adminUserServ.founderMap(userFounderReq);

        typeMap = new HashMap<String, String>();
        if (userFounderResp != null) {
            typeMap = userFounderResp.getMap();
        }

        this.webpage = this.goodsUserManager.goodsUserList(goodsName, user_type, service_code, this.getPageSize(), this.getPage());
        return "goodsUserList";
    }

    public String goodsUser() {
    	this.serviceCodeList = this.goodsUserManager.qryServiceList();
        this.cur_userId = ManagerUtils.getUserId();
        return "goodsUser";
    }

    public String goodsList() {
        this.webpage = this.goodsUserManager.goodsList(goods_id, goods_name, this.getPageSize(), this.getPage());
        return "goodsList";
    }

    public String userList() {
        this.userList = this.goodsUserManager.getGroupUser(group_id);

        this.json = JSON.toJSONString(userList);

        return WWAction.JSON_MESSAGE;
    }

    public String groupList() {
        if ("qr".equals(group_type)) {
            group_type = "confirm";
        }
        this.webpage = this.goodsUserManager.groupList(group_type, this.getPageSize(), this.getPage());
        return "groupList";
    }

    public String addGoodsUser() {
        try {

            for (int i = 0; i < this.goodsUser.getGoodsIds().length; i++) {
                String goodsId = this.goodsUser.getGoodsIds()[i];
                int count = this.goodsUserManager.getGoodsUserCount(goodsId, this.goodsUser.getService_code());
                String goodsName = goodsNames[i];
                if (count > 0) {
                    this.json = "{result:1,message:'商品" + goodsName + "已设置,不能重复执行此操作'}";
                    return WWAction.JSON_MESSAGE;
                }

            }
            this.goodsUserManager.saveGoodsServiceRule(goodsUser,rule_ids);

            this.goodsUserManager.add(goodsUser, goodsRules);
            //
            this.json = "{result:0,message:'操作成功'}";
        } catch (RuntimeException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            this.json = "{result:1,message:'" + e.getMessage() + "'}";
        }
        return WWAction.JSON_MESSAGE;
    }

    public String editGoodsUser() {
    	this.ruleList = this.goodsUserManager.listRule(goods_id,service_code);
        this.goodsUser = this.goodsUserManager.edit(goods_id, user_type, service_code);
        this.groupUserMap = this.goodsUserManager.getListMap(goodsUser);
        this.goodsUser.setQr_user_id(this.goodsUser.getConfirm_user_id());
        this.goodsUser.setQr_group_id(this.goodsUser.getConfirm_group_id());
        UserFounderResp userFounderResp = new UserFounderResp();
        UserFounderReq userFounderReq = new UserFounderReq();
        userFounderResp = this.adminUserServ.founderMap(userFounderReq);

        typeMap = new HashMap<String, String>();
        if (userFounderResp != null) {
            typeMap = userFounderResp.getMap();
        }
        this.cur_userId = ManagerUtils.getUserId();
        this.goodsRules = this.goodsUserManager.getGoodsRules(goods_id, service_code);
        return "editGoodsUser";
    }

    public String saveEditGoodsUser() {
        try {
        	this.goodsUserManager.saveGoodsServiceRule(goodsUser, rule_ids);
        	
            this.goodsUserManager.add(goodsUser, goodsRules);
            //
            this.json = "{result:0,message:'操作成功'}";
        } catch (RuntimeException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            this.json = "{result:1,message:'" + e.getMessage() + "'}";
        }
        return WWAction.JSON_MESSAGE;

    }

    public String delGoodsUser() {
        try {
            this.goodsUserManager.delGoodsUser(goods_id, user_type, service_code);
            //
            this.json = "{result:0,message:'操作成功'}";
        } catch (RuntimeException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            this.json = "{result:1,message:'" + e.getMessage() + "'}";
        }
        return WWAction.JSON_MESSAGE;
    }

    public String setUserIdsStr(String[] userIds) {
        String str = "";
        if (userIds != null) {
            for (int i = 0; i < userIds.length; i++) {
                str += userIds[i];
                if (userIds.length != 1 && i != (userIds.length - 1)) {
                    str += ",";
                }
            }
        }
        return str;
    }

    public String setUserIds(GoodsUsers goodsUser) {
        this.goodsUser.setAccept_user_id(setUserIdsStr(acceptUserIds));
        this.goodsUser.setConfirm_user_id(setUserIdsStr(qrUserIds));
        this.goodsUser.setPay_user_id(setUserIdsStr(payUserIds));
        this.goodsUser.setQuery_user_id(setUserIdsStr(queryUserIds));
        this.goodsUser.setShip_user_id(setUserIdsStr(delveryUserIds));
        return null;
    }
    
    
    public String listRule(){
    	
    	if(this.rule == null){
    		this.rule = new Rule();
    	}
    	DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
	    this.ruleTypeList = dcPublicCache.getList("2381");
    	this.webpage = this.goodsUserManager.qryRuleList(this.rule,this.getPage(),this.getPageSize());
    	
    	return "ruleList";
    }

    public IGoodsUserManager getGoodsUserManager() {
        return goodsUserManager;
    }

    public void setGoodsUserManager(IGoodsUserManager goodsUserManager) {
        this.goodsUserManager = goodsUserManager;
    }

    public GoodsUsers getGoodsUser() {
        return goodsUser;
    }

    public void setGoodsUser(GoodsUsers goodsUser) {
        this.goodsUser = goodsUser;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public IDcPublicInfoManager getDcPublicInfoManager() {
        return dcPublicInfoManager;
    }

    public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
        this.dcPublicInfoManager = dcPublicInfoManager;
    }

    public List getUserList() {
        return userList;
    }

    public void setUserList(List userList) {
        this.userList = userList;
    }

    public List getTypeList() {
        return typeList;
    }

    public void setTypeList(List typeList) {
        this.typeList = typeList;
    }

    public String getGroup_type() {
        return group_type;
    }

    public void setGroup_type(String group_type) {
        this.group_type = group_type;
    }

    public List getGroupList() {
        return groupList;
    }

    public void setGroupList(List groupList) {
        this.groupList = groupList;
    }

    public String getQr_group_id() {
        return qr_group_id;
    }

    public void setQr_group_id(String qr_group_id) {
        this.qr_group_id = qr_group_id;
    }

    public String getQr_user_id() {
        return qr_user_id;
    }

    public void setQr_user_id(String qr_user_id) {
        this.qr_user_id = qr_user_id;
    }

    public GoodsRules getGoodsRules() {
        return goodsRules;
    }

    public void setGoodsRules(GoodsRules goodsRules) {
        this.goodsRules = goodsRules;
    }

    public AdminUserInf getAdminUserServ() {
        return adminUserServ;
    }

    public void setAdminUserServ(AdminUserInf adminUserServ) {
        this.adminUserServ = adminUserServ;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Map<String, String> getTypeMap() {
        return typeMap;
    }

    public void setTypeMap(Map<String, String> typeMap) {
        this.typeMap = typeMap;
    }

    public Map<String, Object> getGroupUserMap() {
        return groupUserMap;
    }

    public void setGroupUserMap(Map<String, Object> groupUserMap) {
        this.groupUserMap = groupUserMap;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String[] getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(String[] goodsIds) {
        this.goodsIds = goodsIds;
    }

    public String[] getQrUserIds() {
        return qrUserIds;
    }

    public void setQrUserIds(String[] qrUserIds) {
        this.qrUserIds = qrUserIds;
    }

    public String[] getPayUserIds() {
        return payUserIds;
    }

    public void setPayUserIds(String[] payUserIds) {
        this.payUserIds = payUserIds;
    }

    public String[] getDelveryUserIds() {
        return delveryUserIds;
    }

    public void setDelveryUserIds(String[] delveryUserIds) {
        this.delveryUserIds = delveryUserIds;
    }

    public String[] getAcceptUserIds() {
        return acceptUserIds;
    }

    public void setAcceptUserIds(String[] acceptUserIds) {
        this.acceptUserIds = acceptUserIds;
    }

    public String[] getQueryUserIds() {
        return queryUserIds;
    }

    public void setQueryUserIds(String[] queryUserIds) {
        this.queryUserIds = queryUserIds;
    }

    public List getServiceCodeList() {
        return serviceCodeList;
    }

    public void setServiceCodeList(List serviceCodeList) {
        this.serviceCodeList = serviceCodeList;
    }

    public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	public String[] getGoodsNames() {
        return goodsNames;
    }

    public void setGoodsNames(String[] goodsNames) {
        this.goodsNames = goodsNames;
    }

    public String getCur_userId() {
        return cur_userId;
    }

    public void setCur_userId(String cur_userId) {
        this.cur_userId = cur_userId;
    }

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public List getRuleTypeList() {
		return ruleTypeList;
	}

	public void setRuleTypeList(List ruleTypeList) {
		this.ruleTypeList = ruleTypeList;
	}

	public String[] getRule_ids() {
		return rule_ids;
	}

	public void setRule_ids(String[] rule_ids) {
		this.rule_ids = rule_ids;
	}

	public List<Rule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<Rule> ruleList) {
		this.ruleList = ruleList;
	}

	public int getFounder() {
		return founder;
	}

	public void setFounder(int founder) {
		this.founder = founder;
	}

}
