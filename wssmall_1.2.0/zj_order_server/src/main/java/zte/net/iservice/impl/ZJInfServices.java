
package zte.net.iservice.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.testng.collections.Lists;

import params.coqueue.req.CoQueueAddReq;
import params.coqueue.resp.CoQueueAddResp;
import params.order.req.OrderHandleLogsReq;
import util.EssOperatorInfoUtil;
import util.ThreadLocalUtil;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderAdslBusiRequest;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderRealNameInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.AopBrdOpenAppReq;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_sub.AopBrdOpenSubReq;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.resp.CardDataQryResponse;
import zte.net.ecsord.params.ecaop.resp.CommAopApplyRsp;
import zte.net.ecsord.params.ecaop.resp.CommAopSubmitRsp;
import zte.net.ecsord.params.ecaop.resp.MainViceOpenResp;
import zte.net.ecsord.params.ecaop.resp.OpenDealApplyResp;
import zte.net.ecsord.params.ecaop.util.AopInterUtil;
import zte.net.ecsord.params.nd.req.OrderResultNotifyReq;
import zte.net.ecsord.params.nd.resp.OrderResultNotifyResp;
import zte.net.ecsord.params.nd.resp.OrderResultNotifyRsp;
import zte.net.ecsord.params.nd.resp.OrderResultNottifyRspZte;
import zte.net.ecsord.params.nd.resp.WechatRetBean;
import zte.net.ecsord.params.nd.vo.OrderResultNotifyVo;
import zte.net.ecsord.params.workCustom.po.ES_WORK_SMS_SEND;
import zte.net.ecsord.params.workCustom.po.WORK_CUSTOM_FLOW_DATA;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.ecsord.utils.SpecUtils;
import zte.net.iservice.IZJInfServices;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.RuleTreeExeResp;
import zte.params.orderctn.resp.OrderCtnResp;

import com.alibaba.fastjson.JSON;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.MD5Util;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.crm.report.lang.Utils;
import com.ztesoft.form.util.MarkTime;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.inf.communication.client.CommCaller;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.ecsord.params.ecaop.req.AmountPayReq;
import com.ztesoft.net.ecsord.params.ecaop.req.AppOrderCancelReq;
import com.ztesoft.net.ecsord.params.ecaop.req.AuditOrderCancelReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BandUserDataReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BroadbandOrderInfoRefundReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BroadbandOrderSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BroadbandPreSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BroadbandrefundReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BusiHandleAPPReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BusiHandleBSSReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BusiHandleCheckAPPReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BusiHandleCheckBSSReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BusinessFeeQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CancelOrderStatusQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CardDataSyncBssReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CardInfoGetAPPReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CardInfoGetBSSReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CbssrefundFeeQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ChangeAppReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ChangeSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ChgcardnoPrecommitReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CmcSmsSendReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CustInfoOrderBindReq;
import com.ztesoft.net.ecsord.params.ecaop.req.DeliveryInfoUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.DepositOrderReq;
import com.ztesoft.net.ecsord.params.ecaop.req.DownloadRecordReq;
import com.ztesoft.net.ecsord.params.ecaop.req.DwzCnCreateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ElectronicVolumeSendReq;
import com.ztesoft.net.ecsord.params.ecaop.req.FtthPreSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.FuKaPreOpenReq;
import com.ztesoft.net.ecsord.params.ecaop.req.GeneralOrderQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.GroupOrderCardCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.req.GroupOrderFixedNetworkCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.req.GroupOrderSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.GuWangPreSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.InitiationCallReq;
import com.ztesoft.net.ecsord.params.ecaop.req.IntentOrderQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.KdNumberCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.req.KdnumberQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.O2OStatusUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ObjectQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ObjectReplacePreSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OldUserCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderCancelPreCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderCancelSubmitReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderChargeReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderDepositReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderDetailReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderFormalSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderGoodsQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoListQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoRefundUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoUpdateNewReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderListActivateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderListByWorkQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderMakeupReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderPayPreCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderPhotoInfoReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderPreSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderResultQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderStatusQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderStatusQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.PayResultAPPReq;
import com.ztesoft.net.ecsord.params.ecaop.req.PayWorkOrderUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.PreCommitAPPReq;
import com.ztesoft.net.ecsord.params.ecaop.req.PreCommitBSSReq;
import com.ztesoft.net.ecsord.params.ecaop.req.PromoteProductReq;
import com.ztesoft.net.ecsord.params.ecaop.req.QryBroadbandFeeReq;
import com.ztesoft.net.ecsord.params.ecaop.req.QryFtthObjIdReq;
import com.ztesoft.net.ecsord.params.ecaop.req.QueryCalllogReq;
import com.ztesoft.net.ecsord.params.ecaop.req.QueryGoodsListReq;
import com.ztesoft.net.ecsord.params.ecaop.req.QuerySelectNumberReq;
import com.ztesoft.net.ecsord.params.ecaop.req.RateStatusReq;
import com.ztesoft.net.ecsord.params.ecaop.req.RelSelectionNumReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ResourCecenterAppReq;
import com.ztesoft.net.ecsord.params.ecaop.req.SchemeFeeQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.SelfspreadOrderinfoSynReq;
import com.ztesoft.net.ecsord.params.ecaop.req.TabuserBtocbSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.TradeQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.UserActivationReq;
import com.ztesoft.net.ecsord.params.ecaop.req.UserDataQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WeiBoShortUrlReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WisdomHomePreSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WorkOrderListQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WorkOrderMixOrdUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WorkOrderUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WotvBroadbandBindReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WotvUserSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WriteCardResultAPPReq;
import com.ztesoft.net.ecsord.params.ecaop.req.orderInfoBackfill.OrderInfoBackfillReq;
import com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack.OrderReceiveBackReq;
import com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack.ReqBody;
import com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack.UNI_BSS_ATTACHED;
import com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack.UNI_BSS_BODY;
import com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack.UNI_BSS_HEAD;
import com.ztesoft.net.ecsord.params.ecaop.resp.AmountPayResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.AppOrderCancelResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.AuditOrderCancelResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BandUserDataResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BroadbandOrderInfoRefundResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BroadbandOrderSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BroadbandPreSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BroadbandRefundResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BusiHandleAPPResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BusiHandleBSSResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BusiHandleCheckAPPResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BusiHandleCheckBSSResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BusinessFeeQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CancelOrderStatusQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CardDataSyncBssResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CardInfoGetAPPResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CardInfoGetBSSResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CbssrefundFeeQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ChangeAppResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ChangeSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ChgcardnoPrecommitResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CmcSmsSendResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CustInfoOrderBindResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.DeliveryInfoUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.DepositSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.DownloadRecordResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.DwzCnCreateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ElectronicVolumeSendResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.FtthPreSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.FuKaPreOpenResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.GeneralOrderQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.GoodsListQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.GroupOrderSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.GuWangPreSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.InitiationCallResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.IntentOrderQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.KdNumberCheckResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.KdnumberQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.O2OStatusUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ObjectQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ObjectReplacePreSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OldUserCheckResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderCancelPreCheckResponse;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderCancelSubmitResponse;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderChargeResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderDetailResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderGoodsQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderInfoListQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderInfoQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderInfoRefundUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderInfoUpdateNewResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderInfoUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderListActivateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderListByWorkQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderMakeupResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderPhotoInfoResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderPreSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderResultQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderStatusQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderStatusQueryResponse;
import com.ztesoft.net.ecsord.params.ecaop.resp.PayPreCheckResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.PayResultAPPResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.PayWorkOrderUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.PreCommitAPPResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.PromoteProductResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.QryBroadbandFeeResponse;
import com.ztesoft.net.ecsord.params.ecaop.resp.QryFtthObjIDResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.QueryCalllogResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.RateStatusResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ResourCecenterAppResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.SchemeFeeQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.SelfspreadOrderinfoSynResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.TabuserBtocbSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.TradeQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.UserActivationResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.UserDataQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WeiBoShortUrlResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WisdomHomeSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WorkOrderListQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WorkOrderMixOrdUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WorkOrderUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WotvBroadbandBindResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WotvUserSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WriteCardResultAPPResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.orderInfoBackfill.OrderInfoBackfillRsp;
import com.ztesoft.net.ecsord.params.ecaop.resp.orderReceiveBack.OrderReceiveBackResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.BroadbandFee;
import com.ztesoft.net.ecsord.params.ecaop.vo.BroadbandFeeVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.BroadbandRefundBussiInfoVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.BroadbandRefundFeeVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.BroadbandRefundOrderInfoVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.BssOrderId;
import com.ztesoft.net.ecsord.params.ecaop.vo.CustInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.DeliveryInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.DeveloperInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.GoodsInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.GoodsInfoVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.GroupInFoVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.IntentOrderInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.NiceInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.NiceInfoVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderBaseInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderCustInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderDeliveryInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderDetail;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderDetailVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderDeveloperInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderListQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderPayInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderQueryInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderResultResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.PayInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.PayPreCheckRespVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.PhoneInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.WeiBoShortUrlVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.generalorderinfo.GeneralOrderInfo;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.rule.util.RuleFlowUtil;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrdIntentManager;
import com.ztesoft.net.service.IOrdWorkManager;
import com.ztesoft.net.service.IOrderExtManager;
import com.ztesoft.net.service.IOrderFlowManager;
import com.ztesoft.net.service.IOrderInfManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomCfgManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomEngine;
import com.ztesoft.net.util.ZjCommonUtils;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;

import consts.ConstsCore;


public class ZJInfServices implements IZJInfServices {
	private static Logger logger = Logger.getLogger(ZJInfServices.class);
	@Resource
	private IOrderInfManager orderInfManager;
	@Resource
	private IOrderFlowManager ordFlowManager;
	@Resource
	private IOrderExtManager orderExtManager;
	@Resource
	private IEcsOrdManager ecsOrdManager;
	@Resource
	private IOrdWorkManager ordWorkManager;
	
	@Resource
	private IWorkCustomCfgManager workCustomCfgManager;
	
	@Resource
	private IWorkCustomEngine workCustomEngine;
	@Resource
    private ICacheUtil cacheUtil;
	@Resource
	private IOrdIntentManager ordIntentManager;
	@Resource
	private IWorkCustomEngine iWorkCustomEngine;
	
	private String ORD_PRE_SUB_RULE_ID = "170201453002000754";
	// @Override
	// public ZJTestInfResponse zjTestInf(ZJTestInfRequest req) {
	// NotifyOrderInfoZBResponse rsp = new NotifyOrderInfoZBResponse();
	// try {
	// CommCaller caller = new CommCaller();
	// Map<String, Object> param = new HashMap<String, Object>();
	// BeanUtils.bean2Map(param, req);
	// param.put("ord_id", req.getNotNeedReqStrOrderId());
	// rsp = (NotifyOrderInfoZBResponse) caller.invoke("periphery.neworderchg",
	// param);
	// } catch (Exception e) {
	// rsp.setRespCode("-9999");
	// rsp.setRespDesc(e.getMessage());
	// e.printStackTrace();
	// }
	// ZJTestInfResponse zjResponse = new ZJTestInfResponse();
	// zjResponse.setRespCode(rsp.getRespCode());
	// zjResponse.setRespDesc(rsp.getRespCode());
	// return zjResponse;
	// }

	public IOrderFlowManager getOrdFlowManager() {
		return ordFlowManager;
	}

	public void setOrdFlowManager(IOrderFlowManager ordFlowManager) {
		this.ordFlowManager = ordFlowManager;
	}

