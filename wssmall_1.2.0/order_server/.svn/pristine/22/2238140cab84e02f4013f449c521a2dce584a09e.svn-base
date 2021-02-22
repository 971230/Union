package com.ztesoft.net.mall.core.action.backend;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.Supplier;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.*;
import com.ztesoft.net.mall.core.service.*;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.WarehousePurorder;

import org.apache.struts2.ServletActionContext;
import params.order.req.ConfirmShippingReq;
import params.order.resp.ConfirmShippingResp;
import params.suppler.req.SupplierObjReq;
import params.suppler.resp.SupplierObjResp;
import services.DeliveryInf;
import services.SupplierInf;
import services.WarehouseInf;
import services.WarhouseGoodsStoreInf;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购退货
 *
 * @作者 MoChunrun
 * @创建日期 2013-11-11
 * @版本 V 1.0
 */
public class WPDeliveryAction extends WWAction {

    private DeliveryInf deliveryServ;

    private IDeliveryManager deliveryManager;
    private SupplierInf supplierServ;
    private IWarehousePurorderMamager warehousePurorderMamager;
    private IOrderManager orderManager;
    private String create_type = OrderStatus.WP_CREATE_TYPE_6;
    private String type = "2";
    private String logi_no;
    private String order_id;
    private Page deliveryPage;
    private Delivery delivery;
    private List<DeliveryItem> deliveryItems;
    private String delivery_id;
    private Order order;
    private WarehousePurorder warehousePurorder;
    private Supplier supplier;
    private Warehouse warehouse;

    private List logiList;
    private List dlyTypeList;
    private ILogiManager logiManager;
    private IDlyTypeManager dlyTypeManager;
    private IOrderNFlowManager orderNFlowManager;
    private List itemList;
    private String logi_id_name;
    private IDeliveryFlow deliveryFlowManager;
    private DeliveryFlow flow;

    private String goods_idArray[];
    private String goods_nameArray[];
    private String goods_snArray[];
    private String product_idArray[];
    private Integer numArray[];
    private String giftidArray[];
    private String giftnameArray[];
    private Integer giftnumArray[];
    private String[] itemid_idArray;

    private String goods_id;
    private String house_id;

    @Resource
    private WarhouseGoodsStoreInf warhouseGoodsStoreServ;

    @Resource
    private WarehouseInf warehouseServ;

    private String name;
    private String s_delivery_id;

