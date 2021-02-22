package com.ztesoft.net.mall.core.service;

import java.util.List;

import vo.Rule;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.ServiceObjOffer;


/**
 * 商品规则接口
 * @author hu.yi
 * @date 2014.02.19
 */
public interface ISysRuleManager {

	/**
	 * 分页查询规则表数据
	 * @param rule
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page listRule(Rule rule,int pageNo,int pageSize);
	
	
	/**
	 * 分页查询服务
	 * @param serviceOffer
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page listServer(ServiceObjOffer serviceOffer, int pageNo, int pageSize);
	
	/**
	 * 根据id查询规则实体
	 * @param rule_id
	 * @return
	 */
	public Rule qryRule(String rule_id);
	
	
	/**
	 * 保存或更新
	 * @param rule
	 */
	public void saveRule(Rule rule);
	
	
	/**
	 * 保存或更新业务
	 * @param serviceObjOffer
	 */
	public void saveServer(ServiceObjOffer serviceObjOffer);
	
	
	/**
	 * 根据id查询业务实体
	 * @param service_id
	 * @return
	 */
	public ServiceObjOffer qryServer(String service_id);
	
	
	/**
	 * 服务关联规则关系保存
	 * @param rule_id
	 * @param service_id
	 */
	public void saveLinkRule(String rule_id,String service_id);
	
	
	
	/**
	 * 获取服务关联的规则列表
	 * @param service_id
	 * @return
	 */
	public List<Rule> qryRuleList(String service_id);
	
	
	/**
	 * 查询数量
	 * @return
	 */
	public int qryServerCount(ServiceObjOffer serviceObjOffer);
	
	
	/**
	 * 查询规则数量
	 * @param rule
	 * @return
	 */
	public int qryRuleCount(Rule rule);
}
