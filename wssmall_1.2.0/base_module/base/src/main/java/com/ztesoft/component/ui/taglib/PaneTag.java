package com.ztesoft.component.ui.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

public class PaneTag extends BodyTagSupport {

	private String position;
	private String style;
	private String use;
	private String autoScroll;	
	private String content;	
	private String withSpliter;
	private String withSlider;
	private String folded;
	
	private String paneId="";
	
	public String getPaneId() {
		return paneId;
	}
	public void setPaneId(String paneId) {
		this.paneId = paneId;
	}
	
	public String getFolded() {
		return (folded!=null ? folded : "false");
	}
	public void setFolded(String folded) {
		this.folded = folded;
	}
	public String getWithSpliter() {
		return withSpliter;
	}
	public void setWithSpliter(String withSpliter) {
		this.withSpliter = withSpliter;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPosition() {
		return position;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getStyle() {
		return (style!=null ? " style='"+style+"'" : "");
	}	
	public void setUse(String use) {
		this.use = use;
	}
	public String getUse() {
		return use;
	}			
	
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		
		String _content = "";
		
		if(this.getUse()!=null){
		  String content1 = (String)pageContext.getRequest().getAttribute(this.getUse());
		  _content += (content1==null)?"":content1.toString();
		}	
		
		this.setContent(_content);		
		
		return super.doStartTag();
	}	
	
	@Override
	public int doAfterBody() throws JspException {
		// TODO Auto-generated method stub
		
		String content1 = this.getBodyContent().getString();
		String _content = (content1==null)?"":content1;
		
		this.setContent(_content+this.getContent());
				
		return super.doAfterBody();
	}
		
	@Override
	public int doEndTag() throws JspException {		
		Tag layout = this.getParent();
		
		if(layout!=null){
			String idStr = "";
			if(this.getPaneId()!=null&&!"".equals(this.getPaneId())){
				idStr = " id='"+this.getPaneId()+"' ";
				
			}
			if("top".equalsIgnoreCase(this.getPosition())){ 
				
				((LayoutTag)layout).setTopPane("<div "+idStr+"class='layout_top'"+this.getStyle()+">"+this.getContent()+"</div>");	
			}
			else if("left".equalsIgnoreCase(this.getPosition())){
				((LayoutTag)layout).setLeftPane("<div "+idStr+"class='layout_left'"+this.getStyle()+">"+this.getContent()+"</div>");				
			}
			else if("center".equalsIgnoreCase(this.getPosition())){
			    ((LayoutTag)layout).setCenterPane("<div "+idStr+"class='layout_center'"+this.getStyle()+">"+this.getContent()+"</div>");				
			}
			else if("right".equalsIgnoreCase(this.getPosition())){
				((LayoutTag)layout).setRightPane("<div "+idStr+"class='layout_right'"+this.getStyle()+">"+this.getContent()+"</div>");				
			}
			else if("bottom".equalsIgnoreCase(this.getPosition())){
				((LayoutTag)layout).setBottomPane("<div "+idStr+"class='layout_bottom'"+this.getStyle()+">"+this.getContent()+"</div>");								
			}
						
			if("true".equalsIgnoreCase(this.getWithSpliter())){
				if("top".equalsIgnoreCase(this.getPosition()))
					((LayoutTag)layout).setTopSpliter(true);
				else if("left".equalsIgnoreCase(this.getPosition()))
					((LayoutTag)layout).setLeftSpliter(true);
				else if("right".equalsIgnoreCase(this.getPosition()))
					((LayoutTag)layout).setRightSpliter(true);
				else if("bottom".equalsIgnoreCase(this.getPosition()))
					((LayoutTag)layout).setBottomSpliter(true);
				
				this.setWithSlider("true");
			}		
			if("true".equalsIgnoreCase(this.getWithSlider())){
				if("top".equalsIgnoreCase(this.getPosition()))
					((LayoutTag)layout).setTopSlider(true);
				else if("left".equalsIgnoreCase(this.getPosition()))
					((LayoutTag)layout).setLeftSlider(true);
				else if("right".equalsIgnoreCase(this.getPosition()))
					((LayoutTag)layout).setRightSlider(true);
				else if("bottom".equalsIgnoreCase(this.getPosition()))
					((LayoutTag)layout).setBottomSlider(true);				
			}			
			if("true".equalsIgnoreCase(this.getFolded())){
				if("top".equalsIgnoreCase(this.getPosition()))
					((LayoutTag)layout).setTopFolded(true);
				else if("left".equalsIgnoreCase(this.getPosition()))
					((LayoutTag)layout).setLeftFolded(true);
				else if("right".equalsIgnoreCase(this.getPosition()))
					((LayoutTag)layout).setRightFolded(true);
				else if("bottom".equalsIgnoreCase(this.getPosition()))
					((LayoutTag)layout).setBottomFolded(true);						
			}
		}
		return super.doEndTag();
	}
	
	@Override
	public void release() {
		// TODO Auto-generated method stub
		super.release();	
		
		this.use = null;
		this.style = null;	
		this.position = null;
		this.autoScroll = null;
	
		this.content = null;
		this.withSpliter = null;
		this.withSlider = null;
		this.folded = null;
	}

	public String getContent() {
		return (content!=null ? content : "");
	}

	public void setContent(String content) {
		this.content = content;
	}	
	
	public String getAutoScroll() {
		return (autoScroll!=null ? autoScroll : "false");
	}
	public void setAutoScroll(String autoScroll) {
		this.autoScroll = autoScroll;
	}
	public String getWithSlider() {
		return (withSlider!=null ? withSlider : "true");
	}
	public void setWithSlider(String withSlider) {
		this.withSlider = withSlider;
	}	

	
}
