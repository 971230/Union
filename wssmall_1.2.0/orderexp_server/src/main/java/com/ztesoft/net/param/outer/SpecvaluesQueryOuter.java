package com.ztesoft.net.param.outer;

import java.io.Serializable;
import model.EsearchSpecvalues;

/**
 * 规格关键字查询出参
 * @author shen.qiyu
 *
 */
public class SpecvaluesQueryOuter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//规格关键字
	private EsearchSpecvalues specvalues;

	public EsearchSpecvalues getSpecvalues() {
		return specvalues;
	}

	public void setSpecvalues(EsearchSpecvalues specvalues) {
		this.specvalues = specvalues;
	}
	
}