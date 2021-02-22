package com.ztesoft.net.mall.widget.setting;

import java.util.List;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.ztesoft.net.mall.core.model.Tag;
import com.ztesoft.net.mall.core.service.ITagManager;

public class GoodsListSetting extends ActionSupport{
	private ITagManager tagManager ;
	private List<Tag> tagList;
	
	@Override
	public String execute(){
		tagList = tagManager.list();
		return Action.SUCCESS;
	}
	public void setTagManager(ITagManager tagManager) {
		this.tagManager = tagManager;
	}
	public List<Tag> getTagList() {
		return tagList;
	}
	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	
}
