package zte.net.iservice.impl;

import org.springframework.stereotype.Service;

import zte.net.iservice.ISysBaseService;
import zte.net.iservice.params.bill.req.QryEopBillReq;
import zte.net.iservice.params.bill.req.QryLLPBillReq;
import zte.net.iservice.params.bill.req.QryNetTraReq;
import zte.net.iservice.params.bill.resp.QryEopBillResp;
import zte.net.iservice.params.bill.resp.QryLLPBillResp;
import zte.net.iservice.params.bill.resp.QryNetTraResp;
import zte.net.iservice.params.goodsOrg.req.GoodsOrgReq;
import zte.net.iservice.params.goodsOrg.resp.GoodsOrgResp;
import zte.net.iservice.params.serv.req.AcceptServReq;
import zte.net.iservice.params.serv.req.QryNetProReq;
import zte.net.iservice.params.serv.req.QryOffDetReq;
import zte.net.iservice.params.serv.resp.AcceptServResp;
import zte.net.iservice.params.serv.resp.QryNetProResp;
import zte.net.iservice.params.serv.resp.QryOffDetResp;
import zte.net.iservice.params.sms.req.SendMsgReq;
import zte.net.iservice.params.sms.req.SendOfflineMsgReq;
import zte.net.iservice.params.sms.resp.SendMsgResp;
import zte.net.iservice.params.sms.resp.SendOfflineMsgResp;
import zte.net.iservice.params.user.req.AuthPwdReq;
import zte.net.iservice.params.user.req.QryCustInfoReq;
import zte.net.iservice.params.user.req.QryCustTypeReq;
import zte.net.iservice.params.user.req.ResetPwdReq;
import zte.net.iservice.params.user.resp.AuthPwdResp;
import zte.net.iservice.params.user.resp.QryCustInfoResp;
import zte.net.iservice.params.user.resp.QryCustTypeResp;
import zte.net.iservice.params.user.resp.ResetPwdResp;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.remote.utils.SysConst;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version="1.0")
@Service
public class ZteSysBaseOpenService {
	
//	@Resource
	private ISysBaseService sysBaseService;
	
    private void init() {
    	if (null == sysBaseService) sysBaseService = ApiContextHolder.getBean("sysBaseService");
    }
	
	@ServiceMethod(method=SysConst.API_PREFIX + "sendOfflineMsg",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public SendOfflineMsgResp sendOfflineMsg(SendOfflineMsgReq sendOfflineMsgReq) {
		this.init();
		return sysBaseService.sendOfflineMsg(sendOfflineMsgReq);
	}
	
	@ServiceMethod(method=SysConst.API_PREFIX + "qryCustInfo",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public QryCustInfoResp qryCustInfo(QryCustInfoReq qryCustInfoReq){
		this.init();
		return sysBaseService.qryCustInfo(qryCustInfoReq);
	}
	
	@ServiceMethod(method=SysConst.API_PREFIX + "qryCustType",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public QryCustTypeResp qryCustType(QryCustTypeReq qryCustTypeReq) {
		this.init();
		return sysBaseService.qryCustType(qryCustTypeReq);
	}
	
	@ServiceMethod(method=SysConst.API_PREFIX + "netTra",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public QryNetTraResp qryNetTra(QryNetTraReq qryNetTraReq) {
		this.init();
		return sysBaseService.qryNetTra(qryNetTraReq);
	}
	
	@ServiceMethod(method=SysConst.API_PREFIX + "qryEopBill",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public QryEopBillResp qryEopBill(QryEopBillReq qryEopBillReq) {
		this.init();
		return sysBaseService.qryEopBill(qryEopBillReq);
	}
	
	@ServiceMethod(method=SysConst.API_PREFIX + "qryOffDet",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public QryOffDetResp qryOffDet(QryOffDetReq qryOffDetReq) {
		this.init();
		return sysBaseService.qryOffDet(qryOffDetReq);
	}
	
	@ServiceMethod(method=SysConst.API_PREFIX + "qryNetPro",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public QryNetProResp qryNetPro(QryNetProReq qryNetProReq) {
		this.init();
		return sysBaseService.qryNetPro(qryNetProReq);
	}
	
	@ServiceMethod(method=SysConst.API_PREFIX + "authPwd",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public AuthPwdResp authPwd(AuthPwdReq authPwdReq) {
		this.init();
		return sysBaseService.authPwd(authPwdReq);
	}
	
	@ServiceMethod(method=SysConst.API_PREFIX + "resetPwd",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public ResetPwdResp resetPwd(ResetPwdReq resetPwdReq) {
		this.init();
		return sysBaseService.resetPwd(resetPwdReq);
	}
	
	@ServiceMethod(method=SysConst.API_PREFIX + "qryLLPBill",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public QryLLPBillResp qryLlpBill(QryLLPBillReq qryLLPBillReq) {
		this.init();
		return sysBaseService.qryLlpBill(qryLLPBillReq);
	}
	
	@ServiceMethod(method=SysConst.API_PREFIX + "acceptServ",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public AcceptServResp acceptServ(AcceptServReq acceptServReq) {
		this.init();
		return sysBaseService.acceptServ(acceptServReq);
	}
	
	@ServiceMethod(method=SysConst.API_PREFIX + "sendMsg",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public SendMsgResp sendMsg(SendMsgReq sendMsgReq) {
		this.init();
		return sysBaseService.sendMsg(sendMsgReq);
	}

	@ServiceMethod(method=SysConst.API_PREFIX + "queryGoodsOrg",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public GoodsOrgResp queryGoodsOrg(GoodsOrgReq goodsOrgReq){
		this.init();
		return sysBaseService.queryGoodsOrg(goodsOrgReq);
	}
}
