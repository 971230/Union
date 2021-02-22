package com.ztesoft.net.mall.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.util.CurrencyUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.*;
import com.ztesoft.net.mall.core.plugin.order.OrderPluginBundle;
import com.ztesoft.net.mall.core.service.IOrderFlowManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import params.member.req.*;
import params.member.resp.*;
import services.MemberInf;
import services.MemberPointInf;
import services.PointHistoryInf;

import java.util.*;

/**
 * 订单业务流程
 *
 * @author kingapex
 *         2010-4-8上午10:19:42
 */
public class OrderFlowManager extends BaseSupport implements IOrderFlowManager {

    private IOrderManager orderManager;
    private OrderPluginBundle orderPluginBundle;
    private MemberPointInf memberPointServ;
    private MemberInf memberServ;
    private PointHistoryInf pointHistoryServ;

    /**
     * 支付
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void pay(PaymentLog payment, boolean isOnline) {

        if (payment == null)
            throw new IllegalArgumentException("param paymentLog is NULL");
        String order_id = payment.getOrder_id();
        if (order_id == null)
            throw new IllegalArgumentException(
                    "param PaymentLog's order_id is NULL");
        Double money = payment.getMoney();
        if (money == null)
            throw new IllegalArgumentException(
                    "param  PaymentLog's money is NULL");
        Order order = this.orderManager.get(order_id);
        checkDisabled(order);
        if (order.getPay_status().intValue() == OrderStatus.PAY_YES) {
            if (logger.isDebugEnabled()) {
                logger
                        .debug("订单[" + order.getSn()
                                + "]支付状态为[已经支付]，不能再对其进行支付操作");
            }
            return;
            // throw new
            // IllegalStateException("订单["+order.getSn()+"]支付状态为[已经支付]，不能再对其进行支付操作");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("支付订单:" + order.getOrder_id());
        }

        if (order.getMember_id() != null) {
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

            if ("0".equals(mpResp.getError_code())) {
                MemberPointReq mpReq1 = new MemberPointReq();
                mpReq.setType(MemberPointInf.TYPE_BUYGOODS + "_num");
                MemberPointResp resp2 = memberPointServ.getItemPointByName(mpReq1);
                if (resp2 != null) {
                    MemberPointAddReq mpReq2 = new MemberPointAddReq();
                    mpReq2.setPoint(order.getGoods_amount().intValue() * resp2.getPoint());
                    mpReq2.setMember_id(order.getMember_id());
                    mpReq2.setType(MemberPointInf.TYPE_BUYGOODS);
                    mpReq2.setRelated_id(order.getOrder_id());
                    MemberPointAddResp resp3 = memberPointServ.addNewPointHistory(mpReq2);
                }

            }

            /**
             * --------------------------- 增加会员积分--在线支付
             * ---------------------------
             */


            MemberPointCheckReq mpReq_1 = new MemberPointCheckReq();
            mpReq.setType(MemberPointInf.TYPE_ONLINEPAY);
            MemberPointCheckResp mpResp_1 = memberPointServ.checkIsOpen(mpReq_1);

