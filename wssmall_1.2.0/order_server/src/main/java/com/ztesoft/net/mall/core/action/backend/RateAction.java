package com.ztesoft.net.mall.core.action.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import params.req.ShopMappingReq;
import params.resp.ShopMappingResp;
import services.CardInf;
import services.GoodsInf;
import services.GoodsTypeInf;
import services.PartnerInf;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.IOrderDirector;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Card;
import com.ztesoft.net.mall.core.model.CardInfRequest;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsApply;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.RateRequest;
import com.ztesoft.net.mall.core.model.RequestLogs;
import com.ztesoft.net.mall.core.model.support.GoodsTypeDTO;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IOrderUtils;
import com.ztesoft.net.mall.core.service.IRequestLogsManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.core.utils.PayUtils;

/**
 * 流量卡调拨处理
 *
 * @author wui
 */
@SuppressWarnings("serial")
public class RateAction extends WWAction {


    private RateRequest rateRequest;
    private String order_id;
    private Order order;
    private IOrderManager orderManager;
    private IOrderDirector orderDirector;

    private IOrderUtils orderUtils;

    private String orderSeq;//淘宝支付流水号
    private String userId; //充值接口归属商户
    private String accNbr; //充值号码
    private String orderAmount; //充值金额
    private String tranDate; // 支付日期
    private String retnCode;//返回编码
    private String retnInfo;//返回信息
    private String goodsId; //充值对应分销平台的商品id
    private String sign;
    private String bill_flag;    //对账标识


    private String exchangeId;
    private String bizCode;
    private String number;
    private String shopId;
    private String amount;
    private String chargeTime;
    private String redirectUri;
    private String signKey;
    private String orderId;


    @Resource
    private CardInf cardServ;

    @Resource
    private GoodsInf goodsServ;

    @Resource
    private GoodsTypeInf goodsTypeServ;

    private PartnerInf partnerServ;
    private IRequestLogsManager requestLogsManager;


    private List goodsList;    //商品列表，导入充值卡或流量卡时关联商品所用


    public IRequestLogsManager getRequestLogsManager() {
        return requestLogsManager;
    }


    public void setRequestLogsManager(IRequestLogsManager requestLogsManager) {
        this.requestLogsManager = requestLogsManager;
    }


    /**
     * 显示流量卡调拨界面
     *
     * @return
     */
    public String showAccNbrDialog() {
        order = orderManager.get(order_id);
        return "accnbr_dialog";
    }


    // 流量卡调拨处理
    public String transfer() {
        try {
            transferCard();
            this.json = "{result:1,message:'订单[" + order.getSn() + "]流量卡调拨'}";
        } catch (RuntimeException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            this.json = "{result:0,message:\"流量卡调拨失败：" + e.getMessage()
                    + "\"}";
        }
        return WWAction.JSON_MESSAGE;
    }

