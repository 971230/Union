package com.ztesoft.net.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import params.ZteError;
import params.adminuser.req.AdminUserReq;
import params.adminuser.resp.AdminUserResp;
import params.coqueue.req.CoQueueAddReq;
import params.coqueue.resp.CoQueueAddResp;
import params.member.req.MemberByIdReq;
import params.member.resp.MemberByIdResp;
import params.order.req.ActionRuleReq;
import params.order.req.OrderSyReq;
import params.order.resp.OrderOuterResp;
import params.order.resp.OrderSyResp;
import rule.params.userstaff.req.OrderOwnerUserReq;
import rule.params.userstaff.resp.OrderOwnerUserResp;
import services.AdminUserInf;
import services.CouponInf;
import services.MemberInf;
import services.OrderRuleInf;
import services.ProductInf;
import services.ServiceBase;
import utils.CoreThreadLocalHolder;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.iservice.IRegionService;
import zte.params.addr.req.MemberAddressAddReq;
import zte.params.order.req.OrderAddReq;
import zte.params.order.resp.OrderAddResp;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.app.base.core.model.MemberAddress;
import com.ztesoft.net.app.base.core.service.ISettingService;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Coupons;
import com.ztesoft.net.mall.core.model.GoodsUsers;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.WarehousePurorder;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.orderstd.rule.StdRuleInvoker;
import com.ztesoft.orderstd.service.IOrderSManager;
import com.ztesoft.util.OrderThreadLocalHolder;

import commons.CommonTools;
import consts.ConstsCore;

public class StdOrderServ extends ServiceBase {
	private AdminUserInf adminUserServ;
	private ProductInf proudctServ;
	private MemberInf memberServ;
    private CouponInf couponServ;
	private IOrderSManager orderSManager;
    private OrderRuleInf orderRuleServ;
	private IRegionService regionService;
	private StdOrderActionRuleServ stdOrderActionRuleServ;

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
            OrderOwnerUserResp orderOwnerUserResp = StdRuleInvoker.setOrderOwnerUser(orderOwnerUserReq);
            if (orderOwnerUserResp != null) {
                userid = orderOwnerUserResp.getUserid();
                req.getParams().put("userid", userid);
            }
        }
        if (!StringUtil.isEmpty(userid)) {
            AdminUser adminUser = getAdminUserById(userid);
            OrderThreadLocalHolder.getInstance().getOrderCommonData().setAdminUser(adminUser);
        }
    }
	
	private AdminUser getAdminUserById(String userid){
		AdminUserReq adminUserReq = new AdminUserReq();
        adminUserReq.setUser_id(userid);
        AdminUserResp adminUserResp = adminUserServ.getAdminUserById(adminUserReq);
	    AdminUser adminUser = new AdminUser();
		if(adminUserResp != null){
			adminUser = adminUserResp.getAdminUser();
		}
		return adminUser;
	}

    /**
     * 下单
     *
     * @param json
     * @作者 MoChunrun
     * @创建日期 2013-9-24
     */
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
            	coupon = useCoupon(cp.getCoupon_code(), member.getMember_id());
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
          	
            
            List<Map<String, String>> staffNos = this.orderSManager.qryStaffNoBySessionID(cp.getUserSessionId());
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
                List<Map<String,String>> goods = this.orderSManager.qryGoodsByStaffNo(stno.get("staff_no"));
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
                     	/*String cityCode = (String) map.get("order_city_code");
                     	if(!StringUtil.isEmpty(cityCode)){
                     		RegionsGetReq rqp = new RegionsGetReq();
                     		rqp.setRegion_id(cityCode);
                     		RegionsGetResp resp = regionService.getRegion(rqp);
                     		if(resp!=null && resp.getRegions()!=null){
                     			prex += resp.getRegions().getRegion_code();
                     		}
                     	}*/
                     	
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
         				//2017年10月18日AOP后激活需求订单号统一加D   
