package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import params.ZteError;
import params.cart.req.CartAddReq;
import params.coqueue.req.CoQueueAddReq;
import params.coqueue.resp.CoQueueAddResp;
import params.goods.sales.req.GoodsSalesReq;
import params.goods.sales.resp.GoodsSalesResp;
import params.member.req.MemberByIdReq;
import params.member.resp.MemberAddressQryResp;
import params.member.resp.MemberByIdResp;
import params.order.req.ActionRuleReq;
import params.order.req.AddCartAndCreateOrderReq;
import params.order.req.DlyTypeReq;
import params.order.req.MemberOrdReq;
import params.order.req.OrderCountResp;
import params.order.req.OrderReq;
import params.order.req.OrderResp;
import params.order.req.OrderSyReq;
import params.order.req.OrderTotalReq;
import params.order.req.PaymentListReq;
import params.order.req.ShowOrderReq;
import params.order.resp.DlyTypeResp;
import params.order.resp.MemberOrdResp;
import params.order.resp.OrderOuterResp;
import params.order.resp.OrderSyResp;
import params.order.resp.OrderTotalResp;
import params.order.resp.PaymentListResp;
import params.order.resp.ShowOrderResp;
import params.paycfg.req.PaymentCfgListReq;
import rule.RuleInvoker;
import rule.params.accept.resp.AcceptRuleResp;
import rule.params.userstaff.req.OrderOwnerUserReq;
import rule.params.userstaff.resp.OrderOwnerUserResp;
import util.OrderThreadLocalHolder;
import utils.CoreThreadLocalHolder;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.iservice.IMemberAddressService;
import zte.net.iservice.IRegionService;
import zte.params.addr.req.MemberAddressAddReq;
import zte.params.order.req.OrderAddReq;
import zte.params.order.resp.OrderAddResp;
import zte.params.region.req.RegionsGetReq;
import zte.params.region.resp.RegionsGetResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.app.base.core.model.MemberAddress;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Coupons;
import com.ztesoft.net.mall.core.model.DlyType;
import com.ztesoft.net.mall.core.model.GoodsUsers;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderMeta;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.OrderQueryParam;
import com.ztesoft.net.mall.core.model.PayCfg;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.model.support.CartItem;
import com.ztesoft.net.mall.core.model.support.OrderPrice;
import com.ztesoft.net.mall.core.service.ICartManager;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IDlyTypeManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IOrderMetaManager;
import com.ztesoft.net.mall.core.service.IOrderUtils;
import com.ztesoft.net.mall.core.service.IPromotionManager;
import com.ztesoft.net.mall.core.service.IWarehousePurorderMamager;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.WarehousePurorder;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.rop.annotation.ServiceMethodBean;
import commons.CommonTools;

import consts.ConstsCore;

/**
 * 订单接口
 *
 * @作者 MoChunrun
 * @创建日期 2013-9-24
 * @版本 V 1.0
 */
@ServiceMethodBean(version = "1.0")
public class OrderServ extends ServiceBase implements OrderInf {

	
    private IDlyTypeManager dlyTypeManager;
    private ICartManager cartManager;
    //private IPaymentManager paymentManager;
    private PaymentCfgInf paymentCfgServ;
    private IOrderManager orderManager;
    
    private IMemberAddressService memberAddressService;
   // private ICommonPayHander commonPayHander;

    @Resource
    private IDcPublicInfoManager dcPublicInfoManager;
    @Resource
    private OrderRuleInf orderRuleServ;

    @Resource
    private CouponInf couponServ;

    @Resource
    private PromotionInf promotionServ;

    @Resource
    private ProductInf proudctServ;

    private OrderOuterInf orderOuterServ;
    private OrderSyInf orderSyServ;
    private OrderActionRuleInf orderActionRuleServ;

    private IOrderUtils orderUtils;

    private ICartService cartServ;
    private IWarehousePurorderMamager warehousePurorderMamager;
    private IOrderMetaManager orderMetaManager;

    private MemberInf memberServ;
	private MemberAddressInf memberAddressServ;
	@Resource
	private IRegionService regionService;
	@Resource
	private IPromotionManager promotionManager;
	
    /**
     * 查询银行列表
     *
     * @param bp
     * @return
     * @作者 MoChunrun
     * @创建日期 2013-9-25
     */
   /* @ServiceMethod(method = "orderServ.qryBankList", version = "1.0", needInSession = NeedInSessionType.YES, timeout = 300000)
    //声明开放为rop模式
    public BankListResp qryBankList(ZteRequest bp) {
        try {
            BankListResp op = new BankListResp();
            List bankList = commonPayHander.getBankList();
            op.setBankList(bankList);
            op.setError_code("0");
            op.setError_msg("成功");
            op.setSession_id(bp.getSessionId());
            addReturn(bp, op);
            return op;
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }*/

