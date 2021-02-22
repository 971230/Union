package zte.net.iservice;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

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
import zte.net.iservice.params.user.req.UserLoginReq;
import zte.net.iservice.params.user.resp.AuthPwdResp;
import zte.net.iservice.params.user.resp.QryCustInfoResp;
import zte.net.iservice.params.user.resp.QryCustTypeResp;
import zte.net.iservice.params.user.resp.ResetPwdResp;
import zte.net.iservice.params.user.resp.UserLoginResp;

/**
 * 系统管理，提供给网厅，掌厅
 * @author xuefeng
 */
@ZteSoftCommentAnnotation(type = "class", desc = "基础服务API", summary = "基础服务API[用户登录、用户注册、版本更新、修改密码、用户应用]")
public interface ISysBaseService {

    @ZteSoftCommentAnnotation(type = "method", desc = "用户登录", summary = "用户登录")
    public UserLoginResp userLogin(UserLoginReq loginReq);
 
    @ZteSoftCommentAnnotation(type = "method", desc = "离线消息推送", summary = "离线消息推送")
    public SendOfflineMsgResp sendOfflineMsg(SendOfflineMsgReq sendOfflineMsgReq);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "客户信息查询", summary = "客户信息查询")
    public QryCustInfoResp qryCustInfo(QryCustInfoReq qryCustInfoReq);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "客户类型查询", summary = "客户类型查询")
    public QryCustTypeResp qryCustType(QryCustTypeReq qryCustTypeReq);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "上网流量查询", summary = "上网流量查询")
    public QryNetTraResp qryNetTra(QryNetTraReq qryNetTraReq);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "月账单查询", summary = "月账单查询")
    public QryEopBillResp qryEopBill(QryEopBillReq qryEopBillReq);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "X套餐业务查询", summary = "X套餐业务查询")
    public QryOffDetResp qryOffDet(QryOffDetReq qryOffDetReq);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "入网进度查询", summary = "入网进度查询")
    public QryNetProResp qryNetPro(QryNetProReq qryNetProReq);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "服务密码认证", summary = "服务密码认证")
    public AuthPwdResp authPwd(AuthPwdReq authPwdReq);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "服务密码重置", summary = "服务密码重置")
    public ResetPwdResp resetPwd(ResetPwdReq resetPwdReq);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "连连钱包收支明细查询", summary = "连连钱包收支明细查询")
    public QryLLPBillResp qryLlpBill(QryLLPBillReq qryLLPBillReq);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "上网流量包、短信业务包办理", summary = "上网流量包、短信业务包办理")
    public AcceptServResp acceptServ(AcceptServReq acceptServReq);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "短信发送", summary = "短信发送")
    public SendMsgResp sendMsg(SendMsgReq sendMsgReq);
    
	@ZteSoftCommentAnnotation(type="method",desc="查询发布组织信息",summary="查询发布组织信息")
	public GoodsOrgResp queryGoodsOrg(GoodsOrgReq goodsOrgReq);
    
    
}
