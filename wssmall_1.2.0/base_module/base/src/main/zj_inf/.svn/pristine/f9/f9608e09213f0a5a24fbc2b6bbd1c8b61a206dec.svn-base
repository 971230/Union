package zte.net.iservice.impl;

import params.ZteResponse;
import zte.net.ecsord.params.base.req.SimulatorDDGJReq;
import zte.net.ecsord.params.base.resp.SimulatorDDGJResp;
import zte.net.ecsord.params.bss.req.AccountOpenFormalSubmissionReq;
import zte.net.ecsord.params.bss.req.ActionRecvBSSReq;
import zte.net.ecsord.params.bss.req.AgencyAcctPayNotifyReq;
import zte.net.ecsord.params.bss.req.BSSAccountOfficialSubReq;
import zte.net.ecsord.params.bss.req.BSSAccountReq;
import zte.net.ecsord.params.bss.req.BSSActivateOperReq;
import zte.net.ecsord.params.bss.req.BSSOrderSubReq;
import zte.net.ecsord.params.bss.req.BaseBSSReq;
import zte.net.ecsord.params.bss.req.CBssLoginCertReq;
import zte.net.ecsord.params.bss.req.CustomerCheckReq;
import zte.net.ecsord.params.bss.req.FeeInformReq;
import zte.net.ecsord.params.bss.req.LocalGoodsStatusSynchronizationReq;
import zte.net.ecsord.params.bss.req.MobileCBssOutReq;
import zte.net.ecsord.params.bss.req.MobileNetworkServiceHandleReq;
import zte.net.ecsord.params.bss.req.NumberResourceChangePreCaptureZjRequest;
import zte.net.ecsord.params.bss.req.NumberResourceNewGroupOrderZjRequest;
import zte.net.ecsord.params.bss.req.NumberResourceQueryZjBssRequest;
import zte.net.ecsord.params.bss.req.NumberStateChangeBssRequest;
import zte.net.ecsord.params.bss.req.OrderAccountOrBuybackInformReq;
import zte.net.ecsord.params.bss.req.OrderFinAccountSyncReq;
import zte.net.ecsord.params.bss.req.OrderListForWorkReq;
import zte.net.ecsord.params.bss.req.PageCallVerifyBSSReq;
import zte.net.ecsord.params.bss.req.PreCommitReq;
import zte.net.ecsord.params.bss.req.RecordInfLogReq;
import zte.net.ecsord.params.bss.req.SPBuyCBssOutReq;
import zte.net.ecsord.params.bss.req.SPBuyServiceHandleReq;
import zte.net.ecsord.params.bss.req.SSOLoginBSSReq;
import zte.net.ecsord.params.bss.req.SSOLoginReqVerifyBSSReq;
import zte.net.ecsord.params.bss.resp.AccountOpenFormalSubmissionResp;
import zte.net.ecsord.params.bss.resp.ActionRecvBSSResp;
import zte.net.ecsord.params.bss.resp.AgencyAcctPayNotifyRsp;
import zte.net.ecsord.params.bss.resp.BSSAccountOfficialSubResp;
import zte.net.ecsord.params.bss.resp.BSSActivateOperRsp;
import zte.net.ecsord.params.bss.resp.BaseBSSResp;
import zte.net.ecsord.params.bss.resp.CustomerCheckResp;
import zte.net.ecsord.params.bss.resp.FeeInformResp;
import zte.net.ecsord.params.bss.resp.LocalGoodsStatusSynchronizationResp;
import zte.net.ecsord.params.bss.resp.MobileCBssOutResp;
import zte.net.ecsord.params.bss.resp.MobileNetworkServiceHandleResp;
import zte.net.ecsord.params.bss.resp.NumberResourceChangePreCaptureZjResponse;
import zte.net.ecsord.params.bss.resp.NumberResourceQueryZjBssResponse;
import zte.net.ecsord.params.bss.resp.NumberStateChangeBssResp;
import zte.net.ecsord.params.bss.resp.OrderAccountOrBuybackInformResp;
import zte.net.ecsord.params.bss.resp.OrderFinAccountSyncResp;
import zte.net.ecsord.params.bss.resp.OrderListForWorkResp;
import zte.net.ecsord.params.bss.resp.PageCallVerifyBSSResp;
import zte.net.ecsord.params.bss.resp.PreCommitResp;
import zte.net.ecsord.params.bss.resp.SPBuyCBssOutResp;
import zte.net.ecsord.params.bss.resp.SPBuyServiceHandleResp;
import zte.net.ecsord.params.bss.resp.SSOLoginBSSResp;
import zte.net.ecsord.params.bss.resp.SSOLoginReqVerifyBSSResp;
import zte.net.ecsord.params.busiopen.account.req.OrderAKeyActReq;
import zte.net.ecsord.params.busiopen.account.resp.OrderAKeyActResp;
import zte.net.ecsord.params.busiopen.ordinfo.req.OrderInfoReq;
import zte.net.ecsord.params.busiopen.ordinfo.req.OrderTreesInfoReq;
import zte.net.ecsord.params.busiopen.ordinfo.resp.OrderInfoResp;
import zte.net.ecsord.params.busiopen.ordinfo.resp.OrderTreesInfoResp;
import zte.net.ecsord.params.busiopen.separteflow.req.OrderSeparteReq;
import zte.net.ecsord.params.busiopen.separteflow.req.PushOrderID2MemcacheReq;
import zte.net.ecsord.params.busiopen.separteflow.resp.OrderSeparteResp;
import zte.net.ecsord.params.busiopen.separteflow.resp.PushOrderID2MemcacheResp;
import zte.net.ecsord.params.busiopen.zbinfexec.req.OrderInfExecReq;
import zte.net.ecsord.params.busiopen.zbinfexec.resp.OrderInfExecResp;
import zte.net.ecsord.params.ecaop.req.AccountPhotoRequest;
import zte.net.ecsord.params.ecaop.req.AopSmsSendReq;
import zte.net.ecsord.params.ecaop.req.AreaQueryReq;
import zte.net.ecsord.params.ecaop.req.BSSCustomerInfoCheckReq;
import zte.net.ecsord.params.ecaop.req.BSSGetCardMsgReq;
import zte.net.ecsord.params.ecaop.req.BSSOrderReverseSalesReq;
import zte.net.ecsord.params.ecaop.req.BSSWriteCardResultNoticeReq;
import zte.net.ecsord.params.ecaop.req.BareMachineSaleReq;
import zte.net.ecsord.params.ecaop.req.BusinessAcceptenceAndVerificationReq;
import zte.net.ecsord.params.ecaop.req.CannelOrder23to4Request;
import zte.net.ecsord.params.ecaop.req.CardDataQryRequest;
import zte.net.ecsord.params.ecaop.req.CardDataSynRequest;
import zte.net.ecsord.params.ecaop.req.CardDataSynRequestZJ;
import zte.net.ecsord.params.ecaop.req.ChangeMachineSubReq;
import zte.net.ecsord.params.ecaop.req.Check23to4Request;
import zte.net.ecsord.params.ecaop.req.CheckIDECAOPRequset;
import zte.net.ecsord.params.ecaop.req.CunFeeSongFee4GReq;
import zte.net.ecsord.params.ecaop.req.CunFeeSongFee4GReqZJ;
import zte.net.ecsord.params.ecaop.req.CunFeeSongFee4GSubmitReq;
import zte.net.ecsord.params.ecaop.req.CunFeeSongFee4GSubmitReqZJ;
import zte.net.ecsord.params.ecaop.req.CunFeeSongFeeRequest;
import zte.net.ecsord.params.ecaop.req.CustInfoCreateReq;
import zte.net.ecsord.params.ecaop.req.CustInfoModReq;
import zte.net.ecsord.params.ecaop.req.CustomerInfoCheckReq;
import zte.net.ecsord.params.ecaop.req.DevelopPersonQueryReq;
import zte.net.ecsord.params.ecaop.req.EmployeesInfoCheckRequest;
import zte.net.ecsord.params.ecaop.req.FlowPacketApplyReq;
import zte.net.ecsord.params.ecaop.req.FlowPacketPreReq;
import zte.net.ecsord.params.ecaop.req.GetBlanckCardDataReq;
import zte.net.ecsord.params.ecaop.req.HandleSpServiceReq;
import zte.net.ecsord.params.ecaop.req.IntentOrderQueryForCBReq;
import zte.net.ecsord.params.ecaop.req.MainViceOpenReq;
import zte.net.ecsord.params.ecaop.req.MessageSyncReq;
import zte.net.ecsord.params.ecaop.req.OldUserBuyApplyReq;
import zte.net.ecsord.params.ecaop.req.OldUserBuySubmitReq;
import zte.net.ecsord.params.ecaop.req.OldUserCheck3GReq;
import zte.net.ecsord.params.ecaop.req.OldUserRenActivityReq;
import zte.net.ecsord.params.ecaop.req.OldUserRenActivitySubmitReq;
import zte.net.ecsord.params.ecaop.req.Open23to4ApplyReq;
import zte.net.ecsord.params.ecaop.req.OpenAccountSubmitReq;
import zte.net.ecsord.params.ecaop.req.OpenDealApplyReq;
import zte.net.ecsord.params.ecaop.req.OpenDealApplyReqZJ;
import zte.net.ecsord.params.ecaop.req.OpenDealSubmitReq;
import zte.net.ecsord.params.ecaop.req.OpenDealSubmitReqZJ;
import zte.net.ecsord.params.ecaop.req.OrderActiveReq;
import zte.net.ecsord.params.ecaop.req.OrderCancelReq;
import zte.net.ecsord.params.ecaop.req.OrderInfoSynReq;
import zte.net.ecsord.params.ecaop.req.OrderQueryReq;
import zte.net.ecsord.params.ecaop.req.OrderReverseSalesReq;
import zte.net.ecsord.params.ecaop.req.OrderTerminalCheckReq;
import zte.net.ecsord.params.ecaop.req.ProdChangeApplyReq;
import zte.net.ecsord.params.ecaop.req.ProdChangeCannelRequest;
import zte.net.ecsord.params.ecaop.req.QueryStdAddressReq;
import zte.net.ecsord.params.ecaop.req.ResourcePreCheckReq;
import zte.net.ecsord.params.ecaop.req.ReturnFileReq;
import zte.net.ecsord.params.ecaop.req.ReturnMachineApplyReq;
import zte.net.ecsord.params.ecaop.req.ReturnMachineSubmitReq;
import zte.net.ecsord.params.ecaop.req.SubProOrderReq;
import zte.net.ecsord.params.ecaop.req.SynCardDataReq;
import zte.net.ecsord.params.ecaop.req.TerminalStatusQueryChanageBatchReq;
import zte.net.ecsord.params.ecaop.req.TerminalStatusQueryChanageReq;
import zte.net.ecsord.params.ecaop.req.TrafficOrderRequest;
import zte.net.ecsord.params.ecaop.req.UserCountCheckReq;
import zte.net.ecsord.params.ecaop.req.UserInfoCheck3BackReq;
import zte.net.ecsord.params.ecaop.req.UserInfoCheck3BackReqZJ;
import zte.net.ecsord.params.ecaop.req.UserJoinMainViceCardReq;
import zte.net.ecsord.params.ecaop.req.WriteCardPreReq;
import zte.net.ecsord.params.ecaop.req.WriteCardResultNoticeReq;
import zte.net.ecsord.params.ecaop.req.WriteCardResultNoticeReqZJ;
import zte.net.ecsord.params.ecaop.resp.AccountPhotoResponse;
import zte.net.ecsord.params.ecaop.resp.AopSmsSendResp;
import zte.net.ecsord.params.ecaop.resp.AreaQueryResp;
import zte.net.ecsord.params.ecaop.resp.BSSAccountResponse;
import zte.net.ecsord.params.ecaop.resp.BSSCustomerInfoResponse;
import zte.net.ecsord.params.ecaop.resp.BSSGetCardMsgRsp;
import zte.net.ecsord.params.ecaop.resp.BSSOrderReverseSalesResp;
import zte.net.ecsord.params.ecaop.resp.BSSOrderSubResponse;
import zte.net.ecsord.params.ecaop.resp.BSSWriteCardResultNoticeResp;
import zte.net.ecsord.params.ecaop.resp.BareMachineSaleResp;
import zte.net.ecsord.params.ecaop.resp.BusinessAcceptenceAndVerificationResponse;
import zte.net.ecsord.params.ecaop.resp.CannelOrder23to4Resp;
import zte.net.ecsord.params.ecaop.resp.CardDataQryResponse;
import zte.net.ecsord.params.ecaop.resp.CardDataSynResponse;
import zte.net.ecsord.params.ecaop.resp.ChangeMachineSubResp;
import zte.net.ecsord.params.ecaop.resp.Check23to4Resp;
import zte.net.ecsord.params.ecaop.resp.CheckIDECAOPResponse;
import zte.net.ecsord.params.ecaop.resp.CunFeeSongFee4GResp;
import zte.net.ecsord.params.ecaop.resp.CunFeeSongFee4GSubmitResp;
import zte.net.ecsord.params.ecaop.resp.CunFeeSongFeeResponse;
import zte.net.ecsord.params.ecaop.resp.CustInfoCreateResponse;
import zte.net.ecsord.params.ecaop.resp.CustInfoModRsp;
import zte.net.ecsord.params.ecaop.resp.CustomerInfoResponse;
import zte.net.ecsord.params.ecaop.resp.DevelopPersonResponse;
import zte.net.ecsord.params.ecaop.resp.EmployeesInfoResponse;
import zte.net.ecsord.params.ecaop.resp.FlowPacketApplyRsp;
import zte.net.ecsord.params.ecaop.resp.FlowPacketPreRsp;
import zte.net.ecsord.params.ecaop.resp.GetBlanckCardDataRsp;
import zte.net.ecsord.params.ecaop.resp.HandleSpServiceResp;
import zte.net.ecsord.params.ecaop.resp.MainViceOpenResp;
import zte.net.ecsord.params.ecaop.resp.MessageSyncResp;
import zte.net.ecsord.params.ecaop.resp.OldUserBuyApplyResp;
import zte.net.ecsord.params.ecaop.resp.OldUserBuySubmitResp;
import zte.net.ecsord.params.ecaop.resp.OldUserCheck3GResp;
import zte.net.ecsord.params.ecaop.resp.OldUserRenActivityResp;
import zte.net.ecsord.params.ecaop.resp.OldUserRenActivitySubmitResp;
import zte.net.ecsord.params.ecaop.resp.OpenAccountSubmitResp;
import zte.net.ecsord.params.ecaop.resp.OpenDealApplyResp;
import zte.net.ecsord.params.ecaop.resp.OpenDealSubmitResp;
import zte.net.ecsord.params.ecaop.resp.OrderActiveResp;
import zte.net.ecsord.params.ecaop.resp.OrderCancelResp;
import zte.net.ecsord.params.ecaop.resp.OrderInfoSynRsp;
import zte.net.ecsord.params.ecaop.resp.OrderQueryRespone;
import zte.net.ecsord.params.ecaop.resp.OrderReverseSalesResp;
import zte.net.ecsord.params.ecaop.resp.OrderTerminalCheckResp;
import zte.net.ecsord.params.ecaop.resp.ProdChangeApplyResp;
import zte.net.ecsord.params.ecaop.resp.ProdChangeCannelResp;
import zte.net.ecsord.params.ecaop.resp.QueryStdAddrResp;
import zte.net.ecsord.params.ecaop.resp.ResourcePreCheckResp;
import zte.net.ecsord.params.ecaop.resp.ReturnFileResp;
import zte.net.ecsord.params.ecaop.resp.ReturnMachineApplyResp;
import zte.net.ecsord.params.ecaop.resp.ReturnMachineSubmitResp;
import zte.net.ecsord.params.ecaop.resp.SubProOrderResp;
import zte.net.ecsord.params.ecaop.resp.SynCardDataRsp;
import zte.net.ecsord.params.ecaop.resp.TerminalStatusQueryChanageResp;
import zte.net.ecsord.params.ecaop.resp.TrafficOrderResponse;
import zte.net.ecsord.params.ecaop.resp.UserCountCheckRsp;
import zte.net.ecsord.params.ecaop.resp.UserInfoCheck3BackResp;
import zte.net.ecsord.params.ecaop.resp.UserJoinMainViceCardResp;
import zte.net.ecsord.params.ecaop.resp.WriteCardPreRsp;
import zte.net.ecsord.params.ecaop.resp.WriteCardResultNoticeResp;
import zte.net.ecsord.params.ems.req.EmsLogisticsInfoSyncReq;
import zte.net.ecsord.params.ems.req.EmsLogisticsNumberGetReq;
import zte.net.ecsord.params.ems.req.EmsRoutePushServiceReq;
import zte.net.ecsord.params.ems.req.LogisticsInfoSyncReq;
import zte.net.ecsord.params.ems.req.LogisticsNumberGetReq;
import zte.net.ecsord.params.ems.resp.EmsLogisticsInfoSyncResp;
import zte.net.ecsord.params.ems.resp.EmsLogisticsNumberGetResp;
import zte.net.ecsord.params.ems.resp.EmsRoutePushServiceResp;
import zte.net.ecsord.params.ems.resp.LogisticsInfoSyncResp;
import zte.net.ecsord.params.ems.resp.LogisticsNumberGetResp;
import zte.net.ecsord.params.hs.req.DeliveNotifyReq;
import zte.net.ecsord.params.hs.req.OrderCancelReceiveReq;
import zte.net.ecsord.params.hs.req.OrderCheckOutReq;
import zte.net.ecsord.params.hs.req.ReturnWarehousingReq;
import zte.net.ecsord.params.hs.resp.DeliveNotifyResp;
import zte.net.ecsord.params.hs.resp.OrderCancelReceiveResp;
import zte.net.ecsord.params.hs.resp.OrderCheckOutResp;
import zte.net.ecsord.params.hs.resp.ReturnWarehousingResp;
import zte.net.ecsord.params.jkzf.req.JKZFInfReq;
import zte.net.ecsord.params.jkzf.resp.JKZFInfResp;
import zte.net.ecsord.params.nd.req.DispatchingNumReceivingNDRequset;
import zte.net.ecsord.params.nd.req.NotifyOrderInfoNDRequset;
import zte.net.ecsord.params.nd.req.NotifyOrderStatuNDRequset;
import zte.net.ecsord.params.nd.req.OrderDealSuccessNDRequset;
import zte.net.ecsord.params.nd.resp.DispatchingNumReceivingNDResponse;
import zte.net.ecsord.params.nd.resp.NotifyOrderInfoNDResponse;
import zte.net.ecsord.params.nd.resp.NotifyOrderStatuNDResponse;
import zte.net.ecsord.params.nd.resp.OrderDealSuccessNDResponse;
import zte.net.ecsord.params.oldsys.req.NotifyOrderInfo2OldSysRequest;
import zte.net.ecsord.params.oldsys.resp.NotifyOrderInfo2OldSysResponse;
import zte.net.ecsord.params.sf.req.ArtificialSelectRequest;
import zte.net.ecsord.params.sf.req.NotifyOrderInfoSFRequset;
import zte.net.ecsord.params.sf.req.OrderFilterServiceRequset;
import zte.net.ecsord.params.sf.req.OrderSearchServiceRequest;
import zte.net.ecsord.params.sf.req.RoutePushServiceRequset;
import zte.net.ecsord.params.sf.req.RouteServiceRequest;
import zte.net.ecsord.params.sf.resp.ArtificialSelectResponse;
import zte.net.ecsord.params.sf.resp.NotifyOrderInfoSFResponse;
import zte.net.ecsord.params.sf.resp.OrderFilterServiceResponse;
import zte.net.ecsord.params.sf.resp.OrderSearchServiceResponse;
import zte.net.ecsord.params.sf.resp.RoutePushServiceResponse;
import zte.net.ecsord.params.sf.resp.RouteServiceResponse;
import zte.net.ecsord.params.sr.req.QueWriMachStaInBatchRGRequset;
import zte.net.ecsord.params.sr.req.RceiveICCIDReq;
import zte.net.ecsord.params.sr.req.ReadICCIDSRRequset;
import zte.net.ecsord.params.sr.req.RevertCardRGRequset;
import zte.net.ecsord.params.sr.req.RevertCardRequset;
import zte.net.ecsord.params.sr.req.SimulationRequset;
import zte.net.ecsord.params.sr.req.SimulationResultReceiveRequset;
import zte.net.ecsord.params.sr.req.WriMachStaQueRequest;
import zte.net.ecsord.params.sr.req.WriteICCIDSRRGRequset;
import zte.net.ecsord.params.sr.req.WriteICCIDSRRequset;
import zte.net.ecsord.params.sr.resp.QueWriMachStaInBatchRGResponse;
import zte.net.ecsord.params.sr.resp.RceiveICCIDResp;
import zte.net.ecsord.params.sr.resp.ReadICCIDSRResponse;
import zte.net.ecsord.params.sr.resp.RevertCardRGResponse;
import zte.net.ecsord.params.sr.resp.RevertCardResponse;
import zte.net.ecsord.params.sr.resp.SimulationResponse;
import zte.net.ecsord.params.sr.resp.SimulationResultReceiveResponse;
import zte.net.ecsord.params.sr.resp.WriMachStaQueResponse;
import zte.net.ecsord.params.sr.resp.WriteICCIDSRRGResponse;
import zte.net.ecsord.params.sr.resp.WriteICCIDSRResponse;
import zte.net.ecsord.params.taobao.req.GetTaobaoOrderInfoTAOBAORequset;
import zte.net.ecsord.params.taobao.req.LogisticsTAOBAORequset;
import zte.net.ecsord.params.taobao.req.SynchronousOrderTBRequset;
import zte.net.ecsord.params.taobao.req.TaobaoIdentityGetRequest;
import zte.net.ecsord.params.taobao.req.TbTianjiOrderDeliverySyncReq;
import zte.net.ecsord.params.taobao.req.TbTianjiOrderInfoGetReq;
import zte.net.ecsord.params.taobao.req.TbTianjiOrderStatusNoticeReq;
import zte.net.ecsord.params.taobao.resp.GetTaobaoOrderInfoTAOBAOResponse;
import zte.net.ecsord.params.taobao.resp.LogisticsTAOBAOResponse;
import zte.net.ecsord.params.taobao.resp.SynchronousOrderTBResponse;
import zte.net.ecsord.params.taobao.resp.TaobaoIdentityGetResponse;
import zte.net.ecsord.params.taobao.resp.TbTianjiOrderDeliverySyncResp;
import zte.net.ecsord.params.taobao.resp.TbTianjiOrderInfoGetResp;
import zte.net.ecsord.params.taobao.resp.TbTianjiOrderStatusNoticeResp;
import zte.net.ecsord.params.wms.req.GetOrdByBoxIdFromWMSReq;
import zte.net.ecsord.params.wms.req.NotifyOrderAccountWMSReq;
import zte.net.ecsord.params.wms.req.NotifyOrderInfoWMSReq;
import zte.net.ecsord.params.wms.req.NotifyOrderStatusFromWMSReq;
import zte.net.ecsord.params.wms.req.NotifyOrderStatusToWMSReq;
import zte.net.ecsord.params.wms.req.NotifyWriteCardResultWMSReq;
import zte.net.ecsord.params.wms.req.SynCardInfoWMSReq;
import zte.net.ecsord.params.wms.req.SynSerialNumWMSReq;
import zte.net.ecsord.params.wms.req.SynchronousCheckFromWMSReq;
import zte.net.ecsord.params.wms.resp.GetOrdByBoxIdFromWMSResp;
import zte.net.ecsord.params.wms.resp.NotifyOrderAccountWMSResp;
import zte.net.ecsord.params.wms.resp.NotifyOrderInfoWMSResp;
import zte.net.ecsord.params.wms.resp.NotifyOrderStatusFromWMSResp;
import zte.net.ecsord.params.wms.resp.NotifyOrderStatusToWMSResp;
import zte.net.ecsord.params.wms.resp.NotifyWriteCardResultWMSResp;
import zte.net.ecsord.params.wms.resp.SynCardInfoWMSResp;
import zte.net.ecsord.params.wms.resp.SynSerialNumWMSResp;
import zte.net.ecsord.params.wms.resp.SynchronousCheckFromWMSResp;
import zte.net.ecsord.params.wyg.req.ChargebackApplyWYGRequset;
import zte.net.ecsord.params.wyg.req.NotifyOrderInfoWYGRequset;
import zte.net.ecsord.params.wyg.req.NumberChangeWYGRequest;
import zte.net.ecsord.params.wyg.req.NumberResourceQueryWYGRequest;
import zte.net.ecsord.params.wyg.req.Sms3NetSendReq;
import zte.net.ecsord.params.wyg.req.StatuSynchReq;
import zte.net.ecsord.params.wyg.req.WYGAcceptanceQueryReq;
import zte.net.ecsord.params.wyg.resp.ChargebackApplyWYGResponse;
import zte.net.ecsord.params.wyg.resp.NotifyOrderInfoWYGResponse;
import zte.net.ecsord.params.wyg.resp.NumberResourceQueryWYGResponse;
import zte.net.ecsord.params.wyg.resp.Sms3NetSendResp;
import zte.net.ecsord.params.wyg.resp.StatuSynchResp;
import zte.net.ecsord.params.wyg.resp.WYGAcceptanceQueryResp;
import zte.net.ecsord.params.zb.req.BatchRefundReq;
import zte.net.ecsord.params.zb.req.CertCheckZBRequest;
import zte.net.ecsord.params.zb.req.NotifyDeliveryGDRequest;
import zte.net.ecsord.params.zb.req.NotifyDeliveryZBRequest;
import zte.net.ecsord.params.zb.req.NotifyOpenAccountGDRequest;
import zte.net.ecsord.params.zb.req.NotifyOrderAbnormalToSystemRequest;
import zte.net.ecsord.params.zb.req.NotifyOrderAbnormalToZBRequest;
import zte.net.ecsord.params.zb.req.NotifyOrderInfoZBRequest;
import zte.net.ecsord.params.zb.req.NotifyRouteInfoZBRequest;
import zte.net.ecsord.params.zb.req.NotifyStringGDRequest;
import zte.net.ecsord.params.zb.req.NotifyStringZBRequest;
import zte.net.ecsord.params.zb.req.NotifyWriteCardInfoRequest;
import zte.net.ecsord.params.zb.req.NumberResourceQueryZbRequest;
import zte.net.ecsord.params.zb.req.NumberStateChangeZBRequest;
import zte.net.ecsord.params.zb.req.PrecheckOpenAcctRequest;
import zte.net.ecsord.params.zb.req.QryCRMInfo2CardRequest;
import zte.net.ecsord.params.zb.req.QryPhoneUrlRequest;
import zte.net.ecsord.params.zb.req.ReplacementNoticeRequest;
import zte.net.ecsord.params.zb.req.StateSynToZBRequest;
import zte.net.ecsord.params.zb.req.StateSynchronizationToSystemRequest;
import zte.net.ecsord.params.zb.req.SynchronousOrderGDRequest;
import zte.net.ecsord.params.zb.req.SynchronousOrderZBRequest;
import zte.net.ecsord.params.zb.resp.CertCheckZBResponse;
import zte.net.ecsord.params.zb.resp.NotifyDeliveryResponse;
import zte.net.ecsord.params.zb.resp.NotifyOpenAccountGDResponse;
import zte.net.ecsord.params.zb.resp.NotifyOrderAbnormalToSystemResponse;
import zte.net.ecsord.params.zb.resp.NotifyOrderAbnormalToZBResponse;
import zte.net.ecsord.params.zb.resp.NotifyOrderInfoZBResponse;
import zte.net.ecsord.params.zb.resp.NotifyRouteInfoZBResponse;
import zte.net.ecsord.params.zb.resp.NotifyStringGDResponse;
import zte.net.ecsord.params.zb.resp.NotifyStringZBResponse;
import zte.net.ecsord.params.zb.resp.NotifyWriteCardInfoResponse;
import zte.net.ecsord.params.zb.resp.NumberResourceQueryZbResponse;
import zte.net.ecsord.params.zb.resp.NumberStateChangeZBResponse;
import zte.net.ecsord.params.zb.resp.PrecheckOpenAcctResponse;
import zte.net.ecsord.params.zb.resp.QryCRMInfo2CardResponse;
import zte.net.ecsord.params.zb.resp.QryPhoneUrlResponse;
import zte.net.ecsord.params.zb.resp.ReplacementNoticeResponse;
import zte.net.ecsord.params.zb.resp.StateSynToZBResponse;
import zte.net.ecsord.params.zb.resp.StateSynchronizationToSystemResponse;
import zte.net.ecsord.params.zb.resp.SynchronousOrderZBResponse;
import zte.net.iservice.IInfServices;
import zte.params.ecsord.req.EMSLogisticsInfoReq;
import zte.params.ecsord.resp.EMSLogisticsInfoResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version = "1.0")
public class ZteInfOpenService implements IInfServices {
	// @Resource
    
