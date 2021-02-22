package zte.net.iservice.impl;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import params.ZteRequest;
import params.ZteResponse;
import params.orderqueue.req.OrderHandleLogsAddReq;
import params.orderqueue.resp.OrderHandleLogsAddResp;
import sun.misc.BASE64Encoder;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.InfConst;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.req.SimulatorDDGJReq;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
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
import zte.net.ecsord.params.bss.vo.GDBssHead;
import zte.net.ecsord.params.bss.vo.GDBssSocketHead;
import zte.net.ecsord.params.busi.req.AttrTmResourceInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtvtlBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderRouteLogBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
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
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
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
import zte.net.ecsord.params.ecaop.resp.EmpIdEssIDResp;
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
import zte.net.ecsord.params.ecaop.resp.vo.OrderInfoRespVo;
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
import zte.net.ecsord.params.nd.vo.OrderInfo;
import zte.net.ecsord.params.nd.vo.StatuOrderInfo;
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
import zte.net.ecsord.params.sf.vo.OrderFilterResult;
import zte.net.ecsord.params.sf.vo.WaybillRoute;
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
import zte.net.ecsord.params.sr.vo.QueWriMachStaInBatchRGResp;
import zte.net.ecsord.params.sr.vo.RceiveICCIDBody;
import zte.net.ecsord.params.sr.vo.ReadIccidResp;
import zte.net.ecsord.params.sr.vo.RecycleCardRGResp;
import zte.net.ecsord.params.sr.vo.RecycleCardResp;
import zte.net.ecsord.params.sr.vo.WriMachStaResp;
import zte.net.ecsord.params.sr.vo.WritCardRGResp;
import zte.net.ecsord.params.sr.vo.WritCardResp;
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
import zte.net.ecsord.params.taobao.vo.LogisticsTAOBAOOnline;
import zte.net.ecsord.params.taobao.vo.LogisticsTAOBAOVirtual;
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
import zte.net.ecsord.params.wms.vo.NotifyStatusFromWMSGoodInfoVo;
import zte.net.ecsord.params.wms.vo.NotifyStatusFromWMSOrderInfoVo;
import zte.net.ecsord.params.workCustom.po.ES_WORK_SMS_SEND;
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
import zte.net.ecsord.params.zb.vo.ResourecsRsp;
import zte.net.ecsord.rule.comm.ExceptionFact;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.ecsord.utils.PCWriteCardTools;
import zte.net.iservice.IInfServices;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.req.RuleExeLogDelReq;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.net.params.resp.RuleExeLogDelResp;
import zte.net.params.resp.RuleTreeExeResp;
import zte.params.ecsord.req.EMSLogisticsInfoReq;
import zte.params.ecsord.req.GetOrderByInfIdReq;
import zte.params.ecsord.req.GetOrderByOutTidReq;
import zte.params.ecsord.req.GetOutOrderByOrderIdReq;
import zte.params.ecsord.resp.EMSLogisticsInfoResp;
import zte.params.ecsord.resp.GetOrderByInfIdResp;
import zte.params.ecsord.resp.GetOrderByOutTidResp;
import zte.params.ecsord.resp.GetOutOrderByOrderIdResp;
import zte.params.order.req.ChargebackStatuChgReq;
import zte.params.order.req.OffLineOpenActReq;
import zte.params.order.resp.ChargebackStatuChgResp;
import zte.params.order.resp.OffLineOpenActResp;

import com.powerise.ibss.framework.FrameException;
import com.taobao.api.domain.Shipping;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.AlibabaAliqinTccTradeIdentityGetRequest;
import com.taobao.api.request.AlibabaTianjiSupplierOrderDeliveryRequest;
import com.taobao.api.request.AlibabaTianjiSupplierOrderDeliveryRequest.DistributionOrderLogisticsModel;
import com.taobao.api.request.AlibabaTianjiSupplierOrderQueryRequest;
import com.taobao.api.request.AlibabaTianjiSupplierOrderResultRequest;
import com.taobao.api.request.AlibabaTianjiSupplierOrderResultRequest.SupplierOrderResultModel;
import com.taobao.api.request.LogisticsDummySendRequest;
import com.taobao.api.request.LogisticsOfflineSendRequest;
import com.taobao.api.request.LogisticsOnlineSendRequest;
import com.taobao.api.request.TradeGetRequest;
import com.taobao.api.request.TradeMemoUpdateRequest;
import com.taobao.api.response.AlibabaAliqinTccTradeIdentityGetResponse;
import com.taobao.api.response.AlibabaAliqinTccTradeIdentityGetResponse.IdentityInfo;
import com.taobao.api.response.AlibabaTianjiSupplierOrderDeliveryResponse;
import com.taobao.api.response.AlibabaTianjiSupplierOrderQueryResponse;
import com.taobao.api.response.AlibabaTianjiSupplierOrderQueryResponse.DistributionOrderInfo;
import com.taobao.api.response.AlibabaTianjiSupplierOrderResultResponse;
import com.taobao.api.response.LogisticsDummySendResponse;
import com.taobao.api.response.LogisticsOfflineSendResponse;
import com.taobao.api.response.LogisticsOnlineSendResponse;
import com.taobao.api.response.TradeGetResponse;
import com.taobao.api.response.TradeMemoUpdateResponse;
import com.zte.cbss.autoprocess.AbsCbssInvokerOutImpl;
import com.zte.cbss.autoprocess.CreateCustomHandler;
import com.zte.cbss.autoprocess.FourGIdlesseTrafficPackCbssInvoker;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.HttpLoginClientPool;
import com.zte.cbss.autoprocess.SPBuyServiceHandleCbssInvoker;
import com.zte.cbss.autoprocess.model.CustomBill;
import com.zte.cbss.autoprocess.model.CustomInfo;
import com.zte.cbss.autoprocess.model.LoginInfo;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.InfCommonUtils;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.inf.communication.client.AbsCbssInvoker;
import com.ztesoft.inf.communication.client.AbsCbssInvokerOut;
import com.ztesoft.inf.communication.client.CommCaller;
import com.ztesoft.inf.framework.commons.CodedException;
import com.ztesoft.net.HttpClientUtil;
import com.ztesoft.net.PeripheryHttpClient;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.vo.PlanRule;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.DesUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.OrderHandleLogs;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.InfCommClientCalllog;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.common.RopResp;
import com.ztesoft.service.IInfLogManager;

import commons.CommonTools;
import consts.ConstsCore;

