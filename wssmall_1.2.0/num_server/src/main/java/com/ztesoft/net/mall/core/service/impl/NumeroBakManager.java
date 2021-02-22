package com.ztesoft.net.mall.core.service.impl;

import java.util.Map;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.NumeroBak;
import com.ztesoft.net.mall.core.service.INumeroBakManager;
import com.ztesoft.net.sqls.SF;

/**
 * Saas式号码分组业务管理
 * 
 * @author cc
 * 
 */
@SuppressWarnings("all")
public class NumeroBakManager extends BaseSupport<NumeroBak> implements
		INumeroBakManager {
	private static final String TABLE="ES_NO_BAK";
	private static final String ID="NO_ID";
	private static final String NO="DN_NO";
	
	
	@Override
	public Page getFormList(int page, int pageSize, Map<String, String> params) {
		return null;
	}

	
	@Override
	public void save(NumeroBak nb) throws Exception {
		baseDaoSupport.insert(TABLE, nb);
	}
	
	
	@Override
	public void del(String ids, Map<String, String> params) {
		String condition = "";
		if (ids.split(",").length > 1) {
			condition = creatrSql(ID, "in", ids);
		} else {
			condition = creatrSql(ID, "=", ids);
		}
		String sql = SF.numeroSql("NUMEROS_BAK_DEL") +condition; 
		baseDaoSupport.execute(sql);
	}
	
	
	@Override
	public void delByNo(String nos, Map<String, String> params) {
		String condition = "";
		if (nos.split(",").length > 1) {
			condition = creatrSql(NO, "in", nos);
		} else {
			condition = creatrSql(NO, "=", nos);
		}
		String sql = SF.numeroSql("NUMEROS_BAK_DEL") +condition; 
		baseDaoSupport.execute(sql);
	}
	
	private String creatrSql(String filed, String operator, String value) {
		String condition = "";

		value = value.trim();
		if (value.endsWith(","))
			value = value.substring(0, value.length() - 1);

		if ("in".equalsIgnoreCase(operator)
				|| "exists".equalsIgnoreCase(operator)
				|| "not exists".equalsIgnoreCase(operator)
				|| "not in".equalsIgnoreCase(operator)) {
			condition = " and " + filed + " " + operator + "(" + value + ")";
		} else
			condition = " and " + filed + " " + operator + " " + value;
		return condition;
	}
}