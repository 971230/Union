package com.ztesoft.net.mall.core.action.backend;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.resource.IUserManager;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.resource.model.EopUser;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.model.DlyCenter;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.PrintTmpl;
import com.ztesoft.net.mall.core.model.Printtmplconfig;
import com.ztesoft.net.mall.core.service.AbsPrintTmplData;
import com.ztesoft.net.mall.core.service.IDlyCenterManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IOrderUtils;
import org.apache.struts2.ServletActionContext;
import params.member.req.MemberByIdReq;
import params.member.resp.MemberByIdResp;
import params.print.req.PrintReq;
import service.PrintInf;
import services.FreeOfferInf;
import services.MemberInf;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
public class OrderPrintAction extends WWAction {
    @Resource
    private FreeOfferInf freeOfferServ;
    @Resource
    private MemberInf memberServ;
    
    private IOrderManager orderManager;
    private IDlyCenterManager dlyCenterManager;
   

    private Member member;

    private String orderId;
    private Order ord;
    private List itemList;
    private List orderGiftList;//赠品列表
    private Map ordermap;
    private List dlyCenterList;
    private String dly_center_id;
    private DlyCenter dlyCenter;
    private List printTmplList;
    private String prt_tmpl_id;
    private PrintTmpl printTmpl;
    private String saveAddr;
    private EopSite site;
    private EopUser user;
    private IUserManager userManager;
    private AdminUser adminUser;
    private IOrderUtils orderUtils;

    private PrintInf printServ;

    public String order_prnt() {
        site = EopContext.getContext().getCurrentSite();
        user = userManager.get(site.getUserid());

        itemList = this.orderManager.listGoodsItems(orderId); // 订单商品列表
        this.ord = this.orderManager.get(orderId); // 订单信息
        this.orderGiftList = this.freeOfferServ.getOrderGift(orderId);
        adminUser = orderUtils.getAdminUserById(ord.getUserid());
        if (ord.getMember_id() != null) {
        	MemberByIdReq req = new MemberByIdReq();
			req.setMember_id(ord.getMember_id());
			MemberByIdResp resp = memberServ.getMemberById(req);
			if("0".equals(resp.getError_code())){
				this.member = resp.getMember();
			}
            this.ordermap = this.orderManager.mapOrderByMemberId(ord.getMember_id());
        }
        return "order_prnt";
    }

    public String delivery_prnt() {
        site = EopContext.getContext().getCurrentSite();
        user = userManager.get(site.getUserid());
        itemList = this.orderManager.listGoodsItems(orderId); // 订单商品列表
        this.ord = this.orderManager.get(orderId); // 订单信息
        adminUser = orderUtils.getAdminUserById(ord.getUserid());
        this.ord = this.orderManager.get(orderId); // 订单信息
        this.orderGiftList = this.freeOfferServ.getOrderGift(orderId);
        if (ord.getMember_id() != null) {
        	MemberByIdReq req = new MemberByIdReq();
			req.setMember_id(ord.getMember_id());
			MemberByIdResp resp = memberServ.getMemberById(req);
			if("0".equals(resp.getError_code())){
				this.member = resp.getMember();
			}
        }
        return "delivery_prnt";
    }

    public String global_prnt() {
        site = EopContext.getContext().getCurrentSite();
        user = userManager.get(site.getUserid());
        itemList = this.orderManager.listGoodsItems(orderId); // 订单商品列表
        this.ord = this.orderManager.get(orderId); // 订单信息
        adminUser = orderUtils.getAdminUserById(ord.getUserid());
        if (ord.getMember_id() != null) {
        	MemberByIdReq req = new MemberByIdReq();
			req.setMember_id(ord.getMember_id());
			MemberByIdResp resp = memberServ.getMemberById(req);
			if("0".equals(resp.getError_code())){
				member = resp.getMember();
			}
            this.ordermap = this.orderManager.mapOrderByMemberId(ord.getMember_id());
        }
        return "global_prnt";
    }

    public String ship_prnt_step1() {
        this.ord = this.orderManager.get(orderId); // 订单信息
        this.dlyCenterList = this.dlyCenterManager.list();
        adminUser = orderUtils.getAdminUserById(ord.getUserid());
        PrintReq printReq = new PrintReq();
        this.printTmplList = this.printServ.getPrintTmplList(printReq).getPrintTmplList();
       // this.printTmplList = this.printTmplManager.listCanUse();
        return "ship_prnt_step1";
    }

