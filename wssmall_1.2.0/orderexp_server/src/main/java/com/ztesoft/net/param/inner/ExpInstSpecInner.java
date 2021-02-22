package com.ztesoft.net.param.inner;

import java.io.Serializable;

public class ExpInstSpecInner implements Serializable{
	private static final long serialVersionUID = 1L;
	private String start_time;
	
	private String end_time;
	
	private String match_content;
	
	public String getStart_time(){
		return start_time;
	}
	public void setStart_time(String start_time){
		this.start_time = start_time;
	}
	public String getEnd_time(){
		return end_time;
	}
	public void setEnd_time(String end_time){
		this.end_time = end_time;
	}
	public String getMatch_content(){
		return match_content;
	}
	public void setMatch_content(String match_content){
		this.match_content = match_content;
	}
}
