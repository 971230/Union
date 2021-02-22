package com.ztesoft.net.mall.core.model;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.auto.rule.vo.RuleExeLog;
import com.ztesoft.net.auto.rule.vo.RuleRel;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 规则基本信息
 * @作者 MoChunrun
 * @创建日期 2013-12-12 
 * @版本 V 1.0
 */
public class RuleConfig implements Serializable {

	@ZteSoftCommentAnnotationParam(name="规则ID",type="String",isNecessary="Y",desc="规则ID")
	private String rule_id;
	@ZteSoftCommentAnnotationParam(name="规则编码",type="String",isNecessary="Y",desc="规则编码")
	private String rule_code;
	@ZteSoftCommentAnnotationParam(name="规则名称",type="String",isNecessary="Y",desc="规则名称")
	private String rule_name;
	@ZteSoftCommentAnnotationParam(name="规则流类型",type="String",isNecessary="Y",desc="规则流类型")
	private String resource_type;
	private String impl_type;
	@ZteSoftCommentAnnotationParam(name="规则脚本",type="String",isNecessary="Y",desc="规则脚本")
	private String rule_script;
	@ZteSoftCommentAnnotationParam(name="开始时间",type="String",isNecessary="Y",desc="开始时间")
	private String exp_date;
	@ZteSoftCommentAnnotationParam(name="结束时间",type="String",isNecessary="Y",desc="结束时间")
	private String eff_date;
	@ZteSoftCommentAnnotationParam(name="规则描述",type="String",isNecessary="Y",desc="规则描述")
	private String rule_desc;
	private String create_date;
	private String create_user;
	private String status_date;
	private String status_user;
	@ZteSoftCommentAnnotationParam(name="规则状态",type="String",isNecessary="Y",desc="规则状态")
	private String status_cd;
	@ZteSoftCommentAnnotationParam(name="父级规则ID",type="String",isNecessary="Y",desc="父级规则ID")
	private String pid;
	private String ass_id; // 组件ID
	private String ass_name; // 组件名称
	private String ass_code; // 组件编号
	private String plan_id; // 方案ID
	private RuleRel ruleRel; // 规则关系表
	
	private List<RuleConfig> childrenList;
	private List<RuleExeLog> objRuleExeLogList;
	
	private int level;
	
	private Integer auto_exe;
	private Integer rel_type;//规则关系
	
	@NotDbField
	public Integer getRel_type() {
		return rel_type;
	}

	public void setRel_type(Integer rel_type) {
		this.rel_type = rel_type;
	}

	@NotDbField
	public Integer getAuto_exe() {
		return auto_exe;
	}

	public void setAuto_exe(Integer auto_exe) {
		this.auto_exe = auto_exe;
	}

	@NotDbField
	public String getHandlerStatus(){
		//获取处理状态
		if(objRuleExeLogList!=null && objRuleExeLogList.size()>0){
			return "已处理";
		}else{
			return "未处理";
		}
	}
	
	@NotDbField
	public boolean isHasChildren(){
		return (childrenList==null || childrenList.size()==0)?false:true;
	}
	
	@NotDbField
	public String getAss_name() {
		return ass_name;
	}
	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}
	@NotDbField
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	@NotDbField
	public List<RuleExeLog> getObjRuleExeLogList() {
		return objRuleExeLogList;
	}
	public void setObjRuleExeLogList(List<RuleExeLog> objRuleExeLogList) {
		this.objRuleExeLogList = objRuleExeLogList;
	}
	@NotDbField
	public List<RuleConfig> getChildrenList() {
		return childrenList;
	}
	public void setChildrenList(List<RuleConfig> childrenList) {
		this.childrenList = childrenList;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getAss_id() {
		return ass_id;
	}
	public void setAss_id(String ass_id) {
		this.ass_id = ass_id;
	}
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	public String getRule_code() {
		return rule_code;
	}
	public void setRule_code(String rule_code) {
		this.rule_code = rule_code;
	}
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}
	public String getResource_type() {
		return resource_type;
	}
	public void setResource_type(String resource_type) {
		this.resource_type = resource_type;
	}
	public String getImpl_type() {
		return impl_type;
	}
	public void setImpl_type(String impl_type) {
		this.impl_type = impl_type;
	}
	public String getRule_script() {
		return rule_script;
	}
	public void setRule_script(String rule_script) {
		this.rule_script = rule_script;
	}
	public String getExp_date() {
		return exp_date;
	}
	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}
	public String getEff_date() {
		return eff_date;
	}
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	public String getRule_desc() {
		return rule_desc;
	}
	public void setRule_desc(String rule_desc) {
		this.rule_desc = rule_desc;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}
	public String getStatus_user() {
		return status_user;
	}
	public void setStatus_user(String status_user) {
		this.status_user = status_user;
	}
	public String getStatus_cd() {
		return status_cd;
	}
	public void setStatus_cd(String status_cd) {
		this.status_cd = status_cd;
	}

	@NotDbField
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public RuleRel getRuleRel() {
		return ruleRel;
	}

	public void setRuleRel(RuleRel ruleRel) {
		this.ruleRel = ruleRel;
	}
	@NotDbField
	public String getAss_code() {
		return ass_code;
	}

	public void setAss_code(String ass_code) {
		this.ass_code = ass_code;
	}
	
}