    /**
     * 统计商品库存数量
     *
     * @return
     * @作者 MoChunrun
     * @创建日期 2013-11-14
     */
    public String sumGoodsStore() {
        String json = "";
        try {
            int store = warhouseGoodsStoreServ.sumStoreByGoodsId(goods_id, house_id);
            json = "{status:0,msg:'成功',store:" + store + "}";
        } catch (Exception ex) {
            ex.printStackTrace();
            json = "{status:1,msg:'失败'}";
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String showReturnedDialog() {
        logiList = logiManager.list();
        Map map = new HashMap();
        map.put("id", "0");
        map.put("name", "--其它--");
        logiList.add(map);
        dlyTypeList = dlyTypeManager.list();
        order = orderManager.get(order_id);
        //itemList = orderManager.listGoodsItems(orderId); // 订单商品列表
        itemList = this.orderNFlowManager.listNotShipGoodsItem(order_id);
        return "returned_dialog";
    }

    /**
     * 出库
     *
     * @return
     * @作者 MoChunrun
     * @创建日期 2013-11-13
     */
    public String confirmReturned() {
        String json = "";
        try {
            Delivery dv = null;
            if (!StringUtil.isEmpty(delivery_id)) {
                dv = this.deliveryManager.get(delivery_id);
            } else {
                dv = this.deliveryManager.getByOrderId(order_id);
            }
            String[] logi = logi_id_name.split("\\|");
            dv.setLogi_id(logi[0]);
            dv.setLogi_name(logi[1]);
            dv.setShip_name(delivery.getShip_name());
            dv.setShip_tel(delivery.getShip_tel());
            dv.setShip_mobile(delivery.getShip_mobile());
            dv.setShip_zip(delivery.getShip_zip());
            dv.setShip_addr(delivery.getShip_addr());
            dv.setRemark(delivery.getRemark());
            List<DeliveryItem> itemList = new ArrayList<DeliveryItem>();
            int i = 0;
            int totalNum = 0;
            for (String goods_id : goods_idArray) {
                DeliveryItem item = new DeliveryItem();
                item.setGoods_id(goods_id);
                item.setName(goods_nameArray[i]);
                item.setNum(numArray[i]);
                item.setProduct_id(product_idArray[i]);
                item.setSn(goods_snArray[i]);
                item.setItemtype(0);
                item.setOrder_item_id(itemid_idArray[i]);
                itemList.add(item);
                if (item.getNum() != null) {
                    totalNum += item.getNum();
                }
                i++;
            }
            if (totalNum == 0) throw new IllegalArgumentException("商品出库总数不能为[0]");
            i = 0;
            List<DeliveryItem> giftitemList = new ArrayList<DeliveryItem>();
            if (giftidArray != null) {
                for (String giftid : giftidArray) {
                    DeliveryItem item = new DeliveryItem();
                    item.setGoods_id(giftid);
                    item.setName(giftnameArray[i]);
                    item.setNum(giftnumArray[i]);
                    item.setProduct_id(giftid);
                    item.setItemtype(2);
                    giftitemList.add(item);
                    item.setOrder_item_id(itemid_idArray[i]);
                    i++;
                }
            }
            warehousePurorder = this.warehousePurorderMamager.qryWarehousePurorderByBatchId(dv.getBatch_id());
            ConfirmShippingReq req = new ConfirmShippingReq();
            req.setDelivery_id(delivery_id);
            if (warehousePurorder != null)
                req.setHouse_id(warehousePurorder.getHouse_id());
            req.setDeliveryItems(itemList);
            req.setGiftitemList(giftitemList);
            req.setDelivery(dv);
            if (OrderStatus.WP_CREATE_TYPE_6.equals(create_type) || OrderStatus.WP_CREATE_TYPE_7.equals(create_type)) {
                req.setShippingType("2");
            } else {
                req.setShippingType("1");
            }

            ConfirmShippingResp resp = deliveryServ.confirmShipping(req);
            if ("2".equals(resp.getError_code())) {
                json = "{status:2,msg:'" + resp.getError_msg() + "'}";
            } else {
                if ("0".equals(delivery.getLogi_id())) {
                    //写物流流程表
                    String delivery_id = delivery.getDelivery_id();
                    AdminUser user = ManagerUtils.getAdminUser();
                    if (flow != null) {
                        flow.setCreate_time(DBTUtil.current());
                        flow.setOp_id(user.getUserid());
                        flow.setOp_name(user.getUsername());
                        flow.setDelivery_id(delivery_id);
                        deliveryFlowManager.addDeliveryFlow(flow);
                    }
                }
                json = "{status:1,msg:'处理成功'}";
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            json = "{status:0,msg:'" + ex.getMessage() + "'}";
        } catch (Exception ex) {
            ex.printStackTrace();
            json = "{status:0,msg:'失败'}";
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String detial() {
        delivery = this.deliveryManager.get(delivery_id);
        return "wp_detial";
    }

    /**
     * 查询采购单列表
     *
     * @return
     * @作者 MoChunrun
     * @创建日期 2013-11-11
     */
    public String qryWpDelivery() {
        deliveryPage = deliveryManager.qryDelivery(create_type, logi_no, type, order_id, this.getPage(), this.getPageSize(), OrderStatus.WP_AUDIT_STATUS_1, name, s_delivery_id);
        return "wp_deliverylist";
    }

    public String showBase() {
        delivery = this.deliveryManager.get(delivery_id);
        order = this.orderManager.get(delivery.getOrder_id());
        warehousePurorder = this.warehousePurorderMamager.qryWarehousePurorderByBatchId(order.getBatch_id());

        SupplierObjReq supplierObjReq = new SupplierObjReq();
        supplierObjReq.setSupplier_id(warehousePurorder.getSupper_id());

        SupplierObjResp supplierObjResp = new SupplierObjResp();
        supplierObjResp = this.supplierServ.findSupplierById(supplierObjReq);

        supplier = new Supplier();
        if (supplierObjResp != null) {
            supplier = supplierObjResp.getSupplier();
        }
        warehouse = this.warehouseServ.findWarehouseByHoustId(warehousePurorder.getHouse_id());
        return "delivery_base";
    }

    public String showItems() {
        deliveryItems = this.deliveryManager.qryDeliveryItems(delivery_id);
        return "delivery_items";
    }

    public IDeliveryManager getDeliveryManager() {
        return deliveryManager;
    }

    public void setDeliveryManager(IDeliveryManager deliveryManager) {
        this.deliveryManager = deliveryManager;
    }

    public String getCreate_type() {
        return create_type;
    }

    public void setCreate_type(String create_type) {
        this.create_type = create_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogi_no() {
        return logi_no;
    }

    public void setLogi_no(String logi_no) {
        this.logi_no = logi_no;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Page getDeliveryPage() {
        return deliveryPage;
    }

    public void setDeliveryPage(Page deliveryPage) {
        this.deliveryPage = deliveryPage;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public List<DeliveryItem> getDeliveryItems() {
        return deliveryItems;
    }

    public void setDeliveryItems(List<DeliveryItem> deliveryItems) {
        this.deliveryItems = deliveryItems;
    }

    public String getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(String delivery_id) {
        this.delivery_id = delivery_id;
    }

    public IWarehousePurorderMamager getWarehousePurorderMamager() {
        return warehousePurorderMamager;
    }

    public void setWarehousePurorderMamager(
            IWarehousePurorderMamager warehousePurorderMamager) {
        this.warehousePurorderMamager = warehousePurorderMamager;
    }

    public IOrderManager getOrderManager() {
        return orderManager;
    }

    public void setOrderManager(IOrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public WarehousePurorder getWarehousePurorder() {
        return warehousePurorder;
    }

    public void setWarehousePurorder(WarehousePurorder warehousePurorder) {
        this.warehousePurorder = warehousePurorder;
    }

    public SupplierInf getSupplierServ() {
        return supplierServ;
    }

    public void setSupplierServ(SupplierInf supplierServ) {
        this.supplierServ = supplierServ;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public List getLogiList() {
        return logiList;
    }

    public void setLogiList(List logiList) {
        this.logiList = logiList;
    }

    public List getDlyTypeList() {
        return dlyTypeList;
    }

    public void setDlyTypeList(List dlyTypeList) {
        this.dlyTypeList = dlyTypeList;
    }

    public ILogiManager getLogiManager() {
        return logiManager;
    }

    public void setLogiManager(ILogiManager logiManager) {
        this.logiManager = logiManager;
    }

    public IDlyTypeManager getDlyTypeManager() {
        return dlyTypeManager;
    }

    public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
        this.dlyTypeManager = dlyTypeManager;
    }

    public IOrderNFlowManager getOrderNFlowManager() {
        return orderNFlowManager;
    }

    public void setOrderNFlowManager(IOrderNFlowManager orderNFlowManager) {
        this.orderNFlowManager = orderNFlowManager;
    }

    public List getItemList() {
        return itemList;
    }

    public void setItemList(List itemList) {
        this.itemList = itemList;
    }

    public DeliveryInf getDeliveryServ() {
        return deliveryServ;
    }

    public void setDeliveryServ(DeliveryInf deliveryServ) {
        this.deliveryServ = deliveryServ;
    }

    public String getLogi_id_name() {
        return logi_id_name;
    }

    public void setLogi_id_name(String logi_id_name) {
        this.logi_id_name = logi_id_name;
    }

    public IDeliveryFlow getDeliveryFlowManager() {
        return deliveryFlowManager;
    }

    public void setDeliveryFlowManager(IDeliveryFlow deliveryFlowManager) {
        this.deliveryFlowManager = deliveryFlowManager;
    }

    public DeliveryFlow getFlow() {
        return flow;
    }

    public void setFlow(DeliveryFlow flow) {
        this.flow = flow;
    }

    public String[] getGoods_idArray() {
        return goods_idArray;
    }

    public void setGoods_idArray(String[] goods_idArray) {
        this.goods_idArray = goods_idArray;
    }

    public String[] getGoods_nameArray() {
        return goods_nameArray;
    }

    public void setGoods_nameArray(String[] goods_nameArray) {
        this.goods_nameArray = goods_nameArray;
    }

    public String[] getGoods_snArray() {
        return goods_snArray;
    }

    public void setGoods_snArray(String[] goods_snArray) {
        this.goods_snArray = goods_snArray;
    }

    public String[] getProduct_idArray() {
        return product_idArray;
    }

    public void setProduct_idArray(String[] product_idArray) {
        this.product_idArray = product_idArray;
    }

    public Integer[] getNumArray() {
        return numArray;
    }

    public void setNumArray(Integer[] numArray) {
        this.numArray = numArray;
    }

    public String[] getGiftidArray() {
        return giftidArray;
    }

    public void setGiftidArray(String[] giftidArray) {
        this.giftidArray = giftidArray;
    }

    public String[] getGiftnameArray() {
        return giftnameArray;
    }

    public void setGiftnameArray(String[] giftnameArray) {
        this.giftnameArray = giftnameArray;
    }

    public Integer[] getGiftnumArray() {
        return giftnumArray;
    }

    public void setGiftnumArray(Integer[] giftnumArray) {
        this.giftnumArray = giftnumArray;
    }

    public String[] getItemid_idArray() {
        return itemid_idArray;
    }

    public void setItemid_idArray(String[] itemid_idArray) {
        this.itemid_idArray = itemid_idArray;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getHouse_id() {
        return house_id;
    }

    public void setHouse_id(String house_id) {
        this.house_id = house_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getS_delivery_id() {
        return s_delivery_id;
    }

    public void setS_delivery_id(String s_delivery_id) {
        this.s_delivery_id = s_delivery_id;
    }

}
