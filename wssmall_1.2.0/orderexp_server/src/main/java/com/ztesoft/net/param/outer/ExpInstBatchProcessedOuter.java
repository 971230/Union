package com.ztesoft.net.param.outer;

import java.util.List;


/**
 * 批量异常单实例处理出参
 * @author qin.yingxiong
 */
public class ExpInstBatchProcessedOuter extends BaseOuter {

	private static final long serialVersionUID = 1L;
	
	private List<ExpInstProcessedOuter> outers;

	public List<ExpInstProcessedOuter> getOuters() {
		return outers;
	}

	public void setOuters(List<ExpInstProcessedOuter> outers) {
		this.outers = outers;
	}
}