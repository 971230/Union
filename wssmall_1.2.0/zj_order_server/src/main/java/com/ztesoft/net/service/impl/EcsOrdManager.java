package com.ztesoft.net.service.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.drools.core.util.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.ListUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.inf.communication.client.util.SFUtil;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.cache.common.SerializeList;
import com.ztesoft.net.ecsord.params.ecaop.req.O2OStatusUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderListActivateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ProductSubReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.O2OStatusUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderListActivateResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.MSGPKGVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderQueryActivateInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderReceiveVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.PKGBODYVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.PKGHEADVO;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.model.Logi;
import com.ztesoft.net.mall.core.model.OrderReleaseRecord;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IDictManager;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.service.impl.cache.DictManagerCacheProxy;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AopQueryDataVo;
import com.ztesoft.net.model.AutoOrderTree;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.model.EntityInfoVO;
import com.ztesoft.net.model.LockOrder;
import com.ztesoft.net.model.OrdReceipt;
import com.ztesoft.net.model.OrderBatchPreModel;
import com.ztesoft.net.model.OrderBtn;
import com.ztesoft.net.model.OrderQueryParams;
import com.ztesoft.net.model.OrderWarningResult;
import com.ztesoft.net.model.WriteCardPercent;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrdArchiveTacheManager;
import com.ztesoft.net.service.IOrderExtManager;
import com.ztesoft.net.service.IOrderInfManager;
import com.ztesoft.net.service.IWorkPoolManager;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.net.sqls.SqlInBuilder;
import com.ztesoft.net.sqls.SqlUtil;
import com.ztesoft.net.util.ZjCommonUtils;

import commons.CommonTools;
import consts.ConstsCore;
import edu.emory.mathcs.backport.java.util.Arrays;
import params.adminuser.req.AdminUserReq;
import params.adminuser.resp.AdminUserResp;
import params.order.req.OrderExceptionCollectReq;
import params.order.req.OrderHandleLogsReq;
import params.req.HeadquartersMallBusiRequest;
import services.AdminUserInf;
import util.EssOperatorInfoUtil;
import zte.net.ecsord.common.AttrBusiInstTools;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.base.po.ATTR_FIELD_TABLE;
import zte.net.ecsord.params.base.po.ATTR_OPTIONS;
import zte.net.ecsord.params.bss.req.NumberStateChangeBss1Request;
import zte.net.ecsord.params.bss.resp.NumberStateChangeBssResp;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageSubProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtvtlBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSpProductBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.TerminalStatusQueryChanage1Req;
import zte.net.ecsord.params.ecaop.req.TerminalStatusQueryChanageReq;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ResourcesInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ResourcesRspVo;
import zte.net.ecsord.params.ecaop.resp.EmpIdEssIDResp;
import zte.net.ecsord.params.ecaop.resp.TerminalStatusQueryChanageResp;
import zte.net.ecsord.params.sf.req.OrderSearchServiceRequest;
import zte.net.ecsord.params.sf.resp.OrderSearchServiceResponse;
import zte.net.ecsord.params.sf.resp.RoutePushServiceResponse;
import zte.net.ecsord.params.sf.vo.SFBodyVO;
import zte.net.ecsord.params.sf.vo.WaybillRoute;
import zte.net.ecsord.params.wyg.req.WYGTestRequest;
import zte.net.ecsord.params.wyg.resp.WYGTestResponse;
import zte.net.ecsord.params.zb.req.NumberStateChangeZB1Request;
import zte.net.ecsord.params.zb.resp.NumberStateChangeZBResponse;
import zte.net.ecsord.params.zb.vo.ResourcesInfo;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.iservice.IOrderServices;
import zte.params.goodstype.req.GoodsTypeGetReq;
import zte.params.goodstype.resp.GoodsTypeGetResp;
import zte.params.order.req.OrderCollect;
/**
 * 订单处理类
 * 
 * @作者 MoChunrun
 * @创建日期 2014-9-30
 * @版本 V 1.0
 */
public class EcsOrdManager extends BaseSupport implements IEcsOrdManager {

    public static final String QUERY_TYPE_RETURNED = "returned";
    public static final String QUERY_TYPE_ORDER = "order";
    public static final String QUERY_TYPE_EXCEPTION = "exception";
    public static final String QUERY_TYPE_RETURNED_CFM = "returned_cfm";
    public static final String QUERY_TYPE_REFUND_APPLY = "refund_apply"; 
    public static final String QUERY_TYPE_REFUND_AUDIT = "refund_audit";
    public static final String QUERY_TYPE_RETURNED_CFMN = "returned_cfmn";
    public static final String QUERY_TYPE_RETURNED_BSS = "returned_bss";
    public static final String QUERY_TYPE_YCL = "ycl";// 预处理
    public static final String D_TYPE_CFM = "cfm";// 申请确认
    public static final String QUERY_TYPE_SUPPLY = "supply";
    public static final String QUERY_TYPE_ORDER_VIEW = "order_view";// 查看所有单
    public static final String QUERY_TYPE_ORDER_RECOVER = "order_recover";// 归档订单恢复
    public static final String QUERY_TYPE_LOGISTICS = "logistics";// 物流单号补录列表
    public static final String QUERY_TYPE_LOGISTICS_PRINT = "logistics_print";// 物流单号打印列表
    public static final String QUERY_TYPE_BSS_PARALLEL = "bss_parallel";
    public static final String QUERY_TYPE_ORDER_QH = "order_qh";// 取货页面
    public static final String QUERY_TYPE_ORDER_RECEIVE = "order_receive";// 订单领取
    public static final String QUERY_TYPE_ORDER_RECEIVE2 = "order_receive2";// 订单领取-新
    @Resource
    protected IDcPublicInfoManager dcPublicInfoManager;
    @Resource
    private IOrdArchiveTacheManager ordArchiveTacheManager;
    @Resource
    private IOrderServices orderServices;
    @Resource
    private AdminUserInf adminUserServ;
    @Resource
    private IOrderExtManager orderExtManager;
    @Resource
    private IWorkPoolManager workPoolManager;
    private static Logger logger = Logger.getLogger(EcsOrdManager.class);
    static INetCache cache;
    static {
        cache = (INetCache) com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
    }
    public static int NAMESPACE = 300;
    static int time = 60 * 24 * 60 * 5;// 缺省缓存20天,memcache最大有效期是30天

    @Override
    public String getSequences(String seq_name) {
        return this.baseDaoSupport.getSequences(seq_name);
    }
    
    /**
     * 根据订单编号查询订单操作类型
     * @param list
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String,String> getOrderOptTypesByOrderIds(List<OrderBusiRequest> list){
    	Map<String,String> ret = new HashMap<String,String>();
    	
    	if (list!=null && !list.isEmpty()){
    		List<String> orderIdList = new ArrayList<String>();
    		
    		for(OrderBusiRequest order : list){
    			orderIdList.add(order.getOrder_id());
    		}
    		
    		StringBuilder builder = new StringBuilder();
    		builder.append(" select a.order_id, a.opttype from es_order_extvtl  a where 1=1 ");
    		builder.append(SqlUtil.getSqlInStr("a.order_id", orderIdList, false, true));
    		
    		List<Map> retlist = this.baseDaoSupport.queryForList(builder.toString());
    		
    		if(retlist!=null && !retlist.isEmpty()){
    			Iterator<Map> it = retlist.iterator();
    			for(;it.hasNext();){
    				Map m = it.next();
    				
    				if(m != null ){
    					String order_id = String.valueOf(m.get("order_id"));
    					String opttype = String.valueOf(m.get("opttype"));
    					
    					if(org.apache.commons.lang.StringUtils.isNotBlank(opttype) 
    							&& !"null".equals(opttype)){
    						ret.put(order_id, opttype);
    					}
    				}
    			}
    		}
    	}
    	
    	return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page queryOrderForPageBuyFlow(int pageNo, int pageSize, OrderQueryParams params) {
        long start = System.currentTimeMillis();
        Long time1 = System.currentTimeMillis();
        String work_operator="";
        String work_state="";
        AdminUser user = ManagerUtils.getAdminUser();
        String user_id = user.getUserid();
        // List<OrderTreeBusiRequest>
        List sqlParams = new ArrayList();
        List sqlParams_count = new ArrayList();
        String sql = getOrderQuerySql(params, sqlParams, false);// 生成查询语句
        Long time2 = System.currentTimeMillis();
        logger.info("性能测试****************************订单查询getOrderQuerySql总耗时：" + (time2 - time1));
        logger.info(sql);
        String countSql = "select count(1) from (" + getOrderQuerySql(params, sqlParams_count, true) + ") cord";
        if (StringUtil.equals(params.getOrder_status(), "11")) {
            sql += " order by oe.tid_time asc";
        } else {
            sql += " order by oe.tid_time desc";
        }
     logger.info("订单查询Sql:"+sql);
        Page page = this.daoSupportForAsyCount.queryForCPage(sql, pageNo, pageSize, OrderBusiRequest.class, countSql,
                sqlParams.toArray());
        // Page page = this.baseDaoSupportfor.queryForCPage(sql, pageNo, pageSize,
        // OrderBusiRequest.class, countSql, sqlParams.toArray());

        long end = System.currentTimeMillis();
        logger.info("订单列表查询时间========>>>>>" + (end - start));
        Long time3 = System.currentTimeMillis();
        logger.info("性能测试****************************订单查询数据库总耗时：" + (time3 - time2));
        if (page == null)
            return page;
        DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
        List<Map> sourceFrom = dcPublicCache.getList(EcsOrderConsts.SOURCE_FROM);
        List<Map> send_type = dcPublicCache.getList(EcsOrderConsts.shipping_type_cache);
        List<Map> flowTrace = dcPublicCache.getList(EcsOrderConsts.DIC_ORDER_NODE);
        List<Map> archiveType = dcPublicCache.getList(EcsOrderConsts.ARCHIVE_TYPE);
        List<OrderBusiRequest> list = page.getResult();
        Long time4 = System.currentTimeMillis();
        logger.info("性能测试****************************订单查询数据字典查询总耗时：" + (time4 - time3));
        if (list == null)
            return page;
        
        Map<String,String> orderOptTypeMap = this.getOrderOptTypesByOrderIds(list);
        
        List<AutoOrderTree> tree = new ArrayList<AutoOrderTree>();
        for (OrderBusiRequest ob : list) { 
            Long time11 = System.currentTimeMillis();
            OrderTreeBusiRequest orderTree = null; 
            try {
                if (params != null && EcsOrderConsts.IS_ORDER_HIS_YES.equals(params.getOrder_is_his())) {// 历史单
                    orderTree = CommonDataFactory.getInstance().getOrderTreeHis(ob.getOrder_id());
                } else {
                    orderTree = CommonDataFactory.getInstance().getOrderTree(ob.getOrder_id());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            String optType = CommonDataFactory.getInstance().getAttrFieldValue(orderTree.getOrder_id(), AttrConsts.optType);
            
            Long time12 = System.currentTimeMillis();
            logger.info("性能测试****************************订单查询订单树查询耗时：" + (time12 - time11));
            // 获取流程页面访问地址
            if (orderTree != null) {
                AutoOrderTree ot = new AutoOrderTree();
                String ess_pre_desc = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest()
                        .getEss_pre_desc();
                String bss_pre_desc = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest()
                        .getBss_pre_desc();
                String bss_time_type = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest()
                        .getBss_time_type() + "";
                String order_id =orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getOrder_id();
                //查询工单SQL
                String work_sql="select * from (select ewo.operator_name,ewo.status,ewo.source_from from es_work_order ewo "
                				+"where ewo.order_id='"+order_id+"' order by ewo.create_time desc) where rownum=1";
                List<Map>  work_List=daoSupport.queryForList(work_sql);
                for (Map work_maps : work_List) {
                	for (Object k : work_maps.keySet()){ 
                		work_operator = (String) work_maps.get("operator_name");
                        work_state = (String) work_maps.get("status");
                	}
				}
                if (!StringUtil.isEmpty(ess_pre_desc) && ess_pre_desc.length() > 30)
                    ess_pre_desc = ess_pre_desc.substring(0, 30);
                if (!StringUtil.isEmpty(bss_pre_desc) && bss_pre_desc.length() > 30)
                    bss_pre_desc = bss_pre_desc.substring(0, 30);
                ot.setEss_pre_desc(ess_pre_desc);
                ot.setBss_pre_desc(bss_pre_desc);
                ot.setBss_time_type(bss_time_type);
                ot.setWork_operator(work_operator);
                ot.setWork_state(work_state);
                // 设置锁定样式

                List<OrderLockBusiRequest> orderLockBusiRequestsList = orderTree.getOrderLockBusiRequests();
                String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
                boolean isMyLock = false;
                int isLock = 0;
                if (orderLockBusiRequestsList != null && orderLockBusiRequestsList.size() != 0) {
                    for (int i = 0; i < orderLockBusiRequestsList.size(); i++) {
                        OrderLockBusiRequest orderLockBusiRequest = orderLockBusiRequestsList.get(i);
                        // 锁定时间比较值
                        int lock_time_flag = 0;
                        try {
                            if (!StringUtil.isEmpty(orderLockBusiRequest.getLock_end_time()))
                                lock_time_flag = DateUtil.compareDate(DateUtil.getTime2(),
                                        orderLockBusiRequest.getLock_end_time(), DateUtil.DATE_FORMAT_2);
                        } catch (FrameException e) {
                            logger.info(" 当前时间 和 订单锁定结束时间 比较失败");
                            e.printStackTrace();
                        }
                        // 当前时间大于锁定结束时间的自动释放订单锁
                        if (lock_time_flag > 0)
                            CommonDataFactory.getInstance().unlockWorkerPool(orderLockBusiRequest.getOrder_id());
                        if (EcsOrdManager.QUERY_TYPE_BSS_PARALLEL.equals(params.getQuery_type())) {
                            if ("F,H,J,L".contains(trace_id) && "Y2".equals(orderLockBusiRequest.getTache_code())
                                    && EcsOrderConsts.LOCK_STATUS.equals(orderLockBusiRequest.getLock_status())
                                    && lock_time_flag <= 0) {
                                isLock++;
                                if (user.getUserid().equals(orderLockBusiRequest.getLock_user_id()))
                                    isMyLock = true;
                            }
                        } else {
                            if (!StringUtil.isEmpty(trace_id) && trace_id.equals(orderLockBusiRequest.getTache_code())
                                    && EcsOrderConsts.LOCK_STATUS.equals(orderLockBusiRequest.getLock_status())
                                    && lock_time_flag <= 0) {
                                isLock++;
                                if (user.getUserid().equals(orderLockBusiRequest.getLock_user_id()))
                                    isMyLock = true;
                            }
                            if (!StringUtil.isEmpty(trace_id)
                                    && trace_id.equals(orderLockBusiRequest.getTache_code())) {
                                /*
                                 * ot.setLock_user_name(getCacheName("DC_LOCK_USERNAME",orderLockBusiRequest.
                                 * getLock_user_id()));
                                 */
                                if (!(StringUtil.isEmpty(orderLockBusiRequest.getLock_user_id()))) {
                                    //判断锁定人是不是团队  add by wjq
                                    if ("team".equals(orderLockBusiRequest.getDealer_type()) || "org".equals(orderLockBusiRequest.getDealer_type())) {
                                        ot.setLock_user_realname(orderLockBusiRequest.getLock_user_name());
                                        /* ot.setLock_user_id(orderLockBusiRequest.getLock_user_id()); */
                                        ot.setLock_user_name(orderLockBusiRequest.getLock_user_id());
                                    }else{
                                        String sql_locker = "select e.username, e.realname from es_adminuser e where e.userid = '"
                                                + orderLockBusiRequest.getLock_user_id() + "'";
                                        List<Map> maps=this.baseDaoSupport.queryForList(sql_locker, null);
                                        if (maps.size()>0) {
                                            ot.setLock_user_realname((String) maps.get(0).get("realname"));
                                            /* ot.setLock_user_id(orderLockBusiRequest.getLock_user_id()); */
                                            ot.setLock_user_name((String) maps.get(0).get("username"));
                                        }else{
                                            ot.setLock_user_realname(orderLockBusiRequest.getLock_user_name());
                                            /* ot.setLock_user_id(orderLockBusiRequest.getLock_user_id()); */
                                            ot.setLock_user_name(orderLockBusiRequest.getLock_user_id());
                                        }
//                                      
                                    }
                                }
                            }
                        }
                    }
                }

                if (isLock > 0) {
                    if (isMyLock == true) {
                        ot.setLock_clazz("unlock");
                    } else {
                        ot.setLock_clazz("otherlock");
                    }
                } else {
                    ot.setLock_clazz("lock");
                }

                Long time13 = System.currentTimeMillis();
                logger.info("性能测试****************************订单查询订单锁定状态封装耗时：" + (time13 - time12));

                String action_url = null;// EcsOrderConsts.YCL_URL;
                if (QUERY_TYPE_RETURNED.equals(params.getQuery_type())) {
                    action_url = EcsOrderConsts.RETURNED_DETAIL_URL;
                } else if (QUERY_TYPE_RETURNED_CFM.equals(params.getQuery_type())
                        || QUERY_TYPE_RETURNED_CFMN.equals(params.getQuery_type())
                        || QUERY_TYPE_RETURNED_BSS.equals(params.getQuery_type())) {
                    action_url = EcsOrderConsts.RETURNED_DETAIL_URL + "?d_type=" + D_TYPE_CFM + "&query_type="
                            + params.getQuery_type();
                } else if (QUERY_TYPE_REFUND_APPLY.equals(params.getQuery_type())) {
                    action_url = EcsOrderConsts.REFUND_DETAIL_URL;
                } else if (QUERY_TYPE_REFUND_AUDIT.equals(params.getQuery_type())) {
                    action_url = EcsOrderConsts.REFUND_DETAIL_URL + "?d_type=" + D_TYPE_CFM;
                } else if (QUERY_TYPE_YCL.equals(params.getQuery_type())) {
                    action_url = EcsOrderConsts.REPLENISH_URL + "?d_type=" + QUERY_TYPE_YCL;
                } else if (EcsOrdManager.QUERY_TYPE_SUPPLY.equals(params.getQuery_type())) {
                    if (params != null && EcsOrderConsts.IS_ORDER_HIS_YES.equals(params.getOrder_is_his())) {// 历史单
                        action_url = EcsOrderConsts.SUPPLY_URL + "?order_is_his=" + EcsOrderConsts.IS_ORDER_HIS_YES;
                    } else {
                        action_url = EcsOrderConsts.SUPPLY_URL;
                    }
                } else if (EcsOrdManager.QUERY_TYPE_ORDER_VIEW.equals(params.getQuery_type())) {
                    if (params != null && EcsOrderConsts.IS_ORDER_HIS_YES.equals(params.getOrder_is_his())) {// 历史单
                        action_url = EcsOrderConsts.ORDER_VIEW_DETAIL_URL + "?order_is_his="
                                + EcsOrderConsts.IS_ORDER_HIS_YES;
                    }else {
                        action_url = EcsOrderConsts.ORDER_VIEW_DETAIL_URL;
                    }
                } else if (EcsOrdManager.QUERY_TYPE_ORDER_RECOVER.equals(params.getQuery_type())) {
                    action_url = EcsOrderConsts.ORDER_RECOVER_URL;
                }

                String shipping_type = orderTree.getOrderBusiRequest().getShipping_type();
                if (send_type != null && shipping_type != null) {
                    for (Map m : send_type) {
                        String pkey = (String) m.get("pkey");
                        if (shipping_type.equals(pkey)) {
                            ot.setShipping_type((String) m.get("pname"));
                            break;
                        }
                    }
                }
                // 订单环节

                String flow_trace = ob.getFlow_trace_id();

                if (flowTrace != null && flow_trace != null) {
                    for (Map f : flowTrace) {
                        String pkey = (String) f.get("pkey");
                        if (flow_trace.equals(pkey)) {
                            ot.setFlow_trace((String) f.get("pname"));
                            break;
                        }
                    }
                }
                String refund_deal_type = orderTree.getOrderExtBusiRequest().getRefund_deal_type();
                String shipping_quick = orderTree.getOrderExtBusiRequest().getShipping_quick();
                Integer order_status = orderTree.getOrderBusiRequest().getStatus();
                if (StringUtil.equals("02", refund_deal_type)
                        && StringUtil.equals(QUERY_TYPE_ORDER_VIEW, params.getQuery_type())
                        && !StringUtils.isEmpty(params.getQuery_type())) {

                    if (order_status == 11) {
                        ot.setFlow_trace("退单,外呼");
                    } else {
                        ot.setFlow_trace("退单");
                    }
                } else {
                    if (order_status == 11) {
                        ot.setFlow_trace("外呼");
                    }
                }

                String ofrom = orderTree.getOrderExtBusiRequest().getOrder_from();
                if (!StringUtil.isEmpty(ofrom)) {
                    for (Map s : sourceFrom) {
                        String key = (String) s.get("pkey");
                        if (key != null && key.equals(ofrom)) {
                            ot.setOrder_from_c((String) s.get("pname"));
                            break;
                        }
                    }
                }
                String phone_num = "";
                String terminal = "";
                String goods_package = "";
                String city_name = ""; // 城市
                String goods_type_name = "";// 商品类型名称
                String goods_name = "";// 商品名称
                String offer_name = "";// 套餐名称
                String bss_status = AttrUtils.getInstance().getAttrFieldValue(orderTree, ob.getOrder_id(),
                        AttrConsts.BSS_STATUS, "", "");// 业务办理状态 是否已办理:1.是；0.否
                String pay_type_name = "";
                String channel_mark = "";
                String wms_refund_status = "";
                String color = "";
                String oper_remark = "";// 外呼原因
                if ("11".equals(params.getOrder_status())) {
                    ot.setOper_remark(ob.getOper_remark());
                }
                ot.setOutcall_type_c(ob.getOutcall_type_c());
                if (orderTree != null) {
                    String pay_type = orderTree.getOrderExtBusiRequest().getPay_type();
                    pay_type_name = getCacheName("DIC_PAY_TYPE", pay_type);

                    String channel_mark_code = AttrUtils.getInstance().getAttrFieldValue(orderTree,
                            orderTree.getOrder_id(), AttrConsts.CHANNEL_MARK, "", "");
                    channel_mark = getCacheName("DIC_CHANNEL_MARK", channel_mark_code);

                    String wms_refund_status_code = AttrUtils.getInstance().getAttrFieldValue(orderTree,
                            orderTree.getOrder_id(), AttrConsts.WMS_REFUND_STATUS, "", "");
                    wms_refund_status = getCacheName("WMS_REFUND_STATUS", wms_refund_status_code);

                    ot.setPay_type_name(pay_type_name);
                    ot.setChannel_mark(channel_mark);
                    ot.setWms_refund_status(wms_refund_status);
                    if (orderTree.getOrderDeliveryBusiRequests() != null
                            && orderTree.getOrderDeliveryBusiRequests().size() > 0
                            && orderTree.getOrderDeliveryBusiRequests().get(0).getShip_status() != null) {
                        ot.setShip_status(orderTree.getOrderDeliveryBusiRequests().get(0).getShip_status());
                    }
                }
                /**
                 * 业务办理状态标志开始
                 */
                List<OrderSpProductBusiRequest> spProductList = orderTree.getSpProductBusiRequest();
                if (null != spProductList) {// 这里应不会出现空指针
                    for (OrderSpProductBusiRequest spProduct : spProductList) {// 若存在未办理成功的SP产品，则标记业务未办理
                        if (!(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2.equals(spProduct.getStatus())
                                || EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3.equals(spProduct.getStatus()))) {
                            bss_status = EcsOrderConsts.NO_DEFAULT_VALUE;
                            break;
                        }
                    }
                }
                if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(bss_status)) {
                    List<AttrPackageSubProdBusiRequest> attrPackageSubProdBusiRequest = orderTree
                            .getAttrPackageSubProdBusiRequest();
                    if (null != attrPackageSubProdBusiRequest) {// 这里应不会出现空指针
                        for (AttrPackageSubProdBusiRequest attrPackageSubProduct : attrPackageSubProdBusiRequest) {// 若存在未办理成功的附加产品，则标记业务未办理
                            if (!(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2.equals(attrPackageSubProduct.getStatus())
                                    || EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3
                                            .equals(attrPackageSubProduct.getStatus()))) {
                                bss_status = EcsOrderConsts.NO_DEFAULT_VALUE;
                                break;
                            }
                        }
                    }
                }
                /**
                 * 业务办理状态标志结束
                 */
                if (params != null && EcsOrderConsts.IS_ORDER_HIS_YES.equals(params.getOrder_is_his())) {// 历史单
                    phone_num = CommonDataFactory.getInstance().getAttrFieldValueHis(ob.getOrder_id(),
                            AttrConsts.PHONE_NUM);
                    /*terminal = CommonDataFactory.getInstance().getProductSpecHis(ob.getOrder_id(),
                            SpecConsts.TYPE_ID_10000, null, SpecConsts.GOODS_NAME);*/
                    terminal = CommonDataFactory.getInstance().getAttrFieldValueHis(ob.getOrder_id(),"object_name");
                    color = CommonDataFactory.getInstance().getProductSpecHis(ob.getOrder_id(),
                            SpecConsts.TYPE_ID_10000, null, SpecConsts.COLOR_NAME);
                    goods_package = CommonDataFactory.getInstance().getAttrFieldValueHis(ob.getOrder_id(),
                            "plan_title");
                    // bss_status=CommonDataFactory.getInstance().getAttrFieldValueHis(ob.getOrder_id(),
                    // AttrConsts.BSS_STATUS,"","");
                    // 归档类型
                    String archive_type = orderTree.getOrderExtBusiRequest().getArchive_type();
                    if (archiveType != null && archive_type != null) {
                        for (Map f : archiveType) {
                            String pkey = (String) f.get("pkey");
                            if (archive_type.equals(pkey)) {
                                ot.setArchive_type((String) f.get("pname"));
                                break;
                            }
                        }
                    }
                } else {
                    phone_num = AttrUtils.getInstance().getAttrFieldValue(orderTree, ob.getOrder_id(),
                            AttrConsts.PHONE_NUM, "", "");
                    /*terminal = CommonDataFactory.getInstance().getProductSpec(ob.getOrder_id(),
                            SpecConsts.TYPE_ID_10000, null, SpecConsts.GOODS_NAME);*/
                    terminal = CommonDataFactory.getInstance().getAttrFieldValueHis(ob.getOrder_id(),"object_name");
                    color = CommonDataFactory.getInstance().getProductSpec(ob.getOrder_id(), SpecConsts.TYPE_ID_10000,
                            null, SpecConsts.COLOR_NAME);
                    goods_package = AttrUtils.getInstance().getAttrFieldValue(orderTree, ob.getOrder_id(), "plan_title",
                            "", "");
                    // bss_status=AttrUtils.getInstance().getAttrFieldValue(ob.getOrder_id(),
                    // AttrConsts.BSS_STATUS,"","");
                    // DC_MODE_REGION
                    String city_code = AttrUtils.getInstance().getAttrFieldValue(orderTree, ob.getOrder_id(),
                            AttrConsts.ORDER_CITY_CODE, "", "");
                    city_name = getCacheName("DC_MODE_REGION", city_code);

                    goods_name = AttrUtils.getInstance().getAttrFieldValue(orderTree, ob.getOrder_id(),
                            AttrConsts.GOODSNAME, "", "");
                    // offer_name = CommonDataFactory.getInstance().getProductSpec(ob.getOrder_id(),
                    // SpecConsts.TYPE_ID_10002, SpecConsts.PLAN_TITLE);
                    offer_name = CommonDataFactory.getInstance().getAttrFieldValue(orderTree, ob.getOrder_id(),
                            AttrConsts.PLAN_TITLE, "", "");
                    String goods_type = AttrUtils.getInstance().getAttrFieldValue(orderTree, ob.getOrder_id(),
                            AttrConsts.GOODS_TYPE, "", "");
                    goods_type_name = getCacheName("DC_MODE_GOODS_TYPE", goods_type);

                }
                
                ot.setOrderTree(orderTree);
                ot.setTid_time(orderTree.getOrderExtBusiRequest().getTid_time());
                ot.setOut_tid(orderTree.getOrderExtBusiRequest().getOut_tid());
                ot.setAction_url(action_url);
                ot.setPhone_num(phone_num);
                ot.setGoods_package(goods_package);
                ot.setTerminal(terminal);
                ot.setBss_status(bss_status);
                ot.setGoods_type_name(goods_type_name);
                ot.setGoods_name(goods_name);
                ot.setOffer_name(offer_name);
                ot.setCity_name(city_name);
                String receiver_mobile = CommonDataFactory.getInstance().getAttrFieldValue(orderTree,
                        orderTree.getOrder_id(), "receiver_mobile");
                String ship_addr = CommonDataFactory.getInstance().getAttrFieldValue(orderTree, orderTree.getOrder_id(),
                        "ship_addr");
                String pay_method = CommonDataFactory.getInstance().getAttrFieldValue(orderTree,
                        orderTree.getOrder_id(), "pay_method");
                pay_method = AttrUtils.getInstance().getDcPublicDataByPkey("pay_way", pay_method);
                String specificatio_nname = CommonDataFactory.getInstance().getAttrFieldValue(orderTree,
                        orderTree.getOrder_id(), "specificatio_nname");
                String activity_name = "";
                if (orderTree.getDiscountInfoBusiRequests() instanceof List) {
                    if (!ListUtil.isEmpty(orderTree.getDiscountInfoBusiRequests())) {
                        activity_name = orderTree.getDiscountInfoBusiRequests().get(0).getActivity_name();
                    }
                }
                String ship_name = CommonDataFactory.getInstance().getAttrFieldValue(orderTree, orderTree.getOrder_id(),
                        "ship_name");
                String if_Send_Photos = CommonDataFactory.getInstance().getAttrFieldValue(orderTree,
                        orderTree.getOrder_id(), AttrConsts.CERT_UPLOAD_STATUS);
                ot.setReceiver_mobile(receiver_mobile);
                ot.setShip_addr(ship_addr);
                ot.setPay_method(pay_method);
                ot.setSpecificatio_nname(specificatio_nname);
                ot.setActivity_name(activity_name);
                ot.setShip_name(ship_name);
                ot.setIf_Send_Photos(if_Send_Photos);
                
                // add by zhaochen 20190428 增加操作类型字段
                if(orderOptTypeMap.containsKey(ob.getOrder_id())){
                	ot.setOptType(orderOptTypeMap.get(ob.getOrder_id()));
                }
                
                // otb.getOrderExtBusiRequest().load(instQuery);
                tree.add(ot);
                Long time14 = System.currentTimeMillis();
                logger.info("性能测试****************************订单查询订单树封装耗时：" + (time14 - time13));
               /*本次不上线
                * List<OrderInternetBusiRequest> lists = orderTree.getOrderInternetBusiRequest();
                List<OrderAdslBusiRequest> orderAdslBusiRequest = CommonDataFactory.getInstance().getOrderTree(ob.getOrder_id()).getOrderAdslBusiRequest();
                List<Map<String, Object>> phone_nusList = new ArrayList<Map<String,Object>>();
                List<String> phone_infoMOBILE  = new ArrayList<String>();//MOBILE：移网成员
                Map map = new HashMap();
                if(lists.size()>0 && !lists.isEmpty()){
                    for (int i = 0; i < lists.size(); i++) {
                        OrderInternetBusiRequest sBusiRequest = lists.get(i);
                        String phone_n = sBusiRequest.getService_num();
                        String inf = sBusiRequest.getSubs_id();//先模拟数据信息
                        phone_infoMOBILE.add(phone_n);
                    
                    }
                
                }
            
                List<String> phone_infoFIX  = new ArrayList<String>();//FIX：固网成员  
                if(orderAdslBusiRequest.size()>0 && !orderAdslBusiRequest.isEmpty()){//这张表展示为宽带号码
                    for (int i = 0; i < orderAdslBusiRequest.size(); i++) {
                        OrderAdslBusiRequest orderAdsl = orderAdslBusiRequest.get(i);
                        String phone_n = orderAdsl.getAdsl_number();
                        String inf = orderAdsl.getProduct_type();//先模拟数据信息
                      
                        phone_infoFIX.add(phone_n);
                    }
                
                }
                map.put("KD", phone_infoFIX);
                map.put("YW",phone_infoMOBILE);
                phone_nusList.add(map);
                ot.setPhone_infoList(phone_nusList);
                String mix_all_prod = orderTree.getOrder_id();//字段需要重新取值代码需要合并
                if(!StringUtil.isEmpty(mix_all_prod) && mix_all_prod.equals("D90000000001462366")){
                    ot.setMix_all_prod("1");
                }else{
                    ot.setMix_all_prod("0");
                }*/
                /*测试数据页面是已经处理了的
                List<String> phone_info11  = new ArrayList<String>();//宽带 kd
                List<String> phone_info1233  = new ArrayList<String>();//宽带 kd

                phone_info11.add("12331");
                phone_info11.add("1231");
                phone_info1233.add("12331");
                phone_info1233.add("1231");
                Map map = new HashMap();
                map.put("KDD","KDD");
                map.put("KDD1",phone_info11);
                map.put("YW",phone_info1233);

                phone_nusList.add(map);
                ot.setPhone_infoList(phone_nusList);
                if(ob.getOrder_id().equals("D90000000001461901")){
                    ot.setMix_all_prod("0");
                }else{
                    ot.setMinx_all("1");
                }*/
            }
        }
        long end2 = System.currentTimeMillis();
        logger.info("获取订单树数据时间========>>>>>" + (end2 - end));
        page.setResult(tree);
        return page; 
    }

    @Override
    public Page queryOrderForPageBuyFlowNew(int pageNo, int pageSize, OrderQueryParams params) {
        long start = System.currentTimeMillis();
        AdminUser user = ManagerUtils.getAdminUser();
        List<String> sqlParams = new ArrayList<String>();
        List<String> sqlParams_count = new ArrayList<String>();
        String sql = getOrderQuerySqlNew(params, sqlParams, false);// 生成查询语句
        logger.info("订单处理查询sql：" + sql);
        String countSql = "select count(1) from (" + getOrderQuerySqlNew(params, sqlParams_count, true) + ") cord";
        if (StringUtil.equals(params.getOrder_status(), "11")) {
            sql += " order by oe.tid_time asc";
        } else {
            sql += " order by oe.tid_time desc";
        }

        Page page = this.daoSupportForAsyCount.queryForCPage(sql, pageNo, pageSize, AutoOrderTree.class, countSql,
                sqlParams.toArray());
        // Page page = this.baseDaoSupportfor.queryForCPage(sql, pageNo,
        // pageSize, OrderBusiRequest.class, countSql, sqlParams.toArray());

        long end = System.currentTimeMillis();
        logger.info("订单列表查询时间========>>>>>" + (end - start));
        if (page == null)
            return page;
        DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
        List<Map<String, String>> sourceFrom = dcPublicCache.getList(EcsOrderConsts.SOURCE_FROM);
        List<Map<String, String>> send_type = dcPublicCache.getList(EcsOrderConsts.shipping_type_cache);
        List<Map<String, String>> flowTrace = dcPublicCache.getList(EcsOrderConsts.DIC_ORDER_NODE);
        // List<Map<String, String>> archiveType =
        // dcPublicCache.getList(EcsOrderConsts.ARCHIVE_TYPE);
        List<AutoOrderTree> list = page.getResult();
        if (list == null) {
            return page;
        }

        for (AutoOrderTree tree : list) {
            OrderTreeBusiRequest orderTree = null;
            if (params != null && EcsOrderConsts.IS_ORDER_HIS_YES.equals(params.getOrder_is_his())) {// 历史单
                orderTree = CommonDataFactory.getInstance().getOrderTreeHis(tree.getOrder_id());
            } else {
                orderTree = CommonDataFactory.getInstance().getOrderTree(tree.getOrder_id());
            }

            // //获取流程页面访问地址
            if (orderTree != null) {
                String bss_time_type = "";
                if (null != orderTree.getOrderItemBusiRequests()) {
                    if (null != orderTree.getOrderItemBusiRequests()) {
                        bss_time_type = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest()
                                .getBss_time_type() + "";
                    }
                }

                tree.setBss_time_type(bss_time_type);
                // //设置锁定样式
                List<OrderLockBusiRequest> orderLockBusiRequestsList = orderTree.getOrderLockBusiRequests();
                String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
                boolean isMyLock = false;
                int isLock = 0;
                if (orderLockBusiRequestsList != null && orderLockBusiRequestsList.size() != 0) {
                    for (int i = 0; i < orderLockBusiRequestsList.size(); i++) {
                        OrderLockBusiRequest orderLockBusiRequest = orderLockBusiRequestsList.get(i);
                        // 锁定时间比较值
                        int lock_time_flag = 0;
                        try {
                            if (!StringUtil.isEmpty(orderLockBusiRequest.getLock_end_time())) {
                                lock_time_flag = DateUtil.compareDate(DateUtil.getTime2(),
                                        orderLockBusiRequest.getLock_end_time(), DateUtil.DATE_FORMAT_2);
                            }
                        } catch (FrameException e) {
                            logger.info(" 当前时间 和 订单锁定结束时间 比较失败");
                            e.printStackTrace();
                        }
                        // 当前时间大于锁定结束时间的自动释放订单锁
                        if (lock_time_flag > 0) {
                            CommonDataFactory.getInstance().unlockWorkerPool(orderLockBusiRequest.getOrder_id());
                        }
                        if (EcsOrdManager.QUERY_TYPE_BSS_PARALLEL.equals(params.getQuery_type())) {
                            if ("F,H,J,L".contains(trace_id) && "Y2".equals(orderLockBusiRequest.getTache_code())
                                    && EcsOrderConsts.LOCK_STATUS.equals(orderLockBusiRequest.getLock_status())
                                    && lock_time_flag <= 0) {
                                isLock++;
                                if (user.getUserid().equals(orderLockBusiRequest.getLock_user_id())) {
                                    isMyLock = true;
                                }
                            }
                        } else {
                            if (!StringUtil.isEmpty(trace_id) && trace_id.equals(orderLockBusiRequest.getTache_code())
                                    && EcsOrderConsts.LOCK_STATUS.equals(orderLockBusiRequest.getLock_status())
                                    && lock_time_flag <= 0) {
                                isLock++;
                                if (user.getUserid().equals(orderLockBusiRequest.getLock_user_id())) {
                                    isMyLock = true;
                                }
                            }
                            if (!StringUtil.isEmpty(trace_id)
                                    && trace_id.equals(orderLockBusiRequest.getTache_code())) {
                                String user_id = orderLockBusiRequest.getLock_user_id();
                                if (!(StringUtil.isEmpty(user_id))) {
                                    String key = "LOCK_" + user_id;
                                    SerializeList serializeList = (SerializeList) cache.get(NAMESPACE, key);
                                    List<AdminUser> userList = null;
                                    if (null != serializeList) {
                                        userList = serializeList.getObj();
                                    } else {
                                        String sql_locker = "select e.username, e.realname from es_adminuser e where e.userid = ?";
                                        serializeList = new SerializeList();
                                        userList = baseDaoSupport.queryForList(sql_locker, AdminUser.class, user_id);
                                        serializeList.setObj(userList);
                                        cache.set(NAMESPACE, key, serializeList);

                                    }
                                    if (null != userList && userList.size() > 0) {
                                        tree.setLock_user_realname(userList.get(0).getRealname());
                                        tree.setLock_user_name(userList.get(0).getUsername());
                                    }
                                }
                            }
                        }
                    }
                }

                if (isLock > 0) {
                    if (isMyLock == true) {
                        tree.setLock_clazz("unlock");
                    } else {
                        tree.setLock_clazz("otherlock");
                    }
                } else {
                    tree.setLock_clazz("lock");
                }

                String action_url = null;// EcsOrderConsts.YCL_URL;

                String shipping_type = orderTree.getOrderBusiRequest().getShipping_type();
                if (send_type != null && shipping_type != null) {
                    for (Map<String, String> m : send_type) {
                        String pkey = (String) m.get("pkey");
                        if (shipping_type.equals(pkey)) {
                            tree.setShipping_type((String) m.get("pname"));
                            break;
                        }
                    }
                }

                // 订单环节
                if (flowTrace != null && trace_id != null) {
                    for (Map<String, String> f : flowTrace) {
                        String pkey = (String) f.get("pkey");
                        if (trace_id.equals(pkey)) {
                            tree.setFlow_trace((String) f.get("pname"));
                            break;
                        }
                    }
                }
                String refund_deal_type = orderTree.getOrderExtBusiRequest().getRefund_deal_type();
                Integer order_status = orderTree.getOrderBusiRequest().getStatus();
                if (StringUtil.equals("02", refund_deal_type)
                        && StringUtil.equals(QUERY_TYPE_ORDER_VIEW, params.getQuery_type())
                        && !StringUtils.isEmpty(params.getQuery_type())) {

                    if (order_status == 11) {
                        tree.setFlow_trace("退单,外呼");
                    } else {
                        tree.setFlow_trace("退单");
                    }
                } else {
                    if (order_status == 11) {
                        tree.setFlow_trace("外呼");
                    }
                }

                String ofrom = orderTree.getOrderExtBusiRequest().getOrder_from();
                if (!StringUtil.isEmpty(ofrom)) {
                    for (Map<String, String> s : sourceFrom) {
                        String key = (String) s.get("pkey");
                        if (key != null && key.equals(ofrom)) {
                            tree.setOrder_from_c((String) s.get("pname"));
                            break;
                        }
                    }
                }
                String phone_num = "";
                String city_name = ""; // 城市
                String goods_name = "";// 商品名称

                if (params != null && EcsOrderConsts.IS_ORDER_HIS_YES.equals(params.getOrder_is_his())) {// 历史单
                    if (null != orderTree.getOrderItemBusiRequests()) {
                        phone_num = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest()
                                .getPhone_num();
                    }
                } else {
                    if (null != orderTree.getOrderItemBusiRequests()) {
                        phone_num = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest()
                                .getPhone_num();
                        goods_name = orderTree.getOrderItemBusiRequests().get(0).getName();
                    }

                    String city_code = orderTree.getOrderExtBusiRequest().getOrder_city_code();
                    city_name = getCacheName("DC_MODE_REGION", city_code);

                }

                tree.setOrderTree(orderTree);
                tree.setTid_time(orderTree.getOrderExtBusiRequest().getTid_time());
                tree.setOut_tid(orderTree.getOrderExtBusiRequest().getOut_tid());
                tree.setAction_url(action_url);
                tree.setPhone_num(phone_num);
                tree.setGoods_name(goods_name);
                tree.setCity_name(city_name);
                String if_Send_Photos = orderTree.getOrderExtBusiRequest().getIf_Send_Photos();
                tree.setIf_Send_Photos(if_Send_Photos);
            }
        }
        long end2 = System.currentTimeMillis();
        logger.info("获取订单树数据时间========>>>>>" + (end2 - end));
        page.setResult(list);
        return page;
    }

    /***
     * 优化查询条件 add by hxm 2018.5.31
     * 
     * @param params
     * @param sqlParams
     * @param is_count
     * @return
     */
    private String getOrderQuerySqlNew(OrderQueryParams params, List<String> sqlParams, boolean is_count) {
        String his = "_his";
        String order_table = "es_order";
        String order_ext_table = "es_order_ext";
        String es_delivery = "es_delivery";
        String es_order_items_ext_table = "es_order_items_ext";
        String es_order_items_table = "es_order_items";
        String es_payment_logs_table = "es_payment_logs";
        String es_delivery_item_table = "es_delivery_item";
        String es_order_extvtl = "es_order_extvtl";
        String es_attr_gift_info = "es_attr_gift_info";
        String es_order_items_prod_ext = "es_order_items_prod_ext";
        String es_order_sp_product = "es_order_sp_product";
        String es_attr_package_subprod = "es_attr_package_subprod";
        if (params != null && EcsOrderConsts.IS_ORDER_HIS_YES.equals(params.getOrder_is_his())) {// 历史单
            order_table = order_table + his;
            order_ext_table = order_ext_table + his;
            es_delivery = es_delivery + his;
            es_order_items_ext_table = es_order_items_ext_table + his;
            es_order_items_table = es_order_items_table + his;
            es_payment_logs_table = es_payment_logs_table + his;
            es_delivery_item_table = es_delivery_item_table + his;
            es_order_extvtl = es_order_extvtl + his;
            es_attr_gift_info = es_attr_gift_info + his;
            es_order_items_prod_ext = es_order_items_prod_ext + his;
            es_order_sp_product = es_order_sp_product + his;
            es_attr_package_subprod = es_attr_package_subprod + his;
        }

        // 基本语句
        String sql = "select " + (is_count ? "1"
                : "o.*,oe.flow_trace_id,(select t.bss_time_type from es_order_items_ext t where t.order_id=o.order_id) bss_time_type")
                + ("11".equals(params.getOrder_status())
                        ? " ,ol.OPER_REMARK ,(select d.PNAME from es_dc_public_ext d where d.PKEY=ol.DEAL_REMARK and d.STYPE='OUTCALL_TYPE') outcall_type_c"
                        : " ")
                + " from " + order_table + " o," + order_ext_table + " oe "
                + ("11".equals(params.getOrder_status())
                        ? " ,(select l.* from es_order_outcall_log l where is_finish = 0) ol"
                        : " ")
                + " where o.order_id=oe.order_id and o.source_from=oe.source_from and o.source_from=? ";
        sqlParams.add(ManagerUtils.getSourceFrom());
        AdminUser user = ManagerUtils.getAdminUser();
        String user_id = user.getUserid();

        // 订单状态
        if (!"order_view".equals(params.getQuery_type())) {
            if (params.getOrder_status() != null
                    && ZjEcsOrderConsts.DIC_ORDER_STATUS_11 == Integer.valueOf(params.getOrder_status())) {
                sql += " and o.order_id = ol.order_id and o.status= 11 ";
            } else {
                sql += " and o.status <> 11 ";
            }
        }

        // 把作废单排除---zengxianlian
        sql += " and oe.order_if_cancel ='0' ";

        // 用来特殊控制订单是否隐藏
        sql += " and not exists(select 1 from es_order_hide eoh where eoh.order_id = oe.order_id) ";

        // 增加权限
        int founder = user.getFounder();
        if (params == null || !EcsOrderConsts.IS_ORDER_HIS_YES.equals(params.getOrder_is_his())) {// 非历史单
            if (true && (params == null || (!QUERY_TYPE_RETURNED.equals(params.getQuery_type())
                    && !QUERY_TYPE_RETURNED_CFM.equals(params.getQuery_type())
                    && !QUERY_TYPE_REFUND_APPLY.equals(params.getQuery_type())
                    && !QUERY_TYPE_REFUND_AUDIT.equals(params.getQuery_type())
                    && !QUERY_TYPE_RETURNED_CFMN.equals(params.getQuery_type())
                    && !QUERY_TYPE_RETURNED_BSS.equals(params.getQuery_type())
                    && !QUERY_TYPE_ORDER_VIEW.equals(params.getQuery_type())
                    && !QUERY_TYPE_EXCEPTION.equals(params.getQuery_type())))) {
                // 把异常关键字is_order_sys_view字段配置为N的异常单排除掉，为了让正常单与异常单的展示互斥
                // 互斥不做一刀切，根据异常关键字的配置逐个进行
                sql += " and not exists (select 1 from  es_esearch_expinst ei,es_esearch_specvalues es "
                        + " where ei.out_tid=oe.out_tid and ei.key_id = es.key_id " + " and ei.record_status = '"
                        + EcsOrderConsts.EXPINST_RECORD_STATUS_0 + "' and es.is_order_sys_view = '"
                        + EcsOrderConsts.EXP_SPECVALUES_IS_ORDER_SYS_VIEW_N + "' )";
            }
        }

        if (params != null) {
            // 数据权限，订单处理，订单发货--自己锁定的并且有环节权限的
            if (user.getFounder() != 1 && EcsOrdManager.QUERY_TYPE_ORDER.equals(params.getQuery_type())) {
                String tacheAuth = user.getTacheAuth();// 环节权限'B','',''
                String lockUserAuth = user.getLockUserAuth();// 锁定工号权限，可以查询工号下锁定的订单
                if (!StringUtils.isEmpty(tacheAuth)) {
                    sql += " and oe.Flow_Trace_Id in (" + tacheAuth + ") ";// 环节权限
                } else {
                    sql += " and 1=2 ";
                }
                if (user.getFounder() != 2) {// 普通管理员不需要过滤锁定权限
                    // 锁定工号权限过滤
                    if (StringUtils.isEmpty(lockUserAuth)) {// 如果为空则只查询自己锁定的订单
                        sql += " and exists (select 1 from es_order_lock eol where o.order_id = eol.order_id and eol.lock_status = '1' and eol.lock_user_id = '"
                                + user_id + "' and eol.tache_code = oe.Flow_Trace_Id )";// 当前用户锁定
                    } else if (!StringUtils.isEmpty(lockUserAuth) && lockUserAuth.contains("'1'")) {// 如果锁定工号权限未全部，则查询所有工号锁定的订单
                        sql += " and exists (select 1 from es_order_lock eol where o.order_id = eol.order_id and eol.lock_status = '1' and eol.tache_code = oe.Flow_Trace_Id )";// 当前用户锁定
                    } else {// 查询拥有锁定工号权限工号锁定的订单
                        lockUserAuth += ",'" + ManagerUtils.getAdminUser().getUserid() + "'";// 增加登录用户本身锁定
                        sql += " and exists (select 1 from es_order_lock eol where o.order_id = eol.order_id and eol.lock_status = '1' and eol.lock_user_id in("
                                + lockUserAuth + ") and eol.tache_code = oe.Flow_Trace_Id )";// 当前用户锁定
                    }
                }
            }
            // 业务办理查询
            if (user.getFounder() != 1 && EcsOrdManager.QUERY_TYPE_BSS_PARALLEL.equals(params.getQuery_type())) {
                String tacheAuth = user.getTacheAuth();// 环节权限'B','',''
                if (!StringUtils.isEmpty(tacheAuth) && tacheAuth.contains("Y2")) {
                    sql += " and oe.flow_trace_id in('F','H','J','L')";
                } else {
                    sql += " and 1=2 ";
                }
                if (user.getFounder() != 2) {
                    sql += " and not exists (select 1 from es_order_lock eol where o.order_id = eol.order_id and eol.lock_status = '1' and eol.lock_user_id <> '"
                            + user_id + "' and eol.tache_code = 'Y2' )";// 当前用户锁定
                }
            }
            // 数据权限，订单领取--环节权限加领取工号锁定的订单
            if (user.getFounder() != 1 && (EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE.equals(params.getQuery_type())
                    || EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE2.equals(params.getQuery_type()))) {
                // if(EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE.equals(params.getQuery_type())){//取消对超级管理员账号的限制
                String tacheAuth = user.getTacheAuth();// 环节权限

                if (!StringUtils.isEmpty(tacheAuth)) {
                    sql += " and oe.Flow_Trace_Id in (" + tacheAuth + ") ";// 环节权限
                } else {
                    sql += " and 1=2 ";
                }
                // 查询未被领取的订单
                if (!EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE2.equals(params.getQuery_type())) {
                    sql += " and not exists (select 1 from es_order_lock eol where o.order_id = eol.order_id and eol.lock_status = '1' and eol.tache_code = oe.Flow_Trace_Id)";
                    sql += " and o.status not in(9,10,13)";// 查询不显示状态为9，10，13的单子
                }

            }
            if (user.getFounder() != 1 && EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE2.equals(params.getQuery_type())) {
                String receiveUserAuth = user.getReceiveUserAuth();// 订单领取工号权限
                sql += " and exists (select 1 from es_order_lock eol where o.order_id = eol.order_id and eol.lock_status = '1' and eol.lock_user_id in ("
                        + receiveUserAuth + ") and eol.tache_code = oe.Flow_Trace_Id)";
                sql += " and o.status not in(9,10,13)";// 查询不显示状态为9，10，13的单子
            }
            if (EcsOrdManager.QUERY_TYPE_ORDER_QH.equals(params.getQuery_type())) {
                sql += " and oe.flow_trace_id in('H')";
            }

            if (QUERY_TYPE_RETURNED.equals(params.getQuery_type())) {
                sql += " and oe.REFUND_DEAL_TYPE!='" + EcsOrderConsts.REFUND_DEAL_TYPE_02 + "'";
            }
            if ((EcsOrdManager.QUERY_TYPE_RETURNED_CFM.equals(params.getQuery_type())
                    || EcsOrdManager.QUERY_TYPE_RETURNED_CFMN.equals(params.getQuery_type())
                    || QUERY_TYPE_RETURNED_BSS.equals(params.getQuery_type())
                    || EcsOrdManager.QUERY_TYPE_RETURNED.equals(params.getQuery_type()))// 退单申请、退单确认不显示华盛订单
                    && founder != 1) {// 超级工号可以查看华盛订单
                sql += " AND oe.plat_type != '" + EcsOrderConsts.PLAT_TYPE_10061 + "' ";
            }
            // 查询需要退款订单
            if (QUERY_TYPE_REFUND_APPLY.equals(params.getQuery_type())) {
                sql += " and oe.is_refund='1' and exists(select 1 from es_order_extvtl v where v.order_id=oe.order_id and v.source_from=oe.source_from and oe.refund_status='01' and (v.bss_refund_status is null or v.bss_refund_status in ('0')))";
                sql += " and oe.REFUND_DEAL_TYPE='" + EcsOrderConsts.REFUND_DEAL_TYPE_02 + "'";
            }
            // 查询退款审核订单
            if (QUERY_TYPE_REFUND_AUDIT.equals(params.getQuery_type())) {
                sql += " and oe.is_refund='1' and exists(select 1 from es_order_extvtl v where v.order_id=oe.order_id and v.source_from=oe.source_from and v.bss_refund_status in('1','2','3','4','5'))";
                sql += " and oe.REFUND_DEAL_TYPE='" + EcsOrderConsts.REFUND_DEAL_TYPE_02 + "'";
            }
            // 开户号码
            if (!StringUtil.isEmpty(params.getPhone_num())) {
                sql += " and exists(select 1 from " + es_order_items_ext_table
                        + " t where t.order_id=o.order_id and t.source_from=o.source_from and t.phone_num=?)";
                sqlParams.add(params.getPhone_num());
            }
            // 是否当月处理
            if (!StringUtil.isEmpty(params.getBss_time_type())) {
                sql += " and exists(select 1 from " + es_order_items_ext_table
                        + " t where t.order_id=o.order_id and t.source_from=o.source_from and t.bss_time_type=?)";
                sqlParams.add(params.getBss_time_type());
            }

            if (params.getVisible_status() != -1) {
                sql += " and oe.visible_status=? ";
                sqlParams.add(params.getVisible_status() + "");
            }
            if (!StringUtil.isEmpty(params.getOrder_from()) && !"-1".equals(params.getOrder_from())) {
                sql += " and oe.order_from in('" + params.getOrder_from().replace(",", "','") + "')";
            }
            // 订单归属地市 es_order->order_city_code
            if (!StringUtil.isEmpty(params.getOrder_city_code()) && !"-1".equals(params.getOrder_city_code())) {
                sql += " and oe.order_city_code in('" + params.getOrder_city_code().replace(",", "','") + "')";
            }
            // 物流单号
            if (!StringUtils.isEmpty(params.getLogi_no())) {
                sql += " and o.order_id in (select t.order_id from " + es_delivery + " t where t.logi_no=?)";
                sqlParams.add(params.getLogi_no());
            }
            // 商品包类型 es_goods_type->type_id
            if (!StringUtil.isEmpty(params.getGoods_pagekage_type()) && !"-1".equals(params.getGoods_pagekage_type())) {
                sql += " and exists(select 1 from " + es_order_items_ext_table
                        + " oi where oi.ORDER_ID=o.ORDER_ID and oi.source_from=o.source_from and oi.GOODS_TYPE=?) ";
                sqlParams.add(params.getGoods_pagekage_type());
            }
            // 机型 es_goods->model_code 前台传的可能是model_code,也可能是model_name所以两个值都需要去匹配
            if (!StringUtil.isEmpty(params.getModel_code())) {
                sql += " and exists(select 1 from " + es_order_items_table
                        + " oi,es_goods gt where oi.goods_id=gt.goods_id and oi.source_from=gt.source_from and oi.source_from=o.source_from and oi.order_id=o.order_id and gt.model_code in(select model_code from es_brand_model where (model_code = ? or model_name like '%"
                        + params.getModel_code() + "%' ) and source_from=oi.source_from) ) ";
                sqlParams.add(params.getModel_code());
            }
            // 创建时间 ES_ORDER_EXT->tid_time
            if (StringUtils.isEmpty(params.getOrder_id()) && StringUtils.isEmpty(params.getOut_tid())
                    && !StringUtil.isEmpty(params.getCreate_start())) {
                sql += " and oe.tid_time>=" + DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_start());
            }
            // 创建时间 ES_ORDER_EXT->tid_time
            if (StringUtils.isEmpty(params.getOrder_id()) && StringUtils.isEmpty(params.getOut_tid())
                    && !StringUtil.isEmpty(params.getCreate_end())) {
                sql += " and oe.tid_time<=" + DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_end());
            }
            // 订单环节流程ID ES_ORDER_EXT->flow_id
            if (!StringUtil.isEmpty(params.getFlow_id()) && !"-1".equals(params.getFlow_id())) {
                String flowId = params.getFlow_id();
                sql += " and oe.flow_trace_id in ('" + flowId.replace(",", "','") + "') ";
            }
            // (是否存在)待办业务; 0:不存在;1存在
            if (!StringUtil.isEmpty(params.getExists_business_to_deal_with())) {
                if (EcsOrderConsts.EXISTS_BUSINESS_TO_DEAL_WITH_1.equals(params.getExists_business_to_deal_with())) {
                    sql += " and ( exists(select 1 from " + es_order_sp_product
                            + " eosp where o.order_id=eosp.order_id and o.source_from=eosp.source_from and eosp.status not in ('"
                            + EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2 + "','"
                            + EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3 + "') )" + // 数据产生即有值'0'，不存在null情况
                            "or exists (select 1 from " + es_attr_package_subprod
                            + " eaps where o.order_id=eaps.order_id and o.source_from=eaps.source_from and (eaps.status is null or eaps.status not in ('"
                            + EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2 + "','"
                            + EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3 + "'))))"
                            + "and oe.flow_trace_id in ('F','H','J','L')";// 只查询业务办理后的环节的订单
                }
                if (EcsOrderConsts.EXISTS_BUSINESS_TO_DEAL_WITH_0.equals(params.getExists_business_to_deal_with())) {
                    sql += " and not exists(select 1 from " + es_order_sp_product
                            + " eosp where o.order_id=eosp.order_id and o.source_from=eosp.source_from and eosp.status not in ('"
                            + EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2 + "','"
                            + EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3 + "') )" + // 数据产生即有值'0'，不存在null情况
                            "and not exists(select 1 from " + es_attr_package_subprod
                            + " eaps where o.order_id=eaps.order_id and o.source_from=eaps.source_from and (eaps.status is null or eaps.status not in ('"
                            + EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2 + "','"
                            + EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3 + "')))";
                }
            }
            // exception_type;//异常类型
            if (!StringUtil.isEmpty(params.getException_type()) && !"-1".equals(params.getException_type())) {
                if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_0.equals(params.getException_type())) {
                    sql += " and oe.ABNORMAL_TYPE in (?,?) ";
                    sqlParams.add(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0);
                    sqlParams.add(EcsOrderConsts.ORDER_ABNORMAL_TYPE_2);
                } else {
                    sql += " and oe.ABNORMAL_TYPE=? ";
                    sqlParams.add(params.getException_type());
                }
            }
            // 异常编码
            if (!StringUtil.isEmpty(params.getException_code()) && !"-1".equals(params.getException_code())) {
                sql += " and oe.EXCEPTION_TYPE in('" + params.getException_code().replace(",", "','") + "')";
            }
            // 条形码
            if (!StringUtil.isEmpty(params.getIccid())) {
                sql += " and exists (select 1 from  " + es_order_extvtl
                        + " ic where ic.order_id=o.order_id and substr(ic.iccid,0,length(ic.iccid)-1) = ? )";
                sqlParams.add(params.getIccid());
            }
            if (!StringUtil.isEmpty(params.getRefund_deal_type()) && !"-1".equals(params.getRefund_deal_type())) {
                sql += " and oe.REFUND_DEAL_TYPE = ? ";
                sqlParams.add(params.getRefund_deal_type());
            }
            if (!StringUtil.isEmpty(params.getOrder_model()) && !"-1".equals(params.getOrder_model())) {
                if (params.getOrder_model().indexOf(",") != -1) {
                    sql += " and oe.order_model in ('" + params.getOrder_model().replace(",", "','") + "') ";
                } else {
                    sql += " and oe.order_model = ? ";
                    sqlParams.add(params.getOrder_model());
                }
            }

            // 商品名称
            if (!StringUtil.isEmpty(params.getGoods_name()) && !"-1".equals(params.getGoods_name())) {
                sql += " and exists (select 1 from  " + es_order_extvtl
                        + " ee where ee.order_id=o.order_id and ee.GoodsName like '%" + params.getGoods_name().trim()
                        + "%')";
            }

            // 订单类型
            if (!StringUtil.isEmpty(params.getOrder_type()) && !"-1".equals(params.getOrder_type())) {
                sql += " and o.order_type in('" + params.getOrder_type().replace(",", "','") + "')";
            }
            // 外单编号 ES_ORDER_EXT->ES_ORDER_EXT
            if (!StringUtil.isEmpty(params.getOut_tid())) {
                if (EcsOrderConsts.DIC_ORDER_NODE_F.equals(params.getFlow_id())) {// 人工质检订单号是手动输入，使用模糊查询
                    sql += " and oe.out_tid like '%" + params.getOut_tid().trim() + "%'";
                } else {
                    sql += " and oe.out_tid = '" + params.getOut_tid().trim() + "'";
                }
            }
            // 内部单号 es_order->order_id
            if (!StringUtil.isEmpty(params.getOrder_id())) {
                if (EcsOrderConsts.DIC_ORDER_NODE_F.equals(params.getFlow_id())) {// 人工质检订单号是手动输入，使用模糊查询
                    sql += " and o.order_id like '%" + params.getOrder_id().trim() + "%'";
                } else {
                    sql += " and o.order_id = '" + params.getOrder_id().trim() + "'";
                }
            }

            if (!StringUtil.isEmpty(params.getLock_user_id())) {
                sql += " and exists (select 1 from  es_order_lock ol where ol.order_id=o.order_id and ol.lock_user_id='"
                        + params.getLock_user_id() + "' and oe.flow_trace_id=ol.tache_code )";
            }

        }
        return sql;
    }

    @SuppressWarnings("unchecked")
    private String getOrderQuerySql(OrderQueryParams params, List sqlParams, boolean is_count) {
        String his = "_his";
        String order_table = "es_order";
        String order_ext_table = "es_order_ext";
        String es_delivery = "es_delivery";
        String es_order_items_ext_table = "es_order_items_ext";
        String es_order_items_table = "es_order_items";
        String es_payment_logs_table = "es_payment_logs";
        String es_delivery_item_table = "es_delivery_item";
        String es_order_extvtl = "es_order_extvtl";
        String es_attr_gift_info = "es_attr_gift_info";
        String es_order_items_prod_ext = "es_order_items_prod_ext";
        String es_order_sp_product = "es_order_sp_product";
        String es_attr_package_subprod = "es_attr_package_subprod";
        String es_order_zhwq_adsl = "es_order_zhwq_adsl";
        // String es_order_items_inv_prints = "es_order_items_inv_prints";
        if (params != null && EcsOrderConsts.IS_ORDER_HIS_YES.equals(params.getOrder_is_his())) {// 历史单
            order_table = order_table + his;
            order_ext_table = order_ext_table + his;
            es_delivery = es_delivery + his;
            es_order_items_ext_table = es_order_items_ext_table + his;
            es_order_items_table = es_order_items_table + his;
            es_payment_logs_table = es_payment_logs_table + his;
            es_delivery_item_table = es_delivery_item_table + his;
            es_order_extvtl = es_order_extvtl + his;
            es_attr_gift_info = es_attr_gift_info + his;
            es_order_items_prod_ext = es_order_items_prod_ext + his;
            es_order_sp_product = es_order_sp_product + his;
            es_attr_package_subprod = es_attr_package_subprod + his;
            es_order_zhwq_adsl = es_order_zhwq_adsl + his;
            // es_order_items_inv_prints = es_order_items_inv_prints+his;
        }

        // 基本语句
        String sql = "select " + (is_count ? "1"
                : "o.*,oe.flow_trace_id,(select t.bss_time_type from es_order_items_ext t where t.order_id=o.order_id) bss_time_type"
                +",oe.is_work_custom ") // add by cqq 20181128 自定义流程标识
                + ("11".equals(params.getOrder_status())
                        ? " ,ol.OPER_REMARK ,(select d.PNAME from es_dc_public_ext d where d.PKEY=ol.DEAL_REMARK and d.STYPE='OUTCALL_TYPE') outcall_type_c"
                        : " ")
                + " from " + order_table + " o," + order_ext_table + " oe "
                + ("11".equals(params.getOrder_status())
                        ? " ,(select l.* from es_order_outcall_log l where is_finish = 0) ol"
                        : " ")
                + " where o.order_id=oe.order_id and o.source_from=oe.source_from and o.source_from=? ";
        sqlParams.add(ManagerUtils.getSourceFrom());
        AdminUser user = ManagerUtils.getAdminUser();
        String user_id = user.getUserid();

        // 订单状态
        if (!"order_view".equals(params.getQuery_type())) {
            if (params.getOrder_status() != null
                    && ZjEcsOrderConsts.DIC_ORDER_STATUS_11 == Integer.valueOf(params.getOrder_status())) {
                sql += " and o.order_id = ol.order_id and o.status= 11 ";
            } else {
                sql += " and o.status <> 11 ";
            }
        }

        // 把作废单排除---zengxianlian
        sql += " and oe.order_if_cancel ='0' ";

        // 用来特殊控制订单是否隐藏
        sql += " and not exists(select 1 from es_order_hide eoh where eoh.order_id = oe.order_id) ";

        // 增加权限
        int founder = user.getFounder();
        if (params == null || !EcsOrderConsts.IS_ORDER_HIS_YES.equals(params.getOrder_is_his())) {// 非历史单
            if (true && (params == null || (!QUERY_TYPE_RETURNED.equals(params.getQuery_type())
                    && !QUERY_TYPE_RETURNED_CFM.equals(params.getQuery_type())
                    && !QUERY_TYPE_REFUND_APPLY.equals(params.getQuery_type())
                    && !QUERY_TYPE_REFUND_AUDIT.equals(params.getQuery_type())
                    && !QUERY_TYPE_RETURNED_CFMN.equals(params.getQuery_type())
                    && !QUERY_TYPE_RETURNED_BSS.equals(params.getQuery_type())
                    && !QUERY_TYPE_ORDER_VIEW.equals(params.getQuery_type())
                    && !QUERY_TYPE_EXCEPTION.equals(params.getQuery_type())))) {
                // 把异常关键字is_order_sys_view字段配置为N的异常单排除掉，为了让正常单与异常单的展示互斥
                // 互斥不做一刀切，根据异常关键字的配置逐个进行
                sql += " and not exists (select 1 from  es_esearch_expinst ei,es_esearch_specvalues es "
                        + " where ei.out_tid=oe.out_tid and ei.key_id = es.key_id " + " and ei.record_status = '"
                        + EcsOrderConsts.EXPINST_RECORD_STATUS_0 + "' and es.is_order_sys_view = '"
                        + EcsOrderConsts.EXP_SPECVALUES_IS_ORDER_SYS_VIEW_N + "' )";
            }
        }

        if (params != null) {
            // 数据权限，订单处理，订单发货--自己锁定的并且有环节权限的
            if (user.getFounder() != 1 && EcsOrdManager.QUERY_TYPE_ORDER.equals(params.getQuery_type())) {
            	
                String tacheAuth = this.getUserFlowTraceAuth();
                
                if (!StringUtils.isEmpty(tacheAuth)) {
                    //add by cqq 20181220 增加自定义流程环节CW
                    sql += " and oe.Flow_Trace_Id in (" + tacheAuth + ") ";// 环节权限
                } else {
                    sql += " and 1=2 ";
                }
                
                String lockUserAuth = user.getLockUserAuth();// 锁定工号权限，可以查询工号下锁定的订单
                
                if (user.getFounder() != 2) {// 普通管理员不需要过滤锁定权限
                    // 锁定工号权限过滤
                    if (StringUtils.isEmpty(lockUserAuth)) {// 如果为空则只查询自己锁定的订单
                        sql += " and exists (select 1 from es_order_lock eol where o.order_id = eol.order_id and eol.lock_status = '1' and eol.lock_user_id = '"
                                + user_id + "' and eol.tache_code = oe.Flow_Trace_Id )";// 当前用户锁定
                    } else if (!StringUtils.isEmpty(lockUserAuth) && lockUserAuth.contains("'1'")) {// 如果锁定工号权限未全部，则查询所有工号锁定的订单
                        sql += " and exists (select 1 from es_order_lock eol where o.order_id = eol.order_id and eol.lock_status = '1' and eol.tache_code = oe.Flow_Trace_Id )";// 当前用户锁定
                    } else {// 查询拥有锁定工号权限工号锁定的订单
                        lockUserAuth += ",'" + ManagerUtils.getAdminUser().getUserid() + "'";// 增加登录用户本身锁定
                        sql += " and exists (select 1 from es_order_lock eol where o.order_id = eol.order_id and eol.lock_status = '1' and eol.lock_user_id in("
                                + lockUserAuth + ") and eol.tache_code = oe.Flow_Trace_Id )";// 当前用户锁定
                    }
                }
            }
            // 业务办理查询
            if (user.getFounder() != 1 && EcsOrdManager.QUERY_TYPE_BSS_PARALLEL.equals(params.getQuery_type())) {
                String tacheAuth = user.getTacheAuth();// 环节权限'B','',''
                String lockUserAuth = user.getLockUserAuth();// 锁定工号权限，可以查询工号下锁定的订单
                if (!StringUtils.isEmpty(tacheAuth) && tacheAuth.contains("Y2")) {
                    sql += " and oe.flow_trace_id in('F','H','J','L')";
                } else {
                    sql += " and 1=2 ";
                }
                if (user.getFounder() != 2) {
                    sql += " and not exists (select 1 from es_order_lock eol where o.order_id = eol.order_id and eol.lock_status = '1' and eol.lock_user_id <> '"
                            + user_id + "' and eol.tache_code = 'Y2' )";// 当前用户锁定
                    // 锁定工号权限过滤
                    /*
                     * if(!StringUtils.isEmpty(lockUserAuth) &&
                     * !lockUserAuth.contains("'1'")){//如果锁定工号权限未全部，则查询所有工号锁定的订单 lockUserAuth +=
                     * ",'"+ManagerUtils.getAdminUser().getUserid()+"'";//增加登录用户本身锁定 sql +=
                     * " and exists (select 1 from es_order_lock eol where o.order_id = eol.order_id and ((eol.lock_status = '1' and eol.lock_user_id in("
                     * +lockUserAuth+")) or eol.lock_status = '0') and eol.tache_code = 'Y2' )";//
                     * 当前用户锁定 }
                     */

                }
            }
            // 数据权限，订单领取--环节权限加领取工号锁定的订单
            if (user.getFounder() != 1 && (EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE.equals(params.getQuery_type())
                    || EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE2.equals(params.getQuery_type()))) {
            	
                String tacheAuth = this.getUserFlowTraceAuth();

                if (!StringUtils.isEmpty(tacheAuth)) {
                    //线上线下 环节 流程 GL
                    sql += " and oe.Flow_Trace_Id in (" + tacheAuth + ") ";// 环节权限
                } else {
                    sql += " and 1=2 ";
                }
                
                // 查询未被领取的订单
                if (!EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE2.equals(params.getQuery_type())) {
                	
                	// 2019-03-20 zhaochen 支持查询自定义流程未被领取的订单
                    sql += this.getOrderReveiveLockCondition();
                    
                    sql += " and o.status not in(9,10,13)";// 查询不显示状态为9，10，13的单子
                }
            }
            
            if (user.getFounder() != 1 && EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE2.equals(params.getQuery_type())) {
                String receiveUserAuth = user.getReceiveUserAuth();// 订单领取工号权限
                sql += " and exists (select 1 from es_order_lock eol where o.order_id = eol.order_id and eol.lock_status = '1' and eol.lock_user_id in ("
                        + receiveUserAuth + ") and eol.tache_code = oe.Flow_Trace_Id)";
                sql += " and o.status not in(9,10,13)";// 查询不显示状态为9，10，13的单子
            }
            if (EcsOrdManager.QUERY_TYPE_ORDER_QH.equals(params.getQuery_type())) {
                sql += " and oe.flow_trace_id in('H')";
            }

            if (QUERY_TYPE_RETURNED.equals(params.getQuery_type())) {
                sql += " and oe.REFUND_DEAL_TYPE!='" + EcsOrderConsts.REFUND_DEAL_TYPE_02 + "'";
            }
            ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
            String kg = cacheUtil.getConfigInfo("showOrderList_kg");
           //正式订单退单状态下不能领取，为避免出错，本次增加开关，若无问题可以去除开关  20190404 sgf
            if (!StringUtil.isEmpty(kg)&&"1".equals(kg) &&EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE2.equals(params.getQuery_type()) || EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE.equals(params.getQuery_type())) {
                sql += " and oe.REFUND_DEAL_TYPE !='" + EcsOrderConsts.REFUND_DEAL_TYPE_02 + "'";
            }
            
            if ((EcsOrdManager.QUERY_TYPE_RETURNED_CFM.equals(params.getQuery_type())
                    || EcsOrdManager.QUERY_TYPE_RETURNED_CFMN.equals(params.getQuery_type())
                    || QUERY_TYPE_RETURNED_BSS.equals(params.getQuery_type())
                    || EcsOrdManager.QUERY_TYPE_RETURNED.equals(params.getQuery_type()))// 退单申请、退单确认不显示华盛订单
                    && founder != 1) {// 超级工号可以查看华盛订单
                sql += " AND oe.plat_type != '" + EcsOrderConsts.PLAT_TYPE_10061 + "' ";
            }
            // 查询需要退款订单
            if (QUERY_TYPE_REFUND_APPLY.equals(params.getQuery_type())) {
                sql += " and oe.is_refund='1' and exists(select 1 from es_order_extvtl v where v.order_id=oe.order_id and v.source_from=oe.source_from and oe.refund_status='01' and (v.bss_refund_status is null or v.bss_refund_status in ('0')))";
                sql += " and oe.REFUND_DEAL_TYPE='" + EcsOrderConsts.REFUND_DEAL_TYPE_02 + "'";
            }
            // 查询退款审核订单
            if (QUERY_TYPE_REFUND_AUDIT.equals(params.getQuery_type())) {
                sql += " and oe.is_refund='1' and exists(select 1 from es_order_extvtl v where v.order_id=oe.order_id and v.source_from=oe.source_from and v.bss_refund_status in('1','2','3','4','5'))";
                sql += " and oe.REFUND_DEAL_TYPE='" + EcsOrderConsts.REFUND_DEAL_TYPE_02 + "'";
            }
            /*
             * if(!QUERY_TYPE_ORDER_VIEW.equals(params.getQuery_type())) { sql +=
             * " and (oe.is_archive='"+EcsOrderConsts.
             * NO_DEFAULT_VALUE+"' or oe.is_archive is null)"; //
             * 如果是非订单查询功能列表,则只允许查询非逻辑归档的数据 }
             */

            // 开户号码
            if (!StringUtil.isEmpty(params.getPhone_num())) {
                sql += " and exists(select 1 from " + es_order_items_ext_table
                        + " t where t.order_id=o.order_id and t.source_from=o.source_from and t.phone_num=?)";
                sqlParams.add(params.getPhone_num());
            }
            // 是否当月处理
            if (!StringUtil.isEmpty(params.getBss_time_type())) {
                sql += " and exists(select 1 from " + es_order_items_ext_table
                        + " t where t.order_id=o.order_id and t.source_from=o.source_from and t.bss_time_type=?)";
                sqlParams.add(params.getBss_time_type());
            }

            // 收货人电话
            if (!StringUtil.isEmpty(params.getShip_tel())) {
                sql += " and exists(select 1 from " + es_delivery + " t where t.order_id=o.order_id and t.ship_tel=?)";
                sqlParams.add(params.getShip_tel());
            }

            if (params.getVisible_status() != -1) {
                sql += " and oe.visible_status=? ";
                sqlParams.add(params.getVisible_status());
            }
            if (!StringUtil.isEmpty(params.getOrder_from()) && !"-1".equals(params.getOrder_from())) {
                sql += " and oe.order_from in('" + params.getOrder_from().replace(",", "','") + "')";
            }
            // 支付开始时间 es_order->pay_time
            if (!StringUtil.isEmpty(params.getPay_start())) {
                sql += " and o.pay_time>=" + DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getPay_start());
            }
            // 支付结束时间 es_order->pay_time
            if (!StringUtil.isEmpty(params.getPay_end())) {
                sql += " and o.pay_time<=" + DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getPay_end());
            }
            
            // 订单归属地市 es_order->order_city_code
            if (!StringUtil.isEmpty(params.getOrder_city_code()) && !"-1".equals(params.getOrder_city_code())) {
                
                sql += " and oe.order_city_code in('" + params.getOrder_city_code().replace(",", "','") + "')";
            }else if(EcsOrdManager.QUERY_TYPE_ORDER_VIEW.equals(params.getQuery_type())
                    && (!"1".equals(user.getPermission_level()))){
                //zhaochen 20181102 订单查询，且不是省级工号，未传地市参数时需要加上地市条件
                sql += SqlUtil.getSqlInStr("oe.order_city_code", user.getPermission_region(), false, true);
            }
            //正将上传类型 
            if(org.apache.commons.lang.StringUtils.isNotBlank(params.getIf_Send_Photos())) {
                String str = params.getIf_Send_Photos().toString();
                String[] strList = str.split(",");
                StringBuilder sb = new StringBuilder();
                for (String s : strList) {
                    sb.append("'" + s + "',");
                }
                String is_send_photos = sb.substring(0, sb.lastIndexOf(","));
                if(is_send_photos.contains("9")){
                	sql +=" and (oe.if_send_photos in ("+is_send_photos+") or oe.if_send_photos is null)";
                }else
                	sql +=" and oe.if_send_photos in ("+is_send_photos+")";
            }
            
            // 物流单号
            if (!StringUtils.isEmpty(params.getLogi_no())) {
                sql += " and o.order_id in (select t.order_id from " + es_delivery + " t where t.logi_no=?)";
                sqlParams.add(params.getLogi_no());
            }
            //联系号码
            if(org.apache.commons.lang.StringUtils.isNotBlank(params.getShip_tel())) {
                sql+=" and o.order_id  in(select t.order_id from  es_delivery t where t.ship_tel='"+params.getShip_tel()+"' )";
            }
            
            // 配送方式 es_order->shipping_id （修改：配送方式应该是shipping_type）
            String systemShipping = System.getProperty("JAVA_BUSI_SHIP_XJ");
            if (systemShipping != null && EcsOrderConsts.SHIPPING_TYPE_XJ.equals(systemShipping)) {
                sql += " and o.shipping_type = 'XJ'";
            } else {
                if (!StringUtil.isEmpty(params.getShipping_id()) && !"-1".equals(params.getShipping_id())) {
                    sql += " and o.shipping_type in('" + params.getShipping_id().replace(",", "','") + "')";
                }
            }
            String p_ordstd = System.getProperty("JAVA_BUSI_ORDCTN");
            if (!StringUtils.isEmpty(p_ordstd)) {
                if ("yes".equals(p_ordstd)) {
                    sql += " and  exists (select 1 from es_order_queue_his qhis where qhis.object_id = oe.out_tid)";
                } else {
                    sql += " and not exists (select 1 from es_order_queue_his qhis where qhis.object_id = oe.out_tid)";
                }
            }
            // 订单发展归属(订单渠道（推广渠道）) ES_ORDER_EXT->order_channel
            if (!StringUtil.isEmpty(params.getOrder_channel()) && !"-1".equals(params.getOrder_channel())) {
                sql += " and oe.channel_mark in('" + params.getOrder_channel().replace(",", "','") + "')";
                // sqlParams.add(params.getOrder_channel());
            }
            // 商品包类型 es_goods_type->type_id
            if (!StringUtil.isEmpty(params.getGoods_pagekage_type()) && !"-1".equals(params.getGoods_pagekage_type())) {
                sql += " and exists(select 1 from " + es_order_items_ext_table
                        + " oi where oi.ORDER_ID=o.ORDER_ID and oi.source_from=o.source_from and oi.GOODS_TYPE=?) ";
                sqlParams.add(params.getGoods_pagekage_type());
            }
            // 机型 es_goods->model_code 前台传的可能是model_code,也可能是model_name所以两个值都需要去匹配
            if (!StringUtil.isEmpty(params.getModel_code())) {
                sql += " and exists(select 1 from " + es_order_items_table
                        + " oi,es_goods gt where oi.goods_id=gt.goods_id and oi.source_from=gt.source_from and oi.source_from=o.source_from and oi.order_id=o.order_id and gt.model_code in(select model_code from es_brand_model where (model_code = ? or model_name like '%"
                        + params.getModel_code() + "%' ) and source_from=oi.source_from) ) ";
                sqlParams.add(params.getModel_code());
            }
            // 创建时间 ES_ORDER_EXT->tid_time
            if (StringUtils.isEmpty(params.getOrder_id()) && StringUtils.isEmpty(params.getOut_tid())
                    && !StringUtil.isEmpty(params.getCreate_start())) {
                sql += " and oe.tid_time>=" + DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_start());
            }
            // 创建时间 ES_ORDER_EXT->tid_time
            if (StringUtils.isEmpty(params.getOrder_id()) && StringUtils.isEmpty(params.getOut_tid())
                    && !StringUtil.isEmpty(params.getCreate_end())) {
                sql += " and oe.tid_time<=" + DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_end());
            }
            // 支付方式; es_order->payment_id
            if (!StringUtil.isEmpty(params.getPayment_id()) && !"-1".equals(params.getPayment_id())) {
                sql += " and exists(select 1  from " + es_payment_logs_table
                        + " plg where plg.order_id=o.order_id and plg.source_from=o.source_from and plg.pay_method in('"
                        + params.getPayment_id().replace(",", "','") + "'))";
            }
            // 订单环节流程ID ES_ORDER_EXT->flow_id
            if (!StringUtil.isEmpty(params.getFlow_id()) && !"-1".equals(params.getFlow_id())) {
                String flowId = params.getFlow_id();
                sql += " and oe.flow_trace_id in ('" + flowId.replace(",", "','") + "') ";
            }
            // 外呼类型es_order_outcall_log->deal_remark
            if (!StringUtil.isEmpty(params.getOutcall_type())) {
                String outcall_type = params.getOutcall_type();
                sql += " and ol.deal_remark in ('" + outcall_type.replace(",", "','") + "') ";
            }
            // (是否存在)待办业务; 0:不存在;1存在
            if (!StringUtil.isEmpty(params.getExists_business_to_deal_with())) {
                if (EcsOrderConsts.EXISTS_BUSINESS_TO_DEAL_WITH_1.equals(params.getExists_business_to_deal_with())) {
                    sql += " and ( exists(select 1 from " + es_order_sp_product
                            + " eosp where o.order_id=eosp.order_id and o.source_from=eosp.source_from and eosp.status not in ('"
                            + EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2 + "','"
                            + EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3 + "') )" + // 数据产生即有值'0'，不存在null情况
                            "or exists (select 1 from " + es_attr_package_subprod
                            + " eaps where o.order_id=eaps.order_id and o.source_from=eaps.source_from and (eaps.status is null or eaps.status not in ('"
                            + EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2 + "','"
                            + EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3 + "'))))"
                            + "and oe.flow_trace_id in ('F','H','J','L')";// 只查询业务办理后的环节的订单
                }
                if (EcsOrderConsts.EXISTS_BUSINESS_TO_DEAL_WITH_0.equals(params.getExists_business_to_deal_with())) {
                    sql += " and not exists(select 1 from " + es_order_sp_product
                            + " eosp where o.order_id=eosp.order_id and o.source_from=eosp.source_from and eosp.status not in ('"
                            + EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2 + "','"
                            + EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3 + "') )" + // 数据产生即有值'0'，不存在null情况
                            "and not exists(select 1 from " + es_attr_package_subprod
                            + " eaps where o.order_id=eaps.order_id and o.source_from=eaps.source_from and (eaps.status is null or eaps.status not in ('"
                            + EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2 + "','"
                            + EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3 + "')))";
                }
            }

            // exception_type;//异常类型
            if (!StringUtil.isEmpty(params.getException_type()) && !"-1".equals(params.getException_type())) {
                if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_0.equals(params.getException_type())) {
                    sql += " and oe.ABNORMAL_TYPE in (?,?) ";
                    sqlParams.add(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0);
                    sqlParams.add(EcsOrderConsts.ORDER_ABNORMAL_TYPE_2);
                } else {
                    sql += " and oe.ABNORMAL_TYPE=? ";
                    sqlParams.add(params.getException_type());
                }
            }
            if (!StringUtil.isEmpty(params.getAbnormal_type()) && !"-1".equals(params.getAbnormal_type())) {
                if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_0.equals(params.getAbnormal_type())) {
                    sql += " and oe.ABNORMAL_TYPE=? ";
                    sqlParams.add(params.getAbnormal_type());
                }
            }
            // 退单状态
            if (QUERY_TYPE_RETURNED_CFM.equals(params.getQuery_type())) {
                if (params.getUsername() != "1") {
                    sql += " and oe.REFUND_STATUS not in('04','05','06') and oe.REFUND_STATUS is not null";
                }
            }
            if (QUERY_TYPE_RETURNED_CFMN.equals(params.getQuery_type())) {
                if (params.getUsername() != "1") {
                    sql += " and oe.REFUND_STATUS not in('04','05','06') and oe.REFUND_STATUS is not null"
                            + " and exists (select 1 from  " + es_order_extvtl
                            + " ee where ee.order_id=o.order_id and (ee.bss_refund_status = '0' or ee.bss_refund_status is null) ) ";

                }
            }

            // 退款
            if (QUERY_TYPE_RETURNED_BSS.equals(params.getQuery_type())) {
                if (params.getUsername() != "1") {
                    sql += " and exists (select 1 from  " + es_order_extvtl
                            + " ee where ee.order_id=o.order_id and ee.bss_refund_status in ('2','4') ) ";
                }
            }

            // TODO 订单待定查询条件处理
            // 处理状态 （全部 挂起 锁定 未锁定）待定
            if (!StringUtils.isEmpty(params.getStatus())) {
                if (EcsOrderConsts.MY_LOCK_STATUS.equals(params.getStatus())) {
                    sql += " and exists (select 1 from es_order_lock eol where o.order_id = eol.order_id and eol.lock_status = ? and eol.lock_user_id = ? and eol.tache_code = oe.Flow_Trace_Id)";
                    // sql+=" and oe.lock_status=? and oe.lock_user_id=?";
                    sqlParams.add(EcsOrderConsts.LOCK_STATUS);
                    sqlParams.add(user_id);
                } else if (EcsOrderConsts.PENDING_REASON.equals(params.getStatus())) {
                    sql += " and oe.pending_reason is not null ";
                } else if (EcsOrderConsts.ALL_LOCK_STATUS.equals(params.getStatus())) {
                    sql += " and exists (select 1 from es_order_lock eol where o.order_id = eol.order_id and eol.lock_status = '1' and eol.tache_code = oe.Flow_Trace_Id)";
                } else {
                    // sql+=" and oe.lock_status=?";
                    sql += " and not exists (select 1 from es_order_lock eol where o.order_id = eol.order_id and eol.lock_status = '1' and eol.tache_code = oe.Flow_Trace_Id)";
                    // sqlParams.add(params.getStatus());
                }
            }
            // 闪电送公司 待定
            if (!StringUtil.isEmpty(params.getQuick_shipping_company())
                    && !"-1".equals(params.getQuick_shipping_company())) {
                sql += " and oe.QUICK_SHIP_COMPANY_CODE=?";
                sqlParams.add(params.getQuick_shipping_company());
            }
            // 闪电送状态 待定
            if (!StringUtil.isEmpty(params.getQuick_shipping_status())
                    && !"-1".equals(params.getQuick_shipping_status())) {
                sql += " and oe.shipping_quick=?";
                sqlParams.add(params.getQuick_shipping_status());
            }
            // 是否异常订单( 全部订单、正常单、异常单) 待定
            if (!StringUtil.isEmpty(params.getIs_exception()) && !"-1".equals(params.getIs_exception())) {// 异常单处理字段
                sql += " and oe.ABNORMAL_STATUS=?";
                sqlParams.add(params.getIs_exception());
            }
            // 异常编码
            if (!StringUtil.isEmpty(params.getException_code()) && !"-1".equals(params.getException_code())) {
                sql += " and oe.EXCEPTION_TYPE in('" + params.getException_code().replace(",", "','") + "')";
            }
            // 非生产模式
            if (!StringUtil.isEmpty(params.getNot_flow_model_code()) && !"-1".equals(params.getNot_flow_model_code())) {
                sql += " and oe.order_model <> ? ";
                sqlParams.add(params.getNot_flow_model_code());
            }
            // private String refund_status; //退单状态
            if (!StringUtil.isEmpty(params.getRefund_status()) && !"-1".equals(params.getRefund_status())) {
                sql += " and oe.REFUND_STATUS = ? ";
                sqlParams.add(params.getRefund_status());
            }
            // 退款状态
            if (!StringUtil.isEmpty(params.getBss_refund_status()) && !"-1".equals(params.getBss_refund_status())) {
                sql += " and exists (select 1 from  " + es_order_extvtl
                        + " ee where ee.order_id=o.order_id and ee.bss_refund_status = ? )";
                sqlParams.add(params.getBss_refund_status());
            }
            // 条形码
            if (!StringUtil.isEmpty(params.getIccid())) {
                sql += " and exists (select 1 from  " + es_order_extvtl
                        + " ic where ic.order_id=o.order_id and substr(ic.iccid,0,length(ic.iccid)-1) = ? )";
                sqlParams.add(params.getIccid());
            }
            // 是否自定义流程 add by cqq 20181128
            if(!StringUtil.isEmpty(params.getIs_work_custom()) ){
                if("1".equals(params.getIs_work_custom())){
                    sql += " and oe.is_work_custom = 1";
                }else{
                    sql += " and (oe.is_work_custom is null or oe.is_work_custom != 1)";
                }
            }
            
            //add by zhaochen 20181102 加入县分权限控制
            if (!StringUtil.isEmpty(params.getOrder_county_code()) && !"-1".equals(params.getOrder_county_code())) {
                String[] busiCountys = params.getOrder_county_code().split(",");
                
                if(busiCountys!=null && busiCountys.length>0){
                    List<String> busiCountyList = Arrays.asList(busiCountys);
                    List<String> countyList = this.transBusiCounty2County(busiCountyList);
                    
                    sql += " and ( ";
                    
                    //行政县分条件
                    sql += " exists (select 1 from  " + es_order_extvtl
                            + " ee where ee.order_id=o.order_id and ( ee.district_code is null " + SqlUtil.getSqlInStr("ee.district_code", countyList, false, false) 
                            + " ) ) ";
                    
                    //营业县分条件
                    sql += " or exists ( select 1 from  " + es_order_zhwq_adsl
                            + " zhwq where zhwq.order_id=o.order_id and ( zhwq.county_id is null " + SqlUtil.getSqlInStr("zhwq.county_id", busiCountyList, false, false) 
                            + " ) ) ";
                    
                    sql += " ) ";
                }
            }else if(EcsOrdManager.QUERY_TYPE_ORDER_VIEW.equals(params.getQuery_type()) 
                    && ("3".equals(user.getPermission_level()))){
                
                //县分层级工号，需要加上县分条件
                sql += " and ( ";
                
                //行政县分条件
                sql += " exists (select 1 from  " + es_order_extvtl
                        + " ee where ee.order_id=o.order_id and ( ee.district_code is null " + SqlUtil.getSqlInStr("ee.district_code", user.getPermission_county(), false, false) 
                        + " ) ) ";
                
                //营业县分条件
                sql += " or exists ( select 1 from  " + es_order_zhwq_adsl
                        + " zhwq where zhwq.order_id=o.order_id and ( zhwq.county_id is null " + SqlUtil.getSqlInStr("zhwq.county_id", user.getPerm_busi_county(), false, false) 
                        + " ) ) ";
                
                sql += " ) ";
            }
            
            // 订单补寄状态 0-待补寄
            if (!StringUtil.isEmpty(params.getOrder_supply_status()) && !"-1".equals(params.getOrder_supply_status())) {
                if (EcsOrderConsts.ORDER_SUPPLY_STATUS_0.equals(params.getOrder_supply_status())) {
                    sql += " and exists (select 1 from  " + es_delivery_item_table
                            + " di where di.order_id=o.order_id and (di.col1 is null or di.col1='0' or di.col1='1') )";
                }
            }
            // 是否智慧沃家产品
            // if(!"-1".equals(params.getIs_zhwj())){//非人为选"全部"
            // if(StringUtil.isEmpty(params.getIs_zhwj()) ||
            // EcsOrderConsts.NO_DEFAULT_VALUE.equals(params.getIs_zhwj())){//未人为选择,或者人为选"否",都认为选"否"
            // sql += " and (oe.is_zhwj = ? or oe.is_zhwj is null)";
            // sqlParams.add(EcsOrderConsts.NO_DEFAULT_VALUE);
            // }else{//人为选其他,包含人为选"是"
            // sql += " and oe.is_zhwj = ? ";
            // sqlParams.add(params.getIs_zhwj());
            // }
            // }
            // private String refund_deal_type; //订单状态类型 01正常单 02异常单
            if (!StringUtil.isEmpty(params.getRefund_deal_type()) && !"-1".equals(params.getRefund_deal_type())) {
                sql += " and oe.REFUND_DEAL_TYPE = ? ";
                sqlParams.add(params.getRefund_deal_type());
            }
            if (!StringUtil.isEmpty(params.getOrder_model()) && !"-1".equals(params.getOrder_model())) {
                if (params.getOrder_model().indexOf(",") != -1) {
                    sql += " and oe.order_model in ('" + params.getOrder_model().replace(",", "','") + "') ";
                } else {
                    sql += " and oe.order_model = ? ";
                    sqlParams.add(params.getOrder_model());
                }
            }

            if (!StringUtil.isEmpty(params.getWms_refund_status()) && !"-1".equals(params.getWms_refund_status())) {
                sql += " and exists (select 1 from  " + es_order_extvtl
                        + " ee where ee.order_id=o.order_id and ee.wms_refund_status  = ? )";
                sqlParams.add(params.getWms_refund_status());
            }
            // 商品名称
            if (!StringUtil.isEmpty(params.getGoods_name()) && !"-1".equals(params.getGoods_name())) {
                sql += " and exists (select 1 from  " + es_order_extvtl
                        + " ee where ee.order_id=o.order_id and ee.GoodsName like '%" + params.getGoods_name().trim()
                        + "%')";
            }
            // 客户类型
            if (!StringUtil.isEmpty(params.getCust_type()) && !"-1".equals(params.getCust_type())) {
                sql += " and exists (select 1 from  " + es_order_extvtl
                        + " ee where ee.order_id=o.order_id and ee.CustomerType = ? )";
                sqlParams.add(params.getCust_type());
            }
            // 集团编码
            if (org.apache.commons.lang.StringUtils.isNotBlank(params.getGroup_code())) {
                sql += " and exists (select 1 from  " + es_order_extvtl
                        + " ee where ee.order_id=o.order_id and ee.group_code = ? )";
                sqlParams.add(params.getGroup_code());
            }
            // 集团名称
            if (org.apache.commons.lang.StringUtils.isNotBlank(params.getGroup_name())) {
                sql += " and exists (select 1 from  " + es_order_extvtl
                        + " ee where ee.order_id=o.order_id and ee.group_name = ? )";
                sqlParams.add(params.getGroup_name());
            }
            // 是否预约单---暂时屏蔽
            /*
             * if(!StringUtil.isEmpty(params.getWm_isreservation_from()) &&
             * !"-1".equals(params.getWm_isreservation_from())){//人为选择，且非选"全部" sql +=
             * " and oe.wm_isreservation_from = ? ";
             * sqlParams.add(params.getWm_isreservation_from()); }
             */
            // 是否老用户
            if (!StringUtil.isEmpty(params.getIs_old()) && !"-1".equals(params.getIs_old())) {
                sql += " and exists (select 1 from  " + es_order_items_prod_ext
                        + " ee where ee.order_id=o.order_id and ee.is_old = ? )";
                sqlParams.add(params.getIs_old());
            }
            // 证件类型+证件号码 证件类型:默认SFZ18，暂无限制
            if (!StringUtil.isEmpty(params.getCerti_type()) && !StringUtil.isEmpty(params.getCert_card_num())) {
                sql += " and exists (select 1 from  " + es_order_items_prod_ext
                        + " ee where ee.order_id=o.order_id and ee.Cert_card_num like ? )";
                // sql += " and exists (select 1 from "+es_order_items_prod_ext+" ee where
                // ee.order_id=o.order_id and ee.Certi_type = ? and ee.Cert_card_num like ? )";
                // sqlParams.add(params.getCerti_type().trim());
                sqlParams.add("%" + params.getCert_card_num().trim() + "%");
            }
            // 订单类型
            if (!StringUtil.isEmpty(params.getOrder_type()) && !"-1".equals(params.getOrder_type())) {
                sql += " and o.order_type in('" + params.getOrder_type().replace(",", "','") + "')";
            }
            // 外单编号 ES_ORDER_EXT->ES_ORDER_EXT
            if (!StringUtil.isEmpty(params.getOut_tid())) {
                if (EcsOrderConsts.DIC_ORDER_NODE_F.equals(params.getFlow_id())) {// 人工质检订单号是手动输入，使用模糊查询
                    sql += " and oe.out_tid like '%" + params.getOut_tid().trim() + "%'";
                } else {
                    sql += " and oe.out_tid = '" + params.getOut_tid().trim() + "'";
                }
            }
            // 内部单号 es_order->order_id
            if (!StringUtil.isEmpty(params.getOrder_id())) {
                if (EcsOrderConsts.DIC_ORDER_NODE_F.equals(params.getFlow_id())) {// 人工质检订单号是手动输入，使用模糊查询
                    sql += " and o.order_id like '%" + params.getOrder_id().trim() + "%'";
                } else {
                    sql += " and o.order_id = '" + params.getOrder_id().trim() + "'";
                }
            }

            // 查询物流单号是空的,物流单号补录
            if (QUERY_TYPE_LOGISTICS.equals(params.getQuery_type())) {
                sql += " and exists (select 1 from  es_delivery de where de.order_id=o.order_id and de.logi_no is null  )";
            }

            // 取货页面，条形码序列号
            if (!StringUtil.isEmpty(params.getOrder_sn())) {
                sql += " and o.sn = ? ";
                sqlParams.add(params.getOrder_sn());
            }
            // 取货页面，订单状态
            if (!StringUtil.isEmpty(params.getOrder_status())) {
                // sql += " and o.status = ?";
                // sqlParams.add(params.getOrder_status());
            }
            // 锁单状态,订单领取
            /*
             * if(!StringUtil.isEmpty(params.getLock_status()) ||
             * EcsOrdManager.QUERY_TYPE_ORDER_RECEIVE.equals(params.getQuery_type())){ sql
             * +=
             * "  and not exists (select 1 from es_order_lock ol where ol.tache_code=oe.Flow_Trace_Id and ol.lock_status = 1  and oe.order_id = ol.order_id) "
             * ; }
             */
            if (!StringUtil.isEmpty(params.getLock_user_id())) {
                sql += " and exists (select 1 from  es_order_lock ol where ol.order_id=o.order_id and ol.lock_user_id='"
                        + params.getLock_user_id() + "' and oe.flow_trace_id=ol.tache_code )";
            }
            if (!StringUtil.isEmpty(params.getOrder_qry_status())
                    && !StringUtil.equals("qxz", params.getOrder_qry_status())) {
                String[] Order_qry_status = params.getOrder_qry_status().split(",");
                String where_sql = "";
                if (params.getOrder_qry_status().indexOf("00") >= 0) {
                    sql += " and ( not exists (select 1 from es_order_ext t,es_order a where t.order_id= a.order_id and (t.abnormal_status='1' or t.refund_deal_type='02' or a.status='11') and t.order_id=o.order_id) ";
                    for (int oqs = 0; oqs < Order_qry_status.length; oqs++) {
                        if (!StringUtil.equals("00", Order_qry_status[oqs])) {
                            if (StringUtil.equals("01", Order_qry_status[oqs])) {// 退单
                                sql += " or exists (select 1 from es_order_ext t,es_order a where t.order_id= a.order_id and  t.refund_deal_type='02' and t.order_id=o.order_id) ";
                            } else if (StringUtil.equals("02", Order_qry_status[oqs])) {// 外呼
                                sql += " or exists (select 1 from es_order_ext t,es_order a where t.order_id= a.order_id and  a.status='11' and t.order_id=o.order_id) ";
                            } else if (StringUtil.equals("03", Order_qry_status[oqs])) {// 异常
                                sql += " or exists (select 1 from es_order_ext t,es_order a where t.order_id= a.order_id and t.abnormal_status='1' and t.order_id=o.order_id) ";
                            } else {
                                if (!"000".equals(Order_qry_status[oqs])) {
                                    /* stringBuffer.append(Order_qry_status[oqs]+","); */
                                    where_sql += "'" + Order_qry_status[oqs] + "',";
                                }
                            }
                        }
                    }

                    if (!StringUtil.isEmpty(where_sql)) {
                        where_sql = where_sql.substring(0, where_sql.lastIndexOf(","));
                        sql += "or exists (select 1 from es_order es where es.order_id = oe.order_id and order_state in ("
                                + where_sql + "))";
                    }
                    sql += " ) ";
                } else {
                    sql += " and ( ";
                    for (int oqs = 0; oqs < Order_qry_status.length; oqs++) {
                        if (!StringUtil.equals("00", Order_qry_status[oqs])) {
                            if (StringUtil.equals("01", Order_qry_status[oqs])) {// 退单
                                sql += " exists (select 1 from es_order_ext t,es_order a where t.order_id= a.order_id and  t.refund_deal_type='02' and t.order_id=o.order_id) or ";
                            } else if (StringUtil.equals("02", Order_qry_status[oqs])) {// 外呼
                                sql += " exists (select 1 from es_order_ext t,es_order a where t.order_id= a.order_id and  a.status='11' and t.order_id=o.order_id) or ";
                            } else if (StringUtil.equals("03", Order_qry_status[oqs])) {// 异常
                                sql += " exists (select 1 from es_order_ext t,es_order a where t.order_id= a.order_id and t.abnormal_status='1' and t.order_id=o.order_id) or ";
                            } else {
                                if (!"000".equals(Order_qry_status[oqs])) {
                                    /* stringBuffer.append(Order_qry_status[oqs]+","); */
                                    where_sql += "'" + Order_qry_status[oqs] + "',";
                                }
                            }
                        }
                    }
                    if (!StringUtil.isEmpty(where_sql)) {
                        where_sql = where_sql.substring(0, where_sql.lastIndexOf(","));
                        sql += " exists (select 1 from es_order es where es.order_id = oe.order_id and order_state in("
                                + where_sql + ")) or ";
                    }
                    sql = sql.substring(0, sql.lastIndexOf("or"));
                    sql += " ) ";
                }

            }
            if(!StringUtil.isEmpty(params.getTop_seed_professional_line())&&!"-1".equals(params.getTop_seed_professional_line())){
            	sql += " and exists (select 1 from  " + es_order_extvtl
                        + " ee where ee.order_id=o.order_id and ee.top_seed_professional_line='"+params.getTop_seed_professional_line()+"' ) ";
            }
            if(!StringUtil.isEmpty(params.getTop_seed_type())&&!"-1".equals(params.getTop_seed_type())){
            	sql += " and exists (select 1 from  " + es_order_extvtl
                        + " ee where ee.order_id=o.order_id and ee.top_seed_type='"+params.getTop_seed_type()+"' ) ";
            }
            if(!StringUtil.isEmpty(params.getTop_seed_group_id())&&!"-1".equals(params.getTop_seed_group_id())){
            	sql += " and exists (select 1 from  " + es_order_extvtl
                        + " ee where ee.order_id=o.order_id and ee.top_seed_group_id='"+params.getTop_seed_group_id()+"' ) ";
            }
            if(!StringUtil.isEmpty(params.getShare_svc_num())){//用户种子号码
            	sql += " and exists (select 1 from  " + es_order_extvtl
                        + " ee where ee.order_id=o.order_id and ee.share_svc_num='"+params.getShare_svc_num()+"' ) ";
            }
        }
        return sql;
    }
    
    @SuppressWarnings("rawtypes")
	private String getOrderReveiveLockCondition(){
    	// 获取组织编码
    	String org_id = ManagerUtils.getAdminUser().getOrg_id();
       
        //获取团队ID
    	String userInfo = ManagerUtils.getAdminUser().getUserid();
        List<Map> listTeamId = this.querTeamId(userInfo);
        StringBuilder teamIds = new StringBuilder();
        
        if (listTeamId != null && listTeamId.size()>0){
        	for (Map m : listTeamId) {
            	if (teamIds.length() > 0){
            		teamIds.append(",");
            	}
            	
            	teamIds.append("'"+m.get("team_id")+"'");
            }
        }
        
        StringBuilder lockBuilder = new StringBuilder();
        
        lockBuilder.append(" and (((oe.is_work_custom is null or oe.is_work_custom <> '1') and ");
        lockBuilder.append(" not exists (select 1 ");
        lockBuilder.append(" from es_order_lock eol ");
        lockBuilder.append("  where o.order_id = eol.order_id ");
        lockBuilder.append(" and eol.lock_status = '1' ");
        lockBuilder.append(" and eol.tache_code = oe.Flow_Trace_Id)) or ");
        
        lockBuilder.append(" (oe.is_work_custom = '1' and ");
        lockBuilder.append(" ( ");
        
        if (teamIds.length() > 0){
        	lockBuilder.append(" exists (SELECT 1 ");
            lockBuilder.append(" FROM es_order_lock eol ");
            lockBuilder.append(" where o.order_id = eol.order_id ");
            lockBuilder.append(" and eol.lock_user_id in (").append(teamIds).append(") ");
            lockBuilder.append(" and eol.dealer_type = 'team') or  ");
        }

        lockBuilder.append(" exists (SELECT 1 ");
        lockBuilder.append(" FROM es_order_lock eol ");
        lockBuilder.append("  where o.order_id = eol.order_id ");
        lockBuilder.append(" and eol.lock_user_id = '").append(org_id).append("'");
        lockBuilder.append("  and eol.dealer_type = 'org')))) ");
        
        return lockBuilder.toString();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<String> transBusiCounty2County(List<String> countyList){
        
        List<String> result = new ArrayList<String>();
        
        StringBuilder sqlBuilder = new StringBuilder();
        
        sqlBuilder.append(" SELECT a.field_value FROM es_dc_public_dict_relation a WHERE a.stype='county' ");
        sqlBuilder.append(SqlUtil.getSqlInStr("a.other_field_value", countyList, false, true));
        
        List<Map> ret = this.baseDaoSupport.queryForList(sqlBuilder.toString());
        
        if(ret!=null && ret.size()>0){
            for(Iterator<Map> it = ret.iterator();it.hasNext();){
                Map item = it.next();
                
                String value = String.valueOf(item.get("field_value"));
                
                result.add(value);
            }
        }
        
        return result;
    }

    @Override
    public OrderBtn getOrderFlowBtns(String trace_id) {
        String sql = "select * from es_order_btn t where t.trace_id=? ";
        List<OrderBtn> list = this.baseDaoSupport.queryForList(sql, OrderBtn.class, trace_id);
        return (list == null || list.size() == 0) ? null : list.get(0);
    }

    @Override
    public List<OrderBtn> listOrderFlowBtns(String trace_id) {
        OrderBtn btn = this.getOrderFlowBtns(trace_id);
        List<OrderBtn> list = new ArrayList<OrderBtn>();
        if (btn != null) {
            String btnsJson = btn.getCbtns();
            if (StringUtil.isEmpty(btnsJson))
                btnsJson = btn.getBtns();
            if (!StringUtils.isEmpty(btnsJson)) {
                list = JSON.parseArray(btnsJson, OrderBtn.class);
            }
        }
        return list;
    }

    private OrderBtn getOrderCrawlerBtns(String trace_id) {
        String sql = "select * from es_order_crawler_btn t where t.trace_id=? ";
        List<OrderBtn> list = this.baseDaoSupport.queryForList(sql, OrderBtn.class, trace_id);
        return (list == null || list.size() == 0) ? null : list.get(0);
    }

    @Override
    public List<OrderBtn> listOrderCrawlerBtns(String trace_id) {
        OrderBtn btn = this.getOrderCrawlerBtns(trace_id);
        List<OrderBtn> list = new ArrayList<OrderBtn>();
        if (btn != null) {
            String btnsJson = btn.getCbtns();
            if (StringUtil.isEmpty(btnsJson))
                btnsJson = btn.getBtns();
            if (!StringUtils.isEmpty(btnsJson)) {
                list = JSON.parseArray(btnsJson, OrderBtn.class);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        String json = "[{btn_ename:'o_shipping',btn_cname:'请发货',show_type:'execute',action_url:'aaa.do'},"
                + "{btn_ename:'o_save',btn_cname:'保存',show_type:'dialog',action_url:'aaa.do'},        "
                + "{btn_ename:'o_suspend',btn_cname:'挂起',show_type:'dialog',action_url:'aaa.do'},     "
                + "{btn_ename:'o_entrust',btn_cname:'委托',show_type:'dialog',action_url:'aaa.do'},     "
                + "{btn_ename:'o_returned',btn_cname:'退单',show_type:'dialog',action_url:'aaa.do'},    "
                + "{btn_ename:'o_rebut',btn_cname:'驳回',show_type:'dialog',action_url:'aaa.do'}]       ";
        List<OrderBtn> list = JSON.parseArray(json, OrderBtn.class);
        logger.info(list);
    }

    @Override
    public OrderTreeBusiRequest suspend_save(String order_id, String pending_reason) {
        // TODO Auto-generated method stub
        OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
        if (orderTree != null) {
            OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
            orderExtBusiRequest.setPending_reason(pending_reason);
            orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
            orderExtBusiRequest.store();

            OrderTreeBusiRequest back_order_tree = CommonDataFactory.getInstance().getOrderTree(order_id);

            OrderHandleLogsReq orderHandleLogsReq = new OrderHandleLogsReq();
            orderHandleLogsReq.setComments(pending_reason); // 操作描述
            try {
                orderHandleLogsReq.setCreate_time(DateUtil.getTime2()); // 操作时间
            } catch (FrameException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            orderHandleLogsReq.setFlow_id(back_order_tree.getOrderExtBusiRequest().getFlow_id()); // 流程ID
            orderHandleLogsReq.setFlow_trace_id(back_order_tree.getOrderExtBusiRequest().getFlow_trace_id()); // 环节ID
            orderHandleLogsReq.setOp_id(ManagerUtils.getAdminUser().getUserid()); // 操作员ID
            orderHandleLogsReq.setOp_name(ManagerUtils.getAdminUser().getUsername()); // 操作员姓名
            orderHandleLogsReq.setOrder_id(back_order_tree.getOrder_id()); // 订单ID
            orderHandleLogsReq.setHandler_type(Const.ORDER_HANDLER_TYPE_SUSPEND); // 业务处理类型编码
            orderHandleLogsReq.setType_code("订单挂起"); // 订单挂起原因
            this.baseDaoSupport.insert("ES_ORDER_HANDLE_LOGS", orderHandleLogsReq);
            return back_order_tree;
        }
        return null;
    }

    @Override
    public void order_lock(String order_id, String tache_code) {
        // TODO Auto-generated method stub
        lockOrderByUser(order_id, tache_code, ManagerUtils.getAdminUser().getUserid(),
                ManagerUtils.getAdminUser().getRealname());

    }

    @Override
    public Boolean orderLockByWl(String order_id, String userId) {
        AdminUserReq userReq = new AdminUserReq();
        userReq.setUser_id(userId);
        AdminUserResp resp = adminUserServ.getAdminUserById(userReq);
        if (null == resp || null == resp.getAdminUser()) {
            return false;
        }
        lockOrderByUser(order_id, null, userId, resp.getAdminUser().getRealname());
        return true;
    }

    public void lockOrderByUser(String order_id, String tache_code, String userId, String userName) {
		try {
	    boolean locking = false;// 是否需要锁定
	    OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
	    OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
	    String currTacheCode = orderExtBusiRequest.getFlow_trace_id();
	    if (!StringUtils.isEmpty(tache_code)) {
	        currTacheCode = tache_code;
	    }
	    ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	    String order_froms = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_from();
	    String goods_cat = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
	    String close_group_order_catid = cacheUtil.getConfigInfo("page_look_info");
	    String kgs = cacheUtil.getConfigInfo("kg_lock");
	
	    boolean flag = true;
	    if("1".equals(kgs) && !"1".equals(userId) && "10093".equals(order_froms) && !StringUtils.isEmpty(close_group_order_catid) && close_group_order_catid.contains(goods_cat)){
	        flag = false;
	    }
	    if(flag){
	    	OrderLockBusiRequest orderLockBusiRequest = CommonDataFactory.getInstance()
	    			.getOrderLockBusiRequest(orderTree, currTacheCode);
	    	if (orderLockBusiRequest == null) {// 根据当前环节编码找不到锁定记录，则认为是没有锁定，此时要可以锁定订单
	    		orderLockBusiRequest = new OrderLockBusiRequest();
	    		orderLockBusiRequest.setLock_id(this.baseDaoSupport.getSequences("o_outcall_log"));
	    		orderLockBusiRequest.setOrder_id(order_id);
	    		orderLockBusiRequest.setTache_code(currTacheCode);
	    		orderLockBusiRequest.setLock_user_id(userId);
	    		orderLockBusiRequest.setLock_user_name(userName);
	    		orderLockBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
	    		orderLockBusiRequest.setLock_time(DateUtil.getTime2());
	    		orderLockBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
	    		orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
	    		orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
	    		// 锁单信息增加工号池和锁单结束时间
	    		orderLockBusiRequest.setPool_id(workPoolManager.getLockTimeByUserId(userId).getPool_id());
	    		orderLockBusiRequest.setLock_end_time(workPoolManager.getLockTimeByUserId(userId).getLock_end_time());
	    		orderLockBusiRequest.store();
	    		// 缓存属性也要更新--暂时屏蔽，消耗性能
	    		/*
	    		 * CommonDataFactory.getInstance().updateAttrFieldValue( order_id, new
	    		 * String[]{AttrConsts.LOCK_STATUS,AttrConsts.LOCK_USER_ID}, new
	    		 * String[]{EcsOrderConsts.LOCK_STATUS,userId});
	    		 */
	    		locking = true;
	    	} else if (orderLockBusiRequest != null
	    			&& EcsOrderConsts.UNLOCK_STATUS.equals(orderLockBusiRequest.getLock_status())) {
	    		// 根据当前环节编码找到锁定记录，但是锁定状态为未锁定，则可以锁定订单
	    		orderLockBusiRequest.setLock_id(orderLockBusiRequest.getLock_id());
	    		orderLockBusiRequest.setOrder_id(order_id);
	    		orderLockBusiRequest.setLock_user_id(userId);
	    		orderLockBusiRequest.setLock_user_name(userName);
	    		orderLockBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
	    		orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
	    		orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
	    		// 锁单信息增加工号池和锁单结束时间
	    		orderLockBusiRequest.setPool_id(workPoolManager.getLockTimeByUserId(userId).getPool_id());
	    		orderLockBusiRequest.setLock_end_time(workPoolManager.getLockTimeByUserId(userId).getLock_end_time());
	    		orderLockBusiRequest.store();
	    		// 缓存属性也要更新--暂时屏蔽，消耗性能
	    		/*
	    		 * CommonDataFactory.getInstance().updateAttrFieldValue( order_id, new
	    		 * String[]{AttrConsts.LOCK_STATUS,AttrConsts.LOCK_USER_ID}, new
	    		 * String[]{EcsOrderConsts.LOCK_STATUS,userId});
	    		 */
	    		locking = true;
	    	}
	    }
	
	    if (locking) {
	        OrderHandleLogsReq orderHandleLogsReq = new OrderHandleLogsReq();
	        orderHandleLogsReq.setComments("订单锁定"); // 操作描述
	        orderHandleLogsReq.setCreate_time(DateUtil.getTime2()); // 操作时间
	        orderHandleLogsReq.setFlow_id(orderTree.getOrderExtBusiRequest().getFlow_id()); // 流程ID
	        orderHandleLogsReq.setFlow_trace_id(currTacheCode); // 环节ID
	        orderHandleLogsReq.setOp_id(userId); // 操作员ID
	        orderHandleLogsReq.setOp_name(userName); // 操作员姓名
	        orderHandleLogsReq.setOrder_id(orderTree.getOrder_id()); // 订单ID
	        orderHandleLogsReq.setHandler_type(Const.ORDER_HANDLER_TYPE_LOCK); // 业务处理类型编码
	        orderHandleLogsReq.setType_code(EcsOrderConsts.LOCK_STATUS); // 处理类型 1-锁定 0-未锁定
	        this.baseDaoSupport.insert("ES_ORDER_HANDLE_LOGS", orderHandleLogsReq);
	    }
	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public Page adminUserList(int pageNO, int pageSize, String obj_name, String order_id) {
        // TODO Auto-generated method stub
        String sql_user = " select distinct e.userid,e.username,e.realname,e.phone_num,e.sex,e.login_status "
                + " from es_role_data a, " + " es_role_auth b, " + " es_role      c, " + " es_user_role d, "
                + " es_adminuser e " + " where a.id = b.authid " + " and a.source_from = b.source_from "
                + " and b.roleid = c.roleid " + " and b.source_from = c.source_from " + " and c.roleid = d.roleid "
                + " and c.source_from = d.source_from " + " and d.userid = e.userid "
                + " and d.source_from = e.source_from " + " and a.flow_node is not null "
                + " and (e.usertype<>'2' or e.usertype is null)";
        if (!StringUtils.isEmpty(order_id)) {
            OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);// CommonDataFactory.getInstance().getOrderTree(order_id);
            OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
            String trace_id = orderExt.getFlow_trace_id();
            sql_user += " and instr(a.flow_node,'" + trace_id + "') > 0  ";
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(obj_name)) {
            sql_user += " and e.realname like '%" + obj_name + "%'";
        }
        sql_user += " order by e.userid ";
        StringBuilder sSql = new StringBuilder(sql_user);
        /*
         * StringBuilder sSql = new
         * StringBuilder("SELECT T.USERID, T.USERNAME, T.REALNAME, " +
         * "T.SEX, T.LOGIN_STATUS FROM ES_ADMINUSER T WHERE 1=1 AND (T.USERTYPE <>'2' OR T.USERTYPE IS NULL)"
         * ); if (org.apache.commons.lang.StringUtils.isNotBlank(obj_name)) {
         * sSql.append(" AND T.REALNAME like '%"+ obj_name +"%'"); }
         * sSql.append(" ORDER BY T.USERID");
         */
        StringBuilder cSql = new StringBuilder("SELECT COUNT(EA.USERID) FROM (" + sSql.toString() + ") EA");
        return this.baseDaoSupport.queryForCPage(sSql.toString(), pageNO, pageSize, AdminUser.class, cSql.toString());

    }

    @Override
    public String entrust_save(String order_id, String userid, String username) {
        // TODO Auto-generated method stub

        OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

        OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
        List<OrderLockBusiRequest> orderLockRequest = orderTree.getOrderLockBusiRequests();
        OrderLockBusiRequest orderLockBusiRequest = null;
        for (int i = 0; i < orderLockRequest.size(); i++) {
            if (orderExtBusiRequest.getFlow_trace_id().equals(orderLockRequest.get(i).getTache_code())) {
                orderLockBusiRequest = orderLockRequest.get(i);
            }
        }

        String user_id = ManagerUtils.getAdminUser().getUserid(); // 当前操作员
        if (orderLockBusiRequest.getLock_status().equals("1")
                && !orderLockBusiRequest.getLock_user_id().equals(user_id)) {
            return "1"; // 已被人锁定
        }

        orderLockBusiRequest.setLock_user_id(userid);
        orderLockBusiRequest.setLock_user_name(username);
        orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
        orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
        // 锁单信息增加工号池和锁单结束时间
        orderLockBusiRequest.setPool_id(workPoolManager.getLockTimeByUserId(userid).getPool_id());
        orderLockBusiRequest.setLock_end_time(workPoolManager.getLockTimeByUserId(userid).getLock_end_time());
        orderLockBusiRequest.store();

        OrderTreeBusiRequest back_order_tree = CommonDataFactory.getInstance().getOrderTree(order_id);
        OrderHandleLogsReq orderHandleLogsReq = new OrderHandleLogsReq();
        orderHandleLogsReq.setComments("订单委托"); // 操作描述
        try {
            orderHandleLogsReq.setCreate_time(DateUtil.getTime2()); // 操作时间
        } catch (FrameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "2"; // 保存当前委托人失败
        }
        orderHandleLogsReq.setFlow_id(back_order_tree.getOrderExtBusiRequest().getFlow_id()); // 流程ID
        orderHandleLogsReq.setFlow_trace_id(back_order_tree.getOrderExtBusiRequest().getFlow_trace_id()); // 环节ID
        orderHandleLogsReq.setOp_id(ManagerUtils.getAdminUser().getUserid()); // 操作员ID
        orderHandleLogsReq.setOp_name(ManagerUtils.getAdminUser().getUsername()); // 操作员姓名
        orderHandleLogsReq.setOrder_id(back_order_tree.getOrder_id()); // 订单ID
        orderHandleLogsReq.setHandler_type(Const.ORDER_HANDLER_TYPE_ENTRUTS); // 业务处理类型编码
        orderHandleLogsReq.setType_code(userid); // 委托人ID
        this.baseDaoSupport.insert("ES_ORDER_HANDLE_LOGS", orderHandleLogsReq);
        return "0"; // 保存当前委托人成功
    }

    /**
     * 得到一个18位唯一的随机数,生成规则为当前时间yyMMddHHmmss+6位随机数
     * 
     * @return String
     */
    public static synchronized String getUniqueRandomNum() {
        String date = "";
        try {
            date = DateUtil.getTime5();
        } catch (FrameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder(date);
        for (int i = 0; i < 6; i++) {
            sb.append(getRandomNum());
        }
        return sb.toString();
    }

    /**
     * 得到一位随机数（0-9）
     * 
     * @return int
     */
    public static int getRandomNum() {
        return (int) (10 * Math.random());
    }

    @Override
    public List<Logi> logi_company(String order_id) {
        String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "order_city_code");
        String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "order_from");
        String flow_code = getFlowCodeNew(order_id);
        return queryLogiCompany(order_city_code,order_from,flow_code);
    }

    private List<Logi> queryLogiCompany(String city_code,String order_from,String flow_code) {
        List<Logi> logi_company_list = new ArrayList<Logi>();
        String key = "QUERYLOGICOMPANY_" + city_code;
        SerializeList serializeList = (SerializeList) cache.get(NAMESPACE, key);
        
       
        
        String region=null;
        StringBuilder sql=null;
        
        // 全国物流编码匹配
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_ORDER_FROM_FOLW_CODE");
        
        String[] bean = flag.split(",");
        List<String> beanList = java.util.Arrays.asList(bean);
        Set<String> beanSet = new HashSet<String>(beanList);
        
		if (beanSet.contains(order_from)||beanSet.contains(flow_code)) {
			sql = new StringBuilder("select l.id,l.company_code as company_code, l.name as post_comp_name, l.key_str as key_str from es_logi_company l where l.is_start='1'");
			List<Map> list = this.baseDaoSupport.queryForList(sql.toString());
			if (list != null && list.size() > 0) {
				for (Map mp : list) {
					Logi logi = new Logi();
					logi.setId(mp.get("id") != null ? mp.get("id").toString() : "");
					logi.setName(mp.get("post_comp_name") != null ? mp.get("post_comp_name").toString() : "");
					logi.setKey_str(mp.get("key_str") != null ? mp.get("key_str").toString() : "");
					logi.setCompany_code(mp.get("company_code") != null ? mp.get("company_code").toString() : "");
					logi_company_list.add(logi);
				}
			}
		} else {
			if (null != serializeList) {
				logi_company_list = serializeList.getObj();
			} else {
				region = CommonDataFactory.getInstance().getLanCode(city_code);
				sql = new StringBuilder(
						"SELECT T.ID , MIN(TT.POST_COMP_NAME) POST_COMP_NAME, MIN(T.COMPANY_CODE) COMPANY_CODE, MIN(T.KEY_STR) KEY_STR FROM ES_LOGI_COMPANY T");
				sql.append(" ,ES_LOGI_COMPANY_REGIONS TT");
				sql.append(" WHERE 1=1");
				sql.append(" AND T.SOURCE_FROM=TT.SOURCE_FROM");
				sql.append(" AND T.ID=TT.POST_ID");
				sql.append(" AND (TT.REGION=? OR TT.REGION='0571')");
				sql.append(" GROUP BY T.ID");
				sql.append(" ORDER BY T.ID ASC");
				List<Map> list = this.baseDaoSupport.queryForList(sql.toString(), region);
				if (list != null && list.size() > 0) {
					for (Map mp : list) {
						Logi logi = new Logi();
						logi.setId(mp.get("ID") != null ? mp.get("ID").toString() : "");
						logi.setName(mp.get("POST_COMP_NAME") != null ? mp.get("POST_COMP_NAME").toString() : "");
						logi.setKey_str(mp.get("KEY_STR") != null ? mp.get("KEY_STR").toString() : "");
						logi.setCompany_code(mp.get("COMPANY_CODE") != null ? mp.get("COMPANY_CODE").toString() : "");
						logi_company_list.add(logi);
					}
				}
				serializeList = new SerializeList();
				serializeList.setObj(logi_company_list);
				cache.set(NAMESPACE, key, serializeList);
			}
		}
        return logi_company_list;
    }
    
    
    /**
     * 获取流程编号
     * @author GL
     * @param order_id
     * @return
     */
    public String getFlowCodeNew(String order_id) {
    	String sql = "select a.flow_code from es_work_custom_workflow_ins a where a.order_id='"+order_id+"'";
    	return this.baseDaoSupport.queryForString(sql);
    }

    @Override
    public List<Logi> logi_company_city_code(String city_code) {
        return queryLogiCompany(city_code,"","");
    }

    @Override
    public List<Map> logi_company_regions(String logi_post_id) {
        // TODO Auto-generated method stub
        AdminUser adminUser = ManagerUtils.getAdminUser();
        StringBuilder sql = new StringBuilder(
                "SELECT T.CARRAY_FEE, T.PROTECT_FEE, T.TEL_NUM, T.LINKMAN FROM ES_LOGI_COMPANY_REGIONS T WHERE T.POST_ID=? AND T.REGION=?");
        List<Map> list = this.baseDaoSupport.queryForList(sql.toString(), logi_post_id, adminUser.getOrg_id());
        return list;
    }

    @Override
    public List<Map> dic_material_retrieve() {

        StringBuilder sql = new StringBuilder(
                "select t.pkey, t.pname from es_dc_public_ext t" + " where t.stype='DIC_MATERIAL_RETRIEVE'");
        List<Map> dic_material_retrieve = baseDaoSupport.queryForList(sql.toString());

        return dic_material_retrieve;
    }

    @Override
    public void affirm_receipt(String order_id, OrdReceipt ordReceipt) {
        // TODO Auto-generated method stub
        OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
        OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
        OrderDeliveryBusiRequest orderDeliveryBusiRequest = orderTree.getOrderDeliveryBusiRequests().get(0);
        orderDeliveryBusiRequest.setUser_recieve_time(ordReceipt.getUser_recieve_time());
        orderExtBusiRequest.setMaterial_retrieve(ordReceipt.getMaterial_retrieve());
        orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
        orderDeliveryBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
        orderDeliveryBusiRequest.store();
        String[] field_name = new String[] { "is_upload", "file_return_type" };
        String[] field_value = new String[] { ordReceipt.getIs_upload(), ordReceipt.getFile_return_type() };
        CommonDataFactory.getInstance().updateAttrFieldValue(order_id, field_name, field_value);
    }

    private String editSave(String order_id, HttpServletRequest request) {
        // 校验前，先将校验值清空处理
        OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
        ThreadContextHolder.setHttpRequest(request);
        String msg = "";
        // PageLoad属性验证方法校验
        List<AttrInstLoadResp> attrInstLoadResps = AttrBusiInstTools.validateOrderAttrInstsForPage(order_id,
                ConstsCore.ATTR_ACTION_lOAD);
        if (attrInstLoadResps.size() > 0)
            msg = "调用load方法，返回错误校验信息";
        attrInstLoadResps = AttrBusiInstTools.validateOrderAttrInstsForPage(order_id, ConstsCore.ATTR_ACTION_UPDATE);
        if (attrInstLoadResps.size() > 0)
            msg = "调用update方法，返回错误校验信息";
        // 待所有验证通过，处理逻辑获取界面修改脏数据,保存入库,增量数据入库处理
        List<AttrInstBusiRequest> pageAttrInstBusiRequests = AttrBusiInstTools.getPageDirtyOrderAttrInst(order_id,
                orderTree);
        for (AttrInstBusiRequest pageAttrInstBusiRequest : pageAttrInstBusiRequests) {
            pageAttrInstBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
            pageAttrInstBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
            pageAttrInstBusiRequest.store(); // 属性数据入库
        }
        return "";
    }

    /**
     * 订单标准化前，订单归档处理
     */
    @Override
    public boolean oldOrderArchivesForStanding(CoQueue coQueueReq) {
        // 归集前，判断历史数据是否已经存在，存在则不同步，直接退出
        boolean canNext = true;
        OrderCollect oc = CommonTools.jsonToBean(coQueueReq.getContents(), OrderCollect.class);
        String sql = SF.orderSql("OUTER_ORDER_ID_NOTEQ_BATCH");// 外系统订单编号,获取订单信息
        String rep_outer_order_id = this.baseDaoSupport.queryForString(sql, oc.getOuter().getOut_tid(),
                oc.getOuter().getOrder_from(), oc.getOrderOuterList().get(0).getBatch_id());
        if (!StringUtil.isEmpty(rep_outer_order_id)) {
            String curr_rep_outer_order_id = oc.getOrderOuterList().get(0).getOrder_id();
            // 删除外系统订单数据
            if (!StringUtil.isEmpty(curr_rep_outer_order_id)) {
                // 数据归档
                this.baseDaoSupport.execute(SF.archivesSql("INS_ORDER_OUTER_HIS"), curr_rep_outer_order_id);
                this.baseDaoSupport.execute(SF.archivesSql("INS_ORDER_OUTER_ATTR_INST_HIS"), curr_rep_outer_order_id);

                // 删除数据
                this.baseDaoSupport.execute(SF.archivesSql("DEL_ORDER_OUTER"), curr_rep_outer_order_id); //
                this.baseDaoSupport.execute(SF.archivesSql("DEL_ORDER_OUTER_ATTR_INST"), curr_rep_outer_order_id); //
                canNext = false;
            }
        }
        return canNext;
    }

    /**
     * 订单标准化前，归档数据处理
     */
    @Override
    public void newOrderArchivesForStanding(CoQueue coQueueReq) throws Exception {
        // 归集前，判断外系统订单是否已经同步，未同步
        OrderCollect oc = CommonTools.jsonToBean(coQueueReq.getContents(), OrderCollect.class);
        String sql = SF.orderSql("OUTER_ORDERS_ID_NOTEQ_BATCH");// 外系统订单编号,获取订单信息
        List<Map<String, String>> outerOrderIdMs = this.baseDaoSupport.queryForList(sql, oc.getOuter().getOut_tid(),
                oc.getOuter().getOrder_from(), oc.getOrderOuterList().get(0).getBatch_id());
        if (!ListUtil.isEmpty(outerOrderIdMs)) {
            for (Map<String, String> outerOrderIdM : outerOrderIdMs) {
                String rep_outer_order_id = outerOrderIdM.get("order_id");
                if (!StringUtil.isEmpty(rep_outer_order_id)) {
                    // 查询新系统归集的新订单,放置删除旧订单
                    String dSql = SF.orderSql("ORDER_ID_BY_OUTERID");
                    List<Map<String, String>> repOrderIds = this.baseDaoSupport.queryForList(dSql, rep_outer_order_id,
                            com.ztesoft.ibss.common.util.Const.ORDER_STAND_AUTO_SERVICE_CODE); // 获取上一条记录的订单编号,删除订单数据
                    if (!ListUtil.isEmpty(repOrderIds)) {
                        String rep_order_id = (String) repOrderIds.get(0).get("z_order_id");
                        ordArchiveTacheManager.ordArchive(rep_order_id);
                    }
                }
            }
        }

    }

    @Override
    public List<Map> getDcSqlByDcName(String dcName) {
        List<Map> list = new ArrayList<Map>();
        StringBuilder sql = new StringBuilder("select t.dc_sql from es_dc_sql t where t.dc_name='" + dcName + "'");
        List<Map> dc_sql_list = baseDaoSupport.queryForList(sql.toString());
        if (dc_sql_list != null && dc_sql_list.size() > 0) {
            String dc_sql = (dc_sql_list.get(0).get("dc_sql")).toString();
            list = baseDaoSupport.queryForList(dc_sql);
        }
        return list;
    }

    @Override
    public List<EntityInfoVO> entityInfoList(String order_id, String serial_num) {
        // TODO Auto-generated method stub
        List<EntityInfoVO> entityInfoList = new ArrayList<EntityInfoVO>();
        OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
        serial_num = serial_num.replace("A", "+");
        String terminal_num = "";
        List<OrderItemProdBusiRequest> itemsprod = orderTree.getOrderItemBusiRequests().get(0)
                .getOrderItemProdBusiRequests();
        for (OrderItemProdBusiRequest prod : itemsprod) {
            if (prod.getProd_spec_type_code().equals(EcsOrderConsts.PRODUCT_TYPE_CODE_TERMINAL)) { // 手机终端
                terminal_num = prod.getOrderItemProdExtBusiRequest().getTerminal_num();
            }
        }
        String order_type = orderTree.getOrderBusiRequest().getOrder_type();
        String order_from = orderTree.getOrderExtBusiRequest().getOrder_from();
        if (EcsOrderConsts.ORDER_TYPE_09.equals(order_type) || order_from.equals(EcsOrderConsts.ORDER_FROM_10061)) {
            List<OrderItemExtvtlBusiRequest> orderItemExtvtls = orderTree.getOrderItemExtvtlBusiRequests();
            for (OrderItemExtvtlBusiRequest orderItemExtvtl : orderItemExtvtls) {
                if (serial_num.equals(orderItemExtvtl.getResources_code())) {
                    EntityInfoVO entityInfo = new EntityInfoVO();
                    entityInfo.setEntity_type(orderItemExtvtl.getGoods_id());
                    entityInfo.setEntity_name(orderItemExtvtl.getGoods_name());
                    entityInfo.setSerial_number(orderItemExtvtl.getResources_code());
                    entityInfo.setInput_serial_number(serial_num);
                    entityInfo.setState(EcsOrderConsts.ENTITY_QUALITY_STATE_1);
                    entityInfo.setEntity_type("2");
                    entityInfo.setSku(orderItemExtvtl.getSku());
                    entityInfoList.add(entityInfo);
                }
            }
        }
        if (serial_num.equals(terminal_num)) { // 判断终端号
            List<Goods> goodsList = CommonDataFactory.getInstance().getEntityProducts(order_id);
            if (goodsList != null && goodsList.size() > 0) {
                for (Goods goods : goodsList) {
                    EntityInfoVO entityInfo = new EntityInfoVO();
                    String type_id = goods.getType_id();
                    GoodsTypeGetReq req = new GoodsTypeGetReq();
                    req.setType_id(type_id);
                    GoodsTypeGetResp goodsTypeGetResp = orderServices.getGoodsTypeName(req);
                    GoodsType goodsType = goodsTypeGetResp.getGoodsType();
                    String goodsTypeName = goodsType.getName();
                    String entity_type = goodsTypeName;
                    entityInfo.setEntity_type(entity_type);
                    entityInfo.setSku(goods.getSku());
                    entityInfo.setEntity_name(goods.getName());
                    entityInfo.setSerial_number(terminal_num);
                    entityInfo.setInput_serial_number(serial_num);
                    entityInfo.setState(EcsOrderConsts.ENTITY_QUALITY_STATE_1);
                    entityInfo.setType("2");
                    entityInfoList.add(entityInfo);
                }
                if (entityInfoList.size() > 0)
                    return entityInfoList;
            }
        }
        if (orderTree != null) { // 判断实物礼品的SKU
            List<AttrGiftInfoBusiRequest> giftInfoList = orderTree.getGiftInfoBusiRequests();
            if (giftInfoList != null && giftInfoList.size() > 0) {
                for (AttrGiftInfoBusiRequest giftInfo : giftInfoList) {
                    String serialNum = serial_num.replace("A", "+");//
                    if (null != giftInfo.getSeries_num() && giftInfo.getSeries_num().equals(serialNum)) {
                        EntityInfoVO entityInfo = new EntityInfoVO();
                        String type_id = giftInfo.getGoods_type();
                        GoodsTypeGetReq req = new GoodsTypeGetReq();
                        req.setType_id(type_id);
                        GoodsTypeGetResp goodsTypeGetResp = orderServices.getGoodsTypeName(req);
                        GoodsType goodsType = goodsTypeGetResp.getGoodsType();
                        String goodsTypeName = goodsType.getName();
                        String entity_type = goodsTypeName;
                        entityInfo.setEntity_type(entity_type);
                        entityInfo.setSku(giftInfo.getSku_id());
                        entityInfo.setEntity_name(giftInfo.getGoods_name());
                        entityInfo.setSerial_number(giftInfo.getSeries_num());
                        entityInfo.setInput_serial_number(serialNum);
                        entityInfo.setState(EcsOrderConsts.ENTITY_QUALITY_STATE_1);
                        entityInfo.setType("1");
                        entityInfoList.add(entityInfo);
                    }
                }
                if (entityInfoList.size() > 0)
                    return entityInfoList;
            }
        }

        String sim_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SIM_TYPE);
        String iccid = "";
        String iccid0 = "";
        String prod_type = "";
        String entity_name = "";
        List<OrderItemBusiRequest> orderItemBusiRequests = CommonDataFactory.getInstance().getOrderTree(order_id)
                .getOrderItemBusiRequests();
        OrderItemBusiRequest orderItemBusiRequest = orderItemBusiRequests.get(0);
        List<OrderItemProdBusiRequest> prods = orderItemBusiRequest.getOrderItemProdBusiRequests();
        for (OrderItemProdBusiRequest prod : prods) {
            if (EcsOrderConsts.PRODUCT_TYPE_CODE_OFFER.equals(prod.getProd_spec_type_code())
                    || EcsOrderConsts.PRODUCT_TYPE_CODE_CONTRACT.equals(prod.getProd_spec_type_code())) {
                entity_name += prod.getName() + ";";// 拼接合约计划和套餐货品的名称，作为号卡的名称
            }
        }
        if (EcsOrderConsts.SIM_TYPE_CK.equals(sim_type)) {
            List<OrderItemProdBusiRequest> prodBusis = CommonDataFactory.getInstance().getOrderTree(order_id)
                    .getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
            for (OrderItemProdBusiRequest prodBusi : prodBusis) {
                OrderItemProdExtBusiRequest prodExt = prodBusi.getOrderItemProdExtBusiRequest();
                if (EcsOrderConsts.PRODUCT_TYPE_CODE_OFFER.equals(prodBusi.getProd_spec_type_code())) {
                    iccid = prodExt.getICCID();
                    iccid0 = prodExt.getICCID();
                    prod_type = EcsOrderConsts.SIM_TYPE_CK;
                    entity_name = prodExt.getCert_card_name();
                }
            }
        } else {
            iccid0 = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
            iccid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
            prod_type = EcsOrderConsts.SIM_TYPE_BK;

            if (!StringUtils.isEmpty(iccid)) {
                iccid = iccid.substring(0, iccid.length() - 1);
            }
        }
        if (!StringUtils.isEmpty(iccid)
                && (serial_num.equals(iccid) || serial_num.substring(0, serial_num.length() - 1).equals(iccid))) { // 判断号卡
            EntityInfoVO entityInfo = new EntityInfoVO();
            entityInfo.setEntity_type(prod_type);
            entityInfo.setSku(iccid0);
            entityInfo.setEntity_name(entity_name);
            entityInfo.setSerial_number(iccid0);
            entityInfo.setInput_serial_number(serial_num);
            entityInfo.setState(EcsOrderConsts.ENTITY_QUALITY_STATE_1);
            entityInfo.setType("3");
            entityInfoList.add(entityInfo);
        }
        return entityInfoList;
    }

    public Page getLockOrdList(int pageNo, int pageSize, OrderQueryParams params) { // 获取当前人锁定的订单
        Page page = null;
        String countSql = "select count(*) from es_order_ext t  left join es_order s on t.order_id=s.order_id left join es_order_extvtl v on t.order_id=v.order_id left join es_order_lock l on t.order_id = l.order_id and t.flow_trace_id = l.tache_code where t.source_from = '"
                + ManagerUtils.getSourceFrom() + "' and (t.visible_status='" + EcsOrderConsts.VISIBLE_STATUS_0
                + "' or (t.visible_status='" + EcsOrderConsts.VISIBLE_STATUS_1 + "' and t.abnormal_type in('"
                + EcsOrderConsts.ORDER_ABNORMAL_TYPE_2 + "','" + EcsOrderConsts.ORDER_ABNORMAL_TYPE_3
                + "') )) and l.lock_status='" + EcsOrderConsts.LOCK_STATUS + "'";
        String sql = "select t.order_id,t.out_tid,t.tid_time,(select username from es_adminuser where userid=l.lock_user_id) lock_user_name,"
                + "l.lock_status,l.lock_user_id,t.order_from,v.goodsname goods_name,(select realname from es_adminuser where userid=l.lock_user_id) lock_user_realname from es_order_ext t  left join es_order s on t.order_id=s.order_id left join es_order_extvtl v on t.order_id=v.order_id left join es_order_lock l on t.order_id = l.order_id and t.flow_trace_id = l.tache_code where t.source_from = '"
                + ManagerUtils.getSourceFrom() + "' and (t.visible_status='" + EcsOrderConsts.VISIBLE_STATUS_0
                + "' or (t.visible_status='" + EcsOrderConsts.VISIBLE_STATUS_1 + "' and t.abnormal_type in('"
                + EcsOrderConsts.ORDER_ABNORMAL_TYPE_2 + "','" + EcsOrderConsts.ORDER_ABNORMAL_TYPE_3
                + "') )) and l.lock_status='" + EcsOrderConsts.LOCK_STATUS + "'";
        List list = new ArrayList();
        if (params != null) {
            if (!StringUtils.isEmpty(params.getLock_user_id())) {
                sql += " and l.lock_user_id ='" + params.getLock_user_id() + "' ";
                countSql += " and l.lock_user_id ='" + params.getLock_user_id() + "' ";
            }
            if (!StringUtils.isEmpty(params.getOrder_id())) {
                sql += " and t.order_id =? ";
                countSql += " and t.order_id =? ";
                list.add(params.getOrder_id());
            }
            if (!StringUtils.isEmpty(params.getOut_tid())) {
                sql += " and t.out_tid =? ";
                countSql += " and t.out_tid =? ";
                list.add(params.getOut_tid());
            }
            if (!StringUtils.isEmpty(params.getFlow_id())) {
                sql += " and t.flow_trace_id in('" + params.getFlow_id().replace(",", "','") + "') ";
                countSql += " and t.flow_trace_id in('" + params.getFlow_id().replace(",", "','") + "') ";
            }
            if (!StringUtils.isEmpty(params.getOrder_from())) {
                sql += " and t.order_from in('" + params.getOrder_from().replace(",", "','") + "') ";
                countSql += " and t.order_from in('" + params.getOrder_from().replace(",", "','") + "') ";
            }
            if (!StringUtils.isEmpty(params.getOrder_city_code())) {
                sql += " and t.order_city_code in('" + params.getOrder_city_code().replace(",", "','") + "') ";
                countSql += " and t.order_city_code in('" + params.getOrder_city_code().replace(",", "','") + "') ";
            }
            if (!StringUtils.isEmpty(params.getOrder_type())) {
                sql += " and s.order_type in('" + params.getOrder_type().replace(",", "','") + "') ";
                countSql += " and s.order_type in('" + params.getOrder_type().replace(",", "','") + "') ";
            }
            if (!StringUtils.isEmpty(params.getPhone_num())) {
                sql += " and exists(select 1 from es_order_items_ext o where t.order_id=o.order_id and t.source_from=o.source_from and o.phone_num = '"
                        + params.getPhone_num() + "') ";
                countSql += " and exists(select 1 from es_order_items_ext o where t.order_id=o.order_id and t.source_from=o.source_from and o.phone_num = '"
                        + params.getPhone_num() + "') ";
                // list.add(params.getPhone_num());
            }
            //
            // if(StringUtils.isEmpty(params.getOrder_id())&&StringUtils.isEmpty(params.getOut_tid())&&!StringUtil.isEmpty(params.getCreate_start())){
            // sql += " and oe.tid_time>="+DBTUtil.to_sql_date("?", 2);
            // sqlParams.add(params.getCreate_start());
            // }

            if (!StringUtil.isEmpty(params.getCreate_start())) {
                sql += " and t.tid_time>=to_date('" + params.getCreate_start() + "','yyyy-mm-dd hh24:mi:ss')";
                countSql += " and t.tid_time>=" + DBTUtil.to_sql_date("'" + params.getCreate_start() + "'", 2);
                // list.add(params.getCreate_start());
            }
            if (!StringUtil.isEmpty(params.getOut_call()) && !"-1".equals(params.getOut_call()) && "1".equals(params.getOut_call())) {
                sql += " and s.status='11' ";
                countSql += " and s.status='11' ";
                // list.add(params.getCreate_start());
            }
            if (!StringUtil.isEmpty(params.getOut_call()) && !"-1".equals(params.getOut_call()) && "0".equals(params.getOut_call())) {
                sql += " and s.status !='11' ";
                countSql += " and s.status !='11' ";
                // list.add(params.getCreate_start());
            }
            // 创建时间结束
            if (!StringUtil.isEmpty(params.getCreate_end())) {
                sql += " and t.tid_time<=to_date('" + params.getCreate_end() + "','yyyy-mm-dd hh24:mi:ss')";
                countSql += " and t.tid_time<=" + DBTUtil.to_sql_date("'" + params.getCreate_end() + "'", 2);
                // list.add(params.getCreate_end());
            }
        }
        sql += " order by t.tid_time desc";

        page = this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, LockOrder.class, countSql, list.toArray());

        return page;
    }

    @SuppressWarnings("unchecked")
    public Page getAllotOrdList(int pageNo, int pageSize, OrderQueryParams params) { // 获取当前人锁定的订单
        Page page = null;
        String countSql = "select count(*) from es_order_ext t " + " inner join es_order a on t.order_id=a.order_id "
                + " left join es_order_lock l on t.order_id = l.order_id and t.flow_trace_id = l.tache_code "
                + " left join es_order_extvtl e on t.order_id=e.order_id" + " where  t.source_from = '"
                + ManagerUtils.getSourceFrom() + "' and (t.visible_status='" + EcsOrderConsts.VISIBLE_STATUS_0
                + "' or (t.visible_status='" + EcsOrderConsts.VISIBLE_STATUS_1 + "' and t.abnormal_type in('"
                + EcsOrderConsts.ORDER_ABNORMAL_TYPE_2 + "','" + EcsOrderConsts.ORDER_ABNORMAL_TYPE_3 + "') )) ";

        String sql = " select o.order_id,o.out_tid,o.tid_time,o.lock_user_id,o.goods_name,o.lock_status,o.order_from,o.lock_user_name,o.lock_user_realname,o.is_work_custom from ( "
                + "select t.order_id,t.out_tid,t.tid_time,t.is_work_custom, l.lock_user_id,(select username from es_adminuser where userid=l.lock_user_id) lock_user_name,e.goodsname as goods_name,"
                + "l.lock_status,t.order_from,(select realname from es_adminuser where userid=l.lock_user_id) lock_user_realname from es_order_ext t "
                + " inner join es_order a on t.order_id=a.order_id "
                + " left join es_order_lock l on t.order_id = l.order_id and t.flow_trace_id = l.tache_code "
                + " left join es_order_extvtl e on t.order_id=e.order_id" + " where  t.source_from = '"
                + ManagerUtils.getSourceFrom() + "' and (t.visible_status='" + EcsOrderConsts.VISIBLE_STATUS_0
                + "' or (t.visible_status='" + EcsOrderConsts.VISIBLE_STATUS_1 + "' and t.abnormal_type in('"
                + EcsOrderConsts.ORDER_ABNORMAL_TYPE_2 + "','" + EcsOrderConsts.ORDER_ABNORMAL_TYPE_3 + "') )) ";
        List list = new ArrayList();
        // 任何人都可以解锁X
        /*
         * if(Consts.CURR_FOUNDER1 !=ManagerUtils.getFounder()){ countSql
         * +="and  t.lock_user_id = ?"; sql += "and  t.lock_user_id = ?";
         * list.add(ManagerUtils.getUserId()); }
         */
        if (params != null) {
            if (!StringUtils.isEmpty(params.getLock_user_id())) {
                sql += " and l.lock_user_id ='" + params.getLock_user_id() + "' ";
                list.add(params.getLock_user_id());
            }
            if (!StringUtils.isEmpty(params.getOrder_id())) {
                sql += " and t.order_id ='" + params.getOrder_id() + "'";
                list.add(params.getOrder_id());
            }
            if (!StringUtils.isEmpty(params.getOut_tid())) {
                sql += " and t.out_tid ='" + params.getOut_tid() + "' ";
                list.add(params.getOut_tid());
            }
            if (!StringUtils.isEmpty(params.getOrder_from())) {
                sql += " and t.order_from in('" + params.getOrder_from().replace(",", "','") + "') ";
            }
            if (!StringUtils.isEmpty(params.getOrder_type())) {
                sql += " and a.order_type in('" + params.getOrder_type().replace(",", "','") + "') ";
            }
            //证件上传状态：意向单没有证件上传，只查询正式单
            if (!StringUtils.isEmpty(params.getOrder_status())) {
            	if("9".equals(params.getOrder_status())){
            		sql += " and (t.if_send_photos ='" + params.getOrder_status() + "'";
            		sql += " or t.if_send_photos is null )";
            	}else
            		sql += " and t.if_send_photos ='" + params.getOrder_status() + "'";
            }
            if (!StringUtils.isEmpty(params.getFlow_id())) {
                sql += " and t.flow_trace_id in('" + params.getFlow_id().replace(",", "','") + "') ";
            }
            if (!StringUtils.isEmpty(params.getOrder_city_code())) {
                sql += " and t.order_city_code in('" + params.getOrder_city_code().replace(",", "','") + "') ";
            }
            if (!StringUtils.isEmpty(params.getPhone_num())) {
                sql += " and exists(select 1 from es_order_items_ext o where t.order_id=o.order_id and t.source_from=o.source_from and o.phone_num = '"
                        + params.getPhone_num() + "') ";
                list.add(params.getPhone_num());
            }
            if (!StringUtils.isEmpty(params.getGoods_name())) {
                sql += " and exists(select 1 from es_order_extvtl o where t.order_id=o.order_id and t.source_from=o.source_from and o.goodsname like '%"
                        + params.getGoods_name() + "%')";
                list.add(params.getGoods_name());
            }
            if (!StringUtil.isEmpty(params.getCreate_start())) {
                sql += " and t.tid_time>=to_date('" + params.getCreate_start() + "','yyyy-mm-dd hh24:mi:ss')";
                countSql += " and t.tid_time>=" + DBTUtil.to_sql_date("?", 2);
                list.add(params.getCreate_start());
            }
            // 创建时间结束
            if (!StringUtil.isEmpty(params.getCreate_end())) {
                sql += " and t.tid_time<=to_date('" + params.getCreate_end() + "','yyyy-mm-dd hh24:mi:ss')";
                countSql += " and t.tid_time<=" + DBTUtil.to_sql_date("?", 2);
                list.add(params.getCreate_end());
            }
            sql += " and a.status<>11 and a.status<>12 and t.abnormal_status='0' ";
            sql += " and t.flow_trace_id not in ('J','L')";
        }
        // 订单分配增加环节权限
        AdminUser user = ManagerUtils.getAdminUser();
        String tacheAuths = this.getUserFlowTraceAuth();
        
        if (user.getFounder() != 1) {
            if (!StringUtils.isEmpty(tacheAuths)) {
                sql += " and t.flow_trace_id in(" + tacheAuths + ") ";
                countSql += " and t.flow_trace_id in(" + tacheAuths + ") ";
            } else {
                sql += "and 1=2 ";
                countSql += "and 1=2 ";
            }
        }

        sql += " ) o where 1=1";
        if (params != null) {
            if (!StringUtils.isEmpty(params.getLock_status())) {
                if ("1".equals(params.getLock_status())) {
                    sql += " and o.lock_status = '" + params.getLock_status() + "'";
                }
                if ("0".equals(params.getLock_status())) {
                    sql += " and (o.lock_status = '" + params.getLock_status() + "' or o.lock_status is null)";
                }

            }
        }
        sql += " order by o.tid_time desc";
        logger.info("订单分配旧："+sql);
        page = this.baseDaoSupport.queryForPageByMap(sql, pageNo, pageSize, LockOrder.class, null);
        return page;
    }

    @SuppressWarnings("unchecked")
    public Page newGetAllotOrdList(int pageNo, int pageSize, OrderQueryParams params) { // 获取当前人锁定的订单
        Page page = null;
        /*
         * String countSql = "select count(*) from es_order_ext t " +
         * " inner join es_order a on t.order_id=a.order_id " +
         * " left join es_order_lock l on t.order_id = l.order_id and t.flow_trace_id = l.tache_code "
         * + " left join es_order_extvtl e on t.order_id=e.order_id" +
         * " where  t.source_from = '"+ManagerUtils.getSourceFrom()
         * +"' and (t.visible_status='"+EcsOrderConsts.
         * VISIBLE_STATUS_0+"' or (t.visible_status='"+EcsOrderConsts.
         * VISIBLE_STATUS_1+"' and t.abnormal_type in('"+EcsOrderConsts.
         * ORDER_ABNORMAL_TYPE_2+"','"+EcsOrderConsts.ORDER_ABNORMAL_TYPE_3+"') )) " ;
         */

        String sqla = "select ship_name,ship_tel,o.order_id,o.out_tid,o.tid_time,o.lock_user_id,o.goods_name,o.lock_status,o.order_from,o.lock_user_name,o.lock_user_realname,order_from_n,allot_status,order_city_code,o.county_code,order_county_code,o.is_work_custom  from (select ed.ship_name,ed.ship_tel,t.order_id,t.out_tid,"
                + " t.tid_time,l.lock_user_id,t.is_work_custom,adm.username as lock_user_name,e.goodsname as goods_name,l.lock_status,t.order_from,"
                + " adm.realname as lock_user_realname,pud.pname as order_from_n,dc.pname as allot_status, t1.field_value_desc as order_city_code,"
                + " t2.field_value as county_code,t2.field_value_desc as order_county_code  from es_order_ext t inner join es_order a on t.order_id = a.order_id"
                + " left join es_dc_public_ext pud on pud.pkey=t.order_from  and pud.stype = 'source_from' "
                + " left join es_order_lock l on (t.order_id = l.order_id and t.flow_trace_id = l.tache_code) left join es_order_items_ext ie on t.order_id = ie.order_id"
                + " left join es_adminuser adm on adm.userid=l.lock_user_id left join es_order_extvtl e"
                + " on t.order_id = e.order_id left join es_dc_public_dict_relation t2  on t2.field_value= e.district_code and t2.stype='county'"
                + " left join es_dc_public_ext dc  on dc.pkey= e.allot_status and dc.stype='ALLOT_STATUS' left join es_order_ext eoe"
                + " on t.order_id = eoe.order_id left join es_dc_public_dict_relation  t1"
                + " on t1.field_value= eoe.order_city_code and t1.stype='bss_area_code'  left join es_delivery ed on t.order_id = ed.order_id "
                + " where t.source_from = '" + ManagerUtils.getSourceFrom() + "' and (t.visible_status = '"
                + EcsOrderConsts.VISIBLE_STATUS_0 + "' " + " or (t.visible_status = '" + EcsOrderConsts.VISIBLE_STATUS_1
                + "' and t.abnormal_type in ('" + EcsOrderConsts.ORDER_ABNORMAL_TYPE_2 + "', '"
                + EcsOrderConsts.ORDER_ABNORMAL_TYPE_3 + "')))";

        /**
         * String sql = " select
         * ship_name,ship_tel,o.order_id,o.out_tid,o.tid_time,o.lock_user_id,o.goods_name,o.lock_status,o.order_from,o.lock_user_name,o.lock_user_realname,order_from_n,allot_status,
         * order_city_code,order_county_code from ( " + "select
         * ed.ship_name,ed.ship_tel,t.order_id,t.out_tid,t.tid_time,
         * l.lock_user_id,(select username from es_adminuser where
         * userid=l.lock_user_id) lock_user_name,e.goodsname as goods_name," +
         * "l.lock_status,t.order_from,(select realname from es_adminuser where
         * userid=l.lock_user_id) lock_user_realname," + " ( select pname from
         * es_dc_public_ext where stype='source_from' and pkey=t.order_from )
         * order_from_n,( select pname from es_dc_public_ext where stype='ALLOT_STATUS'
         * and pkey=e.allot_status) as allot_status,(select field_value_desc from
         * es_dc_public_dict_relation t where stype='bss_area_code' and
         * field_value=eoe.order_city_code) as order_city_code,(select field_value_desc
         * from es_dc_public_dict_relation where stype ='county' and
         * field_value=e.district_code) as order_county_code from es_order_ext t " + "
         * inner join es_order a on t.order_id=a.order_id " + " left join es_order_lock
         * l on t.order_id = l.order_id and t.flow_trace_id = l.tache_code " + " left
         * join es_order_extvtl e on t.order_id=e.order_id " + " left join es_order_ext
         * eoe on t.order_id = eoe.order_id left join es_delivery ed on t.order_id =
         * ed.order_id" + " where t.source_from = '" + ManagerUtils.getSourceFrom() + "'
         * and (t.visible_status='" + EcsOrderConsts.VISIBLE_STATUS_0 + "' or
         * (t.visible_status='" + EcsOrderConsts.VISIBLE_STATUS_1 + "' and
         * t.abnormal_type in('" + EcsOrderConsts.ORDER_ABNORMAL_TYPE_2 + "','" +
         * EcsOrderConsts.ORDER_ABNORMAL_TYPE_3 + "') )) ";
         * 
         * String sql1 = " union all " + " select
         * n.ship_name,n.ship_tel,n.order_id,n.intent_order_id out_tid,n.create_time
         * tid_time,n.lock_id lock_user_id,(select username " + " from es_adminuser " +
         * " where userid = n.lock_id) lock_user_name,n.goods_name,'1'
         * lock_status,n.source_system_type order_from ,(select realname " + " from
         * es_adminuser " + " where userid = n.lock_id) lock_user_realname," + " (
         * select pname from es_dc_public_ext where stype='source_from' and
         * pkey=n.source_system_type) order_from_n,( select pname from es_dc_public_ext
         * where stype='ALLOT_STATUS' and pkey=n.allot_status) as allot_status,(select
         * field_value_desc from es_dc_public_dict_relation t where stype =
         * 'bss_area_code' and field_value = n.order_city_code) as order_city_code,
         * (select field_value_desc from es_dc_public_dict_relation where stype =
         * 'county' and field_value = n.order_county_code) as order_county_code from
         * es_order_intent n" + " where (n.order_id not in (select order_id from
         * es_work_order ) or n.order_id in (select order_id from es_work_order where
         * status='2' )) ";
         **/

        String sqlb = "union all"
                + " select n.ship_name,n.ship_tel,n.order_id,n.intent_order_id out_tid,n.create_time tid_time, n.lock_id lock_user_id, n.is_work_custom, adm.username as lock_user_name,"
                + " n.goods_name,'1' lock_status,n.source_system_type order_from, adm.realname as lock_user_realname,t3.pname   as order_from_n,t4.pname as allot_status,r1.field_value_desc as order_city_code, n.order_county_code as  county_code,(select distinct other_field_value_desc from es_dc_public_dict_relation "
                + " where stype = 'county' and field_value = n.order_county_code) as order_county_code from es_order_intent n left join es_adminuser adm "
                + " on adm.userid = n.lock_id  left join es_work_custom_node_ins ins on n.order_id=ins.order_id and ins.is_curr_step='1' and ins.is_done='0'"
                + " left join es_dc_public_ext t3 on t3.pkey=n.source_system_type and t3.stype = 'source_from'left join es_dc_public_ext t4 on t4.pkey=n.allot_status and t4.stype = 'ALLOT_STATUS' left join es_dc_public_dict_relation r1 on r1.field_value=n.order_city_code and r1.stype='bss_area_code' left join es_dc_public_dict_relation re on re.field_value=n.order_county_code and re.stype='county' where "
                + " n.order_id not in(select order_id from es_work_order where status in ('0','4') and order_id =n.order_id ) and n.status not in ('00','03') and (n.is_online_order <> '1' or n.is_online_order is null) ";

        // List list = new ArrayList();
        // 任何人都可以解锁X
        /*
         * if(Consts.CURR_FOUNDER1 !=ManagerUtils.getFounder()){ countSql
         * +="and  t.lock_user_id = ?"; sql += "and  t.lock_user_id = ?";
         * list.add(ManagerUtils.getUserId()); }
         */
        String where_sql = "";
        String where_sql1 = "";
        if (params != null) {

            if (!StringUtils.isEmpty(params.getOrder_id())) {
                where_sql += " and t.order_id ='" + params.getOrder_id() + "'";
                where_sql1 += " and n.order_id ='" + params.getOrder_id() + "'";
                // list.add(params.getOrder_id());
            }
            if (!StringUtils.isEmpty(params.getShip_name())) {
                where_sql += " and ed.ship_name ='" + params.getShip_name() + "'";
                where_sql1 += " and n.ship_name ='" + params.getShip_name() + "'";
                // list.add(params.getOrder_id());
            }
            //证件上传状态：意向单没有证件上传，只查询正式单，避开意向单
            if (!StringUtils.isEmpty(params.getOrder_status())) {
                if("9".equals(params.getOrder_status())){
                	where_sql += " and (t.if_send_photos ='" + params.getOrder_status() + "'";
            		where_sql += " or t.if_send_photos is null )";
            	}else{
            		where_sql += " and t.if_send_photos ='" + params.getOrder_status() + "'";
            		where_sql1 += " and n.source_from ='intent'";
            	}
            }
            if (!StringUtils.isEmpty(params.getShip_tel())) {
                where_sql += " and ed.ship_tel ='" + params.getShip_tel() + "'";
                where_sql1 += " and n.ship_tel ='" + params.getShip_tel() + "'";
                // list.add(params.getOrder_id());
            }
            if (!StringUtils.isEmpty(params.getPhone_num())) {
                where_sql += " and ie.phone_num ='" + params.getPhone_num() + "'";
                where_sql1 += " and n.acc_nbr ='" + params.getPhone_num() + "'";
            }
            // 地市
            if (!StringUtils.isEmpty(params.getOrder_city_code())) {
                where_sql += " and eoe.order_city_code = '" + params.getOrder_city_code() + "'";
                where_sql1 += " and n.order_city_code = '" + params.getOrder_city_code() + "'";
            }

            // 县分
            if (!StringUtils.isEmpty(params.getOrder_county_code())) {
                where_sql += " and e.district_code ='" + params.getOrder_county_code() + "'";
                where_sql1 += " and n.order_county_code='" + params.getOrder_county_code() + "'";
            }
            if (!StringUtils.isEmpty(params.getOut_tid())) {
                where_sql += " and t.out_tid ='" + params.getOut_tid() + "' ";
                where_sql1 += " and n.intent_order_id ='" + params.getOut_tid() + "' ";
                // list.add(params.getOut_tid());
            }

            if (!StringUtils.isEmpty(params.getGoods_name())) {
                where_sql += " and exists(select 1 from es_order_extvtl o where t.order_id=o.order_id and t.source_from=o.source_from and o.goodsname like '%"
                        + params.getGoods_name() + "%')";
                where_sql1 += " and n.goods_name like '%" + params.getGoods_name() + "%' ";
                // list.add(params.getGoods_name());
            }
            if (!StringUtil.isEmpty(params.getCreate_start())) {
                where_sql += " and t.tid_time>=to_date('" + params.getCreate_start() + "','yyyy-mm-dd hh24:mi:ss') ";
                where_sql1 += " and n.create_time >=to_date('" + params.getCreate_start()
                        + "','yyyy-mm-dd hh24:mi:ss') ";
                // countSql += " and t.tid_time>="+DBTUtil.to_sql_date("?", 2);
                // list.add(params.getCreate_start());
            }
            // 创建时间结束
            if (!StringUtil.isEmpty(params.getCreate_end())) {
                where_sql += " and t.tid_time<=to_date('" + params.getCreate_end() + "','yyyy-mm-dd hh24:mi:ss')";
                where_sql1 += " and n.create_time <=to_date('" + params.getCreate_end() + "','yyyy-mm-dd hh24:mi:ss') ";
                // countSql += " and t.tid_time<="+DBTUtil.to_sql_date("?", 2);
                // list.add(params.getCreate_end());
            }
            where_sql += " and a.status<>11 and a.status<>12 and t.abnormal_status='0' ";
            where_sql += " and t.flow_trace_id not in ('J','L')";
        }
        AdminUser user = ManagerUtils.getAdminUser();
        where_sql += " and l.lock_user_id ='" + user.getUserid() + "' ";
        where_sql1 += " and n.lock_id ='" + user.getUserid() + "' ";
        // 订单分配增加环节权限
        /*
         * AdminUser user = ManagerUtils.getAdminUser(); String tacheAuths =
         * user.getTacheAuth();//环节权限'B','','' if(user.getFounder()!=1){
         * if(!StringUtils.isEmpty(tacheAuths)) { sql +=
         * " and t.flow_trace_id in("+tacheAuths+") "; countSql +=
         * " and t.flow_trace_id in("+tacheAuths+") "; }else { sql += "and 1=2 ";
         * countSql += "and 1=2 "; } }
         */
        // sqla sqlb
        sqla = sqla + where_sql + sqlb + where_sql1;
        sqla += " ) o where 1=1";

        sqla += " and o.lock_status = '1'";

        sqla += " order by o.tid_time desc";
        logger.info("订单分配SQL:" + sqla);
        page = this.baseDaoSupport.queryForPageByMap(sqla, pageNo, pageSize, LockOrder.class, null);
        List<LockOrder> list = page.getResult();
        for (int i = 0; i < list.size(); i++) {
            LockOrder lockOrder = list.get(i);
            String order_id = lockOrder.getOrder_id();
            String is_yxd_sql = " select decode((select order_id from es_order_intent where source_from = '"
                    + ManagerUtils.getSourceFrom() + "'  and (is_online_order <> 1 or is_online_order is null) and order_id='" + order_id
                    + "'),null,'N','Y') is_yxd from dual ";
            String is_yxd=this.baseDaoSupport.queryForString(is_yxd_sql);
            String handle_sql ="";
            if ("N".equals(is_yxd)) {
                handle_sql = " select a.comments remark,a.create_time,(select realname from es_adminuser where userid=a.op_id) username from es_order_handle_logs a where a.source_from='"
                        + ManagerUtils.getSourceFrom() + "' and a.order_id='" + order_id + "' order by a.create_time desc ";
            }else{
                handle_sql = " select a.remark,a.create_time,(select realname from es_adminuser where userid=a.op_id) username from es_intent_handle_logs a where a.source_from='"
                        + ManagerUtils.getSourceFrom() + "' and a.order_id='" + order_id + "' order by a.create_time desc ";
            }
            List<Map<String, Object>> handle_list = this.baseDaoSupport.queryForList(handle_sql, new RowMapper() {
                public Object mapRow(ResultSet rs, int c) throws SQLException {
                    Map data = new HashMap();
                    data.put("remark", rs.getString("remark")); //
                    data.put("create_time", rs.getString("create_time")); //
                    data.put("username", rs.getString("username"));
                    return data;
                }
            }, null);
            lockOrder.setIntenthandle(handle_list);
        }
        return page;
    }

    //订单分配
    public Page getUserList(int pageNo, int pageSize, String userparams, String allotType, String lockOrderIdStr,
            String allot_county_id) {
        String sql_user = "";
        String[] str = null;
        if(!StringUtil.isEmpty(allotType) && !"query".equals(allotType)){
            str = lockOrderIdStr.split(",");
            //正式订单自定义流程人员分配查询  add by wjq
            if(str.length==1){
                String is_yxd_sql = " select decode((select order_id from es_order_intent where source_from = '"
                        + ManagerUtils.getSourceFrom() + "'  and (is_online_order <> 1 or is_online_order is null) and order_id='" + str[0]
                        + "'),null,'N','Y') is_yxd from dual ";
                String is_yxd=this.baseDaoSupport.queryForString(is_yxd_sql);
                if ("N".equals(is_yxd)) {
                    String is_work_sql = "  select t.is_work_custom from es_order_ext t where t.source_from='"+ManagerUtils.getSourceFrom()+"' and t.order_id='"+str[0]+"' ";
                    String isWork = this.baseDaoSupport.queryForString(is_work_sql);
                    if(("1".equals(isWork))){
                        allotType="formal";
                    }
                }
            }
        }
        
        if (!StringUtil.isEmpty(allotType) && "formal".equals(allotType)) {
            String city_code="";
            try {
                city_code = this.getOrderCityOrder(str[0]);
            
            //sql_user="select a.deal_level permission_level,a.region_name city,a.county_name busicityAuth,a.dealer_type col5,a.dealer_name realname,a.dealer_code username,a.dealer_code userid,a.dealer_name team_name from es_work_custom_dealer a left join es_work_custom_node_ins esw on esw.cfg_id=a.cfg_id and a.node_index = esw.node_index where esw.order_id='"+str[0]+"' and esw.is_curr_step=1";
            sql_user="(select distinct a.busicty_id as busicityauth,e.userid,e.username,e.realname,e.phone_num,ewc.deal_level  permission_level,ewc.region_name city " + 
            		"from es_role_data a  " + 
            		"inner join es_role_auth b on a.id = b.authid and a.flow_node is not null " + 
            		"inner join es_role c on b.roleid = c.roleid and b.source_from = c.source_from " + 
            		"inner join es_user_role d on c.roleid = d.roleid and c.source_from = d.source_from " + 
            		"inner join es_adminuser e on d.userid = e.userid and d.source_from = e.source_from " + 
            		"inner join es_user_team_rel eut on eut.userid=e.userid " + 
            		"inner join es_work_custom_dealer ewc on eut.team_id=ewc.dealer_code " + 
            		"inner join es_work_custom_node_ins esw on esw.cfg_id=ewc.cfg_id and ewc.node_index = esw.node_index " + 
            		"where esw.is_curr_step=1 and ewc.dealer_type='team'  and esw.order_id='"+str[0]+"' and ewc.region_id='"+city_code+"' "+
            		" and e.userId<> ";
            String sql_tache="select distinct ext.flow_trace_id from es_order_ext ext where ext.order_id='"+str[0]+"' ";
            String tach_code=this.baseDaoSupport.queryForString(sql_tache);
            String userId="";
            OrderTreeBusiRequest ordertree=CommonDataFactory.getInstance().getOrderTree(str[0]);
            List<OrderLockBusiRequest> orderLockBusiRequests =ordertree.getOrderLockBusiRequests();
            for(OrderLockBusiRequest orderLockBusiRequest:orderLockBusiRequests) {
            	if(orderLockBusiRequest.getTache_code().equals(tach_code)) {
            		userId=orderLockBusiRequest.getLock_user_id();
            	}
            }
            if(!StringUtil.isEmpty(userId)) {
            	sql_user=sql_user+"'"+userId+"'";
            }else {
            	sql_user=sql_user+" (select lo.lock_user_id from es_order_lock lo where lo.order_id='"+str[0]+"'  " + 
                		" and lo.tache_code=(select ext.flow_trace_id from es_order_ext ext where ext.order_id='"+str[0]+"'))";
            }
            if (!StringUtils.isEmpty(userparams)) {
                 sql_user += " and (e.realname like '%"+userparams+"%' or e.userid like '%"+userparams+"%' ) ";
             }
            if (!StringUtils.isEmpty(allot_county_id)) {
                sql_user += " and instr(a.busicty_id,'" + allot_county_id + "') > 0 ";
            }
            sql_user+=" ) ";
            sql_user +="union all " + 
            		"(select distinct ewc.county_name busicityauth,ewc.dealer_code userid,ewc.dealer_code username,ewc.dealer_name realname, " + 
            		"adm.phone_num,ewc.deal_level  permission_level, " + 
            		"       ewc.region_name city " + 
            		"  from es_work_custom_dealer ewc " + 
            		"  left join es_adminuser adm on ewc.dealer_code=adm.userid " + 
            		"  left join es_work_custom_node_ins esw " + 
            		"    on esw.cfg_id = ewc.cfg_id " + 
            		"   and ewc.node_index = esw.node_index " + 
            		" where esw.is_curr_step = 1  and ewc.dealer_type='person' and esw.order_id='"+str[0]+"' and ewc.region_id='"+city_code+"' "+
                    		" and adm.userId<> ";
            if(!StringUtil.isEmpty(userId)) {
            	sql_user=sql_user+"'"+userId+"'";
            }else {
            	sql_user=sql_user+" (select lo.lock_user_id from es_order_lock lo where lo.order_id='"+str[0]+"'  " + 
                		" and lo.tache_code=(select ext.flow_trace_id from es_order_ext ext where ext.order_id='"+str[0]+"'))";
            }
            if (!StringUtils.isEmpty(userparams)) {
                sql_user += " and (adm.realname like '%"+userparams+"%' or adm.userid like '%"+userparams+"%' )";
            }
            if (!StringUtils.isEmpty(allot_county_id)) {
            	StringBuffer sql_county_name=new StringBuffer();
            	sql_county_name.append("select t.field_value_desc from es_dc_public_dict_relation  t  where  t.stype='county' and t.other_field_value ='").append(allot_county_id).append("' ");
            	String county_name=baseDaoSupport.queryForString(sql_county_name.toString());
                sql_user += " and instr(ewc.county_name,'" + county_name + "') > 0 ";
            }
            sql_user+=" ) ";
            logger.info("自定义流程分配查询SQL"+sql_user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!StringUtil.isEmpty(allotType) && "query".equals(allotType)) {
            sql_user = "select t.userid,t.username,t.realname,t.phone_num from es_adminuser t where 1=1 ";
            if (!StringUtils.isEmpty(userparams)) {
                sql_user += " and  t.userid like '%" + userparams + "%'";
            }
        }
        if (!StringUtil.isEmpty(allotType) && "allot".equals(allotType)) {
            sql_user = " select distinct a.busicty_id as busicityauth,e.userid,e.username,e.realname,e.phone_num "
                    + " from es_role_data a, " + " es_role_auth b, " + " es_role      c, " + " es_user_role d, "
                    + " es_adminuser e " + " where a.id = b.authid " + " and a.source_from = b.source_from "
                    + " and b.roleid = c.roleid " + " and b.source_from = c.source_from " + " and c.roleid = d.roleid "
                    + " and c.source_from = d.source_from " + " and d.userid = e.userid "
                    + " and d.source_from = e.source_from " + " and a.flow_node is not null ";
            
            if (str.length > 0) {
                sql_user += " and ( ";
                for (int j = 0; j < str.length; j++) {

                    String order_id = str[0];
                    OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);// CommonDataFactory.getInstance().getOrderTree(order_id);
                    OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
                    String trace_id = orderExt.getFlow_trace_id();
                    sql_user += " instr(a.flow_node,'" + trace_id + "') > 0 or ";
                }
                sql_user = sql_user.substring(0, sql_user.lastIndexOf("or"));
                sql_user += " ) ";

            }
            if (!StringUtils.isEmpty(userparams)) {
                sql_user += " and (e.username like '%" + userparams + "%' or e.realname like '%" + userparams
                        + "%' or e.phone_num like '%" + userparams + "%' )";

            }
            if (!StringUtils.isEmpty(allot_county_id)) {
                sql_user += " and instr(a.busicty_id,'" + allot_county_id + "') > 0 ";
            }
        }

        Page page = this.baseDaoSupport.queryForPageByMap(sql_user, pageNo, pageSize, AdminUser.class, null);
        List dc_list = getDcSqlByDcName("DC_ORDER_BUSICTY");
        if (dc_list.size() <= 0) {
            return page;
        }
        
        
        if (page.getTotalCount() > 0) {
            List<AdminUser> list = page.getResult();
            for (int i = 0; i < list.size(); i++) {
                String new_busicity = "";
                AdminUser adminUser = list.get(i);
                adminUser.setCol5("person");
                
                if (StringUtils.isEmpty(adminUser.getBusicityAuth())) {
                    continue;
                }
                String [] busicity = adminUser.getBusicityAuth().split(",");
                for (int j = 0; j < busicity.length; j++) {
                    for (int j2 = 0; j2 < dc_list.size(); j2++) {
                        Map map = (Map) dc_list.get(j2);
                        if (busicity[j].equals(Const.getStrValue(map, "pkey"))) {
                            busicity[j] = Const.getStrValue(map, "pname");
                            break;
                        }
                    }
                    new_busicity += busicity[j] + ",";
                }
                if (!StringUtils.isEmpty(new_busicity)) {
                    list.get(i).setBusicityAuth(new_busicity);
                }

            }
            
        }
        return page;

    }

    public Page newGetUserList(int pageNo, int pageSize, String userparams, String allotType, String lockOrderIdStr,
            String allot_county_id, String county_code) throws Exception {
        Page page = null;

        // is_work_custom 需要传参
        // if (!StringUtil.isEmpty(is_work_custom)) {
        // page = queryWorkCustomUserList(pageNo, pageSize, userparams, lockOrderIdStr);
        // } else {
        page = newgetUserListQueery(pageNo, pageSize, userparams, allotType, lockOrderIdStr, allot_county_id,
                county_code);
        // }
        return page;
    }
    
    /**
     * 获取订单地市
     * @param order_id
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String getOrderCityOrder(String order_id) throws Exception{
        StringBuilder builder = new StringBuilder();
        
        builder.append("SELECT a.order_city_code FROM es_order_ext a WHERE a.order_id='"+order_id+"'");
        builder.append(" and a.source_from='ECS' ");
        builder.append(" union all  ");
        builder.append("SELECT a.order_city_code FROM es_order_intent a WHERE a.order_id='"+order_id+"'");
        builder.append(" and a.source_from='ECS' ");
        
        List<Map> ret = this.baseDaoSupport.queryForList(builder.toString());
        
        if(ret!=null && ret.size()>0){
            String order_city_code = String.valueOf(ret.get(0).get("order_city_code"));
            
            if(org.apache.commons.lang.StringUtils.isBlank(order_city_code) 
                    || "null".equals(order_city_code)){
                return "";
            }else{
                return order_city_code;
            }
        }else{
            return "";
        }
    }
    
    /**
     * 查询虚拟工号（订单池）
     * @param pre_fix
     * @param order_from
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private List<String> getPoolCodes(String order_city_code,String order_from,String allot_county_id,String county_code) throws Exception{
        List<String> ret = new ArrayList<String>();
        
        StringBuilder builder = new StringBuilder();
        
        builder.append("select pkey value,pname value_desc from es_dc_public_ext where stype = 'DIC_COUNTY_USERS_" + order_from + "' ");
        builder.append(" and pkey in (select a.field_value from es_dc_public_dict_relation a where a.col1 = '"+order_city_code+"' and a.stype='county' ");
        if (!StringUtil.isEmpty(county_code)) {
            builder.append(" and a.field_value = '"+county_code+"' ");
        }
        if (!StringUtil.isEmpty(allot_county_id)) {
            builder.append(" and a.other_field_value = '"+allot_county_id+"' ");
        }
        builder.append(")");
        if(StringUtil.isEmpty(allot_county_id) && StringUtil.isEmpty(county_code)) {
            builder.append(" union all select pkey value, pname value_desc  from es_dc_public_ext where stype = 'DIC_CITY_USERS_" + order_from + "' and pkey = '"+order_city_code+"'");
        }
        
        
        List<Map> ret_Map_List = this.baseDaoSupport.queryForList(builder.toString());
        
        if(ret_Map_List!=null && ret_Map_List.size()>0){
            for(int i=0;i<ret_Map_List.size();i++){
                Map m = ret_Map_List.get(i);
                
                if(m.containsKey("value_desc")){
                    ret.add(String.valueOf(m.get("value_desc")));
                }
            }
        }
        logger.info("领取工号sql:" + builder.toString());
        return ret;
    }
    
    /**
     * 查询人员SQL
     * @param order_from
     * @param order_id
     * @return
     * @throws Exception
     */
    private String getQueryUserSql(String order_from,String order_id,String allot_county_id,String county_code) throws Exception{
        // 查询订单地市
        String order_city_code = this.getOrderCityOrder(order_id);
        
        AdminUser user = ManagerUtils.getAdminUser();
        
        
        
        // 根据领取工号查询人员
        StringBuilder builder = new StringBuilder();
        
        if("1".equals(user.getUserid())){
            builder.append(" select distinct a.busicty_id,u.userid,u.username,u.realname,u.phone_num,u.org_id ");
            builder.append("  from es_adminuser u left join es_user_role d on u.userid=d.userid and u.source_from=d.source_from ");
            builder.append("  left join es_role c on c.roleid=d.roleid and c.source_from= d.source_from ");
            builder.append("  left join es_role_auth b on b.roleid=c.roleid and b.source_from = c.source_from ");
            builder.append("  left join es_role_data a on a.id= b.authid and a.source_from = b.source_from ");
            builder.append("  where a.source_from = 'ECS'  and  a.flow_node is not null and u.state='1' ");
             
             if (org.apache.commons.lang.StringUtils.isNotBlank(allot_county_id)) {
                builder.append(" and instr(a.busicty_id,'" + allot_county_id + "') > 0 ");
             }
             
             if (!StringUtil.isEmpty(county_code)) {
                builder.append(" and instr(a.busicty_id,(select distinct t.other_field_value from es_dc_public_dict_relation t where stype ='county' and t.field_value='"+county_code + "'))> 0");
             }
        }else{
            // 根据订单来源查询领取工号
            List<String> pool_codes = this.getPoolCodes(order_city_code, order_from, allot_county_id, county_code);
            builder.append(" SELECT u.userid,u.username,u.realname,u.phone_num,u.org_id FROM es_adminuser u ");
            builder.append(" WHERE u.source_from = 'ECS' ");
            builder.append(" and U.State='1' ");
            if(pool_codes!=null && pool_codes.size()>0){
                
                List<String> pool_temp = new ArrayList<String>();
                
                for(int i=0;i<pool_codes.size();i++){
                    pool_temp.add(pool_codes.get(i)+",");
                }
                
                //加上虚拟工号限制
                builder.append("  and (exists ");
                
                builder.append("  (SELECT c.id FROM es_user_role a, es_role_auth b, es_role_data c ");
                builder.append("  WHERE a.userid = u.userid ");
                builder.append("  and a.roleid = b.roleid ");
                builder.append("  and b.authid = c.id ");
                builder.append("  and a.source_from = 'ECS' ");
                
                builder.append("  and ( ");
                
                for(int i=0;i<pool_temp.size();i++){
                    if(i!=0)
                        builder.append(" or ");
                    
                    builder.append("  c.ord_receive_user = '").append(pool_temp.get(i)).append("' ");
                }
                
                builder.append("  ) ");
                
                builder.append("  ) ");
                builder.append(" or ( 1=1 ").append(SqlUtil.getSqlInStr("u.userid", pool_codes, false, true)).append(" ) ");
                
                builder.append("  ) ");
            }else{
                builder.append(" and 1=2 ");
            }
        }
        
        logger.info("非自定义流程订单分配人员sql:" + builder.toString());
        return builder.toString();
    }

    /**
     * // 自定义分配人员查询 public Page queryWorkCustomUserList(int pageNo, int pageSize,
     * String userparams, String lockOrderIdStr) { String sql_user = null; //
     * 如果是自定义订单，取出订单组织编码、订单需是组织的 Map map = queryDealerCode(lockOrderIdStr); String
     * dealer_type = map.get("dealer_type").toString(); String dealer_code =
     * map.get("dealer_code").toString();
     * 
     * if (!StringUtil.isEmpty(dealer_code)) {
     * 
     * // default 默认省中台虚拟工号 if (!StringUtil.isEmpty(dealer_type) &&
     * "default".equals(dealer_type)) { sql_user = "select a.realname as
     * realname,a.username as username,a.phone_num as phone_num " + " from
     * es_adminuser a where a.org_id in(select o.party_id from es_organization o
     * where o.org_code ='" + dealer_code + "')";
     * 
     * // org 组织 } else if (!StringUtil.isEmpty(dealer_type) &&
     * "org".equals(dealer_type)) { sql_user = "select a.realname as
     * realname,a.username as username,a.phone_num as phone_num " + " from
     * es_adminuser a where a.org_id='" + dealer_code + "'";
     * 
     * // person 人员 } else if (!StringUtil.isEmpty(dealer_type) &&
     * "person".equals(dealer_type)) { sql_user = "select a.realname as
     * realname,a.username as username,a.phone_num as phone_num " + " from
     * es_adminuser a where a.userid='" + dealer_code + "'"; } }
     * 
     * logger.info("自定义人员查询SQL:" + sql_user);
     * 
     * Page page = this.baseDaoSupport.queryForPageByMap(sql_user, pageNo, pageSize,
     * AdminUser.class, null); return page; }
     * @throws Exception 
     **/

    @SuppressWarnings("unchecked")
    public Page newgetUserListQueery(int pageNo, int pageSize, String userparams, String allotType,
            String lockOrderIdStr, String allot_county_id, String county_code) throws Exception {

        String sql_user = "";
        String order_from = "";
        String[] str1 = lockOrderIdStr.split(",");
        String dealLevel = "";
        String dealLevelSql="select deal_level from es_work_custom_node_ins where order_id='"+str1[0]+"' and is_curr_step=1 and source_from='"+ManagerUtils.getSourceFrom()+"'";
        dealLevel = this.baseDaoSupport.queryForString(dealLevelSql);
        OrderTreeBusiRequest order_tree = CommonDataFactory.getInstance().getOrderTree(str1[0]);
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        if (order_tree == null) {
            String order_from_sql = " select source_system_type from es_order_intent where source_from ='"
                    + ManagerUtils.getSourceFrom() + "' and order_id='" + str1[0] + "' ";
            order_from = this.baseDaoSupport.queryForString(order_from_sql);
        } else {
            order_from = order_tree.getOrderExtBusiRequest().getOrder_from();
        }
        if (!StringUtil.isEmpty(allotType) && "query".equals(allotType)) {
            sql_user = "select t.userid,t.username,t.realname,t.phone_num from es_adminuser t where 1=1 ";
            if (!StringUtils.isEmpty(userparams)) {
                sql_user += " and  t.userid like '%" + userparams + "%'";
            }
        }
        if (!StringUtil.isEmpty(allotType) && "return".equals(allotType)) {
            String[] order_ids = lockOrderIdStr.split(",");
            /*
             * String is_yxd_sql =
             * " select decode((select order_id from es_order_intent where source_from = '"
             * +ManagerUtils.getSourceFrom()+"' and order_id='"+order_ids[0]
             * +"'),null,'N','Y') as is_yxd from dual "; String is_yxd =
             * this.baseDaoSupport.queryForString(is_yxd_sql);
             */
            String gonghao = "";
            /*
             * 0 if("Y".equals(is_yxd)){ gonghao =
             * cacheUtil.getConfigInfo("PRO_USER_ID_"+order_from); }else
             * if("N".equals(is_yxd)){ gonghao =
             * cacheUtil.getConfigInfo("PRO_USER_ID+"+order_from); }
             */
            // 有配置 自定义流程省中台默认工号 SZT001
            logger.info("order_from:" + order_from);
            gonghao = cacheUtil.getConfigInfo("PRO_USER_ID_" + order_from);

            sql_user = "select t.userid,t.username,t.realname,t.phone_num from es_adminuser t where 1=1 and t.userid='"
                    + gonghao + "'";

        }
        if (!StringUtil.isEmpty(allotType) && "allot".equals(allotType)) {
            /**
             * sql_user = " select distinct a.busicty_id as
             * busicityauth,e.userid,e.username,e.realname,e.phone_num " + " from
             * es_role_data a, " + " es_role_auth b, " + " es_role c, " + " es_user_role d,
             * " + " es_adminuser e " + " where a.id = b.authid " + " and a.source_from =
             * b.source_from " + " and b.roleid = c.roleid " + " and b.source_from =
             * c.source_from " + " and c.roleid = d.roleid " + " and c.source_from =
             * d.source_from " + " and d.userid = e.userid " + " and d.source_from =
             * e.source_from " + " and a.flow_node is not null ";
             **/
            sql_user = "select distinct a.busicty_id as busicityauth,e.userid,e.username,e.realname,e.phone_num,e.org_id "
                    + "  from es_adminuser e left join es_user_role d on e.userid=d.userid and e.source_from=d.source_from "
                    + "  left join es_role c on c.roleid=d.roleid and c.source_from= d.source_from "
                    + "  left join es_role_auth b on b.roleid=c.roleid and b.source_from = c.source_from "
                    + "  left join es_role_data a on a.id= b.authid and a.source_from = b.source_from "
                    + "  where a.source_from = 'ECS'  and  a.flow_node is not null and e.state='1' ";

            /*
             * if(str.length>0){ sql_user += " and ( "; for(int j=0;j<str.length;j++){
             * 
             * String order_id = str[0]; OrderTreeBusiRequest orderTree =
             * CommonDataFactory.getInstance().getOrderTree(order_id);//
             * CommonDataFactory.getInstance().getOrderTree(order_id); OrderExtBusiRequest
             * orderExt = orderTree.getOrderExtBusiRequest(); String trace_id =
             * orderExt.getFlow_trace_id(); sql_user
             * +=" instr(a.flow_node,'"+trace_id+"') > 0 or "; } sql_user =
             * sql_user.substring(0,sql_user.lastIndexOf("or")); sql_user += " ) ";
             * 
             * 
             * }
             */
            
             if (!StringUtils.isEmpty(userparams)) {
                 sql_user += " and (e.username like '%" + userparams + "%' or e.realname like '%" + userparams
                         + "%' or e.phone_num like '%" + userparams + "%' )";
             }
             
             if (org.apache.commons.lang.StringUtils.isNotBlank(allot_county_id)) {
                 sql_user += " and instr(a.busicty_id,'" + allot_county_id + "') > 0 ";
             }
             
             if (!StringUtil.isEmpty(county_code)) {
                 sql_user += " and instr(a.busicty_id,(select distinct t.other_field_value from es_dc_public_dict_relation t where stype ='county' and t.field_value='"
                         + county_code + "'))> 0";
             }
             
             String sql = "select is_work_custom from es_order_intent where order_id='"+str1[0]+"' and source_from='"+ManagerUtils.getSourceFrom()+"'";
             String is_work_custom = this.baseDaoSupport.queryForString(sql);
             if(!"1".equals(is_work_custom)){
                 String flag2 = cacheUtil.getConfigInfo("ORDER_ALLOT_SWICTH");
                 
                 if ("1".equals(flag2)) {
                     sql_user = getQueryUserSql(order_from,str1[0],allot_county_id,county_code);
                     
                     if (!StringUtils.isEmpty(userparams)) {
                         sql_user += " and (u.username like '%" + userparams + "%' or u.realname like '%" + userparams
                                 + "%' or u.phone_num like '%" + userparams + "%' )";
                     }
                 }else{
                    AdminUser user = ManagerUtils.getAdminUser();
                     String lan_id = user.getOrg_id();
                     String user_id = user.getUserid();
//                   String receiveUserAuth = user.getReceiveUserAuth();// 订单领取工号权限
                     String flag = cacheUtil.getConfigInfo("ORDER_ALLOT_FLAG");
                     
                     String org_type_sql = " select decode(t.org_type_id,1,'pro','county') as org_type_id from es_organization t,es_adminuser a where t.party_id=a.org_id and t.status_cd='00A' and t.source_from = '"
                             + ManagerUtils.getSourceFrom() + "' and a.userid='" + user_id + "' ";
                     
                     logger.info("org_type_sql：" + org_type_sql);
                     String org_type_id = this.baseDaoSupport.queryForString(org_type_sql, null);
                     
                     String lock_user_sql = " select t.ord_receive_user from es_role_data t,es_user_role a,es_role_auth c where t.id=c.authid and c.roleid=a.roleid and t.source_from = '"
                             + ManagerUtils.getSourceFrom() + "' and a.userid='" + user_id + "' ";
                     logger.info("lock_user_sql:" + lock_user_sql);
                     String lock_user = this.baseDaoSupport.queryForString(lock_user_sql);
                     
                     if (StringUtils.isEmpty(lock_user) && "county".equals(org_type_id) && !StringUtils.isEmpty(org_type_id)) {
                         sql_user += " and a.ord_receive_user is not null and e.userid in (select userid from es_organization t,es_adminuser a where t.party_id=a.org_id and t.status_cd='00A' and t.source_from = '"
                                 + ManagerUtils.getSourceFrom() + "' and a.org_id='" + lan_id + "' ) ";
                         
                     } else if (StringUtils.isEmpty(lock_user) && "pro".equals(org_type_id) && !StringUtils.isEmpty(org_type_id)) {
                         sql_user += " and a.ord_receive_user is not null  and e.userid in (select userid from es_organization t,es_adminuser a where t.party_id=a.org_id and t.status_cd='00A' and t.source_from = '"
                                 + ManagerUtils.getSourceFrom() + "' and a.org_id='" + lan_id + "' ) ";
                         
                     } else if (!StringUtils.isEmpty(lock_user) && "county".equals(org_type_id) && !StringUtils.isEmpty(org_type_id)) {
                         // String[] lockusers = lock_user.split(",");
                         // String sql1 = " and (";
                         // for (int i = 0; i < lockusers.length; i++) {
                         // sql1 += " a.ord_receive_user like '%"+lockusers[i]+",%' or ";
                         // }
                         // sql1 = sql1.substring(0, sql1.lastIndexOf("or"));
                         // sql1 += " ) ";
                         // sql_user += " and a.ord_receive_user is not null "+sql1+" and e.userid in
                         // (select userid from es_organization t,es_adminuser a where
                         // t.party_id=a.org_id and t.status_cd='00A' and t.source_from =
                         // '"+ManagerUtils.getSourceFrom()+"' and a.org_id='"+lan_id+"' ) ";
                         
                         logger.info("领取工号:lock_user" + lock_user + "组织类型org_type_id:" + org_type_id);
                         
                         sql_user += " and e.userid in (select userid from es_organization t,es_adminuser a where t.party_id=a.org_id and t.status_cd='00A' and t.source_from = '"
                                 + ManagerUtils.getSourceFrom() + "') ";
                         sql_user += " and e.state ='1' and a.ord_receive_user is not null ";
                         // 订单对应所有的领取工号
                         List<Map> user_list = this.baseDaoSupport.queryForList(
                                 "select pkey value,pname value_desc from es_dc_public_ext where stype in ('DIC_COUNTY_USERS_" + order_from + "','DIC_CITY_USERS_" + order_from + "')");
                         if (user_list.size() > 0) {
                             String users = " and ( ";
                             for (Map map : user_list) {
                                 if (!users.equals(" and ( ")) {
                                     users += "or ";
                                 }
                                 if (!StringUtils.isEmpty(map.get("value_desc") + "")) {
                                     users += "  replace (' '||a.ord_receive_user,',',', ') like '% " + map.get("value_desc") + ", %' ";
                                 }
                             }
                             if (!users.equals(" and ( ")) {
                                 users += " )";
                                 sql_user += users;
                             }
                         }
                         
                     } else if (!StringUtils.isEmpty(lock_user) && "pro".equals(org_type_id) && !StringUtils.isEmpty(org_type_id)) {
                         sql_user += " and e.userid in (select pname from es_dc_public_ext where stype in ('DIC_COUNTY_USERS_" + order_from + "','DIC_CITY_USERS_" + order_from + "') ) ";
                     }
                 }
             }else{
                String flag = cacheUtil.getConfigInfo("IS_WORK_CUSTOM");
                if("1".equals(flag)){
                    sql_user += "  and ( e.userid in ( select wcd.dealer_code as userid from es_work_custom_dealer wcd where wcd.cfg_id in (select cfg_id from es_work_custom_workflow_ins where order_id = '"+str1[0]+"') and wcd.dealer_type = 'person' ) or e.org_id in ( select wcd.dealer_code as org_id from es_work_custom_dealer wcd where wcd.cfg_id in (select cfg_id from es_work_custom_workflow_ins where order_id = '"+str1[0]+"') and wcd.dealer_type = 'org' ) or e.userid in ( select userid from es_user_team_rel utr where utr.team_id in (select wcd.dealer_code as team_id from es_work_custom_dealer wcd where wcd.dealer_type = 'team' and wcd.cfg_id in (select cfg_id from es_work_custom_workflow_ins where order_id = '"+str1[0]+"') ) ) ) ";
                }
            }
        }
             
        if(!StringUtil.isEmpty(allotType) && "yxd".equals(allotType)){
            
            String city_code = this.getOrderCityOrder(str1[0]);
            sql_user="(select distinct a.busicty_id as busicityauth,e.userid,e.username,e.realname,e.phone_num,ewc.deal_level  permission_level,ewc.region_name city " + 
            		"from es_role_data a  " + 
            		"inner join es_role_auth b on a.id = b.authid and a.flow_node is not null " + 
            		"inner join es_role c on b.roleid = c.roleid and b.source_from = c.source_from " + 
            		"inner join es_user_role d on c.roleid = d.roleid and c.source_from = d.source_from " + 
            		"inner join es_adminuser e on d.userid = e.userid and d.source_from = e.source_from " + 
            		"inner join es_user_team_rel eut on eut.userid=e.userid " + 
            		"inner join es_work_custom_dealer ewc on eut.team_id=ewc.dealer_code " + 
            		"inner join es_work_custom_node_ins esw on esw.cfg_id=ewc.cfg_id and ewc.node_index = esw.node_index " + 
            		"where esw.is_curr_step=1 and ewc.dealer_type='team'  and esw.order_id='"+str1[0]+"' "+
                    " and e.userId<> ";
            String sql_tache="select distinct ext.flow_trace_id from es_order_ext ext where ext.order_id='"+str1[0]+"' ";
            String tach_code=this.baseDaoSupport.queryForString(sql_tache);
            String userId="";
            OrderTreeBusiRequest ordertree=CommonDataFactory.getInstance().getOrderTree(str1[0]);
            List<OrderLockBusiRequest> orderLockBusiRequests =ordertree.getOrderLockBusiRequests();
            for(OrderLockBusiRequest orderLockBusiRequest:orderLockBusiRequests) {
            	if(orderLockBusiRequest.getTache_code().equals(tach_code)) {
            		userId=orderLockBusiRequest.getLock_user_id();
            	}
            }
            if(!StringUtil.isEmpty(userId)) {
            	sql_user=sql_user+"'"+userId+"'";
            }else {
            	sql_user=sql_user+" (select lo.lock_user_id from es_order_lock lo where lo.order_id='"+str1[0]+"'  " + 
                		" and lo.tache_code=(select ext.flow_trace_id from es_order_ext ext where ext.order_id='"+str1[0]+"'))";
            }
            if (!StringUtils.isEmpty(userparams)) {
                 sql_user += " and (e.realname like '%"+userparams+"%' or e.userid like '%"+userparams+"%' ) ";
             }
            //当前层级是市分或县分
            if("region".equals(dealLevel) ) {
                sql_user +=" and ewc.region_id = '"+city_code+"'";
            }else if ("county".equals(dealLevel)) {
                sql_user +=" and ewc.region_id = '"+city_code+"' and ewc.deal_level='county'";
            }else{
                sql_user +=" and ewc.region_id = '"+city_code+"' ";
            }
            if (!StringUtils.isEmpty(allot_county_id)) {
                sql_user += " and instr(a.busicty_id,'" + allot_county_id + "') > 0 ";
            }
            sql_user+=") ";
            sql_user+="union all  " + 
            		"(select distinct ewc.county_name busicityauth,ewc.dealer_code userid,ewc.dealer_code username,ewc.dealer_name realname, " + 
            		"adm.phone_num,ewc.deal_level  permission_level, " + 
            		"       ewc.region_name city " + 
            		"  from es_work_custom_dealer ewc " + 
            		"  left join es_adminuser adm on ewc.dealer_code=adm.userid " + 
            		"  left join es_work_custom_node_ins esw " + 
            		"    on esw.cfg_id = ewc.cfg_id " + 
            		"   and ewc.node_index = esw.node_index " + 
            		" where esw.is_curr_step = 1  and ewc.dealer_type='person' and esw.order_id='"+str1[0]+"' "+
                    " and adm.userId<> ";
            if(!StringUtil.isEmpty(userId)) {
            	sql_user=sql_user+"'"+userId+"'";
            }else {
            	sql_user=sql_user+" (select lo.lock_user_id from es_order_lock lo where lo.order_id='"+str1[0]+"'  " + 
                		" and lo.tache_code=(select ext.flow_trace_id from es_order_ext ext where ext.order_id='"+str1[0]+"'))";
            }
            if (!StringUtils.isEmpty(userparams)) {
                 sql_user += " and (e.realname like '%"+userparams+"%' or e.userid like '%"+userparams+"%' ) ";
             }
            if (!StringUtils.isEmpty(userparams)) {
            	sql_user += " and (adm.realname like '%"+userparams+"%' or adm.userid like '%"+userparams+"%' ) ";
            }
            //当前层级是市分或县分
            if("region".equals(dealLevel) ) {
            	sql_user +=" and ewc.region_id = '"+city_code+"'";
            }else if ("county".equals(dealLevel)) {
            	sql_user +=" and ewc.region_id = '"+city_code+"' and ewc.deal_level='county'";
            }else{
            	sql_user +=" and ewc.region_id = '"+city_code+"' ";
            }
            if (!StringUtils.isEmpty(allot_county_id)) {
            	StringBuffer sql_county_name=new StringBuffer();
            	sql_county_name.append("select t.field_value_desc from es_dc_public_dict_relation  t  where  t.stype='county' and t.other_field_value ='").append(allot_county_id).append("' ");
            	String county_name=baseDaoSupport.queryForString(sql_county_name.toString());
                sql_user += " and instr(ewc.county_name,'" + county_name + "') > 0 ";
            }
            sql_user+=") ";
        }
        if(!StringUtil.isEmpty(allotType) && "yxdreturn".equals(allotType)){
            String city_code = this.getOrderCityOrder(str1[0]);
            //判断是否是政企的
            if ("100103".equals(order_from)) {
                sql_user="select a.deal_level permission_level,a.region_name city,a.county_name busicityAuth,a.dealer_type col5,a.dealer_name realname,a.dealer_code username,a.dealer_code userid,a.dealer_name team_name from es_work_custom_dealer a left join es_work_custom_node_ins esw on esw.cfg_id=a.cfg_id and a.node_index = esw.node_index where esw.order_id='"+str1[0]+"' and esw.is_curr_step=1";
                if("county".equals(dealLevel)){
                    sql_user +=" and a.region_id = '"+city_code+"' and a.deal_level='region'";
                }else{
                    sql_user +=" and 1=2";
                }
            }else{
                String gonghao = "";
                // 有配置 自定义流程省中台默认工号 SZT001
                gonghao = cacheUtil.getConfigInfo("PRO_USER_ID_" + order_from);
                sql_user = "select t.userid,t.username,t.realname,t.lan_name city,'' permission_level,t.city busicityAuth,t.realname team_name,t.dep_name col5 from es_adminuser t where 1=1 and t.userid='"
                        + gonghao + "'";
                //当前层级是县分
                
                if ("county".equals(dealLevel)) {
                    int count = this.baseDaoSupport.queryForInt("select count(*) from es_work_custom_dealer a left join es_work_custom_node_ins esw on esw.cfg_id = a.cfg_id and a.node_index = esw.node_index where esw.order_id='"+str1[0]+"' and esw.is_curr_step=1 and a.region_id = '"+city_code+"' and a.deal_level='region'");
                    if (count>0) {
                        sql_user="select a.deal_level permission_level,a.region_name city,a.county_name busicityAuth,a.dealer_type col5,a.dealer_name realname,a.dealer_code username,a.dealer_code userid,a.dealer_name team_name from es_work_custom_dealer a left join es_work_custom_node_ins esw on esw.cfg_id=a.cfg_id and a.node_index = esw.node_index where esw.order_id='"+str1[0]+"' and esw.is_curr_step=1";
                        sql_user +=" and a.region_id = '"+city_code+"' and a.deal_level='region'";
                    }
                }
            }
        }
        logger.info("订单分配人员查询：" + sql_user);
        Page page = this.baseDaoSupport.queryForPageByMap(sql_user, pageNo, pageSize, AdminUser.class, null);
        List dc_list = getDcSqlByDcName("DC_ORDER_BUSICTY");
        if (dc_list.size() <= 0) {
            return page;
        }
        if (page.getTotalCount() > 0) {
            List<AdminUser> list = page.getResult();
            StringBuilder builder = new StringBuilder();
            builder.append(" SELECT u.userid,c.busicty_id busicityAuth FROM es_adminuser u, es_user_role a, es_role_auth b, es_role_data c ");
            builder.append(" WHERE u.source_from = 'ECS' and U.State = '1' and u.userid = a.userid and a.roleid = b.roleid and b.authid = c.id ");
            builder.append(" and a.source_from = 'ECS' and c.busicty_id is not null and u.userid in ( ");
//          Map<String, String> map = 
            for (int i = 0; i < list.size(); i++) {
                AdminUser adminUser = list.get(i);
                builder.append(" '"+adminUser.getUserid()+"',");
            }
            if (list.size()>0) {
                builder.deleteCharAt(builder.length()-1);
            }
            builder.append(" ) ");
            List<Map> countyList = this.baseDaoSupport.queryForListByMap(builder.toString(),null);
            
            
            for (int i = 0; i < list.size(); i++) {
                 String sql = "select is_work_custom from es_order_intent where order_id='"+str1[0]+"' and source_from='"+ManagerUtils.getSourceFrom()+"' union all select is_work_custom from es_order_ext where order_id='"+str1[0]+"' and source_from='"+ManagerUtils.getSourceFrom()+"'";
                 String is_work_custom = this.baseDaoSupport.queryForString(sql);
                 String new_busicity = "";
                AdminUser adminUser = list.get(i);
                adminUser.setCol5("person");
                
                String busic="";
                if("1".equals(is_work_custom)){
                    busic=adminUser.getBusicityAuth();
                }else{
                    for (Map<String, String> m : countyList) {
                        if (adminUser.getUserid().equals(m.get("userid"))) {
                            busic+=m.get("BUSICITYAUTH");
                        }
                    }
                }
                
                adminUser.setBusicityAuth(busic);
                adminUser.setCol2(dealLevel);
                if (StringUtils.isEmpty(adminUser.getBusicityAuth())) {
                    continue;
                }
                String[] str = adminUser.getBusicityAuth().split(",");
                for (int j = 0; j < str.length; j++) {
                    for (int j2 = 0; j2 < dc_list.size(); j2++) {
                        Map map = (Map) dc_list.get(j2);
                        if (str[j].equals(Const.getStrValue(map, "pkey"))) {
                            str[j] = Const.getStrValue(map, "pname");
                            break;
                        }
                    }
                    new_busicity += str[j] +",";
                }
                if (!StringUtils.isEmpty(new_busicity)) {
                    list.get(i).setBusicityAuth(new_busicity);
                }
            }
           
        }
        return page;
    }

    public List getLockOrderIds(OrderQueryParams params) {
        String sql = "select t.order_id from es_order_ext t  left join es_order s on s.order_id=t.order_id left join es_order_lock l on t.order_id = l.order_id and t.flow_trace_id = l.tache_code  where t.source_from = '"
                + ManagerUtils.getSourceFrom() + "' and t.visible_status='" + EcsOrderConsts.VISIBLE_STATUS_0
                + "' and  l.lock_status='" + EcsOrderConsts.LOCK_STATUS + "' ";
        List list = new ArrayList();
        // if(Consts.CURR_FOUNDER1 !=ManagerUtils.getFounder()){
        // sql += " and t.lock_user_id = ?";
        // list.add(ManagerUtils.getUserId());
        // }
        if (params != null) {
            if (!StringUtils.isEmpty(params.getLock_user_id())) {
                sql += " and l.lock_user_id =? ";
                list.add(params.getLock_user_id());
            }
            if (!StringUtils.isEmpty(params.getOrder_id())) {
                sql += " and t.order_id =? ";
                list.add(params.getOrder_id());
            }
            if (!StringUtils.isEmpty(params.getOut_tid())) {
                sql += " and t.out_tid =? ";
                list.add(params.getOut_tid());
            }
            if (!StringUtils.isEmpty(params.getOrder_from())) {
                sql += " and t.order_from in('" + params.getOrder_from().replace(",", "','") + "') ";
            }
            if (!StringUtils.isEmpty(params.getOrder_city_code())) {
                sql += " and t.order_city_code in('" + params.getOrder_city_code().replace(",", "','") + "') ";
            }
            if (!StringUtils.isEmpty(params.getOrder_type())) {
                sql += " and s.order_type in('" + params.getOrder_type().replace(",", "','") + "') ";
            }
            if (!StringUtils.isEmpty(params.getFlow_id())) {
                sql += " and t.flow_trace_id in('" + params.getFlow_id().replace(",", "','") + "') ";

            }
            if (!StringUtils.isEmpty(params.getPhone_num())) {
                sql += " and exists(select 1 from es_order_items_ext o where t.order_id=o.order_id and t.source_from=o.source_from and o.phone_num = '"
                        + params.getPhone_num() + "') ";
                // list.add(params.getPhone_num());
            }
            if (!StringUtil.isEmpty(params.getCreate_start())) {
                sql += " and t.tid_time>=to_date('" + params.getCreate_start() + "','yyyy-mm-dd hh24:mi:ss')";

                // list.add(params.getCreate_start());
            }
            // 创建时间结束
            if (!StringUtil.isEmpty(params.getCreate_end())) {
                sql += " and t.tid_time<=to_date('" + params.getCreate_end() + "','yyyy-mm-dd hh24:mi:ss')";

                // list.add(params.getCreate_end());
            }
        }
        List resultList = new ArrayList();
        resultList = this.baseDaoSupport.queryForList(sql, list.toArray());
        return resultList;
    }

    public List getLockOrderIdsByOuttids(OrderQueryParams params) {
        String sql = "select order_id from es_order_ext t left join es_order_lock l on t.order_id = l.order_id and t.flow_trace_id = l.tache_code where (t.visible_status='"
                + EcsOrderConsts.VISIBLE_STATUS_0 + "' or (t.visible_status='" + EcsOrderConsts.VISIBLE_STATUS_1
                + "' and t.abnormal_type in('" + EcsOrderConsts.ORDER_ABNORMAL_TYPE_2 + "','"
                + EcsOrderConsts.ORDER_ABNORMAL_TYPE_3 + "') )) and l.lock_status='" + EcsOrderConsts.LOCK_STATUS + "'";
        List list = new ArrayList();
        if (params != null && !StringUtils.isEmpty(params.getOut_tid())) {
            sql += "and out_tid in('" + params.getOut_tid().replace(",", "','") + "')";
        } else {
            return new ArrayList();
        }
        List resultList = new ArrayList();
        resultList = this.baseDaoSupport.queryForList(sql, list.toArray());
        return resultList;
    }

    public String unLock(String order_id) {
        try {
            OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
            OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();

            /*
             * String lock_user_id = orderExtBusiRequest.getLock_user_id(); String
             * lock_user_state = orderExtBusiRequest.getLock_status();
             */

            AdminUser user = ManagerUtils.getAdminUser();
            List<OrderLockBusiRequest> orderLockBusiRequestsList = orderTree.getOrderLockBusiRequests();
            boolean isMyLock = false;
            int isLock = 0;
            String trace_id = orderExtBusiRequest.getFlow_trace_id();
            for (int i = 0; i < orderLockBusiRequestsList.size(); i++) {
                OrderLockBusiRequest orderLockBusiRequest = orderLockBusiRequestsList.get(i);
                if (trace_id.equals(orderLockBusiRequest.getTache_code())
                        && "1".equals(orderLockBusiRequest.getLock_status())
                        && user.getUserid().equals(orderLockBusiRequest.getLock_user_id())) {
                    isMyLock = true;
                }
                if (trace_id.equals(orderLockBusiRequest.getTache_code())
                        && "1".equals(orderLockBusiRequest.getLock_status())) {
                    isLock++;
                }
            }

            if (isLock == 0) {
                return "{result:1,msg:'该订单未被锁定,无需解锁'}";
            }

            String curr_user_id = user.getUserid();
            if (isMyLock == true) {
                for (int i = 0; i < orderLockBusiRequestsList.size(); i++) {
                    OrderLockBusiRequest orderLockBusiRequest = orderLockBusiRequestsList.get(i);
                    if (trace_id.equals(orderLockBusiRequest.getTache_code())
                            && "1".equals(orderLockBusiRequest.getLock_status())
                            && user.getUserid().equals(orderLockBusiRequest.getLock_user_id())) {
                        orderLockBusiRequest.setLock_user_id(ConstsCore.NULL_VAL);
                        orderLockBusiRequest.setLock_user_name(ConstsCore.NULL_VAL);
                        orderLockBusiRequest.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
                        orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
                        orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
                        // 解锁订单释放工号池和锁单结束时间
                        orderLockBusiRequest.setPool_id(ConstsCore.NULL_VAL);
                        orderLockBusiRequest.setLock_end_time(ConstsCore.NULL_VAL);
                        orderLockBusiRequest.store();
                    }
                }

                /*
                 * orderExtBusiRequest.setOrder_id(order_id);
                 * orderExtBusiRequest.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
                 * orderExtBusiRequest.setLock_user_id(ConstsCore.NULL_VAL);
                 * orderExtBusiRequest.setLock_user_name(ConstsCore.NULL_VAL);
                 * orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
                 * orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
                 * orderExtBusiRequest.store();
                 */
                return "{status:0,msg:'解锁成功',order_id:'" + order_id + "'}";
            } else {
                return "{status:1,msg:'只能解锁自己的订单',order_id:'" + order_id + "'}";
            }
        } catch (Exception ex) {
            return "{status:1,msg:'解锁失败'}";
        }
    }

    public String getWarning(String order_ids) {
        String json = "";
        String is_out_time = "";
        try {
            if (!StringUtil.isEmpty(order_ids)) {
                String[] order_id_array = order_ids.split(",");//
                String order_ids_new = "";
                for (int i = 0; i < order_id_array.length; i++) {
                    if (i == 0) {
                        order_ids_new += "'" + order_id_array[i] + "'";
                    } else {
                        order_ids_new += ",'" + order_id_array[i] + "'";
                    }
                }

                StringBuilder sql = new StringBuilder(
                        "select e.order_id,e.last_deal_time,w.warning_time_1 ,w.warning_time_2 ,w.warning_time_3 ,w.warning_time_4  from ");
                sql.append("   (select a.order_id,a.order_city_code,a.order_from ,a.flow_trace_id, a.last_deal_time  ");
                sql.append(
                        "     ,(select distinct ai.goods_type from es_order_items_ext  ai where ai.order_id=a.order_id)  goods_type ");
                sql.append("       from ES_ORDER_EXT a  where a.order_id in (");
                sql.append(order_ids_new);
                sql.append("       ))  e,  ");
                sql.append("       es_order_warning w  ");
                sql.append("       where e.goods_type=w.product_type and e.order_city_code=w.order_origin  ");
                sql.append("             and e.flow_trace_id = w.flow_id and e.order_from=w.order_from  ");

                List<Map> resultList = new ArrayList<Map>();
                resultList = this.baseDaoSupport.queryForList(sql.toString());
                List<OrderWarningResult> reList = new ArrayList<OrderWarningResult>();

                for (Map map : resultList) {
                    int warning_time_1 = Const.getIntValue(map, "warning_time_1");
                    int warning_time_2 = Const.getIntValue(map, "warning_time_2");
                    int warning_time_3 = Const.getIntValue(map, "warning_time_3");
                    int warning_time_4 = Const.getIntValue(map, "warning_time_4");

                    String star_time = Const.getStrValue(map, "last_deal_time");// 参考日期
                    String order_id = Const.getStrValue(map, "order_id");//
                    if (!StringUtil.isEmpty(star_time)) {
                        Date star_time_date = DateFormatUtils.parseStrToDate(star_time,
                                CrmConstants.DATE_TIME_FORMAT23);
                        Long run_time = DateFormatUtils.cal4Dates(star_time_date, new Date(), DateFormatUtils.MINUTE);// 开始时间
                        Long out_time = null;// 超时时间，相对于黄色预警开始时间
                        OrderWarningResult owr = new OrderWarningResult();
                        owr.setOrder_id(order_id);
                        owr.setRun_time(run_time + "");
                        if (warning_time_1 <= run_time && warning_time_2 > run_time) {// 黄色
                            owr.setWarning_colo(EcsOrderConsts.WARNING_YEL);
                        } else if (warning_time_2 <= run_time && warning_time_3 > run_time) {// 橙色
                            owr.setWarning_colo(EcsOrderConsts.WARNING_ORG);
                        } else if (warning_time_3 <= run_time && warning_time_4 > run_time) {// 红色
                            owr.setWarning_colo(EcsOrderConsts.WARNING_RED);
                        }
                        out_time = run_time - warning_time_1;
                        owr.setOut_time(out_time + "");
                        if (owr.getWarning_colo() != null) {// 需要预警的才加到列表
                            reList.add(owr);
                        }

                    }

                }
                json = JSONArray.toJSONString(reList);
                json = "{result:0,message:'成功',list:" + json + "}";
            }

        } catch (Exception ex) {
            json = "{result:1,message:'失败'}";

        }
        return json;
    }

    public List getExceptionLogList(String order_id) {

        String exception_sql = "select t.order_id,t.flow_id,t.flow_trace_id,t.comments,t.create_time,t.op_id,c.realname op_name,t.handler_type, n.pname trace_name,"
                + "(select c.pname from es_dc_public_ext c where c.stype='DIC_ORDER_EXCEPTION_TYPE' and c.pkey=t.exception_id and c.source_from=t.source_from) exception_type_name  "
                + "from es_order_handle_logs t left join es_dc_public_ext n on (t.flow_trace_id = n.pkey and n.stype = 'DIC_ORDER_NODE')  left join es_adminuser c on c.userid=t.op_id "
                + "where t.handler_type = '" + Const.ORDER_HANDLER_TYPE_EXCEPTION + "' and t.source_from ='"
                + ManagerUtils.getSourceFrom() + "' and t.source_from = n.source_from and t.order_id =? ";

        String suspend_sql = "select t.order_id,t.flow_id,t.flow_trace_id,t.comments,t.create_time,t.op_id,c.realname op_name,t.handler_type exception_type_name, n.pname trace_name,t.type_code exception_type_name "
                + "from es_order_handle_logs t left join es_dc_public_ext n on (t.flow_trace_id = n.pkey and n.stype = 'DIC_ORDER_NODE') left join es_adminuser c on c.userid=t.op_id "
                + "where t.handler_type = '" + Const.ORDER_HANDLER_TYPE_SUSPEND + "' and t.source_from ='"
                + ManagerUtils.getSourceFrom() + "' and t.source_from = n.source_from and t.order_id =? ";

        String sql = "select order_id,flow_id,flow_trace_id,comments,create_time,op_id,op_name,exception_type_name,handler_type,trace_name from ("
                + exception_sql + " union all " + suspend_sql + ") order by create_time asc";
        List logs = this.baseDaoSupport.queryForList(sql, order_id, order_id);

        return logs;
    }

    public List getSdsLogList(String order_id) {
        List list = new ArrayList();
        String out_tid = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOut_tid();
        StringBuilder sql = new StringBuilder("select");
        sql.append(" t.WL_COMP_NAME,");
        sql.append(" t.STATUS_NAME,");
        sql.append(" t.CREATE_TIME,");
        sql.append(" t.CREATE_USER");
        sql.append(" from es_sds_status_log t");
        sql.append(" where t.ORIG_ORDER_ID='" + out_tid + "'");
        list = this.baseDaoSupport.queryForList(sql.toString());
        return list;
    }

    // OrderTreeBusiRequest ordertree =
    // CommonDataFactory.getInstance().getOrderTree(order_id);

    public Page getOrderHisPage(String order_id, int pageNo, int pageSize) {
        OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
        StringBuilder sql = new StringBuilder("select");
        String ship_name = ordertree.getOrderBusiRequest().getShip_name();
        String ship_addr = ordertree.getOrderBusiRequest().getShip_addr();
        String ship_mobile = ordertree.getOrderBusiRequest().getShip_mobile();
        String cert_card_num = ordertree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0)
                .getOrderItemProdExtBusiRequest().getCert_card_num();
        
        sql.append(" distinct t.ship_name, ");
        sql.append(" t.ship_addr, ");
        sql.append(" t.ship_tel, ");
        sql.append(" t.order_id, ");
        sql.append(" b.tid_time, ");
        sql.append(" t.order_type, ");
        sql.append(" t.goods_num, ");
        sql.append(" t.status ");
        sql.append(" from es_order t ");
        sql.append(" left join es_order_items_prod_ext a  on t.order_id=a.order_id ");
        sql.append(" inner join es_order_ext  b on t.order_id=b.order_id ");
        sql.append(" where t.source_from = '" + ManagerUtils.getSourceFrom()
                + "' and ((t.ship_name = ? and t.ship_addr = ?) or t.ship_mobile = ? or a.cert_card_num = ?)");
        CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String order_his_time = cacheUtil.getConfigInfo(Const.ORDER_HIS_TIME);
        if (!StringUtils.isEmpty(order_his_time)) {
            sql.append(" and b.tid_time >=add_months(trunc(sysdate),?)  ");
        }
        sql.append(" order by b.tid_time desc ");
        List pList = new ArrayList();
        pList.add(ship_name);
        pList.add(ship_addr);
        pList.add(ship_mobile);
        pList.add(cert_card_num);
        pList.add(order_his_time);
        Map pMap = new HashMap();
        Page page = this.baseDaoSupport.queryForPage(sql.toString(), pageNo, pageSize, pList.toArray());
        List list = page.getResult();
        for (int i = 0; i < list.size(); i++) {
            Map map = (Map) list.get(i);
            String new_status = getCacheName("DIC_ORDER_STATUS", Const.getStrValue(map, "status"));
            map.put("new_status", new_status);
        }
        return page;
    }

    // select
    // t.element_name,t.old_value,t.new_value,t.oper_id,t.update_time,t.log_id from
    // es_order_update_log t
    public List getOrderChangeList(String order_id) {
        List list = new ArrayList();
        StringBuilder sql = new StringBuilder("select");
        CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        sql.append(" t.element_name, ");
        sql.append(" t.old_value, ");
        sql.append(" t.new_value, ");
        sql.append(" t.oper_id, ");
        sql.append(" t.update_time, ");
        sql.append(" t.log_id ");
        sql.append(" from es_order_update_log t ");
        sql.append(
                " where t.source_from = '" + ManagerUtils.getSourceFrom() + "' and t.order_id = '" + order_id + "' ");
        list = this.baseDaoSupport.queryForList(sql.toString());

        return list;
    }

    // 保存修改日志
    public void saveChange(Map old_map, Map new_map, String order_id) {
        AdminUser user = ManagerUtils.getAdminUser();
        String sql_insert = "insert into es_order_update_log (log_id,order_id,element_code,element_name,old_value,new_value,oper_id,update_time,source_from) "
                + "values(?,?,?,?,?,?,?,sysdate,'" + ManagerUtils.getSourceFrom() + "')";
        String log_id = "";
        String element_name = "";
        Set<String> keys = old_map.keySet();
        for (String key : keys) {
            String old_value = (String) old_map.get(key);
            String new_value = (String) new_map.get(key);
            if (StringUtil.equals(old_value, new_value)
                    || (StringUtil.isEmpty(old_value) && StringUtil.isEmpty(new_value))) {
                continue;
            }
            element_name = getCacheName("DC_ORDER_UPDATE_COLUMN", key);
            logger.info(element_name);
            this.daoSupport.execute(sql_insert, log_id, order_id, key, element_name, old_value, new_value,
                    user.getUserid());
        }

    }

    public Map getUpdate(String order_id) {
        OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
        String sql = "select distinct(a.phone_num),t.cert_card_num from es_order_items_prod_ext t,es_order_items_ext a "
                + "where t.order_id=a.order_id and t.order_id='" + order_id + "' and t.source_from='"
                + ManagerUtils.getSourceFrom() + "'";
        List list = daoSupport.queryForListByMap(sql.toString(), null);
        String phone_num = Const.getStrValue((Map) list.get(0), "phone_num");
        String cert_card_num = Const.getStrValue((Map) list.get(0), "cert_card_num");
        Map map = new HashMap();
        map.put("phone_num", phone_num);
        map.put("cert_card_num", cert_card_num);
        return map;

    }

    public String getAttrIdByName(String field_name) {
        String sql = "select field_attr_id from es_attr_def where field_name='" + field_name + "'";

        return this.baseDaoSupport.queryForString(sql);
    }

    public String getCacheName(String code, String key) {
        String cacheName = "";
        IDictManager dictManager = SpringContextHolder.getBean("dictManager");
        DictManagerCacheProxy dc = new DictManagerCacheProxy(dictManager);
        List list = dc.loadData(code);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) list.get(i);
                String value = (String) map.get("value");
                if (value.equals(key)) {
                    cacheName = (String) map.get("value_desc");
                }
            }
        }

        return cacheName;
    }

    public BusiDealResult doSFLogisticsInfo(List<WaybillRoute> waybillRouteList) {
        StringBuffer failSerialId = new StringBuffer();// 未处理的路由流水号
        StringBuffer seccSerialId = new StringBuffer();// 处理成功的路由流水号
        BusiDealResult dealResult = new BusiDealResult();// 规则统一返回对象
        RoutePushServiceResponse iResp = new RoutePushServiceResponse();// 接口返回对象

        for (WaybillRoute waybillRoute : waybillRouteList) {
            final String orderId = waybillRoute.getOrderid();
            OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(orderId);
            if (null == orderTree) {
                iResp.setRespOrderStatus("ERR");
                iResp.setRespOrderDesc("订单号[" + orderId + "]不存在!");
                break;// 跳出循环，不再处理
            }
            // 默认返回提示信息
            iResp.setRespOrderStatus("OK");
            iResp.setRespOrderDesc("订单号[" + orderId + "]接收路由信息成功!");

            // 重复记录检查[?]

            final String orderDealType = CommonDataFactory.getInstance().getAttrFieldValue(orderId,
                    AttrConsts.ORDER_DEAL_TYPE);
            // 只有闪电送订单&&闪电送状态=02才接收
            if ("01".equals(orderDealType)) {
                // 闪电送状态
                final String shipping_quick = CommonDataFactory.getInstance().getOrderTree(orderId)
                        .getOrderExtBusiRequest().getShipping_quick();
                final String sfStatus;
                if (!EcsOrderConsts.SHIPPING_QUICK_02.equals(shipping_quick)) {
                    // 非闪电送状态,跳出循环，不再处理
                    iResp.setRespOrderStatus("ERR");
                    iResp.setRespOrderDesc("订单号[" + orderId + "]不在接收范围内!");
                    break;
                } else if (EcsOrderConsts.SF_DEAL_STATUS_80.equals(waybillRoute.getOpCode())) {
                    // 用户签收业务逻辑处理 :订单解锁用户
                    sfStatus = EcsOrderConsts.SF_ORDER_DEAL_STATUS_1;
                    unLock(orderId);
                } else if (EcsOrderConsts.SF_DEAL_STATUS_7082.equals(waybillRoute.getOpCode())) {
                    // 用户拒收业务处理:转人工生产模式
                    sfStatus = EcsOrderConsts.SF_ORDER_DEAL_STATUS_2;
                } else if (EcsOrderConsts.SF_DEAL_STATUS_Fail.equals(waybillRoute.getOpCode())) {
                    // 处理失败:转异常单
                    sfStatus = EcsOrderConsts.SF_ORDER_DEAL_STATUS_3;
                } else {
                    // 其他派送中的状态业务处理
                    sfStatus = EcsOrderConsts.SF_ORDER_DEAL_STATUS_4;
                }
                // 更新顺丰状态
                CommonDataFactory.getInstance().updateAttrFieldValue(orderId, new String[] { AttrConsts.SF_STATUS },
                        new String[] { sfStatus });
                // 记录处理单号
                failSerialId.append(waybillRoute.getId() + ",");
            } else {
                iResp.setRespOrderStatus("ERR");
                iResp.setRespOrderDesc("订单号[" + orderId + "]不在接收范围内!");
                break;// 跳出循环，不再处理
            }

        }
        iResp.setId(seccSerialId.toString());
        iResp.setId_error(failSerialId.toString());
        dealResult.setResponse(iResp);
        return dealResult;
    }

    public WYGTestResponse getWygTestReturnMsg(WYGTestRequest wygTestRequest) {
        WYGTestResponse wygTestResponse = new WYGTestResponse();
        wygTestResponse.setError_code("0000");
        // 查询是否有-1的状态.有则直接返回
        wygTestResponse = getWygTestReturnMsgList("-1", wygTestRequest);
        if (null != wygTestResponse && !StringUtil.isEmpty(wygTestResponse.getReturnMsg())) {
            return wygTestResponse;
        }
        // 根据环节查询,outOrderId返回报文
        wygTestResponse = getWygTestReturnMsgList("yes", wygTestRequest);
        if (null != wygTestResponse && !StringUtil.isEmpty(wygTestResponse.getReturnMsg())) {
            return wygTestResponse;
        }
        return wygTestResponse;
    }

    public String ordOutCallApply(Map params) {

        String select_sql = "select t.log_id,t.order_status,t.order_id,t.is_finish from ES_ORDER_OUTCALL_LOG t where order_id = ? and t.is_finish = '0'";
        List<Map<String, Object>> selectResult = baseDaoSupport.queryForList(select_sql, params.get("order_id"));

        if (selectResult.size() != 1) {
            return "数据异常";
        }
        Map<String, Object> res = selectResult.get(0);
        if (res.get("log_id").toString() == null || res.get("log_id").toString().isEmpty()) {
            return "外呼记录不存在";
        } else if ((Integer) res.get("is_finish") != 0) {
            return "订单已被处理";
        }

        /*
         * Map<String, String> eParams = new HashMap<String, String>();
         * eParams.put("IS_FINISH", "1"); eParams.put("DEAL_TIME", "sysdate");
         * baseDaoSupport.update("ES_ORDER_OUTCALL_LOG", eParams,
         * "LOG_ID="+selectResult.get("log_id")+"");
         */

        OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance()
                .getOrderTree(params.get("order_id").toString());
        OrderBusiRequest orderBusiRequest = orderTree.getOrderBusiRequest();

        // 修改订单状态
        String isOrderCancel = params.get("isOrderCancel").toString();
        if (isOrderCancel != null && "true".equals(isOrderCancel)) {
            // orderBusiRequest.setStatus(12);
        } else {
            orderBusiRequest.setStatus((Integer) res.get("order_status"));
        }

        orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
        orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
        orderBusiRequest.store();

        // 标识日志记录为已处理
        String update_sql = "update ES_ORDER_OUTCALL_LOG c set c.IS_FINISH = 1, c.DEAL_TIME = sysdate, DEAL_OPER_ID = ? where LOG_ID = ?";
        this.daoSupport.execute(update_sql, params.get("oper_id"), selectResult.get(0).get("log_id"));
        return "success";
    }

    private void resetInfCommClientTestLog() {
        this.baseDaoSupport.execute("update inf_comm_client_test_log t set t.is_open = 'no', t.out_id = null");
        this.baseDaoSupport.execute("update es_order_trees t set t.col7 = null where t.col7 is not null");
    }

    @SuppressWarnings("unchecked")
    private WYGTestResponse getWygTestReturnMsgList(String isOpen, WYGTestRequest wygTestRequest) {
        //// 模拟报文orderID统一设置为:WCSV999999999
        List<Map<String, String>> wygTestReturnMsgList = null;
        StringBuffer sql = new StringBuffer();
        sql.append("select trace_name,   ");
        // if(wygTestRequest.getIsOutOrderId()){
        // sql.append(" replace(deal_desc, 'WCSV999999999', out_id) deal_desc, ");
        // }else{
        sql.append("   deal_desc deal_desc, ");
        // }
        sql.append("       trace_code,   ");
        sql.append("       is_open,   ");
        sql.append("       exe_cnt,   ");
        sql.append("       out_id   ");
        sql.append("  from es_inf_comm_client_test_log t   ");
        sql.append(" where  t.is_open = '").append(isOpen).append("'");

        if (!StringUtil.isEmpty(wygTestRequest.getOutOrderId()) && !"-1".equals(isOpen)) {
            sql.append(" and    t.out_id = ?   ");
            wygTestReturnMsgList = baseDaoSupport.queryForList(sql.toString(), wygTestRequest.getOutOrderId());
        } else {
            wygTestReturnMsgList = baseDaoSupport.queryForList(sql.toString());
        }
        resetInfCommClientTestLog();
        if (null != wygTestReturnMsgList && !wygTestReturnMsgList.isEmpty()) {
            WYGTestResponse wygTestResponse = new WYGTestResponse();
            wygTestResponse.setReturnMsg(wygTestReturnMsgList.get(0).get("deal_desc"));
            wygTestResponse.setTraceCode(wygTestReturnMsgList.get(0).get("trace_code"));
            return wygTestResponse;
        }
        return null;
    }

    @Override
    public boolean isOrderHasAchive(String order_id) {
        String sql = "select count(*) from es_order t where t.order_id=?";
        return this.baseDaoSupport.queryForInt(sql, order_id) == 0;
    }

    @Override
    public Page queryOrderMonitorList(int pageNo, int pageSize, OrderQueryParams params) {
        String sql_total = SF.ecsordSql("ORDER_MONI_TOTAL_LIST");
        String sql = SF.ecsordSql("ORDER_MONI_LIST");
        String sql_his = SF.ecsordSql("ORDER_MONI_HIS_LIST");
        List sqlParams = new ArrayList();
        if (params != null) {
            // 订单来源
            if (!StringUtil.isEmpty(params.getOrder_from()) && !"-1".equals(params.getOrder_from())) {
                sql += " and eoe.order_from in('" + params.getOrder_from().replace(",", "','") + "')";
                sql_his += " and eoe.order_from in('" + params.getOrder_from().replace(",", "','") + "')";
            }
            // 订单环节
            if (!StringUtil.isEmpty(params.getFlow_id()) && !"-1".equals(params.getFlow_id())) {
                sql += " and eoe.flow_trace_id in ('" + params.getFlow_id().replace(",", "','") + "') ";
                sql_his += " and eoe.flow_trace_id in ('" + params.getFlow_id().replace(",", "','") + "') ";
            }
            // 支付时间开始
            if (!StringUtil.isEmpty(params.getPay_start())) {
                sql += " and eo.pay_time>=" + DBTUtil.to_sql_date("?", 2);
                sql_his += " and eo.pay_time>=" + DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getPay_start());
            }
            // 支付时间结束
            if (!StringUtil.isEmpty(params.getPay_end())) {
                sql += " and eo.pay_time<=" + DBTUtil.to_sql_date("?", 2);
                sql_his += " and eo.pay_time<=" + DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getPay_end());
            }
            // 支付方式
            if (!StringUtil.isEmpty(params.getPayment_id()) && !"-1".equals(params.getPayment_id())) {
                sql += " and exists(select 1  from es_payment_logs plg where plg.order_id=eo.order_id and plg.source_from=eo.source_from and plg.pay_method in('"
                        + params.getPayment_id().replace(",", "','") + "'))";
                sql_his += " and exists(select 1  from es_payment_logs_his plg where plg.order_id=eo.order_id and plg.source_from=eo.source_from and plg.pay_method in('"
                        + params.getPayment_id().replace(",", "','") + "'))";
            }
            // 配送方式
            if (!StringUtil.isEmpty(params.getShipping_id()) && !"-1".equals(params.getShipping_id())) {
                sql += " and eo.shipping_type in('" + params.getShipping_id().replace(",", "','") + "')";
                sql_his += " and eo.shipping_type in('" + params.getShipping_id().replace(",", "','") + "')";
            }
            // 创建时间开始
            if (!StringUtil.isEmpty(params.getCreate_start())) {
                sql += " and eoe.tid_time>=" + DBTUtil.to_sql_date("?", 2);
                sql_his += " and eoe.tid_time>=" + DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_start());
            }
            // 创建时间结束
            if (!StringUtil.isEmpty(params.getCreate_end())) {
                sql += " and eoe.tid_time<=" + DBTUtil.to_sql_date("?", 2);
                sql_his += " and eoe.tid_time<=" + DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_end());
            }
            // 订单归属地市【选择城市】
            if (!StringUtil.isEmpty(params.getOrder_city_code()) && !"-1".equals(params.getOrder_city_code())) {
                sql += " and eoe.order_city_code in('" + params.getOrder_city_code().replace(",", "','") + "')";
                sql_his += " and eoe.order_city_code in('" + params.getOrder_city_code().replace(",", "','") + "')";
            }
            // 生产模式
            if (!StringUtil.isEmpty(params.getOrder_model()) && !"-1".equals(params.getOrder_model())) {
                sql += " and eoe.order_model = ? ";
                sql_his += " and eoe.order_model = ? ";
                sqlParams.add(params.getOrder_model());
            }
            // 异常类型
            if (!StringUtil.isEmpty(params.getException_code()) && !"-1".equals(params.getException_code())) {
                sql += " and eoe.EXCEPTION_TYPE=?";
                sql_his += " and eoe.EXCEPTION_TYPE=?";
                sqlParams.add(params.getException_code());
            }
            // 是否异常订单( 全部订单、正常单、异常单) 待定
            if (!StringUtil.isEmpty(params.getIs_exception()) && !"-1".equals(params.getIs_exception())) {
                sql += " and eoe.ABNORMAL_STATUS=?";
                sql_his += " and eoe.ABNORMAL_STATUS=?";
                sqlParams.add(params.getIs_exception());
            }
            if (!StringUtil.isEmpty(params.getAbnormal_type()) && !"-1".equals(params.getAbnormal_type())) {
                if ("0".equals(params.getAbnormal_type())) {
                    sql += " and nvl(eoe.ABNORMAL_TYPE,0) = ? ";
                    sql_his += " and nvl(eoe.ABNORMAL_TYPE,0) = ? ";
                }
                if ("1".equals(params.getAbnormal_type())) {
                    sql += " and eoe.ABNORMAL_TYPE in (?,'2','3') ";
                    sql_his += " and eoe.ABNORMAL_TYPE in (?,'2','3') ";
                }
                sqlParams.add(params.getAbnormal_type());
            }
            // WMS退单状态
            if (!StringUtil.isEmpty(params.getWms_refund_status()) && !"-1".equals(params.getWms_refund_status())) {
                sql += " and exists (select 1 from  es_order_extvtl ee where ee.order_id=eo.order_id and ee.wms_refund_status  = ? )";
                sql_his += " and exists (select 1 from  es_order_extvtl_his ee where ee.order_id=eo.order_id and ee.wms_refund_status  = ? )";
                sqlParams.add(params.getWms_refund_status());
            }

            // 签收状态
            if (!StringUtil.isEmpty(params.getSign_status()) && !"-1".equals(params.getSign_status())) {
                sql += " and ed.sign_status =? ";
                sql_his += " and ed.sign_status =? ";
                sqlParams.add(params.getSign_status());
            }
            // 回单状态
            if (!StringUtil.isEmpty(params.getReceipt_status()) && !"-1".equals(params.getReceipt_status())) {
                sql += " and ed.receipt_status =? ";
                sql_his += " and ed.receipt_status =? ";
                sqlParams.add(params.getReceipt_status());
            }

            // 发票号码
            if (!StringUtil.isEmpty(params.getInvoice_num())) {
                sql += " and eoiip.invoice_num = ? ";
                sql_his += " and eoiip.invoice_num = ? ";
                sqlParams.add(params.getInvoice_num());
            }

            // 是否预约单
            if (!"-1".equals(params.getWm_isreservation_from())) {// 非人为选"全部"
                if (StringUtil.isEmpty(params.getWm_isreservation_from())
                        || EcsOrderConsts.NO_DEFAULT_VALUE.equals(params.getWm_isreservation_from())) {// 未人工选择,或者人为选"否",都认为选"否"
                    sql += " and (eoe.wm_isreservation_from = ? or eoe.wm_isreservation_from is null)";
                    sql_his += " and (eoe.wm_isreservation_from = ? or eoe.wm_isreservation_from is null)";
                    sqlParams.add(EcsOrderConsts.NO_DEFAULT_VALUE);
                } else {// 人为选其他,包含认为选"是"
                    sql += " and eoe.wm_isreservation_from = ? ";
                    sql_his += " and eoe.wm_isreservation_from = ? ";
                    sqlParams.add(params.getWm_isreservation_from());
                }
            }
        }

        // 过滤作废单---zengxianlian
        sql += " and (eoe.order_if_cancel ='0' or eoe.order_if_cancel is null)";
        sql_his += " and (eoe.order_if_cancel ='0' or eoe.order_if_cancel is null)";

        String new_sql = sql_total + " from( " + sql + " union all " + sql_his + ") ord ";
        String countSql = "select count(1) from( " + sql + " union all " + sql_his + ") ord ";
        new_sql += " order by ord.create_time desc";
        List pList = new ArrayList();
        pList.addAll(sqlParams);
        pList.addAll(sqlParams);
        Page page = this.baseDaoSupport.queryForPage(new_sql, countSql, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();
                data.put("out_tid", rs.getString("out_tid"));
                data.put("order_id", rs.getString("order_id"));
                data.put("tid_time", rs.getTimestamp("tid_time"));
                data.put("create_time", rs.getTimestamp("create_time"));
                data.put("order_from_str", rs.getString("order_from_str"));
                data.put("order_city", rs.getString("order_city"));
                data.put("current_flow_name", rs.getString("current_flow_name"));
                data.put("pack_type", rs.getString("pack_type"));
                data.put("goodsname", rs.getString("goodsname"));
                data.put("wms_refund_status", rs.getString("wms_refund_status"));
                data.put("plan_title", rs.getString("plan_title"));
                data.put("first_payment", rs.getString("first_payment"));
                data.put("phone_num", rs.getString("phone_num"));
                data.put("order_mode_str", rs.getString("order_mode_str"));
                data.put("pay_type", rs.getString("pay_type"));
                data.put("pay_method", rs.getString("pay_method"));
                data.put("pay_status", rs.getString("pay_status"));
                data.put("pay_time", rs.getTimestamp("pay_time"));
                data.put("service_remarks", rs.getString("service_remarks"));
                data.put("shipping_quick", rs.getString("shipping_quick"));
                data.put("shipping_type", rs.getString("shipping_type"));
                data.put("spread_channel", rs.getString("spread_channel"));
                data.put("development_code", rs.getString("development_code"));
                data.put("development_name", rs.getString("development_name"));
                data.put("payplatformorderid", rs.getString("payplatformorderid"));
                data.put("goods_num", rs.getString("goods_num"));
                data.put("terminal_num", rs.getString("terminal_num"));
                data.put("iccid", rs.getString("iccid"));
                data.put("brand_name", rs.getString("brand_name"));
                data.put("model_code", rs.getString("model_code"));
                data.put("ative_type", rs.getString("ative_type"));
                data.put("phone_owner_name", rs.getString("phone_owner_name"));
                data.put("certi_type", rs.getString("certi_type"));
                data.put("cert_card_num", rs.getString("cert_card_num"));
                data.put("cert_address", rs.getString("cert_address"));
                data.put("invoice_title", rs.getString("invoice_title"));
                data.put("recommended_name", rs.getString("recommended_name"));
                data.put("recommended_phone", rs.getString("recommended_phone"));
                data.put("shipping_company_name", rs.getString("shipping_company_name"));
                data.put("logi_no", rs.getString("logi_no"));
                data.put("ship_tel", rs.getString("ship_tel"));
                data.put("buyer_message", rs.getString("buyer_message"));
                data.put("seller_message", rs.getString("seller_message"));
                data.put("auto_exception_type", rs.getString("auto_exception_type"));
                data.put("auto_exception_desc", rs.getString("auto_exception_desc"));
                data.put("exception_type", rs.getString("exception_type"));
                data.put("exception_desc", rs.getString("exception_desc"));
                data.put("invoice_num", rs.getString("invoice_num"));
                data.put("paymoney", rs.getString("paymoney"));
                data.put("bss_account_time", rs.getString("bss_account_time"));
                data.put("sign_status", rs.getString("sign_status"));
                data.put("receipt_status", rs.getString("receipt_status"));
                data.put("exception_type", rs.getString("exception_type1"));
                data.put("exception_desc", rs.getString("exception_desc1"));
                data.put("auto_exception_type", rs.getString("exception_type2"));
                data.put("auto_exception_desc", rs.getString("exception_desc2"));
                data.put("wm_isreservation_from", rs.getString("wm_isreservation_from"));
                data.put("related_order_id", rs.getString("related_order_id"));
                return data;
            }
        }, pList.toArray());
        return page;
    }

    @Override
    public List<Map> queryOrderGetGroup() {
        // 查询订单领取分布
        String select_sql = "select glu.lock_user_id,ea.realname,glu.count_order from ( select eol.lock_user_id lock_user_id,count(eol.lock_user_id) count_order from ES_ORDER_EXT oe left join ES_ORDER eo ON oe.order_id = eo.order_id left join es_order_lock eol on eol.order_id = oe.order_id and eol.tache_code = oe.flow_trace_id where oe.source_from = '"
                + ManagerUtils.getSourceFrom()
                + "' and eol.lock_status = 1 group by eol.lock_user_id) glu left join es_adminuser ea on glu.lock_user_id = ea.userid  where ea.realname is not null and rownum<=10 ";
        List<Map> res = new ArrayList();
        res = this.baseDaoSupport.queryForList(select_sql, null);
        return res;
    }

    @Override
    public List<Map> queryOrderStatusGroup() {
        // 查询订单状态分布
        String select_sql = "select edpe.pname status, count(edpe.pname) status_count from es_order eo left join es_dc_public_ext edpe on  edpe.comments = '订单状态' and to_char(eo.status) = edpe.pkey group by edpe.pname having pname is not null";
        List<Map> res = new ArrayList();
        res = this.baseDaoSupport.queryForList(select_sql, null);
        return res;
    }

    // 未开户分布查询
    public List<Map> queryAccount_noopen_group() {
        // 未领取未开户
        // 已领取未开户
        String select_sql = "((select '已领取未开户' typename,count(*) ordercount from (select eoe.out_tid, eoe.flow_trace_id, eo.status, eol.* from es_order_ext eoe left join es_order eo on eo.order_id = eoe.order_id left join es_order_lock eol on eol.order_id = eoe.order_id and eol.tache_code = eoe.flow_trace_id where eoe.source_from = 'ECS' and eo.status in ('4') and eol.lock_status = '1')) union all (select '未领取未开户' typename,count(*) ordercount from (select eoe.out_tid, eoe.flow_trace_id, eo.status, eol.* from es_order_ext eoe left join es_order eo on eo.order_id = eoe.order_id left join es_order_lock eol on eol.order_id = eoe.order_id and eol.tache_code = eoe.flow_trace_id where eoe.source_from = 'ECS' and eo.status in ('4')) notopen where notopen.lock_status != '1' or notopen.lock_id is null)) union all (select (select ead.realname from es_adminuser ead where ead.userid= a.lock_user_id) lockeduser,count(a.lock_user_id) lockedcount from( select eoe.out_tid,eoe.flow_trace_id,eo.status,eol.* from es_order_ext eoe left join es_order eo on eo.order_id=eoe.order_id left join es_order_lock eol on eol.order_id=eoe.order_id and eol.tache_code=eoe.flow_trace_id where eoe.source_from='ECS' and eo.status in ('4') and eol.lock_status='1')a group by a.lock_user_id)";
        List<Map> res = new ArrayList();
        res = this.baseDaoSupport.queryForList(select_sql, null);
        return res;
    }

    // 待审核分布
    public List<Map> queryAccount_audit_group() {
        String select_sql = " ((select '已领取未审核' audittypename,count(*) auditordercount from (select eoe.out_tid, eoe.flow_trace_id, eo.status, eol.* from es_order_ext eoe left join es_order eo on eo.order_id = eoe.order_id left join es_order_lock eol on eol.order_id = eoe.order_id and eol.tache_code = eoe.flow_trace_id where eoe.source_from = 'ECS' and eo.status in ('2') and eol.lock_status = '1')) union all (select '未领取未审核' audittypename,count(*) auditordercount from (select eoe.out_tid, eoe.flow_trace_id, eo.status, eol.* from es_order_ext eoe left join es_order eo on eo.order_id = eoe.order_id left join es_order_lock eol on eol.order_id = eoe.order_id and eol.tache_code = eoe.flow_trace_id where eoe.source_from = 'ECS' and eo.status in ('2')) notopen where notopen.lock_status != '1' or notopen.lock_id is null)) union all (select (select ead.realname from es_adminuser ead where ead.userid= a.lock_user_id) lockeduser,count(a.lock_user_id) lockedcount from( select eoe.out_tid,eoe.flow_trace_id,eo.status,eol.* from es_order_ext eoe left join es_order eo on eo.order_id=eoe.order_id left join es_order_lock eol on eol.order_id=eoe.order_id and eol.tache_code=eoe.flow_trace_id where eoe.source_from='ECS' and eo.status in ('2') and eol.lock_status='1')a group by a.lock_user_id)";
        List<Map> res = new ArrayList();
        res = this.baseDaoSupport.queryForList(select_sql, null);
        return res;
    }

    @Override
    public Page queryReleaseList(int pageNo, int pageSize, OrderQueryParams params) {
        Page page = null;
        String sql = SF.ecsordSql("ORDER_RELEASE_LIST");
        List sqlParams = new ArrayList();

        // 外部订单号
        if (!StringUtil.isEmpty(params.getOut_tid())) {
            String order_id = orderExtManager.getOrderIdByOutTid(params.getOut_tid());
            sql += " and t.order_id = ? ";
            sqlParams.add(order_id);

        }
        // 内部订单号
        if (!StringUtil.isEmpty(params.getOrder_id())) {
            sql += " and t.order_id = ? ";
            sqlParams.add(params.getOrder_id());
        }
        // 类型(手机终端/号码)
        if (!StringUtil.isEmpty(params.getType())) {
            sql += " and t.type = ? ";
            sqlParams.add(params.getType());
        }
        // 类型(手机终端/号码)
        if (!StringUtil.isEmpty(params.getPhone_num())) {
            sql += " and t.serial_or_num = ? ";
            sqlParams.add(params.getPhone_num());
        }
        // 预占工号
        if (!StringUtil.isEmpty(params.getLock_user_id())) {
            sql += " and t.occupied_essid = ? ";
            sqlParams.add(params.getLock_user_id());
        }
        // 预占关键字
        if (!StringUtil.isEmpty(params.getProkey())) {
            sql += " and t.prokey = ? ";
            sqlParams.add(params.getProkey());
        }
        // 处理方式
        if (!StringUtil.isEmpty(params.getDeal_type()) && !"-1".equals(params.getDeal_type())) {
            sql += " and t.is_deal = ? ";
            sqlParams.add(params.getDeal_type());
        }
        // 释放失败时间开始
        if (!StringUtil.isEmpty(params.getCreate_start())) {
            sql += " and t.create_time>=" + DBTUtil.to_sql_date("?", 2);
            sqlParams.add(params.getCreate_start());
        }
        // 释放失败时间结束
        if (!StringUtil.isEmpty(params.getCreate_end())) {
            sql += " and t.create_time<=" + DBTUtil.to_sql_date("?", 2);
            sqlParams.add(params.getCreate_end());
        }
        sql += "order by t.create_time desc";
        String countSql = "select count(*) from (" + sql + ") cord";
        // page = this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize,
        // OrderReleaseRecord.class, countSql, sqlParams.toArray());

        List pList = new ArrayList();
        pList.addAll(sqlParams);

        page = this.baseDaoSupport.queryForPage(sql, countSql, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();
                data.put("release_id", rs.getString("release_id"));
                data.put("order_id", rs.getString("order_id"));
                data.put("type", rs.getString("type"));
                data.put("serial_or_num", rs.getString("serial_or_num"));
                data.put("occupied_essid", rs.getString("occupied_essid"));
                data.put("prokey", rs.getString("prokey"));
                data.put("deal_desc", rs.getString("deal_desc"));
                data.put("create_time", rs.getString("create_time"));
                data.put("is_deal", rs.getString("is_deal"));
                data.put("deal_username", rs.getString("deal_username"));
                data.put("deal_time", rs.getString("deal_time"));
                data.put("source_from", rs.getString("source_from"));
                data.put("realname", rs.getString("realname"));
                return data;
            }
        }, pList.toArray());
        return page;
    }

    @Override
    public String releaseRecord(String release_id) {
        try {
            String sql = SF.ecsordSql("RELEASE_RECORD_SELECT_BY_ID");
            OrderReleaseRecord record = (OrderReleaseRecord) this.baseDaoSupport.queryForObject(sql,
                    OrderReleaseRecord.class, release_id);
            record.setIs_deal(EcsOrderConsts.RELEASE_OFFLINE);// 线下处理
            record.setDeal_desc("线下处理完成");// 释放描述
            AdminUser user = ManagerUtils.getAdminUser();
            if (null != user) {
                record.setDeal_username(user.getUsername());// 处理人
            }
            record.setDeal_time(DateUtil.getTime2());// 处理时间
            this.baseDaoSupport.update("es_order_release_record", record, "release_id='" + release_id + "'");
        } catch (Exception e) {
            e.printStackTrace();
            return "处理失败";
        }
        return "处理成功！";
    }

    @Override
    public String releaseRecordByI(String release_id) {
        String msg = "处理成功!";// 页面提示信息
        try {
            String is_deal = EcsOrderConsts.RELEASE_ONLINE;// 接口处理
            String deal_desc = "接口处理成功";// 保存到数据库的释放描述

            String sql = SF.ecsordSql("RELEASE_RECORD_SELECT_BY_ID");
            OrderReleaseRecord record = (OrderReleaseRecord) this.baseDaoSupport.queryForObject(sql,
                    OrderReleaseRecord.class, release_id);
            if (null == record) {
                return "查无此信息";
            }
            // 将表里的报文转为json对象
            // String json = record.getFirst_req_json();//这个字段已经废弃不用

            // 先拿到上一次释放的工号
            String essid = record.getOccupied_essid();
            if (StringUtils.isEmpty(essid)) {
                // msg = "未获取到释放工号(cb),请联系运维处理";
                // deal_desc = "未获取到释放工号(cb),请联系运维处理";
                return "未获取到释放工号(cb),请联系运维处理";// 这里返回,未更新数据库
            }

            // 获取工号信息
            EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssinfoByEssId(essid, record.getOrder_id(),
                    EcsOrderConsts.OPER_TYPE_ESS);
            EmpOperInfoVo essOperInfo = new EmpOperInfoVo();
            if (StringUtil.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL)
                    || essRspInfo.getOperInfo() == null) {// 获取工号出错
                is_deal = EcsOrderConsts.NOT_RELEASE_YET;// 未处理
                msg = essRspInfo.getError_msg();
                deal_desc = essRspInfo.getError_msg();
            } else {// 向外系统发起释放请求
                essOperInfo = essRspInfo.getOperInfo();

                ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
                if (EcsOrderConsts.TERMINAL_RELEASE_FAIL.equals(record.getType())) {

                    // 终端状态变更(释放)
                    TerminalStatusQueryChanage1Req req = new TerminalStatusQueryChanage1Req();
                    // if(null==req){
                    // throw new JSONException("报文不匹配TerminalStatusQueryChanage1Req");
                    // }
                    /* 直接存在表里的字段 */
                    req.setNotNeedReqStrOrderId(record.getOrder_id());// 订单内部单号
                    req.setEssOperInfo(essOperInfo);// 预占工号
                    req.setOperFlag(EcsOrderConsts.OCCUPIEDFLAG_99);

                    List<ResourcesInfoVo> list = new ArrayList();
                    ResourcesInfoVo resourcesInfo = new ResourcesInfoVo();
                    resourcesInfo.setOccupiedFlag(EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_3);
                    resourcesInfo.setResourcesCode(record.getSerial_or_num());
                    resourcesInfo.setResCodeType("01");
                    resourcesInfo.setResourcesType("01");
                    resourcesInfo.setAllocationFlag(EcsOrderConsts.AOP_ALLOCATION_FLAG_TERI_1);
                    // 获取合约类型
                    String type_id = CommonDataFactory.getInstance().getAttrFieldValue(record.getOrder_id(),
                            AttrConsts.GOODS_TYPE);
                    if (EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id)) {
                        // 商品小类
                        String cat_id = CommonDataFactory.getInstance().getAttrFieldValue(record.getOrder_id(),
                                AttrConsts.GOODS_CAT_ID);
                        // 根据商品小类获取对应的合约类型编码
                        String active_type = AttrUtils.getInstance().getOtherDictCodeValue(AttrConsts.ACTIVE_TYPE_AOP,
                                cat_id);
                        // 为空时默认为存费送机合约
                        if (StringUtils.isEmpty(active_type)) {
                            active_type = EcsOrderConsts.ACTIVE_TYPE_01;
                        }
                        resourcesInfo.setActiveType(active_type);
                    } else {
                        // 无合约
                        resourcesInfo.setActiveType(EcsOrderConsts.ACTIVE_TYPE_04);
                    }
                    resourcesInfo.setIsSelf(EcsOrderConsts.TERM_IS_SELF_1);// 默认非自备
                    list.add(resourcesInfo);
                    req.setResourcesInfo(list);

                    TerminalStatusQueryChanageResp resp = client.execute(req, TerminalStatusQueryChanageResp.class);
                    if ("-9999".equals(resp.getCode())) {// 调接口异常
                        is_deal = EcsOrderConsts.NOT_RELEASE_YET;// 未处理
                        msg = "接口异常";
                        deal_desc = resp.getDetail();
                    } else if (null == resp.getResourcesRsp() || // 空即释放失败
                            resp.getResourcesRsp().size() < 1) {// 数量小于1即释放失败
                        is_deal = EcsOrderConsts.NOT_RELEASE_YET;// 未处理
                        msg = "释放失败";
                        deal_desc = "释放失败";
                    } else if (!StringUtil.equals(resp.getResourcesRsp().get(0).getRscStateCode(),
                            EcsOrderConsts.AOP_SUCCESS_0000)) {// AOP返回释放失败
                        is_deal = EcsOrderConsts.NOT_RELEASE_YET;// 未处理
                        msg = resp.getResourcesRsp().get(0).getRscStateDesc();// AOP返回的结果描述
                        deal_desc = resp.getResourcesRsp().get(0).getRscStateDesc();
                    }
                } else if (EcsOrderConsts.AOP_NUM_RELEASE_FAIL.equals(record.getType())) {// ESS释放失败

                    // 号码状态变更(释放)
                    NumberStateChangeZB1Request req = new NumberStateChangeZB1Request();
                    // if(null==req){
                    // throw new JSONException("报文不匹配NumberStateChangeZB1Request");
                    // }
                    /* 直接存在表里的字段 */
                    req.setNotNeedReqStrOrderId(record.getOrder_id());// 订单内部单号
                    req.setEssOperInfo(essOperInfo);// 预占工号
                    req.setOperFlag(EcsOrderConsts.OCCUPIEDFLAG_99);

                    List<ResourcesInfo> list = new ArrayList();
                    ResourcesInfo resourcesInfo = new ResourcesInfo();
                    resourcesInfo.setOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_4);
                    resourcesInfo.setProKey(record.getProkey());
                    resourcesInfo.setResourcesCode(record.getSerial_or_num());
                    resourcesInfo.setSnChangeTag("0");
                    resourcesInfo.setResourcesType("02");
                    list.add(resourcesInfo);
                    req.setResourcesInfo(list);

                    NumberStateChangeZBResponse resp = client.execute(req, NumberStateChangeZBResponse.class);
                    if ("-9999".equals(resp.getCode())) {
                        is_deal = EcsOrderConsts.NOT_RELEASE_YET;// 未处理
                        msg = "调用AOP接口异常";
                        deal_desc = resp.getDetail();
                    } else if (!resp.getCode().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {// 释放不成功
                        is_deal = EcsOrderConsts.NOT_RELEASE_YET;// 未处理
                        msg = resp.getDetail();
                        deal_desc = resp.getDetail();
                    }
                } else if (EcsOrderConsts.BSS_NUM_RELEASE_FAIL.equals(record.getType())) {// BSS释放失败

                    // 号码状态变更(释放)
                    NumberStateChangeBss1Request req = new NumberStateChangeBss1Request();
                    // if(null==req){
                    // throw new JSONException("报文不匹配NumberStateChangeBss1Request");
                    // }
                    /* 直接存在表里的字段 */
                    req.setNotNeedReqStrOrderId(record.getOrder_id());// 订单内部单号
                    req.setX_tagchar(EcsOrderConsts.BSS_OCCUPIEDFLAG_2);// 释放
                    req.setPro_key(record.getProkey());// 预占关键字
                    req.setPhone_num(record.getSerial_or_num());// 号码
                    req.setEssOperInfo(essOperInfo);// 预占工号
                    req.setParam_value2("30");

                    req.setOperFlag(EcsOrderConsts.OCCUPIEDFLAG_99);

                    NumberStateChangeBssResp resp = client.execute(req, NumberStateChangeBssResp.class);
                    if (!resp.getError_code().equals(EcsOrderConsts.INF_RESP_CODE_0000)) {// 释放不成功
                        is_deal = EcsOrderConsts.NOT_RELEASE_YET;// 未处理
                        String responseFromBSS = resp.getX_check_info();
                        if (StringUtils.isEmpty(responseFromBSS)) {
                            msg = "释放处理失败";
                            deal_desc = "未收到BSS释放结果";
                        } else if (responseFromBSS.contains("130381")) {
                            msg = "号码不需要或者不允许释放";
                            deal_desc = responseFromBSS;
                        } else if (responseFromBSS.contains("1000-1")) {
                            msg = "号码不存在";
                            deal_desc = responseFromBSS;
                        } else {
                            msg = "号码释放失败";
                            deal_desc = responseFromBSS;
                        }
                    }
                } else {// 其他失败，默认处理失败
                    msg = "未定义类型，无法处理!";
                    return msg;
                }
            }

            // deal_desc = msg;
            int length = 250;
            deal_desc = deal_desc.replace("<", "《");// 这2步解除返回报文带html标签时引起的页面解析错误
            deal_desc = deal_desc.replace(">", "》");
            if (null != deal_desc && deal_desc.length() > length) {
                deal_desc = deal_desc.substring(0, length);
            }
            record.setIs_deal(is_deal);// 处理方式
            record.setDeal_desc(deal_desc);// 释放描述
            AdminUser user = ManagerUtils.getAdminUser();
            if (null != user) {
                record.setDeal_username(user.getUsername());// 处理人
            }
            record.setDeal_time(DateUtil.getTime2());// 处理时间
            this.baseDaoSupport.update("es_order_release_record", record, "release_id='" + release_id + "'");
            // } catch (JSONException e) {//json与对应请求类不匹配，一定无法释放，所以直接抛出错误//已经去掉json的转化
            // e.printStackTrace();
            // msg = "处理失败,请线下进行释放";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "处理失败";
        }
        if (null != msg) {
            msg = msg.replace('\'', '\"');
        }
        return msg;
    }

    @Override
    public String terminalTransfer(String ess_emp_id, String terminal_nums) {
        String[] terminalNums = terminal_nums.split(",");
        if (terminalNums.length > 10) {
            return "每次调拨数量不能多于10个!";
        }
        String deal_result = "";// 处理结果
        // 拿工号
        EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssinfoByEssId(ess_emp_id, "terminalTransfer",
                EcsOrderConsts.OPER_TYPE_ESS);
        if (StringUtil.equals(essRspInfo.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_FAIL)
                || essRspInfo.getOperInfo() == null) {// 获取工号出错
            deal_result = "此工号不可用，请换工号或重新绑定。";
        } else {
            EmpOperInfoVo essOperInfo = essRspInfo.getOperInfo();
            ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

            // 先预占
            TerminalStatusQueryChanage1Req req = new TerminalStatusQueryChanage1Req();
            req.setNotNeedReqStrOrderId("terminalTransfer");// 订单内部单号
            req.setEssOperInfo(essOperInfo);// 预占工号
            req.setOperFlag(EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_99);

            List<ResourcesInfoVo> list = new ArrayList();
            for (int i = 0; i < terminalNums.length; i++) {
                if (null != terminalNums[i] && !StringUtils.isEmpty(terminalNums[i].trim())) {
                    ResourcesInfoVo resourcesInfo = new ResourcesInfoVo();
                    resourcesInfo.setOccupiedFlag(EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_1);
                    resourcesInfo.setResourcesCode(terminalNums[i]);
                    resourcesInfo.setResCodeType("01");
                    resourcesInfo.setResourcesType("01");
                    resourcesInfo.setAllocationFlag(EcsOrderConsts.AOP_ALLOCATION_FLAG_TERI_1);
                    resourcesInfo.setOccupiedTime("20591231235900");
                    resourcesInfo.setActiveType(EcsOrderConsts.ACTIVE_TYPE_04);// 默认无合约
                    resourcesInfo.setIsSelf(EcsOrderConsts.TERM_IS_SELF_1);// 默认非自备
                    list.add(resourcesInfo);
                }
            }
            req.setResourcesInfo(list);

            TerminalStatusQueryChanageResp resp = client.execute(req, TerminalStatusQueryChanageResp.class);
            if ("-9999".equals(resp.getCode())) {// 调接口异常
                deal_result = "预占时接口异常";
            } else {
                if (null == resp.getResourcesRsp() || resp.getResourcesRsp().size() == 0) {
                    deal_result = resp.getDetail();
                } else {
                    for (ResourcesRspVo rspvo : resp.getResourcesRsp()) {
                        // 只有code=0000 资源可用 才算执行成功
                        if (!EcsOrderConsts.AOP_TERMI_0000.equals(rspvo.getRscStateCode())) {
                            deal_result += rspvo.getResourcesCode() + "预占时" + rspvo.getRscStateDesc() + ";";
                        }
                    }
                }
            }

            // 后释放
            req = new TerminalStatusQueryChanage1Req();
            // if(null==req){
            // throw new JSONException("报文不匹配TerminalStatusQueryChanage1Req");
            // }
            /* 直接存在表里的字段 */
            req.setNotNeedReqStrOrderId("terminalTransfer");// 订单内部单号
            req.setEssOperInfo(essOperInfo);// 预占工号
            req.setOperFlag(EcsOrderConsts.OCCUPIEDFLAG_99);

            list = new ArrayList();
            for (int i = 0; i < terminalNums.length; i++) {
                if (null != terminalNums[i] && !StringUtils.isEmpty(terminalNums[i].trim())) {
                    ResourcesInfoVo resourcesInfo = new ResourcesInfoVo();
                    resourcesInfo.setOccupiedFlag(EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_3);
                    resourcesInfo.setResourcesCode(terminalNums[i]);
                    resourcesInfo.setResCodeType("01");
                    resourcesInfo.setResourcesType("01");
                    resourcesInfo.setAllocationFlag(EcsOrderConsts.AOP_ALLOCATION_FLAG_TERI_1);
                    resourcesInfo.setActiveType(EcsOrderConsts.ACTIVE_TYPE_04);// 默认无合约
                    resourcesInfo.setIsSelf(EcsOrderConsts.TERM_IS_SELF_1);// 默认非自备
                    list.add(resourcesInfo);
                    req.setResourcesInfo(list);
                }
            }

            resp = client.execute(req, TerminalStatusQueryChanageResp.class);
            if ("-9999".equals(resp.getCode())) {// 调接口异常
                deal_result = "释放时接口异常";
            } else {
                if (null == resp.getResourcesRsp() || resp.getResourcesRsp().size() == 0) {
                    deal_result = resp.getDetail();
                } else {
                    for (ResourcesRspVo rspvo : resp.getResourcesRsp()) {
                        // 只有code=0000 资源可用 才算执行成功
                        if (!EcsOrderConsts.AOP_TERMI_0000.equals(rspvo.getRscStateCode())) {
                            deal_result += rspvo.getResourcesCode() + "释放时" + rspvo.getRscStateDesc() + ";";
                        }
                    }
                }
            }

        }
        if (StringUtils.isEmpty(deal_result)) {
            deal_result = "调拨成功";
        }
        return deal_result;
    }

    @Override
    public List<EmpOperInfoVo> getBindRelByOrderGonghao(String orderOperId) {
        return EssOperatorInfoUtil.getBindRelByOrderGonghao(orderOperId);
    }

    /**
     * 按环境标识过滤是否属于解藕环境的订单
     * 
     * @param order_id
     * @return
     */
    @Override
    public boolean getOrderCtn(String order_id) {
        String p_ordstd = System.getProperty("JAVA_BUSI_ORDCTN");
        int num = this.baseDaoSupport.queryForInt(
                "select count(1) from es_order_queue_his qhis,es_order_ext eoe where qhis.source_from='"
                        + ManagerUtils.getSourceFrom() + "' and qhis.object_id = eoe.out_tid and eoe.order_id=?",
                order_id);
        if (!StringUtils.isEmpty(p_ordstd)) {
            if ("yes".equals(p_ordstd)) {
                return num > 0 ? true : false;
            } else {
                return num <= 0 ? true : false;
            }
        } else {
            return true;
        }
    }

    /**
     * 根据sn获取sku(目前仅针对华盛裸机订单)
     * 
     * @param sn
     * @return
     */
    @Override
    public String getSkuBySn(String sn) {
        String sku = this.baseDaoSupport
                .queryForString("select g.sku from es_goods g where g.type_id = '20003' and g.sn = '" + sn
                        + "' and g.source_from = '" + ManagerUtils.getSourceFrom() + "' and rownum < 2");
        return sku;
    }

    /**
     * 校验终端串号
     * 
     * @return 校验结果 true 通过
     */
    @Override
    public boolean terminalNumCheck(String order_id, String terminal_num) {
        boolean flag = false;
        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        TerminalStatusQueryChanageReq req = new TerminalStatusQueryChanageReq();
        req.setNotNeedReqStrOrderId(order_id);
        req.setResources_code(terminal_num);
        req.setOperFlag(EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_0);

        OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

        TerminalStatusQueryChanageResp infResp = client.execute(req, TerminalStatusQueryChanageResp.class);
        if (!(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "").equals(infResp.getCode())) {
            ResourcesRspVo resourcesRspVo = infResp.getResourcesRsp().get(0);
            if (resourcesRspVo != null) {
                String color_name = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000,
                        null, SpecConsts.COLOR_NAME);
                String model_name = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000,
                        null, SpecConsts.MODEL_NAME);
                String machine_code = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000,
                        null, SpecConsts.MACHINE_CODE);

                String resourcesColor = resourcesRspVo.getResourcesColor();
                String machineTypeName = resourcesRspVo.getMachineTypeName();
                String machineTypeCode = resourcesRspVo.getMachineTypeCode();

                /*
                 * if((StringUtil.equals(color_name,
                 * resourcesColor)||(StringUtil.isEmpty(color_name)&&StringUtil.isEmpty(
                 * resourcesColor))) &&(StringUtil.equals(model_name,
                 * machineTypeName)||(StringUtil.isEmpty(model_name)&&StringUtil.isEmpty(
                 * machineTypeName))) &&(StringUtil.equals(machine_code,
                 * machineTypeCode)||(StringUtil.isEmpty(machine_code)&&StringUtil.isEmpty(
                 * machineTypeCode))) ){//比较是否相同，包括 “” null 也认为相等
                 */
                flag = true;// 校验通过
                /* } */
            }
        }
        return flag;
    }

    @Override
    public Map<String, String> validateTerminalNum(String terminal_nums, String order_sn) {
        Map<String, String> params = new HashMap<String, String>();
        String error_code = ConstsCore.ERROR_FAIL;
        String sql = "SELECT order_id FROM es_order WHERE sn=? AND status=? AND source_from='"
                + ManagerUtils.getSourceFrom() + "'";
        String orderId = this.baseDaoSupport.queryForString(sql, order_sn, ZjEcsOrderConsts.DIC_ORDER_STATUS_72);
        String orderTerminalNums = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.TERMINAL_NUM);
        if (terminal_nums.trim().equals(orderTerminalNums)) {
            error_code = ConstsCore.ERROR_SUCC;
        }
        params.put("error_code", error_code);
        params.put("order_id", orderId);
        return params;
    }

    /**
     * 
     * 根据条件查询库管日报
     */
    @Override
    public Page queryOrderWarehouseDailyList(int pageNo, int pageSize, OrderQueryParams params) {

        String sql_total = SF.ecsordSql("WAREHOUSE_DAILY_REPORT_HEAD_LIST");
        String sql = SF.ecsordSql("WAREHOUSE_DAILY_REPORT_LIST");
        // String sql_his = SF.ecsordSql("ORDER_MONI_HIS_LIST");
        List sqlParams = new ArrayList();
        if (params != null) {

            // 创建时间开始
            if (!StringUtil.isEmpty(params.getCreate_start())) {
                sql += " and eoe.tid_time>=" + DBTUtil.to_sql_date("?", 2);
                // sql_his += " and eoe.tid_time>="+DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_start());
            }
            // 创建时间结束
            if (!StringUtil.isEmpty(params.getCreate_end())) {
                sql += " and eoe.tid_time<=" + DBTUtil.to_sql_date("?", 2);
                // sql_his += " and eoe.tid_time<="+DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_end());
            }
        }

        // 筛选订单状态为发货以及资料归档，订单归档的单子
        sql += "and eoe.flow_trace_id in('H','L','J')";

        // 过滤作废单---zengxianlian
        sql += " and (eoe.order_if_cancel ='0' or eoe.order_if_cancel is null)";
        // sql_his += " and (eoe.order_if_cancel ='0' or eoe.order_if_cancel is null)";

        String new_sql = sql_total + " from(" + sql + ") ord ";
        String countSql = "select count(1) from( " + sql + ") ord ";
        new_sql += " order by ord.create_time2 desc";
        List pList = new ArrayList();
        pList.addAll(sqlParams);
        // pList.addAll(sqlParams);
        Page page = this.baseDaoSupport.queryForPage(new_sql, countSql, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();
                // data.put("out_tid", rs.getString("out_tid"));

                data.put("create_time2", rs.getTimestamp("create_time2"));
                data.put("receive_num", rs.getString("receive_num"));
                data.put("order_id", rs.getString("order_id"));
                data.put("order_status", rs.getString("order_status"));
                data.put("order_from_str", rs.getString("order_from_str"));
                data.put("phone_owner_name", rs.getString("phone_owner_name"));
                data.put("phone_num", rs.getString("phone_num"));
                data.put("order_city", rs.getString("order_city"));
                data.put("specificatio_nname", rs.getString("specificatio_nname"));
                data.put("pack_type", rs.getString("pack_type"));
                data.put("terminal_num", rs.getString("terminal_num"));
                data.put("receiver_name", rs.getString("receiver_name"));

                return data;
            }
        }, pList.toArray());
        return page;
    }

    @Override
    public Page queryOrderWhichRefundList(int pageNo, int pageSize, OrderQueryParams params) {

        String sql_total = SF.ecsordSql("ORDER_REFUND_HEAD_LIST");
        String sql = SF.ecsordSql("ORDER_REFUND_LIST");
        // String sql_his = SF.ecsordSql("ORDER_MONI_HIS_LIST");
        List sqlParams = new ArrayList();
        if (params != null) {

            // 创建时间开始
            if (!StringUtil.isEmpty(params.getCreate_start())) {
                sql += " and eoe.tid_time>=" + DBTUtil.to_sql_date("?", 2);
                // sql_his += " and eoe.tid_time>="+DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_start());
            }
            // 创建时间结束
            if (!StringUtil.isEmpty(params.getCreate_end())) {
                sql += " and eoe.tid_time<=" + DBTUtil.to_sql_date("?", 2);
                // sql_his += " and eoe.tid_time<="+DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_end());
            }
        }

        // 过滤作废单---zengxianlian
        sql += " and (eoe.order_if_cancel ='0' or eoe.order_if_cancel is null)";
        // sql_his += " and (eoe.order_if_cancel ='0' or eoe.order_if_cancel is null)";

        String new_sql = sql_total + " from(" + sql + ") ord ";
        String countSql = "select count(1) from( " + sql + ") ord ";
        new_sql += " order by ord.tid_time desc";
        List pList = new ArrayList();
        pList.addAll(sqlParams);
        // pList.addAll(sqlParams);
        Page page = this.baseDaoSupport.queryForPage(new_sql, countSql, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();
                // data.put("out_tid", rs.getString("out_tid"));

                data.put("order_from", rs.getString("order_from")); // 订单来源
                data.put("out_tid", rs.getString("out_tid")); // 订单编号
                data.put("status", rs.getString("status")); // 订单状态
                data.put("bss_account_time", rs.getString("bss_account_time")); // 订单受理日期
                data.put("refund_time", rs.getString("refund_time")); // 退款日期
                data.put("order_city", rs.getString("order_city")); // 地市
                data.put("goods_type", rs.getString("goods_type")); // 商品类型
                data.put("GoodsName", rs.getString("GoodsName")); // 商品名称
                data.put("phone_num", rs.getString("phone_num")); // 订购号码
                data.put("paymoney", rs.getString("paymoney")); // 实收
                data.put("busi_money", rs.getString("busi_money")); // 营业款
                data.put("brand_name", rs.getString("brand_name")); // 终端品牌
                data.put("specificatio_nname", rs.getString("specificatio_nname")); // 终端型号
                data.put("terminal_color", rs.getString("terminal_color")); // 终端颜色
                data.put("terminal_num", rs.getString("terminal_num")); // 终端串号
                data.put("is_old", rs.getString("is_old")); // 用户类型
                data.put("phone_owner_name", rs.getString("phone_owner_name")); // 联系人
                data.put("ship_tel", rs.getString("ship_tel")); // 联系人电话

                return data;
            }
        }, pList.toArray());
        return page;
    }

    /**
     * 实收款稽核报表
     */
    @Override
    public Page queryOrderActualRevenueAuditReport(int pageNo, int pageSize, OrderQueryParams params) {

        String sql_total = SF.ecsordSql("ORD_ACTUAL_REVENUE_AUDIT_REPORT_HEAD_LIST");
        String sql = SF.ecsordSql("ORD_ACTUAL_REVENUE_AUDIT_REPORT_REPORT_LIST");
        // String sql_his = SF.ecsordSql("ORDER_MONI_HIS_LIST");
        List sqlParams = new ArrayList();
        if (params != null) {

            // 创建时间开始
            if (!StringUtil.isEmpty(params.getCreate_start())) {
                sql += " and eoe.tid_time>=" + DBTUtil.to_sql_date("?", 2);
                // sql_his += " and eoe.tid_time>="+DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_start());
            }
            // 创建时间结束
            if (!StringUtil.isEmpty(params.getCreate_end())) {
                sql += " and eoe.tid_time<=" + DBTUtil.to_sql_date("?", 2);
                // sql_his += " and eoe.tid_time<="+DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_end());
            }
        }

        // 筛选订单状态为已开户之后的单子
        sql += "and eoe.flow_trace_id in('D','X','Y','F','H','L','J')";

        // 过滤作废单---zengxianlian
        sql += " and (eoe.order_if_cancel ='0' or eoe.order_if_cancel is null)" + " order by eost.d_begin_time desc";
        // sql_his += " and (eoe.order_if_cancel ='0' or eoe.order_if_cancel is null)";

        String new_sql = sql_total + " from(" + sql + ") ord ";
        String countSql = "select count(1) from( " + sql + ") ord ";
        // new_sql += " order by ord.d_begin_time desc";
        List pList = new ArrayList();
        pList.addAll(sqlParams);
        // pList.addAll(sqlParams);
        Page page = this.baseDaoSupport.queryForPage(new_sql, countSql, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();

                data.put("seq_num", rs.getString("seq_num")); // 序号
                data.put("d_begin_time", rs.getString("d_begin_time")); // 开户日期
                data.put("city", rs.getString("city")); // 地市
                data.put("phone_num", rs.getString("phone_num")); // 业务号码
                data.put("order_from", rs.getString("order_from")); // 订单来源
                data.put("out_tid", rs.getString("out_tid")); // 商城订单号
                data.put("payprovidername", rs.getString("payprovidername")); // 支付机构
                data.put("pay_time", rs.getString("pay_time")); // 支付日期
                data.put("refund_time", rs.getString("refund_time")); // 退款日期
                data.put("payplatformorderid", rs.getString("payplatformorderid")); // 支付公司订单号
                data.put("paytype", rs.getString("paytype")); // 支付类型
                data.put("account_paymoney", rs.getString("account_paymoney")); // 账户实收金额
                data.put("paymoney", rs.getString("paymoney")); // 订单实收金额
                data.put("paid_in_difference", rs.getString("paid_in_difference")); // 实收差额
                data.put("busi_money", rs.getString("busi_money")); // BSS金额
                data.put("ess_money", rs.getString("ess_money")); // CBSS金额
                data.put("discountrange", rs.getString("discountrange")); // 折扣金额
                data.put("logi_no", rs.getString("logi_no")); // 物流单号
                data.put("audit_receive_money_status", rs.getString("audit_receive_money_status")); // 资金稽核状态
                data.put("audit_comments", rs.getString("audit_comments")); // 稽核说明

                return data;
            }
        }, pList.toArray());
        return page;
    }

    /**
     * 订单领取报表
     */
    @Override
    public Page queryOrderReceiveReportList(int pageNo, int pageSize, OrderQueryParams params) {

        String sql_total = SF.ecsordSql("ORDER_RECEIVE_REPORT_HEAD_LIST");
        String sql = SF.ecsordSql("ORDER_RECEIVE_REPORT_LIST");
        // String sql_his = SF.ecsordSql("ORDER_MONI_HIS_LIST");
        List sqlParams = new ArrayList();
        if (params != null) {

            // 创建时间开始
            if (!StringUtil.isEmpty(params.getCreate_start())) {
                sql += " and eoe.tid_time>=" + DBTUtil.to_sql_date("?", 2);
                // sql_his += " and eoe.tid_time>="+DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_start());
            }
            // 创建时间结束
            if (!StringUtil.isEmpty(params.getCreate_end())) {
                sql += " and eoe.tid_time<=" + DBTUtil.to_sql_date("?", 2);
                // sql_his += " and eoe.tid_time<="+DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_end());
            }

            // 处理人选的工号ID
            if (!StringUtil.isEmpty(params.getUsername())) {
                sql += " and eol.lock_user_id = ? ";
                // sql_his += " and eoe.tid_time<="+DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getUsername());

            }

            // 订单领取排除发货，资料归档，订单归档
            sql += "  and eoe.flow_trace_id not in('H','L','J')";

        }

        // 过滤作废单---zengxianlian
        sql += " and (eoe.order_if_cancel ='0' or eoe.order_if_cancel is null)";
        // sql_his += " and (eoe.order_if_cancel ='0' or eoe.order_if_cancel is null)";

        String new_sql = sql_total + " from(" + sql + ") ord ";
        String countSql = "select count(1) from( " + sql + ") ord ";
        new_sql += " order by ord.tid_time desc";
        List pList = new ArrayList();
        pList.addAll(sqlParams);
        // pList.addAll(sqlParams);
        Page page = this.baseDaoSupport.queryForPage(new_sql, countSql, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();
                // data.put("out_tid", rs.getString("out_tid"));

                data.put("order_id", rs.getString("order_id"));
                data.put("order_from_str", rs.getString("order_from_str"));
                data.put("order_city", rs.getString("order_city"));
                data.put("order_goodsname", rs.getString("order_goodsname"));
                data.put("order_status", rs.getString("order_status"));
                data.put("order_receive", rs.getString("order_receive"));
                data.put("lock_user_name", rs.getString("lock_user_name"));
                data.put("phone_num", rs.getString("phone_num"));
                data.put("plan_title", rs.getString("plan_title"));
                data.put("tid_time", rs.getTimestamp("tid_time"));
                data.put("pay_status", rs.getString("pay_status"));
                data.put("pay_type", rs.getString("pay_type"));
                data.put("order_amount", rs.getString("order_amount"));
                data.put("paymoney", rs.getString("paymoney"));
                data.put("pack_type", rs.getString("pack_type"));
                data.put("brand_name", rs.getString("brand_name"));
                data.put("specificatio_nname", rs.getString("specificatio_nname"));
                data.put("terminal_num", rs.getString("terminal_num"));
                data.put("logi_no", rs.getString("logi_no"));

                return data;
            }
        }, pList.toArray());
        return page;
    }

    /**
     * 物流报表记录查询
     */
    @Override
    public Page queryDistributionReportList(int pageNo, int pageSize, OrderQueryParams params) {

        String sql_total = SF.ecsordSql("ORDER_DISTIBUTION_REPORT_HEAD_LIST");
        String sql = SF.ecsordSql("ORDER_DISTIBUTION_REPORT_LIST");
        // String sql_his = SF.ecsordSql("ORDER_MONI_HIS_LIST");
        List sqlParams = new ArrayList();
        if (params != null) {

            // 创建时间开始
            if (!StringUtil.isEmpty(params.getCreate_start())) {
                sql += " and eost.h_begin_time>=" + DBTUtil.to_sql_date("?", 2);
                // sql_his += " and eoe.tid_time>="+DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_start());
            }
            // 创建时间结束
            if (!StringUtil.isEmpty(params.getCreate_end())) {
                sql += " and eost.h_begin_time<=" + DBTUtil.to_sql_date("?", 2);
                // sql_his += " and eoe.tid_time<="+DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_end());
            }
        }

        // 过滤作废单---zengxianlian
        sql += " and (eoe.order_if_cancel ='0' or eoe.order_if_cancel is null)";
        // sql_his += " and (eoe.order_if_cancel ='0' or eoe.order_if_cancel is null)";

        // 按稽和完成的条件查询单子
        sql += " and eoe.flow_trace_id in('H','L','J')";

        // 排序
        sql += "order by  eost.f_end_time desc";

        String new_sql = sql_total + " from(" + sql + ") ord ";
        String countSql = "select count(1) from( " + sql + ") ord ";

        List pList = new ArrayList();
        pList.addAll(sqlParams);
        // pList.addAll(sqlParams);
        Page page = this.baseDaoSupport.queryForPage(new_sql, countSql, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();
                // data.put("out_tid", rs.getString("out_tid"));

                data.put("sequence_id", rs.getString("sequence_id"));
                data.put("out_order_id", rs.getString("out_order_id"));
                data.put("status", rs.getString("status"));
                data.put("opname", rs.getString("opname"));
                data.put("h_end_time", rs.getString("h_end_time"));
                data.put("goodsname", rs.getString("goodsname"));
                data.put("specificatio_nname", rs.getString("specificatio_nname"));
                data.put("terminal_num", rs.getString("terminal_num"));
                data.put("tid_time", rs.getTimestamp("tid_time"));
                data.put("lock_user_name", rs.getString("lock_user_name"));
                data.put("lock_time", rs.getTimestamp("lock_time"));
                data.put("is_old", rs.getString("is_old"));
                data.put("phone_owner_name", rs.getString("phone_owner_name"));
                data.put("phone_num", rs.getString("phone_num"));
                data.put("ship_name", rs.getString("ship_name"));
                data.put("ship_tel", rs.getString("ship_tel"));
                data.put("logi_no", rs.getString("logi_no"));
                data.put("receipt_no", rs.getString("receipt_no"));
                data.put("ship_addr", rs.getString("ship_addr"));

                data.put("shipping_company", rs.getString("shipping_company")); // 物流公司
                data.put("pay_method", rs.getString("pay_method")); // 付款方式
                data.put("fee", rs.getString("fee")); // 金额
                data.put("ship_batch", rs.getString("ship_batch")); // 发货批次
                data.put("goods_shipper", rs.getString("goods_shipper")); // 发货人
                data.put("goods_send_time", rs.getString("goods_send_time")); // 发货时间
                data.put("net_certi", rs.getString("net_certi")); // 入网证件
                data.put("accept_agree", rs.getString("accept_agree")); // 受理协议
                data.put("liang_agree", rs.getString("liang_agree")); // 靓号协议
                data.put("receive_num", rs.getString("receive_num")); // 存档编号
                data.put("j_op_id", rs.getString("j_op_id")); // 资料归档人
                data.put("archive_time", rs.getString("archive_time")); // 资料归档时间

                return data;
            }
        }, pList.toArray());
        return page;
    }

    @Override
    /**
     * 营业日报
     */
    public Page queryOrderBusinessReportList(int pageNo, int pageSize, OrderQueryParams params) {
        String sql_total = SF.ecsordSql("ORDER_BUSINESS_REPORT_HEAD_LIST");
        String sql = SF.ecsordSql("ORDER_BUSINESS_REPORT_LIST");
        // String sql_his = SF.ecsordSql("ORDER_MONI_HIS_LIST");
        List sqlParams = new ArrayList();
        if (params != null) {
            // 订单来源
            if (!StringUtil.isEmpty(params.getOrder_from()) && !"-1".equals(params.getOrder_from())) {
                sql += " and eoe.order_from in('" + params.getOrder_from().replace(",", "','") + "')";
                // sql_his += " and eoe.order_from in('"+params.getOrder_from().replace(",",
                // "','")+"')";
            }

            // 创建时间开始
            if (!StringUtil.isEmpty(params.getCreate_start())) {
                sql += " and eost.x_end_time>=" + DBTUtil.to_sql_date("?", 2);
                // sql_his += " and eoe.tid_time>="+DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_start());
            }
            // 创建时间结束
            if (!StringUtil.isEmpty(params.getCreate_end())) {
                sql += " and eost.x_end_time<=" + DBTUtil.to_sql_date("?", 2);
                // sql_his += " and eoe.tid_time<="+DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_end());
            }

            // 处理人选的工号ID
            if (!StringUtil.isEmpty(params.getUsername())) {
                sql += " and eost.x_op_id = ? ";
                // sql_his += " and eoe.tid_time<="+DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getUsername());

            }
            // 处理开户写卡完成后的单子显示到营业日报 is_account为需要开户的
            // sql+=" and eoe.is_account='1' and eoe.flow_trace_id in('Y','F','H','L','J')";
            sql += "  and eoe.flow_trace_id in('Y','F','H','L','J')";
            // 选择开户状态为1的已正常开户单子
            // sql+=" and eoie.account_status='1'";

        }

        // 过滤作废单---zengxianlian
        sql += " and (eoe.order_if_cancel ='0' or eoe.order_if_cancel is null)";
        // sql_his += " and (eoe.order_if_cancel ='0' or eoe.order_if_cancel is null)";

        String new_sql = sql_total + " from(" + sql + ") ord ";
        String countSql = "select count(1) from( " + sql + ") ord ";
        new_sql += " order by card_write_time desc";
        List pList = new ArrayList();
        pList.addAll(sqlParams);
        // pList.addAll(sqlParams);
        Page page = this.baseDaoSupport.queryForPage(new_sql, countSql, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();

                data.put("card_write_time", rs.getString("card_write_time")); // 写卡完成日期
                data.put("tid_time", rs.getString("tid_time")); // 日期
                data.put("order_operator", rs.getString("order_operator")); // 订单处理人
                data.put("order_type", rs.getString("order_type")); // 操作类型
                data.put("order_plat_type", rs.getString("order_plat_type")); // 数据来源
                data.put("order_from_str", rs.getString("order_from_str")); // 订单来源
                data.put("out_order_id", rs.getString("out_order_id")); // 订单编号
                data.put("order_type", rs.getString("order_type")); // 订单类型
                data.put("goods_type2", rs.getString("goods_type2")); // 商品类型
                data.put("goodsname", rs.getString("goodsname")); // 商品名称
                data.put("plan_title", rs.getString("plan_title")); // 套餐名称
                // data.put("pack_type",rs.getString("pack_type")); //单卡/合约机
                // data.put("goods_num",rs.getString("goods_num")); //单量
                data.put("is_old", rs.getString("is_old")); // 新用户/老用户
                data.put("first_payment", rs.getString("first_payment")); // 首月模式
                data.put("contract_month", rs.getString("contract_month")); // 合约期限
                data.put("order_city", rs.getString("order_city")); // 地市
                data.put("phone_num", rs.getString("phone_num")); // 订购号码
                data.put("phone_owner_name", rs.getString("phone_owner_name")); // 入网姓名
                data.put("certi_type", rs.getString("certi_type")); // 证件类型
                data.put("cert_card_num", rs.getString("cert_card_num")); // 证件号码
                data.put("ship_name", rs.getString("ship_name")); // 收件人
                data.put("ship_tel", rs.getString("ship_tel")); // 收件人电话
                data.put("ship_addr", rs.getString("ship_addr")); // 收货地址
                data.put("pay_type", rs.getString("pay_type")); // 支付方式
                data.put("order_model", rs.getString("order_model")); // 开户方式
                data.put("ship_type", rs.getString("ship_type")); // 配送方式
                data.put("shipping_company", rs.getString("shipping_company")); // 物流类型
                data.put("logi_no", rs.getString("logi_no")); // 物流单号
                data.put("terminal_num", rs.getString("terminal_num")); // 手机串号
                data.put("invoice_no", rs.getString("invoice_no")); // 发票编号
                data.put("cellphone_type", rs.getString("cellphone_type")); // 手机
                data.put("discountname", rs.getString("discountname")); // 优惠活动及赠品
                data.put("buyer_message", rs.getString("buyer_message")); // 买家备注或留言
                data.put("paymoney", rs.getString("paymoney")); // 商城实收
                data.put("busi_money", rs.getString("busi_money")); // 营业额
                data.put("pickup_self_address", rs.getString("pickup_self_address")); // 自提地址
                data.put("outlet_receiver", rs.getString("outlet_receiver")); // 营业厅收货人
                data.put("outlet_contact_number", rs.getString("outlet_contact_number")); // 营业厅联系电话
                data.put("outlet_pickup_selft", rs.getString("outlet_pickup_selft")); // 自提营业厅

                return data;
            }
        }, pList.toArray());
        return page;
    }

    @Override
    public List<Map> getOrderReceiveList(int pageNo, int pageSize, OrderQueryParams params) {
        // TODO Auto-generated method stub
        AdminUser user = ManagerUtils.getAdminUser();
        String user_id = user.getUserid();
        List sqlParams = new ArrayList();
        List sqlParams_count = new ArrayList();
        String sql = getOrderQuerySql(params, sqlParams, false);
        sql += " order by bss_time_type desc,o.create_time desc";
        List<Map> orderReceiveList = this.daoSupportForAsyCount.queryForListPage(sql, pageNo, pageSize,
                sqlParams.toArray());
        return orderReceiveList;
    }
    @Override
    /**
     * 宽带电商化报表
     */
    public Page queryOrderBroadbandCommerceList(int pageNo, int pageSize, OrderQueryParams params) {
        String sql_total = SF.ecsordSql("ORDER_BROADBAND_COMMERCE");
        
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

        // 规则编号
        String sql = cacheUtil.getConfigInfo("query_Order_Broadband_Commerce");
        String sql2 = cacheUtil.getConfigInfo("query_Order_Broadband_Commerce2");
        
        StringBuilder sql3=new StringBuilder();
        // String sql_his = SF.ecsordSql("ORDER_MONI_HIS_LIST");
        List sqlParams = new ArrayList();
        if (params != null) {
            // 订单来源
            if (!StringUtil.isEmpty(params.getOrder_from()) && !"-1".equals(params.getOrder_from())) {
                sql += " and a.order_from in('" + params.getOrder_from().replace(",", "','") + "')";
                sql2 += " and b.source_system_type in('" + params.getOrder_from().replace(",", "','") + "')";
                // sql_his += " and eoe.order_from in('"+params.getOrder_from().replace(",",
                // "','")+"')";
            }

            // 创建时间开始
            if (StringUtil.isEmpty(params.getCreate_start()) || (" 00:00:00").equals(params.getCreate_start())) {
                String create_start = "1980-01-01 00:00:00";
                sql += " and a.last_deal_time>= to_date('"+create_start+"','yyyy-mm-dd hh24:mi:ss')";
                sql2 += " and b.create_time>=to_date('"+create_start+"','yyyy-mm-dd hh24:mi:ss')";
                // sql_his += " and eoe.tid_time>="+DBTUtil.to_sql_date("?", 2);
            }else{
                sql += " and a.last_deal_time>=to_date('"+params.getCreate_start()+"','yyyy-mm-dd hh24:mi:ss')";
                sql2 += " and b.create_time>=to_date('"+params.getCreate_start()+"','yyyy-mm-dd hh24:mi:ss')";
                // sql_his += " and eoe.tid_time>="+DBTUtil.to_sql_date("?", 2);
            }
            // 创建时间结束
            if (StringUtil.isEmpty(params.getCreate_end()) || (" 23:59:59").equals(params.getCreate_end())) {
                String create_end ="2080-12-31 23:59:59";
                sql += " and a.last_deal_time<=to_date('"+create_end+"','yyyy-mm-dd hh24:mi:ss')";
                sql2 += " and b.create_time<=to_date('"+create_end+"','yyyy-mm-dd hh24:mi:ss')";
                // sql_his += " and eoe.tid_time<="+DBTUtil.to_sql_date("?", 2);
            }else{
                sql += " and a.last_deal_time<=to_date('"+params.getCreate_end()+"','yyyy-mm-dd hh24:mi:ss')";
                sql2 += " and b.create_time<=to_date('"+params.getCreate_end()+"','yyyy-mm-dd hh24:mi:ss')";
                // sql_his += " and eoe.tid_time<="+DBTUtil.to_sql_date("?", 2);
            }
        }

        // 过滤作废单---zengxianlian
        sql += " and (a.order_if_cancel ='0' or a.order_if_cancel is null)";
        // sql_his += " and (eoe.order_if_cancel ='0' or eoe.order_if_cancel is null)";
        sql3.append(sql);
        sql3.append(" union all ");
        sql3.append(sql2);
        String new_sql = sql_total + " from(" + sql3.toString() + ") ord ";
        String countSql = "select count(1) from( " + sql3.toString() + ") ord ";
        new_sql += "";
        List pList = new ArrayList();
        pList.addAll(sqlParams);
        // pList.addAll(sqlParams);
        Page page = this.baseDaoSupport.queryForPage(new_sql, countSql, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();

                data.put("order_city", rs.getString("order_city")); // 地市
                data.put("order_county", rs.getString("order_county")); // 县分
                data.put("order_from", rs.getString("order_from")); //  订单来源
                data.put("order_id", rs.getString("order_id")); // 内部订单号
                data.put("out_tid", rs.getString("out_tid")); // 外部订单号
                data.put("receipt_time", rs.getString("receipt_time")); // 收单时间
                data.put("ship_name", rs.getString("ship_name")); // 联系人
                data.put("ship_tel", rs.getString("ship_tel")); // 联系电话
                data.put("ship_addr", rs.getString("ship_addr")); // 联系地址
                data.put("goods_id", rs.getString("goods_id")); // 商品编码
                data.put("goodsname", rs.getString("goodsname")); // 商品名称
                 data.put("office_id",rs.getString("office_id")); //下单渠道编码
                 data.put("office_name",rs.getString("office_name")); //下单渠道名称
                data.put("deal_operator_num", rs.getString("deal_operator_num")); //下单人编码
                data.put("deal_operator_name", rs.getString("deal_operator_name")); // 下单人名称
                data.put("development_point_code", rs.getString("development_point_code")); //发展点编码
                data.put("development_point_name", rs.getString("development_point_name")); // 发展点名称
                data.put("development_code", rs.getString("development_code")); // 发展人编码
                data.put("development_name", rs.getString("development_name")); // 发展人名称
                data.put("status", rs.getString("status")); // 订单状态
                data.put("out_office_id", rs.getString("out_office_id")); // 受理点编码
                data.put("out_office_name", rs.getString("out_office_name")); // 受理点名称
                data.put("operator_id", rs.getString("operator_id")); // 受理人工号
                data.put("operator_name", rs.getString("operator_name")); // 受理人姓名
                data.put("pro_realfee", rs.getString("pro_realfee")); // 订单金额
                data.put("wxsg_status", rs.getString("wxsg_status")); // 外线施工状态
                data.put("complete_time", rs.getString("complete_time")); // 竣工时间
                data.put("dispatch_time", rs.getString("dispatch_time")); // 订单派单时间
                return data;
            }
        }, pList.toArray());
        return page;
    }

    /**
     * 获取老系统的历史订单数据
     */
    @Override
    public Page qryOrdHisList(int pageNo, int pageSize, OrderQueryParams params) {
        // TODO Auto-generated method stub
        String sql_total = SF.ecsordSql("ORDER_HIS_HEAD_LIST");
        String sql = SF.ecsordSql("ORDER_HIS_LIST");
        List sqlParams = new ArrayList();
        sql = sql + " where 1=1 ";
        if (params != null) {
            // 订单来源
            if (!StringUtil.isEmpty(params.getOrder_src()) && !"-1".equals(params.getOrder_src())) {
                sql += " and oo.order_src in('" + params.getOrder_src().replace(",", "','") + "')";
            }
            // 数据来源
            if (!StringUtil.isEmpty(params.getData_src()) && !"-1".equals(params.getData_src())) {
                sql += " and oo.data_src in('" + params.getData_src().replace(",", "','") + "')";
            }
            // 订单状态
            if (!StringUtil.isEmpty(params.getOrder_status()) && !"-1".equals(params.getOrder_status())) {
                sql += " and oo.order_status in('" + params.getOrder_status().replace(",", "','") + "')";
            }
            // 订单类型
            if (!StringUtil.isEmpty(params.getOrder_type()) && !"-1".equals(params.getOrder_type())) {
                sql += " and oo.order_type in('" + params.getOrder_type().replace(",", "','") + "')";
            }
            // 支付类型
            if (!StringUtil.isEmpty(params.getPay_type()) && !"-1".equals(params.getPay_type())) {
                sql += " and oo.pay_type in('" + params.getPay_type().replace(",", "','") + "')";
            }
            // 城市
            if (!StringUtil.isEmpty(params.getArea_id()) && !"-1".equals(params.getArea_id())) {
                sql += " and oo.area_id in('" + params.getArea_id().replace(",", "','") + "')";
            }

            // 配送
            if (!StringUtil.isEmpty(params.getExpress_type()) && !"-1".equals(params.getExpress_type())) {
                sql += " and oo.express_type in('" + params.getExpress_type().replace(",", "','") + "')";
            }

            // 订单id
            if (!StringUtil.isEmpty(params.getOut_tid()) && !"-1".equals(params.getOut_tid())) {
                sql += " and oo.out_order_id =?";
                sqlParams.add(params.getOut_tid());
            }

            // 订单来源
            if (!StringUtil.isEmpty(params.getBuyer()) && !"-1".equals(params.getBuyer())) {
                sql += " and oo.buyer =?";
                sqlParams.add(params.getBuyer());
            }

            // 订单来源
            if (!StringUtil.isEmpty(params.getSubs_svc_number()) && !"-1".equals(params.getSubs_svc_number())) {
                sql += " and oo.subs_svc_number =?";
                sqlParams.add(params.getSubs_svc_number());
            }

            // 时间开始
            if (!StringUtil.isEmpty(params.getCreate_start())) {
                sql += " and oo.deal_time>=" + DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_start());
            }
            // 时间结束
            if (!StringUtil.isEmpty(params.getCreate_end())) {
                sql += " and oo.deal_time<=" + DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_end());
            }
        }
        String new_sql = sql_total + "  from(" + sql + ") ord ";
        String countSql = "select count(1) from( " + sql + ") ord ";
        new_sql += " order by ord.deal_time desc";
        List pList = new ArrayList();
        pList.addAll(sqlParams);

        Page page = this.baseDaoSupport.queryForPage(new_sql, countSql, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();
                data.put("out_order_id", rs.getString("out_order_id"));
                data.put("order_src", rs.getString("order_src"));
                data.put("order_type", rs.getString("order_type"));
                data.put("goods_type", rs.getString("goods_type"));
                data.put("goods_name", rs.getString("goods_name"));
                data.put("order_status", rs.getString("order_status"));
                data.put("deal_time", rs.getTimestamp("deal_time"));
                data.put("is_take", rs.getString("is_take"));
                data.put("subs_svc_number", rs.getString("subs_svc_number"));
                data.put("main_prod_name", rs.getString("main_prod_name"));
                data.put("area_id", rs.getString("area_id"));
                data.put("pay_type", rs.getString("pay_type"));
                data.put("pay_status", rs.getString("pay_status"));
                data.put("delivery_type", rs.getString("delivery_type"));
                data.put("order_amount", rs.getLong("order_amount") / 1000);
                data.put("termial_model", rs.getString("termial_model"));
                data.put("buyer", rs.getString("buyer"));
                data.put("buyer_phone", rs.getString("buyer_phone"));
                data.put("data_src", rs.getString("data_src"));
                data.put("to_address", rs.getString("to_address"));
                data.put("to_contactor", rs.getString("to_contactor"));
                data.put("order_remark", rs.getString("order_remark"));
                return data;
            }
        }, pList.toArray());
        return page;
    }

    @Override
    public void dealDealInfo(String order_id, String dealDesc) {
        // TODO Auto-generated method stub
        String sql1 = "update es_uni_order_ext a set a.order_remark = ?  where a.order_id in (select order_id from es_uni_order where out_order_id = ?)";
        String[] str = new String[] { dealDesc, order_id };
        this.baseDaoSupport.execute(sql1, str);
    }

    @Override
    public String getOrderInfoBySn(String orderSn, String iccId) {
        StringBuilder sql = new StringBuilder(
                "SELECT a.order_id FROM es_order a LEFT JOIN es_order_extvtl b ON a.order_id=b.order_id WHERE a.status <> 11 AND a.source_from=b.source_from AND a.source_from='"
                        + ManagerUtils.getSourceFrom() + "'");
        List<String> params = new ArrayList<String>();
        if (!StringUtils.isEmpty(orderSn)) {
            sql.append(" AND a.sn=?");
            params.add(orderSn);
        }
        if (!StringUtils.isEmpty(iccId)) {
            sql.append(" AND b.iccid=?");
            params.add(iccId);
        }
        String orderId = this.baseDaoSupport.queryForString(sql.toString(), params.toArray());
        return orderId;
    }

    // @Override
    // public List<Map> getOrderReceiveList(String num) {
    // // TODO Auto-generated method stub
    //// String select_sql = "select glu.lock_user_id,ea.realname,glu.count_order
    // from (select oe.lock_user_id lock_user_id,count(oe.lock_user_id) count_order
    // from ES_ORDER_EXT oe left join ES_ORDER eo ON oe.order_id = eo.order_id where
    // oe.source_from = '"+ManagerUtils.getSourceFrom()+"' and oe.lock_status = 1
    // group by oe.lock_user_id) glu left join es_adminuser ea on glu.lock_user_id =
    // ea.userid where ea.realname is not null";
    // String sql="select o.order_id,oe.flow_trace_id,(select t.bss_time_type from
    // es_order_items_ext t where t.order_id=o.order_id) bss_time_type from es_order
    // o,es_order_ext oe,es_order_lock ol "
    // + " where o.order_id=oe.order_id and oe.order_id=ol.order_id and
    // o.source_from=oe.source_from and o.source_from=
    // '"+ManagerUtils.getSourceFrom()+"' and o.status <> 11 and (oe.order_if_cancel
    // ='0' or oe.order_if_cancel is null) "
    // + " and not exists(select 1 from es_order_hide eoh where eoh.order_id =
    // oe.order_id) and not exists (select 1 from es_esearch_expinst
    // ei,es_esearch_specvalues es "
    // + " where ei.out_tid=oe.out_tid and ei.key_id = es.key_id and
    // ei.record_status = '0' and es.is_order_sys_view = 'N' ) and
    // (oe.is_archive='0' or oe.is_archive is null) and oe.visible_status='0' and
    // ol.lock_status = '1' and rownum <='"+num+"' order by
    // bss_time_type,o.create_time desc";
    // List<Map> res = new ArrayList();
    // res = this.baseDaoSupport.queryForList(sql, null);
    //
    //
    //
    //
    // return res;
    // }

    @Override
    public Page queryOrderForPageByBatchPre(int pageNo, int pageSize, OrderQueryParams params) {
        long start = System.currentTimeMillis();
        AdminUser user = ManagerUtils.getAdminUser();
        String user_id = user.getUserid();
        List sqlParams = new ArrayList();
        List sqlCountParams = new ArrayList();
        String sql = getBatchPreOrderQuerySql(params, sqlParams, false);
        String countSql = "select count(1) from (" + getBatchPreOrderQuerySql(params, sqlCountParams, true) + ") cord";
        sql += " order by handle_flag desc,t.tid_time desc";
        Page page = this.daoSupportForAsyCount.queryForCPage(sql, pageNo, pageSize, OrderBatchPreModel.class, countSql,
                sqlParams.toArray());
        long end = System.currentTimeMillis();
        logger.info(sql);
        logger.info("订单列表查询时间========>>>>>" + (end - start));
        if (null == page)
            return page;
        List<OrderBatchPreModel> list = page.getResult();
        if (null == list || list.size() == 0)
            return page;
        List<AutoOrderTree> tree = new ArrayList<AutoOrderTree>();
        for (OrderBatchPreModel model : list) {
            AutoOrderTree ot = new AutoOrderTree();
            String cert_cart_num = CommonDataFactory.getInstance().getAttrFieldValue(model.getOrder_id(),
                    AttrConsts.CERT_CARD_NUM);
            String cert_address = CommonDataFactory.getInstance().getAttrFieldValue(model.getOrder_id(),
                    AttrConsts.CERT_ADDRESS);
            ot.setOut_tid(model.getOut_tid());
            ot.setOrder_id(model.getOrder_id());
            ot.setLock_user_id(model.getLock_user_id());
            ot.setLock_status(model.getLock_status());
            ot.setLock_user_name(model.getLock_user_name());
            ot.setContact_addr(model.getContact_addr());
            ot.setGoods_name(model.getGoods_name());
            ot.setShip_name(model.getShip_name());
            String sql_excption ="select distinct c.result_msg from (select erel.result_msg,erel.obj_id,erel.source_from, row_number() over(partition by erel.result_msg order by erel.create_time desc) rn "+
               "from es_rule_exe_log erel   where erel.plan_id = '100' "+
               " and erel.exe_result = '-1' and erel.obj_id='"+model.getOrder_id()+"') c where rn = 1 and c.source_from='"+ManagerUtils.getSourceFrom()+"'";
           
            List<Map> result_msg = this.baseDaoSupport.queryForList(sql_excption);
            if(result_msg.size()>0){
            	ot.setException_desc(result_msg.get(0).get("result_msg")+"");
            }
            ot.setShip_tel(model.getShip_tel());
            ot.setHandle_flag(model.getHandle_flag());
            ot.setCert_card_num(cert_cart_num);
            ot.setCert_address(cert_address);
            if (EcsOrderConsts.LOCK_STATUS.equals(ot.getLock_status())) {
                if (user_id.equals(ot.getLock_user_id())) {
                    ot.setLock_clazz("unlock");
                } else {
                    ot.setLock_clazz("otherlock");
                }
            } else {
                ot.setLock_clazz("lock");
            }
            tree.add(ot);
        }
        long end2 = System.currentTimeMillis();
        logger.info("获取订单树数据时间========>>>>>" + (end2 - end));
        page.setResult(tree);
        return page;
    }

    public String getBatchPreOrderQuerySql(OrderQueryParams params, List sqlParams, boolean is_count) {
        StringBuffer sqlBuff = new StringBuffer();
        AdminUser user = ManagerUtils.getAdminUser();
        String user_id = user.getUserid();
        if (is_count) {
            sqlBuff.append("select 1 ");
        } else {
            sqlBuff.append("select t.out_tid,t.order_id,l.lock_user_id,l.lock_status,");
            sqlBuff.append(" l.lock_user_name,d.ship_addr contact_addr,d.ship_tel ,d.ship_name,g.name goods_name ");
            // 查询批量预处理状态(0 成功 非0 不成功)
            sqlBuff.append(
                    ",(select count(1) from es_order_handle_logs hl where hl.order_id=t.order_id and hl.handler_type='"
                            + ZjEcsOrderConsts.BATCH_PREHANDLE_EXP + "' and hl.flow_trace_id='B') handle_flag");
        }
        sqlBuff.append(
                " from es_order_ext t left join es_order_lock l on t.order_id=l.order_id and t.flow_trace_id=l.tache_code ");
        sqlBuff.append(" left join es_order_items g on g.order_id = t.order_id ");
        sqlBuff.append(" left join es_delivery d on d.order_id = t.order_id");
        sqlBuff.append(" left join es_order eo on eo.order_id=t.order_id");// 添加es_order表用于退单状态判断
        sqlBuff.append(
                " where t.source_from = ? and t.Flow_Trace_Id = 'B' and t.abnormal_status = '0' and t.refund_deal_type='01'");// 添加es_order表status设置单子类型为订单生成和待审核
        sqlParams.add(ManagerUtils.getSourceFrom());
        sqlBuff.append(" and not exists (select 1 from es_order_hide h where h.order_id = t.order_id)");
        /*
         * int founder = user.getFounder(); if(founder != 1){
         * sqlBuff.append(" and l.lock_user_id = ? and l.lock_status = '1' ");
         * sqlParams.add(user_id); }
         */
        // 数据权限，自己锁定的并且有环节权限的
        if (user.getFounder() != 1) {
            String tacheAuth = user.getTacheAuth();// 环节权限'B','',''
            String lockUserAuth = user.getLockUserAuth();// 锁定工号权限，可以查询工号下锁定的订单
            if (!StringUtils.isEmpty(tacheAuth)) {
                sqlBuff.append(" and t.flow_trace_Id in (" + tacheAuth + ") ");// 环节权限
            } else {
                sqlBuff.append(" and 1=2 ");
            }
            if (user.getFounder() != 2) {// 普通管理员不需要过滤锁定权限
                // 锁定工号权限过滤
                if (StringUtils.isEmpty(lockUserAuth)) {// 如果为空则只查询自己锁定的订单
                    sqlBuff.append(
                            " and exists (select 1 from es_order_lock eol where t.order_id = eol.order_id and eol.lock_status = '1' and eol.lock_user_id = '"
                                    + user_id + "' and eol.tache_code = t.Flow_Trace_Id )");// 当前用户锁定
                } else if (!StringUtils.isEmpty(lockUserAuth) && lockUserAuth.contains("'1'")) {// 如果锁定工号权限未全部，则查询所有工号锁定的订单
                    sqlBuff.append(
                            " and exists (select 1 from es_order_lock eol where t.order_id = eol.order_id and eol.lock_status = '1' and eol.tache_code = t.Flow_Trace_Id )");// 当前用户锁定
                } else {// 查询拥有锁定工号权限工号锁定的订单
                    lockUserAuth += ",'" + ManagerUtils.getAdminUser().getUserid() + "'";// 增加登录用户本身锁定
                    sqlBuff.append(
                            " and exists (select 1 from es_order_lock eol where t.order_id = eol.order_id and eol.lock_status = '1' and eol.lock_user_id in("
                                    + lockUserAuth + ") and eol.tache_code = t.Flow_Trace_Id )");// 当前用户锁定
                }
            }
        }
        // 下单开始时间
        if (StringUtils.isEmpty(params.getOrder_id()) && StringUtils.isEmpty(params.getOut_tid())
                && !StringUtil.isEmpty(params.getCreate_start())) {
            sqlBuff.append(" and t.tid_time >= " + DBTUtil.to_sql_date("?", 2));
            sqlParams.add(params.getCreate_start());
        }
        // 下单结束时间
        if (StringUtils.isEmpty(params.getOrder_id()) && StringUtils.isEmpty(params.getOut_tid())
                && !StringUtil.isEmpty(params.getCreate_end())) {
            sqlBuff.append(" and t.tid_time <=" + DBTUtil.to_sql_date("?", 2));
            sqlParams.add(params.getCreate_end());
        }
        // 商品
        if (StringUtils.isEmpty(params.getGoods_name())) {
            /*
             * sqlBuff.append(" and exists (select 1 from es_dc_batchpre_cfg d ");
             * sqlBuff.append(" where d.goods_id = g.goods_id)");
             */
        } else {
            /*
             * String arr[] = params.getGoods_id().split(",");
             * sqlBuff.append(" and g.goods_id in ("); for(int i = 0; i < arr.length; i++){
             * if(i == 0){ sqlBuff.append("?"); }else{ sqlBuff.append(",?"); }
             * sqlParams.add(arr[i]); } sqlBuff.append(")");
             */
            sqlBuff.append(" and g.name like ? ");
            sqlParams.add("%" + params.getGoods_name() + "%");
        }
        // 地市
        if (!StringUtils.isEmpty(params.getOrder_city_code())) {
            String arr[] = params.getOrder_city_code().split(",");
            sqlBuff.append(" and t.order_city_code in (");
            for (int i = 0; i < arr.length; i++) {
                if (i == 0) {
                    sqlBuff.append("?");
                } else {
                    sqlBuff.append(",?");
                }
                sqlParams.add(arr[i]);
            }
            sqlBuff.append(")");
        }
        // 外部订单
        if (!StringUtil.isEmpty(params.getOut_tid())) {
            sqlBuff.append(" and t.out_tid = ? ");
            sqlParams.add(params.getOut_tid());
        }
        // 内部订单
        if (!StringUtil.isEmpty(params.getOrder_id())) {
            sqlBuff.append(" and t.order_id = ? ");
            sqlParams.add(params.getOrder_id());
        }
        if (!StringUtil.isEmpty(params.getOrder_from()) && !"-1".equals(params.getOrder_from())) {
        	sqlBuff.append(" and t.order_from in('" + params.getOrder_from().replace(",", "','") + "')");
        }
        // 处理状态
        if (!StringUtil.isEmpty(params.getHandle_status())) {
            if ("0".equals(params.getHandle_status())) {// 未审核
                sqlBuff.append(" and not exists");
            } else {
                sqlBuff.append(" and exists");
            }
            sqlBuff.append(" (select 1 from es_order_handle_logs hl where hl.order_id=t.order_id and hl.handler_type='"
                    + ZjEcsOrderConsts.BATCH_PREHANDLE_EXP + "' and hl.flow_trace_id='B')");
        }

        return sqlBuff.toString();
    }

    @Override
    public List<Map> getEmsLogisticsInfoList() {
        // TODO Auto-generated method stub
        String sql = "select a.order_id  from es_delivery a ,es_order_ext b where a.order_id=b.order_id and a.sign_status='0' and (b.flow_trace_id= 'L' or b.flow_trace_id= 'J') and a.source_from=b.source_from and a.source_from='"
                + ManagerUtils.getSourceFrom() + "' ";
        List list = this.daoSupportForAsyCount.queryForLists(sql);
        Page pageList = this.daoSupportForAsyCount.queryForPage(sql, 1, 2, null);
        // queryForPage//分页查询
        return list;
    }

    /**
     * 检查是否当前锁定用户
     * 
     * @作者 MoChunrun
     * @创建日期 2014-11-11
     * @return
     */
    public String checkLockUser(String order_id, String query_type) {
        String msg = "";
        AdminUser user = ManagerUtils.getAdminUser();
        OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
        OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
        List<OrderLockBusiRequest> orderLockRequest = orderTree.getOrderLockBusiRequests();// 某些单子会出现锁定状态为null的情况
        String currTacheCode = orderExtBusiRequest.getFlow_trace_id();
        OrderLockBusiRequest orderLockBusiRequest = null;

        if (orderLockRequest == null) {
            return msg = "订单锁定状态请求为null，异常";
        } else {
            for (int i = 0; i < orderLockRequest.size(); i++) {
                if (!"bss_parallel".equals(query_type)) {
                    if (orderExtBusiRequest.getFlow_trace_id().equals(orderLockRequest.get(i).getTache_code())) {
                        orderLockBusiRequest = orderLockRequest.get(i);
                    }
                } else {
                    // 并行业务办理环节权限处理
                    if ("Y2".equals(orderLockRequest.get(i).getTache_code())) {
                        orderLockBusiRequest = orderLockRequest.get(i);
                        currTacheCode = "Y2";
                    }
                }
            }
        }
        if (orderLockBusiRequest == null
                || EcsOrderConsts.UNLOCK_STATUS.equals(orderLockBusiRequest.getLock_status())) {
            msg = "请先锁定订单";
        } else if (!user.getUserid().equals(orderLockBusiRequest.getLock_user_id())) {
            msg = "订单已经被其他用户锁定,不能进行操作";
        } else if (ManagerUtils.getAdminUser().getFounder() != 1
                && (StringUtils.isEmpty(ManagerUtils.getAdminUser().getTacheAuth())
                        || !ManagerUtils.getAdminUser().getTacheAuth().contains(currTacheCode))) {
            msg = "您没有订单当前环节的操作权限";
        }
        return msg;
    }

    public List<AopQueryDataVo> getOrdId() {
        String sql = " select order_id,out_tid from (select oe.order_id,oe.out_tid from es_order_aop_query oe,es_order_realname_info ori "
                + " where ori.later_active_flag='1' and oe.deal_status in ('0','3') "
                + " and nvl(oe.deal_num,0)<=6 and oe.order_id=ori.order_id and oe.source_from = '"
                + ManagerUtils.getSourceFrom() + "') where rownum<=500 ";
        List<AopQueryDataVo> list = this.baseDaoSupport.queryForList(sql, AopQueryDataVo.class, null);
        return list;
    }

    public void updateDealStatus(String order_id, String status) {
        String sql = "";
        if (!StringUtils.isEmpty(status)) {
            if (StringUtil.equals(status, "1")) {
                sql = "update es_order_aop_query set deal_status='" + status
                        + "',deal_num=nvl(deal_num,0)+1,deal_time=sysdate where order_id='" + order_id + "'";
            } else {
                sql = "update es_order_aop_query set deal_status='" + status + "' where order_id='" + order_id + "'";
            }
            this.baseDaoSupport.execute(sql, null);
        }

    }

    public Map getOrderDtl(String order_id) {
        String sql = " select a.order_id,adsl.total_fee as adsl_total_fee,gw.total_fee as gw_total_fee,oet.bss_refund_status,oet.group_code,oet.group_name, "
                + " oet.industry_source,adsl.adsl_account,adsl.adsl_number,adsl.adsl_addr,adsl.adsl_speed,adsl.adsl_grid, "
                + " adsl.user_kind,adsl.cust_building_id,gw.service_num as gw_service_num,gw.is_main,yxt.service_num as yxt_service_num,yxt.simid,yxt.sim_type "
                + " from es_order_ext a " + " left join es_order_zhwq_adsl adsl on a.order_id=adsl.order_id "
                + " left join es_order_zhwq_gw gw on a.order_id=gw.order_id "
                + " left join es_order_zhwq_yw yw on a.order_id=yw.order_id "
                + " left join es_order_zhwq_yxt yxt on a.order_id=yxt.order_id "
                + " left join es_order_extvtl oet on a.order_id=oet.order_id "
                + " left join es_attr_discount_info adi on adi.order_id=a.order_id " + " where  a.order_id ='"
                + order_id + "' and a.source_from = '" + ManagerUtils.getSourceFrom() + "' and rownum = '1'";
        Map map = this.baseDaoSupport.queryForMap(sql, null);
        return map;

    }

    @Override
    public Page emsOrderInfoQuery(int pageNo, int pageSize) {
        // TODO Auto-generated method stub
        String sql = "select a.order_id from es_delivery a left join es_order_ext b on a.order_id = b.order_id left join es_order c on c.order_id=a.order_id "
                + "where a.order_id=b.order_id and a.sign_status='0' and (b.flow_trace_id= 'L' or b.flow_trace_id= 'J') and a.source_from=b.source_from and a.source_from='"
                + ManagerUtils.getSourceFrom() + "' order by c.create_time ";
        Page pageList = this.daoSupportForAsyCount.queryForPage(sql, pageNo, pageSize, null);
        return pageList;
    }

    @Override
    public Page queryHeadquartersMallStaff(int pageNo, int pageSize, String staff_code) {

        List sqlParams = new ArrayList();
        String sql = "select t.staff_id,t.staff_code,t.staff_name,t.remark,t.message_code,t.cookie,t.status,t.password from ES_HEADQUARTERS_MALL_STAFF t where t.SOURCE_FROM = '"
                + ManagerUtils.getSourceFrom() + "'";
        if (!StringUtil.isEmpty(staff_code)) {
            sql += " and t.staff_code = ?";
            sqlParams.add(staff_code);
        }
        String countSql = "select count(1) from (" + sql + ") cord";

        return this.daoSupportForAsyCount.queryForCPage(sql, pageNo, pageSize, HeadquartersMallBusiRequest.class,
                countSql, sqlParams.toArray());
    }

    @Override
    public HeadquartersMallBusiRequest queryHeadquartersMallStaffDetail(String staff_code) {
        String sql = "select t.staff_id,t.staff_code,t.staff_name,t.remark,t.message_code,t.cookie,t.status,t.source_from,t.password from ES_HEADQUARTERS_MALL_STAFF t where t.SOURCE_FROM = '"
                + ManagerUtils.getSourceFrom() + "' and t.staff_code = ?";
        List sqlParams = new ArrayList();
        sqlParams.add(staff_code);
        return (HeadquartersMallBusiRequest) this.daoSupportForAsyCount.queryForObject(sql,
                HeadquartersMallBusiRequest.class, sqlParams.toArray());
    }

    @Override
    public void editHeadquartersMallStaff(HeadquartersMallBusiRequest params) {
        List<String> sqlParams = new ArrayList<String>();
        String sql = "update ES_HEADQUARTERS_MALL_STAFF t ";
        if (!StringUtil.isEmpty(params.getStaff_id())) {
            if (sql.indexOf("SET") != -1) {
                sql += " ,t.staff_id = ?";
            } else {
                sql += " SET t.staff_id = ?";
            }

            sqlParams.add(params.getStaff_id());
        }
        if (!StringUtil.isEmpty(params.getStaff_name())) {
            if (sql.indexOf("SET") != -1) {
                sql += " ,t.staff_name = ?";
            } else {
                sql += " SET t.staff_name = ?";
            }
            sqlParams.add(params.getStaff_name());
        }
        if (!StringUtil.isEmpty(params.getRemark())) {
            if (sql.indexOf("SET") != -1) {
                sql += " ,t.remark = ?";
            } else {
                sql += " SET t.remark = ?";
            }
            sqlParams.add(params.getRemark());
        }
        if (!StringUtil.isEmpty(params.getMessage_code())) {
            if (sql.indexOf("SET") != -1) {
                sql += " ,t.message_code = ?";
            } else {
                sql += " SET t.message_code = ?";
            }
            sqlParams.add(params.getMessage_code());
        }
        if (!StringUtil.isEmpty(params.getCookie())) {
            if (sql.indexOf("SET") != -1) {
                sql += " ,t.cookie = ?";
            } else {
                sql += " SET t.cookie = ?";
            }
            sqlParams.add(params.getCookie());
        }
        if (!StringUtil.isEmpty(params.getStatus())) {
            if (sql.indexOf("SET") != -1) {
                sql += " ,t.status = ?";
            } else {
                sql += " SET t.status = ?";
            }
            sqlParams.add(params.getStatus());
        }
        sql += " where t.staff_code = ?";
        sqlParams.add(params.getStaff_code());

        this.daoSupportForAsyCount.execute(sql, sqlParams.toArray());

    }

    public void deleteHeadquartersMallStaff(HeadquartersMallBusiRequest params) {
        String sql = "delete ES_HEADQUARTERS_MALL_STAFF t where t.staff_code = ?";
        List<String> sqlParams = new ArrayList<String>();
        sqlParams.add(params.getStaff_code());
        this.daoSupportForAsyCount.execute(sql, sqlParams.toArray());
    }

    public void addHeadquartersMallStaff(HeadquartersMallBusiRequest params) {
        String sql = "insert into ES_HEADQUARTERS_MALL_STAFF(STAFF_ID,STAFF_CODE,STAFF_NAME,REMARK,MESSAGE_CODE,COOKIE,SOURCE_FROM,STATUS,PASSWORD) "
                + "values(?,?,?,?,?,?,?,?,?)";
        List<String> sqlParams = new ArrayList<String>();
        sqlParams.add(params.getStaff_id());
        sqlParams.add(params.getStaff_code());
        sqlParams.add(params.getStaff_name());
        sqlParams.add(params.getRemark());
        sqlParams.add(params.getMessage_code());
        sqlParams.add(params.getCookie());
        sqlParams.add(ManagerUtils.getSourceFrom());
        sqlParams.add("0");
        sqlParams.add(params.getPassword());

        this.daoSupportForAsyCount.execute(sql, sqlParams.toArray());
    }

    public void editHeadquartersMallStaffStatus(String staff_code) {
        List sqlParams = new ArrayList<String>();
        String sql = "update ES_HEADQUARTERS_MALL_STAFF t set status = '0' where SOURCE_FROM = '"
                + ManagerUtils.getSourceFrom() + "'";
        this.daoSupportForAsyCount.execute(sql);
        String sql2 = "update ES_HEADQUARTERS_MALL_STAFF t set status = '1' where staff_code = ? and SOURCE_FROM = '"
                + ManagerUtils.getSourceFrom() + "'";
        sqlParams.add(staff_code);
        this.daoSupportForAsyCount.execute(sql2, sqlParams.toArray());

    }

    @Override
    public List<Map> getOPerId(String order_id, String tache_code) {
        try {
            String sql = " select t.oper_id,ad.username from es_order_outcall_log t "
                    + " left join es_adminuser ad on t.oper_id=ad.userid " + " where t.order_id='" + order_id
                    + "' and t.tache_code='" + tache_code + "' and t.source_from = '" + ManagerUtils.getSourceFrom()
                    + "'" + " order by t.oper_time desc ";
            List<Map> list = new ArrayList();
            return daoSupportForAsyCount.queryForListByMap(sql.toString(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    /**
     * 订单数据查询报表
     */
    public Page queryOrderDataBySearchList(int pageNo, int pageSize, OrderQueryParams params) {
        String sql_total = SF.ecsordSql("ORDER_DATA_SEARCH_TOTAL_LIST_NEW");
        String sql = SF.ecsordSql("ORDER_DATA_LIST_NEW");
        /*
         * String sql_total = SF.ecsordSql("ORDER_DATA_SEARCH_TOTAL_LIST"); String sql =
         * SF.ecsordSql("ORDER_DATA_LIST");
         */
        // String sql_his = SF.ecsordSql("ORDER_DATA_HIS_LIST");
        List sqlParams = new ArrayList();
        if (params != null) {

            // 外部编号
            if (!StringUtil.isEmpty(params.getOut_tid()) && !"-1".equals(params.getOut_tid())) {
                sql += " and eoe.out_tid = '" + params.getOut_tid().trim() + "'";
                // sqlParams.add(params.getOut_tid());
            }

            // 商品名称 eoevtl.GoodsName
            if (!StringUtil.isEmpty(params.getGoods_name()) && !"-1".equals(params.getGoods_name())) {
                sql += " and eoevtl.GoodsName like '%" + params.getGoods_name().trim() + "%'";
                // sqlParams.add(params.getGoods_name());
            }

            // 创建时间开始
            if (!StringUtil.isEmpty(params.getCreate_start())) {
                sql += " and eoe.tid_time>=" + DBTUtil.to_sql_date("?", 2);
                // sql_his += " and eoe.tid_time>="+DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_start());
            }
            // 创建时间结束
            if (!StringUtil.isEmpty(params.getCreate_end())) {
                sql += " and eoe.tid_time<=" + DBTUtil.to_sql_date("?", 2);
                // sql_his += " and eoe.tid_time<="+DBTUtil.to_sql_date("?", 2);
                sqlParams.add(params.getCreate_end());
            }

            // 开户号码
            if (!StringUtil.isEmpty(params.getPhone_num())) {
                sql += " and eoie.phone_num like '%" + params.getPhone_num().trim() + "%'";
                // sql_his += " and eoe.tid_time<="+DBTUtil.to_sql_date("?", 2);
                // sqlParams.add(params.getPhone_num());
            }

            // 开户人姓名

            if (!StringUtil.isEmpty(params.getPhone_owner_name()) && !"-1".equals(params.getPhone_owner_name())) {
                sql += " and eoevtl.phone_owner_name like '%" + params.getPhone_owner_name().trim() + "%'";
                // sqlParams.add(params.getGoods_name());
            }

            // 联系电话

            if (!StringUtil.isEmpty(params.getShip_mobile()) && !"-1".equals(params.getShip_mobile())) {
                sql += " and ed.ship_mobile like '%" + params.getShip_mobile().trim() + "%'";
            }

            // 处理人选
            if (!StringUtil.isEmpty(params.getLock_user_id()) && !"-1".equals(params.getLock_user_id())) {
                sql += " or eost.b_op_id = '" + params.getLock_user_id().trim() + "'";
                sql += " or eost.c_op_id = '" + params.getLock_user_id().trim() + "'";
                sql += " or eost.d_op_id = '" + params.getLock_user_id().trim() + "'";
                sql += " or eost.x_op_id = '" + params.getLock_user_id().trim() + "'";
                sql += " or eost.y_op_id = '" + params.getLock_user_id().trim() + "'";
                sql += " or eost.f_op_id = '" + params.getLock_user_id().trim() + "'";
                sql += " or eost.h_op_id = '" + params.getLock_user_id().trim() + "'";
                sql += " or eost.j_op_id = '" + params.getLock_user_id().trim() + "'";
                sql += " or eost.l_op_id = '" + params.getLock_user_id().trim() + "'";
            }
            // 数据来源
            if (!StringUtil.isEmpty(params.getData_from()) && !"-1".equals(params.getData_from())) {
                sql += " and eoe.plat_type in('" + params.getData_from().replace(",", "','") + "')";
            }

            // 订单来源
            if (!StringUtil.isEmpty(params.getOrder_from()) && !"-1".equals(params.getOrder_from())) {
                sql += " and eoe.order_from in('" + params.getOrder_from().replace(",", "','") + "')";
            }

            // 订单类型

            if (!StringUtil.isEmpty(params.getOrder_type()) && !"-1".equals(params.getOrder_type())) {
                sql += " and eo.order_type in('" + params.getOrder_type().replace(",", "','") + "')";
            }

            // 订单环节
            if (!StringUtil.isEmpty(params.getDcmodeOrderStatus_id())
                    && !"-1".equals(params.getDcmodeOrderStatus_id())) {
                sql += " and eo.status in ('" + params.getDcmodeOrderStatus_id().replace(",", "','") + "') ";
            }

            // 新老用户
            if (!StringUtil.isEmpty(params.getIs_old()) && !"-1".equals(params.getIs_old())) {
                sql += " and eoipe.is_old = '" + params.getIs_old().trim() + "'";
            }

            // 配送方式
            if (!StringUtil.isEmpty(params.getShipping_id()) && !"-1".equals(params.getShipping_id())) {
                sql += " and eo.shipping_type in('" + params.getShipping_id().replace(",", "','") + "')";
            }

            // 支付方式
            if (!StringUtil.isEmpty(params.getPayment_id()) && !"-1".equals(params.getPayment_id())) {
                sql += " and exists(select 1  from es_payment_logs plg where plg.order_id=eo.order_id and plg.source_from=eo.source_from and plg.pay_method in('"
                        + params.getPayment_id().replace(",", "','") + "'))";
            }

            // 商品类型
            if (!StringUtil.isEmpty(params.getDcmodeGoodsType_id()) && !"-1".equals(params.getDcmodeGoodsType_id())) {
                sql += " and eoie.goods_type in('" + params.getDcmodeGoodsType_id().replace(",", "','") + "')";
            }

            // 活动类型
            if (!StringUtil.isEmpty(params.getDcmodeActivityType_id())
                    && !"-1".equals(params.getDcmodeActivityType_id())) {
                sql += " and eoevtl.ative_type in('" + params.getDcmodeActivityType_id().replace(",", "','") + "')";
            }

            // 订单归属地市【选择城市】
            if (!StringUtil.isEmpty(params.getOrder_city_code()) && !"-1".equals(params.getOrder_city_code())) {
                sql += " and eoe.order_city_code in('" + params.getOrder_city_code().replace(",", "','") + "')";
            }

        }

        // 过滤作废单---zengxianlian
        sql += " and (eoe.order_if_cancel ='0' or eoe.order_if_cancel is null)";

        // String new_sql = sql_total + " from( "+sql+" union all "+sql_his+") ord ";
        // String countSql = "select count(1) from( "+sql+" union all "+sql_his+") ord
        // ";
        String new_sql = sql_total + " from( " + sql + ") ord ";
        String countSql = "select count(1) from(" + sql + ") ord ";

        new_sql += " order by ord.tid_time desc";
        List pList = new ArrayList();
        pList.addAll(sqlParams);
        logger.info("订单数据查询:" + new_sql);
        Page page = this.baseDaoSupport.queryForPage(new_sql, countSql, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();

                data.put("out_tid", rs.getString("out_tid")); // 订单号
                data.put("tid_time", rs.getString("tid_time")); // 订单生成日期
                data.put("order_collect_time", rs.getString("order_collect_time")); // 订单领取时间
                data.put("time_difference", rs.getString("time_difference")); // 时间差(用户签收时间-订单领取时间)

                /*
                 * data.put("package_acp_date", rs.getString("package_acp_date")); // 用户签收时间 1
                 * 需要替换 data.put("shipping_time", rs.getString("shipping_time")); // 发货时间 1 需要替换
                 * data.put("contact_addr", rs.getString("contact_addr")); // 收货地址 1 需要替换
                 */

                data.put("ship_addr", rs.getString("ship_addr")); // 收货地址
                data.put("mainnumber", rs.getString("mainnumber")); // 主号码 新增
                data.put("ship_end_time", rs.getString("ship_end_time")); // 发货时间
                data.put("active_flag", rs.getString("active_flag")); // 激活状态 新增
                data.put("active_time", rs.getString("active_time")); // 激活时间 新增
                data.put("sign_status", rs.getString("sign_status")); // 签收状态 新增
                data.put("route_acceptime", rs.getString("route_acceptime")); // 签收时间

                data.put("order_from", rs.getString("order_from")); // 数据来源
                data.put("order_origin", rs.getString("order_origin")); // 商城来源
                data.put("spread_channel", rs.getString("spread_channel")); // 推广渠道
                data.put("development_name", rs.getString("development_name")); // 发展点名称
                data.put("development_code", rs.getString("development_code")); // 发展点编码
                data.put("handle_name", rs.getString("handle_name")); // 订单处理人
                data.put("handle_time", rs.getString("handle_time")); // 订单处理时间
                data.put("f_op_id", rs.getString("f_op_id")); // 订单稽核人
                data.put("f_end_time", rs.getString("f_end_time")); // 订单稽核时间

                data.put("logi_no", rs.getString("logi_no")); // 物流编号
                data.put("terminal_num", rs.getString("terminal_num")); // 终端串号
                data.put("refund_desc", rs.getString("refund_desc")); // 退单原因
                data.put("lan_code", rs.getString("lan_code")); // 地市
                data.put("goods_type", rs.getString("goods_type")); // 商品类型
                data.put("goodsName", rs.getString("goodsName")); // 商品名称
                data.put("invoice_no", rs.getString("invoice_no")); // 发票串号
                data.put("plan_title", rs.getString("plan_title")); // 套餐名称
                data.put("privilege_combo", rs.getString("privilege_combo")); // 特权包
                data.put("phone_num", rs.getString("phone_num")); // 用户号码
                data.put("zb_fuka_info", rs.getString("zb_fuka_info")); // 副卡
                data.put("terminal", rs.getString("terminal")); // 终端
                data.put("prod_cat_id", rs.getString("prod_cat_id")); // 合约类型
                data.put("contract_month", rs.getString("contract_month")); // 合约期限
                data.put("pro_realfee", rs.getString("pro_realfee")); // 实收(元)
                data.put("pay_status", rs.getString("pay_status")); // 支付状态
                data.put("paytype", rs.getString("paytype")); // 支付类型
                data.put("status", rs.getString("status")); // 订单状态
                data.put("account_status", rs.getString("account_status")); // 是否开户（是/否）
                data.put("is_old", rs.getString("is_old")); // 用户类型
                data.put("ship_name", rs.getString("ship_name")); // 联系人
                data.put("reference_phone", rs.getString("reference_phone")); // 联系人电话
                data.put("carry_person_mobile", rs.getString("carry_person_mobile")); // 其他联系电话
                data.put("audit_remark", rs.getString("audit_remark")); // 操作备注
                data.put("service_remarks", rs.getString("service_remarks")); // 订单备注
                data.put("devlopname", rs.getString("devlopname")); // 发展人
                data.put("sending_type", rs.getString("sending_type")); // 配送方式
                data.put("prize", rs.getString("prize")); // 奖品
                data.put("special_combo", rs.getString("special_combo")); // 特色包
                data.put("phone_owner_name", rs.getString("phone_owner_name")); // 入网人姓名

                return data;
            }
        }, pList.toArray());
        return page;
    }

    @Override
    public List<String> queryOrdAgrtImg(String orderId) {
        // TODO Auto-generated method stub
        String sql = "select a.file_path from es_order_file a where a.order_id = ? and a.file_type = 'jpg'";
        List sqlParams = new ArrayList();
        sqlParams.add(orderId);
        List<String> result = this.baseDaoSupport.queryForList(sql, orderId);
        return result;
    }

    /**
     * 更新订单外呼信息
     * 
     * @param order_id
     */
    public void updateEsOrderOutallLog(String order_id) {
        String userId = ManagerUtils.getAdminUser().getUserid();
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map params = new HashMap();
        params.put("oper_time", time.format(new Date()));
        params.put("is_finish", 1);
        params.put("oper_id", userId);
        Map whereMap = new HashMap();
        whereMap.put("order_id", order_id);
        whereMap.put("is_finish", 0);
        this.baseDaoSupport.update("es_order_outcall_log", params, whereMap);
    }

    /**
     * 查询外呼前订单状态
     * 
     * @param order_id
     */
    public String queryOrderStatusByorderId(String order_id) {
        StringBuffer sqlSb = new StringBuffer();
        sqlSb.append("select ").append("    t.ORDER_STATUS ").append("from ").append("  es_order_outcall_log t ")
                .append("where ").append("  t.order_id = ? ").append("  and t.IS_FINISH = 0 ")
                .append("   and t.source_from = ? ");
        String orderId = daoSupport.queryForString(sqlSb.toString(), order_id, ManagerUtils.getSourceFrom());
        return orderId;
    }

    /**
     * 顺丰物流查询
     */
    @Override
    public Page querySFlogiOrder(int pageNo, int pageSize, OrderQueryParams params) {
        String sql = " select  a.logi_no as mailno,a.order_id as orderid,e.out_tid,a.ship_type,a.ship_name,a.ship_addr,a.ship_tel,a.ship_mobile,a.create_time "
                + " from es_delivery a left join es_order_ext e on e.order_id = a.order_id where a.source_from = '"
                + ManagerUtils.getSourceFrom() + "' ";
        List list = new ArrayList();
        // if(params!=null){
        //
        // //创建时间开始
        // if(!StringUtil.isEmpty(params.getCreate_start())){
        // sql += " and a.create_time>="+DBTUtil.to_sql_date("?", 2);
        // //sql_his += " and eoe.tid_time>="+DBTUtil.to_sql_date("?", 2);
        // list.add(params.getCreate_start());
        // }
        // //创建时间结束
        // if(!StringUtil.isEmpty(params.getCreate_end())){
        // sql += " and a.create_time<="+DBTUtil.to_sql_date("?", 2);
        // //sql_his += " and eoe.tid_time<="+DBTUtil.to_sql_date("?", 2);
        // list.add(params.getCreate_end());
        // }
        // }
        // 取外部订单ID
        if (!StringUtil.isEmpty(params.getOut_tid())) {
            sql += " and e.out_tid = ? ";
            list.add(params.getOut_tid());
        }
        // 取内部订单ID
        if (!StringUtil.isEmpty(params.getOrder_id())) {
            sql += " and a.order_id = ? ";
            list.add(params.getOrder_id());
        }
        // 取开户人电话
        if (!StringUtil.isEmpty(params.getPhone_num())) {
            sql += " and a.ship_tel = ? ";
            list.add(params.getPhone_num());
        }
        // 取顺丰物流单号
        if (!StringUtil.isEmpty(params.getDelivery_id())) {
            sql += " and a.delivery_id = ? ";
            list.add(params.getDelivery_id());
        }

        logger.info("-------------------------------" + sql);
        Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, list.toArray());
        if (page.getTotalCount() == 0
                || StringUtil.isEmpty(Const.getStrValue((Map) page.getResult().get(0), "mailno"))) {
            ZteClient client = client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

            OrderSearchServiceRequest req = new OrderSearchServiceRequest();
            req.setOrderid(params.getOrder_id());
            String a = "<arg0><![CDATA[<?xml version='1.0' encoding='UTF-8'?><Request service= \"OrderSearchService\" lang=\"zh-CN\"><Head>5710928910</Head><Body><OrderSearch orderid=\""
                    + params.getOrder_id() + "\" /></Body></Request>]]></arg0>";
            String b = "";
            try {
                b = SFUtil.genVerifyCode(a, "rBCTv9YaVm7RRNpY5wHgGe6nlpPKuGys");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            req.setVerifyCode(b);
            OrderSearchServiceResponse resp = client.execute(req, OrderSearchServiceResponse.class);
            logger.info(resp);

            String mailno = resp.getBodyJson().getOrderResponse().getMailno();
            String orderid = resp.getBodyJson().getOrderResponse().getOrderid();

            List<SFBodyVO> list3 = new ArrayList<SFBodyVO>();
            List paramsList = new ArrayList();
            Map paramsMap = new HashMap();

            paramsMap.put("mailno", mailno);
            paramsMap.put("orderid", orderid);

            paramsList.add(paramsMap);
            page.setResult(paramsList);
        }
        logger.info(page.getData());
        logger.info(page.getResult());
        return page;

    }

    @Override
    public List<Map<String, String>> getCrawlerProc() {
        // TODO Auto-generated method stub
        String sql = "select distinct ip,port from es_crawler_proc where status='0'";
        return this.baseDaoSupport.queryForList(sql);
    }

    /**
     * 修改进程状态
     */
    @Override
    public void editThreadState(String ip, String port, String thread_name, String editType) {
        StringBuilder sqlBuilder = null;
        // 设置该ip,port，下所有线程为禁用状态
        if (StringUtil.equals(editType, "allStop")) {
            sqlBuilder = new StringBuilder(
                    "update es_crawler_proc a set a.status = '1' where a.ip = ? and a.port = ? ");
            this.baseDaoSupport.execute(sqlBuilder.toString(), ip, port);
        }
        // 根据ip,port,thread_name逐一启用线程
        else if (StringUtil.equals(editType, "oneStart")) {
            sqlBuilder = new StringBuilder(
                    "update es_crawler_proc a set a.status = '0' where a.ip = ? and a.port = ? and a.thread_name = ? ");
            this.baseDaoSupport.execute(sqlBuilder.toString(), ip, port, thread_name);
        }
        // 根据ip,port,thread_name逐一禁用线程
        else if (StringUtil.equals(editType, "oneStop")) {
            sqlBuilder = new StringBuilder(
                    "update es_crawler_proc a set a.status = '1' where a.ip = ? and a.port = ? and a.thread_name = ? ");
            this.baseDaoSupport.execute(sqlBuilder.toString(), ip, port, thread_name);
        }
    }

    /**
     * 获取爬虫线程列表
     */
    @Override
    public List<Map<String, String>> qryCrawlerProcList(String qryType) {
        String sql = "select distinct ip,port,PROC_NAME from es_crawler_proc where status='0' ";
        return this.baseDaoSupport.queryForList(sql);
    }

    /**
     * 查询写卡成功订单数量
     */
    @Override
    public Page qryQueueCardOrderNum(OrderQueryParams params, int pageNo, int pageSize) {
        String sql = " select " + " count(case when a.status='成功' then 1end) as success,"
                + " count(case when a.status='失败' then 1end) as fail," + " count(status) as num,"
                + " to_char(a.create_time,'yyyy-mm-dd') as create_time" + " from (select l.machine_no," + " t.order_id,"
                + " (case" + " when t.flow_trace_id in ('L','J','H') and" + " t.order_model = '06' then" + " '成功'"
                + " when t.flow_trace_id = 'B' and t.order_model = '02' then" + " '失败'" + " else" + " '其他'"
                + " end) as status," + " w.create_time" + " from es_order_ext t"
                + " left join es_queue_card_action_log l" + " on t.order_id = l.order_id"
                + " left join (select min(create_time) as create_time,order_id" + " from es_queue_write_card_his "
                + " group by order_id) w" + " on w.order_id = l.order_id" + " where 1=1";
        if (!StringUtils.isEmpty(params.getCreate_start())) {
            sql += " and trunc(w.create_time)>=to_date('" + params.getCreate_start() + "','yyyy-mm-dd') ";
        }
        if (!StringUtils.isEmpty(params.getCreate_end())) {
            sql += " and trunc(w.create_time)<=to_date('" + params.getCreate_end() + "','yyyy-mm-dd') ";
        }
        sql += " and t.order_id = l.order_id" + " and w.order_id = l.order_id" + " and l.source_from='"
                + ManagerUtils.getSourceFrom() + "'" + " and l.machine_no is not null"
                + " group by t.order_id, t.flow_trace_id, t.order_model, l.machine_no,w.create_time) a"
                + " group by to_char(a.create_time,'yyyy-mm-dd')" + " order by to_char(a.create_time,'yyyy-mm-dd')";

        String countSql = "select count(1) from (" + sql + ") cord";
        Page page = this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, WriteCardPercent.class, countSql, null);

        return page;
    }

    /**
     * 查询导入订单信息
     */
    @Override
    public Page queryInputOrder(OrderQueryParams params, int pageNo, int pageSize) {

        String sql = " select eimh.id, eimh.create_time, eimh.import_user_id, eimh.status"
                + " from es_order_input_mid_his eimh" + " where eimh.input_inst_id in (select max(input_inst_id)"
                + " from es_order_input_mid_his a" + " group by a.id)";

        if (params.getBat_id() != null && !params.getBat_id().equals("")) {
            sql = sql + " and eimh.id='" + params.getBat_id() + "'";
        }

        if (params.getLock_user_id() != null && !params.getLock_user_id().equals("")) {
            sql = sql + " and eimh.import_user_id='" + params.getLock_user_id() + "'";
        }

        sql = sql + " and eimh.source_from is not null order by eimh.create_time desc";
        return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, null);
    }

    /**
     * 查询改批次号所有导入订单
     */
    @Override
    public List queryAllInputOrderInfoByBatID(String bat_id) {
        String sql = "select eimh.out_order_id," + "eimh.acc_nbr," + " eimh.is_realname," + " eimh.special_busi_type,"
                + " eimh.cust_name," + " eimh.certi_type," + " eimh.certi_num," + " eimh.old_plan_title,"
                + " eimh.order_city_code," + " eimh.source_from," + " eimh.is_old," + " eimh.offer_eff_type,"
                + " eimh.pay_method," + " eimh.prod_offer_type," + " eimh.prod_offer_code," + " eimh.is_attached,"
                + " eimh.plan_title," + " eimh.contract_month," + " eimh.pay_money," + " eimh.ess_money,"
                + " eimh.ship_name," + " eimh.ship_phone," + " eimh.ship_addr," + " eimh.ship_city,"
                + " eimh.ship_county," + " eimh.logi_no," + " eimh.invoice_no," + " eimh.terminal_num,"
                + " eimh.buyer_message," + " eimh.iccid," + " eimh.service_remarks," + " eimh.status"
                + " from es_order_input_mid_his eimh " + " where eimh.id = '" + bat_id + "'"
                + " and eimh.source_from is not null";

        return this.baseDaoSupport.queryForList(sql, null);
    }

    public Page getCountyList(int pageNo, int pageSize, String county_name) {
        String sql = " select t.countyid,t.countyname from es_busicty t where 1=1  ";
        if (!StringUtils.isEmpty(county_name)) {
            sql += " and t.countyname like '%" + county_name + "%' ";
        }
        String countSql = " select count(1) from (" + sql + ") ";
        return this.baseDaoSupport.queryForPage(sql, countSql, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();
                data.put("countyid", rs.getString("countyid")); //
                data.put("countyname", rs.getString("countyname")); //
                return data;
            }
        }, null);
    }

    /**
     * 查询导入订单详细信息
     */
    @Override
    public Page queryInputOrderInfo(String bat_id, int pageNo, int pageSize) {
        String sql = "select eimh.out_order_id," + "eimh.acc_nbr," + " eimh.is_realname," + " eimh.special_busi_type,"
                + " eimh.cust_name," + " eimh.certi_type," + " eimh.certi_num," + " eimh.old_plan_title,"
                + " eimh.order_city_code," + " eimh.source_from," + " eimh.is_old," + " eimh.offer_eff_type,"
                + " eimh.pay_method," + " eimh.prod_offer_type," + " eimh.prod_offer_code," + " eimh.is_attached,"
                + " eimh.plan_title," + " eimh.contract_month," + " eimh.pay_money," + " eimh.ess_money,"
                + " eimh.ship_name," + " eimh.ship_phone," + " eimh.ship_addr," + " eimh.ship_city,"
                + " eimh.ship_county," + " eimh.logi_no," + " eimh.invoice_no," + " eimh.terminal_num,"
                + " eimh.buyer_message," + " eimh.iccid," + " eimh.service_remarks," + " eimh.status"
                + " from es_order_input_mid_his eimh " + " where eimh.id = '" + bat_id + "'"
                + " and eimh.source_from is not null";

        return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, null);
    }

    /**
     * 查询订单内部单号
     */
    public Map queryOrderID(String out_tid) {
        return this.baseDaoSupport
                .queryForMap("select order_id,exception_desc from es_order_ext where out_tid='" + out_tid + "'");
    }

    public String queryCertiType(String type) {
        return this.baseDaoSupport.queryForString(
                "select p.pname value_desc from es_dc_public_ext p where p.stype='certi_type' and p.pkey='" + type
                        + "'");
    }

    public String queryCityCode(String cityCode) {
        return this.baseDaoSupport.queryForString(
                "select city.LAN_NAME value_desc from es_lan city  where city.PROV_ID='330000' and city.lan_id = '"
                        + cityCode + "'");
    }

    public String queryRegionCode(String regionCode) {
        return this.baseDaoSupport.queryForString(
                "select region_name value_desc from es_common_region p where p.region_id='" + regionCode + "'");
    }

    public Page queryOrderReceiveList(int pageNo, int pageSize, OrderQueryParams params) {
        
        StringBuilder sbBuilder = new StringBuilder();
        sbBuilder.append(" select 'order' orderType, ");
        sbBuilder.append(" t.order_id, ");
        sbBuilder.append(" t.out_tid, ");
        sbBuilder.append(" t.tid_time, ");
        sbBuilder.append(" (select pname ");
        sbBuilder.append(" from es_dc_public_ext ");
        sbBuilder.append(" where stype = 'source_from' ");
        sbBuilder.append(" and t.order_from = pkey) as order_from_n, ");
        sbBuilder.append(" (select pname ");
        sbBuilder.append(" from es_dc_public_ext ");
        sbBuilder.append(" where stype = 'DIC_ORDER_NODE' ");
        sbBuilder.append(" and pkey = t.flow_trace_id) as flow_trace_n, ");
        sbBuilder.append(" e.goodsname as goods_name, ");
        sbBuilder.append(" to_char(o.paymoney, 'fm9990.9999999') as paymoney, ");
        sbBuilder.append(" ote.phone_num, ");
        sbBuilder.append(" (select field_value_desc ");
        sbBuilder.append(" from es_dc_public_dict_relation ta ");
        sbBuilder.append(" where stype = 'bss_area_code' ");
        sbBuilder.append(" and field_value = t.order_city_code) as order_city_code, ");
        sbBuilder.append(" (select distinct field_value_desc ");
        sbBuilder.append(" from es_dc_public_dict_relation ");
        sbBuilder.append(" where stype = 'county' ");
        sbBuilder.append(" and field_value = e.district_code) as order_county_code, ");
        sbBuilder.append(" '' as is_work_custom, ");
        sbBuilder.append(
                "      (select realname from es_adminuser  c where c.userid = eol.lock_user_id ) as deal_name,");
        //增加客户名称、联系电话、客户地址，去除外部编号，意向单业务号码、宽带类业务号码
        sbBuilder.append("m.uname as ship_name,m.mobile  as ship_tel, m.address as ship_addr,  ote.phone_num as  acc_nbr,g.price  as sell_price");
        sbBuilder.append(" from es_order_ext  t  ");
        sbBuilder.append(" left join es_order  o  on o.order_id=t.order_id  ");
        sbBuilder.append(" left join es_member m on o.member_id = m.member_id ");
        sbBuilder.append(" left join es_order_extvtl e  on t.order_id = e.order_id ");
        sbBuilder.append(" left join es_order_items_ext ote on t.order_id = ote.order_id ");
        sbBuilder.append(" left join es_goods  g  on o.goods_id=g.goods_id");
        //增加宽带类业务号码
        sbBuilder.append(" left join es_order_zhwq_adsl z on z.order_id=t.order_id and ( z.product_type !='TV' or z.product_type is null)");
        
        //增加联系电话查询条件
        sbBuilder.append("  left join es_delivery d on t.order_id= d.order_id ");
        
        sbBuilder.append(" left join es_order_lock eol on eol.order_id = t.order_id and t.flow_trace_id = eol.tache_code  where  t.source_from='")
                .append(ManagerUtils.getSourceFrom()).append("' ");
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String kg = cacheUtil.getConfigInfo("orderReceiveList_kg");
        if(!StringUtil.isEmpty(kg)&&"1".equals(kg)){//正式订单退单,外呼情况下 状态下不能领取，为避免出错，本次增加开关，若无问题可以去除开关  20190404 sgf
            sbBuilder.append(" and t.refund_deal_type !='02' and o.status !='11' ");
        }
        String query_sql1 = sbBuilder.toString();
        
        StringBuilder sbString = new StringBuilder();
        sbString.append(" union all select 'intent' orderType, i.order_id, ");
        sbString.append(" i.intent_order_id as out_tid,i.create_time as tid_time, ");
        sbString.append(" (select pname from es_dc_public_ext ");
        sbString.append(" where stype = 'source_from' and pkey = i.source_system_type) as order_from_n, ");
        sbString.append(" ewcni.node_name as flow_trace_n, i.goods_name, ");
        sbString.append(" '0' as paymoney, '' as phone_num, ");
        sbString.append(" (select field_value_desc from es_dc_public_dict_relation t where stype = 'bss_area_code' ");
        sbString.append(" and field_value = i.order_city_code) as order_city_code, ");
        sbString.append(" (select distinct other_field_value_desc from es_dc_public_dict_relation ");
        sbString.append(" where stype = 'county' and other_field_value = i.county_id) as order_county_code, ");
        sbString.append(" (select pname from es_dc_public_ext where stype='is_work_custom' and pkey = i.is_work_custom ) as is_work_custom, ");
        sbString.append(" decode(i.is_work_custom, '1', ewcni.dealer_name, i.lock_name) as deal_name,  ");
        
        //增加客户名称、联系电话、客户地址，去除外部编号，意向单业务号码
        sbString.append(" i.ship_name as ship_name, i.ship_tel  as ship_tel, i.ship_addr as ship_addr,i.acc_nbr as  acc_nbr, g.price as sell_price");
        sbString.append(" from es_order_intent i   ");
        sbString.append(" left join es_work_custom_node_ins ewcni on ewcni.order_id = i.order_id and ewcni.is_curr_step = '1' ");
        sbString.append(" and ewcni.is_done = '0'");
        sbString.append(" left join es_goods g    on i.goods_id = g.goods_id"); 
        sbString.append(" where (i.order_id not in (select order_id from es_work_order) or  ");
        sbString.append(" i.order_id in  (select order_id from es_work_order where status not in ('0','1'))) ");
        sbString.append(" and i.status not in ('03', '00')  and (i.order_type <> '001' or i.order_type is null) ");
        sbString.append(" and (i.is_online_order <> '1' or i.is_online_order is null) ");
        String query_sql2 = sbString.toString();
        String where_sql1 = "";
        String where_sql2 = "";
        if (params != null) {
        	if(!StringUtil.isEmpty(params.getShare_svc_num())){
        		where_sql1 += " and e.share_svc_num ='" + params.getShare_svc_num() + "'";
                where_sql2 += " and i.share_svc_num = '" + params.getShare_svc_num() + "'";
        	}
            if (!StringUtil.isEmpty(params.getFlow_id()) && !"-1".equals(params.getFlow_id())) {
                String flowId = params.getFlow_id();
                where_sql1 += " and t.flow_trace_id in ('" + flowId.replace(",", "','") + "') ";
            }
            if (!StringUtil.isEmpty(params.getOrder_from()) && !"-1".equals(params.getOrder_from())) {
                where_sql1 += " and t.order_from in('" + params.getOrder_from().replace(",", "','") + "')";
                where_sql2 += " and i.source_system_type in('" + params.getOrder_from().replace(",", "','") + "')";
            }
            // 创建时间 ES_ORDER_EXT->tid_time
            if (StringUtils.isEmpty(params.getOrder_id()) && StringUtils.isEmpty(params.getOut_tid())
                    && !StringUtil.isEmpty(params.getCreate_start())) {
                where_sql1 += " and t.tid_time>=to_date('" + params.getCreate_start() + "', 'yyyy-mm-dd hh24:mi:ss')";
                where_sql2 += " and i.create_time>=to_date('" + params.getCreate_start()
                        + "', 'yyyy-mm-dd hh24:mi:ss')";
            }
            // 创建时间 ES_ORDER_EXT->tid_time
            if (StringUtils.isEmpty(params.getOrder_id()) && StringUtils.isEmpty(params.getOut_tid())
                    && !StringUtil.isEmpty(params.getCreate_end())) {
                where_sql1 += " and t.tid_time<=to_date('" + params.getCreate_end() + "', 'yyyy-mm-dd hh24:mi:ss')";
                where_sql2 += " and i.create_time<=to_date('" + params.getCreate_end() + "', 'yyyy-mm-dd hh24:mi:ss')";
            }
            // 订单归属地市 es_order->order_city_code
            if (!StringUtil.isEmpty(params.getOrder_city_code()) && !"-1".equals(params.getOrder_city_code())) {
                where_sql1 += " and t.order_city_code in('" + params.getOrder_city_code().replace(",", "','") + "')";
                where_sql2 += " and i.order_city_code in('" + params.getOrder_city_code().replace(",", "','") + "')";
            }
            if (!StringUtils.isEmpty(params.getOrder_county_code()) && !"-1".equals(params.getOrder_county_code())) {
                where_sql1 += " and e.district_code ='" + params.getOrder_county_code() + "'";
                where_sql2 += " and i.order_county_code = '" + params.getOrder_county_code() + "'";
            }
            //证件上传状态：意向单没有证件上传，只查询正式单,避开意向单
            if (!StringUtils.isEmpty(params.getIf_Send_Photos())) {
                if("9".equals(params.getIf_Send_Photos())){
                	where_sql1 += " and (t.if_send_photos ='" + params.getIf_Send_Photos() + "'";
            		where_sql1 += " or t.if_send_photos is null )";
            	}else{
            		where_sql1 += " and t.if_send_photos ='" + params.getIf_Send_Photos() + "'";
                    where_sql2 += " and i.source_from ='intent'";
            	}
            }
            // 商品名称
            if (!StringUtil.isEmpty(params.getGoods_name()) && !"-1".equals(params.getGoods_name())) {
                where_sql1 += " and exists (select 1 from  es_order_extvtl ee where ee.order_id=t.order_id and ee.GoodsName like '%"
                        + params.getGoods_name().trim() + "%')";
                where_sql2 += " and exists (select 1 from  es_order_extvtl ee where ee.order_id=i.order_id and ee.GoodsName like '%"
                        + params.getGoods_name().trim() + "%')";
            }
            // 外单编号 ES_ORDER_EXT->ES_ORDER_EXT
            if (!StringUtil.isEmpty(params.getOut_tid())) {
                where_sql1 += " and t.out_tid = '" + params.getOut_tid().trim() + "'";
                where_sql2 += " and i.intent_order_id = '" + params.getOut_tid().trim() + "'";
            }
            // 内部单号 es_order->order_id
            if (!StringUtil.isEmpty(params.getOrder_id())) {
                where_sql1 += " and t.order_id = '" + params.getOrder_id().trim() + "'";
                where_sql2 += " and i.order_id = '" + params.getOrder_id().trim() + "'";
            }
            // 联系电话 
            if(!StringUtil.isEmpty(params.getShip_tel())) {
                where_sql1+=" and d.ship_tel='"+params.getShip_tel()+"'";
                where_sql2+=" and i.ship_tel='"+params.getShip_tel()+"'";
            }

            // 开户号码
            if (!StringUtil.isEmpty(params.getPhone_num())) {
                where_sql1 += " and exists(select 1 from es_order_items_ext oie where t.order_id=oie.order_id and t.source_from=oie.source_from and oie.phone_num='"
                        + params.getPhone_num() + "')";
                where_sql2 += " and i.acc_nbr = '" + params.getPhone_num() + "'";
            }
        }
        AdminUser user = ManagerUtils.getAdminUser();
        String sqlTeam = "select eutr.team_id from es_user_team_rel eutr left join es_user_team eut on eut.team_id=eutr.team_id  where eut.source_from='"+ManagerUtils.getSourceFrom()+"' and eut.state='1' and eutr.state='1' and eutr.userid='"+user.getUserid()+"'";
        List<Map> list = this.baseDaoSupport.queryForList(sqlTeam);
        String Team_id="";
        if(list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                Team_id  += list.get(i).get("team_id")+",";    
            }
        }
        if(!StringUtil.isEmpty(Team_id)){
            Team_id = Team_id.substring(0, Team_id.length()-1);
            Team_id = Team_id.replace(",", "','");
        }
        if (user.getFounder() != 1) {
            String org_id = ManagerUtils.getAdminUser().getOrg_id();// 获取组织编码
            String userInfo = ManagerUtils.getAdminUser().getUserid();// 员工个人
            String receiveUserAuth = user.getReceiveUserAuth();// 订单领取工号权限
            
            String isWorkCuctem = this.queryisWorkCustom(params.getOrder_id());
            //已经分配到个人的不查询出来
                where_sql1 += " and ((t.is_work_custom <> '1' or t.is_work_custom is null) or (t.is_work_custom = '1' and eol.dealer_type <> 'person'))";
            //获取团队ID
            List<Map> listTeamId=this.querTeamId(userInfo);
            //组织ID  团队ID拼接
            StringBuilder sb = new StringBuilder();
            for (Map m : listTeamId) {
                sb.append(",'"+m.get("team_id")+"'");
            }
            sb.append(",'"+org_id+"'");
            
            where_sql1 += " and exists (select 1 from es_order_lock eol where o.order_id = eol.order_id "
          		+ " and eol.lock_status = '1' "
          		+ " and eol.lock_user_id in ("
                   + receiveUserAuth + ""+sb+") and eol.tache_code = t.Flow_Trace_Id)";
          /*  
            where_sql2 += " and (i.lock_id in (" + receiveUserAuth + ") or ((ewcni.dealer_code='" + org_id
                    + "' or ewcni.dealer_code='" + userInfo + "' or ewcni.dealer_code in (" + receiveUserAuth
                    + ") or ewcni.dealer_code in ('"+Team_id+"')) and i.is_work_custom='1' and ewcni.is_lock_flag = '0') )";
            
            */
            String kgs = cacheUtil.getConfigInfo("orderReceiveList_intent_kg");
            if(!StringUtil.isEmpty(kgs)&&"1".equals(kgs)){
            	   where_sql2 += " and (i.lock_id in (" + receiveUserAuth + ") or ((ewcni.dealer_code='" + org_id
                           + "' or ewcni.dealer_code in (" + receiveUserAuth
                           + ") or ewcni.dealer_code in ('"+Team_id+"')) and i.is_work_custom='1' and ewcni.is_lock_flag = '0') )";
            }else{
            	  
                where_sql2 += " and (i.lock_id in (" + receiveUserAuth + ") or ((ewcni.dealer_code='" + org_id
                        + "' or ewcni.dealer_code='" + userInfo + "' or ewcni.dealer_code in (" + receiveUserAuth
                        + ") or ewcni.dealer_code in ('"+Team_id+"')) and i.is_work_custom='1' and ewcni.is_lock_flag = '0') )";
                
            }
        /*    where_sql1 += " and not exists (select 1 from es_order_lock eol where o.order_id = eol.order_id "
              		+ " and eol.lock_status = '1' "
              		+ " and eol.lock_user_id ='"+ userInfo +"' and eol.tache_code = t.Flow_Trace_Id)";
            
            where_sql2 += "and i.lock_id != '"+userInfo+"' ";*/
            
        }
        String query_sql = " select a.* from (" + query_sql1 + where_sql1 + query_sql2 + where_sql2
                + " ) a order by a.tid_time desc ";
        String countSql = " select count(1) from (" + query_sql1 + where_sql1 + query_sql2 + where_sql2 + " ) a  ";
        
        logger.info("订单领取-query_sql:"+query_sql);
        
        Page page = this.daoSupportForAsyCount.queryForCPage(query_sql, pageNo, pageSize, OrderReceiveVO.class,
                countSql, null);
        
        return page;

    }

    public Page getCountInfoByName(String name, int pageNo, int pageSize) {
        // select t.field_value org_code,t.field_value_desc org_name from
        // es_dc_public_dict_relation t where t.stype ='county' and t.field_value_desc
        // like '%义乌%';
        String sql = "select t.field_value org_code,t.field_value_desc org_name from  es_dc_public_dict_relation t where t.stype ='county' ";

        if (!StringUtils.isEmpty(name)) {
            sql += " and t.field_value_desc like '%" + name + "%'";
        }

        return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
    }
    
    @Override
    public List exportVplanOrderDtl(OrderQueryParams params) throws Exception {
        
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

        // 规则编号
        String sql1 = cacheUtil.getConfigInfo("VPLAN_DETAIL_ORDER_DTL_SQL_1");
        String sql2 = cacheUtil.getConfigInfo("VPLAN_DETAIL_ORDER_DTL_SQL_2");
        String sql=sql1+sql2;

        if (StringUtils.isEmpty(sql))
            throw new Exception("v计划统计报表明细导出查询SQL配置为空");

        
        List<Map> orderFromCfg = this.getDcSqlByDcName("V_PLAN");
        
        String startTime = params.getCreate_start();
        String endTime = params.getCreate_end();
        String activtyID=params.getOrder_from();

        String key1=" ";
        String key2=" ";
        String key3=" ";
        for (int i = 0; i < orderFromCfg.size(); i++) {
            if(orderFromCfg.get(i).get("value").equals(activtyID)){
                String key=(String) orderFromCfg.get(i).get("codea");
                String[] keys=key.split(",");
                key1=keys[0];
                key2=keys[1];
                key3=keys[2];
            }           
        }
        if(StringUtil.isEmpty(activtyID)){
            sql = sql.replace("and a.market_user_id = '{key1}'", " ");
            sql = sql.replace("and a.market_user_id = '{key2}'", " ");
            sql = sql.replace("and a.market_user_id = '{key3}'", " ");
            sql = sql.replace("and eoe.market_user_id = '{key3}'", " ");
        }
        if (StringUtil.isEmpty(startTime))
            startTime = "1980/01/01 00:00:00";

        if (StringUtil.isEmpty(endTime))
            endTime = "2080/12/31 23:59:59";
    
        sql = sql.replace("{startTime}", startTime);
        sql = sql.replace("{endTime}", endTime);
        sql = sql.replace("{key1}", key1);
        sql = sql.replace("{key2}", key2);
        sql = sql.replace("{key3}", key3);
        
        List ret = this.baseDaoSupport.queryForList(sql);

        return ret;
    }
    
    @Override
    public Page queryIntentOrderReportList(int pageNo, int pageSize, OrderQueryParams params) throws Exception {

        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

        // 规则编号
        String sql = "";
        String sql_count = "";
        
        StringBuilder orderFromBuilder = new StringBuilder();
        
        List<Map> orderFromCfg = this.getDcSqlByDcName("ORDER_FROM");
        
        for (Iterator<Map> it = orderFromCfg.iterator();it.hasNext();) {
            Map m = it.next();
            
            String key = String.valueOf(m.get("codea"));
            String value = String.valueOf(m.get("value"));
            
            if ("intent".equals(key)) {
                if(orderFromBuilder.length() == 0){
                    orderFromBuilder.append(value);
                }else{
                    orderFromBuilder.append("','"+value);
                }
            }
        }
        
        String sql_order_from = orderFromBuilder.toString();

        if ("1".equals(params.getReport_search_type())) {
            // 按地市统计SQL
            sql = cacheUtil.getConfigInfo("INTENT_ORDER_REPORT_CITY_SQL");
            sql_count = cacheUtil.getConfigInfo("INTENT_ORDER_REPORT_CITY_COUNT_SQL");
        } else {
            // 按县分统计SQL
            sql = cacheUtil.getConfigInfo("INTENT_ORDER_REPORT_COUNTY_SQL");
            sql_count = cacheUtil.getConfigInfo("INTENT_ORDER_REPORT_COUNTY_COUNT_SQL");
        }

        if (StringUtils.isEmpty(sql))
            throw new Exception("意向单统计报表查询SQL配置为空");

        if (StringUtils.isEmpty(sql_count))
            throw new Exception("意向单统计报表数量查询SQL配置为空");

        List sqlParams = new ArrayList();

        if (params == null)
            throw new Exception("意向单统计报表传入查询参数为空");

        String startTime = params.getCreate_start();
        String endTime = params.getCreate_end();

        if (StringUtil.isEmpty(startTime))
            startTime = "1980/01/01 00:00:00";

        if (StringUtil.isEmpty(endTime))
            endTime = "2080/12/31 23:59:59";
        String strOrderFrom = "";
        if (!StringUtil.isEmpty(params.getOrder_from())) {
            strOrderFrom = params.getOrder_from();
            String[] str = strOrderFrom.split(",");
            StringBuilder sb = new StringBuilder();
            for (String s : str) {
                sb.append("" + s + "','");
            }
            sql_order_from = sb.substring(0, sb.lastIndexOf(",") - 1);
        }

        sql = sql.replace("{startTime}", startTime);
        sql = sql.replace("{endTime}", endTime);
        sql = sql.replace("{orderFrom}", sql_order_from);
        
        AdminUser user = ManagerUtils.getAdminUser();
        
        if(!StringUtil.isEmpty(params.getOrder_city_code())){
            sql = sql.replace("{order_city_code}", " and a.order_city_code='"+params.getOrder_city_code()+"' ");
            sql = sql.replace("{order_city_code_main}", " and c.col1='"+params.getOrder_city_code()+"' ");
            
            sql_count = sql_count.replace("{order_city_code_main}", " and c.col1='"+params.getOrder_city_code()+"' ");
        }else if(!"1".equals(user.getPermission_level())){
            //zhaochen 20181106 不是省级工号，没有传入地市参数，需要加上地市条件
            String str = SqlUtil.getSqlInStr("a.order_city_code", user.getPermission_region(), false, true);
            sql = sql.replace("{order_city_code}", str);
            
            str = SqlUtil.getSqlInStr("c.col1", user.getPermission_region(), false, true);
            sql = sql.replace("{order_city_code_main}", str);
            
            sql_count = sql_count.replace("{order_city_code_main}", str);
        }else{
            sql = sql.replace("{order_city_code}", "");
            sql = sql.replace("{order_city_code_main}", "");
            
            sql_count = sql_count.replace("{order_city_code_main}", "");
        }

        List pList = new ArrayList();
        pList.addAll(sqlParams);

        Page page = null;

        logger.info("统记查询SQL" + sql);

        if ("1".equals(params.getReport_search_type())) {
            page = this.baseDaoSupport.queryForPage(sql, sql_count, pageNo, pageSize, new RowMapper() {
                public Object mapRow(ResultSet rs, int c) throws SQLException {
                    Map data = new HashMap();

                    data.put("city_code", rs.getString("city_code")); // 地市编码
                    data.put("city_name", rs.getString("city_name")); // 地市名称
                    data.put("total", rs.getString("total")); // 总量
                    data.put("undeal", rs.getString("undeal")); // 未处理
                    data.put("dealing", rs.getString("dealing")); // 处理中
                    data.put("done", rs.getString("done")); // 已完成
                    data.put("can", rs.getString("can")); // 退单

                    return data;
                }
            }, pList.toArray());
        } else {
            page = this.baseDaoSupport.queryForPage(sql, sql_count, pageNo, pageSize, new RowMapper() {
                public Object mapRow(ResultSet rs, int c) throws SQLException {
                    Map data = new HashMap();

                    data.put("city_code", rs.getString("city_code")); // 地市编码
                    data.put("city_name", rs.getString("city_name")); // 地市名称
                    data.put("county_code", rs.getString("county_code")); // 县分编码
                    data.put("county_name", rs.getString("county_name")); // 县分名称
                    data.put("total", rs.getString("total")); // 总量
                    data.put("undeal", rs.getString("undeal")); // 未处理
                    data.put("dealing", rs.getString("dealing")); // 处理中
                    data.put("done", rs.getString("done")); // 已完成
                    data.put("can", rs.getString("can")); // 退单

                    return data;
                }
            }, pList.toArray());  
        }

        return page;
    }
    
    @Override
    public Page queryVplanOrderReportList(int pageNo, int pageSize,
            OrderQueryParams params) throws Exception {
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String sql="";
        String sql_count="";
        
        String sql1= cacheUtil.getConfigInfo("VPLAN_ORDER_DTL_SQL1");
        String sql2= cacheUtil.getConfigInfo("VPLAN_ORDER_DTL_SQL2");
        
        sql=sql1+sql2;
        sql_count=cacheUtil.getConfigInfo("COUNT_VPLAN_ORDER_SQL");
        
        List<Map> orderFromCfg = this.getDcSqlByDcName("V_PLAN");
                
        String startTime = params.getCreate_start();
        String endTime = params.getCreate_end();
        String activtyID=params.getOrder_from();

        String key1=" ";
        String key2=" ";
        String key3=" ";
        for (int i = 0; i < orderFromCfg.size(); i++) {
            if(orderFromCfg.get(i).get("value").equals(activtyID)){
                String key=(String) orderFromCfg.get(i).get("codea");
                String[] keys=key.split(",");
                key1=keys[0];
                key2=keys[1];
                key3=keys[2];
            }           
        }
        if(StringUtil.isEmpty(activtyID)){
            sql = sql.replace("and t.market_user_id = '{key1}' ", " ");
            sql = sql.replace("and  t.market_user_id = '{key2}' ", " ");
            sql = sql.replace("eoe2.market_user_id = '{key3}'    and", " ");
            sql = sql.replace("t.market_user_id = '{key3}'  and", " ");
            sql = sql.replace("and a.market_user_id = '{key3}' ", " ");
        }
/*      if(StringUtil.isEmpty(key1)){
            sql = sql.replace("and t.market_user_id = '{key1}' ", " ");
        }
        if(StringUtil.isEmpty(key2)){
            sql = sql.replace("and  t.market_user_id = '{key2}' ", " ");
        }
        if(StringUtil.isEmpty(key3)){
            sql = sql.replace("eoe2.market_user_id = '{key3}'    and", " ");
            sql = sql.replace("t.market_user_id = '{key3}'  and", " ");
            sql = sql.replace("and a.market_user_id = '{key3}' ", " ");
        }*/
        if (StringUtil.isEmpty(startTime))
            startTime = "1980/01/01 00:00:00";

        if (StringUtil.isEmpty(endTime))
            endTime = "2080/12/31 23:59:59";
    
        sql = sql.replace("{startTime}", startTime);
        sql = sql.replace("{endTime}", endTime);
        sql = sql.replace("{key1}", key1);
        sql = sql.replace("{key2}", key2);
        sql = sql.replace("{key3}", key3);
        
        List sqlParams = new ArrayList();
        List pList = new ArrayList();
        pList.addAll(sqlParams);
                
        Page page = null;
        
        page = this.baseDaoSupport.queryForPage(sql, sql_count, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();

                data.put("city", rs.getString("city")); 
                data.put("counts1", rs.getString("counts1"));
                data.put("counts2", rs.getString("counts2"));
                data.put("counts3", rs.getString("counts3"));
                data.put("counts4", rs.getString("counts4"));
                data.put("counts5", rs.getString("counts5"));
                data.put("counts6", rs.getString("counts6"));
                data.put("counts7", rs.getString("counts7"));
                data.put("counts8", rs.getString("counts8"));
                data.put("counts9", rs.getString("counts9"));
                data.put("counts10", rs.getString("counts10")); 
                data.put("counts11", rs.getString("counts11")); 
                data.put("counts12", rs.getString("counts12")); 
                data.put("counts13", rs.getString("counts13")); 
                data.put("counts14", rs.getString("counts14")); 
                data.put("counts15", rs.getString("counts15")); 
                data.put("counts16", rs.getString("counts16")); 
                data.put("counts17", rs.getString("counts17")); 
                data.put("counts18", rs.getString("counts18")); 
                data.put("counts19", rs.getString("counts19")); 
                data.put("counts20", rs.getString("counts20")); 
                data.put("counts21", rs.getString("counts21")); 
               // data.put("counts22", rs.getString("counts22")); 
                data.put("counts23", rs.getString("counts23")); 
                data.put("counts24", rs.getString("counts24")); 
                data.put("counts25", rs.getString("counts25")); 

                return data;
            }
        }, pList.toArray());
        
        return page;
    }
    
    @Override
	public Page queryOrderQuantityReportListByName(int pageNo, int pageSize,
			OrderQueryParams params) throws Exception {
    	ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	 	String sql="";
        String sql_count="";
        
        StringBuilder orderFromBuilder = new StringBuilder();
        List<Map> orderFromCfg = this.getDcSqlByDcName("ORDER_FROM");
        
        for (Iterator<Map> it = orderFromCfg.iterator();it.hasNext();) {
            Map m = it.next();
            
            String key = String.valueOf(m.get("codea"));
            String value = String.valueOf(m.get("value"));
            
            if ("intent".equals(key)) {
                if(orderFromBuilder.length() == 0){
                    orderFromBuilder.append(value);
                }else{
                    orderFromBuilder.append("','"+value);
                }
            }
        }
        
        String sql_order_from = orderFromBuilder.toString();
        
        sql= cacheUtil.getConfigInfo("INTENT_ORDER_QUANTITY_BY_NAME_SQL");
        sql_count=cacheUtil.getConfigInfo("INTENT_ORDER_QUANTITY_BY_NAME_COUNT_SQL");

        if (StringUtils.isEmpty(sql))
            throw new Exception("意向单各人员各环节订单量查询BY_NAME SQL配置为空");

        if (StringUtils.isEmpty(sql_count))
            throw new Exception("意向单各人员各环节订单量查询BY_NAME总条数 SQL配置为空");
        
        if(!StringUtils.isEmpty(params.getUsername())){
        	if(!StringUtils.isEmpty(params.getOrder_role())){
        		if(params.getOrder_role().equals("03124")){
                	
                	sql = sql.replace("{username}", "ewo.operator_name = '"+params.getUsername()+"'");
                    sql_count = sql_count.replace("{username}", "ewo.operator_name = '"+params.getUsername()+"'");
                }else{
                	
                	sql = sql.replace("{username}", "eoi.lock_name = '"+params.getUsername()+"'");
                    sql_count = sql_count.replace("{username}", "eoi.lock_name = '"+params.getUsername()+"'");
                }
        		
        	}else{
            	
        		sql = sql.replace("{username}", "(eoi.lock_name = '"+params.getUsername()+"'"
            			+" or ewo.operator_name = '"+params.getUsername()+"')");
                sql_count = sql_count.replace("{username}", "(eoi.lock_name = '"+params.getUsername()+"'"
            			+" or ewo.operator_name = '"+params.getUsername()+"')");
            }
        	
        }else{
        	throw new Exception("传入查询人员为空");
        }
        if(!StringUtils.isEmpty(params.getPhone_num())){
        	if(!StringUtils.isEmpty(params.getOrder_role())){
        		if(params.getOrder_role().equals("03124")){
                	
                	sql = sql.replace("{phone_num}", "and ewo.operator_num = '"+params.getPhone_num()+"'");
                    sql_count = sql_count.replace("{phone_num}", "and ewo.operator_num = '"+params.getPhone_num()+"'");
                }else{
                	
                	sql = sql.replace("{phone_num}", "and ad.phone_num = '"+params.getPhone_num()+"'");
                    sql_count = sql_count.replace("{phone_num}", "and ad.phone_num = '"+params.getPhone_num()+"'");
                }
        	}else{
            	
        		sql = sql.replace("{phone_num}", "and (ad.phone_num = '"+params.getPhone_num()+"'"
            			+" or ewo.operator_num = '"+params.getPhone_num()+"')");
                sql_count = sql_count.replace("{phone_num}", "and (ad.phone_num = '"+params.getPhone_num()+"'"
            			+" or ewo.operator_num = '"+params.getPhone_num()+"')");
            }
        	
        }else {
        	sql = sql.replace("{phone_num}", "");
            sql_count = sql_count.replace("{phone_num}", "");
        }
   
        if(StringUtils.isEmpty(params.getOrder_city_code())){
       	 	sql = sql.replace("and eoi.order_city_code='{city_code}'", "");
            sql_count = sql_count.replace("and eoi.order_city_code='{city_code}'", "");
        }else{
        	sql = sql.replace("{city_code}", params.getOrder_city_code());
            sql_count = sql_count.replace("{city_code}", params.getOrder_city_code());
        }
        
        if(StringUtils.isEmpty(params.getOrder_county_code())){
          	 sql = sql.replace("and eoi.order_county_code='{county_code}'", "");
             sql_count = sql_count.replace("and eoi.order_county_code='{county_code}'", "");
        }else{
          	 sql = sql.replace("{county_code}", params.getOrder_county_code());
             sql_count = sql_count.replace("{county_code}", params.getOrder_county_code());
        }
        
        if(!StringUtils.isEmpty(params.getOrder_from())){
            String strOrderFrom = "";
            if (!StringUtil.isEmpty(params.getOrder_from())) {
                strOrderFrom = params.getOrder_from();
                String[] str = strOrderFrom.split(",");
                StringBuilder sb = new StringBuilder();
                for (String s : str) {
                    sb.append("" + s + "','");
                }
                sql_order_from = sb.substring(0, sb.lastIndexOf(",") - 1);
            }
       	 	sql = sql.replace("{orderFrom}", sql_order_from);
       	 	sql_count = sql_count.replace("{orderFrom}", sql_order_from);
        }else{
	       	 sql = sql.replace("{orderFrom}",sql_order_from);
	         sql_count = sql_count.replace("{orderFrom}", sql_order_from);
        }
        
        if(StringUtils.isEmpty(params.getCreate_start())){
       	 	sql = sql.replace("and eoi.create_time>=to_date('{startTime}','yyyy/MM/dd hh24:mi:ss')", "");
       	 	sql_count = sql_count.replace("and eoi.create_time>=to_date('{startTime}','yyyy/MM/dd hh24:mi:ss')", "");
        }else{
	       	 sql = sql.replace("{startTime}", params.getCreate_start());
	         sql_count = sql_count.replace("{startTime}", params.getCreate_start());
        }
        
        if(StringUtils.isEmpty(params.getCreate_end())){
       	 	sql = sql.replace("and eoi.create_time<=to_date('{endTime}','yyyy/MM/dd hh24:mi:ss')", "");
       	 	sql_count = sql_count.replace("and eoi.create_time<=to_date('{endTime}','yyyy/MM/dd hh24:mi:ss')", "");
        }else{
	       	 sql = sql.replace("{endTime}", params.getCreate_end());
	         sql_count = sql_count.replace("{endTime}", params.getCreate_end());
        }
    
        List sqlParams = new ArrayList();
        List pList = new ArrayList();
        pList.addAll(sqlParams);
                
        Page page = null;
        
        logger.info("统记报表SQL" + sql);
        
        page = this.baseDaoSupport.queryForPage(sql, sql_count, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();

               /* data.put("city_name", rs.getString("city_name")); 
                data.put("county_name", rs.getString("county_name"));
                data.put("rolename", rs.getString("rolename"));
                data.put("realname", rs.getString("realname"));
                data.put("userid", rs.getString("userid"));
                data.put("phone_num", rs.getString("phone_num"));*/
                
                data.put("order_id", rs.getString("order_id"));
                data.put("goods_name", rs.getString("goods_name"));
                data.put("ship_name", rs.getString("ship_name"));
                data.put("ship_tel", rs.getString("ship_tel"));
                data.put("ship_addr", rs.getString("ship_addr"));
                data.put("create_time", rs.getString("create_time"));
                
                data.put("LOCK_ID", rs.getString("LOCK_ID")); 
                data.put("lock_name", rs.getString("lock_name")); 
                data.put("acc_nbr", rs.getString("acc_nbr")); 
                data.put("order_status", rs.getString("order_status")); 
                data.put("intent_finish_time", rs.getString("intent_finish_time")); 
                
                data.put("WORK_ORDER_ID", rs.getString("WORK_ORDER_ID")); 
                data.put("cast_time", rs.getString("cast_time")); 
                data.put("operator_name", rs.getString("operator_name")); 
                data.put("operator_num", rs.getString("operator_num")); 
                data.put("work_status", rs.getString("work_status")); 
                data.put("update_time", rs.getString("update_time")); 

                return data;
            }
        }, pList.toArray());
        
        return page;
    }
	@Override
	public Page queryOrderQuantityReportList(int pageNo, int pageSize,
			OrderQueryParams params) throws Exception {
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		 	String sql="";
	        String sql_count="";
	        
	        StringBuilder orderFromBuilder = new StringBuilder();
	        List<Map> orderFromCfg = this.getDcSqlByDcName("ORDER_FROM");
	        
	        for (Iterator<Map> it = orderFromCfg.iterator();it.hasNext();) {
	            Map m = it.next();
	            
	            String key = String.valueOf(m.get("codea"));
	            String value = String.valueOf(m.get("value"));
	            
	            if ("intent".equals(key)) {
	                if(orderFromBuilder.length() == 0){
	                    orderFromBuilder.append(value);
	                }else{
	                    orderFromBuilder.append("','"+value);
	                }
	            }
	        }
	        
	        String sql_order_from = orderFromBuilder.toString();

	        String sql1= cacheUtil.getConfigInfo("INTENT_ORDER_QUANTITY_SQL_1");
	        String sql2= cacheUtil.getConfigInfo("INTENT_ORDER_QUANTITY_SQL_2");
	        sql_count=cacheUtil.getConfigInfo("COUNT_INTENT_ORDER_QUANTITY_SQL");
	        
	        if (StringUtils.isEmpty(sql1))
	            throw new Exception("意向单统计报表查询SQL配置为空");
	        
	        if (StringUtils.isEmpty(sql2))
	            throw new Exception("意向单统计报表查询SQL配置为空");

	        if (StringUtils.isEmpty(sql_count))
	            throw new Exception("意向单统计报表数量查询SQL配置为空");
	        
	        sql=sql1+sql2;
	        
	        String startTime = params.getCreate_start();
	        String endTime = params.getCreate_end();
	       
	        if (StringUtil.isEmpty(startTime))
	            startTime = "1980/01/01 00:00:00";

	        if (StringUtil.isEmpty(endTime))
	            endTime = "2080/12/31 23:59:59";
	        
	        String strOrderFrom = "";
	        if (!StringUtil.isEmpty(params.getOrder_from())) {
	            strOrderFrom = params.getOrder_from();
	            String[] str = strOrderFrom.split(",");
	            StringBuilder sb = new StringBuilder();
	            for (String s : str) {
	                sb.append("" + s + "','");
	            }
	            sql_order_from = sb.substring(0, sb.lastIndexOf(",") - 1);
	        }
	        
	        if(!StringUtil.isEmpty(params.getOrder_role())){
	        	if(params.getOrder_role().equals("03121")){
	        		//省中台
	        		String province=cacheUtil.getConfigInfo("CNPC_PROVINCE_ORDER_SQL");
	        		sql = sql.replace("{roleid}", province);
	    	        sql_count = sql_count.replace("{roleid}", "'180801043561300391'");
	        	}else if(params.getOrder_role().equals("03122")){
	        		//市中台 250642129993330688,90000000000000512
	        		String city=cacheUtil.getConfigInfo("CNPC_CITY_ORDER_SQL");
	        		/*String city="'90000000000000532','90000000000000512','90000000000000517','90000000000000518',"
	        					+ "'90000000000000519','90000000000000520','90000000000000516','90000000000000522',"
	        					+ "'90000000000000525','90000000000000524','249851013383581696','249852333117140992','250642129993330688'";*/
	        		sql = sql.replace("{roleid}", city);
	    	        sql_count = sql_count.replace("{roleid}", city);
	        	}else if(params.getOrder_role().equals("03123")){
	        		//县中台
	        		String county=cacheUtil.getConfigInfo("CNPC_COUNTY_ORDER_SQL");
	        		sql = sql.replace("{roleid}", county);
	    	        sql_count = sql_count.replace("{roleid}", county);
	        	}else if(params.getOrder_role().equals("03124")){
	        		//特投人员
	        		sql= cacheUtil.getConfigInfo("INTENT_ORDER_QUANTITY_SQL_BY_OPERATOR");
	        		sql_count =cacheUtil.getConfigInfo("COUNT_INTENT_ORDER_QUANTITY_BY_OPERATOR_SQL");
	        	}
	        }else{
	        	String all="select er.roleid from es_role er where er.role_group = '02' and  er.rolename not like '%虚拟%'  and REGEXP_LIKE(er.rolename, '(县中台|县分中台|分公司|意向单|)')";
	            String sql11=cacheUtil.getConfigInfo("INTENT_ORDER_QUANTITY_SQL_BY_OPERATOR");
        		sql =sql+" union all "+sql11;
        		sql = sql.replace("{roleid}",all );
        		sql_count ="select count(1) from (" + sql+")";
        	}
	    
	        sql = sql.replace("{startTime}", startTime);
	        sql = sql.replace("{endTime}", endTime);
	        sql = sql.replace("{orderFrom}", sql_order_from);
	        
	        sql_count = sql_count.replace("{startTime}", startTime);
	        sql_count = sql_count.replace("{endTime}", endTime);
	        sql_count = sql_count.replace("{orderFrom}", sql_order_from);
	        
	        
	        if(!StringUtil.isEmpty(params.getOrder_city_code())){
	        	sql = sql.replace("{city_code}", params.getOrder_city_code());
	            sql_count = sql_count.replace("{city_code}", params.getOrder_city_code());
	        }else {
	        	sql = sql.replace("and eoi.order_city_code='{city_code}'", "");
	            sql_count = sql_count.replace("and eoi.order_city_code='{city_code}'", "");
	        }
	        
	        if(!StringUtil.isEmpty(params.getOrder_county_code())){
	        	sql = sql.replace("{county_code}", params.getOrder_county_code());
	            sql_count = sql_count.replace("{county_code}", params.getOrder_county_code());
	        }else {
	        	sql = sql.replace("and eoi.order_county_code='{county_code}'", "");
	            sql_count = sql_count.replace("and eoi.order_county_code='{county_code}'", "");
	        }
	            
	        
	        List sqlParams = new ArrayList();
	        List pList = new ArrayList();
	        pList.addAll(sqlParams);
	                
	        Page page = null;
	        
	        logger.info("统记报表SQL" + sql);
	        
	        page = this.baseDaoSupport.queryForPage(sql, sql_count, pageNo, pageSize, new RowMapper() {
	            public Object mapRow(ResultSet rs, int c) throws SQLException {
	                Map data = new HashMap();
 
	                data.put("city_name", rs.getString("city_name")); 
	                data.put("county_name", rs.getString("county_name"));
	                data.put("rolename", rs.getString("rolename"));
	                data.put("realname", rs.getString("realname"));
	                data.put("userid", rs.getString("userid"));
	                data.put("phone_num", rs.getString("phone_num"));
	                data.put("untreat_order_counts", rs.getString("untreat_order_counts"));
	                data.put("in_hand_order_counts", rs.getString("in_hand_order_counts"));
	                data.put("finish_order_counts", rs.getString("finish_order_counts"));
	                data.put("statement_order_counts", rs.getString("statement_order_counts"));
	                data.put("order_total", rs.getString("order_total")); 
	                data.put("untreat_work_counts", rs.getString("untreat_work_counts")); 
	                data.put("in_hand_work_counts", rs.getString("in_hand_work_counts")); 
	                data.put("success_work_counts", rs.getString("success_work_counts")); 
	                data.put("fail_work_counts", rs.getString("fail_work_counts")); 
	                data.put("cancle_work_counts", rs.getString("cancle_work_counts")); 
	                data.put("work_total", rs.getString("work_total")); 

	                return data;
	            }
	        }, pList.toArray());
	        
	        return page;
	}
    
    public List queryIntentOrderReportList(OrderQueryParams params) throws Exception {

        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        
        //订单来源
        String sql = "";
        
        StringBuilder orderFromBuilder = new StringBuilder();
        
        List<Map> orderFromCfg = this.getDcSqlByDcName("ORDER_FROM");
        
        for (Iterator<Map> it = orderFromCfg.iterator();it.hasNext();) {
            Map m = it.next();
            
            String key = String.valueOf(m.get("codea"));
            String value = String.valueOf(m.get("value"));
            
            if ("intent".equals(key)) {
                if(orderFromBuilder.length() == 0){
                    orderFromBuilder.append(value);
                }else{
                    orderFromBuilder.append("','"+value);
                }
            }
        }
        
        String sql_order_from = orderFromBuilder.toString();
        
        if ("1".equals(params.getReport_search_type())) {
            // 按地市统计SQL
            sql = cacheUtil.getConfigInfo("INTENT_ORDER_REPORT_CITY_SQL");
        } else {
            // 按县分统计SQL
            sql = cacheUtil.getConfigInfo("INTENT_ORDER_REPORT_COUNTY_SQL");
        }

        if (StringUtils.isEmpty(sql))
            throw new Exception("意向单统计报表查询SQL配置为空");

        List sqlParams = new ArrayList();

        if (params == null)
            throw new Exception("意向单统计报表传入查询参数为空");

        String startTime = params.getCreate_start();
        String endTime = params.getCreate_end();

        if (StringUtil.isEmpty(startTime))
            startTime = "1980/01/01 00:00:00";

        if (StringUtil.isEmpty(endTime))
            endTime = "2080/12/31 23:59:59";
        String strOrderFrom = "";
        
        if (!StringUtil.isEmpty(params.getOrder_from())) {
            strOrderFrom = params.getOrder_from();
            String[] str = strOrderFrom.split(",");
            StringBuilder sb = new StringBuilder();
            for (String s : str) {
                sb.append("" + s + "','");
            }
            sql_order_from = sb.substring(0, sb.lastIndexOf(",") - 1);
        }

        sql = sql.replace("{startTime}", startTime);
        sql = sql.replace("{endTime}", endTime);
        sql = sql.replace("{orderFrom}", sql_order_from);
        
        AdminUser user = ManagerUtils.getAdminUser();
        
        if(!StringUtil.isEmpty(params.getOrder_city_code())){
            sql = sql.replace("{order_city_code}", " and a.order_city_code='"+params.getOrder_city_code()+"' ");
            sql = sql.replace("{order_city_code_main}", " and c.col1='"+params.getOrder_city_code()+"' ");
        }else if(!"1".equals(user.getPermission_level())){
            //zhaochen 20181106 不是省级工号，没有传入地市参数，需要加上地市条件
            String str = SqlUtil.getSqlInStr("a.order_city_code", user.getPermission_region(), false, true);
            
            sql = sql.replace("{order_city_code}", str);
            
            str = SqlUtil.getSqlInStr("c.col1", user.getPermission_region(), false, true);
            
            sql = sql.replace("{order_city_code_main}", str);
        }else {
            sql = sql.replace("{order_city_code}", "");
            sql = sql.replace("{order_city_code_main}", "");
        }

        logger.info("统记查询SQL" + sql);
        
        List ret = this.baseDaoSupport.queryForList(sql);

        return ret;
    }

    private String buildExportIntentOrderDtlCondition(OrderQueryParams params, String countType) throws Exception {
        StringBuilder builder = new StringBuilder();

        builder.append(" ");

        if (params == null)
            throw new Exception("传入查询参数为空");

        if (!StringUtil.isEmpty(params.getCreate_start())) {
            String startTime = params.getCreate_start();

            builder.append(" and ");
            builder.append(" a.create_time>to_date('" + startTime + "','yyyy/MM/dd hh24:mi:ss')");
        }

        if (!StringUtil.isEmpty(params.getCreate_end())) {
            String endTime = params.getCreate_end();

            builder.append(" and ");
            builder.append(" a.create_time<to_date('" + endTime + "','yyyy/MM/dd hh24:mi:ss')");
        }
        
        AdminUser user = ManagerUtils.getAdminUser();
        
        if (!StringUtil.isEmpty(params.getOrder_city_code())) {
            builder.append(" and ");
            builder.append(" a.city_code='"+params.getOrder_city_code()+"' ");
        }else if(!"1".equals(user.getPermission_level())){
            //zhaochen 20181106 不是省级工号，没有传入地市参数，需要加上地市条件
            builder.append(SqlUtil.getSqlInStr("a.city_code", user.getPermission_region(), false, true));
        }

        // 0 查明细 1查总数
        if ("0".equals(countType)) {
            if (!StringUtil.isEmpty(params.getOrder_from())) {
                String str = params.getOrder_from().toString();
                String[] strList = str.split(",");
                List<String> fromList = Arrays.asList(strList);
                String order_from_c = SqlUtil.getSqlInStr(" a.source_system_type", fromList, false, true);
                builder.append(order_from_c);
            }
        } else {
            if (!StringUtil.isEmpty(params.getOrder_from_c())) {
                String str = params.getOrder_from().toString();
                String[] strList = str.split(",");
                StringBuilder sb = new StringBuilder();
                for (String s : strList) {
                    sb.append("'" + s + "',");
                }
                String order_from = sb.substring(0, sb.lastIndexOf(","));
                builder.append(" and ");
                builder.append(" a.source_system_type in (" + order_from + ")");
            }
        }

        return builder.toString();
    }

    // sgf  EJX
    @Override
    public Page exportOrderReportExcel(int pageNo, int pageSize, OrderQueryParams params) {
        // DOTO
        StringBuilder sbStart = new StringBuilder();
        sbStart.append(" select distinct  to_char( oe.tid_time, 'yyyy-mm-dd hh24:mi:ss') as tid_time, ");
        sbStart.append("oe.refund_desc  as cancelOrderMemo, ");
        sbStart.append("t.short_url as photoShortUrl, ");
        sbStart.append(" (select pname from es_dc_public_ext where stype = 'DIC_ORDER_NODE' and pkey = oe.flow_trace_id) as orderNodeStatus, ");
        
        sbStart.append(
                "  (select a.local_name from es_regions a where  a.region_id = oe.order_city_code ) as city_name,");
        sbStart.append("    decode((select dr.countyname from es_busicty dr where dr.countyid = ez.county_id),");
        sbStart.append(" '', (select eocn.county_name from es_order_county_name eocn where eocn.county_id=t.district_code), ");
        sbStart.append("  (select dr.countyname  from es_busicty dr where dr.countyid = ez.county_id)) as county_name,");
        sbStart.append("  oe.order_id as order_id,");
        sbStart.append(
                " (select pname from es_dc_public_ext where stype = 'source_from' and pkey = oe.order_from) as order_from,");
        sbStart.append(" oe.out_tid as out_tid,");
        sbStart.append(" t.goodsname as goods_name,");
        sbStart.append(" s.ship_name as buy_name,");
        sbStart.append(" decode(ed.ship_tel,'',ed.ship_mobile,ed.ship_tel) as mobile,");
        //报表导出新增身份证号码,开户人员工号,退单人员工号三个字段
        sbStart.append(" pe.cert_card_num as cert_card_num,");
        sbStart.append(" t.open_account_userid as open_account_userid,");
        sbStart.append(" t.refund_userid as refund_userid,");
        //报表导出新增开户时间和退单时间字段
        sbStart.append(" t.refund_time as refund_time,");
        sbStart.append(" t.open_account_time as open_account_time,");
        
        sbStart.append(" ed.ship_addr as address,");
        sbStart.append(" ed.province, ed.city, ed.region, ed.ship_addr as adsl_addr, ");
        sbStart.append(
                "  decode(t.develop_point_code_new,'',ez.develop_point_code,t.develop_point_code_new) as development_point_code,");
        sbStart.append(
                " decode(t.development_point_name,'',ez.develop_point_name,t.development_point_name) as development_point_name, ");
        sbStart.append("  decode(t.develop_code_new,'',ez.develop_code,t.develop_code_new) as development_code,");
        sbStart.append(" decode(t.develop_name_new,'',ez.develop_name,t.develop_name_new) as development_name,");
        sbStart.append(
                " (select pname from es_dc_public_ext where stype = 'order_state' and pkey = s.order_state) as order_state,");
        sbStart.append(" (select status from (SELECT order_id,status FROM( ");
        sbStart.append("                               select u.status,u.order_id");
        sbStart.append(
                "                                ,ROW_NUMBER()OVER(PARTITION BY u.order_id ORDER BY u.update_time desc) RN  FROM es_work_order u ");
        sbStart.append(
                "                                    left JOIN es_order_ext p ON u.order_id=p.order_id) T where  RN=1 ) M  where M.order_id = oe.order_id ) as status,");
        sbStart.append("   ol.lock_user_id as lock_user_id,");
        sbStart.append("  (select realname from es_adminuser where userid = ol.lock_user_id) as lock_user_name,");
        sbStart.append("  ez.adsl_account as adsl_account,");
        sbStart.append("   ez.adsl_number as adsl_number, ie.phone_num as phone_num, t.iccid as sim, ");
        sbStart.append(" (select elc.name from es_logi_company elc  where elc.id = ed.shipping_company) as shipping_company, ed.logi_no as logi_no ");
        sbStart.append("  from es_order_ext oe  ");
        sbStart.append("         left join es_order_zhwq_adsl ez on oe.order_id = ez.order_id  ");
        sbStart.append("         left join es_order_extvtl t on oe.order_id = t.order_id  ");
        sbStart.append("         left join es_order s on oe.order_id = s.order_id  ");
        sbStart.append("         left join es_delivery ed on oe.order_id = ed.order_id  ");
        sbStart.append(
                "         left join es_order_lock ol on oe.order_id = ol.order_id and oe.flow_trace_id = ol.tache_code ");
        sbStart.append("         left join es_order_items_ext ie on oe.order_id = ie.order_id  ");
        sbStart.append("         left join es_goods eg on s.goods_id = eg.goods_id  ");
        sbStart.append("         left join es_order_items_prod_ext pe on oe.order_id = pe.order_id  ");
        sbStart.append("         left join es_payment_logs ps  on oe.order_id = ps.order_id  ");
        sbStart.append(" where oe.source_from ='" + ManagerUtils.getSourceFrom() + "'");

        StringBuilder countBuilder = new StringBuilder();
        countBuilder.append("  select count(1) from es_order_ext oe  ");
        countBuilder.append("         left join es_order_zhwq_adsl ez on oe.order_id = ez.order_id  ");
        countBuilder.append("         left join es_order_extvtl t on oe.order_id = t.order_id  ");
        countBuilder.append("         left join es_order s on oe.order_id = s.order_id  ");
        countBuilder.append("         left join es_delivery ed on oe.order_id = ed.order_id  ");
        countBuilder.append(
                "         left join es_order_lock ol on oe.order_id = ol.order_id and oe.flow_trace_id = ol.tache_code ");
        countBuilder.append("         left join es_order_items_ext ie on oe.order_id = ie.order_id  ");
        countBuilder.append("         left join es_goods eg on s.goods_id = eg.goods_id  ");
        countBuilder.append("         left join es_order_items_prod_ext pe on oe.order_id = pe.order_id  ");
        countBuilder.append("         left join es_payment_logs ps  on oe.order_id = ps.order_id  ");
        countBuilder.append(" where oe.source_from ='" + ManagerUtils.getSourceFrom() + "'");
        String condition = this.bulidExportOrderParams(params);
        sbStart.append(condition);
        countBuilder.append(condition);
        List sqlParams = new ArrayList();
        List pList = new ArrayList();
        pList.addAll(sqlParams);
        // Page page = this.daoSupportForAsyCount.queryForCPage(sql, pageNo, pageSize,
        // OrderExportQueryVO.class, countSql,null);
        Page page = this.baseDaoSupport.queryForPage(sbStart.toString(), countBuilder.toString(), pageNo, pageSize,
                new RowMapper() {
                    @Override
                    public Object mapRow(ResultSet rs, int r) throws SQLException {

                        Map data = new HashMap();
                        data.put("city_name", rs.getString("city_name")); // 地市
                        data.put("county_name", rs.getString("county_name")); // 县分
                        data.put("order_id", rs.getString("order_id")); // 订单号
                        data.put("order_from", rs.getString("order_from")); // 订单来源
                        data.put("out_tid", rs.getString("out_tid")); // 外部订单号
                        data.put("goods_name", rs.getString("goods_name")); // 商品名称
                        data.put("buy_name", rs.getString("buy_name")); // 客户名称
                        data.put("mobile", rs.getString("mobile")); // 客户联系电话
                        data.put("cert_card_num", rs.getString("cert_card_num")); // 身份证号码
                        data.put("open_account_userid", rs.getString("open_account_userid")); // 开户人员工号
                        data.put("open_account_time", rs.getString("open_account_time")); // 开户时间
                        data.put("refund_userid", rs.getString("refund_userid")); // 退单人员工号
                        data.put("refund_time", rs.getString("refund_time")); // 退单时间
                        data.put("address", rs.getString("address")); // 客户联系地址
                        String adsl_addr = rs.getString("adsl_addr");
                        
                        if (!StringUtil.isEmpty(adsl_addr)){
                        	if ("收货地址".equals(adsl_addr)) {
								adsl_addr=rs.getString("province")+rs.getString("city")+rs.getString("region");
							} else if (adsl_addr.indexOf("省") == -1) {
								adsl_addr=rs.getString("province")+rs.getString("city")+rs.getString("region")+rs.getString("adsl_addr");
							}
//                        	String regex="((?<province>[^省]+省|.+自治区)|上海|北京|天津|重庆)(?<city>[^市]+市|.+自治州)(?<county>[^县]+县|.+区|.+镇|.+局)?(?<town>[^区]+区|.+镇)?(?<village>.*)"; 
//                        	Matcher m=Pattern.compile(regex).matcher(adsl_addr); 
//                        	String province=null,city=null,county=null,town=null,village=null; 
//                    		province=m.group("province"); 
//                    		row.put("province", province==null?"":province.trim()); 
//                    		city=m.group("city"); 
//                    		row.put("city", city==null?"":city.trim()); 
//                    		county=m.group("county"); 
//                    		row.put("county", county==null?"":county.trim()); 
//                    		town=m.group("town"); 
//                    		row.put("town", town==null?"":town.trim()); 
//                    		village=m.group("village"); 
//                    		row.put("village", village==null?"":village.trim()); 
                        }else{
                        	adsl_addr=rs.getString("province")+rs.getString("city")+rs.getString("region");
                        }
                        
                        data.put("adsl_addr", adsl_addr);// 标准地址
                        data.put("development_point_code", rs.getString("development_point_code"));// 发展点编码
                        data.put("development_point_name", rs.getString("development_point_name"));// 发展点名称
                        data.put("development_code", rs.getString("development_code"));// 发展人编码
                        data.put("development_name", rs.getString("development_name"));// 发展人名称
                        data.put("order_state", rs.getString("order_state"));// 订单状态
                        data.put("orderNodeStatus", rs.getString("orderNodeStatus"));//订单环节状态
                        data.put("cancelOrderMemo", rs.getString("cancelOrderMemo"));//退单备注
                        data.put("photoShortUrl", rs.getString("photoShortUrl"));//照片上传短链接
                        String status = rs.getString("status");
                        if (StringUtil.isEmpty(status)) {
                            data.put("status", null);
                        } else if ("0".equals(status)) {
                            data.put("status", "未处理");// 工单状态
                        } else if ("1".equals(status)) {
                            data.put("status", "处理成功");// 工单状态
                        } else if ("2".equals(status)) {
                            data.put("status", "处理失败");// 工单状态
                        } else if ("3".equals(status)) {
                            data.put("status", "已撤单");// 工单状态
                        }
                        data.put("lock_user_id", rs.getString("lock_user_id"));// 锁定人工号
                        data.put("lock_user_name", rs.getString("lock_user_name"));// 锁定人姓名

                        data.put("adsl_account", rs.getString("adsl_account"));// 宽带账号
                        data.put("adsl_number", rs.getString("adsl_number"));// 宽带业务号码
                        data.put("phone_num", rs.getString("phone_num"));// 用户号码
                        data.put("sim", rs.getString("sim"));// sim号
                        data.put("shipping_company", rs.getString("shipping_company"));// 物流公司
                        data.put("logi_no", rs.getString("logi_no"));// 物流单号
                        data.put("tid_time", rs.getString("tid_time"));// 下单时间
                        return data;
                    }
                }, pList.toArray());
        return page;
    }

    private String bulidExportOrderParams(OrderQueryParams params) {
        StringBuilder paramBuilder = new StringBuilder();
        paramBuilder.append(" ");
        paramBuilder.append(" and  (ez.product_type !='TV' or ez.product_type is null) ");
        paramBuilder.append(" and  (oe.order_if_cancel ='0' or oe.order_if_cancel is null) ");
        // SQL拼接
        // 订单流程
        if (!StringUtil.isEmpty(params.getFlow_id()) && !"-1".equals(params.getFlow_id())) {
            paramBuilder.append(" and oe.flow_trace_id in('" + params.getFlow_id().replace(",", "','") + "')");
        }
        // 订单来源
        if (!StringUtil.isEmpty(params.getOrder_from()) && !"-1".equals(params.getOrder_from())) {
            paramBuilder.append(" and oe.order_from in('" + params.getOrder_from().replace(",", "','") + "')");
        }
        // 支付方式
        if (!StringUtil.isEmpty(params.getPayment_id()) && !"-1".equals(params.getPayment_id())) {
            paramBuilder.append(" and ps.pay_method in('" + params.getPayment_id().replace(",", "','") + "')");
        }
        // 配送方式
        if (!StringUtil.isEmpty(params.getShipping_id()) && !"-1".equals(params.getShipping_id())) {
            paramBuilder.append(" and s.shipping_type in('" + params.getShipping_id().replace(",", "','") + "')");
        }
        // 订单类型
        if (!StringUtil.isEmpty(params.getOrder_type()) && !"-1".equals(params.getOrder_type())) {
            paramBuilder.append(" and s.order_type in('" + params.getOrder_type().replace(",", "','") + "')");
        }
        // 订单发展归属
        if (!StringUtil.isEmpty(params.getOrder_channel()) && !"-1".equals(params.getOrder_channel())) {
            paramBuilder.append(" and oe.order_type in('" + params.getOrder_channel().replace(",", "','") + "')");
        }
        // 订单状态 需要进行判断是否是外呼02 异常03 正常00 退单01
        if (!StringUtil.isEmpty(params.getOrder_qry_status()) && !"qxz".equals(params.getOrder_qry_status())) {
            String[] order_qry_status = params.getOrder_qry_status().split(",");
            StringBuilder qryStatus = new StringBuilder();
            qryStatus.append(" and ( ");
            StringBuilder sq = new StringBuilder();
            List<String> list = new ArrayList<String>();
            if (order_qry_status.length > 0) {
                for (int i = 0; i < order_qry_status.length; i++) {
                    if ("00".equals(order_qry_status[i]) || "01".equals(order_qry_status[i])
                            || "02".equals(order_qry_status[i]) || "03".equals(order_qry_status[i])) {
                        list.add(order_qry_status[i]);
                    } else if ("qxz".equals(order_qry_status[i])) {
                        continue;
                    } else {
                        sq.append(order_qry_status[i] + ",");

                    }
                }
            }
            if (list.size() > 0) {
                for (String param : list) {
                    if ("00".equals(param)) {// 正常
                        qryStatus.append(
                                " not exists (select 1 from es_order_ext t,es_order a where t.order_id= a.order_id and (t.abnormal_status='1' or t.refund_deal_type='02' or a.status='11') and t.order_id=oe.order_id) or");
                    } else if ("01".equals(param)) {// 退单
                        qryStatus.append(" oe.refund_deal_type='02' or ");
                    } else if ("02".equals(param)) {// 外呼
                        qryStatus.append(" s.status='11' or ");
                    } else if ("03".equals(param)) {// 异常
                        qryStatus.append(" oe.abnormal_status='1' or ");
                    }
                }
            }
            if (!StringUtils.isEmpty(sq.toString())) {
                String s = sq.toString().substring(0, sq.toString().lastIndexOf(","));
                qryStatus.append(" s.order_state in('" + s.replace(",", "','") + "')  or ");
            }
            String sql = qryStatus.toString().substring(0, qryStatus.toString().lastIndexOf("or"));
            paramBuilder.append(sql);
            paramBuilder.append(" ) ");
        }

        // 支付时间
        if (!StringUtil.isEmpty(params.getPay_start())) {
            String start = "'" + params.getPay_start() + "'";
            paramBuilder.append(" and s.pay_time >=" + DBTUtil.to_sql_date(start, 2) + "");
        }
        if (!StringUtil.isEmpty(params.getPay_end())) {
            String end = "'" + params.getPay_end() + "'";
            paramBuilder.append(" and s.pay_time <=" + DBTUtil.to_sql_date(end, 2) + "");
        }
        // 创建时间
        if (!StringUtil.isEmpty(params.getCreate_start())) {
            String date = "'" + params.getCreate_start() + "'";
            paramBuilder.append(" and oe.tid_time >=" + DBTUtil.to_sql_date(date, 2) + "");
        }
        if (!StringUtil.isEmpty(params.getCreate_end())) {
            String end = "'" + params.getCreate_end() + "'";
            paramBuilder.append(" and oe.tid_time <=" + DBTUtil.to_sql_date(end, 2) + "");
        }
        // 外部订单号
        if (!StringUtil.isEmpty(params.getOut_tid())) {
            paramBuilder.append(" and oe.out_tid = '" + params.getOut_tid() + "'");
        }
        // 内部订单号
        if (!StringUtil.isEmpty(params.getOrder_id())) {
            paramBuilder.append(" and s.order_id = '" + params.getOrder_id() + "'");
        }
        // 选择城市
        if (!StringUtil.isEmpty(params.getOrder_city_code())) {
            paramBuilder
                    .append(" and oe.order_city_code in('" + params.getOrder_city_code().replace(",", "','") + "')");
        }
        // 开户号码
        if (!StringUtil.isEmpty(params.getPhone_num())) {
            paramBuilder.append(" and ie.phone_num = '" + params.getPhone_num() + "'");
        }
        // 商品名称
        if (!StringUtil.isEmpty(params.getGoods_name())) {
            paramBuilder.append(" and t.goodsname like '%" + params.getGoods_name() + "%'");
        }
        // 是否是当月办理
        if (!StringUtil.isEmpty(params.getBss_time_type()) && !"-1".equals(params.getBss_time_type())) {
            paramBuilder.append(" and ie.bss_time_type  = '" + params.getBss_time_type() + "'");
        }
        // 物流单号
        if (!StringUtil.isEmpty(params.getLogi_no())) {
            paramBuilder.append(" and ed.logi_no = '" + params.getLogi_no() + "'");
        }
        // 异常单类型
        if (!StringUtil.isEmpty(params.getException_type()) && !"-1".equals(params.getException_type())) {
            paramBuilder.append(" and oe.visible_status = '" + params.getException_type() + "'");
        }
        // 型号
        if (!StringUtil.isEmpty(params.getModel_code())) {
            paramBuilder.append(" and eg.model_code = '" + params.getModel_code() + "'");
        }
        // 商品包类型
        if (!StringUtil.isEmpty(params.getGoods_pagekage_type()) && !"-1".equals(params.getGoods_pagekage_type())) {
            paramBuilder.append(" and eg.type_id = '" + params.getGoods_pagekage_type() + "'");
        }
        // 生产模式
        if (!StringUtil.isEmpty(params.getOrder_model()) && !"-1".equals(params.getOrder_model())) {
            paramBuilder.append(" and oe.order_model = '" + params.getOrder_model() + "'");
        }
        // 集团名称
        if (!StringUtil.isEmpty(params.getGroup_name())) {
            paramBuilder.append(" and t.group_name = '" + params.getGroup_name() + "'");
        }
        // 收货人电话
        if (!StringUtil.isEmpty(params.getShip_tel())) {
            paramBuilder.append(" and ed.ship_tel = '" + params.getShip_tel() + "'");
        }
        // 锁定人账号
        if (!StringUtil.isEmpty(params.getLock_user_id())) {
            paramBuilder.append(" and ol.lock_user_id = '" + params.getLock_user_id() + "'");
        }
        // 证件号码
        if (!StringUtil.isEmpty(params.getCert_card_num())) {
            paramBuilder.append(" and pe.cert_card_num = '" + params.getCert_card_num() + "'");
        }
        // 是否是老用户
        if (!StringUtil.isEmpty(params.getOrder_is_his()) && !"-1".equals(params.getOrder_is_his())) {
            paramBuilder.append(" and pe.is_old = '" + params.getOrder_is_his() + "'");
        }
        // iccid
        if (!StringUtil.isEmpty(params.getIccid())) {
            paramBuilder.append(" and t.iccid = '" + params.getIccid() + "'");
        }
        // 用户种子号码
        if (!StringUtil.isEmpty(params.getShare_svc_num())) {
            paramBuilder.append(" and t.share_svc_num = '" + params.getShare_svc_num() + "'");
        }
        //证件上传状态：意向单没有证件上传，只查询正式单
        if (!StringUtils.isEmpty(params.getIf_Send_Photos())) {
            
                String str = params.getIf_Send_Photos().toString();
                String[] strList = str.split(",");
                StringBuilder sb = new StringBuilder();
                for (String s : strList) {
                    sb.append("'" + s + "',");
                }
                String is_send_photos = sb.substring(0, sb.lastIndexOf(","));
                if(is_send_photos.contains("9")){
                	paramBuilder.append(" and (oe.if_send_photos in ("+is_send_photos+") or oe.if_send_photos is null)");
                }else
                	paramBuilder.append(" and oe.if_send_photos in ("+is_send_photos+")");
            
        }
        // 是否可见
        if (params.getVisible_status() != -1) {
            paramBuilder.append(" and oe.visible_status = " + params.getVisible_status() + "");
        }

        paramBuilder.append("   order by tid_time desc ");
        return paramBuilder.toString();
    }

    @Override
    public Page exportIntentOrderDtl(int pageNo, int pageSize, OrderQueryParams params) throws Exception {

        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

        // 规则编号
        String sql = cacheUtil.getConfigInfo("INTENT_ORDER_DTL_SQL");
        String sql_count = cacheUtil.getConfigInfo("INTENT_ORDER_DTL_COUNT_SQL");

        if (StringUtils.isEmpty(sql))
            throw new Exception("意向单统计报表明细导出查询SQL配置为空");

        if (StringUtils.isEmpty(sql_count))
            throw new Exception("意向单统计报表明细导出数量查询SQL配置为空");

        List sqlParams = new ArrayList();

        String condition = this.buildExportIntentOrderDtlCondition(params, "0");
        
        sql = sql + condition;
        sql_count = sql_count + " where 1=1 " + condition;

        logger.info("---------------意向单报表明细导出--------------------");
        logger.info("sql:" + sql);
        logger.info("sql_count:" + sql_count);

        List pList = new ArrayList();
        pList.addAll(sqlParams);

        Page page = this.baseDaoSupport.queryForPage(sql, sql_count, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();

                data.put("city_code", rs.getString("city_code")); // 地市编码
                data.put("city_name", rs.getString("city_name")); // 地市名称
                data.put("county_code", rs.getString("county_code")); // 县分编码
                data.put("county_name", rs.getString("county_name")); // 县分名称
                data.put("order_id", rs.getString("order_id")); // 订单号

                data.put("intent_order_id", rs.getString("intent_order_id")); // 意向单号
                data.put("goods_name", rs.getString("goods_name")); // 商品名称
                data.put("ship_name", rs.getString("ship_name")); // 客户姓名
                data.put("ship_tel", rs.getString("ship_tel")); // 客户号码
                data.put("ship_addr", rs.getString("ship_addr")); // 客户地址

                data.put("referee_num", rs.getString("referee_num")); // 推荐人号码
                data.put("create_time", rs.getString("create_time")); // 创建时间
                data.put("remark", rs.getString("remark")); // 备注
                data.put("status", rs.getString("status")); // 状态
                data.put("intent_finish_time", rs.getString("intent_finish_time")); // 结束时间

                data.put("lock_id", rs.getString("lock_id")); // 锁定人工号
                data.put("lock_name", rs.getString("lock_name")); // 锁定人名称
                data.put("source_system_name", rs.getString("source_system_name")); // 订单来源
                return data;
            }
        }, pList.toArray());
        return page;
    }
    
    public List exportIntentOrderDtl(OrderQueryParams params) throws Exception {

        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        
        // 规则编号
        String sql1 = cacheUtil.getConfigInfo("INTENT_ORDER_DTL_SQL_1");
        String sql2 = cacheUtil.getConfigInfo("INTENT_ORDER_DTL_SQL_2");
        String sql=sql1+sql2;
        if (StringUtils.isEmpty(sql))
            throw new Exception("意向单统计报表明细导出查询SQL配置为空");

        List sqlParams = new ArrayList();
        AdminUser user = ManagerUtils.getAdminUser();
        //订单来源
        if (!StringUtil.isEmpty(params.getOrder_from())) {
            sql = sql+(" and a.source_system_type in('" + params.getOrder_from().replace(",", "','") + "')");
        }
        //时间条件
        if (!StringUtil.isEmpty(params.getCreate_start())) {
        	sql = sql+(" and a.create_time>to_date('" +  params.getCreate_start() + "','yyyy/MM/dd hh24:mi:ss')");
        }
        if (!StringUtil.isEmpty(params.getCreate_end())) {
        	sql = sql+(" and a.create_time<to_date('" +  params.getCreate_end() + "','yyyy/MM/dd hh24:mi:ss')");
        }
        //地市条件
        if(!StringUtil.isEmpty(params.getOrder_city_code())){
        	sql = sql+(" and a.city_code='"+params.getOrder_city_code()+"' ");
        }else if(!"1".equals(user.getPermission_level())){
            String str = SqlUtil.getSqlInStr("a.city_code", user.getPermission_region(), false, true);
            sql =sql+str;
        }else {
        	sql = sql+(" and a.city_code in('330100','330200','330300','330400','330500','330600','330700','330800','330900','331000','331100') ");
        }

        logger.info("意向单导出详情SQL:"+sql);
        System.out.println("意向单导出详情SQL:"+sql);
        List ret = this.baseDaoSupport.queryForList(sql);

        return ret;
    }
    
    //导出v计划订报表
    public List queryVplanOrderReportList(OrderQueryParams params) throws Exception {

        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

        // 规则编号
        String sql1 = cacheUtil.getConfigInfo("VPLAN_ORDER_DTL_SQL1");
        String sql2 = cacheUtil.getConfigInfo("VPLAN_ORDER_DTL_SQL2");

        String sql=sql1+sql2;
        if (StringUtils.isEmpty(sql))
            throw new Exception("V计划统计报表导出查询SQL配置为空");

        List sqlParams = new ArrayList();
        
        List<Map> orderFromCfg = this.getDcSqlByDcName("V_PLAN");
            
        String startTime = params.getCreate_start();
        String endTime = params.getCreate_end();
        String activtyID=params.getOrder_from();

        String key1=" ";
        String key2=" ";
        String key3=" ";
        for (int i = 0; i < orderFromCfg.size(); i++) {
            if(orderFromCfg.get(i).get("value").equals(activtyID)){
                String key=(String) orderFromCfg.get(i).get("codea");
                String[] keys=key.split(",");
                key1=keys[0];
                key2=keys[1];
                key3=keys[2];
            }           
        }
        if(StringUtil.isEmpty(activtyID)){
            sql = sql.replace("and t.market_user_id = '{key1}' ", " ");
            sql = sql.replace("and  t.market_user_id = '{key2}' ", " ");
            sql = sql.replace("eoe2.market_user_id = '{key3}'  and ", " ");
            sql = sql.replace("t.market_user_id = '{key3}'  and", " ");
            sql = sql.replace("and a.market_user_id = '{key3}' ", " ");
        }
        if (StringUtil.isEmpty(startTime))
            startTime = "1980/01/01 00:00:00";

        if (StringUtil.isEmpty(endTime))
            endTime = "2080/12/31 23:59:59";
            
        sql = sql.replace("{startTime}", startTime);
        sql = sql.replace("{endTime}", endTime);
        sql = sql.replace("{key1}", key1);
        sql = sql.replace("{key2}", key2);
        sql = sql.replace("{key3}", key3);
        
        List ret = this.baseDaoSupport.queryForList(sql);

        return ret;
    }
    
    

    @Override
    public int getExportIntentOrderDtlCount(OrderQueryParams params) throws Exception {
        int count = 0;
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String sql_count = cacheUtil.getConfigInfo("INTENT_ORDER_DTL_COUNT_SQL");

        if (StringUtils.isEmpty(sql_count))
            throw new Exception("意向单统计报表明细导出数量查询SQL配置为空");

        List sqlParams = new ArrayList();

        String condition = this.buildExportIntentOrderDtlCondition(params, "1");
        logger.info("sql_count:" + sql_count);
        logger.info("condition:" + condition);
        sql_count = sql_count + " where 1=1 " + condition;

        logger.info("意向单导出订单总数查询：" + sql_count);
        count = this.baseDaoSupport.queryForInt(sql_count);

        return count;
    }

    // songgf
    @Override
    public int getExportOrderReport(OrderQueryParams params) {
        StringBuilder countBuilder = new StringBuilder();
        countBuilder.append("  select count(1) from es_order_ext oe  ");
        countBuilder.append("         left join es_order_zhwq_adsl ez on oe.order_id = ez.order_id  ");
        countBuilder.append("         left join es_order_extvtl t on oe.order_id = t.order_id  ");
        countBuilder.append("         left join es_order s on oe.order_id = s.order_id  ");
        countBuilder.append("         left join es_delivery ed on oe.order_id = ed.order_id  ");
        countBuilder.append(
                "         left join es_order_lock ol on oe.order_id = ol.order_id and oe.flow_trace_id = ol.tache_code ");
        countBuilder.append("         left join es_order_items_ext ie on oe.order_id = ie.order_id  ");
        countBuilder.append("         left join es_goods eg on s.goods_id = eg.goods_id  ");
        countBuilder.append("         left join es_order_items_prod_ext pe on oe.order_id = pe.order_id  ");
        countBuilder.append("         left join es_payment_logs ps  on oe.order_id = ps.order_id  ");
        countBuilder.append(" where oe.source_from ='" + ManagerUtils.getSourceFrom() + "'");
        String paramInfo = this.bulidExportOrderParams(params);
        countBuilder.append(paramInfo);
        int count = this.baseDaoSupport.queryForInt(countBuilder.toString());
        return count;
    }

    @Override
    public int getExportIntentOrderCount(OrderQueryParams params) throws Exception {
        int count = 0;

        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

        // SQL配置
        String sql_count = "";

        if ("1".equals(params.getReport_search_type())) {
            // 按地市统计SQL
            sql_count = cacheUtil.getConfigInfo("INTENT_ORDER_REPORT_CITY_COUNT_SQL");
        } else {
            // 按县分统计SQL
            sql_count = cacheUtil.getConfigInfo("INTENT_ORDER_REPORT_COUNTY_COUNT_SQL");
        }

        count = this.baseDaoSupport.queryForInt(sql_count);

        return count;
    }

    @Override
    public List<Map> queryAllGonghaoByCountyId(String county_id,String gonghao) {
        /*String sql = "select a.userid,a.phone_num,b.org_name from es_adminuser a,es_organization b where a.state = '1' and "
                + " a.org_id = b.org_code and " + " a.usertype = '1' and " + " a.source_from = '"
                + ManagerUtils.getSourceFrom() + "' and " + " a.org_id = '" + county_id
                + "' order by a.create_time desc";*/
        StringBuilder sbBuilder = new StringBuilder();
        sbBuilder.append(" select ea.userid, ea.username, ea.realname, ea.phone_num, ea.dep_name as org_name ");
        sbBuilder.append("  from es_adminuser ea  ");
        sbBuilder.append(" where ea.userid in  ");
        sbBuilder.append("      (select eur.userid from es_user_role eur where eur.roleid in ");
        sbBuilder.append("            (select era.roleid from es_role_auth era ");
        sbBuilder.append("              where era.authid in (select erd.id  from es_role_data erd where erd.ord_receive_user like '").append(gonghao).append(",%') ");
        sbBuilder.append("                       ) ");
        sbBuilder.append("      ) ");
        sbBuilder.append(" and ea.source_from = '").append(ManagerUtils.getSourceFrom()).append("' ");
        sbBuilder.append(" and ea.state = '1' and ea.sms_receive='1' ");
        return this.baseDaoSupport.queryForList(sbBuilder.toString());
    }

    @Override
    public List<Map> queryAllAdminUserId(String userId) throws Exception {
        String sql = "select ad.userid,ad.phone_num,b.org_name "
                + " from es_adminuser ad ,es_organization b where   ad.org_id = b.org_code and ad.state ='1' and  ad.userid in"
                + " (select ur.userid from es_user_role ur where  ur.source_from = 'ECS' and  ur.roleid in ("
                + " select au.roleid  from es_role_auth  au where au.authid in ("
                + " select a.id from es_role_data a  where a.ord_receive_user like '" + userId + ",%'" + ")" + "))";

        return this.baseDaoSupport.queryForList(sql);
    }

    @Override
    public List<Map> queryAllocationAdminUserId(String userId) {
        String sql = "select t.userid,t.phone_num,o.org_name from es_adminuser t,es_organization o "
                + " where  t.source_from ='ECS' and  t.org_id = o.org_code  and t.userid ='" + userId + "' "
                + " and t.userid in "
                + " (select ur.userid from es_user_role ur where  ur.source_from = 'ECS' and  ur.roleid in ( "
                + " select au.roleid  from es_role_auth  au where au.authid in ( "
                + " select distinct a.id from es_role_data a,es_dc_public_ext b  where "
                + " instr(a.ord_receive_user,b.pname||',')>0 and b.stype  like 'DIC_COUNTY_USERS_%'))) ";
        return this.baseDaoSupport.queryForList(sql);
    }

    @Override
    public List<Map> queryMessageAdminReminder() {

        // 时间配置
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        // 正式
        String sql =cacheUtil.getConfigInfo("REMINDER_WOEK_NOTE_TIME_SQL");
        
        logger.info("短信催单工单：" + sql);
        return this.baseDaoSupport.queryForList(sql);
    }

    @Override
    public List<Map> queryMessageOitaReminder() {
        // 时间配置
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

        // 规则编号  正式 
        String sql = cacheUtil.getConfigInfo("REMINDER_MIDDLEGROUND_NOTE_SQL");
        
        logger.info("短信催单县中台:" + sql);
        return this.baseDaoSupport.queryForList(sql);
    }

    @Override
    public String queryIsWorkCustom(String order_id) {
        String sql = " select der.is_work_custom from es_order_ext der where der.order_id='" + order_id + "'";
        return this.baseDaoSupport.queryForString(sql);
    }

    public Map queryDealerCode(String lockOrderIdStr) {
        String sql = "select w.dealer_type as dealer_type,w.dealer_code as dealer_code "
                + " from es_work_custom_node_ins w where w.order_id='" + lockOrderIdStr + "' and w.is_curr_step=1";
        logger.info("订单组织编码查询sql：" + sql);
        return this.baseDaoSupport.queryForMap(sql, null);
    }

    @Override
    public String queryOrgName(String old_ship_name) {
        String sql = "select org.org_name as org_name from es_organization  org where org.party_id='" + old_ship_name
                + "'";
        return this.baseDaoSupport.queryForString(sql);
    }

    @Override
    public String queryCountyCoding(String order_city_code) {
        String sql = "select distinct t.other_field_value from es_dc_public_dict_relation t where stype ='county' "
                + " and t.field_value='" + order_city_code + "'";
        return this.baseDaoSupport.queryForString(sql);
    }
    @Override
    public Page getUserGroupList(int page, int pageSize, String team_level, String team_id, String team_name) {
        String sql = "SELECT decode(a.team_level, 'province', '省公司', 'region', '地市', 'county', '县分', '') as team_level, a.team_id, a.team_name, a.region_name, a.county_name "
                + "FROM es_user_team a where a.source_from='ECS' ";
        if (!StringUtil.isEmpty(team_level)) {
            sql += " and a.team_level='" + team_level + "'";
        }
        if (!StringUtil.isEmpty(team_id)) {
            sql += " and a.team_id='" + team_id + "'";
        }
        if (!StringUtil.isEmpty(team_name)) {
            sql += " and a.team_name like '%" + team_name + "%'";
        }
        String countSql = "select count(*) from (" + sql + ") cord";
        List pList = new ArrayList();
        
        logger.info("订单分配团队查询："+sql);
        return this.baseDaoSupport.queryForPage(sql, countSql, page, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();
                data.put("team_level", rs.getString("team_level"));
                data.put("team_id", rs.getString("team_id"));
                data.put("team_name", rs.getString("team_name"));
                data.put("region_name", rs.getString("region_name"));
                data.put("county_name", rs.getString("county_name"));
                return data;
            }
        }, pList.toArray());        
    }

    @Override
    public Page queryCommerceDevelopChannelReportList(int pageNo, int pageSize,  OrderQueryParams params)  throws Exception{
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        // 规则编号
        String sql = "";
        String sql_count = "";
        StringBuilder sqlBuilder = new StringBuilder();
        String sqls1 = cacheUtil.getConfigInfo("COMMERCE_DEVELOP_CHANNEL_CITY_SQL");
        String sqls2 = cacheUtil.getConfigInfo("COMMERCE_DEVELOP_CHANNEL_CITY_SQL1");
        String sqls3 = cacheUtil.getConfigInfo("COMMERCE_DEVELOP_CHANNEL_CITY_SQL2");
        String sqls4 = cacheUtil.getConfigInfo("COMMERCE_DEVELOP_CHANNEL_CITY_SQL3");
       
        sqlBuilder.append(sqls1);
        sqlBuilder.append(sqls2);
        sqlBuilder.append(sqls3);
        sqlBuilder.append(sqls4);
        sql = sqlBuilder.toString();
        sql_count = cacheUtil.getConfigInfo("COMMERCE_DEVELOP_CHANNEL_CITY_COUNT_SQL");
        String order_from =  cacheUtil.getConfigInfo("COMMERCE_ORDER_FROM");//默认查询的来源
        String order_from_intent =  cacheUtil.getConfigInfo("COMMERCE_ORDER_FROM_INTENT");//默认查询的来源
        if (StringUtils.isEmpty(sql)){
            throw new Exception("统计报表查询SQL配置为空");
        }
        if (StringUtils.isEmpty(sql_count)){
            throw new Exception("统计报表数量查询SQL配置为空");
        }

        List sqlParams = new ArrayList();
        String endTime = params.getCreate_start()+"-01 00:00:00";
        String startTime = params.getCreate_end()+" 00:00:00";
        
        String order_from_c="";
        String order_from_intent_c = "";
        if(!StringUtil.isEmpty(params.getOrder_from())){
            String[] order_froms = params.getOrder_from().split(",");
            for (int i = 0; i < order_froms.length; i++) {//    sql_order_from = sb.substring(0, sb.lastIndexOf(",") - 1);
                if(order_from.contains(order_froms[i])){
                    order_from_c +=order_froms[i]+",";
                }else{
                    order_from_intent_c +=order_froms[i]+",";
                }
            }
        }
        if(StringUtil.isEmpty(params.getOrder_from())){
            order_from_c = "'"+order_from.replace(",", "','")+"'";
            order_from_intent_c = "'"+order_from_intent.replace(",", "','")+"'";
        }else{
            if(StringUtil.isEmpty(order_from_c)){
                order_from_c = "''";
            }else{
                order_from_c = "'"+order_from_c.replace(",", "','")+"'";
            }
            if(StringUtil.isEmpty(order_from_intent_c)){
                order_from_intent_c = "''";
            }else{
                order_from_intent_c = "'"+order_from_intent_c.replace(",", "','")+"'";
            }
        }
        sql = sql.replace("{startTime}", startTime);
        sql = sql.replace("{startTime1}", endTime);//当月的开始日期入 2018-12-01 00：00:00
        sql = sql.replace("{order_from}", order_from_c);
        sql = sql.replace("{order_from_intent}",order_from_intent_c);
        
        sql_count = sql_count.replace("{order_from}", order_from_c);
        sql_count = sql_count.replace("{order_from_intent}",order_from_intent_c);
        
        if(!StringUtil.isEmpty(params.getOrder_city_code())){
            sql = sql.replace("{city_codes}", " and c.col1='"+params.getOrder_city_code()+"' ");
            sql = sql.replace("{city_code}", " and eoet.order_city_code='"+params.getOrder_city_code()+"' ");
            sql = sql.replace("{city_code_intent}", " and a.order_city_code='"+params.getOrder_city_code()+"' ");
            sql_count = sql_count.replace("{city_codes}", " and c.col1='"+params.getOrder_city_code()+"' ");
        }else{
            sql = sql.replace("{city_codes}", "");
            sql = sql.replace("{city_code}", "");
            sql = sql.replace("{city_code_intent}", "");
            sql_count = sql_count.replace("{city_codes}", "");
        }
        if(!StringUtil.isEmpty(params.getOrder_county_code())){
            sql = sql.replace("{county_codes}", " and c.field_value='"+params.getOrder_county_code()+"' ");
            sql = sql.replace("{county_code}", " and eoext.district_code='"+params.getOrder_county_code()+"' ");
            sql = sql.replace("{county_code_intent}", " and a.order_county_code='"+params.getOrder_county_code()+"' ");
            sql_count = sql_count.replace("{county_codes}", " and c.field_value='"+params.getOrder_county_code()+"' ");
        }else{
            sql = sql.replace("{county_code}", "");
            sql = sql.replace("{county_codes}", "");
            sql = sql.replace("{county_code_intent}", "");
            sql_count = sql_count.replace("{county_codes}", "");
        }
        List pList = new ArrayList();
        pList.addAll(sqlParams);
        Page page = null;
        logger.info("统记查询SQL" + sql);
        page = this.baseDaoSupport.queryForPage(sql, sql_count, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();
                data.put("city_code", rs.getString("city_code")); // 地市编码
                data.put("city_name", rs.getString("city_name")); // 地市名称
                data.put("county_code", rs.getString("county_code")); // 县分
                data.put("county_name", rs.getString("county_name")); //名称
                data.put("pkey", rs.getString("pkey")); // 来源
                data.put("pname", rs.getString("pname")); // 来源名称
                data.put("p1", rs.getString("p1")); // 预约竣工率,
                data.put("p2", rs.getString("p2")); // 派单竣工率
                data.put("p3", rs.getString("p3")); // 当日预约量
                data.put("p4", rs.getString("p4")); // 当月累计预约量
                data.put("p5", rs.getString("p5")); // 当日派单量
                data.put("p6", rs.getString("p6")); // 月当累计派单量
                data.put("p7", rs.getString("p7")); // 当日竣工量
                data.put("p8", rs.getString("p8")); // 当月累计竣工量
                data.put("p9", rs.getString("p9")); // 当日订单
                data.put("p10", rs.getString("p10")); // 月累计未处理
                return data;
            }
        }, pList.toArray());
        return page;
    }

    @Override
    public List<Map> getRoleGroup() {
        String sql ="select p.pkey,p.pname from es_dc_public_ext p where p.source_from='ECS' and  p.stype='certificate_type'";
        return this.baseDaoSupport.queryForListByMap(sql, null);
    }

    @Override
    public void messages_send_count(String order_id) {
        
         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        // 查询短信发送次数
        String query_count = " select e.messages_send_count from es_order_extvtl e where e.order_id='" + order_id + "'";
        String count = baseDaoSupport.queryForString(query_count);

        if (!org.apache.commons.lang.StringUtils.isBlank(count)) {
            count = (Integer.parseInt(count) + 1) + "";
        } else {
            count = 1 + "";
        }
        
        String [] str =new String[2];
        str[0] = df.format(new Date());
        str[1] = count;
        
        // 记录短信发送最近发送时间 和发送次数
        CommonDataFactory.getInstance().updateAttrFieldValue(order_id,new String [] {"messages_send_lasttime","messages_send_count"},str);
        
        //String updte_last_time = " update es_order_extvtl e  set  e.messages_send_lasttime=sysdate,e.messages_send_count='"
            //  + count + "' where e.order_id='" + order_id + "' ";
        
    //  this.baseDaoSupport.execute(updte_last_time);
    }
    @Override
    public Page queryBroadbandMonitorList(int pageNo, int pageSize,
            OrderQueryParams params) throws Exception {
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String sql1=cacheUtil.getConfigInfo("BROADBAND_MONITOR_SQL1");
        String sql2=cacheUtil.getConfigInfo("BROADBAND_MONITOR_SQL2");
        String sql_count=cacheUtil.getConfigInfo("COUNT_BROADBAND_MONITOR_SQL");
        String sql=sql1+sql2;
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-01 00:00:00");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        
        List sqlParams = new ArrayList();

        String time=df.format(new Date());
        String DayStarTime=time+" 00:00:00";
        String DayEndTime =time+" 23:59:59";
            
        Date date =df.parse(time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String monthStarTime = sdf1.format(cal.getTime()); //第一天
        Calendar ca = Calendar.getInstance(); 
        ca.add(Calendar.MONTH, 0);    //加一个月
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String monthEndTime = sdf2.format(ca.getTime()); //最后一天
        
        //{MonthStarTime} {MonthEndTime} {DayStarTime} {DayEndTime}
        sql=sql.replace("{DayStarTime}", DayStarTime);
        sql=sql.replace("{DayEndTime}", DayEndTime);
        sql=sql.replace("{MonthStarTime}", monthStarTime);
        sql=sql.replace("{MonthEndTime}", monthEndTime);
        
        List pList = new ArrayList();
        pList.addAll(sqlParams);
        
        Page page = null;
        page = this.baseDaoSupport.queryForPage(sql, sql_count, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();

                data.put("order_city", rs.getString("order_city")); 
                data.put("sum00", rs.getString("sum00"));
                data.put("sum0", rs.getString("sum0"));
                data.put("sum01", rs.getString("sum01"));
                data.put("sum1", rs.getString("sum1"));
                data.put("rate1", rs.getString("rate1"));
                data.put("sum02", rs.getString("sum02"));
                data.put("sum2", rs.getString("sum2"));
                data.put("rate2", rs.getString("rate2"));
                data.put("sum03", rs.getString("sum03"));
                data.put("sum3", rs.getString("sum3")); 
                data.put("sum04", rs.getString("sum04")); 
                data.put("sum4", rs.getString("sum4")); 
                data.put("sum05", rs.getString("sum05")); 
                data.put("sum5", rs.getString("sum5")); 
                data.put("sum06", rs.getString("sum06")); 
                data.put("sum6", rs.getString("sum6")); 
                data.put("sum07", rs.getString("sum07")); 
                data.put("sum7", rs.getString("sum7")); 

                return data;
            }
        }, pList.toArray());
        
        return page;
    }

    @Override
    public List queryBroadbandMonitorListDtl(OrderQueryParams params) throws Exception {
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        // 规则编号
        String sql = "";
        String sql1=cacheUtil.getConfigInfo("BROADBAND_MONITOR_SQL1");
        String sql2=cacheUtil.getConfigInfo("BROADBAND_MONITOR_SQL2");
        String sql_count=cacheUtil.getConfigInfo("COUNT_BROADBAND_MONITOR_SQL");
        sql=sql1+sql2;
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-01 00:00:00");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        
        List sqlParams = new ArrayList();
        String time=df.format(new Date());
        String DayStarTime=time+" 00:00:00";
        String DayEndTime =time+" 23:59:59";
        
        
        
        
        Date date =df.parse(time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String monthStarTime = sdf1.format(cal.getTime()); //第一天
        Calendar ca = Calendar.getInstance(); 
        ca.add(Calendar.MONTH, 0);    //加一个月
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String monthEndTime = sdf2.format(ca.getTime()); //最后一天
        
        //{MonthStarTime} {MonthEndTime} {DayStarTime} {DayEndTime}
        sql=sql.replace("{DayStarTime}", DayStarTime);
        sql=sql.replace("{DayEndTime}", DayEndTime);
        sql=sql.replace("{MonthStarTime}", monthStarTime);
        sql=sql.replace("{MonthEndTime}", monthEndTime);
        
        List ret = this.baseDaoSupport.queryForList(sql);
        return ret;
    }
    
    @Override
    public Page queryBroadbandMonitorList_His(int pageNo, int pageSize,
            OrderQueryParams params) {
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
//      String sql=cacheUtil.getConfigInfo("BROADBAND_MONITOR_SQL_HIS");
//      String sql_count=cacheUtil.getConfigInfo("COUNT_BROADBAND_MONITOR_SQL");
        String sql="    select city_name,book_day_count,book_month_count,receive_day_count,receive_month_count,                                         "
                  +"    avg_receive_time,unreceive_day_count,unreceive_month_count,distribute_day_count,                                                "
                  +"    distribute_month_count,avg_distribute_time,distribute_return_day_count,distribute_return_month_count,                           "
                  +"    hang_day_count,hang_month_count,complete_day_count,complete_month_count,outside_return_day_count,outside_return_month_count     "
                  +"    from es_broadband_monitor_his a where  create_time='{starTime}'                                                                 ";  
        
        String sql_count=" select count(1) from es_broadband_monitor_his a where a.create_time='{starTime}'  and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
        List sqlParams = new ArrayList();
        
        sql_count=sql_count.replace("{starTime}",  params.getCreate_start());
        sql=sql.replace("{starTime}",  params.getCreate_start());
//      sql=sql.replace("{sourceFrom}", ManagerUtils.getSourceFrom());
        sql=sql+"and source_from='"+ManagerUtils.getSourceFrom()+"'";
        List pList = new ArrayList();
        pList.addAll(sqlParams);
        
        Page page = null;
        page = this.baseDaoSupport.queryForPage(sql, sql_count, pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();

                data.put("order_city", rs.getString("city_name")); 
                data.put("sum00", rs.getString("book_day_count"));
                data.put("sum0", rs.getString("book_month_count"));
                data.put("sum01", rs.getString("receive_day_count"));
                data.put("sum1", rs.getString("receive_month_count"));
                data.put("rate1", rs.getString("avg_receive_time"));
                data.put("sum02", rs.getString("unreceive_day_count"));
                data.put("sum2", rs.getString("unreceive_month_count"));
                data.put("rate2", rs.getString("avg_distribute_time"));
                data.put("sum03", rs.getString("distribute_day_count"));
                data.put("sum3", rs.getString("distribute_month_count")); 
                data.put("sum04", rs.getString("distribute_return_day_count")); 
                data.put("sum4", rs.getString("distribute_return_month_count")); 
                data.put("sum05", rs.getString("hang_day_count")); 
                data.put("sum5", rs.getString("hang_month_count")); 
                data.put("sum06", rs.getString("complete_day_count")); 
                data.put("sum6", rs.getString("complete_month_count")); 
                data.put("sum07", rs.getString("outside_return_day_count")); 
                data.put("sum7", rs.getString("outside_return_month_count")); 

                return data;
            }
        }, pList.toArray());
        
        return page;
        
    }
    
    @Override
    public List queryBroadbandMonitorListDtl_His(OrderQueryParams params) {
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
//      String sql=cacheUtil.getConfigInfo("BROADBAND_MONITOR_SQL_HIS");
        String sql="    select city_name,book_day_count,book_month_count,receive_day_count,receive_month_count,                                         "
                  +"    avg_receive_time,unreceive_day_count,unreceive_month_count,distribute_day_count,                                                "
                  +"    distribute_month_count,avg_distribute_time,distribute_return_day_count,distribute_return_month_count,                           "
                  +"    hang_day_count,hang_month_count,complete_day_count,complete_month_count,outside_return_day_count,outside_return_month_count     "
                  +"    from es_broadband_monitor_his  where  create_time='{starTime}'                                                              ";  
        
        
        sql=sql.replace("{starTime}",  params.getCreate_start());
//      sql=sql.replace("{sourceFrom}", ManagerUtils.getSourceFrom());
        sql=sql+"and source_from='"+ManagerUtils.getSourceFrom()+"'";
        
        List ret = this.baseDaoSupport.queryForList(sql);
        return ret;
    }

    @Override
    public List queryCommerceDevelopChannelReportListExcel(OrderQueryParams params) throws Exception {
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        // 规则编号
        String sql = "";
        StringBuilder sqlBuilder = new StringBuilder();
        String sqls1 = cacheUtil.getConfigInfo("COMMERCE_DEVELOP_CHANNEL_CITY_SQL");
        String sqls2 = cacheUtil.getConfigInfo("COMMERCE_DEVELOP_CHANNEL_CITY_SQL1");
        String sqls3 = cacheUtil.getConfigInfo("COMMERCE_DEVELOP_CHANNEL_CITY_SQL2");
        String sqls4 = cacheUtil.getConfigInfo("COMMERCE_DEVELOP_CHANNEL_CITY_SQL3");
       
        sqlBuilder.append(sqls1);
        sqlBuilder.append(sqls2);
        sqlBuilder.append(sqls3);
        sqlBuilder.append(sqls4);
        sql = sqlBuilder.toString();
        String order_from =  cacheUtil.getConfigInfo("COMMERCE_ORDER_FROM");//默认查询的来源
        String order_from_intent =  cacheUtil.getConfigInfo("COMMERCE_ORDER_FROM_INTENT");//默认查询的来源
        if (StringUtils.isEmpty(sql)){
            throw new Exception("统计报表查询SQL配置为空");
        }
        List sqlParams = new ArrayList();
        String endTime = params.getCreate_start()+"-01 00:00:00";
        String startTime = params.getCreate_end()+" 00:00:00";
        String order_from_c="";
        String order_from_intent_c = "";
        if(!StringUtil.isEmpty(params.getOrder_from())){
            String[] order_froms = params.getOrder_from().split(",");
            for (int i = 0; i < order_froms.length; i++) {//    sql_order_from = sb.substring(0, sb.lastIndexOf(",") - 1);
                if(order_from.contains(order_froms[i])){
                    order_from_c +=order_froms[i]+",";
                }else{
                    order_from_intent_c +=order_froms[i]+",";
                }
            }
        }
        if(StringUtil.isEmpty(params.getOrder_from())){
            order_from_c = "'"+order_from.replace(",", "','")+"'";
            order_from_intent_c = "'"+order_from_intent.replace(",", "','")+"'";
        }else{
            if(StringUtil.isEmpty(order_from_c)){
                order_from_c = "''";
            }else{
                order_from_c = "'"+order_from_c.replace(",", "','")+"'";
            }
            if(StringUtil.isEmpty(order_from_intent_c)){
                order_from_intent_c = "''";
            }else{
                order_from_intent_c = "'"+order_from_intent_c.replace(",", "','")+"'";
            }
        }
        sql = sql.replace("{startTime}", startTime);
        sql = sql.replace("{startTime1}", endTime);//当月的开始日期入 2018-12-01 00：00:00
        sql = sql.replace("{order_from}", order_from_c);
        sql = sql.replace("{order_from_intent}",order_from_intent_c);
        
        
        if(!StringUtil.isEmpty(params.getOrder_city_code())){
            sql = sql.replace("{city_codes}", " and c.col1='"+params.getOrder_city_code()+"' ");
            sql = sql.replace("{city_code}", " and eoet.order_city_code='"+params.getOrder_city_code()+"' ");
            sql = sql.replace("{city_code_intent}", " and a.order_city_code='"+params.getOrder_city_code()+"' ");
        }else{
            sql = sql.replace("{city_codes}", "");
            sql = sql.replace("{city_code}", "");
            sql = sql.replace("{city_code_intent}", "");
        }
        if(!StringUtil.isEmpty(params.getOrder_county_code())){
            sql = sql.replace("{county_codes}", " and c.field_value='"+params.getOrder_county_code()+"' ");
            sql = sql.replace("{county_code}", " and eoext.district_code='"+params.getOrder_county_code()+"' ");
            sql = sql.replace("{county_code_intent}", " and a.order_county_code='"+params.getOrder_county_code()+"' ");
        }else{
            sql = sql.replace("{county_code}", "");
            sql = sql.replace("{county_codes}", "");
            sql = sql.replace("{county_code_intent}", "");
        }
        List ret = this.baseDaoSupport.queryForList(sql);
        return ret;
    }

    @Override
    public Page findByCommerChannel(int pageNo, int pageSize,OrderQueryParams params) throws Exception {
        List sqlParams = new ArrayList();
        StringBuilder sbBuilder = new StringBuilder();
        sbBuilder.append("select t.city_name,t.county_name,t.pname as pname,t.p1,t.p2,t.p3,t.p4,t.p5,t.p6,t.p7,t.p8,t.p9,t.p10 from es_commerce_channel_count t  ");
        sbBuilder.append( "where t.source_from='").append(ManagerUtils.getSourceFrom()).append("'");
        StringBuilder sbBuilder_count = new StringBuilder();
        sbBuilder_count.append("select count(1) from es_commerce_channel_count t  ");
        sbBuilder_count.append( "where t.source_from='").append(ManagerUtils.getSourceFrom()).append("'");
        
        if(!StringUtil.isEmpty(params.getOrder_city_code())){
            sbBuilder.append(" and t.city_code ='").append(params.getOrder_city_code()).append("'");
            sbBuilder_count.append(" and t.city_code ='").append(params.getOrder_city_code()).append("'");
        }
        if(!StringUtil.isEmpty(params.getOrder_county_code())){
            sbBuilder.append(" and t.county_code ='").append(params.getOrder_county_code()).append("'");
            sbBuilder_count.append(" and t.county_code ='").append(params.getOrder_county_code()).append("'");
        }
        if(!StringUtil.isEmpty(params.getOrder_from())){
            sbBuilder.append(" and t.pkey in ('").append(params.getOrder_from().replace(",", "','")).append("')");
            sbBuilder_count.append(" and t.pkey in ('").append(params.getOrder_from().replace(",", "','")).append("')");

        }
        if(!StringUtil.isEmpty(params.getCreate_start())){
            sbBuilder.append(" and t.old_date='").append(params.getCreate_start()).append("'");
            sbBuilder_count.append(" and t.old_date='").append(params.getCreate_start()).append("'");
        }
        sbBuilder.append(" order by t.city_code ");
        List pList = new ArrayList();
        pList.addAll(sqlParams);
        Page page = null;
        page = this.baseDaoSupport.queryForPage(sbBuilder.toString(), sbBuilder_count.toString(), pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                Map data = new HashMap();
                //data.put("city_code", rs.getString("city_code")); // 地市编码
                data.put("city_name", rs.getString("city_name")); // 地市名称
                //data.put("county_code", rs.getString("county_code")); // 县分
                data.put("county_name", rs.getString("county_name")); //名称
               // data.put("pkey", rs.getString("pkey")); // 来源
                data.put("pname", rs.getString("pname")); // 来源名称
                data.put("p1", rs.getString("p1")); // 预约竣工率,
                data.put("p2", rs.getString("p2")); // 派单竣工率
                data.put("p3", rs.getString("p3")); // 当日预约量
                data.put("p4", rs.getString("p4")); // 当月累计预约量
                data.put("p5", rs.getString("p5")); // 当日派单量
                data.put("p6", rs.getString("p6")); // 月当累计派单量
                data.put("p7", rs.getString("p7")); // 当日竣工量
                data.put("p8", rs.getString("p8")); // 当月累计竣工量
                data.put("p9", rs.getString("p9")); // 当日订单
                data.put("p10", rs.getString("p10")); // 月累计未处理
                return data;
            }
        }, pList.toArray());
        return page;
    }

    @Override
    public List findByCommerChannelExcel(OrderQueryParams params) {
        StringBuilder sbBuilder = new StringBuilder();
        sbBuilder.append("select t.city_name,t.county_name,t.pname as pname,t.p1,t.p2,t.p3,t.p4,t.p5,t.p6,t.p7,t.p8,t.p9,t.p10 from es_commerce_channel_count t  ");
        sbBuilder.append( "where t.source_from='").append(ManagerUtils.getSourceFrom()).append("'");
        if(!StringUtil.isEmpty(params.getOrder_city_code())){
            sbBuilder.append(" and t.city_code ='").append(params.getOrder_city_code()).append("'");
        }
        if(!StringUtil.isEmpty(params.getOrder_county_code())){
            sbBuilder.append(" and t.county_code ='").append(params.getOrder_county_code()).append("'");
        }
        if(!StringUtil.isEmpty(params.getOrder_from())){
            sbBuilder.append(" and t.pkey in ('").append(params.getOrder_from().replace(",", "','")).append("')");

        }
        if(!StringUtil.isEmpty(params.getCreate_start())){
            sbBuilder.append(" and t.old_date='").append(params.getCreate_start()).append("'");
        }
        sbBuilder.append(" order by t.city_code ");
        List ret = this.baseDaoSupport.queryForList(sbBuilder.toString());
        return ret;
    }
    
    //add by cqq 20181128 自定义流程-获取当前环节信息
    public String queryTraceName(String order_id) {
        String sql = "select cni.node_name from es_work_custom_node_ins cni where cni.is_curr_step='1' and order_id = ?";
        return this.baseDaoSupport.queryForString(sql,order_id);
    }
    
    /**
     * 获取属性配置信息
     * @param field_names 字段名
     * @return 以表名为键值的属性MAP
     * @throws Exception
     */
    private Map<String,List<ATTR_FIELD_TABLE>> getFieldTables(List<String> field_names) throws Exception{
        List<ATTR_FIELD_TABLE> list = new ArrayList<ATTR_FIELD_TABLE>();
        
        String baseSql = "SELECT a.field_name,a.attr_spec_type,b.table_name,b.field_name as col_name"
                +" FROM es_attr_def a,es_attr_table b WHERE a.attr_spec_id='-999' "
                +" and a.field_attr_id=b.attr_def_id and a.source_from='ECS' ";
        
        ATTR_FIELD_TABLE pojo = new ATTR_FIELD_TABLE();
        
        List<SqlBuilderInterface> sqlBuilds = new ArrayList<SqlBuilderInterface>();
        SqlInBuilder field_name = new SqlInBuilder("field_name", field_names);
        sqlBuilds.add(field_name);
        
        list = this.baseDaoSupport.queryPojoList(baseSql, pojo, sqlBuilds);
        
        Map<String,List<ATTR_FIELD_TABLE>> result = new HashMap<String,List<ATTR_FIELD_TABLE>>();
        
        if(list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                String table_name = list.get(i).getTable_name();
                
                if(result.containsKey(table_name)){
                    List<ATTR_FIELD_TABLE> temp = result.get(table_name);
                    
                    temp.add(list.get(i));
                    
                    result.put(table_name, temp);
                }else{
                    List<ATTR_FIELD_TABLE> temp = new ArrayList<ATTR_FIELD_TABLE>();
                    
                    temp.add(list.get(i));
                    
                    result.put(table_name, temp);
                }
            }
        }
        
        return result;
    }
    
    /**
     * 查询属性值
     * @param order_id  订单编号
     * @param table_name 表名
     * @param attrs  属性配置
     * @return
     * @throws Exception
     */
    private Map getAttrValues(String order_id,String table_name,List<ATTR_FIELD_TABLE> attrs) throws Exception{
        Map result = new HashMap();
        
        if(org.apache.commons.lang.StringUtils.isBlank(order_id)
                || org.apache.commons.lang.StringUtils.isBlank(table_name)
                || attrs==null || attrs.size()==0)
            return result;
        
        StringBuilder builder = new StringBuilder();
        
        builder.append(" select ");
        
        for(int i=0;i<attrs.size();i++){
            if(i!=0)
                builder.append(" , ");
            
            ATTR_FIELD_TABLE attr = attrs.get(i);
            builder.append(" a.").append(attr.getCol_name()).append(" as ").append(attr.getField_name());
        }
        
        builder.append(" from ").append(table_name).append(" a where ");
        builder.append(" a.order_id='").append(order_id).append("' ");
        builder.append(" and a.source_from='ECS' ");
        
        List ret = this.baseDaoSupport.queryForList(builder.toString());
        
        //只取第一行数据
        if(ret!=null && ret.size()>0)
            result = (Map)ret.get(0);
        
        return result;
    }
    
    /**
     * 查询订单状态
     * @param order_id
     * @return
     * @throws Exception
     */
    private Map getOrderState(String order_id) throws Exception{
        Map result = new HashMap();
        
        String sql = "SELECT a.order_state, a.status, b.abnormal_status, b.refund_deal_type "
                +" FROM es_order a, es_order_ext b"
                +" WHERE a.source_from='ECS' and a.order_id=b.order_id and b.order_id='"+order_id+"'";
        
        List ret = this.baseDaoSupport.queryForList(sql);
        
        if(ret!=null && ret.size()>0){
            Map temp = (Map)ret.get(0);
            
            String order_state = Const.getStrValue(temp, "order_state");
            String status = Const.getStrValue(temp, "status");
            String abnormal_status = Const.getStrValue(temp, "abnormal_status");
            String refund_deal_type = Const.getStrValue(temp, "refund_deal_type");
            
            if("02".equals(refund_deal_type)){
                result.put("order_state", "01");//退单
            }else if("11".equals(status)){
                result.put("order_state", "02");//外呼
            }else if("1".equals(abnormal_status)){
                result.put("order_state", "03");//异常
            }else{
                result.put("order_state", "00");//正常
            }
        }
        
        return result;
    }
    
    /**
     * 获取订单外部操作员（下单人信息）
     * @param order_id
     * @param info
     * @return
     * @throws Exception
     */
    private Map getOrderOutInfo(String order_id,Map info) throws Exception{
        Map result = new HashMap();
        
        String out_operator = String.valueOf(info.get("out_operator"));
        String out_office = String.valueOf(info.get("out_office"));
        
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        
        //获取外部操作员姓名（下单人姓名）
        if(org.apache.commons.lang.StringUtils.isNotBlank(out_operator)
                && !"null".equals(out_operator)){
            
            String sql = "select a.user_name from ZJUCRM2O.TAB_USER@to_crmdb1 a ";
            
            String str = cacheUtil.getConfigInfo("GET_OUT_OPERATOR_NAME_SQL");
            if(org.apache.commons.lang.StringUtils.isNotBlank(str))
                sql = str;
            
            sql = sql +" where a.USER_CODE='"+out_operator+"' ";
            
            List ret = this.baseDaoSupport.queryForList(sql);
            
            if(ret!=null && ret.size()>0){
                Map temp = (Map)ret.get(0);
                
                String user_name = Const.getStrValue(temp, "user_name");
                
                result.put("out_operator_name", user_name);
            }
        }
        
        
        //获取外部操点名称（下单点名称）
        if(org.apache.commons.lang.StringUtils.isNotBlank(out_office)
                && !"null".equals(out_office)){
            
            String sql = "select a.channel_name from ZJUCRM2O.agent_channel@TO_CRMDB1 a ";
            
            String str = cacheUtil.getConfigInfo("GET_OUT_OFFICE_NAME_SQL");
            if(org.apache.commons.lang.StringUtils.isNotBlank(str))
                sql = str;
            
            sql = sql +" where a.CHANNEL_ID='"+out_office+"' or a.channel_hq_code='"+out_office+"' ";
            
            List ret = this.baseDaoSupport.queryForList(sql);
            
            if(ret!=null && ret.size()>0){
                Map temp = (Map)ret.get(0);
                
                String channel_name = Const.getStrValue(temp, "channel_name");
                
                result.put("out_office_name", channel_name);
            }
        }

        //发展人姓名
        String develop_code_new = String.valueOf(info.get("develop_code_new"));
        if(org.apache.commons.lang.StringUtils.isNotBlank(develop_code_new)
                && !"null".equals(develop_code_new)){
            
            String sql = "SELECT T1.CHANNEL_NAME FROM ZJUCRM2O.AGENT_CHANNEL@TO_CRMDB1 T1 "
            		+ " WHERE T1.CHANNEL_ID='{CODE}' UNION ALL "
            		+ " SELECT T2.CHANNEL_NAME FROM ZJUCRM2O.AGENT_CHANNEL_HQ@TO_CRMDB1 T2 "
            		+ " WHERE T2.CHANNEL_HQ_CODE='{CODE}' AND T2.CHNL_KIND_ID='999' ";
            
            String str = cacheUtil.getConfigInfo("GET_DEVELOP_NAME_SQL");
            if(org.apache.commons.lang.StringUtils.isNotBlank(str))
                sql = str;
            
            sql = sql.replace("{CODE}", develop_code_new);
            
            List ret = this.baseDaoSupport.queryForList(sql);
            
            if(ret!=null && ret.size()>0){
                Map temp = (Map)ret.get(0);
                
                String CHANNEL_NAME = Const.getStrValue(temp, "CHANNEL_NAME");
                
                result.put("develop_name_new", CHANNEL_NAME);
            }
        }
        
        //发展点名称
        String develop_point_code_new = String.valueOf(info.get("develop_point_code_new"));
        if(org.apache.commons.lang.StringUtils.isNotBlank(develop_point_code_new)
                && !"null".equals(develop_point_code_new)){
            
            String sql = "SELECT T1.CHANNEL_NAME FROM ZJUCRM2O.AGENT_CHANNEL@TO_CRMDB1 T1 ";
            
            String str = cacheUtil.getConfigInfo("GET_DEVELOP_POINT_NAME_SQL");
            if(org.apache.commons.lang.StringUtils.isNotBlank(str))
                sql = str;
            
            sql = sql +" WHERE T1.CHANNEL_ID='"+develop_point_code_new+"' OR T1.CHANNEL_HQ_CODE='"+develop_point_code_new+"' ";
            
            List ret = this.baseDaoSupport.queryForList(sql);
            
            if(ret!=null && ret.size()>0){
                Map temp = (Map)ret.get(0);
                
                String CHANNEL_NAME = Const.getStrValue(temp, "CHANNEL_NAME");
                
                result.put("development_point_name", CHANNEL_NAME);
            }
        }
        
        return result;
    }
    
    private Map getPaySeq(Map info) throws Exception{
        Map result = new HashMap();
        
        String payplatformorderid = String.valueOf(info.get("payplatformorderid"));     
        
        //有支付流水沉淀
        if(org.apache.commons.lang.StringUtils.isNotBlank(payplatformorderid)
                && !"null".equals(payplatformorderid)){
            
            String[] arr = payplatformorderid.split("\\|");
            
            if(arr!=null && arr.length>0){
                result.put("pay_sequ", arr[0]);             
            }
            
            if(arr!=null && arr.length>1){
                result.put("pay_back_sequ", arr[1]);        
            }
        }
        
        return result;
    }
    
    

    /**
     * 批量获取属性值
     * @param order_id  订单编号
     * @param field_names  属性字段名称
     * @return
     * @throws Exception
     */
    @Override
    public Map getAttrValuesBatch(String order_id, List<String> field_names)
            throws Exception {
        Map result = new HashMap();
        
        if(org.apache.commons.lang.StringUtils.isBlank(order_id) || field_names==null 
                || field_names.size()==0)
            return result;
        
        //获取需要查询的表和字段
        Map<String, List<ATTR_FIELD_TABLE>> tableAttrMap = this.getFieldTables(field_names);
        
        if(tableAttrMap!=null && tableAttrMap.size()>0){
            Iterator<Entry<String, List<ATTR_FIELD_TABLE>>> it = tableAttrMap.entrySet().iterator();
            
            for(;it.hasNext();){
                Entry<String, List<ATTR_FIELD_TABLE>> entry = it.next();
                
                String table_name = entry.getKey();
                List<ATTR_FIELD_TABLE> attrList = entry.getValue();
                
                //查询字段
                Map temp = this.getAttrValues(order_id, table_name, attrList);
                
                result.putAll(temp);
            }
        }
        
        //查询订单状态
        result.putAll(this.getOrderState(order_id));
        
        result.putAll(this.getOrderOutInfo(order_id, result));
        
        result.putAll(this.getPaySeq(result));
        
        //tid_time
        SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
        
        Iterator it = result.entrySet().iterator();
        for(;it.hasNext();){
            Entry entry = (Entry)it.next();
            
            String key = String.valueOf(entry.getKey());
            
            Object obj = entry.getValue();
            
            if (obj instanceof Date || obj instanceof java.sql.Date) {
                entry.setValue(format.format(obj));
            }
        }

        return result;
    }

    @Override
    public List<Map> queryInstanceId(String order_id) {
        String sql ="select ins.instance_id,ins.deal_url from es_work_custom_node_ins ins where ins.source_from='ECS' and ins.order_id ='"+order_id+"' and  ins.is_curr_step='1'";
        return this.baseDaoSupport.queryForList(sql);
    }

    @Override
    public String queryIccId(String order_id) {
        String sql ="select ext.iccid from es_order_extvtl ext where ext.source_from='ECS' and ext.order_id='"+order_id+"'";
        return this.baseDaoSupport.queryForString(sql);
    }

    @Override
    public String queryWorkCustomIsLink() {
        String sql="select inf.cf_value from es_config_info inf where inf.source_from='ECS' and inf.cf_id = 'Is_Work_Custeam_Bean_Bss_CBss'";
        return this.baseDaoSupport.queryForString(sql);
    }

    @Override
    public String queryisWorkCustom(String order_id) {
        String sql = "select ext.is_work_custom from es_order_ext ext where ext.source_from='ECS' and ext.order_id='"+order_id+"'";
        return this.baseDaoSupport.queryForString(sql);
    }

    public  List<Map> querTeamId(String user_id) {
        String sql="select rel.team_id as team_id from es_user_team_rel rel where rel.source_from='ECS' and  rel.userid='"+user_id+"'";
        return this.baseDaoSupport.queryForList(sql);
    }

    @Override
    public void updateWorkCustom(String order_id) {
        String user_id = ManagerUtils.getAdminUser().getUserid();
        String user_name = ManagerUtils.getAdminUser().getRealname();
        // 更新自定义流程表
        String workSql = "update es_work_custom_node_ins ins set ins.curr_user_id = '" + user_id
                + "', ins.curr_user_name = '" + user_name
                + "', ins.is_lock_flag = '1' where ins.source_from = 'ECS' and ins.is_curr_step = '1' and ins.order_id = '"
                + order_id + "'";
        this.baseDaoSupport.execute(workSql);
    }
    //add by cqq 20181218 自定义流程
    public void updateOrderTreeCustom(String set_sql,String table_name,String order_id,OrderTreeBusiRequest orderTree) throws Exception{
        String sql ="update "+table_name+" set "+set_sql+" where order_id=?";
        if(this.baseDaoSupport==null){
            this.baseDaoSupport=SpringContextHolder.getBean("baseDaoSupport");
        }
        this.baseDaoSupport.execute(sql, order_id);
        //更新订单树缓存
        cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY+"order_id"+ order_id,orderTree, RequestStoreManager.time);
        
    }
    

    @Override
    public Map<String,List<ATTR_OPTIONS>> getAttrOptions(List<String> option_list) throws Exception {
        Map<String,List<ATTR_OPTIONS>> result = new HashMap<String,List<ATTR_OPTIONS>>();
        
        if(option_list==null || option_list.size()==0)
            return result;
        
        List<ATTR_OPTIONS> list = new ArrayList<ATTR_OPTIONS>();
        
        String baseSql = " select a.stype, a.pkey value,a.pname value_desc from es_dc_public_ext a where "
                +" a.source_from='ECS' order by a.stype,a.sortby ";
        
        ATTR_OPTIONS pojo = new ATTR_OPTIONS();
        
        List<SqlBuilderInterface> sqlBuilds = new ArrayList<SqlBuilderInterface>();
        SqlInBuilder stypeBuilder = new SqlInBuilder("stype", option_list);
        sqlBuilds.add(stypeBuilder);
        
        list = this.baseDaoSupport.queryPojoList(baseSql, pojo, sqlBuilds);
        
        if(list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                String stype = list.get(i).getStype();
                
                if(result.containsKey(stype)){
                    List<ATTR_OPTIONS> temp = result.get(stype);
                    
                    temp.add(list.get(i));
                    
                    result.put(stype, temp);
                }else{
                    List<ATTR_OPTIONS> temp = new ArrayList<ATTR_OPTIONS>();
                    
                    temp.add(list.get(i));
                    
                    result.put(stype, temp);
                }
            }
        }
        
        return result;
    }

    @Override
    public Map getOperatorInfo(String order_id){
    	Map map = new HashMap();
    	try{
    		map.put("out_operator", CommonDataFactory.getInstance().getAttrFieldValue(order_id, "out_operator"));
    		map.put("out_office", CommonDataFactory.getInstance().getAttrFieldValue(order_id, "out_office"));
        	map.putAll(getOrderOutInfo(order_id,map)); 
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return map;
    }

    
    /**
     * 简单订单信息查询
     */
    @Override
    public OrderListActivateResp queryOrderActivate(OrderListActivateReq requ) {
        OrderListActivateResp resp=new OrderListActivateResp();
        String templet_id=requ.getTemplet_id();
        OrderQueryActivateInfo query_info = requ.getQuery_info();
        try {
            
            if ("01".equals(templet_id)) {//业务模板:01.身份证后六位和SIM卡后七位查询模板
                String iccid_num = query_info.getIccid_num();
                String cert_num = query_info.getCert_num();
                String source_from = ManagerUtils.getSourceFrom();
                StringBuffer sql =new StringBuffer();
                if (StringUtil.isEmpty(iccid_num)||iccid_num.length()!=7) {
                    resp.setResp_code("1");
                    resp.setResp_msg("SIM号输入不对");
                    return resp;
                }else {
                    sql.append("select eoie.order_id, eoie.phone_num from es_order_items_ext eoie ").
                    append("inner join es_order_ext ext on eoie.order_id = ext.order_id ").
                    append("where 1 = 1 ").
                    append("and eoie.source_from = '").
                    append(source_from).
                    append("' ").
                    append("and ext.order_from = '").
                    append(requ.getSource_system()).
                    append("' ").
                    append("and exists (select extvtl.iccid from es_order_extvtl extvtl where eoie.order_id = extvtl.order_id ").
                    append("and extvtl.iccid is not null ").
                    append("and substr(extvtl.iccid, length(extvtl.iccid) - 6, 7) = '").
                    append(iccid_num).
                    append("') ");
                }
                
                if (!StringUtil.isEmpty(cert_num)) {
                    if (cert_num.length()!=6) {
                        resp.setResp_code("1");
                        resp.setResp_msg("身份证号位数不对");
                        return resp;
                    }else {
                        sql.append(" and exists (select eoipe.cert_card_num from es_order_items_prod_ext eoipe where eoie.order_id = eoipe.order_id ").
                        append("and eoipe.cert_card_num is not null ").
                        append("and substr(eoipe.cert_card_num,length(eoipe.cert_card_num) - 5,6) = '").
                        append(cert_num).
                        append("') ");
                    }
                }
                logger.info("简单订单信息查询接口:" + sql);
                List<Map<String,Object>> list = this.baseDaoSupport.queryForList(sql.toString());
                resp.setResp_code("0");// 1失败、0成功
                resp.setResp_msg("查询成功");// 失败原因
                resp.setQuery_list(list);
                logger.info("订单信息查询结果"+resp.getQuery_list().toString());
            }else {
                resp.setResp_code("1");// 1失败、0成功
                resp.setResp_msg("templet_id输入错误");// 失败原因
            }
            
        } catch (Exception e) {
            resp.setResp_code("1");
            resp.setResp_msg("异常错误2：" + ((e.getMessage() == null ? e.toString() : e.getMessage())));
            e.printStackTrace();
        }
        
        return resp;
    }

    /**
     * 获取用户流程环节权限
     * @return
     */
    private String getUserFlowTraceAuth(){
    	//不管授权取自定义流程的环节
    	DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> list = dcPublicCache.getList("DIC_ORDER_NODE");
		
		StringBuilder builder = new StringBuilder();
		
		if (list!=null && list.size()>0){
			
			Iterator<Map> it = list.iterator();
			for (;it.hasNext();){
				Map m = it.next();
				
				if ("workCustom".equals(m.get("CODEC"))){
					if (builder.length() != 0){
						builder.append(",");
					}
					
					builder.append("'"+m.get("pkey")+"'");
				}
			}
		}
		
		//取权限配置中的环节授权
		AdminUser user = ManagerUtils.getAdminUser();
        String tacheAuths = user.getTacheAuth();
        
        if (org.apache.commons.lang.StringUtils.isNotEmpty(tacheAuths)){
        	if (builder.length() > 0){
        		builder.append(",");
        	}
        	
        	builder.append(tacheAuths);
        }
		
		return builder.toString();
    }

	@Override
	public List<String> getLogiCompanyCodeList() {
		
		String deliverySql ="select c.id from es_logi_company c where c.is_start='1'";
	
		return this.baseDaoSupport.queryForList(deliverySql, null);
	}
    
    public Map gettopSeedInfo(String order_id){
    	Map map = null;
    	try{
    		String sql = " select market_user_id,seed_user_id,share_svc_num,top_share_userid,top_share_num,activity_name, "
    				   + " (select pname from es_dc_public_ext a where a.stype='top_seed_professional_line' and a.pkey= top_seed_professional_line) as top_seed_professional_line, "
    				   + " (select pname from es_dc_public_ext a where a.stype='top_seed_type' and a.pkey= top_seed_type) as top_seed_type,"
    				   + " (select pname from es_dc_public_ext a where a.stype='top_seed_group_id' and a.pkey= top_seed_group_id) as top_seed_group from es_order_extvtl  where order_id= ? ";
    		List<Map<String,Object>> list = this.baseDaoSupport.queryForList(sql,new String[]{order_id});
    		if(list.size()>0){
    			map = list.get(0);
    		}else{
    			map = new HashMap();
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    		map = new HashMap();
    	}
    	return map;
    }

	@Override
	public String getFlowCode(String order_id) {
		return getFlowCodeNew(order_id);
	}

	/**
	 * 蜂行动
	 */
	@Override
	public Page queryBeeActionIntentMidList(int pageNo, int pageSize, OrderQueryParams params) throws Exception {
		 StringBuffer sql = new StringBuffer();
		 sql.append("select a.*,org.org_name from （select * from es_order_intent_mid union all select * from es_order_intent_mid_his） a, es_organization org"
		 		+ " where a.num_city = org.org_code and a.source_from = 'ECS' ");
		if(params != null) {
			
			if(!(params.getOut_tid() == null || params.getOut_tid().equals(""))) {
				sql.append(" and a.order_id = '").
					append(params.getOut_tid()).
					append("' ");
			}
			if(!(params.getCert_card_num() == null || params.getCert_card_num().equals("")) ) {
				sql.append("and a.pspt_id = '").
				append(params.getCert_card_num()).
				append("' ");
			}
			
			if(!(params.getContact_name() == null || params.getContact_name().equals(""))) {
				sql.append("and a.contact_name = '").
				append(params.getContact_name()).
				append("' ");
			}
			
			if(!(params.getContact_phone() == null || params.getContact_phone().equals(""))) {
				sql.append("and a.contact_phone = '").
				append(params.getContact_phone()).
				append("' ");
			}
			
			if(!(params.getOrder_city_code() != null || params.getOrder_city_code().equals(""))) {
				sql.append("and a.num_city = '").
				append(params.getOrder_city_code()).
				append("' ");
			}
			

			if(!(params.getSerial_number() == null || params.getSerial_number().equals(""))) {
				sql.append("and a.serial_number = '").
				append(params.getSerial_number()).
				append("' ");
			}
			
			if((params.getCreate_start() != null && !params.getCreate_start().equals("")) &&
					(params.getCreate_end() != null && !params.getCreate_end().equals(""))) {
				sql.append("and a.create_date between to_date('").
				append(params.getCreate_start()).
				append("', 'yyyy-mm-dd hh24:mi:ss') ").
				append("and to_date ('").
				append(params.getCreate_end()).
				append("', 'yyyy-mm-dd hh24:mi:ss') ");
			}
			if(!(params.getOrder_type() == null || params.getOrder_type().equals(""))) {
				sql.append("and a.order_type = '").
				append(params.getOrder_type()).
				append("' ");
			}
			if(!(params.getStatus() == null || params.getStatus().equals(""))) {
				sql.append("and a.done_status = '").
				append(params.getStatus()).
				append("' ");
			}
			
			if((params.getDone_time_start() != null && !params.getDone_time_start().equals(""))
					&& (params.getDone_time_end() != null && !params.getDone_time_end().equals(""))) {
				sql.append("and a.done_time between to_date('").
				append(params.getDone_time_start()).
				append("', 'yyyy-mm-dd hh24:mi:ss') ").
				append("and to_date ('").
				append(params.getDone_time_end()).
				append("', 'yyyy-mm-dd hh24:mi:ss') ");
			}
		}
		Page page = null;
		page = this.baseDaoSupport.queryForPage(new String(sql), pageNo, pageSize, new RowMapper() {
	            public Object mapRow(ResultSet rs, int c) throws SQLException {
	                Map data = new HashMap();

	                data.put("order_id", rs.getString("order_id")); //外部单号
	                data.put("pspt_id", rs.getString("pspt_id"));//证件号码
	                data.put("contact_name", rs.getString("contact_name"));//联系人名称
	                data.put("contact_phone", rs.getString("contact_phone"));//联系人号码
	                data.put("num_city", rs.getString("org_name"));//地市
	                data.put("serial_number", rs.getString("serial_number"));//业务号码
	                data.put("create_date", rs.getString("create_date"));//下单时间
	                data.put("order_type", rs.getString("order_type"));//订单类型
	                data.put("req", rs.getString("req"));//报文
	                data.put("done_status", rs.getString("done_status"));//处理状态
	                data.put("exception_desc", rs.getString("exception_desc")); //失败原因
	                data.put("done_time", rs.getString("done_time")); //处理时间
	                data.put("serial_number", rs.getString("serial_number"));//业务号
	                return data;
	            }
	        });
		return page;
	}

	@Override
	public boolean getLockShowCallOut(String order_id) {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if(orderTree == null){
			return false;
		}else{
			List<OrderLockBusiRequest> orderLockBusiRequestsList = orderTree.getOrderLockBusiRequests();
			String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			String sql = "select lock_user_id from es_order_lock t  where t.order_id='"+order_id+"' and t.tache_code='"+trace_id+"' and t.lock_status='1'";
			List<Map> list = this.baseDaoSupport.queryForList(sql);
			if(list.size()>0){
				String lock_user_id = (String) list.get(0).get("lock_user_id");
				if(ManagerUtils.getAdminUser().getUserid().equals(lock_user_id)){
					return false;
				}else{
					return true;
				}
			}else{
				return false;//没有锁定的
			}
		}
	}

	@Override
	public void order_lock_showCallout(String order_id, String tacheCode) {
		lockOrderByUserShowCallout(order_id, tacheCode, ManagerUtils.getAdminUser().getUserid(),
                ManagerUtils.getAdminUser().getRealname());
		
	}
    public void lockOrderByUserShowCallout(String order_id, String tache_code, String userId, String userName) {
		try {
	    boolean locking = false;// 是否需要锁定
	    OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
	    OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
	    String currTacheCode = orderExtBusiRequest.getFlow_trace_id();
	    if (!StringUtils.isEmpty(tache_code)) {
	        currTacheCode = tache_code;
	    }
    	OrderLockBusiRequest orderLockBusiRequest = CommonDataFactory.getInstance()
    			.getOrderLockBusiRequest(orderTree, currTacheCode);
    	if (orderLockBusiRequest == null) {// 根据当前环节编码找不到锁定记录，则认为是没有锁定，此时要可以锁定订单
    		orderLockBusiRequest = new OrderLockBusiRequest();
    		orderLockBusiRequest.setLock_id(this.baseDaoSupport.getSequences("o_outcall_log"));
    		orderLockBusiRequest.setOrder_id(order_id);
    		orderLockBusiRequest.setTache_code(currTacheCode);
    		orderLockBusiRequest.setLock_user_id(userId);
    		orderLockBusiRequest.setLock_user_name(userName);
    		orderLockBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
    		orderLockBusiRequest.setLock_time(DateUtil.getTime2());
    		orderLockBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
    		orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
    		orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
    		orderLockBusiRequest.setPool_id(workPoolManager.getLockTimeByUserId(userId).getPool_id());
    		orderLockBusiRequest.setLock_end_time(workPoolManager.getLockTimeByUserId(userId).getLock_end_time());
    		orderLockBusiRequest.store();
    		locking = true;
    	} else if (orderLockBusiRequest != null
    			&& EcsOrderConsts.UNLOCK_STATUS.equals(orderLockBusiRequest.getLock_status())) {
    		// 根据当前环节编码找到锁定记录，但是锁定状态为未锁定，则可以锁定订单
    		orderLockBusiRequest.setLock_id(orderLockBusiRequest.getLock_id());
    		orderLockBusiRequest.setOrder_id(order_id);
    		orderLockBusiRequest.setLock_user_id(userId);
    		orderLockBusiRequest.setLock_user_name(userName);
    		orderLockBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
    		orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
    		orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
    		// 锁单信息增加工号池和锁单结束时间
    		orderLockBusiRequest.setPool_id(workPoolManager.getLockTimeByUserId(userId).getPool_id());
    		orderLockBusiRequest.setLock_end_time(workPoolManager.getLockTimeByUserId(userId).getLock_end_time());
    		orderLockBusiRequest.store();
    		locking = true;
    	}
	    if (locking) {
	        OrderHandleLogsReq orderHandleLogsReq = new OrderHandleLogsReq();
	        orderHandleLogsReq.setComments("订单锁定"); // 操作描述
	        orderHandleLogsReq.setCreate_time(DateUtil.getTime2()); // 操作时间
	        orderHandleLogsReq.setFlow_id(orderTree.getOrderExtBusiRequest().getFlow_id()); // 流程ID
	        orderHandleLogsReq.setFlow_trace_id(currTacheCode); // 环节ID
	        orderHandleLogsReq.setOp_id(userId); // 操作员ID
	        orderHandleLogsReq.setOp_name(userName); // 操作员姓名
	        orderHandleLogsReq.setOrder_id(orderTree.getOrder_id()); // 订单ID
	        orderHandleLogsReq.setHandler_type(Const.ORDER_HANDLER_TYPE_LOCK); // 业务处理类型编码
	        orderHandleLogsReq.setType_code(EcsOrderConsts.LOCK_STATUS); // 处理类型 1-锁定 0-未锁定
	        this.baseDaoSupport.insert("ES_ORDER_HANDLE_LOGS", orderHandleLogsReq);
	    }
	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    


	/**
	 * 杭州银行联名卡发展日报杭州
	 * 
	 */
	@Override
	public Page queryHangZhouDayReport(int pageNo, int pageSize, OrderQueryParams params) throws Exception {
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-01 00:00:00");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//冰激凌
		String sql129 = cacheUtil.getConfigInfo("HANGZHOU_JION_CARD_BJL129_COUNT");// 冰激凌129
		String sql199 = cacheUtil.getConfigInfo("HANGZHOU_JION_CARD_BJL199_COUNT");// 冰激凌199
		//王卡
		String bigCardsql = cacheUtil.getConfigInfo("HANGZHOU_JION_CARD_TENCENT_BIG_CARD");// 大王卡
		String skyCardsql = cacheUtil.getConfigInfo("HANGZHOU_JION_CARD_TENCENT_SKY_CARD");// 天王卡
		String groundCardsql = cacheUtil.getConfigInfo("HANGZHOU_JION_CARD_TENCENT_GROUND_CARD");// 地王卡
		//当日
		String dayDevsql = cacheUtil.getConfigInfo("HANGZHOU_JION_CARD_DAY_DEVELOPMENT_COUNT");// 日发展量
		String dayactsql = cacheUtil.getConfigInfo("HANGZHOU_JION_CARD_DAY_ACTIVETED_COUNT");// 日激活量
		//当月
		String monthDevsql = cacheUtil.getConfigInfo("HANGZHOU_JION_CARD_MOUNTH_DEVELOPMENT_COUNT");// 月发展量
		String monthActsql = cacheUtil.getConfigInfo("HANGZHOU_JION_CARD_MOUNTH_ACTIVETED_COUNT");// 月发展激活量
		String totalCBSSActsql = cacheUtil.getConfigInfo("HANGZHOU_JION_CARD_CBSS_MOUNTH_TOTAL_ACTIVETED_COUNT");// 当月CBSS累计激活量
		String totalBSSActsql = cacheUtil.getConfigInfo("HANGZHOU_JION_CARD_BSS_MOUNTH_TOTAL_ACTIVETED_COUNT");// 当月BSS累计激活量
		
		//累计
		String leiJiDevsql = cacheUtil.getConfigInfo("HANGZHOU_JION_CARD_TOTAL_DEVELOPMENT_COUNT");// 累计发展量
		String leiJiActsql = cacheUtil.getConfigInfo("HANGZHOU_JION_CARD_TOTAL_ACTIVETED_COUNT");// 累计发展激活量
		//String leiJiCBSSActsql = cacheUtil.getConfigInfo("HANGZHOU_JION_CARD_CBSS_TOTAL_ACTIVETED_COUNT");// 累计CBSS累计激活量
		//String leiJiBSSActsql = cacheUtil.getConfigInfo("HANGZHOU_JION_CARD_BSS_TOTAL_ACTIVETED_COUNT");// 累计BSS累计激活量
		
		if(params.getCreate_date() == null || params.getCreate_date().equals("")) {
			// 获取当前时间的前一天
			
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			date = calendar.getTime();
			String time = sdf.format(date);
			String DayStarTime = time + " 00:00:00";
			String DayEndTime = time + " 23:59:59";
			
			
			//获取当前时间所在月的第一天和最后一天
			String time1 = sdf.format(new Date());

			Date date1 = sdf.parse(time1);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date1);
			String monthStarTime = sdf1.format(cal.getTime()); // 第一天
			Calendar ca = Calendar.getInstance();
			ca.add(Calendar.MONTH, 0); // 加一个月
			ca.setTime(date1);
			ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			String monthEndTime = sdf2.format(ca.getTime()); // 最后一天
			
			
			
			sql129 = sql129.replace("{DayStarTime}", DayStarTime);
			sql129 = sql129.replace("{DayEndTime}", DayEndTime);
			
			sql199 = sql199.replace("{DayStarTime}", DayStarTime);
			sql199 = sql199.replace("{DayEndTime}", DayEndTime);
			
			bigCardsql = bigCardsql.replace("{DayStarTime}", DayStarTime);
			bigCardsql = bigCardsql.replace("{DayEndTime}", DayEndTime);
			
			
			skyCardsql = skyCardsql.replace("{DayStarTime}", DayStarTime);
			skyCardsql = skyCardsql.replace("{DayEndTime}", DayEndTime);
			
			groundCardsql = groundCardsql.replace("{DayStarTime}", DayStarTime);
			groundCardsql = groundCardsql.replace("{DayEndTime}", DayEndTime);
			
			dayDevsql = dayDevsql.replace("{DayStarTime}", DayStarTime);
			dayDevsql = dayDevsql.replace("{DayEndTime}", DayEndTime);
			
			dayactsql = dayactsql.replace("{DayStarTime}", DayStarTime);
			dayactsql = dayactsql.replace("{DayEndTime}", DayEndTime);
			
			monthDevsql = monthDevsql.replace("{monthStarTime}", monthStarTime);
			monthDevsql = monthDevsql.replace("{monthEndTime}", monthEndTime);
			
			monthActsql = monthActsql.replace("{monthStarTime}", monthStarTime);
			monthActsql = monthActsql.replace("{monthEndTime}", monthEndTime);
			
			totalCBSSActsql = totalCBSSActsql.replace("{monthStarTime}", monthStarTime);
			totalCBSSActsql = totalCBSSActsql.replace("{monthEndTime}", monthEndTime);
			
			totalBSSActsql = totalBSSActsql.replace("{monthStarTime}", monthStarTime);
			totalBSSActsql = totalBSSActsql.replace("{monthEndTime}", monthEndTime);
			
		}else {
			//指定的日期
			String tempDayTime  = params.getCreate_date(); 
			
	        //根据制定日期获取此日期所在月的第一天和最后一天
	        Date date1 =sdf.parse(tempDayTime);
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date1);
	        String monthStarTime = sdf1.format(cal.getTime()); //第一天
	        Calendar ca = Calendar.getInstance(); 
	        ca.add(Calendar.MONTH, 0);    //加一个月
	        ca.setTime(date1);
	        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
	        String monthEndTime = sdf2.format(ca.getTime()); //最后一天
			
			
			String createDayStarTime = tempDayTime + " 00:00:00";
			String createDayEndTime = tempDayTime + " 23:59:59";
			
			
			sql129 = sql129.replace("{DayStarTime}",createDayStarTime);
			sql129 = sql129.replace("{DayEndTime}", createDayEndTime);
			
			sql199 = sql199.replace("{DayStarTime}", createDayStarTime);
			sql199 = sql199.replace("{DayEndTime}", createDayEndTime);
			
			bigCardsql = bigCardsql.replace("{DayStarTime}", createDayStarTime);
			bigCardsql = bigCardsql.replace("{DayEndTime}", createDayEndTime);
			
			
			skyCardsql = skyCardsql.replace("{DayStarTime}", createDayStarTime);
			skyCardsql = skyCardsql.replace("{DayEndTime}", createDayEndTime);
			
			groundCardsql = groundCardsql.replace("{DayStarTime}", createDayStarTime);
			groundCardsql = groundCardsql.replace("{DayEndTime}", createDayEndTime);
			
			dayDevsql = dayDevsql.replace("{DayStarTime}", createDayStarTime);
			dayDevsql = dayDevsql.replace("{DayEndTime}", createDayEndTime);
			
			dayactsql = dayactsql.replace("{DayStarTime}", createDayStarTime);
			dayactsql = dayactsql.replace("{DayEndTime}", createDayEndTime);
			
			monthDevsql = monthDevsql.replace("{monthStarTime}", monthStarTime);
			monthDevsql = monthDevsql.replace("{monthEndTime}", monthEndTime);
			
			monthActsql = monthActsql.replace("{monthStarTime}", monthStarTime);
			monthActsql = monthActsql.replace("{monthEndTime}", monthEndTime);
			
			
			totalCBSSActsql = totalCBSSActsql.replace("{monthStarTime}", monthStarTime);
			totalCBSSActsql = totalCBSSActsql.replace("{monthEndTime}", monthEndTime);
			
			totalBSSActsql = totalBSSActsql.replace("{monthStarTime}", monthStarTime);
			totalBSSActsql = totalBSSActsql.replace("{monthEndTime}", monthEndTime);
		}
		
		
		Page page = null;
		String sql = "select t.field_value,t.field_value_desc from es_dc_public_dict_relation  t  where t.stype = 'city' and source_from = 'ECS'";

		page = this.baseDaoSupport.queryForPage(new String(sql), pageNo, pageSize, new RowMapper() {
			public Object mapRow(ResultSet rs, int c) throws SQLException {
				Map data = new HashMap();
				data.put("cityCode", rs.getString("field_value")); // 地市code
				data.put("cityName", rs.getString("field_value_desc")); // 地市name
				return data;
			}
		});

		List cityList = page.getResult();
		int bjl129 = changeListValue(cityList, support.queryForList(sql129), "bjl129");
		int bjl199 = changeListValue(cityList, support.queryForList(sql199), "bjl199");
		int bigCard = changeListValue(cityList, support.queryForList(bigCardsql), "bigCard");
		int skyCard = changeListValue(cityList, support.queryForList(skyCardsql), "skyCard");
		int groundCard = changeListValue(cityList, support.queryForList(groundCardsql), "groundCard");
		int dayDev = changeListValue(cityList, support.queryForList(dayDevsql), "dayDev");
		int dayact = changeListValue(cityList, support.queryForList(dayactsql), "dayact");
		int monthDev = changeListValue(cityList, support.queryForList(monthDevsql), "monthDev");
		int monthAct = changeListValue(cityList, support.queryForList(monthActsql), "monthAct");
		int totalBSSAct = changeListValue(cityList, support.queryForList(totalBSSActsql), "totalBSSAct");
		int totalCBSSAct = changeListValue(cityList, support.queryForList(totalCBSSActsql), "totalCBSSAct");
		
	
		
		int leiJiDev = changeListValue(cityList, support.queryForList(leiJiDevsql), "leiJiDev");
		int leiJiDevAct = changeListValue(cityList, support.queryForList(leiJiActsql), "leiJiDevAct");
		//int leiJiBSSAct = changeListValue(cityList, support.queryForList(leiJiBSSActsql), "leiJiBSSAct");
		//int leiJiCBSSAct = changeListValue(cityList, support.queryForList(leiJiCBSSActsql), "leiJiCBSSAct");
		
		
		
		
		
		//统计全省的
		
		Map quanShengMap = new HashMap();
		quanShengMap.put("cityName", "全省");
		quanShengMap.put("bjl129", bjl129);
		quanShengMap.put("bjl199", bjl199);
		quanShengMap.put("bigCard", bigCard);
		quanShengMap.put("skyCard", skyCard);
		quanShengMap.put("groundCard", groundCard);
		quanShengMap.put("dayDev", dayDev);
		quanShengMap.put("dayact", dayact);
		quanShengMap.put("monthDev", monthDev);
		quanShengMap.put("monthAct", monthAct);
		quanShengMap.put("totalAct", totalBSSAct+totalCBSSAct);
		
		quanShengMap.put("leiJiDev", leiJiDev);
		quanShengMap.put("leiJiDevAct", leiJiDevAct);
		//quanShengMap.put("leiJiAct", leiJiBSSAct+leiJiCBSSAct);
		
		//全省转化率
		if(leiJiDevAct == 0) {
			quanShengMap.put("zhuanhualv","0%");
		}else {
			DecimalFormat df = new DecimalFormat("0.00");
			String zhunhualv = df.format((float)leiJiDevAct / leiJiDev * 100);
			quanShengMap.put("zhuanhualv", zhunhualv + "%");
		}
		
		//各个地市算转化率和月累计激活量和总激活量
		for(int i = 0 ; i<cityList.size(); i++) {
			Map cityMap = (Map) cityList.get(i);
			cityMap.put("totalAct", Integer.parseInt(cityMap.get("totalCBSSAct").toString())+Integer.parseInt(cityMap.get("totalBSSAct").toString()));//月累计
			//cityMap.put("leiJiAct", Integer.parseInt(cityMap.get("leiJiBSSAct").toString())+Integer.parseInt(cityMap.get("leiJiCBSSAct").toString()));//所有累计
			if(null == cityMap.get("leiJiDevAct") || cityMap.get("leiJiDevAct").equals("") || cityMap.get("leiJiDevAct").equals("0")) {
				cityMap.put("zhuanhualv", "0%");
			}else {
				int leiJiDevNO  = Integer.parseInt(cityMap.get("leiJiDev").toString());
				int leiJiActNO  = Integer.parseInt(cityMap.get("leiJiDevAct").toString());
				DecimalFormat df = new DecimalFormat("0.00");
				String zhunhualv = df.format((float)leiJiActNO / leiJiDevNO * 100);
				cityMap.put("zhuanhualv", zhunhualv+"%");
			}
			
		}
		cityList.add(quanShengMap);
		page.setResult(cityList);
		return page;
	}

	/**
	 * 以地市划分，将按列查询的结果转化为行
	 * 
	 * @param cityList
	 * @param queryList
	 * @param key
	 * @return
	 */
	private int changeListValue(List cityList, List queryList, String key) {
		int totalCount = 0;
		for (int i = 0; i < cityList.size(); i++) {
			Map cityMap = (Map) cityList.get(i);
			cityMap.put(key, "0");
			for (int j = 0; j < queryList.size(); j++) {
				Map queryMap = (Map) queryList.get(j);
				if (queryMap.get("citycode").toString().equals(cityMap.get("cityCode").toString())) {
					cityMap.put(key, queryMap.get("countNum"));
					totalCount += Integer.parseInt(queryMap.get("countNum").toString());
					break;
				}
			}
		}
		return totalCount;
	}
	/**
	 * 杭州银行联名卡发展详单
	 */
	@Override
	public List queryHangZhouDevReport(OrderQueryParams params) throws Exception {
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String sql = cacheUtil.getConfigInfo("HANGZHOU_JION_CARD_DEV_REPORT");
		if(null == params.getCreate_start() || params.getCreate_start().equals("")|| 
		   null == params.getCreate_end() || params.getCreate_end().equals("")) {
			sql = sql + " order by a.create_time  desc";
		}else {
			sql = sql + "and a.create_time > to_date('" + params.getCreate_start()  + "', 'yyyy-MM-dd hh24:mi:ss') and a.create_time < to_date('" +params.getCreate_end()+ "', 'yyyy-MM-dd hh24:mi:ss')  order by a.create_time  desc ";
		}
		
		return support.queryForList(sql);
	}
	/**
	 * 短信异步发送
	 */
	@Override
	public Page queryMessageSendMidList(int pageNo, int pageSize, OrderQueryParams params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select * from (select * from es_order_mid_sms union all select * from es_order_his_sms ) a where a.source_from = 'ECS'");
		if (null != params) {
			if (!(params.getSms_id() == null || params.getSms_id().equals(""))) {
				sql.append(" and a.sms_id = '").append(params.getSms_id()).append("' ");
			}
			if (!(params.getOrder_id() == null || params.getOrder_id().equals(""))) {
				sql.append(" and a.order_id = '").append(params.getOrder_id()).append("' ");
			}
			if (!(params.getBill_num() == null || params.getBill_num().equals(""))) {
				sql.append(" and a.bill_num = '").append(params.getBill_num()).append("' ");
			}
			if (!(params.getService_num() == null || params.getService_num().equals(""))) {
				sql.append(" and a.service_num = '").append(params.getService_num()).append("' ");
			}
			if (!(params.getSms_flag() == null || params.getSms_flag().equals(""))) {
				sql.append(" and a.sms_flag = '").append(params.getSms_flag()).append("' ");
			}
			if (!(params.getSend_status() == null || params.getSend_status().equals(""))) {
				sql.append(" and a.send_status = '").append(params.getSend_status()).append("' ");
			}
			
			if (!(params.getSms_type() == null || params.getSms_type().equals(""))) {
				sql.append(" and a.sms_type = '").append(params.getSms_type()).append("' ");
			}

			if (!(params.getSms_data() == null || params.getSms_data().equals(""))) {
				sql.append(" and a.sms_data like '%").append(params.getSms_data()).append("%' ");
			}

			if ((params.getCreate_start() != null && !params.getCreate_start().equals(""))
					&& (params.getCreate_end() != null && !params.getCreate_end().equals(""))) {
				sql.append("and a.create_time between to_date('").append(params.getCreate_start())
						.append("', 'yyyy-mm-dd hh24:mi:ss') ").append("and to_date ('").append(params.getCreate_end())
						.append("', 'yyyy-mm-dd hh24:mi:ss') ");
			}

			if ((params.getDone_time_start() != null && !params.getDone_time_start().equals(""))
					&& (params.getDone_time_end() != null && !params.getDone_time_end().equals(""))) {
				sql.append("and a.done_time between to_date('").append(params.getDone_time_start())
						.append("', 'yyyy-mm-dd hh24:mi:ss') ").append("and to_date ('")
						.append(params.getDone_time_end()).append("', 'yyyy-mm-dd hh24:mi:ss') ");
			}
		}
		Page page = null;
		
		page = this.baseDaoSupport.queryForPage(new String(sql), pageNo, pageSize, new RowMapper() {
			public Object mapRow(ResultSet rs, int c) throws SQLException {
				Map data = new HashMap();
				data.put("order_id", rs.getString("order_id"));
				data.put("sms_id", rs.getString("sms_id"));
				data.put("service_num", rs.getString("service_num"));
				data.put("bill_num", rs.getString("bill_num"));
				data.put("sms_data", rs.getString("sms_data"));
				data.put("sms_flag", rs.getString("sms_flag"));
				data.put("sms_type", rs.getString("sms_type").equals("cmc")?"异网":"本网");
				data.put("send_type", rs.getString("send_type"));
				data.put("send_status", rs.getString("send_status"));
				data.put("send_num", rs.getString("send_num"));
				data.put("exception_desc", rs.getString("exception_desc"));
				data.put("done_time", rs.getString("done_time"));
				data.put("create_time", rs.getString("create_time"));
				return data;
			}
		});
		return page;
	}  
	@Override
	/**
	 * 加速包充值包请求参数
	 * @return
	 */
	public ProductSubReq productReqSet(String order_id) throws Exception{
		ProductSubReq req= new ProductSubReq();
		String service_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
		IOrderInfManager orderInfManager = SpringContextHolder.getBean("orderInfManager");
		Map map = orderInfManager.qryGoodsDtl(order_id);
		String product_id = com.ztesoft.ibss.common.util.Const.getStrValue(map, "sn")+"";
		String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR);
		String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE);
		String optype = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "opttype");
		String out_operator = "";
		String out_office = "";
		String op_type = "";
		if(!StringUtil.isEmpty(OUT_OPERATOR)){
			out_operator = OUT_OPERATOR;
		}else{
			out_operator = ZjCommonUtils.getGonghaoInfoByOrderId(order_id).getUser_code();
		}
		if(!StringUtil.isEmpty(OUT_OFFICE)) {
			out_office = OUT_OFFICE;
		}else {
			out_office = ZjCommonUtils.getGonghaoInfoByOrderId(order_id).getDept_id();
		}
		if(StringUtil.equals("00", optype)) {
			op_type = "SUBS";
		}else if(StringUtil.equals("01", optype)) {
			op_type = "UNSUBS";
		}else {
			throw new Exception("optype传入有误");
		}
		req.setProduct_id(product_id);
		req.setService_num(service_num);
		req.setOut_operator(out_operator);
		req.setOut_channel(out_office);
		req.setOp_type(op_type);
		req.setNotNeedReqStrOrderId(order_id);
		return req;
	}
	
	@Override
	/**
	 * 退单通知手机商城
	 * @param order_id
	 * @return
	 */
	public O2OStatusUpdateResp returnToMobileStore(String order_id) throws Exception{
		O2OStatusUpdateResp refundResp=new O2OStatusUpdateResp();
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String orderFroms = cacheUtil.getConfigInfo("REFUND_STATUS_SYN_ORDER_FROM");
		StringBuffer sql = new StringBuffer();
		if(!StringUtil.isEmpty(orderFroms)){
			sql.append("select * from es_order_intent where order_id='").append(order_id.trim()).append("' ");
			List list = baseDaoSupport.queryForList(sql.toString());
			String order_from = "";
			String intent_order_id = "";
			
			//先取意向单订单来源，没有意向单，取正式订单
			if(list!=null && list.size()>0){
				order_from = ((Map)list.get(0)).get("source_system_type").toString();
				intent_order_id = ((Map)list.get(0)).get("intent_order_id").toString();
			}else{
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				
				if(orderTree!=null){
					order_from = orderTree.getOrderExtBusiRequest().getOrder_from();
					intent_order_id =  CommonDataFactory.getInstance().
							getAttrFieldValue(order_id, "out_tid");
				}
			}
			
			if(orderFroms.contains(order_from+",")){
				String order_code = "";
				order_code = intent_order_id;
				O2OStatusUpdateReq req = new O2OStatusUpdateReq();
				req.setNotNeedReqStrOrderId(order_id.trim());
				MSGPKGVO new_MSGPKG = new MSGPKGVO();
				PKGHEADVO PKG_HEAD = new PKGHEADVO();
				PKGBODYVO PKG_BODY = new PKGBODYVO();
				PKG_HEAD.setACTION_ID("M10001");
				
				PKG_BODY.setORDER_CODE(order_code);
				PKG_BODY.setORDER_STATUS("06");
				new_MSGPKG.setPKG_BODY(PKG_BODY);
				new_MSGPKG.setPKG_HEAD(PKG_HEAD);
				req.setMSGPKG(new_MSGPKG);
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				refundResp = client.execute(req, O2OStatusUpdateResp.class);
			}else {
				refundResp.setError_code("00000");
			}
		}
		return refundResp;
	}
    
}