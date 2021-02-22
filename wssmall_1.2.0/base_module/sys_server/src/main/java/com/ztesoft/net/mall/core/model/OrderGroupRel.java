package com.ztesoft.net.mall.core.model;


/**
 * 订单分组关系，一个分组包含用户，或者包含用户角色
 * @author wui
 */
public class OrderGroupRel implements java.io.Serializable {
    private String group_id ;
    private String userid ;
    private String role_id ;
    private String disabled;
    private String source_from;
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	
    
}