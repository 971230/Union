package com.ztesoft.net.param.outer;
 
import java.io.Serializable;
import java.util.List;

import model.EsearchSpec;

/**
 * 规格定义出参
 * @author shen.qiyu
 *
 */
public class SpecDefineQueryOuter implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//规格定义集合
	private List<EsearchSpec> specDefineList;

	public List<EsearchSpec> getSpecDefineList() {
		return specDefineList;
	}

	public void setSpecDefineList(List<EsearchSpec> specDefineList) {
		this.specDefineList = specDefineList;
	}

}