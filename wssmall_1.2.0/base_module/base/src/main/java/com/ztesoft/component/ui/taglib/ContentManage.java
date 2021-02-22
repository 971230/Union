package com.ztesoft.component.ui.taglib;

import javax.servlet.jsp.tagext.Tag;

public class ContentManage {
	
	public static boolean isSetParentContent(Tag tag, String content){

		return false;
		/* 
		boolean flag = false;
		Tag parent = tag.getParent();
		if(parent!=null){
		    if(parent instanceof ContentTag){
		        ((ContentTag)parent).setContentListChild(content);
		        flag = true;
		    }
		    else if(parent instanceof TabpageTag){
		    	((TabpageTag)parent).setContentListChild(content);
		    	flag = true;
	    	}	
		}
		return flag;
		*/
	}
	
}
