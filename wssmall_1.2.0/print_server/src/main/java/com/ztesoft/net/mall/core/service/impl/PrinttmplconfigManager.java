package com.ztesoft.net.mall.core.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.PrintConfigName;
import com.ztesoft.net.mall.core.model.Printtmplconfig;
import com.ztesoft.net.mall.core.service.IPrinttmplconfigManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 打印模配置Manager
 * @作者 MoChunrun
 * @创建日期 2013-11-7 
 * @版本 V 1.0
 */
public class PrinttmplconfigManager extends BaseSupport implements IPrinttmplconfigManager {

	@Override
	public List<Printtmplconfig> listPrinttmplconfig() {
		String sql = "select t.* from es_print_tmpl_config t where t.source_from=?";
		return this.baseDaoSupport.queryForList(sql, Printtmplconfig.class,ManagerUtils.getSourceFrom());
	}

	@Override
	public Printtmplconfig get(String config_id) {
		String sql = "select t.* from es_print_tmpl_config t where t.config_id=?";
		return (Printtmplconfig) this.baseDaoSupport.queryForObject(sql, Printtmplconfig.class, config_id);
	}

	@Override
	public List<PrintConfigName> getConfigName(String config_id) {
		Printtmplconfig config = this.get(config_id);
		if(config!=null){
			List<PrintConfigName> list = JSON.parseArray(config.getTmpl_json(),PrintConfigName.class);
			return list;
		}
		return null;
	}

}
