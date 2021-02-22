package com.ztesoft.test.dubbo;

import junit.framework.Assert;

import org.testng.annotations.Test;

import params.ZteResponse;

import com.ztesoft.api.ZteClient;
import zte.net.iservice.params.bill.req.QryEopBillReq;
import zte.net.iservice.params.bill.req.QryLLPBillReq;
import zte.net.iservice.params.bill.req.QryNetTraReq;
import zte.net.iservice.params.bill.resp.QryEopBillResp;
import zte.net.iservice.params.bill.resp.QryLLPBillResp;
import zte.net.iservice.params.bill.resp.QryNetTraResp;
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
import com.ztesoft.test.dubbo.base.DubboClientTest;
public class SysBaseDubboClientTest extends DubboClientTest{
	@Test(enabled=true)
	public void sendOfflineMsg(){
		SendOfflineMsgReq sendOfflineMsgReq=new SendOfflineMsgReq();
		sendOfflineMsgReq.setAcc_nbr("");
		sendOfflineMsgReq.setContent("");
		sendOfflineMsgReq.setService_code("");
		sendOfflineMsgReq.setProduct_id("");
		sendOfflineMsgReq.setContent_type("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(sendOfflineMsgReq, SendOfflineMsgResp.class);
		Assert.assertEquals(response.getError_msg(),"0");
	}
	@Test(enabled=true)
	public void qryCustInfo(){
		QryCustInfoReq qryCustInfoReq=new QryCustInfoReq();
		qryCustInfoReq.setAcc_nbr("");
		qryCustInfoReq.setCust_type("");
		qryCustInfoReq.setService_code("");
		qryCustInfoReq.setProduct_id("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(qryCustInfoReq, QryCustInfoResp.class);
		Assert.assertEquals(response.getError_msg(),"0");
	}
	@Test(enabled=true)
	public void qryCustType(){
		QryCustTypeReq qryCustTypeReq=new QryCustTypeReq();
		qryCustTypeReq.setAcc_nbr("");
		qryCustTypeReq.setProduct_id("");
		qryCustTypeReq.setService_code("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(qryCustTypeReq, QryCustTypeResp.class);
		Assert.assertEquals(response.getError_msg(),"0");
	}
	@Test(enabled=true)
	public void qryNetTra(){
		QryNetTraReq qryNetTraReq=new QryNetTraReq();
		qryNetTraReq.setAcc_nbr("");
		qryNetTraReq.setBilling_cycle("");
		qryNetTraReq.setProduct_id("");
		qryNetTraReq.setService_code("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(qryNetTraReq, QryNetTraResp.class);
		Assert.assertEquals(response.getError_msg(),"0");
	}
	@Test(enabled=true)
	public void qryEopBill(){
		QryEopBillReq qryEopBillReq=new QryEopBillReq();
		qryEopBillReq.setAcc_nbr("");
		qryEopBillReq.setBilling_cycle("");
		qryEopBillReq.setProduct_id("");
		qryEopBillReq.setService_code("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(qryEopBillReq, QryEopBillResp.class);
		Assert.assertEquals(response.getError_msg(),"0");
	}
	@Test(enabled=true)
	public void qryOffDet(){
		QryOffDetReq qryOffDetReq=new QryOffDetReq();
		qryOffDetReq.setAcc_nbr("");
		qryOffDetReq.setProduct_id("");
		qryOffDetReq.setContent("");
		qryOffDetReq.setContent_type("");
		qryOffDetReq.setService_code("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(qryOffDetReq, QryOffDetResp.class);
		Assert.assertEquals(response.getError_msg(),"0");
	}
	@Test(enabled=true)
	public void qryNetPro(){
		QryNetProReq qryNetProReq=new QryNetProReq();
		qryNetProReq.setAcc_nbr("");
		qryNetProReq.setProduct_id("");
		qryNetProReq.setService_code("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(qryNetProReq, QryNetProResp.class);
		Assert.assertEquals(response.getError_msg(),"0");
	}
	@Test(enabled=true)
	public void authPwd(){
		AuthPwdReq authPwdReq=new AuthPwdReq();
		authPwdReq.setAcc_nbr("");
		authPwdReq.setAccount_type("");
		authPwdReq.setPass_type("");
		authPwdReq.setPass_word("");
		authPwdReq.setProduct_id("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(authPwdReq, AuthPwdResp.class);
		Assert.assertEquals(response.getError_msg(),"0");
	}
	@Test(enabled=true)
	public void resetPwd(){
		ResetPwdReq resetPwdReq=new ResetPwdReq();
		resetPwdReq.setAcc_nbr("");
		resetPwdReq.setAccount_type("");
		resetPwdReq.setOld_password("");
		resetPwdReq.setNew_password("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(resetPwdReq, ResetPwdResp.class);
		Assert.assertEquals(response.getError_msg(),"0");
	}
	@Test(enabled=true)
	public void qryLlpBill(){
		QryLLPBillReq qryLLPBillReq=new QryLLPBillReq();
		qryLLPBillReq.setAcc_nbr("");
		qryLLPBillReq.setDt_end("");
		qryLLPBillReq.setDt_start("");
		qryLLPBillReq.setProduct_id("");
		qryLLPBillReq.setService_code("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(qryLLPBillReq, QryLLPBillResp.class);
		Assert.assertEquals(response.getError_msg(),"0");
	}
	@Test(enabled=true)
	public void acceptServ(){
		AcceptServReq acceptServReq=new AcceptServReq();
		acceptServReq.setAcc_nbr("");
		acceptServReq.setProd_offer_nbr("");
		acceptServReq.setProduct_id("");
		acceptServReq.setService_code("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(acceptServReq, AcceptServResp.class);
		Assert.assertEquals(response.getError_msg(),"0");
	}
	@Test(enabled=true)
	public void SendMsgReq(){
		SendMsgReq sendMsgReq=new SendMsgReq();
		sendMsgReq.setAcc_nbr("");
		sendMsgReq.setContent("");
		sendMsgReq.setContent_type("");
		sendMsgReq.setProduct_id("");
		sendMsgReq.setService_code("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(sendMsgReq, SendMsgResp.class);
		Assert.assertEquals(response.getError_msg(),"0");
	}
}
