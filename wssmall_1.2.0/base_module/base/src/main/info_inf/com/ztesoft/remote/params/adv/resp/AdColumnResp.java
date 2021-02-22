package com.ztesoft.remote.params.adv.resp;

import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.AdColumn;

public class AdColumnResp extends ZteResponse{
	
	@ZteSoftCommentAnnotationParam(name="广告位信息",type="AdColumn",isNecessary="N",desc="广告位信息")
    private  AdColumn adColumn;
	
	@ZteSoftCommentAnnotationParam(name="广告位信息列表",type="List",isNecessary="N",desc="广告位信息列表", hasChild=true)
    private  List<AdColumn> adColumnList;
	public AdColumn getAdColumn() {
		return adColumn;
	}
	public void setAdColumn(AdColumn adColumn) {
		this.adColumn = adColumn;
	}
	public List<AdColumn> getAdColumnList() {
		return adColumnList;
	}
	public void setAdColumnList(List<AdColumn> adColumnList) {
		this.adColumnList = adColumnList;
	}
     
     
}
