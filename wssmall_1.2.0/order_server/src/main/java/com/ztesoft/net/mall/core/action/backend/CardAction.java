package com.ztesoft.net.mall.core.action.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import params.req.ShopMappingReq;
import params.resp.ShopMappingResp;
import services.CardInf;
import services.GoodsInf;
import services.GoodsTypeInf;
import services.PartnerInf;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.IOrderDirector;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Card;
import com.ztesoft.net.mall.core.model.CardInfRequest;
import com.ztesoft.net.mall.core.model.CardRequest;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsApply;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.RequestLogs;
import com.ztesoft.net.mall.core.model.support.GoodsTypeDTO;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IOrderUtils;
import com.ztesoft.net.mall.core.service.IRequestLogsManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.core.utils.PayUtils;

/**
 * 充值卡调拨处理类
 *
 * @author wui
 */
public class CardAction extends WWAction {
    @Resource
    private CardInf cardServ;

    private String orderId;
    private Order order;
    private IOrderManager orderManager;
    private IOrderUtils orderUtils;
    private Card card;
    private String cards_ids;
    private IOrderDirector orderDirector;
    private String from_page;


    /////////////////充值卡充值
    private String orderSeq;//淘宝支付流水号
    private String userId; //充值接口归属商户
    private String accNbr; //充值号码
    private String orderAmount; //充值金额
    private String tranDate; // 支付日期
    private String retnCode;//返回编码
    private String retnInfo;//返回信息
    private String goodsId; //充值对应分销平台的商品id
    private String bill_flag;

    private String exchangeId;
    private String bizCode;
    private String clientId;
    private String password;
    private String number;
    private String shopId;
    private String amount;
    private String chargeTime;
    private String redirectUri;
    private String signKey;


    private PartnerInf partnerServ;
    private IRequestLogsManager requestLogsManager;

    @Resource
    private GoodsInf goodsServ;

    @Resource
    private GoodsTypeInf goodsTypeServ;

    private String sign;
    private Map countMap;
    private String type_code;    //区分充值卡和流量卡

    private List goodsList;    //商品列表，导入充值卡或流量卡时关联商品所用

    private AdminUser adminUser;


    protected String json;

    //切面处理订单id
    public void acpOrderId() {
        if (!StringUtil.isEmpty(orderId) && orderId.indexOf(",") > -1)
            orderId = orderId.substring(0, orderId.indexOf(","));
    }

    public String cardCharge() {
        try {

//			Goods good = goodsManager.getGoodsByType(OrderBuilder.CARD_KEY, ManagerUtils.getIntegerVal(Double.parseDouble(amount)));
//			if(null == good){
//				this.json = "{result:0,message:'没有面额为[" + amount+ "]的充值卡'}";
//				return this.JSON_MESSAGE;
//			}
            orderAmount = amount;
//			goodsId = good.getGoods_id();
            userId = ManagerUtils.getUserId();
            tranDate = StringUtil.getCurrentTime().replaceAll("-", "");
            chargeTime = StringUtil.getCurrentTime().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
            number = accNbr;
            exchangeId = goodsServ.getPaySequ("s_es_payment_list");
            pay();
            this.json = "{result:1,message:'订单[" + orderSeq + "]充值卡充值成功'}";
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            this.json = "{result:1,message:'订单[" + exchangeId + "]充值卡充值失败，" + e.getMessage() + "'}";
        }
        return WWAction.JSON_MESSAGE;
    }

    public String chargeInfo() {

        goodsList = this.cardServ.getGoodsList(OrderBuilder.CARD_KEY);

        return "card_charge";
    }

    // 充值卡充值
    //http://127.0.0.1:8080/wssmall/shop/admin/card!charge.do?shopId=BXW&amount=50&tranDate=2012-10-12&orderSeq=20130517