    public String ship_prnt_step2() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, String[]> param = request.getParameterMap();
        Iterator it = param.keySet().iterator();
        Map<String, String> map = new HashMap<String, String>();
        while (it.hasNext()) {
            String key = (String) it.next();
            String[] ss = param.get(key);
            if (ss != null && ss.length > 0) {
                map.put(key, ss[0]);
            }
        }
        //site = EopContext.getContext().getCurrentSite();
        //user = userManager.get(site.getUserid());
        //map.put("orderId", orderId);
        //map.put("dly_center_id", dly_center_id);
        PrintReq printReq = new PrintReq();
        printReq.setPrt_tmpl_id(prt_tmpl_id);
        this.printTmpl = this.printServ.getPrintTmpl(printReq).getPrintTmpl(); // this.printTmplManager.get(prt_tmpl_id);
        printReq.setConfig_id(printTmpl.getConfig_id());
        Printtmplconfig config = this.printServ.getPrintTmplConfig(printReq).getPrintTmplconfig();
        AbsPrintTmplData abs = SpringContextHolder.getBean(config.getBean_name());
        abs.parseTmplData(printTmpl, map);
        return "ship_prnt_step2";
    }

	/*private String processTmplData(String tmplData, Order order, DlyCenter dlyCenter){
		Date date = new Date();
		Calendar cal =Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		String result = tmplData.replaceAll("收货人-姓名", order.getShip_name());
		result = result.replaceAll("收货人-地区", order.getShipping_area());
		result = result.replaceAll("收货人-地址", order.getShip_addr());
		result = result.replaceAll("收货人-电话", order.getShip_tel());
		result = result.replaceAll("收货人-手机", order.getShip_mobile());
		result = result.replaceAll("收货人-邮编", order.getShip_zip());
		result = result.replaceAll("发货人-姓名", dlyCenter.getName());
		result = result.replaceAll("发货人-地区", dlyCenter.getProvince()+"-"+dlyCenter.getCity()+"-"+dlyCenter.getRegion());
		result = result.replaceAll("发货人-地址", dlyCenter.getAddress());
		result = result.replaceAll("发货人-电话", dlyCenter.getPhone());
		result = result.replaceAll("发货人-手机", dlyCenter.getCellphone());
		result = result.replaceAll("发货人-邮编", dlyCenter.getZip());
		result = result.replaceAll("当日日期-年", String.valueOf(year));
		result = result.replaceAll("当日日期-月", String.valueOf(month));
		result = result.replaceAll("当日日期-日", String.valueOf(day));
		result = result.replaceAll("订单-订单号", order.getSn());
		result = result.replaceAll("订单总金额",  StringUtil.toCurrency(order.getOrder_amount()));
		result = result.replaceAll("订单费用金额",  StringUtil.toCurrency(order.getShipping_amount()));
		result = result.replaceAll("订单物品总重量", StringUtil.toString(order.getWeight()));
		result = result.replaceAll("订单-物品数量", StringUtil.toString(order.getGoods_num()));
		result = result.replaceAll("订单-备注", order.getRemark());
		result = result.replaceAll("订单-送货时间", order.getShip_day()+order.getShip_time());
		result = result.replaceAll("网店名称", EopContext.getContext().getCurrentSite().getSitename());
		return result;
	}*/

    public IOrderManager getOrderManager() {
        return orderManager;
    }

    public void setOrderManager(IOrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Order getOrd() {
        return ord;
    }

    public void setOrd(Order ord) {
        this.ord = ord;
    }

    public List getItemList() {
        return itemList;
    }

    public void setItemList(List itemList) {
        this.itemList = itemList;
    }

    public List getOrderGiftList() {
        return orderGiftList;
    }

    public void setOrderGiftList(List orderGiftList) {
        this.orderGiftList = orderGiftList;
    }

    public Map getOrdermap() {
        return ordermap;
    }

    public void setOrdermap(Map ordermap) {
        this.ordermap = ordermap;
    }

    public List getDlyCenterList() {
        return dlyCenterList;
    }

    public void setDlyCenterList(List dlyCenterList) {
        this.dlyCenterList = dlyCenterList;
    }

    public IDlyCenterManager getDlyCenterManager() {
        return dlyCenterManager;
    }

    public void setDlyCenterManager(IDlyCenterManager dlyCenterManager) {
        this.dlyCenterManager = dlyCenterManager;
    }

  

    public FreeOfferInf getFreeOfferServ() {
		return freeOfferServ;
	}

	public void setFreeOfferServ(FreeOfferInf freeOfferServ) {
		this.freeOfferServ = freeOfferServ;
	}

	public MemberInf getMemberServ() {
		return memberServ;
	}

	public void setMemberServ(MemberInf memberServ) {
		this.memberServ = memberServ;
	}

	public PrintInf getPrintServ() {
		return printServ;
	}

	public void setPrintServ(PrintInf printServ) {
		this.printServ = printServ;
	}

	public List getPrintTmplList() {
        return printTmplList;
    }

    public void setPrintTmplList(List printTmplList) {
        this.printTmplList = printTmplList;
    }

    public PrintTmpl getPrintTmpl() {
        return printTmpl;
    }

    public void setPrintTmpl(PrintTmpl printTmpl) {
        this.printTmpl = printTmpl;
    }

    public String getDly_center_id() {
        return dly_center_id;
    }

    public void setDly_center_id(String dlyCenterId) {
        dly_center_id = dlyCenterId;
    }

    public DlyCenter getDlyCenter() {
        return dlyCenter;
    }

    public void setDlyCenter(DlyCenter dlyCenter) {
        this.dlyCenter = dlyCenter;
    }


    public String getPrt_tmpl_id() {
        return prt_tmpl_id;

    }

    public void setPrt_tmpl_id(String prt_tmpl_id) {
        this.prt_tmpl_id = prt_tmpl_id;
    }

    public String getSaveAddr() {
        return saveAddr;
    }

    public void setSaveAddr(String saveAddr) {
        this.saveAddr = saveAddr;
    }

    public EopSite getSite() {
        return site;
    }

    public void setSite(EopSite site) {
        this.site = site;
    }

    public EopUser getUser() {
        return user;
    }

    public void setUser(EopUser user) {
        this.user = user;
    }

    public IUserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(IUserManager userManager) {
        this.userManager = userManager;
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public IOrderUtils getOrderUtils() {
        return orderUtils;
    }

    public void setOrderUtils(IOrderUtils orderUtils) {
        this.orderUtils = orderUtils;
    }

   


}
