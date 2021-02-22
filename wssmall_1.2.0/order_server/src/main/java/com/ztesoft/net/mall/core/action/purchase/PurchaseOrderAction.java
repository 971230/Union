package com.ztesoft.net.mall.core.action.purchase;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import params.regions.req.RegionProvinceListReq;
import params.regions.resp.RegionProvinceListResp;
import params.suppler.req.SuppliersListReq;
import params.suppler.resp.SuppliersListResp;
import services.GoodsInf;
import services.OrderInf;
import services.RegionsInf;
import services.SupplierInf;
import services.WarehouseInf;
import services.WarhouseGoodsStoreInf;
import zte.params.order.req.OrderAddReq;
import zte.params.order.resp.OrderAddResp;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.WarhouseGoodsStore;
import com.ztesoft.net.mall.core.service.ICartManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IWarehousePurorderMamager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.WarehousePurorder;

public class PurchaseOrderAction extends WWAction {

    private List warehouseList;
    private SupplierInf supplierServ;
    private String user_name;
    private String phone;
    private String goods_name;
    private String sn;
    private String goods_id;
    private String num;
    private String price;
    private String member_lv_id;
    private String create_type;
    private Page goodsPage;
    private String product_id;
    private String supplier_name;
    private String pru_order_name;
    private String pru_order_id;
    private String company_name;
    private String house_name;
    private String order_id;
    private String pru_status;
    private WarehousePurorder warehousePurorder;
    private IWarehousePurorderMamager warehousePurorderMamager;
    private OrderInf orderServ;
    private ICartManager cartManager;
    private IOrderManager orderManager;
    private String orderId;
    private Order ord;
    private List provinceList;
    private Double ship_amount;
    private String audit_state;
    private String z_order_id;
    private String audit_status;
    private String store_action_type;
    private String[] goodsIds;
    private String[] goods_nameArray;

    private GoodsInf goodsServ;
    private WarehouseInf warehouseServ;
    private WarhouseGoodsStoreInf warhouseGoodsStoreServ;
    
    private RegionsInf regionsServ;

