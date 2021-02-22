package zte.service;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import params.order.req.OrderIdReq;
import params.order.resp.OrderIdResp;
import params.req.BroadBandCtnStandardReq;
import params.req.CenterMallOrderStandardReq;
import params.req.CodePurchaseMallOrderStandardReq;
import params.req.DepositOrderStandardReq;
import params.req.FixBusiOrderStandardReq;
import params.req.GroupOrderStandardReq;
import params.req.HSMallOrderStandardReq;
import params.req.InternetGroupOrderStandardReq;
import params.req.InternetOrderStandardReq;
import params.req.MobileBusiCtnStandardReq;
import params.req.NewMallOrderStandardReq;
import params.req.ObjectReplaceStandardReq;
import params.req.OrderBeeReq;
import params.req.OrderDistributeCtnStandardReq;
import params.req.OrderPreCreateCtnStandardReq;
import params.req.StdChannelConvertReq;
import params.req.StdStartWorkflowReq;
import params.req.StdWorkflowMatchReq;
import params.req.TaoBaoFenxiaoOrderStandardReq;
import params.req.TaoBaoMallOrderStandardReq;
import params.req.TaoBaoTianjiOrderStandardReq;
import params.req.TemplatesOrderStandardReq;
import params.req.ZJLocalMallOrderStandardReq;
import params.resp.BroadBandCtnStandardResp;
import params.resp.CenterMallOrderStandardResp;
import params.resp.CodePurchaseMallOrderStandardResp;
import params.resp.DepositOrderStandardResp;
import params.resp.FixBusiOrderStandardResp;
import params.resp.GroupOrderStandardResp;
import params.resp.HSMallOrderStandardResp;
import params.resp.InternetGroupStandardResp;
import params.resp.InternetOrderStandardResp;
import params.resp.MobileBusiCtnStandardResp;
import params.resp.NewMallOrderStandardResp;
import params.resp.ObjectReplaceStandardResp;
import params.resp.OrderBeeResp;
import params.resp.OrderDistributeCtnStandardResp;
import params.resp.OrderPreCreateCtnStandardResp;
import params.resp.TaoBaoFenxiaoOrderStandardResp;
import params.resp.TaoBaoMallOrderStandardResp;
import params.resp.TaoBaoTianjiOrderStandardResp;
import params.resp.TemplatesOrderStandardResp;
import params.resp.ZJLocalMallOrderStandardResp;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.resp.ChannelConvertQrySubResp;
import zte.net.ecsord.params.order.resp.StartWorkflowRsp;
import zte.net.ecsord.params.order.resp.WorkflowMatchRsp;
import zte.net.iservice.IOrderstdService;
import zte.params.orderctn.resp.OrderCtnResp;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.MarkTime;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.IntentUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.outter.inf.iservice.IOuterService;
import com.ztesoft.net.server.TaobaoOrderUtil;
import com.ztesoft.orderstd.service.INewOrderStandingManager;
import com.ztesoft.orderstd.service.IOrderStandingManager;
import com.ztesoft.rop.annotation.ServiceMethodBean;

import commons.CommonTools;
import consts.ConstsCore;

@ServiceMethodBean(version = "1.0")
@ZteSoftCommentAnnotation(type = "class", desc = "订单标准化服务", summary = "订单标准化服务")
public class OrderstdService extends BaseSupport implements IOrderstdService{
	@Resource 
	private IOuterService stdOuterService;
	@Resource
	private IOrderStandingManager orderStandingManager;
	@Autowired
	private INewOrderStandingManager orderStandingManagerImpl;
	@Resource
	private TaobaoOrderUtil taobaoOrderUtil;
	@Resource
	private IntentUtil intentUtil;
	
