package com.ztesoft.check.service;

import java.util.List;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.params.req.CheckReq;
import zte.params.resp.CheckResp;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.ztesoft.check.common.Consts;
import com.ztesoft.check.common.IdentifyReq;
import com.ztesoft.check.common.IdentifyResp;
import com.ztesoft.check.common.factory.BizDataFatory;
import com.ztesoft.check.common.factory.FunDataFatory;
import com.ztesoft.check.common.factory.LogDataFatory;
import com.ztesoft.check.inf.AbstractCheckHander;
import com.ztesoft.check.model.Biz;
import com.ztesoft.check.model.BizFator;
import com.ztesoft.check.model.Fun;
import com.ztesoft.check.model.FunDealLog;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.consts.EccConsts;

import consts.ConstsCore;

public class EccCheckHandler {

	public CheckResp handler(CheckReq checkReq){
		CheckResp checkResp = new CheckResp();
		checkResp.setError_code(ConstsCore.ERROR_SUCC);
		checkResp.setError_msg("接口调用成功！");
		checkResp.setStack_trace_info("接口调用成功！");
		//业务匹配
		Biz biz = getBiz(checkReq);
		//biz_id小于0，直接返回
		if(null == biz ||(null != biz && biz.getBiz_id().startsWith(Consts.FILTER_BIZ_FLAG))){
			checkResp.setError_code(ConstsCore.ERROR_SUCC);
			checkResp.setError_msg("没有设置校验业务！");
			checkResp.setStack_trace_info("没有设置校验业务！");
		}else{
			IdentifyReq req = transformData(checkReq);
			req.setBiz_id(biz.getBiz_id());
			IdentifyResp resp = null;
			String key = biz.getBiz_id() + "#" + checkReq.getTrace_code() + "#" + checkReq.getExe_time();
			List<Fun> list = FunDataFatory.getInstance().getBizTacheFunList(key);
			if(null == list || list.isEmpty()){
				checkResp.setError_code(ConstsCore.ERROR_SUCC);
				checkResp.setError_msg("没有设置校验功能！");
				checkResp.setStack_trace_info("没有设置校验功能！");
			}else{
				String exe_method = FunDataFatory.getInstance().getBizTacheExeMethod(key);
				if(null != list && list.size()>0){
					for(Fun fun : list){//遍历List<Fun>执行对应的实现
						AbstractCheckHander check = FunDataFatory.getInstance().getHandler(fun);
						MethodAccess access = MethodAccess.get(check.getClass());
						req.setFun_id(fun.getFun_id());
						req.setClazz(fun.getClazz());
						req.setImpl(fun.getImpl());
						FunDealLog funLog = new FunDealLog();
						funLog = LogDataFatory.getInstance().initBLog(req);
						try{
							resp = (IdentifyResp)access.invoke(check, "validByCode", req);
						}catch(Exception e){
							String expMessage =  CommonDataFactory.getInstance().getErrorStactMsg(e);
							resp = new IdentifyResp();
							resp.setCode(EccConsts.IDENTI_9999);
							resp.setMsg(expMessage);
						}
						LogDataFatory.getInstance().initALog(funLog, resp);
						LogDataFatory.getInstance().log(funLog);
						
						if(!EccConsts.IDENTI_SUCCESS.equals(resp.getCode()) && Consts.BIZ_EXE_METHOD_1.equals(exe_method)){//校验不通过，且校验链是串行执行——有报错直接返回
							checkResp.setError_code(ConstsCore.ERROR_FAIL);
							checkResp.setError_msg(fun.getFun_name());
							checkResp.setStack_trace_info(StringUtils.substr(resp.getMsg(), 1000));
							break;
						}
						if(!EccConsts.IDENTI_SUCCESS.equals(resp.getCode()) && Consts.BIZ_EXE_METHOD_2.equals(exe_method)){//校验不通过，且校验链是并行执行——有报错直接返回
							checkResp.setError_code(ConstsCore.ERROR_FAIL);
							checkResp.setError_msg(fun.getFun_name());
							checkResp.setError_msg(checkResp.getError_msg()+";"+StringUtils.substr(resp.getMsg(), 100));
						}
					}
				}
			}

		}
		
		return checkResp;
	}
	/**
	 * 校验系统的业务应用订单系统的流程匹配思路，校验系统的业务是独立的匹配：由订单属性匹配业务
	 * @param checkReq
	 * @return
	 */
	private Biz getBiz(CheckReq checkReq){
		Biz biz = null;
		BizFator bizFator = new BizFator();
		String obj_type = checkReq.getObj_type();
		String order_id = "", net_type="", goods_type="", user_flag="", need_open_act="", biz_id="",delivery_method="",order_source="";
		biz_id = checkReq.getBiz_id();

		//先取反向业务
		if("order".equals(obj_type)){
			order_id = checkReq.getObj_id();
			delivery_method = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SENDING_TYPE);//发货方式
			order_source = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_ORIGIN);//订单来源
			bizFator.setDelivery_method(delivery_method);
			bizFator.setOrder_source(order_source);
			biz = BizDataFatory.getInstance().getAntiBizByFator(bizFator);
		}/*else if("order_queue".equals(obj_type)){//订单标准化之前
			order_id = checkReq.getObj_id();
			return BizDataFatory.getInstance().getBizByKey(Consts.ORDER_STANDER_BIZ);
		}*/
		
/*		//有biz_id取biz
		if(null == biz && !StringUtils.isEmpty(biz_id)){
			biz = BizDataFatory.getInstance().getBizByKey(biz_id);
		}*/
		
		//最后是因子匹配出来
		if(null == biz && "order".equals(obj_type)){
			order_id = checkReq.getObj_id();
			net_type = CommonDataFactory.getInstance().getProductSpec(order_id,SpecConsts.TYPE_ID_10002, "", SpecConsts.NET_TYPE);//4G
			goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);//20002
			user_flag = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);//1
			need_open_act = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_ACCOUNT);//0
			bizFator.setNet_type(net_type);
			bizFator.setGoods_type(goods_type);
			bizFator.setUser_flag(user_flag);
			bizFator.setNeed_open_act(need_open_act);
			biz = BizDataFatory.getInstance().getBizByFator(bizFator);
		}
		
		return biz;
	}
	
	private IdentifyReq transformData(CheckReq src){
		IdentifyReq target = new IdentifyReq();
		target.setOrder_id(src.getObj_id());
		target.setTache_code(src.getTrace_code());
		target.setExe_time(src.getExe_time());
		return target;
	}
	
}
