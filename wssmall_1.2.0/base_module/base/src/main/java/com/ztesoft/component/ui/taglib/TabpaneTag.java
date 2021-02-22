package com.ztesoft.component.ui.taglib;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

public class TabpaneTag extends BodyTagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TabpaneTag.class);
	
	private String id;
	private String type;
	private String style;
	private String scroll;
	private String direction;
	private String minimizable;
	private String maximizable;
	private String windowable;
	
	private ArrayList titles;
	private ArrayList contents;	
	
	@Override
	public String getId() {
		if(id==null){
			id="tabpane_"+System.currentTimeMillis();
		}
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	public String getStyle() {
		return (style!=null ? style : "");
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getType() {
		return (type!=null ? type : "L");
	}
	public void setType(String type) {
		this.type = type;
	}
	public ArrayList getContents() {
		if(contents==null){
			contents = new ArrayList();
		}		
		return contents;
	}
	public void setContents(ArrayList contents) {
		this.contents = contents;
	}
	public void setContent(Object content) {
		if(contents==null){
			contents = new ArrayList();
		}			
		this.contents.add(content);
	}	
	public ArrayList getTitles() {
		if(titles==null){
			titles = new ArrayList();
		}
		return titles;
	}
	public void setTitles(ArrayList titles) {
		this.titles = titles;
	}	
	public void setTitle(Object title) {
		if(titles==null){
			titles = new ArrayList();
		}
		titles.add(title);
	}		
	
	public String getTitleDiv(){
		
		StringBuffer sbuffer = new StringBuffer();
		
		sbuffer.append("<td id='"+this.getId()+"_tabsetTitleDiv' class='tabsetTitleDiv tabsetTitleDiv_"+this.getDirection()+"'");
		if("true".equalsIgnoreCase(this.getMaximizable())){
			sbuffer.append(" ondblclick='maxShowPage(\""+this.getId()+"\")'");
		}
		sbuffer.append(">");
		
		sbuffer.append("<table class='tabsetTitleDiv_innerDiv' cellspacing='0' cellpadding='0'><tr><td>");
		sbuffer.append("<table class='tabsetTitle'>");
		sbuffer.append("<tr>");
		sbuffer.append("<td class='tabsetTitle_left'>&nbsp;</td>");
	    for(int i=0; i<this.getTitles().size(); i++){
	    	sbuffer.append( this.getTitles().get(i).toString() );
		}
		sbuffer.append("<td class='tabsetTitle_right' width='*'>&nbsp;</td>");
		sbuffer.append("</tr>");
		sbuffer.append("</table>");
		sbuffer.append("</td>");
		if("true".equalsIgnoreCase(this.getScroll())){
			sbuffer.append("<td class='tabsetTitle_control'>");
			sbuffer.append("<span title='左移标签页' onmousedown='tabsetScroll(this, \"left\")' onmouseup='tabsetScrollStop()'>&lt;</span>&nbsp;");
			sbuffer.append("<span title='右移标签页' onmousedown='tabsetScroll(this, \"right\")' onmouseup='tabsetScrollStop()'>&gt;</span>");		
			sbuffer.append("</td>");
		}
		if("true".equalsIgnoreCase(this.getMinimizable())){
			sbuffer.append("<td class='tabsetTitle_control_min'>");
			sbuffer.append("<IMG title='最小化' id='"+this.getId()+"_minButton' SRC='/CrmWeb/public/skins/bsn/tabset/cmnuMinimize.gif' WIDTH='13' HEIGHT='11' BORDER=0 ALT='' align='center' onclick='minShowPage(\""+this.getId()+"\")' />");	
			sbuffer.append("</td>");
		}
		if("true".equalsIgnoreCase(this.getMaximizable())){
			sbuffer.append("<td class='tabsetTitle_control_max'>");
			sbuffer.append("<IMG title='最大化' id='"+this.getId()+"_maxButton'SRC='/CrmWeb/public/skins/bsn/tabset/cmnuMaximize.gif' WIDTH='13' HEIGHT='11' BORDER=0 ALT='' align='center' onclick='maxShowPage(\""+this.getId()+"\")' />");	
			sbuffer.append("</td>");
		}		
		if("true".equalsIgnoreCase(this.getWindowable())){
			sbuffer.append("<td class='tabsetTitle_control_win'>");
			sbuffer.append("<IMG title='窗口显示' id='"+this.getId()+"_winButton' SRC='/CrmWeb/public/skins/bsn/tabset/cmnuClose.gif' WIDTH='13' HEIGHT='11' BORDER=0 ALT='' align='center' onclick='winShowPage(\""+this.getId()+"\")' />");	
			sbuffer.append("</td>");
		}
		sbuffer.append("</tr></table>");
		
		sbuffer.append("</td>");	
		
		return sbuffer.toString();
	}
	
	public String getTitleDiv_Horizontal(){
		
		StringBuffer sbuffer = new StringBuffer();
		
		sbuffer.append("<td id='"+this.getId()+"_tabsetTitleDiv' class='tabsetTitleDiv tabsetTitleDiv_"+this.getDirection()+"'>");
		
		sbuffer.append("<table class='tabsetTitleDiv_innerDiv' cellspacing='0' cellpadding='0'>");
		sbuffer.append("<tr><td>");
		sbuffer.append("<table class='tabsetTitle'>");
		sbuffer.append("<tr><td class='tabsetTitle_left'>&nbsp;</td></tr>");
	    for(int i=0; i<this.getTitles().size(); i++){
	    	sbuffer.append("<tr>"+this.getTitles().get(i).toString()+"</tr>");
		}
		sbuffer.append("<tr><td class='tabsetTitle_right' height='*'>&nbsp;</td></tr>");
		sbuffer.append("</table>");
		sbuffer.append("</td></tr>");
		if("true".equalsIgnoreCase(this.getScroll())){
			sbuffer.append("<tr><td class='tabsetTitle_control'>");
			sbuffer.append("<span title='上移标签页' onmousedown='tabsetScroll(this, \"up\")' onmouseup='tabsetScrollStop()'>&lt;</span>&nbsp;");
			sbuffer.append("</td></tr>");
			sbuffer.append("<tr><td class='tabsetTitle_control'>");
			sbuffer.append("<span title='下移标签页' onmousedown='tabsetScroll(this, \"down\")' onmouseup='tabsetScrollStop()'>&gt;</span>");		
			sbuffer.append("</td></tr>");
		}		
		sbuffer.append("</table>");
		
		sbuffer.append("</td>");	
		
		return sbuffer.toString();
	}	
	
	public String getContentDiv(){
		
		StringBuffer sbuffer = new StringBuffer();
		
		sbuffer.append("<td id='"+this.getId()+"_tabsetContentDiv' class='tabsetContentDiv tabsetContentDiv_"+this.getDirection()+"'>");
		//sbuffer.append("<div style='width: 100%; height: 100%; overflow: auto;'>");
		//sbuffer.append("<div class='tabsetContent'>");
		sbuffer.append("<table class='tabsetContent'>");
	    for(int i=0; i<this.getContents().size(); i++){
	    	sbuffer.append( this.getContents().get(i).toString() );
		}
		sbuffer.append("</table>");
	    //sbuffer.append("</div>");
		//sbuffer.append("</div>");
		sbuffer.append("</td>");		
		
		return sbuffer.toString();
		
	}
	
	@Override
	public int doAfterBody() throws JspException {
		// TODO Auto-generated method stub

		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append("<div id='"+this.getId()+"'");
		if(this.getStyle()!=null)
			sbuffer.append(" style='"+this.getStyle()+"'");
		sbuffer.append(" pageCount='"+this.getTitles().size()+"' selectedPageIndex='0'>");
		sbuffer.append("<table class='tabsetPanel'>");
					
		if("top".equalsIgnoreCase(this.getDirection())){
			sbuffer.append("<tr>"+this.getTitleDiv()+"</tr>");
			sbuffer.append("<tr>"+this.getContentDiv()+"</tr>");	
		}
		else if("bottom".equalsIgnoreCase(this.getDirection())){
			sbuffer.append("<tr>"+this.getContentDiv()+"</tr>");
			sbuffer.append("<tr>"+this.getTitleDiv()+"</tr>");					
		}		
		else if("left".equalsIgnoreCase(this.getDirection())){
			sbuffer.append("<tr>");
			sbuffer.append(this.getTitleDiv_Horizontal());
			sbuffer.append(this.getContentDiv());
			sbuffer.append("</tr>");
		}
		else if("right".equalsIgnoreCase(this.getDirection())){
			sbuffer.append("<tr>");
			sbuffer.append(this.getContentDiv());
			sbuffer.append(this.getTitleDiv_Horizontal());			
			sbuffer.append("</tr>");
		}		
		
		sbuffer.append("</table>");
		sbuffer.append("</div>");
    	
		sbuffer.append("<script>Tabset.initial('"+this.getId()+"');</script>");
			
		try{
			String result = sbuffer.toString();
			if(!ContentManage.isSetParentContent(this, result)){
				getBodyContent().getEnclosingWriter().println(result);
			}
		}catch(Exception e){
			logger.info("标签页解释错误", e);		
		}
		return super.doAfterBody();
	}	
	
	@Override
	public void release() {
	    super.release();

	    this.setId(null);
	    this.setStyle(null);
	    this.setType(null);
		this.setTitles(null);
		this.setContents(null);
		this.setScroll(null);
		this.setDirection(null);
		this.setMaximizable(null);
		this.setMinimizable(null);
		this.setWindowable(null);
	}
	public String getScroll() {
		return scroll;
	}
	public void setScroll(String scroll) {
		this.scroll = scroll;
	}
	public String getMaximizable() {
		return maximizable;
	}
	public void setMaximizable(String maximizable) {
		this.maximizable = maximizable;
	}
	public String getMinimizable() {
		return minimizable;
	}
	public void setMinimizable(String minimizable) {
		this.minimizable = minimizable;
	}
	public String getDirection() {
		return (direction!=null ? direction : "top");
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getWindowable() {
		return windowable;
	}
	public void setWindowable(String windowable) {
		this.windowable = windowable;
	}

	
}
