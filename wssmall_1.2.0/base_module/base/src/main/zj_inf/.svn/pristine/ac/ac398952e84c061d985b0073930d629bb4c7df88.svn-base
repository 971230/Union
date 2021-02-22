package zte.net.iservice;

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
import zte.params.ecsord.req.EMSLogisticsInfoReq;
import zte.params.ecsord.resp.EMSLogisticsInfoResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type = "class", desc = "接口API能力封装", summary = "接口API能力封装")
public interface IInfServices {

	/**
	 * 订单信息变更
	 * 
	 * @seq J101
	 * @作者 li.weili
	 * @创建日期 2014-9-22
	 * @第三方系统:ZB(总部)
	 * @param NotifyOrderInfoZBRequest
	 * @return NotifyOrderInfoZBResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单信息变更", summary = "订单系统将订单信息变更需要通知总部")
	public NotifyOrderInfoZBResponse notifyOrderInfoZB(NotifyOrderInfoZBRequest request) throws ApiBusiException;

	/**
	 * 订单信息同步接口
	 * 
	 * @seq J102
	 * @作者 li.weili
	 * @创建日期 2014-9-22
	 * @第三方系统:ZB(总部)
	 * @param SynchronousOrderZBRequest
	 * @return SynchronousOrderZBResponse
	 * @throws ApiBusiException
	 * @throws Exception
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单信息同步接口", summary = "商城同步订单总部；总部同步订单商城")
	public SynchronousOrderZBResponse synchronousOrderZB(SynchronousOrderZBRequest request) throws ApiBusiException, Exception;

	/**
	 * 订单信息同步接口
	 * 
	 * @seq J102
	 * @作者 li.weili
	 * @创建日期 2014-9-22
	 * @第三方系统:ZB(总部)
	 * @param SynchronousOrderZBRequest
	 * @return SynchronousOrderZBResponse
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单信息同步接口", summary = "商城同步订单总部；总部同步订单商城")
	public SynchronousOrderZBResponse synchronousOrderGD(SynchronousOrderGDRequest request) throws ApiBusiException;

	/**
	 * 将串号信息同步到总部接口
	 * 
	 * @seq J103
	 * @作者 li.weili
	 * @创建日期 2014-9-22
	 * @第三方系统:ZB(总部)
	 * @param NotifyStringZBRequest
	 * @return NotifyStringZBResponse
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "将串号信息同步到总部接口", summary = "将串号信息同步到总部")
	public NotifyStringZBResponse notifyStringZB(NotifyStringZBRequest request) throws ApiBusiException;

	/**
	 * 将货品信息反馈给省分能力平台
	 * 
	 * @seq J104
	 * @作者 li.weili
	 * @创建日期 2014-9-22
	 * @第三方系统:ZB(总部)
	 * @param NotifyStringGDRequest
	 * @return NotifyStringGDResponse
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "将货品信息反馈给省分能力平台", summary = "总部将相应的货品明细信息反馈给省分能力平台")
	public NotifyStringGDResponse notifyStringGD(NotifyStringGDRequest request) throws ApiBusiException;

	/**
	 * 开户完成接口
	 * 
	 * @seq J105
	 * @作者 li.weili
	 * @创建日期 2014-9-22
	 * @第三方系统:ZB(总部)
	 * @param NotifyOpenAccountGDRequest
	 * @return NotifyOpenAccountGDResponse
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "开户完成接口", summary = "总部将开户完成信息同步订单系统")
	public NotifyOpenAccountGDResponse notifyOpenAccountGD(NotifyOpenAccountGDRequest request) throws ApiBusiException;

	/**
	 * 开户预校验接口
	 * 
	 * @seq J106
	 * @作者 li.weili
	 * @创建日期 2014-9-22
	 * @第三方系统:ZB(总部)
	 * @param PrecheckOpenAcctRequest
	 * @return PrecheckOpenAcctResponse
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "开户预校验接口", summary = "开户信息预提交")
	public PrecheckOpenAcctResponse precheckOpenAcctZB(PrecheckOpenAcctRequest request) throws ApiBusiException;

	/**
	 * 写卡信息查询接口
	 * 
	 * @seq J107
	 * @作者 li.weili
	 * @创建日期 2014-9-22
	 * @第三方系统:ZB(总部)
	 * @param QryCRMInfo2CardRequest
	 * @return QryCRMInfo2CardResponse
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "写卡信息查询接口", summary = "在支撑写卡业务前提下，由触发写卡端发起写卡申请，通过开放平台向总部CRM获取写卡信息，由于写卡存在多个环节，失败的时候需要进行重复读卡和重复写卡操作。")
	public QryCRMInfo2CardResponse qryCRMInfo2CardZB(QryCRMInfo2CardRequest request) throws ApiBusiException;

	/**
	 * 订单状态同步接口
	 * 
	 * @seq J108
	 * @作者 li.weili
	 * @创建日期 2014-9-22
	 * @第三方系统:ZB(总部)
	 * @param NotifyWriteCardInfoRequest
	 * @return NotifyWriteCardInfoResponse
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单状态同步接口", summary = "触发写卡端将写卡结果通知总部CRM，以做管控处理")
	public NotifyWriteCardInfoResponse notifyWriteCardInfoZB(NotifyWriteCardInfoRequest request) throws ApiBusiException;

	/**
	 * 发货通知接口
	 * 
	 * @seq J109
	 * @作者 li.weili
	 * @创建日期 2014-9-22
	 * @第三方系统:ZB(总部)
	 * @param NotifyDeliveryRequest
	 * @return notifyDeliveryResponse
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "发货通知接口", summary = "总部将指定发货的物流公司以及相应的物流信息通知省份能力平台，进行发货通知处理。")
	public NotifyDeliveryResponse notifyDeliveryGD(NotifyDeliveryGDRequest request) throws ApiBusiException;

	/**
	 * 发货通知接口
	 * 
	 * @seq J110
	 * @作者 li.weili
	 * @创建日期 2014-9-22
	 * @第三方系统:ZB(总部)
	 * @param NotifyDeliveryRequest
	 * @return notifyDeliveryResponse
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "发货通知接口", summary = "总部将指定发货的物流公司以及相应的物流信息通知省份能力平台，进行发货通知处理。")
	public NotifyDeliveryResponse notifyDeliveryZB(NotifyDeliveryZBRequest request) throws ApiBusiException;

	/**
	 * 订单异常通知
	 * 
	 * @seq J111
	 * @作者 li.weili
	 * @创建日期 2014-9-22
	 * @第三方系统:ZB(总部)
	 * @param NotifyOrderAbnormalToZBRequest
	 * @return NotifyOrderAbnormalToZBResponse
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单异常通知", summary = "通知/接收异常状态，挂起或恢复订单生产")
	public NotifyOrderAbnormalToZBResponse notifyOrderAbnormalToZB(NotifyOrderAbnormalToZBRequest request) throws ApiBusiException;

	/**
	 * 订单异常通知
	 * 
	 * @seq J112
	 * @作者 li.weili
	 * @创建日期 2014-9-22
	 * @第三方系统:ZB(总部)
	 * @param NotifyOrderAbnormalToSystemRequest
	 * @return NotifyOrderAbnormalToSystemResponse
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单异常通知", summary = "通知/接收异常状态，挂起或恢复订单生产")
	public NotifyOrderAbnormalToSystemResponse notifyOrderAbnormalToGD(NotifyOrderAbnormalToSystemRequest request) throws ApiBusiException;

	/**
	 * 换货通知接口
	 * 
	 * @seq J113
	 * @作者 li.weili
	 * @创建日期 2014-9-22
	 * @第三方系统:ZB(总部)
	 * @param ReplacementNoticeRequest
	 * @return ReplacementNoticeResponse
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "换货通知接口", summary = "换货确认时，把换货串号、物流单号同步到省分能力平台")
	public ReplacementNoticeResponse replacementNoticeGD(ReplacementNoticeRequest request) throws ApiBusiException;

	/**
	 * 状态同步接口
	 * 
	 * @seq J114
	 * @作者 li.weili
	 * @创建日期 2014-9-22
	 * @第三方系统:ZB(总部)
	 * @param StateSynchronizationToSystemRequest
	 * @return StateSynchronizationToSystemResponse
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "状态同步接口", summary = "总部推送订单状态到订单系统系统")
	public StateSynchronizationToSystemResponse stateSynchronizationToSystem(StateSynchronizationToSystemRequest request) throws ApiBusiException;

	/**
	 * 状态同步接口(已调试通过)
	 * 
	 * @seq J115
	 * @作者 li.weili
	 * @创建日期 2014-9-22
	 * @第三方系统:ZB(总部)
	 * @param StateSynchronizationToZBRequest
	 * @return StateSynchronizationToZBResponse
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "状态同步接口", summary = "订单管理系统将订单的状态信息推送到总部系统")
	public StateSynToZBResponse stateSynToZB(StateSynToZBRequest request) throws ApiBusiException;

	/**
	 * 发货完成通知【状态通知接口】
	 * 
	 * @seq J21
	 * @作者 sguo
	 * @创建日期 2014-9-22
	 * @第三方系统 新商城(WSFW)
	 * @param NotifyOrderInfoWYGRequset
	 * @return NotifyOrderInfoWSFWResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J21 订单信息变更", summary = "订单管理系统在订单状态变更的时候将信息同步到沃云购")
	public NotifyOrderInfoWYGResponse notifyOrderInfoWYG(NotifyOrderInfoWYGRequset req) throws ApiBusiException;

	/**
	 * 退单申请【状态通知接口】
	 * 
	 * @seq J22
	 * @作者 sguo
	 * @创建日期 2014-9-22
	 * @第三方系统 新商城
	 * @param ChargebackApplyWYGRequset
	 * @return ChargebackApplyWSFWResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J22退单申请【状态通知接口】", summary = "沃云购将订单状态同步到订单管理系统")
	public ChargebackApplyWYGResponse chargebackApplyWYG(ChargebackApplyWYGRequset req) throws ApiBusiException;

	/**
	 * 一键开户
	 * 
	 * @seq J23
	 * @作者 sguo
	 * @创建日期 2014-11-10
	 * @第三方系统 新商城【营业厅】
	 * @param OrderAKeyActReq
	 * @return OrderAKeyActResp
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J23一键开户", summary = "订单管理系统提供提供沃云购一键开户")
	public OrderAKeyActResp orderAKeyActWYG(OrderAKeyActReq req) throws ApiBusiException;

	/**
	 * 订单状态通知
	 * 
	 * @seq J24
	 * @作者 sguo
	 * @创建日期 2014-11-10
	 * @第三方系统 新商城【营业厅】
	 * @param StatuSynchReq
	 * @return StatuSynchResp
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J24订单状态通知", summary = "订单状态通知")
	public StatuSynchResp statuSynchToWYG(StatuSynchReq req) throws ApiBusiException;

	/**
	 * 订单系统归集出库单，并全量下传给WMS，WMS根据订单系统的出库单进行发货；
	 * 订单系统下传给WMS的单据，WMS安排波次之后订单系统将不能对单据进行修改 接口发起方 订单系统
	 * 
	 * @seq J31
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单信息同步到WMS", summary = "订单信息同步到WMS")
	public NotifyOrderInfoWMSResp notifyOrderInfoWMS(NotifyOrderInfoWMSReq req) throws ApiBusiException;

	/**
	 * WMS获取到订单商品中的终端串号后，通知订单系统， 订单系统收到通知后，进行开户操作。 每拣选一个终端上传一次到订单系统。 接口发起方 WMS
	 * 
	 * @seq J32
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "接收WMS货品（串号）通知，由WMS发起", summary = "接收WMS货品（串号）通知")
	public SynSerialNumWMSResp synchronousSerialNumberWMS(SynSerialNumWMSReq req) throws ApiBusiException;

	/**
	 * 开户完成后，通知WMS进行下一步操作。 接口发起方 订单系统
	 * 
	 * @seq J33
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单业务完成状态通知", summary = "订单业务完成状态通知")
	public NotifyOrderAccountWMSResp notifyOrderAccountWMS(NotifyOrderAccountWMSReq req) throws ApiBusiException;

	/**
	 * 在完成开户任务后，在料箱走到指点工位后同步写卡数据到订单系统 接口发起方 WMS
	 * 
	 * @seq J34
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "接收WMS写卡机编号", summary = "接收WMS写卡机编号")
	public SynCardInfoWMSResp synchronousCardInfoWMS(SynCardInfoWMSReq req) throws ApiBusiException;

	/**
	 * 写卡完成并掉卡后，通知WMS进行下一步操作。 接口发起方 订单系统
	 * 
	 * @seq J35
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "通知WMS写卡完成", summary = "通知WMS写卡完成")
	public NotifyWriteCardResultWMSResp notifyWriteCardResultWMS(NotifyWriteCardResultWMSReq req) throws ApiBusiException;

	/**
	 * 本接口主要是对订单在WMS和订单系统中的每个不同业务下状态的跟踪，
	 * 订单系统到WMS状态类型：开始稽查（写卡已成功），稽查完成/已打印，取消订单（整单）， 稽核失败通知，异常处理成功（订单继续），退货单。
	 * 接口发起方 订单系统
	 * 
	 * @seq J36
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "通知WMS订单状态", summary = "通知WMS订单状态")
	public NotifyOrderStatusToWMSResp notifyOrderStatusToWMS(NotifyOrderStatusToWMSReq req) throws ApiBusiException;

	/**
	 * 本接口主要是对订单在WMS和订单系统中的每个不同业务下状态的跟踪， 订单系统到WMS状态类型：货物到出库口，发货完成(RF确认出库)，
	 * 订单取消成功,缺货（描述里面填写缺什么货物），异常口到位通知，退货完成 接口发起方 WMS
	 * 
	 * @seq J37
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "接收WMS订单状态通知", summary = "接收WMS订单状态通知")
	public NotifyOrderStatusFromWMSResp notifyOrderStatusFromWMS(NotifyOrderStatusFromWMSReq req) throws ApiBusiException;

	/**
	 * WMS对料箱中实物进行扫描并上传到订单系统， 订单系统进行数据对比，同一个订单在有多个料箱任务的情况下，
	 * 在扫描完所有料箱后再上传到上位系统，最终完成稽核动作 接口发起方 WMS
	 * 
	 * @seq J38
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "同步稽查数据", summary = "同步稽查数据")
	public SynchronousCheckFromWMSResp synchronousCheckFromWMS(SynchronousCheckFromWMSReq req) throws ApiBusiException;

	/**
	 * 订单系统把料箱号传到WMS，WMS根据料箱号获取对应的订单号返回给订单系统
	 * 
	 * @seq J39
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "获取订单号", summary = "获取订单号")
	public GetOrdByBoxIdFromWMSResp getOrdByBoxIdFromWMS(GetOrdByBoxIdFromWMSReq req) throws ApiBusiException;

	/**
	 * 用户在商城页面点击登录按钮，调用该webservice接口。 该接口首先会和商城确认该是否是来自商城页面的登录，
	 * 若得到确认调用营业的texudo接口判断该用户的合法性，并将结果返回给商城。 接口发起方 订单系统
	 * 
	 * @seq J41
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "单点登录到BSS", summary = "单点登录到BSS")
	public SSOLoginBSSResp SSOLoginBSS(SSOLoginBSSReq req) throws ApiBusiException;

	/**
	 * 在商城订单生产系统调用BSS接口进行登录的时候， BSS系统回调用身份验证回调接口进行反向验证， 确定该请求是从商城订单生产系统发起的。
	 * 接口发起方 BSS
	 * 
	 * @seq J42
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "登陆反调确认接口", summary = "登陆反调确认接口")
	public SSOLoginReqVerifyBSSResp SSOLoginReqVerifyBSS(SSOLoginReqVerifyBSSReq req) throws ApiBusiException;

	/**
	 * 在商城订单生产系统界面打开BSS相关功能页面的时候， BSS通过该接口验证请求是通过商城订单生产系统发起 接口发起方 BSS
	 * 
	 * @seq J43
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "页面功能调用反调确认接口", summary = "页面功能调用反调确认接口")
	public PageCallVerifyBSSResp PageCallVerifyBSS(PageCallVerifyBSSReq req) throws ApiBusiException;

	/**
	 * BSS对账信息同步 接口发起方 BSS
	 * 
	 * @seq J44
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS对账信息同步", summary = "BSS对账信息同步")
	public OrderFinAccountSyncResp orderFinAccountSync(OrderFinAccountSyncReq req) throws ApiBusiException;

	/**
	 * 社会机TAC码、商品折扣包录入 接口发起方 订单系统
	 * 
	 * @seq J45
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS社会机TAC码、商品折扣包录入", summary = "社会机TAC码、商品折扣包录入")
	public ActionRecvBSSResp actionRecvBSS(ActionRecvBSSReq req) throws ApiBusiException;

	/**
	 * 代理扣款返销 接口发起方 订单系统
	 * 
	 * @seq J47
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "代理扣款返销", summary = "代理扣款返销")
	public AgencyAcctPayNotifyRsp agencyAcctPayNotify(AgencyAcctPayNotifyReq req) throws ApiBusiException;

	/**
	 * 退单申请【状态通知接口】
	 * 
	 * @seq J51
	 * @作者 sguo
	 * @创建日期 2014-9-22
	 * @第三方系统 森瑞(SR)
	 * @param ReadICCIDSRRequset
	 * @return ReadICCIDSRSResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J51 读取ICCID值接口", summary = "订单系统请求写卡机服务读取ICCID值。")
	public ReadICCIDSRResponse readICCIDSR(ReadICCIDSRRequset req) throws ApiBusiException;

	/**
	 * 写卡请求
	 * 
	 * @seq J52
	 * @作者 sguo
	 * @创建日期 2014-9-22
	 * @第三方系统 森瑞(SR)
	 * @param WriteICCIDSRRequset
	 * @return WriteICCIDSRSResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J52 写卡接口", summary = "外部针对每次写卡机访问请求对写卡机管理服务发起一个HTTP请求，写卡机管理服务接收到请求之后会对该请求进行合法性校验，如果校验不通过，将返回校验失败并中断流程；如果校验通过，则启动对写卡机的操作，处理完成后将返回处理结果。")
	public WriteICCIDSRResponse writeICCIDSR(WriteICCIDSRRequset req) throws ApiBusiException;

	/**
	 * 回收卡
	 * 
	 * @seq J53
	 * @作者 sguo
	 * @创建日期 2014-9-22
	 * @第三方系统 森瑞(SR)
	 * @param WriteICCIDSRRequset
	 * @return WriteICCIDSRSResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J53回收卡", summary = "回收卡")
	public RevertCardResponse revertCard(RevertCardRequset req) throws ApiBusiException;

	/**
	 * 身份校验
	 * 
	 * @seq J61
	 * @作者 sguo
	 * @创建日期 2014-9-22
	 * @第三方系统 国政通(ECAOP)
	 * @param CheckIDECAOPRequset
	 * @return CheckIDECAOPResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J61身份校验接口", summary = "验证身份证信息是否正确")
	public CheckIDECAOPResponse checkIDECAOP(CheckIDECAOPRequset req) throws ApiBusiException;

	/**
	 * 身份校验
	 * 
	 * @seq J61
	 * @作者 sguo
	 * @创建日期 2014-9-22
	 * @第三方系统 国政通(ECAOP)
	 * @param CheckIDECAOPRequset
	 * @return CheckIDECAOPResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J62获取证件照图片路径", summary = "获取证件照图片路径")
	public AccountPhotoResponse getAccountPhotoFromWYG(AccountPhotoRequest req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "订单状态同步到淘宝天机平台", summary = "订单状态同步到淘宝天机平台")
	public TbTianjiOrderStatusNoticeResp syncTbTianjiOrderStatus(TbTianjiOrderStatusNoticeReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "订单物流信息同步到淘宝天机平台（发货）", summary = "订单物流信息同步到淘宝天机平台（发货）")
	public TbTianjiOrderDeliverySyncResp syncTbTianjiOrderDelivery(TbTianjiOrderDeliverySyncReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "获取淘宝天机订单信息", summary = "获取淘宝天机订单信息")
	public TbTianjiOrderInfoGetResp getTbTianjiOrderInfo(TbTianjiOrderInfoGetReq req) throws ApiBusiException;

	/**
	 * 订单状态获取【订单详情查询接口】
	 * 
	 * @seq J71
	 * @作者 sguo
	 * @创建日期 2014-9-22
	 * @第三方系统 淘宝(TAOBAO)
	 * @param GetTaobaoOrderInfoTAOBAORequset
	 * @return GetTaobaoOrderInfoTAOBAOResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J71 订单详情查询接口", summary = "订单系统将订单信息通知淘宝")
	public GetTaobaoOrderInfoTAOBAOResponse getTaobaoOrderInfo(GetTaobaoOrderInfoTAOBAORequset req) throws ApiBusiException;

	/**
	 * 发货通知接口
	 * 
	 * @seq J72
	 * @作者 sguo
	 * @创建日期 2014-9-22
	 * @第三方系统 淘宝(TAOBAO)
	 * @param GetTaobaoOrderInfoTAOBAORequset
	 * @return GetTaobaoOrderInfoTAOBAOResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J72 订单信息变更", summary = "订单系统调用该接口可实现无需物流（虚拟）发货,使用该接口发货，交易订单状态会直接变成卖家已发货；用户调用该接口可实现在线订单发货（支持货到付款） 调用该接口实现在线下单发货。")
	public LogisticsTAOBAOResponse LogisticsTB(LogisticsTAOBAORequset req) throws ApiBusiException;

	/**
	 * 订单信息变更
	 * 
	 * @seq J73
	 * @作者 sguo
	 * @创建日期 2014-9-22
	 * @第三方系统 淘宝(TAOBAO)
	 * @param GetTaobaoOrderInfoTAOBAORequset
	 * @return GetTaobaoOrderInfoTAOBAOResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J73 订单信息同步", summary = "订单信息同步,同步备注信息")
	public SynchronousOrderTBResponse SynchronousOrderTB(SynchronousOrderTBRequset req) throws ApiBusiException;

	/**
	 * 订单信息同步
	 * 
	 * @seq J81
	 * @作者 sguo
	 * @创建日期 2014-9-23
	 * @第三方系统 南都(ND)
	 * @param NotifyOrderInfoNDRequset
	 * @return NotifyOrderInfoNDResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J81 订单信息变更", summary = "订单系统将订单信息通知南都")
	public NotifyOrderInfoNDResponse notifyOrderInfoND(NotifyOrderInfoNDRequset req) throws ApiBusiException;

	/**
	 * 派工号接收
	 * 
	 * @seq J82
	 * @作者 sguo
	 * @创建日期 2014-9-23
	 * @第三方系统 南都(ND)
	 * @param NotifyOrderInfoNDRequset
	 * @return NotifyOrderInfoNDResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J82 订单信息变更", summary = "物流公司接到订单系统订单后，给订单分配派工号时，调用该接口，通知订单系统，该订单由哪个工号进行处理；只有此工号登陆订单系统，才能看到此订单进行开户、写卡操作。")
	public DispatchingNumReceivingNDResponse dispatchingNumReceivingND(DispatchingNumReceivingNDRequset req) throws ApiBusiException;

	/**
	 * 订单状态通知
	 * 
	 * @seq J83
	 * @作者 sguo
	 * @创建日期 2014-9-23
	 * @第三方系统 南都(ND)
	 * @param NotifyOrderInfoNDRequset
	 * @return NotifyOrderInfoNDResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J83 订单状态通知接口", summary = "物流公司获取订单后，将订单的状态通知给订单系统。状态包括：成功接收、拒绝接收、开户失败、用户拒收、确认配送。")
	public NotifyOrderStatuNDResponse notifyOrderStatuND(NotifyOrderStatuNDRequset req) throws ApiBusiException;

	/**
	 * 处理完成通知
	 * 
	 * @seq J84
	 * @作者 sguo
	 * @创建日期 2014-9-23
	 * @第三方系统 南都(ND)
	 * @param OrderDealSuccessNDRequset
	 * @return OrderDealSuccessNDResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J84 处理完成通知", summary = "物流公司获取订单后，对订单进行处理，处理完成后，把相关信息（串号、开户信息、物流信息、补寄信息）通知给订单系统。")
	public OrderDealSuccessNDResponse orderDealSuccessND(OrderDealSuccessNDRequset req) throws ApiBusiException;

	/**
	 * 订单分流
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单分流", summary = "沃云购2.0新老系统订单分流、接口机分流")
	public OrderSeparteResp orderSeparterFlow(OrderSeparteReq req) throws ApiBusiException;

	/**
	 * 订单分流
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单分流", summary = "沃云购2.0新老系统订单分流、接口机分流")
	public OrderSeparteResp orderSeparterFlow2(OrderSeparteReq req) throws ApiBusiException;

	/**
	 * 订单分流，接口机调用信息通知新订单系统
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "接口执行", summary = " 订单分流，接口机调用信息通知新订单系统")
	public OrderInfExecResp orderInfExec(OrderInfExecReq req) throws ApiBusiException;

	/**
	 * 订单分流，接口机调用信息通知新订单系统
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "接口执行", summary = " 订单分流，接口机调用信息通知新订单系统")
	public OrderInfExecResp orderInfExec2(OrderInfExecReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "订单Tree基本信息对外提供", summary = "订单Tree基本信息对外提供")
	public OrderTreesInfoResp getOrderTreeInfo(OrderTreesInfoReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "模拟订单归集请求", summary = "模拟订单归集请求")
	public SimulatorDDGJResp simulatorDDGJ(SimulatorDDGJReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "订单信息查询接口", summary = "接收外部订单查询请求，返回订单信息")
	public OrderInfoResp queryOrderInfo(OrderInfoReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "测试单退单", summary = "测试单退单")
	public StateSynToZBResponse batchRefund(BatchRefundReq req) throws ApiBusiException;

	/**
	 * 商城收费、退款成功触发接口
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "商城收费、退款成功触发接口", summary = "商城收费、退款成功触发接口")
	public FeeInformResp feeInform(FeeInformReq feeInformReq) throws ApiBusiException;

	/**
	 * 订单开户、返销完工触发接口"
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J48订单开户、返销完工触发接口", summary = "订单开户、返销完工触发接口")
	public OrderAccountOrBuybackInformResp orderAccountOrBuybackInform(OrderAccountOrBuybackInformReq orderAccountOrBuybackInformReq) throws ApiBusiException;

	/**
	 * 测试环境通过此接口将订单缓存到正式环境的memcache中
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "测试环境通过此接口将订单缓存到正式环境的memcache中", summary = "测试环境通过此接口将订单缓存到正式环境的memcache中")
	public PushOrderID2MemcacheResp pushOrderID2Memcache(PushOrderID2MemcacheReq request) throws ApiBusiException;

	/**
	 * CBSS-移网产品服务变更
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "CBSS-移网产品服务变更", summary = "CBSS-移网产品服务变更")
	public MobileNetworkServiceHandleResp mobileNetworkServiceHandle(MobileNetworkServiceHandleReq req) throws ApiBusiException;

	/**
	 * CBSS-SP订购
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "CBSS-SP订购", summary = "CBSS-SP订购")
	public SPBuyServiceHandleResp spBuyServiceHandle(SPBuyServiceHandleReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "CBSS-系统登录认证", summary = "CBSS-系统登录认证")
	public Object toLoginAndCert(CBssLoginCertReq req);

	/**
	 * CBSS-SP订购(提供老订单系统使用)
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "CBSS-SP订购", summary = "CBSS-SP订购")
	public SPBuyCBssOutResp spBuyServiceHandle(SPBuyCBssOutReq req) throws ApiBusiException;

	/**
	 * CBSS-移网(提供老订单系统使用)
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "CBSS-移网", summary = "CBSS-移网")
	public MobileCBssOutResp MobileServiceHandle(MobileCBssOutReq req) throws ApiBusiException;

	/**
	 * 订单流转到旧订单系统
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单流转到旧订单系统", summary = "订单流转到旧订单系统")
	public NotifyOrderInfo2OldSysResponse orderInfo2OldOrderSys(NotifyOrderInfo2OldSysRequest req) throws ApiBusiException;

	// ------------------顺丰相关接口 Start--------------------------
	/**
	 * 订单信息同步（下单）
	 * 
	 * @seq J91
	 * @作者 sguo
	 * @创建日期 2014-9-23
	 * @第三方系统 顺丰(SF)
	 * @param NotifyOrderInfoSFRequset
	 * @return NotifyOrderInfoSFResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J91 订单信息变更", summary = "订单系统将订单信息变更需要通知顺丰")
	public NotifyOrderInfoSFResponse notifyOrderInfoSF(NotifyOrderInfoSFRequset req) throws ApiBusiException;

	/**
	 * 订单筛选接口
	 * 
	 * @seq J92
	 * @作者 sguo
	 * @创建日期 2014-9-23
	 * @第三方系统 顺丰(SF)
	 * @param OrderFilterServiceRequset
	 * @return OrderFilterServiceResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J92 订单筛选接口", summary = "1、	订单系统通过此接口向顺丰企业服务平台发送自动筛单请求，用于判断客户的收、派地址是否属于顺丰的收派范围。顺丰系统会根据收派双方的地址自动判断是否在顺丰的收派范围内。如果属于范围内则返回可收派，否则返回不可收派。")
	public OrderFilterServiceResponse orderFilterServiceSF(OrderFilterServiceRequset req) throws ApiBusiException;

	/**
	 * 订单筛选接口
	 * 
	 * @seq J93
	 * @作者 sguo
	 * @创建日期 2014-9-23
	 * @第三方系统 顺丰(SF)
	 * @param OrderFilterServiceRequset
	 * @return OrderFilterServiceResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J93 订单信息变更", summary = "1、	向订单系统定时推送运单的路由信息。需要客户系统提供服务以便顺丰向客户系统推送路由数据。")
	public RoutePushServiceResponse routePushServiceSF(RoutePushServiceRequset req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "J93 订单信息变更", summary = "1、\t向订单系统定时推送运单的路由信息。需要客户系统提供服务以便顺丰向客户系统推送路由数据。")
	public EmsRoutePushServiceResp routePushServiceEms(EmsRoutePushServiceReq req) throws ApiBusiException;

	/**
	 * 接收顺丰推送订单人工筛选信息
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J94 接收派工号(接收顺丰推送订单人工筛选信息)", summary = "接收顺丰推送订单人工筛选信息")
	public ArtificialSelectResponse artificialSelectServiceSF(ArtificialSelectRequest request) throws ApiBusiException;

	/**
	 * 顺丰物流路由查询
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J95顺丰物流路由查询", summary = "顺丰物流路由查询")
	public RouteServiceResponse querySfRoute(RouteServiceRequest req) throws ApiBusiException;

	/**
	 * 顺丰订单信息查询
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J96顺丰订单信息查询", summary = "顺丰订单信息查询")
	public OrderSearchServiceResponse querySfOrder(OrderSearchServiceRequest req) throws ApiBusiException;

	// ------------------顺丰相关接口End--------------------------
	/**
	 * 淘宝证件图片获取
	 * 
	 * @seq J117
	 * @作者 zou.qh
	 * @创建日期 2015-4-17
	 * @第三方系统 TB
	 * @param TaobaoIdentityGetRequest
	 * @return TaobaoIdentityGetResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "淘宝证件信息获取", summary = "淘宝证件信息获取")
	public TaobaoIdentityGetResponse getIdentityFromTaobao(TaobaoIdentityGetRequest req);

	/**
	 * 照片信息查询
	 * 
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "照片信息查询", summary = "照片信息查询")
	public CertCheckZBResponse getAccountPhotoFromZB(CertCheckZBRequest req) throws ApiBusiException;

	/**
	 * 号码状态变更(总部aop)
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "aop号码状态变更", summary = "aop号码状态变更")
	public NumberStateChangeZBResponse numberStateChange(NumberStateChangeZBRequest req) throws ApiBusiException;

	/**
	 * 号码状态变更(bss)
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "bss号码状态变更", summary = "bss号码状态变更")
	public NumberStateChangeBssResp numberStateChangeBss(NumberStateChangeBssRequest req) throws ApiBusiException;

	/**
	 * 号码资源查询(新商城)
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "号码资源查询", summary = "号码资源查询")
	public NumberResourceQueryWYGResponse numberResourceQueryWYG(NumberResourceQueryWYGRequest req) throws ApiBusiException;

	/**
	 * 号码变更通知新商城
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "号码变更通知新商城", summary = "号码变更通知新商城")
	public NotifyOrderInfoWYGResponse numberChangeWYG(NumberChangeWYGRequest req) throws ApiBusiException;

	/**
	 * 终端状态查询变更
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "终端状态查询变更", summary = "终端状态查询变更")
	public TerminalStatusQueryChanageResp terminalStatusQueryChanage(TerminalStatusQueryChanageReq req) throws ApiBusiException;

	/**
	 * 开户处理提交
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "开户处理提交", summary = "开户处理提交")
	public OpenDealSubmitResp openDealSubmit(OpenDealSubmitReq req) throws ApiBusiException;

	/**
	 * 开户处理提交
	 * 
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "开户处理提交", summary = "开户处理提交")
	public OpenDealSubmitResp openDealSubmitZJ(OpenDealSubmitReqZJ req) throws ApiBusiException;

	/**
	 * 发展人查询（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "发展人查询(AOP)", summary = "发展人查询(AOP)")
	public DevelopPersonResponse devrPersonQuery(DevelopPersonQueryReq req) throws ApiBusiException;

	/**
	 * 客户信息查询（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "客户信息查询(AOP)", summary = "客户信息查询(AOP)")
	public CustomerInfoResponse customerInfoQuery(CustomerInfoCheckReq req) throws Exception;

	/**
	 * 客户信息查询（BSS）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "客户信息查询(BSS)", summary = "客户信息查询(BSS)")
	public BSSCustomerInfoResponse customerInfoQueryBSS(BSSCustomerInfoCheckReq req) throws ApiBusiException;

	/**
	 * 写卡结果通知（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "写卡结果通知(AOP)", summary = "写卡结果通知(AOP)")
	public WriteCardResultNoticeResp writeCardResultNotice(WriteCardResultNoticeReq req) throws ApiBusiException;

	/**
	 * 写卡结果通知（AOP）ZJ
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "写卡结果通知(AOP)", summary = "写卡结果通知(AOP)")
	public WriteCardResultNoticeResp writeCardResultNoticeZJ(WriteCardResultNoticeReqZJ req) throws ApiBusiException;

	/**
	 * 写卡结果通知（BSS）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "写卡结果通知(BSS)", summary = "写卡结果通知(BSS)")
	public BSSWriteCardResultNoticeResp writeCardResultNoticeBSS(BSSWriteCardResultNoticeReq req) throws ApiBusiException;

	/**
	 * 写卡数据获取
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J205AOP写卡数据获取", summary = "AOP写卡数据获取")
	CardDataQryResponse cardDataQry(CardDataQryRequest req) throws ApiBusiException;

	/**
	 * 写卡数据同步
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J206AOP写卡数据同步", summary = "AOP写卡数据同步")
	CardDataSynResponse cardDataSyn(CardDataSynRequest req) throws ApiBusiException;

	/**
	 * 写卡数据同步
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "J206AOP写卡数据同步", summary = "AOP写卡数据同步")
	public CardDataSynResponse cardDataSynZJ(CardDataSynRequestZJ req) throws ApiBusiException;

	/**
	 * 开户处理申请
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "开户处理申请", summary = "开户处理申请")
	public OpenDealApplyResp openDealApply(OpenDealApplyReq req) throws ApiBusiException;

	/**
	 * 开户处理申请
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "开户处理申请", summary = "开户处理申请")
	public OpenDealApplyResp openDealApplyZJ(OpenDealApplyReqZJ req) throws ApiBusiException;

	/**
	 * 员工信息校验（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "员工信息校验(AOP)", summary = "员工信息校验(AOP)")
	public EmployeesInfoResponse employeesInfoCheck(EmployeesInfoCheckRequest req) throws ApiBusiException;

	/**
	 * 老用户存费送费
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]老用户存费送费", summary = "老用户存费送费")
	public CunFeeSongFeeResponse cunFeeSongFee(CunFeeSongFeeRequest req) throws ApiBusiException;

	/**
	 * 4G老用户存费送费
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]4G老用户存费送费", summary = "4G老用户存费送费")
	public CunFeeSongFee4GResp cunFeeSongFee4G(CunFeeSongFee4GReq req) throws ApiBusiException;

	/**
	 * 4G老用户存费送费
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]4G老用户存费送费", summary = "4G老用户存费送费")
	public CunFeeSongFee4GResp cunFeeSongFee4GZJ(CunFeeSongFee4GReqZJ req) throws ApiBusiException;

	/**
	 * 
	 * 4G老用户存费送费正式提交
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]4G老用户存费送费正式提交", summary = "4G老用户存费送费正式提交")
	public CunFeeSongFee4GSubmitResp cunFeeSongFee4GSubmit(CunFeeSongFee4GSubmitReq req) throws ApiBusiException;

	/**
	 * 
	 * 4G老用户存费送费正式提交
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]4G老用户存费送费正式提交", summary = "4G老用户存费送费正式提交")
	public CunFeeSongFee4GSubmitResp cunFeeSongFee4GSubmitZJ(CunFeeSongFee4GSubmitReqZJ req) throws ApiBusiException;

	/**
	 * 订单返销
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单返销", summary = "订单返销")
	public OrderReverseSalesResp orderReverseSales(OrderReverseSalesReq req) throws ApiBusiException;

	/**
	 * 订单返销
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单返销[BSS]", summary = "订单返销[BSS]")
	public BSSOrderReverseSalesResp orderReverseSalesBSS(BSSOrderReverseSalesReq req) throws ApiBusiException;

	/**
	 * 退机申请
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "退机申请", summary = "退机申请")
	public ReturnMachineApplyResp returnMachineApply(ReturnMachineApplyReq req) throws ApiBusiException;

	/**
	 * 退机提交
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "退机提交", summary = "退机提交")
	public ReturnMachineSubmitResp returnMachineSubmit(ReturnMachineSubmitReq req) throws ApiBusiException;

	/**
	 * 换机提交
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "换机提交", summary = "换机提交")
	public ChangeMachineSubResp ChangeMachineSub(ChangeMachineSubReq req) throws ApiBusiException;

	/**
	 * 2-3G转4G校验（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "2-3G转4G校验（AOP）", summary = "2-3G转4G校验（AOP）")
	public Check23to4Resp check23to4(Check23to4Request req) throws ApiBusiException;

	/**
	 * 2-3G转4G开户申请（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "2-3G转4G开户申请（AOP）", summary = "2-3G转4G开户申请（AOP）")
	public OpenDealApplyResp openAccApply23to4(Open23to4ApplyReq req) throws ApiBusiException;

	/**
	 * 2-3G转4G撤单（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "2-3G转4G撤单（AOP）", summary = "2-3G转4G撤单（AOP）")
	public CannelOrder23to4Resp cannelOrder23to4(CannelOrder23to4Request req) throws ApiBusiException;

	/**
	 * 流量包订购（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "流量包订购（AOP）", summary = "流量包订购（AOP）")
	public TrafficOrderResponse trafficOrder(TrafficOrderRequest req) throws ApiBusiException;

	/**
	 * 套餐变更,撤单（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "套餐变更,撤单（AOP）", summary = "套餐变更,撤单（AOP）")
	public ProdChangeCannelResp prodChangeCancel(ProdChangeCannelRequest req) throws ApiBusiException;

	/**
	 * 套餐变更申请（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "套餐变更申请", summary = "套餐变更申请")
	public ProdChangeApplyResp productChangeApply(ProdChangeApplyReq req) throws ApiBusiException;

	/**
	 * 老用户业务校验（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "老用户业务校验（3G）", summary = "老用户业务校验（3G）")
	public OldUserCheck3GResp oldUserCheck3G(OldUserCheck3GReq req) throws ApiBusiException;

	/**
	 * 老用户优惠购机申请（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "老用户优惠购机申请-支持3G", summary = "老用户优惠购机申请")
	public OldUserBuyApplyResp oldActivityMobileApply(OldUserBuyApplyReq req) throws ApiBusiException;

	/**
	 * 老用户优惠购机提交
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "老用户优惠购机提交-支持3G", summary = "老用户优惠购机提交")
	public OldUserBuySubmitResp oldActivityMobileSubmit(OldUserBuySubmitReq req) throws ApiBusiException;

	/**
	 * 用户资料校验三户返回
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "用户资料校验三户返回", summary = "用户资料校验三户返回")
	public UserInfoCheck3BackResp userInfoCheck3Back(UserInfoCheck3BackReq req) throws ApiBusiException;

	/**
	 * 用户资料校验三户返回
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "用户资料校验三户返回", summary = "用户资料校验三户返回")
	public UserInfoCheck3BackResp userInfoCheck3BackZJ(UserInfoCheck3BackReqZJ req) throws ApiBusiException;

	/**
	 * 接口转发
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "封装接口转发", summary = "接口转发")
	public JKZFInfResp jkzfInf(JKZFInfReq req) throws ApiBusiException;

	/**
	 * 订单系统向森锐仿真派单(业务办理、开户)
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "仿真派单", summary = "仿真派单")
	public SimulationResponse simulation(SimulationRequset req) throws ApiBusiException;

	/**
	 * 接受森锐仿真回单(业务办理、开户)
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "仿真回单", summary = "仿真回单")
	public SimulationResultReceiveResponse simulationResultReceive(SimulationResultReceiveRequset req) throws ApiBusiException;

	/**
	 * 批量终端状态变更
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]终端状态变更-批量", summary = "终端状态变更-批量")
	public TerminalStatusQueryChanageResp termiStatusChanageBatch(TerminalStatusQueryChanageBatchReq req) throws ApiBusiException;

	/**
	 * 裸机销售
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]裸机销售", summary = "裸机销售")
	public BareMachineSaleResp bareMachineSale(BareMachineSaleReq req) throws ApiBusiException;

	/**
	 * 订单查询[AOP]
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单查询[AOP]", summary = "订单查询[AOP]")
	public OrderQueryRespone orderQuery(OrderQueryReq req) throws ApiBusiException;
	/**
	 * 订单查询[AOP]自定义
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单查询[AOP]自定义", summary = "订单查询[AOP]自定义")
    public OrderQueryRespone orderQueryNewIntent(IntentOrderQueryForCBReq req) throws ApiBusiException;
	/**
	 * 发送三网短信
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "发送三网短信", summary = "发送三网短信")
	public Sms3NetSendResp send3NetSms(Sms3NetSendReq req) throws ApiBusiException;

	/**
	 * 获取总商上传图片地址
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "获取总商上传图片地址", summary = "获取总商上传图片地址")
	public QryPhoneUrlResponse qryPhoneUrl(QryPhoneUrlRequest req) throws ApiBusiException;

	/**
	 * 开户处理申请
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "[BSS]开户处理申请", summary = "[BSS]开户处理申请")
	public BSSAccountResponse openDealApplyBSS(BSSAccountReq req) throws ApiBusiException;

	/**
	 * 开户处理提交
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "[BSS]开户处理提交", summary = "[BSS]开户处理提交")
	public BSSOrderSubResponse openDealSubmitBSS(BSSOrderSubReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "BSS H2接口方式", summary = "BSS H2接口方式")
	public BaseBSSResp BSSH2Interface(BaseBSSReq req) throws ApiBusiException;

	/**
	 * BSS卡数据同步
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS卡数据同步接口", summary = "BSS卡数据同步接口")
	public WriteCardPreRsp writeCardDataSync2BSS(WriteCardPreReq req) throws ApiBusiException;

	/**
	 * 获取写卡数据
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS获取写卡数据", summary = "BSS获取写卡数据")
	public BSSGetCardMsgRsp getCardDataFromBSS(BSSGetCardMsgReq req) throws ApiBusiException;

	/**
	 * BSS使用人数量校验接口
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS使用人数量校验接口", summary = "BSS使用人数量校验接口")
	public UserCountCheckRsp userCountCheckFromBSS(UserCountCheckReq req) throws ApiBusiException;

	/**
	 * SOA-SP服务办理
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "SP服务办理接口", summary = "SP服务办理接口")
	public HandleSpServiceResp handleSpService(HandleSpServiceReq req) throws ApiBusiException;

	/**
	 * SOA-附加产品订购接口
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "附加产品订购接口", summary = "附加产品订购接口")
	public SubProOrderResp subProOrder(SubProOrderReq req) throws ApiBusiException;

	/**
	 * 老用户续约申请（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "老用户续约申请", summary = "老用户续约申请")
	public OldUserRenActivityResp oldRenActivityApply(OldUserRenActivityReq req) throws ApiBusiException;

	/**
	 * 老用户续约提交（AOP）
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "老用户续约提交", summary = "老用户续约提交")
	public OldUserRenActivitySubmitResp oldRenActivitySubmit(OldUserRenActivitySubmitReq req) throws ApiBusiException;

	/**
	 * 路由信息通知总部
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "路由信息通知总部", summary = "路由信息通知总部")
	public NotifyRouteInfoZBResponse notifyRouteInfoZB(NotifyRouteInfoZBRequest request) throws ApiBusiException;

	/**
	 * 沃云购 受理单信息查询
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "沃云购 受理单信息查询", summary = "沃云购 受理单信息查询")
	public WYGAcceptanceQueryResp acceptanceQuery(WYGAcceptanceQueryReq request) throws ApiBusiException;

	/**
	 * 接收华盛 SAPB2C省分退货、拦截指令
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "接收华盛 SAPB2C省分退货、拦截指令", summary = "接收华盛 SAPB2C省分退货、拦截指令")
	public OrderCancelReceiveResp orderCancelReceive(OrderCancelReceiveReq request) throws ApiBusiException;

	/**
	 * 出库信息回传SAP
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "出库信息回传SAP", summary = "出库信息回传SAP")
	public DeliveNotifyResp deliveNotify(DeliveNotifyReq request) throws ApiBusiException;

	/**
	 * 出库信息回传SAP
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "退货入库传输SAP", summary = "退货入库传输SAP")
	public ReturnWarehousingResp ReturnWarehousing(ReturnWarehousingReq request) throws ApiBusiException;

	/**
	 * 仓储商采购退货订单出库回传SAP接口
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "仓储商采购退货订单出库回传SAP接口", summary = "仓储商采购退货订单出库回传SAP接口")
	public OrderCheckOutResp orderCheckOutB2B(OrderCheckOutReq request) throws ApiBusiException;

	/**
	 * 轮询写卡机工位状态
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "轮询写卡机工位状态", summary = "轮询写卡机工位状态")
	public WriMachStaQueResponse WriteMachineStation(WriMachStaQueRequest request) throws ApiBusiException;

	/**
	 * 写卡请求
	 * 
	 * @创建日期 2016-11-18
	 * @第三方系统 森瑞(SR)
	 * @param WriteICCIDSRRGRequset
	 * @return WriteICCIDSRSResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "写卡接口(人工批量)", summary = "外部针对每次写卡机访问请求对写卡机管理服务发起一个HTTP请求，写卡机管理服务接收到请求之后会对该请求进行合法性校验，如果校验不通过，将返回校验失败并中断流程；如果校验通过，则启动对写卡机的操作，处理完成后将返回处理结果。")
	public WriteICCIDSRRGResponse writeICCIDSRRG(WriteICCIDSRRGRequset req) throws ApiBusiException;

	/**
	 * 回收卡
	 * 
	 * @创建日期 2016-11-18
	 * @第三方系统 森瑞(SR)
	 * @param RevertCardRGRequset
	 * @return WriteICCIDSRSResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "回收卡(人工批量)", summary = "回收卡(人工批量)")
	public RevertCardRGResponse revertCardRG(RevertCardRGRequset req) throws ApiBusiException;

	/**
	 * 回收卡
	 * 
	 * @创建日期 2016-11-21
	 * @第三方系统 森瑞(SR)
	 * @param QueWriMachStaInBatchRGRequset
	 * @return QueWriMachStaInBatchRGResponse
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "批量获取写卡机状态获取data", summary = "批量获取写卡机状态获取data")
	public QueWriMachStaInBatchRGResponse queWriMachStaInBatchRG(QueWriMachStaInBatchRGRequset req) throws ApiBusiException;

	/**
	 * 单笔推送可用ICCID
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "接收森锐单笔推送可用ICCID", summary = "接收森锐单笔推送可用ICCID")
	public RceiveICCIDResp rceiveICCID(RceiveICCIDReq req) throws ApiBusiException;

	/**
	 * 浙江省份BSS选号接口
	 * 
	 * @seq
	 * @作者 wayne
	 * @创建日期 2016-9-30
	 * @第三方系统:
	 * @param NumberResourceQueryZjBssRequest
	 * @return NumberResourceQueryZjBssResponse
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "号码资源查询", summary = "号码资源查询")
	public NumberResourceQueryZjBssResponse numberResourceQueryZjBss(NumberResourceQueryZjBssRequest req) throws ApiBusiException;

	/**
	 * 总部号码资源查询接口
	 * 
	 * @seq
	 * @作者 wayne
	 * @创建日期 2016-10-10
	 * @第三方系统:
	 * @param
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "总部号码资源查询", summary = "总部号码资源查询")
	public NumberResourceQueryZbResponse numberResourceQueryZb(NumberResourceQueryZbRequest req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "获取EMS物流单号", summary = "获取EMS物流单号")
	public EmsLogisticsNumberGetResp getEmsLogisticsNumber(EmsLogisticsNumberGetReq req) throws ApiBusiException;

	/**
	 * 浙江省份号码预占接口
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "浙江省份号码预占", summary = "浙江省份号码预占")
	public NumberResourceChangePreCaptureZjResponse NumberResourceChangePreCaptureZj(NumberResourceChangePreCaptureZjRequest req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "物流信息同步EMS", summary = "物流信息同步EMS")
	public EmsLogisticsInfoSyncResp syncLogisticsInfoToEms(EmsLogisticsInfoSyncReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "从EMS自助服务平台获取EMS物流单号", summary = "从EMS自助服务平台获取EMS物流单号")
	public LogisticsNumberGetResp getLogisticsNumber(LogisticsNumberGetReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "物流信息同步EMS自助服务平台", summary = "物流信息同步EMS自助服务平台")
	public LogisticsInfoSyncResp syncLogisticsInfo(LogisticsInfoSyncReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "调总部接口发送短信", summary = "调总部接口发送短信")
	public AopSmsSendResp sendSmsZb(AopSmsSendReq req) throws ApiBusiException, Exception;

	@ZteSoftCommentAnnotation(type = "method", desc = "用户属性查询接口(客户校验)", summary = "用户属性查询接口(客户校验)")
	public CustomerCheckResp bssCustomerCheck(CustomerCheckReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "开户预提交", summary = "开户预提交")
	public PreCommitResp bssPreCommit(PreCommitReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "后向激活订单激活BSS", summary = "后向激活订单激活BSS")
	public BSSActivateOperRsp syncActiveToBSS(BSSActivateOperReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "后向激活订单激活AOP", summary = "后向激活订单激活AOP")
	public OrderActiveResp syncActiveToAOP(OrderActiveReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "获取白卡数据", summary = "获取白卡数据")
	public GetBlanckCardDataRsp getBlanckCard(GetBlanckCardDataReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "获取EMS物流信息", summary = "获取EMS物流单信息")
	public EMSLogisticsInfoResp getEmsLogisticsInfo(EMSLogisticsInfoReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "开户正式提交", summary = "开户正式提交")
	public BSSAccountOfficialSubResp subAccountOfficial(BSSAccountOfficialSubReq req) throws ApiBusiException;

	/**
	 * 续约活动校验和受理接口
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "号码资源查询", summary = "号码资源查询")
	public BusinessAcceptenceAndVerificationResponse businessAcceptenceAndVerification(BusinessAcceptenceAndVerificationReq req) throws ApiBusiException;

	@ZteSoftCommentAnnotation(type = "method", desc = "卡数据同步", summary = "卡数据同步")
	public SynCardDataRsp synCardData(SynCardDataReq req) throws ApiBusiException;

	// songqi
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]后激活订单信息同步接口AOP", summary = "[AOP]后激活订单信息同步接口AOP")
	public OrderInfoSynRsp orderInfoSyn(OrderInfoSynReq req) throws ApiBusiException;

	// songqi 2017年11月24日 15:31:28
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]sp类产品订购申请", summary = "[AOP]sp类产品订购申请")
	public FlowPacketPreRsp flowPacketPre(FlowPacketPreReq req) throws ApiBusiException;

	// songqi 2017年11月24日 15:31:31
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]流量包订购/退订", summary = "[AOP]流量包订购/退订")
	public FlowPacketApplyRsp flowPacketApply(FlowPacketApplyReq req) throws ApiBusiException;

	/**
	 * BSS本地商城订单状态同步接口
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "本地商城状态同步", summary = "本地商城状态同步")
	public LocalGoodsStatusSynchronizationResp localGoodsStatusSynchronization(LocalGoodsStatusSynchronizationReq req) throws ApiBusiException;

	/**
	 * 开户正式提交接口
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "号码资源查询", summary = "号码资源查询")
	public AccountOpenFormalSubmissionResp accountOpenFormalSubmission(AccountOpenFormalSubmissionReq req) throws ApiBusiException;

	/**
	 * 接口日志记录
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "接口日志记录", summary = "接口日志记录")
	public ZteResponse recordInfLog(RecordInfLogReq req) throws ApiBusiException;

	/**
	 * 后向激活订单撤单(AOP)
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "后向激活订单撤单(AOP)", summary = "后向激活订单撤单(AOP)")
	public OrderCancelResp laterActiveOrderCancel(OrderCancelReq req) throws ApiBusiException;

	/**
	 * 消息同步通知
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "商品消息同步", summary = "商品消息同步")
	public MessageSyncResp messageSync(MessageSyncReq req) throws ApiBusiException;

	/**
	 * 用户返档
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "用户返档", summary = "用户返档")
	public ReturnFileResp returnFile(ReturnFileReq req) throws ApiBusiException;

	/**
	 * 标准地址查询
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "标准地址查询", summary = "标准地址查询")
	public QueryStdAddrResp qryStdAddr(QueryStdAddressReq req) throws ApiBusiException;

	/**
	 * 资源预判
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "资源预判", summary = "资源预判")
	public ResourcePreCheckResp resourcePreCheck(ResourcePreCheckReq req) throws ApiBusiException;

	/**
	 * 一体化县分查询
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "一体化县分查询", summary = "一体化县分查询")
	public AreaQueryResp areaQuery(AreaQueryReq req) throws ApiBusiException;

	/**
	 * 查询任务人员
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "查询任务人员", summary = "查询任务人员")
	public OrderListForWorkResp getOperator(OrderListForWorkReq req) throws ApiBusiException;

	/**
	 * @author song.qi
	 * CB用户返档接口
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]客户资料信息修改", summary = "[AOP]客户资料信息修改")
	public CustInfoModRsp custInfoMod(CustInfoModReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "新用户主副卡开户处理申请", summary = "新用户主副卡开户处理申请")
	public MainViceOpenResp mainViceOpen(MainViceOpenReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "正式提交接口", summary = "正式提交接口")
	public OpenAccountSubmitResp openAccountSubmit(OpenAccountSubmitReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "老用户加入主副卡", summary = "老用户加入主副卡")
	public UserJoinMainViceCardResp joinMVCard(UserJoinMainViceCardReq req) throws ApiBusiException;
	@ZteSoftCommentAnnotation(type = "method", desc = "客户资料创建", summary = "客户资料创建")
	public CustInfoCreateResponse CustInfoCreate(CustInfoCreateReq req) throws ApiBusiException;
	@ZteSoftCommentAnnotation(type = "method", desc = "浙江省份移网号码预占", summary = "浙江省份移网号码预占")
    public NumberResourceChangePreCaptureZjResponse numberResourceNewGroupOrderZjRequest(NumberResourceNewGroupOrderZjRequest req) throws ApiBusiException;

	/**
	 * 终端校验
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "终端校验", summary = "终端校验")
	public OrderTerminalCheckResp orderTerminalCheck(OrderTerminalCheckReq req) throws ApiBusiException;

}