public class InfServices extends BaseSupport implements IInfServices {
	private static Logger logger = Logger.getLogger(InfServices.class);
	private static final RopResp resp = null;

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单信息变更", summary = "订单系统将订单信息变更需要通知总部")
	public NotifyOrderInfoZBResponse notifyOrderInfoZB(NotifyOrderInfoZBRequest request) throws ApiBusiException {
		NotifyOrderInfoZBResponse rsp = new NotifyOrderInfoZBResponse();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2Map(param, request);
			param.put("ord_id", request.getNotNeedReqStrOrderId());
			rsp = (NotifyOrderInfoZBResponse) caller.invoke("periphery.neworderchg", param);
		} catch (Exception e) {
			rsp.setRespCode("-9999");
			rsp.setRespDesc(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单信息同步接口", summary = "商城同步订单总部；总部同步订单商城")
	public SynchronousOrderZBResponse synchronousOrderZB(SynchronousOrderZBRequest request) throws Exception {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		SynchronousOrderZBResponse rsp = new SynchronousOrderZBResponse();
		try {
			BeanUtils.bean2Map(param, request);
			param.put("ord_id", request.getNotNeedReqStrOrderId());
			rsp = (SynchronousOrderZBResponse) caller.invoke("periphery.ordersyn", param);
		} catch (Exception e) {
			rsp.setRespCode("-9999");
			rsp.setRespDesc(e.getMessage());
			e.printStackTrace();
		}
		return rsp;

	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单信息同步接口", summary = "商城同步订单总部；总部同步订单商城")
	public SynchronousOrderZBResponse synchronousOrderGD(SynchronousOrderGDRequest request) throws ApiBusiException {
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		RuleTreeExeReq req = new RuleTreeExeReq();
		req.setRule_id(EcsOrderConsts.ZB_SYN_ORD_GD_RULEID);
		AutoFact fact = new TacheFact();
		fact.setRequest(request);
		req.setFact(fact);
		RuleTreeExeResp resp = client.execute(req, RuleTreeExeResp.class);
		SynchronousOrderZBResponse response = new SynchronousOrderZBResponse();
		return response;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "将串号信息同步到总部接口", summary = "将串号信息同步到总部")
	public NotifyStringZBResponse notifyStringZB(NotifyStringZBRequest request) throws ApiBusiException {
		NotifyStringZBResponse rsp = new NotifyStringZBResponse();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2Map(param, request);
			param.put("ord_id", request.getNotNeedReqStrOrderId());
			rsp = (NotifyStringZBResponse) caller.invoke("periphery.goodsnotify", param);
		} catch (Exception e) {
			rsp.setRespCode("-9999");
			rsp.setRespDesc(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "将货品信息反馈给省分能力平台", summary = "总部将相应的货品明细信息反馈给省分能力平台")
	public NotifyStringGDResponse notifyStringGD(NotifyStringGDRequest request) throws ApiBusiException {
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		RuleTreeExeReq req = new RuleTreeExeReq();
		req.setRule_id(EcsOrderConsts.ZB_NOTIFY_STR_GD_RULEID);
		AutoFact fact = new TacheFact();
		fact.setRequest(request);
		req.setFact(fact);
		RuleTreeExeResp resp = client.execute(req, RuleTreeExeResp.class);
		NotifyStringGDResponse response = new NotifyStringGDResponse();
		return response;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "开户完成接口", summary = "总部将开户完成信息同步订单系统")
	public NotifyOpenAccountGDResponse notifyOpenAccountGD(NotifyOpenAccountGDRequest request) throws ApiBusiException {
		NotifyOpenAccountGDResponse response = new NotifyOpenAccountGDResponse();
		String activeNo = request.getActiveNo();
		try {
			RuleTreeExeReq req = new RuleTreeExeReq();
			String order_id = "";
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTreeByZBInfId(request.getOrderId());
			if (null != orderTree)
				order_id = orderTree.getOrder_id();

			if (null == order_id || "".equals(order_id)) {
				CommonTools.addBusiError("-1", "获取order_id失败");
			}
			String OPER_MODE = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_MODEL);
			if (EcsOrderConsts.ORDER_MODEL_01.equals(OPER_MODE)) {
				req.setRule_id(EcsOrderConsts.ZB_NOTIFY_OPEN_ACCT_GD_RULEID_01);
				req.setExePeerAfRules(false); // 先卡住,只执行接收总部开户结果
				req.setExeParentsPeerAfRules(false); // 先卡住,只执行接收总部开户结果
			} else if (EcsOrderConsts.ORDER_MODEL_02.equals(OPER_MODE)) {
				req.setRule_id(EcsOrderConsts.ZB_NOTIFY_OPEN_ACCT_GD_RULEID_02);
			} else if (EcsOrderConsts.ORDER_MODEL_03.equals(OPER_MODE)) {
				req.setRule_id(EcsOrderConsts.ZB_NOTIFY_OPEN_ACCT_GD_RULEID_03);
			} else if (EcsOrderConsts.ORDER_MODEL_04.equals(OPER_MODE)) {
				req.setRule_id(EcsOrderConsts.ZB_NOTIFY_OPEN_ACCT_GD_RULEID_04);
			}
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			fact.setRequest(request);
			req.setFact(fact);
			orderTree.setCol3(EcsOrderConsts.TRACE_TRIGGER_INF);
			orderTree.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderTree.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderTree.store();
			CommonDataFactory.getInstance().exeRuleTree(req);
			response.setResp_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
			response.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
			response.setResp_msg("开户确认完成");

			// 开启异步线程执行后续规则
			try {
				if (EcsOrderConsts.ORDER_MODEL_01.equals(OPER_MODE)) {
					RuleTreeExeReq reqNext = new RuleTreeExeReq();
					reqNext.setRule_id(EcsOrderConsts.WMS_NOTIFY_OPEN_ACCT_RULEID);
					TacheFact factNext = new TacheFact();
					factNext.setOrder_id(order_id);
					reqNext.setFact(factNext);
					TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(reqNext) {
						@Override
						public ZteResponse execute(ZteRequest zteRequest) {
							RuleTreeExeReq exereq = (RuleTreeExeReq) zteRequest;
							ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
							RuleTreeExeResp rsp = client.execute(exereq, RuleTreeExeResp.class);
							BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
							return busiResp;
						}
					});
					ThreadPoolFactory.getInfServerThreadPoolExector().execute(taskThreadPool);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			response.setResp_code("-999999");
			response.setResp_msg("接口处理异常");
		}
		response.setActiveNo(activeNo);
		return response;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "开户预校验接口", summary = "开户信息预提交")
	public PrecheckOpenAcctResponse precheckOpenAcctZB(PrecheckOpenAcctRequest request) throws ApiBusiException {
		PrecheckOpenAcctResponse rsp = new PrecheckOpenAcctResponse();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2Map(param, request);
			param.put("ord_id", request.getNotNeedReqStrOrderId());
			rsp = (PrecheckOpenAcctResponse) caller.invoke("periphery.businessnotify", param);
		} catch (Exception e) {
			rsp.getAccountRsp().setRespCode("-9999");
			rsp.getAccountRsp().setRespDesc("开户预校验" + e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "写卡信息查询接口", summary = "在支撑写卡业务前提下,由触发写卡端发起写卡申请,通过开放平台向总部CRM获取写卡信息,由于写卡存在多个环节,失败的时候需要进行重复读卡和重复写卡操作。")
	public QryCRMInfo2CardResponse qryCRMInfo2CardZB(QryCRMInfo2CardRequest request) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		QryCRMInfo2CardResponse rsp = new QryCRMInfo2CardResponse();
		try {
			BeanUtils.bean2Map(param, request);
			param.put("ord_id", request.getNotNeedReqStrOrderId());
			rsp = (QryCRMInfo2CardResponse) caller.invoke("periphery.carddataquery", param);
		} catch (Exception e) {
			rsp.setRespCode("-9999");
			rsp.setRespDesc(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单状态同步接口", summary = "触发写卡端将写卡结果通知总部CRM,以做管控处理")
	public NotifyWriteCardInfoResponse notifyWriteCardInfoZB(NotifyWriteCardInfoRequest request) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		NotifyWriteCardInfoResponse rsp = new NotifyWriteCardInfoResponse();
		try {
			BeanUtils.bean2Map(param, request);
			param.put("ord_id", request.getNotNeedReqStrOrderId());
			rsp = (NotifyWriteCardInfoResponse) caller.invoke("periphery.cardresnotify", param);
		} catch (Exception e) {
			rsp.setRespCode("-9999");
			rsp.setRespDesc(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "发货通知接口", summary = "总部将指定发货的物流公司以及相应的物流信息通知省份能力平台,进行发货通知处理。")
	public NotifyDeliveryResponse notifyDeliveryGD(NotifyDeliveryGDRequest request) throws ApiBusiException {
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		RuleTreeExeReq req = new RuleTreeExeReq();
		req.setRule_id(EcsOrderConsts.ZB_NOTIFY_DELIVERY_ACCT_GD_RULEID);
		AutoFact fact = new TacheFact();
		fact.setRequest(request);
		req.setFact(fact);
		RuleTreeExeResp resp = client.execute(req, RuleTreeExeResp.class);
		NotifyDeliveryResponse response = new NotifyDeliveryResponse();
		return response;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "发货通知接口", summary = "总部将指定发货的物流公司以及相应的物流信息通知省份能力平台,进行发货通知处理。")
	public NotifyDeliveryResponse notifyDeliveryZB(NotifyDeliveryZBRequest request) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		NotifyDeliveryResponse rsp = new NotifyDeliveryResponse();
		try {
			BeanUtils.bean2Map(param, request);
			param.put("ord_id", request.getNotNeedReqStrOrderId());
			rsp = (NotifyDeliveryResponse) caller.invoke("periphery.deliverynotify", param);// periphery.deliverynewnotify
		} catch (Exception e) {
			rsp.setRespCode("-9999");
			rsp.setRespDesc(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单异常通知", summary = "通知/接收异常状态,挂起或恢复订单生产")
	public NotifyOrderAbnormalToZBResponse notifyOrderAbnormalToZB(NotifyOrderAbnormalToZBRequest request) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		NotifyOrderAbnormalToZBResponse rsp = new NotifyOrderAbnormalToZBResponse();
		try {
			BeanUtils.bean2Map(param, request);
			param.put("ord_id", request.getNotNeedReqStrOrderId());
			rsp = (NotifyOrderAbnormalToZBResponse) caller.invoke("periphery.exceptionnotify", param);
		} catch (Exception e) {
			rsp.setRespCode("-9999");
			rsp.setRespDesc(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单异常通知", summary = "通知/接收异常状态,挂起或恢复订单生产")
	public NotifyOrderAbnormalToSystemResponse notifyOrderAbnormalToGD(NotifyOrderAbnormalToSystemRequest request) throws ApiBusiException {
		NotifyOrderAbnormalToSystemResponse response = new NotifyOrderAbnormalToSystemResponse();
		try {
			GetOrderByInfIdReq oreq = new GetOrderByInfIdReq();
			oreq.setZb_inf_id(request.getOrderId());
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
			GetOrderByInfIdResp oresp = client.execute(oreq, GetOrderByInfIdResp.class);
			String order_id = oresp.getOrder_id();

//			// 通知新商城异常
//			try {
//				HashMap para = new HashMap();
//				para.put("order_id", order_id);
//				para.put("error_msg", request.getExceptionDesc());
//				CommonDataFactory.getInstance().notifyBusiException(para);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}

			PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
			plan.setPlan_id(EcsOrderConsts.MARKED_EXCEPTION_PLAN);
			ExceptionFact excep = new ExceptionFact();
			excep.setOrder_id(order_id);
			excep.setException_from(EcsOrderConsts.EXCEPTION_FROM_ZB);
			excep.setException_desc(request.getExceptionDesc());
			plan.setFact(excep);
			String NoticeType = request.getNoticeType();// 异常类型
			if (EcsOrderConsts.Notice_Type_RestorationException.equals(NoticeType)) {// 恢复异常
				plan.setPlan_id(EcsOrderConsts.RESTORE_EXCEPTION_PLAN);
			} else {// 异常通知
				plan.setPlan_id(EcsOrderConsts.MARKED_EXCEPTION_PLAN);
			}
			// 业务组件正常返回即为成功,执行失败则抛出异常
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTreeByZBInfId(request.getOrderId());
			orderTree.setCol3(EcsOrderConsts.TRACE_TRIGGER_INF);
			orderTree.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderTree.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			CommonDataFactory.getInstance().exePlanRuleTree(plan);
			response.setRespCode(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
			response.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
			response.setRespDesc("通知成功");
		} catch (Exception e) {
			e.printStackTrace();
			response.setRespCode("-99999");
			response.setRespDesc("通知失败");
		}
		response.setActiveNo(request.getActiveNo());
		return response;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "换货通知接口", summary = "换货确认时,把换货串号,物流单号同步到省分能力平台")
	public ReplacementNoticeResponse replacementNoticeGD(ReplacementNoticeRequest request) throws ApiBusiException {
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		RuleTreeExeReq req = new RuleTreeExeReq();
		req.setRule_id(EcsOrderConsts.ZB_REPLACE_GD_RULEID);
		AutoFact fact = new TacheFact();
		fact.setRequest(request);
		req.setFact(fact);
		RuleTreeExeResp resp = client.execute(req, RuleTreeExeResp.class);
		ReplacementNoticeResponse response = new ReplacementNoticeResponse();
		return response;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "状态同步接口", summary = "将商品管理系统的品牌信息推送到商城等系统")
	public StateSynchronizationToSystemResponse stateSynchronizationToSystem(StateSynchronizationToSystemRequest request) throws ApiBusiException {
		StateSynchronizationToSystemResponse resp = new StateSynchronizationToSystemResponse();
		String stateTag = "";
		String zbOrderId = "";
		try {
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			RuleTreeExeReq req = new RuleTreeExeReq();
			List<zte.net.ecsord.params.zb.vo.Order> o = request.getOrders();
			String order_id = "";
			String orderStateTag = "";
			for (zte.net.ecsord.params.zb.vo.Order order : o) {
				// 去除同一订单重复的状态通知
				if (orderStateTag.contains(order.getOrderId().concat("@@").concat(order.getStateTag())))
					continue;
				orderStateTag = orderStateTag.concat("[").concat(order.getOrderId()).concat("@@").concat(order.getStateTag()).concat("]");

				zbOrderId = order.getOrderId();
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTreeByZBInfId(order.getOrderId());
				if (null != orderTree)
					order_id = orderTree.getOrder_id();

				stateTag = order.getStateTag();
				request.setNotNeedReqStrzb_status(stateTag);
				if (EcsOrderConsts.STATE_CHG_PHOTO.equals(stateTag)) {// 照片上传
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CERT_UPLOAD_STATUS }, new String[] { EcsOrderConsts.CERT_STATUS_UPLOADED });
					resp.setRespCode(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
					resp.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
					return resp;
				} else if (EcsOrderConsts.STATE_CHG_REPHOTO.equals(stateTag)) {// 照片重传
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.CERT_UPLOAD_STATUS }, new String[] { EcsOrderConsts.CERT_STATUS_REUPLOAD });
					resp.setRespCode(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
					resp.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
					return resp;
				} else if (EcsOrderConsts.STATE_CHG_RETURN.equals(stateTag)) {// 已退单[STATE_CHG_RETURN]（退单审核通过,并完成退单）
					req.setRule_id(EcsOrderConsts.ZB_RETURN_GD_RULEID);
				} else if (EcsOrderConsts.STATE_CHG_REJECT.equals(stateTag)) { // 退单申请驳回[STATE_CHG_REJECT]
					req.setCheckAllRelyOnRule(true);
					req.setCheckCurrRelyOnRule(true);
					req.setRule_id(EcsOrderConsts.ORDER_CANCELRETURNED_RULE);
					CommonDataFactory.getInstance().delRuleExeLog(EcsOrderConsts.ORDER_CANCELRETURNED_PLAN, null, order_id);
				} else if (EcsOrderConsts.STATE_CHG_RETURNMONEY.equals(stateTag)) { // 已退款
					req.setRule_id(EcsOrderConsts.ZB_RETURNMONEY_GD_RULEID);
				} else if (EcsOrderConsts.STATE_CHG_REJECTCANLPROD.equals(stateTag)) {// 退货申请驳回
					req.setRule_id(EcsOrderConsts.ZB_REJECTCANLPROD_GD_RULEID);
				} else if (EcsOrderConsts.STATE_CHG_REJECTCHANGEPROD.equals(stateTag)) {// 换货申请驳回
					req.setRule_id(EcsOrderConsts.ZB_REJECTCHANGEPROD_GD_RULEID);
				} else if (EcsOrderConsts.STATE_CHG_ACCT.equals(stateTag)) {// 签收
					req.setRule_id(EcsOrderConsts.ZB_ACCT_GD_RULEID);
				} else if (EcsOrderConsts.STATE_CHG_REPE.equals(stateTag)) {// 拒收重发货
					req.setRule_id(EcsOrderConsts.ZB_REPE_GD_RULEID);
				} else if (EcsOrderConsts.STATE_CHG_CANLODR.equals(stateTag)) {// 总部发起退单申请
					req.setRule_id(EcsOrderConsts.ORDER_RETURN_APPLY_RULE);
					// 记录订单的审核模式，手动
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ZB_REFUND_STATUS, AttrConsts.REFUND_AUDIT_MODE },
							new String[] { EcsOrderConsts.IS_DEFAULT_VALUE, EcsOrderConsts.REFUND_AUDIT_HAND });

					// 保存退单原因
					// CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					// new String[]{AttrConsts.REFUND_DESC},
					// new String[]{"退单申请原因：商城发起退单"});
				} else if (EcsOrderConsts.STATE_CHG_RETURN_CHECKFREE.equals(stateTag)) {// 总部发起
																						// 不需要审核的退单申请
					req.setPlan_id(EcsOrderConsts.ORDER_RETURNED_PLAN_AUTO);
					req.setRule_id(EcsOrderConsts.ORDER_RETURN_APPLY_RULE_AUTO);
					// 记录订单的审核模式，免审核
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ZB_REFUND_STATUS, AttrConsts.REFUND_AUDIT_MODE },
							new String[] { EcsOrderConsts.IS_DEFAULT_VALUE, EcsOrderConsts.REFUND_AUDIT_AUTO });

					// 保存退单原因
					// CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
					// new String[]{AttrConsts.REFUND_DESC},
					// new String[]{"退单申请原因：商城发起退单"});
				} else if (EcsOrderConsts.STATE_CHG_ACTIVESUCC.equals(stateTag)) {// 激活成功
					req.setPlan_id(EcsOrderConsts.ORDER_ARCHIVE_PLAN);
					req.setRule_id(EcsOrderConsts.DT_ORDER_BACK_RULE_ID);
				}

				// 审核不通过的、激活失败的
				if (EcsOrderConsts.STATE_CHG_ACTIVEFAIL.equals(stateTag) || EcsOrderConsts.STATE_CHG_ACTIVENOTPASS.equals(stateTag)) {
					// 不做任何处理
				} else {
					TacheFact fact = new TacheFact();
					fact.setOrder_id(order_id);
					fact.setRequest(request);
					req.setFact(fact);
					req.setCheckAllRelyOnRule(true);
					req.setCheckCurrRelyOnRule(true);
					// 业务组件不成功则抛出异常
					orderTree.setCol3(EcsOrderConsts.TRACE_TRIGGER_INF);// 加一个标示表示从区分从接口,页面来源
					orderTree.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderTree.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					client.execute(req, RuleTreeExeResp.class);
				}

			}
			resp.setRespCode(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
			resp.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
		} catch (Exception e) {
			e.printStackTrace();
			if (EcsOrderConsts.STATE_CHG_ACCT.equals(stateTag) || EcsOrderConsts.STATE_CHG_RETURN.equals(stateTag)) {
				resp.setRespCode(EcsOrderConsts.ZB_INF_RESP_CODE_0000);

			} else {
				resp.setRespCode("9999");
			}
			e.printStackTrace();
		} finally {
			try {
				if (EcsOrderConsts.STATE_CHG_ACCT.equals(stateTag) || EcsOrderConsts.STATE_CHG_RETURN.equals(stateTag)) {
					// 在模拟器界面上做订单退单是,先要在es_order_batch_archive造一条数据;其他正常退单忽略这段代码
					// 更新[es_order_batch_archive.zb_return]状态
					ChargebackStatuChgReq chargebackStatuChgReq = new ChargebackStatuChgReq();
					chargebackStatuChgReq.setFlag("zb_return");
					chargebackStatuChgReq.setZb_order_id(zbOrderId);
					ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
					client.execute(chargebackStatuChgReq, ChargebackStatuChgResp.class);
				}
			} catch (Exception stateChg) {

			}
		}
		resp.setActiveNo(request.getActiveNo());
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "状态同步接口", summary = "订单管理系统将订单的状态信息推送到总部系统")
	public StateSynToZBResponse stateSynToZB(StateSynToZBRequest request) throws ApiBusiException {
		request.setDb_action("");
		CommCaller caller = new CommCaller();
		StateSynToZBResponse rsp = new StateSynToZBResponse();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, request);
			param.put("ord_id", request.getNotNeedReqStrOrderId());
			rsp = (StateSynToZBResponse) caller.invoke(EcsOrderConsts.ZB_INF_ORDERSTATENOTIFY, param);// 广东联通
																										// periphery.orderStateChange
		} catch (Exception e) {
			rsp.setRespCode("-99999");
			rsp.setRespDesc(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J21 订单信息变更", summary = "订单管理系统在订单状态变更的时候将信息同步到沃云购")
	public NotifyOrderInfoWYGResponse notifyOrderInfoWYG(NotifyOrderInfoWYGRequset req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		NotifyOrderInfoWYGResponse rsp = new NotifyOrderInfoWYGResponse();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			param.put("ep_mall", CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.ORDER_FROM));
			rsp = (NotifyOrderInfoWYGResponse) caller.invoke("WYG.notifyOrderInfoWYG", param);
		} catch (Exception e) {
			rsp.setResp_code("-9999");
			rsp.setResp_msg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J22退单申请【状态通知接口】", summary = "沃云购将订单状态同步到订单管理系统")
	public ChargebackApplyWYGResponse chargebackApplyWYG(ChargebackApplyWYGRequset req) throws ApiBusiException {

		ChargebackApplyWYGResponse chargebackApplyWYGResp = new ChargebackApplyWYGResponse();
		String out_id = req.getOut_order_id();// 外部单号
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
		GetOrderByOutTidReq getOrderByOutTidReq = new GetOrderByOutTidReq();
		getOrderByOutTidReq.setOut_tid(out_id);
		GetOrderByOutTidResp resp = client.execute(getOrderByOutTidReq, GetOrderByOutTidResp.class);
		String order_id = resp.getOrder_id();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

		// 是否主动触发调用指定规则
		Boolean isAutoPlan = false;
		// 退单状态
		String refund_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFUND_STATUS);
		// 只有退单确认状态才可以接收退款通知
		if (EcsOrderConsts.REFUND_STATUS_01.equals(refund_status) || EcsOrderConsts.REFUND_STATUS_02.equals(refund_status) || EcsOrderConsts.REFUND_STATUS_03.equals(refund_status)) {
			isAutoPlan = true;
		}
		logger.info("退款状态跟踪====R12====order_id" + order_id + ",isAutoPlan：" + isAutoPlan + ",refund_status：" + refund_status);

		if (null == orderTree) {
			chargebackApplyWYGResp.setRespCode(EcsOrderConsts.WYG_INF_FAIL_CODE);
			chargebackApplyWYGResp.setRespMsg("订单不存在：[" + out_id + "]");

		} else {
			if (EcsOrderConsts.NEW_SHOP_ORDER_STATE_11.equals(req.getTrace_code())) {// T-退单申请
				OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
				if (EcsOrderConsts.REFUND_DEAL_TYPE_02.equals(orderExtBusiRequest.getRefund_deal_type())) {
					chargebackApplyWYGResp.setRespCode(EcsOrderConsts.WYG_INF_SUCC_CODE);// 参考老系统，该情况也算是成功
					chargebackApplyWYGResp.setRespMsg("此单已申请退单，不能重复申请退单：[" + out_id + "]");
				} else {
					// 标记为商城主动发起的退单
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ZB_REFUND_STATUS }, new String[] { EcsOrderConsts.IS_DEFAULT_VALUE });

					// 调用订单退单申请规则
					// TacheFact fact = new TacheFact();
					// fact.setOrder_id(order_id);
					// RuleTreeExeReq ruleTreeExeReq = new RuleTreeExeReq();
					// ruleTreeExeReq.setDeal_from(EcsOrderConsts.DEAL_FROM_INF);
					// ruleTreeExeReq.setCheckCurrRelyOnRule(true);
					// ruleTreeExeReq.setCheckAllRelyOnRule(true);
					// String shippingType =
					// orderTree.getOrderBusiRequest().getShipping_type();
					// String is_aop =
					// orderTree.getOrderExtBusiRequest().getIs_aop();
					// ruleTreeExeReq.setPlan_id(EcsOrderConsts.ORDER_RETURNED_PLAN);
					// ruleTreeExeReq.setRule_id(EcsOrderConsts.ORDER_RETURN_APPLY_RULE);
					// ruleTreeExeReq.setFact(fact);
					// RuleTreeExeResp presp =
					// CommonDataFactory.getInstance().exeRuleTree(ruleTreeExeReq);//

					// 获取审核模式，并保存
					String refundMode = AttrUtils.getInstance().getRefundModeByRule(order_id);
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.REFUND_AUDIT_MODE }, new String[] { refundMode });

					// 获取方案信息
					Map m = AttrUtils.getInstance().getRefundPlanInfo(order_id);
					String plan_mode = m.get("plan_id").toString();
					String rule_mode = m.get("app_rule_id").toString();

					// 删除规则日志
					RuleExeLogDelReq delLogReq = new RuleExeLogDelReq();
					delLogReq.setObj_id(order_id);
					delLogReq.setPlan_id(new String[] { plan_mode });
					client.execute(delLogReq, RuleExeLogDelResp.class);

					// 启动方案
					TacheFact fact = new TacheFact();
					fact.setOrder_id(order_id);
					RuleTreeExeReq ruleTreeExeReq = new RuleTreeExeReq();
					ruleTreeExeReq.setFact(fact);
					ruleTreeExeReq.setPlan_id(plan_mode);
					ruleTreeExeReq.setRule_id(rule_mode);
					ruleTreeExeReq.setDeal_from(EcsOrderConsts.DEAL_FROM_INF);
					ruleTreeExeReq.setCheckCurrRelyOnRule(true);
					ruleTreeExeReq.setCheckAllRelyOnRule(true);
					RuleTreeExeResp ruleTreeExeResp = CommonDataFactory.getInstance().exeRuleTree(ruleTreeExeReq);
					if (ruleTreeExeResp != null && "0".equals(ruleTreeExeResp.getError_code())) {
						chargebackApplyWYGResp.setRespCode(EcsOrderConsts.WYG_INF_SUCC_CODE);
						chargebackApplyWYGResp.setRespMsg("订单退单申请成功：[" + out_id + "]！");
					} else {
						chargebackApplyWYGResp.setRespCode(EcsOrderConsts.WYG_INF_FAIL_CODE);
						chargebackApplyWYGResp.setRespMsg("订单退单申请失败:[" + out_id + "]" + chargebackApplyWYGResp.getError_msg());
					}
				}
			} else if (EcsOrderConsts.DIC_ORDER_NODE_J.equals(req.getTrace_code())) {// J-回单
				// 记录签收状态：1.采用属性更新,则补寄、重发的记录也会被记录签收;2.采用订单树更新,则属性更新不到,后续根据属性判断时会误判
				// 因为签收状态不是属性，所以采用更新订单树的方式，而上的第2点疑虑也没有了

				List<OrderDeliveryBusiRequest> orderDeliveryBusiRequest = orderTree.getOrderDeliveryBusiRequests();
				OrderDeliveryBusiRequest deliverBusiReq = null;
				if (orderDeliveryBusiRequest != null && orderDeliveryBusiRequest.size() > 0) {// 取正常发货记录
					for (OrderDeliveryBusiRequest deli : orderDeliveryBusiRequest) {
						if (EcsOrderConsts.LOGIS_NORMAL.equals(deli.getDelivery_type())) {
							deliverBusiReq = deli;
							break;
						}
					}
				}
				if (null != deliverBusiReq) {
					deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					deliverBusiReq.setSign_status(EcsOrderConsts.SIGN_STATUS_1);// 运单设为已签收
					deliverBusiReq.store(); // 属性数据入库
				}

				// 如果符合条件,则自动回单
				String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();// 订单环节
				String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);// 订单来源
				String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);// 商品类型
				String order_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_TYPE);// 订单类型
				if (EcsOrderConsts.DIC_ORDER_NODE_J.equals(flow_trace_id) && EcsOrderConsts.ORDER_FROM_10053.equals(order_from) && EcsOrderConsts.GOODS_TYPE_PHONE.equals(goods_type)
						&& EcsOrderConsts.ORDER_TYPE_09.equals(order_type)) {// 回单环节,B2B商城,裸机,订单类型B2B
					// 自动回单,执行方案与规则
					String is_aop = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_AOP);// 是否aop
					// String plan_id = "";
					String rule_id = "";
					// 区分方案与规则
					if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(is_aop)) {// AOP
						// plan_id = EcsOrderConsts.FLOW_TRACE_J_PLAN_ID_AOP;
						rule_id = EcsOrderConsts.AOP_ACCT_GD_RULEID;
					} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
						rule_id = EcsOrderConsts.BSSKL_ACCT_GD_RULEID;
					} else {// 总商
							// plan_id =
							// EcsOrderConsts.FLOW_TRACE_J_PLAN_ID_ZONGSHANG;
						rule_id = EcsOrderConsts.ZB_ACCT_GD_RULEID;
					}

					RuleTreeExeReq ruleReq = new RuleTreeExeReq();
					ruleReq.setRule_id(rule_id);
					ruleReq.setReCurrRule(true);// 这个需要调试
					// ruleReq.setExePeerAfRules(false); //
					// ruleReq.setExeParentsPeerAfRules(false); //
					TacheFact fact = new TacheFact();
					// fact.setRequest(request);
					fact.setOrder_id(order_id);
					ruleReq.setFact(fact);
					RuleTreeExeResp busiResp = client.execute(ruleReq, RuleTreeExeResp.class);
				}

				// 返回成功编码,不管自动回单是否成功
				chargebackApplyWYGResp.setRespCode(EcsOrderConsts.WYG_INF_SUCC_CODE);
				chargebackApplyWYGResp.setRespMsg("订单回单同步成功：[" + out_id + "]！");
			}
			/**
			 * ZX add 2016年3月30日 13:24:08 start
			 */
			else if (EcsOrderConsts.DIC_ORDER_NODE_R.equals(req.getTrace_code()) || EcsOrderConsts.DIC_ORDER_NODE_S.equals(req.getTrace_code())) {
				Map m = AttrUtils.getInstance().getRefundPlanInfo(order_id);
				String plan_mode = m.get("plan_id").toString();
				String rule_mode = m.get("confirm_rule_id").toString();
				if (EcsOrderConsts.DIC_ORDER_NODE_R.equals(req.getTrace_code())) { // 线上
					if (req.getRefund_state().equals(EcsOrderConsts.MALL_REFUND_STATE_SUCC)) {
						logger.info("退款状态跟踪====R5====order_id：" + order_id + ",更新前：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));

						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS }, new String[] { EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_SUCC });// 更新退款状态

						logger.info("退款状态跟踪====R5====order_id：" + order_id + ",更新后：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));
						if (!EcsOrderConsts.IS_DEFAULT_VALUE.equals(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFUND_RULE_EXECUTE))) {
							isAutoPlan = false;
						}
						if (isAutoPlan) {
							TacheFact fact = new TacheFact();
							fact.setOrder_id(order_id);
							RuleTreeExeReq ruleTreeExeReq = new RuleTreeExeReq();
							ruleTreeExeReq.setFact(fact);
							ruleTreeExeReq.setPlan_id(plan_mode);
							ruleTreeExeReq.setRule_id(rule_mode);
							ruleTreeExeReq.setDeal_from(EcsOrderConsts.DEAL_FROM_INF);
							RuleTreeExeResp ruleTreeExeResp = CommonDataFactory.getInstance().exeRuleTree(ruleTreeExeReq);
						}
					} else {
						logger.info("退款状态跟踪====R7====order_id：" + order_id + ",更新前：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));

						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS }, new String[] { EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_FAIL });
						logger.info("退款状态跟踪====R7====order_id：" + order_id + ",更新后：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));

					}
				} else if (EcsOrderConsts.DIC_ORDER_NODE_S.equals(req.getTrace_code())) { // 线下
					if (req.getRefund_state().equals(EcsOrderConsts.MALL_REFUND_STATE_SUCC)) {
						logger.info("退款状态跟踪====R6====order_id：" + order_id + ",更新前：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));

						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS }, new String[] { EcsOrderConsts.BSS_REFUND_STATUS_OFFLINE_SUCC });// 更新退款状态

						logger.info("退款状态跟踪====R6====order_id：" + order_id + ",更新后：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));

						if (!EcsOrderConsts.IS_DEFAULT_VALUE.equals(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFUND_RULE_EXECUTE))) {
							isAutoPlan = false;
						}
						if (isAutoPlan) {
							TacheFact fact = new TacheFact();
							fact.setOrder_id(order_id);
							RuleTreeExeReq ruleTreeExeReq = new RuleTreeExeReq();
							ruleTreeExeReq.setFact(fact);
							ruleTreeExeReq.setPlan_id(plan_mode);
							ruleTreeExeReq.setRule_id(rule_mode);
							ruleTreeExeReq.setDeal_from(EcsOrderConsts.DEAL_FROM_INF);
							RuleTreeExeResp ruleTreeExeResp = CommonDataFactory.getInstance().exeRuleTree(ruleTreeExeReq);
						}
					} else {
						logger.info("退款状态跟踪====R8====order_id：" + order_id + ",更新前：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));

						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS }, new String[] { EcsOrderConsts.BSS_REFUND_STATUS_OFFLINE_FAIL });
						logger.info("退款状态跟踪====R8====order_id：" + order_id + ",更新后：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));

					}
				}
				// 退款失败记录轨迹信息
				if (!StringUtils.equals(EcsOrderConsts.MALL_REFUND_STATE_SUCC, req.getRefund_state())) {
					orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
					OrderHandleLogsAddReq handleReq = new OrderHandleLogsAddReq();
					String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
					String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
					OrderHandleLogs logReq = new OrderHandleLogs();
					logReq.setOrder_id(order_id);
					logReq.setFlow_id(flow_id);
					logReq.setFlow_trace_id(flowTraceId);
					logReq.setComments("退款失败原因：" + req.getDeal_desc());
					logReq.setOp_id("1");
					logReq.setOp_name("admin");
					logReq.setHandler_type(EcsOrderConsts.ORDER_HANDLER_TYPE_RETURNED);
					logReq.setType_code(EcsOrderConsts.REFUND_STATUS_09);
					handleReq.setOrderHandleLogs(logReq);
					client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
					OrderHandleLogsAddResp handleResp = client.execute(handleReq, OrderHandleLogsAddResp.class);
				}
				chargebackApplyWYGResp.setRespCode(EcsOrderConsts.WYG_INF_SUCC_CODE);
				chargebackApplyWYGResp.setRespMsg("接收商城线上退款状态成功,订单：[" + out_id + "]");
			}
			/**
			 * ZX add 2016年3月30日 13:24:27 end
			 */
			else {
				chargebackApplyWYGResp.setRespCode(EcsOrderConsts.WYG_INF_FAIL_CODE);
				chargebackApplyWYGResp.setRespMsg("同步失败,订单状态不匹配：[" + out_id + "]");
			}

		}
		return chargebackApplyWYGResp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J23一键开户", summary = "订单管理系统提供提供沃云购一键开户")
	public OrderAKeyActResp orderAKeyActWYG(OrderAKeyActReq req) throws ApiBusiException {
		ZteResponse response = new ZteResponse();
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
		GetOrderByOutTidReq getOrderByOutTidReq = new GetOrderByOutTidReq();
		getOrderByOutTidReq.setOut_tid(req.getOrder_id());
		GetOrderByOutTidResp resp = client.execute(getOrderByOutTidReq, GetOrderByOutTidResp.class);
		String order_id = resp.getOrder_id();
		logger.info("=========================orderAKeyActWYG订单编号：" + order_id);
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if (null == orderTree) {
			OrderAKeyActResp orderAKeyActResp = new OrderAKeyActResp();
			orderAKeyActResp.setResp_code("-1");
			orderAKeyActResp.setResp_msg(order_id + "订单不存在!");
			return orderAKeyActResp;
		}
		String out_id = orderTree.getOrderExtBusiRequest().getOut_tid();
		HashMap param = new HashMap<Object, Object>();
		param.put("source_from", ManagerUtils.getSourceFrom());
		param.put("order_id", order_id);
		param.put("op_code", "orderAKeyActWYG");
		param.put("out_tid", out_id);
		param.put("zb_inf_id", CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ZB_INF_ID));
		BeanUtils.ordBindEvnLog(param);
		String is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
		// ....配合新商城模拟订单异常情况测试代码开始....
		/*
		 * WYGTestRequest wygTestRequest = new WYGTestRequest(); String
		 * outOrderId =
		 * CommonDataFactory.getInstance().getAttrFieldValue(order_id,
		 * AttrConsts.OUT_TID); wygTestRequest.setOutOrderId(outOrderId);
		 * wygTestRequest.setTraceCode(EcsOrderConsts.DIC_ORDER_NODE_O);
		 * wygTestRequest.setOrderId(order_id); RuleTreeExeReq reqReq = new
		 * RuleTreeExeReq(); TacheFact factTest = new TacheFact();
		 * factTest.setOrder_id(order_id); factTest.setRequest(wygTestRequest);
		 * reqReq.setRule_id(EcsOrderConsts.WYG_SIMULATE_EXCEPTION_RULEID);
		 * reqReq.setFact(factTest);
		 * CommonDataFactory.getInstance().exeRuleTree(reqReq); //删除规则日志
		 * RuleExeLogDelReq delLogReq = new RuleExeLogDelReq();
		 * delLogReq.setObj_id(order_id);
		 * delLogReq.setRule_id(EcsOrderConsts.WYG_SIMULATE_EXCEPTION_RULEID);
		 * client.execute(delLogReq, RuleExeLogDelResp.class);
		 * 
		 * OrderTreeBusiRequest orderTreeTest =
		 * CommonDataFactory.getInstance().getOrderTree(order_id); final String
		 * returnObj = orderTreeTest.getCol7();
		 * if(!StringUtil.isEmpty(returnObj) && !"null".equals(returnObj)){
		 * WYGTestResponse wygTestResponse = CommonTools.jsonToBean(returnObj,
		 * WYGTestResponse.class); String analogReturnMsg =
		 * wygTestResponse.getReturnMsg(); //如果报文不为null则发送模拟报文 环节编码 if(null !=
		 * analogReturnMsg && !StringUtil.isEmpty(analogReturnMsg)){
		 * analogReturnMsg = analogReturnMsg.replaceAll("WCSV999999999",
		 * outOrderId); final String traceCode = wygTestResponse.getTraceCode();
		 * StatuSynchReq statuSyn = new
		 * StatuSynchReq(order_id,traceCode,String.valueOf(EcsOrderConsts.
		 * DIC_ORDER_NODE_DESC_MAP.get(traceCode)),EcsOrderConsts.
		 * ACCOUNT_STATUS_0,analogReturnMsg,"");
		 * CommonDataFactory.getInstance().notifyNewShop(statuSyn);
		 * response.setError_code(ConstsCore.ERROR_FAIL);
		 * response.setError_msg(analogReturnMsg); OrderAKeyActResp
		 * orderAKeyActResp = new OrderAKeyActResp();
		 * orderAKeyActResp.setResp_code(response.getError_code());
		 * orderAKeyActResp.setResp_msg(response.getError_msg());
		 * CommonDataFactory.getInstance().notifyNewShop(statuSyn);
		 * 
		 * OrderTreeBusiRequest orderTreeTestUpdate = new
		 * OrderTreeBusiRequest(); orderTreeTestUpdate.setOrder_id(order_id);
		 * orderTreeTestUpdate.setCol7(null);
		 * orderTreeTestUpdate.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		 * orderTreeTestUpdate.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		 * orderTreeTestUpdate.store();
		 * 
		 * return orderAKeyActResp; } }
		 */
		// ....配合新商城模拟订单异常情况测试代码结束....

		orderTree.setCol3(EcsOrderConsts.TRACE_TRIGGER_INF);// 加一个标示表示从区分从接口,页面来源
		orderTree.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderTree.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		String imei = req.getImei();
		String iccid = req.getIccid();
		String old_iccid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
		String action_code = req.getAction_code();
		boolean only = true;// 控制此接口每次调用只走一种场景
		// 场景:新商城通知订单系统“写卡完成”并且iccid与订单树上的一致,即可断定异常出现在订单系统通知总部写卡完成这一节点,可重新通知总部；

		if (EcsOrderConsts.ACTION_CODE_XKWC.equals(action_code) && old_iccid.equals(iccid)) {

			// add by wui写卡完成重复处理流程,先删除写卡方案、写卡结果通知总部规则
			RuleExeLogDelReq reqr = new RuleExeLogDelReq();
			reqr.setObj_id(order_id);
			reqr.setPlan_id(new String[] { EcsOrderConsts.RULE_PLAN_ID_103 });
			if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
				reqr.setRule_id(EcsOrderConsts.WRITE_CARD_INFO_TO_ZB_03);
			} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
				reqr.setRule_id(EcsOrderConsts.WRITE_CARD_INFO_TO_ZB_03_BSSKL);
			} else {
				reqr.setRule_id(EcsOrderConsts.WRITE_CARD_INFO_TO_ZB_03_AOP);
			}
			String source_from = ManagerUtils.getSourceFrom();
			ZteClient clientd = ClientFactory.getZteDubboClient(source_from);
			RuleExeLogDelResp resplog = clientd.execute(reqr, RuleExeLogDelResp.class);

			//
			only = false;
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.WRITE_CARD_RESULT }, new String[] { EcsOrderConsts.WRITE_CARD_SUCESS_TO_ZB });
			RuleTreeExeReq rteReq = new RuleTreeExeReq();
			rteReq.setCheckAllRelyOnRule(true);
			rteReq.setCheckCurrRelyOnRule(true);
			if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
				rteReq.setRule_id(EcsOrderConsts.WRITE_CARD_INFO_TO_ZB_03);
			} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
				rteReq.setRule_id(EcsOrderConsts.WRITE_CARD_INFO_TO_ZB_03_BSSKL);
			} else {
				rteReq.setRule_id(EcsOrderConsts.WRITE_CARD_INFO_TO_ZB_03_AOP);
			}
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			rteReq.setFact(fact);
			RuleTreeExeResp rteResp = CommonDataFactory.getInstance().exeRuleTree(rteReq);
			BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rteResp);
			if (ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
				response.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
				response.setError_msg("写卡完成通知总部成功[" + out_id + "]！");
			} else {
				response.setError_code(ConstsCore.ERROR_FAIL);
				response.setError_msg("写卡完成通知总部失败:[" + out_id + "]" + busiResp.getError_msg());
			}
		}
		// 场景:一键开户[订单退单]：调用订单退单申请
		if (EcsOrderConsts.ACTION_CODE_DDTD.equals(action_code)) {
			BusiCompResponse busiResp = null;
			only = false;
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			RuleTreeExeReq ruleTreeExeReq = new RuleTreeExeReq();
			ruleTreeExeReq.setCheckCurrRelyOnRule(true);
			ruleTreeExeReq.setCheckAllRelyOnRule(true);
			ruleTreeExeReq.setFact(fact);
			String shippingType = orderTree.getOrderBusiRequest().getShipping_type();

			// 标记为商城主动发起的退单
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ZB_REFUND_STATUS }, new String[] { EcsOrderConsts.IS_DEFAULT_VALUE });

			// 获取审核模式，并保存
			String refundMode = AttrUtils.getInstance().getRefundModeByRule(order_id);
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.REFUND_AUDIT_MODE }, new String[] { refundMode });

			// 获取方案信息
			Map m = AttrUtils.getInstance().getRefundPlanInfo(order_id);
			String plan_id = m.get("plan_id").toString();
			String rule_id = m.get("app_rule_id").toString();
			// AOP+现场交付订单退单自动完成---zengxianlian
			if (StringUtils.equals(refundMode, EcsOrderConsts.REFUND_AUDIT_HAND)) {
				ruleTreeExeReq.setPlan_id(plan_id);
				ruleTreeExeReq.setRule_id(rule_id);
				RuleTreeExeResp presp = CommonDataFactory.getInstance().exeRuleTree(ruleTreeExeReq);
				busiResp = CommonDataFactory.getInstance().getRuleTreeresult(presp);
			} else {
				ruleTreeExeReq.setPlan_id(plan_id);
				ruleTreeExeReq.setRule_id(rule_id);

				busiResp = new BusiCompResponse();
				// 开启异步线程执行后续规则
				try {
					TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(ruleTreeExeReq) {
						@Override
						public ZteResponse execute(ZteRequest zteRequest) {
							RuleTreeExeReq exereq = (RuleTreeExeReq) zteRequest;
							RuleTreeExeResp rsp = CommonDataFactory.getInstance().exeRuleTree(exereq);
							BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
							return busiResp;
						}
					});
					ThreadPoolFactory.getInfServerThreadPoolExector().execute(taskThreadPool);
					busiResp.setError_code(ConstsCore.ERROR_SUCC);
					busiResp.setError_msg("订单退单申请处理成功!");
				} catch (Exception e) {
					e.printStackTrace();
					busiResp.setError_code(ConstsCore.ERROR_SUCC);
					busiResp.setError_msg(e.getMessage());
				}
			}

			if (ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
				response.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
				response.setError_msg("订单退单申请成功[" + out_id + "]！");
			} else {
				response.setError_code(ConstsCore.ERROR_FAIL);
				response.setError_msg("订单退单申请失败:[" + out_id + "]" + busiResp.getError_msg());
			}
		}
		// 场景：一键开户[线下完成]：场景7_线下完成(小莫):a) 异常恢复业务组件；b)
		// 订单直接跳转质检稽核,待总部回单确认成功通知,则流程流转完回单环节,进入归档环节,订单归档；
		if (EcsOrderConsts.ACTION_CODE_XXWC.equals(action_code)) {
			only = false;

			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			RuleTreeExeReq ruleTreeExeReq = new RuleTreeExeReq();
			ruleTreeExeReq.setCheckCurrRelyOnRule(true);
			ruleTreeExeReq.setCheckAllRelyOnRule(true);
			ruleTreeExeReq.setPlan_id(EcsOrderConsts.ORDER_RETURNED_PLAN);
			ruleTreeExeReq.setRule_id(EcsOrderConsts.ORDER_RETURN_APPLY_RULE);
			ruleTreeExeReq.setFact(fact);
			RuleTreeExeResp presp = CommonDataFactory.getInstance().exeRuleTree(ruleTreeExeReq);
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.SUB_REFUND_DEAL_TYPE }, new String[] { EcsOrderConsts.NEW_SHOP_OFFLINE_DEAL });

			BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(presp);
			if (ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
				response.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
				response.setError_msg("线下完成申请成功[" + out_id + "]！");
			} else {
				response.setError_code(ConstsCore.ERROR_FAIL);
				response.setError_msg("线下完成申请失败:[" + out_id + "]" + busiResp.getError_msg());
			}
		}
		// 场景：一键开户[写卡异常]：写卡失败标记异常
		if (EcsOrderConsts.ACTION_CODE_XKYC.equals(action_code)) {
			only = false;
			BusiCompRequest bcr = new BusiCompRequest();
			bcr.setEnginePath("zteCommonTraceRule.markedException");
			bcr.setOrder_id(order_id);
			Map map = new HashMap();
			map.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			map.put(EcsOrderConsts.EXCEPTION_TYPE, EcsOrderConsts.ABNORMAL_TYPE_OPEN);
			map.put(EcsOrderConsts.EXCEPTION_REMARK, "一键开户标记异常");
			bcr.setQueryParams(map);
			BusiCompResponse busiReponse = CommonDataFactory.getInstance().execBusiComp(bcr);
			if (ConstsCore.ERROR_SUCC.equals(busiReponse.getError_code())) {
				response.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
				response.setError_msg("一键开户标记异常操作成功[" + out_id + "]！");
			} else {
				response.setError_code(ConstsCore.ERROR_FAIL);
				response.setError_msg("一键开户标记异常操作失败:[" + out_id + "]" + busiReponse.getError_msg());
			}
		}
		// 场景：一键开户[写卡异常]：发起重新写卡,删除所有日志
		if (EcsOrderConsts.ACTION_CODE_CXXK.equals(action_code)) {
			only = false;
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ICCID }, new String[] { iccid });

			String trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
			if (!EcsOrderConsts.DIC_ORDER_NODE_X.equals(trace_id)) {
				response.setError_code(ConstsCore.ERROR_FAIL);
				response.setError_msg("重新写卡失败:订单环节非写卡环节");
				OrderAKeyActResp orderAKeyActResp = new OrderAKeyActResp();
				orderAKeyActResp.setResp_code(response.getError_code());
				orderAKeyActResp.setResp_msg(response.getError_msg());
				return orderAKeyActResp;
			}

			// 当前订单为写卡环节,发起重新写卡操作流程
			PlanRuleTreeExeReq planTreeExeReq = new PlanRuleTreeExeReq();
			planTreeExeReq.setPlan_id(EcsOrderConsts.RULE_PLAN_ID_103);
			planTreeExeReq.setDeleteLogs(true);
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			planTreeExeReq.setFact(fact);
			PlanRuleTreeExeResp rsp = CommonDataFactory.getInstance().exePlanRuleTree(planTreeExeReq);
			BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
			if (ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
				response.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
				response.setError_msg("重新写卡成功[" + out_id + "]！");
			} else {
				response.setError_code(ConstsCore.ERROR_FAIL);
				response.setError_msg("重新写卡失败:[" + out_id + "]" + busiResp.getError_msg());
			}
		}

		// 场景：一键开户 订单信息变更处理
		if (EcsOrderConsts.ACTION_CODE_XXBG.equals(action_code)) {
			// 1、change_fields数据转换，非空值入库，保存
			/*
			 * 2、调用沃云购2.0预校验方案 3、成功，返回变更成功，可以进行一键开户
			 * 
			 */
			only = false;
			String changeFields = req.getChange_fields();
			// [{"field_name":'phone_num',"field_desc":"开户号码",field_value:""},{"field_name":'development_name',"field_desc":"发展人名称",field_value:""},{"field_name":'development_code',"field_desc":"发展人编码",field_value:""},{"field_name":'bss_operator',"field_desc":"BSS工号",field_value:""},{"field_name":'oss_operator',"field_desc":"订单支撑系统工号",field_value:""},{"field_name":'bss_operator_name',"field_desc":"BSS工号名称",field_value:""},{"field_name":'phone_num',"field_desc":"开户号码",field_value:""},{"field_name":'imei',"field_desc":"串号",field_value:""}]
			if (null != changeFields && !"".equals(changeFields)) {
				List<Map<String, String>> changeFieldsList = CommonTools.jsonToList(changeFields, HashMap.class);
				if (changeFieldsList.size() > 0) {
					String[] fieldNames = new String[changeFieldsList.size()];
					String[] fieldValues = new String[changeFieldsList.size()];
					for (int i = 0; i < changeFieldsList.size(); i++) {
						Map<String, String> field = changeFieldsList.get(i);
						if (field.get("field_name").equals("imei")) {
							fieldNames[i] = AttrConsts.TERMINAL_NUM;
						} else {
							fieldNames[i] = field.get("field_name");
						}
						fieldValues[i] = field.get("field_value");
					}
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, fieldNames, fieldValues);

					// add by wui订单锁单人,重置锁单人
					String oss_operator = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "oss_operator"); // 变更工号
					String lock_user_id = CommonDataFactory.getInstance().getOrderLockUserId(order_id); // 锁单人
					if (!StringUtils.isEmpty(oss_operator) && !oss_operator.equals(lock_user_id)) { // add
																									// by
																									// wui不为空，且当前锁单人与传入锁单人不一致则设置订单锁定人
						OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
						/*
						 * orderExtBusiRequest.setLock_status(EcsOrderConsts.
						 * LOCK_STATUS); try {
						 * orderExtBusiRequest.setLock_time(DateUtil.getTime2())
						 * ; } catch (FrameException e) { e.printStackTrace(); }
						 */
						String trace_id = orderExtBusiRequest.getFlow_trace_id();
						List<OrderLockBusiRequest> orderLockBusiRequestList = orderTree.getOrderLockBusiRequests();
						for (int i = 0; i < orderLockBusiRequestList.size(); i++) {
							OrderLockBusiRequest orderLockBusiRequest = orderLockBusiRequestList.get(i);
							if (trace_id.equals(orderLockBusiRequest.getTache_code())) {
								orderLockBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
								try {
									orderLockBusiRequest.setLock_time(DateUtil.getTime2());
								} catch (FrameException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								orderLockBusiRequest.setLock_user_id(oss_operator);
								orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
								orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
								orderLockBusiRequest.store();
							}
						}

						/*
						 * orderExtBusiRequest.setLock_user_id(oss_operator);
						 * orderExtBusiRequest.setIs_dirty(ConstsCore.
						 * IS_DIRTY_YES);
						 * orderExtBusiRequest.setDb_action(ConstsCore.
						 * DB_ACTION_UPDATE); orderExtBusiRequest.store();
						 */
					}

					PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
					plan.setPlan_id(EcsOrderConsts.WORDER__PRE_DEAL_PLAN);
					TacheFact fact = new TacheFact();
					fact.setOrder_id(order_id);
					plan.setFact(fact);
					plan.setDeleteLogs(true);
					PlanRuleTreeExeResp preResp = CommonDataFactory.getInstance().exePlanRuleTree(plan);
					BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(preResp);
					if (ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
						response.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
						response.setError_msg("订单信息变更成功[" + out_id + "]！");
						StatuSynchReq statuSyn = new StatuSynchReq(order_id, EcsOrderConsts.DIC_ORDER_NODE_O, "信息变更成功", EcsOrderConsts.ORDER_STANDARDING_1, "信息变更成功", "");
						CommonDataFactory.getInstance().notifyNewShop(statuSyn);
					} else {
						response.setError_code(ConstsCore.ERROR_FAIL);
						response.setError_msg("订单信息变更失败:[" + out_id + "]" + preResp.getError_msg());
					}
				}

			}
		}

		// 一键开户异常，转物流生产（新商城发起）
		if (EcsOrderConsts.ACTION_CODE_WLSC.equals(action_code)) {
			only = false;
			response = this.processWlsc(req, order_id);
		}

		// 场景:一键开户请求；
		/*
		 * add by wui 客户回访调整、添加客户回访自动跳过一级规则 现场交付 -------------订单预校验、下一步 非现场交付
		 */
		if (only) {
			// 更新串号,ICCID入库到订单树
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.TERMINAL_NUM, AttrConsts.ICCID }, new String[] { imei, iccid });
			OrderExtBusiRequest ordExtBusiReq = new OrderExtBusiRequest();
			String flow_trace_id = "";
			OrderTreeBusiRequest orderTreeBusiReq = CommonDataFactory.getInstance().getOrderTree(order_id);
			if (orderTreeBusiReq != null) {
				ordExtBusiReq = orderTreeBusiReq.getOrderExtBusiRequest();
			}
			if (ordExtBusiReq != null) {
				flow_trace_id = ordExtBusiReq.getFlow_trace_id();
			}

			// //代理商资金归集扣款
			// PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
			// plan.setPlan_id(EcsOrderConsts.AGENT_DEDUCT_PLAN_ID);
			// TacheFact factAgent = new TacheFact();
			// factAgent.setOrder_id(order_id);
			// plan.setFact(factAgent);
			// plan.setDeleteLogs(true);
			// PlanRuleTreeExeResp prespAgent =
			// CommonDataFactory.getInstance().exePlanRuleTree(plan);
			// BusiCompResponse busiRespAgent =
			// CommonDataFactory.getInstance().getRuleTreeresult(prespAgent);
			// if(!ConstsCore.ERROR_SUCC.equals(busiRespAgent.getError_code())){
			// response.setError_code(ConstsCore.ERROR_FAIL);
			// response.setError_msg("代理商资金归集:["+out_id+"]"+busiRespAgent.getError_msg());
			// }else{
			// 场景:写卡环节(之前考虑新商城没有受理成功还可以通过一键开户接口再次受理。)
			if (flow_trace_id != null && EcsOrderConsts.DIC_ORDER_NODE_X.equals(flow_trace_id)) {
				TacheFact fact = new TacheFact();
				fact.setOrder_id(order_id);
				PlanRuleTreeExeReq planReq = new PlanRuleTreeExeReq();
				planReq.setPlan_id(EcsOrderConsts.RULE_PLAN_ID_103);
				planReq.setFact(fact);
				planReq.setDeleteLogs(true);// 删除写卡方案下执行方案日志,可重复运行写卡规则,请求总部写卡数据
				PlanRuleTreeExeResp presp = CommonDataFactory.getInstance().exePlanRuleTree(planReq);
				BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(presp);
				if (ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
					response.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
					response.setError_msg("请求成功,等待总部开户数据[" + out_id + "]。");
				} else {
					/** add by shusx 默认返回成功给新商城,失败信息通知状态通知接口告诉新商城 */
					response.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
					response.setError_msg("请求成功,但订单侧处理报错[" + out_id + "]" + busiResp.getError_msg());
					// response.setError_code(ConstsCore.ERROR_FAIL);
					// response.setError_msg("请求失败:["+out_id+"]"+busiResp.getError_msg());
				}
			} else {
				BusiCompRequest busiCompReq = new BusiCompRequest();
				busiCompReq.setOrder_id(order_id);
				Map queryParams = new HashMap();
				queryParams.put("order_id", order_id);
				queryParams.put("is_auto", EcsOrderConsts.RULE_EXE_ALL);
				queryParams.put("source_from", "infServices");
				busiCompReq.setQueryParams(queryParams);
				busiCompReq.setEnginePath("enterTraceRule.exec");
				BusiCompResponse busiCompResp = CommonDataFactory.getInstance().execBusiComp(busiCompReq);
				BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(busiCompResp);
				if (ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
					response.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
					response.setError_msg("一键开户操作成功[" + out_id + "]");
				} else {
					/** add by shusx 默认返回成功给新商城,失败信息通知状态通知接口告诉新商城 */
					response.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
					response.setError_msg("请求成功,但订单侧处理报错[" + out_id + "]" + busiResp.getError_msg());
					// response.setError_code(ConstsCore.ERROR_FAIL);
					// response.setError_msg("一键开户操作失败:["+out_id+"]"+busiResp.getError_msg());
				}
			}

			// }

		}

		/**
		 * 改造思路: 1、一键开户联调测试，根据当前订单环节获取错误信息。订单标准化(O)、修改信息变更、开户（K）、写卡（X）。
		 * 2、订单归集添加模拟调用功能
		 * 
		 */
		OrderAKeyActResp orderAKeyActResp = new OrderAKeyActResp();
		orderAKeyActResp.setResp_code(response.getError_code());
		orderAKeyActResp.setResp_msg(response.getError_msg());
		return orderAKeyActResp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J24订单状态通知", summary = "订单状态通知")
	public StatuSynchResp statuSynchToWYG(StatuSynchReq req) throws ApiBusiException {
		String order_id = req.getORDER_ID();
		// 获取外部订单编号
		GetOutOrderByOrderIdReq goreq = new GetOutOrderByOrderIdReq();
		goreq.setOrder_id(order_id);
		// ZteClient client=
		// ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		GetOutOrderByOrderIdResp goresp = client.execute(goreq, GetOutOrderByOrderIdResp.class);
		String out_order_id = goresp.getOut_order_id();
		req.setORDER_ID(out_order_id);
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		param.put("ord_id", order_id);
		param.put("ep_mall", CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM));
		StatuSynchResp rsp = new StatuSynchResp();
		rsp = (StatuSynchResp) caller.invoke("WYG.statuSynchToWYG", param);
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单信息同步到WMS", summary = "订单信息同步到WMS")
	public NotifyOrderInfoWMSResp notifyOrderInfoWMS(NotifyOrderInfoWMSReq req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		NotifyOrderInfoWMSResp resp = new NotifyOrderInfoWMSResp();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ws_isNet", "T");
			param.put("ws_method", "ReqOSOrderInfo");
			param.put("ws_namespace", "http://tempuri.org/ReqOSOrderInfo");
			param.put("ord_id", req.getOrderId());
			resp = (NotifyOrderInfoWMSResp) caller.invoke("orderInfoSync2Wms", param);
		} catch (Exception e) {
			resp.setErrorCode("-9999");
			resp.setErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "接收WMS货品（串号）通知,由WMS发起", summary = "接收WMS货品（串号）通知")
	public SynSerialNumWMSResp synchronousSerialNumberWMS(SynSerialNumWMSReq request) throws ApiBusiException {

		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		String order_id = request.getOrderInfo().getOrderId();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if (null == orderTree || null == orderTree.getOrderExtBusiRequest()) {
			SynSerialNumWMSResp resp = new SynSerialNumWMSResp();
			resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0001);
			resp.setErrorMessage("订单不存在或已归档！");
			return resp;
		}

		// 自动化模式或者华盛B2B订单才接收WMS的拣货通知(接收串号)
		// 订单生产模式
		String orderModel = orderTree.getOrderExtBusiRequest().getOrder_model();
		// 订单来源
		String orderFrom = orderTree.getOrderExtBusiRequest().getOrder_from();
		if (!(EcsOrderConsts.OPER_MODE_ZD.equals(orderModel) || EcsOrderConsts.ORDER_FROM_10062.equals(orderFrom))) {
			SynSerialNumWMSResp resp = new SynSerialNumWMSResp();
			resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0001);
			resp.setErrorMessage("非自动化模式、非华盛B2B订单无需同步串号！");
			return resp;
		}

		String is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
		String ruleid = "";
		if (EcsOrderConsts.OPER_MODE_ZD.equals(orderModel)) {
			if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
				ruleid = EcsOrderConsts.WMS_REPLACE_GD_RULEID;
			} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
				ruleid = EcsOrderConsts.WMS_REPLACE_GD_RULEID_BSSKL;
			} else {
				ruleid = EcsOrderConsts.WMS_REPLACE_GD_RULEID_AOP;
			}
		} else if (EcsOrderConsts.OPER_MODE_RG.equals(orderModel)) {
			if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
				ruleid = EcsOrderConsts.WMS_REPLACE_RG_RULEID;
			} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
				// ruleid = EcsOrderConsts.WMS_REPLACE_RG_RULEID_BSSKL; //
				// 场景暂时未用到,暂时不定义
			} else {
				// ruleid = EcsOrderConsts.WMS_REPLACE_RG_RULEID_AOP; //
				// 场景暂时未用到,暂时不定义
			}
		}

		// 删除规则日志(缺货通知)
		RuleExeLogDelReq delLogReq = new RuleExeLogDelReq();
		delLogReq.setObj_id(request.getOrderInfo().getOrderId());
		delLogReq.setRule_id(ruleid);
		client.execute(delLogReq, RuleExeLogDelResp.class);

		// 调用货品通知业务组件
		SynSerialNumWMSResp resp = new SynSerialNumWMSResp();
		RuleTreeExeReq req = new RuleTreeExeReq();
		req.setRule_id(ruleid);
		req.setExePeerAfRules(false); // 先卡住，只执行接收WMS货品（串号）通知
		req.setExeParentsPeerAfRules(false); // 先卡住，只执行接收WMS货品（串号）通知
		TacheFact fact = new TacheFact();
		fact.setRequest(request);
		fact.setOrder_id(order_id);
		req.setFact(fact);
		RuleTreeExeResp busiResp = client.execute(req, RuleTreeExeResp.class);
		if (ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
			resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0000);
			resp.setErrorMessage("接收WMS货品（串号）通知成功！");

			// 开启异步线程执行后续规则
			try {
				// 获取接收到WMS终端串号的下一步规则ID
				if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
					ruleid = EcsOrderConsts.ZB_PRE_PICK_RULEID_01;
				} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
					ruleid = EcsOrderConsts.TERMI_PRE_OCCUPANCY_01_BSSKL;
				} else {
					ruleid = EcsOrderConsts.TERMI_PRE_OCCUPANCY_01_AOP;
				}

				RuleTreeExeReq reqNext = new RuleTreeExeReq();
				reqNext.setRule_id(ruleid);
				TacheFact factNext = new TacheFact();
				factNext.setOrder_id(order_id);
				reqNext.setFact(factNext);
				TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(reqNext) {
					@Override
					public ZteResponse execute(ZteRequest zteRequest) {
						RuleTreeExeReq exereq = (RuleTreeExeReq) zteRequest;
						ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
						RuleTreeExeResp rsp = client.execute(exereq, RuleTreeExeResp.class);
						BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
						return busiResp;
					}
				});
				ThreadPoolFactory.getInfServerThreadPoolExector().execute(taskThreadPool);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0001);
			resp.setErrorMessage(busiResp.getError_msg());
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单业务完成状态通知", summary = "订单业务完成状态通知")
	public NotifyOrderAccountWMSResp notifyOrderAccountWMS(NotifyOrderAccountWMSReq req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		NotifyOrderAccountWMSResp resp = new NotifyOrderAccountWMSResp();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ws_isNet", "T");
			param.put("ws_method", "ReqOSOrderBussStatus");
			param.put("ws_namespace", "http://tempuri.org/ReqOSOrderBussStatus");
			param.put("ord_id", req.getOrderId());
			resp = (NotifyOrderAccountWMSResp) caller.invoke("businessStatusSync2Wms", param);
		} catch (Exception e) {
			resp.setErrorCode("-9999");
			resp.setErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "接收WMS写卡机编号", summary = "接收WMS写卡机编号")
	public SynCardInfoWMSResp synchronousCardInfoWMS(SynCardInfoWMSReq request) throws ApiBusiException {
		SynCardInfoWMSResp resp = new SynCardInfoWMSResp();
		try {
			String order_id = request.getOrderInfo().getOrderId();
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			if (null == orderTree || null == orderTree.getOrderExtBusiRequest()) {
				resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0001);
				resp.setErrorMessage("订单不存在或已归档！");
				return resp;
			}
			String is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
			String ruleid = "";
			if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
				ruleid = EcsOrderConsts.WMS_RECIVE_MATHIN_ID_GD_RULEID;
			} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
				ruleid = EcsOrderConsts.WMS_RECIVE_MATHIN_ID_GD_RULEID_BSSKL;
			} else {
				ruleid = EcsOrderConsts.WMS_RECIVE_MATHIN_ID_GD_RULEID_AOP;
			}

			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			RuleTreeExeReq req = new RuleTreeExeReq();
			req.setRule_id(ruleid);
			req.setExePeerAfRules(false); // 先卡住，只执行接收写卡机编号规则
			req.setExeParentsPeerAfRules(false); // 先卡住，只执行接收写卡机编号规则
			TacheFact fact = new TacheFact();
			fact.setRequest(request);
			fact.setOrder_id(order_id);
			req.setFact(fact);
			RuleTreeExeResp rsp = client.execute(req, RuleTreeExeResp.class);
			BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
			if (ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
				resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0000);
				resp.setErrorMessage("接收WMS写卡机编号成功！");

				// 开启异步线程执行后续规则
				try {
					// 获取接收到WMS写卡机编号的下一步规则ID
					if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
						ruleid = EcsOrderConsts.SENRI_READ_ICCID_RULEID;
					} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
						ruleid = EcsOrderConsts.SENRI_READ_ICCID_RULEID_BSSKL;
					} else {
						ruleid = EcsOrderConsts.SENRI_READ_ICCID_RULEID_AOP;
					}

					RuleTreeExeReq reqNext = new RuleTreeExeReq();
					reqNext.setRule_id(ruleid);
					TacheFact factNext = new TacheFact();
					factNext.setOrder_id(order_id);
					reqNext.setFact(factNext);
					TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(reqNext) {
						@Override
						public ZteResponse execute(ZteRequest zteRequest) {
							RuleTreeExeReq exereq = (RuleTreeExeReq) zteRequest;
							ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
							RuleTreeExeResp rsp = client.execute(exereq, RuleTreeExeResp.class);
							BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
							return busiResp;
						}
					});
					ThreadPoolFactory.getInfServerThreadPoolExector().execute(taskThreadPool);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0001);
				resp.setErrorMessage(busiResp.getError_msg());
				Map params = new HashMap();
				params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
				CommonDataFactory.getInstance().markException(request.getOrderInfo().getOrderId(), params);
			}
		} catch (Exception e) {
			resp.setErrorCode("-9999");
			resp.setErrorMessage("处理失败");
			Map params = new HashMap();
			params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			CommonDataFactory.getInstance().markException(request.getOrderInfo().getOrderId(), params);
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "通知WMS写卡完成", summary = "通知WMS写卡完成")
	public NotifyWriteCardResultWMSResp notifyWriteCardResultWMS(NotifyWriteCardResultWMSReq req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		NotifyWriteCardResultWMSResp resp = new NotifyWriteCardResultWMSResp();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ws_isNet", "T");
			param.put("ws_method", "ReqOSWriteCardResult");
			param.put("ws_namespace", "http://tempuri.org/ReqOSWriteCardResult");
			param.put("ord_id", req.getOrderId());
			resp = (NotifyWriteCardResultWMSResp) caller.invoke("writeCardResultSync2Wms", param);
		} catch (Exception e) {
			resp.setErrorCode("-9999");
			resp.setErrorMessage(e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "通知WMS订单状态", summary = "通知WMS订单状态")
	public NotifyOrderStatusToWMSResp notifyOrderStatusToWMS(NotifyOrderStatusToWMSReq req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		NotifyOrderStatusToWMSResp resp = new NotifyOrderStatusToWMSResp();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ws_isNet", "T");
			param.put("ws_method", "ReqOSOrderStatus");
			param.put("ws_namespace", "http://tempuri.org/ReqOSOrderStatus");
			param.put("ord_id", req.getOrderId());
			resp = (NotifyOrderStatusToWMSResp) caller.invoke("orderStatusSync2Wms", param);
		} catch (Exception e) {
			resp.setErrorCode("-9999");
			resp.setErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "接收WMS订单状态通知", summary = "接收WMS订单状态通知")
	public NotifyOrderStatusFromWMSResp notifyOrderStatusFromWMS(NotifyOrderStatusFromWMSReq request) throws ApiBusiException {
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		RuleTreeExeReq req = new RuleTreeExeReq();
		NotifyStatusFromWMSOrderInfoVo notifyStatus = request.getOrderInfo();
		NotifyOrderStatusFromWMSResp resp = new NotifyOrderStatusFromWMSResp();
		String order_id = notifyStatus.getOrderId();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if (null == orderTree || null == orderTree.getOrderExtBusiRequest()) {
			resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0001);
			resp.setErrorMessage("订单不存在或已归档！");
			return resp;
		}
		String is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
		String ruleid = "";
		if (EcsOrderConsts.ORDER_STATUS_WMS_9.equals(notifyStatus.getStatus())) {
			if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
				ruleid = EcsOrderConsts.WMS_NOTIFY_ORD_SHIP_RULEID;
			} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
				ruleid = EcsOrderConsts.WMS_NOTIFY_ORD_SHIP_RULEID_BSSKL;
			} else {
				ruleid = EcsOrderConsts.WMS_NOTIFY_ORD_SHIP_RULEID_AOP;
			}
			req.setRule_id(ruleid); // 货到出库口
		} else if (EcsOrderConsts.ORDER_STATUS_WMS_10.equals(notifyStatus.getStatus())) {
			if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
				ruleid = EcsOrderConsts.WMS_NOTIFY_ORD_SHIP_FINISH_RULEID;
			} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
				ruleid = EcsOrderConsts.WMS_NOTIFY_ORD_SHIP_FINISH_RULEID_BSSKL;
			} else {
				ruleid = EcsOrderConsts.WMS_NOTIFY_ORD_SHIP_FINISH_RULEID_AOP;
			}
			req.setRule_id(ruleid); // 发货完成
			req.setExePeerAfRules(false); // 先卡住，只执行接收WMS订单状态通知
			req.setExeParentsPeerAfRules(false); // 先卡住，只执行接收WMS订单状态通知
		} else if (EcsOrderConsts.ORDER_STATUS_WMS_1.equals(notifyStatus.getStatus())) {
			// 非自动化订单无需接收缺货通知
			String orderModel = orderTree.getOrderExtBusiRequest().getOrder_model();
			if (!EcsOrderConsts.ORDER_MODEL_01.equals(orderModel)) {
				resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0001);
				resp.setErrorMessage("非自动化订单无需接收缺货通知！");
				return resp;
			}
			// req.setRule_id("201411211978000274");//缺货通知,缺货恢复,货物到异常口通知,周转箱到稽核工位通知
			PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
			plan.setPlan_id(EcsOrderConsts.MARKED_EXCEPTION_PLAN);
			ExceptionFact excep = new ExceptionFact();
			excep.setOrder_id(order_id);
			excep.setException_from(EcsOrderConsts.EXCEPTION_FROM_WMS);
			excep.setException_type(EcsOrderConsts.ABNORMAL_TYPE_STOCKOUT);
			excep.setException_desc("WMS通知缺货异常");
			plan.setFact(excep);
			CommonDataFactory.getInstance().exePlanRuleTree(plan);
			resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0000);
			resp.setErrorMessage("接收WMS异常通知成功！");
			return resp;
		} else if (EcsOrderConsts.ORDER_STATUS_WMS_25.equals(notifyStatus.getStatus())) {
			req.setRule_id(EcsOrderConsts.WMS_NOTIFY_ORD_BACK_RULEID); // 回库确认
		} else if (EcsOrderConsts.ORDER_STATUS_WMS_21.equals(notifyStatus.getStatus())) {
			// 华盛订单接收WMS退货入库信息的状态
			String platType = orderTree.getOrderExtBusiRequest().getPlat_type();
			String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
			String order_type = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderBusiRequest().getOrder_type();
			List<NotifyStatusFromWMSGoodInfoVo> goodInfoList = notifyStatus.getGoodInfo();
			if (goodInfoList == null) {
				resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0001);
				resp.setErrorMessage("GoodInfo节点未传!");
				return resp;
			}

			if (StringUtils.equals(platType, EcsOrderConsts.PLAT_TYPE_10061) || EcsOrderConsts.ORDER_TYPE_09.equals(order_type)) {
				// 华盛B2C、B2B与原有B2B订单根据多商品表校验串号是否一致
				List<OrderItemExtvtlBusiRequest> orderItemExtvtls = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemExtvtlBusiRequests();
				//屏蔽无用代码 xiao.ruidan 20180518
				/*List<HuashengOrderItemBusiRequest> hsOrderItems = CommonDataFactory.getInstance().getOrderTree(order_id).getHuashengOrderItemBusiRequest();
				String mchk = ""; // 串码管理
				for (NotifyStatusFromWMSGoodInfoVo goodsInfoVo : goodInfoList) {
					for (OrderItemExtvtlBusiRequest orderItemExtvtl : orderItemExtvtls) {
						for (HuashengOrderItemBusiRequest hsItems : hsOrderItems) {
							if (StringUtils.equals(hsItems.getSku(), orderItemExtvtl.getSku())) {
								if (goodsInfoVo.getOrderProductId().equals(orderItemExtvtl.getItems_id())) {
									mchk = CommonDataFactory.getInstance().getHSMCHK(hsItems.getMatnr());
									// 串码管理的订单才校验串号一致
									if (StringUtils.equals(mchk, "X") && !StringUtils.equals(orderItemExtvtl.getResources_code(), goodsInfoVo.getImei())) {
										// 校验WMS退货入库串号与拣货串号是否一致
										resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0001);
										resp.setErrorMessage("WMS退货入库串号与拣货串号不一致！");
										return resp;
									}
								}
							}
						}
					}
				}*/
			} else {
				// 正常订单取es_order_items_prod_ext表校验串号是否一致
				List<OrderItemProdBusiRequest> prodBusis = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
				for (NotifyStatusFromWMSGoodInfoVo goodsInfoVo : goodInfoList) {
					for (OrderItemProdBusiRequest prodBusi : prodBusis) {
						OrderItemProdExtBusiRequest prodExt = prodBusi.getOrderItemProdExtBusiRequest();
						if (!StringUtils.equals(prodExt.getTerminal_num(), goodsInfoVo.getImei())) {
							// 校验WMS退货入库串号与拣货串号是否一致
							resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0001);
							resp.setErrorMessage("WMS退货入库串号与拣货串号不一致！");
							return resp;
						}
					}
				}
			}
			// 华盛B2C订单退单后自动调用确认退单规则，其它来源订单需要人工确认
			if (StringUtils.equals(order_from, EcsOrderConsts.ORDER_FROM_10061)) {
				// 校验成功后，调用退单人工审核方案中的确认退单规则，规则执行完成退单所有操作
				req.setRule_id(EcsOrderConsts.ORDER_RETURN_CONFIRM_RULE);
				req.setCheckAllRelyOnRule(true);
				req.setCheckCurrRelyOnRule(true);
				TacheFact fact = new TacheFact();
				fact.setOrder_id(order_id);
				req.setFact(fact);
				RuleTreeExeResp rsp = client.execute(req, RuleTreeExeResp.class);
				BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
				if (ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
					resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0000);
					resp.setErrorMessage("接收订单WMS退货入库状态成功！");
					return resp;
				} else {
					resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0001);
					resp.setErrorMessage("接收订单WMS退货入库状态成功，执行退单规则失败！");
					return resp;
				}
			} else {
				resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0000);
				resp.setErrorMessage("接收订单WMS退货入库状态成功！");
				return resp;
			}
		} else {
			resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0000);
			resp.setErrorMessage("接收成功！");
			return resp;
		}
		req.setCheckAllRelyOnRule(true);
		req.setCheckCurrRelyOnRule(true);
		TacheFact fact = new TacheFact();
		fact.setRequest(request);
		fact.setOrder_id(order_id);
		req.setFact(fact);
		RuleTreeExeResp rsp = client.execute(req, RuleTreeExeResp.class);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
		if (ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
			resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0000);
			resp.setErrorMessage("接收WMS订单状态通知成功！");

			// 开启异步线程执行后续规则
			try {
				if (EcsOrderConsts.ORDER_STATUS_WMS_10.equals(notifyStatus.getStatus())) { // 发货完成
					// 获取发货完成下一步规则ID
					if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
						ruleid = EcsOrderConsts.WCS_NOTIFY_SHIP_RULEID;
					} else if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
						ruleid = EcsOrderConsts.WCS_NOTIFY_SHIP_RULEID_BSSKL;
					} else {
						ruleid = EcsOrderConsts.WCS_NOTIFY_SHIP_RULEID_AOP;
					}

					RuleTreeExeReq reqNext = new RuleTreeExeReq();
					reqNext.setRule_id(ruleid);
					TacheFact factNext = new TacheFact();
					factNext.setOrder_id(order_id);
					reqNext.setFact(factNext);
					TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(reqNext) {
						@Override
						public ZteResponse execute(ZteRequest zteRequest) {
							RuleTreeExeReq exereq = (RuleTreeExeReq) zteRequest;
							ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
							RuleTreeExeResp rsp = client.execute(exereq, RuleTreeExeResp.class);
							BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
							return busiResp;
						}
					});
					ThreadPoolFactory.getInfServerThreadPoolExector().execute(taskThreadPool);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0001);
			resp.setErrorMessage(busiResp.getError_msg());
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J38同步稽查数据", summary = "同步稽查数据")
	public SynchronousCheckFromWMSResp synchronousCheckFromWMS(SynchronousCheckFromWMSReq request) throws ApiBusiException {
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		RuleTreeExeReq req = new RuleTreeExeReq();
		req.setRule_id(EcsOrderConsts.WMS_SYN_CHECK_GD_RULEID);
		AutoFact fact = new TacheFact();
		fact.setRequest(request);
		req.setFact(fact);
		RuleTreeExeResp rsp = client.execute(req, RuleTreeExeResp.class);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
		SynchronousCheckFromWMSResp resp = new SynchronousCheckFromWMSResp();
		if (ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
			resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0000);
			resp.setErrorMessage("接收稽查数据成功！");
		} else {
			resp.setErrorCode(EcsOrderConsts.WMS_INF_RESP_CODE_0001);
			resp.setErrorMessage(busiResp.getError_msg());
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取订单号", summary = "获取订单号")
	public GetOrdByBoxIdFromWMSResp getOrdByBoxIdFromWMS(GetOrdByBoxIdFromWMSReq req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, String> param = new HashMap<String, String>();
		GetOrdByBoxIdFromWMSResp resp = new GetOrdByBoxIdFromWMSResp();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ws_isNet", "T");
			param.put("ws_method", "ReqAuditInfo");
			param.put("ws_namespace", "http://tempuri.org/ReqAuditInfo");
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (GetOrdByBoxIdFromWMSResp) caller.invoke("boxCodeSync2Wms", param);
		} catch (Exception e) {
			resp.setErrorCode("-9999");
			resp.setErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "单点登录到BSS", summary = "单点登录到BSS")
	public SSOLoginBSSResp SSOLoginBSS(SSOLoginBSSReq req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		param.put("ord_id", req.getNotNeedReqStrOrderId());
		Map map = (Map) caller.invoke("bss.SSOLoginBSS", param);
		SSOLoginBSSResp rsp = new SSOLoginBSSResp();
		rsp.setError_code((String) map.get("RespCode"));
		rsp.setError_msg((String) map.get("RespDesc"));
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "登陆反调确认接口", summary = "登陆反调确认接口")
	public SSOLoginReqVerifyBSSResp SSOLoginReqVerifyBSS(SSOLoginReqVerifyBSSReq req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		param.put("ord_id", req.getNotNeedReqStrOrderId());
		Map map = (Map) caller.invoke("bss.SSOLoginReqVerifyBSS", param);
		SSOLoginReqVerifyBSSResp rsp = new SSOLoginReqVerifyBSSResp();
		rsp.setError_code((String) map.get("RespCode"));
		rsp.setError_msg((String) map.get("RespDesc"));
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "页面功能调用反调确认接口", summary = "页面功能调用反调确认接口")
	public PageCallVerifyBSSResp PageCallVerifyBSS(PageCallVerifyBSSReq req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		param.put("ord_id", req.getNotNeedReqStrOrderId());
		Map map = (Map) caller.invoke("bss.PageCallVerifyBSS", param);
		PageCallVerifyBSSResp rsp = new PageCallVerifyBSSResp();
		rsp.setError_code((String) map.get("RespCode"));
		rsp.setError_msg((String) map.get("RespDesc"));
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS对账信息同步", summary = "BSS对账信息同步")
	public OrderFinAccountSyncResp orderFinAccountSync(OrderFinAccountSyncReq req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		String xml = InfCommonUtils.infXMLConvert(req, "");
		Map<String, String> param = new HashMap<String, String>();
		param.put("wms_req_xml", xml);
		param.put("ws_isNet", "T");
		param.put("ws_method", "ReqOSOrderBussStatus");
		param.put("ws_namespace", "http://tempuri.org/ReqOSOrderBussStatus");
		param.put("ord_id", req.getOPER_NO());
		OrderFinAccountSyncResp resp = (OrderFinAccountSyncResp) caller.invoke("bss.orderFinAccountSync", param);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS社会机TAC码商品折扣包录入", summary = "BSS社会机TAC码商品折扣包录入")
	public ActionRecvBSSResp actionRecvBSS(ActionRecvBSSReq actionRecvDREQ) throws ApiBusiException {
		GDBssHead GD_BSS_HEAD = new GDBssHead();
		GD_BSS_HEAD.setNotNeedReqStrOrderId(actionRecvDREQ.getORDER_ID());
		GD_BSS_HEAD.setMSG_SENDER(EcsOrderConsts.ORDERSERVICED);
		GD_BSS_HEAD.setSERVICE_NAME(EcsOrderConsts.ACTIONRECV);
		Map<String, String> param = new HashMap<String, String>();
		Map<String, String> gD_BSS_HEAD = new HashMap<String, String>();
		try {
			BeanUtils.bean2Map(gD_BSS_HEAD, GD_BSS_HEAD);
			actionRecvDREQ.setGD_BSS_HEAD(gD_BSS_HEAD);
			BeanUtils.bean2Map(param, actionRecvDREQ);
		} catch (Exception e) {
			e.printStackTrace();
		}
		param.put("ord_id", actionRecvDREQ.getORDER_ID());
		CommCaller caller = new CommCaller();
		ActionRecvBSSResp resp = (ActionRecvBSSResp) caller.invoke("bss.actionRecvBSS", param);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "代理扣款返销", summary = "代理扣款返销")
	public AgencyAcctPayNotifyRsp agencyAcctPayNotify(AgencyAcctPayNotifyReq req) throws ApiBusiException {
		Map<String, String> param = new HashMap<String, String>();
		try {
			BeanUtils.bean2Map(param, req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		param.put("ord_id", req.getNotNeedReqStrOrderId());
		CommCaller caller = new CommCaller();
		AgencyAcctPayNotifyRsp resp = (AgencyAcctPayNotifyRsp) caller.invoke("bss.agencyAcctPayNotify", param);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J51读取ICCID值接口", summary = "订单系统请求写卡机服务读取ICCID值。")
	public ReadICCIDSRResponse readICCIDSR(ReadICCIDSRRequset req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		ReadICCIDSRResponse rsp = new ReadICCIDSRResponse();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			rsp = (ReadICCIDSRResponse) caller.invoke("sr.writeCard", param);
		} catch (Exception e) {
			rsp.setSr_response(new ReadIccidResp("-999", e.getMessage()));
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J52写卡接口", summary = "外部针对每次写卡机访问请求对写卡机管理服务发起一个HTTP请求,写卡机管理服务接收到请求之后会对该请求进行合法性校验,如果校验不通过,将返回校验失败并中断流程；如果校验通过,则启动对写卡机的操作,处理完成后将返回处理结果。")
	public WriteICCIDSRResponse writeICCIDSR(WriteICCIDSRRequset req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		WriteICCIDSRResponse rsp = new WriteICCIDSRResponse();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			rsp = (WriteICCIDSRResponse) caller.invoke("sr.writeICCIDSR", param);
		} catch (Exception e) {
			rsp.setSr_response(new WritCardResp("-9999", e.getMessage()));
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J53回收卡", summary = "回收卡")
	public RevertCardResponse revertCard(RevertCardRequset req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		RevertCardResponse rsp = new RevertCardResponse();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			rsp = (RevertCardResponse) caller.invoke("sr.revertCard", param);
		} catch (Exception e) {
			rsp.setSr_response(new RecycleCardResp("-999", e.getMessage()));
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J61身份校验接口", summary = "验证身份证信息是否正确")
	public CheckIDECAOPResponse checkIDECAOP(CheckIDECAOPRequset req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		param.put("ord_id", req.getNotNeedReqStrOrderId());
		param.put("bizkey", "CS-007");
		CheckIDECAOPResponse resp = (CheckIDECAOPResponse) caller.invoke("ecaop.trades.query.comm.cert.check", param);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J62获取证件照图片路径", summary = "获取证件照图片路径")
	public AccountPhotoResponse getAccountPhotoFromWYG(AccountPhotoRequest req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		param.put("ord_id", req.getOrderId());
		String urls = (String) caller.invoke("WYG.cert.photo", param);
		AccountPhotoResponse resp = new AccountPhotoResponse();
		resp.setUrls(urls);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J116照片信息查询", summary = "照片信息查询")
	public CertCheckZBResponse getAccountPhotoFromZB(CertCheckZBRequest req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		CertCheckZBResponse rsp = new CertCheckZBResponse();
		try {
			BeanUtils.bean2Map(param, req);
		} catch (Exception e) {
			rsp.setError_msg(e.getMessage());// 失败原因
			e.printStackTrace();
		}
		param.put("ord_id", req.getNotNeedReqStrOrderId());// 这个ord_id不知道干嘛用的
		rsp = (CertCheckZBResponse) caller.invoke("periphery.photoquery", param);
		rsp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);// 调用接口成功
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单状态同步到淘宝天机平台", summary = "订单状态同步到淘宝天机平台")
	public TbTianjiOrderStatusNoticeResp syncTbTianjiOrderStatus(TbTianjiOrderStatusNoticeReq req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		TbTianjiOrderStatusNoticeResp resp = new TbTianjiOrderStatusNoticeResp();
		AlibabaTianjiSupplierOrderResultRequest orderResultReq = new AlibabaTianjiSupplierOrderResultRequest();
		SupplierOrderResultModel model = new SupplierOrderResultModel();
		try {
			BeanUtils.copyProperties(model, req);
			orderResultReq.setSupplierOrderResultModel(model);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ord_id", req.getNotNeedReqStrOrderId());
			params.put("request", orderResultReq);
			params.put("ep_mall", CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.ORDER_FROM));
			AlibabaTianjiSupplierOrderResultResponse alibabaResp = (AlibabaTianjiSupplierOrderResultResponse) caller.invoke("alibaba.tianji.supplier.order.result", params);
			if (alibabaResp == null) {
				resp.setError_code("-1");
				resp.setError_msg("调用失败");
			} else {
				boolean result = alibabaResp.getModel();
				if (!result) {
					resp.setError_code("-1");
					resp.setError_msg("订单状态通知淘宝天机平台失败：" + alibabaResp.getSubMsg());
				} else {
					resp.setError_code("0000");
					resp.setError_msg("调用成功");
					resp.setModel(result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code("-1");
			resp.setError_msg("订单状态通知淘宝天机平台失败：" + e.getMessage());
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单物流信息同步到淘宝天机平台（发货）", summary = "订单物流信息同步到淘宝天机平台（发货）")
	public TbTianjiOrderDeliverySyncResp syncTbTianjiOrderDelivery(TbTianjiOrderDeliverySyncReq deliverySyncReq) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		TbTianjiOrderDeliverySyncResp resp = new TbTianjiOrderDeliverySyncResp();
		AlibabaTianjiSupplierOrderDeliveryRequest req = new AlibabaTianjiSupplierOrderDeliveryRequest();
		DistributionOrderLogisticsModel model = new DistributionOrderLogisticsModel();
		try {
			BeanUtils.copyProperties(model, deliverySyncReq.getLogisticsModel());
			req.setParamDistributionOrderLogisticsModel(model);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ord_id", deliverySyncReq.getNotNeedReqStrOrderId());
			params.put("request", req);
			params.put("ep_mall", CommonDataFactory.getInstance().getAttrFieldValue(deliverySyncReq.getNotNeedReqStrOrderId(), AttrConsts.ORDER_FROM));
			AlibabaTianjiSupplierOrderDeliveryResponse alibabaResp = (AlibabaTianjiSupplierOrderDeliveryResponse) caller.invoke("alibaba.tianji.supplier.order.result", params);
			if (alibabaResp == null) {
				resp.setError_code("-1");
				resp.setError_msg("调用失败");
			} else {
				boolean result = alibabaResp.getModel();
				if (!result) {
					resp.setError_code("-1");
					resp.setError_msg("订单物流信息同步淘宝天机平台失败：" + alibabaResp.getSubMsg());
				} else {
					resp.setError_code("0000");
					resp.setError_msg("订单物流信息同步淘宝天机平台成功");
					resp.setModel(result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code("-1");
			resp.setError_msg("订单物流信息同步淘宝天机平台失败：" + e.getMessage());
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J71订单详情查询接口", summary = "从淘宝获取订单详情信息")
	public TbTianjiOrderInfoGetResp getTbTianjiOrderInfo(TbTianjiOrderInfoGetReq req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		TbTianjiOrderInfoGetResp resp = new TbTianjiOrderInfoGetResp();
		AlibabaTianjiSupplierOrderQueryRequest alibabaReq = new AlibabaTianjiSupplierOrderQueryRequest();

		alibabaReq.setParamSupplierTopQueryModel(req.getModel());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ord_id", req.getNotNeedReqStrOrderId());
		params.put("request", alibabaReq);
		params.put("ep_mall", CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.ORDER_FROM));
		AlibabaTianjiSupplierOrderQueryResponse alibabaResp = (AlibabaTianjiSupplierOrderQueryResponse) caller.invoke("alibaba.tianji.supplier.order.query", params);
		if (alibabaResp == null) {
			resp.setError_code("-1");
			resp.setError_msg("调用失败");
		} else {
			List<DistributionOrderInfo> orderList = alibabaResp.getModelList();
			if (null == orderList || 0 == orderList.size()) {
				resp.setError_code("-1");
				resp.setError_msg("订单" + req.getNotNeedReqStrOrderId() + "不存在");
			} else {
				resp.setError_code("0000");
				resp.setError_msg("调用成功");
				resp.setOrderInfo(orderList.get(0));
			}
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J71订单详情查询接口", summary = "订单系统将订单信息通知淘宝")
	public GetTaobaoOrderInfoTAOBAOResponse getTaobaoOrderInfo(GetTaobaoOrderInfoTAOBAORequset req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> params = new HashMap();
		TradeGetRequest tradereq = new TradeGetRequest();
		tradereq.setFields((String) param.get("fields"));
		tradereq.setTid(Long.parseLong((String) param.get("tid")));
		params.put("ord_id", req.getNotNeedReqStrOrderId());
		params.put("request", tradereq);
		params.put("ep_mall", CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.ORDER_FROM));
		TradeGetResponse tradeResp = (TradeGetResponse) caller.invoke("tb.getOrderInfo", params);
		GetTaobaoOrderInfoTAOBAOResponse resp = new GetTaobaoOrderInfoTAOBAOResponse();
		if (tradeResp == null) {
			resp.setError_code("-1");
			resp.setError_msg("调用失败");
		} else {
			Trade dataTrade = tradeResp.getTrade();
			if (null == dataTrade) {
				resp.setError_code("-1");
				resp.setError_msg("调用失败");
			} else {
				resp.setError_code("0000");
				resp.setError_msg("调用成功");
				resp.setDataTrade(dataTrade);
			}
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J72订单信息变更", summary = "订单系统调用该接口可实现无需物流(虚拟)发货,使用该接口发货,交易订单状态会直接变成卖家已发货,用户调用该接口可实现在线订单发货支持货到付款调用该接口实现在线下单发货.")
	public LogisticsTAOBAOResponse LogisticsTB(LogisticsTAOBAORequset req) throws ApiBusiException {
		LogisticsTAOBAOResponse resp = new LogisticsTAOBAOResponse();
		resp.setMark(req.getMark());
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		param.put("ord_id", req.getNotNeedReqStrOrderId());
		boolean tmpship = false;
		// 虚拟商品
		if (EcsOrderConsts.TB_SHIPPING_MARK_VIRTUAL.equals(req.getMark())) {
			LogisticsTAOBAOVirtual virtual = req.getVirtual();
			LogisticsDummySendRequest dumpsendreq = new LogisticsDummySendRequest();
			dumpsendreq.setTid(Long.parseLong(virtual.getTid()));
			dumpsendreq.setFeature(virtual.getFeature());
			param.put("request", dumpsendreq);
			param.put("ep_mall", CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.ORDER_FROM));
			LogisticsDummySendResponse dumprespo = (LogisticsDummySendResponse) caller.invoke("tb.logistics", param);
			resp.setDumprespo(dumprespo);
			resp.setIs_success(dumprespo.isSuccess() ? "true" : "false");
			Shipping shipptmp = dumprespo.getShipping();
			if (shipptmp != null) {
				tmpship = shipptmp.getIsSuccess();
				resp.setError_code("0000");
				resp.setError_msg(
						"errorcode:" + dumprespo.getErrorCode() + "," + "msg:" + dumprespo.getMsg() + "," + "suberrorcode:" + dumprespo.getSubCode() + "," + "submsg:" + dumprespo.getSubMsg());
			} else {
				resp.setError_code("-1");
				resp.setError_msg(
						"errorcode:" + dumprespo.getErrorCode() + "," + "msg:" + dumprespo.getMsg() + "," + "suberrorcode:" + dumprespo.getSubCode() + "," + "submsg:" + dumprespo.getSubMsg());
			}
		} else {
			LogisticsTAOBAOOnline online = req.getOnline();
			// 外部订单号
			String out_tid = CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.OUT_TID);
			// 运单号
			String logi_no = CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.LOGI_NO);
			// 物流公司编码
			String company_code = CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.SHIPPING_COMPANY);
			company_code = CommonDataFactory.getInstance().getLogiCompanyCode(company_code);
			Pattern pmobile = Pattern.compile(
					"^POST|EMS|STO|YTO|YUNDA|ZJS|BEST|FEDEX|DBL|SHQ|ZTO|CCES|HTKY|TTKDEX|SF|AIRFEX|APEX|CYEXP|DTW|YUD|STARS|ANTO|CRE|EBON|HZABC|DFH|SY|YC|ZY|YCT|XB|NEDA|XFHONG|LB|WLB-ABC|WLB-SAD|WLB-STARS|FAST|UC|QRT|LTS|QFKD|UNIPS|UAPEX");
			Matcher m1 = pmobile.matcher(company_code);
			if (!m1.find()) {
				company_code = EcsOrderConsts.TAOBAO_OTHER_COMPANY_CODE;
			}
			String payModeCd = CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.PAY_TYPE);
			if (EcsOrderConsts.PAY_TYPE_HDFK.equals(payModeCd)) {// 货到付款
				LogisticsOnlineSendRequest dd = new LogisticsOnlineSendRequest();
				dd.setTid(Long.parseLong(online.getTid()));
				dd.setOutSid(logi_no);
				dd.setCompanyCode(company_code);
				dd.setFeature(online.getFeature());
				param.put("request", dd);
				param.put("ep_mall", CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.ORDER_FROM));
				LogisticsOnlineSendResponse logonresp = (LogisticsOnlineSendResponse) caller.invoke("tb.logistics", param);
				resp.setLogonresp(logonresp);
				resp.setIs_success(logonresp.isSuccess() ? "true" : "false");
				Shipping shipptmp = logonresp.getShipping();
				if (shipptmp != null) {
					tmpship = shipptmp.getIsSuccess();
					resp.setError_code("0000");
					resp.setError_msg(
							"errorcode:" + logonresp.getErrorCode() + "," + "msg:" + logonresp.getMsg() + "," + "suberrorcode:" + logonresp.getSubCode() + "," + "submsg:" + logonresp.getSubMsg());
				} else {
					resp.setError_code("-1");
					resp.setError_msg(
							"errorcode:" + logonresp.getErrorCode() + "," + "msg:" + logonresp.getMsg() + "," + "suberrorcode:" + logonresp.getSubCode() + "," + "submsg:" + logonresp.getSubMsg());
				}
			} else {
				LogisticsOfflineSendRequest logoff = new LogisticsOfflineSendRequest();
				logoff.setTid(Long.parseLong(online.getTid()));
				logoff.setOutSid(logi_no);
				logoff.setCompanyCode(company_code);
				logoff.setFeature(online.getFeature());
				param.put("request", logoff);
				param.put("ep_mall", CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.ORDER_FROM));
				LogisticsOfflineSendResponse logoffresp = (LogisticsOfflineSendResponse) caller.invoke("tb.logistics", param);
				resp.setLogoffresp(logoffresp);
				resp.setIs_success(logoffresp.isSuccess() ? "true" : "false");
				Shipping shipptmp = logoffresp.getShipping();
				if (shipptmp != null) {
					tmpship = shipptmp.getIsSuccess();
					resp.setError_code("0000");
					resp.setError_msg(
							"errorcode:" + logoffresp.getErrorCode() + "," + "msg:" + logoffresp.getMsg() + "," + "suberrorcode:" + logoffresp.getSubCode() + "," + "submsg:" + logoffresp.getSubMsg());
				} else {
					resp.setError_code("-1");
					resp.setError_msg(
							"errorcode:" + logoffresp.getErrorCode() + "," + "msg:" + logoffresp.getMsg() + "," + "suberrorcode:" + logoffresp.getSubCode() + "," + "submsg:" + logoffresp.getSubMsg());
				}
			}
		}

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J73订单信息同步", summary = "订单信息同步,同步备注信息")
	public SynchronousOrderTBResponse SynchronousOrderTB(SynchronousOrderTBRequset req) throws ApiBusiException {
		SynchronousOrderTBResponse resp = new SynchronousOrderTBResponse();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> params = new HashMap();
		params.put("ord_id", req.getNotNeedReqStrOrderId());
		TradeMemoUpdateRequest tradeMemoUpdateRequest = new TradeMemoUpdateRequest();
		tradeMemoUpdateRequest.setTid(Long.parseLong(req.getTid()));
		tradeMemoUpdateRequest.setMemo(req.getMemo());
		// tradeMemoUpdateRequest.setFlag(Long.parseLong(req.getFlag()));
		// //更改旗子颜色
		tradeMemoUpdateRequest.setReset(false);
		params.put("request", tradeMemoUpdateRequest);
		params.put("ep_mall", CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.ORDER_FROM));
		TradeMemoUpdateResponse response = (TradeMemoUpdateResponse) caller.invoke("tb.synchronousOrder", params);
		if (response.isSuccess()) {
			resp.setError_code("0000");
			resp.setError_msg("调用成功");
		} else {
			resp.setError_code("-1");
			resp.setError_msg("调用失败");
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J81订单信息变更", summary = "订单系统将订单信息通知南都")
	public NotifyOrderInfoNDResponse notifyOrderInfoND(NotifyOrderInfoNDRequset req) throws ApiBusiException {
		NotifyOrderInfoNDResponse resp = new NotifyOrderInfoNDResponse();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrorderId());
			Map map = (Map) caller.invoke("nd.notifyOrderInfo", param);
			resp.setErrorCode(Const.getStrValue(map, "errorMessage"));
			resp.setErrorMessage(Const.getStrValue(map, "errorCode"));
			resp.setActiveNo(Const.getStrValue(map, "activeNo"));

		} catch (Exception e) {
			resp.setErrorCode("-9999");
			resp.setErrorMessage("调用接口异常:" + e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J82订单信息变更", summary = "物流公司接到订单系统订单后,给订单分配派工号时,调用该接口,通知订单系统,该订单由哪个工号进行处理;只有此工号登陆订单系统,才能看到此订单进行开户,写卡操作。")
	public DispatchingNumReceivingNDResponse dispatchingNumReceivingND(DispatchingNumReceivingNDRequset request) throws ApiBusiException {
		DispatchingNumReceivingNDResponse resp = new DispatchingNumReceivingNDResponse();
		resp.setActiveNo(request.getActiveNo());
		try {

			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			RuleTreeExeReq req = new RuleTreeExeReq();
			req.setRule_id(EcsOrderConsts.ND_DISPATCHING_NUM_GD_RULEID);
			/*
			 * String outOrderid = request.getOrderId(); OrderTreeBusiRequest
			 * orderTree =
			 * CommonDataFactory.getInstance().getOrderTreeByOutId(outOrderid);
			 * if(null==orderTree){ throw new Exception("订单号"+outOrderid+"不存在");
			 * }
			 */

			// 删除规则日志
			RuleExeLogDelReq delLogReq = new RuleExeLogDelReq();
			delLogReq.setObj_id(request.getOrderId());
			delLogReq.setRule_id(EcsOrderConsts.ND_DISPATCHING_NUM_GD_RULEID);
			client.execute(delLogReq, RuleExeLogDelResp.class);

			TacheFact fact = new TacheFact();
			fact.setOrder_id(request.getOrderId());
			fact.setRequest(request);
			req.setFact(fact);

			RuleTreeExeResp rsp = client.execute(req, RuleTreeExeResp.class);
			boolean isExecute = false;// 是否已经执行规则 true -是 flase --否
			List<PlanRule> excList = rsp.getFact().getHasExeRuleList();
			if (excList != null && excList.size() > 0) {
				PlanRule planRule = excList.get(0);
				isExecute = planRule.isExecute();
			}
			if (ConstsCore.ERROR_SUCC.equals(rsp.getError_code()) && isExecute) {// 没调用业务组件也算失败
				resp.setErrorCode(EcsOrderConsts.ND_INF_SUCC_CODE);
				resp.setErrorMessage("接收工号:[" + request.getOperCode() + "]成功！");
			} else {
				String msg = rsp.getError_msg();
				if (!isExecute) {
					msg = "没有调用接收工号的业务组件";
				}
				resp.setErrorCode(EcsOrderConsts.ND_INF_FAIL_CODE);
				resp.setErrorMessage("接收工号:[" + request.getOperCode() + "]失败:" + msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setErrorCode(EcsOrderConsts.ND_INF_FAIL_CODE);
			resp.setErrorMessage("接收工号失败:" + e.getMessage());
		}

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J83订单状态通知接口", summary = "物流公司获取订单后,将订单的状态通知给订单系统。状态包括：成功接收,拒绝接收,开户失败,用户拒收,确认配送。")
	public NotifyOrderStatuNDResponse notifyOrderStatuND(NotifyOrderStatuNDRequset request) throws ApiBusiException {

		NotifyOrderStatuNDResponse resp = new NotifyOrderStatuNDResponse();
		resp.setActiveNo(request.getActiveNo());
		try {
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			List<StatuOrderInfo> orderInfoList = request.getOrderInfo();
			if (orderInfoList.size() > 0) {
				StatuOrderInfo orderInfo = orderInfoList.get(0);
				RuleTreeExeReq req = new RuleTreeExeReq();
				req.setRule_id(EcsOrderConsts.ND_SYN_ORD_GD_RULEID);
				TacheFact fact = new TacheFact();
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTreeByOutId(orderInfo.getOrigOrderId());
				if (null == orderTree) {
					throw new Exception("订单号不存在");
				}
				String order_id = orderTree.getOrder_id();
				fact.setOrder_id(order_id);
				fact.setRequest(request);
				req.setFact(fact);
				RuleTreeExeResp rsp = client.execute(req, RuleTreeExeResp.class);
				BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
				if (ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {// 业务组件返回订单信息
					resp = (NotifyOrderStatuNDResponse) busiResp.getResponse();
				} else {
					resp.setErrorCode(EcsOrderConsts.ND_INF_FAIL_CODE);
					resp.setErrorMessage("失败！");
				}
			} else {
				resp.setErrorCode(EcsOrderConsts.ND_INF_FAIL_CODE);
				resp.setErrorMessage("报文缺少OrderInfo节点");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setErrorCode(EcsOrderConsts.ND_INF_FAIL_CODE);
			resp.setErrorMessage("更新订单状态时,出现异常:" + e.getMessage());
		}

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J84处理完成通知", summary = "物流公司获取订单后,将订单的状态通知给订单系统。状态包括：成功接收,拒绝接收,开户失败,用户拒收,确认配送。")
	public OrderDealSuccessNDResponse orderDealSuccessND(OrderDealSuccessNDRequset request) throws ApiBusiException {
		OrderDealSuccessNDResponse resp = new OrderDealSuccessNDResponse();
		resp.setActiveNo(request.getActiveNo());
		try {
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			List<OrderInfo> orderInfoList = request.getOrderInfo();
			if (orderInfoList.size() == 0) {
				throw new Exception("报文缺少orderInfo节点");
			}
			String outOrderId = orderInfoList.get(0).getOrigOrderId();
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTreeByOutId(outOrderId);
			if (null == orderTree) {
				throw new Exception("订单号不存在");
			}
			String order_id = orderTree.getOrder_id();
			RuleTreeExeReq req = new RuleTreeExeReq();
			req.setRule_id(EcsOrderConsts.ND_ORD_DEAL_GD_RULEID);
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			fact.setRequest(request);
			req.setFact(fact);
			RuleTreeExeResp rsp = client.execute(req, RuleTreeExeResp.class);
			BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
			if (ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {// 业务组件返回订单信息
				resp = (OrderDealSuccessNDResponse) busiResp.getResponse();

			} else {
				resp.setErrorCode(EcsOrderConsts.ND_INF_FAIL_CODE);
				resp.setErrorMessage("处理完成通知订单系统失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setErrorCode(EcsOrderConsts.ND_INF_FAIL_CODE);
			resp.setErrorMessage("处理完成通知订单系统失败:" + e.getMessage());
		}

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J91订单信息变更(向顺丰下单)", summary = "订单系统将订单信息变更需要通知顺丰")
	public NotifyOrderInfoSFResponse notifyOrderInfoSF(NotifyOrderInfoSFRequset req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		NotifyOrderInfoSFResponse resp = new NotifyOrderInfoSFResponse();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getOrderid());
			resp = (NotifyOrderInfoSFResponse) caller.invoke("sf.orderstatenotify", param);
			// 测试代码 开始
			/*
			 * resp.setErrorCode("OK"); resp.setErrorMessage("模拟成功");
			 * zte.net.ecsord.params.sf.vo.OrderInfo orderInfo =new
			 * zte.net.ecsord.params.sf.vo.OrderInfo();
			 * orderInfo.setOrderid("GZ201502109307998661");//订单号
			 * orderInfo.setMailno("444003392360");
			 * orderInfo.setReturn_tracking_no(""); orderInfo.setOrigincode("");
			 * orderInfo.setDestcode("020LH");
			 * orderInfo.setFilter_result(EcsOrderConsts.SF_FILTER_RESULT_2);
			 * orderInfo.setRemark("下单成功可以派送");
			 * List<zte.net.ecsord.params.sf.vo.OrderInfo> orderInfos = new
			 * ArrayList<zte.net.ecsord.params.sf.vo.OrderInfo>();
			 * orderInfos.add(orderInfo); resp.setOrderInfo(orderInfos);
			 */
			// 测试代码结束
		} catch (Exception e) {// 接口级别异常
			if (resp == null) {
				resp = new NotifyOrderInfoSFResponse();
			}
			resp.setError_code("-99999");
			resp.setError_msg("接口调用异常");
			resp.setErrorCode("-99999");
			resp.setErrorMessage("顺丰接口调用异常");
			e.printStackTrace();
		}

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J92订单筛选接口(暂无用)", summary = "订单系统通过此接口向顺丰企业服务平台发送自动筛单请求,用于判断客户的收,派地址是否属于顺丰的收派范围。顺丰系统会根据收派双方的地址自动判断是否在顺丰的收派范围内。如果属于范围内则返回可收派,否则返回不可收派。")
	public OrderFilterServiceResponse orderFilterServiceSF(OrderFilterServiceRequset req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		param.put("ord_id", req.getNotNeedReqStrOrderId());
		Map map = (Map) caller.invoke("sf.orderFilterService", param);
		OrderFilterServiceResponse rsp = new OrderFilterServiceResponse();
		rsp.setError_code((String) map.get("RespCode"));
		rsp.setError_msg((String) map.get("RespDesc"));
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J93物流状态接收(接受顺丰推送订单路由信息)", summary = "向订单系统定时推送运单的路由信息。需要客户系统提供服务以便顺丰向客户系统推送路由数据。")
	public RoutePushServiceResponse routePushServiceSF(RoutePushServiceRequset request) throws ApiBusiException {
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		RoutePushServiceResponse resp = new RoutePushServiceResponse();
		RuleTreeExeReq req = new RuleTreeExeReq();
		String order_id = "";
		try {
			// 校验
			List<WaybillRoute> wayBillRouteList = request.getWaybillRoute();
			order_id = wayBillRouteList.get(0).getOrderid();
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			if (null == orderTree) {
				orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
			}
			if (null == orderTree) {
				throw new Exception("不存在订单号" + order_id);
			}
			// 删除规则执行日志
			RuleExeLogDelReq delLogReq = new RuleExeLogDelReq();
			delLogReq.setObj_id(order_id);
			delLogReq.setRule_id(EcsOrderConsts.SF_ROUTE_PUSH_GD_RULEID);
			client.execute(delLogReq, RuleExeLogDelResp.class);

			// 执行规则
			req.setRule_id(EcsOrderConsts.SF_ROUTE_PUSH_GD_RULEID);
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			fact.setRequest(request);
			req.setFact(fact);
			RuleTreeExeResp rsp = client.execute(req, RuleTreeExeResp.class);

			// 执行结果
			boolean isExecute = false;// 是否已经执行规则 true -是 flase --否
			List<PlanRule> excList = rsp.getFact().getHasExeRuleList();
			if (excList != null && excList.size() > 0) {
				PlanRule planRule = excList.get(0);
				isExecute = planRule.isExecute();
			}
			if (ConstsCore.ERROR_SUCC.equals(rsp.getError_code()) && isExecute) {// 没调用业务组件也算失败
				Map<String, ZteResponse> respMap = rsp.getFact().getResponses();
				for (String key : respMap.keySet()) {
					BusiCompResponse busRsp = (BusiCompResponse) respMap.get(key);
					resp = (RoutePushServiceResponse) busRsp.getResponse();// 获取response对象数据
					resp.setRespOrderStatus(EcsOrderConsts.SF_INF_SUCC_CODE);
					resp.setRespOrderDesc("成功");
				}
			} else {
				String msg = rsp.getError_msg();
				if (!isExecute) {
					msg = "没有调用业务组件";
				}
				resp.setRespOrderStatus(EcsOrderConsts.SF_INF_FAIL_CODE);
				resp.setRespOrderDesc("调用业务组件失败：" + msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
			resp.setRespOrderStatus(EcsOrderConsts.SF_INF_FAIL_CODE);
			resp.setRespOrderDesc("inf接口异常：" + e.getMessage());
		}
		// 模拟成功-开始
		/*
		 * resp.setId(order_id);
		 * resp.setRespOrderStatus(EcsOrderConsts.SF_INF_SUCC_CODE);
		 * resp.setRespOrderDesc("成功");
		 */
		// 模拟成功-结束
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J93 订单信息变更", summary = "1、\t向订单系统定时推送运单的路由信息。需要客户系统提供服务以便顺丰向客户系统推送路由数据。")
	@ServiceMethod(method = "com.zte.unicomService.ems.routePushService", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public EmsRoutePushServiceResp routePushServiceEms(EmsRoutePushServiceReq req) throws ApiBusiException {

		return null;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J94接收派工号(接收顺丰推送订单人工筛选信息(暂无用))", summary = "接收顺丰推送订单人工筛选信息")
	public ArtificialSelectResponse artificialSelectServiceSF(ArtificialSelectRequest request) throws ApiBusiException {
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		RuleTreeExeReq req = new RuleTreeExeReq();
		RuleTreeExeResp rsp = new RuleTreeExeResp();
		ArtificialSelectResponse resp = new ArtificialSelectResponse();
		try {
			// 数据验证
			req.setRule_id(EcsOrderConsts.SF_ORDER_SREENING_RULEID);
			TacheFact fact = new TacheFact();
			List<OrderFilterResult> orderFilterList = request.getOrderFilterResultList();
			String orderid = orderFilterList.get(0).getOrderid();
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(orderid);
			if (null == orderTree) {
				throw new Exception("不存在订单号" + orderid);
			}
			// 删除规则执行日志
			RuleExeLogDelReq delLogReq = new RuleExeLogDelReq();
			delLogReq.setObj_id(orderid);
			delLogReq.setRule_id(EcsOrderConsts.SF_ORDER_SREENING_RULEID);
			client.execute(delLogReq, RuleExeLogDelResp.class);
			// 执行规则
			fact.setOrder_id(orderid);
			fact.setRequest(request);
			req.setFact(fact);
			rsp = client.execute(req, RuleTreeExeResp.class);

			// 执行结果
			boolean isExecute = false;// 是否已经执行规则 true -是 flase --否
			List<PlanRule> excList = rsp.getFact().getHasExeRuleList();
			if (excList != null && excList.size() > 0) {
				PlanRule planRule = excList.get(0);
				isExecute = planRule.isExecute();
			}
			if (ConstsCore.ERROR_SUCC.equals(rsp.getError_code()) && isExecute) {// 没调用业务组件也算失败
				Map<String, ZteResponse> respMap = rsp.getFact().getResponses();
				for (String key : respMap.keySet()) {
					BusiCompResponse busRsp = (BusiCompResponse) respMap.get(key);
					resp = (ArtificialSelectResponse) busRsp.getResponse();// 获取response对象数据
					resp.setRespOrderStatus(EcsOrderConsts.SF_INF_SUCC_CODE);
					resp.setRespOrderDesc("成功");
				}
			} else {
				String msg = rsp.getError_msg();
				if (!isExecute) {
					msg = "没有调用业务组件";
				}
				resp.setRespOrderStatus(EcsOrderConsts.SF_INF_FAIL_CODE);
				resp.setRespOrderDesc("调用业务组件失败：" + msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setRespOrderStatus(EcsOrderConsts.SF_INF_FAIL_CODE);
			resp.setRespOrderDesc("inf接口异常：" + e.getMessage());
		}

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J95 顺丰物流路由查询", summary = "查询顺丰物流的路由信息")
	public RouteServiceResponse querySfRoute(RouteServiceRequest req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		RouteServiceResponse resp = new RouteServiceResponse();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getTracking_number());
			resp = (RouteServiceResponse) caller.invoke("sf.routeservice", param);
		} catch (Exception e) {
			if (resp == null) {
				resp = new RouteServiceResponse();
			}
			resp.setError_code("-99999");
			resp.setError_msg("接口调用异常");
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J96顺丰订单信息查询", summary = "顺丰订单信息查询")
	public OrderSearchServiceResponse querySfOrder(OrderSearchServiceRequest req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		OrderSearchServiceResponse resp = new OrderSearchServiceResponse();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getOrderid());
			resp = (OrderSearchServiceResponse) caller.invoke("sf.ordersearchservice", param);
		} catch (Exception e) {// 接口级别异常
			if (resp == null) {
				resp = new OrderSearchServiceResponse();
			}
			resp.setError_code("-99999");
			resp.setError_msg("接口调用异常");
			// resp.setErrorMessage("顺丰接口调用异常");
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单分流入口(正式环境)", summary = "沃云购2.0新老系统订单分流,接口机分流")
	public OrderSeparteResp orderSeparterFlow(OrderSeparteReq req) throws ApiBusiException {
		INetCache cache = CacheFactory.getCacheByType("");
		String outer_id = req.getOuter_id();
		Object obj = cache.get("ORD4855671086861487685L" + outer_id);
		logger.info("===========================================OrderSeparteResp orderSeparterFlow(OrderSeparteReq req)" + outer_id);
		req.setApiMethodName("2");
		// 分流到“测试环境”还是“正式环境”:
		// 默认“正式环境”
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
		;
		if (null != obj && obj instanceof PushOrderID2MemcacheReq) {
			PushOrderID2MemcacheReq pushOrderID2MemcacheReq = (PushOrderID2MemcacheReq) obj;
			if (outer_id.equals(pushOrderID2MemcacheReq.getZb_inf_id())) {
				client = null;
				// 满足测试环境条件,分流到“测试环境”
				client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD_TEST);
			}
		}
		OrderSeparteResp resp = client.execute(req, OrderSeparteResp.class);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单二次分流(正式环境|测试环境)", summary = "沃云购2.0新老系统订单分流,接口机分流")
	public OrderSeparteResp orderSeparterFlow2(OrderSeparteReq req) throws ApiBusiException {
		OrderSeparteResp resp = new OrderSeparteResp();
		/*resp.setResp_code(EcsOrderConsts.ORDER_SFLOW_OLD);
		String outer_id = req.getOuter_id();
		// 值为空,缺省新系统做
		if (StringUtil.isEmpty(outer_id)) { 
			resp.setResp_code(EcsOrderConsts.ORDER_SFLOW_NEW);
			return resp;
		}
		logger.info("OrderSeparteResp orderSeparterFlow2(OrderSeparteReq req)===================>" + req.getOuter_id());
		// 获取外部订单编号
		GetOrderByInfIdReq oreq = new GetOrderByInfIdReq();
		oreq.setZb_inf_id(outer_id);
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
		GetOrderByInfIdResp oresp = client.execute(oreq, GetOrderByInfIdResp.class);
		String order_id = oresp.getOrder_id();
		if (StringUtil.isEmpty(order_id)) {
			resp.setResp_code(EcsOrderConsts.ORDER_SFLOW_OLD);
			return resp;
		}
		logger.info("OrderSeparteResp orderSeparterFlow2(OrderSeparteReq req)===================>" + order_id);

		// 记录接口回调的订单和环境信息<<begin
		String source_from = ManagerUtils.getSourceFrom();
		HashMap param = new HashMap();
		param.put("source_from", source_from);
		param.put("order_id", order_id);
		param.put("op_code", req.getOp_code());
		param.put("out_tid", outer_id);
		param.put("zb_inf_id", CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ZB_INF_ID));
		BeanUtils.ordBindEvnLog(param);
		// 记录接口回调的订单和环境信息<<end

		// fact方案对象构造
		String sending_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SENDING_TYPE);
		String ship_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_NAME);
		String buyer_message = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BUYER_MESSAGE);
		TacheFact fact = new TacheFact();
		fact.setSending_type(sending_type);
//		fact.setShip_name(ship_name);
		fact.setComments(buyer_message);
		fact.setRequest(req);
		PlanRuleTreeExeReq planReq = new PlanRuleTreeExeReq();
		planReq.setFact(fact);
		planReq.setPlan_id(EcsOrderConsts.ORDER_SEPARTER_FLOW_2);
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(planReq);

		// 判断新老系统执行与否
		boolean isExec = planResp.isRuleExecute();
		if (isExec)
			resp.setResp_code(EcsOrderConsts.ORDER_SFLOW_NEW);*/
		return resp;

	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "接口执行入口(正式环境)", summary = " 订单分流,接口机调用信息通知新订单系统")
	public OrderInfExecResp orderInfExec(OrderInfExecReq req) throws ApiBusiException {
		String outer_id = req.getOuter_id();
		OrderInfExecResp resp = new OrderInfExecResp();
		try {
			logger.info("OrderInfExecResp orderInfExec(OrderInfExecReq req)===================>" + req.getParam_desc());
			// 总部开户完成通知J105
			if (StringUtil.isEmpty(outer_id) && "zte.net.ecsord.params.zb.req.NotifyOpenAccountGDRequest".equals(req.getOp_req())) {
				NotifyOpenAccountGDRequest notifyOpenAccountGDRequest = JsonUtil.fromJson(req.getParam_desc(), NotifyOpenAccountGDRequest.class);
				outer_id = notifyOpenAccountGDRequest.getOrderId();
			}
			// 总部异常通知J112
			if (StringUtil.isEmpty(outer_id) && "zte.net.ecsord.params.zb.req.NotifyOrderAbnormalToSystemRequest".equals(req.getOp_req())) {
				NotifyOrderAbnormalToSystemRequest notifyOrderAbnormalToSystemRequest = JsonUtil.fromJson(req.getParam_desc(), NotifyOrderAbnormalToSystemRequest.class);
				outer_id = notifyOrderAbnormalToSystemRequest.getOrderId();
			}
			// 总部订单状态同步通知J114
			if (StringUtil.isEmpty(outer_id) && "zte.net.ecsord.params.zb.req.StateSynchronizationToSystemRequest".equals(req.getOp_req())) {
				StateSynchronizationToSystemRequest stateSynchronizationToSystemRequest = JsonUtil.fromJson(req.getParam_desc(), StateSynchronizationToSystemRequest.class);
				List<zte.net.ecsord.params.zb.vo.Order> Orders = stateSynchronizationToSystemRequest.getOrders();
				if (Orders.size() > 0) {
					outer_id = Orders.get(0).getOrderId();
				}
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		logger.info("OrderSeparteResp orderSeparterFlow2(OrderSeparteReq req)===================>" + req.getOuter_id());
		// 获取外部订单编号
		GetOrderByInfIdReq oreq = new GetOrderByInfIdReq();
		oreq.setZb_inf_id(outer_id);
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
		GetOrderByInfIdResp oresp = client.execute(oreq, GetOrderByInfIdResp.class);
		String order_id = oresp.getOrder_id();
		if (StringUtil.isEmpty(order_id)) {
			resp.setResp_code("-1");
			resp.setError_msg("该单号未找到订单：" + outer_id);
			return resp;
		}

		logger.info("===========================================OrderInfExecResp orderInfExec(OrderInfExecReq req)" + outer_id);
		// 记录接口回调的订单和环境信息<<begin
		String source_from = ManagerUtils.getSourceFrom();
		HashMap param = new HashMap();
		param.put("source_from", source_from);
		param.put("order_id", order_id);
		param.put("op_code", req.getOp_code());
		param.put("out_tid", CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID));
		param.put("zb_inf_id", CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ZB_INF_ID));
		BeanUtils.ordBindEvnLog(param);
		// 记录接口回调的订单和环境信息<<end
		req.setApiMethodName("2");
		client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		resp = client.execute(req, OrderInfExecResp.class);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "接口执行二次分流(正式环境|测试环境)", summary = " 订单分流,接口机调用信息通知新订单系统")
	public OrderInfExecResp orderInfExec2(OrderInfExecReq req) throws ApiBusiException {
		OrderInfExecResp ordInfExecResp = new OrderInfExecResp();
		try {
			String op_req = req.getOp_req();
			String op_rsp = req.getOp_rsp();
			String plan_type = req.getPlan_type();
			String param_desc = req.getParam_desc();
			Class<?> clazz_req = Class.forName(op_req);
			logger.info("=======================================>>inf_spare_test_op_req:" + op_req);
			ZteRequest request = (ZteRequest) JsonUtil.fromJson(param_desc, clazz_req);
			Class clazz_rsp = Class.forName(op_rsp);
			logger.info("=======================================>>inf_spare_test_op_req:" + op_rsp);
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			Object obj = clazz_rsp.newInstance();
			obj = client.execute(request, clazz_rsp);
			ZteResponse resp = (ZteResponse) obj;

			// add by wui 0000
			if (EcsOrderConsts.ZB_INF_RESP_CODE_0000.equals(resp.getError_code())) {
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2Map(param, obj);
				String respObj = JsonUtil.toJson(param);
				if (EcsOrderConsts.EXCEPTION_FROM_WMS.equals(plan_type)) {
					ordInfExecResp.setRespObj(respObj);
				} else {
					ordInfExecResp.setRespObj(StringUtils.capitalized(respObj));
				}
				ordInfExecResp.setResp_code("0000");
				ordInfExecResp.setResp_msg("调用成功");
				return ordInfExecResp;
			}

			///
			if (!"-1".equals(resp.getError_code())) {
				ordInfExecResp.setResp_code("-1");
				ordInfExecResp.setResp_msg("调用失败：" + resp.getError_msg());
			} else {
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2Map(param, obj);
				String respObj = JsonUtil.toJson(param);
				if (EcsOrderConsts.EXCEPTION_FROM_WMS.equals(plan_type)) {
					ordInfExecResp.setRespObj(respObj);
				} else {
					ordInfExecResp.setRespObj(StringUtils.capitalized(respObj));
				}
				ordInfExecResp.setResp_code("0000");
				ordInfExecResp.setResp_msg("调用成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ordInfExecResp.setResp_code("-1");
			ordInfExecResp.setResp_msg("调用失败：" + e.getMessage());
		}
		return ordInfExecResp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单Tree基本信息对外提供", summary = "订单Tree基本信息对外提供")
	public OrderTreesInfoResp getOrderTreeInfo(OrderTreesInfoReq req) throws ApiBusiException {
		OrderTreesInfoResp resp = new OrderTreesInfoResp();
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTreeByOutId(req.getOrder_id());
		if (tree == null)
			resp.setError_msg("订单编号无效");
		resp.setOrderBusiRequest(tree.getOrderBusiRequest());
		resp.setOrderExtBusiRequest(tree.getOrderExtBusiRequest());
		resp.setOrderItemBusiRequests(tree.getOrderItemBusiRequests());
		return resp;
	}

	@Override
	public SimulatorDDGJResp simulatorDDGJ(SimulatorDDGJReq req) throws ApiBusiException {

		String orderid = "ZQ201501144099143323";
		// CommonDataFactory.getInstance().updateOrderTree(orderid);
		// String AGENT_CODE =
		// CommonDataFactory.getInstance().getAttrFieldValue(orderid,
		// "agent_code_dls");
		// String AGENT_NAME =
		// CommonDataFactory.getInstance().getAttrFieldValue(orderid,
		// "agent_name");
		// String AGENT_CITY =
		// CommonDataFactory.getInstance().getAttrFieldValue(orderid,
		// "agent_city");
		// String AGENT_DISTRICT =
		// CommonDataFactory.getInstance().getAttrFieldValue(orderid,
		// "agent_district");
		// String CHANNEL_TYPE =
		// CommonDataFactory.getInstance().getAttrFieldValue(orderid,
		// "channel_type");
		// String zb_inf_id =
		// CommonDataFactory.getInstance().getAttrFieldValue(orderid,
		// "zb_inf_id");
		//
		// ZteClient client46 =
		// ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		// AgencyAcctPayNotifyReq req46 = new AgencyAcctPayNotifyReq();
		// req46.setNotNeedReqStrOrderId(orderid);
		// req46.setNotNeedReqTRADE_TYPE("0");
		// req46.setPAY_FEE("100");
		// req46.setORDER_ID("6115010602066519");
		// req46.setORG_ORDER_ID("");
		// AgencyAcctPayNotifyRsp resp46 = client46.execute(req46,
		// AgencyAcctPayNotifyRsp.class);

		/*
		 * ICacheUtil util =
		 * (ICacheUtil)SpringContextHolder.getBean("cacheUtil"); String
		 * order_flow_control = util.getConfigInfo("ORDER_FLOW_CONTROL");
		 * //控制仅测试环境执行_本地缓存中是否设置了“ORDER_FLOW_CONTROL=true”
		 * if(!StringUtil.isEmpty(order_flow_control) &&
		 * "false".equals(order_flow_control)){ // String orderId =
		 * order.getOrder_id(); String zbOrderId =
		 * CommonDataFactory.getInstance().getAttrFieldValue(orderid,
		 * AttrConsts.ZB_INF_ID); //测试环境调用接口将订单缓存到正式环境的memcache中
		 * if(!StringUtil.isEmpty(zbOrderId)){ PushOrderID2MemcacheReq
		 * pushOrderID2MemcacheReq = new PushOrderID2MemcacheReq();
		 * pushOrderID2MemcacheReq.setZb_inf_id(zbOrderId); ZteClient client =
		 * ClientFactory.getZteRopClient(AppKeyEnum.
		 * APP_KEY_WSSMALL_ECSORD_RELESE); PushOrderID2MemcacheResp
		 * pushOrderID2MemcacheResp = client.execute(pushOrderID2MemcacheReq,
		 * PushOrderID2MemcacheResp.class); int i=0; i++; } }
		 */

		// if(true)
		// return new SimulatorDDGJResp();

		SimulatorDDGJResp resp = new SimulatorDDGJResp();
		JSONObject order_req = null;
		JSONObject jsonObject = null;
		String url = req.getSevletPath();
		if ("json".equals(req.getFormat())) {
			String json = req.getReqStr();
			jsonObject = JSONObject.fromObject(json);
			order_req = jsonObject;// jsonObject.getJSONObject("order_req");
		}

		String order_id = "";
		if (!"httpPost".equals(req.getReqType()) && "json".equals(req.getFormat()) && order_req != null) {
			try {
				order_id = (String) order_req.get("OrderId");
			} catch (Exception e) {
			}
		}
		String result_str = "";
		if ("ddgj_zb".equals(req.getReqType())) {
			String outPackage = null;
			try {
				outPackage = new PeripheryHttpClient().doPost(url, jsonObject.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ("ddgj".equals(req.getReqType())) {
			try {
				String resultStr = HttpClientUtil.getResult(url, jsonObject.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// %E8%AE%A2%E5%8D%95%E7%BC%96%E5%8F%B7%2F%E8%AE%A2%E5%8D%95ID
		if ("scrapy".equals(req.getReqType())) {
			url = "http://admin.mall.10010.com/Odm/OrderQuery/query?orderNo=" + order_id
					+ "&orderState=100&page.webPager.action=refresh&page.webPager.currentPage=1&page.webPager.pageInfo.pageSize=5&page.webPager.pageInfo.totalSize=130&pageSize=5&preNum=18578324394&provinceCode=51";
			try {
				String resultStr = HttpClientUtil.getResult(url, "");
				logger.info(resultStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ("httpPost".equals(req.getReqType()) && "json".equals(req.getFormat())) {// httpPost模拟请求
			try {
				result_str = HttpClientUtil.getResult(url, jsonObject.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ("httpPost".equals(req.getReqType()) && "xml".equals(req.getFormat())) {// xml
																					// 请求报文
																					// httpPost模拟请求
			try {
				result_str = HttpClientUtil.getResult(url, req.getReqStr());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		StringBuffer returuninfo = new StringBuffer();
		if ("ddtd".equals(req.getReqType())) {
			String orders = req.getReqStr();
			if (com.ztesoft.api.internal.utils.StringUtils.isEmpty(orders))
				return null;
			String[] ordersIdArr = orders.split(",");
			// 订单管理系统将订单的状态信息推送到总部系统
			for (String zbOrderIdAndSourceFrom : ordersIdArr) {
				BatchRefundReq batchRefundReq = new BatchRefundReq();
				batchRefundReq.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_REASON_CANLODR);
				if (-1 == zbOrderIdAndSourceFrom.indexOf("@"))
					continue;
				String[] tmpStrs = zbOrderIdAndSourceFrom.split("@");
				final String zbOrderId = tmpStrs[0];
				batchRefundReq.setNotNeedReqStrSourceFrom(tmpStrs[1]);
				batchRefundReq.setNotNeedReqStrOrderId(zbOrderId);
				CommCaller caller = new CommCaller();
				StateSynToZBResponse rsp = new StateSynToZBResponse();
				Map<String, Object> param = new HashMap<String, Object>();
				try {
					BeanUtils.bean2Map(param, batchRefundReq);
					param.put("ord_id", zbOrderId);
					rsp = (StateSynToZBResponse) caller.invoke("periphery.orderStateChange", param);
					returuninfo.append("订单号:").append(zbOrderId).append("返回信息:{[").append(rsp.getRespCode()).append("]:")
							.append(null == rsp.getOrders() || rsp.getOrders().isEmpty() ? "" : rsp.getOrders().get(0).getRespDesc()).append("}    ");
				} catch (Exception e) {
					rsp.setRespCode("-99999");
					rsp.setRespDesc("状态同步异常");
					e.printStackTrace();
				}
				// 更新[es_order_batch_archive.notify_zb]状态
				ChargebackStatuChgReq chargebackStatuChgReq = new ChargebackStatuChgReq();
				if (EcsOrderConsts.INF_RESP_CODE_0000.equals(rsp.getRespCode())) {
					chargebackStatuChgReq.setFlag("notify_zb");
				} else {
					chargebackStatuChgReq.setFlag("zb_exception");
				}
				chargebackStatuChgReq.setZb_order_id(zbOrderId);
				ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
				client.execute(chargebackStatuChgReq, ChargebackStatuChgResp.class);
			}
			order_id = returuninfo.toString();
		}
		resp.setOrder_id("out_tid:" + order_id);
		resp.setResult_str(result_str);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单信息查询接口", summary = "接收外部订单查询请求,返回订单信息")
	public OrderInfoResp queryOrderInfo(OrderInfoReq req) throws ApiBusiException {
		// 返回对象 设置返回对象-固定信息
		OrderInfoResp resp = new OrderInfoResp();
		resp.setActive_no(req.getActive_no());
		resp.setRsp_code(InfConst.RESP_CODE_SUCCESS);
		resp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);
		// 判断指定第三方系统编码,如果为null,则直接返回
		if (com.ztesoft.api.internal.utils.StringUtils.isEmpty(req.getThird_sys_code())) {
			resp.setRsp_msg("没有指定第三方系统编码!");
			resp.setError_msg(resp.getRsp_msg());
			return resp;
		}
		// 判断指定查询结果,如果为null,则直接返回
		if (com.ztesoft.api.internal.utils.StringUtils.isEmpty(req.getResult_type())) {
			resp.setRsp_msg("没有指定查询结果集!");
			resp.setError_msg(resp.getRsp_msg());
			return resp;
		}
		// 判断指定返回数量,超过100则直接返回
		if (com.ztesoft.api.internal.utils.StringUtils.isEmpty(req.getOut_order_id())// 指定订单号则不必限定数量
				&& null != req.getPage_size() && 100 < req.getPage_size()) {// 未传页面大小系统会默认10,传了页面大小则要限定上限100
			resp.setRsp_msg("查询数量不能超过100!");
			resp.setError_msg(resp.getRsp_msg());
			return resp;
		}
		// 获取订单信息
		req.setApi_method("zte.net.ecsord.params.attr.req.getOrderInfo");
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
		OrderInfoResp oresp = client.execute(req, OrderInfoResp.class);
		// 未找到订单
		if (null == oresp.getOrderInfo() || oresp.getOrderInfo().isEmpty()) {
			resp.setRsp_msg("未找到订单;请检查请求参数并稍后重试!");
		} else {
			resp.setOrderInfo(oresp.getOrderInfo());
			resp.setRsp_msg("查询成功!");
		}
		resp.setError_msg(resp.getRsp_msg());
		// 测完即删
		// ObjectMapper mapper = new ObjectMapper();
		// try {
		// logger.info( mapper.writeValueAsString(resp));
		// } catch (Exception outerErr) {
		// Map<String,String> map = new LinkedHashMap<String,String>();
		// try {
		// BeanUtils.beanToMap(map, resp);
		// } catch (Exception InnerErr) {
		// InnerErr.printStackTrace();
		// }
		// logger.info(CommonTools.beanToJson(map));
		// }
		// 测完即删
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "测试单退单", summary = "测试单退单")
	public StateSynToZBResponse batchRefund(BatchRefundReq req) throws ApiBusiException {
		StateSynToZBResponse resp = new StateSynToZBResponse();
		CommCaller caller = new CommCaller();
		StateSynToZBResponse rsp = new StateSynToZBResponse();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			rsp = (StateSynToZBResponse) caller.invoke("periphery.orderStateChange", param);
		} catch (Exception e) {
			rsp.setRespCode("-99999");
			rsp.setRespDesc("状态同步异常");
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J47商城收费,退款成功触发接口", summary = "商城收费,退款成功触发接口")
	public FeeInformResp feeInform(FeeInformReq feeInformReq) throws ApiBusiException {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(feeInformReq.getNotNeedReqStrOrderId());
		String order_id = "";
		if (null != orderTree) {
			order_id = orderTree.getOrder_id();
		}
		if (null == order_id || "".equals(order_id)) {
			CommonTools.addBusiError("-1", "获取order_id失败");
		}
		GDBssSocketHead gDBssSocketHead = new GDBssSocketHead();
		gDBssSocketHead.setA2(String.format("%-20s", order_id));
		gDBssSocketHead.setA4(InfConst.FEE_INFORM_SERVICE_TYPE);
		gDBssSocketHead.setA5(String.format("%-20s", CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM)));
		feeInformReq.setgDBssSocketHead(gDBssSocketHead);
		feeInformReq.setxTag("0");
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, feeInformReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 计算包体长度
		int length = 0;
		for (Entry<String, Object> entry : param.entrySet()) {
			if (entry.getValue() instanceof String) {
				length += String.valueOf(entry.getValue()).getBytes().length;
			}
		}
		((Map<String, String>) param.get("gDBssSocketHead")).put("a1", String.format("%-5s", length + 86));
		CommCaller caller = new CommCaller();
		FeeInformResp resp = (FeeInformResp) caller.invoke("bss.feeInformBSS", param);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J48订单开户,返销完工触发接口", summary = "订单开户,返销完工触发接口")
	public OrderAccountOrBuybackInformResp orderAccountOrBuybackInform(OrderAccountOrBuybackInformReq orderAccountOrBuybackInformReq) throws ApiBusiException {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(orderAccountOrBuybackInformReq.getNotNeedReqStrOrderId());
		String order_id = "";
		if (null != orderTree) {
			order_id = orderTree.getOrder_id();
		}
		if (null == order_id || "".equals(order_id)) {
			CommonTools.addBusiError("-1", "获取order_id失败");
		}
		GDBssSocketHead gDBssSocketHead = new GDBssSocketHead();
		gDBssSocketHead.setA2("odrder" + new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()));
		gDBssSocketHead.setA4(InfConst.ORDER_ACCOUNTORBUYBACK_INFORM_SERVICE_TYPE);
		gDBssSocketHead.setA5(String.format("%-20s", CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM)));
		orderAccountOrBuybackInformReq.setgDBssSocketHead(gDBssSocketHead);
		// 开户设置开户时间,返销设置返销受理时间
		if (EcsOrderConsts.ORDER_ACCOUNT.equals(orderAccountOrBuybackInformReq.getxTag())) {
			orderAccountOrBuybackInformReq
					.setAcceptDate(CommonDataFactory.getInstance().getAttrFieldValue(orderAccountOrBuybackInformReq.getNotNeedReqStrOrderId(), AttrConsts.BSS_ACCOUNT_TIME).replaceAll("\\D*", ""));
		} else if (EcsOrderConsts.ORDER_BUYBACK.equals(orderAccountOrBuybackInformReq.getxTag())) {
			orderAccountOrBuybackInformReq.setAcceptDate(orderTree.getOrderExtBusiRequest().getLast_deal_time().replaceAll("\\D*", ""));
		}
		Map<String, Object> param = new HashMap<String, Object>();
		OrderAccountOrBuybackInformResp resp = new OrderAccountOrBuybackInformResp();
		try {
			BeanUtils.bean2Map(param, orderAccountOrBuybackInformReq);
			// 计算包体长度
			int length = 0;
			for (Entry<String, Object> entry : param.entrySet()) {
				if (entry.getValue() instanceof String) {
					length += String.valueOf(entry.getValue()).getBytes().length;
				}
			}
			((Map<String, String>) param.get("gDBssSocketHead")).put("a1", String.format("%-5s", length + 86));
			CommCaller caller = new CommCaller();
			resp = (OrderAccountOrBuybackInformResp) caller.invoke("bss.orderAccountOrBuybackInformBSS", param);
			final boolean isSuccess = null != resp && !com.ztesoft.api.internal.utils.StringUtils.isEmpty(resp.getxCheckInfo())
					&& resp.getxCheckInfo().substring(0, resp.getxCheckInfo().length() - 1).endsWith(EcsOrderConsts.INF_RESP_CODE_0000);
			if (isSuccess) {
				resp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);
			} else {
				resp.setError_code("-1");
				resp.setError_msg("通知失败!");
			}
		} catch (Exception e) {
			resp.setError_code("-99999");
			resp.setError_msg("接口调用异常");
			e.printStackTrace();
		}

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "测试环境通过此接口将订单缓存到正式环境的memcache中", summary = "测试环境通过此接口将订单缓存到正式环境的memcache中")
	public PushOrderID2MemcacheResp pushOrderID2Memcache(PushOrderID2MemcacheReq request) throws ApiBusiException {
		PushOrderID2MemcacheResp resp = new PushOrderID2MemcacheResp();
		INetCache cache = CacheFactory.getCacheByType("");
		cache.set("ORD4855671086861487685L" + request.getZb_inf_id(), request);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "CBSS-移网产品服务变更", summary = "CBSS-移网产品服务变更")
	public MobileNetworkServiceHandleResp mobileNetworkServiceHandle(MobileNetworkServiceHandleReq req) throws ApiBusiException {
		MobileNetworkServiceHandleResp resp = new MobileNetworkServiceHandleResp();
		try {
			String acc_nbr = CommonDataFactory.getInstance().getAttrFieldValue(req.getOrderid(), AttrConsts.PHONE_NUM);
			AbsCbssInvoker<MobileNetworkServiceHandleReq, MobileNetworkServiceHandleResp> cbssInvoker = null;
			// 4G省内闲时流量包附加产品 [附加产品] 办理 | 测试号码: 18566412058 18689302832
			// 18578324321 18566413906
			// if("a".equals(req.getPackFlag()) ||
			// "b".equals(req.getPackFlag())){
			cbssInvoker = new FourGIdlesseTrafficPackCbssInvoker();
			req.setMobileNo(acc_nbr);
			cbssInvoker.setZteRequest(req);
			// }
			cbssInvoker.invoker(req.getOrderid());
			resp.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
		} catch (Exception e) {
			resp.setError_code("-99999");
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "CBSS-SP订购", summary = "CBSS-SP订购")
	public SPBuyServiceHandleResp spBuyServiceHandle(SPBuyServiceHandleReq req) throws ApiBusiException {
		SPBuyServiceHandleResp resp = new SPBuyServiceHandleResp();
		try {
			AbsCbssInvoker<SPBuyServiceHandleReq, SPBuyServiceHandleResp> cbssInvoker = new SPBuyServiceHandleCbssInvoker();
			cbssInvoker.setZteRequest(req);
			cbssInvoker.invoker(req.getOrderid());
			resp.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
		} catch (Exception e) {
			resp.setError_code("-99999");
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "CBSS-系统登录认证", summary = "CBSS-系统登录认证")
	public HttpLoginClient toLoginAndCert(CBssLoginCertReq req) {
		HttpLoginClient client = null;
		LoginInfo info = new LoginInfo();
		try {
			info.setCbssAccount(req.getLoginUser());
			info.setCbssPassword(req.getLoginPsw());
			info.setProvinceCode(req.getProvinceCode()); // 省份代码（默认"51"广东）
			client = HttpLoginClientPool.add(info); // 登录CBSS系统
			CustomBill bill = new CustomBill();
			bill.setPsptId(req.getPsptId()); // 身份证号
			bill.setCustomName(req.getCustomName()); // 客户姓名（对应身份上姓名）
			bill.setContact(req.getContact()); // 开户人姓名（机主姓名）
			bill.setContactPhone(req.getContactPhone()); // 开户号码
			bill.setPsptEndDate(req.getPsptEndDate()); // 身份证有效期
			bill.setIdTypeCode(req.getIdTypeCode()); // 证件类型（默认1-身份证）
			bill.setPhone(req.getPhone()); // 收货人电话
			bill.setPostAddress(req.getPostAddress()); // 通信地址必须大于八个字符
			bill.setSerialNumber(req.getSerialNumber()); // 设置要办理业务号码 真实环境需要改代码
			CustomInfo customInfo = CreateCustomHandler.emulate(bill, client); // 客户认证
			client.getParam().setCustomInfo(customInfo);
		} catch (Exception e) {
			throw new CodedException("8002", "客户认证失败!");
		}
		return client;
	}

	// 物流模式处理方式
	private ZteResponse processWlsc(OrderAKeyActReq req, String order_id) {
		ZteResponse response = new ZteResponse();
		String out_id = req.getOrder_id();
		try {
			String errorMsg = ""; // 错误描述
			String oldSendType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SENDING_TYPE); // 配送方式
			// 保存物流数据
			String synOrdZb = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYN_ORD_ZB);
			if (!StringUtil.isEmpty(req.getLogi_fields())) {
				List<Map<String, String>> logiFieldsList = CommonTools.jsonToList(req.getLogi_fields(), HashMap.class);
				if (null != logiFieldsList && !logiFieldsList.isEmpty()) { // 存储物流变更信息
					String fieldNames = ""; // 物流字段
					String fieldValues = "";
					for (int i = 0; i < logiFieldsList.size(); i++) {
						Map<String, String> logiField = logiFieldsList.get(i);
						String field_name = Const.getStrValue(logiField, "field_name");
						String field_value = Const.getStrValue(logiField, "field_value");
						if (StringUtil.isEmpty(field_name)) {
							errorMsg = "错误数据[{\"field_desc\":\"" + Const.getStrValue(logiField, "field_desc") + "\",\"field_name\":\"" + field_name + "\", \"field_value\":\" " + field_value
									+ " \"}];";
						}
						// 不能为空的字段
						if (AttrConsts.SHIP_NAME.equals(field_name) || AttrConsts.PROVINCE_CODE.equals(field_name) || AttrConsts.CITY_CODE.equals(field_name) || "district_id".equals(field_name)
								|| AttrConsts.SHIP_ADDR.equals(field_name) || AttrConsts.REVEIVER_MOBILE.equals(field_name)) {
							if (StringUtil.isEmpty(field_value))
								errorMsg = "错误数据[{\"field_desc\":\"" + Const.getStrValue(logiField, "field_desc") + "\",\"field_name\":\"" + field_name + "\", \"field_value\":\" " + field_value
										+ " \"}]不能为空;";
						}

						// 检查配送方式
						if (AttrConsts.SENDING_TYPE.equals(field_name)) {
							if (EcsOrderConsts.SHIPPING_TYPE_XJ.equals(field_value))
								errorMsg = "配送方式不能为：" + EcsOrderConsts.SHIPPING_TYPE_XJ + "[{\"field_desc\":\"" + Const.getStrValue(logiField, "field_desc") + "\",\"field_name\":\"" + field_name
										+ "\", \"field_value\":\" " + field_value + " \"}];";
						}

						if (!StringUtil.isEmpty(field_value) || AttrConsts.POST_FEE.equals(field_name) || AttrConsts.N_SHIPPING_AMOUNT.equals(field_name)) {
							// 不同名称字段处理
							if (field_name.equals("logi_company")) {
								field_name = AttrConsts.SHIPPING_COMPANY_NAME;
							} else if (field_name.equals("district_id")) {
								field_name = AttrConsts.AREA_CODE;
							}
							// 需要转换的字段处理
							if (AttrConsts.POST_FEE.equals(field_name) || AttrConsts.N_SHIPPING_AMOUNT.equals(field_name)) {
								if (StringUtil.isEmpty(field_value)) {
									field_value = "0.00";
								} else {
									Double fee = Double.valueOf(field_value) / 1000.0;
									DecimalFormat df = new DecimalFormat("###0.00");
									field_value = df.format(fee);
								}
							}
							fieldNames += field_name + ",";
							fieldValues += field_value + ",";
						}
						if (!StringUtil.isEmpty(errorMsg))
							break;
					}
					if (!StringUtil.isEmpty(fieldNames) && StringUtil.isEmpty(errorMsg)) {
						String[] field_names = fieldNames.split(",");
						String[] filed_values = fieldValues.split(",");
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, field_names, filed_values);
					}
				}
			}
			if (!StringUtil.isEmpty(errorMsg)) {
				response.setError_code(ConstsCore.ERROR_FAIL);
				response.setError_msg("转物流模式处理失败:[" + out_id + "]," + errorMsg);
				return response;
			}
			// 订单系统处理
			if (EcsOrderConsts.SYN_ORD_ZB_1.equals(synOrdZb)) { // 已同步总部
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ORDER_MODEL, AttrConsts.OLD_SENDING_TYPE, AttrConsts.SENDING_TYPE, AttrConsts.NEED_SHIPPING },
						new String[] { EcsOrderConsts.ORDER_MODEL_02, oldSendType, EcsOrderConsts.SHIP_TYPE_KD, EcsOrderConsts.NEED_SHIPPING_1 });
				// 转手工开户
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				OffLineOpenActReq actReq = new OffLineOpenActReq();
				actReq.setOrder_id(order_id);
				client.execute(actReq, OffLineOpenActResp.class);
			} else { // 未同步到总部
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ORDER_MODEL }, new String[] { EcsOrderConsts.ORDER_MODEL_05 });
			}
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_REFRESH }, new String[] { EcsOrderConsts.IS_DEFAULT_VALUE });
			response.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
			response.setError_msg("转物流模式处理成功[" + out_id + "]！");
		} catch (Exception e) {
			e.printStackTrace();
			response.setError_code(ConstsCore.ERROR_FAIL);
			response.setError_msg("转物流模式处理失败:[" + out_id + "]" + e.getMessage());
		}
		return response;
	}

	// @Override
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "CBSS-SP订购", summary = "CBSS-SP订购")
	public SPBuyCBssOutResp spBuyServiceHandle(SPBuyCBssOutReq req) throws ApiBusiException {
		SPBuyCBssOutResp resp = new SPBuyCBssOutResp();
		try {
			AbsCbssInvokerOut invoker = new AbsCbssInvokerOutImpl();
			invoker.spBuyBusiness(req);
			resp.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
		} catch (Exception e) {
			resp.setError_code("-99999");
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "CBSS-移网", summary = "CBSS-移网")
	public MobileCBssOutResp MobileServiceHandle(MobileCBssOutReq req) throws ApiBusiException {
		MobileCBssOutResp resp = new MobileCBssOutResp();
		try {
			AbsCbssInvokerOut invoker = new AbsCbssInvokerOutImpl();
			invoker.mobileBusiness(req);
			resp.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resp.setError_code("-99999");
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J11:订单流转到旧订单系统", summary = "订单流转到旧订单系统")
	public NotifyOrderInfo2OldSysResponse orderInfo2OldOrderSys(NotifyOrderInfo2OldSysRequest req) throws ApiBusiException {
		NotifyOrderInfo2OldSysResponse resp = new NotifyOrderInfo2OldSysResponse();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			// 调用接口时候需外部设置:processType,tarFlowNode;autoExceptionSystem
			BeanUtils.bean2Map(param, req);
			param.remove("inf_log_id");
			param.remove("dyn_field");
			logger.info("环节跳转通知老系统：" + param.toString());
			// param.put("origOrderId", "FSZBWO20141219031169");
			Map map = (Map) caller.invoke("oldOrderSys.2OrderInfo", param);
			resp.setError_code(Const.getStrValue(map, "resp_code"));
			resp.setError_msg(Const.getStrValue(map, "resp_msg"));
			resp.setActiveNo(Const.getStrValue(map, "activeNo"));
		} catch (Exception e) {
			resp.setError_code("-9999");
			resp.setError_msg("调用接口异常:" + e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}

	@Override
	public TaobaoIdentityGetResponse getIdentityFromTaobao(TaobaoIdentityGetRequest req) {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		AlibabaAliqinTccTradeIdentityGetRequest identityReq = new AlibabaAliqinTccTradeIdentityGetRequest();
		identityReq.setSellerNick(req.getShop_name());
		identityReq.setBizOrderId(Long.parseLong((String) param.get("tid")));
		params.put("ord_id", req.getNotNeedReqStrOrderId());
		params.put("request", identityReq);
		params.put("ep_mall", CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.ORDER_FROM));
		AlibabaAliqinTccTradeIdentityGetResponse identityResp = (AlibabaAliqinTccTradeIdentityGetResponse) caller.invoke("tb.getIdentity", params);
		TaobaoIdentityGetResponse resp = new TaobaoIdentityGetResponse();
		if (identityResp == null) {
			resp.setError_code("-1");
			resp.setError_msg("调用失败");
		} else {
			IdentityInfo identity = identityResp.getResult();
			if (null == identity) {
				resp.setError_code("-1");
				resp.setError_msg("调用失败");
			} else {
				resp.setError_code("0000");
				resp.setError_msg("调用成功");
				try {
					BeanUtils.copyProperties(resp, identity);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// 请求url获取流
		if ("0000".equals(resp.getError_code())) {
			Map<String, String> urlMap = new HashMap<String, String>();
			urlMap.put("frontImageUrl", resp.getFrontImageUrl());
			urlMap.put("backImageUrl", resp.getBackImageUrl());
			urlMap.put("holdImageUrl", resp.getHoldImageUrl());

			BASE64Encoder encoder = new BASE64Encoder();
			ByteArrayOutputStream baos = null;
			BufferedInputStream bis = null;
			HttpURLConnection httpUrl = null;
			URL url = null;
			// logger.info();
			try {
				Iterator<String> it = urlMap.keySet().iterator();
				//logger.info();
				while (it.hasNext()) {
					//logger.info();
					String key = it.next();
					String imageurl = urlMap.get(key);
					if (!StringUtils.isEmpty(imageurl)) {
						url = new URL(imageurl);
						httpUrl = (HttpURLConnection) url.openConnection();
						// logger.info("获取淘宝证件图片信息·证件照对比:key="+key+"########imageurl="+imageurl);
						httpUrl.connect();
						// logger.info("获取淘宝证件图片信息·证件照对比:httpUrl.connect()
						// 成功");
						bis = new BufferedInputStream(httpUrl.getInputStream());
						BufferedImage bi = ImageIO.read(bis);
						baos = new ByteArrayOutputStream();
						ImageIO.write(bi, "jpg", baos);
						byte imgdata[] = baos.toByteArray();
						urlMap.put(key, encoder.encodeBuffer(imgdata).trim());
					}
				}
				BeanUtils.copyProperties(resp, urlMap);
				// logger.info("获取淘宝证件图片信息·证件照对比:照片翻译成功");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					baos.close();
					bis.close();
					httpUrl.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
			resp.setError_msg("获取证件图片成功");
		} else {
			resp.setError_code("-1");
			resp.setError_msg("获取证件图片失败");
		}
		return resp;
	}

	/**
	 * 号码状态 变更(总部aop)
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "号码状态变更", summary = "号码状态变更")
	public NumberStateChangeZBResponse numberStateChange(NumberStateChangeZBRequest req) throws ApiBusiException {
		NumberStateChangeZBResponse rsp = new NumberStateChangeZBResponse();
		try {
			// 获取工号信息
			String operFlag = req.getOperFlag();
			String orderId = req.getNotNeedReqStrOrderId();
			EmpIdEssIDResp essRspInfo = new EmpIdEssIDResp();

			// 如果是释放页面过来的请求,则不需要调工号查询,传过来就会有,如果工号信息确实,调用之前就会报错了
			if (EcsOrderConsts.OCCUPIEDFLAG_99.equals(operFlag)) {
				essRspInfo.setError_code("0");
				essRspInfo.setOperInfo(req.getEssOperInfo());
			} else {// 如果是环节操作,则根据订单号获取锁定工号和地市，再根据这些信息获取绑定的ess工号详情
				essRspInfo = CommonDataFactory.getEssInfoByOrderId(orderId);
			}

			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.PHONE_NUM_OCCUPLY_SIMPLE;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (NumberStateChangeZBResponse) caller.invoke(opcode, param);
				if (rsp.getResourcesRsp() != null && rsp.getResourcesRsp().size() > 0) {
					rsp.setCode(EcsOrderConsts.AOP_SUCCESS_0000);
					rsp.setDetail("资源可用");
					// 批量变更时，有一个失败，规则就当失败
					for (ResourecsRsp r : rsp.getResourcesRsp()) {
						if (!StringUtils.equals(r.getRscStateCode(), EcsOrderConsts.AOP_SUCCESS_0000)) {
							rsp.setCode(r.getRscStateCode());
							rsp.setDetail(r.getRscStateDesc());
						}
					}
				} else {// 业务异常
					rsp.setCode(EcsOrderConsts.AOP_ERROR_9999);
					rsp.setDetail(rsp.getError_msg()+rsp.getDetail());
					/*if (!StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)) {
		                // 标记异常
		                // Utils.markException(order_id,false);
		                Utils.markException(order_id, false, "", result.getError_msg());
		            } else {
		                OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
		                orderItemExtBusiRequest.setEss_pre_status(EcsOrderConsts.ESS_PRE_STATUS_0);
		                orderItemExtBusiRequest.setEss_pre_desc("错误编码：" + rsp.getCode() + ";错误信息：" + rsp.getDetail());
		                orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		                orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		                orderItemExtBusiRequest.store();
		            }*/
				}
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 号码状态 变更(bss)
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "bss号码状态变更", summary = "bss号码状态变更")
	public NumberStateChangeBssResp numberStateChangeBss(NumberStateChangeBssRequest req) throws ApiBusiException {
		NumberStateChangeBssResp rsp = new NumberStateChangeBssResp();
		try {
			// 获取工号信息
			String operFlag = req.getOperFlag();
			String orderId = req.getNotNeedReqStrOrderId();
			EmpIdEssIDResp essRspInfo = new EmpIdEssIDResp();

			// 如果是释放页面过来的请求,则不需要调工号查询,传过来就会有,如果工号信息确实,调用之前就会报错了
			if (StringUtils.equals(EcsOrderConsts.OCCUPIEDFLAG_99, operFlag)) {
				essRspInfo.setError_code("0");
				essRspInfo.setOperInfo(req.getEssOperInfo());
			} else {// 如果是环节操作,则根据订单号获取锁定工号和地市，再根据这些信息获取绑定的ess工号详情
				essRspInfo = CommonDataFactory.getEssInfoByOrderId(orderId);
			}

			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setResp_code("-9999");
				rsp.setResp_msg(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());
				rsp = (NumberStateChangeBssResp) caller.invoke("bss.numberStateChangeBSS", param);
			}
		} catch (Exception e) {
			rsp.setError_code("-9999");
			rsp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 号码资源查询(新商城)
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "号码资源查询", summary = "号码资源查询")
	public NumberResourceQueryWYGResponse numberResourceQueryWYG(NumberResourceQueryWYGRequest req) throws ApiBusiException {
		NumberResourceQueryWYGResponse rsp = new NumberResourceQueryWYGResponse();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = "WYG.numberResourceQuery";
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);

			rsp = (NumberResourceQueryWYGResponse) caller.invoke(opcode, param);
		} catch (Exception e) {
			rsp.setResp_code("-9999");
			rsp.setResp_msg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 号码变更通知新商城
	 * 
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "号码变更", summary = "号码变更")
	public NotifyOrderInfoWYGResponse numberChangeWYG(NumberChangeWYGRequest req) throws ApiBusiException {
		NotifyOrderInfoWYGResponse rsp = new NotifyOrderInfoWYGResponse();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = "WYG.NumberChange";
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);

			rsp = (NotifyOrderInfoWYGResponse) caller.invoke(opcode, param);
			if (rsp.getResp_code() == null) {
				rsp.setResp_code("0");
				rsp.setResp_msg("变更成功");
			}
		} catch (Exception e) {
			rsp.setResp_code("-9999");
			rsp.setResp_msg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * ESS工号查询
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]ESS工号查询", summary = "ESS工号查询")
	public EmployeesInfoResponse employeesInfoCheck(EmployeesInfoCheckRequest req) throws ApiBusiException {
		EmployeesInfoResponse rsp = new EmployeesInfoResponse();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = EcsOrderConsts.ESS_INFO_QUERY;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);

			rsp = (EmployeesInfoResponse) caller.invoke(opcode, param);
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 发展人查询
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]发展人查询", summary = "发展人查询")
	public DevelopPersonResponse devrPersonQuery(DevelopPersonQueryReq req) throws ApiBusiException {
		DevelopPersonResponse rsp = new DevelopPersonResponse();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = EcsOrderConsts.DEVOLOP_INFO_QUERY;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);
			rsp = (DevelopPersonResponse) caller.invoke(opcode, param);
		} catch (Exception e) {
			e.printStackTrace();
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
		}
		return rsp;

	}

	/**
	 * 客户信息查询
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]客户信息查询", summary = "客户信息查询")
	public CustomerInfoResponse customerInfoQuery(CustomerInfoCheckReq req) throws Exception {
		CustomerInfoResponse rsp = new CustomerInfoResponse();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.CHECK_CUST_INFO_MUTI;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (CustomerInfoResponse) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
		}
		return rsp;
	}

	/**
	 * 客户信息校验
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[BSS]客户信息查询校验", summary = "[BSS]客户信息查询校验")
	public BSSCustomerInfoResponse customerInfoQueryBSS(BSSCustomerInfoCheckReq req) throws ApiBusiException {
		BSSCustomerInfoResponse rsp = new BSSCustomerInfoResponse();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setError_code("-9999");
				rsp.setError_msg(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.CHECK_CUST_INFO_MUTI_BSS;

				rsp = (BSSCustomerInfoResponse) caller.invoke(opcode, param);
				// 设置返回错误信息
				if (null != rsp) {
					if (EcsOrderConsts.BSS_ANSWER_CODE_0000.equals(rsp.getError_code())) {
						rsp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);
						rsp.setError_msg("成功");
					} else {
						rsp.setError_code("-9999");
						rsp.setError_msg(rsp.getError_msg() + "," + rsp.getRespDesc());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			rsp.setRespCode("-9999");
			rsp.setRespDesc(e.getMessage());
			rsp.setError_code("-9999");
			rsp.setError_msg(e.getMessage());
		}
		return rsp;
	}

	/**
	 * 终端状态查询变更
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]终端状态查询变更", summary = "终端状态查询变更")
	public TerminalStatusQueryChanageResp terminalStatusQueryChanage(TerminalStatusQueryChanageReq req) throws ApiBusiException {
		TerminalStatusQueryChanageResp rsp = new TerminalStatusQueryChanageResp();
		try {
			// 获取工号信息
			String operFlag = req.getOperFlag();
			String orderId = req.getNotNeedReqStrOrderId();
			EmpIdEssIDResp essRspInfo = new EmpIdEssIDResp();

			// 如果是释放/调拨页面过来的请求,则不需要调工号查询,传过来就会有工号信息,如果工号信息确实,调用之前就会报错了
			if (StringUtils.equals(EcsOrderConsts.OCCUPIEDFLAG_99, operFlag)) {
				essRspInfo.setError_code("0");
				essRspInfo.setOperInfo(req.getEssOperInfo());
			}

			// 如果是环节预占,则根据订单号获取锁定工号和地市，再根据这些信息获取绑定的ess工号详情
			if (StringUtils.equals(EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_1, operFlag) || StringUtils.equals(EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_0, operFlag)) {
				essRspInfo = CommonDataFactory.getEssInfoByOrderId(orderId);
			}

			// 如果是环节释放,则先取到预占工号，然后根据ess工号获取工号详情
			if (EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_3.equals(operFlag)) {
				if (CommonDataFactory.getInstance().getOrderTree(orderId).getTmResourceInfoBusiRequests().size() > 0) {
					AttrTmResourceInfoBusiRequest vo = CommonDataFactory.getInstance().getOrderTree(orderId).getTmResourceInfoBusiRequests().get(0);
					String essId = vo.getOccupied_essId();
					essRspInfo = CommonDataFactory.getEssinfoByEssId(essId, orderId, EcsOrderConsts.OPER_TYPE_ESS);
				} else {
					essRspInfo.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
					essRspInfo.setError_msg("未获取到终端信息,请连续运维查询!");
				}
			}

			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", orderId);

				String opcode = EcsOrderConsts.TERMI_OCCUPLY;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);
				param.remove("resources_code");
				rsp = (TerminalStatusQueryChanageResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 开户处理申请
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]开户处理申请", summary = "开户处理申请")
	public OpenDealApplyResp openDealApply(OpenDealApplyReq req) throws ApiBusiException {
		OpenDealApplyResp rsp = new OpenDealApplyResp();
		try {
			long start = System.currentTimeMillis();
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			logger.info(req.getNotNeedReqStrOrderId() + "调用开户处理申请接口【openApplyToAop】获取员工信息耗时：" + (System.currentTimeMillis() - start));
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				start = System.currentTimeMillis();
				BeanUtils.bean2MapForAOP(param, req);
				logger.info(req.getNotNeedReqStrOrderId() + "调用开户处理申请接口【openApplyToAop】封装报文耗时：" + (System.currentTimeMillis() - start));
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.OPEN_ACC_APPLY;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				start = System.currentTimeMillis();
				rsp = (OpenDealApplyResp) caller.invoke(opcode, param);
				logger.info(req.getNotNeedReqStrOrderId() + "调用开户处理申请接口【openApplyToAop】[自服务接口出去]耗时：" + (System.currentTimeMillis() - start));
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 开户处理申请
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]开户处理申请", summary = "开户处理申请")
	public OpenDealApplyResp openDealApplyZJ(OpenDealApplyReqZJ req) throws ApiBusiException {
		OpenDealApplyResp rsp = new OpenDealApplyResp();
		try {
			long start = System.currentTimeMillis();
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			start = System.currentTimeMillis();
			BeanUtils.bean2MapForAOP(param, req);
			logger.info(req.getNotNeedReqStrOrderId() + "调用开户处理申请接口【openApplyToAop】封装报文耗时：" + (System.currentTimeMillis() - start));
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = EcsOrderConsts.OPEN_ACC_APPLYZJ;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);

			start = System.currentTimeMillis();
			rsp = (OpenDealApplyResp) caller.invoke(opcode, param);
			logger.info(req.getNotNeedReqStrOrderId() + "调用开户处理申请接口【openApplyToAop】[自服务接口出去]耗时：" + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 开户处理提交
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]开户处理提交", summary = "开户处理提交")
	public OpenDealSubmitResp openDealSubmit(OpenDealSubmitReq req) throws ApiBusiException {
		OpenDealSubmitResp rsp = new OpenDealSubmitResp();
		try {
			long start = System.currentTimeMillis();
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			logger.info(req.getNotNeedReqStrOrderId() + "调用开户处理提交接口【openDealSubmitToAop】获取员工信息耗时：" + (System.currentTimeMillis() - start));
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				start = System.currentTimeMillis();
				BeanUtils.bean2MapForAOP(param, req);
				logger.info(req.getNotNeedReqStrOrderId() + "调用开户处理提交接口【openDealSubmitToAop】封装报文耗时：" + (System.currentTimeMillis() - start));
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.OPEN_ACC_SUBMIT;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				start = System.currentTimeMillis();
				rsp = (OpenDealSubmitResp) caller.invoke(opcode, param);
				logger.info(req.getNotNeedReqStrOrderId() + "调用开户处理提交接口【openDealSubmitToAop】[自服务接口出去]耗时：" + (System.currentTimeMillis() - start));
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 开户处理提交
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]开户处理提交", summary = "开户处理提交")
	public OpenDealSubmitResp openDealSubmitZJ(OpenDealSubmitReqZJ req) throws ApiBusiException {
		OpenDealSubmitResp rsp = new OpenDealSubmitResp();
		try {
			long start = System.currentTimeMillis();
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			start = System.currentTimeMillis();
			BeanUtils.bean2MapForAOP(param, req);
			logger.info(req.getNotNeedReqStrOrderId() + "调用开户处理提交接口【openDealSubmitToAop】封装报文耗时：" + (System.currentTimeMillis() - start));
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = EcsOrderConsts.OPEN_ACC_SUBMITZJ;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);

			start = System.currentTimeMillis();
			rsp = (OpenDealSubmitResp) caller.invoke(opcode, param);
			logger.info(req.getNotNeedReqStrOrderId() + "调用开户处理提交接口【openDealSubmitToAop】[自服务接口出去]耗时：" + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 写卡数据获取
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]写卡数据获取", summary = "AOP写卡数据获取")
	public CardDataQryResponse cardDataQry(CardDataQryRequest req) throws ApiBusiException {
		CardDataQryResponse rsp = new CardDataQryResponse();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.CARD_DATE_QUERY;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", "TS-3G-005");

				rsp = (CardDataQryResponse) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 写卡结果通知
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]写卡结果通知", summary = "写卡结果通知")
	public WriteCardResultNoticeResp writeCardResultNotice(WriteCardResultNoticeReq req) throws ApiBusiException {
		WriteCardResultNoticeResp rsp = new WriteCardResultNoticeResp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.CARD_RESULTS_NOTIFY;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (WriteCardResultNoticeResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
		}
		return rsp;
	}

	/**
	 * 写卡结果通知
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]写卡结果通知", summary = "写卡结果通知")
	public WriteCardResultNoticeResp writeCardResultNoticeZJ(WriteCardResultNoticeReqZJ req) throws ApiBusiException {
		WriteCardResultNoticeResp rsp = new WriteCardResultNoticeResp();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = EcsOrderConsts.CARD_RESULTS_NOTIFYZJ;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);

			rsp = (WriteCardResultNoticeResp) caller.invoke(opcode, param);
		} catch (Exception e) {
			e.printStackTrace();
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
		}
		return rsp;
	}

	/**
	 * 写卡结果通知BSS
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[BSS]写卡结果通知", summary = "[BSS]写卡结果通知")
	public BSSWriteCardResultNoticeResp writeCardResultNoticeBSS(BSSWriteCardResultNoticeReq req) throws ApiBusiException {
		BSSWriteCardResultNoticeResp rsp = new BSSWriteCardResultNoticeResp();
		try {
			String type_id = CommonDataFactory.getInstance().getGoodSpec(req.getNotNeedReqStrOrderId(), "", SpecConsts.TYPE_ID);
			if (StringUtil.equals(type_id, SpecConsts.TYPE_ID_GOODS_CBSS)) {// 号卡-总部（AOP）
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());
				String opcode = EcsOrderConsts.CARD_RESULTS_NOTIFY_BSS;
				rsp = (BSSWriteCardResultNoticeResp) caller.invoke(opcode, param);
				// 设置返回错误信息
				if (null != rsp) {
					if (EcsOrderConsts.BSS_ANSWER_CODE_0000.equals(rsp.getError_code())) {
						rsp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);
						rsp.setError_msg("成功");
					} else {
						rsp.setError_code("-9999");
						rsp.setError_msg(rsp.getError_msg() + "," + rsp.getRESP_DESC());
					}
				}
			} else {
				// 获取工号信息
				EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
				if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
					rsp.setError_code("-9999");
					rsp.setError_msg(essRspInfo.getError_msg());
				} else {
					EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
					req.setEssOperInfo(essOperInfo);
					CommCaller caller = new CommCaller();
					Map<String, Object> param = new HashMap<String, Object>();
					BeanUtils.bean2MapForAOP(param, req);
					param.put("ord_id", req.getNotNeedReqStrOrderId());

					String opcode = EcsOrderConsts.CARD_RESULTS_NOTIFY_BSS;

					rsp = (BSSWriteCardResultNoticeResp) caller.invoke(opcode, param);
					// 设置返回错误信息
					if (null != rsp) {
						if (EcsOrderConsts.BSS_ANSWER_CODE_0000.equals(rsp.getError_code())) {
							rsp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);
							rsp.setError_msg("成功");
						} else {
							rsp.setError_code("-9999");
							rsp.setError_msg(rsp.getError_msg() + "," + rsp.getRESP_DESC());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			rsp.setRespCode("-9999");
			rsp.setRespDesc(e.getMessage());
			rsp.setError_code("-9999");
			rsp.setError_msg(e.getMessage());
		}
		return rsp;
	}

	/**
	 * 写卡数据同步
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]写卡数据同步", summary = "写卡数据同步")
	public CardDataSynResponse cardDataSyn(CardDataSynRequest req) throws ApiBusiException {
		CardDataSynResponse rsp = new CardDataSynResponse();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());
				String opcode = EcsOrderConsts.CARD_DATA_SYS;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);
				rsp = (CardDataSynResponse) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 写卡数据同步
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]写卡数据同步", summary = "写卡数据同步")
	public CardDataSynResponse cardDataSynZJ(CardDataSynRequestZJ req) throws ApiBusiException {
		CardDataSynResponse rsp = new CardDataSynResponse();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = EcsOrderConsts.CARD_DATA_SYSZJ;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);
			rsp = (CardDataSynResponse) caller.invoke(opcode, param);

		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 老用户存费送费
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]老用户存费送费", summary = "老用户存费送费")
	public CunFeeSongFeeResponse cunFeeSongFee(CunFeeSongFeeRequest req) throws ApiBusiException {
		CunFeeSongFeeResponse rsp = new CunFeeSongFeeResponse();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssinfoByOrderFrom(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());
				rsp = (CunFeeSongFeeResponse) caller.invoke("gdaop.trades.cunFeeSongFee", param);
				if (StringUtils.equals(rsp.getAop_result(), EcsOrderConsts.AOP_CUN_FEE_SONG_FEE_QUERY_SUCC)) {
					if (StringUtils.equals(rsp.getResultcode(), EcsOrderConsts.AOP_CUN_FEE_SONG_FEE_PROCESS_SUCC)) {
						rsp.setCode(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "");
						rsp.setDetail(rsp.getAop_des());
					} else {
						rsp.setCode(rsp.getResultcode());
						rsp.setDetail(rsp.getResultdesc());
					}
				} else {
					rsp.setCode(rsp.getAop_result());
					rsp.setDetail(rsp.getAop_des());
				}
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 4G老用户存费送费
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]4G老用户存费送费", summary = "4G老用户存费送费")
	public CunFeeSongFee4GResp cunFeeSongFee4G(CunFeeSongFee4GReq req) throws ApiBusiException {
		CunFeeSongFee4GResp rsp = new CunFeeSongFee4GResp();
		try {
			// 获取开户工号
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.CUN_FEE_SONG_FEE_4G;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (CunFeeSongFee4GResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 4G老用户存费送费
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]4G老用户存费送费", summary = "4G老用户存费送费")
	public CunFeeSongFee4GResp cunFeeSongFee4GZJ(CunFeeSongFee4GReqZJ req) throws ApiBusiException {
		CunFeeSongFee4GResp rsp = new CunFeeSongFee4GResp();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = EcsOrderConsts.CUN_FEE_SONG_FEE_4GZJ;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);

			rsp = (CunFeeSongFee4GResp) caller.invoke(opcode, param);
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 4G存费送费正式提交
	 */
	@Override
	public CunFeeSongFee4GSubmitResp cunFeeSongFee4GSubmit(CunFeeSongFee4GSubmitReq req) throws ApiBusiException {
		CunFeeSongFee4GSubmitResp rsp = new CunFeeSongFee4GSubmitResp();
		try {
			// 获取开户工号
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.CUN_FEE_SONG_FEE_4G_SUB;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (CunFeeSongFee4GSubmitResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 4G存费送费正式提交
	 */
	@Override
	public CunFeeSongFee4GSubmitResp cunFeeSongFee4GSubmitZJ(CunFeeSongFee4GSubmitReqZJ req) throws ApiBusiException {
		CunFeeSongFee4GSubmitResp rsp = new CunFeeSongFee4GSubmitResp();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = EcsOrderConsts.CUN_FEE_SONG_FEE_4G_SUBZJ;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);

			rsp = (CunFeeSongFee4GSubmitResp) caller.invoke(opcode, param);
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 订单返销
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单返销", summary = "订单返销")
	public OrderReverseSalesResp orderReverseSales(OrderReverseSalesReq req) throws ApiBusiException {
		OrderReverseSalesResp rsp = new OrderReverseSalesResp();
		try {
			// 获取开户工号
			String essId = CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.BSS_OPERATOR);
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssinfoByEssId(essId, req.getNotNeedReqStrOrderId(), EcsOrderConsts.OPER_TYPE_ESS);
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.ORDER_REVERSE_SALES;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (OrderReverseSalesResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * BSS订单返销
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[BSS]订单返销", summary = "[BSS]订单返销")
	public BSSOrderReverseSalesResp orderReverseSalesBSS(BSSOrderReverseSalesReq req) throws ApiBusiException {
		BSSOrderReverseSalesResp rsp = new BSSOrderReverseSalesResp();
		try {
			// 获取开户工号
			String essId = CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.BSS_OPERATOR);
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssinfoByEssId(essId, req.getNotNeedReqStrOrderId(), EcsOrderConsts.OPER_TYPE_ESS);
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setError_code("-9999");
				rsp.setError_msg(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.ORDER_REVERSE_SALES_BSS;

				rsp = (BSSOrderReverseSalesResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setError_code("-9999");
			rsp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 退机申请
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "退机申请", summary = "退机申请")
	public ReturnMachineApplyResp returnMachineApply(ReturnMachineApplyReq req) throws ApiBusiException {
		ReturnMachineApplyResp rsp = new ReturnMachineApplyResp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.RETURN_MACHINE_APPLY;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (ReturnMachineApplyResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 退机提交
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "退机提交", summary = "退机提交")
	public ReturnMachineSubmitResp returnMachineSubmit(ReturnMachineSubmitReq req) throws ApiBusiException {
		ReturnMachineSubmitResp rsp = new ReturnMachineSubmitResp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.RETURN_MACHINE_SUBMIT;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (ReturnMachineSubmitResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 换机提交
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "换机提交", summary = "换机提交")
	public ChangeMachineSubResp ChangeMachineSub(ChangeMachineSubReq req) throws ApiBusiException {
		ChangeMachineSubResp rsp = new ChangeMachineSubResp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.CHANGE_MACHINE_SUBMIT;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (ChangeMachineSubResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 2-3G转4G校验（AOP）
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]2-3G转4G校验", summary = "2-3G转4G校验")
	public Check23to4Resp check23to4(Check23to4Request req) throws ApiBusiException {
		Check23to4Resp rsp = new Check23to4Resp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.OLD_CHECK_23TO4;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (Check23to4Resp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 2-3G转4G预提交（AOP）
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]2-3G转4G申请", summary = "2-3G转4G申请")
	public OpenDealApplyResp openAccApply23to4(Open23to4ApplyReq req) throws ApiBusiException {
		OpenDealApplyResp rsp = new OpenDealApplyResp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.OPEN_23TO4_APPLY;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (OpenDealApplyResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 2-3G转4G撤单（AOP）
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]2-3G转4G撤单", summary = "2-3G转4G撤单")
	public CannelOrder23to4Resp cannelOrder23to4(CannelOrder23to4Request req) throws ApiBusiException {
		CannelOrder23to4Resp rsp = new CannelOrder23to4Resp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.OPEN_23TO4_CANCLE;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (CannelOrder23to4Resp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 流量包订购（AOP）
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]流量包订购", summary = "流量包订购")
	public TrafficOrderResponse trafficOrder(TrafficOrderRequest req) throws ApiBusiException {
		TrafficOrderResponse rsp = new TrafficOrderResponse();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.TRAFFIC_ORDER;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (TrafficOrderResponse) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]套餐变更申请-支持3G", summary = "套餐变更申请")
	public ProdChangeApplyResp productChangeApply(ProdChangeApplyReq req) throws ApiBusiException {
		ProdChangeApplyResp rsp = new ProdChangeApplyResp();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = EcsOrderConsts.PRODUCT_CHANGE_APPLY;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);

			rsp = (ProdChangeApplyResp) caller.invoke(opcode, param);
		} catch (Exception e) {
			rsp.setError_code("-9999");
			rsp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 套餐变更,撤单（AOP）
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]套餐变更,撤单", summary = "套餐变更,撤单")
	public ProdChangeCannelResp prodChangeCancel(ProdChangeCannelRequest req) throws ApiBusiException {
		ProdChangeCannelResp rsp = new ProdChangeCannelResp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.PRODUCT_CHANGE_CANCLE;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (ProdChangeCannelResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]老用户业务校验（3G）", summary = "老用户业务校验（3G）")
	public OldUserCheck3GResp oldUserCheck3G(OldUserCheck3GReq req) throws ApiBusiException {
		OldUserCheck3GResp rsp = new OldUserCheck3GResp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.OLD_CHECK_3G;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (OldUserCheck3GResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]老用户优惠购机申请-支持3G", summary = "老用户优惠购机申请")
	public OldUserBuyApplyResp oldActivityMobileApply(OldUserBuyApplyReq req) throws ApiBusiException {
		OldUserBuyApplyResp rsp = new OldUserBuyApplyResp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.OLD_MACHINE_APPLY;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (OldUserBuyApplyResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]老用户优惠购机提交-支持3G", summary = "老用户优惠购机提交")
	public OldUserBuySubmitResp oldActivityMobileSubmit(OldUserBuySubmitReq req) throws ApiBusiException {
		OldUserBuySubmitResp rsp = new OldUserBuySubmitResp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.OLD_MACHINE_SUBMIT;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (OldUserBuySubmitResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]用户资料校验三户返回", summary = "用户资料校验三户返回")
	public UserInfoCheck3BackResp userInfoCheck3Back(UserInfoCheck3BackReq req) throws ApiBusiException {
		UserInfoCheck3BackResp rsp = new UserInfoCheck3BackResp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.OLD_QUERY_CUST_USER_ACC;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (UserInfoCheck3BackResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]用户资料校验三户返回", summary = "用户资料校验三户返回")
	public UserInfoCheck3BackResp userInfoCheck3BackZJ(UserInfoCheck3BackReqZJ req) throws ApiBusiException {
		UserInfoCheck3BackResp rsp = new UserInfoCheck3BackResp();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = EcsOrderConsts.OLD_QUERY_CUST_USER_ACCZJ;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);

			rsp = (UserInfoCheck3BackResp) caller.invoke(opcode, param);
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "封装接口转发", summary = "接口转发")
	public JKZFInfResp jkzfInf(JKZFInfReq req) throws ApiBusiException {
		JKZFInfResp resp = new JKZFInfResp();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> paramsMap = req.getParamsMap();

			String opcode = req.getJk_mark();
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				paramsMap.put("bizkey", bizkey);

			Object obj = caller.invoke(req.getJk_mark(), req.getParamsMap());
			Map map = new HashMap();
			BeanUtils.beanToMap(map, obj);
			resp.setBack_params(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "仿真派单", summary = "仿真派单")
	public SimulationResponse simulation(SimulationRequset req) throws ApiBusiException {
		SimulationResponse resp = new SimulationResponse();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				resp.setError_code("-9999");
				resp.setError_msg(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);

				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());
				resp = (SimulationResponse) caller.invoke("sr.simulation", param);
				resp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);// 接口调用成功
				resp.setError_msg("调用成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_msg(e.getMessage());// 调用失败有默认值-1
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "仿真回单", summary = "仿真回单")
	public SimulationResultReceiveResponse simulationResultReceive(SimulationResultReceiveRequset req) throws ApiBusiException {
		SimulationResultReceiveResponse resp = new SimulationResultReceiveResponse();
		try {
			String order_id = req.getFlowInstId();
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			if (null == orderTree) {
				// 业务层同步失败
				resp.setResp_code(EcsOrderConsts.SIMULATION_RECEIVE_RUSULT_F);
				resp.setResp_msg("订单不存在或已归档");
			} else {// 业务处理
				if (EcsOrderConsts.SIMULATION_IS_SUCCESS_T.equals(req.getIsSuccess())) {// 仿真业务办理成功
					// 流程自动流转到归档环节

					// 以下需要调某个方案或业务组件

				} else {// 仿真业务办理失败
						// 把流程回退到异常环节，并记录异常描述信息

					// 以下动作是标记异常
					Map params = new HashMap();
					BusiCompRequest bcr = new BusiCompRequest();
					bcr.setEnginePath("zteCommonTraceRule.markedException");
					bcr.setOrder_id(order_id);
					params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
					bcr.setQueryParams(params);
					CommonDataFactory.getInstance().execBusiComp(bcr);
				}

				// 业务层同步成功
				resp.setResp_code(EcsOrderConsts.SIMULATION_RECEIVE_RUSULT_S);
				resp.setResp_msg("通知成功");
			}

			resp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);// 接口层成功
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_msg(e.getMessage());// 接口层错误信息

			// 业务层信息
			resp.setResp_code(EcsOrderConsts.SIMULATION_RECEIVE_RUSULT_F);// 同步失败
			resp.setResp_msg("通知失败");
		}
		return resp;
	}

	/**
	 * 批量终端状态变更
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]终端状态变更-批量", summary = "终端状态变更-批量")
	public TerminalStatusQueryChanageResp termiStatusChanageBatch(TerminalStatusQueryChanageBatchReq req) throws ApiBusiException {
		TerminalStatusQueryChanageResp rsp = new TerminalStatusQueryChanageResp();
		try {
			// 获取工号信息
			if (StringUtils.isEmpty(req.getOssOperId())) {
				rsp.setCode("-9999");
				rsp.setDetail("未获取到调拨工号，请联系运维查询！");
			} else {
				EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssinfoByEssId(req.getOssOperId(), req.getNotNeedReqStrOrderId(), EcsOrderConsts.OPER_TYPE_ORDER);

				if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
					rsp.setCode("-9999");
					rsp.setDetail(essRspInfo.getError_msg());
				} else {
					EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
					req.setEssOperInfo(essOperInfo);
					CommCaller caller = new CommCaller();
					Map<String, Object> param = new HashMap<String, Object>();
					BeanUtils.bean2MapForAOP(param, req);
					param.put("ord_id", req.getNotNeedReqStrOrderId());

					String opcode = EcsOrderConsts.TERMI_OCCUPLY;
					String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
					if (!StringUtils.isEmpty(bizkey))
						param.put("bizkey", bizkey);

					rsp = (TerminalStatusQueryChanageResp) caller.invoke(opcode, param);
				}
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 裸机销售
	 */
	@Override
	public BareMachineSaleResp bareMachineSale(BareMachineSaleReq req) throws ApiBusiException {
		BareMachineSaleResp rsp = new BareMachineSaleResp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.TERM_SALE;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (BareMachineSaleResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]订单查询", summary = "订单查询")
	public OrderQueryRespone orderQuery(OrderQueryReq req) throws ApiBusiException {
		OrderQueryRespone rsp = new OrderQueryRespone();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.ORDER_QUERY;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (OrderQueryRespone) caller.invoke(opcode, param);

				String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.GOODS_TYPE);
				IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
				DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
				List<Map> list = dcPublicCache.getList("DIC_CB_BUSI_TYPE");
				String pname = "";
				for (int i = 0; i < list.size(); i++) {
					if (StringUtil.equals(goods_type, (String) list.get(i).get("pkey"))) {
						pname = (String) list.get(i).get("pname");
						break;
					}
				}

				// 根据返回返销订单号查询Cbss订单信息，List中
				// 1）开户业务：存在orderCode=3则认为已返销（cBSS解释会有2条）
				// 2）存费活动类业务：存在orderCode=0则认为已返销（cBSS解释会有1条）
				List<OrderInfoRespVo> ls = rsp.getOrdiInfo();
				boolean flag = true;
				if (pname.equals("2")) {
					for (OrderInfoRespVo vo : ls) {
						if (vo.getOrderCode().equals("0")) {
							flag = false;
							break;
						}
					}
				} else if (pname.equals("1")) {
					for (OrderInfoRespVo vo : ls) {
						if (vo.getOrderCode().equals("3")) {
							flag = false;
							break;
						}
					}
				} else {
					flag = false;
				}
				if (flag) {
					throw new Exception("返销状态有误");
				}
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setError_code("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]订单查询自定义", summary = "订单查询")
    public OrderQueryRespone orderQueryNewIntent(IntentOrderQueryForCBReq req) throws ApiBusiException {
        OrderQueryRespone rsp = new OrderQueryRespone();
        EmpIdEssIDResp res = new EmpIdEssIDResp();
        try {
            String order_from = req.getParmas().get("source_system_type")+"";
            ICacheUtil util = (ICacheUtil) SpringContextHolder.getBean("cacheUtil");
            String orderEssInfoFrom = util.getConfigInfo("orderEssInfoFrom");
            if (orderEssInfoFrom.contains(order_from)) {
                res = getEssInfoFromOrder(req.getParmas());
            } 
            // 获取工号信息
            if (StringUtils.equals(res.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || res.getOperInfo() == null) {// 获取工号出错
                rsp.setCode("-9999");
                rsp.setDetail("缺少参数");
            } else {
                EmpOperInfoVo essOperInfo = res.getOperInfo();
                req.setEssOperInfo(essOperInfo);
                CommCaller caller = new CommCaller();
                Map<String, Object> param = new HashMap<String, Object>();
                BeanUtils.bean2MapForAOP(param, req);
                param.put("ord_id", req.getNotNeedReqStrOrderId());

                String opcode = EcsOrderConsts.ORDER_QUERY;
                String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
                if (!StringUtils.isEmpty(bizkey))
                    param.put("bizkey", bizkey);
                rsp = (OrderQueryRespone) caller.invoke(opcode, param);
                String sql="select (select type_id from es_goods eg where eg.goods_id = t.goods_id) as type_name from es_order_intent t where t.order_id='"+req.getNotNeedReqStrOrderId()+"'";
                String goods_type = daoSupport.queryForString(sql);
                IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
                DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
                List<Map> list = dcPublicCache.getList("DIC_CB_BUSI_TYPE");
                String pname = "";
                for (int i = 0; i < list.size(); i++) {
                    if (StringUtil.equals(goods_type, (String) list.get(i).get("pkey"))) {
                        pname = (String) list.get(i).get("pname");
                        break;
                    }
                }
                // 根据返回返销订单号查询Cbss订单信息，List中
                // 1）开户业务：存在orderCode=3则认为已返销（cBSS解释会有2条）
                // 2）存费活动类业务：存在orderCode=0则认为已返销（cBSS解释会有1条）
                List<OrderInfoRespVo> ls = rsp.getOrdiInfo();
                boolean flag = true;
                if (pname.equals("2")) {
                    for (OrderInfoRespVo vo : ls) {
                        if (vo.getOrderCode().equals("0")) {
                            flag = false;
                            break;
                        }
                    }
                } else if (pname.equals("1")) {
                    for (OrderInfoRespVo vo : ls) {
                        if (vo.getOrderCode().equals("3")) {
                            flag = false;
                            break;
                        }
                    }
                } else {
                    flag = false;
                }
                if (flag) {
                    throw new Exception("返销状态有误");
                }
            }
        } catch (Exception e) {
            rsp.setCode("-9999");
            rsp.setError_code("-9999");
            rsp.setDetail(e.getMessage());
            e.printStackTrace();
        }
        return rsp;
    }
	private EmpIdEssIDResp getEssInfoFromOrder(Map<String, Object> map) {
	    EmpIdEssIDResp resp = new EmpIdEssIDResp();
        resp.setError_code("0");
        EmpOperInfoVo operInfo = new EmpOperInfoVo();
        operInfo.setChannel_id(map.get("deal_office_id")+"");
        operInfo.setChannel_name("");
        operInfo.setChannel_type(map.get("channelType")+"");
        String city = map.get("order_city_code").toString();
        if (!StringUtils.isEmpty(city) && city.length() == 6) {
            city = CommonDataFactory.getInstance().getOtherDictVodeValue("city", city);
        }
        operInfo.setCity(city);
        operInfo.setDep_id(map.get("development_code")+"");
        operInfo.setDep_name(map.get("development_name")+"");
        operInfo.setDistrict("");
        operInfo.setEss_emp_id(map.get("deal_operator")+"");
        operInfo.setOrder_gonghao("");
        operInfo.setProvince("36");
        operInfo.setSource_from(ManagerUtils.getSourceFrom());
        operInfo.setStaffName("");
        operInfo.setStaffNumber("");
        operInfo.setStaffPsptId("");
        operInfo.setStaffPsptType("");
        operInfo.setStaffSex("");
        resp.setOperInfo(operInfo);
        return resp;
    }

    @Override
	@ZteSoftCommentAnnotation(type = "method", desc = "发送三网短信", summary = "发送三网短信")
	public Sms3NetSendResp send3NetSms(Sms3NetSendReq req) throws ApiBusiException {
		Sms3NetSendResp resp = new Sms3NetSendResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (Sms3NetSendResp) caller.invoke("WYG.Send3NetSms", param);
			if (!EcsOrderConsts.WYG_INF_SUCC_CODE.equals(resp.getResp_code())) {// 业务级别错误,封装到接口级别
				if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getResp_code())) {// 业务错误编码和接口成功编码相同时不可复制，避免外层误判
					resp.setError_code(resp.getResp_code());
				}
				resp.setError_msg(resp.getResp_msg());
			} else {
				resp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);// 调用接口成功
				resp.setError_msg(resp.getResp_msg());
			}
		} catch (Exception e) {// 调用接口失败
			// resp.setError_code("-1");//调用接口失败,编码有默认值"-1"
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取总商上传图片地址", summary = "获取总商上传图片地址")
	public QryPhoneUrlResponse qryPhoneUrl(QryPhoneUrlRequest req) throws ApiBusiException {
		QryPhoneUrlResponse rsp = new QryPhoneUrlResponse();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			rsp = (QryPhoneUrlResponse) caller.invoke("periphery.orderurlnotify", param);
		} catch (Exception e) {
			rsp.setRespCode("-9999");
			rsp.setRespDesc(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 开户处理申请
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[BSS]开户处理申请", summary = "[BSS]开户处理申请")
	public BSSAccountResponse openDealApplyBSS(BSSAccountReq req) throws ApiBusiException {
		BSSAccountResponse rsp = new BSSAccountResponse();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setRespCode("-9999");
				rsp.setRespDesc(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.OPEN_ACC_APPLY_BSS;
				rsp = (BSSAccountResponse) caller.invoke(opcode, param);
				// 设置返回错误信息
				if (null != rsp) {
					if (EcsOrderConsts.BSS_ANSWER_CODE_0000.equals(rsp.getError_code())) {
						rsp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);
						rsp.setError_msg("成功");
					} else {
						rsp.setError_code("-9999");
						rsp.setError_msg(rsp.getError_msg() + "," + rsp.getRespDesc());
					}
				}
			}
		} catch (Exception e) {
			rsp.setError_code("-9999");
			rsp.setError_msg(e.getMessage());
			rsp.setRespCode("-9999");
			rsp.setRespDesc(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 开户处理提交
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[BSS]开户处理提交", summary = "开户处理提交")
	public BSSOrderSubResponse openDealSubmitBSS(BSSOrderSubReq req) throws ApiBusiException {
		BSSOrderSubResponse rsp = new BSSOrderSubResponse();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setRespCode("-9999");
				rsp.setRespDesc(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.OPEN_ACC_SUBMIT_BSS;
				rsp = (BSSOrderSubResponse) caller.invoke(opcode, param);
				// 设置返回错误信息
				if (null != rsp) {
					if (EcsOrderConsts.BSS_ANSWER_CODE_0000.equals(rsp.getError_code())) {
						rsp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);
						rsp.setError_msg("成功");
					} else {
						rsp.setError_code("-9999");
						rsp.setError_msg(rsp.getError_msg() + "," + rsp.getRespDesc());
					}
				}
			}
		} catch (Exception e) {
			rsp.setRespCode("-9999");
			rsp.setRespDesc(e.getMessage());
			rsp.setError_code("-9999");
			rsp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS H2接口方式", summary = "BSS H2接口方式")
	public BaseBSSResp BSSH2Interface(BaseBSSReq req) throws ApiBusiException {
		BaseBSSResp resp = new BaseBSSResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (BaseBSSResp) caller.invoke(req.getOp_Code(), param);
			if (!"00000".equals(resp.getA11())) {// 业务级别错误,封装到接口级别
				if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getA11())) {// 业务错误编码和接口成功编码相同时不可复制，避免外层误判
					resp.setError_code(resp.getA11());
				}
				resp.setError_msg("BSS返回错误,请联系BSS或者运维人员.");
			} else {
				resp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);// 调用接口成功
			}
		} catch (Exception e) {// 调用接口失败
			// resp.setError_code("-1");//调用接口失败,编码有默认值"-1"
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS卡数据同步接口", summary = "BSS卡数据同步接口")
	public WriteCardPreRsp writeCardDataSync2BSS(WriteCardPreReq req) throws ApiBusiException {
		WriteCardPreRsp resp = new WriteCardPreRsp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				resp.setError_code("-9999");
				resp.setError_msg(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2Map(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				resp = (WriteCardPreRsp) caller.invoke(EcsOrderConsts.CARD_DATA_SYS_BSS, param);
				// 设置返回错误信息
				if (null != resp) {
					if (EcsOrderConsts.BSS_ANSWER_CODE_0000.equals(resp.getError_code())) {
						resp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);
						resp.setError_msg("成功");
					} else {
						resp.setError_code("-9999");
						resp.setError_msg(resp.getError_msg() + "," + resp.getRespDesc());
					}
				}
			}
		} catch (Exception e) {
			resp.setRespCode("-9999");
			resp.setRespDesc(e.getMessage());
			resp.setError_code("-9999");
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS获取写卡数据", summary = "BSS获取写卡数据")
	public BSSGetCardMsgRsp getCardDataFromBSS(BSSGetCardMsgReq req) throws ApiBusiException {
		BSSGetCardMsgRsp resp = new BSSGetCardMsgRsp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				resp.setError_code("-9999");
				resp.setError_msg(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2Map(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				resp = (BSSGetCardMsgRsp) caller.invoke(EcsOrderConsts.CARD_DATE_QUERY_BSS, param);
				// 设置返回错误信息
				if (null != resp) {
					if (EcsOrderConsts.BSS_ANSWER_CODE_0000.equals(resp.getError_code())) {
						resp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);
						resp.setError_msg("成功");
					} else {
						resp.setError_code("-9999");
						resp.setError_msg(resp.getError_msg() + "," + resp.getRESP_DESC());
					}
				}
			}
		} catch (Exception e) {
			resp.setRESP_CODE("-9999");
			resp.setRESP_DESC(e.getMessage());
			resp.setError_code("-9999");
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS使用人数量校验接口", summary = "BSS使用人数量校验接口")
	public UserCountCheckRsp userCountCheckFromBSS(UserCountCheckReq req) throws ApiBusiException {
		UserCountCheckRsp resp = new UserCountCheckRsp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				resp.setError_code("-9999");
				resp.setError_msg(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2Map(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				resp = (UserCountCheckRsp) caller.invoke(EcsOrderConsts.BSS_USER_COUNT_CHECK, param);
				// 设置返回错误信息
				if (null != resp) {
					if (EcsOrderConsts.BSS_ANSWER_CODE_0000.equals(resp.getError_code())) {
						resp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);
						resp.setError_msg("成功");
					} else {
						resp.setError_code("-9999");
						resp.setError_msg(resp.getError_msg() + "," + resp.getRespDesc());
					}
				}
			}
		} catch (Exception e) {
			resp.setRespCode("-9999");
			resp.setRespDesc(e.getMessage());
			resp.setError_code("-9999");
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "SP服务办理接口", summary = "SP服务办理接口")
	public HandleSpServiceResp handleSpService(HandleSpServiceReq req) throws ApiBusiException {
		HandleSpServiceResp rsp = new HandleSpServiceResp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setRespCode("-9999");
				rsp.setRespDesc(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());// 是日志表inf_comm_client_calllog的col3字段
				rsp = (HandleSpServiceResp) caller.invoke(EcsOrderConsts.AOP_HANDLESPSERVICE, param);
			}
		} catch (Exception e) {
			rsp.setRespCode("-9999");
			rsp.setRespDesc(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "附加产品订购接口", summary = "附加产品订购接口")
	public SubProOrderResp subProOrder(SubProOrderReq req) throws ApiBusiException {
		SubProOrderResp rsp = new SubProOrderResp();
		try {
			// 获取工号信息--此接口工号固定传SUPERUSR
			// EmpIdEssIDResp essRspInfo =
			// CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			// if(StringUtils.equals(essRspInfo.getError_code(),EcsOrderConsts.BUSI_DEAL_RESULT_FAIL)
			// || essRspInfo.getOperInfo()==null){//获取工号出错
			// rsp.setRespCode("-9999");
			// rsp.setRespDesc(essRspInfo.getError_msg());
			// }else{
			// EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
			// req.setEssOperInfo(essOperInfo);
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 是日志表inf_comm_client_calllog的col3字段
			rsp = (SubProOrderResp) caller.invoke("gdaop.trades.subProOrder", param);
			if (!EcsOrderConsts.GDAOP_SUCC_CODE.equals(rsp.getRespCode())) {// 业务级别错误,封装到接口级别
				if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(rsp.getRespCode())) {// 业务错误编码和接口成功编码相同时不可复制，避免外层误判
					rsp.setError_code(rsp.getRespCode());
				}
			} else {
				rsp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);// 调用接口成功
			}
			rsp.setError_msg(rsp.getRespDesc());
			// }
		} catch (Exception e) {
			// rsp.setError_code("-1");
			rsp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "续约申请", summary = "续约申请")
	public OldUserRenActivityResp oldRenActivityApply(OldUserRenActivityReq req) throws ApiBusiException {
		OldUserRenActivityResp rsp = new OldUserRenActivityResp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.OLD_RENACTIVITY_APPLY;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (OldUserRenActivityResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "续约提交", summary = "续约提交")
	public OldUserRenActivitySubmitResp oldRenActivitySubmit(OldUserRenActivitySubmitReq req) throws ApiBusiException {
		OldUserRenActivitySubmitResp rsp = new OldUserRenActivitySubmitResp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.OLD_RENACTIVITY_SUBMIT;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (OldUserRenActivitySubmitResp) caller.invoke(opcode, param);
			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "路由信息通知总部", summary = "路由信息通知总部")
	public NotifyRouteInfoZBResponse notifyRouteInfoZB(NotifyRouteInfoZBRequest req) throws ApiBusiException {
		NotifyRouteInfoZBResponse rsp = new NotifyRouteInfoZBResponse();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			rsp = (NotifyRouteInfoZBResponse) caller.invoke("logistics.statusnotify", param);
		} catch (Exception e) {
			rsp.setRespCode("-9999");
			rsp.setRespDesc(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "沃云购 受理单信息查询", summary = "接收沃云购 受理单信息查询")
	public WYGAcceptanceQueryResp acceptanceQuery(WYGAcceptanceQueryReq req) throws ApiBusiException {
		// 返回对象 设置返回对象-固定信息
		WYGAcceptanceQueryResp resp = new WYGAcceptanceQueryResp();
		// 获取订单信息
		req.setApi_method("zte.net.ecsord.acceptanceQuery");
		try {
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
			resp = client.execute(req, WYGAcceptanceQueryResp.class);
		} catch (Exception e) {
			logger.info(req.getSerial_no() + "受理单模板获取报错:");
			e.printStackTrace();
			resp.setResp_code(EcsOrderConsts.WYG_INF_FAIL_CODE);
			resp.setResp_msg("获取电子受理单数据出错");
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "接收华盛 SAPB2C省分退货、拦截指令", summary = "接收华盛 SAPB2C省分退货、拦截指令")
	public OrderCancelReceiveResp orderCancelReceive(OrderCancelReceiveReq req) throws ApiBusiException {
		OrderCancelReceiveResp resp = new OrderCancelReceiveResp();
		//屏蔽无用代码 xiao.ruidan 20180518
		/*try {
			String ori_vbeln = req.getORI_VBELN();// 原出库单号(外部单号)
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
			GetOrderByOutTidReq getOrderByOuttidReq = new GetOrderByOutTidReq();
			getOrderByOuttidReq.setOut_tid(ori_vbeln);
			GetOrderByOutTidResp orderIdResp = client.execute(getOrderByOuttidReq, GetOrderByOutTidResp.class);
			String order_id = orderIdResp.getOrder_id();
			logger.info("=========================华盛退货、拦截orderCancelReceive订单编号：" + order_id);

			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			if (null == orderTree) {
				resp.setTYPE("E");
				resp.setMESSAGE("订单拦截失败:订单不存在或已归档!");
				resp.setError_msg("订单拦截失败:订单不存在或已归档!");
				return resp;
			}

			// 退货信息存储与业务处理
			List<HuashengOrderBusiRequest> hsOrders = orderTree.getHuashengOrderBusiRequest();
			if (null != hsOrders && hsOrders.size() > 0) {
				for (HuashengOrderBusiRequest hsOrder : hsOrders) {
					// hsOrder.setOrder_id("");
					// hsOrder.setVbeln("");
					// hsOrder.setMjahr("");
					// hsOrder.setWerks("");
					// hsOrder.setCompany_id("");
					// hsOrder.setCompany_name("");
					// hsOrder.setDisvendor_code("");
					// hsOrder.setSf_origincode("");
					// hsOrder.setSf_destcode("");
					hsOrder.setBstkd(req.getBSTKD());
					hsOrder.setLgort(req.getLGORT());// 订单推送LGORT放在二级节点，存在es_huasheng_order_item表；订单退货LGORT放在一级节点，存在es_huasheng_order表。此点待澄清
					hsOrder.setOutinflag(req.getOUTINFLAG());
					hsOrder.setPost_company(req.getPOST_COMPANY());
					hsOrder.setPost_number(req.getPOST_NUMBER());

					hsOrder.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					hsOrder.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					hsOrder.store();
				}
			}
			List<HuashengOrderItemBusiRequest> hsOrderItems = orderTree.getHuashengOrderItemBusiRequest();
			if (null != hsOrderItems && hsOrderItems.size() > 0) {
				List<Items> itemsList = req.getItems();
				if (null != itemsList && itemsList.size() > 0) {
					Items items = itemsList.get(0);
					if (null != items && null != items.getItem() && items.getItem().size() > 0) {
						for (HuashengOrderItemBusiRequest hsOrderItem : hsOrderItems) {
							for (Item item : items.getItem()) {
								if (StringUtils.equals(hsOrderItem.getMatnr(), item.getMATNR())) {
									// hsOrderItem.setItems_id("");
									// hsOrderItem.setOrder_id("");
									// hsOrderItem.setItem("");
									// hsOrderItem.setLgort("");
									// hsOrderItem.setMatnr("");
									// hsOrderItem.setSobkz("");
									// hsOrderItem.setMenge("");
									// hsOrderItem.setMeins("");
									hsOrderItem.setPosnr(item.getPOSNR());
									// hsOrderItem.setSn("");

									hsOrderItem.setIs_dirty(ConstsCore.IS_DIRTY_YES);
									hsOrderItem.setDb_action(ConstsCore.DB_ACTION_UPDATE);
									hsOrderItem.store();
								}
							}
						}
					}
				}
			}

			// 获取当前环节
			String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			PlanRuleTreeExeReq planTreeExeReq = new PlanRuleTreeExeReq();
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			if (StringUtils.equals(flowTraceId, EcsOrderConsts.DIC_ORDER_NODE_J) || StringUtils.equals(flowTraceId, EcsOrderConsts.DIC_ORDER_NODE_L)) {
				// 订单已经发货 调用退单人工审核方案，并且接口返回E订单拦截失败
				resp.setTYPE("E");
				resp.setMESSAGE("订单拦截失败");
				resp.setError_msg("订单拦截失败");
				fact = new TacheFact();
				fact.setOrder_id(order_id);
				RuleTreeExeReq ruleTreeExeReq = new RuleTreeExeReq();
				ruleTreeExeReq.setCheckAllRelyOnRule(true);
				ruleTreeExeReq.setCheckCurrRelyOnRule(true);
				ruleTreeExeReq.setPlan_id(EcsOrderConsts.ORDER_RETURNED_PLAN);
				ruleTreeExeReq.setRule_id(EcsOrderConsts.ORDER_RETURN_APPLY_RULE);
				ruleTreeExeReq.setFact(fact);
				RuleTreeExeResp presp = CommonDataFactory.getInstance().exeRuleTree(ruleTreeExeReq);
				BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(presp);
				if (!ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
					resp.setTYPE("F");
					resp.setMESSAGE("调用退单方案失败[" + ruleTreeExeReq.getPlan_id() + "]" + busiResp.getError_msg());
					resp.setError_msg("调用退单方案失败[" + ruleTreeExeReq.getPlan_id() + "]" + busiResp.getError_msg());
				}
				return resp;
			} else {
				// 订单还未发货 调用退单自动审核方案，并且返回S订单拦截成功；
				planTreeExeReq.setPlan_id(EcsOrderConsts.ORDER_RETURNED_PLAN_AUTO);
				resp.setTYPE("S");
				resp.setMESSAGE("拦截成功");
				resp.setError_msg("拦截成功");
				resp.setError_code(ApiConsts.ERROR_SUCC);
				planTreeExeReq.setFact(fact);
				PlanRuleTreeExeResp rsp = CommonDataFactory.getInstance().exePlanRuleTree(planTreeExeReq);
				BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
				if (!ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
					resp.setTYPE("F");
					resp.setMESSAGE("调用退单方案失败[" + planTreeExeReq.getPlan_id() + "]" + busiResp.getError_msg());
					resp.setError_msg("调用退单方案失败[" + planTreeExeReq.getPlan_id() + "]" + busiResp.getError_msg());
				}
				return resp;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setTYPE("F");
			resp.setMESSAGE("系统处理失败" + e.getMessage());
			resp.setError_msg("系统处理失败" + e.getMessage());
		}*/
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "出库信息回传SAP", summary = "出库信息回传SAP")
	public DeliveNotifyResp deliveNotify(DeliveNotifyReq req) throws ApiBusiException {
		DeliveNotifyResp resp = new DeliveNotifyResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (DeliveNotifyResp) caller.invoke("hs.deliveNotify", param);
			if (!"S".equals(resp.getTYPE())) {// 业务级别错误,封装到接口级别
				if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getTYPE())) {// 业务错误编码和接口成功编码相同时不可复制，避免外层误判
					resp.setError_code(resp.getTYPE());
				}
				resp.setError_msg(resp.getMESSAGE());
			} else {
				resp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);// 调用接口成功
			}
		} catch (Exception e) {// 调用接口失败
			// resp.setError_code("-1");//调用接口失败,编码有默认值"-1"
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "退货入库传输SAP", summary = "退货入库传输SAP")
	public ReturnWarehousingResp ReturnWarehousing(ReturnWarehousingReq req) throws ApiBusiException {
		ReturnWarehousingResp resp = new ReturnWarehousingResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (ReturnWarehousingResp) caller.invoke("hs.returnWarehousing", param);
			if (!"S".equals(resp.getTYPE())) {// 业务级别错误,封装到接口级别
				if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getTYPE())) {// 业务错误编码和接口成功编码相同时不可复制，避免外层误判
					resp.setError_code(resp.getTYPE());
				}
				resp.setError_msg(resp.getMESSAGE());
			} else {
				resp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);// 调用接口成功
			}
		} catch (Exception e) {// 调用接口失败
			// resp.setError_code("-1");//调用接口失败,编码有默认值"-1"
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "仓储商采购退货订单出库回传SAP接口", summary = "仓储商采购退货订单出库回传SAP接口")
	public OrderCheckOutResp orderCheckOutB2B(OrderCheckOutReq req) throws ApiBusiException {
		OrderCheckOutResp resp = new OrderCheckOutResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (OrderCheckOutResp) caller.invoke("hs.orderChekOutB2B", param);
			if (!"S".equals(resp.getTYPE())) {// 业务级别错误,封装到接口级别
				if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getTYPE())) {// 业务错误编码和接口成功编码相同时不可复制，避免外层误判
					resp.setError_code(resp.getTYPE());
				}
				resp.setError_msg(resp.getMESSAGE());
			} else {
				resp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);// 调用接口成功
			}
		} catch (Exception e) {// 调用接口失败
			// resp.setError_code("-1");//调用接口失败,编码有默认值"-1"
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * wayne 浙江省份BSS选号接口
	 */

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "省份BSS选号接口", summary = "省份BSS选号接口")
	public NumberResourceQueryZjBssResponse numberResourceQueryZjBss(NumberResourceQueryZjBssRequest req) throws ApiBusiException {

		NumberResourceQueryZjBssResponse rsp = new NumberResourceQueryZjBssResponse();

		try {
			CommCaller caller = new CommCaller();// 接口调用框架
			Map<String, Object> param = new HashMap<String, Object>();// 存放参数的MAP

			BeanUtils.bean2MapForAiPBSS(param, req);// 把传入的对象参数转为MAP并存入
	        param.put("ord_id", req.getNotNeedReqStrOrderId());
			String opcode = "ecaop.trades.serv.num.data.qry";// 接口编码
			rsp = (NumberResourceQueryZjBssResponse) caller.invoke(opcode, param);// 开始调用
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setMsg(e.getMessage());// 错误信息
			e.printStackTrace();
		}
		return rsp;

	}

	/**
	 * wayne
	 */

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "总部选号接口", summary = "总部选号接口")
	public NumberResourceQueryZbResponse numberResourceQueryZb(NumberResourceQueryZbRequest req) throws ApiBusiException {
		NumberResourceQueryZbResponse resp = new NumberResourceQueryZbResponse();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = "ecaop.trades.query.comm.simpsnres.qry";
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);
			// param.put("qryCbss", "1");
			// param.put("is3G", "1");
			resp = (NumberResourceQueryZbResponse) caller.invoke(opcode, param);
		} catch (Exception e) {
			resp.setCode("-9999");
			resp.setDeatil(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取EMS物流单号", summary = "获取EMS物流单号")
	public EmsLogisticsNumberGetResp getEmsLogisticsNumber(EmsLogisticsNumberGetReq req) throws ApiBusiException {
		EmsLogisticsNumberGetResp resp = new EmsLogisticsNumberGetResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (EmsLogisticsNumberGetResp) caller.invoke("ems.inland.mms.mailnum.apply", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "物流信息同步EMS", summary = "物流信息同步EMS")
	public EmsLogisticsInfoSyncResp syncLogisticsInfoToEms(EmsLogisticsInfoSyncReq req) throws ApiBusiException {
		EmsLogisticsInfoSyncResp resp = new EmsLogisticsInfoSyncResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (EmsLogisticsInfoSyncResp) caller.invoke("ems.inland.waybill.create.normal", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "从EMS自助服务平台获取EMS物流单号", summary = "从EMS自助服务平台获取EMS物流单号")
	public LogisticsNumberGetResp getLogisticsNumber(LogisticsNumberGetReq req) throws ApiBusiException {
		LogisticsNumberGetResp resp = new LogisticsNumberGetResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			// param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (LogisticsNumberGetResp) caller.invoke("ems.user.data.getbillnumbysys", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "物流信息同步EMS自助服务平台", summary = "物流信息同步EMS自助服务平台")
	public LogisticsInfoSyncResp syncLogisticsInfo(LogisticsInfoSyncReq req) throws ApiBusiException {
		LogisticsInfoSyncResp resp = new LogisticsInfoSyncResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			// param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (LogisticsInfoSyncResp) caller.invoke("ems.user.printemsdatas.update", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public AopSmsSendResp sendSmsZb(AopSmsSendReq req) throws Exception {

		AopSmsSendResp resp = new AopSmsSendResp();
		// 插入入中间表
		Map<String, String> map = new HashMap<String, String>();
		List<ES_WORK_SMS_SEND> listSmsPojo = req.getListpojo();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String time = df.format(new Date());
		
		if (null != listSmsPojo && listSmsPojo.size() > 0) {// 批量插入
			List<ES_WORK_SMS_SEND> temp = new ArrayList<ES_WORK_SMS_SEND>();
			
			for (int i = 0; i < listSmsPojo.size(); i++) {
				String sms_id = baseDaoSupport.getSequences("S_ES_SEND_SMS");
				
				if(org.apache.commons.lang.StringUtils.isNotBlank(listSmsPojo.get(i).getBill_num())
						&& org.apache.commons.lang.StringUtils.isNotBlank(listSmsPojo.get(i).getService_num())
						&& org.apache.commons.lang.StringUtils.isNotBlank(listSmsPojo.get(i).getSms_data())) {
					listSmsPojo.get(i).setSms_id(sms_id);
					listSmsPojo.get(i).setSms_type("sms");
					listSmsPojo.get(i).setSend_num("0");
					listSmsPojo.get(i).setSend_status("0");
					listSmsPojo.get(i).setCreate_time(time);
					
					temp.add(listSmsPojo.get(i));
				}
			}
			
			if(!temp.isEmpty()) {
				baseDaoSupport.saveBatch("es_order_mid_sms", "insert", listSmsPojo, null);
			}
		} else {
			if(StringUtil.isEmpty(req.getBill_num())) {
				throw new Exception("发送号码不能为空");
			}
			if(StringUtil.isEmpty(req.getService_num())){
				throw new Exception("接收号码不能为空");
			}
			if(StringUtil.isEmpty(req.getSms_data())) {
				throw new Exception("发送内容不能为空");
			}
			
			String sms_id = baseDaoSupport.getSequences("S_ES_SEND_SMS");
			map.put("sms_id", sms_id);// 主键
			map.put("service_num", req.getService_num());// 接收号码 + 非空校验
			map.put("bill_num", req.getBill_num());// 发送号码
			map.put("sms_data", req.getSms_data());// 短信内容+ 非空校验
			map.put("sms_type", "sms");// sms本网，cmc是异网
			map.put("send_type", req.getSend_type());
			map.put("sms_flag", req.getSms_flag());
			map.put("send_status", "0");// 发送状态，0，未发送。
			map.put("send_num", "0");// 发送次数
			map.put("create_time", time);// 创建时间
			map.put("order_id",req.getOrder_id());
			baseDaoSupport.insertByMap("es_order_mid_sms", map);
		}
		
		resp.setCode("00000");
		resp.setError_code(ConstsCore.ERROR_SUCC);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "用户属性查询接口(客户校验)", summary = "用户属性查询接口(客户校验)")
	public CustomerCheckResp bssCustomerCheck(CustomerCheckReq req) throws ApiBusiException {
		CustomerCheckResp resp = new CustomerCheckResp();
		// 2017年12月27日
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (CustomerCheckResp) caller.invoke(EcsOrderConsts.BSS_CUSTOMER_CHECK, param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "开户预提交", summary = "开户预提交")
	public PreCommitResp bssPreCommit(PreCommitReq req) throws ApiBusiException {
		PreCommitResp resp = new PreCommitResp();

		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);
			resp = (PreCommitResp) caller.invoke(EcsOrderConsts.BSS_PRE_COMMIT, param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}

	/**
	 * 
	 * 浙江省份号码预占接口
	 */
	@Override
	public NumberResourceChangePreCaptureZjResponse NumberResourceChangePreCaptureZj(NumberResourceChangePreCaptureZjRequest req) throws ApiBusiException {

		NumberResourceChangePreCaptureZjResponse rsp = new NumberResourceChangePreCaptureZjResponse();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			String opcode = "ecaop.trades.serv.newu.numprehold.app";
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);
			rsp = (NumberResourceChangePreCaptureZjResponse) caller.invoke(opcode, param);
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;

	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "轮询写卡机工位状态", summary = "轮询写卡机工位状态")
	public WriMachStaQueResponse WriteMachineStation(WriMachStaQueRequest req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		WriMachStaQueResponse rsp = new WriMachStaQueResponse();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			rsp = (WriMachStaQueResponse) caller.invoke("sr.WriteMachineStation", param);
			if (null != rsp.getSr_response() && null != rsp.getSr_response().getHead()) {
				rsp.setError_code(rsp.getSr_response().getHead().getCode());
			}
		} catch (Exception e) {
			rsp.setSr_response(new WriMachStaResp("-9999", e.getMessage()));
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "写卡接口(人工批量)", summary = "外部针对每次写卡机访问请求对写卡机管理服务发起一个HTTP请求,写卡机管理服务接收到请求之后会对该请求进行合法性校验,如果校验不通过,将返回校验失败并中断流程；如果校验通过,则启动对写卡机的操作,处理完成后将返回处理结果。")
	public WriteICCIDSRRGResponse writeICCIDSRRG(WriteICCIDSRRGRequset req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		WriteICCIDSRRGResponse rsp = new WriteICCIDSRRGResponse();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			rsp = (WriteICCIDSRRGResponse) caller.invoke("sr.rgwriteICCIDSR", param);
			if (EcsOrderConsts.SR_INF_SUCC_CODE.equals(rsp.getSr_response().getBody().getResult())) {// 业务成功才算成功，此接口成功信息在body中
				rsp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);
			} else {
				if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(rsp.getSr_response().getHead().getCode())) {// 此接口失败信息在head中，当业务失败编码与接口成功编码一致，不可复制
					rsp.setError_code(rsp.getSr_response().getHead().getCode());
				}
				rsp.setError_msg(rsp.getSr_response().getHead().getMessage());
			}
		} catch (Exception e) {
			rsp.setSr_response(new WritCardRGResp("-9999", e.getMessage()));
			rsp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "回收卡(人工批量)", summary = "回收卡(人工批量)")
	public RevertCardRGResponse revertCardRG(RevertCardRGRequset req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		RevertCardRGResponse rsp = new RevertCardRGResponse();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			rsp = (RevertCardRGResponse) caller.invoke("sr.rgrevertCard", param);
			if (EcsOrderConsts.SR_INF_SUCC_CODE.equals(rsp.getSr_response().getBody().getResult())) {// 业务成功才算成功，此接口成功信息在body中
				rsp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);
			} else {
				if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(rsp.getSr_response().getHead().getCode())) {// 此接口失败信息在head中，当业务失败编码与接口成功编码一致，不可复制
					rsp.setError_code(rsp.getSr_response().getHead().getCode());
				}
				rsp.setError_msg(rsp.getSr_response().getHead().getMessage());
			}
		} catch (Exception e) {
			rsp.setSr_response(new RecycleCardRGResp("-999", e.getMessage()));
			rsp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "批量获取写卡机状态获取data", summary = "批量获取写卡机状态获取data")
	public QueWriMachStaInBatchRGResponse queWriMachStaInBatchRG(QueWriMachStaInBatchRGRequset req) throws ApiBusiException {
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		QueWriMachStaInBatchRGResponse rsp = new QueWriMachStaInBatchRGResponse();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			rsp = (QueWriMachStaInBatchRGResponse) caller.invoke("sr.rgqueWriMachStaInBatch", param);
			if (EcsOrderConsts.SR_INF_SUCC_CODE.equals(rsp.getSr_response().getHead().getCode())) {// 0代表有返回信息
				rsp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);
			} else {
				if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(rsp.getSr_response().getHead().getCode())) {// 无返回信息当接口失败，业务上无需作任何处理
					rsp.setError_code(rsp.getSr_response().getHead().getCode());
				}
				rsp.setError_msg(rsp.getSr_response().getHead().getMessage());
			}
		} catch (Exception e) {
			rsp.setSr_response(new QueWriMachStaInBatchRGResp("-999", e.getMessage()));
			rsp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "接收森锐单笔推送可用ICCID", summary = "接收森锐单笔推送可用ICCID")
	public RceiveICCIDResp rceiveICCID(RceiveICCIDReq req) throws ApiBusiException {
		RceiveICCIDResp resp = new RceiveICCIDResp();
		// 成功
		resp.setResp_code("0");
		resp.setResp_msg("推送成功");
		RceiveICCIDBody body = req.getBody();
		if (null != body) {
			final String iccid = req.getBody().getIccid();
			final String machineNo = body.getMachineNo();
			if (StringUtils.isEmpty(iccid)) {
				resp.setResp_code("-1");
				resp.setResp_msg("iccid为空");
				return resp;
			}
			if (StringUtils.isEmpty(machineNo)) {
				resp.setResp_code("-1");
				resp.setResp_msg("machineNo为空");
				return resp;
			}
			List<Map> orderList = PCWriteCardTools.queryWaitReceiveICCIDOrders(machineNo, null);
			if (null == orderList || orderList.size() == 0) {
				resp.setResp_code("-1");
				resp.setResp_msg("没有待写卡订单.");
				return resp;
			}
			String order_id = orderList.get(0).get("ORDER_ID").toString();
			return PCWriteCardTools.queueWriteCard(iccid, machineNo, order_id);
			// List orderList = PCWriteCardTools.queryWaitOpenOrders(machineNo);
			// if(null == orderList || orderList.size() == 0){
			// resp.setResp_code("-1");
			// resp.setResp_msg("没有待写卡订单.");
			// return resp;
			// }
			// return PCWriteCardTools.queueWriteCard(iccid, machineNo);
		} else {
			resp.setResp_code("-1");// 编码待定
			resp.setResp_msg("推送内容body为空");
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "后向激活订单激活AOP", summary = "后向激活订单激活AOP")
	public OrderActiveResp syncActiveToAOP(OrderActiveReq req) throws ApiBusiException {
		OrderActiveResp rsp = new OrderActiveResp();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = EcsOrderConsts.AOP_ORDER_ACTIVE;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);

			rsp = (OrderActiveResp) caller.invoke(opcode, param);
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "后向激活订单激活BSS", summary = "后向激活订单激活BSS")
	public BSSActivateOperRsp syncActiveToBSS(BSSActivateOperReq req) throws ApiBusiException {
		BSSActivateOperRsp rsp = new BSSActivateOperRsp();
		try {
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssInfoByOrderId(req.getNotNeedReqStrOrderId());
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setRespCode("-9999");
				rsp.setRespDesc(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.BSS_ORDER_ACTIVE;
				rsp = (BSSActivateOperRsp) caller.invoke(opcode, param);
				// 设置返回错误信息
				if (null != rsp) {
					if (EcsOrderConsts.BSS_ANSWER_CODE_0000.equals(rsp.getError_code())) {
						rsp.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);
						rsp.setError_msg("成功");
					} else {
						rsp.setError_code("-9999");
						rsp.setError_msg(rsp.getError_msg() + "," + rsp.getRespDesc());
					}
				}
			}
		} catch (Exception e) {
			rsp.setError_code("-9999");
			rsp.setError_msg(e.getMessage());
			rsp.setRespCode("-9999");
			rsp.setRespDesc(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取白卡数据", summary = "获取白卡数据")
	public GetBlanckCardDataRsp getBlanckCard(GetBlanckCardDataReq req) throws ApiBusiException {
		GetBlanckCardDataRsp rsp = new GetBlanckCardDataRsp();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);

			String opcode = EcsOrderConsts.GET_BLANK_CARD;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);

			rsp = (GetBlanckCardDataRsp) caller.invoke(opcode, param);
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "卡数据同步", summary = "卡数据同步")
	public SynCardDataRsp synCardData(SynCardDataReq req) throws ApiBusiException {
		SynCardDataRsp rsp = new SynCardDataRsp();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);

			String opcode = EcsOrderConsts.SYN_CARD_DATA;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);

			rsp = (SynCardDataRsp) caller.invoke(opcode, param);
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	// songqi
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]后激活订单信息同步接口", summary = "[AOP]后激活订单信息同步接口")
	public OrderInfoSynRsp orderInfoSyn(OrderInfoSynReq req) throws ApiBusiException {
		OrderInfoSynRsp rsp = new OrderInfoSynRsp();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			String opcode = EcsOrderConsts.AOP_ORDER_INFO_SYN;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);

			rsp = (OrderInfoSynRsp) caller.invoke(opcode, param);
		} catch (Exception e) {
			rsp.setError_code("-9999");
			rsp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	// songqi
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]sp类产品订购申请", summary = "[AOP]sp类产品订购申请")
	public FlowPacketPreRsp flowPacketPre(FlowPacketPreReq req) throws ApiBusiException {
		FlowPacketPreRsp rsp = new FlowPacketPreRsp();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			String opcode = EcsOrderConsts.AOP_FLOW_PACKET_PRE;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey)) {
				param.put("bizkey", bizkey);
			}
			rsp = (FlowPacketPreRsp) caller.invoke(opcode, param);
		} catch (Exception e) {
			rsp.setError_code("-9999");
			rsp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	// songqi
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]流量包订购/退订", summary = "[AOP]流量包订购/退订")
	public FlowPacketApplyRsp flowPacketApply(FlowPacketApplyReq req) throws ApiBusiException {
		FlowPacketApplyRsp rsp = new FlowPacketApplyRsp();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			String opcode = EcsOrderConsts.AOP_FLOW_PACKET_APPLY;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey)) {
				param.put("bizkey", bizkey);
			}
			rsp = (FlowPacketApplyRsp) caller.invoke(opcode, param);
		} catch (Exception e) {
			rsp.setError_code("-9999");
			rsp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 续约活动校验和受理接口
	 */
	@Override
	public BusinessAcceptenceAndVerificationResponse businessAcceptenceAndVerification(BusinessAcceptenceAndVerificationReq req) throws ApiBusiException {

		BusinessAcceptenceAndVerificationResponse resp = new BusinessAcceptenceAndVerificationResponse();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = "ecaop.trades.subscription.wb.verify.renewalactivity";
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);

			resp = (BusinessAcceptenceAndVerificationResponse) caller.invoke(opcode, param);
		} catch (Exception e) {
			resp.setCode("-9999");
			// resp.setDeatil(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取EMS物流信息", summary = "获取EMS物流信息")
	public EMSLogisticsInfoResp getEmsLogisticsInfo(EMSLogisticsInfoReq req) throws ApiBusiException {
		// TODO Auto-generated method stub
		EMSLogisticsInfoResp resp = new EMSLogisticsInfoResp();
		CommCaller caller = new CommCaller();
		CommonDataFactory common = new CommonDataFactory();
		List<EMSLogisticsInfoReq> resultList = req.getList();
		// EMSLogisticsInfoReq emsLogisticsInfoReq=null;
		// emsLogisticsInfoReq= resultList.get(i);
		// String dasd=resultList.get(i).getNotNeedReqStrOrderId();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			for (int j = 0; j < resultList.size(); j++) {
				logger.info(resultList.get(j).getNotNeedReqStrOrderId());
				// String order_id=req.getNotNeedReqStrOrderId();
				String order_id = resultList.get(j).getNotNeedReqStrOrderId();
				OrderDeliveryBusiRequest orderDeliveryBusiRequest = common.getDeliveryBusiRequest(order_id, "0");
				BeanUtils.bean2Map(param, orderDeliveryBusiRequest);
				param.put("ord_id", req.getNotNeedReqStrOrderId());
				resp = (EMSLogisticsInfoResp) caller.invoke("ems.getEMSLogisticsInfo", param);
				List list = resp.getTraces();
				for (int i = 0; i < list.size(); i++) {
					Map<String, String> map = (Map<String, String>) list.get(i);
					String contentList = map.get("acceptAddress");
					String acceptTime = map.get("acceptTime");
					String remark = map.get("remark");
					String mail_num = orderDeliveryBusiRequest.getLogi_no();
					OrderRouteLogBusiRequest route = new OrderRouteLogBusiRequest();
					String order_route_id = baseDaoSupport.getSequences("ES_ORDER_ROUTE_LOG_SEQ");
					route.setId(order_route_id);
					route.setOrder_id(orderDeliveryBusiRequest.getOrder_id());
					// route.setRoute_id(mail.getString("serialnumber"));
					route.setMail_no(orderDeliveryBusiRequest.getLogi_no());
					// route.setOp_code(mail.getString("action"));
					route.setRoute_acceptime(acceptTime);
					route.setRoute_acceptadress(contentList);
					route.setRoute_remark(remark);
					route.setCreate_time(DateUtil.currentDateTime());
					route.setCreate_user("root");
					route.setSource_from(ManagerUtils.getSourceFrom());
					route.setDb_action(ConstsCore.DB_ACTION_INSERT);
					route.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					route.store();
					if (orderDeliveryBusiRequest != null) {
						String status = EcsOrderConsts.SIGN_STATUS_1;
						String[] resultRemark = remark.split("，");
						logger.info(resultRemark);
						if (status.equals(orderDeliveryBusiRequest.getSign_status())) {
							break;
						}
						if (EcsOrderConsts.EMS_ORDER_DEAL_STATUS_15.equals(resultRemark[0]) || EcsOrderConsts.EMS_ORDER_DEAL_STATUS_16.equals(resultRemark[0])
								|| EcsOrderConsts.EMS_ORDER_DEAL_STATUS_17.equals(resultRemark[0]) || EcsOrderConsts.EMS_ORDER_DEAL_STATUS_18.equals(resultRemark[0])
								|| EcsOrderConsts.EMS_ORDER_DEAL_STATUS_19.equals(resultRemark[0])) {
							orderDeliveryBusiRequest.setSign_status(EcsOrderConsts.SIGN_STATUS_1);
							orderDeliveryBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							orderDeliveryBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							orderDeliveryBusiRequest.store();
						}
						// else
						// if(!status.equals(orderDeliveryBusiRequest.getSign_status())){//已签收状态不允许变回未签收
						// orderDeliveryBusiRequest.setSign_status(EcsOrderConsts.SIGN_STATUS_0);
						// orderDeliveryBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						// orderDeliveryBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						// orderDeliveryBusiRequest.store();
						// }
					}
				}
			}
		} catch (Exception e) {// 调用接口失败
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS开户正式提交", summary = "BSS开户正式提交")
	public BSSAccountOfficialSubResp subAccountOfficial(BSSAccountOfficialSubReq req) throws ApiBusiException {
		// TODO Auto-generated method stub
		BSSAccountOfficialSubResp resp = new BSSAccountOfficialSubResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			resp = (BSSAccountOfficialSubResp) caller.invoke("ecaop.trades.serv.newu.order.sub", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 本地商城订单状态同步
	 */
	@Override
	public LocalGoodsStatusSynchronizationResp localGoodsStatusSynchronization(LocalGoodsStatusSynchronizationReq req) throws ApiBusiException {

		LocalGoodsStatusSynchronizationResp resp = new LocalGoodsStatusSynchronizationResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(params, req);
			String opcode = "local.goods.status.synchronization";
			resp = (LocalGoodsStatusSynchronizationResp) caller.invoke(opcode, params);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setError_code("-9999");
		}

		return resp;
	}

	/**
	 * 智慧沃企开户正式提交接口
	 */
	@Override
	public AccountOpenFormalSubmissionResp accountOpenFormalSubmission(AccountOpenFormalSubmissionReq req) throws ApiBusiException {

		AccountOpenFormalSubmissionResp resp = new AccountOpenFormalSubmissionResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = "ecaop.trades.serv.newu.order.sub";
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);
			resp = (AccountOpenFormalSubmissionResp) caller.invoke(opcode, param);
		} catch (Exception e) {
			resp.setCode("-9999");
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 接口日志写入
	 */
	@Override
	public ZteResponse recordInfLog(RecordInfLogReq req) throws ApiBusiException {
		ZteResponse resp = new ZteResponse();
		try {
			IInfLogManager infLogManager = SpringContextHolder.getBean("infLogManager");
			InfCommClientCalllog log = new InfCommClientCalllog();
			BeanUtils.copyProperties(log, req);
			log.setParam_desc(StringUtils.substr(req.getReq_xml(), 2000));
			log.setResult_desc(StringUtils.substr(req.getRsp_xml(), 2000));
			infLogManager.log(log);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code("-9999");
		}
		return resp;
	}

	/*
	 * @Override public NotifyOrderInfoWXResponse
	 * notifyOrderInfoWX(NotifyOrderInfoWXRequset req) throws ApiBusiException{
	 * NotifyOrderInfoWXResponse resp = new NotifyOrderInfoWXResponse();
	 * CommCaller caller = new CommCaller(); Map<String, Object> param = new
	 * HashMap<String, Object>(); try { BeanUtils.bean2Map(param, req);
	 * param.put("ord_id", req.getOrderid());
	 * 
	 * 
	 * 
	 * String opcode = "ordercenter.order.status.notify"; resp =
	 * (NotifyOrderInfoWXResponse) caller.invoke(opcode, param); } catch
	 * (Exception e) { resp.setCode("-9999"); e.printStackTrace(); } return
	 * resp;
	 * 
	 * 
	 * }
	 */

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "后向激活订单撤单AOP", summary = "后向激活订单撤单AOP")
	public OrderCancelResp laterActiveOrderCancel(OrderCancelReq req) throws ApiBusiException {
		OrderCancelResp rsp = new OrderCancelResp();
		try {
			// 获取开户工号
			String essId = CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.BSS_OPERATOR);
			// 获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssinfoByEssId(essId, req.getNotNeedReqStrOrderId(), EcsOrderConsts.OPER_TYPE_ESS);
			if (StringUtils.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL) || essRspInfo.getOperInfo() == null) {// 获取工号出错
				rsp.setCode("-9999");
				rsp.setDetail(essRspInfo.getError_msg());
			} else {
				EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
				req.setEssOperInfo(essOperInfo);
				CommCaller caller = new CommCaller();
				Map<String, Object> param = new HashMap<String, Object>();
				BeanUtils.bean2MapForAOP(param, req);
				param.put("ord_id", req.getNotNeedReqStrOrderId());

				String opcode = EcsOrderConsts.AOP_ORDER_CANCEL;
				String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
				if (!StringUtils.isEmpty(bizkey))
					param.put("bizkey", bizkey);

				rsp = (OrderCancelResp) caller.invoke(opcode, param);

			}
		} catch (Exception e) {
			rsp.setCode("-9999");
			rsp.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	public MessageSyncResp messageSync(MessageSyncReq req) throws ApiBusiException {

		MessageSyncResp resp = new MessageSyncResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>(100);
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);

			Map map = new HashMap(100);
			map.put("goods_req", param);
			
			//接收系统编码
			String receive_system = req.getReceive_system();
			
			if(org.apache.commons.lang.StringUtils.isNotBlank(receive_system)){
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				//取通知调用的方法
				String method = cacheUtil.getConfigInfo("GOODS_PUSH_METHOD_"+receive_system);
				
				if(org.apache.commons.lang.StringUtils.isNotBlank(method)){
					resp = (MessageSyncResp) caller.invoke(method, map);
				}
			}
		} catch (Exception e) {
			resp.setResp_code("-9999");
			resp.setResp_msg(e.getMessage());
		}
		return resp;
	}

	@Override
	public ReturnFileResp returnFile(ReturnFileReq req) throws ApiBusiException {
		ReturnFileResp resp = new ReturnFileResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();

		try {
			BeanUtils.bean2MapForAiPBSS(param, req);

			param.put("ord_id", req.getOut_order_id());

			resp = (ReturnFileResp) caller.invoke("ecaop.trades.serv.customerDataRe.subs", param);

		} catch (Exception e) {
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
		}
		return resp;
	}

	@Override
	public QueryStdAddrResp qryStdAddr(QueryStdAddressReq req) throws ApiBusiException {
		QueryStdAddrResp resp = new QueryStdAddrResp();

		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();

		try {
			BeanUtils.bean2MapForAiPBSS(param, req);

			param.put("ord_id", "");
			// Todo
			resp = (QueryStdAddrResp) caller.invoke("ecaop.trades.serv.comm.standerAddr.qry", param);

		} catch (Exception e) {
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
		}
		return resp;
	}

	@Override
	public ResourcePreCheckResp resourcePreCheck(ResourcePreCheckReq req) throws ApiBusiException {
		ResourcePreCheckResp resp = new ResourcePreCheckResp();

		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);

			param.put("ord_id", req.getNotNeedReqStrOrderId());
			// Todo
			resp = (ResourcePreCheckResp) caller.invoke("ecaop.trades.serv.resource.telpre.chk", param);

		} catch (Exception e) {
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
		}
		return resp;
	}

	@Override
	public AreaQueryResp areaQuery(AreaQueryReq req) throws ApiBusiException {
		AreaQueryResp resp = new AreaQueryResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);

			param.put("ord_id", req.getNotNeedReqStrOrderId());
			// Todo
			resp = (AreaQueryResp) caller.invoke("yth.wssmall.check.areaQuery", param);

		} catch (Exception e) {
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
		}

		return resp;
	}

	/**
	 * 查询任务人员
	 * 
	 * @author song.qi
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@Override
	public OrderListForWorkResp getOperator(OrderListForWorkReq req) throws ApiBusiException {
		OrderListForWorkResp resp = new OrderListForWorkResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			param.put("Ordertype", req.getOrdertype());
			ICacheUtil util = (ICacheUtil) SpringContextHolder.getBean("cacheUtil");
			String DES_KEY = util.getConfigInfo("DES_KEY");// unic0Mnetunic0m6
			String eparchyCode = req.getEparchyCode();
			String cityCode = req.getCityCode();
			String decodeData = DesUtil.encode("eparchyCode=" + eparchyCode + "&cityCode=" + cityCode, DES_KEY);
			param.put("decodeData", decodeData);
			resp = (OrderListForWorkResp) caller.invoke("yth.wssmall.receive.staffQuery", param);
		} catch (Exception e) {
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
		}

		// OrderListForWorkResp resp = new OrderListForWorkResp();
		// try {
		// String eparchyCode = req.getEparchyCode();
		// String cityCode = req.getCityCode();
		// String Ordertype = req.getOrdertype();
		// //Ordertype = "01";
		// ICacheUtil util = (ICacheUtil)
		// SpringContextHolder.getBean("cacheUtil");
		// // http://132.151.42.80:8012/rest/orderQuery/orderList/01/...
		// String url = util.getConfigInfo("WORK_URL");
		// String DES_KEY = util.getConfigInfo("DES_KEY");// unic0Mnetunic0m6
		// url = url + Ordertype + "/" + DesUtil.encode("eparchyCode=" +
		// eparchyCode + "&cityCode=" + cityCode, DES_KEY);
		// logger.info(url);
		// String results = HttpClientUtils4Work.get(url, "UTF-8", 60000,
		// 60000);
		// if (!StringUtil.isEmpty(results)) {
		// results = results.replace("resp", "respJson");
		// JSONObject json = JSONObject.fromObject(results);
		// // Map map = new HashMap();
		// // map.put("stus", OrderListForWorkResp.class);
		// // resp = (OrderListForWorkResp) JSONObject.toBean(json,
		// // OrderListForWorkResp.class, map);
		// resp.setCode(json.getString("code"));
		// resp.setMsg(json.getString("code"));
		// JSONObject respJson = json.getJSONObject("respJson");
		// JSONArray staffListJson = respJson.getJSONArray("staffList");
		// logger.info(staffListJson);
		// if (staffListJson.size() > 0) {
		// StaffListResp staffListResp = new StaffListResp();
		// List<StaffList> staffList = new ArrayList<StaffList>();
		// StaffList staff = new StaffList();
		// for (int i = 0; i < staffListJson.size(); i++) {
		// // logger.info(staffListJson.get(i));
		// JSONObject obj = staffListJson.getJSONObject(i);
		// staff = new StaffList();
		// staff.setPhoneNum(obj.getString("phoneNum"));
		// staff.setStaffCode(obj.getString("staffCode"));
		// staff.setStaffName(obj.getString("staffName"));
		// staffList.add(staff);
		// }
		// staffListResp.setStaffList(staffList);
		// resp.setRespJson(staffListResp);
		// }
		// }
		// } catch (Exception e) {
		// resp.setCode("-9999");
		// resp.setMsg(e.getMessage());
		// e.printStackTrace();
		// }
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "[AOP]客户资料信息修改", summary = "[AOP]客户资料信息修改")
	public CustInfoModRsp custInfoMod(CustInfoModReq req) throws ApiBusiException {
		CustInfoModRsp rsp = new CustInfoModRsp();
		try {
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			String opcode = EcsOrderConsts.AOP_CUST_INFO_MOD;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey)) {
				param.put("bizkey", bizkey);
			}
			rsp = (CustInfoModRsp) caller.invoke(opcode, param);
		} catch (Exception e) {
			rsp.setError_code("-9999");
			rsp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "新用户主副卡开户处理申请", summary = "新用户主副卡开户处理申请")
	public MainViceOpenResp mainViceOpen(MainViceOpenReq req) throws ApiBusiException {
		MainViceOpenResp rsp = new MainViceOpenResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			String opcode = EcsOrderConsts.AOP_MAIN_VICE_OPEN;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey)) {
				param.put("bizkey", bizkey);
			}
			// 调用接口
			rsp = (MainViceOpenResp) caller.invoke(opcode, param);
		} catch (Exception e) {
			rsp.setError_code("-9999");
			rsp.setError_msg("调用接口异常:" + e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "号卡开户正式提交接口", summary = "正式提交接口")
	public OpenAccountSubmitResp openAccountSubmit(OpenAccountSubmitReq req) throws ApiBusiException {
		OpenAccountSubmitResp rsp = new OpenAccountSubmitResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			String opcode = EcsOrderConsts.OPEN_ACC_SUBMIT_AOP;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey)) {
				param.put("bizkey", bizkey);
			}
			rsp = (OpenAccountSubmitResp) caller.invoke(opcode, param);
		} catch (Exception e) {
			rsp.setError_code("-9999");
			rsp.setError_msg("调用接口异常:" + e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "老用户加入主副卡", summary = "老用户加入主副卡")
	public UserJoinMainViceCardResp joinMVCard(UserJoinMainViceCardReq req) throws ApiBusiException {
		UserJoinMainViceCardResp rsp = new UserJoinMainViceCardResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			String opcode = EcsOrderConsts.JOIN_MAIN_VICE_AOP;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey)) {
				param.put("bizkey", bizkey);
			}
			rsp = (UserJoinMainViceCardResp) caller.invoke(opcode, param);
		} catch (Exception e) {
			rsp.setError_code("-9999");
			rsp.setError_msg("调用接口异常:" + e.getMessage());
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
    @ZteSoftCommentAnnotation(type = "method", desc = "客户资料创建", summary = "客户资料创建")
    public CustInfoCreateResponse CustInfoCreate(CustInfoCreateReq req) throws ApiBusiException {
	    CustInfoCreateResponse rsp = new CustInfoCreateResponse();
        CommCaller caller = new CommCaller();
        Map<String, Object> param = new HashMap<String, Object>();
        try {
            BeanUtils.bean2MapForAOP(param, req);
            param.put("ord_id", req.getNotNeedReqStrOrderId());
            String opcode = EcsOrderConsts.CUST_INFO_CREATE_AOP;
            String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
            if (!StringUtils.isEmpty(bizkey)) {
                param.put("bizkey", bizkey);
            }
            // 调用接口
            rsp = (CustInfoCreateResponse) caller.invoke(opcode, param);
        } catch (Exception e) {
            rsp.setError_code("-9999");
            rsp.setError_msg("调用接口异常:" + e.getMessage());
            e.printStackTrace();
        }
        return rsp;
    }
	   /**
     * 
     * 浙江省份号码预占接口
     */
    @Override
    public NumberResourceChangePreCaptureZjResponse numberResourceNewGroupOrderZjRequest(NumberResourceNewGroupOrderZjRequest req) throws ApiBusiException {
        NumberResourceChangePreCaptureZjResponse rsp = new NumberResourceChangePreCaptureZjResponse();
        try {
            CommCaller caller = new CommCaller();
            Map<String, Object> param = new HashMap<String, Object>();
            BeanUtils.bean2MapForAOP(param, req);
            param.put("ord_id", req.getNotNeedReqStrOrderId());
            String opcode = "ecaop.trades.serv.newu.numprehold.app";
            String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
            if (!StringUtils.isEmpty(bizkey))
                param.put("bizkey", bizkey);
            rsp = (NumberResourceChangePreCaptureZjResponse) caller.invoke(opcode, param);
        } catch (Exception e) {
            rsp.setCode("-9999");
            rsp.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return rsp;

    }

	@Override
	public OrderTerminalCheckResp orderTerminalCheck(OrderTerminalCheckReq req) throws ApiBusiException {
		OrderTerminalCheckResp resp = new OrderTerminalCheckResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = "ecaop.trades.base.resi.pre.chk";

			resp = (OrderTerminalCheckResp) caller.invoke(opcode, param);
		} catch (Exception e) {
			resp.setCode("-9999");
			// resp.setDeatil(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}
}