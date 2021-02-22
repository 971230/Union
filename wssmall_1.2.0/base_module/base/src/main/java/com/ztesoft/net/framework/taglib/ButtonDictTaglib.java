package com.ztesoft.net.framework.taglib;

import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.service.IDictManager;
import com.ztesoft.net.mall.core.service.impl.cache.DictManagerCacheProxy;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ButtonDictTaglib extends EnationTagSupport {

	private String attr_code;
	private String name;
	private String value;
	private String type;
	private String url;
	private String onClick;
	private String id;
	private String btn_class;	//class
	private String extend_attr;	//扩展属性

	@Override
	@SuppressWarnings("unchecked")
	public int doEndTag() throws JspException { //
		IDictManager dictManager = SpringContextHolder.getBean("dictManager");
		DictManagerCacheProxy dc = new DictManagerCacheProxy(dictManager);
		
		
		//查询当前用户角色所对应的所有按钮权限 ，对应es_auth_action表中的objvalue
		AdminUser adminuser = ManagerUtils.getAdminUser();
		List<Map> roleList = dc.qryUserRole(adminuser.getUserid());
		
		String role_id = "";
		if(!ListUtil.isEmpty(roleList)){
			for (int i = 0; i < roleList.size(); i++) {
				Map map = roleList.get(i);
				role_id += map.get("roleid")+",";
			}
		}
		if(!StringUtils.isEmpty(role_id)){
			role_id = role_id.substring(0,role_id.length()-1);
		}
		
		List<Map> userRoleBtnList =new ArrayList<Map>();
		userRoleBtnList = dc.loadUserRoleList(role_id);
		
		
		//查看当前用户对应的按钮权限中，有没有当前标签所需要的权限
		StringBuffer selectBuffer = new StringBuffer("");
		if(!ListUtil.isEmpty(userRoleBtnList)){
			for (int i = 0; i < userRoleBtnList.size(); i++) {
				Map map = userRoleBtnList.get(i);
				String objVakue = (String) map.get("objvalue");
				if(!StringUtils.isEmpty(objVakue)){
					String[] values = objVakue.split(",");
					for (int j = 0; j < values.length; j++) {
						String b_value = values[j];
						
						String attr = "";
						if(StringUtils.isNotEmpty(extend_attr)){
							attr = " extend_attr='"+extend_attr+"'";
						}
						
						if(attr_code.equals(b_value)){
							if("button".equals(type)){
								selectBuffer.append(
								"<input style='margin-right:10px;' ")
								.append(" name='").append(name).append("'").append(" class='")
								.append(btn_class).append("'").append(attr).append(" id='")
								.append(id).append("'").append("value='").append(value)
								.append("'").append("type='").append(type).append("'");
							}else if("a".equals(type)){
								selectBuffer.append(
								"<a href='javascript:void(0);' style='margin-right: 10px;'")
								.append(" name='").append(name).append("'").append(" class='")
								.append(btn_class).append("'").append(attr).append(" id='")
								.append(id).append("'").append("value='").append(value).append("'");
							}
							
							if (StringUtils.isNotEmpty(onClick)) {
								selectBuffer.append(" onClick='").append(onClick).append("'");
							}
							
							if("button".equals(type)){
								selectBuffer.append("></input>");
							}else if("a".equals(type)){
								selectBuffer.append(">"+value+"</a>");
							}
							
							break;
						}
					}
				}
			}
		}

		this.print(selectBuffer.toString());

		return Tag.EVAL_BODY_INCLUDE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getAttr_code() {
		return attr_code;
	}

	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}

	public String getBtn_class() {
		return btn_class;
	}

	public void setBtn_class(String btn_class) {
		this.btn_class = btn_class;
	}

	public String getExtend_attr() {
		return extend_attr;
	}

	public void setExtend_attr(String extend_attr) {
		this.extend_attr = extend_attr;
	}
	
}