	/**
	 * 订单撤销申请接口
	 */
	@Override
	public OrderCancelPreCheckResponse orderCancelPreCheck(OrderCancelPreCheckReq req) throws ApiBusiException {
		OrderCancelPreCheckResponse resp = new OrderCancelPreCheckResponse();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = "ecaop.trades.query.iom.orderCancel.precheck";
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);
			resp = (OrderCancelPreCheckResponse) caller.invoke(opcode, param);
		} catch (Exception e) {
			resp.setCode("-9999");
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 订单正式撤单接口
	 */
	@Override
	public OrderCancelSubmitResponse orderCancelSubmit(OrderCancelSubmitReq req) throws ApiBusiException {
		OrderCancelSubmitResponse resp = new OrderCancelSubmitResponse();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = "ecaop.trades.query.iom.orderCancel.submit";
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);
			resp = (OrderCancelSubmitResponse) caller.invoke(opcode, param);
		} catch (Exception e) {
			resp.setCode("-9999");
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "宽带受理费用查询", summary = "宽带受理费用查询")
	public QryBroadbandFeeResponse qryBroadbandFee(QryBroadbandFeeReq req) throws ApiBusiException {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		QryBroadbandFeeResponse resp = new QryBroadbandFeeResponse();

		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			Map map = orderInfManager.qryGoodsDtl(req.getNotNeedReqStrOrderId());
			req.setScheme_id(Const.getStrValue(map, "p_code") + "");
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (QryBroadbandFeeResponse) caller.invoke("ecaop.trades.base.net.fee.qry", param);
			List<AttrFeeInfoBusiRequest> feeInfoBusiRequests = CommonDataFactory.getInstance()
					.getOrderTree(req.getNotNeedReqStrOrderId()).getFeeInfoBusiRequests();

			if (resp.getCode().equals("00000") && !StringUtils.isEmpty(resp.getCode())) {
				BroadbandFee broadbandFee = resp.getRespJson();
				if (broadbandFee.getFee_list() instanceof List) {
					List<BroadbandFeeVO> list = broadbandFee.getFee_list();
					for (int i = 0; i < feeInfoBusiRequests.size(); i++) {
						/*if (!StringUtils.isEmpty(feeInfoBusiRequests.get(i).getIs_aop_fee())
								&& StringUtil.equals("2", feeInfoBusiRequests.get(i).getIs_aop_fee())) {*/
							feeInfoBusiRequests.get(i).setIs_dirty(ConstsCore.IS_DIRTY_YES);
							feeInfoBusiRequests.get(i).setDb_action(ConstsCore.DB_ACTION_DELETE);
							feeInfoBusiRequests.get(i).store();
						/*}*/
					}
					for (int j = 0; j < list.size(); j++) {
						AttrFeeInfoBusiRequest attrfee = new AttrFeeInfoBusiRequest();

						attrfee.setFee_item_name(list.get(j).getFee_desc());// 费用名称
						attrfee.setFee_item_code(list.get(j).getSubject_id());// 账本ID
						attrfee.setO_fee_num(Double.valueOf(list.get(j).getNeed_amount()));// 应收费用
						attrfee.setDiscount_fee(Double.valueOf(list.get(j).getDeration_amount()));// 减免费用
						attrfee.setN_fee_num(Double.valueOf(list.get(j).getReal_amount()));// 实收费用
						attrfee.setFee_category(list.get(j).getFee_rule_id());
						attrfee.setIs_aop_fee("2");
						attrfee.setOrder_id(req.getNotNeedReqStrOrderId());
						attrfee.setInst_id(req.getNotNeedReqStrOrderId());
						attrfee.setFee_inst_id(baseDaoSupport.getSequences("seq_attr_fee_info"));
						attrfee.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						attrfee.setDb_action(ConstsCore.DB_ACTION_INSERT);
						attrfee.store();
					}
				}
			} else {
				resp.setCode("-9999");
				resp.setError_msg(resp.getMsg());
			}

		} catch (Exception e) {// 调用接口失败
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}

	// 宽带受理预提交接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "宽带受理预提交接口", summary = "宽带受理预提交接口")
	public BroadbandPreSubResp broadbandPreSub(BroadbandPreSubReq req) throws ApiBusiException {
		BroadbandPreSubResp resp = new BroadbandPreSubResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			CommonDataFactory.getInstance().updateOrderTree(req.getNotNeedReqStrOrderId());
			Map map = orderInfManager.qryGoodsDtl(req.getNotNeedReqStrOrderId());

			logger.info(CommonDataFactory.getInstance().getOrderTree(req.getNotNeedReqStrOrderId()).getOrderDeliveryBusiRequests().get(0).getShip_tel());

			req.setScheme_id(Const.getStrValue(map, "p_code") + "");
			req.setProduct_id(Const.getStrValue(map, "sn") + "");
			
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (BroadbandPreSubResp) caller.invoke("ecaop.trades.base.net.pre.sub", param);
			
			if (!StringUtils.isEmpty(resp.getCode()) && "00000".equals(resp.getCode()) ) {
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance()
						.getOrderTree(req.getNotNeedReqStrOrderId());
				List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
				OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0)
						.getOrderItemExtBusiRequest();
				BssOrderId BssOrderId = resp.getRespJson();
				if (BssOrderId.getBms_accept_id() instanceof String) {
					orderItemExtBusiRequest.setBssOrderId(BssOrderId.getBms_accept_id());
					orderItemExtBusiRequest.setActive_no(BssOrderId.getBms_accept_id());
					orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					orderItemExtBusiRequest.store();
				}

			} else {
				resp.setCode("-9999");
				resp.setError_msg(resp.getMsg());
			}

		} catch (Exception e) {// 调用接口失败
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}

	// 沃TV新装接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "沃TV新装接口", summary = "沃TV新装接口")
	public WotvUserSubResp wotvUserSub(WotvUserSubReq req) throws ApiBusiException {
		WotvUserSubResp resp = new WotvUserSubResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {

			Map map = orderInfManager.qryGoodsDtl(req.getNotNeedReqStrOrderId());
			logger.info(CommonDataFactory.getInstance().getOrderTree(req.getNotNeedReqStrOrderId()).getOrderDeliveryBusiRequests().get(0).getShip_tel());

			req.setScheme_id(Const.getStrValue(map, "p_code") + "");
			req.setProduct_id(Const.getStrValue(map, "sn") + "");
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (WotvUserSubResp) caller.invoke("ecaop.trades.base.wotv.user.sub", param);
			if (resp.getCode().equals("00000") && !StringUtils.isEmpty(resp.getCode())) {
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance()
						.getOrderTree(req.getNotNeedReqStrOrderId());
				List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
				OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0)
						.getOrderItemExtBusiRequest();
				BssOrderId BssOrderId = resp.getRespJson();
				if (BssOrderId.getBms_accept_id() instanceof String) {
					orderItemExtBusiRequest.setBssOrderId(BssOrderId.getBms_accept_id());
					orderItemExtBusiRequest.setActive_no(BssOrderId.getBms_accept_id());
					orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					orderItemExtBusiRequest.store();
				}

			} else {
				resp.setCode("-9999");
				resp.setError_msg(resp.getMsg());
			}

		} catch (Exception e) {// 调用接口失败
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}

	// 宽带绑沃TV接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "宽带绑沃TV接口", summary = "宽带绑沃TV接口")
	public WotvBroadbandBindResp wotvBroadbandBind(WotvBroadbandBindReq req) throws ApiBusiException {
		WotvBroadbandBindResp resp = new WotvBroadbandBindResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {

			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (WotvBroadbandBindResp) caller.invoke("ecaop.trades.base.wotv.broadband.bind", param);
		} catch (Exception e) {// 调用接口失败
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}

	// 光改预提交接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "光改预提交接口", summary = "光改预提交接口")
	public FtthPreSubResp ftthPreSub(FtthPreSubReq req) throws ApiBusiException {
		FtthPreSubResp resp = new FtthPreSubResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {

			Map map = orderInfManager.qryGoodsDtl(req.getNotNeedReqStrOrderId());

			logger.info(CommonDataFactory.getInstance().getOrderTree(req.getNotNeedReqStrOrderId()).getOrderDeliveryBusiRequests().get(0).getShip_tel());

			req.setProduct_id(Const.getStrValue(map, "sn") + "");
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (FtthPreSubResp) caller.invoke("ecaop.trades.base.ftth.pre.sub", param);
			if (resp.getCode().equals("00000") && !StringUtils.isEmpty(resp.getCode())) {
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance()
						.getOrderTree(req.getNotNeedReqStrOrderId());
				List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
				OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0)
						.getOrderItemExtBusiRequest();
				BssOrderId BssOrderId = resp.getRespJson();
				if (BssOrderId.getBms_accept_id() instanceof String) {
					orderItemExtBusiRequest.setBssOrderId(BssOrderId.getBms_accept_id());
					orderItemExtBusiRequest.setActive_no(BssOrderId.getBms_accept_id());
					orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					orderItemExtBusiRequest.store();
				}

			} else {
				resp.setCode("-9999");
				resp.setError_msg(resp.getMsg());
			}

		} catch (Exception e) {// 调用接口失败
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}

	// 宽带户正式提交接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "宽带户正式提交接口", summary = "宽带户正式提交接口")
	public BroadbandOrderSubResp broadbandOrdSub(BroadbandOrderSubReq req) throws ApiBusiException {
		BroadbandOrderSubResp resp = new BroadbandOrderSubResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (BroadbandOrderSubResp) caller.invoke("ecaop.trades.serv.newu.order.sub", param);

		} catch (Exception e) { 
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}

	/**
	 * 电子卷发放接口
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "电子卷发放接口", summary = "电子卷发放接口")
	public ElectronicVolumeSendResp electronicVolumeSend(ElectronicVolumeSendReq req) {
		ElectronicVolumeSendResp resp = new ElectronicVolumeSendResp();

		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			// 传进来有version字段不去除
			BeanUtils.bean2MapForYR(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (ElectronicVolumeSendResp) caller.invoke("yunr.ECoupon.date.send", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("-9999");
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 订单信息列表查询接口
	 *
	 * @param requ
	 *            请求参数
	 * @return
	 *
	 * @author 宋琪
	 * @throws Exception
	 *
	 * @date 2017年6月2日
	 */
	@Override
	public OrderInfoListQueryResp queryOrderInfoListByRequ(OrderInfoListQueryReq requ) throws Exception {
		//
		OrderInfoListQueryResp resp = new OrderInfoListQueryResp();
		resp.setQuery_resp(new ArrayList<OrderListQueryResp>());

		try {

			resp = orderInfManager.queryOrderInfoList(requ);

		} catch (Exception e) {
			resp.setResp_code("1");
			resp.setResp_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}

		return resp;
	}

	/**
	 * 工单列表查询
	 * 
	 * @author 宋琪
	 * @date 2017年12月1日
	 */
	@Override
	public WorkOrderListQueryResp queryWorkOrderListByRequ(WorkOrderListQueryReq requ) throws Exception {
		WorkOrderListQueryResp resp = new WorkOrderListQueryResp();
		try {
			resp = orderInfManager.queryWorkOrderListByRequ(requ);
		} catch (Exception e) {
			resp.setResp_code("1");
			resp.setResp_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 工单状态同步接口
	 * 
	 * @author 宋琪
	 * @date 2017年12月1日
	 */
	@Override
	public WorkOrderUpdateResp updateWorkOrderUpdateByRequ(WorkOrderUpdateReq requ) throws Exception {
		WorkOrderUpdateResp resp = new WorkOrderUpdateResp();
		try {
			resp = orderInfManager.updateWorkOrderUpdateByRequ(requ);
		} catch (Exception e) {
			resp.setResp_code("1");
			resp.setResp_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 工单状态同步接口
	 * 
	 * @author 宋琪
	 * @date 2017年12月1日
	 */
	@Override
	public PayWorkOrderUpdateResp updatePayWorkOrderUpdateByRequ(PayWorkOrderUpdateReq requ) throws Exception {
		PayWorkOrderUpdateResp resp = new PayWorkOrderUpdateResp();
		try {
			resp = orderInfManager.updatePayWorkOrderUpdateByRequ(requ);
		} catch (Exception e) {
			resp.setResp_code("1");
			resp.setResp_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 订单信息查询接口
	 *
	 * @param requ
	 *            请求参数
	 * @return
	 *
	 * @author liu.quan68@ztesoft.com
	 * @throws Exception
	 *             修改：宋琪2017年6月14日 17:01:28
	 *
	 * @date 2017年2月27日
	 */
	@Override
	public OrderInfoQueryResp queryOrderInfoByRequ(OrderInfoQueryReq requ) throws Exception {
		OrderInfoQueryResp resp = new OrderInfoQueryResp();
		List<OrderQueryResp> query_resp = new ArrayList<OrderQueryResp>();
		resp.setQuery_resp(query_resp);
		try {
			/*
			 * 模板ID templateId 01.根据订单中心订单号查询模板 02.根据业务号码查询订单信息模板
			 * 03.根据证件号码查询订单信息模板 04.根据联系电话和ICCD查询订单信息(后激活专用) 05.根据外系统单号查询模板
			 */
			String templateId = requ.getTemplet_id();
			OrderQueryInfo query_info = requ.getQuery_info();
			String orderId = null;
			if ("01".equals(templateId)) {
				orderId = query_info.getOut_order_id();
			} else if ("02".equals(templateId)) {// 单个业务号码可以有多条记录所以不可行
			} else if ("03".equals(templateId)) {// 单个证件号码可以有多条记录所以不可行
			} else if ("04".equals(templateId)) {// 04.根据联系电话和ICCD查询订单信息(后激活专用)
				String iccid = query_info.getICCID();
				// APP传过来的ICCID是后八位，这里判断一下
				if (!StringUtil.isEmpty(iccid) && iccid.length() == 8) {
					String contact_phone = query_info.getContact_phone();
					iccid = iccid.substring(0, iccid.length() - 1);
					orderId = orderInfManager.queryOrderInfo(iccid, contact_phone);
				} else {
					resp.setResp_code("1");
					resp.setResp_msg("输入的ICCID位数不正确，请输入正确位数（末尾八位）的ICCID");
				}
			} else if ("05".equals(templateId)) {// 05.根据外部单号查询订单信息
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String max_num = cacheUtil.getConfigInfo("QRY_ORDER_LAG_NUM");
				String sleep_time = cacheUtil.getConfigInfo("QRY_ORDER_LAG_TIME");
				int max = Integer.parseInt(max_num);
				for (int i = 0; i < max; i++) {
					Thread.sleep(Integer.parseInt(sleep_time));
					OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance()
							.getOrderTreeByOutId(query_info.getOut_order_id());
					if (orderTree != null) {
						orderId = orderTree.getOrder_id();
						if (!StringUtil.isEmpty(orderId)) {
							break;
						}
					}
				}
				if (orderId != null) {

				} else {
					resp.setResp_code("1");
					resp.setResp_msg("外系统单号有误，请核对外系统单号是否存在");
				}

			}
			// 如果前面没有设置状态
			if (StringUtil.isEmpty(resp.getResp_code())) {
				// 如果查询到有效的订单信息s
				if (!StringUtil.isEmpty(orderId)) {
					OrderQueryResp orderQueryResp = new OrderQueryResp();
					
					CommonDataFactory.getInstance().updateOrderTree(orderId);
					
					OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(orderId);
					// 返回参数的获取
					String bus_num = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PHONE_NUM);// 业务号码
					String activite_status = orderTree.getOrderRealNameInfoBusiRequest().getActive_flag();// 号码激活状态
					String cert_num = CommonDataFactory.getInstance().getAttrFieldValue(orderId,
							AttrConsts.CERT_CARD_NUM);// 证件号码
					String cust_name = CommonDataFactory.getInstance().getAttrFieldValue(orderId,
							AttrConsts.PHONE_OWNER_NAME);// 客户姓名
					// 新增
					OrderBaseInfo orderBaseInfo = new OrderBaseInfo();
					OrderCustInfo orderCustInfo = new OrderCustInfo();
					OrderPayInfo orderPayInfo = new OrderPayInfo();
					OrderDeveloperInfo orderDeveloperInfo = new OrderDeveloperInfo();
					OrderDeliveryInfo orderDeliveryInfo = new OrderDeliveryInfo();
					//add wjq  查询出靓号信息节点
					NiceInfo niceInfo = new NiceInfo();
					
					if(null != orderTree.getPhoneInfoBusiRequest()){
						niceInfo.setClassId(orderTree.getPhoneInfoBusiRequest().getClassId());
						niceInfo.setLhscheme_id(orderTree.getPhoneInfoBusiRequest().getLhscheme_id());
						niceInfo.setAdvancePay(orderTree.getPhoneInfoBusiRequest().getAdvancePay());
						niceInfo.setLowCostPro(orderTree.getPhoneInfoBusiRequest().getLowCostPro());
						niceInfo.setPre_fee(orderInfManager.getLiangPrice(orderId));
						niceInfo.setTimeDurPro(orderTree.getPhoneInfoBusiRequest().getTimeDurPro());
						
						orderQueryResp.setNiceInfo(niceInfo);
					}
					
					// 还有一些参数的获取
					Map map = orderInfManager.getOrderInfo(orderId);
					// 订单来源
					orderBaseInfo.setSource_from(
							CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ORDER_FROM));
					// 外部订单号
					orderBaseInfo.setOut_order_id(
							CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.OUT_TID));
					// 订单状态 //优先级，已退款>异常>已退单>处理完成、处理中
					orderBaseInfo.setOrder_status(map.get("order_status") + "");
					// 订单归属地市
					orderBaseInfo.setOrder_city_code(
							CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ORDER_CITY_CODE));
					/**
					 * Add Wcl 订单异常信息
					 */
					if (StringUtils.equals("05", MapUtils.getString(map, "order_status"))) {
						orderBaseInfo.setException_desc(MapUtils.getString(map, "exception_desc"));
					} else {
						orderBaseInfo.setException_desc("");
					}
					/**
					 * add sgf 订单当前状态以及锁定人 
					 */
					Map mapInfo = orderInfManager.getOrderStateAndLockName(orderId);
	                orderBaseInfo.setOrder_state(mapInfo.get("order_state")+"");
	                orderBaseInfo.setLock_user_name(mapInfo.get("lock_user_name")+"");
					/*
					 * 暂时先返回 order_city_code订单归属地
					 */
					// 订单归属县分
					if (orderTree.getOrderAdslBusiRequest().size() > 0) {
						orderBaseInfo.setCounty_id(orderTree.getOrderAdslBusiRequest().get(0).getCounty_id());
					} else {
						orderBaseInfo.setCounty_id("");
					}
					// 订单创建时间 es_order create_time 订单树的时间格式不一致
					orderBaseInfo.setCreate_time(map.get("create_time") + "");
					// 新增bssid
					orderBaseInfo.setBss_pre_order_id(map.get("bssorderid") + "");

					// 证件类型
					orderCustInfo.setCert_type(
							CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERTI_TYPE));
					/*// 出入境号
                    orderCustInfo.setCert_num2(
                            CommonDataFactory.getInstance().getAttrFieldValue(orderId, "cert_num2"));*/
					// 支付类型
					orderPayInfo.setPay_method(
							CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PAY_TYPE));
					// 支付流水
					orderPayInfo.setPay_sequ(map.get("payplatformorderid") + "");
					// 商品价格 单位厘 //商品单价
					// String goods_amountStr =
					// CommonDataFactory.getInstance().getAttrFieldValue(orderId,
					// AttrConsts.PRO_ORIG_FEE);
					// CommonDataFactory.getInstance().getGoodSpec(orderId,
					// null, SpecConsts.PRICE)
					String goods_amountStr = CommonDataFactory.getInstance().getGoodSpec(orderId, null,
							SpecConsts.PRICE);
					if (!"".equals(goods_amountStr)) {
						Double goods_amount = Double.parseDouble(goods_amountStr) * 1000;
						orderPayInfo.setGoods_amount(goods_amount.intValue() + "");
					} else {
						orderPayInfo.setGoods_amount("");
					}
					// 优惠金额 单位厘
					String dis = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.DISCOUNTRANGE);
					if (!"".equals(dis)) {
						Double dis_amount = Double.parseDouble(dis) * 1000;
						orderPayInfo.setDiscount_amount(dis_amount.intValue() + "");
					} else {
						orderPayInfo.setDiscount_amount("0");
					}
					// 实收金额 单位
					if ("".equals(map.get("paymoney") + "")) {
						orderPayInfo.setPay_amount("");
					} else {
						Double pay = Double.parseDouble(map.get("paymoney") + "");
						// Double discount_amount =
						// Double.parseDouble(map.get("paymoney")+"")*1000;
						orderPayInfo.setPay_amount(pay.intValue() + "");
					}

					// 发展人编码
					orderDeveloperInfo.setDevelop_code(
							CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.DEVELOPMENT_CODE));
					// 发展人姓名
					orderDeveloperInfo.setDevelop_name(
							CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.DEVELOPMENT_NAME));
					// 收货人姓名
					orderDeliveryInfo.setShip_name(
							CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SHIP_NAME));
					// 收货省
					orderDeliveryInfo.setShip_province(
							CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PROVINCE_CODE));
					// 收货地市
					orderDeliveryInfo.setShip_city(
							CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CITY_CODE));
					// 收货区县
					orderDeliveryInfo.setShip_district(
							CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.DISTRICT));
					// 收货地址
					orderDeliveryInfo.setShip_addr(
							CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SHIP_ADDR));
					// 收货人姓名
					orderDeliveryInfo.setShip_name(
							CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SHIP_NAME));
					// 物流公司
					orderDeliveryInfo.setShipping_company(CommonDataFactory.getInstance().getAttrFieldValue(orderId,
							AttrConsts.SHIPPING_COMPANY_NAME));
					// 物流单号
					orderDeliveryInfo
							.setLogi_no(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.LOGI_NO));

					
					orderQueryResp.setOrder_id(orderId);
					orderQueryResp.setBus_num(bus_num);
					orderQueryResp.setActivite_status(activite_status);
					orderQueryResp.setCert_num(cert_num);
					orderQueryResp.setCust_name(cust_name);
					orderQueryResp.setOrder_base_info(orderBaseInfo);
					orderQueryResp.setOrder_cust_info(orderCustInfo);
					orderQueryResp.setOrder_pay_info(orderPayInfo);
					orderQueryResp.setOrder_developer_info(orderDeveloperInfo);
					orderQueryResp.setOrder_delivery_info(orderDeliveryInfo);
					
					query_resp.add(orderQueryResp);
					resp.setResp_code("0");
					resp.setResp_msg("查询成功");
				} else {
					resp.setResp_code("1");
					resp.setResp_msg("未查询到有效的订单信息");
				}
			}
		} catch (Exception e) {
			resp.setResp_code("1");
			resp.setResp_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 2.3.8. 订单收单结果查询接口
	 * 
	 * @param requ
	 *            请求参数
	 * @return
	 * @author 宋琪
	 * @throws Exception
	 * @date 2017年6月2日
	 */
	@Override
	public OrderResultQueryResp queryOrderResultByRequ(OrderResultQueryReq requ) throws Exception {
		OrderResultQueryResp resp = new OrderResultQueryResp();
		resp.setQuery_resp(new ArrayList<OrderResultResp>());
		try {
			resp = orderInfManager.queryOrderResult(requ);
		} catch (Exception e) {
			resp.setResp_code("1");
			resp.setResp_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 根据订单信息更新订单状态
	 *
	 * @param requ
	 * @return
	 * @throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月28日
	 */
	@Override
	public OrderInfoUpdateResp updateOrderInfoByRequ(OrderInfoUpdateReq requ) throws Exception {
		OrderInfoUpdateResp resp = new OrderInfoUpdateResp();
		try {
			if (!Utils.isEmpty(requ.getPay_result()) && !Utils.isEmpty(requ.getWork_result())) {// 如果支付结果和施工结果同时存在
				resp.setResp_code("1");
				resp.setResp_msg("支付结果(pay_result)、施工结果(work_result)不能同时存在");
			} else if (!Utils.isEmpty(requ.getWork_result()) && !Utils.isEmpty(requ.getAuth_result())) {// 如果施工结果和认证结果同时存在
				resp.setResp_code("1");
				resp.setResp_msg("施工结果(work_result)、认证结果(auth_result)不能同时存在");
			} else if (!Utils.isEmpty(requ.getAuth_result()) && !Utils.isEmpty(requ.getPay_result())) {// 如果认证结果和支付结果同时存在
				resp.setResp_code("1");
				resp.setResp_msg("认证结果(auth_result)、支付结果(work_result)不能同时存在");
			} else if (!Utils.isEmpty(requ.getPay_result())) {// 如果支付结果存在
				OrderStatusUpdate(requ, resp, "支付结果通知订单中心", "CO_PAY_RESULT_BD");
			} else if (!Utils.isEmpty(requ.getWork_result())) {// 如果施工结果存在
				OrderStatusUpdate(requ, resp, "施工结果通知订单中心", "CO_WORK_RESULT_BD");
			} else if (!Utils.isEmpty(requ.getAuth_result())) {// 如果认证结果存在(用户激活接口)
				this.OrderInfoDeal(requ, resp);
			} else {// 如果支付结果、施工结果、认证结果同时为空
				resp.setResp_code("1");
				resp.setResp_msg("支付结果(pay_result)、施工结果(work_result)、认证结果(auth_result)不能同时为空");
			}
		} catch (Exception e) {
			resp.setResp_code("1");
			resp.setResp_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 根据订单状态更新订单状态
	 * 
	 * @param requ
	 * @return
	 * @throws Exception
	 * @author songqi
	 * @date 2017年10月19日 11:45:09
	 */
	@Override
	public OrderInfoUpdateNewResp newUpdateOrderInfoByRequ(OrderInfoUpdateNewReq requ) throws Exception {
		OrderInfoUpdateNewResp resp = new OrderInfoUpdateNewResp();
		resp.setResp_code("1");// 默认失败，成功后再修改
		resp.setResp_msg("");// 失败原因：返回接收方系统的接口日志，包括号码新增、修改、回收的成功数，失败数，同步失败的号码及原因。
		try {
			String order_id = requ.getOrder_id();
			String update_status = requ.getUpdate_status();
			if (StringUtils.isEmpty(order_id)) {
				resp.setResp_msg("order_id为空");
				return resp;
			}
			/**
             * 订单状态更新 A01：活体认证成功，订单激活操作 A02：活体认证失败，转人工审核 A03：订单激活模式撤单操作 C01：竣工成功
             * (暂无竣工业务) C02：竣工失败
             */
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
            if (null == orderTree) {
                resp.setResp_msg("order_id无效,未查询到有效订单");
                return resp;
            }
            if ("C01".equals(update_status) || "C02".equals(update_status)) {
                resp.setResp_msg("暂无竣工业务");
                return resp;
            }
            OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
            String orderfrom = orderExt.getOrder_from();
            String close_group_order_catid = cacheUtil.getConfigInfo("update_group_order_cat_id");
            String cat_id_group = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.CAT_ID);
            if(StringUtils.isNotEmpty(close_group_order_catid)&& "10093".equals(orderfrom) && close_group_order_catid.contains(cat_id_group)){//add by sgf 
               resp = this.groupOrderActivation(order_id);
               return resp;
            } else{
                order_id = requ.getOrder_id().trim();
                if (StringUtils.isEmpty(update_status)) {
                    resp.setResp_msg("update_status为空");
                    return resp;
                }
                update_status = requ.getUpdate_status().trim();
                // 工作流环节ID
                String flow_trace_id = orderExt.getFlow_trace_id();
                // 当前订单生产模式
                String order_model = orderExt.getOrder_model();
                // 订单来源
                String order_from = orderExt.getOrder_from();
                // 激活状态 0:未激活 1:线下认证失败 2:线下激活成功 3:线上激活成功 4:人工认证中 5:人工认证失败
                String active_flag = orderTree.getOrderRealNameInfoBusiRequest().getActive_flag();
                // 如果是订单归档环节，说明已审核通过，才能进行激活
                if (ZjEcsOrderConsts.DIC_ORDER_NODE_L.equals(flow_trace_id)) {
                    if (ZjEcsOrderConsts.ACTIVE_FLAG_0.equals(active_flag)
                            || ZjEcsOrderConsts.ACTIVE_FLAG_5.equals(active_flag)) {// 0:未激活或者
                        // 5:人工认证失败
                        //
                        if ("A01".equals(update_status)) {// 活体认证成功，订单激活操作
                            // 调用用户激活接口业务逻辑处理
                            UserActivationReq userRequ = new UserActivationReq();
                            userRequ.setNotNeedReqStrOrderId(order_id);
                            userRequ.setOper_type("0");
                            // 调用激活接口
                            UserActivationResp userResp = this.callUserActivation(userRequ);
                            // 如果激活成功
                            if (EcsOrderConsts.BSS_SUCCESS_00000.equals(userResp.getCode())) {
                                
                                if("1".equals(orderExt.getIs_work_custom())){
                                    //自定义流程订单归档
                                    this.dealWorkCustomOrderArchive(order_id);
                                }else{
                                    // 调用订单归档规则
                                    RuleTreeExeReq req = new RuleTreeExeReq();
                                    TacheFact fact = new TacheFact();
                                    fact.setOrder_id(requ.getOrder_id());
                                    req.setFact(fact);
                                    req.setRule_id(EcsOrderConsts.ORDER_PHOTO_RULE_ID);
                                    req.setCheckAllRelyOnRule(true);
                                    req.setCheckCurrRelyOnRule(true);
                                    ZteClient client1 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
                                    RuleTreeExeResp ruleResp = client1.execute(req, RuleTreeExeResp.class);
                                    // 如果订单归档规则调用失败
                                    if (!ConstsCore.ERROR_SUCC.equals(ruleResp.getError_code())) {
                                        logger.info("订单归档规则调用失败：" + ruleResp.getError_msg());
                                    }
                                }
                                
                                // 更新实名制信息表的激活状态为3:线上激活成功
                                OrderRealNameInfoBusiRequest orderRealNameInfo = orderTree
                                        .getOrderRealNameInfoBusiRequest();
                                orderRealNameInfo.setActive_flag(ZjEcsOrderConsts.ACTIVE_FLAG_3);
                                orderRealNameInfo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
                                orderRealNameInfo.setDb_action(ConstsCore.DB_ACTION_UPDATE);
                                orderRealNameInfo.store();
                                resp.setResp_code("0");
                                resp.setResp_msg("激活成功");
                                if (StringUtil.equals(order_from, "10070")) {// 码上购订单需状态同步码上购
                                    // 调用组件
                                    BusiCompRequest busi = new BusiCompRequest();
                                    Map queryParams = new HashMap();
                                    queryParams.put("order_id", requ.getOrder_id());
                                    busi.setEnginePath("zteCommonTraceRule.o2OStatusUpdate");
                                    busi.setOrder_id(requ.getOrder_id());
                                    busi.setQueryParams(queryParams);
                                    BusiCompResponse busiResp = CommonDataFactory.getInstance().execBusiComp(busi);
                                }
                            } else {
                                // 返回信息
                                resp.setResp_msg("激活失败：" + userResp.getMsg());
                            }
                        } else if ("A02".equals(update_status)) {// 活体认证失败，转人工审核
                            // 更新实名制信息表的激活状态为4:人工认证中
                            OrderRealNameInfoBusiRequest orderRealNameInfo = orderTree.getOrderRealNameInfoBusiRequest();
                            orderRealNameInfo.setActive_flag(ZjEcsOrderConsts.ACTIVE_FLAG_4);
                            orderRealNameInfo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
                            orderRealNameInfo.setDb_action(ConstsCore.DB_ACTION_UPDATE);
                            orderRealNameInfo.store();
                            // 插入订单外呼日志表记录
                            OrderBusiRequest orderBusiObject = orderTree.getOrderBusiRequest();
                            OrderExtBusiRequest orderExtBusi = orderTree.getOrderExtBusiRequest();
                            callOutApply(order_id, orderBusiObject.getStatus(), orderExtBusi.getFlow_trace_id());
                            // 更新订单状态为外呼申请
                            orderBusiObject.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_11);
                            orderBusiObject.setIs_dirty(ConstsCore.IS_DIRTY_YES);
                            orderBusiObject.setDb_action(ConstsCore.DB_ACTION_UPDATE);
                            orderBusiObject.store();
                            // 返回信息
                            resp.setResp_msg("激活失败：认证失败，已转人工处理");
                        } else if ("A03".equals(update_status)) {// 订单激活模式撤单操作
                            // 调用用户激活接口业务逻辑处理 撤单
                            UserActivationReq userRequ = new UserActivationReq();
                            userRequ.setNotNeedReqStrOrderId(order_id);
                            userRequ.setOper_type("1");// 撤单
                            // 调用激活接口 撤单
                            UserActivationResp userResp = this.callUserActivation(userRequ);
                            // 如果撤单成功
                            if (EcsOrderConsts.BSS_SUCCESS_00000.equals(userResp.getCode())) {
                                // 调用退单流程
                                try {
                                    ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
                                    RuleTreeExeReq requ2 = new RuleTreeExeReq();
                                    TacheFact fact = new TacheFact();
                                    fact.setOrder_id(order_id);
                                    requ2.setRule_id(EcsOrderConsts.REFUND_ID);
                                    // requ.setPlan_id(EcsOrderConsts.ORDER_RETURNED_PLAN);
                                    requ2.setFact(fact);
                                    requ2.setCheckAllRelyOnRule(true);
                                    requ2.setCheckCurrRelyOnRule(true);
                                    RuleTreeExeResp ruleResp = client.execute(requ2, RuleTreeExeResp.class);
                                    if (!StringUtil.isEmpty(ruleResp.getError_code())
                                            && ruleResp.getError_code().equals("0")) {
                                        Map<String, String> map = new HashMap<String, String>();
                                        Map<String, String> mapwhere = new HashMap<String, String>();
                                        map.put("zb_refund_status", "1");
                                        mapwhere.put("order_id", order_id);
                                        IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
                                        baseDaoSupport.update("es_order_extvtl", map, mapwhere);
                                        CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
                                                new String[] { AttrConsts.REFUND_DESC },
                                                new String[] { EcsOrderConsts.REFUND_APPLY_DESC + "激活接口发起撤单！" });
                                        resp.setResp_code("0");
                                        resp.setResp_msg("撤单发起成功");
                                    } else {
                                        resp.setResp_msg("撤单失败,调用退单失败:" + ruleResp.getError_msg());
                                    }
                                    
                                } catch (Exception e) {// 调用接口失败
                                    resp.setResp_msg(e.getMessage());
                                    e.printStackTrace();
                                }
                            } else {
                                resp.setResp_msg("撤单失败：" + userResp.getMsg());
                            }
                        } else {
                            resp.setResp_msg("update_status不符合规范");
                        }
                    } else if (ZjEcsOrderConsts.ACTIVE_FLAG_1.equals(active_flag)) {// 1:线下激活失败
                        resp.setResp_msg("激活失败：当前状态为“线下认证失败”");
                    } else if (ZjEcsOrderConsts.ACTIVE_FLAG_2.equals(active_flag)) {// 2:线下激活成功
                        resp.setResp_msg("激活失败：当前状态为“线下激活成功”");
                    } else if (ZjEcsOrderConsts.ACTIVE_FLAG_3.equals(active_flag)) {// 3:线上激活成功
                        resp.setResp_msg("激活失败：当前状态为“线上激活成功”");
                    } else if (ZjEcsOrderConsts.ACTIVE_FLAG_4.equals(active_flag)) {// 4:人工认证中
                        resp.setResp_msg("激活失败：当前状态为“人工认证中”");
                    }
                } else {
                    resp.setResp_msg("激活失败：订单环节异常，请核对订单是否已审核通过");
                    return resp;
                }
            }
		} catch (Exception e) {
			resp.setResp_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}
	/**
	 * 
	 * <p>Title: groupOrderActivation</p>
	 * <p>Description: 触发规则执行</p>
	 * @author sgf
	 * @time 2019年3月6日 下午2:53:09
	 * @version 1.0
	 * @param order_id
	 * @return
	 */
	private OrderInfoUpdateNewResp groupOrderActivation(String order_id) {
	    OrderInfoUpdateNewResp resp = new OrderInfoUpdateNewResp();
        OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
        OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
        // 工作流环节ID
        String flow_trace_id = orderExt.getFlow_trace_id();
        // 当前订单生产模式
        String order_model = orderExt.getOrder_model();
        // 订单来源
        String order_from = orderExt.getOrder_from();
        // 激活状态 0:未激活 1:线下认证失败 2:线下激活成功 3:线上激活成功 4:人工认证中 5:人工认证失败
        String active_flag = orderTree.getOrderRealNameInfoBusiRequest().getActive_flag();
        
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String typeCommitRuleId = cacheUtil.getConfigInfo("group_order_activation_rule_id");
        // 后激活
        try {
            if (ZjEcsOrderConsts.DIC_ORDER_NODE_L.equals(flow_trace_id)) {
                if (ZjEcsOrderConsts.ACTIVE_FLAG_0.equals(active_flag)|| ZjEcsOrderConsts.ACTIVE_FLAG_5.equals(active_flag)) {// 0:未激活或者 // 5:人工认证失败
                    ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
                    RuleTreeExeReq requ2 = new RuleTreeExeReq();
                    TacheFact fact = new TacheFact();
                    fact.setOrder_id(order_id);
                    requ2.setRule_id(typeCommitRuleId);
                    requ2.setFact(fact);
                    requ2.setCheckAllRelyOnRule(true);
                    requ2.setCheckCurrRelyOnRule(true);
                    RuleTreeExeResp ruleResp = client.execute(requ2, RuleTreeExeResp.class);
                    if (!StringUtil.isEmpty(ruleResp.getError_code())&& ruleResp.getError_code().equals("0")) {
                        // 更新实名制信息表的激活状态为3:线上激活成功
                        OrderRealNameInfoBusiRequest orderRealNameInfo = orderTree.getOrderRealNameInfoBusiRequest();
                        orderRealNameInfo.setActive_flag(ZjEcsOrderConsts.ACTIVE_FLAG_3);
                        orderRealNameInfo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
                        orderRealNameInfo.setDb_action(ConstsCore.DB_ACTION_UPDATE);
                        orderRealNameInfo.store();
                        resp.setResp_code("0");
                        resp.setResp_msg("激活成功");
                        return resp;
                    } else {
                        resp.setResp_code("1");
                        resp.setResp_msg(ruleResp.getError_msg());
                        return resp;
                    }
                }else if (ZjEcsOrderConsts.ACTIVE_FLAG_1.equals(active_flag)) {// 1:线下激活失败
                    resp.setResp_msg("1");
                    resp.setResp_msg("激活失败：当前状态为“线下认证失败”");
                    return resp;
                } else if (ZjEcsOrderConsts.ACTIVE_FLAG_2.equals(active_flag)) {// 2:线下激活成功
                    resp.setResp_msg("1");
                    resp.setResp_msg("激活失败：当前状态为“线下激活成功”");
                    return resp;
                } else if (ZjEcsOrderConsts.ACTIVE_FLAG_3.equals(active_flag)) {// 3:线上激活成功
                    resp.setResp_msg("1");
                    resp.setResp_msg("激活失败：当前状态为“线上激活成功”");
                    return resp;
                } else if (ZjEcsOrderConsts.ACTIVE_FLAG_4.equals(active_flag)) {// 4:人工认证中
                    resp.setResp_msg("1");
                    resp.setResp_msg("激活失败：当前状态为“人工认证中”");
                    return resp;
                }
            }else {
                resp.setResp_msg("1");
                resp.setResp_msg("激活失败：订单环节异常,未处于激活环节下");
                return resp;
            }
        } catch (Exception e) {// 调用接口失败
            resp.setResp_msg(e.getMessage());
            e.printStackTrace();
        }
        return resp;
    }

    private void OrderStatusUpdate(OrderInfoUpdateReq requ, OrderInfoUpdateResp resp, String co_name,
			String service_code) {
		JSONObject jsonObj = JSONObject.fromObject(requ);
		String jsoStr = jsonObj.toString();
		String order_id = null;
		if (!StringUtil.isEmpty(requ.getOut_order_id())) {
			String outt_id = requ.getOut_order_id();
			OrderTreeBusiRequest tree = null;
			if ("CO_PAY_RESULT_BD".equals(service_code)) {
				tree = CommonDataFactory.getInstance().getOrderTreeByOutId(outt_id);
			} else if ("CO_WORK_RESULT_BD".equals(service_code)) {
				tree = CommonDataFactory.getInstance().getOrderTreeByBssId(outt_id);
			}
			order_id = tree.getOrder_id();
		} else if (StringUtil.isEmpty(order_id)) {
			order_id = requ.getOrder_id();
		}
		if (StringUtil.isEmpty(order_id)) {
			resp.setResp_code("1");
			resp.setResp_msg("异常错误：外部单号与内部单号不能都为空！");
			return;
		}

		OrderTreeBusiRequest order_tree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String abnormal_type = order_tree.getOrderExtBusiRequest().getAbnormal_type();
		if (StringUtils.equals(abnormal_type, EcsOrderConsts.ORDER_ABNORMAL_TYPE_4)
				&& requ.getSource_system().equals("10071")) {
			resp.setResp_code("1");
			resp.setResp_msg("行销订单已自动关闭,无法更新支付状态");
			return;
		}

		String batch_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_BATCH);
		if (StringUtil.isEmpty(batch_id)) {
			batch_id = "1";
			for (int i = 0; i < 17; i++) {
				batch_id += (int) (Math.random() * 10);
			}
		}
		try {
			// 构造参数对象
			CoQueueAddReq req = new CoQueueAddReq();
			req.setCo_name(co_name);
			req.setService_code(service_code);
			req.setAction_code("A");
			req.setObject_type("DINGDAN");
			req.setContents(jsoStr);
			req.setObject_id(order_id);
			req.setBatch_id(batch_id);
			// 信息写入es_co_queue
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			CoQueueAddResp coresp = client.execute(req, CoQueueAddResp.class);
			String msg = "";
			if ("CO_PAY_RESULT_BD".equals(service_code)) {
				msg = orderExtManager.updateStatus(order_id, service_code);
			}
			if ("0".equals(msg)) {
				resp.setResp_code("0");
				resp.setResp_msg("接收成功");
			} else {
				resp.setResp_code("1");
				resp.setResp_msg(msg);
				/*ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String status_back = cacheUtil.getConfigInfo("IS_STATUS_BACK");
				if(!StringUtil.isEmpty(status_back)){
					OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
					OrderBusiRequest OrderBusi = orderTree.getOrderBusiRequest();
					OrderBusi.setPay_status(0);
					String Pay_Plan_id = cacheUtil.getConfigInfo("PAY_RULE_ID");
					String sql ="update es_order set pay_status=0 where order_id=?";
					String del_sql = "delete from es_rule_exe_log where obj_id='"+order_id+"' and plan_id='"+Pay_Plan_id+"'";
					IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
					baseDaoSupport.execute(sql, order_id);
					baseDaoSupport.execute(del_sql, null);
					//更新订单树缓存
					INetCache cache = (INetCache) com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
					cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY+"order_id"+ order_id,orderTree, RequestStoreManager.time);
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setResp_code("1");
			resp.setResp_msg("异常错误：单号有误！" + e.getMessage());
			logger.info(e.getMessage());
		}
	}

	/**
	 * 用户激活接口业务处理方法
	 * 
	 * @author liu.quan
	 * @date 2017年4月20日
	 * @param requ
	 * @param resp
	 */
	private void OrderInfoDeal(OrderInfoUpdateReq requ, OrderInfoUpdateResp resp) {
		String order_id = requ.getOrder_id();
		String active_flag = "";
		String resp_code = "1";
		try {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			// 如果订单存在
			if (orderTree != null) {
				OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
				OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id)
						.getOrderExtBusiRequest();

				// 工作流环节ID
				String flow_trace_id = orderExtBusiRequest.getFlow_trace_id();
				// 当前订单生产模式
				String order_model = orderExtBusiRequest.getOrder_model();
				// 订单来源
				String order_from = orderExt.getOrder_from();
				// 激活状态 0:未激活 1:线下认证失败 2:线下激活成功 3:线上激活成功 4:人工认证中 5:人工认证失败
				active_flag = orderTree.getOrderRealNameInfoBusiRequest().getActive_flag();
				// 如果生产模式为10(非订单中心号卡生产模式)或者订单来源为浙江码上购、天机，才能进行激活操作
				// if ("10".equals(order_model) || StringUtil.equals(order_from,
				// "10070")|| StringUtil.equals(order_from, "10012")) {
				// 如果是订单归档环节，说明已审核通过，才能进行激活
				if (ZjEcsOrderConsts.DIC_ORDER_NODE_L.equals(flow_trace_id)) {
					if (ZjEcsOrderConsts.ACTIVE_FLAG_0.equals(active_flag)
							|| ZjEcsOrderConsts.ACTIVE_FLAG_5.equals(active_flag)) {// 0:未激活或者
						// 5:人工认证失败
						OrderRealNameInfoBusiRequest orderRealNameInfoBusiRequest = orderTree
								.getOrderRealNameInfoBusiRequest();
						// 如果认证成功
						if (ConstsCore.ERROR_SUCC.equals(requ.getAuth_result())) {
							// 调用用户激活接口业务逻辑处理
							UserActivationReq userRequ = new UserActivationReq();
							userRequ.setNotNeedReqStrOrderId(order_id);
							// userRequ.setService_num(CommonDataFactory.getInstance().getAttrFieldValue(order_id,
							// AttrConsts.PHONE_NUM));//联通服务号码
							// userRequ.setService_type("1001");//业务类型(移网用户，暂时写死)
							// 调用激活接口
							UserActivationResp userResp = this.callUserActivation(userRequ);
							// 如果激活成功
							if (EcsOrderConsts.BSS_SUCCESS_00000.equals(userResp.getCode())) {
								
								if("1".equals(orderExt.getIs_work_custom())){
									//自定义流程订单归档
									this.dealWorkCustomOrderArchive(order_id);
								}else{
									// 调用订单归档规则
									RuleTreeExeReq req = new RuleTreeExeReq();
									TacheFact fact = new TacheFact();
									fact.setOrder_id(requ.getOrder_id());
									req.setFact(fact);
									req.setRule_id(EcsOrderConsts.ORDER_PHOTO_RULE_ID);
									req.setCheckAllRelyOnRule(true);
									req.setCheckCurrRelyOnRule(true);
									ZteClient client1 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
									RuleTreeExeResp ruleResp = client1.execute(req, RuleTreeExeResp.class);
									// 如果订单归档规则调用失败
									if (!ConstsCore.ERROR_SUCC.equals(ruleResp.getError_code())) {
										logger.info("订单归档规则调用失败：" + ruleResp.getError_msg());
									}
								}

								// 更新实名制信息表的激活状态为3:线上激活成功
								updateActiveFlag(orderRealNameInfoBusiRequest, ZjEcsOrderConsts.ACTIVE_FLAG_3);
								// 返回信息
								resp_code = "0";
								active_flag = ZjEcsOrderConsts.ACTIVE_FLAG_3;
								resp.setResp_msg("激活成功");
								if (StringUtil.equals(order_from, "10070")) {// 码上购订单需状态同步码上购
									// 调用组件
									BusiCompRequest busi = new BusiCompRequest();
									Map queryParams = new HashMap();
									queryParams.put("order_id", requ.getOrder_id());
									busi.setEnginePath("zteCommonTraceRule.o2OStatusUpdate");
									busi.setOrder_id(requ.getOrder_id());
									busi.setQueryParams(queryParams);
									BusiCompResponse busiResp = CommonDataFactory.getInstance().execBusiComp(busi);
								}
							} else {
								// 返回信息
								resp.setResp_msg("激活失败：" + userResp.getMsg());
							}
						} else {// 否则认证失败,转外呼
							// 更新实名制信息表的激活状态为4:人工认证中
							updateActiveFlag(orderRealNameInfoBusiRequest, ZjEcsOrderConsts.ACTIVE_FLAG_4);
							// 插入订单外呼日志表记录
							OrderBusiRequest orderBusiObject = orderTree.getOrderBusiRequest();
							OrderExtBusiRequest orderExtBusi = orderTree.getOrderExtBusiRequest();
							callOutApply(order_id, orderBusiObject.getStatus(), orderExtBusi.getFlow_trace_id());
							// 更新订单状态为外呼申请
							orderBusiObject.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_11);
							orderBusiObject.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							orderBusiObject.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							orderBusiObject.store();
							// 返回信息
							resp_code = "0";
							active_flag = ZjEcsOrderConsts.ACTIVE_FLAG_4;
							resp.setResp_msg("激活失败：认证失败，已转人工处理");
						}
					} else if (ZjEcsOrderConsts.ACTIVE_FLAG_1.equals(active_flag)) {// 1:线下激活失败
						resp.setResp_msg("激活失败：当前状态为“线下认证失败”");
					} else if (ZjEcsOrderConsts.ACTIVE_FLAG_2.equals(active_flag)) {// 2:线下激活成功
						resp.setResp_msg("激活失败：当前状态为“线下激活成功”");
					} else if (ZjEcsOrderConsts.ACTIVE_FLAG_3.equals(active_flag)) {// 3:线上激活成功
						resp.setResp_msg("激活失败：当前状态为“线上激活成功”");
					} else if (ZjEcsOrderConsts.ACTIVE_FLAG_4.equals(active_flag)) {// 4:人工认证中
						resp.setResp_msg("激活失败：当前状态为“人工认证中”");
					}
				} else {
					resp.setResp_msg("激活失败：订单环节异常，请核对订单是否已审核通过");
				}
				/*
				 * } else { resp.setResp_msg("激活失败：订单生产模式异常，请核对订单是否是省内激活订单"); }
				 */
			} else {
				resp.setResp_msg("激活失败：订单号有误，请核对订单号是否存在");
			}
		} catch (Exception e) {
			resp.setResp_msg("异常错误3：" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
		}
		resp.setResp_code(resp_code);
		resp.setActivite_status(active_flag);
	}

	private void callOutApply(String order_id, int status, String flow_trace_id) throws Exception {
		Map<String, Object> orderOutCall = new HashMap<String, Object>();
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		orderOutCall.put("order_id", order_id);
		orderOutCall.put("tache_code", flow_trace_id);
		orderOutCall.put("order_status", status);
		orderOutCall.put("oper_time", time.format(new Date()));
		orderOutCall.put("oper_id", "1");// 超级管理员
		orderOutCall.put("is_finish", "0");
		String remark = "认证失败，自动转外呼";
		orderOutCall.put("oper_remark", remark);
		orderOutCall.put("deal_remark", remark);
		ordFlowManager.insertOrderOutCallLog(orderOutCall);
	}

	private void updateActiveFlag(OrderRealNameInfoBusiRequest orderRealNameInfoBusiRequest, String active_flag) {
		orderRealNameInfoBusiRequest.setActive_flag(active_flag);// 0:未激活
		// 2:线下激活成功
		// 3:线上激活成功
		// 4:人工认证中
		// 5:人工认证失败
		orderRealNameInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderRealNameInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderRealNameInfoBusiRequest.store();
	}

	/**
	 * 用户激活接口
	 *
	 * @param requ
	 * @return
	 * @throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月28日
	 */
	@Override
	public UserActivationResp callUserActivation(UserActivationReq requ) throws Exception {
		UserActivationResp resp = new UserActivationResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, requ);
			param.put("ord_id", requ.getNotNeedReqStrOrderId());// 记录日志
			resp = (UserActivationResp) caller.invoke("ecaop.trades.serv.user.normalize.sub", param);
		} catch (Exception e) {// 调用接口失败
			resp.setCode("-9999");
			resp.setMsg("异常错误：" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 订单收费接口
	 *
	 * @param requ
	 * @return
	 * @throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月28日
	 */
	@Override
	public OrderChargeResp callOrderCharge(OrderChargeReq requ) throws Exception {
		OrderChargeResp resp = new OrderChargeResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, requ);
			param.put("ord_id", requ.getNotNeedReqStrOrderId());// 记录日志
			resp = (OrderChargeResp) caller.invoke("ecaop.trades.serv.order.fee.sub", param);
		} catch (Exception e) {// 调用接口失败
			resp.setCode("-9999");
			resp.setMsg("异常错误：" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 订单正式提交接口
	 *
	 * @param requ
	 * @return
	 * @throws Exception
	 *
	 * @author xiang.yangbo@ztesoft.com
	 *
	 * @date 2017年4月10日
	 */
	@Override
	public BroadbandOrderSubResp orderFormalSub(OrderFormalSubReq requ) throws Exception {
		BroadbandOrderSubResp resp = new BroadbandOrderSubResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, requ);
			param.put("ord_id", requ.getNotNeedReqStrOrderId());// 记录日志
			resp = (BroadbandOrderSubResp) caller.invoke("ecaop.trades.serv.newu.order.sub", param);
		} catch (Exception e) {// 调用接口失败
			resp.setCode("-9999");
			resp.setMsg("异常错误：" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 卡数据同步接口（省内）
	 * 
	 * @param requ
	 * @return
	 * @throws Exception
	 * @author xiang.yangbo@ztesoft.com
	 * @date 2017年4月10日
	 */
	@Override
	public CardDataSyncBssResp cardDataSyncBss(CardDataSyncBssReq requ) throws Exception {
		CardDataSyncBssResp resp = new CardDataSyncBssResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, requ);
			param.put("ord_id", requ.getNotNeedReqStrOrderId());// 记录日志
			resp = (CardDataSyncBssResp) caller.invoke("ecaop.trades.serv.newu.opencarddata.syn", param);
		} catch (Exception e) {// 调用接口失败
			resp.setCode("-9999");
			resp.setMsg("异常错误：" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 根据传入的图片信息处理订单协议图片
	 *
	 * @param requ
	 * @return
	 * @throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月28日
	 */
	@Override
	public OrderPhotoInfoResp dealOrderPhotoInfoByRequ(OrderPhotoInfoReq requ) throws Exception {
		OrderPhotoInfoResp resp = new OrderPhotoInfoResp();
        
		try {
			//增加图片信息校验 zwh
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	        String check_message = cacheUtil.getConfigInfo("check_photo_info_interface");
	        if ("1".equals(check_message)) {
	        	OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(requ.getOrder_id());
	        	String refund_deal_type = orderTree.getOrderExtBusiRequest().getRefund_deal_type();
	        	if (StringUtil.equals("02", refund_deal_type)) {
	        		resp.setResp_code("1");
	        		String message = cacheUtil.getConfigInfo("photo_exception_message_01");
					resp.setResp_msg("异常信息："+message);
					return resp;
	        	}
				/*
				 * 暂时不校验图片流上传的情况
				 * List<PhotoInfoVO> photoList = requ.getPhoto_list(); if (photoList != null &&
				 * photoList.size() == 3 ) { for (PhotoInfoVO photo : photoList) {
				 * if(org.apache.commons.lang.StringUtils.isBlank(photo.getPhoto_stream())) {
				 * String message = cacheUtil.getConfigInfo("photo_exception_message_02");
				 * resp.setResp_code("1"); resp.setResp_msg("异常信息："+message); return resp; } } }
				 * else { String message =
				 * cacheUtil.getConfigInfo("photo_exception_message_02");
				 * resp.setResp_code("1"); resp.setResp_msg("异常信息："+message); return resp; }
				 */
	        	
	        }
	        
			// 调用接收码上购协议照片(订单归档)组件
			TacheFact fact = new TacheFact();
			fact.setRequest(requ);
			BusiCompRequest bcr = new BusiCompRequest();
			bcr.setEnginePath("ZteOrderBackTraceRule.receiveCodePurchaseAgrtImgs");// 组件名称
			bcr.setOrder_id(requ.getOrder_id());
			Map queryParams = new HashMap();
			queryParams.put("fact", fact);
			bcr.setQueryParams(queryParams);
			BusiCompResponse callResp = CommonDataFactory.getInstance().execBusiComp(bcr);
			// 如果规则调用成功
			if (ConstsCore.ERROR_SUCC.equals(callResp.getError_code())) {
				// 返回信息
				resp.setResp_code(ConstsCore.ERROR_SUCC);
				resp.setResp_msg("图片保存成功");
			} else {
				// 返回信息
				resp.setResp_code(ConstsCore.RULE_ERROR_FAIL);
				resp.setResp_msg("图片保存失败：" + callResp.getError_msg());
			}
		} catch (Exception e) {
			resp.setResp_code("1");
			resp.setResp_msg("异常错误2：" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 客户资料与订单绑定接口
	 *
	 * @param requ
	 * @return
	 * @throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月28日
	 */
	@Override
	public CustInfoOrderBindResp callCustInfoOrderBind(CustInfoOrderBindReq requ) throws Exception {
		CustInfoOrderBindResp resp = new CustInfoOrderBindResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, requ);
			param.put("ord_id", requ.getOrder_id());// 记录日志
			resp = (CustInfoOrderBindResp) caller.invoke("ecaop.trades.serv.user.orderconnect.sub", param);
		} catch (Exception e) {// 调用接口失败
			resp.setCode("1");
			resp.setMsg("异常错误：" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 订单接收接口
	 *
	 * @param requ
	 * @return
	 * @throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月28日
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public OrderMakeupResp orderMakeupInsert(OrderMakeupReq requ) throws Exception {
		MarkTime mark = new MarkTime("orderMakeupInsert out_order_id="+requ.getOut_order_id());
		
		OrderMakeupResp resp = new OrderMakeupResp();
		try {
			// 通过商品id获取信息
			String prod_offer_type = CommonDataFactory.getInstance().getGoodSpec(null, requ.getProd_offer_code(),
					SpecConsts.CAT_ID);
			String prod_offer_code = CommonDataFactory.getInstance().getGoodSpec(null, requ.getProd_offer_code(),
					SpecConsts.SKU);
			requ.setProd_offer_type(prod_offer_type);
			requ.setProd_offer_code(prod_offer_code);
			// 校验数据正确性
			String msg = dataCheck(requ);
			String code = "1";
			
			OrderTreeBusiRequest orderTree = null;
			
			if (StringUtil.isEmpty(msg)) {
				Map manualOrder = JSONObject.fromObject(requ);
				OrderCtnResp orderCtnResp = orderExtManager.saveManualOrder(manualOrder, "D");
				if ("0".equals(orderCtnResp.getError_code())) {
					msg = "处理成功";
					code = "0";
					// 返回订单中心订单号
					
					if("1".equals(requ.getIs_update())){
						//更新的订单不会更新原有订单的外部单号，这时外部单号为后台生成的意向单的单号
						orderTree = CommonDataFactory.getInstance()
								.getOrderTreeByOutId(requ.getRel_order_id());
					}else{
						orderTree = CommonDataFactory.getInstance().getOrderTree(orderCtnResp.getOrder_id());
					}

					if (orderTree != null) {
						resp.setOrder_id(orderTree.getOrder_id());
					}
				} else {
					msg = orderCtnResp.getError_msg();
				}
			}else{
				resp.setResp_code("1");
				resp.setResp_msg(msg);
				
				mark.markEndTime("orderMakeupInsert fail out_order_id="+requ.getOut_order_id());
				
				return resp;
			}
			resp.setResp_code(code);
			resp.setResp_msg(msg);
			if("1".equals(code)){
			    return resp;
			}
			// 数据入库
			if (requ.getNice_info() instanceof Map && requ.getNice_info() != null) {
				if (orderTree != null) {
					OrderPhoneInfoBusiRequest phoneInfoBusiRequest = orderTree.getPhoneInfoBusiRequest();
					if (phoneInfoBusiRequest == null || phoneInfoBusiRequest.getOrder_id() == null) {// 收单是该表未新建
						phoneInfoBusiRequest.setOrder_id(orderTree.getOrder_id());
						phoneInfoBusiRequest.setId(orderTree.getOrder_id());
						phoneInfoBusiRequest.setOrder_from(orderTree.getOrderExtBusiRequest().getOrder_from());
						phoneInfoBusiRequest.setPhone_num(requ.getAcc_nbr());
						phoneInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						phoneInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
						phoneInfoBusiRequest.store();
						phoneInfoBusiRequest = orderTree.getPhoneInfoBusiRequest();
					}
					
					if (!StringUtil.isEmpty((String) requ.getNice_info().get("lhscheme_id"))) {// 省内
						String lhscheme_id = requ.getNice_info().get("lhscheme_id") + "";
						phoneInfoBusiRequest.setLhscheme_id(lhscheme_id);
						//if (!StringUtils.isEmpty(lhscheme_id)) {
						//	phoneInfoBusiRequest.setClassId(Integer.parseInt(lhscheme_id));// 省内靓号没有字段	
						//}
						
						// lhscheme_id存入classId字段中靓号信息参照NiceInfoVO.java
						String pre_fee = requ.getNice_info().get("pre_fee") + "";
						if (!StringUtils.isEmpty(pre_fee)) {
							phoneInfoBusiRequest.setAdvancePay(pre_fee);// 省内靓号没有字段
						}	
						String advancePay = requ.getNice_info().get("advancePay") + "";
						String lowCostPro = requ.getNice_info().get("lowCostPro") + "";
						String timeDurPro = requ.getNice_info().get("timeDurPro") + "";
						if(!StringUtils.isEmpty(advancePay)) {
							phoneInfoBusiRequest.setAdvancePay(advancePay);
						}
						if (!StringUtils.isEmpty(lowCostPro)) {
							phoneInfoBusiRequest.setLowCostPro(lowCostPro);
						}
						if (!StringUtils.isEmpty(timeDurPro)) {
							phoneInfoBusiRequest.setTimeDurPro(timeDurPro);
						}
						// pre_fee存入advancePay字段中
						
					} else {
						// phoneInfoBusiRequest.setClassId(Integer.getInteger(requ.getNice_info().get("lhscheme_id")
						// == null ? "" :
						// requ.getNice_info().get("lhscheme_id").toString()));
						String classId = requ.getNice_info().get("classId") + "";
						String advancePay = requ.getNice_info().get("advancePay") + "";
						String lowCostPro = requ.getNice_info().get("lowCostPro") + "";
						String timeDurPro = requ.getNice_info().get("timeDurPro") + "";
						if (!StringUtils.isEmpty(classId)) {
							phoneInfoBusiRequest.setClassId(Integer.parseInt(classId));
						}
						if (!StringUtils.isEmpty(advancePay)) {
							phoneInfoBusiRequest.setAdvancePay(advancePay);
						}
						if (!StringUtils.isEmpty(lowCostPro)) {
							phoneInfoBusiRequest.setLowCostPro(lowCostPro);
						}
						if (!StringUtils.isEmpty(timeDurPro)) {
							phoneInfoBusiRequest.setTimeDurPro(timeDurPro);
						}
					}
					phoneInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					phoneInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					phoneInfoBusiRequest.store();
				}
			}

			CommonDataFactory.getInstance().updateAttrFieldValue(
					orderTree.getOrder_id(),
					new String[] { AttrConsts.COUPON_BATCH_ID, AttrConsts.IS_PAY },
					new String[] { requ.getReq_swift_num(), requ.getIs_pay() });
		} catch (Exception e) {
			resp.setResp_code("1");
			resp.setResp_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			
			logger.error("orderMakeupInsert error,out_order_id="+requ.getOut_order_id()+",error info:"+e.getMessage(),e);
			
			mark.markEndTime("orderMakeupInsert error out_order_id="+requ.getOut_order_id());
		}
		
		mark.markEndTime("orderMakeupInsert success out_order_id="+requ.getOut_order_id());
		
		return resp;
	}

	/**
	 * 业务办理校验接口(APP)
	 * 
	 * @author liu.quan
	 * @date 2017年4月5日
	 * @param requ
	 * @return
	 */
	@Override
	public BusiHandleCheckAPPResp busiHandleCheckAPP(BusiHandleCheckAPPReq requ) throws Exception {
		BusiHandleCheckAPPResp resp = new BusiHandleCheckAPPResp();
		String str = "";
		String bss_code = "";
		// List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String[] proda_offer_code = requ.getProd_offer_code().split(",");
			for (int i = 0; i < proda_offer_code.length; i++) {
				bss_code = CommonDataFactory.getInstance().getGoodSpec(null, proda_offer_code[i], SpecConsts.BSS_CODE);
				str += (bss_code + " ");
				map.put(bss_code, proda_offer_code[i]);
			}
			// str ="756256 756257 756252 757298 757295 758802 758807 758808
			// 758803 757301 757292 757298 ";
			String str1 = str.substring(0, str.length() - 1);
			logger.info("----------------------------------------------------");
			logger.info(str1);

			BusiHandleCheckBSSReq bssReq = new BusiHandleCheckBSSReq();
			bssReq.setService_num(requ.getService_num());
			bssReq.setScheme_list(bss_code);
			bssReq.setOperator_id(requ.getDeal_operator());
			bssReq.setOffice_id(requ.getDeal_office_id());

			// bssReq.setService_num("18668487454");
			// bssReq.setScheme_list(str1);
			// bssReq.setOperator_id("AQHZH521");
			// bssReq.setOffice_id("AQHZ96");
			// 调用业务办理校验接口
			BusiHandleCheckBSSResp bssResp = this.busiHandleCheckBSS(bssReq);
			// 默认失败标识为1
			String resp_code = "1";
			// 如果调用结果返回成功
			List list = new ArrayList();
			String scheme_list_return = "";
			if (EcsOrderConsts.BSS_SUCCESS_00000.equals(bssResp.getCode())) {
				String bsscode = bssResp.getRespJson().getScheme_list_return();
				logger.info("bss_code：" + bsscode);
				if (bsscode.contains(",")) {
					String[] bsscode1 = bsscode.split(",");
					for (int i = 0; i < bsscode1.length; i++) {
						if (map.get(bsscode1[i]) != null) {
							scheme_list_return += map.get(bsscode1[i]) + ",";
						}
					}

					scheme_list_return.substring(0, scheme_list_return.length() - 1);
				} else {
					if (map.get(bsscode) != null) {
						scheme_list_return += map.get(bsscode);
					}
				}

				resp_code = "0";
			}
			resp.setResp_code(resp_code);
			resp.setResp_msg(bssResp.getMsg());
			resp.setScheme_list_return(scheme_list_return);
			logger.info(resp);
		} catch (Exception e) {// 调用接口失败
			resp.setResp_code("1");
			resp.setResp_msg("异常错误：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 业务办理校验接口(BSS)
	 * 
	 * @author liu.quan
	 * @date 2017年4月5日
	 * @param requ
	 * @return
	 */
	@Override
	public BusiHandleCheckBSSResp busiHandleCheckBSS(BusiHandleCheckBSSReq requ) throws Exception {
		BusiHandleCheckBSSResp resp = new BusiHandleCheckBSSResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, requ);
			resp = (BusiHandleCheckBSSResp) caller.invoke("ecaop.trades.subs.comm.pro.preCheck", param);
		} catch (Exception e) {// 调用接口失败
			resp.setCode("1");
			resp.setMsg("异常错误：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 业务办理预提交接口(APP)
	 * 
	 * @author liu.quan
	 * @date 2017年4月5日
	 * @param requ
	 * @return
	 */
	@Override
	public BusiHandleAPPResp busiHandleAPP(BusiHandleAPPReq requ) throws Exception {
		BusiHandleAPPResp resp = new BusiHandleAPPResp();
		try {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(requ.getOrder_id());
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	             String types =  cacheUtil.getConfigInfo("type_ids");
	             String ignore_error =  cacheUtil.getConfigInfo("ignore_error");
	 	        String ignore_error_kg =  cacheUtil.getConfigInfo("ignore_error_kg");
			if (orderTree != null) {
				String order_id = requ.getOrder_id();
				String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.TYPE_ID);
		        String opt_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id,"opttype");
	            String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id,"order_from");

				if (StringUtil.equals(type_id, SpecConsts.TYPE_ID_CBSS_ACTIVITY)
						|| StringUtil.equals(type_id, "172971653252000777")
						|| StringUtil.equals(type_id, SpecConsts.TYPE_ID_CBSS_ADD) || (StringUtil.equals(type_id,types) && StringUtil.equals("02", opt_type))) {// 总部-活动受理
																						// //
																						// 总部-流量办理add
					// by
					// song.qi
					ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
					RuleTreeExeReq req = new RuleTreeExeReq();
					TacheFact fact = new TacheFact();
					fact.setOrder_id(order_id);
					req.setRule_id(EcsOrderConsts.BUSI_CUNFEESONGFEE_AOP_PRESUBMIT_RULE_ID);
					req.setFact(fact);
					req.setCheckAllRelyOnRule(true);
					req.setCheckCurrRelyOnRule(true);
					RuleTreeExeResp rsp = client.execute(req, RuleTreeExeResp.class);
					resp.setError_code(rsp.getError_code());
					resp.setError_msg(rsp.getError_msg());
					String bssOrderid = CommonDataFactory.getInstance().getOrderTree(order_id)
							.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getBssOrderId();
					if (!StringUtil.equals(resp.getError_code(), "1")) {
						resp.setResp_code("0");
						resp.setBus_resp("0");
						resp.setBss_pre_order_id(bssOrderid);
						resp.setResp_msg("接收成功");
					} else {
						if(!StringUtil.isEmpty(ignore_error_kg)&&"1".equals(ignore_error_kg)&&!StringUtil.isEmpty(rsp.getError_msg())&&rsp.getError_msg().contains(ignore_error)){
							resp.setResp_code("0");
							resp.setBus_resp("0");
							resp.setBss_pre_order_id(bssOrderid);
							resp.setResp_msg("接收成功");
						}else{
							resp.setResp_code("1");
							resp.setBus_resp("1");
							resp.setResp_msg(rsp.getError_msg());
						}
					}
				} else if (StringUtil.equals(type_id, "172501418202000419")
						|| StringUtil.equals(type_id, "172501423222000452")) {
					// 子产品
					// 流量包子产品 Add Wcl
					PromoteProductReq productReq = new PromoteProductReq();
					productReq.setNotNeedReqStrOrderId(requ.getOrder_id());

					// 调用行销APP-产品受理规则
					RuleTreeExeReq req = new RuleTreeExeReq();
					TacheFact fact = new TacheFact();
					fact.setOrder_id(requ.getOrder_id());
					fact.setRequest(productReq);
					req.setRule_id(EcsOrderConsts.PRODUCT_HANDLE_RULE_ID);
					req.setFact(fact);
					req.setCheckAllRelyOnRule(true);
					req.setCheckCurrRelyOnRule(true);
					ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
					RuleTreeExeResp ruleResp = client.execute(req, RuleTreeExeResp.class);

					String resp_code = "1";
					String bus_resp = "1";
					// 如果调用结果返回成功
					if (ConstsCore.ERROR_SUCC.equals(ruleResp.getError_code())) {
						resp_code = "0";
						bus_resp = "0";
						orderTree = CommonDataFactory.getInstance().getOrderTree(requ.getOrder_id());
						OrderItemExtBusiRequest orderItemExtBusiRequest = orderTree.getOrderItemBusiRequests().get(0)
								.getOrderItemExtBusiRequest();
						resp.setBss_pre_order_id(orderItemExtBusiRequest.getBssOrderId());
					}
					resp.setResp_code(resp_code);
					resp.setResp_msg(ruleResp.getError_msg());
					resp.setBus_resp(bus_resp);
				} else {
					// 拼接请求参数
					BusiHandleBSSReq bssRequ = new BusiHandleBSSReq();
					bssRequ.setNotNeedReqStrOrderId(requ.getOrder_id());

					// 调用行销APP-活动受理预提交规则
					RuleTreeExeReq req = new RuleTreeExeReq();
					TacheFact fact = new TacheFact();
					fact.setOrder_id(requ.getOrder_id());
					fact.setRequest(bssRequ);
					req.setRule_id(EcsOrderConsts.BUSI_HANDLE_RULE_ID);
					req.setFact(fact);
					req.setCheckAllRelyOnRule(true);
					req.setCheckCurrRelyOnRule(true);
					ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
					RuleTreeExeResp ruleResp = client.execute(req, RuleTreeExeResp.class);

					String resp_code = "1";
					String bus_resp = "1";
					// 如果调用结果返回成功
					if (ConstsCore.ERROR_SUCC.equals(ruleResp.getError_code())) {
						resp_code = "0";
						bus_resp = "0";
						orderTree = CommonDataFactory.getInstance().getOrderTree(requ.getOrder_id());
						OrderItemExtBusiRequest orderItemExtBusiRequest = orderTree.getOrderItemBusiRequests().get(0)
								.getOrderItemExtBusiRequest();
						resp.setBss_pre_order_id(orderItemExtBusiRequest.getBssOrderId());
					}
					resp.setResp_code(resp_code);
					resp.setResp_msg(ruleResp.getError_msg());
					resp.setBus_resp(bus_resp);
				}
			} else {
				resp.setResp_code("1");
				resp.setBus_resp("1");
				resp.setResp_msg("单号有误，请核对单号是否存在");
			}
		} catch (Exception e) {// 调用接口失败
			resp.setResp_code("1");
			resp.setBus_resp("1");
			resp.setResp_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 业务办理预提交接口(BSS)
	 * 
	 * @author liu.quan
	 * @date 2017年4月5日
	 * @param requ
	 * @return
	 */
	@Override
	public BusiHandleBSSResp busiHandleBSS(BusiHandleBSSReq requ) throws Exception {
		BusiHandleBSSResp resp = new BusiHandleBSSResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, requ);
			param.put("ord_id", requ.getNotNeedReqStrOrderId());// 记录日志
			resp = (BusiHandleBSSResp) caller.invoke("ecaop.trades.subscription.wb.verify.renewalactivity", param);
		} catch (Exception e) {// 调用接口失败
			resp.setCode("1");
			resp.setMsg("异常错误3：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	private String dataCheck(OrderMakeupReq requ) {
		String msg = "";
		if (StringUtils.isEmpty(requ.getAcc_nbr())) {// 用户号码
			msg = "用户号码(acc_nbr)不能为空";
		} else if (StringUtils.isEmpty(requ.getCerti_type())) {// 证件类型
			msg = "证件类型(certi_type)不能为空";
		} else if (StringUtils.isEmpty(requ.getSource_from())) {// 订单来源
			msg = "订单来源(source_from)不能为空";
		} else if (StringUtils.isEmpty(requ.getEss_money())) {// 营业款(元)
			msg = "营业款(ess_money)不能为空";
		} else if (StringUtils.isEmpty(requ.getShip_phone())) {// 收货人电话
			msg = "收货人电话(ship_phone)不能为空";
		} else if (StringUtils.isEmpty(requ.getIs_realname())) {// 是否实名
			msg = "是否实名(is_realname)不能为空";
		} else if (StringUtils.isEmpty(requ.getCust_name())) {// 用户姓名
			msg = "用户姓名(cust_name)不能为空";
		} else if (StringUtils.isEmpty(requ.getCerti_num())) {// 证件号码
			msg = "证件号码(certi_num)不能为空";
		} /**
			 * else if (StringUtils.isEmpty(requ.getOrder_city_code())) {//地市
			 * msg = "地市(order_city_code)不能为空"; }
			 */
		else if (StringUtils.isEmpty(requ.getIs_old())) {// 新老用户
			msg = "新老用户(is_old)不能为空";
		} else if (StringUtils.isEmpty(requ.getPay_method())) {// 支付方式
			msg = "支付方式(pay_method)不能为空";
		} else if (StringUtils.isEmpty(requ.getPay_money())) {// 商城实收金额
			msg = "商城实收金额(pay_money)不能为空";
		} else if (StringUtils.isEmpty(requ.getShip_name())) {// 收货人姓名
			msg = "收货人姓名(ship_name)不能为空";
		} else if (StringUtils.isEmpty(requ.getShip_addr())) {// 收货地址
			msg = "收货地址(ship_addr)不能为空";
		}
		try {
			Float.valueOf(requ.getEss_money());
			try {
				Float.valueOf(requ.getPay_money());
			} catch (Exception e) {
				msg = "商城实收金额(pay_money)必须为浮点型数字";
			}
		} catch (Exception e) {
			msg = "营业款(ess_money)必须为浮点型数字";
		}
		return msg;
	}

	/**
	 * BSS订单反销校验
	 * 
	 * @param order_id
	 * @throws Exception
	 */
	private void checkBssOrderBack(String order_id) throws Exception {
		String is_aop = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_AOP);

		if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)) {
			CancelOrderStatusQryReq req = new CancelOrderStatusQryReq();
			req.setNotNeedReqStrOrderId(order_id);

			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			CancelOrderStatusQryResp resp = client.execute(req, CancelOrderStatusQryResp.class);

			if (!StringUtil.isEmpty(resp.getCode())
					&& StringUtil.equals(resp.getCode(), EcsOrderConsts.INF_RESP_CODE_00000)) {
				String is_back = resp.getRespJson().getIs_back();

				if (StringUtil.isEmpty(is_back)
						|| (!StringUtil.equals(is_back, EcsOrderConsts.WYG_BACK_ORDER_STATUS_1))) {
					throw new Exception("订单未返销");
				}
			} else {
				throw new Exception(resp.getMsg());
			}
		}
	}

	// 订单撤单接口 接收码上购退单申请接口【修改zb_refund_status属性值为1】
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单撤单接口", summary = "订单撤单接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.orderInfoRefundUpdateReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderInfoRefundUpdateResp orderInfoRefundUpdate(OrderInfoRefundUpdateReq req) throws ApiBusiException {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		OrderInfoRefundUpdateResp resp = new OrderInfoRefundUpdateResp();
		try {
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			RuleTreeExeReq requ = new RuleTreeExeReq();
			TacheFact fact = new TacheFact();
			OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTreeByOutId(req.getOut_order_id());
			String order_id = "";
			if (tree != null) {
				order_id = tree.getOrder_id();
			} else {
				order_id = req.getOrder_id();
				tree = CommonDataFactory.getInstance().getOrderTree(order_id);
			}
			if (StringUtils.isEmpty(order_id)) {
				resp.setResp_code("1");
				resp.setResp_msg("未找到对应订单");
				return resp;
			}

			// add by zhaochen 20180531 BSS的订单调用BSS接口查询业务是否退单，接口返回没有退单则返回错误
			try {
				this.checkBssOrderBack(order_id);
			} catch (Exception e) {
				resp.setResp_code("1");
				resp.setResp_msg("订单反销校验失败：" + e.getMessage());
				return resp;
			}
			// add end
			String is_work_custom = tree.getOrderExtBusiRequest().getIs_work_custom();
			if(!StringUtil.isEmpty(is_work_custom)&&"1".equals(is_work_custom)){
				if(checkWorkCustomFinish(order_id)){
					workCustomCfgManager.moveFinsh2Ins(order_id);
				}
				StringBuffer sql_Intent=new  StringBuffer();
				sql_Intent.append("select a.instance_id from ES_WORK_CUSTOM_NODE_INS a where a.order_id='").append(order_id).append("' and a.node_code ='refund_money' ");
				String instance_id=baseDaoSupport.queryForString(sql_Intent.toString());
				
				if(!(instance_id==null||"".equals(instance_id))) {
					try {
						Map map_param=new HashMap();
						map_param.put("dealDesc", "退单");
						map_param.put("returnedReasonCode", "");
						map_param.put("applyFrom", "1");
						String json_param=JSON.toJSONString(map_param);
						//泛智能终端是否走支付判断环节
						StringBuffer sql_ins=new  StringBuffer();
						sql_ins.append("select a.instance_id from ES_WORK_CUSTOM_NODE_INS a where a.order_id='").append(order_id).append("' and a.node_code ='judge_pay' ");
						String ins_id=baseDaoSupport.queryForString(sql_ins.toString());
						String node_code = "";
						if(!(ins_id==null||"".equals(ins_id))) {
							node_code = "judge_pay";
						}else {
							node_code = "refund_money";
						}
						
						workCustomEngine.gotoNode(order_id, node_code,"");
						WORK_CUSTOM_FLOW_DATA flow_data=workCustomEngine.runNodeManualByCode(order_id, node_code, true, "", "", json_param);
						resp.setResp_code("0");
						resp.setResp_msg("撤单发起成功");
					} catch (Exception e) {
						resp.setResp_code("1");
						resp.setResp_msg(e.getMessage());
					}
				}
			}else{
				fact.setOrder_id(order_id);
				/**
				 * Add by wcl
				 * 缴费退款走新流程 rule_id = 204920630917722112;
				 */
				String goodsType = tree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getGoods_type();
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				
				String ruleId = EcsOrderConsts.REFUND_ID;
				if(StringUtils.equals(goodsType, EcsOrderConsts.GOODS_TYPE_FEI_HK)) {
					ruleId = cacheUtil.getConfigInfo("JIAOFEI_REFUND_RULEID");
				}
				requ.setRule_id(ruleId);
				// requ.setPlan_id(EcsOrderConsts.ORDER_RETURNED_PLAN);
				requ.setFact(fact);
				requ.setCheckAllRelyOnRule(true);
				requ.setCheckCurrRelyOnRule(true);
				RuleTreeExeResp ruleResp = client.execute(requ, RuleTreeExeResp.class);
				if (!StringUtil.isEmpty(ruleResp.getError_code()) && ruleResp.getError_code().equals("0")) {
					Map map = new HashMap();
					Map mapwhere = new HashMap();
					map.put("zb_refund_status", "1");
					mapwhere.put("order_id", order_id);
					baseDaoSupport.update("es_order_extvtl", map, mapwhere);
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.REFUND_DESC },
							new String[] { EcsOrderConsts.REFUND_APPLY_DESC + "用户发起撤单！" });
					resp.setResp_code("0");
					resp.setResp_msg("撤单发起成功");
				} else {
					resp.setResp_code("1");
					resp.setResp_msg(ruleResp.getError_msg());
				}
			}
			

		} catch (Exception e) {// 调用接口失败
			resp.setResp_code("1");
			resp.setResp_msg(e.getMessage());
			e.printStackTrace();
		}

		return resp;

	}

	// 订单状态查询接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单状态查询接口", summary = "订单状态查询接口")
	public OrderStatusQueryResponse orderStatusQuery(OrderStatusQueryReq req) throws ApiBusiException {
		OrderStatusQueryResponse resp = new OrderStatusQueryResponse();
		String order_id = null;
		try {
			if (!StringUtil.isEmpty(req.getOut_order_id())) {
				String outt_id = req.getOut_order_id();
				OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTreeByOutId(outt_id);
				order_id = tree.getOrder_id();
			} else if (StringUtil.isEmpty(order_id)) {
				order_id = req.getOrder_id();
			}
			// CommonDataFactory.getInstance().updateOrderTree(order_id);
			String bss_refund_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
					AttrConsts.BSS_REFUND_STATUS);
			String refund_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
					AttrConsts.REFUND_DEAL_TYPE);
			String new_refund_status = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest()
					.getRefund_status();
			String zb_refund_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
					AttrConsts.ZB_REFUND_STATUS);
			OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String trace_id = tree.getOrderExtBusiRequest().getFlow_trace_id();
			String refund_flag = orderInfManager.checkRefund(order_id, trace_id);
			// logger.info("当前环节状态"+order_id+","+trace_id+";refund_status:"+refund_status+";bss_refund_status:"+bss_refund_status);
			String order_from = tree.getOrderExtBusiRequest().getOrder_from();
			String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
			if (EcsOrderConsts.REFUND_STATUS_01.equals(new_refund_status)) {
				resp.setOrder_status("10");
			} else if (EcsOrderConsts.REFUND_STATUS_02.equals(new_refund_status)) {
				resp.setOrder_status("10");
			} else if (EcsOrderConsts.REFUND_STATUS_03.equals(new_refund_status)) {
				resp.setOrder_status("10");
			} else if (EcsOrderConsts.REFUND_STATUS_07.equals(new_refund_status)) {
				resp.setOrder_status("10");
			} else if (EcsOrderConsts.REFUND_STATUS_08.equals(new_refund_status)) {
				resp.setOrder_status("08");
			} else if ("01".equals(refund_status) && !StringUtils.isEmpty(refund_status)
					&& !StringUtils.isEmpty(refund_flag) && StringUtil.equals(refund_flag, "yes")) {
				resp.setOrder_status("09");
			} else if (EcsOrderConsts.DIC_ORDER_NODE_M.equals(trace_id) || "1".equals(bss_refund_status)) {
				resp.setOrder_status("07");
			} else if (EcsOrderConsts.DIC_ORDER_NODE_T.equals(trace_id) || "02".equals(refund_status)
					|| "1".equals(zb_refund_status)) {
				resp.setOrder_status("06");
			} else if (EcsOrderConsts.DIC_ORDER_NODE_P.equals(trace_id)
					|| ( (order_from.equals("10072")||order_from.equals("10112")) 
							&& goods_type.equals(EcsOrderConsts.GOODS_TYPE_FEI_HK)
							&& trace_id.equals(EcsOrderConsts.DIC_ORDER_NODE_B)) ) {
				resp.setOrder_status("02");
			} else if (EcsOrderConsts.DIC_ORDER_NODE_D.equals(trace_id)
					|| EcsOrderConsts.DIC_ORDER_NODE_B.equals(trace_id)) {
				resp.setOrder_status("03");
			} else if (EcsOrderConsts.DIC_ORDER_NODE_L.equals(trace_id)) {
				resp.setOrder_status("04");
			} else if (EcsOrderConsts.DIC_ORDER_NODE_J.equals(trace_id)) {
				resp.setOrder_status("05");
			} else {
				resp.setOrder_status("01");
			}
			resp.setResp_code("0");
			resp.setResp_msg("查询成功");
		} catch (Exception e) {
			resp.setResp_code("1");
			resp.setResp_msg("查询失败:" + e.getMessage());
		}
		return resp;
	}

	// 根据序列号找到商品报文
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "商品报文查询接口", summary = "商品报文查询接口")
	public OrderGoodsQueryResp queryOrderGoodsByRequ(OrderGoodsQueryReq req) throws ApiBusiException {
		OrderGoodsQueryResp resp = new OrderGoodsQueryResp();
		try {
			String seq_no = req.getSeq_no();

			if (seq_no == null || seq_no.equals("")) {
				resp.setResp_code("-1");
				resp.setResp_msg("商品标识为空");

				return resp;
			}

			if (req.getSource_system() == null || req.getSource_system().equals("")) {
				resp.setResp_code("-1");
				resp.setResp_msg("查询系统来源不能为空");

				return resp;
			}

			String query_resp = orderInfManager.queryGoodsInfo(seq_no, req.getSource_system());

			if (query_resp == null || query_resp.equals("")) {
				resp.setResp_code("-9999");
				resp.setResp_msg("无法根据该系统来源或商品标识查到报文信息");

				return resp;
			}
			// 查询成功即添加查询时间
			orderInfManager.addRspTime(seq_no, req.getSource_system());

			resp.setResp_content(query_resp);
			resp.setResp_code("0");
			resp.setResp_msg("接收成功");
		} catch (Exception e) {
			resp.setResp_code("-9999");
			resp.setResp_msg(e.getMessage());
		}

		return resp;
	}

	/**
	 * 根据信息进行商品筛选
	 */
	@Override
	public GoodsListQueryResp queryGoodsListByRequ(QueryGoodsListReq req) {
		GoodsListQueryResp resp = new GoodsListQueryResp();
		try {
			if (StringUtils.isEmpty(req.getSource_system())) {
				resp.setResp_code("1");
				resp.setResp_msg("系统表示不能为空");

				return resp;
			}

			List<GoodsInfoVO> result = orderInfManager.queryGoodsList(req);
			resp.setResp_code("0");
			resp.setResp_msg("查询成功");
			resp.setGoods_info(result);
		} catch (Exception e) {
			// TODO: handle exception
			resp.setResp_code("1");
			resp.setResp_msg(e.getMessage());
		}

		return resp;
	}

	// 订单支付前校验
	@Override
	public PayPreCheckResp orderPayPreCheck(OrderPayPreCheckReq req) throws ApiBusiException {
		PayPreCheckResp resp = new PayPreCheckResp();

		String order_id = req.getOrder_id();

		if (StringUtils.isEmpty(order_id)) {
			resp.setResp_code(EcsOrderConsts.FAIL_RESP_1);
			resp.setResp_msg("订单号不能为空");

			return resp;
		}

		// 根据订单号查询该订单是否在订单支付环节
		OrderTreeBusiRequest orderExtBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		if (orderExtBusiRequest == null) {
			resp.setResp_code(EcsOrderConsts.FAIL_RESP_1);
			resp.setResp_msg("根据该单号未查询到订单，请核对单号。");

			return resp;
		}
		// 获取订单环节
		String flow_trace_id = orderExtBusiRequest.getOrderExtBusiRequest().getFlow_trace_id();
		if (StringUtils.isEmpty(flow_trace_id) || !flow_trace_id.equals(EcsOrderConsts.DIC_ORDER_NODE_P)) {
			resp.setResp_code(EcsOrderConsts.FAIL_RESP_1);
			resp.setResp_msg("当前环节无法支付。");

			PayPreCheckRespVO respVo = new PayPreCheckRespVO();
			respVo.setOrder_id(order_id);
			respVo.setOrder_pay_msg("当前环节无法支付。");
			respVo.setOrder_pay_status(EcsOrderConsts.ORDER_PAY_STATUS_01);

			resp.setQuery_resp(respVo);
			return resp;
		} else {
			resp.setResp_code(EcsOrderConsts.SUCCESS_RESP_0);
			resp.setResp_msg("校验成功");

			PayPreCheckRespVO respVo = new PayPreCheckRespVO();
			respVo.setOrder_id(order_id);
			respVo.setOrder_pay_msg("校验成功，订单可支付。");
			respVo.setOrder_pay_status(EcsOrderConsts.ORDER_PAY_STATUS_02);

			resp.setQuery_resp(respVo);
			return resp;
		}
	}

	// 订单退款接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单退款接口(APP)", summary = "订单退款接口(APP)")
	public BroadbandRefundResp broadbandRefund(BroadbandrefundReq req) throws ApiBusiException {
		BroadbandRefundResp resp = new BroadbandRefundResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			resp = (BroadbandRefundResp) caller.invoke("Q2M.app.refund", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("1");
			resp.setError_msg("异常错误：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	// 订单退款接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单退款接口", summary = "订单退款接口")
	public BroadbandOrderInfoRefundResp broadbandOrderInfoRefund(BroadbandOrderInfoRefundReq req)
			throws ApiBusiException {BroadbandOrderInfoRefundResp resp = new BroadbandOrderInfoRefundResp();
			BroadbandRefundBussiInfoVO BroadbandRefundBussiInfo = new BroadbandRefundBussiInfoVO();
			List<BroadbandRefundOrderInfoVO> orderInfoList = new ArrayList();
			BroadbandRefundOrderInfoVO BroadbandRefundOrderInfo = new BroadbandRefundOrderInfoVO();
			List<BroadbandRefundFeeVO> feelist = new ArrayList();
			OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(req.getNotNeedReqStrOrderId());
			List<AttrFeeInfoBusiRequest> new_feelist = tree.getFeeInfoBusiRequests();

			/*boolean isRobot = false;
			// 如果是订单来源是机器人平台10082，设置req中的ServiceId为00000007
			if (tree.getOrderExtBusiRequest() != null && "10082".equals(tree.getOrderExtBusiRequest().getOrder_from()))
				isRobot = true;

			if (isRobot)
				req.setServiceId("00000007");*/
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String serviceId = cacheUtil.getConfigInfo("REFUND_SERVICE_ID_"+tree.getOrderExtBusiRequest().getOrder_from());
			if(StringUtils.isEmpty(serviceId)||"null".equals(serviceId)){
				resp.setResp_code("-999");
				resp.setResp_msg("未配置对应serviceid");
				return resp;
			}
			req.setServiceId(serviceId);
			for (int i = 0; i < feelist.size(); i++) {
				BroadbandRefundFeeVO BroadbandRefundFee = new BroadbandRefundFeeVO();
				BroadbandRefundFee.setCnt("1");// 数量
				String fee = new_feelist.get(i).getN_fee_num() + "";
				String FeeCategory = new_feelist.get(i).getO_fee_num() + "";
				if (!StringUtil.isEmpty(fee)) {
					Double cny = Double.parseDouble(fee);
					fee = cny * 100 + "";
					BroadbandRefundFee.setFee(fee);// 应收费用(分)
				} else {
					BroadbandRefundFee.setFee("0");
				}
				if (!StringUtil.isEmpty(FeeCategory)) {
					Double cny = Double.parseDouble(FeeCategory);
					FeeCategory = cny * 100 + "";
					BroadbandRefundFee.setFeeCategory(FeeCategory);// 应收费用(分)
				} else {
					BroadbandRefundFee.setFeeCategory("0");
				}
				BroadbandRefundFee.setOrigFee(new_feelist.get(i).getFee_item_code());// 收费项科目
				feelist.add(BroadbandRefundFee);

			}
			if (feelist != null) {
				BroadbandRefundOrderInfo.setFeeInfos(feelist);
			}

			BroadbandRefundOrderInfo.setOrderId(tree.getOrderExtBusiRequest().getOut_tid());
			BroadbandRefundOrderInfo.setOrderDesc(CommonDataFactory.getInstance()
					.getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.REFUND_DESC));
			String real_price = CommonDataFactory.getInstance().getOrderTree(req.getNotNeedReqStrOrderId())
					.getOrderBusiRequest().getPaymoney() + "";
			Integer cny = (int) (Double.parseDouble(real_price) * 100);
			String orderPrice = cny + "";
			BroadbandRefundOrderInfo.setOrderPrice(orderPrice);
			// BroadbandRefundOrderInfo.setBssPaySerialNo(bssPaySerialNo);
			/*
			 * BroadbandRefundOrderInfo.setOrigSysId(origSysId);
			 * BroadbandRefundOrderInfo.setHomeSysId(homeSysId);
			 * BroadbandRefundOrderInfo.setBusiKeyType(busiKeyType);
			 * BroadbandRefundOrderInfo.setBusiKey(busiKey);
			 * BroadbandRefundOrderInfo.setBusiCode(busiCode);
			 */
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			Date date = new Date();
			BroadbandRefundOrderInfo.setBusiOccurrTime(sdf.format(date));
			orderInfoList.add(BroadbandRefundOrderInfo);
			String busiStaffId = CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(),
					AttrConsts.OUT_OPERATOR);
			BroadbandRefundBussiInfo.setBusiStaffId(busiStaffId);// 营业员工号
			// BroadbandRefundBussiInfo.setBusiType(busiType);//业务类型（mis-pos支付必传）1：营业侧；
			// 2：账管侧
			// BroadbandRefundBussiInfo.setChannelId(channelId);//受理渠道编码(总部渠道编码)
			// BroadbandRefundBussiInfo.setChannelType(channelType);//受理渠道类型
			// BroadbandRefundBussiInfo.setCountyId(countyId);//业务发生区县编码
			BroadbandRefundBussiInfo.setOrderInfos(orderInfoList);
			BroadbandRefundBussiInfo.setProvinceCode("36");// 业务发生省份编码
			String region_id = CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(),
					AttrConsts.ORDER_CITY_CODE);
			region_id = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.BSS_AREA_CODE, region_id);
			BroadbandRefundBussiInfo.setRegionId(region_id);// 业务发生地市编码
			req.setBusiInfo(BroadbandRefundBussiInfo);
			String payplatformorderid = CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(),
					AttrConsts.PAY_PLATFORM_ORDER_ID);
			String[] new_pay_ids = payplatformorderid.split("\\|");
			String new_pay_id = new_pay_ids[0];
			req.setOutTradeNo(new_pay_id);
			String outRefundNo = "";
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date1 = new Date();

				outRefundNo = serviceId + sdf1.format(date1);

			req.setOutRefundNo(outRefundNo);
			Map<String, Object> param = new HashMap<String, Object>();
			CommCaller caller = new CommCaller();
			try {
				BeanUtils.bean2Map(param, req);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info(param.toString());
			Map new_map = param;
			param = new HashMap<String, Object>();
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志

			// 设置业务系统为00000009--订单中心平台
			String ddzx_serviceid = cacheUtil.getConfigInfo("REFUND_SERVICE_ID_DDZX");
			param.put("serviceId", ddzx_serviceid);

			param.put("data", orderInfManager.getRSASign(new_map));
			resp = (BroadbandOrderInfoRefundResp) caller.invoke("pc.user.page.refund", param);
			String data = resp.getData();
			data = orderInfManager.decodeInfo(data);
			Map data_map = JSON.parseObject(data);
			logger.info(data.toString());
			resp.setResp_code(data_map.get("resultCode").toString());
			resp.setResp_msg(data_map.get("resultDesc").toString());
			resp.setOutRefundNo(data_map.get("outRefundNo").toString());
			resp.setResultCode(data_map.get("resultCode").toString());
			resp.setResultDesc(data_map.get("resultDesc").toString());
			resp.setResultType(data_map.get("resultType").toString());
			return resp;
			}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "接收外系统预开户申请", summary = "接收外系统预开户申请")
	public OrderPreSubResp orderPreSub(OrderPreSubReq req) throws ApiBusiException {
		OrderPreSubResp resp = new OrderPreSubResp();
		String order_id = req.getOrder_id();

		try {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			if (orderTree != null) {
				if (StringUtil.equals(req.getBusi_type(), "01") || StringUtil.equals(req.getBusi_type(), "04")
						|| StringUtil.equals(req.getBusi_type(), "05") || StringUtil.equals(req.getBusi_type(), "06")
						|| StringUtil.equals(req.getBusi_type(), "07")) {
					ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
					RuleTreeExeReq requ = new RuleTreeExeReq();
					TacheFact fact = new TacheFact();
					fact.setOrder_id(order_id);

					String goods_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests()
							.get(0).getGoods_id();
					String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, goods_id, "type_id");

					String ruleId = this.ORD_PRE_SUB_RULE_ID;

					// 融合业务-总部的商品预提交规则与别的业务不同，从配置中读取
					if (AopInterUtil.isAopBrdOpenApply(type_id)) {
						ruleId = AopInterUtil.getAopBrdPreSubRuleId(order_id);

						if (StringUtils.isEmpty(ruleId))
							throw new ApiBusiException("融合业务-总部商品的预提交规则配置为空");
					}
					
					if("215040095894368256".equals(type_id)){
						ruleId = cacheUtil.getConfigInfo("ZDGHRULEID");;
					}

					requ.setRule_id(ruleId);
					// // add by xiang.yangbo
					// String isAop =
					// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
					// AttrConsts.IS_AOP);
					// // 1:aop 0:bss
					// if("1".equals(isAop)){
					// requ.setRule_id(EcsOrderConsts.BUSI_CARD_AOP_PRESUB_RULE_ID);
					// }
					// // end by xiang.yangbo
					requ.setFact(fact);
					requ.setCheckAllRelyOnRule(true);
					requ.setCheckCurrRelyOnRule(true);
					RuleTreeExeResp rsp = client.execute(requ, RuleTreeExeResp.class);
					resp.setError_code(rsp.getError_code());
					resp.setError_msg(rsp.getError_msg());

					String bssOrderid = "";

					if (AopInterUtil.isAopBrdOpenApply(type_id)) {
						// 如果是融合业务-总部的商品，取CBSS的单号
						bssOrderid = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests()
								.get(0).getOrderItemExtBusiRequest().getActive_no();
					} else {
						bssOrderid = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests()
								.get(0).getOrderItemExtBusiRequest().getBssOrderId();
					}

					if (!StringUtil.isEmpty(bssOrderid)) {
						resp.setResp_code("0");
						resp.setBus_resp("0");
						resp.setBss_pre_order_id(bssOrderid);
						resp.setResp_msg("接收成功");
					} else {
						resp.setResp_code("1");
						resp.setBus_resp("1");
						resp.setResp_msg(rsp.getError_msg());
					}

				} else {
					resp.setResp_code("1");
					resp.setBus_resp("1");
					resp.setResp_msg("busi_type不正确");
				}
			} else {
				resp.setError_code("1");
				resp.setError_msg("根据订单号找不到对应订单");
				resp.setResp_code("1");
				resp.setBus_resp("1");
				resp.setResp_msg("根据订单号找不到对应订单");
			}

		} catch (Exception e) {
			resp.setError_code("1");
			resp.setError_msg(e.getMessage());
			resp.setBus_resp("1");
			resp.setResp_code("1");
		}
		return resp;
	}

	// ECS省份业务用户提速续费改资料接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "ECS省份业务用户提速续费改资料接口", summary = "ECS省份业务用户提速续费改资料接口")
	public BandUserDataResp bandUserData(BandUserDataReq req) throws ApiBusiException {
		BandUserDataResp resp = new BandUserDataResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String goods_id = CommonDataFactory.getInstance().getOrderTree(req.getNotNeedReqStrOrderId())
					.getOrderItemBusiRequests().get(0).getGoods_id();
			String bss_brand_speed = CommonDataFactory.getInstance().getGoodSpec(req.getNotNeedReqStrOrderId(),
					goods_id, "bss_brand_speed");
			List list = ecsOrdManager.getDcSqlByDcName("DC_BRAND_SPEED_BSS");
			if (StringUtil.isEmpty(bss_brand_speed)) {
				resp.setError_code("1");
				resp.setError_msg("商品没有配置速率");
			}
			for (int i = 0; i < list.size(); i++) {
				String value = Const.getStrValue((Map) list.get(i), "value");
				String value_desc = Const.getStrValue((Map) list.get(i), "value_desc");
				if (StringUtil.equals(value, bss_brand_speed)) {
					req.setRate(value_desc);
					break;
				}
			}
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			resp = (BandUserDataResp) caller.invoke("ecaop.trades.subscription.wb.modify.banduserdata", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("1");
			resp.setError_msg("异常错误：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 白卡获取接口
	 * 
	 * @author hzs
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "白卡获取接口", summary = "白卡获取接口")
	public CardInfoGetBSSResp getWhiteCardInfo(CardInfoGetBSSReq req) throws ApiBusiException {
		CardInfoGetBSSResp resp = new CardInfoGetBSSResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		Object obj = new Object();
		PreCommitBSSReq requ = new PreCommitBSSReq();
		CommonDataFactory.getInstance().updateAttrFieldValue("170301741260000169", new String[] { AttrConsts.PROC_ID },
				new String[] { "J3617050517222808816" });
		CommonDataFactory.getInstance().updateOrderTree("170301741260000169");
		// 170301741260000169

		logger.info("test--------->" + CommonDataFactory.getInstance().getAttrFieldValue("170301741260000169", AttrConsts.PROC_ID));

		// requ.setIsBacklogOrder("0");
		// requ.setAppKey("agentAss");
		// requ.setCert_addr("阿斯蒂芬看似简单规范四大金刚活动后");
		// requ.setCert_effected_date("20100101000000");
		// requ.setCert_expire_date("20210109000000");
		// requ.setCert_issuedat(" ");
		// requ.setCert_nation("汉");
		// requ.setCert_num("zxz1234567");
		// requ.setCert_expire_date("20210109000000");
		// requ.setCert_sex("1");
		// requ.setCert_type("12");
		// requ.setCert_verified("N");
		// requ.setContact_name("测试");
		// requ.setCustomer_adder("阿斯蒂芬看似简单规范四大金刚活动后");
		// requ.setCustomer_name("测试");
		// requ.setContact_phone("13221820120");
		// requ.setDeal_office_id("AQHZ96");
		// requ.setDeal_operator("AQHZH521");
		// requ.setFormat("json");
		// requ.setHttp_request("YXBpPW51bWJlckNlbnRlciZhcHBfa2V5PWFnZW50QXNzJmZvcm1hdD1qc29uJm1ldGhvZD1lY2FvcC50cmFkZXMuc2Vydi5yZXNvdXJjZS5jZW50ZXIuYXBwJnNlcnZpY2VfbmFtZT1wcmVlbXB0ZWROdW0mc2Vzc2lvbj03ODE2MjEzNzM5MSZzaWduPWEzZGQ0MWIxYzI3Mjk2YTU0ZDM0NmNkZWFjYjQzYmEzJnNpZ25fbWV0aG9kPW1kNSZzeXN0ZW1faWQ9cmVzb3VyY2VDZW50ZXImdGltZXN0YW1wPTIwMTctMDUtMDQgMTY6NTY6NTEmdW5pYnNzYm9keT17IlBSRUVNUFRFRF9OVU1fUkVRIjp7IkNIQU5ORUxfSUQiOiIzNmEwMTg3IiwiQ0lUWV9DT0RFIjoiMzYwIiwiRElTVFJJQ1RfQ09ERSI6IjM2MjAwMSIsIlBST1ZJTkNFX0NPREUiOiIzNiIsIlNFUklBTF9OVU1CRVIiOiIxODYwNjUzMTQ1MiIsIlNU
		// QUZGX0lEIjoiQUVES0gxMzUiLCJTWVNfQ09ERSI6IjM2MDIifX0mdj0yLjAm&http_request1=YXBpPW51bWJlckNlbnRlciZhcHBfa2V5PWFnZW50QXNzJmZvcm1hdD1qc29uJm1ldGhvZD1lY2FvcC50cmFkZXMuc2Vydi5yZXNvdXJjZS5jZW50ZXIuYXBwJnNlcnZpY2VfbmFtZT1vY2N1cHlOdW0mc2Vzc2lvbj03ODE2MjEzNzM5MSZzaWduPTNmZGQ1Y2U4OTQ1Yjg1YmI5MmI2MTBlZGZkNmE5ZjUzJnNpZ25fbWV0aG9kPW1kNSZzeXN0ZW1faWQ9cmVzb3VyY2VDZW50ZXImdGltZXN0YW1wPTIwMTctMDUtMDQgMTY6NTY6NTEmdW5pYnNzYm9keT17Ik9DQ1VQWV9OVU1fUkVRIjp7IkNIQU5ORUxfSUQiOiIzNmEwMTg3IiwiQ0lUWV9DT0RFIjoiMzYwIiwiRElTVFJJQ1RfQ09ERSI6IjM2MjAwMSIsIklDQ0lEIjoiODk4NjAxMTA2MDM2MDgzNjgzMSIsIklNU0kiOiI0NjAwMTY1MzA2NzQ1NzIiLCJQUk9WSU5DRV9DT0RFIjoiMzYiLCJSRVFfTk8iOiIlc2VyaWFsbm8lIiwiU0VSSUFMX05VTUJFUiI6IjE4NjA2NTMxNDUyIiwiU1RBRkZfSUQiOiJBRURLSDEzNSIsIlNZU19DT0RFIjoiMzYwMiJ9fSZ2PTIuMCY=&http_request2=YXBpPWNhcmRDZW50ZXImYXBwX2tleT1hZ2VudEFzcyZmb3JtYXQ9anNvbiZtZXRob2Q9ZWNhb3AudHJhZGVzLnNlcnYucmVzb3VyY2UuY2VudGVyLmFwcCZzZXJ2aWNlX25hbWU9bm90aWZ5Q3JlYXRlQ2FyZFJlc3VsdCZzZXNzaW9uPTc4MTYyMTM3MzkxJnNpZ249MmRiYTE2MzMzNWNmOTgwMjE3MGZjNzAwMjBmMDhiYmQmc2lnbl9tZXRob2Q9bWQ1JnN5c3RlbV9pZD1yZXNvdXJjZUNlbnRlciZ0aW1lc3RhbXA9MjAxNy0wNS0wNCAxNjo1Njo1MSZ1bmlic3Nib2R5PXsiTk9USUZZX0NSRUFURV9DQVJEX1JFU1VMVF9SRVEiOnsiQ0hBTk5FTF9JRCI6IjM2YTAxODciLCJDSVRZX0NPREUiOiIzNjAiLCJESVNUUklDVF9DT0RFIjoiMzYyMDAxIiwiSUNDSUQiOiI4OTg2MDExMDYwMzYwODM2ODMxIiwiUFJPVklOQ0VfQ09ERSI6IjM2IiwiUkVRX05PIjoiJXNlcmlhbG5vJSIsIlNFUklBTF9OVU1CRVIiOiIxODYwNjUzMTQ1MiIsIlNUQUZGX0lEIjoiQUVES0gxMzUiLCJTWVNfQ09ERSI6IjM2MDIiLCJUUkFERV9UWVBFIjoiMDEifX0mdj0yLjAm&imsi=460016530674572&ki=MIIBlwYJKoZIhvcNAQcDoIIBiDCCAYQCAQAxggEQMIIBDAIBADB3MHAxCzAJBgNVBAYTAkNOMQ4wDAYDVQQIDAVqaWxpbjESMBAGA1UEBwwJY2hhbmdjaHVuMQwwCgYDVQQKDANDTkMxDjAMBgNVBAsMBUNOQ0NBMR8wHQYDVQQDDBZDSElOQSBORVRDT00gQ0xBU1MzIENBAgM2bwEwCwYJKoZIhvcNAQEBBIGADtumGv4p6ukBxMaywWdpO0RXC1nO9XfpTt4+u/Jx4TerhbOUvFZI05xKNsiIhlAoTpAkItUp7nRRBKkUoDwpigtauzSf8gNrF6cx51VBmjDE4HgCHYbZ/xJ+RoTmNE9HW1piq3QIdGeVsbLIJ+7rqVROJ32swU/G79zS8ja8h10wawYJKoZIhvcNAQcBMBQGCCqGSIb3DQMHBAiDDSR11klmiYBIWnCVDyxrO5/chjHB6ILKKB5f/t2faNVHxejtieAB7qdztvcFl3geeIu6jdOcPoOcP8jE8hQa6upC3pRzeZ8MCks7J9bwfvWu");
		// requ.setParam11("1");
		// requ.setParam12("02");
		// requ.setParam13("");
		// requ.setParam15("19000101");
		// requ.setPcheme_id("756256");
		// requ.setProduct_id("50465361");
		// requ.setRes_number("15558163177");
		// requ.setService_num("15558163177");
		// requ.setService_type("1001");
		// requ.setSign("d01f50533d81910c2d58fd0c9633353b");
		// requ.setSim_card("8986011683600078172Y");
		// requ.setSource_flag("1");
		// requ.setUrl_key("AIP_SERVER");
		//
		// PreCommitAPPResp resp2 = new PreCommitAPPResp();
		// try {
		// param = requ.toMap();
		// resp2 = (PreCommitAPPResp)
		// caller.invoke("ecaop.trades.ass.account.comm.preSubs.openAcct",
		// param);//调用接口待查
		// logger.info(obj.getClass().getPackage());
		// //需保存数据到数据库
		// //CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new
		// String[]{AttrConsts.SIMID,AttrConsts.SCRIPT_SEQ,AttrConsts.WRITE_CARD_STATUS},
		// new
		// String[]{infResp.getRsp().getImsi(),infResp.getRsp().getScriptseq(),EcsOrderConsts.WRITE_CARD_STATUS_3});
		// } catch (Exception e) {//调用接口失败
		// resp.setRes_code("-9999");
		// resp.setRes_message("异常错误：" + e.getMessage() == null?
		// e.toString():e.getMessage());
		// e.printStackTrace();
		// }
		return resp;
	}

	/**
	 * 号卡开户预提交接口
	 * 
	 * @author hzs
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单预提交接口", summary = "订单预提交接口")
	public PreCommitAPPResp preCommitService(PreCommitAPPReq req) throws ApiBusiException {
		String order_id = req.getOrder_id();
		PreCommitAPPResp resp = new PreCommitAPPResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			
			if(orderTree == null)
				throw new Exception("找不到"+order_id+"订单的订单树对象");
			
			if(orderTree.getOrderExtBusiRequest() == null)
				throw new Exception("找不到"+order_id+"订单的订单树扩展对象");
			
			if("1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())){
				//自定义流程--预提交
				this.dealWorkCustomMobilePreCommit(order_id);
				
				String bss_order_id = "";
				if(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(orderTree.getOrderExtBusiRequest().getIs_aop())){
					bss_order_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSSORDERID);
				}else{
					bss_order_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO);
				}
					
				resp.setBss_pre_order_id(bss_order_id);
				resp.setResp_code("0");
				resp.setResp_msg("success");
				resp.setBus_resp("0");
			}else {
				String iccid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
				String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.TYPE_ID);
				String cat_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.CAT_ID);
				//226723870818693120 集客号卡总部
				//改到从数据库中取值
		        String xx_card_center = cacheUtil.getConfigInfo("xx_card_center");//号卡-总部（AOP）
		        Boolean flag = false;
		        if(!StringUtils.isEmpty(xx_card_center) && xx_card_center.contains(type_id)){
		            flag = true;
		        }
				if (flag || StringUtil.equals(type_id, SpecConsts.TYPE_ID_GOODS_CBSS) || "226723870818693120".equals(type_id) ) {// 号卡-总部（AOP）
					// 调用规则
					RuleTreeExeReq requ = new RuleTreeExeReq();
					TacheFact fact = new TacheFact();
					fact.setOrder_id(req.getOrder_id());
					requ.setFact(fact);
					requ.setRule_id(EcsOrderConsts.BUSI_CARD_AOP_PRESUBMIT_RULE_ID);
					requ.setCheckAllRelyOnRule(true);
					requ.setCheckCurrRelyOnRule(true);
					ZteClient client1 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
					RuleTreeExeResp ruleResp = client1.execute(requ, RuleTreeExeResp.class);
					// 如果开户预提交规则调用失败
					BusiCompResponse response = (BusiCompResponse) ruleResp.getFact().getResponses().get("");
					if ("181251437482000193".equals(cat_id)) {// CB副卡
						MainViceOpenResp rsp = (MainViceOpenResp) ThreadLocalUtil.getProperty(order_id);
						if (rsp.getResultMsg() != null && !StringUtils.isEmpty(rsp.getResultMsg().getProvOrderId())) {
							resp.setBss_pre_order_id(rsp.getResultMsg().getProvOrderId());
							resp.setResp_code("0");
							resp.setResp_msg("success");
							resp.setBus_resp("0");
						} else {
							resp.setError_code("1");
							resp.setError_msg("异常错误2：" + (rsp.getError_msg() + rsp.getDetail() + rsp.getRes_message()
									+ rsp.getResultMsg() != null ? rsp.getResultMsg().getDetail() : ""));
							resp.setResp_code("1");
							resp.setResp_msg("接口调用错误:" + (rsp.getError_msg() + rsp.getDetail() + rsp.getRes_message()
									+ rsp.getResultMsg() != null ? rsp.getResultMsg().getDetail() : ""));
						}
					} else {
						OpenDealApplyResp openDealApplyResp = (OpenDealApplyResp) ThreadLocalUtil.getProperty(order_id);
						if (!ConstsCore.ERROR_SUCC.equals(ruleResp.getError_code())) {
							// System.out.println("开户预提交规则调用失败：" +
							// ruleResp.getError_msg());
							resp.setError_code("1");
							resp.setError_msg("异常错误2：" + (ruleResp.getError_msg()));
							// Add by Wcl
							resp.setResp_code("1");
							resp.setResp_msg("接口调用错误:" + ruleResp.getError_msg());
						} else {
							// OpenDealApplyResp openDealApplyResp =
							// (OpenDealApplyResp)
							// ruleResp.getFact().getResponses();
							// OpenDealApplyResp openDealApplyResp =
							// (OpenDealApplyResp)
							// ThreadLocalUtil.getProperty(order_id);
							// ThreadLocalUtil.removeProperty(order_id);
							String serial_num = openDealApplyResp.getProvOrderId();
							resp.setBss_pre_order_id(serial_num);
							resp.setResp_code("0");
							resp.setResp_msg("success");
							resp.setBus_resp("0");
						}
					}
				} else if (StringUtil.equals(type_id, SpecConsts.TYPE_ID_GOODS_BSS)
						|| StringUtil.equals(type_id, SpecConsts.TYPE_ID_GOODS_JIKE)
						|| StringUtils.equals(type_id, EcsOrderConsts.GOODS_TYPE_RONGHE)) {// 号卡-省内（BSS）
					// 调用规则
					RuleTreeExeReq requ = new RuleTreeExeReq();
					TacheFact fact = new TacheFact();
					fact.setOrder_id(req.getOrder_id());
					requ.setFact(fact);
					requ.setRule_id(EcsOrderConsts.BUSI_CARD_BSS_PRESUBMIT_RULE_ID);
					requ.setCheckAllRelyOnRule(true);
					requ.setCheckCurrRelyOnRule(true);
					ZteClient client1 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
					RuleTreeExeResp ruleResp = client1.execute(requ, RuleTreeExeResp.class);
					// 如果开户预提交规则调用失败
					if (!ConstsCore.ERROR_SUCC.equals(ruleResp.getError_code())) {
						logger.info("开户预提交规则调用失败：" + ruleResp.getError_msg());
						resp.setError_code("1");
						resp.setError_msg("异常错误2：" + (ruleResp.getError_msg()));
						resp.setResp_code("1");
						resp.setResp_msg("error：" + (ruleResp.getError_msg()));
						resp.setBus_resp("1");
					} else {
						/*
						 * PreCommitBSSResp preCommitBSSResp =
						 * (PreCommitBSSResp)
						 * ThreadLocalUtil.getProperty(order_id);
						 * ThreadLocalUtil.removeProperty(order_id); String
						 * serial_num =
						 * preCommitBSSResp.getRespJson().getSerial_num();
						 */
						String serial_num = CommonDataFactory.getInstance().getOrderTree(order_id)
								.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getBssOrderId();
						resp.setBss_pre_order_id(serial_num);
						resp.setResp_code("0");
						resp.setResp_msg("success");
						resp.setBus_resp("0");
					}
				} else {
					throw new Exception("商品类型错误，调用接口失败");
				}
			}
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("-9999");
			resp.setResp_msg("异常错误：" + e.getMessage() == null ? e.toString() : e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 卡信息获取接口
	 * 
	 * @author huang.zhisheng@ztesoft.com
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "卡信息获取接口", summary = "卡信息获取接口")
	public CardInfoGetAPPResp cardInfoGet(CardInfoGetAPPReq req) throws ApiBusiException {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		CardInfoGetAPPResp resp = new CardInfoGetAPPResp();
		String order_id = req.getOrder_id();
		try {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			//是否自定义订单 
			String is_work_custeam= orderTree.getOrderExtBusiRequest().getIs_work_custom();
			
			if (orderTree != null) {
				String iccid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
				OrderItemExtBusiRequest orderItemExt = orderTree.getOrderItemBusiRequests().get(0)
						.getOrderItemExtBusiRequest();
				if(!StringUtils.isEmpty(orderItemExt.getBssOrderId())||!StringUtils.isEmpty(orderItemExt.getActive_no())){
					resp.setResp_code("1");
					resp.setResp_msg("该订单已经提交不允许换卡,原卡号"+iccid);
					return resp;
				}
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ICCID },
						new String[] { req.getICCID() });
			
			if(orderTree == null)
				throw new Exception("找不到"+order_id+"订单的订单树对象");
			
			if(orderTree.getOrderExtBusiRequest() == null)
				throw new Exception("找不到"+order_id+"订单的订单树扩展对象");
			if((!StringUtils.isEmpty(orderItemExt.getBssOrderId())||!StringUtils.isEmpty(orderItemExt.getActive_no()))&& !StringUtils.isEmpty(iccid)){
				resp.setResp_code("1");
				resp.setResp_msg("该订单已经提交不允许换卡,原卡号"+iccid);
				return resp;
			}
			
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ICCID },
					new String[] { req.getICCID() });
			
			if("1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())){
				//自定义流程--获取卡信息
				this.dealWorkCustomOrderCardInfo(order_id);
				
				String sql = "SELECT a.simid as imsi,a.script_seq as scriptseq,a.proc_id as proc_id "
						+"FROM es_order_extvtl a WHERE a.order_id='"+order_id+"'";
				List ret = baseDaoSupport.queryForList(sql );
				
				if(ret==null || ret.size()==0)
					throw new Exception("未查询到卡信息");
				
				resp.setCard_Info(ret.get(0));
				resp.setResp_code("00000");
				resp.setResp_msg("success");
			}else {
				
				String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.TYPE_ID);
				//String goodsCatId = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.CAT_ID);
				String xx_card_center = cacheUtil.getConfigInfo("xx_card_center");//号卡-总部（AOP）
	            Boolean flag = false;
	            if(!StringUtils.isEmpty(xx_card_center) && xx_card_center.contains(type_id)){
	                    flag = true;
	            }
				if (flag || StringUtil.equals(type_id, SpecConsts.TYPE_ID_GOODS_CBSS)
						|| "226723870818693120".equals(type_id)) {// 号卡-总部（AOP）
					/**
					 * @author song.qi 规则已执行->导致后续业务获取数据为空，导致空指针 XXAPP问题修改 三、 手机开户，获取卡数据接口异常错误
					 */
					String logSql = "select * from es_rule_exe_log where obj_id='" + order_id + "' and rule_id='"
							+ EcsOrderConsts.BUSI_CARD_AOP_RULE_ID + "' and exe_result = '" + 0 + "' ";
					List list = baseDaoSupport.queryForList(logSql);
					if (list != null && list.size() > 0) {
						resp.setResp_code("1");
						resp.setResp_msg(order_id + "规则已执行，建议重新下单");
						return resp;
					}
					RuleTreeExeReq requ = new RuleTreeExeReq();
					TacheFact fact = new TacheFact();
					fact.setOrder_id(req.getOrder_id());
					ThreadLocalUtil.setProperty(req.getOrder_id() + "_region_id", req.getRegion_id());
					requ.setFact(fact);
					requ.setRule_id(EcsOrderConsts.BUSI_CARD_AOP_RULE_ID);
					requ.setCheckAllRelyOnRule(true);
					requ.setCheckCurrRelyOnRule(true);
					ZteClient client1 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
					RuleTreeExeResp ruleResp = client1.execute(requ, RuleTreeExeResp.class);
					// 如果获取卡信息规则调用失败
					if (!ConstsCore.ERROR_SUCC.equals(ruleResp.getError_code())) {
						logger.info("获取卡信息规则调用失败：" + ruleResp.getError_msg());
						resp.setResp_code("1");
						resp.setResp_msg("异常错误2：" + (ruleResp.getError_msg()));
					} else {
						CardDataQryResponse cardDataQryResponse = (CardDataQryResponse) ThreadLocalUtil
								.getProperty(order_id);
						ThreadLocalUtil.removeProperty(order_id);
						Map cardInfo = new HashMap();
						cardInfo.put("scriptseq", cardDataQryResponse.getScriptSeq());
						cardInfo.put("proc_id", cardDataQryResponse.getProcId());
						cardInfo.put("imsi", cardDataQryResponse.getImsi());
						resp.setCard_Info(cardInfo);
						resp.setResp_code("00000");
						resp.setResp_msg("success");
					}
				} else if (StringUtil.equals(type_id, SpecConsts.TYPE_ID_GOODS_BSS)
						|| StringUtil.equals(type_id, SpecConsts.TYPE_ID_GOODS_JIKE)
						|| StringUtil.equals(type_id, "226723870818693120")
						|| StringUtils.equals(type_id, EcsOrderConsts.GOODS_TYPE_RONGHE)) {// 号卡-省内（BSS）
				
				Map<String, Object> fields = new HashMap<String, Object>();
				Map<String, Object> where = new HashMap<String, Object>();
				fields.put("exe_result", "1");
				where.put("rule_id", EcsOrderConsts.BUSI_CARD_BSS_RULE_ID);
				where.put("obj_id", order_id);
				where.put("exe_result", "0");
				baseDaoSupport.update("es_rule_exe_log", fields, where);
				
					// 调用规则
					RuleTreeExeReq requ = new RuleTreeExeReq();
					TacheFact fact = new TacheFact();
					fact.setOrder_id(req.getOrder_id());
					ThreadLocalUtil.setProperty(req.getOrder_id() + "_region_id", req.getRegion_id());
					requ.setFact(fact);
					requ.setRule_id(EcsOrderConsts.BUSI_CARD_BSS_RULE_ID);
					requ.setCheckAllRelyOnRule(true);
					requ.setCheckCurrRelyOnRule(true);
					ZteClient client1 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
					RuleTreeExeResp ruleResp = client1.execute(requ, RuleTreeExeResp.class);
					// 如果开户预提交规则调用失败
					if (!ConstsCore.ERROR_SUCC.equals(ruleResp.getError_code())) {
						logger.info("卡信息获取规则调用失败：" + ruleResp.getError_msg());
						resp.setError_code("1");
						resp.setError_msg("异常错误2：" + (ruleResp.getError_msg()));
					} else {
						CardInfoGetBSSResp cardInfoGetBSSResp = (com.ztesoft.net.ecsord.params.ecaop.resp.CardInfoGetBSSResp) ThreadLocalUtil
								.getProperty(order_id);
						ThreadLocalUtil.removeProperty(order_id);
						resp.setCard_Info(cardInfoGetBSSResp.getRespJson());
						resp.setResp_code(cardInfoGetBSSResp.getCode());
						resp.setResp_msg(cardInfoGetBSSResp.getMsg());
					}
				} else {
					throw new Exception("商品类型错误，调用接口失败");
				}
			}
			}
		} catch (Exception e) {// 调用接口失败
			resp.setResp_code("1");
			resp.setResp_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}
	