            if (isOnline && mpResp_1.isResult()) {
                MemberPointReq mpReq_2 = new MemberPointReq();
                mpReq_2.setType(MemberPointInf.TYPE_ONLINEPAY + "_num");
                MemberPointResp resp_2 = memberPointServ.getItemPointByName(mpReq_2);
                if (resp_2 != null) {
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

        payment.setType(1);
        payment.setStatus(1);
        payment.setCreate_time(DBTUtil.current());
        payment.setMember_id(order.getMember_id());
        this.baseDaoSupport.insert("payment_logs", payment);

        if (logger.isDebugEnabled()) {
            logger.debug("更新订单状态[" + OrderStatus.ORDER_PAY + "],支付状态["
                    + payStatus + "]");
        }
        this.baseDaoSupport
                .execute(
                        SF.orderSql("SERVICE_PAY_UPDATE"),
                        OrderStatus.ORDER_PAY, payStatus, money,
                        order_id);

        this.log(order_id, "支付订单，金额" + money, null, "管理员");

    }


    /**
     * 更新会员积分
     *
     * @param order
     * @param member
     */
    @Transactional(propagation = Propagation.REQUIRED)
    private void savePoint(Integer point, String memberid) {
        if (point == null) return;


        String sql = SF.orderSql("SERVICE_POINT_UPDATE");
        this.baseDaoSupport.execute(sql, point, memberid);

        if (logger.isDebugEnabled()) {
            logger.debug("增加用户[" + memberid + "]积分:" + point);
        }

        PointHistory pointHistory = new PointHistory();
        pointHistory.setMember_id(memberid);
        pointHistory.setOperator("管理员");
        pointHistory.setPoint(point);
        pointHistory.setReason("order_pay_get");
        pointHistory.setTime(DBTUtil.current());

        //如果会员登录，更新session中预存款和积分的值
        Member sessionMember = UserServiceFactory.getUserService().getCurrentMember();
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
    private void saveMoney(Order order, PaymentLog payment) {
        if (logger.isDebugEnabled()) {
            logger.debug("扣除用户[" + order.getMember_id() + "]预存款:" + payment.getMoney());
        }
        Member member = null;

        MemberByIdReq req = new MemberByIdReq();
        req.setMember_id(order.getMember_id());
        MemberByIdResp resp = memberServ.getMemberById(req);
        if ("0".equals(resp.getError_code())) {
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
        //再取回预存款的数值
        MemberByIdReq req1 = new MemberByIdReq();
        req1.setMember_id(order.getMember_id());
        MemberByIdResp resp1 = memberServ.getMemberById(req);
        if ("0".equals(resp1.getError_code())) {
            member = resp1.getMember();
        }

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
        if (nm == om)
            payStatus = OrderStatus.PAY_CANCEL;// 已退款
        if (nm < om)
            payStatus = OrderStatus.PAY_PARTIAL_REFUND;// 部分退款
        if (nm > om) {
            if (logger.isDebugEnabled()) {
                logger.debug("退款金额[" + nm + "]超过订单支付金额[" + m + "]");
            }
            throw new RuntimeException("退款金额[" + nm + "]超过订单支付金额[" + m + "]");
        }
        payment.setType(2);
        payment.setStatus(1);
        payment.setCreate_time(DBTUtil.current());
        payment.setMember_id(order.getMember_id());
        this.baseDaoSupport.insert("payment_logs", payment);


        if (logger.isDebugEnabled()) {
            logger.debug("更新订单状态[" + OrderStatus.ORDER_CANCEL_PAY + "],支付状态[" + payStatus + "]");
        }
        baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUE_UPDATE"), OrderStatus.ORDER_CANCEL_PAY, payStatus, payment.getMoney(), payment.getOrder_id());
        //返还用户预存款
        //if(payment.getPay_method().trim().equals("预存款支付")){
        //上面有逻辑错误，应该是Pay_type对应的是预存款（deposit），而不是Pay_method（它对应的是Pay_type为在线支付(online)时的支付方式）
        if (payment.getPay_type().trim().equals("deposit")) {
            if (order.getMember_id() != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("返还用户[" + order.getMember_id() + "]预存款:" + payment.getMoney());
                }
                baseDaoSupport.execute(SF.orderSql("SERVICE_ADVANCE_ADD_UPDATE"), payment.getMoney(), payment.getMember_id());
                //既然是用预存款支付，则应加到预存款日志中
                Member member = null;//再取回预存款的数值

                MemberByIdReq req = new MemberByIdReq();
                req.setMember_id(payment.getMember_id());
                MemberByIdResp resp = memberServ.getMemberById(req);
                if ("0".equals(resp.getError_code())) {
                    member = resp.getMember();
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

        this.log(payment.getOrder_id(), "订单退款，金额" + payment.getMoney(), null, "管理员");

    }

    /**
     * 记录订单操作日志
     *
     * @param order_id
     * @param message
     * @param op_id
     * @param op_name
     */
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

    public void shipping(Delivery delivery, List<DeliveryItem> itemList, List<DeliveryItem> giftItemList) {

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
        delivery.setCreate_time(DBTUtil.current());
        baseDaoSupport.insert("delivery", delivery);
        String delivery_id = delivery.getDelivery_id();

        int shipStatus = OrderStatus.SHIP_YES;

        //处理商品发货项
        int goodsShipStatus = this.processGoodsShipItem(orderId, delivery_id, itemList);
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

        String ship_info = "订单发货";
        if (!StringUtil.isEmpty(delivery.getLogi_no()))
            ship_info = "订单发货，物流单据号[" + delivery.getLogi_no() + "]";
        this.log(delivery.getOrder_id(), ship_info, null, "管理员");


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
            //记录退货明细
            this.baseDaoSupport.insert("delivery_item", item);

            //更新退货量
            baseDaoSupport.execute(SF.orderSql("SERVICE_SHIP_NUM_UPDATE"), item.getNum(), orderId, item.getProduct_id());

            //更新库存量
            baseDaoSupport.execute(SF.orderSql("SERVICE_GOODS_STORE_UPDATE"), item.getNum(), item.getGoods_id());
            baseDaoSupport.execute(SF.orderSql("SERVICE_PRODUCT_STORE_UPDATE"), item.getNum(), item.getProduct_id());

        }
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
            //记录退货明细
            this.baseDaoSupport.insert("delivery_item", item);

            //更新退货量
            baseDaoSupport.execute(SF.orderSql("SERVICE_RETURN_SHIPNUM_UPDATE"), item.getNum(), orderId, item.getGoods_id());

            //更新库存量
            baseDaoSupport.execute(SF.orderSql("SERVICE_REPERTORY_UPDATE"), item.getNum(), item.getGoods_id());

        }
        return shipStatus;
    }

    /**
     * 将配件项加入到要发退货项中
     *
     * @param orderid
     * @param itemList
     */
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
                        Collection<AdjunctItem> collection = JSON.parseArray(addonstr, AdjunctItem.class);
                        Iterator<AdjunctItem> iterator = collection.iterator();
                        while (iterator.hasNext()) {
                            AdjunctItem adj = iterator.next();
                            DeliveryItem deliveryItem = new DeliveryItem();
                            deliveryItem.setName(adj.getName());
                            deliveryItem.setGoods_id(adj.getGoodsid());
                            deliveryItem.setProduct_id(adj.getProductid());
                            deliveryItem.setNum(adj.getNum());
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
    private int processGoodsShipItem(String orderId, String delivery_id, List<DeliveryItem> itemList) {


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
            //检查库存
            checkGoodsStore(orderId, productList, item);

            item.setDelivery_id(delivery_id);
            //记录发货明细
            this.baseDaoSupport.insert("delivery_item", item);

            //更新发货量
            baseDaoSupport.execute(SF.orderSql("SERVICE_SHIPNUM_ADD_UPDATE"), item.getNum(), orderId, item.getProduct_id());

            //更新库存量
            baseDaoSupport.execute(SF.orderSql("SERVICE_GOODS_STORE_MINUS_UPDATE"), item.getNum(), item.getGoods_id());
            baseDaoSupport.execute(SF.orderSql("SERVICE_PRODUCT_STORE_MINUS_UPDATE"), item.getNum(), item.getProduct_id());

            //更新购买数量
            baseDaoSupport.execute(SF.orderSql("SERVICE_BUY_NUM_UPDATE"), item.getNum(), item.getGoods_id());

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
    private int processGiftShipItem(String orderId, String delivery_id, List<DeliveryItem> itemList) {


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
	public void refuseRorC(String orderId) {
        if (orderId == null) throw new IllegalArgumentException("param orderid id is null");

        if (logger.isDebugEnabled()) {
            logger.debug("订单[" + orderId + "]管理员拒绝退货");
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
        checkDisabled(order);
        if (order.getShip_status().intValue() == OrderStatus.SHIP_CANCEL) {
            if (logger.isDebugEnabled()) {
                logger.debug("订单[" + order.getSn() + "]状态已经为【已退货】，不能再对其进行退货操作");
            }
            throw new IllegalStateException("订单[" + order.getSn() + "]状态已经为【已退货】，不能再对其进行退货操作");
        }

        delivery.setType(2);
        delivery.setMember_id(order.getMember_id());
        delivery.setCreate_time(DBTUtil.current());
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
        baseDaoSupport.execute(SF.orderSql("SERVICE_SHIP_STATUS_UPDATE"), OrderStatus.ORDER_CANCEL_SHIP, shipStatus, orderId);
        this.log(delivery.getOrder_id(), "订单发货，物流单据号[" + delivery.getLogi_no() + "]", null, "管理员");
    }


    @Override
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
        baseDaoSupport.insert("delivery", delivery);

        if (logger.isDebugEnabled()) {
            logger.debug("更新订单[" + orderId + "]状态[" + OrderStatus.ORDER_CHANGED + "]");
        }
        //更新订单状态为已换货
        baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_SHIP_STATUS_UPDATE"), OrderStatus.ORDER_CHANGED, OrderStatus.SHIP_CHANED, orderId);
        this.log(delivery.getOrder_id(), "订单发货，物流单据号[" + delivery.getLogi_no() + "]", null, "管理员");

    }


    /**
     * 检查退货量是否合法，并且计算退货状态
     *
     * @param orderItemList 购买的订单货物信息 @see {@link IOrderManager#listGoodsItems(Integer)}
     * @param item          某一个发货项
     * @return
     */
    private int checkGoodsReturnNum(List<Map> orderItemList, DeliveryItem item) {

        int status = OrderStatus.SHIP_YES;
        for (Map orderItem : orderItemList) {

            if ((orderItem.get("spec_id").toString()).compareTo(item.getProduct_id()) == 0) {

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
    public void audit_through(String orderId) {
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUS_UPDATE"), OrderStatus.ORDER_NOT_PAY, orderId);
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_COMPLETE_UPDATE"), orderId);
        this.log(orderId, "订单审核通过：", null, ManagerUtils.getAdminUser().getUsername());
    }


    /**
     * 订单审核不通过
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void audit_not_through(String orderId) {
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUS_UPDATE"), OrderStatus.ORDER_COLLECT_REFUSE, orderId);
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_COMPLETE_UPDATE"), orderId);
        this.log(orderId, "订单审核不通过：", null, ManagerUtils.getAdminUser().getUsername());
    }


    /**
     * 订单自动支付
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void pay_auto(String orderId) {
        if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_PAY_UPDATE"), OrderStatus.ORDER_PAY, OrderStatus.PAY_YES, 0, orderId);//设置
        this.log(orderId, "环节自动跳转已支付：", null, ManagerUtils.getAdminUser().getUsername());
    }


    /**
     * 订单受理审核通过
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void accept_through(String orderId) {
        if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUS_UPDATE"), OrderStatus.ORDER_NOT_PAY, orderId);
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_COMPLETE_UPDATE"), orderId);
        this.log(orderId, "订单审核通过：", null, ManagerUtils.getAdminUser().getUsername());
    }


    @Override
    public void accept_not_through(String orderId) {
        // TODO Auto-generated method stub

    }

    /**
     * 订单受理，通知发货
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void accept(String orderId) {
        if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORD_ACCEPT_TIME_UPDATE"), orderId);
        this.log(orderId, "订单受理完成：", null, ManagerUtils.getAdminUser().getUsername());
    }


    /**
     * 完成
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void complete(String orderId) {
        if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_FINISH_UPDATE"), OrderStatus.ORDER_COMPLETE, orderId);
        this.log(orderId, "订单完结：", null, ManagerUtils.getAdminUser().getUsername());
    }

    /**
     * 作废
     */

    @Override
	public void cancel(String orderId) {
        if (orderId == null) throw new IllegalArgumentException("param orderId is NULL");
        this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUS_UPDATE"), OrderStatus.ORDER_CANCELLATION, orderId);
        this.log(orderId, "订单作废：", null, ManagerUtils.getAdminUser().getUsername());
    }


    @Override
	public List<OrderItem> listNotShipGoodsItem(String orderId) {
        String sql = SF.orderSql("SERVICE_ORDER_NOT_SHIP_SELECT");
        return this.daoSupport.queryForList(sql, OrderItem.class, orderId);

    }


    @Override
	public List<Map> listNotShipGiftItem(String orderId) {
        String sql = SF.orderSql("SERVICE_GIFT_NOT_SHIP_SELECT");
        return this.baseDaoSupport.queryForList(sql, orderId);
    }


    @Override
	public List<OrderItem> listShipGoodsItem(String orderId) {
        String sql = SF.orderSql("SERVICE_GOODS_SHIP_SELECT");
        return this.daoSupport.queryForList(sql, OrderItem.class, orderId);
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

            if ((orderItem.get("spec_id").toString()).compareTo(item.getProduct_id()) == 0) {

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

    public OrderPluginBundle getOrderPluginBundle() {
        return orderPluginBundle;
    }

    public void setOrderPluginBundle(OrderPluginBundle orderPluginBundle) {
        this.orderPluginBundle = orderPluginBundle;
    }


    public PointHistoryInf getPointHistoryServ() {
        return pointHistoryServ;
    }

    public void setPointHistoryServ(PointHistoryInf pointHistoryServ) {
        this.pointHistoryServ = pointHistoryServ;
    }

}