    /**
     * 去结算
     *
     * @param json
     * @作者 MoChunrun
     * @创建日期 2013-9-24
     */
    @Override
	public ShowOrderResp showOrder(ShowOrderReq cp) {
        try {
            ShowOrderResp op = new ShowOrderResp();
            if (cp.getUserSessionId() == null || "".equals(cp.getUserSessionId()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "session_id不能为空"));
            Member member = CommonTools.getLoginMember();
            
            if(member == null && cp.getMember_id() != null && !"".equals(cp.getMember_id())){
            	MemberByIdReq memberQryReq = new MemberByIdReq();
				memberQryReq.setMember_id(cp.getMember_id());
				MemberByIdResp resp = memberServ.getMemberById(memberQryReq);
				if("0".equals(resp.getError_code())){
					member = resp.getMember();
				}
			}
            if (member == null) {
            	CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "会员ID有误"));
            }
            Coupons coupon = null;
            if(!StringUtils.isEmpty(cp.getCoupon_code()) && member!=null)
            	coupon = promotionServ.useCoupon(cp.getCoupon_code(), member.getMember_id());
            if (cp.getMember_lv_id() != null && !CommonTools.checkMemberLvId(cp.getMember_lv_id(), member))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "用户等级ID[" + cp.getMember_lv_id() + "]有误"));
            //计置当当登录用户信息，查询列表时可以获取到当前登录用户
            ThreadContextHolder.getSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
            String sessionid = cp.getUserSessionId();
            if (member != null) {
                //读取此会员的收货地址列表
            	MemberAddressQryResp resp = memberAddressServ.getMemberAddressList(cp);
				if("0".equals(resp.getError_code())){
					op.setAddressList(resp.getMemberAddressList());
				}
            }

            //读取购物车中的商品列表 已计算会员价
            List goodsItemList = cartManager.listGoods(member,sessionid,coupon);
            List<CartItem> checkedGoodsItemList = new ArrayList<CartItem>();
            for (int i = 0; goodsItemList != null && i < goodsItemList.size(); i++) {
                CartItem cartItem = (CartItem) goodsItemList.get(i);
                if ("1".equals(cartItem.getIs_checked())) {
                    checkedGoodsItemList.add(cartItem);
                }
            }
            goodsItemList = checkedGoodsItemList;
            List giftItemList = cartManager.listGift(member,sessionid);
            List pgkItemList = cartManager.listPgk(member,sessionid);
            List groupBuyList = cartManager.listGroupBuy(sessionid);

            op.setGiftItemList(giftItemList);
            op.setGoodsItemList(goodsItemList);
            op.setPgkItemList(pgkItemList);
            op.setGroupBuyList(groupBuyList);

            //读取支付方式列表
            PaymentCfgListReq req = new PaymentCfgListReq();
            List paymentList  = paymentCfgServ.queryPaymentCfgList(req).getPayCfgList();
            //List paymentList = this.paymentManager.list();
            
            op.setPaymentList(paymentList);


            if (member != null) {
                //显示可享受的优惠规则
                Double originalTotal = cartManager.countGoodsTotal(member,sessionid, true);
                List pmtList = this.promotionServ.list(originalTotal, cp.getMember_lv_id() == null ? member.getLv_id():cp.getMember_lv_id(),coupon);
                op.setPmtList(pmtList);

                //显示可获得的赠品
                List giftList = this.promotionServ.listGift(pmtList);
                op.setGiftList(giftList);
            }
            op.setError_code("0");
            op.setError_msg("成功");
            op.setUserSessionId(op.getUserSessionId());
            addReturn(cp, op);
            return op;
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }

    /**
     * 下单,第三方系统订单处理
     * @作者 wui
     * @创建日期 2013-9-24
     * @param json
     */
    @SuppressWarnings("unchecked")
    @Override
    public OrderAddResp createOuterOrder(OrderAddReq req) throws Exception{
    	   CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(req);
    	   OrderAddResp orderAddResp = new OrderAddResp();
	        try {
	        	//同步外系统订单
	            List<OrderOuterResp> orderOuterResps = addOrderOuter(req);

	            //外系统订单同步
	            syOuterOrder(req, orderAddResp, orderOuterResps);
	          
	        } catch (Throwable e) {
//	        	e.printStackTrace();
	        	//错误日志
	        	orderError(orderAddResp, e);
	            
	        } finally {
	        	CoreThreadLocalHolder.getInstance().clear();
	            OrderThreadLocalHolder.getInstance().clear();
	        }
	        return orderAddResp;
		}

	private void orderError(OrderAddResp orderAddResp, Throwable e)
			throws Exception {
		StackTraceElement stacks [] = e.getStackTrace();
//		StringBuffer errMsgBuffer = new StringBuffer();
//		for (int i = 0; i < stacks.length; i++) {
//			errMsgBuffer.append(stacks[i].toString()).append("\n");
//		}
//		e.printStackTrace();
		orderAddResp.setError_code("-1");
		ZteError entity = CoreThreadLocalHolder.getInstance().getZteCommonData().getZteError();
		if (null != entity) {
			orderAddResp.setError_msg(StringUtils.isEmpty(entity.getError_msg()) ? "" : entity.getError_msg());
		}
		if (null != entity && Consts.EXP_BUSS.equals(entity.getType_code())) {
		    throw new Exception(e.getMessage());
		}
		
		cartManager.clean(CommonTools.getUserSessionId(), false);
		//系统程序的异常，不属于业务异常
		throw new RuntimeException(e);
	}

    //插入订单
	@Override
	public List<Order> syOuterOrder(OrderAddReq req, OrderAddResp orderAddResp,List<OrderOuterResp> orderOuterResps) throws ApiBusiException {
		try{
			//订单同步处理
			long start = System.currentTimeMillis();
			//logger.info(" 标准化信息入库写入es_order表========================================>");
			OrderSyReq syReq = new OrderSyReq();
			syReq.setOrderOuterResps(orderOuterResps);
			syReq.setParamsl(req.getParamsl());
			long start0 = System.currentTimeMillis();
			OrderSyResp syResp= orderSyServ.syNOrder(syReq);
			long end = System.currentTimeMillis();
			//logger.info(" 标准化信息入库写入es_order表完成，总耗时========================================>"+(end-start));
			start = System.currentTimeMillis();
			//logger.info(" 标准化信息入库写入es_order扩展信息表表开始========================================>"+(end-start));
			//订单规则触发处理
			for (Order order : syResp.getOrders()) {
				logger.info("订单ID============="+order.getOrder_id());
			    //同步返回处理
			    ActionRuleReq arreq = new ActionRuleReq();
			    arreq.setUserSessionId(req.getUserSessionId());
			    arreq.setOrder_id(order.getOrder_id());
			    arreq.setPayment_id(orderOuterResps.get(0).getOrderOuter().getPayment_id());
			    arreq.setAppKey(req.getAppKey());
			    arreq.setSourceFrom(req.getSourceFrom());
			    arreq.setOrderItemList(order.getOrderItemList());
			    arreq.setZteRequest(req.getZteRequest());
			    arreq.setOrder(order);
			    arreq.setUserSessionId(req.getUserSessionId());
			    arreq.setOrderOuters(syResp.getOrderOuters());
			    arreq.setParamsList(req.getParamsl());
			    
			    long start1 = System.currentTimeMillis();
			    orderActionRuleServ.confirmOrder(orderAddResp,arreq);
			    end = System.currentTimeMillis();
				//logger.info(" 标准化信息入库写入es_order扩展表表完成，总耗时========================================>"+(end-start));
				if(Const.ORDERSTANDARDIZE_CODE.equals(order.getService_code())){
				    CoQueueAddReq coreq = new CoQueueAddReq();
				    coreq.setCo_name("订单同步");
				    coreq.setBatch_id(order.getOrder_id());
				    coreq.setService_code(Consts.SERVICE_CODE_CO_DINGDAN);//CO_DINGDAN
				    coreq.setAction_code("A");
				    coreq.setObject_type("DINGDAN");
				    coreq.setObject_id(order.getOrder_id());
				    coreq.setOper_id("-1");
				    String sys_code = orderManager.getOrderSysCode(order.getOrder_id());
				    coreq.setSys_code(sys_code);
					for(int j=0;j<3;j++){
						try{
							ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
							CoQueueAddResp coresp = client.execute(coreq, CoQueueAddResp.class);
							//CoQueueAddResp coresp = coQueueService.add(coreq);
							break ;
						}catch(Exception ex){
							ex.printStackTrace();
							continue ;
						}
					}
			    }
			}
		
			//返回信息处理
			orderAddResp.setOrderList(syResp.getOrders());
			orderAddResp.setError_code("0");
			orderAddResp.setError_msg("成功");
			cartManager.clean(CommonTools.getUserSessionId(), false);
			return syResp.getOrders();
		}catch(Exception ex){
			ex.printStackTrace();
//			throw new ApiBusiException(ex.getMessage());
			CommonTools.addFailError(ex.getMessage());
			return null;
		}
	}

	
	@Override
	public void launchOrder(OrderAddReq req, OrderAddResp resp,List<OrderOuterResp> orderOuterResps){
			for (Order order:resp.getOrderList()) {
				//添加订单推送消息队列================================
		    	if(Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code())){
					//更新内存对象
				    OrderTreeBusiRequest orderTreeBusiRequest = new OrderTreeBusiRequest();
					orderTreeBusiRequest.setOrder_id(order.getOrder_id());
					orderTreeBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					orderTreeBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderTreeBusiRequest.setCreate_time(Consts.SYSDATE);
					orderTreeBusiRequest.setUpdate_time(Consts.SYSDATE);
					orderTreeBusiRequest.store();
//					this.baseDaoSupport.execute("insert into es_order_trees(order_id,create_time,update_time,source_from) values('"+order.getOrder_id()+"',sysdate,sysdate,'"+ManagerUtils.getSourceFrom()+"')", null); //做到事物控制一致性
					
		    	}
			}
	}
	//外系统订单同步
	private List<OrderOuterResp> addOrderOuter(OrderAddReq req)throws ApiBusiException, Exception {
		//初始化
		initParam(req);
		
		List<OrderOuterResp> orderOuterResps = new ArrayList<OrderOuterResp>();
		//订单同步到外系统订单
		if (!ListUtil.isEmpty(req.getParamsl())) {
		    for (int i = 0; i < req.getParamsl().size(); i++) {
				Map pMap = req.getParamsl().get(i);
		        //验证
		        AcceptRuleResp acceptRuleResp = RuleInvoker.validateAcceptInst(pMap);
		        //生成外系统订单
		        orderOuterResps.add(orderOuterServ.cerateOuterOrder(pMap));
		    }
		}
		return orderOuterResps;
	}
    
    
    @Override
	@SuppressWarnings("unchecked")
    public  void initParam(OrderAddReq req) throws ApiBusiException {

    	//转换成列表
        if (req.getParamsl() == null || req.getParamsl().size() == 0) {
            req.getParamsl().add(req.getParams());
        }
        if (req.getParams() == null) {
        	req.setParams(req.getParamsl().get(0));
        }
        
    	//add by wui
    	if(req.getParamsl()!=null && req.getParamsl().size()>0){
    		String order_type = (String) req.getParamsl().get(0).get("order_type");
    		if(StringUtil.isEmpty(order_type))
    			order_type = OrderStatus.ORDER_TYPE_1;
    	}else if(req.getParams()!=null){
    		String order_type = (String) req.getParams().get("order_type");
    		if(StringUtil.isEmpty(order_type))
    			order_type = OrderStatus.ORDER_TYPE_1;
    	}
    	
	   for (Map map : req.getParamsl()){
	    if (map != null) {
             String source_from = (String) map.get("source_from");
             if (StringUtil.isEmpty(source_from)) {
                 map.put("source_from", StringUtil.isEmpty(req.getSourceFrom())? CommonTools.getSourceForm():req.getSourceFrom());
             }
             
             if(!StringUtil.isEmpty(req.getAppKey()))
            	 map.put("app_key", req.getAppKey());
             
             if (OrderThreadLocalHolder.getInstance().getOrderCommonData().getAdminUser() != null) {
                 String name = (String) map.get("name");
                 if (StringUtil.isEmpty(name)) {
                     map.put("name", OrderThreadLocalHolder.getInstance().getOrderCommonData().getAdminUser().getRealname());
                 }

                 String uname = (String) map.get("uname");
                 if (StringUtil.isEmpty(uname)) {
                     map.put("uname", OrderThreadLocalHolder.getInstance().getOrderCommonData().getAdminUser().getUsername());
                 }
             }
             CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(req);
             Product product =proudctServ.get((String) map.get("product_id"));
             //对象拷贝
             map.put("service_code", req.getService_code());
             map.put("ship_amount", req.getShip_amount());
             map.put("create_type", req.getCreate_type());
             map.put("userSessionId", req.getUserSessionId());
             map.put("goods_id", product.getGoods_id());
             map.put("spread_member_id", req.getSpread_member_id());
             map.put("service_type", req.getService_type());
             if(!StringUtil.isEmpty(req.getService_id()))
            	 map.put("service_id", req.getService_id());
             map.put("warehousePurorder", req.getWarehousePurorder());
             
	    }
	  }
        //模拟单点登录 add by wui
        String userid = (String) req.getParamsl().get(0).get("userid");
        //用户id为空，则触发配置规则，设置userid
        if (StringUtil.isEmpty(userid)) {
            OrderOwnerUserReq orderOwnerUserReq = new OrderOwnerUserReq();
            orderOwnerUserReq.setInMap(req.getParams());
            orderOwnerUserReq.setAppKey(req.getAppKey());
            OrderOwnerUserResp orderOwnerUserResp = RuleInvoker.setOrderOwnerUser(orderOwnerUserReq);
            if (orderOwnerUserResp != null) {
                userid = orderOwnerUserResp.getUserid();
                req.getParams().put("userid", userid);
            }
        }
        if (!StringUtil.isEmpty(userid)) {
            AdminUser adminUser = orderUtils.getAdminUserById(userid);
            OrderThreadLocalHolder.getInstance().getOrderCommonData().setAdminUser(adminUser);
        }
    }

    /**
     * 下单
     *
     * @param json
     * @作者 MoChunrun
     * @创建日期 2013-9-24
     */
    @Override
	@SuppressWarnings("unchecked")
    public OrderAddResp createOrder(OrderOuter cp) {
        try {
        	OrderAddResp op = new OrderAddResp();
            if (cp.getUserSessionId() == null || "".equalsIgnoreCase(cp.getUserSessionId()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "session_id不能为空"));
            if (cp.getPayment_id() == null || "".equalsIgnoreCase(cp.getPayment_id()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "payment_id不能为空"));
            //if(cp.getShipping_id()==null || "".equalsIgnoreCase(cp.getShipping_id()))CommonTools.addError(new ErrorEntity(ConstsCore.ERROR_FAIL,"shipping_id不能为空"));
            if (cp.getMember_id() == null || "".equalsIgnoreCase(cp.getMember_id()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "member_id不能为空"));
            Coupons coupon = null;
            
            
            //会员获取
            Member member = null;
        	MemberByIdReq memberQryReq = new MemberByIdReq();
			memberQryReq.setMember_id(cp.getMember_id());
			MemberByIdResp memResp = memberServ.getMemberById(memberQryReq);
			if("0".equals(memResp.getError_code())){
				member = memResp.getMember();
			}
            if (member == null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "会员ID有误"));
            if(!StringUtils.isEmpty(cp.getCoupon_code()))
            	coupon = promotionManager.useCoupon(cp.getCoupon_code(), member.getMember_id());
            if (coupon != null && !couponServ.hascouponUseTimes(coupon.getMemc_code(),cp.getMember_id())) {
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "你的优惠券使用次数已用完"));
            }
            cp.setCoupon(coupon);//设置优惠券 mochunrun2015-01-29------------------------
            if (!StringUtils.isEmpty(cp.getMember_lv_id()) && !CommonTools.checkMemberLvId(cp.getMember_lv_id(), member))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "用户等级ID[" + cp.getMember_lv_id() + "]有误"));
          
            //会员地址
            List<Order> orders = new ArrayList<Order>();
            String shippingId = cp.getShipping_id();
            