//	/**
//	 * 执行下一步环节
//	 * @param nodeIns 订单当前环节实例对象
//	 * @param nodeName 需要比较的环节名称
//	 */
//	private String customEngineNext(String nodeName,String order_id) {
//		ES_WORK_CUSTOM_NODE_INS_DAO customNodeDao = SpringContextHolder.getBean("ES_WORK_CUSTOM_NODE_INS_DAO"); 
//		
//		List<ES_WORK_CUSTOM_NODE_INS> results = customNodeDao.qryForListByArgs("select a.instance_id,a.node_name from es_work_custom_node_ins a where 1=1 and a.is_curr_step = '1' "
//				+ "and a.is_done = '0' and a.order_id=?", order_id);
//		//获取当前实例
//		ES_WORK_CUSTOM_NODE_INS nodeIns = results.get(0);
//		
//		if(StringUtils.equals(nodeName, nodeIns.getNode_name())) {
//			try {
//				workCustomEngine.runNodeManual(nodeIns.getInstance_id(), true, null);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				return e.getMessage();
//			}
//		} 
//		return "当前不是【 "+nodeName+"】环节";
//	}
	/**
	 * 写卡结果通知
	 * 
	 * @author xiang.yangbo@ztesoft.com
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "写卡结果通知", summary = "写卡结果通知")
	public WriteCardResultAPPResp writeCardResultService(WriteCardResultAPPReq req) throws ApiBusiException {
		
		WriteCardResultAPPResp resp = new WriteCardResultAPPResp();
		
		String order_id = req.getOrder_id();
		
		try {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			
			if(orderTree == null)
				throw new Exception("找不到"+order_id+"订单的订单树对象");
			
			if(orderTree.getOrderExtBusiRequest() == null)
				throw new Exception("找不到"+order_id+"订单的订单树扩展对象");
			
			if("1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())){
				//自定义流程--写卡结果通知
				this.dealWorkCustomWriteCardResult(order_id);
				
				resp.setRes_code("00000");
				resp.setRes_msg("success");
			}else {
				
				String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.TYPE_ID);
				String cat_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.CAT_ID);
				String xx_card_center = cacheUtil.getConfigInfo("xx_card_center");//号卡-总部（AOP）
                Boolean flag = false;
                if(!StringUtils.isEmpty(xx_card_center) && xx_card_center.contains(type_id)){
                        flag = true;
                }
				if (flag ||StringUtil.equals(type_id, SpecConsts.TYPE_ID_GOODS_CBSS)  || "226723870818693120".equals(type_id)) {// 号卡-总部（AOP）
					// 调用规则
					RuleTreeExeReq requ = new RuleTreeExeReq();
					TacheFact fact = new TacheFact();
					fact.setOrder_id(req.getOrder_id());

					requ.setFact(fact);
					requ.setRule_id(EcsOrderConsts.BUSI_CARD_AOP_WTRSTOAOP_RULE_ID);
					requ.setCheckAllRelyOnRule(true);
					requ.setCheckCurrRelyOnRule(true);
					ZteClient client1 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
					RuleTreeExeResp ruleResp = client1.execute(requ, RuleTreeExeResp.class);
					// 如果获取卡信息规则调用失败
					if (!ConstsCore.ERROR_SUCC.equals(ruleResp.getError_code())) {
						logger.info("写卡结果通知规则调用失败：" + ruleResp.getError_msg());
						resp.setRes_code("1");
						resp.setError_msg("异常错误：" + (ruleResp.getError_msg()));
					} else {
						resp.setRes_code("00000");
						resp.setRes_msg("success");
					}
				} else if (StringUtil.equals(type_id, SpecConsts.TYPE_ID_GOODS_BSS)
						|| StringUtils.equals(type_id, SpecConsts.TYPE_ID_GOODS_JIKE)
						|| StringUtils.equals(type_id, EcsOrderConsts.GOODS_TYPE_RONGHE)) {// 号卡-省内（BSS）
					// 把region_id缓存下来，调用规卡数据同步规则时候用
					ThreadLocalUtil.setProperty(req.getOrder_id() + "_region_id", req.getRegion_id());
					ThreadLocalUtil.setProperty(req.getOrder_id() + "_reasonId", req.getReasonId());
					// 调用规则
					RuleTreeExeReq requ = new RuleTreeExeReq();
					TacheFact fact = new TacheFact();
					fact.setOrder_id(req.getOrder_id());
					requ.setFact(fact);
					requ.setRule_id(EcsOrderConsts.BUSI_CARD_BSS_WTRSTOBSS_RULE_ID);
					requ.setCheckAllRelyOnRule(true);
					requ.setCheckCurrRelyOnRule(true);
					ZteClient client1 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
					RuleTreeExeResp ruleResp = client1.execute(requ, RuleTreeExeResp.class);
					// 如果规则调用失败
					if (!ConstsCore.ERROR_SUCC.equals(ruleResp.getError_code())) {
						logger.info("卡信息同步规则调用失败：" + ruleResp.getError_msg());
						resp.setRes_code("1");
						resp.setError_msg("异常错误：" + (ruleResp.getError_msg()));
					} else {
						resp.setRes_code("00000");
						resp.setRes_msg("success");
					}
				} else {
					throw new Exception("商品类型错误，调用接口失败");
				}
			}
		} catch (Exception e) {// 调用接口失败
			resp.setRes_code("1");
			resp.setRes_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 等待支付结果
	 * 
	 * @author huang.zhisheng@ztesoft.com
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "等待支付结果", summary = "等待支付结果")
	public PayResultAPPResp PayResultService(PayResultAPPReq req) throws ApiBusiException {
		// TODO Auto-generated method stub
		PayResultAPPResp resp = new PayResultAPPResp();
		String order_id = req.getOrder_id();
		try {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			if (orderTree != null) {
				String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.TYPE_ID);
				String xx_card_center = cacheUtil.getConfigInfo("xx_card_center");//号卡-总部（AOP）
                Boolean flag = false;
                if(!StringUtils.isEmpty(xx_card_center) && xx_card_center.contains(type_id)){
                        flag = true;
                }
				if (flag ||StringUtil.equals(type_id, SpecConsts.TYPE_ID_GOODS_CBSS) || "226723870818693120".equals(type_id)) {// 号卡-总部（AOP）
					// 调用规则
					RuleTreeExeReq requ = new RuleTreeExeReq();
					TacheFact fact = new TacheFact();
					fact.setOrder_id(req.getOrder_id());
					fact.setRequest(req);
					requ.setFact(fact);
					requ.setRule_id(EcsOrderConsts.BUSI_CARD_AOP_PAYRESULT_RULE_ID);
					requ.setCheckAllRelyOnRule(true);
					requ.setCheckCurrRelyOnRule(true);
					ZteClient client1 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
					RuleTreeExeResp ruleResp = client1.execute(requ, RuleTreeExeResp.class);
					// 如果获取卡信息规则调用失败
					if (!ConstsCore.ERROR_SUCC.equals(ruleResp.getError_code())) {
						resp.setResp_code("0");
						resp.setResp_msg("success");
					} else {
						logger.info("等待支付结果规则调用失败：" + ruleResp.getError_msg());
						resp.setError_code("1");
						resp.setError_msg("异常错误2：" + (ruleResp.getError_msg()));
						resp.setResp_code("1");
						resp.setResp_msg("异常错误2：" + (ruleResp.getError_msg()));
					}
				} else if (StringUtil.equals(type_id, SpecConsts.TYPE_ID_GOODS_BSS)
						|| StringUtil.equals(type_id, SpecConsts.TYPE_ID_GOODS_JIKE)) {// 号卡-省内（BSS）
					// 调用规则
					RuleTreeExeReq requ = new RuleTreeExeReq();
					TacheFact fact = new TacheFact();
					fact.setOrder_id(req.getOrder_id());
					OrderInfoUpdateReq req2 = new OrderInfoUpdateReq();
					req2.setOrder_id(req.getOrder_id());
					req2.setPay_result(req.getPay_result());
					fact.setRequest(req2);
					requ.setFact(fact);
					requ.setRule_id(EcsOrderConsts.BUSI_CARD_BSS_PAYRESULT_RULE_ID);
					requ.setCheckAllRelyOnRule(true);
					requ.setCheckCurrRelyOnRule(true);
					ZteClient client1 = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
					RuleTreeExeResp ruleResp = client1.execute(requ, RuleTreeExeResp.class);
					// 如果规则调用失败
					if (!ConstsCore.ERROR_SUCC.equals(ruleResp.getError_code())) {
						resp.setResp_code("0");
						resp.setResp_msg("success");
					} else {
						logger.info("等待支付结果规则调用失败：" + ruleResp.getError_msg());
						resp.setError_code("1");
						resp.setError_msg("异常错误2：" + (ruleResp.getError_msg()));
						resp.setResp_code("1");
						resp.setResp_msg("异常错误2：" + (ruleResp.getError_msg()));
					}

				} else {
					throw new Exception("商品类型错误，调用接口失败");
				}
			}
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("1");
			resp.setError_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			resp.setResp_code("1");
			resp.setResp_msg("异常错误2：" + (resp.getError_msg()));
			e.printStackTrace();
		}
		return resp;
	}

	// public static void main(String[] args) {
	// BusiHandleCheckAPPResp resp = new BusiHandleCheckAPPResp();
	// logger.info(resp.bss_code);
	// }

	// 固网预提交接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "固网预提交接口", summary = "固网预提交接口")
	public GuWangPreSubResp guWangPreSub(GuWangPreSubReq req) throws ApiBusiException {
		GuWangPreSubResp resp = new GuWangPreSubResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {

			Map map = orderInfManager.qryGoodsDtl(req.getNotNeedReqStrOrderId());

			logger.info(CommonDataFactory.getInstance().getOrderTree(req.getNotNeedReqStrOrderId()).getOrderDeliveryBusiRequests().get(0).getShip_tel());

			req.setScheme_id(Const.getStrValue(map, "p_code") + "");
			req.setProduct_id(Const.getStrValue(map, "sn") + "");
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (GuWangPreSubResp) caller.invoke("ecaop.trades.base.guhua.pre.sub", param);
			if (resp.getCode().equals("00000") && !StringUtils.isEmpty(resp.getCode())) {
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance()
						.getOrderTree(req.getNotNeedReqStrOrderId());
				List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
				OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0)
						.getOrderItemExtBusiRequest();
				BssOrderId BssOrderId = resp.getRespJson();
				if (BssOrderId.getBms_accept_id() instanceof String) {
					orderItemExtBusiRequest.setBssOrderId(BssOrderId.getBms_accept_id());
					orderItemExtBusiRequest.setActive_no(BssOrderId.getBms_accept_id());
					orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					orderItemExtBusiRequest.store();
				}

			} else {
				resp.setCode("-9999");
				resp.setError_msg(resp.getMsg());
			}

		} catch (Exception e) {// 调用接口失败
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}

	// 融合预提交接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "融合预提交接口", summary = "融合预提交接口")
	public GroupOrderSubResp groupOrderSub(GroupOrderSubReq req) throws ApiBusiException {
		GroupOrderSubResp resp = new GroupOrderSubResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {

			Map map = orderInfManager.qryGoodsDtl(req.getNotNeedReqStrOrderId());
			GroupInFoVO groupinfo = new GroupInFoVO();
			groupinfo.setScheme_id(Const.getStrValue(map, "p_code") + "");
			groupinfo.setGroupservicetype("106");
			//无线宽带 kdInfo传[];
			String catId = CommonDataFactory.getInstance().getGoodSpec(req.getNotNeedReqStrOrderId(), null, SpecConsts.CAT_ID);
			if(StringUtils.equals(catId, EcsOrderConsts.GOODS_CAT_ID_WUXIANKD)) {
				req.setKdInfo(new ArrayList());
				req.getYwInfo();
			}
			//add by sgf 无线宽带线上  取值问题
			EssOperatorInfoUtil essinfo = new EssOperatorInfoUtil();
	        EmpOperInfoVo essOperInfo = null;
	        String order_from = CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.ORDER_FROM);//地市
	        String cat_id = CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.GOODS_CAT_ID);//地市
	        String city_id = CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(), AttrConsts.ORDER_CITY_CODE);//地市
	        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	        String groupFlowKg = cacheUtil.getConfigInfo("groupFlowKg");
	        if(StringUtils.isNotEmpty(groupFlowKg) &&"1".equals(groupFlowKg) && "10093".equals(order_from) && "221668199563784192".equals(cat_id)){
	             essOperInfo = EssOperatorInfoUtil.getEmpInfoFromDataBase(null,"WFGROUPUPINTENT",city_id);//无线宽带线上取默认的渠道和工号信息
	        }
	        if(essOperInfo != null){
	            req.setOperator_id(essOperInfo.getEss_emp_id());
	            req.setOffice_id(essOperInfo.getDep_id());
	        }
			
			/**
			 * Add by Wcl scheme_type 从商品属性取 1.随意包 2.超值包 3.其他
			 * 
			 * 增加account_number字段
			 * 
			 * 修改groupinfo节点 user_to_account默认传值的问题 account_number 不为空
			 * user_to_account传2，account_number 为空 user_to_account传0
			 */
			String scheme_type = CommonDataFactory.getInstance().getGoodSpec(req.getNotNeedReqStrOrderId(), null,
					"scheme_type");
			if (StringUtils.isEmpty(scheme_type)) {
				scheme_type = "2";
			}
			groupinfo.setScheme_type(scheme_type);
			groupinfo.setGroup_name(CommonDataFactory.getInstance().getAttrFieldValue(req.getNotNeedReqStrOrderId(),
					AttrConsts.CERT_CARD_NAME));

			List<OrderAdslBusiRequest> adslList = CommonDataFactory.getInstance()
					.getOrderTree(req.getNotNeedReqStrOrderId()).getOrderAdslBusiRequest();
			if(null != adslList && adslList.size() > 0) {
				String accountNumber = adslList.get(0).getAccount_number();
				if (StringUtils.isNotEmpty(accountNumber)) {
					groupinfo.setUser_to_account("2");
				}
				groupinfo.setAccount_number(accountNumber);
			}
			/**
			 * end
			 */
			req.setGroupInfo(groupinfo);
			
			
			// "ywInfo\":[{\"is_main_number\":\"1\",\"is_new\":\"0\",\"source_flag\":\"0\",\"subs_id\":\"310793454\"}]"

			// req.setProduct_id(Const.getStrValue(map, "sn")+"");
			
			BeanUtils.bean2MapForAiPBSSNew(param, req);
			
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (GroupOrderSubResp) caller.invoke("ecaop.trades.serv.group.order.sub", param);
			if (resp.getCode().equals("00000") && !StringUtils.isEmpty(resp.getCode())) {
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance()
						.getOrderTree(req.getNotNeedReqStrOrderId());
				List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
				OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0)
						.getOrderItemExtBusiRequest();
				BssOrderId BssOrderId = resp.getRespJson();
				if (BssOrderId.getBms_accept_id() instanceof String) {
					orderItemExtBusiRequest.setBssOrderId(BssOrderId.getBms_accept_id());
					orderItemExtBusiRequest.setActive_no(BssOrderId.getBms_accept_id());
					orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					orderItemExtBusiRequest.store();
				}
			} else {
				resp.setCode("-9999");
				resp.setError_msg(resp.getMsg());
			}

		} catch (Exception e) {// 调用接口失败
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}

	// 订单状态查询接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS订单状态查询接口", summary = "BSS订单状态查询接口")
	public OrderStatusQryResp orderStatusQry(OrderStatusQryReq req) throws ApiBusiException {
		OrderStatusQryResp resp = new OrderStatusQryResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			resp = (OrderStatusQryResp) caller.invoke("ecaop.trades.query.bmsorder.status.qry", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("1");
			resp.setError_msg("异常错误：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	// 客户下用户数据查询接口 宋琪
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "客户下用户数据查询接口", summary = "客户下用户数据查询接口")
	public UserDataQryResp userDataQry(UserDataQryReq req) throws ApiBusiException {
		UserDataQryResp resp = new UserDataQryResp();
		CommCaller caller = new CommCaller();
		// servlet调用这里
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			resp = (UserDataQryResp) caller.invoke("ecaop.trades.query.cust.usernumr.qry", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("1");
			resp.setError_msg("异常错误：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	// 返销订单状态查询接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS返销订单状态查询接口", summary = "BSS返销订单状态查询接口")
	public CancelOrderStatusQryResp cancelOrderStatusQry(CancelOrderStatusQryReq req) throws ApiBusiException {
		CancelOrderStatusQryResp resp = new CancelOrderStatusQryResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			resp = (CancelOrderStatusQryResp) caller.invoke("ecaop.trades.query.bmscancelorder.status.qry", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("1");
			resp.setError_msg("异常错误：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	// 2.2.3 产品受理接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "产品受理接口", summary = "产品受理接口")
	public PromoteProductResp promoteProduct(PromoteProductReq req) throws ApiBusiException {
		PromoteProductResp resp = new PromoteProductResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (PromoteProductResp) caller.invoke("ecaop.trades.subs.comm.promote.product", param);

		} catch (Exception e) {// 调用接口失败
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "CBSS返销费用查询接口", summary = "CBSS返销费用查询接口")
	public CbssrefundFeeQryResp cbssrefundFeeQry(CbssrefundFeeQryReq req) throws ApiBusiException {
		CbssrefundFeeQryResp resp = new CbssrefundFeeQryResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (CbssrefundFeeQryResp) caller.invoke("ecaop.trades.query.cbssrefund.fee.qry", param);

		} catch (Exception e) {// 调用接口失败
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS工号转CB工号接口", summary = "BSS工号转CB工号接口")
	public TabuserBtocbSubResp tabuserBtocbSub(TabuserBtocbSubReq req) throws ApiBusiException {
		TabuserBtocbSubResp resp = new TabuserBtocbSubResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (TabuserBtocbSubResp) caller.invoke("ecaop.trades.serv.tabuser.btocb.sub", param);

		} catch (Exception e) {// 调用接口失败
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "二次选占接口", summary = "二次选占接口")
	public ResourCecenterAppResp resourCecenterApp(ResourCecenterAppReq req) throws ApiBusiException {
		ResourCecenterAppResp resp = new ResourCecenterAppResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (ResourCecenterAppResp) caller.invoke("ecaop.trades.serv.resource.center.app", param);

		} catch (Exception e) {// 调用接口失败
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}
	
	 @Override
	 @ZteSoftCommentAnnotation(type = "method", desc = "成卡卡号校验", summary = "成卡卡号校验")
     public ResourCecenterAppResp checkCreateCard(GroupOrderCardCheckReq req) throws ApiBusiException {
	     ResourCecenterAppResp resp = new ResourCecenterAppResp();
	        CommCaller caller = new CommCaller();
	        Map<String, Object> param = new HashMap<String, Object>();
	        try {
	            BeanUtils.bean2MapForAiPBSS(param, req);
	            param.put("ord_id", req.getNotNeedReqStrOrderId());
	            resp = (ResourCecenterAppResp) caller.invoke("ecaop.trades.serv.resource.center.app", param);
	        } catch (Exception e) {// 调用接口失败
	            resp.setCode("-9999");
	            resp.setMsg(e.getMessage());
	            resp.setError_msg(e.getMessage());
	            e.printStackTrace();
	        }

	        return resp;
     }

	 @Override
	 @ZteSoftCommentAnnotation(type = "method", desc = "终端串号校验", summary = "终端串号校验")
     public ResourCecenterAppResp checkApiTerminal( GroupOrderFixedNetworkCheckReq req) throws ApiBusiException {
	     ResourCecenterAppResp resp = new ResourCecenterAppResp();
         CommCaller caller = new CommCaller();
         Map<String, Object> param = new HashMap<String, Object>();
         try {
             BeanUtils.bean2MapForAiPBSS(param, req);
             param.put("ord_id", req.getNotNeedReqStrOrderId());//记录日志
             resp = (ResourCecenterAppResp) caller.invoke("ecaop.trades.serv.resource.center.app", param);
         } catch (Exception e) {// 调用接口失败
             resp.setCode("-9999");
             resp.setMsg(e.getMessage());
             resp.setError_msg(e.getMessage());
             e.printStackTrace();
         }
         return resp;
     }

	// 码上购状态同步接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "码上购状态同步接口", summary = "码上购状态同步接口")
	public O2OStatusUpdateResp o2OStatusUpdate(O2OStatusUpdateReq req) throws ApiBusiException {
		O2OStatusUpdateResp resp = new O2OStatusUpdateResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			resp = (O2OStatusUpdateResp) caller.invoke("Q2M.app.status.update", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("1");
			resp.setError_msg("异常错误：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	// 活动费用查询接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "活动费用查询接口", summary = "活动费用查询接口")
	public SchemeFeeQryResp schemeFeeQry(SchemeFeeQryReq req) throws ApiBusiException {
		SchemeFeeQryResp resp = new SchemeFeeQryResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			resp = (SchemeFeeQryResp) caller.invoke("ecaop.trades.query.scheme.fee.qry", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("1");
			resp.setError_msg("异常错误：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	// 活动费用查询接口 --songqi
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "营业费用查询接口", summary = "营业费用查询接口")
	public BusinessFeeQryResp businessFeeQry(BusinessFeeQryReq req) throws ApiBusiException {
		BusinessFeeQryResp resp = new BusinessFeeQryResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			resp = (BusinessFeeQryResp) caller.invoke("ecaop.trades.base.comm.fee.qry", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("1");
			resp.setError_msg("异常错误：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	// 获取宽带账号/编码
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取宽带账号/编码", summary = "获取宽带账号/编码")
	public KdnumberQryResp kdnumberQry(KdnumberQryReq req) throws ApiBusiException {
		KdnumberQryResp resp = new KdnumberQryResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			resp = (KdnumberQryResp) caller.invoke("ecaop.trades.query.resource.kdnumber.qry", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("1");
			resp.setError_msg("异常错误：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	// 光猫ID获取
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "光猫ID获取", summary = "光猫ID获取")
	public QryFtthObjIDResp qryFtthObjID(QryFtthObjIdReq req) throws ApiBusiException {
		QryFtthObjIDResp resp = new QryFtthObjIDResp();

		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			resp = (QryFtthObjIDResp) caller.invoke("ecaop.trades.query.ftth.objectid.qry", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("1");
			resp.setError_msg("异常错误：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}

		return resp;
	}

	// 新老客户校验
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "新老客户校验", summary = "新老客户校验")
	public OldUserCheckResp oldUserCheck(OldUserCheckReq req) throws ApiBusiException {
		OldUserCheckResp resp = new OldUserCheckResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			resp = (OldUserCheckResp) caller.invoke("ecaop.trades.serv.olduser.group.chk", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("1");
			resp.setError_msg("异常错误：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "宽带账号校验", summary = "宽带账号校验")
	public KdNumberCheckResp kdNumberCheck(KdNumberCheckReq req) throws ApiBusiException {
		KdNumberCheckResp resp = new KdNumberCheckResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			resp = (KdNumberCheckResp) caller.invoke("ecaop.trades.serv.resource.kdnumber.chk", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("1");
			resp.setError_msg("异常错误：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "赠品查询接口", summary = "赠品查询接口")
	public ObjectQryResp objectQry(ObjectQryReq req) throws ApiBusiException {
		ObjectQryResp resp = new ObjectQryResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			//param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			resp = (ObjectQryResp) caller.invoke("ecaop.trades.query.present.object.qry", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("1");
			resp.setError_msg("异常错误：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "宽带新装、移机、修障进度查询接口", summary = "宽带新装、移机、修障进度查询接口")
	public RateStatusResp rateStatusQry(RateStatusReq req) throws ApiBusiException {
		RateStatusResp resp = new RateStatusResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			resp = (RateStatusResp) caller.invoke("ecaop.trades.query.order.status.qry", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("1");
			resp.setError_msg("异常错误：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	public static void main(String[] args) {
	/*	String private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCFG4EEw4baXFQmFcXKyaM4USaOav2012hHcnSaTis/S8IgE2A2U6TcTkesMLU53QaUEHyoIpQbdqAPHvBleCXR6UOErMdlLV5TM7+TvYbn30dkUt76Yj0J2o59RKpA0xjXDIwVULN2PaT4mrAtKuvkqnX3NsI4kzmsEDDOkGVRsBsFJtsO5bNhu9PI8AT4FQfScCVcXvMeMfM3BZq9RP5BBenH8RkcXvWMbsUvdehnKl3ZgXZRN/uUgkRSCI3GATL5vU9TKhwT8vFSEjHNHJYbhRHpBMB3h9wPhNAj983KVx/z2cy+BiYZFicopXIscYD7DZ48beBSeXv85XdaLc9nAgMBAAECggEAI524aA3ph4QtAAPTaO+LPQZ5yqHWQQ1iWbvGd4NzDvy2dN66X88BzfEkPG7MDy+VEwlWUabu+mpDachdiskbVY+VAWs571i0ZI70NBQChQi5kZ6bC2Q98OUKO3qkE7RocSJqWUm+u2TDP8IkYfGaLRVvezarwXejdubeKGd6tOCCX1WjFYJBNJ0ylwcjxNqUUXF0hyxYzbB+Kn1jd7anyiTy1cvQ20IYK3Idsu5ZBED9gYCUbeNtUB5q6K2tzYiun/mWpzvDD8Lb6lgj0+BeVjfKNVmryrKdvGz+k+cSQsaKwTG1hlxH/ec8vfXNIEtz6de0EB/LDvEndxd9NOFcAQKBgQD+76e57CCTBgPjiNXkdnZh9z+La58ruAwGIW4nfaleTyl1+2mr8GruMQVghNtty2+GIzKHNX3vnik5BBj9QYag77GJ94sMLn0ySu/PJiTCDi4VVDy+OgcuNScAQabbyNO55PnXphiVTq4F+icDqqb+qMiS8Zh9Kx5OO+mK0r0IHQKBgQCFqbNqUPC1C9vN2lqrivylNqroh6B633joBNbHfsJn3+xhMIT+WmRUPV0dHkgV5hg/Yj+qOb5SN5P6YiGwWpLrI56YCDL/2EsQlkHx5JIwr5YLzi87ouXtoibCymiuH/u6CGLlU3ay+k0c5r+r2UHOKA5q8cjYMbBXNcmkaCmGUwKBgQChwJFdJ/LrFg/1mGaOFJO8SNIqtdhCNv56DVAFAngp/HREBYbgryRJiRTuTp2jJkbWhxKSM2B6XpGtOWpZle/DwRGWhlfVHNIWzPEnL/52meSnZ9E6qTrRGKxsPzuNi/i6pAvZxesnIMrNxBBpBSf7wD+y7FNiZ69M2Dqv+FI0CQKBgEDntwfZZgmESKgdcAn849IWScfAlF0WR0/jiag2FZD7W32WYf5hRnbhlWWeXsLDiOK+gsvWXmTuOB1Nqa5JTS6BjXGmCZ3TGiBj1oHvzE139hSleqoq4BwUsV3FjC+BuGsNx3g0n18pzGhMZKa+SQYizV2BK1ZQSqEOrWv4aLGhAoGBAO3EWcZ+xmW596LpFgePvoeL3Nw3MMQt5q+dxDIPszsX5cOsLMf2DDZgGsheJLEg1BgfXGa+Bl14YMIQsm3MoGKquaoAR1hJnQlLbSSF1dtoQR8md0qYWgPE5bYslaW89hLkW010lyOlg4obG3TVLqAL3UkOhQQvb64qQo2FRVwN{publicKey=MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhRuBBMOG2lxUJhXFysmjOFEmjmr9tNdoR3J0mk4rP0vCIBNgNlOk3E5HrDC1Od0GlBB8qCKUG3agDx7wZXgl0elDhKzHZS1eUzO/k72G599HZFLe+mI9CdqOfUSqQNMY1wyMFVCzdj2k+JqwLSrr5Kp19zbCOJM5rBAwzpBlUbAbBSbbDuWzYbvTyPAE+BUH0nAlXF7zHjHzNwWavUT+QQXpx/EZHF71jG7FL3XoZypd2YF2UTf7lIJEUgiNxgEy+b1PUyocE/LxUhIxzRyWG4UR6QTAd4fcD4TQI/fNylcf89nMvgYmGRYnKKVyLHGA+w2ePG3gUnl7/OV3Wi3PZwIDAQAB,privateKey=MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCFG4EEw4baXFQmFcXKyaM4USaOav2012hHcnSaTis/S8IgE2A2U6TcTkesMLU53QaUEHyoIpQbdqAPHvBleCXR6UOErMdlLV5TM7+TvYbn30dkUt76Yj0J2o59RKpA0xjXDIwVULN2PaT4mrAtKuvkqnX3NsI4kzmsEDDOkGVRsBsFJtsO5bNhu9PI8AT4FQfScCVcXvMeMfM3BZq9RP5BBenH8RkcXvWMbsUvdehnKl3ZgXZRN/uUgkRSCI3GATL5vU9TKhwT8vFSEjHNHJYbhRHpBMB3h9wPhNAj983KVx/z2cy+BiYZFicopXIscYD7DZ48beBSeXv85XdaLc9nAgMBAAECggEAI524aA3ph4QtAAPTaO+LPQZ5yqHWQQ1iWbvGd4NzDvy2dN66X88BzfEkPG7MDy+VEwlWUabu+mpDachdiskbVY+VAWs571i0ZI70NBQChQi5kZ6bC2Q98OUKO3qkE7RocSJqWUm+u2TDP8IkYfGaLRVvezarwXejdubeKGd6tOCCX1WjFYJBNJ0ylwcjxNqUUXF0hyxYzbB+Kn1jd7anyiTy1cvQ20IYK3Idsu5ZBED9gYCUbeNtUB5q6K2tzYiun/mWpzvDD8Lb6lgj0+BeVjfKNVmryrKdvGz+k+cSQsaKwTG1hlxH/ec8vfXNIEtz6de0EB/LDvEndxd9NOFcAQKBgQD+76e57CCTBgPjiNXkdnZh9z+La58ruAwGIW4nfaleTyl1+2mr8GruMQVghNtty2+GIzKHNX3vnik5BBj9QYag77GJ94sMLn0ySu/PJiTCDi4VVDy+OgcuNScAQabbyNO55PnXphiVTq4F+icDqqb+qMiS8Zh9Kx5OO+mK0r0IHQKBgQCFqbNqUPC1C9vN2lqrivylNqroh6B633joBNbHfsJn3+xhMIT+WmRUPV0dHkgV5hg/Yj+qOb5SN5P6YiGwWpLrI56YCDL/2EsQlkHx5JIwr5YLzi87ouXtoibCymiuH/u6CGLlU3ay+k0c5r+r2UHOKA5q8cjYMbBXNcmkaCmGUwKBgQChwJFdJ/LrFg/1mGaOFJO8SNIqtdhCNv56DVAFAngp/HREBYbgryRJiRTuTp2jJkbWhxKSM2B6XpGtOWpZle/DwRGWhlfVHNIWzPEnL/52meSnZ9E6qTrRGKxsPzuNi/i6pAvZxesnIMrNxBBpBSf7wD+y7FNiZ69M2Dqv+FI0CQKBgEDntwfZZgmESKgdcAn849IWScfAlF0WR0/jiag2FZD7W32WYf5hRnbhlWWeXsLDiOK+gsvWXmTuOB1Nqa5JTS6BjXGmCZ3TGiBj1oHvzE139hSleqoq4BwUsV3FjC+BuGsNx3g0n18pzGhMZKa+SQYizV2BK1ZQSqEOrWv4aLGhAoGBAO3EWcZ+xmW596LpFgePvoeL3Nw3MMQt5q+dxDIPszsX5cOsLMf2DDZgGsheJLEg1BgfXGa+Bl14YMIQsm3MoGKquaoAR1hJnQlLbSSF1dtoQR8md0qYWgPE5bYslaW89hLkW010lyOlg4obG3TVLqAL3UkOhQQvb64qQo2FRVwN}";
		// String private_key =
		// "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApwNADvSBwMi+sAZBqT6y+PC5u99PcvSAUyZ+4DxYYedqiOJR4JUqmXZ0R0vNri/r/1VJePpx3caGx4ZRgE+xNVtzZJ7C9spexka4V07e0TfT4cHzZTVm8c5SP1hL6lmSjwphVb3Vv1arLIszi0/JyYXzzulHNXbpK3tmJxYVvhQWDOfapzr/viZF8+R761RIiN70Z2V33UaCoKqutw+FEewI16Qu2vDnveH4YBQU+XrrnU77g8cn7PAfXa1apSwJeCmEpU7QL3gI8HEjd0+DCJHVIuwt72Ceo93PWyULr1DQ9T8bDmYfS/1F7XdR1+uVsxXDf4ImXO2ZlfgfoYakCQIDAQAB";
		String decStr = PayCenterRSA.decryptRSA(
				"VjJJnoY2hpbKSvi2bQ0VLfC49Is9hpHPDdLcvXKeHVUwspGbkJNDseiLRoHO1PknyzSYRibxxb2LhrqrNWraZjNVZ/z+b3MWaIoRlTNXp0aGKHqdo7dzKgKZggvzv/m/jSPttYJ8Ps3wxVwIrDHr94rKx1IscEG+3P3a02gyFffU2f0kqTVNNCwPHVD+wysZ4ueeHEDekMFaQOTcpDQvjWusKg5wwgQX1fc55TOcu/s5Fhgk1rGtzZaJzya3qb6fJaw7zY8jFGH9Ib8aiiFiQLU5Ll7iqBGvIDwCcJfm5nFeN67dBkJTJYoRcFmT3tTbNE2wRgHt5bdBkg/wnqbmwQ==#PFbgabPKzoLNa1yLycK95y/hfByfYekU3SWe88JkWfOnNUhLdLpFZkNRHPKZ4XcZQEmMWLlyz8gIP1w8pZr7ilnOwL2BxCCbOdTyxhRlRRoI8cQAmXVI+DbnOFC5rTqTaYT6xrRwu6JxtUc3iYHQShvMFWFlO/nT8vf8rMKHJH6qbYQPwh3cy1z7MJ1d6FughDU0tONStM9bOjmoeB0n6SVVgVyQAK4A6DEXC39MMkKAhaNaiRw3jTU2sDlyI+oxP6cdMh0SRn+5EGHXFZG+YCI1ewsQCrxomm3KhlYpotDlqdfwueqcAlWRLqu5fwpJ2M2XsV48JEALJF08fMp0nw==#gXrGyXgDRJzRejyDgLYMiXs3MZwa+WsfOp83b2j0g2lkP4nLlz74SD0YIF9hdvqrtIoFe54rRHQxRJgKHMEiXX7wgZA1vxQQQHKWKKIoDPcl+t0VAfjdrVoBbAYAK2E6Fs/dlt78NKJTtcyDIJSIWaNTXlRpXzn1cra5ukAY4YwIDoRbe0jGMcDz3BcFBLvLvINl8ln6i1rDdZr0EjNZWFzIvajPEQOg7XZwi2nET+96I4nJgkXeD7CHKUUD7holZYYdXZyMhW2GhSIe6x7DBN3ns2jDLpn94CU9hOWWANNme6+gybSko0QXEjKgv59GtWZxDL4UH4eLqe0BL5sqdg==#g9QgLw89MkP+ibX/tZ0oJy9Gsr1QAK6n99YFmqPzbBHhF4RGUtEzhKmtxLujePzCtOKnrZkcEEl205QpH6oqJ1bvD9LSvh0xpCYO5+1r2HWu5JrfqCiAK2/en4PTrsfR88nCZXUMt4vEJxsMswcMwXvgPvBd1hS+oltdcNhBSl537pe8wrs4RHp7Y5ZETTuKI+FO11OWKIN2E7iB2C6LqH5jMQt8KEMjKuGY9osr9Wg54SHz7Wp1mMv+WKJxvgbqwS0wOkM2z3LftD3JnDiKYzXYYNlY6mXHaiemiQ07BoigPBpRn9rMxv4+/pevmDCDD5I9U/lXuUcbno4GEXiCAQ==#",
				private_key);
		String new_str = "";
		try {
			new_str = java.net.URLDecoder.decode(decStr, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info(new_str);
		DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			logger.info(DF.format(DF.parse("2017-08-01 08:40:00")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		try {
			String imgFile = "C:\\Users\\Administrator\\Desktop\\123.jpg";//待处理的图片
			String imgbese=getImgStr(imgFile);
			System.out.println(imgbese);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static String getImgStr(String imgFile){
		//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		//读取图片字节数组
		try 
		{
			in = new FileInputStream(imgFile);        
			data = new byte[in.available()];
			in.read(data);
			in.close();
		}catch (IOException e) {
		  e.printStackTrace();
		}
		return new String(Base64.encodeBase64(data));
	}
		
	/**
	 * 订单信息查询接口wssmall.order.detailInfo.query 2017年12月25日
	 * 
	 * @param requ
	 * @return
	 * @throws Exception
	 */
	@Override
	public OrderDetailResp queryOrderDetailByRequ(OrderDetailReq requ) throws Exception {
		OrderDetailResp resp = new OrderDetailResp();
		List<OrderDetail> query_resp = new ArrayList<OrderDetail>();
		resp.setQuery_resp(query_resp);
		try {
			List<OrderDetailVO> query_info = requ.getQuery_info();
			if (null != query_info && query_info.size() > 0) {
				IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
				DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
				List<Map> list = dcPublicCache.getList("DIC_XXAPP_BSS_OP_TYPE");
				for (OrderDetailVO o : query_info) {
					String orderId = o.getOrder_id();
					OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(orderId);
					if (null != orderTree) {
						// 返回参数的获取
						String bus_num = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest()
								.getPhone_num();// 业务号码
						String source_from = orderTree.getOrderExtBusiRequest().getOrder_from();// 订单来源
						String out_order_id = orderTree.getOrderExtBusiRequest().getOut_tid();// 外部订单号
						String order_status;// 订单基本状态
						String order_status_n = "";// 订单补充状态
						String goods_type = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest()
								.getGoods_type();
						String op_type = "";// 0:固网;1:移网
						String pname = "";
						for (int i = 0; i < list.size(); i++) {
							if (StringUtil.equals(goods_type, (String) list.get(i).get("pkey"))) {
								pname = (String) list.get(i).get("pname");
								break;
							}
						}
						if (!StringUtil.isEmpty(pname)) {
							op_type = pname;
						}
						if ("0".equals(op_type)) {// 固网为施工竣工状态 状态待补充
							order_status_n = "";
						} else if ("1".equals(op_type)) {// 移网为号码激活状态0:未激活2:线下激活成功3:线上激活成功4:人工认证中5:人工认证失败
							order_status_n = orderTree.getOrderRealNameInfoBusiRequest().getActive_flag();// 订单补充状态

						}
						String order_city_code = orderTree.getOrderExtBusiRequest().getOrder_city_code();// 订单归属地市330100
						String county_id = CommonDataFactory.getInstance().getAttrFieldValue(orderId,
								AttrConsts.DISTRICT_CODE);// 订单归属县分
						String create_time;// 订单创建时间
						// Bss/cBSS单号 ,ACTIVE_NO
						String bss_pre_order_id = orderTree.getOrderItemBusiRequests().get(0)
								.getOrderItemExtBusiRequest().getBssOrderId();
						if (StringUtil.isEmpty(bss_pre_order_id)) {
							bss_pre_order_id = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest()
									.getActive_no();
						}
						// 还有一些参数的获取
						Map map = orderInfManager.getOrderInfo(orderId);
						order_status = map.get("order_status") + "";
						create_time = map.get("create_time") + "";
						GoodsInfo orderGoodsInfo = new GoodsInfo();

						orderGoodsInfo.setGoods_num(Integer.parseInt(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.GOODS_NUM)));// 货品数量GOODS_NUM
						orderGoodsInfo.setOffer_price(0);// 单位厘货品应收
						orderGoodsInfo.setProd_code(orderTree.getOrderItemBusiRequests().get(0).getGoods_id());// 商品编码spuid
						orderGoodsInfo.setProd_name(map.get("name") + "");// 商品名称用于订单展示
						List<Goods> products = SpecUtils.getAllOrderProducts(orderId);
						if (null != products && products.size() > 0) {
							orderGoodsInfo.setProd_offer_code(products.get(0).getGoods_id());// 货品编码skuid
							orderGoodsInfo.setProd_offer_name(products.get(0).getName());// 货品名称
						} else {
							orderGoodsInfo.setProd_offer_code("");// 货品编码skuid
							orderGoodsInfo.setProd_offer_name("");// 货品名称
						}
						orderGoodsInfo.setReal_offer_price(0);// 单位厘货品实收
						// 商品价格 单位厘 商品单价
						String goods_amountStr = CommonDataFactory.getInstance().getGoodSpec(orderId, null,
								SpecConsts.PRICE);
						if (!"".equals(goods_amountStr)) {
							Double goods_amount = Double.parseDouble(goods_amountStr) * 1000;
							orderGoodsInfo.setOffer_price(goods_amount.intValue());// 单位厘货品应收
						}
						// 实收金额 单位
						if (!"".equals(map.get("paymoney") + "")) {
							Double pay = Double.parseDouble(map.get("paymoney") + "");
							orderGoodsInfo.setReal_offer_price(pay.intValue());// 单位厘货品实收
						}

						CustInfo orderCustInfo = new CustInfo();
						// 是否实名校验
						orderCustInfo.setIs_real_name(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, "is_real_name"));
						// 客户姓名
						orderCustInfo.setCustomer_name(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERT_CARD_NAME));
						OrderItemProdExtBusiRequest orderItemProdExt = orderTree.getOrderItemBusiRequests().get(0)
								.getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest();
						// 证件类型
						orderCustInfo.setCert_type(orderItemProdExt.getCerti_type());
						// 证件号码
						orderCustInfo.setCert_num(orderItemProdExt.getCert_card_num());
						// 出入境号
                        orderCustInfo.setCert_num2(orderItemProdExt.getCert_num2());
						// 证件地址
						orderCustInfo.setCert_addr(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERT_ADDRESS));
						// 名族
						orderCustInfo.setCert_nation(CommonDataFactory.getInstance().getAttrFieldValue(orderId,
								AttrConsts.CERT_CARD_NATION));
						// 性别
						orderCustInfo.setCert_sex(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERTI_SEX));
						// 客户生日
						orderCustInfo.setCust_birthday(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERT_CARD_BIRTH));
						// 签发机关
						orderCustInfo.setCert_issuedat(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERT_ISSUER));
						// 证件失效时间
						orderCustInfo.setCert_expire_date(CommonDataFactory.getInstance().getAttrFieldValue(orderId,
								AttrConsts.CERT_FAILURE_TIME));
						// 证件生效时间
						orderCustInfo.setCert_effected_date(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERT_EFF_TIME));
						// 客户电话
						orderCustInfo.setCust_tel(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.REFERENCE_PHONE));
						// 客户类型CustomerType
						orderCustInfo.setCustomer_type(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CUSTOMER_TYPE));
						// 客户编号
						orderCustInfo.setCust_id(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CUST_ID));
						// 收入归集集团
						orderCustInfo.setGroup_code(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.GROUP_CODE));
						// 拍照流水------------------------------------------------------------------
						List<OrderAdslBusiRequest> orderAdsl = orderTree.getOrderAdslBusiRequest();
						if (null != orderAdsl && orderAdsl.size() > 0) {
							orderCustInfo.setReq_swift_num(orderAdsl.get(0).getReq_swift_num());
						} else {
							orderCustInfo.setReq_swift_num("");
						}

						PayInfo orderPayInfo = new PayInfo();
						// 是否已支付0 –已支付；-1 – 未支付
						String pay_status = CommonDataFactory.getInstance().getAttrFieldValue(orderId,
								AttrConsts.PAY_STATUS);
						if ("1".equals(pay_status)) {
							orderPayInfo.setPay_status("0");
						} else {
							orderPayInfo.setPay_status("-1");
						}
						// null|null
						String payplatformorderid = (map.get("payplatformorderid") + "");
						if (!StringUtil.isEmpty(payplatformorderid)) {
							String[] paySequ = payplatformorderid.split("|");
							if (paySequ.length == 2) {
								if (!StringUtil.isEmpty(paySequ[0])) {
									// 支付发起流水
									orderPayInfo.setPay_sequ(paySequ[0]);
								}
								if (!StringUtil.isEmpty(paySequ[1])) {
									// 支付返回流水
									orderPayInfo.setPay_back_sequ(paySequ[1]);
								}
							}
						}
						// 支付类型
						orderPayInfo.setPay_type(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PAY_TYPE));
						// 支付方式
						orderPayInfo.setPay_method(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PAY_METHOD));
						orderPayInfo.setRemark("");

						DeveloperInfo orderDeveloperInfo = new DeveloperInfo();
						// 发展点编码
						orderDeveloperInfo.setDevelop_point_code(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, "development_point_code"));
						// 发展点名称
						orderDeveloperInfo.setDevelop_point_name(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, "development_point_name"));
						// 发展人编码
						orderDeveloperInfo.setDevelop_code(CommonDataFactory.getInstance().getAttrFieldValue(orderId,
								AttrConsts.DEVELOPMENT_CODE));
						// 发展人姓名
						orderDeveloperInfo.setDevelop_name(CommonDataFactory.getInstance().getAttrFieldValue(orderId,
								AttrConsts.DEVELOPMENT_NAME));
						// 推荐人号码
						orderDeveloperInfo.setReferee_num(CommonDataFactory.getInstance().getAttrFieldValue(orderId,
								AttrConsts.RECOMMENDED_PHONE));
						// 推荐人名称
						orderDeveloperInfo.setReferee_name(CommonDataFactory.getInstance().getAttrFieldValue(orderId,
								AttrConsts.RECOMMENDED_NAME));
						// 营业县分编码
						if (null != orderAdsl && orderAdsl.size() > 0) {
							orderDeveloperInfo.setCounty_id(orderAdsl.get(0).getCounty_id());
						} else {
							orderDeveloperInfo.setCounty_id("");
						}
						// 渠道类型1
						orderDeveloperInfo.setDeal_office_type(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CHANNEL_TYPE));
						// 渠道分类
						orderDeveloperInfo.setChannelType(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CHANNEL_TYPE));
						// 操作点
						orderDeveloperInfo.setDeal_office_id(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.OUT_OFFICE));
						// 操作员编码
						orderDeveloperInfo.setDeal_operator(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.OUT_OPERATOR));
						// 操作员姓名
						orderDeveloperInfo.setDeal_operator_name("");
						// 操作员号码
						orderDeveloperInfo.setDeal_operator_num("");

						DeliveryInfo orderDeliveryInfo = new DeliveryInfo();
						// 配送方式
						orderDeliveryInfo.setDeli_mode(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, "shipping_type"));
						// 配送时间类型
						orderDeliveryInfo.setDeli_time_mode(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, "shipping_time"));
						// 收货人姓名
						orderDeliveryInfo.setDeli_name(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SHIP_NAME));
						// 收货人电话
						orderDeliveryInfo.setDeli_phone(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.REFERENCE_PHONE));
						// 收货省
						orderDeliveryInfo.setDeli_province(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PROVINCE_CODE));
						// 收货地市
						orderDeliveryInfo.setDeli_city(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CITY_CODE));
						// 收货区县
						orderDeliveryInfo.setDeli_district(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.DISTRICT));
						// 收货人邮编
						orderDeliveryInfo.setDeli_code(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SHIP_ZIP));
						// 收货地址
						orderDeliveryInfo.setDeli_addr(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SHIP_ADDR));
						// 物流公司
						orderDeliveryInfo.setShipping_company(CommonDataFactory.getInstance().getAttrFieldValue(orderId,
								AttrConsts.SHIPPING_COMPANY_NAME));
						// 物流单号
						orderDeliveryInfo.setLogi_no(
								CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.LOGI_NO));

						OrderDetail orderQueryResp = new OrderDetail();
						orderQueryResp.setOrder_id(orderId);
						orderQueryResp.setBus_num(bus_num);
						orderQueryResp.setOut_order_id(out_order_id);
						orderQueryResp.setSource_from(source_from);
						orderQueryResp.setOrder_status(order_status);
						orderQueryResp.setOrder_status_n(order_status_n);
						orderQueryResp.setOrder_city_code(order_city_code);
						orderQueryResp.setCounty_id(county_id);
						orderQueryResp.setCreate_time(create_time);
						orderQueryResp.setBss_pre_order_id(bss_pre_order_id);

						orderQueryResp.setOrder_goods_info(orderGoodsInfo);
						orderQueryResp.setOrder_cust_info(orderCustInfo);
						orderQueryResp.setOrder_pay_info(orderPayInfo);
						orderQueryResp.setOrder_developer_info(orderDeveloperInfo);
						orderQueryResp.setOrder_delivery_info(orderDeliveryInfo);
						query_resp.add(orderQueryResp);
					} else {
						resp.setResp_code("1");
						resp.setResp_msg(orderId + "异常");
						return resp;
					}
				} // for
				resp.setResp_code("0");
				resp.setResp_msg("查询成功");
			} else {
				resp.setResp_code("1");
				resp.setResp_msg("query_info节点为空");
			}
		} catch (Exception e) {
			resp.setResp_code("1");
			resp.setResp_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 工单相关订单列表查询接口
	 * 
	 * @author 宋琪
	 * @date 2018年1月3日
	 */
	@Override
	public OrderListByWorkQueryResp queryOrderListByWork(OrderListByWorkQueryReq requ) throws Exception {
		return ordWorkManager.queryOrderListByWork(requ);
	}

	@Override
	public WorkOrderMixOrdUpdateResp doWorkOrderMixOrdUpdate(WorkOrderMixOrdUpdateReq requ) throws Exception {
		WorkOrderMixOrdUpdateResp resp = new WorkOrderMixOrdUpdateResp();

		try {
			resp = orderInfManager.doWorkOrderMixOrdUpdate(requ);
		} catch (Exception e) {
			resp.setResp_code("1");
			resp.setResp_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "用户缴费接口（支持4G用户）", summary = "用户缴费接口（支持4G用户）")
	public AmountPayResp paymentByBSS(AmountPayReq req) throws Exception {
		AmountPayResp resp = new AmountPayResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2Map(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
			resp = (AmountPayResp) caller.invoke("ecaop.trades.subscription.comm.amount.pay", param);
		} catch (Exception e) {// 调用接口失败
			resp.setError_code("1");
			resp.setError_msg("异常错误：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}
		return resp;

	}

	@Override
	@SuppressWarnings("rawtypes")
	@ZteSoftCommentAnnotation(type = "method", desc = "意向单查询接口", summary = "意向单查询接口")
	public IntentOrderQueryResp intentOrderQuery(IntentOrderQueryReq req) throws Exception {
		IntentOrderQueryResp resp = new IntentOrderQueryResp();
//		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
//		String intentOrder_sql = cacheUtil.getConfigInfo("yxd_select_sql");
//		
//		intentOrder_sql += " left join (select row_number() OVER( PARTITION BY  b.order_id order by b.create_time desc) as rw,b.* from es_work_order b) w on w.rw=1 and w.order_id=t.order_id and w.type in ('07','08') left join es_goods eg on eg.goods_id = t.goods_id and eg.type = 'goods' " 
//		+ " where t.source_from = '"
//		+ ManagerUtils.getSourceFrom() + "' ";
		String intentOrder_sql = " select t.top_share_userid,t.top_share_num,t.offer_eff_type,t.pay_tag,t.intent_order_id,t.create_time as intent_order_time,t.mainNumber as mainNumber,eg.cat_id as cat_id,"
				+ " t.status,t.remark,t.order_id,t.ship_name,t.ship_tel,t.ship_addr, "
		        + " t.develop_point_code, t.develop_point_name,t.develop_code, t.develop_name, t.develop_phone, t.channeltype, t.county_id,t.deal_operator, "
				+ " t.deal_operator_name,  t.deal_operator_num,   t.deal_office_id,  t.deal_office_type, t.referee_num, t.referee_name, "
		        + " to_char(substr(t.chip_illumination,0,4000))  as chip_illumination1, to_char(substr(t.chip_illumination,4001,4000))  as chip_illumination2, to_char(substr(t.chip_illumination,8001,4000))  as chip_illumination3, to_char(substr(t.chip_illumination,12001,4000))  as chip_illumination4, to_char(substr(t.chip_illumination,16001,4000))  as chip_illumination5,"
				+ " '' as deal_user,'' as deal_user_name,w.operator_num as deal_user_phone,t.intent_finish_time,t.is_real_name,t.customer_name,t.cert_type,t.cert_num,t.cert_num2 as cert_num2,t.cert_addr,t.cert_nation,t.cert_sex,t.cust_birthday,t.cert_issuedat,t.cert_expire_date,t.cert_effected_date,t.cust_tel,t.customer_type,t.cust_id,t.group_code,t.req_swift_num,t.check_type,t.acc_nbr,t.contract_month,lhscheme_id,t.pre_fee,t.advancepay,t.classid,t.lowcostpro,timedurpro,t.goods_id as prod_code,t.goods_name as prod_name,t.prod_offer_code,t.prod_offer_name,t.goods_num,t.offer_price,t.real_offer_price,t.is_real_name,t.customer_name,dbms_lob.substr(t.is_no_modify) AS is_no_modify,t.share_svc_num,t.market_user_id,t.seed_user_id "
				+" ,t.is_online_order  "
				+ " from es_order_intent t "
				+ " left join (select row_number() OVER( PARTITION BY  b.order_id order by b.create_time desc) as rw,b.* from es_work_order b) w on w.rw=1 and w.order_id=t.order_id and w.type in ('07','08') left join es_goods eg on eg.goods_id = t.goods_id and eg.type = 'goods' " + " where t.source_from = '"
				+ ManagerUtils.getSourceFrom() + "' ";
		if (!StringUtils.isEmpty(req.getIntent_order_id())) {
			intentOrder_sql += " and t.intent_order_id = '" + req.getIntent_order_id() + "' ";
		}
		if (!StringUtils.isEmpty(req.getOrder_id())) {
			intentOrder_sql += " and t.order_id = '" + req.getOrder_id() + "' ";
		}
		if (!StringUtils.isEmpty(req.getShip_tel())) {
			intentOrder_sql += " and t.ship_tel = '" + req.getShip_tel() + "' ";
		}
		if (!StringUtils.isEmpty(req.getReferee_num())) {
			intentOrder_sql += " and t.referee_num = '" + req.getReferee_num() + "' ";
		}
		if (!StringUtils.isEmpty(req.getStart_date())) {
			intentOrder_sql += " and t.create_time >= to_date('" + req.getStart_date() + "','yyyy-mm-dd hh24:mi:ss') ";
		}
		if (!StringUtils.isEmpty(req.getEnd_date())) {
			intentOrder_sql += " and t.create_time <= to_date('" + req.getEnd_date() + "','yyyy-mm-dd hh24:mi:ss') ";
		}
		if (!StringUtils.isEmpty(req.getIntent_type())) {
			intentOrder_sql += " and t.status = '" + req.getIntent_type() + "' ";
		}
		if (!StringUtils.isEmpty(req.getOperator_num())) {
			intentOrder_sql += " and w.order_id=t.order_id and w.type in ('07','08') and w.operator_num='" + req.getOperator_num()
					+ "' ";
		}
		if (!StringUtils.isEmpty(req.getWork_order_status())) {
			intentOrder_sql += " and w.status='" + req.getWork_order_status() + "' ";
		} 
		
		if(StringUtils.isNotEmpty(req.getSeed_user_id())) {
			intentOrder_sql += " and t.seed_user_id='" + req.getSeed_user_id() + "' ";
		}
		
		if(StringUtils.isNotEmpty(req.getMarket_user_id())) {
			intentOrder_sql += " and t.market_user_id ='"+req.getMarket_user_id() + "' ";
		}
		
		if(StringUtils.isNotEmpty(req.getShare_svc_num())) {
			intentOrder_sql += " and t.share_svc_num = '"+req.getShare_svc_num()+ "' ";
		}
		if(StringUtils.isNotEmpty(req.getCert_num())) {
			intentOrder_sql += " and t.cert_num = '"+req.getCert_num()+ "' ";
		}
		if(!StringUtils.isEmpty(req.getCat_id())){//新增商品类型 sgf
		    intentOrder_sql +=" and eg.cat_id in('" + req.getCat_id().replace(",", "','") + "')";
		}
		if(!StringUtils.isEmpty(req.getOrder_type())){//新增订单类型
		    intentOrder_sql +=" and t.order_type = '"+req.getOrder_type()+ "' ";
		}
		if(!StringUtils.isEmpty(req.getProd_code())){//新增商品编码
		    intentOrder_sql +=" and eg.goods_id in('" + req.getProd_code().replace(",", "','") + "')";
		}
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		List<Map<String,Object>> list = baseDaoSupport.queryForList(intentOrder_sql);
		List<IntentOrderInfo> resultList = Lists.newArrayList();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map result = list.get(i);
				
				String order_id = MapUtils.getString(result, "order_id");
				String intent_order_id = MapUtils.getString(result, "intent_order_id");
				String intent_order_time = MapUtils.getString(result, "intent_order_time");
				String mainNumber = MapUtils.getString(result, "mainNumber");
				String cat_id = MapUtils.getString(result, "cat_id");//商品小类
				String status = MapUtils.getString(result, "status");
				String remark = MapUtils.getString(result, "remark");
				String ship_name = MapUtils.getString(result, "ship_name");
				String ship_tel = MapUtils.getString(result, "ship_tel");
				String ship_addr = MapUtils.getString(result, "ship_addr");
				String referee_num = MapUtils.getString(result, "referee_num");
				String referee_name = MapUtils.getString(result, "referee_name");
				String deal_user = MapUtils.getString(result, "deal_user");
				String deal_user_name = MapUtils.getString(result, "deal_user_name");
				String deal_user_phone = MapUtils.getString(result, "deal_user_phone");
				String intent_finish_time = MapUtils.getString(result, "intent_finish_time");
				String seed_user_id = MapUtils.getString(result, "seed_user_id");
				String market_user_id = MapUtils.getString(result, "market_user_id");
				String share_svc_num = MapUtils.getString(result, "share_svc_num");
				String is_no_modify = MapUtils.getString(result, "is_no_modify");
				
				String acc_nbr = MapUtils.getString(result, "acc_nbr");
				String contract_month = MapUtils.getString(result, "contract_month");
				String lhscheme_id = MapUtils.getString(result, "lhscheme_id");
				String pre_fee = MapUtils.getString(result, "pre_fee");
				String advancePay = MapUtils.getString(result, "advancePay");
				String classId = MapUtils.getString(result, "classId");
				String lowCostPro = MapUtils.getString(result, "lowCostPro");
				String timeDurPro = MapUtils.getString(result, "timeDurPro"); 
				NiceInfoVO nice_info = new NiceInfoVO.Builder().advancePay(advancePay).classId(classId).lhschemeId(lhscheme_id).lowCostPro(lowCostPro)
						.preFee(pre_fee).timeDurPro(timeDurPro).build();
				PhoneInfo phone_info = new PhoneInfo();
				phone_info.setNice_info(nice_info);
				phone_info.setAcc_nbr(acc_nbr);
				phone_info.setContract_month(contract_month);
				phone_info.setMainNumber(mainNumber);
				GoodsInfo goods_info = new GoodsInfo();
				String prod_code = MapUtils.getString(result, "prod_code");
				String prod_name = MapUtils.getString(result, "prod_name");
				String prod_offer_code = MapUtils.getString(result, "prod_offer_code");
				String prod_offer_name = MapUtils.getString(result, "prod_offer_name");
				int goods_num = MapUtils.getIntValue(result, "goods_num");
				int offer_price = MapUtils.getIntValue(result, "offer_price");
				int real_offer_price = MapUtils.getIntValue(result, "offer_price");
				goods_info.setProd_code(prod_code);
				goods_info.setGoods_num(goods_num);
				goods_info.setProd_name(prod_name);
				goods_info.setProd_offer_code(prod_offer_code);
				goods_info.setProd_offer_name(prod_offer_name);
				goods_info.setReal_offer_price(real_offer_price);
				goods_info.setOffer_price(offer_price);
				goods_info.setCat_id(cat_id);//查询返回商品小类
				
				CustInfo cust_info = new CustInfo();
				String is_real_name = MapUtils.getString(result, "is_real_name");
				String customer_name = MapUtils.getString(result, "customer_name");
				String cert_type = MapUtils.getString(result, "cert_type");
				String cert_num = MapUtils.getString(result, "cert_num");
				String cert_addr = MapUtils.getString(result, "cert_addr");
				String cert_nation = MapUtils.getString(result, "cert_nation");
				String cert_sex = MapUtils.getString(result, "cert_sex");
				String cust_birthday = MapUtils.getString(result, "cust_birthday");
				String cert_issuedat = MapUtils.getString(result, "cert_issuedat");
				String cert_expire_date = MapUtils.getString(result, "cert_expire_date");
				String cert_effected_date = MapUtils.getString(result, "cert_effected_date");
				String cust_tel = MapUtils.getString(result, "cust_tel");
				String customer_type = MapUtils.getString(result, "customer_type");
				String cust_id = MapUtils.getString(result, "cust_id");
				String group_code = MapUtils.getString(result, "group_code");
				String req_swift_num = MapUtils.getString(result, "req_swift_num");
				String check_type = MapUtils.getString(result, "check_type");
                String cert_num2 = MapUtils.getString(result, "cert_num2");
                String chip_illumination = MapUtils.getString(result, "chip_illumination1") + MapUtils.getString(result, "chip_illumination2") + MapUtils.getString(result, "chip_illumination3") + MapUtils.getString(result, "chip_illumination4") + MapUtils.getString(result, "chip_illumination5");
				cust_info.setIs_real_name(is_real_name);
				cust_info.setCustomer_name(customer_name);
				cust_info.setCert_type(cert_type);
				cust_info.setCert_num(cert_num);
				cust_info.setCert_addr(cert_addr);
				cust_info.setCert_nation(cert_nation);
				cust_info.setCert_sex(cert_sex);
				cust_info.setCust_birthday(cust_birthday);
				cust_info.setCert_issuedat(cert_issuedat);
				cust_info.setCert_expire_date(cert_expire_date);
				cust_info.setCert_effected_date(cert_effected_date);
				cust_info.setCust_tel(cust_tel);
				cust_info.setCustomer_type(customer_type);
				cust_info.setCust_id(cust_id);
				cust_info.setGroup_code(group_code);
				cust_info.setReq_swift_num(req_swift_num);
				cust_info.setCheck_type(check_type);
				cust_info.setCert_num2(cert_num2);
				cust_info.setChip_illumination(chip_illumination);
				DeveloperInfo developerInfo = new DeveloperInfo();
				String develop_point_code = MapUtils.getString(result, "develop_point_code");
                String develop_point_name = MapUtils.getString(result, "develop_point_name");
                String develop_code = MapUtils.getString(result, "develop_code");
                String develop_name = MapUtils.getString(result, "develop_name");
                String develop_phone = MapUtils.getString(result, "develop_phone");
                String channeltype = MapUtils.getString(result, "channeltype");
                String county_id = MapUtils.getString(result, "county_id");
                String deal_operator = MapUtils.getString(result, "deal_operator");
                String deal_operator_name = MapUtils.getString(result, "deal_operator_name");
                String deal_operator_num = MapUtils.getString(result, "deal_operator_num");
                String deal_office_id = MapUtils.getString(result, "deal_office_id");
                String deal_office_type = MapUtils.getString(result, "deal_office_type");
                developerInfo.setCounty_id(county_id);
                developerInfo.setDevelop_point_code(develop_point_code);
                developerInfo.setDevelop_point_name(develop_point_name);
                developerInfo.setChannelType(channeltype);
                developerInfo.setDeal_office_id(deal_office_id);
                developerInfo.setDeal_office_type(deal_office_type);
                developerInfo.setDeal_operator(deal_operator);
                developerInfo.setDeal_operator_num(deal_operator_num);
                developerInfo.setDevelop_code(develop_code);
                developerInfo.setDevelop_name(develop_name);
                developerInfo.setReferee_name(referee_name);
                developerInfo.setReferee_num(referee_num);
                developerInfo.setDevelop_phone(develop_phone);
                developerInfo.setDeal_operator_name(deal_operator_name);
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				OrderInfo order_info = null;
				IntentOrderInfo intentOrderInfo = null ;
				if (orderTree == null) {
					order_info = new OrderInfo();
				} else {
					order_info = new OrderInfo();
					String busi_order_id = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest()
							.getBssOrderId();
					if (!StringUtils.isEmpty(busi_order_id) && busi_order_id != "null") {
						order_info.setBusi_order_id(busi_order_id);
					}
					String goodsName = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "GoodsName");
					order_info.setGoods_name(goodsName);
					String office_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE);
					if (!StringUtils.isEmpty(office_id) && busi_order_id != "null") {
						order_info.setOffice_id(office_id);
					}
					String operator_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
							AttrConsts.OUT_OPERATOR);
					if (!StringUtils.isEmpty(office_id) && busi_order_id != "null") {
						order_info.setOperator_id(operator_id);
					}
					String order_finish_time_sql = " select t.l_end_time from es_order_stats_tache t where t.order_id='"
							+ order_id + "' and t.source_from='" + ManagerUtils.getSourceFrom() + "' ";
					String order_finish_time = baseDaoSupport.queryForString(order_finish_time_sql);
					if (!StringUtils.isEmpty(order_finish_time) && order_finish_time != "null") {
						order_info.setOrder_finish_time(order_finish_time);
					}
					order_info.setOrder_id(order_id);
					String order_status = "";
					String bss_refund_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
							AttrConsts.BSS_REFUND_STATUS);
					String refund_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
							AttrConsts.REFUND_DEAL_TYPE);
					String new_refund_status = CommonDataFactory.getInstance().getOrderTree(order_id)
							.getOrderExtBusiRequest().getRefund_status();
					String zb_refund_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
							AttrConsts.ZB_REFUND_STATUS);
					OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(order_id);
					String trace_id = tree.getOrderExtBusiRequest().getFlow_trace_id();
					String refund_flag = orderInfManager.checkRefund(order_id, trace_id);
					// logger.info("当前环节状态"+order_id+","+trace_id+";refund_status:"+refund_status+";bss_refund_status:"+bss_refund_status);
					if (EcsOrderConsts.REFUND_STATUS_01.equals(new_refund_status)) {
						order_status = "10";
					} else if (EcsOrderConsts.REFUND_STATUS_02.equals(new_refund_status)) {
						order_status = "10";
					} else if (EcsOrderConsts.REFUND_STATUS_03.equals(new_refund_status)) {
						order_status = "10";
					} else if (EcsOrderConsts.REFUND_STATUS_07.equals(new_refund_status)) {
						order_status = "10";
					} else if (EcsOrderConsts.REFUND_STATUS_08.equals(new_refund_status)) {
						order_status = "08";
					} else if ("01".equals(refund_status) && !StringUtils.isEmpty(refund_status)
							&& !StringUtils.isEmpty(refund_flag) && StringUtil.equals(refund_flag, "yes")) {
						order_status = "09";
					} else if (EcsOrderConsts.DIC_ORDER_NODE_M.equals(trace_id) || "1".equals(bss_refund_status)) {
						order_status = "07";
					} else if (EcsOrderConsts.DIC_ORDER_NODE_T.equals(trace_id) || "02".equals(refund_status)
							|| "1".equals(zb_refund_status)) {
						order_status = "06";
					} else if (EcsOrderConsts.DIC_ORDER_NODE_P.equals(trace_id)) {
						order_status = "02";
					} else if (EcsOrderConsts.DIC_ORDER_NODE_D.equals(trace_id)
							|| EcsOrderConsts.DIC_ORDER_NODE_B.equals(trace_id)) {
						order_status = "03";
					} else if (EcsOrderConsts.DIC_ORDER_NODE_L.equals(trace_id)) {
						order_status = "04";
					} else if (EcsOrderConsts.DIC_ORDER_NODE_J.equals(trace_id)) {
						order_status = "05";
					} else {
						order_status = "01";
					}
					order_info.setOrder_status(order_status);
					order_info.setOrder_time(orderTree.getOrderBusiRequest().getCreate_time());
					/*
					 * String member_id =
					 * orderTree.getOrderBusiRequest().getMember_id();// select *
					 * from es_member where member_id = '' Map buyInfoMap =
					 * this.ordFlowManager.getBuyInfoByMemberId(member_id);
					 */
					String customer_nameSub = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
							AttrConsts.PHONE_OWNER_NAME);
					if (!StringUtils.isEmpty(customer_nameSub) && customer_nameSub != "null") {
						CustInfo cust_infoSub = new CustInfo();
						cust_infoSub.setCustomer_name(customer_nameSub);
						order_info.setCust_info(cust_infoSub);
					}
				}
				
				//add by cqq 20181226 线上线下订单
				String is_Online_Order = MapUtils.getString(result, "is_online_order");
				String top_share_userid = MapUtils.getString(result, "top_share_userid");
				String top_share_num = MapUtils.getString(result, "top_share_num");
				String offer_eff_type = MapUtils.getString(result, "offer_eff_type");
				String pay_tag = MapUtils.getString(result, "pay_tag");
				
				intentOrderInfo =  new IntentOrderInfo.Builder().custInfo(cust_info).dealUser(deal_user_phone).dealUserName(deal_user_name).dealUserPhone(deal_user_phone)
						.goodsInfo(goods_info).intentFinishTime(intent_finish_time).intentOrderId(intent_order_id).intentOrderTime(intent_order_time).orderId(order_id).orderInfo(order_info)
						.phoneInfo(phone_info).refereeName(referee_name).refereeNum(referee_num).shipAddr(ship_addr).shipName(ship_name).shipTel(ship_tel).status(status)
						.remark(remark).dealUser(deal_user).seedUserId(seed_user_id).marketUserId(market_user_id).isNoModify(is_no_modify).shareSvcNum(share_svc_num).developerInfo(developerInfo)
						.isOnlineOrder(is_Online_Order).top_share_userid(top_share_userid).top_share_num(top_share_num).offer_eff_type(offer_eff_type).pay_tag(pay_tag).build();//build()//add by cqq 20181226
				resultList.add(intentOrderInfo);
			}
			resp.setResp_code("0");
			resp.setResp_msg("查询成功！");
			resp.setIntent_order_info(resultList);
		} else {
			resp.setResp_code("1");
			resp.setResp_msg("未查询到意向单");
		}

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "冰淇淋宽带预提交", summary = "冰淇淋宽带预提交")
	public CommAopApplyRsp doAopBrdOpenApp(AopBrdOpenAppReq req) throws ApiBusiException {
		CommAopApplyRsp rsp = new CommAopApplyRsp();

		try {
			long start = System.currentTimeMillis();
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();

			start = System.currentTimeMillis();

			BeanUtils.bean2MapForAOP(param, req);

			logger.info(req.getNotNeedReqStrOrderId() + "调用开户处理申请接口【doAopBrdOpenApp】封装报文耗时：" + (System.currentTimeMillis() - start));
			

			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = EcsOrderConsts.AOP_BRD_OPEN_APP;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);

			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);

			start = System.currentTimeMillis();

			// 调用接口
			rsp = (CommAopApplyRsp) caller.invoke(opcode, param);
			
			logger.info(req.getNotNeedReqStrOrderId() + "调用开户处理申请接口【doAopBrdOpenApp】[自服务接口出去]耗时：" + (System.currentTimeMillis() - start));

		} catch (Exception e) {
			rsp.setError_code("-9999");
			rsp.setError_msg(e.getMessage());
			e.printStackTrace();
		}

		return rsp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "冰淇淋宽带正式提交", summary = "冰淇淋宽带正式提交")
	public CommAopSubmitRsp doAopBrdOpenSub(AopBrdOpenSubReq req) throws ApiBusiException {
		CommAopSubmitRsp rsp = new CommAopSubmitRsp();

		try {
			long start = System.currentTimeMillis();
			CommCaller caller = new CommCaller();
			Map<String, Object> param = new HashMap<String, Object>();

			start = System.currentTimeMillis();

			BeanUtils.bean2MapForAOP(param, req);

			logger.info(req.getNotNeedReqStrOrderId() + "调用开户处理申请接口【doAopBrdOpenApp】封装报文耗时：" + (System.currentTimeMillis() - start));
			param.put("ord_id", req.getNotNeedReqStrOrderId());

			String opcode = EcsOrderConsts.AOP_BRD_OPEN_SUB;
			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);

			if (!StringUtils.isEmpty(bizkey))
				param.put("bizkey", bizkey);

			start = System.currentTimeMillis();

			// 调用接口
			rsp = (CommAopSubmitRsp) caller.invoke(opcode, param);

			logger.info(req.getNotNeedReqStrOrderId() + "调用开户处理申请接口【doAopBrdOpenApp】[自服务接口出去]耗时：" + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			rsp.setError_code("-9999");
			rsp.setError_msg(e.getMessage());
			e.printStackTrace();
		}

		return rsp;
	}

	/**
	 * 将异步通知接口调用信息保存在接口调用表中，由后台task扫描处理失败的记录
	 * 
	 * @param req
	 * @param errorMsg
	 */
	private void saveOrderResultNotifyInfo(OrderResultNotifyReq req, String errorMsg, boolean exist) {

		// 保存订单信息到接口调用表
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String createDate = format.format(now);

		IDaoSupport dao = SpringContextHolder.getBean("baseDaoSupport");

		OrderResultNotifyVo data = new OrderResultNotifyVo();

		data.setOrder_id(req.getOrder_id());
		data.setOut_id(req.getOut_order_id());
		data.setResult_code(req.getResult_code());
		data.setResult_msg(req.getResult_msg());
		data.setNotify_method(req.getNotify_method());

		if (StringUtil.isEmpty(errorMsg)) {
			// 设置通知状态为1-已通知
			data.setNotify_status(1);
			// 设置通知时间
			data.setNotify_date(createDate);
		} else {
			// 设置通知状态为0-未通知
			data.setNotify_status(0);
		}

		data.setNotify_count(1);
		data.setError_msg(errorMsg);
		data.setCreate_date(createDate);
		data.setSource_from("ECS");

		if (exist) {
			// 接口调用表中已有数据
			String updateSql = "";
			Map<String, Object> args = new HashMap<String, Object>();

			args.put("order_id", data.getOrder_id());
			args.put("out_id", data.getOut_id());
			args.put("result_code", data.getResult_code());
			args.put("result_msg", data.getResult_msg());

			args.put("notify_method", data.getNotify_method());
			args.put("notify_status", data.getNotify_status());
			args.put("error_msg", errorMsg);

			if (StringUtil.isEmpty(errorMsg)) {
				// 接口调用成功，更新通知时间
				updateSql = "update es_order_result_notify a "
						+ "set a.out_id=:out_id ,a.result_code=:result_code ,a.result_msg=:result_msg , a.notify_method=:notify_method , "
						+ "a.notify_status=:notify_status ,a.notify_count=(a.notify_count+1) ,"
						+ "a.notify_date=sysdate, a.error_msg=:error_msg " + "WHERE a.order_id=:order_id ";
			} else {
				// 接口调用失败，不更新通知时间
				updateSql = "update es_order_result_notify a "
						+ "set a.out_id=:out_id ,a.result_code=:result_code ,a.result_msg=:result_msg , a.notify_method=:notify_method , "
						+ "a.notify_status=:notify_status ,a.notify_count=(a.notify_count+1) ,"
						+ "a.error_msg=:error_msg " + "WHERE a.order_id=:order_id ";
			}

			dao.update(updateSql, args);
		} else {
			// 接口调用表中没有数据
			dao.insert("es_order_result_notify", data);
		}
	}

	/**
	 * 将订单改为退单
	 * 
	 * @param req
	 */
	private void cancelOrder4wechat(OrderResultNotifyReq req) {
		String order_id = req.getOrder_id();
		String dealDesc = "微信服务号业务办理失败，退单";
		String returnedReasonCode = null;

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

		if (orderTree == null || orderTree.getOrderExtBusiRequest() == null) {
			return;
		}

		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();

		if (EcsOrderConsts.REFUND_STATUS_08.equals(orderExt.getRefund_status())// 退单申请
				|| EcsOrderConsts.REFUND_STATUS_01.equals(orderExt.getRefund_status())// 退单确认通过
				|| EcsOrderConsts.REFUND_STATUS_02.equals(orderExt.getRefund_status())// BSS返销
				|| EcsOrderConsts.REFUND_STATUS_03.equals(orderExt.getRefund_status())) {// ESS返销
			// 订单已退单或退单申请，不做处理
			return;
		}

		// 业务办理失败的，将订单状态改为退单
		// 记录退单原因
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
				new String[] { AttrConsts.REFUND_DESC, AttrConsts.RETURNED_REASON_CODE },
				new String[] { EcsOrderConsts.REFUND_APPLY_DESC + "" + dealDesc, returnedReasonCode });

		// 标记为非商城主动发起的退单
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ZB_REFUND_STATUS },
				new String[] { EcsOrderConsts.NO_DEFAULT_VALUE });

		// 获取审核模式，并保存
		String refundMode = AttrUtils.getInstance().getRefundModeByRule(order_id);
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.REFUND_AUDIT_MODE },
				new String[] { refundMode });

		// 获取方案信息
		Map m = AttrUtils.getInstance().getRefundPlanInfo(order_id);
		String plan_mode = m.get("plan_id").toString();
		String rule_mode = m.get("app_rule_id").toString();

		// 清除之前的日志
		CommonDataFactory.getInstance().delRuleExeLog(plan_mode, null, order_id);

		// 修改订单状态为退单中的状态
		OrderBusiRequest orderBusiReq = orderTree.getOrderBusiRequest();
		orderBusiReq.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_12);
		orderBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiReq.store();

		ecsOrdManager.unLock(order_id);

		TacheFact fact = new TacheFact();
		fact.setOrder_id(orderTree.getOrder_id());
		RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(plan_mode, rule_mode, fact, true, true,
				EcsOrderConsts.DEAL_FROM_PAGE);

		if (resp != null && "0".equals(resp.getError_code())) {
			String handler_comments = ZjEcsOrderConsts.REFUND_APPLY_DESC + "" + dealDesc;
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.REFUND_DESC },
					new String[] { handler_comments });
			// 写日志
			OrderHandleLogsReq logReq = new OrderHandleLogsReq();
			String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
			String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			logReq.setOrder_id(order_id);
			logReq.setFlow_id(flow_id);
			logReq.setFlow_trace_id(flowTraceId);
			logReq.setComments(handler_comments);
			logReq.setHandler_type(Const.ORDER_HANDLER_TYPE_RETURNED);
			logReq.setType_code(EcsOrderConsts.REFUND_STATUS_08);
			// logReq.setOp_id(user.getUserid());
			// logReq.setOp_name(user.getUsername());
			this.ordFlowManager.insertOrderHandLog(logReq);
		}
	}
	
	private String doWeChatResultNotify(OrderResultNotifyReq req,CommCaller caller,String method,Map<String, Object> param,
			OrderResultNotifyRsp rsp) throws ApiBusiException {
		// 接口调用失败信息
		String errorMsg = "";
				
		try {
			BeanUtils.bean2Map(param, req);
			param.remove("notify_method");
			rsp = (OrderResultNotifyRsp) caller.invoke(method, param);
		} catch (Exception e) {
			rsp.setError_code("-9999");
			rsp.setError_msg("调用接口异常:" + e.getMessage());
			e.printStackTrace();
		}
		
		if (rsp == null) {
			errorMsg = "调用接口异常:能力开放平台返回的信息为空";
		} else if (!"200".equals(rsp.getError_code())) {
			// 访问能力开发平台失败
			errorMsg = "访问能力开发平台异常:" + rsp.getError_msg();
		} else if (!"00000".equals(rsp.getRes_code())) {
			// 能力开发平台返回失败
			errorMsg = "能力开发平台异常:" + rsp.getRes_message();
		} else {
			WechatRetBean webchatBean = rsp.getResultMsg();
			
			if (null == webchatBean) {
				// 微信公众号平台返回信息为空
				errorMsg = "调用接口异常:微信公众号平台返回的信息为空";
			} else if (!"0".equals(webchatBean.getResp_code())) {
				// 微信公众号平台返回失败
				errorMsg = "外围平台返回：" + webchatBean.getResp_msg();
			}
		}
		
		return errorMsg;
	}
	
	private String doZteResultNotify(OrderResultNotifyReq req,CommCaller caller,String method,Map<String, Object> param,
			OrderResultNotifyRsp rsp) throws ApiBusiException {
		// 接口调用失败信息
		String errorMsg = "";
		OrderResultNottifyRspZte zte = new OrderResultNottifyRspZte();
		
		try {
			req.setResult_type("02");
			BeanUtils.bean2Map(param, req);
			param.remove("notify_method");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_result_notify_req", param);
			zte = (OrderResultNottifyRspZte) caller.invoke(method, map);
		} catch (Exception e) {
			zte.setError_code("-9999");
			zte.setError_msg("调用接口异常:" + e.getMessage());
			e.printStackTrace();
		}
		
		// 返回信息处理
		if (zte == null) {
			errorMsg = "调用接口异常:能力开放平台返回的信息为空";
		} else if (!"200".equals(zte.getError_code())) {
			// 访问能力开发平台失败
			errorMsg = "访问能力开发平台异常:" + zte.getError_msg();
		} else if (!"00000".equals(zte.getRes_code())) {
			// 能力开发平台返回失败
			errorMsg = "能力开发平台异常:" + zte.getRes_message();
		} else {
			OrderResultNotifyResp  orderResultNotifyResp= zte.getResultMsg().getOrder_resultMsg_notify_resp();
			
			if (null == orderResultNotifyResp) { 
				// 中兴平台返回信息为空
				errorMsg = "调用接口异常:中兴平台返回的信息为空";
			} else {
				rsp.setError_code(zte.getError_code());
				rsp.setError_msg(zte.getError_msg());
				rsp.setRes_code(zte.getRes_code());
				rsp.setRes_message(zte.getRes_message());
				
				WechatRetBean webchatBean = new WechatRetBean();
				webchatBean.setResp_code(orderResultNotifyResp.getResp_code());
				webchatBean.setResp_msg(orderResultNotifyResp.getResp_msg());
				
				rsp.setResultMsg(webchatBean);
				
				if (!"0".equals(orderResultNotifyResp.getResp_code())) {
					// 中兴公众号平台返回失败
					errorMsg = "外围平台返回：" + orderResultNotifyResp.getResp_msg();
				}
			} 
		}
		
		return errorMsg;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public OrderResultNotifyRsp doOrderResultNotify(OrderResultNotifyReq req) throws ApiBusiException {
		OrderResultNotifyRsp rsp = new OrderResultNotifyRsp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		
		String method = req.getNotify_method();
		if (StringUtil.isEmpty(method)) {
			rsp.setError_code("999");
			rsp.setError_msg("未沉淀异步通知方法result_url");

			return rsp;
		}

		IDaoSupport dao = SpringContextHolder.getBean("baseDaoSupport");

		// 查询接口调用表中有无数据
		String querySql = "SELECT a.order_id,a.notify_status FROM es_order_result_notify a WHERE a.order_id='"
				+ req.getOrder_id() + "'";
		List<Map> existMap = dao.queryForList(querySql);

		boolean exist = false;

		if (existMap != null && existMap.size() > 0) {
			exist = true;

			String notify_status = String.valueOf(existMap.get(0).get("notify_status"));
			if ("1".equals(notify_status)) {
				// 接口调用表中的状态为已通知，则直接返回成功信息
				rsp.setError_code("200");
				rsp.setRes_code("00000");

				WechatRetBean webchatBean = new WechatRetBean();
				webchatBean.setResp_code("0");
				rsp.setResultMsg(webchatBean);

				return rsp;
			}
		}
		
		// 接口调用失败信息
		String errorMsg = "";
		
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(req.getOrder_id());
		if (orderTree != null && orderTree.getOrderExtBusiRequest() != null) {
			OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
			
			if ("10083".equals(orderExt.getOrder_from())) {
				errorMsg = this.doWeChatResultNotify(req, caller, method, param, rsp);
			} else {
				errorMsg = this.doZteResultNotify(req, caller, method, param, rsp);
			}
		}

		this.saveOrderResultNotifyInfo(req, errorMsg, exist);

		if (StringUtil.isEmpty(errorMsg) && "-1".equals(req.getResult_code())) {
			// 异步通知成功且工单异常的，将订单改为退单
			this.cancelOrder4wechat(req);
		}

		return rsp;
	}

	// @Override
	// public AppOrderCancelResp cancelOrderByRequ(AppOrderCancelReq requ)
	// throws Exception {
	// // TODO Auto-generated method stub
	// String order_id = requ.getOrder_id();
	// AppOrderCancelResp resp = new AppOrderCancelResp();
	//
	// //bss单号为空，则未进行预提交，直接修改订单状态为退单
	// //bss单号不为空，且未调用正式提交接口或正式提交接口出错，先调用B侧撤单接口，在修改订单状态
	// OrderTreeBusiRequest orderTree =
	// CommonDataFactory.getInstance().getOrderTree(order_id);
	//
	// if(null == orderTree) {
	// resp.setResp_code("1");
	// resp.setResp_msg("请核对内部单号是否正确。");
	//
	// return resp;
	// }
	// OrderItemExtBusiRequest orderItemExtBusiRequest =
	// orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest();
	// String bssOrderId = orderItemExtBusiRequest.getBssOrderId();
	// //TODO
	// //撤单不包含总部业务(暂时只对总部号卡进行判断)
	// String goodsType =
	// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
	// AttrConsts.GOODS_TYPE);
	// if(StringUtils.equals(goodsType, SpecConsts.TYPE_ID_GOODS_CBSS)) {
	// resp.setResp_code("1");
	// resp.setResp_msg("该撤单接口暂不支持总部业务撤单");
	//
	// return resp ;
	// }
	// //已退单订单，无法退单
	// OrderExtBusiRequest orderExtReq = orderTree.getOrderExtBusiRequest();
	// if(StringUtils.equals(orderExtReq.getRefund_deal_type(),
	// EcsOrderConsts.REFUND_DEAL_TYPE_02)) {
	// resp.setResp_code("1");
	// resp.setResp_msg("该订单已撤单");
	//
	// return resp ;
	// }
	//
	// if(StringUtils.isEmpty(bssOrderId)) {
	// resp.setResp_code("0");
	// resp.setResp_msg("订单撤单成功");
	// }else {
	// String submitResult = isSubmitByOrderID(order_id);
	// if(StringUtils.isEmpty(submitResult)) {
	// //Todo:
	// //ecaop.trades.serv.newu.order.sub -> order_type = 1 订单取消
	// BroadbandOrderSubResp cancelResp = new BroadbandOrderSubResp();
	// BroadbandOrderSubReq cancelReq = new BroadbandOrderSubReq();
	// cancelReq.setNotNeedReqStrOrderId(order_id);
	// cancelReq.setOrder_type("1");
	//
	// CommCaller caller = new CommCaller();
	// Map<String, Object> param = new HashMap<String, Object>();
	// try {
	// BeanUtils.bean2MapForAiPBSS(param, cancelReq);
	// param.put("ord_id", order_id);
	// cancelResp = (BroadbandOrderSubResp)
	// caller.invoke("ecaop.trades.serv.newu.order.sub", param);
	// } catch (Exception e) {// 调用接口失败
	// cancelResp.setCode("-9999");
	// cancelResp.setMsg(e.getMessage());
	// cancelResp.setError_msg(e.getMessage());
	// }
	// if(StringUtils.equals(cancelResp.getCode(),
	// EcsOrderConsts.INF_RESP_CODE_00000)) {
	// resp.setResp_code("0");
	// resp.setResp_msg("订单撤单成功");
	// }else {
	// resp.setResp_code("1");
	// resp.setResp_msg(cancelResp.getMsg());
	// }
	// }else {
	// resp.setResp_code("1");
	// resp.setResp_msg(submitResult);
	// }
	// }
	// //修改订单状态
	//
	// orderExtReq.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_02);
	// orderExtReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
	// orderExtReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
	//
	// orderExtReq.store();
	// //写日志
	// OrderHandleLogsReq req = new OrderHandleLogsReq();
	// String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
	// String flowTraceId =
	// orderTree.getOrderExtBusiRequest().getFlow_trace_id();
	// req.setOrder_id(order_id);
	// req.setFlow_id(flow_id);
	// req.setFlow_trace_id(flowTraceId);
	// req.setComments(ZjEcsOrderConsts.REFUND_APPLY_DESC+""+"行销APP发起撤单");
	// req.setHandler_type(Const.ORDER_HANDLER_TYPE_RETURNED);
	// req.setType_code(ZjEcsOrderConsts.REFUND_STATUS_08);
	// //默认管理员为退单操作员
	// req.setOp_id("1");
	// req.setOp_name("超级管理员");
	//
	// this.ordFlowManager.insertOrderHandLog(req);
	//
	// return resp;
	// }

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "分享卡 副卡新开户预提交接口", summary = "分享卡 副卡新开户预提交接口")
	public FuKaPreOpenResp kuKaPreOpen(FuKaPreOpenReq req) throws ApiBusiException {
		FuKaPreOpenResp resp = new FuKaPreOpenResp();
		// ecaop.trades.user.comm.fuka.preOpenAccount
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			//Map map = orderInfManager.qryGoodsDtl(req.getNotNeedReqStrOrderId());
			//logger.info(CommonDataFactory.getInstance().getOrderTree(req.getNotNeedReqStrOrderId()).getOrderDeliveryBusiRequests().get(0).getShip_tel());
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(req.getNotNeedReqStrOrderId());

			req.setOrderTree(orderTree);
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (FuKaPreOpenResp) caller.invoke("ecaop.trades.user.comm.fuka.preOpenAccount", param);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode("-999");
			resp.setMsg("系统异常：" + e.getMessage());
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "统一补换卡预提交", summary = "统一补换卡预提交")
	public ChangeAppResp changeAppPre(ChangeAppReq req) throws ApiBusiException {
		ChangeAppResp resp = new ChangeAppResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			//Map map = orderInfManager.qryGoodsDtl(req.getNotNeedReqStrOrderId());
			//logger.info(CommonDataFactory.getInstance().getOrderTree(req.getNotNeedReqStrOrderId()).getOrderDeliveryBusiRequests().get(0).getShip_tel());
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(req.getNotNeedReqStrOrderId());

			req.setOrderTree(orderTree);
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (ChangeAppResp) caller.invoke("ecaop.trades.serv.usim.change.app", param);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode("-999");
			resp.setMsg("系统异常：" + e.getMessage());
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "统一补换卡正式提交", summary = "统一补换卡正式提交")
	public ChangeSubResp changeSub(ChangeSubReq req) throws ApiBusiException {
		ChangeSubResp resp = new ChangeSubResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {

			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (ChangeSubResp) caller.invoke("ecaop.trades.serv.usim.change.sub", param);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode("-999");
			resp.setMsg("系统异常：" + e.getMessage());
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单中心补换卡预提交", summary = "订单中心补换卡预提交")
	public ChgcardnoPrecommitResp chgcardnoPrecommit(ChgcardnoPrecommitReq req) throws ApiBusiException {
		ChgcardnoPrecommitResp resp = new ChgcardnoPrecommitResp();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String order_id = req.getOrder_id();
			OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
			if (ordertree == null) {
				resp.setResp_code("1");
				resp.setResp_msg("订单号没有对应订单!");
				return resp;
			}
			String key = "";
			String value = "";
			String source_system = req.getSource_system();
			if (StringUtils.isEmpty(source_system)) {
				resp.setResp_code("1");
				resp.setResp_msg("source_system不能为空!");
				return resp;
			}
			if (!StringUtils.isEmpty(req.getImsi())) {
				key += "simid,";
				value += req.getImsi() + ",";
			}
			if (!StringUtils.isEmpty(req.getProcId())) {
				key += "proc_id,";
				value += req.getProcId() + ",";
			}
			if (!StringUtils.isEmpty(req.getCardData())) {
				key += "card_data,";
				value += req.getCardData() + ",";
			}
			if (!StringUtils.isEmpty(req.getCardType())) {
				key += "card_type,";
				value += req.getCardType() + ",";
			}
			if (!StringUtils.isEmpty(req.getChgType())) {
				key += "chg_type,";
				value += req.getChgType() + ",";
			}
			if (!StringUtils.isEmpty(req.getOld_simid())) {
				key += "old_iccid,";
				value += req.getOld_simid() + ",";
			}
			if (!StringUtils.isEmpty(req.getSimId())) {
				key += "ICCID,";
				value += req.getSimId() + ",";
			}
			if (!StringUtils.isEmpty(req.getOperatorId())) {
				key += "out_operator,";
				value += req.getOperatorId() + ",";
				String old_out_operator = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
						AttrConsts.OUT_OPERATOR);
				if (!StringUtils.isEmpty(old_out_operator)) {
					key += "old_out_operator,";
					value += old_out_operator + ",";
				}
			}
			if (!StringUtils.isEmpty(req.getOfficeId())) {
				key += "out_office,";
				value += req.getOfficeId() + ",";
				String old_out_office = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
						AttrConsts.OUT_OFFICE);
				if (!StringUtils.isEmpty(old_out_office)) {
					key += "old_out_office,";
					value += old_out_office + ",";
				}
			}
			key = key.substring(0, key.lastIndexOf(","));
			value = value.substring(0, value.lastIndexOf(","));
			if (key.length() > 0 && value.length() > 0) {
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, key.split(","), value.split(","));
				TacheFact fact = new TacheFact();
				fact.setOrder_id(order_id);
				fact.setRequest(req);
				RuleTreeExeResp rule_resp = new RuleTreeExeResp();
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String planId = cacheUtil.getConfigInfo("CHG_PRE_PLAN_ID_" + source_system);
				String rule_id = cacheUtil.getConfigInfo("CHG_PRE_RULE_ID_" + source_system);
				rule_resp = RuleFlowUtil.executeRuleTree(planId, rule_id, fact, true, true,
						EcsOrderConsts.DEAL_FROM_INF);
				if (!StringUtils.isEmpty(rule_resp.getError_code()) && "0".equals(rule_resp.getError_code())) {
					resp.setResp_code("0");
					resp.setResp_msg("预提交成功！");
					return resp;
				} else {
					resp.setResp_code("1");
					resp.setResp_msg("预提交失败：" + rule_resp.getError_msg());
					return resp;
				}
			} else {
				resp.setResp_code("1");
				resp.setResp_msg("业务参数都为空!");
				return resp;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setResp_code("1");
			resp.setResp_msg("系统异常：" + e.getMessage());
			return resp;
		}
	}

	@Override
	public AppOrderCancelResp cancelOrderByRequ(AppOrderCancelReq requ) throws Exception {
		// TODO Auto-generated method stub
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String bss_invalid_return_code = cacheUtil.getConfigInfo("bss_invalid_return_code");
		String order_id = requ.getOrder_id();
		AppOrderCancelResp resp = new AppOrderCancelResp();
		// bss单号为空，则未进行预提交，直接修改订单状态为退单
		// bss单号不为空，且未调用正式提交接口或正式提交接口出错，先调用B侧撤单接口，在修改订单状态
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		
		if (null == orderTree) {
			resp.setResp_code("1");
			resp.setResp_msg("请核对内部单号是否正确。");

			return resp;
		}
		OrderItemExtBusiRequest orderItemExtBusiRequest = orderTree.getOrderItemBusiRequests().get(0)
				.getOrderItemExtBusiRequest();
		//(a<b)?a:b;
		String bssOrderId = !StringUtils.isEmpty(orderItemExtBusiRequest.getBssOrderId())
				? orderItemExtBusiRequest.getBssOrderId() : orderItemExtBusiRequest.getActive_no();
		
		// 已退单订单，无法退单
		OrderExtBusiRequest orderExtReq = orderTree.getOrderExtBusiRequest();
		if (StringUtils.equals(orderExtReq.getRefund_deal_type(), EcsOrderConsts.REFUND_DEAL_TYPE_02)) {
			resp.setResp_code("1");
			resp.setResp_msg("该订单已撤单");

			return resp;
		}

		if (StringUtils.isEmpty(bssOrderId)) {
			//if (StringUtils.isNotEmpty(bssOrderId)) {
			resp.setResp_code("0");
			resp.setResp_msg("订单撤单成功");
		} else {
			// 撤单不包含总部业务
			String isAop = orderTree.getOrderExtBusiRequest().getIs_aop();
			if (StringUtils.equals(isAop, "1")) {
				resp.setResp_code("1");
				resp.setResp_msg("该撤单接口暂不支持总部业务撤单");

				return resp;
			}else{
				String submitResult = isSubmitByOrderID(order_id);
				if (StringUtils.isEmpty(submitResult)) {
					// ecaop.trades.serv.newu.order.sub -> order_type = 1 订单取消
					BroadbandOrderSubResp cancelResp = new BroadbandOrderSubResp();
					BroadbandOrderSubReq cancelReq = new BroadbandOrderSubReq();
					cancelReq.setNotNeedReqStrOrderId(order_id);
					cancelReq.setOrder_type("1");
	
					CommCaller caller = new CommCaller();
					Map<String, Object> param = new HashMap<String, Object>();
					try {
						BeanUtils.bean2MapForAiPBSS(param, cancelReq);
						param.put("ord_id", order_id);
						cancelResp = (BroadbandOrderSubResp) caller.invoke("ecaop.trades.serv.newu.order.sub", param);
						cancelResp.setCode("00303");
					} catch (Exception e) {// 调用接口失败
						resp.setResp_code("1");
						resp.setResp_msg(e.getMessage());
						return resp;
					}
					if (StringUtils.equals(cancelResp.getCode(), EcsOrderConsts.INF_RESP_CODE_00000)||StringUtils.equals(cancelResp.getCode(), bss_invalid_return_code) ) {
						resp.setResp_code("0");
						resp.setResp_msg("订单撤单成功");
					} else {
						resp.setResp_code("1");
						resp.setResp_msg(cancelResp.getMsg());
	
						return resp;
					}
				} else {
					resp.setResp_code("1");
					resp.setResp_msg(submitResult);
	
					return resp;
				}
			}
		}
		// 修改订单状态
		orderExtReq.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_02);
		orderExtReq.setRefund_status(EcsOrderConsts.REFUND_STATUS_08);
		orderExtReq.setAbnormal_type("4");
		orderExtReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);

		orderExtReq.store();
		
		// 写日志
		OrderHandleLogsReq req = new OrderHandleLogsReq();
		String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
		String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		req.setOrder_id(order_id);
		req.setFlow_id(flow_id);
		req.setFlow_trace_id(flowTraceId);
		req.setComments(ZjEcsOrderConsts.REFUND_APPLY_DESC + "" + "行销APP发起撤单");
		req.setHandler_type(Const.ORDER_HANDLER_TYPE_RETURNED);
		req.setType_code(ZjEcsOrderConsts.REFUND_STATUS_08);
		// 默认管理员为退单操作员
		req.setOp_id("1");
		req.setOp_name("超级管理员");

		this.ordFlowManager.insertOrderHandLog(req);

		ordIntentManager.cancelIntent(order_id);
		
		return resp;
	}

	/**
	 * 判断正式提交是否成功
	 * 
	 * @param order_id
	 * @return ""表示允许撤单
	 */
	private String isSubmitByOrderID(String order_id) {
		String goods_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0)
				.getGoods_id();
		String type_id = CommonDataFactory.getInstance().getGoodSpec(null, goods_id, AttrConsts.TYPE_ID);
		// 行销APP业务 ：省内号卡,单宽，融合，活动受理，集客号卡,新装沃TV，新装固话
		// 商品的类型ID为key,对应的正式提交规则ID为value
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String typeCommitRuleId = cacheUtil.getConfigInfo("TYPE_RULE_COMMIT");

		Map typeToRule = (Map) JSON.parse(typeCommitRuleId);

		String commitRuleId = MapUtils.getString(typeToRule, type_id);
		if (StringUtils.isEmpty(commitRuleId)) {
			return "请查看es_config_info中是否配置了该商品的类型ID";
		}
		//String sql = "select exe_result from es_rule_exe_log where rule_id='"+commitRuleId+"' and obj_id='"+order_id+"' order by create_time desc";
		String sql = "select exe_result from (select * from es_rule_exe_log union select * from es_rule_exe_log_bak) where rule_id='"+commitRuleId+"' and obj_id='"+order_id+"' order by create_time desc";

		List<Map> resultList = baseDaoSupport.queryForList(sql);

		// 未执行过正式提交规则，允许撤单
		if (resultList == null || resultList.size() == 0) {
			return "";
		} else {
			// 最新一条正式提交记录
			Map resultMap = resultList.get(0);
			String exeResult = MapUtils.getString(resultMap, "exe_result");

			// 正式提交通过，无法撤单
			if (StringUtils.equals(exeResult, "0")) {
				return "该订单已正式提交，无法进行撤单";
			}
			return "";
		}
	}

	@Override
	public AuditOrderCancelResp auditOrderCancel(AuditOrderCancelReq requ) {
		AuditOrderCancelResp resp = new AuditOrderCancelResp();

		String orderId = requ.getOrder_id();
		if (StringUtils.isEmpty(orderId)) {
			resp.setResp_code("1");
			resp.setResp_msg("订单中心单号不能为空");

			return resp;
		}

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(orderId);
		if (null == orderTree) {
			resp.setResp_code("1");
			resp.setResp_msg("未根据单号有误，请核对单号");

			return resp;
		}

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("0", "发起退单");
		resultMap.put("1", "发起退款");
		resultMap.put("2", "发起补单");
		resultMap.put("3", "发起补款");
		resultMap.put("4", "退现金");

		String operType = requ.getOper_type();
		if (StringUtils.isEmpty(operType)) {
			resp.setResp_code("1");
			resp.setResp_msg("操作类型不能为空");

			return resp;
		}

		String operMessage = MapUtils.getString(resultMap, operType);
		if (StringUtils.isEmpty(operMessage)) {
			resp.setResp_code("1");
			resp.setResp_msg("未找到对应的操作类型。");

			return resp;
		}

		// 已退款订单，不能再次发起退款操作
		String refundplatformorderid = CommonDataFactory.getInstance().getAttrFieldValue(orderId,
				"refundplatformorderid");
		if (!StringUtils.isEmpty(refundplatformorderid) && StringUtils.equals(operType, "1")) {
			resp.setResp_code("1");
			resp.setResp_msg("该订单已退款");

			return resp;
		}

		try {
			if (StringUtils.equals(operType, "1")) { // 退款需要调支付中心然后翻转订单中心稽核状态
				BroadbandOrderInfoRefundReq req = new BroadbandOrderInfoRefundReq();
				req.setNotNeedReqStrOrderId(orderId);
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				BroadbandOrderInfoRefundResp refundResp = client.execute(req, BroadbandOrderInfoRefundResp.class);
				if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(refundResp.getResp_code())) {
					resp.setResp_code("1");
					resp.setResp_msg(refundResp.getResp_msg());

					return resp;
				} else {
					CommonDataFactory.getInstance().updateAttrFieldValue(orderId,
							new String[] { AttrConsts.BSS_REFUND_STATUS, "refundplatformorderid",
									AttrConsts.ACCOUNT_VALI },
							new String[] { EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_SUCC, refundResp.getOutRefundNo(),
									operType });
					OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
					orderExt.setRefund_time(Consts.SYSDATE);
					orderExt.setAudit_type(operType);

					orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					orderExt.store();

					resp.setResp_code("0");
					resp.setResp_msg("退款成功");
				}
			} else {// 直接翻转订单中心稽核状态
				resp = updateAuditOrdetStatus(operType, operMessage, orderTree);
			}
		} catch (Exception e) {
			resp.setResp_code("1");
			resp.setResp_msg(e.getMessage());

			return resp;
		}
		return resp;
	}

	/**
	 * 
	 * @param operType
	 * @param operMessage
	 * @param orderTree
	 * @return
	 */
	private AuditOrderCancelResp updateAuditOrdetStatus(String operType, String operMessage,
			OrderTreeBusiRequest orderTree) {
		AuditOrderCancelResp resp = new AuditOrderCancelResp();
		try {
			OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
			orderExt.setAudit_type(operType);

			orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExt.store();

			resp.setResp_code("0");
			resp.setResp_msg(operMessage + "成功");
		} catch (Exception e) {
			resp.setResp_code("1");
			resp.setResp_msg(e.getMessage());
		}
		return resp;
	}


	@Override
	@SuppressWarnings("all")
	public SelfspreadOrderinfoSynResp selfspreadOrderinfoSyn(SelfspreadOrderinfoSynReq req) {

		SelfspreadOrderinfoSynResp resp = new SelfspreadOrderinfoSynResp();
		String order_id = req.getNotNeedReqStrOrderId();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String develop_id = "";
		String pre_order_id = "";
		String develop_man = "";
		String office_id = "";
		String operator_id = "";
		String region_id = "";
		// String goods_id = "";
		String service_num = "";
		String type_id = "";
		String cat_id="";
		if (null != orderTree) {
			pre_order_id = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getActive_no();
			develop_man = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "develop_code_new");
			develop_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "development_point_code");
			office_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE);
			operator_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR);
			region_id = orderTree.getOrderExtBusiRequest().getOrder_city_code();
			// region_id =
			// CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.BSS_AREA_CODE,
			// region_id);
			String sql = "select t.* from es_dc_public_ext t where t.stype='DIC_FXD_CITY_CODE'";
			List<Map<String, String>> list = baseDaoSupport.queryForList(sql);
			for (Map<String, String> map : list) {
				if (region_id.equals(map.get("pname"))) {
					region_id = map.get("pkey");
					break;
				}
			}
			type_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
	        cat_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
			service_num = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getPhone_num();
			req.setBms_accept_id(pre_order_id);
		} else {
			resp.setCode("-999");
			resp.setMsg("没有查询到订单树!");
			return resp;
		}

		String to_action_wcf = cacheUtil.getConfigInfo("TO_ACTION_WCF");
		String sql = "";
		if ("1".equals(to_action_wcf)) {
			String sql_count = "select t.intent_order_id,t.goods_id from es_order_intent t where t.order_id='"
					+ order_id + "' and t.source_from='" + ManagerUtils.getSourceFrom() + "'";
			List<Map> list = baseDaoSupport.queryForList(sql_count);
			if (list.size() > 0) {
				sql = " select a.source_system_type,a.top_share_userid,a.market_user_id,a.goods_id,a.intent_order_id,a.acc_nbr,a.deal_operator,a.deal_office_id,"
						+ "a.develop_point_code,a.develop_code,a.seed_user_id,a.share_svc_num,gp.sn as share_goods_id from "
						+ " es_order_intent a left join es_goods_package gp on a.goods_id = gp.goods_id where a.order_id='" + order_id + "' and a.source_from='"
						+ ManagerUtils.getSourceFrom() + "' ";
			} else {
				StringBuilder sbBuilder = new StringBuilder();
				sbBuilder.append(
						"select e.order_from as source_system_type,i.goods_id, ie.phone_num as acc_nbr,  v.out_operator as deal_operator, v.out_office as deal_office_id, ");
				sbBuilder.append(
						"  v.development_point_code as develop_point_code, v.develop_code_new as develop_code, ");
				sbBuilder.append("       i.order_id as intent_order_id, ");
				sbBuilder.append("       v.market_user_id as market_user_id, ");
				sbBuilder.append("       v.seed_user_id as seed_user_id, ");
				sbBuilder.append("       v.share_svc_num as share_svc_num, ");
				sbBuilder.append("       gp.sn as share_goods_id,v.top_share_userid,v.top_share_num ");
				sbBuilder.append("  from es_order_items i left join es_goods_package gp on i.goods_id = gp.goods_id");
				sbBuilder.append("  left join es_order_items_ext ie ");
				sbBuilder.append("    on i.order_id = ie.order_id ");
				sbBuilder.append("  left join es_order_ext e ");
				sbBuilder.append("    on i.order_id = e.order_id ");
				sbBuilder.append("  left join es_order_extvtl v ");
				sbBuilder.append("    on i.order_id = v.order_id where i.source_from='")
						.append(ManagerUtils.getSourceFrom()).append("' ");
				sbBuilder.append(" and i.order_id='").append(order_id).append("' ");
				sql = sbBuilder.toString();
			}
		} else {
			sql = cacheUtil.getConfigInfo("WCF_SQL") + " where i.order_id='" + order_id + "' and i.source_from='"
					+ ManagerUtils.getSourceFrom() + "' ";
		}
		logger.error(sql);
		
		List list = baseDaoSupport.queryForList(sql);
		if(list.size()==0){
			resp.setCode("-999");
			resp.setMsg("单号异常");
			return resp;
		}
		Map map = (Map) list.get(0);
		String sub_id = "";
		String share_scheme_id = Const.getStrValue(map, "market_user_id");
		if (cacheUtil.getConfigInfo("SHARE_SCHEME_ID_FROM").contains(orderTree.getOrderExtBusiRequest().getOrder_from())
				|| cacheUtil.getConfigInfo("SHARE_SCHEME_ID_FROM")
						.contains(Const.getStrValue(map, "source_system_type"))) {
			share_scheme_id = cacheUtil
					.getConfigInfo("SHARE_SCHEME_ID_" + orderTree.getOrderExtBusiRequest().getOrder_from());
			if(StringUtils.isEmpty(share_scheme_id)){
				share_scheme_id = cacheUtil
						.getConfigInfo("SHARE_SCHEME_ID_" + Const.getStrValue(map, "source_system_type"));
			}
		}
		//String order_center_id = Const.getStrValue(map, "intent_order_id");
		String order_center_id = order_id;
		String share_partner_id = Const.getStrValue(map, "seed_user_id");
		String share_svc_num = Const.getStrValue(map, "share_svc_num");
		String reward_rela_user = "";
		if (cacheUtil.getConfigInfo("REWARD_RELA_USER_FROM")
				.contains(orderTree.getOrderExtBusiRequest().getOrder_from())
				|| cacheUtil.getConfigInfo("REWARD_RELA_USER_FROM")
						.contains(Const.getStrValue(map, "source_system_type"))) {
			reward_rela_user = Const.getStrValue(map, "develop_point_code");
		}
		if (cacheUtil.getConfigInfo("REWARD_RELA_USER_FROM_10102")
				.contains(orderTree.getOrderExtBusiRequest().getOrder_from())
				|| cacheUtil.getConfigInfo("REWARD_RELA_USER_FROM_10102")
						.contains(Const.getStrValue(map, "source_system_type"))) {
			reward_rela_user = Const.getStrValue(map, "top_share_userid");
		}
		String share_goods_id = Const.getStrValue(map, "share_goods_id");
		String deal_operator = Const.getStrValue(map, "deal_operator");
		String deal_office_id = Const.getStrValue(map, "deal_office_id");
		
		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");
		
		if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)){
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
					"order_deal_method");
			// 自定义流程
			if("1".equals(deal_method)){
				
				// 自定义流程，检查自定义流程的操作员、操作点是否配置
				String order_from_operator = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_ID_FLAG_"
						+orderTree.getOrderExtBusiRequest().getOrder_from());
				String order_from_channelId = cacheUtil.getConfigInfo("WORK_CUSTOM_CHANNEL_ID_FLAG_"
						+orderTree.getOrderExtBusiRequest().getOrder_from());
				
				if(StringUtil.isEmpty(order_from_operator)){
					// 根据来源未取到配置，根据flow_code取人员配置
					String flow_code = this.getFlowCode(order_id);
					
					if(!StringUtil.isEmpty(flow_code)){
						order_from_operator = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_ID_FLAG_"+flow_code);
						order_from_channelId = cacheUtil.getConfigInfo("WORK_CUSTOM_CHANNEL_ID_FLAG_"+flow_code);
					}
				}
				
				if(!StringUtil.isEmpty(order_from_operator)){
					operator_id = order_from_operator;
					office_id = order_from_channelId;
				}else{
					office_id = ZjCommonUtils.getGonghaoInfoByOrderId(order_id).getDept_id();
					operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(order_id).getUser_code();
				}
			}
		}else{
			if (cacheUtil.getConfigInfo("WCF_ORDER_FROM").contains(orderTree.getOrderExtBusiRequest().getOrder_from())) {
				office_id = ZjCommonUtils.getGonghaoInfoByOrderId(order_id).getDept_id();
				operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(order_id).getUser_code();
			}
			if (cacheUtil.getConfigInfo("ZB_ORDER_FROM").contains(Const.getStrValue(map, "source_system_type"))) {
				office_id = Const.getStrValue(map, "deal_office_id");
				operator_id = Const.getStrValue(map, "deal_operator");
			}
		}

		String order_source = cacheUtil.getConfigInfo("ZCB_ORDER_SOURCE_" + type_id);

		String order_sale_plat = cacheUtil
				.getConfigInfo("ORDER_SALE_PLAT_" + orderTree.getOrderExtBusiRequest().getOrder_from());
		if (StringUtils.isEmpty(order_sale_plat)) {
			order_sale_plat = cacheUtil.getConfigInfo("ORDER_SALE_PLAT_" + Const.getStrValue(map, "source_system_type"));
		}
		EssOperatorInfoUtil essinfo = new EssOperatorInfoUtil();
        String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);//地市// 4位

		req.setShare_scheme_id(share_scheme_id); // 营销活动ID
        EmpOperInfoVo essOperInfo = null;
        String groupFlowKg = cacheUtil.getConfigInfo("groupFlowKg");
        if(StringUtils.isNotEmpty(groupFlowKg) &&"1".equals(groupFlowKg) &&"10093".equals(orderTree.getOrderExtBusiRequest().getOrder_from()) && "221668199563784192".equals(cat_id)){
             essOperInfo = EssOperatorInfoUtil.getEmpInfoFromDataBase(null,"WFGROUPUPINTENT",order_city_code);//无线宽带线上取默认的渠道和工号信息
        }
        if(essOperInfo != null){
            operator_id = essOperInfo.getEss_emp_id();
            office_id = essOperInfo.getDep_id();
        }
		req.setShare_scheme_id(share_scheme_id);
		req.setOrder_center_id(order_center_id);
		req.setOrder_source(order_source);
		req.setService_num(service_num); // 服务号码
		req.setSub_id(sub_id);
		req.setOffice_id(office_id);
		req.setOperator_id(operator_id);
		req.setDevelop_id(develop_id);
		req.setDevelop_man(develop_man);
		req.setShare_partner_id(share_partner_id);
		req.setShare_svc_num(share_svc_num);
		req.setRegion_id(region_id);
		req.setOrder_sale_plat(order_sale_plat);
		//req.setOrder_sale_plat("00000012");//自传播：00000012
		req.setShare_goods_id(share_goods_id);
		req.setOrder_get_channel(deal_office_id);
		req.setOrder_get_oper(deal_operator);
		req.setReward_rela_user(reward_rela_user); // 引流受益人ID
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			logger.info(JSON.toJSON(param));
			resp = (SelfspreadOrderinfoSynResp) caller.invoke("ecaop.trades.serv.selfspread.orderinfo.syn", param);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode("-999");
			resp.setMsg("系统异常：" + e.getMessage());
		}
		return resp;
	}
	
	/**
	 * 根据订单编号查询流程编码
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	private String getFlowCode(String order_id){
		IDaoSupport<EmpOperInfoVo> support = SpringContextHolder.getBean("baseDaoSupport");
		String sql = "SELECT a.flow_code FROM es_work_custom_workflow_ins a WHERE a.order_id='"+order_id+"'";
		
		return support.queryForString(sql);
	}

	/**
	 * 商品预定回填接口
	 */
	@Override
	public OrderReceiveBackResp orderReceiveBack(OrderReceiveBackReq req) {
		OrderReceiveBackResp resp = new OrderReceiveBackResp();
		try {
			CommCaller caller = new CommCaller();
			ReqBody reqBody = new ReqBody();
			UNI_BSS_HEAD UNI_BSS_HEAD = new UNI_BSS_HEAD();
			UNI_BSS_BODY UNI_BSS_BODY = new UNI_BSS_BODY();
			UNI_BSS_ATTACHED UNI_BSS_ATTACHED = new UNI_BSS_ATTACHED();
			reqBody.setUNI_BSS_HEAD(JSONObject.fromObject(UNI_BSS_HEAD));
			reqBody.setUNI_BSS_BODY(JSONObject.fromObject(UNI_BSS_BODY));
			reqBody.setUNI_BSS_ATTACHED(JSONObject.fromObject(UNI_BSS_ATTACHED));

			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String APP_ID = cacheUtil.getConfigInfo("BLD_APP_ID");
			UNI_BSS_HEAD.setAPP_ID(APP_ID);
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			String TIMESTAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(date);
			UNI_BSS_HEAD.setTIMESTAMP(TIMESTAMP);
			Random random = new Random();
			String TRANS_ID = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
			for (int i = 0; i < 6; i++) {
				TRANS_ID += random.nextInt(10);
			}
			UNI_BSS_HEAD.setTRANS_ID(TRANS_ID);
			String app_secret = cacheUtil.getConfigInfo("BLD_APP_SECRET");
			String TOKEN = "APP_ID"+APP_ID+"TIMESTAMP"+TIMESTAMP+"TRANS_ID"+TRANS_ID+app_secret;
			TOKEN = MD5Util.MD5(TOKEN);
			
			UNI_BSS_HEAD.setTOKEN(TOKEN);

			UNI_BSS_BODY.setORDER_RECEIVE_BACK_REQ(req.getORDER_RECEIVE_BACK_REQ());

			Map<String, Object> param = new HashMap<String, Object>();
			//BeanUtils.bean2MapForAiPBSS(param, reqBody);
			param.put("UNI_BSS_HEAD", JSONObject.fromObject(UNI_BSS_HEAD));
			param.put("UNI_BSS_BODY", JSONObject.fromObject(UNI_BSS_BODY));
			//param.put("UNI_BSS_ATTACHED", JSONObject.fromObject(UNI_BSS_ATTACHED));
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (OrderReceiveBackResp) caller.invoke("orderReceiveBack", param);
		} catch (Exception e) {
			e.printStackTrace();
			// resp.setCode("-999");
			// resp.setMsg("系统异常：" + e.getMessage());
		}
		return resp;
	}
	
	//trade-query
	@Override
	public TradeQueryResp tradeQuery (TradeQueryReq req) throws ApiBusiException{
		TradeQueryResp resp = new TradeQueryResp();
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(req.getNotNeedReqStrOrderId());
		String serviceId = cacheUtil.getConfigInfo("REFUND_SERVICE_ID_" + tree.getOrderExtBusiRequest().getOrder_from());
		if (StringUtils.isEmpty(serviceId) || "null".equals(serviceId)) {
			resp.setResp_code("-999");
			resp.setResp_msg("未配置对应serviceid");
			return resp;
		}
		req.setServiceId(serviceId);
		req.setTradeType("1");
		Map<String, Object> param = new HashMap<String, Object>();
		CommCaller caller = new CommCaller();
		try {
			BeanUtils.bean2Map(param, req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(JSONObject.fromObject(param));
		Map new_map = param;
		param = new HashMap<String, Object>();
		param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志

		// 设置业务系统为00000009--订单中心平台
		String ddzx_serviceid = cacheUtil.getConfigInfo("REFUND_SERVICE_ID_DDZX");
		param.put("serviceId", ddzx_serviceid);
		//System.out.println(JSONObject.fromObject(new_map));
		param.put("data", orderInfManager.getRSASign(new_map));
		resp = (TradeQueryResp) caller.invoke("pc.user.page.trade-query", param);
		String data = resp.getData();
		data = orderInfManager.decodeInfo(data);
		//System.out.println(data);
		Map data_map = JSON.parseObject(data);
		resp.setResp_code(data_map.get("resultCode").toString());
		resp.setResp_msg(data_map.get("resultDesc").toString());
		resp.setTradeType(data_map.get("tradeType").toString());
		resp.setResultCode(data_map.get("resultCode").toString());
		resp.setResultDesc(data_map.get("resultDesc").toString());
		resp.setOutTradeNo(data_map.get("outTradeNo").toString());
		return resp;
		
	}
	
	
	@Override
	public DwzCnCreateResp dwzCnCreate(DwzCnCreateReq req) throws ApiBusiException{
		DwzCnCreateResp resp = new DwzCnCreateResp();
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String url = cacheUtil.getConfigInfo("UPLOAD_PHOTOS_URL");
		url += req.getNotNeedReqStrOrderId();
		req.setUrl(url);
		//req.setUrl("http://www.baidu.com");
		Map<String, Object> param = new HashMap<String, Object>();
		CommCaller caller = new CommCaller();
		try {
			BeanUtils.bean2Map(param, req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
		resp = (DwzCnCreateResp) caller.invoke("baidu.user.message.send", param);
		return resp;
	}
	
	@Override
	public CmcSmsSendResp cmcSmsSend(CmcSmsSendReq req) throws Exception{
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		List<ES_WORK_SMS_SEND>listSmsPojo = req.getListpojo();
		if(null != req.getListpojo()&&listSmsPojo.size()>0 ) {//批量插入
			List<ES_WORK_SMS_SEND> temp = new ArrayList<ES_WORK_SMS_SEND>();
			
			for(int i = 0 ; i<listSmsPojo.size();i++) {
				if(org.apache.commons.lang.StringUtils.isNotBlank(listSmsPojo.get(i).getBill_num())
						&& org.apache.commons.lang.StringUtils.isNotBlank(listSmsPojo.get(i).getService_num())
						&& org.apache.commons.lang.StringUtils.isNotBlank(listSmsPojo.get(i).getSms_data())) {
					String sms_id = baseDaoSupport.getSequences("S_ES_SEND_SMS");
					listSmsPojo.get(i).setSms_id(sms_id);
					listSmsPojo.get(i).setSms_type("cmc");
					listSmsPojo.get(i).setSend_num("0");
					listSmsPojo.get(i).setSend_status("0");
					listSmsPojo.get(i).setCreate_time(df.format(new Date()));
					temp.add(listSmsPojo.get(i));
				}
			}
			if(!temp.isEmpty()) {
				baseDaoSupport.saveBatch("es_order_mid_sms", "insert", listSmsPojo, null);
			}
		}else {//单个插入
			if(StringUtil.isEmpty(req.getUserNumber())){
				throw new Exception("接收号码不能为空");
			}
			if(StringUtil.isEmpty(req.getMessageContent())) {
				throw new Exception("发送内容不能为空");
			}
			String sms_id = baseDaoSupport.getSequences("S_ES_SEND_SMS");
			Map<String,String> map = new HashMap<String, String>();
			map.put("sms_id", sms_id);//主键
			map.put("service_num", req.getUserNumber());//接收号码
			map.put("bill_num", "");//发送号码
			map.put("sms_data", req.getMessageContent());//短信内容
			map.put("sms_type","cmc");//sms本网，cmc是异网
			map.put("send_type",req.getSend_type());
			map.put("sms_flag", "");
			map.put("send_status", "0");//发送状态，0，未发送。
			map.put("send_num", "0");//发送次数
			map.put("create_time", df.format(new Date()));//创建时间
			map.put("order_id", req.getNotNeedReqStrOrderId());//order_id
			baseDaoSupport.insertByMap("es_order_mid_sms", map);
		}
		
		
		CmcSmsSendResp resp = new CmcSmsSendResp();
		/*
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String spCode = cacheUtil.getConfigInfo("YXT_spCode");
		String userName = cacheUtil.getConfigInfo("YXT_userName");
		String Password = cacheUtil.getConfigInfo("YXT_Password");
		//String key = cacheUtil.getConfigInfo("YXT_KEY");
		req.setSpCode(spCode);
		req.setLoginName(userName);
		req.setPassword(Password);
		try {
			req.setSerialNumber(DateUtil.getTime7()+"000");
		} catch (FrameException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		req.setF("1");
		Map<String, Object> param = new HashMap<String, Object>();
		CommCaller caller = new CommCaller();
		
		try {
			param.put("SpCode", req.getSpCode());
			param.put("LoginName", req.getLoginName());
			param.put("Password", req.getPassword());
			param.put("MessageContent", URLEncoder.encode(req.getMessageContent(),"gbk"));
			param.put("UserNumber", req.getUserNumber());
			param.put("SerialNumber", req.getSerialNumber());
			param.put("f", req.getF());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		param.put("ord_id", req.getNotNeedReqStrOrderId());// 记录日志
		/*String YXT_field = cacheUtil.getConfigInfo("YXT_field");
		String[] fields = YXT_field.split(",");
		String str = "";
		for (int i = 0; i < fields.length; i++) {
			String value = param.get(fields[i])+"";
			str += fields[i]+"="+value;
		}
		str += key;
		System.out.println(str);
		String sign = "";
		try {
			sign = MD5Util.MD5(str, "utf-8");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		param.put("sign", sign);
		resp = (CmcSmsSendResp) caller.invoke("yeion.user.message.send", param);
		*/
		resp.setResultcode("0");
		return resp;
	}

    @Override
    public WisdomHomeSubResp wisdomHomePreSub(WisdomHomePreSubReq req) throws ApiBusiException {

        WisdomHomeSubResp resp = new WisdomHomeSubResp();
        CommCaller caller = new CommCaller();
        Map<String, Object> param = new HashMap<String, Object>();
        try {
            CommonDataFactory.getInstance().updateOrderTree(req.getNotNeedReqStrOrderId());
            Map map = orderInfManager.qryGoodsDtl(req.getNotNeedReqStrOrderId());
            logger.info(CommonDataFactory.getInstance().getOrderTree(req.getNotNeedReqStrOrderId()).getOrderDeliveryBusiRequests().get(0).getShip_tel());
            req.setScheme_id(Const.getStrValue(map, "p_code") + "");
            req.setProduct_id(Const.getStrValue(map, "sn") + "");
            BeanUtils.bean2MapForAiPBSS(param, req);
            param.put("ord_id", req.getNotNeedReqStrOrderId());
            resp = (WisdomHomeSubResp) caller.invoke("ecaop.trades.serv.zhdj.newopen.sub", param);
            if (!StringUtils.isEmpty(resp.getCode()) && "00000".equals(resp.getCode()) ) {
                OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance()
                        .getOrderTree(req.getNotNeedReqStrOrderId());
                List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
                OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0)
                        .getOrderItemExtBusiRequest();
                BssOrderId BssOrderId = resp.getRespJson();
                if (BssOrderId.getBms_accept_id() instanceof String) {
                    orderItemExtBusiRequest.setBssOrderId(BssOrderId.getBms_accept_id());
                    orderItemExtBusiRequest.setActive_no(BssOrderId.getBms_accept_id());
                    orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
                    orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
                    orderItemExtBusiRequest.store();
                }
            } else {
                resp.setCode("-9999");
                resp.setError_msg(resp.getMsg());
            }

        } catch (Exception e) {// 调用接口失败
            resp.setCode("-9999");
            resp.setMsg(e.getMessage());
            resp.setError_msg(e.getMessage());
            e.printStackTrace();
        }
        return resp;
    }
	
	@Override
	public OrderInfoBackfillRsp orderInfoBackfill(OrderInfoBackfillReq req) throws ApiBusiException {
		OrderInfoBackfillRsp resp = new OrderInfoBackfillRsp();
		try {
			CommCaller caller = new CommCaller();
			UNI_BSS_HEAD UNI_BSS_HEAD = new UNI_BSS_HEAD();
			Map<String, Object> UNI_BSS_BODY = new HashMap<String, Object>();

			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String APP_ID = cacheUtil.getConfigInfo("BLD_APP_ID");
			UNI_BSS_HEAD.setAPP_ID(APP_ID);
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			String TIMESTAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(date);
			UNI_BSS_HEAD.setTIMESTAMP(TIMESTAMP);
			Random random = new Random();
			String TRANS_ID = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
			for (int i = 0; i < 6; i++) {
				TRANS_ID += random.nextInt(10);
			}
			UNI_BSS_HEAD.setTRANS_ID(TRANS_ID);
			String app_secret = cacheUtil.getConfigInfo("BLD_APP_SECRET");
			String TOKEN = "APP_ID" + APP_ID + "TIMESTAMP" + TIMESTAMP + "TRANS_ID" + TRANS_ID + app_secret;
			TOKEN = MD5Util.MD5(TOKEN);
			UNI_BSS_HEAD.setTOKEN(TOKEN);

			UNI_BSS_BODY.put("ORDER_INFO_BACKFILL_REQ", req.getORDER_INFO_BACKFILL_REQ());

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("UNI_BSS_HEAD", JSONObject.fromObject(UNI_BSS_HEAD));
			param.put("UNI_BSS_BODY", JSONObject.fromObject(UNI_BSS_BODY));
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (OrderInfoBackfillRsp) caller.invoke("orderInfoBackfill", param);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code("9999");
			resp.setError_msg(e.getMessage());
		}
		return resp;
	}
	
	
	@Override
	public InitiationCallResp initiationCall (InitiationCallReq req) throws ApiBusiException{
		InitiationCallResp resp = new InitiationCallResp();
		try{
			CommCaller caller = new CommCaller();
			req.setRecordOutID(getRecordOutID());
	        Map<String, Object> param = new HashMap<String, Object>();
	        BeanUtils.bean2MapForAiPBSS(param, req);
	        param.put("sign", getsign(req.getPartner()));
	        param.put("ord_id", req.getNotNeedReqStrOrderId());
	        resp = (InitiationCallResp) caller.invoke("IVR.user.callOut.applyIvrReco", param);
		}catch(Exception e){
			e.printStackTrace();
		}
		return resp;
		
	}
	
	@Override
	public QueryCalllogResp queryCalllog (QueryCalllogReq req) throws ApiBusiException{
		QueryCalllogResp resp = new QueryCalllogResp();
		try{
			CommCaller caller = new CommCaller();
			
	        Map<String, Object> param = new HashMap<String, Object>();
	        BeanUtils.bean2MapForAiPBSS(param, req);
	        param.put("ord_id", req.getNotNeedReqStrOrderId());
	        param.put("sign", getsign(req.getPartner()));
	        resp = (QueryCalllogResp) caller.invoke("IVR.user.callOut.queryUocpIvrCalllogNew", param);
		}catch(Exception e){
			e.printStackTrace();
		}
		return resp;
		
	}
	
	@Override
	public DownloadRecordResp downloadRecord (DownloadRecordReq req) throws ApiBusiException{
		DownloadRecordResp resp = new DownloadRecordResp();
		try{
			CommCaller caller = new CommCaller();
	        Map<String, Object> param = new HashMap<String, Object>();
	        BeanUtils.bean2MapForAiPBSS(param, req);
	        param.put("ord_id", req.getNotNeedReqStrOrderId());
	        param.put("sign", getsign(req.getPartner()));
	        resp = (DownloadRecordResp) caller.invoke("IVR.user.callOut.downloadRecord", param);
		}catch(Exception e){
			e.printStackTrace();
		}
		return resp;
		
	}
	
	private String getRecordOutID(){
		String recordOutID = "";
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String oldrecordOutID = baseDaoSupport.nextVal("ES_CALLLOG_SEQ")+"";
		int digit = 12-oldrecordOutID.length();
		String newrecordOutID = "";
		for (int i = 0; i < digit; i++) {
			newrecordOutID += "0";
		}
		recordOutID = newrecordOutID+oldrecordOutID;
		return recordOutID;
	}
	
	private String getsign(String partner) {
		String sign = "";
		try {
			sign = MD5Util.MD5(partner, "utf-8");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sign;
	}
	
	
	
	@Override
	public ObjectReplacePreSubResp objectReplace(ObjectReplacePreSubReq req) {
		// TODO Auto-generated method stub
		ObjectReplacePreSubResp resp = new ObjectReplacePreSubResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (ObjectReplacePreSubResp) caller.invoke("ecaop.trades.serv.user.onuReplace.chg", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 处理自定义流程--获取卡信息
	 * @param order_id
	 * @throws Exception
	 */
	private void dealWorkCustomOrderCardInfo(String order_id) throws Exception{
		
		String instance_id = this.workCustomEngine.checkCurrNodes(order_id, 
				"获取卡信息", "WORKFLOW_CARD_INFO_NODES");
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(instance_id)){
			WORK_CUSTOM_FLOW_DATA flowdata = this.workCustomEngine.runNodeManual(instance_id, 
					false, "", "获取卡信息", Const.GET_CARD_INFO);
			
			if(ConstsCore.ERROR_FAIL.equals(flowdata.getRun_result()))
				throw new Exception(flowdata.getRun_msg());
		}else{
			throw new Exception(order_id+"订单不在获取卡信息环节");
		}
	}
	
	/**
	 * 处理自定义流程--号卡预提交
	 * @param order_id
	 * @throws Exception
	 */
	private void dealWorkCustomMobilePreCommit(String order_id) throws Exception{
		
		String instance_id = this.workCustomEngine.checkCurrNodes(order_id, 
				"号卡开户预提交", "WORKFLOW_MOBILE_PRE_COMMIT_NODES");
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(instance_id)){
			WORK_CUSTOM_FLOW_DATA flowdata = this.workCustomEngine.runNodeManual(instance_id, 
					false, "", "号卡开户预提交", Const.PRE_COMMIT);
			
			if(ConstsCore.ERROR_FAIL.equals(flowdata.getRun_result()))
				throw new Exception(flowdata.getRun_msg());
		}else{
			throw new Exception(order_id+"订单不在号卡开户预提交环节");
		}
	}
	
	/**
	 * 处理自定义流程--卡数据同步
	 * @param order_id
	 * @throws Exception
	 */
	private void dealWorkCustomWriteCardResult(String order_id) throws Exception{
		
		String instance_id = this.workCustomEngine.checkCurrNodes(order_id, 
				"卡数据同步", "WORKFLOW_MOBILE_WRITE_CARD_RESULT");
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(instance_id)){
			WORK_CUSTOM_FLOW_DATA flowdata = this.workCustomEngine.runNodeManual(instance_id, 
					false, "", "卡数据同步", Const.WRITE_CARD_RESULT);
			
			if(ConstsCore.ERROR_FAIL.equals(flowdata.getRun_result()))
				throw new Exception(flowdata.getRun_msg());
		}else{
			throw new Exception(order_id+"订单不在卡数据同步环节");
		}
	}
	
	private void dealWorkCustomOrderArchive(String order_id) throws Exception{
		
		String instance_id = this.workCustomEngine.checkCurrNodes(order_id, 
				"订单归档", "WORKFLOW_ORDER_ARCHIVE");
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(instance_id)){
			WORK_CUSTOM_FLOW_DATA flowdata = this.workCustomEngine.runNodeManual(instance_id, 
					false, "", "订单归档", Const.ORDER_ARCHIVE);
			
			if(ConstsCore.ERROR_FAIL.equals(flowdata.getRun_result()))
				throw new Exception(flowdata.getRun_msg());
		}else{
			throw new Exception(order_id+"订单不在订单归档环节");
		}
	}
	
	   /**
     * 简单订单信息查询
     * @author 黄斯达
     * @data 2019-3-8
     */
    @Override
    public OrderListActivateResp queryOrderActivate(OrderListActivateReq req) {
        return ecsOrdManager.queryOrderActivate(req);
    }

	/**
	 * 押金业务收单接口
	 *
	 * @param requ
	 * @return
	 * @throws Exception
	 *
	 * @author wjq
	 *
	 * @date 2019年1月31日
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public OrderMakeupResp orderDepositInsert(OrderDepositReq requ) throws Exception {
		MarkTime mark = new MarkTime("orderDepositInsert out_order_id="+requ.getOut_order_id());
		
		OrderMakeupResp resp = new OrderMakeupResp();
		try {
			// 通过商品id获取信息
			String prod_offer_type = CommonDataFactory.getInstance().getGoodSpec(null, requ.getProd_offer_code(),
					SpecConsts.CAT_ID);
			requ.setProd_offer_type(prod_offer_type);
			// 校验数据正确性
			String msg = depositCheck(requ);
			String code = "1";
			
			OrderTreeBusiRequest orderTree = null;
			
			if (StringUtil.isEmpty(msg)) {
				Map manualOrder = JSONObject.fromObject(requ);
				OrderCtnResp orderCtnResp = orderExtManager.insertManualOrder(manualOrder, "D");
				if ("0".equals(orderCtnResp.getError_code())) {
					msg = "处理成功";
					code = "0";
					// 返回订单中心订单号
					
					orderTree = CommonDataFactory.getInstance()
							.getOrderTreeByOutId(orderCtnResp.getBase_order_id());

					if (orderTree != null) {
						resp.setOrder_id(orderTree.getOrder_id());
					}
				} else {
					msg = orderCtnResp.getError_msg();
				}
			}else{
				resp.setResp_code("1");
				resp.setResp_msg(msg);
				
				mark.markEndTime("orderDepositInsert fail out_order_id="+requ.getOut_order_id());
				
				return resp;
			}
			resp.setResp_code(code);
			resp.setResp_msg(msg);
			if("1".equals(code)){
			    return resp;
			}
			// 数据入库

			CommonDataFactory.getInstance().updateAttrFieldValue(
					orderTree.getOrder_id(),
					new String[] { AttrConsts.IS_PAY },
					new String[] { requ.getIs_pay() });
		} catch (Exception e) {
			resp.setResp_code("1");
			resp.setResp_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			
			logger.error("orderDepositInsert error,out_order_id="+requ.getOut_order_id()+",error info:"+e.getMessage(),e);
			
			mark.markEndTime("orderDepositInsert error out_order_id="+requ.getOut_order_id());
		}
		
		mark.markEndTime("orderDepositInsert success out_order_id="+requ.getOut_order_id());
		
		return resp;
	}
	
	private String depositCheck(OrderDepositReq requ) {
		String msg = "";
		if (StringUtils.isEmpty(requ.getSerial_no())) {// 序列号
			msg = "序列号(serial_no)不能为空";
		} else if (StringUtils.isEmpty(requ.getCreate_time())) {// 时间
			msg = "时间(time)不能为空";
		} else if (StringUtils.isEmpty(requ.getSource_system())) {// 发起方系统标识
			msg = "发起方系统标识(source_system)不能为空";
		} else if (StringUtils.isEmpty(requ.getSource_system_type())) {// 发起方系统标识分类
			msg = "发起方系统标识分类(source_system_type)不能为空";
		} else if (StringUtils.isEmpty(requ.getOut_order_id())) {// 外系统单号
			msg = "外系统单号(out_order_id)不能为空";
		} else if (StringUtils.isEmpty(requ.getOrder_province_code())) {// 订单归属省份
			msg = "订单归属省份(order_province_code)不能为空";
		} else if (StringUtils.isEmpty(requ.getOrder_city_code())) {// 订单归属地市
			msg = "订单归属地市(order_city_code)不能为空";
		} else if (StringUtils.isEmpty(requ.getService_num())) {// 业务号码
			msg = "业务号码(service_num)不能为空";
		} else if (StringUtils.isEmpty(requ.getProd_offer_name())) {// 商品名称
			msg = "商品名称(prod_offer_name)不能为空";
		} else if (StringUtils.isEmpty(requ.getIs_pay())) {//是否收费
			msg = "是否收费(is_pay)不能为空";
		} else if (StringUtils.isEmpty(requ.getFee_type())) {// 押金项目
			msg = "押金项目(fee_type)不能为空";
		} else if (StringUtils.isEmpty(requ.getOrig_fee())) {//应收费用
			msg = "应收费用(orig_fee)不能为空";
		} else if (StringUtils.isEmpty(requ.getRelief_fee())) {// 减免费用
			msg = "减免费用(relief_fee)不能为空";
		} else if (StringUtils.isEmpty(requ.getReal_fee())) {// 实收费用
			msg = "实收费用(real_fee)不能为空";
		} else if (StringUtils.isEmpty(requ.getFee_rule_id())) {// 费用规则id
			msg = "费用规则id(fee_rule_id)不能为空";
		} else if (StringUtils.isEmpty(requ.getDeal_operator())) {// 办理操作员
			msg = "办理操作员(deal_operator)不能为空";
		} else if (StringUtils.isEmpty(requ.getDeal_office_id())) {// 办理操作点
			msg = "办理操作点员(deal_office_id)不能为空";
		}
		return msg;
	}

	// 押金预提交接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "押金预提交接口", summary = "押金预提交接口")
	public DepositSubResp depositOrderSub(DepositOrderReq req) throws ApiBusiException {

		DepositSubResp resp = new DepositSubResp();
        CommCaller caller = new CommCaller();
        Map<String, Object> param = new HashMap<String, Object>();
        try {
            CommonDataFactory.getInstance().updateOrderTree(req.getNotNeedReqStrOrderId());
            BeanUtils.bean2MapForAiPBSS(param, req);
            param.put("ord_id", req.getNotNeedReqStrOrderId());
            resp = (DepositSubResp) caller.invoke("ecaop.trades.serv.busi.deposit.sub", param);
            if (!StringUtils.isEmpty(resp.getCode()) && "00000".equals(resp.getCode()) ) {
                OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance()
                        .getOrderTree(req.getNotNeedReqStrOrderId());
                List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
                OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0)
                        .getOrderItemExtBusiRequest();
                String bms_accept_id = resp.getBms_accept_id();
				if (!bms_accept_id.isEmpty()) {
					orderItemExtBusiRequest.setBssOrderId(bms_accept_id);
					orderItemExtBusiRequest.setActive_no(bms_accept_id);
					orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					orderItemExtBusiRequest.store();
				}
            } else {
                resp.setCode("-9999");
                resp.setError_msg(resp.getMsg());
            }

        } catch (Exception e) {// 调用接口失败
            resp.setCode("-9999");
            resp.setMsg(e.getMessage());
            resp.setError_msg(e.getMessage());
            e.printStackTrace();
        }
        return resp;
    }

	@Override
	public DeliveryInfoUpdateResp deliveryInfoUpdate(DeliveryInfoUpdateReq req) {
		
		DeliveryInfoUpdateResp resp = new DeliveryInfoUpdateResp();
		resp.setResp_code("1");
		
		// 获取订单中心物流公司编码 
		List<String> listMap = ecsOrdManager.getLogiCompanyCodeList();
		Set<String> beansSet =new  HashSet<String>(listMap);
		
         // 校验物流公司编码是否在数据库存在 
		if(beansSet.contains(req.getLogi_company_code())) {
			resp.setResp_msg("订单中心不存在物流公司编码:"+req.getLogi_company_code());
			return resp;
		}
		
		String [] str =new String[2];
        str[0] = req.getLogi_no();
        str[1] = req.getLogi_company_code();
		
		try {
			// 沉淀物流公司编码 物流单号
			CommonDataFactory.getInstance().updateAttrFieldValue(req.getOrder_id(),
					new String[] { "logi_no", "shipping_company" }, str);

			// 物流信息更新成功 继续下一步
			String instance_id = this.workCustomEngine.checkCurrNodes(req.getOrder_id(), "物流信息更新",
					"WORKFLOW_DELIVERY_INFO_NODES");

			if (org.apache.commons.lang.StringUtils.isNotBlank(instance_id)) {
				WORK_CUSTOM_FLOW_DATA flowdata = this.workCustomEngine.runNodeManual(instance_id, false, "", "物流信息更新",
						Const.UPDATE_DELIVERY);

				if (ConstsCore.ERROR_FAIL.equals(flowdata.getRun_result()))
					throw new Exception(flowdata.getRun_msg());
			} else { 
				resp.setResp_msg("订单不在物流信息更新环节");
				return resp;
			}
			resp.setResp_code("0");
			resp.setResp_msg("物流信息更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setResp_msg(e.getMessage());
		}
		return resp;
	}
	/**
	 * 选、预占号码查询
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "选、预占号码查询", summary = "选、预占号码查询")
	public ResourCecenterAppResp QuerySelectNumber(QuerySelectNumberReq req)throws Exception{
		ResourCecenterAppResp resp = new ResourCecenterAppResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (ResourCecenterAppResp) caller.invoke("ecaop.trades.serv.resource.center.app", param);
		} catch (Exception e) {// 调用接口失败
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}
	
	/*private CardInfoGetAPPResp getCardInfos(OrderInternetBusiRequest orderInternetBusiRequest){
		CardInfoGetAPPResp resp = new CardInfoGetAPPResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		String order_id = orderInternetBusiRequest.getOrder_id();
		param.put("ord_id", order_id);
		param.put("operatorId", CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR));
		param.put("province", "36");
		String city = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);
		if (city.trim().length()!=3) {
			city = CommonDataFactory.getInstance().getOtherDictVodeValue("city", city);
		}
		param.put("city", city);
		param.put("district", CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.DISTRICT_CODE));
		param.put("channelId", CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CHA_CODE));
		String channelType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CHANNEL_TYPE);
		param.put("channelType", channelType.trim().length()==0?"1030100":channelType);
		param.put("cardUseType", EcsOrderConsts.AOP_CARD_USE_TYPE_0);
		String reDoTag = "";
		if (StringUtils.isEmpty(orderInternetBusiRequest.getOld_iccid())) {//没有记录老ICCID，首次读卡
			reDoTag = EcsOrderConsts.AOP_RE_DO_TAG_0;
		}else if (orderInternetBusiRequest.getOld_iccid().equalsIgnoreCase(orderInternetBusiRequest.getIccid())) {//新老ICCID一样，重复读卡
			reDoTag = EcsOrderConsts.AOP_RE_DO_TAG_1;
		}else {//新老ICCID不一样，新卡
			reDoTag = EcsOrderConsts.AOP_RE_DO_TAG_0;
		}
		param.put("reDoTag", reDoTag);
		String net_type = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		String opeSysType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_operSys_by_mobileNet", net_type);
		param.put("opeSysType", opeSysType);
		param.put("procId", orderInternetBusiRequest.getProc_id());
		param.put("activeId", orderInternetBusiRequest.getActive_id());
		param.put("iccid", orderInternetBusiRequest.getIccid());
		param.put("oldIccid", orderInternetBusiRequest.getOld_iccid());
		param.put("numId", orderInternetBusiRequest.getService_num());
		param.put("busiType", EcsOrderConsts.AOP_BUSI_TYPE_32);
		String goodType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		param.put("cardType", CommonDataFactory.getInstance().getOtherDictVodeValue("aop_whitecard_type", goodType));
		String serType = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE);
		param.put("userType", CommonDataFactory.getInstance().getOtherDictVodeValue("aop_pay_type", serType));
		String allocationFlag = EcsOrderConsts.AOP_ALLOCATION_FLAG_0;
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		if (cacheUtil.getConfigInfo("ALLOCATIONFLAG_ORDER_FROM").contains(
				CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM))) {
			allocationFlag = EcsOrderConsts.AOP_ALLOCATION_FLAG_1;
		}
		param.put("allocationFlag", allocationFlag);
		
		String opcode = EcsOrderConsts.CARD_DATE_QUERY;
		String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
		if (!StringUtils.isEmpty(bizkey))
			param.put("bizkey", "TS-3G-005");

		CardDataQryResponse rsp = (CardDataQryResponse) caller.invoke(opcode, param);
		if(StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"")){
			orderInternetBusiRequest.setOld_iccid(orderInternetBusiRequest.getIccid());
			orderInternetBusiRequest.setIccid(rsp.getIccid());
			orderInternetBusiRequest.setSimid(rsp.getImsi());
			orderInternetBusiRequest.setProc_id(rsp.getProcId());
			orderInternetBusiRequest.setActive_id(rsp.getActiveId());
			orderInternetBusiRequest.setScript_seq(rsp.getScriptSeq());
			orderInternetBusiRequest.setCapacity_type_code(rsp.getCapacityTypeCode());
			orderInternetBusiRequest.setRes_kind_code(rsp.getResKindCode());
			orderInternetBusiRequest.setWrite_card_status(EcsOrderConsts.WRITE_CARD_STATUS_3);
			orderInternetBusiRequest.setCard_data(rsp.getCardData());
			OrderActionLogReq logReq = new OrderActionLogReq();
			logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_09);
			logReq.setAction_desc("获取取写卡数据");
			logReq.setOrder_id(order_id);
			AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
			Map cardInfo = new HashMap();
			cardInfo.put("scriptseq", rsp.getScriptSeq());
			cardInfo.put("proc_id", rsp.getProcId());
			cardInfo.put("imsi", rsp.getImsi());
			resp.setCard_Info(cardInfo);
			resp.setResp_code("00000");
			resp.setResp_msg("success");
		}else{
			resp.setResp_code("1");
			resp.setResp_msg(rsp.getError_msg());
		}
		return resp;
	}*/

	@Override
	public WeiBoShortUrlResp getShortUrl (WeiBoShortUrlReq req){
		WeiBoShortUrlResp resp = new WeiBoShortUrlResp();
		try{
			CommCaller caller = new CommCaller();
	        Map<String, Object> param = new HashMap<String, Object>();
	        BeanUtils.bean2MapForAiPBSS(param, req);
	        param.put("ord_id", req.getNotNeedReqStrOrderId());
	        resp = (WeiBoShortUrlResp) caller.invoke("otherServlet.xinlangMessage.message.http", param);
		}catch(Exception e){
			e.printStackTrace();
			resp.setUrls(new ArrayList<WeiBoShortUrlVO>());
		}
		return resp;
	}
	private boolean checkWorkCustomFinish(String order_id){
		boolean flag = false;
		String sql = " select count(1) from es_work_custom_workflow_ins_f where order_id='"+order_id+"' ";
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		int num = baseDaoSupport.queryForInt(sql, null);
		if(num>0){
			flag = true;
		}
		return flag;
	}
	
	/*public MobileComteSaleResp mobileComteSale(MobileComteSaleReq req){
		MobileComteSaleResp resp = new MobileComteSaleResp();
		try{
			CommCaller caller = new CommCaller();
			Map<String,Object> param = new HashMap<String,Object>();
			BeanUtils.bean2MapForAOP(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (MobileComteSaleResp) caller.invoke("", param);
		}catch(Exception e){
			e.printStackTrace();
		}
		return resp;
	}*/
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "号码手动释放", summary = "号码手动释放")
	public ResourCecenterAppResp RelSelectionNum(RelSelectionNumReq req)throws Exception{
		ResourCecenterAppResp resp = new ResourCecenterAppResp();
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			BeanUtils.bean2MapForAiPBSS(param, req);
			param.put("ord_id", req.getNotNeedReqStrOrderId());
			resp = (ResourCecenterAppResp) caller.invoke("ecaop.trades.serv.resource.center.app", param);
		} catch (Exception e) {// 调用接口失败
			resp.setCode("-9999");
			resp.setMsg(e.getMessage());
			resp.setError_msg(e.getMessage());
			e.printStackTrace();
		}

		return resp;
	}

	@Override
	public GeneralOrderQueryResp generalOrderQuery(GeneralOrderQueryReq req) {
		//
		GeneralOrderQueryResp resp = new GeneralOrderQueryResp();
		resp.setOrder_info(new ArrayList<GeneralOrderInfo>());
		try {
			resp = orderInfManager.generalOrderQuery(req);
		} catch (Exception e) {
			resp.setResp_code("1");
			resp.setResp_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
			e.printStackTrace();
		}

		return resp;
	}

}
