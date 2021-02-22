package zte.net.iservice.impl;

import zte.net.iservice.IEccServices;
import zte.params.req.CheckFunReq;
import zte.params.req.CheckReq;
import zte.params.resp.CheckFunResp;
import zte.params.resp.CheckResp;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version = "1.0")
public class ZteEccOpenService{
	private IEccServices eccServices;
	
	private void init() {
		if (null == eccServices) eccServices = ApiContextHolder.getBean("eccServices");
	}
	
	@ServiceMethod(method="com.eccService.check",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CheckResp check(CheckReq req){
		this.init();
		return this.eccServices.check(req);
	}
	
	@ServiceMethod(method="com.eccService.checkFun",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CheckFunResp checkFun(CheckFunReq req) {
		this.init();
		return this.eccServices.checkFun(req);
	}
	
}
