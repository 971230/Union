package com.powerise.ibss.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
public class InputTag  extends TagSupport {
	private String  name    = null ; //控件名
	private String  var     = null ; //参数名
	private String  scope   = null ; //取值范围
	private String  def     = null ; //缺省值
	private String  repl    = null ; //替换值
	private String  type    = null ; //控件类型 
	private String  others  = null ; //
	private String  id      = null ; //
	private boolean bEmpty  = false ; //
	private String  items   = null ; //
	
	//需要提交服务替换字段值
	private String  formerPara= null ; //前置影响参数名称 格式 参数名;取值范围;参数名;取值范围;
	private String  action    = null;
	private String  servType  = "1";  // 1 动态SQL 2 服务 3 静态字典 当为3 时 action为表名 nameField为字段名
	private boolean needUser  = true;
	private String  valueField= null;
  	private String  nameField = null;
  	private String  retRSName = null;
	
	
	public int print( JspWriter w ,StringBuffer sb)  {
		if (w==null || sb==null) return EVAL_PAGE;
		try{
			w.print( sb.toString() );
		}catch(Exception e){
		}
		return (EVAL_PAGE);
	}
	
	@Override
	public int doStartTag() throws JspException {
		if (name ==null || name.trim().equals("")) return (EVAL_PAGE);
		String    strValue = null;
		String    strValue1= null;
		String    strTmp   = null;
		String    vPrefix  = "s";
		String    sItems   = "";
		ArrayList replList = null;
		int       iPos = 0;
		JspWriter w = pageContext.getOut();
		StringBuffer sbHTML = new StringBuffer("");
		try{
			name = name.trim();
			if (var==null || var.trim().equals("")) var = "s." + name;
			iPos = var.indexOf(".");
			if (iPos > -1){
				vPrefix = var.substring(0,iPos);
			}
			//通过服务取得静态数据字典
			if (action!=null && !action.trim().equals("")) {
				replList = FuncTag.getStaticData(pageContext,action ,servType,needUser,valueField,nameField,retRSName,vPrefix,formerPara);
				//if (replList!=null) logger.info("InputTag replList=\n" + replList.toString());
			}
			
			strValue = FuncTag.getValueFromAttr(pageContext, var,scope);
			
			if (type==null) type = "";
			type  =type.trim().toLowerCase();
			if (type.equals("")) type = "show";
			type = ";"+type +";";
			
			//值变换
			strValue1 = strValue;
			if (strValue1==null){
				strValue1 = def;
			}else{
				if (repl!=null)	strValue1 = FuncTag.getValuesFromStr(repl,strValue);
				if (replList!=null)	{
					strTmp    = strValue1;
					strValue1 = FuncTag.getStaticValue(replList,strValue1,false);
					if (strValue1==null) strValue1 = strTmp;
				}
			}
				
			//显示值
			if (";show;view;v;".indexOf(type)>-1){
				sbHTML.append(strValue);
				return print(w ,sbHTML);
			}
			String KeyName  = null;
			String KeyValue = null;
			//处理卡片控制信息
			others = FuncTag.dealStyleinControl(pageContext , others , name  );
			sItems = FuncTag.getItemsFromAttr  (pageContext , items  , scope);
			if(sItems!=null) others+= " "+sItems;
			//下拉框
			if (";combobox;combo;c;select;".indexOf(type)>-1){
				sbHTML.append("<select name=\"" + name +"\"");
				if (id    !=null ) sbHTML.append(" id=\"" + id +"\"");
				if (others!=null ) sbHTML.append(" " + others);
				sbHTML.append(">\n" );
				if (bEmpty) sbHTML.append("<option value=\"\">请选择……</option>") ;
				
				String strOptions = FuncTag.getStaticValue(replList,strValue1,true);
				if(strOptions!=null) sbHTML.append(strOptions);
				sbHTML.append ( "<select>") ;
				return print(w ,sbHTML);
			}
			
			//输入框
			if (";text;i;password;pwd;p;".indexOf(type)>-1){
				sbHTML.append("<input name=\"" + name +"\" type=\"");
				if (";password;pwd;p;".indexOf(type)>-1) {
					sbHTML.append("password\"");
				}else{
					sbHTML.append("text\"");
				}
				if (id    !=null ) sbHTML.append(" id=\"" + id +"\"");
				if (others!=null ) sbHTML.append(" " + others);
				if (strValue1==null) strValue1 = "";
				sbHTML.append(" value=\"" + strValue1+"\"");
				sbHTML.append(" >\n");
				return print(w ,sbHTML);
			}
			if (";hidden;h;".indexOf(type)>-1){
				sbHTML.append("<input name=\"" + name +"\" type=\"hidden\"");
				if (id    !=null ) sbHTML.append(" id=\"" + id +"\"");
				if (others!=null ) sbHTML.append(" " + others);
				if (strValue1==null) strValue1 = "";
				sbHTML.append(" value=\"" + strValue1+"\"");
				sbHTML.append(" >\n");
				return print(w ,sbHTML);
			}
			//输入框
			if (";textarea;area;ta;".indexOf(type)>-1){
				sbHTML.append("<textarea name=\"" + name +"\"");
				if (id    !=null ) sbHTML.append(" id=\"" + id +"\"");
				if (others!=null ) sbHTML.append(" " + others);
				if (others!=null ) sbHTML.append(" >");
				if (strValue1==null) strValue1 = "";
				sbHTML.append(strValue1);
				sbHTML.append("<textarea>\n");
				return print(w ,sbHTML);
			}
		}
		catch (Exception e) {
			throw new JspException("在取值时，出现异常" + e.getMessage());
		}
		return (EVAL_PAGE);
	}
	//支撑函数
	//以下为属性设置
	 
  	//变量名称
	public void setName(String vName) {
		this.name = vName;
	}
	
	public void setType(String vType) {
		this.type = vType;
	}
	
	public void setFormerPara(String vFormerPara) {
		this.formerPara = vFormerPara;
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
	public void setOthers(String others) {
		this.others = others;
	}
	@Override
	public void setId(String vID) {
		this.id = vID;
	}
	public void setVar(String strVar) {
		this.var = strVar;
	}
	public void setBEmpty(boolean bEmpty) {
		this.bEmpty = bEmpty;
	}
	public void setItems(String items) {
		this.items = items;
	}

}