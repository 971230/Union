package com.ztesoft.net.service.impl;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.param.inner.ExpInstProcessedInner;
import com.ztesoft.net.param.outer.ExpInstProcessedOuter;
import com.ztesoft.net.service.IOrderExpManager;

import consts.ConstsCore;

public class OrderExpProcessThread implements Runnable {
	
	//异常处理入参
	private ExpInstProcessedInner singleInner;
	//异常处理出参
	private ExpInstProcessedOuter singleOuter;
	
	public OrderExpProcessThread(ExpInstProcessedInner singleInner) {
		this.singleInner = singleInner;
	}
	
	@Override
	public void run() {
		try{
			IOrderExpManager orderExpManager = SpringContextHolder.getBean("orderExpManager");
			singleOuter = orderExpManager.expInstProcess(singleInner);
			if(!singleOuter.getOuter_status().equals(ConstsCore.ERROR_SUCC)) {
				singleOuter.setDeal_result(singleOuter.getOuter_msg());
			}
		}catch(Exception e) {
			e.printStackTrace();
			singleOuter = new ExpInstProcessedOuter();
			singleOuter.setExp_inst_id(singleInner.getExp_inst_id());
			singleOuter.setDeal_status(EcsOrderConsts.EXPINST_RECORD_STATUS_0);
			singleOuter.setDeal_result(e.getMessage());
			singleOuter.setOuter_status(ConstsCore.ERROR_FAIL);
			singleOuter.setOuter_msg("系统异常："+e.getMessage());
		}
	}

	public ExpInstProcessedOuter getSingleOuter() {
		return singleOuter;
	}
}