	@Override 
	@ZteSoftCommentAnnotation(type = "method", desc = "新商城订单标准化", summary = "新商城订单标准化")
	public NewMallOrderStandardResp newMallOrderStandard(
			NewMallOrderStandardReq req) {
		long start = System.currentTimeMillis();
		NewMallOrderStandardResp resp = new NewMallOrderStandardResp();
		String NEWMALL_ORDER_FROM=this.daoSupport.queryForString("select cf_value from es_config_info where cf_id = 'NEWMALL_ORDER_FROM'");
		if(NEWMALL_ORDER_FROM.contains(req.getOrder_from())){//微信走新流程
			if(orderStandingManagerImpl==null){
				orderStandingManagerImpl=SpringContextHolder.getBean("orderStandingManagerImpl");
			}
			resp = orderStandingManagerImpl.newMallOrderStandard(req);
		}else{
			if(orderStandingManager == null){
				orderStandingManager = SpringContextHolder.getBean("orderStandingManager");
			}
			resp = orderStandingManager.newMallOrderStandard(req);
		}
//		if(orderStandingManager==null){orderStandingManager=SpringContextHolder.getBean("orderStandingManager");}
//		resp = orderStandingManager.newMallOrderStandard(req);
		logger.info(resp.getError_code()+":"+resp.getError_msg());
		long end = System.currentTimeMillis();
		logger.info("新商城总耗时======="+(end-start));
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "总部商城订单标准化", summary = "总部商城订单标准化")
	public CenterMallOrderStandardResp centerMallOrderStandard(
			CenterMallOrderStandardReq req) {
		long start = System.currentTimeMillis();
		CenterMallOrderStandardResp resp = new CenterMallOrderStandardResp();
		if(orderStandingManager==null){orderStandingManager=SpringContextHolder.getBean("orderStandingManager");}
		resp = orderStandingManager.centerMallOrderStandard(req);
		logger.info(resp.getError_code()+":"+resp.getError_msg());
		long end = System.currentTimeMillis();
		logger.info("总部总耗时======="+(end-start));
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "码上购商城订单标准化", summary = "码上购商城订单标准化")
	public CodePurchaseMallOrderStandardResp codePurchaseMallOrderStandard(
			CodePurchaseMallOrderStandardReq req) {
		
		long start = System.currentTimeMillis();
		CodePurchaseMallOrderStandardResp resp = new CodePurchaseMallOrderStandardResp();
		String CODE_PURCHASE_MASLL_ORDER = this.daoSupport.queryForString("select cf_value from es_config_info where cf_id = 'CODE_PURCHASE_MASLL_ORDER'");
            if(CODE_PURCHASE_MASLL_ORDER.contains(req.getOrder_from()+",")){
                if(orderStandingManagerImpl == null){
                    orderStandingManagerImpl = SpringContextHolder.getBean("orderStandingManagerImpl");
                }
                resp = orderStandingManagerImpl.codePurchaseMallOrderStandard(req);
                logger.info("码上购总耗时=--新架构");
            }else{
                if(orderStandingManager == null){
                    orderStandingManager = SpringContextHolder.getBean("orderStandingManager");
                }
                resp = orderStandingManager.codePurchaseMallOrderStandard(req);
                logger.info("码上购总耗时=--老架构");
            }
		logger.info(resp.getError_code()+":"+resp.getError_msg());
		long end = System.currentTimeMillis();
		logger.info("码上购总耗时======="+(end-start));
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "融合业务订单标准化", summary = "融合业务订单标准化")
	public GroupOrderStandardResp GroupOrderStandard(GroupOrderStandardReq req) {
		long start = System.currentTimeMillis();
		GroupOrderStandardResp resp = new GroupOrderStandardResp();
        String GROUP_ORDERSTD = this.daoSupport.queryForString("select cf_value from es_config_info where cf_id = 'GROUP_ORDERSTD'");
        if(GROUP_ORDERSTD.contains(req.getOrder_from()+",")){
            if(orderStandingManagerImpl == null){
                orderStandingManagerImpl = SpringContextHolder.getBean("orderStandingManagerImpl");
            }
            resp = orderStandingManagerImpl.groupOrderStandard(req);
            logger.info("融合业务--新架构");
        }else{
            if(orderStandingManager == null){
                orderStandingManager = SpringContextHolder.getBean("orderStandingManager");
            }
            resp = orderStandingManager.groupOrderStandard(req);
            logger.info("融合业务--老架构");
        }
		
		logger.info(resp.getError_code()+":"+resp.getError_msg());
		long end = System.currentTimeMillis();
		logger.info("融合业务总耗时======="+(end-start));
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "淘宝商城订单标准化", summary = "淘宝商城订单标准化")
	public TaoBaoMallOrderStandardResp taoBaoMallOrderStandard(
			TaoBaoMallOrderStandardReq req) {
		TaoBaoMallOrderStandardResp resp = new TaoBaoMallOrderStandardResp();
		if(orderStandingManager==null){orderStandingManager=SpringContextHolder.getBean("orderStandingManager");}
		resp = orderStandingManager.taoBaoMallOrderStandard(req);
		//logger.info("淘宝标准化信息:"+resp.getError_code()+"================"+resp.getError_msg()+"==========="+resp.getError_stack_msg());
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "淘宝分销订单标准化", summary = "淘宝分销订单标准化")
	public TaoBaoFenxiaoOrderStandardResp taoBaoFenxiaoOrderStandard(
			TaoBaoFenxiaoOrderStandardReq req) {
		TaoBaoFenxiaoOrderStandardResp resp = new TaoBaoFenxiaoOrderStandardResp();
		if(orderStandingManager==null){orderStandingManager=SpringContextHolder.getBean("orderStandingManager");}
		resp = orderStandingManager.taoBaoFenxiaoOrderStandard(req);
		//logger.info("淘宝标准化信息:"+resp.getError_code()+"================"+resp.getError_msg()+"==========="+resp.getError_stack_msg());
		return resp;
	}
	
	
	@Override
	public OrderIdResp orderId(OrderIdReq req) {
		OrderIdResp orderIdResp = new OrderIdResp();
		orderIdResp.setOrder_id(getOrderIdByOrderOutId(req.getOrder_out_id()));
		orderIdResp.setError_code("0");
		orderIdResp.setError_msg("成功");
		return orderIdResp;
	}
	private String getOrderIdByOrderOutId(String outId){
		String sql = "select t.order_id from es_order_ext t where t.out_tid=? and t.source_from=?";
		ArrayList para = new ArrayList();
		para.add(outId);
		para.add(CommonTools.getSourceForm());
		String order_id = this.baseDaoSupport.queryForString(sql, para.toArray());
		return order_id;
	}

