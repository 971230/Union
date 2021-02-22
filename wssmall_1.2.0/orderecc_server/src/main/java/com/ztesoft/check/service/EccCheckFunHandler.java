package com.ztesoft.check.service;

import zte.net.ecsord.common.CommonDataFactory;
import zte.params.req.CheckFunReq;
import zte.params.resp.CheckFunResp;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.ztesoft.check.common.IdentifyReq;
import com.ztesoft.check.common.IdentifyResp;
import com.ztesoft.check.common.factory.FunDataFatory;
import com.ztesoft.check.common.factory.LogDataFatory;
import com.ztesoft.check.inf.AbstractCheckHander;
import com.ztesoft.check.model.Fun;
import com.ztesoft.check.model.FunDealLog;
import com.ztesoft.net.consts.EccConsts;

import consts.ConstsCore;

public class EccCheckFunHandler {

	public CheckFunResp handler(CheckFunReq checkReq){
		CheckFunResp checkResp = new CheckFunResp();
		checkResp.setError_code(ConstsCore.ERROR_SUCC);
		checkResp.setError_msg("接口调用成功！");
		IdentifyResp resp = null;
		
		Fun fun = FunDataFatory.getInstance().getFun(checkReq.getFun_id());
		if(null == fun){
			checkResp.setError_code(ConstsCore.ERROR_FAIL);
			checkResp.setError_msg("校验功能不存在");
			return checkResp;
		}
		AbstractCheckHander check = FunDataFatory.getInstance().getHandler(fun);
		MethodAccess access = MethodAccess.get(check.getClass());
		
		IdentifyReq req = transformData(checkReq);
		req.setFun_id(fun.getFun_id());
		req.setClazz(fun.getClazz());
		req.setImpl(fun.getImpl());
		
		FunDealLog log = new FunDealLog();
		log = LogDataFatory.getInstance().initBLog(req);
		try{
			resp = (IdentifyResp)access.invoke(check, "validByCode", req);
		}catch(Exception e){
			String expMessage =  CommonDataFactory.getInstance().getErrorStactMsg(e);
			resp = new IdentifyResp();
			resp.setCode(EccConsts.IDENTI_9999);
			resp.setMsg(expMessage);
		}
		LogDataFatory.getInstance().initALog(log, resp);
		LogDataFatory.getInstance().log(log);
		
		if(!EccConsts.IDENTI_SUCCESS.equals(resp.getCode())){
			checkResp.setError_code(ConstsCore.ERROR_FAIL);
			checkResp.setError_msg(fun.getFun_name());
			checkResp.setError_msg("接口调用失败！："+resp.getMsg());
		}
		
		return checkResp;
	}
	
	private IdentifyReq transformData(CheckFunReq src){
		IdentifyReq target = new IdentifyReq();
		target.setOrder_id(src.getObj_id());
		target.setTache_code(src.getTrace_code());
		return target;
	}
}