	private IInfServices infServices;

	private void init() {
		infServices = SpringContextHolder.getBean("infServices");
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.zb.notifyOrderInfo", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyOrderInfoZBResponse notifyOrderInfoZB(NotifyOrderInfoZBRequest request) throws ApiBusiException {
		this.init();
		return infServices.notifyOrderInfoZB(request);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.zb.SynchronousOrderZB", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public SynchronousOrderZBResponse synchronousOrderZB(SynchronousOrderZBRequest request) throws Exception {
		this.init();
		return infServices.synchronousOrderZB(request);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.zb.SynchronousOrderGD", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public SynchronousOrderZBResponse synchronousOrderGD(SynchronousOrderGDRequest request) throws ApiBusiException {
		this.init();
		return infServices.synchronousOrderGD(request);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.zb.NotifyStringZB", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyStringZBResponse notifyStringZB(NotifyStringZBRequest request) throws ApiBusiException {
		this.init();
		return infServices.notifyStringZB(request);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.zb.NotifyStringGD", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyStringGDResponse notifyStringGD(NotifyStringGDRequest request) throws ApiBusiException {
		this.init();
		return infServices.notifyStringGD(request);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.zb.NotifyOpenAccountGD", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyOpenAccountGDResponse notifyOpenAccountGD(NotifyOpenAccountGDRequest request) throws ApiBusiException {
		this.init();
		return infServices.notifyOpenAccountGD(request);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.zb.PrecheckOpenAcct", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public PrecheckOpenAcctResponse precheckOpenAcctZB(PrecheckOpenAcctRequest request) throws ApiBusiException {
		this.init();
		return infServices.precheckOpenAcctZB(request);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.zb.QryCRMInfo2Card", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public QryCRMInfo2CardResponse qryCRMInfo2CardZB(QryCRMInfo2CardRequest request) throws ApiBusiException {
		this.init();
		return infServices.qryCRMInfo2CardZB(request);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.zb.NotifyWriteCardInfo", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyWriteCardInfoResponse notifyWriteCardInfoZB(NotifyWriteCardInfoRequest request) throws ApiBusiException {
		this.init();
		return infServices.notifyWriteCardInfoZB(request);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.zb.NotifyDeliveryGD", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyDeliveryResponse notifyDeliveryGD(NotifyDeliveryGDRequest request) throws ApiBusiException {
		this.init();
		return infServices.notifyDeliveryGD(request);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.zb.NotifyDeliveryZB", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyDeliveryResponse notifyDeliveryZB(NotifyDeliveryZBRequest request) throws ApiBusiException {
		this.init();
		return infServices.notifyDeliveryZB(request);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.zb.NotifyOrderAbnormalToZB", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyOrderAbnormalToZBResponse notifyOrderAbnormalToZB(NotifyOrderAbnormalToZBRequest request) throws ApiBusiException {
		this.init();
		return infServices.notifyOrderAbnormalToZB(request);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.zb.NotifyOrderAbnormalToSystem", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyOrderAbnormalToSystemResponse notifyOrderAbnormalToGD(NotifyOrderAbnormalToSystemRequest request) throws ApiBusiException {
		this.init();
		return infServices.notifyOrderAbnormalToGD(request);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.zb.ReplacementNotice", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ReplacementNoticeResponse replacementNoticeGD(ReplacementNoticeRequest request) throws ApiBusiException {
		this.init();
		return infServices.replacementNoticeGD(request);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.zb.StateSynchronizationToSystem", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public StateSynchronizationToSystemResponse stateSynchronizationToSystem(StateSynchronizationToSystemRequest request) throws ApiBusiException {
		this.init();
		return infServices.stateSynchronizationToSystem(request);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.zb.stateSynToZBRequest", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public StateSynToZBResponse stateSynToZB(StateSynToZBRequest request) throws ApiBusiException {
		this.init();
		return infServices.stateSynToZB(request);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.wyg.notifyorderinfo", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyOrderInfoWYGResponse notifyOrderInfoWYG(NotifyOrderInfoWYGRequset req) throws ApiBusiException {
		this.init();
		return this.infServices.notifyOrderInfoWYG(req);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.wyg.chargebackApply", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ChargebackApplyWYGResponse chargebackApplyWYG(ChargebackApplyWYGRequset req) throws ApiBusiException {
		this.init();
		return this.infServices.chargebackApplyWYG(req);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.wyg.statuSynch", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public StatuSynchResp statuSynchToWYG(StatuSynchReq req) throws ApiBusiException {
		this.init();
		return this.infServices.statuSynchToWYG(req);
	}

