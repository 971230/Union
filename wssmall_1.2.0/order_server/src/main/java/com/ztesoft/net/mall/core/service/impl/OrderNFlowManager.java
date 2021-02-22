package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.member.req.MemberByIdReq;
import params.member.req.MemberPointAddReq;
import params.member.req.MemberPointCheckReq;
import params.member.req.MemberPointReq;
import params.member.req.PointHistoryReq;
import params.member.resp.MemberByIdResp;
import params.member.resp.MemberPointAddResp;
import params.member.resp.MemberPointCheckResp;
import params.member.resp.MemberPointResp;
import params.member.resp.PointHistoryResp;
import services.MemberInf;
import services.MemberPointInf;
import services.PointHistoryInf;
import services.WarhouseGoodsStoreInf;
import com.alibaba.fastjson.JSON;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.context.webcontext.ThreadOrderHolder;
import com.ztesoft.net.framework.util.CurrencyUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.AdjunctItem;
import com.ztesoft.net.mall.core.model.AdvanceLogs;
import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.DeliveryItem;
import com.ztesoft.net.mall.core.model.FreeOffer;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsActionRule;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderChange;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.model.OrderLog;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.OrderRel;
import com.ztesoft.net.mall.core.model.PaymentLog;
import com.ztesoft.net.mall.core.model.PointHistory;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.model.ShipRequest;
import com.ztesoft.net.mall.core.plugin.order.OrderPluginBundle;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IOrderNFlowManager;
import com.ztesoft.net.mall.core.service.OrderUtils;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

