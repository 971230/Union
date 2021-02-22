package zte.net.iservice.impl;

import params.req.ZJLocalMallOrderStandardReq;
import params.resp.ZJLocalMallOrderStandardResp;
import zte.net.iservice.IZJOrderstdService;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;
@ServiceMethodBean(version="1.0")
public class ZteZJOrderstdOpensService {
	private IZJOrderstdService zjOrderstdService;
	 
	public void init(){
		zjOrderstdService = ApiContextHolder.getBean("zjOrderstdService");;
	}
	
	@ServiceMethod(method="zte.net.orderstd.ZJLocalMallOrderStandard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZJLocalMallOrderStandardResp zjLocalMallOrderStandard(
			ZJLocalMallOrderStandardReq req) {
		init();
		return zjOrderstdService.zjLocalMallOrderStandard(req);
	}
}