//            MemberAddressReq memberAddressReq = new MemberAddressReq();
//			memberAddressReq.setAddress_id(cp.getAddress_id());
//			MemberAddress memberAddress = null;
//            if(StringUtil.isEmpty(memberAddressReq.getAddress_id())) {//是新增地址
//            	memberAddress = createAddress(cp);
//			}else{ //选择已有的地址 
//				MemberAddressQryResp resp = memberAddressServ.getAddressById(memberAddressReq);
//				if("0".equals(resp.getError_code())){
//					memberAddress = resp.getMemberAddress();
//				}
//			}
            
            MemberAddress memberAddress = null;
//          if(StringUtil.isEmpty(memberAddressReq.getAddress_id())) {//是新增地址
          	memberAddress = createAddress(cp);
//			}else{ //选择已有的地址 
//				MemberAddressQryResp resp = memberAddressServ.getAddressById(memberAddressReq);
//				if("0".equals(resp.getError_code())){
//					memberAddress = resp.getMemberAddress();
//				}
//			}
          	
            
            List<Map<String, String>> staffNos = this.orderManager.qryStaffNoBySessionID(cp.getUserSessionId());
            if (staffNos == null || staffNos.size() == 0)
                CommonTools.addError(new ZteError(ConstsCore.NULL_CART, "购物车为空"));
            String batchID = UUID.randomUUID().toString().replace("-", "");
            for (Map<String, String> stno : staffNos) {

                Order order = new Order();
                //add by wui外系统同步订单id
                if (!StringUtil.isEmpty(cp.getUserid())) {
                    order.setUserid(cp.getUserid());
                    //设置订单归属对象
                    AdminUser adminUser = OrderThreadLocalHolder.getInstance().getOrderCommonData().getAdminUser();
                    if (adminUser != null)
                        order.setOrder_adscription_id(adminUser.getFounder() + "");
                }else{
                	order.setUserid(stno.get("staff_no"));
                }


                //获取商品id
                List<Map<String,String>> goods = this.orderManager.qryGoodsByStaffNo(stno.get("staff_no"));
                order.setGoods_id(goods.get(0).get("goods_id"));

                order.setCreate_type(Integer.valueOf(cp.getCreate_type()));
                order.setBatch_id(batchID);
                order.setSource_from(cp.getSource_from());
                order.setShipping_id(shippingId); //配送方式
                try{
                	Integer paymentId = Integer.valueOf(cp.getPayment_id());
                	order.setPayment_id(paymentId);//支付方式
                }catch(Exception ex){
                	
                }
                //order.setBank_id(bank_id);//支付银行
                if(!StringUtils.isEmpty(cp.getShip_addr())){
                	order.setShip_addr(cp.getShip_addr());
                }else{
                	order.setShip_addr(memberAddress.getAddr());
                }
                if(!StringUtils.isEmpty(cp.getShip_mobile())){
                	order.setShip_mobile(cp.getShip_mobile());
                }else{
                	order.setShip_mobile(memberAddress.getMobile());
                }
                if(!StringUtils.isEmpty(cp.getShip_tel())){
                	order.setShip_tel(cp.getShip_tel());
                }else{
                	order.setShip_tel(memberAddress.getTel());
                }
                if(!StringUtils.isEmpty(cp.getShip_zip())){
                	order.setShip_zip(cp.getShip_zip());
                }else{
                	order.setShip_zip(memberAddress.getZip());
                }
                order.setShipping_area(memberAddress.getProvince() + "-" + memberAddress.getCity() + "-" + memberAddress.getRegion());
                if(!StringUtils.isEmpty(cp.getShip_name())){
                	order.setShip_name(cp.getShip_name());
                }else{
                	order.setShip_name(memberAddress.getName());
                }
                order.setRegionid(memberAddress.getRegion_id());
                order.setType_code(OrderBuilder.COMMONAGE);
                order.setShip_day(cp.getShip_day());
                order.setService_code(cp.getService_code());
                order.setApp_key(cp.getApp_key());
                order.setShip_time("sysdate");
                order.setRemark(cp.getRemark());
                //发票信息
                order.setInvoice_content(cp.getInvoice_content());
                order.setInvoice_title(cp.getInvoice_title());
                order.setInvoice_title_desc(cp.getInvoice_title_desc());
                order.setInvoice_type(cp.getInvoice_type());
                order.setApp_key(cp.getApp_key());
                order.setDly_address_id(cp.getDlyaddressid());
                order.setLan_id(cp.getLan_code());
                //order_type
                String order_type = cp.getOrder_type();
                order.setSpread_id(cp.getSpread_member_id());
                order.setService_type(cp.getService_type());
                order.setService_id(cp.getService_id());
                if(!StringUtils.isEmpty(order_type)){
                	order.setOrder_type(order_type);
                }else{
                	order.setOrder_type(OrderStatus.ORDER_TYPE_1);
                }
                //处理用户工号信息
                dealUsers(cp, order, order.getGoods_id());
                String order_id = this.baseDaoSupport.getSequences("s_es_order");
                CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
                String prex = cacheUtil.getConfigInfo("ORDER_PREX"); // 订单Id前缀               2017年10月18日AOP后激活需求订单号统一加D   songqi
                List<Map> list = cp.getParamsl();
                //订单ID加前缀=====
                if("ECS".equals(ManagerUtils.getSourceFrom())){
                	 if(list!=null && list.size()>0){
                      	Map map = list.get(0);
                      	String cityCode = (String) map.get("order_city_code");
                      	if(!StringUtil.isEmpty(cityCode)){
                      		RegionsGetReq rqp = new RegionsGetReq();
                      		rqp.setRegion_id(cityCode);
                      		RegionsGetResp resp = regionService.getRegion(rqp);
                      		if(resp!=null && resp.getRegions()!=null){
                      			prex = resp.getRegions().getRegion_code();
                      		}
                      	}
                      	
          				Map outOrderMap = new HashMap();
          				outOrderMap = (cp.getParamsl().get(0));
          				String plat_type = (String)outOrderMap.get("plat_type");
          				String order_from  = (String)outOrderMap.get("order_from");//获取商城来源
          				if(CommonDataFactory.getInstance().isZbOrder(plat_type)){
          					order_from = EcsOrderConsts.ORDER_FROM_10003;
          				}

          				//淘宝编码转内部商城编码
          				List<Map> relations = CommonDataFactory.getInstance().getDictRelationListData(StypeConsts.ISTBORDER);
          				if(relations!=null && relations.size()>0){
          					for(Map relation : relations){
          						if(order_from.equals(Const.getStrValue(relation, "field_value"))){
          							order_from = Const.getStrValue(relation, "other_field_value");
          							break ;
          						}
          					}
          				}
          				
//          				CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
//          				List orderFromList = cacheUtil.doDcPublicQuery(EcsOrderConsts.ORDER_FROM_STYPE);
//          				//然后根据商城来源和region_code 获取 静态数据 DIC_ORDER_ORIGIN 的pkey 然后+order_id
//              		    if(orderFromList!=null&&orderFromList.size()>0){
//              		    	for(int i=0;i<orderFromList.size();i++){
//              		    		Map  orderFromMap = new HashMap();
//              		    		orderFromMap = (Map)orderFromList.get(i);
//              		    		if(orderFromMap!=null){
//              		    			String codea = orderFromMap.get("codea").toString();
//          		    				String pkey =  orderFromMap.get("pkey").toString();
//              		    			boolean flag = false;
//          		    				if(order_from.equals(codea) && !StringUtil.isEmpty(pkey)){
//          		    					prex += pkey;
//          		    				}
//              		    		}
//              		    	}
//              		    }
          				order_id = prex+order_id;
                      	
                      }
                }
                for(Map map:cp.getParamsl()){
                	if(null!=map.get("intent_order_id")&&!StringUtils.isEmpty(map.get("intent_order_id")+"")){
                		String sql ="select t.order_id from es_order_intent t where t.intent_order_id='"+map.get("intent_order_id")+"' and t.source_from='"+ManagerUtils.getSourceFrom()+"'";
                		order_id = this.baseDaoSupport.queryForString(sql);
                		if(StringUtils.isEmpty(order_id)){
                			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "根据intent_order_id获取order_id异常"));
                		}
                	}
                }
                //订单ID加前缀=====
                order.setOrder_id(order_id);
                order.setSource_from(cp.getSource_from());
                String goodsids = "";
                for(Map<String,String> map:goods){
                	goodsids += map.get("goods_id")+",";
                }
                if(goodsids.length()>1)
                	goodsids = goodsids.substring(0,goodsids.length()-1);
                
                boolean createrOrder = orderManager.isCreateOrder(cp.getService_code(), goodsids);
                order.setCreaterOrder(createrOrder+"");
                this.orderManager.add(cp,member,order, cp.getUserSessionId(), stno.get("staff_no"));
                orders.add(order);
            }
            if (coupon != null) {
                couponServ.updateUseTimes(member.getMember_id(), coupon.getMemc_code(), 1);
                ThreadContextHolder.getSessionContext().removeAttribute("coupon");
            }

            if (orders.size() > 0) {
                op.setError_code("0");
                op.setError_msg("成功");
                op.setOrderList(orders);
                op.setBatch_id(batchID);
                //增加采购单记录 add by wui先屏蔽掉，后续放开处理
                if (cp != null && !StringUtil.isEmpty(cp.getCreate_type())) {
                    WarehousePurorder wp = cp.getWarehousePurorder();
                    if (wp != null) {
                        wp.setZ_order_id(batchID);
                        wp.setCreate_type(cp.getCreate_type());
                        if (StringUtil.isEmpty(wp.getAudit_status()))
                            wp.setAudit_status(OrderStatus.WP_AUDIT_STATUS_0);
                        wp.setPru_status(OrderStatus.WP_PRU_STATUS_0);
                        this.warehousePurorderMamager.add(wp);
                    }
                }
                addReturn(cp, op);
            } else {
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "下单失败"));
            }
            return op;
        } catch (RuntimeException ex) {
        	ex.printStackTrace();
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }

    private void dealUsers(OrderOuter orderOuter, Order order, String goods_id) throws Exception {
        GoodsUsers goodsUsers = orderRuleServ.getGoodsUsersByGoodsId(goods_id, order.getSource_from(), order.getService_code());
      // cp.setGoodsUsers(goodsUsers);

        //根据商品设置用户受理工号、发货工号、支付工号等基本信息,此处需要转换处理
        if(goodsUsers!=null){
        	BeanUtils.copyProperties(order, orderRuleServ.dealTransferGoodsUsers(goodsUsers));
        }
    }


    /**
     * 查看配送方式
     *
     * @param json
     * @作者 MoChunrun
     * @创建日期 2013-9-24
     */
    @Override
	public DlyTypeResp showDlyType(DlyTypeReq cp) {
        try {
            DlyTypeResp op = new DlyTypeResp();
            if (cp.getUserSessionId() == null || "".equalsIgnoreCase(cp.getUserSessionId()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "session_id不能为空"));
            if (cp.getRegion_id() == null || "".equalsIgnoreCase(cp.getRegion_id()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "region_id不能为空"));
            
            Member member = UserServiceFactory.getUserService().getCurrentMember();
            Double orderPrice = cartManager.countGoodsTotal(member,cp.getUserSessionId(), true);
            Double weight = cartManager.countGoodsWeight(cp.getUserSessionId(), true);
            List<DlyType> dlyTypeList = this.dlyTypeManager.list(weight, orderPrice, cp.getRegion_id());
            op.setDlyTypeList(dlyTypeList);
            op.setError_code("0");
            op.setError_msg("成功");
            addReturn(cp, op);
            return op;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception ex) {
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }

    /**
     * 显示订单总金额
     *
     * @param json
     * @作者 MoChunrun
     * @创建日期 2013-9-24
     */
    @Override
	public OrderTotalResp showOrderTotal(OrderTotalReq cp) {
        try {
            OrderTotalResp ops = new OrderTotalResp();
            if (cp.getUserSessionId() == null || "".equalsIgnoreCase(cp.getUserSessionId()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "session_id不能为空"));
            if (cp.getMember_id() == null || "".equalsIgnoreCase(cp.getMember_id()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "member_id不能为空"));
            if (cp.getRegion_id() == null || "".equalsIgnoreCase(cp.getRegion_id()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "region_id不能为空"));
            if (cp.getDlyType_id() == null || "".equalsIgnoreCase(cp.getDlyType_id()))
                CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "dlyType_id不能为空"));

            MemberByIdReq memberByIdReq = new MemberByIdReq();
            memberByIdReq.setMember_id(cp.getMember_id());
			MemberByIdResp resp = memberServ.getMemberById(memberByIdReq);
			if("0".equals(resp.getError_code())){
				cp.setMember(resp.getMember());
			}
			
			if(cp.getMember() == null){
				CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"会员ID有误"));
			}
			Coupons coupon = null;
            if(!StringUtils.isEmpty(cp.getCoupon_code()) && cp.getMember()!=null)
            	coupon = promotionServ.useCoupon(cp.getCoupon_code(), cp.getMember().getMember_id());
            //计置当当登录用户信息，查询列表时可以获取到当前登录用户
            ThreadContextHolder.getSessionContext().setAttribute(IUserService.CURRENT_MEMBER_KEY, cp.getMember());
            String typeId = cp.getDlyType_id();
            String regionId = cp.getRegion_id();
            boolean isProtected = "1".equals(cp.getIsProtected());
            //按订单计算物流价再算总和===========mochunrun=======================
            OrderPrice orderPrice = new OrderPrice();
            List<CartItem> goodsItemList = cartManager.listGoods(cp.getMember(),cp.getUserSessionId(),coupon);
            List<CartItem> checkedGoodsItemList = new ArrayList<CartItem>();
            for (int i = 0; goodsItemList != null && i < goodsItemList.size(); i++) {
                CartItem cartItem = goodsItemList.get(i);
                if ("1".equals(cartItem.getIs_checked())) {
                    checkedGoodsItemList.add(cartItem);
                }
            }
            Set<String> set = new HashSet<String>();
            for (CartItem c : checkedGoodsItemList) {
                set.add(c.getStaff_no());
            }
            String[] stafs = set.toArray(new String[0]);
            OrderOuter ot = new OrderOuter();
        	ot.setCoupon(coupon);
            for (String s : stafs) {
                OrderPrice op = this.cartManager.countPrice(ot,cp.getMember(),cp.getUserSessionId(), typeId, regionId, isProtected, true, s);
                if (op.getOriginalPrice() != null)
                    orderPrice.setOriginalPrice((orderPrice.getOriginalPrice() == null ? 0 : orderPrice.getOriginalPrice()) + op.getOriginalPrice());
                if (op.getShippingPrice() != null)
                    orderPrice.setShippingPrice((orderPrice.getShippingPrice() == null ? 0 : orderPrice.getShippingPrice()) + op.getShippingPrice());
                if (op.getProtectPrice() != null)
                    orderPrice.setProtectPrice((orderPrice.getProtectPrice() == null ? 0 : orderPrice.getProtectPrice()) + op.getProtectPrice());
                if (op.getDiscountPrice() != null)
                    orderPrice.setDiscountPrice((orderPrice.getDiscountPrice() == null ? 0 : orderPrice.getDiscountPrice()) + op.getDiscountPrice());
                if (op.getPoint() != null)
                    orderPrice.setPoint((orderPrice.getPoint() == null ? 0 : orderPrice.getPoint()) + op.getPoint());
                if (op.getOrderPrice() != null)
                    orderPrice.setOrderPrice((orderPrice.getOrderPrice() == null ? 0 : orderPrice.getOrderPrice()) + op.getOrderPrice());
                if (op.getGoodsPrice() != null)
                    orderPrice.setGoodsPrice((orderPrice.getGoodsPrice() == null ? 0 : orderPrice.getGoodsPrice()) + op.getGoodsPrice());
                if (op.getWeight() != null)
                    orderPrice.setWeight((orderPrice.getWeight() == null ? 0 : orderPrice.getWeight()) + op.getWeight());
                orderPrice.setStaffCount(orderPrice.getStaffCount() + op.getStaffCount());
                orderPrice.getDiscountItem().putAll(op.getDiscountItem());

            }
            //按订单计算物流价再算总和==================================

            ops.setOrderPrice(orderPrice);
            ops.setError_code("0");
            ops.setError_msg("成功");
            addReturn(cp, ops);
            return ops;
        } catch (RuntimeException ex) {
        	ex.printStackTrace();
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        } catch (Exception ex) {
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }

    @Override
    public OrderAddResp addCartAndCreateOrder(
            AddCartAndCreateOrderReq req) {
        try {
            if (req.getUserSessionId() == null || "".equals(req.getUserSessionId()))
                req.setUserSessionId(CommonTools.createSessionId());
            CartAddReq cart = new CartAddReq();
            BeanUtils.copyProperties(cart, req);
            cartServ.add(cart);
            OrderOuter order = new OrderOuter();
            BeanUtils.copyProperties(order, req);
            OrderAddResp resp = this.createOrder(order);
            return resp;
        } catch (Exception ex) {
            ex.printStackTrace();
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "系统繁忙"));
            return null;
        }
    }

    @Override
    public PaymentListResp qryPaymetList(PaymentListReq req) {
        PaymentListResp resp = new PaymentListResp();
        List<PayCfg> pcList = this.baseDaoSupport.queryForList(SF.orderSql("SERVICE_PAYMENT_CFG_SELECT"), PayCfg.class);
        resp.setPaymentList(pcList);
        resp.setError_code("0");
        resp.setError_msg("成功");
        addReturn(req, resp);
        return resp;
    }

    private MemberAddress createAddress(OrderOuter cp){
    	MemberAddress memberAddress = new MemberAddress();
		memberAddress.setMember_id(cp.getMember_id());
		memberAddress.setName(cp.getName());
		memberAddress.setTel(cp.getShip_tel());
		memberAddress.setMobile(cp.getShip_mobile());
		memberAddress.setProvince_id(cp.getProvince_id());
		memberAddress.setCity_id(cp.getCity_id());
		memberAddress.setRegion_id(cp.getRegion_id());
		memberAddress.setProvince(cp.getProvince());
		memberAddress.setCity(cp.getCity());
		memberAddress.setRegion(cp.getRegion());
		memberAddress.setAddr(cp.getShip_addr());
		memberAddress.setZip(cp.getShip_zip());
		MemberAddressAddReq req  = new MemberAddressAddReq();
		req.setMemberAddress(memberAddress);
//		memberAddressService.addMemeberAddress(req);
		return memberAddress;
	}

    public IDlyTypeManager getDlyTypeManager() {
        return dlyTypeManager;
    }

    public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
        this.dlyTypeManager = dlyTypeManager;
    }

    public ICartManager getCartManager() {
        return cartManager;
    }

    public void setCartManager(ICartManager cartManager) {
        this.cartManager = cartManager;
    }

    /*public IPaymentManager getPaymentManager() {
        return paymentManager;
    }

    public void setPaymentManager(IPaymentManager paymentManager) {
        this.paymentManager = paymentManager;
    }*/

    public IOrderManager getOrderManager() {
        return orderManager;
    }

    public void setOrderManager(IOrderManager orderManager) {
        this.orderManager = orderManager;
    }

   /* public ICommonPayHander getCommonPayHander() {
        return commonPayHander;
    }

    public void setCommonPayHander(ICommonPayHander commonPayHander) {
        this.commonPayHander = commonPayHander;
    }*/

    public OrderOuterInf getOrderOuterServ() {
        return orderOuterServ;
    }

    public void setOrderOuterServ(OrderOuterInf orderOuterServ) {
        this.orderOuterServ = orderOuterServ;
    }


    public OrderSyInf getOrderSyServ() {
        return orderSyServ;
    }

    public void setOrderSyServ(OrderSyInf orderSyServ) {
        this.orderSyServ = orderSyServ;
    }

    public OrderActionRuleInf getOrderActionRuleServ() {
        return orderActionRuleServ;
    }

    public void setOrderActionRuleServ(OrderActionRuleInf orderActionRuleServ) {
        this.orderActionRuleServ = orderActionRuleServ;
    }

    public IOrderUtils getOrderUtils() {
        return orderUtils;
    }

    public void setOrderUtils(IOrderUtils orderUtils) {
        this.orderUtils = orderUtils;
    }


    public ICartService getCartServ() {
		return cartServ;
	}

	public void setCartServ(ICartService cartServ) {
		this.cartServ = cartServ;
	}

	public IWarehousePurorderMamager getWarehousePurorderMamager() {
        return warehousePurorderMamager;
    }

    public void setWarehousePurorderMamager(
            IWarehousePurorderMamager warehousePurorderMamager) {
        this.warehousePurorderMamager = warehousePurorderMamager;
    }

    //获取会员订单
    @Override
	public MemberOrdResp listOrderByMemberId(MemberOrdReq memberOrdReq) {
        MemberOrdResp memberOrdResp = new MemberOrdResp();
        List listOrder = this.orderManager.listOrderByMemberId(memberOrdReq.getMember_id());
        memberOrdResp.setMemOrders(listOrder);
        memberOrdResp.setError_code("0");
        addReturn(memberOrdReq, memberOrdResp);
        return memberOrdResp;
    }

    @Override
	public OrderResp get(OrderReq req) {
        OrderResp orderResp = new OrderResp();
        Order order = orderManager.get(req.getOrder_id());
        orderResp.setOrder(order);
        addReturn(req, orderResp);
        return orderResp;
    }

    @Override
	public OrderResp listGoodsItems(OrderReq req) {
        OrderResp orderResp = new OrderResp();
        List orderItemsList = orderManager.listGoodsItems(req.getOrder_id());
        orderResp.setOrderItems(orderItemsList);
        addReturn(req, orderResp);
        return orderResp;
    }

    @Override
	public OrderResp listOrderMetas(OrderReq req) {
        OrderResp orderResp = new OrderResp();
        List<OrderMeta> metaList = orderMetaManager.list(req.getOrder_id());
        orderResp.setMetaList(metaList);
        addReturn(req, orderResp);
        return orderResp;
    }

    //江西采购，获取商品售卖金额
    @Override
	public GoodsSalesResp goodsSalseMoney(GoodsSalesReq goodsSalesReq) {
        GoodsSalesResp goodsSalesResp = new GoodsSalesResp();
        double d = orderManager.goodsSalseMoney(goodsSalesReq.getGoods_id(), goodsSalesReq.getLan_code());
        goodsSalesResp.setSalesP(d);
        addReturn(goodsSalesReq, goodsSalesResp);
        return goodsSalesResp;
    }

    //江西采购，获取商品售卖数量
    @Override
	public GoodsSalesResp goodsSalseCount(GoodsSalesReq goodsSalesReq) {
        GoodsSalesResp goodsSalesResp = new GoodsSalesResp();
        int d = orderManager.goodsSalseCount(goodsSalesReq.getGoods_id(), goodsSalesReq.getLan_code());
        goodsSalesResp.setSalesP(d);
        addReturn(goodsSalesReq, goodsSalesResp);
        return goodsSalesResp;
    }

    @Override
	public OrderResp censusState(OrderReq req) {
        OrderResp orderResp = new OrderResp();
        Map userIndexInfo = orderManager.censusState();
        orderResp.setUserIndexInfo(userIndexInfo);
        addReturn(req, orderResp);
        return orderResp;
    }

    @Override
	public OrderCountResp countOrders(OrderQueryParam ordParam) {
        OrderCountResp resp = new OrderCountResp();
        int count = orderManager.countOrders(ordParam);
        resp.setCount(count);
        addReturn(ordParam, resp);
        return resp;
    }

    public IOrderMetaManager getOrderMetaManager() {
        return orderMetaManager;
    }

    public void setOrderMetaManager(IOrderMetaManager orderMetaManager) {
        this.orderMetaManager = orderMetaManager;
    }

	public MemberInf getMemberServ() {
		return memberServ;
	}

	public void setMemberServ(MemberInf memberServ) {
		this.memberServ = memberServ;
	}

	public MemberAddressInf getMemberAddressServ() {
		return memberAddressServ;
	}

	public void setMemberAddressServ(MemberAddressInf memberAddressServ) {
		this.memberAddressServ = memberAddressServ;
	}

	public PaymentCfgInf getPaymentCfgServ() {
		return paymentCfgServ;
	}

	public void setPaymentCfgServ(PaymentCfgInf paymentCfgServ) {
		this.paymentCfgServ = paymentCfgServ;
	}

	public OrderRuleInf getOrderRuleServ() {
		return orderRuleServ;
	}

	public void setOrderRuleServ(OrderRuleInf orderRuleServ) {
		this.orderRuleServ = orderRuleServ;
	}

	public CouponInf getCouponServ() {
		return couponServ;
	}

	public void setCouponServ(CouponInf couponServ) {
		this.couponServ = couponServ;
	}

	public PromotionInf getPromotionServ() {
		return promotionServ;
	}

	public void setPromotionServ(PromotionInf promotionServ) {
		this.promotionServ = promotionServ;
	}

	public ProductInf getProudctServ() {
		return proudctServ;
	}

	public void setProudctServ(ProductInf proudctServ) {
		this.proudctServ = proudctServ;
	}

	public IMemberAddressService getMemberAddressService() {
		return memberAddressService;
	}

	public void setMemberAddressService(IMemberAddressService memberAddressService) {
		this.memberAddressService = memberAddressService;
	}



   
}