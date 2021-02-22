package com.test;

import org.apache.log4j.Logger;

import zte.net.ecsord.common.CommonDataFactory;
import zte.params.req.CheckFunReq;
import zte.params.req.CheckReq;
import zte.params.resp.CheckFunResp;
import zte.params.resp.CheckResp;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.DefaultZteRopClient;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.consts.EccConsts;

import consts.ConstsCore;

public class EccTest {
	private static Logger logger = Logger.getLogger(EccTest.class);
	public static void main(String[] args) {
//		check();
//		checkFun006();
//		checkFun016();
//		checkFun017();
		checkFun018();
//		checkFun151681414320000030();
	}

	
	private static ZteClient getClient() {
		return new DefaultZteRopClient("http://localhost:8080/router",AppKeyEnum.APP_KEY_WSSMALL_ECSORD.getAppKey(),AppKeyEnum.APP_KEY_WSSMALL_ECSORD.getAppSec(),"1.0");
	}
	
	/**
	 * 业务环节功能校验自测
	 */
	public static void check() {
		CheckReq req = new CheckReq();
		req.setTrace_code(ConstsCore.TRACE_V);
		req.setObj_id("TB151081439150000008");
		req.setObj_type("order");
		req.setExe_time(EccConsts.EXE_TIME_AFTER);
		CheckResp resp = getClient().execute(req, CheckResp.class);
		logger.info("result code:"+resp.getError_code());
	}	

	/**
	 * 单校验功能调用自测:品同步WMS校验
	 */
	public static void checkFun018() {
		CheckFunReq req = new CheckFunReq();
		req.setObj_id("DG152161206511733738");
		req.setObj_type("order");
		req.setFun_id(EccConsts.FUN_018);
		CheckFunResp resp = getClient().execute(req, CheckFunResp.class);
		logger.info("result code:"+resp.getError_code());
	}

	/**
	 * 单校验功能调用自测:流程匹配校验
	 */
	public static void checkFun017() {
		CheckFunReq req = new CheckFunReq();
		req.setObj_id("DG152161206511733738");
		req.setObj_type("order");
		req.setFun_id(EccConsts.FUN_017);
		CheckFunResp resp = getClient().execute(req, CheckFunResp.class);
		logger.info("result code:"+resp.getError_code());
	}
	
	/**
	 * 单校验功能调用自测:礼品是否存在校验  
	 */
	public static void checkFun016() {
		CheckFunReq req = new CheckFunReq();
		req.setObj_id("DG152161206511733738");
		req.setObj_type("order");
		req.setFun_id(EccConsts.FUN_016);
		CheckFunResp resp = getClient().execute(req, CheckFunResp.class);
		logger.info("result code:"+resp.getError_code());
	}	
	
	/**
	 * 单校验功能调用自测:商品是否存在
	 */
	public static void checkFun006() {
		CheckFunReq req = new CheckFunReq();
		req.setObj_id("DG152161206511733738");
		req.setObj_type("order");
		req.setFun_id(EccConsts.FUN_006);
		CheckFunResp resp = getClient().execute(req, CheckFunResp.class);
		logger.info("result code:"+resp.getError_code());
	}	

	/**
	 * 单校验功能调用自测(sql实现方式):生产模式不能为空
	 */
	public static void checkFun151681210170000023() {
		CheckFunReq req = new CheckFunReq();
		req.setObj_id("TB151081439150000008");
		req.setObj_type("order");
		req.setFun_id("151681210170000023");
		CheckFunResp resp = getClient().execute(req, CheckFunResp.class);
		logger.info("result code:"+resp.getError_code());
	}

	/**
	 * 单校验功能调用自测(sql实现方式):流程实例id不能为空
	 */
	public static void checkFun151681414320000030() {
		CheckFunReq req = new CheckFunReq();
		req.setObj_id("TB151081439150000008");
		req.setObj_type("order");
		req.setFun_id("151681414320000030");
		CheckFunResp resp = getClient().execute(req, CheckFunResp.class);
		logger.info("result code:"+resp.getError_code());
	}
	
	/**
	 * 订单标准化统一定义业务(前)：订单标准收单报文——当前配置空
	 */
	public static CheckResp ordStardPre(){
		CheckReq req = new CheckReq();
		req.setBiz_id(EccConsts.ORDER_STANDER_BIZ);
		req.setExe_time(EccConsts.EXE_TIME_BEFORE);
		req.setObj_id("");
		req.setObj_type("order_queue");
		req.setTrace_code(ConstsCore.TRACE_V);
        CheckResp checkRsp = CommonDataFactory.getInstance().checkProxy(req);
        if(!ConstsCore.ERROR_SUCC.equals(checkRsp.getError_code())){
            return checkRsp;
        }
        
        return null;
	}
	
	/**
	 * (环节V)订单标准化统一定义业务(后):与订单标准化方案执行的前校验重复——当前配置空
	 */
	public static CheckResp ordStardAft(){
		CheckReq req = new CheckReq();
		req.setBiz_id(EccConsts.ORDER_STANDER_BIZ);
		req.setExe_time(EccConsts.EXE_TIME_AFTER);
		req.setObj_id("");
		req.setObj_type("order_queue");
		req.setTrace_code(ConstsCore.TRACE_V);
        CheckResp checkRsp = CommonDataFactory.getInstance().checkProxy(req);
        if(!ConstsCore.ERROR_SUCC.equals(checkRsp.getError_code())){
            return checkRsp;
        }
        
        return null;
	}	
	
	/**
	 * 正式单系统流转方案统一定义业务(前):flow_id为空、order_model为空、要校验《校验系统业务梳理V1.2》列表内容
	 */
	public static CheckResp ordfFlowBef(){
		CheckReq req = new CheckReq();
		req.setBiz_id(EccConsts.ORDER_PLAN_FLOW_BIZ);
		req.setExe_time(EccConsts.EXE_TIME_BEFORE);
		req.setObj_id("1");
		req.setObj_type("order");
		req.setTrace_code(ConstsCore.TRACE_R);
        CheckResp checkRsp = CommonDataFactory.getInstance().checkProxy(req);
        if(!ConstsCore.ERROR_SUCC.equals(checkRsp.getError_code())){
            return checkRsp;
        }
        
        return null;
	}
	
	/**
	 * 正式单系统流转方案统一定义业务(后):flow_id不为空、order_model不为空
	 */
	public static CheckResp ordfFlowAft(){
		CheckReq req = new CheckReq();
		req.setBiz_id(EccConsts.ORDER_PLAN_FLOW_BIZ);
		req.setExe_time(EccConsts.EXE_TIME_AFTER);
		req.setTrace_code(ConstsCore.TRACE_R);
		req.setObj_id("WCS15120521374810601195");
		req.setObj_type("order");
        CheckResp checkRsp = CommonDataFactory.getInstance().checkProxy(req);
        if(!ConstsCore.ERROR_SUCC.equals(checkRsp.getError_code())){
            return checkRsp;
        }
        
        return null;
	}
	

	
	/**
	 * 同步订单中实物SKU到WMS解决方案组件：ecc下调用不了
	 */
	public static CheckResp synSKUToWMSPlan(){
	
        
        return null;
	}
	
}
