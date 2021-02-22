package com.ztesoft.framework;

import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.util.XMLItem;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RoleVO extends ValueObject implements VO, XMLItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String roleName = "";
	private String roleDesc = "";
	private String roleNameShort = "";
	private String roleId = "";
	private String state = "";
	private String stateDate = "";
	
	public RoleVO( String roleName , String roleDesc, String roleNameShort, String roleId, String state, String stateDate ){
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.roleNameShort = roleNameShort;
		this.roleId = roleId;
		this.state = state;
		this.stateDate = stateDate;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleNameShort() {
		return roleNameShort;
	}
	public void setRoleNameShort(String roleNameShort) {
		this.roleNameShort = roleNameShort;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStateDate() {
		return stateDate;
	}
	public void setStateDate(String stateDate) {
		this.stateDate = stateDate;
	}
	
	/**
     * 生成item节点格式的XML片断
     * @return item节点格式的XML片断
     */
    @Override
	public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
    {
        sbXml.append("<item ");
        sbXml.append("roleId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.roleId));
        sbXml.append("' ");
        sbXml.append("roleName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.roleName));
        sbXml.append("' ");
        sbXml.append("roleDesc='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.roleDesc));
        sbXml.append("' ");
        sbXml.append("state='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.state));
        sbXml.append("' ");
        sbXml.append("stateDate='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.stateDate));
        sbXml.append("' ");
        sbXml.append("roleNameShort='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.roleNameShort));
        sbXml.append("' ");
        sbXml.append(">");
        return sbXml;
    }
	@Override
	public List getKeyFields() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void loadFromHashMap(HashMap map) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public HashMap unloadToHashMap() {
		// TODO Auto-generated method stub
		return null;
	}
}