    public String charge() {
//        HttpRequester request = new HttpRequester();
        try {
            pay();
            this.json = "{result:1,message:'订单充值成功'}";
            //通知百信网充值结果
            //request.sendPost(redirectUri + "?result=1&message=" + "订单[" + exchangeId + "]充值卡充值成功&SignKey=" + signKey);
        } catch (Exception e) {
            //e.printStackTrace();
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }

            this.json = "{result:0,message:\"" + e.getMessage() + "\"}";
        }
        return WWAction.JSON_MESSAGE;
    }

    /**
     * 充值卡充值
     */
    @SuppressWarnings("unchecked")
    private void pay() {

        //根据商户id，获取用户信息、ip过滤
        String requestIp = StringUtil.getIpAddr();
        Map map = new HashMap();
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

                    throw new RuntimeException("非法ip" + requestIp);
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


        //exchangeId = goodsManager.getPaySequ("s_es_payment_list");

        //金额+key+userid+orderid+orderdate握手key
        String strMac = "exchangeId=" + exchangeId + "&bizCode=" + bizCode +
                "&orderId=" + orderId + "&number=" + number + "&shopId=" + shopId + "&amount=" +
                amount + "&chargeTime=" + chargeTime + "&redirectUri=" + redirectUri;

        try {
            strMac = PayUtils.md5Digest(strMac);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        //MD5校验
//		if(!strMac.equals(signKey) && !StringUtil.isEmpty(shopId) ){
//			throw new RuntimeException("用户签名验证失败");
//		}

        //判断是否已经受理充值卡,已经受理成功不允许受理
        List recharges = orderUtils.queryRechargeSuccForBill(exchangeId);
        if (!ListUtil.isEmpty(recharges))//已经充值直接退出
            throw new RuntimeException("订单：" + exchangeId + "重复充值");

        AdminUser adminUser = orderUtils.getAdminUserById(userId);
//        ManagerUtils.createSession(adminUser);

        //写日志表
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        RequestLogs requestLogs = new RequestLogs();
        requestLogs.setRequest_id(exchangeId);
        requestLogs.setReq_add(request.getRequestURL().toString() + "?" + request.getQueryString());
        requestLogs.setBiz_code(bizCode);
        requestLogs.setOrder_id(orderId);
        chargeTime = StringUtil.transToDate(chargeTime);
        requestLogs.setReq_date(chargeTime);
        //根据金额获取商品ID
        //Goods goods = goodsManager.getGoodsByType(OrderBuilder.CARD_KEY, ManagerUtils.getIntegerVal(Double.parseDouble(amount)));
        Goods goods = goodsServ.getGoodsById(goodsId);

        if (null == goods) {
            requestLogs.setReq_flag(Consts.REQUEST_FLAG_00A);
            requestLogsManager.addReqLog(requestLogs);
            throw new RuntimeException("不存在id为【" + goodsId + "】的充值卡");
        }

        requestLogs.setReq_flag(Consts.REQUEST_FLAG_00S);
        requestLogsManager.addReqLog(requestLogs);

        GoodsApply goodsApply = new GoodsApply();
        goodsApply.setGoods_num(1);
        goodsApply.setSales_price(Double.valueOf(amount));
        goodsApply.setApply_desc("淘宝充值卡订单");
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

    public String listCount() {

        if (card == null) {
            card = new Card();
        }
        try {
            Map countMap = this.cardServ.listc(card);
            String jsonStr = ManagerUtils.toJsonString(countMap);

            this.json = "{result:1," + jsonStr + "}";
        } catch (RuntimeException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            this.json = "{result:0,message:\"读取失败：" + e.getMessage() + "\"}";
        }

        return WWAction.JSON_MESSAGE;
    }


    /**
     * 查询可申请商品的总量(不带goodsId)
     * 目前功能仅适用首页数字超链接，其它情况请看清楚条件慎用
     *
     * @return
     * @author hu.yi
     * @date 2013.5.16
     */
    public String listAvialableCount() {


        if (card == null) {
            card = new Card();
        }
        try {
            Map countMap = this.cardServ.listAvialableC(card);
            String jsonStr = ManagerUtils.toJsonString(countMap);

            this.json = "{result:1," + jsonStr + "}";
        } catch (RuntimeException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            this.json = "{result:0,message:\"读取失败：" + e.getMessage() + "\"}";
        }

        return WWAction.JSON_MESSAGE;

    }


    /**
     * 显示充值卡调拨界面
     *
     * @return
     */
    public String showCardDialog() {
        acpOrderId();
        order = orderManager.get(orderId); //根据订单获取分销商信息
        if (card == null) {
            card = new Card();
            card.setType_code(order.getType_code());
        }
        this.webpage = this.cardServ.transfer_list(this.getPage(), this.getPageSize(), 0, card);
        return "card_list";
    }


    /**
     * 显示充值卡调拨界面
     *
     * @return
     */
    public String showCardRandomDialog() {
        acpOrderId();
        order = orderManager.get(orderId); //根据订单获取分销商信息
        if (card == null) {
            card = new Card();
            card.setType_code(order.getType_code());
        }
        if (order != null) {
            card.setGoods_id(order.getGoods_id());//根据商品获取金额
        }
        countMap = this.cardServ.listc(card);
        return "card_random_list";
    }


    /**
     * 分页读取订单列表
     * 根据订单状态 state 检索，如果未提供状态参数，则检索所有
     *
     * @return
     */
    public String list() {
        acpOrderId();
        this.adminUser = ManagerUtils.getAdminUser();
        //默认查询可用卡信息
        if (card != null) {
            if (card.getState() == null) {
                card.setState("0");
            }
        }
        this.webpage = this.cardServ.transfer_list(this.getPage(), this.getPageSize(), 0, card);


        /**
         * 以下代码是为了导出excel功能所编写
         * title:excel表头，传递需要打印的表头注释
         * content:内容字段，输入实体对象所对应表头字段的属性
         * set的时候勿更改属性名，否则将出现错误
         * fileTitle为导出excel文件的标题，根据自己导出的内容设定
         *
         * 若需要导出功能，请在jsp页面的grid属性里面添加 excel="yes"
         */
        String[] title = {"卡号", "状态", "生效时间", "失效时间", "价格", "调拨时间", "调拨订单编号", "充值订单编号"};
        String[] content = {"card_code", "state_name", "eff_date", "exp_date", "card_price", "deal_time", "order_id", "sec_order_id"};
        String fileTitle = "充值卡流量卡数据导出";

        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("webpage", this.webpage);
        request.setAttribute("title", title);
        request.setAttribute("content", content);
        request.setAttribute("fileTitle", fileTitle);

        if (excel != null && !"".equals(excel)) {
            /**
             * 修改退出方式 mochunrun 20130917
             */
            DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
            return null;
            //return "export_excel_list";
        } else {
            return "card_list";
        }

    }

    //充值卡流量卡可用库存查询
    public String list_avaible() {
        acpOrderId();

        if (card == null) {
            card = new Card();
        }
        if (!StringUtil.isEmpty(goodsId)) {
            card.setGoods_id(goodsId);
        }


        card.setP_apply_show_parent_stock(Consts.APPLY_SHOW_PARENT_STOCK);
        card.setState(Consts.CARD_INFO_STATE_0);
        this.webpage = this.cardServ.transfer_list(this.getPage(), this
                .getPageSize(), 0, card);

        if (!StringUtil.isEmpty(orderId))
            order = orderManager.get(orderId);
        return "card_avliable_list";
    }


    /**
     * 查询所有的可用商品
     * 目前功能仅适用首页数字超链接，其它情况请看清楚条件慎用
     *
     * @return
     * @author hu.yi
     * @date 2013.5.17
     */
    public String list_allAvaible() {
        acpOrderId();

        if (card == null) {
            card = new Card();
        }

        card.setP_apply_show_parent_stock(Consts.APPLY_SHOW_PARENT_STOCK);
        card.setState(Consts.CARD_INFO_STATE_0);
        this.webpage = this.cardServ.transfer_allList(this.getPage(), this
                .getPageSize(), 0, card);

        if (!StringUtil.isEmpty(orderId))
            order = orderManager.get(orderId);
        return "card_avliable_list";
    }


    public String listImport() {
        acpOrderId();
        this.webpage = this.cardServ.transfer_list(this.getPage(), this.getPageSize(), 0, card);
        return "importCard_list";
    }


    public String importCard() {

        goodsList = this.cardServ.getGoodsList(type_code);

        HttpServletRequest request = ServletActionContext.getRequest();
        request.getSession().setAttribute("type_code", type_code);
        return "import_card_list";
    }

    public Map listc() {
        return new HashMap();
    }

    // 充值卡调拨处理
    public String transfer() {
        try {
            acpOrderId();
            transferCard();
            this.json = "{result:1,message:'订单[" + orderId + "]调拨成功'}";
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            this.json = "{result:0,message:\"调拨失败：" + e.getMessage()
                    + "\"}";
        }
        return WWAction.JSON_MESSAGE;
    }

    /**
     * 订单充值卡调拨受理
     */
    private void transferCard() {

        Order order = this.orderManager.get(orderId);

        CardRequest cardRequest = new CardRequest();
        cardRequest.setCards_ids(cards_ids);

        orderDirector.getOrderBuilder().buildOrderFlow();
        OrderRequst orderRequst = new OrderRequst();
        orderRequst.setService_name(order.getType_code());
        orderRequst.setFlow_name(OrderBuilder.ACCEPT);
        orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_ORDER);
        orderRequst.setOrderId(orderId);
        orderRequst.setCardRequest(cardRequest);
        orderDirector.perform(orderRequst);
    }


    public Order getOrder() {
        return order;
    }


    public void setOrder(Order order) {
        this.order = order;
    }


    public IOrderManager getOrderManager() {
        return orderManager;
    }


    public void setOrderManager(IOrderManager orderManager) {
        this.orderManager = orderManager;
    }


    public String getOrderId() {
        return orderId;
    }


    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public IOrderUtils getOrderUtils() {
        return orderUtils;
    }


    public void setOrderUtils(IOrderUtils orderUtils) {
        this.orderUtils = orderUtils;
    }


    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }


    public String getCards_ids() {
        return cards_ids;
    }

    public void setCards_ids(String cards_ids) {
        this.cards_ids = cards_ids;
    }

    public IOrderDirector getOrderDirector() {
        return orderDirector;
    }


    public void setOrderDirector(IOrderDirector orderDirector) {
        this.orderDirector = orderDirector;
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

    @Override
	public String getJson() {
        return json;
    }

    @Override
	public void setJson(String json) {
        this.json = json;
    }

    public String getFrom_page() {
        return from_page;
    }

    public void setFrom_page(String from_page) {
        this.from_page = from_page;
    }

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }

    public Map getCountMap() {
        return countMap;
    }

    public void setCountMap(Map countMap) {
        this.countMap = countMap;
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public PartnerInf getPartnerServ() {
		return partnerServ;
	}

	public void setPartnerServ(PartnerInf partnerServ) {
		this.partnerServ = partnerServ;
	}

	public IRequestLogsManager getRequestLogsManager() {
        return requestLogsManager;
    }

    public void setRequestLogsManager(IRequestLogsManager requestLogsManager) {
        this.requestLogsManager = requestLogsManager;
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
