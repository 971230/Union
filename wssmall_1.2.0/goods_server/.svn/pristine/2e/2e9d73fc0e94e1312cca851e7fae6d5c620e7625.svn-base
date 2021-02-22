package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.GoodsRules;
import com.ztesoft.net.mall.core.model.GoodsUsers;

import java.util.List;
import java.util.Map;

import vo.Rule;

public interface IGoodsUserManager {
    public void add(GoodsUsers goodsUser,GoodsRules goodsRules);
    
    public void editSave(GoodsUsers goodsUser);
    
    public GoodsUsers edit(String goods_id,String user_type,String buy_type);
    
    public Page goodsList(String goods_id,String goods_name,int pageSize,int pageNo);
    
    public List getGroupUser(String group_id);
    
    public Page groupList(String group_type,int pageSize,int pageNo);
    
    public Page goodsUserList(String goods_name,String user_type,String buy_type,int pageSize,int pageNo);
    
    public Map<String,Object>  getListMap(GoodsUsers goodsUser);
    
    public GoodsRules getGoodsRules(String goods_id,String buy_type);
    
    public void delGoodsUser(String goods_id,String user_type,String buy_type);
    
    public int getGoodsUserCount(String goods_id,String buy_type);
    
    public List qryServiceList();
    
    public Page qryRuleList(Rule rule,int pageNo,int pageSize);
    
    public void saveGoodsServiceRule(GoodsUsers goodsUsers,String[] rule_ids);
    
    public List<Rule> listRule(String goods_id,String service_type);
}
