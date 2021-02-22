package com.ztesoft.component.ui.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
;

public class PanelTag extends BodyTagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PanelTag.class);

	private String id;
	private String type;
	private String desc;
	private String style;
    
	private String title;
	private String content;
	private String autoScroll;
	
	private String dynaControlId;
	
	/**
	 * @return the dynaControlId
	 */
	public String getDynaControlId() {
		return dynaControlId;
	}

	/**
	 * @param dynaControlId the dynaControlId to set
	 */
	public void setDynaControlId(String dynaControlId) {
		this.dynaControlId = dynaControlId;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return (desc!=null ? desc : "");
	}
	
	public void setStyle(String style) {
		this.style = style;
	}

	public String getStyle() {
		return style;
	}	
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return (content!=null ? content : "");
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.getDesc()+(title!=null ? title : "");
	}	
		
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		return super.doStartTag();
	}
    	
	private String createPanelPart(String id){
		
		StringBuffer sbuffer = new StringBuffer();
		
		if("titleDiv".equals(id)){
			sbuffer.append("<span class='"+this.getType()+"_"+id+"'>"+this.getTitle()+"</span>");			
		}
		else if("contentDiv".equals(id)){
			sbuffer.append("<td><div class='"+this.getType()+"_"+id+"' style='height:100%;width:100%;'>");	
			sbuffer.append(this.getContent());
			sbuffer.append("</div></td>"); 			
		}
		else if("titleTable_center".equals(id)){
			sbuffer.append("<td class='"+this.getType()+"_"+id+"'>"+createPanelPart("titleDiv")+"</td>");			
		}
		else if("top_center".equals(id)){
			sbuffer.append("<td class='"+this.getType()+"_"+id+"'>");
			sbuffer.append(" <table class='"+this.getType()+"_titleTable'>");
			sbuffer.append("  <tr>");
			sbuffer.append(createPanelPart("titleTable_left"));
			sbuffer.append(createPanelPart("titleTable_center"));
			sbuffer.append(createPanelPart("titleTable_right"));
			sbuffer.append("  </tr>");
			sbuffer.append(" </table>");
			sbuffer.append("</td>");
		}
		else{ 
			sbuffer.append("<td class='"+this.getType()+"_"+id+"'></td>"); 
		}
		
		return sbuffer.toString();
	}
	
	@Override
	public int doAfterBody() throws JspException {
		// TODO Auto-generated method stub

		if("titleTable".equalsIgnoreCase(this.getType())){
		  	this.setType("titleList");	
		}				

		if(this.dynaControlId!=null&&!"".equals(this.dynaControlId)){
			PageTagUtils.getInstance().setTabPanelTagDynaElement(this);
		}
		StringBuffer sbuffer = new StringBuffer();
		
		if("modalDialog".equalsIgnoreCase(this.getType())){
			sbuffer.append("<table class='"+this.getType()+"_tablePanel'");
			if(this.getStyle()!=null)
				sbuffer.append(" style='"+this.getStyle()+"'");
			sbuffer.append(">");
			sbuffer.append(" <tr>");
			sbuffer.append(createPanelPart("top_center"));
			sbuffer.append(" </tr>");
			sbuffer.append(" <tr>");
			sbuffer.append(createPanelPart("contentDiv"));
			sbuffer.append(" </tr>");
			sbuffer.append(" <tr>");
			sbuffer.append(createPanelPart("bottom_center"));		
			sbuffer.append(" </tr>");
			sbuffer.append("</table>"); 			
		}
		else{
			sbuffer.append("<table class='"+this.getType()+"_tablePanel'");
			if(this.getStyle()!=null)
				sbuffer.append(" style='"+this.getStyle()+"'");
			sbuffer.append(">");
			sbuffer.append(" <tr>");
			sbuffer.append(createPanelPart("top_left"));
			sbuffer.append(createPanelPart("top_center"));
			sbuffer.append(createPanelPart("top_right"));
			sbuffer.append(" </tr>");
			sbuffer.append(" <tr>");
			sbuffer.append(createPanelPart("center_left"));
			sbuffer.append(createPanelPart("contentDiv"));
			sbuffer.append(createPanelPart("center_right"));
			sbuffer.append(" </tr>");
			sbuffer.append(" <tr>");
			sbuffer.append(createPanelPart("bottom_left"));
			sbuffer.append(createPanelPart("bottom_center"));
			sbuffer.append(createPanelPart("bottom_right"));			
			sbuffer.append(" </tr>");
			sbuffer.append("</table>");    
		}
		
		try{
			String result = sbuffer.toString();
			if(!ContentManage.isSetParentContent(this, result)){
				getBodyContent().getEnclosingWriter().println(result);
			}
		}catch(Exception e){
			logger.info("面板标签解释失败", e);
		}
		return super.doAfterBody();
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		super.release();	
		
		this.type = null;
		this.desc = null;
		this.style = null;	
		
		this.title = null;
		this.content = null;
		this.autoScroll = null;
		this.dynaControlId = null;
	
	}	

	public String getAutoScroll() {
		return (autoScroll!=null ? autoScroll : "false");
	}
	public void setAutoScroll(String autoScroll) {
		this.autoScroll = autoScroll;
	}	
	
}
