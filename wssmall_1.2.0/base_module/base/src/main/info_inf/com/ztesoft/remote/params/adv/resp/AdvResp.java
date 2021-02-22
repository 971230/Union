package com.ztesoft.remote.params.adv.resp;

import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.AdColumn;
import com.ztesoft.net.app.base.core.model.Adv;
/**
 * 
 * @author wui
 * 广告返回实体
 *
 */
public class AdvResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="宽度",type="String",isNecessary="N",desc="宽度")
	private String width;
	
	@ZteSoftCommentAnnotationParam(name="高度",type="String",isNecessary="N",desc="高度")
	private String height;
	
	@ZteSoftCommentAnnotationParam(name="广告位信息",type="AdColumn",isNecessary="N",desc="广告位信息")
	private AdColumn adColumn;
	
	@ZteSoftCommentAnnotationParam(name="广告信息列表",type="List",isNecessary="N",desc="广告信息列表Lis<Adv>",hasChild=true)
	private List<Adv>  advList;
	
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public AdColumn getAdColumn() {
		return adColumn;
	}
	public void setAdColumn(AdColumn adColumn) {
		this.adColumn = adColumn;
	}
	public List<Adv> getAdvList() {
		return advList;
	}
	public void setAdvList(List<Adv> advList) {
		this.advList = advList;
	}
	
	
}
