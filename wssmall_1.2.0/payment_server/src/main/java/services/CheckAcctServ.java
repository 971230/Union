package services;

import org.apache.commons.lang.StringUtils;

import params.ZteError;
import params.checkacctconfig.req.CheckAcctConfigReq;
import params.checkacctconfig.resp.CheckAcctConfigResp;

import com.ztesoft.net.mall.cmp.ICheckAcctManager;
import commons.CommonTools;

import consts.ConstsCore;


/**
 * 订单
 * 
 * @作者 wui
 * @创建日期 2013-10-22
 * @版本 V 1.0
 */
public class CheckAcctServ extends ServiceBase implements CheckAcctInf{

	ICheckAcctManager checkAcctManager;
	
	@Override
	public CheckAcctConfigResp checkAcct(CheckAcctConfigReq checkAcctConfigReq) {
		CheckAcctConfigResp resp = null;
		try{
		/*if (!CheckSchedulerServer.isMatchServer()) {
			return null;
		}*/
		//按systemid对账
		if(!StringUtils.isEmpty(checkAcctConfigReq.getSystem_id()))
			resp = checkAcctManager.checkAcctBySystemId(checkAcctConfigReq.getSystem_id());
		//按yyyymmddsystemid对账
		if(!StringUtils.isEmpty(checkAcctConfigReq.getDsystem_id()))
			resp = checkAcctManager.checkAcctByDateAndSystemId(checkAcctConfigReq.getDsystem_id());
		//循环配置数据全部对账
		if(StringUtils.isEmpty(checkAcctConfigReq.getSystem_id()) && StringUtils.isEmpty(checkAcctConfigReq.getDsystem_id()))
			resp = checkAcctManager.checkAcct();
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(checkAcctConfigReq,resp);
		}catch(Exception ex){
			ex.printStackTrace();
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"系统繁忙"));
		}
		return resp;
	}

	public ICheckAcctManager getCheckAcctManager() {
		return checkAcctManager;
	}

	public void setCheckAcctManager(ICheckAcctManager checkAcctManager) {
		this.checkAcctManager = checkAcctManager;
	}
	
}