package com.ztesoft.net.mall.core.action.order;

import java.util.List;

/**
 * 按钮对象
 * @author wui
 *
 */
public  class Button extends Element {

	private String button_name;
	private String button_id;
	private String button_style;
	private boolean visibile = true;
	private boolean disabled = false;
	
	
	public Button(String button_name,String button_id,boolean disabled,String name){
		this.button_name =button_name;
		this.button_id =button_id;
		this.disabled =disabled;
	}
	@Override
	public String toHtml(String render_type) {
		StringBuffer buffer = new StringBuffer();
		//buffer.append("<input type='button' value='"+this.button_name+"' name='"+this.button_id+"'"+(disabled?"disabled":"")+">");
		
		if("button".equals(render_type))
			buffer.append("<a href='javascript:void(0)'  to_detail='yes'   name='"+this.button_id+"'"+(disabled?"disabled":"")+"style=''><span>"+this.button_name+"</span></a><span class='tdsper'></span>");
		else
			buffer.append("<a class='greenbtnbig' href='javascript:void(0)'   name='"+this.button_id+"'"+(disabled?"disabled":"")+"style=''>"+this.button_name+"</a>");
		return  buffer.toString();
	}
	
	
	public static String toHtml(List<Button> buttons,String render_type) {
		StringBuffer htmlBuffer = new StringBuffer();
		for(Button button:buttons){
			htmlBuffer.append(button.toHtml(render_type));
		}
		return htmlBuffer.toString();
	}
	

	public String getButton_name() {
		return button_name;
	}

	public void setButton_name(String button_name) {
		this.button_name = button_name;
	}

	public String getButton_id() {
		return button_id;
	}

	public void setButton_id(String button_id) {
		this.button_id = button_id;
	}

	public String getButton_style() {
		return button_style;
	}

	public void setButton_style(String button_style) {
		this.button_style = button_style;
	}
	public boolean isVisibile() {
		return visibile;
	}
	public void setVisibile(boolean visibile) {
		this.visibile = visibile;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	
	
}
