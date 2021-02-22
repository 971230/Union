/**
 * 
 */
package com.ztesoft.rule.manager.action;

import java.util.List;

/**
 * @author ZX
 * SearchRuleByDirVo.java
 * 2015-1-9
 */
public class SearchRuleByDirVo {

	private String dir_id; // 目录ID
	private List<SearchRuleByPlanVo> searchRuleByPlanList; // 方案过滤条件集合
	
	public String getDir_id() {
		return dir_id;
	}
	public void setDir_id(String dir_id) {
		this.dir_id = dir_id;
	}
	public List<SearchRuleByPlanVo> getSearchRuleByPlanList() {
		return searchRuleByPlanList;
	}
	public void setSearchRuleByPlanList(
			List<SearchRuleByPlanVo> searchRuleByPlanList) {
		this.searchRuleByPlanList = searchRuleByPlanList;
	}
	
}