	@Override
	public TemplatesOrderStandardResp templatesOrderStandard(TemplatesOrderStandardReq req) {
		if(orderStandingManager==null){orderStandingManager=SpringContextHolder.getBean("orderStandingManager");}
		return orderStandingManager.templatesOrderStandard(req); 
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "新商城订单标准化", summary = "新商城订单标准化")
	public HSMallOrderStandardResp hsMallOrderStandard(
			HSMallOrderStandardReq req) {
		long start = System.currentTimeMillis();
		HSMallOrderStandardResp resp = new HSMallOrderStandardResp();
		
		if(orderStandingManager==null){orderStandingManager=SpringContextHolder.getBean("orderStandingManager");}
		resp = orderStandingManager.hsMallOrderStandard(req);
		logger.info(resp.getError_code()+":"+resp.getError_msg());
		long end = System.currentTimeMillis();
		logger.info("新商城总耗时======="+(end-start));
		return resp;
	}

	@Override
	public TaoBaoTianjiOrderStandardResp taoBaoTianjiOrderStandard(
			TaoBaoTianjiOrderStandardReq req) {
		TaoBaoTianjiOrderStandardResp resp = new TaoBaoTianjiOrderStandardResp();
		if(orderStandingManager==null){orderStandingManager=SpringContextHolder.getBean("orderStandingManager");}
		resp = orderStandingManager.taoBaoTianjiOrderStandard(req);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "浙江本地商城订单标准化", summary = "浙江本地商城订单标准化")
	public ZJLocalMallOrderStandardResp zjLocalMallOrderStandard(
			ZJLocalMallOrderStandardReq req) {
		long start = System.currentTimeMillis();
		ZJLocalMallOrderStandardResp resp = new ZJLocalMallOrderStandardResp();
		
		if(orderStandingManager==null){orderStandingManager=SpringContextHolder.getBean("orderStandingManager");}
		resp = orderStandingManager.zjLocalMallOrderStandard(req);
		logger.info(resp.getError_code()+":"+resp.getError_msg());
		long end = System.currentTimeMillis();
		logger.info("浙江本地商城订单标准化总耗时======="+(end-start));
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "温大固网业务订单标准化", summary = "温大固网业务订单标准化")
	public FixBusiOrderStandardResp fixBusiOrderStandard(FixBusiOrderStandardReq req) {
		
		long start = System.currentTimeMillis();
		FixBusiOrderStandardResp resp = new FixBusiOrderStandardResp();
		
		if(this.isNewStd("fixBusiOrderStandard",req.getOrder_from())){
			//新的标准化方法
			if(orderStandingManagerImpl==null){
				orderStandingManagerImpl=SpringContextHolder.getBean("orderStandingManagerImpl");
			}
			
			resp = orderStandingManagerImpl.fixBusiOrderStandard(req);
		}else{
			//旧的标准化方法
			if(orderStandingManager==null){
				orderStandingManager=SpringContextHolder.getBean("orderStandingManager");
			}
			
			resp = orderStandingManager.fixBusiOrderStandard(req);
		}
		
		logger.info(resp.getError_code()+":"+resp.getError_msg());
		long end = System.currentTimeMillis();
		logger.info("融合业务总耗时======="+(end-start));
		
		return resp;
	}
	
