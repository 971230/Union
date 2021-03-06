package com.powerise.ibss.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
public class OutTag  extends TagSupport {
	private String  value   = null ; //变量名称
	private String  scope   = null ; //
	private String  def     = null ; //
	private String  repl    = null ; //
	
	//需要提交服务替换字段值
	private String  formerPara= null ; //前置影响参数名称 格式 参数名;取值范围;参数名;取值范围;
	private String  action    = null;
	private String  servType  = "1";  // 1 动态SQL 2 服务 3 静态字典 当为3 时 action为表名 nameField为字段名
	private boolean needUser  = true;
	private String  valueField= null;
  	private String  nameField = null;
  	private String  retRSName = null;
  	
	@Override
	public int doStartTag() throws JspException {
		if (value ==null || value.trim().equals("")) return (EVAL_PAGE);
		JspWriter w = pageContext.getOut();
		String      strValue = null ;
		String      strTmp   = null ;
		int         iPos     = 0;
		ArrayList  replList  = null;
		String vPrefix = "s";
		if (w ==null) return (EVAL_PAGE);
		try {
  			if (action!=null && !action.trim().equals("")) {
				if(value!=null){
					iPos = value.indexOf(".");
					if (iPos > -1){
						vPrefix = value.substring(0,iPos);
					}
				}
  				replList =FuncTag.getStaticData(pageContext,action ,servType,needUser,valueField,nameField,retRSName,vPrefix, formerPara);
			}
			strValue = FuncTag.getValueFromAttr(pageContext,value,scope);
			if (repl!=null){
				strValue = FuncTag.getValuesFromStr(repl,strValue);
			}
			if (replList!=null){
				strTmp = FuncTag.getStaticValue(replList,strValue,false);
				if(strTmp!=null) strValue = strTmp;
			}
			if (strValue==null) strValue = def;
			if (strValue!=null) w.print( strValue );
		}
		catch (Exception e) {
			throw new JspException("在取值时，出现异常" + e.getMessage());
		}
		return (EVAL_PAGE);
	}
	//支撑函数
	//以下为属性设置
  	//变量名称
	public void setValue(String value) {
		this.value = value;
	}
	//动作名称
	public void setScope(String scope) {
		this.scope = scope;
	}
	public void setDef(String def) {
		this.def = def;
	}
	public void setRepl(String repl) {
		this.repl = repl;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public void setServType(String servType) {
		this.servType = servType;
	}
	public void setNeedUser(boolean needUser) {
		this.needUser = needUser;
	}
	
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
	
	public void setNameField(String nameField) {
		this.nameField = nameField;
	}
	
	public void setRetRSName(String retRSName) {
		this.retRSName = retRSName;
	}
	//前置影响参数名称 格式 参数名;取值范围;参数名;取值范围;
	public void setFormerPara(String formerPara) {
		this.formerPara = formerPara;
	}
}