//         				CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
//         				List orderFromList = cacheUtil.doDcPublicQuery(EcsOrderConsts.ORDER_FROM_STYPE);
//         				//然后根据商城来源和region_code 获取 静态数据 DIC_ORDER_ORIGIN 的pkey 然后+order_id
//             		    if(orderFromList!=null&&orderFromList.size()>0){
//             		    	for(int i=0;i<orderFromList.size();i++){
//             		    		Map  orderFromMap = new HashMap();
//             		    		orderFromMap = (Map)orderFromList.get(i);
//             		    		if(orderFromMap!=null){
//             		    			String codea = orderFromMap.get("codea").toString();
//         		    				String pkey =  orderFromMap.get("pkey").toString();
//         		    				if(order_from.equals(codea) && !StringUtil.isEmpty(pkey)){
//         		    					prex += pkey;
//         		    				}
//             		    		}
//             		    	}
//             		    }
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
            	
            	boolean createrOrder = orderSManager.isCreateOrder(cp.getService_code(), goodsids);
            	order.setCreaterOrder(createrOrder+"");
            	this.orderSManager.add(cp,member,order, cp.getUserSessionId(), stno.get("staff_no"));
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
                        addWarehousePurOrder(wp);
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
  //插入订单
  	public List<Order> syOuterOrder(OrderAddReq req, OrderAddResp orderAddResp,List<OrderOuterResp> orderOuterResps) throws Exception {
  		
  			//订单同步处理
  			long start = System.currentTimeMillis();
  			//logger.info(" 标准化信息入库写入es_order表========================================>");
  			OrderSyReq syReq = new OrderSyReq();
  			syReq.setOrderOuterResps(orderOuterResps);
  			syReq.setParamsl(req.getParamsl());
  			long start0 = System.currentTimeMillis();
  			OrderSyResp syResp= orderSManager.perform(syReq);
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
  			    stdOrderActionRuleServ.confirmOrder(orderAddResp,arreq);//订单扩展信息，商品扩展信息，货品信息，货品扩展信息，支付信息，物流信息等入库
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
  				    String sys_code = orderSManager.getOrderSysCode(order.getOrder_id());
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
  			clean(CommonTools.getUserSessionId(), false);
  			return syResp.getOrders();
  	
  	}
  	
  	public Coupons useCoupon(String code, String memberId) {
        String str_mc_use_times = settingService.getSetting("coupons", "coupon.mc.use_times");
        int mc_use_times = str_mc_use_times == null ? 1 : Integer.valueOf(str_mc_use_times);
        String sql = SF.goodsSql("USE_COUPON");
        List param=new ArrayList();
        param.add(code);
        param.add(memberId);
        param.add(mc_use_times);
        //param.add(DBTUtil.current());
        Coupons coupons = (Coupons) this.baseDaoSupport.queryForObject(sql, Coupons.class, param.toArray());
        if (coupons != null) {
            coupons.setMemc_code(code);
            ThreadContextHolder.getSessionContext().setAttribute("coupon", coupons);
        }
        return coupons;
    }
  	
  	public void addWarehousePurOrder(WarehousePurorder warehousePurorder) {
		this.baseDaoSupport.insert("ES_WAREHOUSE_PURORDER", warehousePurorder);
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
  	
  	private void dealUsers(OrderOuter orderOuter, Order order, String goods_id) throws Exception {
        GoodsUsers goodsUsers = orderRuleServ.getGoodsUsersByGoodsId(goods_id, order.getSource_from(), order.getService_code());
      // cp.setGoodsUsers(goodsUsers);

        //根据商品设置用户受理工号、发货工号、支付工号等基本信息,此处需要转换处理
        if(goodsUsers!=null){
        	BeanUtils.copyProperties(order, orderRuleServ.dealTransferGoodsUsers(goodsUsers));
        }
    }
  	public void clean(String sessionid, boolean countChecked) {
		String sql = SF.orderSql("SERVICE_CART_DELETE");
		if(countChecked)
			sql += " and is_checked=1 or is_checked is null ";
		this.baseDaoSupport.execute(sql, sessionid);
	}
  	private ISettingService settingService;

    public ISettingService getSettingService() {
        return settingService;
    }

    public void setSettingService(ISettingService settingService) {
        this.settingService = settingService;
    }
  	public ProductInf getProudctServ() {
		return proudctServ;
	}

	public void setProudctServ(ProductInf proudctServ) {
		this.proudctServ = proudctServ;
	}
	
	public AdminUserInf getAdminUserServ(){
		return adminUserServ;
	}
	public void setAdminUserServ(AdminUserInf adminUserServ){
		this.adminUserServ = adminUserServ;
	}
	
	public StdOrderActionRuleServ getStdOrderActionRuleServ(){
		return stdOrderActionRuleServ;
	}
	public void setStdOrderActionRuleServ(StdOrderActionRuleServ stdOrderActionRuleServ){
		this.stdOrderActionRuleServ = stdOrderActionRuleServ;
	}
//	public OrderSyInf getOrderSyServ(){
//		return orderSyServ;
//	}
//	public void setOrderSyServ(OrderSyInf orderSyServ){
//		this.orderSyServ = orderSyServ;
//	}
	public MemberInf getMemberServ(){
		return memberServ;
	}
	public void setMemberServ(MemberInf memberServ){
		this.memberServ = memberServ;
	}
	public CouponInf getCouponServ(){
		return couponServ;
	}
	public void setCouponServ(CouponInf couponServ){
		this.couponServ = couponServ;
	}
	public IOrderSManager getOrderSManager(){
		return orderSManager;
	}
	public void setOrderSManager(IOrderSManager orderSManager){
		this.orderSManager = orderSManager;
	}
	public OrderRuleInf getOrderRuleServ(){
		return orderRuleServ;
	}
	public void setOrderRuleServ(OrderRuleInf orderRuleServ){
		this.orderRuleServ = orderRuleServ;
	}
	public IRegionService getRegionService(){
		return regionService;
	}
	public void setRegionService(IRegionService regionService){
		this.regionService = regionService;
	}
}
