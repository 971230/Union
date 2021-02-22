package com.ztesoft.orderstd.service;

import params.req.CodePurchaseMallOrderStandardReq;
import params.req.DepositOrderStandardReq;
import params.req.GroupOrderStandardReq;
import params.req.InternetGroupOrderStandardReq;
import params.req.FixBusiOrderStandardReq;
import params.req.MobileBusiCtnStandardReq;
import params.req.NewMallOrderStandardReq;
import params.req.ObjectReplaceStandardReq;
import params.resp.CodePurchaseMallOrderStandardResp;
import params.resp.DepositOrderStandardResp;
import params.resp.GroupOrderStandardResp;
import params.resp.InternetGroupStandardResp;
import params.resp.FixBusiOrderStandardResp;
import params.resp.MobileBusiCtnStandardResp;
import params.resp.NewMallOrderStandardResp;
import params.resp.ObjectReplaceStandardResp;

public interface INewOrderStandingManager {
	public MobileBusiCtnStandardResp mobileBusiCtnStandard(MobileBusiCtnStandardReq req);
	public NewMallOrderStandardResp newMallOrderStandard(NewMallOrderStandardReq req);
    public InternetGroupStandardResp internetGroupOrderStandard(
            InternetGroupOrderStandardReq req);
    public GroupOrderStandardResp groupOrderStandard(GroupOrderStandardReq req);
	
	/**
	 * 线上固网业务通用收单接口-标准化新方法
	 * @param req
	 * @return
	 */
	public FixBusiOrderStandardResp fixBusiOrderStandard(FixBusiOrderStandardReq req);
    public CodePurchaseMallOrderStandardResp codePurchaseMallOrderStandard(
            CodePurchaseMallOrderStandardReq req);
    
    public ObjectReplaceStandardResp objectReplaceStandard(ObjectReplaceStandardReq req);
    
    public NewMallOrderStandardResp orderUpdate(NewMallOrderStandardReq req) throws Exception;
    public DepositOrderStandardResp orderDepositStandard(DepositOrderStandardReq req);
}