    public String isExits() {

        try {

            try {
                pru_order_name = URLDecoder.decode(pru_order_name, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (this.warehousePurorderMamager
                    .pruOrderNameIsExits(pru_order_name)) {
                json = "{'result':2,'message':'采购单名称已存在！'}";
            } else {
                json = "{'result':0,'message':'可以添加！'}";
            }
        } catch (RuntimeException e) {
            json = "{'result':1,'message':'操作失败！'}";
            e.printStackTrace();
        }
        return WWAction.JSON_MESSAGE;
    }

    public String purchaseOrderList() {
        this.webpage = this.warehousePurorderMamager.search(store_action_type, house_name,
                pru_order_name, order_id, company_name, audit_status, create_type, this.getPage(), this.getPageSize());
        return "list";
    }

    public String addPurchaseOrder() {
        warehouseList = this.warehouseServ.warehouseList();
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("webpage", this.webpage);
        if (OrderStatus.WP_CREATE_TYPE_5.equals(create_type)) {
            return "addPurchaseOrder";
        }
        return "returned_apply";
    }

    public String supplierList() {

        SuppliersListReq suppliersListReq = new SuppliersListReq();
        suppliersListReq.setSuppler_name(user_name);
        suppliersListReq.setPhone_num(phone);
        suppliersListReq.setPage_index(String.valueOf(this.getPage()));
        suppliersListReq.setPage_size(String.valueOf(this.getPageSize()));

        SuppliersListResp suppliersListResp = new SuppliersListResp();
        suppliersListResp = this.supplierServ.supplierList(suppliersListReq);

        this.webpage = new Page();
        if (suppliersListResp != null) {
            this.webpage = suppliersListResp.getWebPage();
        }
        return "supplierList";
    }

    public String qryGoods() {
//		GoodsListReq req = new GoodsListReq();
//		req.setMember_lv_id(member_lv_id);
        try {
            if (StringUtils.isNotEmpty(goods_name)) {
                goods_name = new String(goods_name.getBytes("iso-8859-1"), "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//		req.setKeyword(goods_name);
//		goodsPage = goodsServ.qryGoodsList(req).getGoodsPage();
        goodsPage = this.goodsServ.listGoodsByUserId(goods_name, this.getPage(), this.getPageSize());
        return "goods_list";
    }

    public String add() {
        List<Map> mapList = new ArrayList();
        OrderAddReq oreq = new OrderAddReq();
        AdminUser adminUser = ManagerUtils.getAdminUser();

        String[] id = product_id.split(",");
        String[] strNum = num.split(",");
        String[] strPrice = price.split(",");
        String goodsids = "";
        Map<String, Double> mapStore = new HashMap<String, Double>();
        Map<String, String> mapGoodsName = new HashMap<String, String>();
        for (int i = 0; i < goodsIds.length; i++) {
            String goodsid = goodsIds[i].trim();
            if (mapStore.containsKey(goodsid)) {
                Double d = mapStore.get(goodsid) + Double.valueOf(strNum[i]);
                mapStore.put(goodsid, d);
            } else {
                mapStore.put(goodsid, Double.valueOf(strNum[i]));
                goodsids = goodsid.trim() + ",";
                mapGoodsName.put(goodsid, goods_nameArray[i]);
            }
        }
        goodsids = goodsids.substring(0, goodsids.length() - 1);
        boolean flag = true;
        String msg = "";
        if (OrderStatus.WP_CREATE_TYPE_6.equals(create_type) || OrderStatus.WP_CREATE_TYPE_7.equals(create_type)) {
            List<WarhouseGoodsStore> storeList = warhouseGoodsStoreServ.sumStoreByGoodsAndHouse(goodsids, warehousePurorder.getHouse_id());
            if (storeList == null || storeList.size() != mapStore.size()) {
                flag = false;
                for (WarhouseGoodsStore gs : storeList) {
                    if (mapStore.containsKey(gs.getGoods_id())) {
                        mapStore.remove(gs.getGoods_id());
                    }
                }
                Iterator it = mapStore.keySet().iterator();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String goodsName = mapGoodsName.get(key);
                    msg += "[" + goodsName + "]库存不足;\\r\\n";
                }
            } else {
                for (WarhouseGoodsStore gs : storeList) {
                    if (gs.getStore() < mapStore.get(gs.getGoods_id())) {
                        flag = false;
                        msg += "[" + gs.getGoodsName() + "]库存不足;\\r\\n";
                    }
                }
            }
        }
        if (flag) {
            for (int i = 0; i < id.length; i++) {
                Map map = new HashMap();
                map.put("userid", adminUser.getUserid());
                map.put("product_id", id[i].trim());// 150 201310113208001326 //156
                map.put("goods_num", strNum[i].trim());
                map.put("payment_id", 2);
                map.put("ship_amount", ship_amount);
                map.put("price", strPrice[i].trim());

                mapList.add(map);
                oreq.setParamsl(mapList);
            }
            // 测试用=========================
            // WarehousePurorder warehousePurorder = new WarehousePurorder();
            oreq.setCreate_type(create_type);
            oreq.setShip_amount(ship_amount);
            warehousePurorder.setCreate_type(create_type);
            warehousePurorder.setCreate_time(DBTUtil.current());

            if (StringUtils.isNotEmpty(audit_status)) {
                warehousePurorder.setAudit_status(audit_status);
            }

            if (StringUtils.isNotEmpty(store_action_type)) {
                warehousePurorder.setStore_action_type(store_action_type);
            }

            if (OrderStatus.WP_CREATE_TYPE_7.equals(create_type)) {
                warehousePurorder.setAudit_status("1");
                warehousePurorder.setAudit_time(DBTUtil.current());
            }

            oreq.setWarehousePurorder(warehousePurorder);
            // warehousePurorder.setDeposit(100d);
            // 测试用=========================
            try {
            	OrderAddResp resp = orderServ.createOuterOrder(oreq);
                if ("0".equals(resp.getError_code())) {
                    json = "{status:0,msg:'添加成功'}";
                    // cartManager.clean(member_id, false);
                } else {
                    json = "{status:1,msg:'添加失败'}";
                }
            } catch (Exception e) {
                e.printStackTrace();
                json = "{status:1,msg:'添加失败'}";
            }
        } else {
            json = "{status:1,msg:'" + msg + "'}";
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
        //return this.JSON_MESSAGE;
    }

    /**
     * 显示订单的详细内容 到订单详细页
     *
     * @return
     */
    public String detail() {
        warehousePurorder = this.warehousePurorderMamager.qryWarehousePurorderByPruOrderId(pru_order_id);
        this.ord = this.orderManager.get(orderId);
        RegionProvinceListReq req = new RegionProvinceListReq();
        RegionProvinceListResp resp = regionsServ.getProvinceList(req);
		if(resp != null){
			provinceList = resp.getProvince_list();
		}
        return "detail";
    }

    public String showaudit() {
        warehousePurorder = this.warehousePurorderMamager.qryWarehousePurorderByPruOrderId(pru_order_id);
        return "showaudit";
    }

    /**
     * 审核
     *
     * @return
     */
    public String saveAudit() {
        try {
            warehousePurorderMamager.updateAuditStatus(orderId, audit_state);
            this.json = "{'result':2,'message':'操作成功!'}";
        } catch (RuntimeException e) {
            this.json = "{'result':1,'message':'操作失败'}";
        }

        return WWAction.JSON_MESSAGE;
    }

    public List getWarehouseList() {
        return warehouseList;
    }

    public void setWarehouseList(List warehouseList) {
        this.warehouseList = warehouseList;
    }

    public SupplierInf getSupplierServ() {
        return supplierServ;
    }

    public void setSupplierServ(SupplierInf supplierServ) {
        this.supplierServ = supplierServ;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public WarehousePurorder getWarehousePurorder() {
        return warehousePurorder;
    }

    public void setWarehousePurorder(WarehousePurorder warehousePurorder) {
        this.warehousePurorder = warehousePurorder;
    }

    public OrderInf getOrderServ() {
        return orderServ;
    }

    public void setOrderServ(OrderInf orderServ) {
        this.orderServ = orderServ;
    }

    public ICartManager getCartManager() {
        return cartManager;
    }

    public void setCartManager(ICartManager cartManager) {
        this.cartManager = cartManager;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getPru_order_id() {
        return pru_order_id;
    }

    public void setPru_order_id(String pru_order_id) {
        this.pru_order_id = pru_order_id;
    }

    public IWarehousePurorderMamager getWarehousePurorderMamager() {
        return warehousePurorderMamager;
    }

    public void setWarehousePurorderMamager(
            IWarehousePurorderMamager warehousePurorderMamager) {
        this.warehousePurorderMamager = warehousePurorderMamager;
    }

    public String getMember_lv_id() {
        return member_lv_id;
    }

    public void setMember_lv_id(String member_lv_id) {
        this.member_lv_id = member_lv_id;
    }

    public GoodsInf getGoodsServ() {
        return goodsServ;
    }

    public void setGoodsServ(GoodsInf goodsServ) {
        this.goodsServ = goodsServ;
    }

    public Page getGoodsPage() {
        return goodsPage;
    }

    public void setGoodsPage(Page goodsPage) {
        this.goodsPage = goodsPage;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPru_order_name() {
        return pru_order_name;
    }

    public void setPru_order_name(String pru_order_name) {
        this.pru_order_name = pru_order_name;
    }

    public String getCreate_type() {
        return create_type;
    }

    public void setCreate_type(String create_type) {
        this.create_type = create_type;
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

    public Order getOrd() {
        return ord;
    }

    public void setOrd(Order ord) {
        this.ord = ord;
    }


    public List getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List provinceList) {
        this.provinceList = provinceList;
    }

    public Double getShip_amount() {
        return ship_amount;
    }

    public void setShip_amount(Double ship_amount) {
        this.ship_amount = ship_amount;
    }

    public String getAudit_state() {
        return audit_state;
    }

    public void setAudit_state(String audit_state) {
        this.audit_state = audit_state;
    }

    public String[] getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(String[] goodsIds) {
        this.goodsIds = goodsIds;
    }

    public String[] getGoods_nameArray() {
        return goods_nameArray;
    }

    public void setGoods_nameArray(String[] goods_nameArray) {
        this.goods_nameArray = goods_nameArray;
    }

    public String getZ_order_id() {
        return z_order_id;
    }

    public void setZ_order_id(String z_order_id) {
        this.z_order_id = z_order_id;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public String getStore_action_type() {
        return store_action_type;
    }

    public void setStore_action_type(String store_action_type) {
        this.store_action_type = store_action_type;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPru_status() {
        return pru_status;
    }

    public void setPru_status(String pru_status) {
        this.pru_status = pru_status;
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

	public WarehouseInf getWarehouseServ() {
		return warehouseServ;
	}

	public void setWarehouseServ(WarehouseInf warehouseServ) {
		this.warehouseServ = warehouseServ;
	}

	public WarhouseGoodsStoreInf getWarhouseGoodsStoreServ() {
		return warhouseGoodsStoreServ;
	}

	public void setWarhouseGoodsStoreServ(
			WarhouseGoodsStoreInf warhouseGoodsStoreServ) {
		this.warhouseGoodsStoreServ = warhouseGoodsStoreServ;
	}

	public RegionsInf getRegionsServ() {
		return regionsServ;
	}

	public void setRegionsServ(RegionsInf regionsServ) {
		this.regionsServ = regionsServ;
	}


}
