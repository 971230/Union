package com.ztesoft.net.mall.core.action.backend;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.IOrderDirector;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsApply;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.OrderOuterQueryParam;
import com.ztesoft.net.mall.core.model.support.GoodsTypeDTO;
import com.ztesoft.net.mall.core.service.IOrderOuterManager;
import com.ztesoft.net.mall.core.service.IOrderUtils;
import services.GoodsInf;
import services.GoodsTypeInf;

import javax.annotation.Resource;
import java.util.List;

/**
 * 外系统订单管理action
 *
 * @author wui
 */
public class OrderOuterAction extends WWAction {

    private String orderId;

    private String orderIds;

    private IOrderOuterManager orderOuterManager;

    private OrderOuterQueryParam ordParam;

    private String order;


    private IOrderDirector orderDirector;


    private IOrderUtils orderUtils;
    private OrderOuter orderOuter;

    @Resource
    private GoodsInf goodsServ;

    @Resource
    private GoodsTypeInf goodsTypeServ;

    public String list() {
        this.webpage = this.orderOuterManager.listn(this.getPage(), this.getPageSize(), 0, ordParam, order);
        return "list";
    }

    public String importList() {

        orderIds = "";
        List<OrderOuter> result = this.orderOuterManager.getListByBatchId(ordParam.getBatch_id());
        if (result != null && !result.isEmpty()) {
            for (int i = 0; i < result.size(); i++) {
                orderIds += result.get(i).getOrder_id() + ",";
            }
            orderIds = orderIds.substring(0, orderIds.length() - 1);
        }
//		goodsSy();
        this.webpage = this.orderOuterManager.listn(this.getPage(), this.getPageSize(), 0, ordParam, order);
        return "import_list";
    }

    //订单同步
    public String syorder() {
        try {
            goodsSy();
            this.json = "{result:1,message:'订单" + orderId + "同步成功'}";
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            this.json = "{result:0,message:\"订单同步失败：" + e.getMessage() + "\"}";
        }
        return WWAction.JSON_MESSAGE;
    }

    //订单删除
    public String deleteOrder() {
        try {
            this.orderOuterManager.deleteOrder(orderIds);
            this.json = "{result:1,message:'订单删除成功'}";
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            this.json = "{result:0,message:\"订单删除失败：" + e.getMessage() + "\"}";
        }
        return WWAction.JSON_MESSAGE;
    }


    /**
     * 商品申请流程 触发条件：前台商品申请时触发
     */
    private void goodsSy() {

        if (!StringUtil.isEmpty(orderIds)) {
            for (int i = 0; i < orderIds.split(",").length; i++) {
                String orderid = orderIds.split(",")[i];
                OrderOuter orderOuter = orderOuterManager.getOrderByOrderId(orderid);

                GoodsApply goodsApply = new GoodsApply();
                goodsApply.setGoods_num(Integer.valueOf(orderOuter.getGoods_num()));
                goodsApply.setSales_price(Double.valueOf(orderOuter.getOrder_amount()) / new Integer(orderOuter.getGoods_num()).intValue());
                goodsApply.setApply_desc("淘宝订单同步");
                goodsApply.setSource_from(OrderStatus.SOURCE_FROM_TAOBAO);
                goodsApply.setShip_addr(orderOuter.getShip_addr());
                goodsApply.setShip_day(orderOuter.getShip_day());
                goodsApply.setUserid(orderOuter.getUserid());
                goodsApply.setShip_name(orderOuter.getShip_name());
                goodsApply.setShip_tel(orderOuter.getShip_mobile());
                goodsApply.setOrder_type(orderOuter.getOrder_type());
                goodsApply.setGoods_id(orderOuter.getGoods_id());

                if (Consts.CURR_FOUNDER_3.equals(orderUtils.getAdminUserById(orderOuter.getUserid()).getFounder() + ""))
                    goodsApply.setShip_id(Consts.SHIP_ID_PARTNER_ONE);
                if (Consts.CURR_FOUNDER_2.equals(orderUtils.getAdminUserById(orderOuter.getUserid()).getFounder() + ""))
                    goodsApply.setShip_id(Consts.SHIP_ID_PARTNER_TWO);
                goodsApply.setPayment_id(Consts.PAYMENT_ID_BANK);

                Goods goods = goodsServ.getGoods(orderOuter.getGoods_id());
                GoodsTypeDTO goodsType = goodsTypeServ.get(goods.getType_id());
                orderDirector.getOrderBuilder().buildOrderFlow();
                OrderRequst orderRequst = new OrderRequst();
                orderRequst.setService_name(goodsType.getType_code());
                orderRequst.setFlow_name(OrderBuilder.COLLECT);
                orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_ORDER);
                orderRequst.setGoodsApply(goodsApply);
                orderRequst.setOrderOuter(orderOuter);
                orderDirector.perform(orderRequst);
            }
        }

    }


    public String imported() {

        return "import";
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public IOrderOuterManager getOrderOuterManager() {
        return orderOuterManager;
    }

    public void setOrderOuterManager(IOrderOuterManager orderOuterManager) {
        this.orderOuterManager = orderOuterManager;
    }

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
    }

    public OrderOuterQueryParam getOrdParam() {
        return ordParam;
    }

    public void setOrdParam(OrderOuterQueryParam ordParam) {
        this.ordParam = ordParam;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public IOrderDirector getOrderDirector() {
        return orderDirector;
    }

    public void setOrderDirector(IOrderDirector orderDirector) {
        this.orderDirector = orderDirector;
    }

    public OrderOuter getOrderOuter() {
        return orderOuter;
    }

    public void setOrderOuter(OrderOuter orderOuter) {
        this.orderOuter = orderOuter;
    }

    public IOrderUtils getOrderUtils() {
        return orderUtils;
    }

    public void setOrderUtils(IOrderUtils orderUtils) {
        this.orderUtils = orderUtils;
    }
}