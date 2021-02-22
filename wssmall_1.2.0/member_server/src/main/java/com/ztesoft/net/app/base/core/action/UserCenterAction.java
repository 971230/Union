package com.ztesoft.net.app.base.core.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.ztesoft.remote.inf.IAdvService;
import com.ztesoft.remote.inf.INewsService;
import com.ztesoft.remote.params.adv.req.AdvCountReq;
import com.ztesoft.remote.params.news.req.NewsCountReq;
import params.comments.req.CountCommentsReq;
import params.comments.resp.CountCommentsResp;
import params.suppler.req.AgentTodayNewReq;
import params.suppler.req.AgentWaitAuditReq;
import params.suppler.req.AgentYestodayNewReq;
import params.suppler.req.SuppCooperateReq;
import params.suppler.req.SuppEndReq;
import params.suppler.req.SuppTodayNewReq;
import params.suppler.req.SuppWaitAuditReq;
import params.suppler.req.SuppYestodayNewReq;
import params.suppler.resp.SupplierCountResp;
import services.CommentsInf;
import services.GoodsInf;
import services.OrderInf;
import services.SupplierInf;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.OrderQueryParam;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class UserCenterAction extends WWAction {

    private INewsService newsService;
    private SupplierInf supplierServ;

    private IAdvService advService;
    private OrderInf orderServ;
    /**
     * 待处理事物 (订单信息)
     *
     * @return
     */
    private CommentsInf commentsServ;

    @Resource
    private GoodsInf goodsServ;

    public String OrderCenter() {
        OrderQueryParam ordParam = new OrderQueryParam();
        ordParam.setAction("wait_confir_ship");

        int wait_confirm_ship = orderServ.countOrders(ordParam).getCount();// 待确认收货订单
        ordParam.setAction("wait_refund_ship");
        int wait_returned_ship = orderServ.countOrders(ordParam).getCount();// 待退货订单
        ordParam.setAction("refund");
        int wait_refund_ship = orderServ.countOrders(ordParam).getCount();// 待退费订单

        ordParam.setAction("wait_pay");
        int wait_pay_order = orderServ.countOrders(ordParam).getCount(); //  待支付订单
        ordParam.setAction("wait_ship");
        int wait_deliverGoods_order = orderServ.countOrders(ordParam).getCount();// 待发货订单
        ordParam.setAction("wait_accept");
        int wait_handle_order = orderServ.countOrders(ordParam).getCount();//待处理订单
        try {
            Map countMap = new HashMap();
            countMap.put("wait_confirm_ship", wait_confirm_ship + "");
            countMap.put("wait_returned_ship", wait_returned_ship + "");
            countMap.put("wait_refund_ship", wait_refund_ship + "");
            countMap.put("wait_pay_order", wait_pay_order + "");
            countMap.put("wait_deliverGoods_order", wait_deliverGoods_order + "");
            countMap.put("wait_handle_order", wait_handle_order + "");
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
     * 评价
     */
    public String assess() {
        CountCommentsReq countCommentsReq = new CountCommentsReq();
        countCommentsReq.setObject_type("discuss");
        CountCommentsResp resp = commentsServ.iCountComments(countCommentsReq);
        int wait_assess = resp == null ? 0 : resp.getCount(); // 待回复评价
        countCommentsReq.setObject_type("ask");
        int wait_question = commentsServ.iCountComments(countCommentsReq).getCount(); //  待回复咨询
        try {
            Map countMap = new HashMap();
            countMap.put("wait_assess", wait_assess);
            countMap.put("wait_question", wait_question);
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
     * 待处理事物--商品
     */
    public String waitTransGoods() {
        int wait_finalist_goods = 0; // 待入围审核商品
        int wait_change_goods = 0; //  待审核资料变更
        try {
            Map countMap = new HashMap();
            countMap.put("wait_change_goods", wait_change_goods + "");
            countMap.put("wait_finalist_goods", wait_finalist_goods + "");
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
     * 小伙伴
     *
     * @return
     */
    public String partner() {

        SupplierCountResp supplierCountResp = new SupplierCountResp();
        SupplierCountResp supplierCountResp2 = new SupplierCountResp();

        SuppWaitAuditReq suppWaitAuditReq = new SuppWaitAuditReq();
        AgentWaitAuditReq agentWaitAuditReq = new AgentWaitAuditReq();
        supplierCountResp = supplierServ.waitAuditSupplier(suppWaitAuditReq); // 待审核供货商
        supplierCountResp2 = supplierServ.waitAuditAgency(agentWaitAuditReq); //  待审核经销商
        int wait_audit_supplier = 0;
        int wait_audit_agency = 0;

        if (supplierCountResp != null) {
            wait_audit_supplier = supplierCountResp.getWait_audit_supplier();
        }
        if (supplierCountResp2 != null) {
            wait_audit_agency = supplierCountResp2.getWait_audit_agency();
        }
        try {
            Map countMap = new HashMap();
            countMap.put("wait_audit_supplier", wait_audit_supplier + "");
            countMap.put("wait_audit_agency", wait_audit_agency + "");
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

    /*新增订单*/
    public String newOrder() {
        //add by wui 屏蔽掉
        int yestoday_order = 0;//orderManager.countYestodayOrders(); // 昨日新增菜单
        int today_order = 0;// orderManager.countTodayOrders(); //今日新增菜单
        try {
            Map countMap = new HashMap();
            countMap.put("yestoday_order", yestoday_order + "");
            countMap.put("today_order", today_order + "");
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
     * 供销商
     *
     * @return
     */
    public String supplier() {

        SupplierCountResp supplierCountResp = new SupplierCountResp();
        SupplierCountResp supplierCountResp2 = new SupplierCountResp();
        SupplierCountResp supplierCountResp3 = new SupplierCountResp();
        SupplierCountResp supplierCountResp4 = new SupplierCountResp();
        SupplierCountResp supplierCountResp5 = new SupplierCountResp();
        SupplierCountResp supplierCountResp6 = new SupplierCountResp();

        SuppCooperateReq suppCooperateReq = new SuppCooperateReq();
        SuppEndReq suppEndReq = new SuppEndReq();
        SuppTodayNewReq suppTodayNewReq = new SuppTodayNewReq();
        SuppYestodayNewReq suppYestodayNewReq = new SuppYestodayNewReq();
        AgentTodayNewReq agentTodayNewReq = new AgentTodayNewReq();
        AgentYestodayNewReq agentYestodayNewReq = new AgentYestodayNewReq();
        supplierCountResp = supplierServ.cooperationSupplier(suppCooperateReq); //合作中的供货商
        supplierCountResp2 = supplierServ.endSupplier(suppEndReq); //今已终止供货商
        supplierCountResp3 = supplierServ.todayNewSupplier(suppTodayNewReq); // 今日新增供货商
        supplierCountResp4 = supplierServ.yestodayNewSupplier(suppYestodayNewReq); //昨日新增供货商
        supplierCountResp5 = supplierServ.todayNewAgency(agentTodayNewReq); // 今日新增经销商
        supplierCountResp6 = supplierServ.yestodayNewAgency(agentYestodayNewReq); //昨日新增经销商

        int cooperation_supplier = 0;
        int end_supplier = 0;
        int today_newSupplier = 0;
        int yestoday_newSupplier = 0;
        int today_newAgency = 0;
        int yestoday_newAgency = 0;

        try {

            if (supplierCountResp != null && supplierCountResp.getCooperation_supplier() != null) {
                cooperation_supplier = supplierCountResp.getCooperation_supplier();
            }
            if (supplierCountResp2 != null && supplierCountResp2.getEnd_supplier() != null) {
                end_supplier = supplierCountResp2.getEnd_supplier();
            }
            if (supplierCountResp3 != null && supplierCountResp3.getToday_newSupplier() != null) {
                today_newSupplier = supplierCountResp3.getToday_newSupplier();
            }
            if (supplierCountResp4 != null && supplierCountResp4.getYestoday_newSupplier() != null) {
                yestoday_newSupplier = supplierCountResp4.getYestoday_newSupplier();
            }
            if (supplierCountResp5 != null && supplierCountResp5.getToday_newAgency() != null) {
                today_newAgency = supplierCountResp5.getToday_newAgency();
            }
            if (supplierCountResp6 != null && supplierCountResp6.getToday_newAgency() != null) {
                yestoday_newAgency = supplierCountResp6.getToday_newAgency();
            }


            Map countMap = new HashMap();
            countMap.put("cooperation_supplier", cooperation_supplier + "");
            countMap.put("end_supplier", end_supplier + "");
            countMap.put("today_newSupplier", today_newSupplier + "");
            countMap.put("yestoday_newSupplier", yestoday_newSupplier + "");
            countMap.put("today_newAgency", today_newAgency + "");
            countMap.put("yestoday_newAgency", yestoday_newAgency + "");
            String jsonStr = ManagerUtils.toJsonString(countMap);
            this.json = "{result:1," + jsonStr + "}";
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            this.json = "{result:0,message:\"读取失败：" + e.getMessage() + "\"}";
        }
        return WWAction.JSON_MESSAGE;
    }

    /**
     * 业务概况---商品
     */
    public String businessOverviewGoods() {
        int grounding_goods = this.goodsServ.groundingGoods(); // 上架商品
        int undercarriage_goods = this.goodsServ.undercarriageGoods(); //已下架商品
        int outof_register = this.goodsServ.outofRegister(); //缺货登记
        try {
            Map countMap = new HashMap();
            countMap.put("grounding_goods", grounding_goods + "");
            countMap.put("undercarriage_goods", undercarriage_goods + "");
            countMap.put("outof_register", outof_register + "");
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
     * 广告
     */
    public String adv() {
        AdvCountReq advCountReq = new AdvCountReq();
        int wait_audit_adv = this.advService.getAdvCount(advCountReq).getWait_audit_adv(); // 待审核广告
        int adv_Count = this.advService.getAdvCount(advCountReq).getAdv_Count(); //广告总数
        try {
            Map countMap = new HashMap();
            countMap.put("wait_audit_adv", wait_audit_adv + "");
            countMap.put("adv_Count", adv_Count + "");

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
     * 快讯
     */
    public String flash() {
        NewsCountReq newsCountReq = new NewsCountReq();
        int wait_audit_flash = this.newsService.getNewsCount(newsCountReq).getWait_audit_flash();// newsManager.countNoAudit(); // 待审核快讯
        int flash_count = this.newsService.getNewsCount(newsCountReq).getFlash_count(); //newsManager.countAllNews(); //快讯总数
        try {
            Map countMap = new HashMap();
            countMap.put("wait_audit_adv", wait_audit_flash + "");
            countMap.put("adv_Count", flash_count + "");

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


    public SupplierInf getSupplierServ() {
        return supplierServ;
    }

    public void setSupplierServ(SupplierInf supplierServ) {
        this.supplierServ = supplierServ;
    }

    public GoodsInf getGoodsServ() {
        return goodsServ;
    }

    public void setGoodsServ(GoodsInf goodsServ) {
        this.goodsServ = goodsServ;
    }

    public OrderInf getOrderServ() {
        return orderServ;
    }

    public void setOrderServ(OrderInf orderServ) {
        this.orderServ = orderServ;
    }

    public CommentsInf getCommentsServ() {
        return commentsServ;
    }

    public void setCommentsServ(CommentsInf commentsServ) {
        this.commentsServ = commentsServ;
    }

    public void setNewsService(INewsService newsService) {
        this.newsService = newsService;
    }

    public void setAdvService(IAdvService advService) {
        this.advService = advService;
    }
}