	/**
	 * 是否调用新的标准化方法
	 * @param std_key
	 * @param order_from
	 * @return
	 */
	private boolean isNewStd(String std_key,String order_from){
		boolean isNewStd = false;
		
		//读取新标准化的订单配置
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String key = "NEW_STD_CFG_"+std_key+"_"+order_from;
		String flag = cacheUtil.getConfigInfo(key);
		
		if("1".equals(flag)){
			isNewStd = true;
		}
		
		return isNewStd;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "新固网业务订单标准化", summary = "新固网业务订单标准化")
	public BroadBandCtnStandardResp broadBandCtnStandard(BroadBandCtnStandardReq req) {
		
		long start = System.currentTimeMillis();
		BroadBandCtnStandardResp resp = new BroadBandCtnStandardResp();
		if(orderStandingManager==null){orderStandingManager=SpringContextHolder.getBean("orderStandingManager");}
		resp = orderStandingManager.broadBandCtnStandard(req);
		logger.info(resp.getError_code()+":"+resp.getError_msg());
		long end = System.currentTimeMillis();
		logger.info("新固网业务总耗时======="+(end-start));
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type="method",desc="互联网业务订单标准化",summary="互联网业务订单标准化")
	public InternetOrderStandardResp internetBusiOrderStandard(InternetOrderStandardReq req) {
		
		long start = System.currentTimeMillis();
		InternetOrderStandardResp resp = new InternetOrderStandardResp();
		if(orderStandingManager==null){orderStandingManager=SpringContextHolder.getBean("orderStandingManager");}
		resp = orderStandingManager.internetBusiOrderStandard(req);
		logger.info(resp.getError_code()+":"+resp.getError_msg());
		long end = System.currentTimeMillis();
		logger.info("互联网业务总耗时======="+(end-start));
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type="method",desc="互联网融合业务订单标准化",summary="互联网融合业务订单标准化")
	public InternetGroupStandardResp internetGroupOrderStandard(InternetGroupOrderStandardReq req) {
		long start = System.currentTimeMillis();
		InternetGroupStandardResp resp = new InternetGroupStandardResp();
        String INTENT_GROUP_ORDERSTD = this.daoSupport.queryForString("select cf_value from es_config_info where cf_id = 'INTENT_GROUP_ORDERSTD'");
		if(INTENT_GROUP_ORDERSTD.contains(req.getOrder_from()+",")){
		    if(orderStandingManagerImpl == null){
		        orderStandingManagerImpl = SpringContextHolder.getBean("orderStandingManagerImpl");
            }
	        logger.info("互联网融合业务--新架构");
            resp = orderStandingManagerImpl.internetGroupOrderStandard(req);
		}else{
		    if(orderStandingManager == null){
	            orderStandingManager = SpringContextHolder.getBean("orderStandingManager");
	        }
	        logger.info("互联网融合业务--老架构");
	        resp = orderStandingManager.internetGroupOrderStandard(req);
		}
		long end = System.currentTimeMillis();
		logger.info(resp.getError_code()+":"+resp.getError_msg());
		logger.info("互联网融合业务总耗时======="+(end-start));
		
		return resp;
		
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "移网产品活动业务标准化", summary = "移网产品活动业务标准化")
	public MobileBusiCtnStandardResp mobileBusiCtnStandard(MobileBusiCtnStandardReq req) {
		long start = System.currentTimeMillis();
		MobileBusiCtnStandardResp resp = new MobileBusiCtnStandardResp();
		String MOBILE_ORDER_FROM = this.daoSupport.queryForString("select cf_value from es_config_info where cf_id = 'MOBILE_ORDER_FROM'");
		if(MOBILE_ORDER_FROM.contains(req.getOrder_from())){//微信走新流程
			if(orderStandingManagerImpl==null){
				orderStandingManagerImpl=SpringContextHolder.getBean("orderStandingManagerImpl");
			}
			resp = orderStandingManagerImpl.mobileBusiCtnStandard(req);
		}else{
			if(orderStandingManager == null){
				orderStandingManager = SpringContextHolder.getBean("orderStandingManager");
			}
			resp = orderStandingManager.mobileBusiCtnStandard(req);
		}
		
		long end = System.currentTimeMillis();
		logger.info(Thread.currentThread().getId()+"移网产品活动业务总耗时======="+(end-start));
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "新零售商品预定接口标准化", summary = "新零售商品预定接口标准化")
	public OrderPreCreateCtnStandardResp orderPreCreateCtnStandard(OrderPreCreateCtnStandardReq req) {
		long start = System.currentTimeMillis();
		OrderPreCreateCtnStandardResp resp = new OrderPreCreateCtnStandardResp();
		if(orderStandingManager == null){
			orderStandingManager = SpringContextHolder.getBean("orderStandingManager");
		}
		resp = orderStandingManager.orderPreCreateCtnStandard(req);
		long end = System.currentTimeMillis();
		logger.info(Thread.currentThread().getId()+"新零售商品预定接口标准化业务总耗时======="+(end-start));
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单下发接口标准化", summary = "订单下发接口标准化")
	public OrderDistributeCtnStandardResp orderDistributeCtnStandard(OrderDistributeCtnStandardReq req) {
		long start = System.currentTimeMillis();
		OrderDistributeCtnStandardResp resp = new OrderDistributeCtnStandardResp();
		if(orderStandingManager == null){
			orderStandingManager = SpringContextHolder.getBean("orderStandingManager");
		}
		resp = orderStandingManager.orderDistributeCtnStandard(req);
		long end = System.currentTimeMillis();
		logger.info(Thread.currentThread().getId()+"订单下发接口标准化业务总耗时======="+(end-start));
		return resp;
	}
	
	@Override
	public StartWorkflowRsp doStartWorkflow(StdStartWorkflowReq req)
			throws Exception {
		if(this.orderStandingManager==null){
			orderStandingManager=SpringContextHolder.getBean("orderStandingManager");
		}
		
		return this.orderStandingManager.doStartWorkflow(req);
	}

	@Override
	public WorkflowMatchRsp doWorkflowMatch(StdWorkflowMatchReq req)
			throws Exception {
		if(this.orderStandingManager==null){
			orderStandingManager=SpringContextHolder.getBean("orderStandingManager");
		}
		
		return this.orderStandingManager.doWorkflowMatch(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "固网终端", summary = "固网终端")
	public ObjectReplaceStandardResp objectReplaceStandard(ObjectReplaceStandardReq req) {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		ObjectReplaceStandardResp resp = new ObjectReplaceStandardResp();
		if(this.orderStandingManager==null){
			orderStandingManager=SpringContextHolder.getBean("orderStandingManager");
		}
		resp = orderStandingManagerImpl.objectReplaceStandard(req);
		
		logger.info(resp.getError_code()+":"+resp.getError_msg());
		long end = System.currentTimeMillis();
		logger.info("新商城总耗时======="+(end-start));
		
		return resp;
	}

    @Override
    public ChannelConvertQrySubResp channelConvert(StdChannelConvertReq req)
            throws Exception {
        if(this.orderStandingManager==null){
            orderStandingManager=SpringContextHolder.getBean("orderStandingManager");
        }
        return this.orderStandingManager.channelConvert(req);
    }
    
    @Override
	public NewMallOrderStandardResp orderUpdate(
			NewMallOrderStandardReq req) throws Exception{
		if(orderStandingManagerImpl==null){
			orderStandingManagerImpl=SpringContextHolder.getBean("orderStandingManagerImpl");
		}
		
		NewMallOrderStandardResp resp = orderStandingManagerImpl.orderUpdate(req);
		
		return resp;
	}
    
    @Override
	@ZteSoftCommentAnnotation(type = "method", desc = "押金收单标准化", summary = "押金收单标准化")
	public DepositOrderStandardResp orderDepositStandard(
			DepositOrderStandardReq req) {
		long start = System.currentTimeMillis();
		DepositOrderStandardResp resp = new DepositOrderStandardResp();
		if(orderStandingManagerImpl==null){
			orderStandingManagerImpl=SpringContextHolder.getBean("orderStandingManagerImpl");
		}
		resp = orderStandingManagerImpl.orderDepositStandard(req);
		logger.info(resp.getError_code()+":"+resp.getError_msg());
		long end = System.currentTimeMillis();
		logger.info("押金总耗时======="+(end-start));
		return resp;
	}


	@Override
	public OrderBeeResp orderBeeMakeupInsert(OrderBeeReq requ) throws Exception {
		MarkTime mark = new MarkTime("orderMakeupInsert out_order_id="+requ.getOut_order_id());
		OrderBeeResp resp = new OrderBeeResp();
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
				OrderCtnResp orderCtnResp = intentUtil.saveManualOrder(manualOrder, "D");
				if ("0".equals(orderCtnResp.getError_code())) {
					msg = "处理成功";
					code = "0";
					// 返回订单中心订单号
					
					if("1".equals(requ.getIs_update())){
						//更新的订单不会更新原有订单的外部单号，这时外部单号为后台生成的意向单的单号
						orderTree = CommonDataFactory.getInstance()
								.getOrderTreeByOutId(requ.getRel_order_id());
					}else{
						orderTree = CommonDataFactory.getInstance()
								.getOrderTreeByOutId(orderCtnResp.getBase_order_id());
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
						if(!StringUtils.isEmpty(advancePay)) {
							phoneInfoBusiRequest.setAdvancePay(advancePay);
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
	
	private String dataCheck(OrderBeeReq requ) {
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
}