    /**
     * 流量卡调拨受理
     */
    private void transferCard() {
        orderDirector.getOrderBuilder().buildOrderFlow();
        OrderRequst orderRequst = new OrderRequst();
        orderRequst.setService_name(OrderBuilder.CLOUD_KEY);
        orderRequst.setFlow_name(OrderBuilder.ACCEPT);
        orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_ORDER);
        orderRequst.setOrderId(order_id);
        orderRequst.setRateRequest(rateRequest);
        orderDirector.perform(orderRequst);
    }

    public String rateCharge() {
        try {
//			Goods good = goodsManager.getGoodsByType(OrderBuilder.RECHARGE_CARD_KEY, ManagerUtils.getIntegerVal(Double.parseDouble(amount)));
//			if(null == good){
//				this.json = "{result:0,message:'没有面额为[" + amount+ "]的流量卡'}";
//				return this.JSON_MESSAGE;
//			}

            orderAmount = amount;
//			goodsId = good.getGoods_id();
            userId = ManagerUtils.getUserId();
            tranDate = StringUtil.getCurrentTime().replaceAll("-", "");
            chargeTime = StringUtil.getCurrentTime().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
            exchangeId = goodsServ.getPaySequ("s_es_payment_list");
            number = accNbr;
            pay();
            this.json = "{result:1,message:'订单[" + exchangeId + "]流量卡充值成功'}";
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            this.json = "{result:1,message:'订单[" + exchangeId + "]流量卡充值失败，失败原因：" + e.getMessage() + "'}";
        }
        return WWAction.JSON_MESSAGE;

    }

    public String chargeInfo() {

        goodsList = this.cardServ.getGoodsList(OrderBuilder.RECHARGE_CARD_KEY);

        return "rate_charge";
    }

    // 流量卡充值
    public String charge() {
//        HttpRequester request = new HttpRequester();
        try {
            pay();
            this.json = "{result:1,message:'订单[" + exchangeId + "]流量卡充值成功'}";
            //通知百信网支付结果
            //request.sendPost(redirectUri + "?result=1&message=" + "订单[" + exchangeId + "]流量卡充值成功&SignKey=" + signKey);
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            this.json = "{result:0,message:\"" + e.getMessage() + "\"}";
        }
        return WWAction.JSON_MESSAGE;
    }

    /**
     * 淘宝流量卡充值
     */

    //http://127.0.0.1:8080/wssmall/shop/admin/rate!charge.do?goodsId=201304245386000318&userId=201304157387000135&orderAmount=30&tranDate=2012-10-12&accNbr=18911111111&orderSeq=111
    @SuppressWarnings("unchecked")
    private void pay() {

        //根据商户id，获取用户信息、ip过滤
        String requestIp = StringUtil.getIpAddr();
        Map map = new HashMap();//
        if (!StringUtil.isEmpty(shopId)) {
        	
        	ShopMappingReq shopMappingReq = new ShopMappingReq();
        	shopMappingReq.setShopId(shopId);
        	
        	ShopMappingResp shopMappingResp = partnerServ.getUserInfoByShopCode(shopMappingReq);
        	
            map = new HashMap();
        	if(shopMappingResp != null){
        		map = shopMappingResp.getShopMap();
        	}
            if (null != map) {

                String shopIp = map.get("ip").toString();
                if (!requestIp.equals(shopIp)) {

                    throw new RuntimeException("非法ip:" + requestIp);
                }
                userId = map.get("userid").toString();
            } else {
                throw new RuntimeException("商户id有误");
            }
        }

        //查询号码是否可充值
        try {
            Map cheMap = new HashMap();
            cheMap.put("acc_nbr", number);//移动号码
            cheMap.put("goods_id", goodsId);//商品标识
            Map rt = null;
            if (null != rt) {
                String code = Const.getStrValue(rt, "Code");
                if (!code.equals("0")) {
                    String Message = Const.getStrValue(rt, "Message");
                    throw new RuntimeException(Message);
                }
            } else {
                throw new RuntimeException("checkNumberExistsByAccnbr返回为空");
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            throw new RuntimeException("用户充值校验失败：" + e.getMessage());
        }

        //金额+key+userid+orderid+orderdate握手key
        String strMac = "exchangeId=" + exchangeId + "&bizCode=" + bizCode +
                "&orderId=" + orderId + "&number=" + number + "&shopId=" + shopId + "&amount=" +
                amount + "&chargeTime=" + chargeTime + "&redirectUri=" + redirectUri;

        try {
            strMac = PayUtils.md5Digest(strMac);
        } catch (Exception e) {
            e.printStackTrace();
        }

		/*if(!strMac.equals(signKey)  && !StringUtil.isEmpty(shopId) ){
			throw new RuntimeException("用户签名验证失败");
		}*/

        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        RequestLogs requestLogs = new RequestLogs();
        requestLogs.setRequest_id(exchangeId);
        requestLogs.setReq_add(request.getRequestURL().toString() + "?" + request.getQueryString());
        requestLogs.setBiz_code(bizCode);
        requestLogs.setOrder_id(orderId);
        chargeTime = StringUtil.transToDate(chargeTime);
        requestLogs.setRes_date(DBTUtil.current());
        requestLogs.setReq_date(chargeTime);

        requestLogs.setReq_flag(Consts.REQUEST_FLAG_00S);
        requestLogsManager.addReqLog(requestLogs);

        if (StringUtil.isEmpty(bizCode)) //空默认流量卡
            bizCode = OrderBuilder.RECHARGE_CARD_KEY;

        //根据金额获取商品ID
        //Goods goods = goodsManager.getGoodsByType(bizCode, ManagerUtils.getIntegerVal(Double.parseDouble(amount)));
        Goods goods = goodsServ.getGoodsById(goodsId);
        if (null != goods) {
            goodsId = goods.getGoods_id();
        } else {
            requestLogs.setReq_flag(Consts.REQUEST_FLAG_00A);
            requestLogsManager.addReqLog(requestLogs);
            throw new RuntimeException("没有面额为【" + amount + "】的流量卡");
        }

        //判断是否已经受理流量卡
        List recharges = orderUtils.queryRechargeSuccForBill(exchangeId);
        if (!ListUtil.isEmpty(recharges))//已经充值直接退出
            throw new RuntimeException("订单：" + exchangeId + "重复充值");

        AdminUser adminUser = orderUtils.getAdminUserById(userId);
//        ManagerUtils.createSession(adminUser);

        GoodsApply goodsApply = new GoodsApply();
        goodsApply.setGoods_num(1);
        goodsApply.setSales_price(Double.valueOf(amount));
        goodsApply.setApply_desc("淘宝流量卡订单");
        goodsApply.setSource_from(OrderStatus.SOURCE_FROM_TAOBAO);
        goodsApply.setUserid(userId);
        goodsApply.setOrder_type(OrderStatus.ORDER_TYPE_1);
        goodsApply.setGoods_id(goodsId);

        if (null != bill_flag && bill_flag.equals("F")) {
            goodsApply.setBill_flag(bill_flag);
        } else {
            goodsApply.setBill_flag("T");
        }

        if (Consts.CURR_FOUNDER_3.equals(adminUser.getFounder() + ""))
            goodsApply.setShip_id(Consts.SHIP_ID_PARTNER_ONE);
        if (Consts.CURR_FOUNDER_2.equals(adminUser.getFounder() + ""))
            goodsApply.setShip_id(Consts.SHIP_ID_PARTNER_TWO);
        goodsApply.setPayment_id(Consts.PAYMENT_ID_BANK);

        GoodsTypeDTO goodsType = goodsTypeServ.get(goods.getType_id());
        orderDirector.getOrderBuilder().buildOrderFlow();

        goodsApply.setType_code(goodsType.getType_code());

        OrderRequst orderRequst = new OrderRequst();
        orderRequst.setService_name(goodsType.getType_code());
        orderRequst.setFlow_name(OrderBuilder.COLLECT);
        orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_ORDER);
        orderRequst.setGoodsApply(goodsApply);

        String lan_id = cardServ.getLanIdByAccNbr(number);
        CardInfRequest cardInfRequest = new CardInfRequest(exchangeId, userId, number, amount, chargeTime, retnCode, retnInfo, goodsId, lan_id);
        orderRequst.setCardInfRequest(cardInfRequest);

        goodsApply.setCardInfRequest(cardInfRequest);


        Card card = cardServ.getCardByUserIdAndMoney(userId, amount, goodsType.getType_code(), goodsId);
        orderRequst.setUsedCard(card);

        orderDirector.perform(orderRequst);

    }


    public IOrderManager getOrderManager() {
        return orderManager;
    }


    public void setOrderManager(IOrderManager orderManager) {
        this.orderManager = orderManager;
    }


    public IOrderDirector getOrderDirector() {
        return orderDirector;
    }


    public void setOrderDirector(IOrderDirector orderDirector) {
        this.orderDirector = orderDirector;
    }


    public String getOrder_id() {
        return order_id;
    }


    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }


    public Order getOrder() {
        return order;
    }


    public void setOrder(Order order) {
        this.order = order;
    }


    public IOrderUtils getOrderUtils() {
        return orderUtils;
    }


    public void setOrderUtils(IOrderUtils orderUtils) {
        this.orderUtils = orderUtils;
    }


    public String getOrderSeq() {
        return orderSeq;
    }


    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }


    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getAccNbr() {
        return accNbr;
    }


    public void setAccNbr(String accNbr) {
        this.accNbr = accNbr;
    }


    public String getOrderAmount() {
        return orderAmount;
    }


    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }


    public String getTranDate() {
        return tranDate;
    }


    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }


    public String getRetnCode() {
        return retnCode;
    }


    public void setRetnCode(String retnCode) {
        this.retnCode = retnCode;
    }


    public String getRetnInfo() {
        return retnInfo;
    }


    public void setRetnInfo(String retnInfo) {
        this.retnInfo = retnInfo;
    }


    public String getGoodsId() {
        return goodsId;
    }


    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getSign() {
        return sign;
    }


    public void setSign(String sign) {
        this.sign = sign;
    }



    public RateRequest getRateRequest() {
        return rateRequest;
    }


    public void setRateRequest(RateRequest rateRequest) {
        this.rateRequest = rateRequest;
    }


    public String getExchangeId() {
        return exchangeId;
    }


    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }


    public String getBizCode() {
        return bizCode;
    }


    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }


    public String getNumber() {
        return number;
    }


    public void setNumber(String number) {
        this.number = number;
    }


    public String getShopId() {
        return shopId;
    }


    public void setShopId(String shopId) {
        this.shopId = shopId;
    }


    public String getAmount() {
        return amount;
    }


    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getChargeTime() {
        return chargeTime;
    }


    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }


    public String getSignKey() {
        return signKey;
    }


    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }


    public String getOrderId() {
        return orderId;
    }


    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public PartnerInf getPartnerServ() {
		return partnerServ;
	}


	public void setPartnerServ(PartnerInf partnerServ) {
		this.partnerServ = partnerServ;
	}


	public String getRedirectUri() {
        return redirectUri;
    }


    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }


    public String getBill_flag() {
        return bill_flag;
    }


    public void setBill_flag(String bill_flag) {
        this.bill_flag = bill_flag;
    }


    public List getGoodsList() {
        return goodsList;
    }


    public void setGoodsList(List goodsList) {
        this.goodsList = goodsList;
    }


}