/**
 * 订单业务流程
 *
 * @author wui
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OrderNFlowManager extends BaseSupport implements IOrderNFlowManager {

    private IOrderManager orderManager;
    private OrderUtils orderUtils;
    private OrderPluginBundle orderPluginBundle;
	private MemberPointInf memberPointServ;
    private MemberInf memberServ;
	private PointHistoryInf pointHistoryServ;

    private WarhouseGoodsStoreInf warhouseGoodsStoreServ;

    /**
     * 支付
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void pay(PaymentLog payment, boolean isOnline, Object... args) {

        if (payment == null)
            throw new IllegalArgumentException("param paymentLog is NULL");
        String order_id = payment.getOrder_id();

        if (order_id == null) throw new IllegalArgumentException("param PaymentLog's order_id is NULL");

        Double money = payment.getMoney();
        if (payment.getPaytype() == 0) {
            //单订单支付
            Order order = this.orderManager.get(order_id);
            pay(order, money, payment, isOnline, true, args);
        } else {
            List<Order> list = this.orderManager.getByBatchID(order_id);
            if (list == null || list.size() < 1)
                throw new IllegalArgumentException("param PaymentLog's order_id is NULL");

            for (int i = 0; i < list.size(); i++) {
                Order o = list.get(i);
                pay(o, money, payment, isOnline, (i == 0), args);
                if (money != null && money > o.getOrder_amount()) {
                    money -= o.getOrder_amount();
                } else if (money != null) {
                    money = 0d;
                }
            }
        }

    }

    public void pay(Order order, Double money_pm, PaymentLog payment, boolean isOnline, boolean isFirst, Object... args) {
        String order_id = order.getOrder_id();
        Double money = money_pm;
        if (money == null) {
            payment.setMoney(order.getOrder_amount());
            money = order.getOrder_amount();
        }

        if (money == null)
            throw new IllegalArgumentException(
                    "param  PaymentLog's money is NULL");

        checkDisabled(order);
        if (order.getPay_status().intValue() == OrderStatus.PAY_YES) {
            if (logger.isDebugEnabled()) {
                logger
                        .debug("订单[" + order.getSn()
                                + "]支付状态为[已经支付]，不能再对其进行支付操作");
            }
            throw new IllegalStateException("订单[" + order.getSn() + "]支付状态为[已经支付]，不能再对其进行支付操作");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("支付订单:" + order.getOrder_id());
        }


        boolean isPayed = true;
        if (args != null && args.length > 0) { //需要在线支付
            if (args[0] instanceof Boolean) {
                isPayed = (Boolean) args[0];
            }
        }

        //add by wui已支付，则更新用户积分信息
        if (!StringUtil.isEmpty(order.getMember_id()) && isPayed == true) {
            // 扣除用户预存款
            if (payment.getPay_type().trim().equals("deposit")) {
                this.saveMoney(order, payment);
            }

            // 更新用户积分
            this.savePoint(order.getGainedpoint(), order.getMember_id());

            /**
             * --------------------------- 增加会员积分--商品价格*倍数
             * ---------------------------
             */
            MemberPointCheckReq mpReq = new MemberPointCheckReq();
			mpReq.setType(MemberPointInf.TYPE_BUYGOODS);
			MemberPointCheckResp mpResp = memberPointServ.checkIsOpen(mpReq);
			
			if(mpResp.isResult()){
				MemberPointReq mpReq1 = new MemberPointReq();
				mpReq1.setType(MemberPointInf.TYPE_BUYGOODS + "_num");
				MemberPointResp resp1 = memberPointServ.getItemPointByName(mpReq1);
				if(resp1 != null){
					MemberPointAddReq mpReq2 = new MemberPointAddReq();
					mpReq2.setPoint(order.getGoods_amount().intValue() * resp1.getPoint());
					mpReq2.setMember_id(order.getMember_id());
					mpReq2.setType(MemberPointInf.TYPE_BUYGOODS);
					mpReq2.setRelated_id(order.getOrder_id());
					MemberPointAddResp resp2 = memberPointServ.addNewPointHistory(mpReq2);
				}
			}	
            /**
             * --------------------------- 增加会员积分--在线支付
             * ---------------------------
             */
			MemberPointCheckReq mpReq_1 = new MemberPointCheckReq();
			mpReq.setType(MemberPointInf.TYPE_ONLINEPAY);
			MemberPointCheckResp mpResp_1 = memberPointServ.checkIsOpen(mpReq_1);
			
			if(isOnline && mpResp_1.isResult()){
				MemberPointReq mpReq_2 = new MemberPointReq();
				mpReq_2.setType(MemberPointInf.TYPE_ONLINEPAY + "_num");
				MemberPointResp resp_2 = memberPointServ.getItemPointByName(mpReq_2);
				if(resp_2 != null){
					MemberPointAddReq mpReq_3 = new MemberPointAddReq();
					mpReq_3.setPoint(resp_2.getPoint());
					mpReq_3.setMember_id(order.getMember_id());
					mpReq_3.setType(MemberPointInf.TYPE_ONLINEPAY);
					mpReq_3.setRelated_id(order.getOrder_id());
					MemberPointAddResp resp_3 = memberPointServ.addNewPointHistory(mpReq_3);
				}
				
			}

        }

        double m = order.getOrder_amount();// 订单总额
        double nm = money;// 当前付款金额
        double om = order.getPaymoney();// 已收金额

        // double sm = nm + om;
        double sm = CurrencyUtil.add(nm, om);
        int payStatus = 0;
        if (sm < m)
            payStatus = OrderStatus.PAY_PARTIAL_PAYED;// 部分支付
        if (sm >= m)
            payStatus = OrderStatus.PAY_YES;// 已付款

        payment.setType(OrderStatus.PAY_TYPE_1);
        payment.setCreate_time(DBTUtil.current());
        payment.setMember_id(order.getMember_id());
        //Member sessionMember = UserServiceFactory.getUserService().getCurrentMember();
        //if(sessionMember!=null){
        //	payment.setUserid(sessionMember.getMember_id());
        //}else{
        AdminUser au = ManagerUtils.getAdminUser();
        if (au != null)
            payment.setUserid(ManagerUtils.getAdminUser().getUserid());
        //}
        //OrderRequst orequest = ThreadContextHolder.getOrderParams().getOrderRequst();

        if (!StringUtil.isEmpty(order.getTransaction_id()) && StringUtil.isEmpty(payment.getTransaction_id()))
            payment.setTransaction_id(order.getTransaction_id());
        payment.setStatus(2);
        if (args != null && args.length > 0) { //需要在线支付
            if (args[0] instanceof Boolean) {
                isPayed = (Boolean) args[0];
                if (isPayed == false) {
                    /**
                     * UQ_ORDER_TRANS_ID	Unique	TRANSACTION_ID	Y				N	N	2013/8/6 12:26:54
                     * 多单支付时有唯一索引问题，把数据库索引去掉
                     */
                    this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_TRAN_UPDATE"), payment.getTransaction_id(), payment.getBank_id(), order_id);
                    //如果多笔订单 记录只记录一条记录
                    if (isFirst) {
                        //this.baseDaoSupport.insert("payment_logs", payment);
                        this.baseDaoSupport.update("payment_logs", payment, "payment_id=" + payment.getPayment_id());
                    }
                    return;
                }
            } else {
                payment.setStatus_time(DBTUtil.current()); //设置状态修改时间
            }
        }

        //this.baseDaoSupport.insert("payment_logs", payment);
        this.baseDaoSupport.update("payment_logs", payment, "payment_id=" + payment.getPayment_id());

        if (logger.isDebugEnabled()) {
            logger.debug("更新订单状态[" + OrderStatus.ORDER_PAY + "],支付状态[" + payStatus + "]");
        }
        int status = OrderStatus.ORDER_PAY;
        if(!isOrderShip(order.getOrder_id())){
        	status = OrderStatus.ORDER_SHIP;
        	updateOrderShipNum(order.getOrder_id());
        }
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_PAY_UPDATE"), status, payStatus, money, order_id);
        this.log(order_id, "支付订单，金额" + money, null, ManagerUtils.getAdminUser().getUsername());
    }


    @Override
	public PaymentLog getPaymentLogByTransaction_id(String transaction_id) {
        String sql = SF.orderSql("SERVICE_PAYMENT_LOGS_BY_TRANS_ID");
        List list = this.baseDaoSupport.queryForList(sql, transaction_id);
        PaymentLog paymentLog = new PaymentLog();
        try {
            BeanUtilsBean.getInstance().copyProperties(paymentLog, list.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentLog;
    }

    //银行成功返回支付处理,更新订单状态
    @Override
	public void paySucc(String transaction_id) {
        PaymentLog paymentLog = paySuccResult(transaction_id);
        this.log(paymentLog.getOrder_id(), "支付订单，金额" + paymentLog.getMoney(), null, ManagerUtils.getAdminUser().getUsername());
    }
    
    /**
     * 是否要发货
     * @作者 MoChunrun
     * @创建日期 2014-1-15 
     * @param order_id
     * @return
     */
    private boolean isOrderShip(String order_id){
    	String sql = SF.orderSql("IS_ORDER_SHIP");
    	return this.baseDaoSupport.queryForInt(sql, order_id)>0;
    }
    
    private void updateOrderShipNum(String order_id){
    	String sql = SF.orderSql("UPDATE_NOT_SHIP_ITEMS_NUM");
    	this.baseDaoSupport.execute(sql, order_id);
    }

    @Override
	public PaymentLog paySucc(PaymentLog paymentLog) {
        if (paymentLog == null)
            throw new RuntimeException("paymentLog不通为空！");
        /*Order order = getOrderManager().get(paymentLog.getOrder_id());
        double m = order.getOrder_amount();// 订单总额
		double nm = paymentLog.getMoney();// 当前付款金额
		double om = order.getPaymoney();// 已收金额
		*/

        //===========订单ID还是batchID
        List<Order> list = null;
        Order order = null;
        double m = 0;// 订单总额
        double nm = paymentLog.getMoney();// 当前付款金额
        double om = 0;// 已收金额
        if (paymentLog.getPaytype() != null && paymentLog.getPaytype().intValue() != 0) {
            //批量提交
            list = orderManager.getByBatchID(paymentLog.getOrder_id());
            paymentLog.setOrderList(list);
            for (Order o : list) {
                m += o.getOrder_amount();
                om += o.getPaymoney();
            }
        } else {
            //单个订单提交
            order = getOrderManager().get(paymentLog.getOrder_id());
            m = order.getOrder_amount();
            om = order.getPaymoney();
        }
        //===========订单ID还是batchID===

        double sm = CurrencyUtil.add(nm, om);
        int payStatus = 0;
        if (sm < m)
            payStatus = OrderStatus.PAY_PARTIAL_PAYED;// 部分支付
        if (sm >= m)
            payStatus = OrderStatus.PAY_YES;// 已付款


        if (logger.isDebugEnabled()) {
            logger.debug("更新订单状态[" + OrderStatus.ORDER_PAY + "],支付状态["
                    + payStatus + "]");
        }

        
        //更新订单
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_PAYMENT_LOGS_UPDATE"), OrderStatus.PAY_STATUS_1, paymentLog.getTransaction_id());
        if (paymentLog.getPaytype() != null && paymentLog.getPaytype().intValue() != 0) {
            //按batchID=========
            for (Order o : list) {
            	int status = OrderStatus.ORDER_PAY;
                if(!isOrderShip(o.getOrder_id())){
                	status = OrderStatus.ORDER_SHIP;
                }
                //add by wui追加积分
                addPoints(o);

                if (nm >= (o.getOrder_amount() - o.getPaymoney())) {
                    this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_PAYMONEY_UPDATE"), status, OrderStatus.PAY_YES, o.getOrder_id());
                    nm -= (o.getOrder_amount() - o.getPaymoney());
                    if(status==OrderStatus.ORDER_SHIP){
                    	updateOrderShipNum(o.getOrder_id());
                    }
                } else {
                    this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_PAY_UPDATE_NEW"), OrderStatus.PAY_PARTIAL_PAYED, payStatus, nm,paymentLog.getTransaction_id(), o.getOrder_id());
                    break;
                }
            }
        } else {

            //追加积分
            /**
             * 此处有问题，暂时去掉 2013/10/21 mochunrun
             */
            addPoints(order);
            updateBuyNum(order);
            int status = OrderStatus.ORDER_PAY;
            if(!isOrderShip(order.getOrder_id())){
            	status = OrderStatus.ORDER_SHIP;
            	updateOrderShipNum(order.getOrder_id());
            }
            this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_PAY_UPDATE_NEW"), status, payStatus, paymentLog.getMoney(),paymentLog.getTransaction_id(), paymentLog.getOrder_id());
        }
        return paymentLog;

    }

    public PaymentLog paySuccResult(String transaction_id) {
        PaymentLog paymentLog = getPaymentLogByTransaction_id(transaction_id);

        return paySucc(paymentLog);
    }

    /**
     * 对限购活动，如果支付成功，修改已购买数量
     */
    public void updateBuyNum(Order order){
    	String order_id = order.getOrder_id();
    	Order order2 = orderManager.get(order_id);
    	String buy_goods_num = order2.getGoods_num()+"";
    	String sql = SF.orderSql("HUODONG_BUY_NUM_UPDATE").replaceAll("#buy_num", buy_goods_num);
    	this.baseDaoSupport.execute(sql, order_id);
    }

    // add by wui 添加积分信息
    private void addPoints(Order order) {

        // 更新用户积分
        this.savePoint(order.getGainedpoint(), order.getMember_id(), order.getType_code());

		/*
		 * --------------------------- 增加会员积分--商品价格*倍数
		 * ---------------------------
		 */
        MemberPointCheckReq mpReq = new MemberPointCheckReq();
		mpReq.setType(MemberPointInf.TYPE_BUYGOODS);
		MemberPointCheckResp mpResp = memberPointServ.checkIsOpen(mpReq);
		
		if("0".equals(mpResp.getError_code())){
			MemberPointReq mpReq1 = new MemberPointReq();
			mpReq1.setType(MemberPointInf.TYPE_BUYGOODS + "_num");
			MemberPointResp resp1 = memberPointServ.getItemPointByName(mpReq1);
			if(resp1 != null){
				MemberPointAddReq mpReq2 = new MemberPointAddReq();
				mpReq2.setPoint(order.getGoods_amount().intValue() * resp1.getPoint());
				mpReq2.setMember_id(order.getMember_id());
				mpReq2.setType(MemberPointInf.TYPE_BUYGOODS);
				mpReq2.setRelated_id(order.getOrder_id());
				MemberPointAddResp resp2 = memberPointServ.addNewPointHistory(mpReq2);
			}
		}

        /**
         * --------------------------- 增加会员积分--在线支付
         * ---------------------------
         */
		MemberPointCheckReq mpReq_1 = new MemberPointCheckReq();
		mpReq.setType(MemberPointInf.TYPE_ONLINEPAY);
		MemberPointCheckResp mpResp_1 = memberPointServ.checkIsOpen(mpReq_1);
		
		if(true && "0".equals(mpResp_1.getError_code())){
			MemberPointReq mpReq_2 = new MemberPointReq();
			mpReq_2.setType(MemberPointInf.TYPE_ONLINEPAY + "_num");
			MemberPointResp resp_2 = memberPointServ.getItemPointByName(mpReq_2);
			if(resp_2 != null){
				MemberPointAddReq mpReq_3 = new MemberPointAddReq();
				mpReq_3.setPoint(resp_2.getPoint());
				mpReq_3.setMember_id(order.getMember_id());
				mpReq_3.setType(MemberPointInf.TYPE_ONLINEPAY);
				mpReq_3.setRelated_id(order.getOrder_id());
				MemberPointAddResp resp_3 = memberPointServ.addNewPointHistory(mpReq_3);
			}
			
		}
    }


    /**
     * 更新会员积分
     *
     * @param order
     * @param member type_code 从order传进type_code mochunrun 20130916
     */
    @Transactional(propagation = Propagation.REQUIRED)
    private void savePoint(Integer point, String memberid, String... type_code) {
        if (point == null) return;


        String sql = SF.orderSql("SERVICE_POINT_UPDATE");
        this.baseDaoSupport.execute(sql, point, memberid);

        if (logger.isDebugEnabled()) {
            logger.debug("增加用户[" + memberid + "]积分:" + point);
        }

        Member sessionMember = UserServiceFactory.getUserService().getCurrentMember();
        PointHistory pointHistory = new PointHistory();
        OrderRequst orequest = ThreadOrderHolder.getOrderParams().getOrderRequst();
        String tmp = "";
        if (orequest == null) {
            tmp = type_code[0];
        } else {
            tmp = orequest.getService_name();
        }

        if (sessionMember != null) {
            pointHistory.setOperator(sessionMember.getUname());
            pointHistory.setMember_id(sessionMember.getMember_id());
        } else {
            AdminUser au = ManagerUtils.getAdminUser();
            if(au!=null)
            	pointHistory.setOperator(au.getUsername());
            pointHistory.setMember_id(memberid);
        }
        pointHistory.setPoint(point);
        pointHistory.setReason("order_pay_get");
        pointHistory.setTime(DBTUtil.current());

        //如果会员登录，更新session中预存款和积分的值

        if (sessionMember != null) {
            sessionMember.setPoint(sessionMember.getPoint() + point);
            pointHistory.setOperator(sessionMember.getUname());
        }

    	PointHistoryReq phReq = new PointHistoryReq();
		PointHistoryResp phResp = new PointHistoryResp();
		phReq.setPointHistory(pointHistory);
		phResp = pointHistoryServ.addPointHistory(phReq);
    }


    /**
     * 更新会员预存款
     */
    @Transactional(propagation = Propagation.REQUIRED)
    private void saveMoney(Order order, PaymentLog payment) {
        if (logger.isDebugEnabled()) {
            logger.debug("扣除用户[" + order.getMember_id() + "]预存款:" + payment.getMoney());
        }
        Member member = new Member();
        MemberByIdReq req = new MemberByIdReq();
		req.setMember_id(order.getMember_id());
		MemberByIdResp resp = memberServ.getMemberById(req);
		if("0".equals(resp.getError_code())){
			member = resp.getMember();
		}
        if (member.getAdvance().compareTo(payment.getMoney()) < 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("用户预存款[" + member.getAdvance().doubleValue() + "]不足,需要支付[" + payment.getMoney().doubleValue() + "]");
            }
            throw new RuntimeException("用户预存款[" + member.getAdvance().doubleValue() + "]不足,需要支付[" + payment.getMoney().doubleValue() + "]");
        }
        baseDaoSupport.execute(SF.orderSql("SERVICE_ADVANCE_UPDATE"), payment.getMoney(), order.getMember_id());


        //既然是用预存款支付，则应加到预存款日志中
        MemberByIdReq req1 = new MemberByIdReq();
		req1.setMember_id(order.getMember_id());
		MemberByIdResp resp1 = memberServ.getMemberById(req1);
		if("0".equals(resp1.getError_code())){
			member = resp1.getMember();
		}//再取回预存款的数值
        AdvanceLogs advanceLogs = new AdvanceLogs();
        advanceLogs.setMember_id(order.getMember_id());
        advanceLogs.setDisabled("false");
        advanceLogs.setMtime(order.getCreate_time());
        advanceLogs.setImport_money(null);
        advanceLogs.setExplode_money(payment.getMoney());
        advanceLogs.setMember_advance(member.getAdvance());
        advanceLogs.setShop_advance(member.getAdvance());// 此字段很难理解
        advanceLogs.setMoney(0 - payment.getMoney());
        advanceLogs.setMessage("预存款支付：订单号{" + payment.getOrder_id() + "}");
        advanceLogs.setOrder_id(payment.getOrder_id());
        advanceLogs.setMemo("预存款支付");
        baseDaoSupport.insert("advance_logs", advanceLogs);
        //预存款日志完成


        //更新session中预存款和积分的值
        Member sessionMember = UserServiceFactory.getUserService().getCurrentMember();
        if (sessionMember != null) {
//			sessionMember.setAdvance(sessionMember.getAdvance()-payment.getMoney());
            sessionMember.setAdvance(CurrencyUtil.sub(sessionMember.getAdvance(), payment.getMoney()));
        }
    }

    /**
     * 退款
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void refund(PaymentLog payment) {
        refundResult(payment);
        this.log(payment.getOrder_id(), "订单退款，金额" + payment.getMoney(), null, ManagerUtils.getAdminUser().getUsername());
    }

    public void refundResult(PaymentLog payment) {
        if (payment == null) throw new IllegalArgumentException("param paymentLog is NULL");
        if (payment.getOrder_id() == null) throw new IllegalArgumentException("param PaymentLog's order_id is NULL");
        if (payment.getMoney() == null) throw new IllegalArgumentException("param PaymentLog's money is NULL");
        Order order = this.orderManager.get(payment.getOrder_id());
        checkDisabled(order);
        if (order.getPay_status().intValue() == OrderStatus.PAY_CANCEL) {
            if (logger.isDebugEnabled()) {
                logger.debug("订单[" + order.getSn() + "]支付状态为[已经退款]，不能再对其进行退款操作");
            }
            throw new IllegalStateException("订单[" + order.getSn() + "]支付状态为[已经退款]，不能再对其进行退款操作");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("订单:" + order.getOrder_id() + "退款");
        }


        double m = order.getOrder_amount();// 订单总额
        double nm = payment.getMoney();// 当前付款金额
        double om = order.getPaymoney();// 已收金额


        int payStatus = 0;

        if (nm < om)
            payStatus = OrderStatus.PAY_PARTIAL_REFUND;// 部分退款

        if (nm == om || nm < 0)
            payStatus = OrderStatus.PAY_CANCEL;// 已退款

        if (nm > om) {
            if (logger.isDebugEnabled()) {
                logger.debug("退款金额[" + nm + "]超过订单支付金额[" + m + "]");
            }
            throw new RuntimeException("退款金额[" + nm + "]超过订单支付金额[" + om + "]");
        }
        payment.setPayment_id("");
        payment.setRemark("退费");

        payment.setType(2);
        payment.setStatus(1);
        payment.setCreate_time(DBTUtil.current());
        payment.setMember_id(order.getMember_id());
        payment.setUserid(ManagerUtils.getUserId());
        this.baseDaoSupport.insert("payment_logs", payment);


        if (logger.isDebugEnabled()) {
            logger.debug("更新订单状态[" + OrderStatus.ORDER_CANCEL_PAY + "],支付状态[" + payStatus + "]");
        }
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUE_UPDATE"), OrderStatus.ORDER_CANCEL_PAY, payStatus, payment.getMoney(), payment.getOrder_id());

        //返还用户预存款
        //if(payment.getPay_method().trim().equals("预存款支付")){
        //上面有逻辑错误，应该是Pay_type对应的是预存款（deposit），而不是Pay_method（它对应的是Pay_type为在线支付(online)时的支付方式）
        if (payment.getPay_type().trim().equals("deposit")) {
            if (!StringUtil.isEmpty(order.getMember_id())) {
                if (logger.isDebugEnabled()) {
                    logger.debug("返还用户[" + order.getMember_id() + "]预存款:" + payment.getMoney());
                }
                baseDaoSupport.execute(SF.orderSql("SERVICE_ADVANCE_ADD_UPDATE"), payment.getMoney(), payment.getMember_id());
                //既然是用预存款支付，则应加到预存款日志中
                Member member = null;
                MemberByIdReq req1 = new MemberByIdReq();
        		req1.setMember_id(payment.getMember_id());
        		MemberByIdResp resp1 = memberServ.getMemberById(req1);
        		if("0".equals(resp1.getError_code())){
        			member = resp1.getMember();
        		}
                
                AdvanceLogs advanceLogs = new AdvanceLogs();
                advanceLogs.setMember_id(payment.getMember_id());
                advanceLogs.setDisabled("false");
                advanceLogs.setMtime(DBTUtil.current());
                advanceLogs.setImport_money(payment.getMoney());
                advanceLogs.setExplode_money(null);
                advanceLogs.setMember_advance(member.getAdvance());
                advanceLogs.setShop_advance(member.getAdvance());// 此字段很难理解
                advanceLogs.setMoney(payment.getMoney());
                advanceLogs.setMessage("预存款退款：订单号{" + payment.getOrder_id() + "}");
                advanceLogs.setOrder_id(payment.getOrder_id());
                advanceLogs.setMemo("预存款退款");
                baseDaoSupport.insert("advance_logs", advanceLogs);
                //预存款日志完成
            }
        }
    }

    /**
     * 记录订单操作日志
     *
     * @param order_id
     * @param message
     * @param op_id
     * @param op_name
     */
    @Transactional(propagation = Propagation.REQUIRED)
    private void log(String order_id, String message, String op_id, String op_name) {
        OrderLog orderLog = new OrderLog();
        orderLog.setMessage(message);
        orderLog.setOp_id(op_id);
        orderLog.setOp_name(op_name);
        orderLog.setOp_time(DBTUtil.current());
        orderLog.setOrder_id(order_id);
        this.baseDaoSupport.insert("order_log", orderLog);
    }


    /**
     * 发货
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void shipping(Delivery delivery, List<DeliveryItem> itemList, List<DeliveryItem> giftItemList, String ship_action) {

        if (delivery == null) throw new IllegalArgumentException("param delivery is NULL");
        if (itemList == null) throw new IllegalArgumentException("param itemList is NULL");
        if (delivery.getOrder_id() == null) throw new IllegalArgumentException("param order id is null");

        if (delivery.getMoney() == null) delivery.setMoney(0D);
        if (delivery.getProtect_price() == null) delivery.setProtect_price(0D);

        if (logger.isDebugEnabled()) {
            logger.debug("订单[" + delivery.getOrder_id() + "]发货");
        }

        String orderId = delivery.getOrder_id();
        Order order = this.orderManager.get(orderId);
        checkDisabled(order);
        if (order.getShip_status().intValue() == OrderStatus.SHIP_YES) {
            if (logger.isDebugEnabled()) {
                logger.debug("订单[" + order.getSn() + "]状态已经为【已发货】，不能再对其进行发货操作");
            }
            throw new IllegalStateException("订单[" + order.getSn() + "]状态已经为【已发货】，不能再对其进行发货操作");
        }


        delivery.setType(1);
        delivery.setMember_id(order.getMember_id());
        delivery.setUserid(ManagerUtils.getUserId());
        delivery.setCreate_time(DBTUtil.current());
        baseDaoSupport.insert("delivery", delivery);
        String delivery_id = delivery.getDelivery_id();

        int shipStatus = OrderStatus.SHIP_YES;

        //处理商品发货项
        int goodsShipStatus = this.processGoodsShipItem(orderId, delivery_id, itemList, ship_action,null);
        shipStatus = goodsShipStatus;
        if (giftItemList != null && !giftItemList.isEmpty()) {
            //处理赠品发货项
            int giftShipStatus = this.processGiftShipItem(orderId, delivery_id, giftItemList);
            shipStatus = (giftShipStatus == OrderStatus.SHIP_YES && goodsShipStatus == OrderStatus.SHIP_YES) ? OrderStatus.SHIP_YES : OrderStatus.SHIP_PARTIAL_SHIPED;
        }

        //如果发货状态不为全部发货即为部分发货
        shipStatus = shipStatus == OrderStatus.SHIP_YES ? OrderStatus.SHIP_YES : OrderStatus.SHIP_PARTIAL_SHIPED;


        /**
         * -----------------
         * 激发发货事件
         * -----------------
         */
        this.orderPluginBundle.onShip(delivery, itemList);


        if (logger.isDebugEnabled()) {
            logger.debug("更新订单[" + orderId + "]状态[" + OrderStatus.ORDER_SHIP + "]，发货状态[" + shipStatus + "]");
        }
        //更新订单状态为已发货
        baseDaoSupport.execute(SF.orderSql("SERVICE_SHIP_STATUS_UPDATE"), OrderStatus.ORDER_SHIP, shipStatus, orderId);

        String ship_info = "订单发货，物流单据号";
        if (!StringUtil.isEmpty(delivery.getLogi_no()))
            ship_info = "订单发货，物流单据号[" + delivery.getLogi_no() + "]";
        this.log(delivery.getOrder_id(), ship_info, null, ManagerUtils.getAdminUser().getUsername());

    }


    public OrderItem getOrderItem(String orderId, String itemID) {
        String sql = SF.orderSql("SERVICE_ORD_ITEM_SELECT");
        List<OrderItem> list = this.daoSupport.queryForList(sql, OrderItem.class, orderId, itemID);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }


    /**
     * 处理商品退货
     *
     * @param orderId       订单id
     * @param delivery_id   发货单id
     * @param orderItemList 订单商品货物 {@link IOrderManager#listGoodsItems(Integer)}
     * @param itemList      由用户表单读取的退货数据
     * @return 商品退货状态
     */
    @Transactional(propagation = Propagation.REQUIRED)
    private int processGoodsReturnItem(String orderId, String delivery_id, List<DeliveryItem> itemList) {
        this.fillAdjItem(orderId, itemList);
        List<Map> orderItemList = this.orderManager.listGoodsItems(orderId);//此订单的相关商品货品
        int shipStatus = OrderStatus.SHIP_CANCEL;
        for (DeliveryItem item : itemList) {

            if (item.getGoods_id() == null) throw new IllegalArgumentException(item.getName() + " goods id is  NULL");
            if (item.getProduct_id() == null)
                throw new IllegalArgumentException(item.getName() + " product id is  NULL");
            if (item.getNum() == null) throw new IllegalArgumentException(item.getName() + " num id is  NULL");

            if (logger.isDebugEnabled()) {
                logger.debug("检测item[" + item.getName() + "]退货数量是否合法");
            }
            //检查退货数量是否合法，并得到这项的退货状态
            int itemStatus = this.checkGoodsReturnNum(orderItemList, item);

            //全部为退货完成则订单的发货状态为退货完成f
            shipStatus = (shipStatus == OrderStatus.SHIP_CANCEL && itemStatus == OrderStatus.SHIP_CANCEL) ? OrderStatus.SHIP_CANCEL : itemStatus;

            item.setDelivery_id(delivery_id);
            if (item.getNum() > 0) {
                //记录退货明细
                this.baseDaoSupport.insert("delivery_item", item);

                //更新退货量
                baseDaoSupport.execute(SF.orderSql("SERVICE_ITEM_SHIPNUM_MINUS_UPDATE"), item.getNum(), orderId, item.getOrder_item_id());

                //更新库存量
                baseDaoSupport.execute(SF.orderSql("SERVICE_GOODS_STORE_UPDATE"), item.getNum(), item.getGoods_id());
                baseDaoSupport.execute(SF.orderSql("SERVICE_PRODUCT_STORE_UPDATE"), item.getNum(), item.getProduct_id());

            }

        }
		/*int count = baseDaoSupport.queryForInt("select sum(i.ship_num) from order_items i where i.order_id=?", orderId);
		if(count>0){
			shipStatus = OrderStatus.SHIP_PARTIAL_CANCEL;
		}*/
        return shipStatus;
    }


    /**
     * 处理赠品退货
     *
     * @param orderId       订单id
     * @param delivery_id   发货单id
     * @param orderItemList 订单商品货物 {@link IOrderManager#listGiftItems(Integer)}
     * @param itemList      由用户表单读取的退货数据
     * @return 赠品退货状态
     */
    @Transactional(propagation = Propagation.REQUIRED)
    private int processGiftReturnItem(String orderId, String delivery_id, List<DeliveryItem> itemList) {

        List<Map> orderGiftItemList = this.orderManager.listGiftItems(orderId);//此订单相关赠品的货品
        int shipStatus = OrderStatus.SHIP_CANCEL;
        for (DeliveryItem item : itemList) {

            if (item.getGoods_id() == null) throw new IllegalArgumentException(item.getName() + " goods id is  NULL");
            if (item.getProduct_id() == null)
                throw new IllegalArgumentException(item.getName() + " product id is  NULL");
            if (item.getNum() == null) throw new IllegalArgumentException(item.getName() + " num id is  NULL");

            if (logger.isDebugEnabled()) {
                logger.debug("检测item[" + item.getName() + "]退货数量是否合法");
            }
            //检查退货数量是否合法，并得到这项的退货状态
            int itemStatus = this.checkGiftReturnNum(orderGiftItemList, item);

            //全部为退货完成则订单的发货状态为退货完成
            shipStatus = (shipStatus == OrderStatus.SHIP_CANCEL && itemStatus == OrderStatus.SHIP_CANCEL) ? OrderStatus.SHIP_CANCEL : itemStatus;

            item.setDelivery_id(delivery_id);
            if (item.getNum() > 0) {/**增加判断，不为0时做以下操作**/
                //记录退货明细
                this.baseDaoSupport.insert("delivery_item", item);

                //更新退货量
                baseDaoSupport.execute(SF.orderSql("SERVICE_RETURN_SHIPNUM_UPDATE"), item.getNum(), orderId, item.getGoods_id());

                //更新库存量
                baseDaoSupport.execute(SF.orderSql("SERVICE_REPERTORY_UPDATE"), item.getNum(), item.getGoods_id());
            }

        }
        return shipStatus;
    }

    /**
     * 将配件项加入到要发退货项中
     *
     * @param orderid
     * @param itemList
     */
    @Transactional(propagation = Propagation.REQUIRED)
    private void fillAdjItem(String orderId, List<DeliveryItem> itemList) {
        List<DeliveryItem> newItemList = new ArrayList<DeliveryItem>();
        //此订单相关的配件列表
        List<Map> adjItemList = this.orderManager.listAdjItem(orderId);
        for (DeliveryItem dlyItem : itemList) {
            for (Map adjItem : adjItemList) {
                // 此货品对应的配件
                if ((dlyItem.getProduct_id()).equals(adjItem.get("spec_id").toString()) && dlyItem.getNum() > 0) {
                    String addonstr = (String) adjItem.get("addon");
                    if (addonstr != null || !"null".equals(addonstr)) {
                        Collection<AdjunctItem> collection= JSON.parseArray(addonstr,AdjunctItem.class);
                        Iterator<AdjunctItem> iterator = collection.iterator();
                        while (iterator.hasNext()) {
                            AdjunctItem adj = iterator.next();
                            DeliveryItem deliveryItem = new DeliveryItem();
                            deliveryItem.setName(adj.getName());
                            deliveryItem.setGoods_id(adj.getGoodsid());
                            deliveryItem.setProduct_id(adj.getProductid());
                            deliveryItem.setNum(adj.getNum());
                            deliveryItem.setOrder_item_id("adjuct");
                            newItemList.add(deliveryItem);
                            if (this.logger.isDebugEnabled()) {
                                logger.debug("添加配件项" + adj.getName());
                            }
                        }
                    }
                }
            }
        }
        itemList.addAll(newItemList);
    }

    /**
     * 处理商品发货项
     *
     * @param orderId
     * @param delivery_id
     * @param itemList
     * @return
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public int processGoodsShipItem(String orderId, String delivery_id, List<DeliveryItem> itemList, String ship_action,Map params, Order... order) {


        //此订单的相关货品
        List<Product> productList = listProductbyOrderId(orderId);
        List<Map> orderItemList = this.orderManager.listGoodsItems(orderId);
        this.fillAdjItem(orderId, itemList);
        int shipStatus = OrderStatus.SHIP_YES;

        for (DeliveryItem item : itemList) {

            if (item.getGoods_id() == null) throw new IllegalArgumentException(item.getName() + " goods id is  NULL");
            if (item.getProduct_id() == null)
                throw new IllegalArgumentException(item.getName() + " product id is  NULL");
            if (item.getNum() == null) throw new IllegalArgumentException(item.getName() + " num id is  NULL");

            if (logger.isDebugEnabled()) {
                logger.debug("检测item[" + item.getName() + "]发货数量是否合法");
            }

            //检查发货数量是否合法，并得到这项的发货状态
            int itemStatus = this.checkGoodsShipNum(orderItemList, item);

            //全部为发货完成则订单的发货状态为发货完成
            shipStatus = (shipStatus == OrderStatus.SHIP_YES && itemStatus == OrderStatus.SHIP_YES) ? OrderStatus.SHIP_YES : itemStatus;

            if (logger.isDebugEnabled()) {
                logger.debug("检测item[" + item.getName() + "]库存");
            }

            if ("auto".equals(ship_action)) //自动配送,不需要库存验证
            {
                item.setDelivery_id(delivery_id);
                //记录发货明细
                this.baseDaoSupport.insert("delivery_item", item);
                //更新发货量
                baseDaoSupport.execute(SF.orderSql("SERVICE_ITEM_SHIPNUM_UPDATE"), item.getNum(), orderId, item.getOrder_item_id());
                return shipStatus;
            }

            //检查库存
            if (order == null || order.length == 0 || !OrderStatus.WP_CREATE_TYPE_5.equals(String.valueOf(order[0].getCreate_type())))
                checkGoodsStore(orderId, productList, item);

            item.setDelivery_id(delivery_id);
            //记录发货明细
            this.baseDaoSupport.insert("delivery_item", item);
            //更新发货量
            baseDaoSupport.execute(SF.orderSql("SERVICE_ITEM_SHIPNUM_UPDATE"), item.getNum(), orderId, item.getOrder_item_id());
            //if(order!=null && OrderStatus.WP_CREATE_TYPE_6.equals(String.valueOf(b)))
            String house_id ="";//add by wui TODO 仓库id需要手动传过来 OrderThreadLocalHolder.getInstance().getOrderCommonData().getStringValue("house_id");
            if(params!=null)house_id = (String) params.get("house_id");
            if (order != null && order.length > 0 && OrderStatus.WP_CREATE_TYPE_5.equals(String.valueOf(order[0].getCreate_type()))) {
                //更新库存量
                baseDaoSupport.execute(SF.orderSql("SERVICE_GOODS_STORE_UPDATE"), item.getNum(), item.getGoods_id());
                baseDaoSupport.execute(SF.orderSql("SERVICE_PRODUCT_STORE_UPDATE"), item.getNum(), item.getProduct_id());
                if (!StringUtil.isEmpty(house_id)) {
                    warhouseGoodsStoreServ.addStore(item.getGoods_id(), house_id, item.getNum());
                }
            } else {
                //更新库存量
                baseDaoSupport.execute(SF.orderSql("SERVICE_GOODS_STORE_MINUS_UPDATE"), item.getNum(), item.getGoods_id());
                baseDaoSupport.execute(SF.orderSql("SERVICE_PRODUCT_STORE_MINUS_UPDATE"), item.getNum(), item.getProduct_id());
                if (!StringUtil.isEmpty(house_id)) {
                    warhouseGoodsStoreServ.descStore(item.getGoods_id(), house_id, item.getNum());
                }
            }
            if (order == null || order.length == 0 || !(OrderStatus.WP_CREATE_TYPE_5.equals(String.valueOf(order[0].getCreate_type())) || OrderStatus.WP_CREATE_TYPE_7.equals(String.valueOf(order[0].getCreate_type())) || OrderStatus.WP_CREATE_TYPE_6.equals(String.valueOf(order[0].getCreate_type())))) {
                //更新购买数量
                baseDaoSupport.execute(SF.orderSql("SERVICE_BUY_NUM_UPDATE"), item.getNum(), item.getGoods_id());
            }
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("更新" + item.getName() + "[" + item.getProduct_id() + "," + item.getGoods_id() + "-[" + item.getNum() + "]");
            }
        }


        return shipStatus;
    }


    /**
     * 处理赠品发货项
     *
     * @param orderId
     * @param delivery_id
     * @param itemList
     * @return
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public int processGiftShipItem(String orderId, String delivery_id, List<DeliveryItem> itemList) {


        //此订单的相关赠品
        List<FreeOffer> giftList = listGiftByOrderId(orderId);
        List<Map> giftItemList = this.orderManager.listGiftItems(orderId);

        int shipStatus = OrderStatus.SHIP_YES;

        for (DeliveryItem item : itemList) {

            if (item.getGoods_id() == null) throw new IllegalArgumentException(item.getName() + " goods id is  NULL");
            if (item.getProduct_id() == null)
                throw new IllegalArgumentException(item.getName() + " product id is  NULL");
            if (item.getNum() == null) throw new IllegalArgumentException(item.getName() + " num id is  NULL");

            if (logger.isDebugEnabled()) {
                logger.debug("检测item[" + item.getName() + "]发货数量是否合法");
            }
            //检查发货数量是否合法，并得到这项的发货状态
            int itemStatus = this.checkGiftShipNum(giftItemList, item);

            //全部为发货完成则订单的发货状态为发货完成
            shipStatus = (shipStatus == OrderStatus.SHIP_YES && itemStatus == OrderStatus.SHIP_YES) ? OrderStatus.SHIP_YES : itemStatus;


            if (logger.isDebugEnabled()) {
                logger.debug("检测item[" + item.getName() + "]库存");
            }
            //检查库存
            checkGiftStore(orderId, giftList, item);

            item.setDelivery_id(delivery_id);
            //记录发货明细
            this.baseDaoSupport.insert("delivery_item", item);

            //更新发货量
            baseDaoSupport.execute(SF.orderSql("SERVICE_GIFT_SHIPNUM_UPDATE"), item.getNum(), orderId, item.getGoods_id());

            //更新库存量
            baseDaoSupport.execute(SF.orderSql("SERVICE_REPERTORY_MINUS_UPDATE"), item.getNum(), item.getGoods_id());

        }


        return shipStatus;
    }


    /*
     * 申请退货
     *
     * (non-Javadoc)
     * @see com.enation.mall.core.service.IOrderFlowManager#applyReturns(java.lang.Integer, int[])
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void applyReturns(String orderId, int state, Integer[] specids) {

        if (orderId == null) throw new IllegalArgumentException("param orderid id is null");
        if (specids == null) throw new IllegalArgumentException("param specids is NULL");

        if (logger.isDebugEnabled()) {
            if (state == 0)
                logger.debug("订单[" + orderId + "]申请退货");

            if (state == 1)
                logger.debug("订单[" + orderId + "]申请换货");

            if (state == 2)
                logger.debug("订单[" + orderId + "]申请返修");


        }
        Order order = this.orderManager.get(orderId);
        checkDisabled(order);

        int orderstate = OrderStatus.ORDER_RETURN_APPLY;
        if (state == 1 || state == 2) {
            orderstate = OrderStatus.ORDER_CHANGE_APPLY;
        }

        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ITEM_STATE_UPDATE")+" and spec_id in(" + StringUtil.arrayToString(specids, ",") + ")", orderId);
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUS_UPDATE"), orderstate, orderId);
    }


    /**
     * 拒绝退货或换货
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void refuseRorC(String orderId) {
        if (orderId == null) throw new IllegalArgumentException("param orderid id is null");

        if (logger.isDebugEnabled()) {
            logger.debug("订单[" + orderId + "]" + ManagerUtils.getAdminUser().getUsername() + "拒绝退货");
        }
        Order order = this.orderManager.get(orderId);
        checkDisabled(order);

        int state = -5; //如果是退货则状态 为退货被拒绝
        if (order.getStatus().intValue() == OrderStatus.ORDER_CHANGE_APPLY) state = -6; //如果是换货则状态 为换货被拒绝

        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ITEM_STATE2_UPDATE"), orderId);
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUS_UPDATE"), state, orderId);
    }

    /**
     * 退货
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void returned(Delivery delivery, List<DeliveryItem> itemList, List<DeliveryItem> giftItemList) {
    	ShipRequest shipRequest = ThreadOrderHolder.getOrderParams().getOrderRequst().getShipRequest();
        if (delivery == null) throw new IllegalArgumentException("param delivery is NULL");
        if (itemList == null) throw new IllegalArgumentException("param itemList is NULL");
        if (delivery.getOrder_id() == null) throw new IllegalArgumentException("param order id is null");

        if (delivery.getMoney() == null) delivery.setMoney(0D);
        if (delivery.getProtect_price() == null) delivery.setProtect_price(0D);

        if (logger.isDebugEnabled()) {
            logger.debug("订单[" + delivery.getOrder_id() + "]退货");
        }

        String orderId = delivery.getOrder_id();
        Order order = this.orderManager.get(orderId);
        //checkDisabled(order);
        if (order.getShip_status().intValue() == OrderStatus.SHIP_CANCEL) {
            if (logger.isDebugEnabled()) {
                logger.debug("订单[" + order.getSn() + "]状态已经为【已退货】，不能再对其进行退货操作");
            }
            throw new IllegalStateException("订单[" + order.getSn() + "]状态已经为【已退货】，不能再对其进行退货操作");
        }

        delivery.setType(2);
        if(shipRequest!=null && "exchange".equals(shipRequest.getAction())){
        	//换货
        	delivery.setType(3);
        }
        delivery.setMember_id(order.getMember_id());
        delivery.setCreate_time(DBTUtil.current());
        delivery.setUserid(ManagerUtils.getUserId());
        delivery.setShip_status(1);
        baseDaoSupport.insert("delivery", delivery);
        String delivery_id = delivery.getDelivery_id();//产生的货运单id


        //处理退货状态
        int shipStatus = OrderStatus.SHIP_CANCEL;
        int goodsShipStatus = this.processGoodsReturnItem(orderId, delivery_id, itemList);
        shipStatus = goodsShipStatus;
        if (giftItemList != null) {
            int giftShipStatus = this.processGiftReturnItem(orderId, delivery_id, giftItemList);
            //如果商品货品和赠品货品都退货完成则 退货状态为退货完成，否则为部分退货
            shipStatus = (giftShipStatus == OrderStatus.SHIP_CANCEL && goodsShipStatus == OrderStatus.SHIP_CANCEL) ? OrderStatus.SHIP_CANCEL : OrderStatus.SHIP_PARTIAL_CANCEL;
        }
        //如果退货完成则 退货状态为退货完成，否则为部分退货
        shipStatus = shipStatus == OrderStatus.SHIP_CANCEL ? OrderStatus.SHIP_CANCEL : OrderStatus.SHIP_PARTIAL_CANCEL;

        /**
         * -----------------
         * 激发退货事件
         * -----------------
         */
        this.orderPluginBundle.onReturned(delivery, itemList);


        if (logger.isDebugEnabled()) {
            logger.debug("更新订单[" + orderId + "]状态[" + OrderStatus.ORDER_CANCEL_SHIP + "]，发货状态[" + shipStatus + "]");
        }
        //更新订单状态为已发货
        int order_status = OrderStatus.ORDER_CANCEL_SHIP;
        if(shipRequest!=null && "exchange".equals(shipRequest.getAction())){
        	//换货
        	shipStatus = shipStatus==OrderStatus.SHIP_CANCEL?OrderStatus.SHIP_CHANED:OrderStatus.SHIP_PARTIAL_CHANGE;
        	order_status = OrderStatus.ORDER_CHANGED;
        }
        baseDaoSupport.execute(SF.orderSql("SERVICE_SHIP_STATUS_UPDATE"), order_status, shipStatus, orderId);
        this.log(delivery.getOrder_id(), "订单退货成功", null, ManagerUtils.getAdminUser().getUsername());
    }


    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void change(Delivery delivery, List<DeliveryItem> itemList, List<DeliveryItem> gifitemList) {
        if (delivery == null) throw new IllegalArgumentException("param delivery is NULL");
        if (itemList == null) throw new IllegalArgumentException("param itemList is NULL");
        if (delivery.getOrder_id() == null) throw new IllegalArgumentException("param order id is null");

        if (delivery.getMoney() == null) delivery.setMoney(0D);
        if (delivery.getProtect_price() == null) delivery.setProtect_price(0D);

        if (logger.isDebugEnabled()) {
            logger.debug("订单[" + delivery.getOrder_id() + "]换货");
        }

        String orderId = delivery.getOrder_id();
        Order order = this.orderManager.get(orderId);
        checkDisabled(order);


        delivery.setType(3);
        delivery.setMember_id(order.getMember_id());
        delivery.setCreate_time(DBTUtil.current());
        delivery.setUserid(ManagerUtils.getUserId());
        delivery.setShip_status(1);
        baseDaoSupport.insert("delivery", delivery);

        if (logger.isDebugEnabled()) {
            logger.debug("更新订单[" + orderId + "]状态[" + OrderStatus.ORDER_CHANGED + "]");
        }
        //更新订单状态为已换货
        baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_SHIP_STATUS_UPDATE"), OrderStatus.ORDER_CHANGED, OrderStatus.SHIP_CHANED, orderId);
        this.log(delivery.getOrder_id(), "订单发货，物流单据号[" + delivery.getLogi_no() + "]", null, ManagerUtils.getAdminUser().getUsername());

    }


    /**
     * 检查退货量是否合法，并且计算退货状态
     *
     * @param orderItemList 购买的订单货物信息 @see {@link IOrderManager#listGoodsItems(Integer)}
     * @param item          某一个发货项
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    private int checkGoodsReturnNum(List<Map> orderItemList, DeliveryItem item) {

        int status = OrderStatus.SHIP_YES;
        for (Map orderItem : orderItemList) {

            //if((orderItem.get("spec_id").toString()).compareTo(item.getProduct_id()) == 0){
            if ((orderItem.get("item_id").toString()).compareTo(item.getOrder_item_id()) == 0) {

                Integer shipNum = Integer.valueOf(orderItem.get("ship_num").toString()); //已经发货量
                if (shipNum < item.getNum()) { //已发货量小于本次 退货量
                    if (logger.isDebugEnabled()) {
                        logger.debug("商品[" + item.getName() + "]本次发货量[" + item.getNum() + "]超出已发货量[" + shipNum + "]");
                    }
                    throw new RuntimeException("商品[" + item.getName() + "]本次发货量[" + item.getNum() + "]超出已发货量[" + shipNum + "]");
                }
                if (shipNum.compareTo(item.getNum()) == 0) { //已发货量等于本次退货量，状态为已退货
                    status = OrderStatus.SHIP_CANCEL;
                }

                if (shipNum > item.getNum()) { //已发货量大于本次退货量,状态为部分退货
                    status = OrderStatus.SHIP_PARTIAL_CANCEL;
                }
            }
        }
        return status;
    }


    /**
     * 检测赠品退货量是否合法
     *
     * @param orderItemList 订单赠品货物列表 {@link IOrderManager#listGoodsItems(Integer)}
     * @param item          由用户表单读取到的赠品退货数据
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    private int checkGiftReturnNum(List<Map> orderItemList, DeliveryItem item) {

        int status = OrderStatus.SHIP_YES;
        for (Map orderItem : orderItemList) {

            if ((orderItem.get("gift_id").toString()).equals(item.getGoods_id())) {

                Integer shipNum = Integer.valueOf(orderItem.get("shipnum").toString()); //已经发货量
                if (shipNum < item.getNum()) { //已发货量小于本次 退货量
                    if (logger.isDebugEnabled()) {
                        logger.debug("赠品[" + item.getName() + "]本次发货量[" + item.getNum() + "]超出已发货量[" + shipNum + "]");
                    }
                    throw new RuntimeException("赠品[" + item.getName() + "]本次发货量[" + item.getNum() + "]超出已发货量[" + shipNum + "]");
                }
                if (shipNum.compareTo(item.getNum()) == 0) { //已发货量等于本次退货量，状态为已退货
                    status = OrderStatus.SHIP_CANCEL;
                }

                if (shipNum > item.getNum()) { //已发货量大于本次退货量,状态为部分退货
                    status = OrderStatus.SHIP_PARTIAL_CANCEL;
                }
            }
        }
        return status;

    }


    /**
     * 订单审核通过,提示支付处理，插入日志
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void audit_through(String orderId, String message) {
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUS_UPDATE"), OrderStatus.ORDER_NOT_PAY, orderId);
        this.log(orderId, "订单审核通过：" + message, ManagerUtils.getAdminUser().getUserid(), ManagerUtils.getAdminUser().getUsername());
    }


    /**
     * 订单审核不通过
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void audit_not_through(String orderId, String message) {
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUS_UPDATE"), OrderStatus.ORDER_COLLECT_REFUSE, orderId);
        this.log(orderId, "订单审核不通过：" + message, ManagerUtils.getAdminUser().getUserid(), ManagerUtils.getAdminUser().getUsername());
    }


    /**
     * 订单自动支付
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void pay_auto(String orderId) {
        if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");

        //缺省插入支付记录
        PaymentLog payment = new PaymentLog();
        payment.setPay_type("free");
        payment.setPay_method(OrderStatus.PAY_PAYMENT_CFG_5);
        payment.setRemark(Consts.FREE_REMARK);
        payment.setOrder_id(orderId);
        payment.setStatus(OrderStatus.PAY_STATUS_1);
        Order order = this.orderManager.get(orderId);
        payment.setMoney(order.getOrder_amount());
        this.pay(payment, false, "free");

    }

    /**
     * 订单受理成功
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void accept(String orderId) {
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_ACCEPT_UPDATE"), OrderStatus.ORDER_ACCEPT, OrderStatus.ACCEPT_STATUS_3, orderId);
        this.log(orderId, "订单受理成功：", ManagerUtils.getAdminUser().getUserid(), ManagerUtils.getAdminUser().getUsername());

    }


    /**
     * 订单受理成功直接发货
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void accept_ship_auto(String orderId) {
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_ACCEPT_SHIP_UPDATE"), OrderStatus.ORDER_CONFIRM_SHIP, OrderStatus.ACCEPT_STATUS_3, OrderStatus.SHIP_YES, orderId);


        this.log(orderId, "订单受理成功：", ManagerUtils.getAdminUser().getUserid(), ManagerUtils.getAdminUser().getUsername());

    }


    /**
     * 订单受理中
     */

    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void accept_ing(String orderId) {
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_ACCEPTING_UPDATE"), OrderStatus.ACCEPT_STATUS_5, orderId);
        this.log(orderId, "云卡调拨处理中：", ManagerUtils.getAdminUser().getUserid(), ManagerUtils.getAdminUser().getUsername());

    }

    /**
     * 订单受理失败
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void acceptFail(String orderId, String message) {
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_ACCEPT_FAIL_UPDATE"), OrderStatus.ACCEPT_STATUS_4, message, orderId);
        this.log(orderId, "订单受理失败：" + message, ManagerUtils.getAdminUser().getUserid(), ManagerUtils.getAdminUser().getUsername());
    }


    /**
     * 订单受理审核通过
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void accept_through(String orderId) {
        if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_ACCEPT_THROUGH_UPDATE"), OrderStatus.ORDER_ACCEPT, OrderStatus.ACCEPT_STATUS_2, orderId);
        this.log(orderId, "订单受理审核通过：", ManagerUtils.getAdminUser().getUserid(), ManagerUtils.getAdminUser().getUsername());
    }


    /**
     * 订单受理审核不通过
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void accept_not_through(String orderId) {
        if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_ACCEPT_NOT_THROUGH"), OrderStatus.ORDER_ACCEPT, OrderStatus.ACCEPT_STATUS_1, orderId);
        this.log(orderId, "订单受理审核不通过：", ManagerUtils.getAdminUser().getUserid(), ManagerUtils.getAdminUser().getUsername());
    }


    /**
     * 完成
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void complete(String orderId) {
        if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUS_UPDATE"), OrderStatus.ORDER_COMPLETE, orderId);
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_COMPLETE_UPDATE"), orderId);
        this.log(orderId, "订单完结：", ManagerUtils.getAdminUser().getUserid(), ManagerUtils.getAdminUser().getUsername());
    }


    /**
     * 自动发货
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void ship_auto(String orderId) {
        if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");
        Order order = orderManager.get(orderId);

        List<DeliveryItem> delItemList = new ArrayList<DeliveryItem>();

        List itemList = this.listNotShipGoodsItem(orderId);
        if (!ListUtil.isEmpty(itemList)) {
            for (int i = 0; i < itemList.size(); i++) {
                OrderItem orderItem = (OrderItem) itemList.get(i);
                DeliveryItem item = new DeliveryItem();
                Product product = orderUtils.getProductByGoodsId(orderItem.getGoods_id());
                Goods goods = orderUtils.getGoodsId(orderItem.getGoods_id());
                item.setGoods_id(orderItem.getGoods_id());
                item.setName(goods.getName());
                item.setNum(orderItem.getNum());
                item.setProduct_id(product.getProduct_id());
                item.setSn(product.getSn());
                item.setItemtype(0);
                delItemList.add(item);
            }
        }
        Delivery delivery = new Delivery();
        delivery.setOrder_id(orderId);
        delivery.setLogi_id(Consts.DEF_LOGI_ID);
        delivery.setLogi_name(Consts.DEF_LOGI_NAME);

        delivery.setShip_type(order.getShipping_type());
        delivery.setShip_addr(order.getShip_addr());
        delivery.setShip_email(order.getShip_email());
        delivery.setShip_name(order.getShip_name());
        delivery.setShip_tel(order.getShip_tel());
        delivery.setShip_zip(order.getShip_tel());
        delivery.setIs_protect(order.getIs_protect());
        delivery.setProtect_price(order.getProtect_price());
        delivery.setRemark("环节自动跳转到发货环节");

        List<DeliveryItem> giftItemList = new ArrayList<DeliveryItem>();

        this.shipping(delivery, delItemList, giftItemList, "auto"); //默认生成支付
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_SHIP_AUTO_UPDATE"), OrderStatus.ORDER_SHIP, orderId);
        //this.log(orderId, "订单发货：",  ManagerUtils.getAdminUser().getUserid(),ManagerUtils.getAdminUser().getUsername());
    }

    /**
     * 确认收货
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void confirm_ship(String orderId) {
        if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUS_UPDATE"), OrderStatus.ORDER_CONFIRM_SHIP, orderId);
        this.log(orderId, "订单确认收货：", ManagerUtils.getAdminUser().getUserid(), ManagerUtils.getAdminUser().getUsername());
    }

    /**
     * 作废
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void cancel(String orderId) {
        if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUS_UPDATE"), OrderStatus.ORDER_CANCELLATION, orderId);
        this.log(orderId, "订单作废：", ManagerUtils.getAdminUser().getUserid(), ManagerUtils.getAdminUser().getUsername());
    }


    /**
     * 取消
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void cancel_order(String orderId) {
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUS_UPDATE"), OrderStatus.ORDER_CONFIRM_CANCEL, orderId);
        this.log(orderId, "订单取消：", ManagerUtils.getAdminUser().getUserid(), ManagerUtils.getAdminUser().getUsername());
    }

    /**
     * 撤单
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void withdraw(String orderId) {
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUS_UPDATE"), OrderStatus.ORDER_ACCEPT_WITHDRAW, orderId);
        this.log(orderId, "订单撤单：", ManagerUtils.getAdminUser().getUserid(), ManagerUtils.getAdminUser().getUsername());
    }


    @Override
	public List<OrderItem> listNotShipGoodsItem(String orderId) {
        String sql = SF.orderSql("SERVICE_ORDER_NOT_SHIP_SELECT");
        return this.daoSupport.queryForList(sql, OrderItem.class, orderId);

    }

    //获取未受理的订单
    @Override
	public List<OrderItem> listNotAcceptGoodsItem(String orderId) {
        String sql = SF.orderSql("SERVICE_ORD_NOT_ACCEPT_SELECT");
        return this.daoSupport.queryForList(sql, OrderItem.class, orderId);
    }


    @Override
	public List<Map> listNotShipGiftItem(String orderId) {
        String sql = SF.orderSql("SERVICE_GIFT_NOT_SHIP_SELECT");
        return this.baseDaoSupport.queryForList(sql, orderId);
    }


    @Override
	public List<OrderItem> listShipGoodsItem(String orderId) {
        String sql = SF.orderSql("SERVICE_GOODS_SHIP_SELECT")+" and oi.source_from=?";
        return this.daoSupport.queryForList(sql, OrderItem.class, orderId,ManagerUtils.getSourceFrom());
    }


    @Override
	public List<Map> listShipGiftItem(String orderId) {
        String sql = SF.orderSql("SERVICE_GIFTITEM_SHIP_SELECT");
        return this.baseDaoSupport.queryForList(sql, orderId);
    }


    /**
     * 检查发货量是否合法，并且计发货状态
     *
     * @param orderItemList 购买的订单货物信息
     * @param item          某一个发货项
     * @return
     */
    private int checkGoodsShipNum(List<Map> orderItemList, DeliveryItem item) {

        int status = OrderStatus.SHIP_NO;

        for (Map orderItem : orderItemList) {

            if ((orderItem.get("item_id").toString()).compareTo(item.getOrder_item_id()) == 0) {

                Integer num = Integer.valueOf(orderItem.get("num").toString()); //总购买量
                Integer shipNum = Integer.valueOf(orderItem.get("ship_num").toString()); //已经发货量
                if (num < item.getNum() + shipNum) { //总购买量小于总发货量
                    if (logger.isDebugEnabled()) {
                        logger.debug("商品[" + item.getName() + "]本次发货量[" + item.getNum() + "]后超出用户购买量[" + num + "],此商品已经发货[" + shipNum + "]");
                    }
                    throw new RuntimeException("商品[" + item.getName() + "]本次发货量[" + item.getNum() + "]后超出用户购买量[" + num + "],此商品已经发货[" + shipNum + "]");
                }
                if (num.compareTo(item.getNum() + shipNum) == 0) { //总购买量等于总发货量
                    status = OrderStatus.SHIP_YES;
                }

                if (num > item.getNum() + shipNum) { //总购买量大于总发货量
                    status = OrderStatus.SHIP_PARTIAL_SHIPED;
                }
            }else if("adjuct".equals(item.getOrder_item_id())){
            	status = OrderStatus.SHIP_YES;
            }
        }
        return status;

    }


    /**
     * 检查赠品发货量是否合法，并且计算发货状态
     *
     * @param orderItemList 购买的订单赠品货物信息，对应order_gift表
     * @param item          某一个发货项
     * @return
     */
    private int checkGiftShipNum(List<Map> orderItemList, DeliveryItem item) {

        int status = OrderStatus.SHIP_NO;
        for (Map orderItem : orderItemList) {

            if ((orderItem.get("gift_id").toString()).equals(item.getGoods_id())) {

                Integer num = Integer.valueOf(orderItem.get("num").toString()); //总购买量
                Integer shipNum = Integer.valueOf(orderItem.get("shipnum").toString()); //已经发货量
                if (num < item.getNum() + shipNum) { //总购买量小于总发货量
                    if (logger.isDebugEnabled()) {
                        logger.debug("赠品[" + item.getName() + "]本次发货量[" + item.getNum() + "]后超出用户购买量[" + num + "],此商品已经发货[" + shipNum + "]");
                    }
                    throw new RuntimeException("赠品[" + item.getName() + "]本次发货量[" + item.getNum() + "]后超出用户购买量[" + num + "],此商品已经发货[" + shipNum + "]");
                }
                if (num.compareTo(item.getNum() + shipNum) == 0) { //总购买量等于总购买量
                    status = OrderStatus.SHIP_YES;
                }

                if (num > item.getNum() + shipNum) { //总购买量大于总购买量
                    status = OrderStatus.SHIP_PARTIAL_SHIPED;
                }
            }
        }
        return status;

    }


    /**
     * 检查要发货商品的库存
     */
    private void checkGoodsStore(String orderId, List<Product> productList, DeliveryItem item) {

        for (Product product : productList) {
            if (product.getProduct_id().compareTo(item.getProduct_id()) == 0) {
                if (product.getStore().compareTo(item.getNum()) < 0) { //库存小于发货量
                    if (logger.isDebugEnabled()) {
                        logger.debug("商品[" + item.getName() + "]库存[" + product.getStore() + "]不足->发货量[" + item.getNum() + "]");
                    }

                    throw new RuntimeException("商品[" + item.getName() + "]库存[" + product.getStore() + "]不足->发货量[" + item.getNum() + "]");
                } else {
                    product.setStore(product.getStore() - item.getNum());
                }
            }
        }
    }

    /**
     * 检查赠品库存
     *
     * @param orderId
     * @param giftList
     * @param item
     */
    private void checkGiftStore(String orderId, List<FreeOffer> giftList, DeliveryItem item) {

        for (FreeOffer freeoffer : giftList) {
            if (freeoffer.getFo_id().equals(item.getGoods_id())) {
                if (freeoffer.getRepertory().compareTo(item.getNum()) < 0) { //库存小于发货量
                    if (logger.isDebugEnabled()) {
                        logger.debug("赠品[" + item.getName() + "]库存[" + freeoffer.getRepertory() + "]不足->发货量[" + item.getNum() + "]");
                    }

                    throw new RuntimeException("赠品[" + item.getName() + "]库存[" + freeoffer.getRepertory() + "]不足->发货量[" + item.getNum() + "]");
                }
            }
        }
    }


    /**
     * 读取某个订单的货品
     *
     * @param orderId
     * @return
     */
    private List<Product> listProductbyOrderId(String orderId) {
        String sql = SF.orderSql("SERVICE_PRODUCT_SELECT");
        List<Product> productList = this.daoSupport.queryForList(sql, Product.class, orderId);
        return productList;
    }

    /**
     * 读取某个订单赠品列表
     *
     * @param orderId
     * @return
     */

    private List<FreeOffer> listGiftByOrderId(String orderId) {
        String sql = SF.orderSql("SERVICE_GIFT_SELECT_BY_ORDER");
        List<FreeOffer> productList = this.daoSupport.queryForList(sql, FreeOffer.class, orderId);

        return productList;
    }


    /**
     * 检查订单状态是否在可操作状态
     *
     * @param order
     * @throws IllegalStateException 如果订单是完成或作废状态
     */
    private void checkDisabled(Order order) {
        if (order.getStatus().intValue() == OrderStatus.ORDER_COMPLETE || order.getStatus().intValue() == OrderStatus.ORDER_CANCELLATION)
            throw new IllegalStateException("订单已经完成或作废，不能完成操作");
    }

    public IOrderManager getOrderManager() {
        return orderManager;
    }

    public void setOrderManager(IOrderManager orderManager) {
        this.orderManager = orderManager;
    }


    public OrderPluginBundle getOrderPluginBundle() {
        return orderPluginBundle;
    }

    public void setOrderPluginBundle(OrderPluginBundle orderPluginBundle) {
        this.orderPluginBundle = orderPluginBundle;
    }


    public OrderUtils getOrderUtils() {
        return orderUtils;
    }


    public void setOrderUtils(OrderUtils orderUtils) {
        this.orderUtils = orderUtils;
    }


    @Override
	public OrderChange getChangeByFieldName(String order_id,
                                            String order_item_id, String table_name, String field_name) {
        // TODO Auto-generated method stub
        return null;
    }


    /**
     * 保存变动信息
     * 场景：换终端
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void saveChange(OrderChange orderChange) {
        this.baseDaoSupport.insert("order_change", orderChange);
    }

    @Override
	@SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOrderItem(OrderItem orderItem) {
        this.baseDaoSupport.update("order_items", orderItem, " item_id ='" + orderItem.getItem_id() + "'");
    }


    @Override
	@SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOrderItemByOrderId(OrderItem orderItem) {
        this.baseDaoSupport.update("order_items", orderItem, " order_id ='" + orderItem.getOrder_id() + "'");
    }


    //非事物更新订单
    @Override
	@SuppressWarnings("unchecked")
    public void updateOrderFNR(Order order) {
        this.baseDaoSupport.update("order", order, " order_id ='" + order.getOrder_id() + "'");
    }


    //获取关联订单
    @Override
	public String getZOrderId(String order_id, String rel_type) {
        List zorderids = this.daoSupport.queryForList(SF.orderSql("SERVICE_ORD_REL_SELECT"), order_id, rel_type, OrderStatus.ORDER_REL_STATE_0);
        if (!ListUtil.isEmpty(zorderids))
            return ((Map) zorderids.get(0)).get("z_order_id").toString();
        return "";
    }


    //获取订单id
    @Override
	public String getOrderOuterOrderIdByOldOrderId(String order_id) {
        List orderids = this.daoSupport.queryForList(SF.orderSql("SERVICE_ORD_ID_SELECT"), order_id);
        if (!ListUtil.isEmpty(orderids))
            return ((Map) orderids.get(0)).get("order_id").toString();
        return "";
    }

    //获取关联订单
    @Override
	public String getAOrderId(String order_id, String rel_type) {
        List aorderids = this.daoSupport.queryForList(SF.orderSql("SERVICE_A_ORDER_ID_SELECT"), order_id, rel_type);
        if (!ListUtil.isEmpty(aorderids))
            return ((Map) aorderids.get(0)).get("a_order_id").toString();
        return "";
    }

    //更新外系统订单
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void updateOrderOuter(OrderOuter orderOuter) {
        this.baseDaoSupport.update("order_outer", orderOuter, " order_id ='" + orderOuter.getOrder_id() + "'");
    }


    //更新订单信息
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void updateOrder(Order order) {
        this.baseDaoSupport.update("order", order, " order_id ='" + order.getOrder_id() + "'");
    }


    //插入订单关系【退费如】
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void saveOrderRel(OrderRel orderRel) {
        this.baseDaoSupport.insert("order_rel", orderRel);
    }


    @Override
	public OrderItem getOrderItemByOrderId(String orderId) {
        return (OrderItem) this.baseDaoSupport.queryForObject(SF.orderSql("SERVICE_ITEM_SELECT"), OrderItem.class, orderId);
    }

    @Override
    public void updateOrderRel(OrderRel orderRel) {
        this.baseDaoSupport.update("order_rel", orderRel, " z_order_id ='" + orderRel.getZ_order_id() + "' and rel_type = " + orderRel.getRel_type());
    }

    @Override
    public PaymentLog queryPaymentLogByOrderId(String order_id, String pay_type) {
        PaymentLog payment = (PaymentLog) this.baseDaoSupport.queryForObject(SF.orderSql("SERVICE_PAYMENT_LOGS_SELECT")+" and l.type = ? and l.status ='1'", PaymentLog.class, order_id, pay_type);
        return payment;
    }


    /**
     * 按订单ID查询物流
     */
    @Override
    public Delivery qryDeliverByOrderID(String orderID) {
        String sql = SF.orderSql("SERVICE_DELIVERY_SELECT_BY_ORDER");
        List list = this.baseDaoSupport.queryForList(sql, orderID);
        Delivery delivery = new Delivery();
        try {
            if (list != null && list.size() > 0) {
                BeanUtilsBean.getInstance().copyProperties(delivery, list.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return delivery;
    }

    @Override
    public void paySucc(String transaction_id, String type_code) {
        PaymentLog paymentLog = paySuccResult(transaction_id);
        String userName = "";
		/*if(ManagerUtils.getMemberSession()!=null){
			userName = ManagerUtils.getMemberSession().getUname();
		}else if(ManagerUtils.getAdminUser()!=null){
			userName = ManagerUtils.getAdminUser().getUsername();
		}*/
        if (0 == paymentLog.getPaytype().intValue()) {
            this.log(paymentLog.getOrder_id(), "订单支付，金额" + paymentLog.getMoney(), null, userName);
        } else {
            List<Order> list = paymentLog.getOrderList();
            double pm = paymentLog.getMoney();
            if (list != null) {
                for (Order o : list) {
                    if (pm >= o.getOrder_amount()) {
                        this.log(o.getOrder_id(), "订单支付，金额" + o.getOrder_amount(), null, userName);
                        pm -= o.getOrder_amount();
                    } else {
                        this.log(o.getOrder_id(), "订单支付，金额" + pm, null, userName);
                        pm = 0;
                    }

                }
            }
        }
    }

    @Override
    public void refund(PaymentLog payment, String type_code) {
        refundResult(payment);
        if (OrderBuilder.COMMONAGE.equals(type_code)) {
            this.log(payment.getOrder_id(), "订单退款，金额" + payment.getMoney(), null, ManagerUtils.getMemberSession().getUname());
        } else {
            this.log(payment.getOrder_id(), "订单退款，金额" + payment.getMoney(), null, ManagerUtils.getAdminUser().getUsername());
        }
    }

    @Override
    public void payExcetion(String transaction_id, Integer payAmount,
                            String type_code) {
        PaymentLog paymentLog = getPaymentLogByTransaction_id(transaction_id);
        if (paymentLog == null)
            throw new RuntimeException("流水号:" + transaction_id + "无支付记录，请检查！");
        //===========订单ID还是batchID
        List<Order> list = null;
        //Order order = null;
        if (paymentLog.getPaytype() != null && paymentLog.getPaytype().intValue() != 0) {
            //批量提交
            list = orderManager.getByBatchID(paymentLog.getOrder_id());
        }/*else{
			//单个订单提交
			order = getOrderManager().get(paymentLog.getOrder_id());
		}*/

        //更新订单
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_PAYMENT_LOGS_UPDATE"), OrderStatus.PAY_STATUS_1, transaction_id);

        String userName = "";
        if (0 == paymentLog.getPaytype().intValue()) {
            this.log(paymentLog.getOrder_id(), "订单支付，金额" + (payAmount / 100d), null, userName);
        } else {
            double pm = payAmount / 100d;
            if (list != null) {
                for (Order o : list) {
                    if (pm >= o.getOrder_amount()) {
                        this.log(o.getOrder_id(), "订单支付，金额" + o.getOrder_amount(), null, userName);
                        pm -= o.getOrder_amount();
                    } else {
                        this.log(o.getOrder_id(), "订单支付，金额" + pm, null, userName);
                        pm = 0;
                    }

                }
            }
        }
    }

    @Override
    public List<OrderItem> listOrderItemActionCode(String order_id, String service_code) {
        String sql = SF.orderSql("SERVICE_ITEM_ACTION_CODE_SELECT");
        return this.baseDaoSupport.queryForList(sql, OrderItem.class, service_code, order_id);
    }

    @Override
    public List<GoodsActionRule> listGoodsActionRule(String order_id) {
        String sql = SF.orderSql("SERVICE_ITEM_ACTION_RULE_SELECT");
        return this.baseDaoSupport.queryForList(sql, GoodsActionRule.class, order_id);
    }

    @Override
	public List<GoodsActionRule> listGoodsActionRule(String order_id,String service_code){
    	String sql = SF.orderSql("OrderActionRule");
        return this.baseDaoSupport.queryForList(sql, GoodsActionRule.class,service_code,order_id,ManagerUtils.getSourceFrom());
    }
    
	public MemberPointInf getMemberPointServ() {
		return memberPointServ;
	}

	public void setMemberPointServ(MemberPointInf memberPointServ) {
		this.memberPointServ = memberPointServ;
	}

	public MemberInf getMemberServ() {
		return memberServ;
	}

	public void setMemberServ(MemberInf memberServ) {
		this.memberServ = memberServ;
	}

	public PointHistoryInf getPointHistoryServ() {
		return pointHistoryServ;
	}

	public void setPointHistoryServ(PointHistoryInf pointHistoryServ) {
		this.pointHistoryServ = pointHistoryServ;
	}

	public WarhouseGoodsStoreInf getWarhouseGoodsStoreServ() {
		return warhouseGoodsStoreServ;
	}

	public void setWarhouseGoodsStoreServ(
			WarhouseGoodsStoreInf warhouseGoodsStoreServ) {
		this.warhouseGoodsStoreServ = warhouseGoodsStoreServ;
	}

	@Override
	public List<GoodsActionRule> listGoodsActionRuleByGoodsId(String goods_id,String service_code) {
		String sql = SF.orderSql("QUERY_GOODS_ACTION_RULE_GOODS_ID");
		return this.baseDaoSupport.queryForList(sql, GoodsActionRule.class, goods_id,ManagerUtils.getSourceFrom(),service_code);
	}

	@Override
	public List<GoodsActionRule> listGoodsActionRuleByGoodsIds(String goods_ids,String service_codde){
		//:inids 
		String sql = SF.orderSql("QUERY_GOODS_ACTION_RULE_GOODS_IDS").replace(":inids", goods_ids);
		return this.baseDaoSupport.queryForList(sql, GoodsActionRule.class,ManagerUtils.getSourceFrom(),service_codde);
	}

}
