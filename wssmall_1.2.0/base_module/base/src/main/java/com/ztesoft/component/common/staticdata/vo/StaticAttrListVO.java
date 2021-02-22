package com.ztesoft.component.common.staticdata.vo;

import java.util.ArrayList;

import com.ztesoft.common.valueobject.ValueObject;

/**
 * 
 * @Classname     : StaticAttrVO
 * @Description   : 静态数据值对象
 * @Copyright ? 2006 ZTEsoft.
 * @Author        : fjy
 * @Create Date   : 2006-3-28
 *
 * @Last Modified : 
 * @Modified by   : 
 * @Version       : 1.0
 */
public class StaticAttrListVO extends ValueObject {
	

    private String attrCode = "";   //静态属性代码
    private ArrayList dataList =  new ArrayList();
    
    public StaticAttrListVO() {
    }

	public String getAttrCode() {
		return attrCode;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}

	public ArrayList getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList dataList) {
		this.dataList = dataList;
	}
    
}