	@Override
	@ServiceMethod(method = "zte.net.infservice.order.busi.akeyact", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderAKeyActResp orderAKeyActWYG(OrderAKeyActReq req) throws ApiBusiException {
		this.init();
		return this.infServices.orderAKeyActWYG(req);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.wms.notifyOrderInfoWMS", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyOrderInfoWMSResp notifyOrderInfoWMS(NotifyOrderInfoWMSReq req) throws ApiBusiException {
		this.init();
		req.setRopRequestContext(null);
		return infServices.notifyOrderInfoWMS(req);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.wms.synchronousSerialNumberWMS", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public SynSerialNumWMSResp synchronousSerialNumberWMS(SynSerialNumWMSReq req) throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.synchronousSerialNumberWMS(req);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.wms.notifyOrderAccountWMS", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyOrderAccountWMSResp notifyOrderAccountWMS(NotifyOrderAccountWMSReq req) throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.notifyOrderAccountWMS(req);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.wms.synchronousCardInfoWMS", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public SynCardInfoWMSResp synchronousCardInfoWMS(SynCardInfoWMSReq req) throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.synchronousCardInfoWMS(req);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.wms.notifyWriteCardResultWMS", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyWriteCardResultWMSResp notifyWriteCardResultWMS(NotifyWriteCardResultWMSReq req) throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.notifyWriteCardResultWMS(req);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.wms.notifyOrderStatusToWMS", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyOrderStatusToWMSResp notifyOrderStatusToWMS(NotifyOrderStatusToWMSReq req) throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.notifyOrderStatusToWMS(req);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.wms.notifyOrderStatusFromWMS", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyOrderStatusFromWMSResp notifyOrderStatusFromWMS(NotifyOrderStatusFromWMSReq req) throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.notifyOrderStatusFromWMS(req);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.wms.synchronousCheckFromWMS", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public SynchronousCheckFromWMSResp synchronousCheckFromWMS(SynchronousCheckFromWMSReq req) throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.synchronousCheckFromWMS(req);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.wms.getOrdByBoxIdFromWMS", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public GetOrdByBoxIdFromWMSResp getOrdByBoxIdFromWMS(GetOrdByBoxIdFromWMSReq req) throws ApiBusiException {
		this.init();
		return infServices.getOrdByBoxIdFromWMS(req);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.bss.SSOLoginBSS", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public SSOLoginBSSResp SSOLoginBSS(SSOLoginBSSReq req) throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.SSOLoginBSS(req);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.bss.SSOLoginReqVerify", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public SSOLoginReqVerifyBSSResp SSOLoginReqVerifyBSS(SSOLoginReqVerifyBSSReq req) throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.SSOLoginReqVerifyBSS(req);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.bss.PageCallVerify", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public PageCallVerifyBSSResp PageCallVerifyBSS(PageCallVerifyBSSReq req) throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.PageCallVerifyBSS(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS对账信息同步", summary = "BSS对账信息同步")
	@ServiceMethod(method = "com.zte.unicomService.bss.orderFinAccountSync", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderFinAccountSyncResp orderFinAccountSync(OrderFinAccountSyncReq req) throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.orderFinAccountSync(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS业务办理", summary = "BSS业务办理")
	@ServiceMethod(method = "com.zte.unicomService.bss.ActionRecv", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ActionRecvBSSResp actionRecvBSS(ActionRecvBSSReq req) throws ApiBusiException {
		this.init();
		return infServices.actionRecvBSS(req);
	}

	@Override
	@ServiceMethod(method = "com.zte.unicomService.bss.agencyAcctPayNotifyReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public AgencyAcctPayNotifyRsp agencyAcctPayNotify(AgencyAcctPayNotifyReq req) throws ApiBusiException {
		this.init();
		return infServices.agencyAcctPayNotify(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J51 读取ICCID值接口", summary = "订单系统请求写卡机服务读取ICCID值。")
	@ServiceMethod(method = "com.zte.unicomService.sr.readICCID", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ReadICCIDSRResponse readICCIDSR(ReadICCIDSRRequset req) throws ApiBusiException {
		this.init();
		return infServices.readICCIDSR(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J52 写卡接口", summary = "外部针对每次写卡机访问请求对写卡机管理服务发起一个HTTP请求，写卡机管理服务接收到请求之后会对该请求进行合法性校验，如果校验不通过，将返回校验失败并中断流程；如果校验通过，则启动对写卡机的操作，处理完成后将返回处理结果。")
	@ServiceMethod(method = "com.zte.unicomService.sr.writeICCID", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public WriteICCIDSRResponse writeICCIDSR(WriteICCIDSRRequset req) throws ApiBusiException {
		this.init();
		return infServices.writeICCIDSR(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J53回收卡", summary = "回收卡")
	@ServiceMethod(method = "com.zte.unicomService.sr.revertCard", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public RevertCardResponse revertCard(RevertCardRequset req) throws ApiBusiException {
		this.init();
		return infServices.revertCard(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J61身份校验接口", summary = "验证身份证信息是否正确")
	@ServiceMethod(method = "com.zte.unicomService.ecaop.checkID", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CheckIDECAOPResponse checkIDECAOP(CheckIDECAOPRequset req) throws ApiBusiException {
		this.init();
		return infServices.checkIDECAOP(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J62获取证件照图片路径", summary = "获取证件照图片路径")
	@ServiceMethod(method = "WYG.cert.photo.url", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 6000)
	public AccountPhotoResponse getAccountPhotoFromWYG(AccountPhotoRequest req) throws ApiBusiException {
		this.init();
		return infServices.getAccountPhotoFromWYG(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J116照片信息查询", summary = "照片信息查询")
	@ServiceMethod(method = "com.zte.unicomService.zb.certCheck", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 6000)
	public CertCheckZBResponse getAccountPhotoFromZB(CertCheckZBRequest req) throws ApiBusiException {
		this.init();
		return infServices.getAccountPhotoFromZB(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J71 订单状态同步淘宝天机平台", summary = "订单状态同步淘宝天机平台")
	@ServiceMethod(method = "com.zte.unicomService.taobao.syncTbTianjiOrderStatus", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public TbTianjiOrderStatusNoticeResp syncTbTianjiOrderStatus(TbTianjiOrderStatusNoticeReq req) throws ApiBusiException {
		this.init();
		return infServices.syncTbTianjiOrderStatus(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J71 订单物流信息同步淘宝天机平台", summary = "订单物流信息同步淘宝天机平台")
	@ServiceMethod(method = "com.zte.unicomService.taobao.syncTbTianjiOrderDelivery", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public TbTianjiOrderDeliverySyncResp syncTbTianjiOrderDelivery(TbTianjiOrderDeliverySyncReq req) throws ApiBusiException {
		this.init();
		return infServices.syncTbTianjiOrderDelivery(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J71 从淘宝天机平台获取订单详情信息", summary = "从淘宝天机平台获取订单详情信息")
	@ServiceMethod(method = "com.zte.unicomService.taobao.getTbTianjiOrderInfo", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public TbTianjiOrderInfoGetResp getTbTianjiOrderInfo(TbTianjiOrderInfoGetReq req) throws ApiBusiException {
		this.init();
		return infServices.getTbTianjiOrderInfo(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J71 订单详情查询接口", summary = "订单系统将订单信息通知淘宝")
	@ServiceMethod(method = "com.zte.unicomService.taobao.getTaobaoOrder", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public GetTaobaoOrderInfoTAOBAOResponse getTaobaoOrderInfo(GetTaobaoOrderInfoTAOBAORequset req) throws ApiBusiException {
		this.init();
		return infServices.getTaobaoOrderInfo(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J72 订单信息变更", summary = "订单系统调用该接口可实现无需物流（虚拟）发货,使用该接口发货，交易订单状态会直接变成卖家已发货；用户调用该接口可实现在线订单发货（支持货到付款） 调用该接口实现在线下单发货。")
	@ServiceMethod(method = "com.zte.unicomService.taobao.logistics", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public LogisticsTAOBAOResponse LogisticsTB(LogisticsTAOBAORequset req) throws ApiBusiException {
		this.init();
		return infServices.LogisticsTB(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J73 订单信息变更", summary = "订单系统调用该接口可实现无需物流（虚拟）发货,使用该接口发货，交易订单状态会直接变成卖家已发货；用户调用该接口可实现在线订单发货（支持货到付款） 调用该接口实现在线下单发货。")
	@ServiceMethod(method = "com.zte.unicomService.taobao.SynchronousOrder", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public SynchronousOrderTBResponse SynchronousOrderTB(SynchronousOrderTBRequset req) throws ApiBusiException {
		this.init();
		return infServices.SynchronousOrderTB(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J81 订单信息变更", summary = "订单系统将订单信息通知南都")
	@ServiceMethod(method = "com.zte.unicomService.nd.notifyorderinfo", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyOrderInfoNDResponse notifyOrderInfoND(NotifyOrderInfoNDRequset req) throws ApiBusiException {
		this.init();
		return infServices.notifyOrderInfoND(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J82 订单信息变更", summary = "物流公司接到订单系统订单后，给订单分配派工号时，调用该接口，通知订单系统，该订单由哪个工号进行处理；只有此工号登陆订单系统，才能看到此订单进行开户、写卡操作。")
	@ServiceMethod(method = "com.zte.unicomService.nd.dispatchingNumReceiving", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public DispatchingNumReceivingNDResponse dispatchingNumReceivingND(DispatchingNumReceivingNDRequset req) throws ApiBusiException {
		this.init();
		return infServices.dispatchingNumReceivingND(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J83 订单状态通知接口", summary = "物流公司获取订单后，将订单的状态通知给订单系统。状态包括：成功接收、拒绝接收、开户失败、用户拒收、确认配送。")
	@ServiceMethod(method = "com.zte.unicomService.nd.notifyOrderStatu", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyOrderStatuNDResponse notifyOrderStatuND(NotifyOrderStatuNDRequset req) throws ApiBusiException {
		this.init();
		return infServices.notifyOrderStatuND(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J84处理完成通知", summary = "物流公司获取订单后，将订单的状态通知给订单系统。状态包括：成功接收、拒绝接收、开户失败、用户拒收、确认配送。")
	@ServiceMethod(method = "com.zte.unicomService.nd.orderDealSuccess", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderDealSuccessNDResponse orderDealSuccessND(OrderDealSuccessNDRequset req) throws ApiBusiException {
		this.init();
		return infServices.orderDealSuccessND(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J91 订单信息变更", summary = "订单系统将订单信息变更需要通知顺丰")
	@ServiceMethod(method = "com.zte.unicomService.sf.notifyOrderInfo", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyOrderInfoSFResponse notifyOrderInfoSF(NotifyOrderInfoSFRequset req) throws ApiBusiException {
		this.init();
		return infServices.notifyOrderInfoSF(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J92 订单筛选接口", summary = "1、\t订单系统通过此接口向顺丰企业服务平台发送自动筛单请求，用于判断客户的收、派地址是否属于顺丰的收派范围。顺丰系统会根据收派双方的地址自动判断是否在顺丰的收派范围内。如果属于范围内则返回可收派，否则返回不可收派。")
	@ServiceMethod(method = "com.zte.unicomService.sf.orderFilterService", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderFilterServiceResponse orderFilterServiceSF(OrderFilterServiceRequset req) throws ApiBusiException {
		this.init();
		return infServices.orderFilterServiceSF(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J93 订单信息变更", summary = "1、\t向订单系统定时推送运单的路由信息。需要客户系统提供服务以便顺丰向客户系统推送路由数据。")
	@ServiceMethod(method = "com.zte.unicomService.sf.routePushService", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public RoutePushServiceResponse routePushServiceSF(RoutePushServiceRequset req) throws ApiBusiException {
		this.init();
		return infServices.routePushServiceSF(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J93 订单信息变更", summary = "1、\t向订单系统定时推送运单的路由信息。需要客户系统提供服务以便顺丰向客户系统推送路由数据。")
	@ServiceMethod(method = "com.zte.unicomService.ems.routePushService", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public EmsRoutePushServiceResp routePushServiceEms(EmsRoutePushServiceReq req) throws ApiBusiException {
		this.init();
		return infServices.routePushServiceEms(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J95 顺丰物流路由查询", summary = "查询顺丰物流的路由信息")
	@ServiceMethod(method = "com.zte.unicomService.sf.routeService", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public RouteServiceResponse querySfRoute(RouteServiceRequest req) throws ApiBusiException {
		this.init();
		return infServices.querySfRoute(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J96顺丰订单信息查询", summary = "顺丰订单信息查询")
	@ServiceMethod(method = "com.zte.unicomService.sf.orderSearchService", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderSearchServiceResponse querySfOrder(OrderSearchServiceRequest req) throws ApiBusiException {
		this.init();
		return infServices.querySfOrder(req);
	}

	///////////////////////// add by wui 接口机调用
	/**
	 * 订单分流判断
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单分流判断", summary = "沃云购2.0新老系统订单分流、接口机分流")
	@ServiceMethod(method = "zte.net.infservice.order.busi.separteflow.ordersepartereq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderSeparteResp orderSeparterFlow(OrderSeparteReq req) throws ApiBusiException {
		this.init();
		return infServices.orderSeparterFlow(req);
	}

	/**
	 * 订单分流判断
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单分流判断", summary = "沃云购2.0新老系统订单分流、接口机分流")
	@ServiceMethod(method = "zte.net.infservice.order.busi.separteflow.ordersepartereq2", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderSeparteResp orderSeparterFlow2(OrderSeparteReq req) throws ApiBusiException {
		this.init();
		return infServices.orderSeparterFlow2(req);
	}

	/**
	 * 订单分流，接口机调用信息通知新订单系统
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单分流，总部接口调用", summary = "订单分流，接口机调用信息通知新订单系统")
	@ServiceMethod(method = "zte.net.infservice.order.busi.zbinfexec.orderinfexec", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderInfExecResp orderInfExec(OrderInfExecReq req) throws ApiBusiException {
		this.init();
		return infServices.orderInfExec(req);
	}

	/**
	 * 订单分流，接口机调用信息通知新订单系统
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单分流，总部接口调用", summary = "订单分流，接口机调用信息通知新订单系统")
	@ServiceMethod(method = "zte.net.infservice.order.busi.zbinfexec.orderinfexec2", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderInfExecResp orderInfExec2(OrderInfExecReq req) throws ApiBusiException {
		this.init();
		return infServices.orderInfExec2(req);
	}

	/**
	 * 订单分流，接口机调用信息通知新订单系统
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单分流", summary = "订单分流，接口机调用信息通知新订单系统")
	@ServiceMethod(method = "zte.net.infservice.order.busi.ordinfo.ordertreesimpinforeq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderTreesInfoResp getOrderTreeInfo(OrderTreesInfoReq req) throws ApiBusiException {
		this.init();
		return infServices.getOrderTreeInfo(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "模拟订单归集请求", summary = "模拟订单归集请求")
	@ServiceMethod(method = "zte.net.ecsord.base.simuDDGJ", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public SimulatorDDGJResp simulatorDDGJ(SimulatorDDGJReq req) throws ApiBusiException {
		this.init();
		return infServices.simulatorDDGJ(req);
	}

	@ZteSoftCommentAnnotation(type = "method", desc = "订单信息查询接口", summary = "接收外部订单查询请求，返回订单信息")
	@ServiceMethod(method = "zte.net.infservice.order.busi.queryOrderInfo", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderInfoResp queryOrderInfo(OrderInfoReq req) throws ApiBusiException {
		this.init();
		return infServices.queryOrderInfo(req);
	}

	@ZteSoftCommentAnnotation(type = "method", desc = "测试单退单api", summary = "测试单退单api")
	@ServiceMethod(method = "com.zte.unicomService.zb.batchRefund", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public StateSynToZBResponse batchRefund(BatchRefundReq req) throws ApiBusiException {
		this.init();
		return infServices.batchRefund(req);
	}

	@ZteSoftCommentAnnotation(type = "method", desc = "商城收费、退款成功触发接口", summary = "商城收费、退款成功触发接口")
	@ServiceMethod(method = "com.zte.unicomService.bss.feeInform", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public FeeInformResp feeInform(FeeInformReq req) throws ApiBusiException {
		this.init();
		return infServices.feeInform(req);
	}

	@ZteSoftCommentAnnotation(type = "method", desc = "商城收费、退款成功触发接口", summary = "商城收费、退款成功触发接口")
	@ServiceMethod(method = "com.zte.unicomService.bss.orderAccountOrBuybackInform", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderAccountOrBuybackInformResp orderAccountOrBuybackInform(OrderAccountOrBuybackInformReq req) throws ApiBusiException {
		this.init();
		return infServices.orderAccountOrBuybackInform(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "接收顺丰推送人工筛选订单信息", summary = "商城收费、退款成功触发接口")
	@ServiceMethod(method = "com.zte.unicomService.sf.artificialSelect", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ArtificialSelectResponse artificialSelectServiceSF(ArtificialSelectRequest req) throws ApiBusiException {
		this.init();
		return infServices.artificialSelectServiceSF(req);
	}

	@Override
	@ServiceMethod(method = "zte.net.ecsord.params.busiopen.separteflow.req.pushOrderID2Memcache", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public PushOrderID2MemcacheResp pushOrderID2Memcache(PushOrderID2MemcacheReq request) throws ApiBusiException {
		this.init();
		return infServices.pushOrderID2Memcache(request);
	}

	@ZteSoftCommentAnnotation(type = "method", desc = "CBSS-移网产品服务变更", summary = "CBSS-移网产品服务变更")
	@ServiceMethod(method = "com.zte.unicomService.bss.mobilenetworkservicehandle", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public MobileNetworkServiceHandleResp mobileNetworkServiceHandle(MobileNetworkServiceHandleReq req) throws ApiBusiException {
		this.init();
		return infServices.mobileNetworkServiceHandle(req);
	}

	@ZteSoftCommentAnnotation(type = "method", desc = "CBSS-SP订购", summary = "CBSS-SP订购")
	@ServiceMethod(method = "com.zte.unicomService.bss.spbuyservicehandle", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public SPBuyServiceHandleResp spBuyServiceHandle(SPBuyServiceHandleReq req) throws ApiBusiException {
		// TODO Auto-generated method stub
		this.init();
		return infServices.spBuyServiceHandle(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "com.zte.unicomService.bss.loginandcert", desc = "CBSS-系统登录认证", summary = "CBSS-系统登录认证")
	public Object toLoginAndCert(CBssLoginCertReq req) {
		// TODO Auto-generated method stub
		this.init();
		return infServices.toLoginAndCert(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "zte.net.ecsord.params.bss.req.spbuycbsslogincert", desc = "CBSS-SP订购", summary = "CBSS-SP订购")
	public SPBuyCBssOutResp spBuyServiceHandle(SPBuyCBssOutReq req) throws ApiBusiException {
		// TODO Auto-generated method stub
		this.init();
		return infServices.spBuyServiceHandle(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "zte.net.ecsord.params.bss.req.mobilecbsslogincert", desc = "CBSS-移网", summary = "CBSS-移网")
	public MobileCBssOutResp MobileServiceHandle(MobileCBssOutReq req) throws ApiBusiException {
		// TODO Auto-generated method stub
		this.init();
		return infServices.MobileServiceHandle(req);
	}

	/**
	 * 订单分流，接口机调用信息通知新订单系统
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单分流", summary = "订单分流，接口机调用信息通知新订单系统")
	@ServiceMethod(method = "com.zte.unicomService.oldsys.notifyOrderinfo", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyOrderInfo2OldSysResponse orderInfo2OldOrderSys(NotifyOrderInfo2OldSysRequest req) throws ApiBusiException {
		this.init();
		return infServices.orderInfo2OldOrderSys(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J117 淘宝证件信息查询接口", summary = "淘宝证件信息查询接口")
	@ServiceMethod(method = "com.zte.unicomService.taobao.getIdentity", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public TaobaoIdentityGetResponse getIdentityFromTaobao(TaobaoIdentityGetRequest req) {
		this.init();
		return infServices.getIdentityFromTaobao(req);
	}

	/**
	 * aop号码状态变更
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "aop号码状态变更", summary = "aop号码状态变更")
	@ServiceMethod(method = "com.zte.unicomService.zb.numberStateChange", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NumberStateChangeZBResponse numberStateChange(NumberStateChangeZBRequest req) throws ApiBusiException {
		this.init();
		return infServices.numberStateChange(req);
	}

	/**
	 * bss号码状态变更
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "bss号码状态变更", summary = "bss号码状态变更")
	@ServiceMethod(method = "com.zte.unicomService.bss.numberStateChangeBss", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NumberStateChangeBssResp numberStateChangeBss(NumberStateChangeBssRequest req) throws ApiBusiException {
		this.init();
		return infServices.numberStateChangeBss(req);
	}

	/**
	 * 号码资源查询(新商城)
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "号码资源查询", summary = "号码资源查询")
	@ServiceMethod(method = "com.zte.unicomService.wyg..numberResourceQueryWYG", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NumberResourceQueryWYGResponse numberResourceQueryWYG(NumberResourceQueryWYGRequest req) throws ApiBusiException {
		this.init();
		return infServices.numberResourceQueryWYG(req);
	}

	/**
	 * 号码资源查询
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "号码资源查询", summary = "号码资源查询")
	@ServiceMethod(method = "com.zte.unicomService.wyg.numberChangeWYG", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyOrderInfoWYGResponse numberChangeWYG(NumberChangeWYGRequest req) throws ApiBusiException {
		this.init();
		return infServices.numberChangeWYG(req);
	}

	/**
	 * 终端状态查询变更
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "终端状态查询变更", summary = "终端状态查询变更")
	@ServiceMethod(method = "com.zte.unicomService.zb.terminalStatusQueryChanage", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public TerminalStatusQueryChanageResp terminalStatusQueryChanage(TerminalStatusQueryChanageReq req) throws ApiBusiException {
		this.init();
		return infServices.terminalStatusQueryChanage(req);
	}

	/**
	 * 开户处理提交
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "开户处理提交", summary = "开户处理提交")
	@ServiceMethod(method = "com.zte.unicomService.zb.openDealSubmit", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OpenDealSubmitResp openDealSubmit(OpenDealSubmitReq req) throws ApiBusiException {
		this.init();
		return infServices.openDealSubmit(req);
	}

	/**
	 * 开户处理提交
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "开户处理提交", summary = "开户处理提交")
	@ServiceMethod(method = "com.zte.unicomService.zb.openDealSubmitzj", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OpenDealSubmitResp openDealSubmitZJ(OpenDealSubmitReqZJ req) throws ApiBusiException {
		this.init();
		return infServices.openDealSubmitZJ(req);
	}

	/**
	 * 客户信息查询
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "客户信息查询", summary = "客户信息查询")
	@ServiceMethod(method = "com.zte.unicomService.zb.customerInfoQuery", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CustomerInfoResponse customerInfoQuery(CustomerInfoCheckReq req) throws Exception {
		this.init();
		return infServices.customerInfoQuery(req);
	}

	/**
	 * 客户信息查询BSS
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "客户信息查询BSS", summary = "客户信息查询BSS")
	@ServiceMethod(method = "com.zte.unicomService.zb.customerInfoQuery.bss", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public BSSCustomerInfoResponse customerInfoQueryBSS(BSSCustomerInfoCheckReq req) throws ApiBusiException {
		this.init();
		return infServices.customerInfoQueryBSS(req);
	}

	/**
	 * 发展人查询
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "发展人查询", summary = "发展人查询")
	@ServiceMethod(method = "com.zte.unicomService.zb.devrPersonQuery", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public DevelopPersonResponse devrPersonQuery(DevelopPersonQueryReq req) throws ApiBusiException {
		this.init();
		return infServices.devrPersonQuery(req);
	}

	/**
	 * 写卡结果通知
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "写卡结果通知", summary = "写卡结果通知")
	@ServiceMethod(method = "com.zte.unicomService.zb.writeCardResultNotice", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public WriteCardResultNoticeResp writeCardResultNotice(WriteCardResultNoticeReq req) throws ApiBusiException {
		this.init();
		return infServices.writeCardResultNotice(req);
	}

	/**
	 * 写卡结果通知zj
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "写卡结果通知", summary = "写卡结果通知")
	@ServiceMethod(method = "com.zte.unicomService.zb.writeCardResultNoticezj", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public WriteCardResultNoticeResp writeCardResultNoticeZJ(WriteCardResultNoticeReqZJ req) throws ApiBusiException {
		this.init();
		return infServices.writeCardResultNoticeZJ(req);
	}

	/**
	 * 写卡结果通知BSS
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "写卡结果通知BSS", summary = "写卡结果通知BSS")
	@ServiceMethod(method = "com.zte.unicomService.zb.writeCardResultNotice.bss", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public BSSWriteCardResultNoticeResp writeCardResultNoticeBSS(BSSWriteCardResultNoticeReq req) throws ApiBusiException {
		this.init();
		return infServices.writeCardResultNoticeBSS(req);
	}

	/**
	 * 开户申请
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "开户申请", summary = "开户申请")
	@ServiceMethod(method = "ecaop.trades.sell.mob.newu.open.app", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OpenDealApplyResp openDealApply(OpenDealApplyReq req) throws ApiBusiException {
		this.init();
		return infServices.openDealApply(req);
	}

	/**
	 * 开户申请
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "开户申请", summary = "开户申请")
	@ServiceMethod(method = "ecaop.trades.sell.mob.newu.open.appzj", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OpenDealApplyResp openDealApplyZJ(OpenDealApplyReqZJ req) throws ApiBusiException {
		this.init();
		return infServices.openDealApplyZJ(req);
	}

	/**
	 * 写卡数据获取
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J205AOP写卡数据获取", summary = "AOP写卡数据获取")
	@ServiceMethod(method = "ecaop.trades.sell.mob.comm.carddate.qry", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CardDataQryResponse cardDataQry(CardDataQryRequest req) throws ApiBusiException {
		this.init();
		return infServices.cardDataQry(req);
	}

	/**
	 * 写卡数据同步
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J206AOP写卡数据同步", summary = "AOP写卡数据同步")
	@ServiceMethod(method = "ecaop.trades.sell.mob.newu.opencarddate.syn", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CardDataSynResponse cardDataSyn(CardDataSynRequest req) throws ApiBusiException {
		this.init();
		return infServices.cardDataSyn(req);
	}

	/**
	 * 写卡数据同步zj
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J206AOP写卡数据同步", summary = "AOP写卡数据同步")
	@ServiceMethod(method = "ecaop.trades.sell.mob.newu.opencarddate.synzj", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CardDataSynResponse cardDataSynZJ(CardDataSynRequestZJ req) throws ApiBusiException {
		this.init();
		return infServices.cardDataSynZJ(req);
	}

	/**
	 * ess员工信息查询
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "员工信息查询", summary = "员工信息查询")
	@ServiceMethod(method = "ecaop.trades.check.staff.check", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public EmployeesInfoResponse employeesInfoCheck(EmployeesInfoCheckRequest req) throws ApiBusiException {
		this.init();
		return infServices.employeesInfoCheck(req);
	}

	/**
	 * 老用户存费送费
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "老用户存费送费", summary = "老用户存费送费")
	@ServiceMethod(method = "com.zte.unicomService.gdaop.cunFeeSongFee", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CunFeeSongFeeResponse cunFeeSongFee(CunFeeSongFeeRequest req) throws ApiBusiException {
		this.init();
		return infServices.cunFeeSongFee(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "4G老用户存费送费", summary = "4G老用户存费送费")
	@ServiceMethod(method = "ecaop.trades.sell.mob.oldu.fee.chg", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CunFeeSongFee4GResp cunFeeSongFee4G(CunFeeSongFee4GReq req) throws ApiBusiException {
		this.init();
		return infServices.cunFeeSongFee4G(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "4G老用户存费送费", summary = "4G老用户存费送费")
	@ServiceMethod(method = "ecaop.trades.sell.mob.oldu.fee.chgzj", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CunFeeSongFee4GResp cunFeeSongFee4GZJ(CunFeeSongFee4GReqZJ req) throws ApiBusiException {
		this.init();
		return infServices.cunFeeSongFee4GZJ(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "4G老用户存费送费正式提交", summary = "4G老用户存费送费正式提交")
	@ServiceMethod(method = "ecaop.trades.sell.mob.oldu.fee.chg.sub", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CunFeeSongFee4GSubmitResp cunFeeSongFee4GSubmit(CunFeeSongFee4GSubmitReq req) throws ApiBusiException {
		this.init();
		return infServices.cunFeeSongFee4GSubmit(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "4G老用户存费送费正式提交", summary = "4G老用户存费送费正式提交")
	@ServiceMethod(method = "ecaop.trades.sell.mob.oldu.fee.chg.subzj", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CunFeeSongFee4GSubmitResp cunFeeSongFee4GSubmitZJ(CunFeeSongFee4GSubmitReqZJ req) throws ApiBusiException {
		this.init();
		return infServices.cunFeeSongFee4GSubmitZJ(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单返销", summary = "订单返销")
	@ServiceMethod(method = "ecaop.trades.serv.curt.cannel.sub", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderReverseSalesResp orderReverseSales(OrderReverseSalesReq req) throws ApiBusiException {
		this.init();
		return infServices.orderReverseSales(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单返销bss", summary = "订单返销bss")
	@ServiceMethod(method = "ecaop.trades.serv.curt.cannel.sub.bss", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public BSSOrderReverseSalesResp orderReverseSalesBSS(BSSOrderReverseSalesReq req) throws ApiBusiException {
		this.init();
		return infServices.orderReverseSalesBSS(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "退机申请", summary = "退机申请")
	@ServiceMethod(method = "ecaop.trades.serv.curt.cannelph.app", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ReturnMachineApplyResp returnMachineApply(ReturnMachineApplyReq req) throws ApiBusiException {
		this.init();
		return infServices.returnMachineApply(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "退机提交", summary = "退机提交")
	@ServiceMethod(method = "ecaop.trades.serv.curt.cannelph.sub", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ReturnMachineSubmitResp returnMachineSubmit(ReturnMachineSubmitReq req) throws ApiBusiException {
		this.init();
		return infServices.returnMachineSubmit(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "换机提交", summary = "换机提交")
	@ServiceMethod(method = "ecaop.trades.serv.curt.changeph.sub", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ChangeMachineSubResp ChangeMachineSub(ChangeMachineSubReq req) throws ApiBusiException {
		this.init();
		return infServices.ChangeMachineSub(req);
	}

	/**
	 * 2-3G转4G校验（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "2-3G转4G校验（AOP）", summary = "2-3G转4G校验（AOP）")
	@ServiceMethod(method = "ecaop.trades.query.comm.23to4.check", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public Check23to4Resp check23to4(Check23to4Request req) throws ApiBusiException {
		this.init();
		return infServices.check23to4(req);
	}

	/**
	 * 2-3G转4G开户申请（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "2-3G转4G开户与申请（AOP）", summary = "2-3G转4G开户与申请（AOP）")
	@ServiceMethod(method = "ecaop.trades.sell.mob.newu.23to4open.app", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OpenDealApplyResp openAccApply23to4(Open23to4ApplyReq req) throws ApiBusiException {
		this.init();
		return infServices.openAccApply23to4(req);
	}

	/**
	 * 2-3G转4G撤单（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "2-3G转4G撤单（AOP）", summary = "2-3G转4G撤单（AOP）")
	@ServiceMethod(method = "ecaop.trades.serv.curt.23to4cannel.sub", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CannelOrder23to4Resp cannelOrder23to4(CannelOrder23to4Request req) throws ApiBusiException {
		this.init();
		return infServices.cannelOrder23to4(req);
	}

	/**
	 * 流量包订购（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "流量包订购（AOP）", summary = "流量包订购（AOP）")
	@ServiceMethod(method = "ecaop.trades.sell.mob.comm.traffic.order", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public TrafficOrderResponse trafficOrder(TrafficOrderRequest req) throws ApiBusiException {
		this.init();
		return infServices.trafficOrder(req);
	}

	/**
	 * 套餐变更-支持3G
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "套餐变更申请-支持3G", summary = "套餐变更申请")
	@ServiceMethod(method = "ecaop.trades.sell.mob.oldu.product.chg", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ProdChangeApplyResp productChangeApply(ProdChangeApplyReq req) throws ApiBusiException {
		this.init();
		return infServices.productChangeApply(req);
	}

	/**
	 * 套餐变更,撤单（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "套餐变更,撤单（AOP）", summary = "套餐变更,撤单（AOP）")
	@ServiceMethod(method = "ecaop.trades.serv.curt.prochgcannel.sub", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ProdChangeCannelResp prodChangeCancel(ProdChangeCannelRequest req) throws ApiBusiException {
		this.init();
		return infServices.prodChangeCancel(req);
	}

	/**
	 * 老用户优惠购机处理申请-支持3G
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "老用户优惠购机处理申请-支持3G", summary = "老用户优惠购机处理申请")
	@ServiceMethod(method = "ecaop.trades.sell.mob.oldu.activity.app", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OldUserBuyApplyResp oldActivityMobileApply(OldUserBuyApplyReq req) throws ApiBusiException {
		this.init();
		return infServices.oldActivityMobileApply(req);
	}

	/**
	 * 老用户优惠购机处理提交-支持3G
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "老用户优惠购机处理提交-支持3G", summary = "老用户优惠购机处理申请")
	@ServiceMethod(method = "ecaop.trades.sell.mob.oldu.activity.sub", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OldUserBuySubmitResp oldActivityMobileSubmit(OldUserBuySubmitReq req) throws ApiBusiException {
		this.init();
		return infServices.oldActivityMobileSubmit(req);
	}

	/**
	 * 用户资料校验三户返回
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "用户资料校验三户返回", summary = "用户资料校验三户返回")
	@ServiceMethod(method = "ecaop.trades.query.comm.user.threepart.check", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public UserInfoCheck3BackResp userInfoCheck3Back(UserInfoCheck3BackReq req) throws ApiBusiException {
		this.init();
		return infServices.userInfoCheck3Back(req);
	}

	/**
	 * 用户资料校验三户返回
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "用户资料校验三户返回", summary = "用户资料校验三户返回")
	@ServiceMethod(method = "ecaop.trades.query.comm.user.threepart.checkzj", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public UserInfoCheck3BackResp userInfoCheck3BackZJ(UserInfoCheck3BackReqZJ req) throws ApiBusiException {
		this.init();
		return infServices.userInfoCheck3BackZJ(req);
	}

	/**
	 * 老用户业务校验
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "老用户业务校验", summary = "老用户业务校验")
	@ServiceMethod(method = "ecaop.trades.check.oldu.check", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OldUserCheck3GResp oldUserCheck3G(OldUserCheck3GReq req) throws ApiBusiException {
		this.init();
		return infServices.oldUserCheck3G(req);
	}

	/**
	 * 封装接口转发
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "封装接口转发", summary = "接口转发")
	@ServiceMethod(method = "zte.net.infservices.jkzfinf", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public JKZFInfResp jkzfInf(JKZFInfReq req) throws ApiBusiException {
		this.init();
		return infServices.jkzfInf(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "仿真派单", summary = "仿真派单")
	@ServiceMethod(method = "zte.net.infservices.simulation", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public SimulationResponse simulation(SimulationRequset req) throws ApiBusiException {
		this.init();
		return infServices.simulation(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "仿真回单", summary = "仿真回单")
	@ServiceMethod(method = "zte.net.infservices.simulationresultreceive", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public SimulationResultReceiveResponse simulationResultReceive(SimulationResultReceiveRequset req) throws ApiBusiException {
		this.init();
		return infServices.simulationResultReceive(req);
	}

	/**
	 * 终端状态查询变更-批量
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "终端状态查询变更-批量", summary = "终端状态查询变更-批量")
	@ServiceMethod(method = "com.zte.unicomService.zb.termiStatusChanageBatch", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public TerminalStatusQueryChanageResp termiStatusChanageBatch(TerminalStatusQueryChanageBatchReq req) throws ApiBusiException {
		this.init();
		return infServices.termiStatusChanageBatch(req);
	}

	/**
	 * 裸机销售
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "裸机销售", summary = "裸机销售")
	@ServiceMethod(method = "ecaop.trades.sell.mob.comm.term.sale", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public BareMachineSaleResp bareMachineSale(BareMachineSaleReq req) throws ApiBusiException {
		this.init();
		return infServices.bareMachineSale(req);
	}

	/**
	 * 订单查询[AOP]
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单查询[AOP]", summary = "订单查询[AOP]")
	@ServiceMethod(method = "ecaop.trades.query.ordi.ordilist.qry", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderQueryRespone orderQuery(OrderQueryReq req) throws ApiBusiException {
		this.init();
		return infServices.orderQuery(req);
	}
	/**
     * 订单查询[AOP]
     * 
     * @param req
     * @return
     */
    @ZteSoftCommentAnnotation(type = "method", desc = "订单查询[AOP]自定义", summary = "订单查询[AOP]自定义")
    @ServiceMethod(method = "ecaop.trades.query.ordi.ordilist.qryNew", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
    public OrderQueryRespone orderQueryNewIntent(IntentOrderQueryForCBReq req) throws ApiBusiException {
        this.init();
        return infServices.orderQueryNewIntent(req);
    }
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "发送三网短信", summary = "发送三网短信")
	@ServiceMethod(method = "zte.net.iservice.impl.infservices.send3NetSms", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public Sms3NetSendResp send3NetSms(Sms3NetSendReq req) throws ApiBusiException {
		this.init();
		return infServices.send3NetSms(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取总商上传图片地址", summary = "获取总商上传图片地址")
	@ServiceMethod(method = "zte.net.iservice.impl.infservices.qryPhoneUrl", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public QryPhoneUrlResponse qryPhoneUrl(QryPhoneUrlRequest req) throws ApiBusiException {
		this.init();
		return infServices.qryPhoneUrl(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[BSS]开户处理申请", summary = "[BSS]开户处理申请")
	@ServiceMethod(method = "com.zte.unicomService.zb.openDealApplyBSS.bss", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public BSSAccountResponse openDealApplyBSS(BSSAccountReq req) throws ApiBusiException {
		this.init();
		return infServices.openDealApplyBSS(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[BSS]开户处理提交", summary = "[BSS]开户处理提交")
	@ServiceMethod(method = "com.zte.unicomService.zb.openDealSubmitBSS.bss", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public BSSOrderSubResponse openDealSubmitBSS(BSSOrderSubReq req) throws ApiBusiException {
		this.init();
		return infServices.openDealSubmitBSS(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS H2接口方式", summary = "BSS H2接口方式")
	@ServiceMethod(method = "zte.net.iservice.impl.infservices.BSSH2Interface", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public BaseBSSResp BSSH2Interface(BaseBSSReq req) throws ApiBusiException {
		this.init();
		return infServices.BSSH2Interface(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS卡数据同步接口", summary = "BSS卡数据同步接口")
	@ServiceMethod(method = "ecaop.trades.sell.mob.newu.opencarddate.syn.bss", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public WriteCardPreRsp writeCardDataSync2BSS(WriteCardPreReq req) throws ApiBusiException {
		this.init();
		return infServices.writeCardDataSync2BSS(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取写卡数据", summary = "获取写卡数据")
	@ServiceMethod(method = "ecaop.trades.sell.mob.comm.carddate.autoqry.bss", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public BSSGetCardMsgRsp getCardDataFromBSS(BSSGetCardMsgReq req) throws ApiBusiException {
		this.init();
		return infServices.getCardDataFromBSS(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取白卡数据", summary = "获取白卡数据")
	@ServiceMethod(method = "ecaop.trades.serv.newu.getblanckcard.app", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public GetBlanckCardDataRsp getBlanckCard(GetBlanckCardDataReq req) throws ApiBusiException {
		this.init();
		return infServices.getBlanckCard(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "卡数据同步", summary = "卡数据同步")
	@ServiceMethod(method = "ecaop.trades.serv.newu.opencarddata.syn", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public SynCardDataRsp synCardData(SynCardDataReq req) throws ApiBusiException {
		this.init();
		return infServices.synCardData(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]后激活订单信息同步接口", summary = "[AOP]后激活订单信息同步接口")
	@ServiceMethod(method = "ecaop.trades.syn.orderinfo.sub_aop", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderInfoSynRsp orderInfoSyn(OrderInfoSynReq req) throws ApiBusiException {
		this.init();
		return infServices.orderInfoSyn(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]sp类产品订购申请", summary = "[AOP]sp类产品订购申请")
	@ServiceMethod(method = "ecaop.trades.sell.mob.sp.order.app", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public FlowPacketPreRsp flowPacketPre(FlowPacketPreReq req) throws ApiBusiException {
		this.init();
		return infServices.flowPacketPre(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]流量包订购/退订", summary = "[AOP]流量包订购/退订")
	@ServiceMethod(method = "ecaop.trades.sell.mob.comm.traffic.sub", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public FlowPacketApplyRsp flowPacketApply(FlowPacketApplyReq req) throws ApiBusiException {
		this.init();
		return infServices.flowPacketApply(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "使用人数量校验接口", summary = "使用人数量校验接口")
	@ServiceMethod(method = "bss.userCountCheckFromBSS", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public UserCountCheckRsp userCountCheckFromBSS(UserCountCheckReq req) throws ApiBusiException {
		this.init();
		return infServices.userCountCheckFromBSS(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "SP服务办理接口", summary = "SP服务办理接口")
	@ServiceMethod(method = "soa.zte.net.iservice.impl.handleSpService", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public HandleSpServiceResp handleSpService(HandleSpServiceReq req) throws ApiBusiException {
		this.init();
		return infServices.handleSpService(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "附加产品订购接口", summary = "附加产品订购接口")
	@ServiceMethod(method = "soa.zte.net.iservice.impl.subProOrder", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public SubProOrderResp subProOrder(SubProOrderReq req) throws ApiBusiException {
		this.init();
		return infServices.subProOrder(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "老用户续约申请[AOP]", summary = "老用户续约申请[AOP]")
	@ServiceMethod(method = "ecaop.trades.sell.mob.oldu.renActivity.app", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OldUserRenActivityResp oldRenActivityApply(OldUserRenActivityReq req) throws ApiBusiException {
		this.init();
		return infServices.oldRenActivityApply(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "老用户续约提交[AOP]", summary = "老用户续约提交[AOP]")
	@ServiceMethod(method = "ecaop.trades.sell.mob.oldu.renActivity.sub", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OldUserRenActivitySubmitResp oldRenActivitySubmit(OldUserRenActivitySubmitReq req) throws ApiBusiException {
		this.init();
		return infServices.oldRenActivitySubmit(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "路由信息通知总部", summary = "路由信息通知总部")
	@ServiceMethod(method = "com.zte.unicomService.zb.notifyRouteInfoZBRequest", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NotifyRouteInfoZBResponse notifyRouteInfoZB(NotifyRouteInfoZBRequest request) throws ApiBusiException {
		this.init();
		return infServices.notifyRouteInfoZB(request);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "沃云购 受理单信息查询", summary = "沃云购 受理单信息查询")
	@ServiceMethod(method = "com.zte.unicomService.wyg.acceptanceQuery", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public WYGAcceptanceQueryResp acceptanceQuery(WYGAcceptanceQueryReq req) throws ApiBusiException {
		this.init();
		return this.infServices.acceptanceQuery(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "接收华盛 SAPB2C省分退货、拦截指令", summary = "接收华盛 SAPB2C省分退货、拦截指令")
	@ServiceMethod(method = "com.zte.unicomService.hs.orderCancel", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderCancelReceiveResp orderCancelReceive(OrderCancelReceiveReq req) throws ApiBusiException {
		this.init();
		return this.infServices.orderCancelReceive(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "出库信息回传SAP", summary = "出库信息回传SAP")
	@ServiceMethod(method = "com.zte.unicomService.hs.deliveNotify", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public DeliveNotifyResp deliveNotify(DeliveNotifyReq request) throws ApiBusiException {
		this.init();
		return this.infServices.deliveNotify(request);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "退货入库传输SAP", summary = "退货入库传输SAP")
	@ServiceMethod(method = "com.zte.unicomService.hs.returnWarehousing", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ReturnWarehousingResp ReturnWarehousing(ReturnWarehousingReq request) throws ApiBusiException {
		this.init();
		return this.infServices.ReturnWarehousing(request);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "仓储商采购退货订单出库回传SAP接口", summary = "仓储商采购退货订单出库回传SAP接口")
	@ServiceMethod(method = "com.zte.unicomService.hs.orderCheckOutB2B", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderCheckOutResp orderCheckOutB2B(OrderCheckOutReq request) throws ApiBusiException {
		this.init();
		return this.infServices.orderCheckOutB2B(request);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "轮询写卡机工位状态", summary = "轮询写卡机工位状态")
	@ServiceMethod(method = "com.zte.unicomService.sr.WriteMachineStation", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public WriMachStaQueResponse WriteMachineStation(WriMachStaQueRequest req) throws ApiBusiException {
		this.init();
		return infServices.WriteMachineStation(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "写卡接口(人工批量)", summary = "外部针对每次写卡机访问请求对写卡机管理服务发起一个HTTP请求，写卡机管理服务接收到请求之后会对该请求进行合法性校验，如果校验不通过，将返回校验失败并中断流程；如果校验通过，则启动对写卡机的操作，处理完成后将返回处理结果。")
	@ServiceMethod(method = "com.zte.unicomService.sr.rgwriteICCID", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public WriteICCIDSRRGResponse writeICCIDSRRG(WriteICCIDSRRGRequset req) throws ApiBusiException {
		this.init();
		return infServices.writeICCIDSRRG(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "回收卡(人工批量)", summary = "回收卡(人工批量)")
	@ServiceMethod(method = "com.zte.unicomService.sr.rgrevertCard", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public RevertCardRGResponse revertCardRG(RevertCardRGRequset req) throws ApiBusiException {
		this.init();
		return infServices.revertCardRG(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "批量获取写卡机状态获取data", summary = "批量获取写卡机状态获取data")
	@ServiceMethod(method = "com.zte.unicomService.sr.rgquerywritemachinestatusinbatch", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public QueWriMachStaInBatchRGResponse queWriMachStaInBatchRG(QueWriMachStaInBatchRGRequset req) throws ApiBusiException {
		this.init();
		return infServices.queWriMachStaInBatchRG(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "接收森锐单笔推送可用ICCID", summary = "接收森锐单笔推送可用ICCID")
	@ServiceMethod(method = "com.zte.unicomService.sr.rceiveICCID", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public RceiveICCIDResp rceiveICCID(RceiveICCIDReq req) throws ApiBusiException {
		this.init();
		return infServices.rceiveICCID(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "省份BSS选号接口", summary = "省份BSS选号接口")
	@ServiceMethod(method = "com.zte.unicomService.bss.numberResourceQueryZjBss", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NumberResourceQueryZjBssResponse numberResourceQueryZjBss(NumberResourceQueryZjBssRequest req) throws ApiBusiException {
		this.init();
		return this.infServices.numberResourceQueryZjBss(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "总部选号接口", summary = "总部选号接口")
	@ServiceMethod(method = "com.zte.unicomService.zb.numberResourceQueryZb", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public NumberResourceQueryZbResponse numberResourceQueryZb(NumberResourceQueryZbRequest req) throws ApiBusiException {

		this.init();
		return this.infServices.numberResourceQueryZb(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取EMS物流单号", summary = "获取EMS物流单号")
	@ServiceMethod(method = "com.zte.unicomService.ems.getEmsLogisticsNumber", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public EmsLogisticsNumberGetResp getEmsLogisticsNumber(EmsLogisticsNumberGetReq req) throws ApiBusiException {
		this.init();
		return this.infServices.getEmsLogisticsNumber(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "物流信息同步EMS", summary = "物流信息同步EMS")
	@ServiceMethod(method = "com.zte.unicomService.ems.syncLogisticsInfoToEms", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public EmsLogisticsInfoSyncResp syncLogisticsInfoToEms(EmsLogisticsInfoSyncReq req) throws ApiBusiException {
		this.init();
		return this.infServices.syncLogisticsInfoToEms(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "从EMS自助服务平台获取EMS物流单号", summary = "从EMS自助服务平台获取EMS物流单号")
	@ServiceMethod(method = "com.zte.unicomService.ems.getLogisticsNumber", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public LogisticsNumberGetResp getLogisticsNumber(LogisticsNumberGetReq req) throws ApiBusiException {
		this.init();
		return this.infServices.getLogisticsNumber(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "物流信息同步EMS自助服务平台", summary = "物流信息同步EMS自助服务平台")
	@ServiceMethod(method = "com.zte.unicomService.ems.syncLogisticsInfo", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public LogisticsInfoSyncResp syncLogisticsInfo(LogisticsInfoSyncReq req) throws ApiBusiException {
		this.init();
		return this.infServices.syncLogisticsInfo(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "总部短信发送", summary = "总部短信发送")
	@ServiceMethod(method = "com.zte.unicomService.zb.sendSmsZb", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public AopSmsSendResp sendSmsZb(AopSmsSendReq req) throws Exception {
		this.init();
		return this.infServices.sendSmsZb(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "用户属性查询接口(客户校验)", summary = "用户属性查询接口(客户校验)")
	@ServiceMethod(method = "com.zte.unicomService.bss.customerCheck", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CustomerCheckResp bssCustomerCheck(CustomerCheckReq req) throws ApiBusiException {
		this.init();
		return this.infServices.bssCustomerCheck(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "用户属性查询接口(客户校验)", summary = "用户属性查询接口(客户校验)")
	@ServiceMethod(method = "com.zte.unicomService.bss.bssPreCommit", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public PreCommitResp bssPreCommit(PreCommitReq req) throws ApiBusiException {
		this.init();
		return this.infServices.bssPreCommit(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "浙江省份号码预占", summary = "浙江省份号码预占")
	@ServiceMethod(method = "com.zte.unicomService.bss.NumberResourceChangePreCaptureZj", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)

	public NumberResourceChangePreCaptureZjResponse NumberResourceChangePreCaptureZj(NumberResourceChangePreCaptureZjRequest req) throws ApiBusiException {

		this.init();
		return this.infServices.NumberResourceChangePreCaptureZj(req);
	}
    @Override
    @ZteSoftCommentAnnotation(type = "method", desc = "浙江省份移网号码预占", summary = "浙江省份移网号码预占")
    @ServiceMethod(method = "com.zte.unicomService.bss.NumberResourceNewGroupOrderZjRequest", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
    public NumberResourceChangePreCaptureZjResponse numberResourceNewGroupOrderZjRequest(NumberResourceNewGroupOrderZjRequest req) throws ApiBusiException {
        this.init();
        return this.infServices.numberResourceNewGroupOrderZjRequest(req);
    }
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取EMS物流信息", summary = "获取EMS物流信息")
	@ServiceMethod(method = "com.zte.unicomService.ems.getEmsLogisticsInfo", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public EMSLogisticsInfoResp getEmsLogisticsInfo(EMSLogisticsInfoReq req) throws ApiBusiException {
		this.init();
		return this.infServices.getEmsLogisticsInfo(req);
	}

	@Override
	public BSSActivateOperRsp syncActiveToBSS(BSSActivateOperReq req) throws ApiBusiException {
		this.init();
		return this.infServices.syncActiveToBSS(req);
	}

	@Override
	public OrderActiveResp syncActiveToAOP(OrderActiveReq req) throws ApiBusiException {
		this.init();
		return this.infServices.syncActiveToAOP(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS开户正式提交", summary = "BSS开户正式提交")
	@ServiceMethod(method = "com.zte.unicomService.bss.subAccountOfficial", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public BSSAccountOfficialSubResp subAccountOfficial(BSSAccountOfficialSubReq req) throws ApiBusiException {
		this.init();
		return this.infServices.subAccountOfficial(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "续约活动校验和受理接口", summary = "续约活动校验和受理接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.BusinessAcceptenceAndVerification", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public BusinessAcceptenceAndVerificationResponse businessAcceptenceAndVerification(BusinessAcceptenceAndVerificationReq req) throws ApiBusiException {
		this.init();
		return this.infServices.businessAcceptenceAndVerification(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS本地商城订单状态同步接口", summary = "BSS本地商城订单状态同步接口")
	@ServiceMethod(method = "local.goods.status.synchronization", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public LocalGoodsStatusSynchronizationResp localGoodsStatusSynchronization(LocalGoodsStatusSynchronizationReq request) throws ApiBusiException {
		this.init();
		return this.infServices.localGoodsStatusSynchronization(request);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "智慧沃企开户正式提交接口", summary = "智慧沃企开户正式提交接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.BusinessAcceptenceAndVerification", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)

	public AccountOpenFormalSubmissionResp accountOpenFormalSubmission(AccountOpenFormalSubmissionReq req) throws ApiBusiException {
		this.init();
		return this.infServices.accountOpenFormalSubmission(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "接口日志记录", summary = "接口日志记录")
	@ServiceMethod(method = "zte.net.ecsord.params.bss.req.recordInfLog", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ZteResponse recordInfLog(RecordInfLogReq req) throws ApiBusiException {
		this.init();
		return this.infServices.recordInfLog(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "后向激活订单撤单AOP", summary = "后向激活订单撤单AOP")
	@ServiceMethod(method = "ecaop.trades.serv.curt.cbssso.cancle.sub", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderCancelResp laterActiveOrderCancel(OrderCancelReq req) throws ApiBusiException {
		this.init();
		return this.infServices.laterActiveOrderCancel(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "商品消息同步", summary = "商品消息同步")
	@ServiceMethod(method = "com.zte.unicomService.zb.messageSync", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public MessageSyncResp messageSync(MessageSyncReq req) throws ApiBusiException {
		this.init();
		return this.infServices.messageSync(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "用户返档", summary = "用户返档")
	@ServiceMethod(method = "com.zte.unicomService.zb.returnFile", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ReturnFileResp returnFile(ReturnFileReq req) throws ApiBusiException {
		this.init();
		return this.infServices.returnFile(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "标准地址查询", summary = "标准地址查询")
	@ServiceMethod(method = "com.zte.unicomService.bss.qrystdaddr", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public QueryStdAddrResp qryStdAddr(QueryStdAddressReq req) throws ApiBusiException {
		this.init();
		return this.infServices.qryStdAddr(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "一体化县分查询", summary = "一体化县分查询")
	@ServiceMethod(method = "com.zte.unicomService.yth.areaquery", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public AreaQueryResp areaQuery(AreaQueryReq req) throws ApiBusiException {
		this.init();
		return this.infServices.areaQuery(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "资源预判", summary = "资源预判")
	@ServiceMethod(method = "com.zte.unicomService.ecaop.resourceprecheck", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ResourcePreCheckResp resourcePreCheck(ResourcePreCheckReq req) throws ApiBusiException {
		this.init();
		return this.infServices.resourcePreCheck(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询任务人员", summary = "查询任务人员")
	@ServiceMethod(method = "zte.net.iservice.impl.ZteInfOpenService.getOperator", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderListForWorkResp getOperator(OrderListForWorkReq req) throws ApiBusiException {
		this.init();
		return this.infServices.getOperator(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]客户资料信息修改", summary = "[AOP]客户资料信息修改")
	@ServiceMethod(method = "ecaop.trades.serv.curt.custinfo.mod", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CustInfoModRsp custInfoMod(CustInfoModReq req) throws ApiBusiException {
		this.init();
		return infServices.custInfoMod(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "新用户主副卡开户处理申请", summary = "新用户主副卡开户处理申请")
	@ServiceMethod(method = "ecaop.trades.sell.main.vice.open.app", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public MainViceOpenResp mainViceOpen(MainViceOpenReq req) throws ApiBusiException {
		this.init();
		return infServices.mainViceOpen(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "正式提交接口", summary = "正式提交接口")
	@ServiceMethod(method = "ecaop.trades.sell.mob.newu.open.sub", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OpenAccountSubmitResp openAccountSubmit(OpenAccountSubmitReq req) throws ApiBusiException {
		this.init();
		return infServices.openAccountSubmit(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "老用户加入主副卡", summary = "老用户加入主副卡")
	@ServiceMethod(method = "ecaop.trades.sell.mob.main.vice.chg", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public UserJoinMainViceCardResp joinMVCard(UserJoinMainViceCardReq req) throws ApiBusiException {
		this.init();
		return infServices.joinMVCard(req);
	}
	@Override
    @ZteSoftCommentAnnotation(type = "method", desc = "资料创建", summary = "资料创建")
    @ServiceMethod(method = "ecaop.serv.curt.custinfo.create", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
    public CustInfoCreateResponse CustInfoCreate(CustInfoCreateReq req) throws ApiBusiException {
        this.init();
        return infServices.CustInfoCreate(req);
    }
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "终端校验接口", summary = "终端校验接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.OrderTerminalCheck", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderTerminalCheckResp orderTerminalCheck(OrderTerminalCheckReq req) throws ApiBusiException {
		this.init();
		return this.infServices.orderTerminalCheck(req);
	}

}
