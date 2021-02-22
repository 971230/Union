/**
 * 
 */
package com.ztesoft.net.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.iservice.IOrderServices;
import zte.params.order.req.DeliveryItemsQueryReq;
import zte.params.order.req.ReissueGoodsShippingAddReq;
import zte.params.order.resp.DeliveryItemsQueryResp;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Logi;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.model.OrderQueryParams;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrderExtManager;
import com.ztesoft.net.service.IOrderSupplyManager;

/**
 * 
 * @author
 * ZX OrderSupplyAction.java
 * 订单补寄和重发
 * 2014-10-30
 * 
 */
public class OrderSupplyAction extends WWAction {
	private static final long serialVersionUID = 1L;

	@Resource
	private IOrderExtManager orderExtManager;
	@Resource
	private IEcsOrdManager ecsOrdManager;
	@Resource
	private IOrderServices orderServices;
	@Resource
	private IDcPublicInfoManager dcPublicInfoManager;
	

	
	

	private String order_id;
	private List<OrderDeliveryBusiRequest> deliveryLs;
	private OrderTreeBusiRequest orderTree;
	private List<Logi> logiCompanyList; // 物流公司
	private IOrderSupplyManager iOrderSupplyManager;
	private DeliveryItemsQueryResp deliveryItemsQueryResp; // 物流子弹
	private String deliveri_item_idArray; // 物流子弹ID（,分割）
	private String delivery_id; // 物流表主键

	/**
	 * 物流属性
	 */
	private String shipping_company; // 物流公司
	private String logi_receiver_phone; // 物流电话
	private String protect_price; // 保费
	private String n_shipping_amount; // 运费
	private String logi_no; // 物流单号
	private String logi_receiver; // 物流联系人

	
	private String order_list_is_his; //1-查询归档的订单，0-查询还没归档的订单
	
	private OrderQueryParams params;
	private String order_is_his;//1-是历史单（重发、补寄时用到、从列表传送过来）
	private String post_type;//补寄、重发
	
	
	private List<Map> order_from_list;
	private List<Map> is_quick_ship_list;
	private List<Map> dc_MODE_REGIONList;
	
	
	
	/**
	 * 订单补寄和重发
	 * 
	 * @return
	 */
	public String order_supply() {

		
		if(!StringUtil.isEmpty(order_is_his)&&order_is_his.equals(EcsOrderConsts.IS_ORDER_HIS_YES)){//历史单
			
			deliveryLs = iOrderSupplyManager.order_supply(order_id,true);//his
			orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
			List<OrderDeliveryItemBusiRequest> list = iOrderSupplyManager.queryDeliveryItems(order_id,true); 
			if(list==null||list.size()==0){
				list=new ArrayList<OrderDeliveryItemBusiRequest>();
			}
			deliveryItemsQueryResp=new DeliveryItemsQueryResp();
			deliveryItemsQueryResp.setDeliveryItems(list);
			
		}else{
			deliveryLs = iOrderSupplyManager.order_supply(order_id,false);
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			DeliveryItemsQueryReq req = new DeliveryItemsQueryReq();
			req.setOrder_id(order_id);
			deliveryItemsQueryResp = orderServices.queryDeliveryItems(req);
		}
		
		logiCompanyList = ecsOrdManager.logi_company(order_id);
		

		return "order_supply";
	}

	/**
	 * 保存补寄打印
	 * 历史订单则保存到his表，后面用sql查询
	 * @return
	 */
	public String order_supply_savePrint() {

		String count = orderExtManager.getOrderCountByLogino(logi_no, "1");
		if(!"0".equals(count)){//物流单号已被使用过
			json = "{'result':1,'message':'物流单号"+logi_no+"已被使用过！'}";
			return JSON_MESSAGE;
		}
		try {
            if(!StringUtil.isEmpty(order_is_his)&&order_is_his.equals(EcsOrderConsts.IS_ORDER_HIS_YES)){//历史单
            	String[] deliveri_item_idArrays = {};
            	if(post_type!=null&&post_type.equals(EcsOrderConsts.LOGIS_SUPPLY)){//补寄
            		if (StringUtils.isNotBlank(deliveri_item_idArray)) {
        				deliveri_item_idArrays = deliveri_item_idArray.split(",");
        			}
            	}
            	orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
            	OrderDeliveryBusiRequest orderDeliveryBusiRequest = new OrderDeliveryBusiRequest();
    			List<OrderDeliveryBusiRequest> list=orderTree.getOrderDeliveryBusiRequests();
    			
    			for (OrderDeliveryBusiRequest de : list) {
					if(EcsOrderConsts.DELIVERY_TYPE_NORMAL.equals(de.getDelivery_type())){//正常单的物流信息
						orderDeliveryBusiRequest=de;
					}
				}

    			orderDeliveryBusiRequest.setDelivery_id(iOrderSupplyManager.getSequences("S_ES_DELIVERY"));
    			orderDeliveryBusiRequest.setShipping_company(shipping_company);
    			orderDeliveryBusiRequest.setLogi_receiver_phone(logi_receiver_phone);
    			orderDeliveryBusiRequest.setN_shipping_amount(n_shipping_amount);
    			orderDeliveryBusiRequest.setLogi_no(logi_no);
    			orderDeliveryBusiRequest.setProtect_price(Double.parseDouble(protect_price));
    			orderDeliveryBusiRequest.setLogi_receiver(logi_receiver);
    			orderDeliveryBusiRequest.setDelivery_type(post_type);
    			orderDeliveryBusiRequest.setCreate_time(DateUtil.getTime2());
    			orderDeliveryBusiRequest.setShip_status(OrderStatus.DELIVERY_SHIP_SATUS_NOT);//未发货
    			orderDeliveryBusiRequest.setReceipt_no("");//回单号设为空
    			orderDeliveryBusiRequest.setSign_status("");//签收状态设为空
    			orderDeliveryBusiRequest.setReceipt_status("");//回单状态设为空
    			iOrderSupplyManager.addReissueGoodsShippingDelivery(orderDeliveryBusiRequest,deliveri_item_idArrays,true);
              
            }else{
            	String[] deliveri_item_idArrays = {};
    			ReissueGoodsShippingAddReq req = new ReissueGoodsShippingAddReq();
            	if(post_type!=null&&post_type.equals(EcsOrderConsts.LOGIS_SUPPLY)){
            		if (StringUtils.isNotBlank(deliveri_item_idArray)) {
        				deliveri_item_idArrays = deliveri_item_idArray.split(",");
        				req.setDeliveri_item_idArray(deliveri_item_idArrays);
        			}
            	}else{
            		req.setDeliveri_item_idArray(deliveri_item_idArrays);
            	}
            	//CommonDataFactory.getInstance().updateOrderTree(order_id);
    			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
    			
    			//n_shipping_amount = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "n_shipping_amount");//是哪条记录
    			//logi_no = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "logi_no");
    			
    			OrderDeliveryBusiRequest orderDeliveryBusiRequest = new OrderDeliveryBusiRequest();
    			List<OrderDeliveryBusiRequest> list=orderTree.getOrderDeliveryBusiRequests();
    			
    			for (OrderDeliveryBusiRequest de : list) {
					if(EcsOrderConsts.DELIVERY_TYPE_NORMAL.equals(de.getDelivery_type())){//正常单
						orderDeliveryBusiRequest=de;
					}
				}
    			
    			orderDeliveryBusiRequest.setDelivery_id(iOrderSupplyManager.getSequences("S_ES_DELIVERY"));
    			orderDeliveryBusiRequest.setShipping_company(shipping_company);
    			orderDeliveryBusiRequest.setLogi_receiver_phone(logi_receiver_phone);
    			orderDeliveryBusiRequest.setN_shipping_amount(n_shipping_amount);
    			orderDeliveryBusiRequest.setLogi_no(logi_no);
    			orderDeliveryBusiRequest.setProtect_price(Double.parseDouble(protect_price));
    			orderDeliveryBusiRequest.setLogi_receiver(logi_receiver);
    			orderDeliveryBusiRequest.setDelivery_type(post_type);
    			orderDeliveryBusiRequest.setCreate_time(DateUtil.getTime2());
    			orderDeliveryBusiRequest.setShip_status(OrderStatus.DELIVERY_SHIP_SATUS_NOT);//未发货
    			orderDeliveryBusiRequest.setReceipt_no("");//回单号设为空
    			orderDeliveryBusiRequest.setSign_status("");//签收状态设为空
    			orderDeliveryBusiRequest.setReceipt_status("");//回单状态设为空
    			//String[] field_name = new String[]{"shipping_company", "n_shipping_amount", "logi_no"};
    			//String[] field_value = new String[]{shipping_company, n_shipping_amount, logi_no};			
    			//CommonDataFactory.getInstance().updateAttrFieldValue(order_id, field_name, field_value);
    			
    			req.setDelivery(orderDeliveryBusiRequest);
    			orderServices.addReissueGoodsShippingDelivery(req);
                //更新订单树
    			CommonDataFactory.getInstance().updateOrderTree(order_id);
            }
			
			json = "{'result':0,'message':'操作成功！'}";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json = "{'result':1,'message':'操作失败！'}";
		}
		return JSON_MESSAGE;
	}

	/**
	 * 更新重发补寄物流单的发货状态
	 * 
	 * @return
	 */
	public String updateDeliveryStatus() {
		try {
			if(!StringUtil.isEmpty(order_is_his)&&order_is_his.equals(EcsOrderConsts.IS_ORDER_HIS_YES)){
				iOrderSupplyManager.updateDeliveryStatus(delivery_id,true);
				
			}else{
				iOrderSupplyManager.updateDeliveryStatus(delivery_id,false);
				//更新订单树
				CommonDataFactory.getInstance().updateOrderTree(order_id);
			}
		
		 
		json = "{'result':0,'message':'操作成功！'}";
	} catch (Exception e) {
		e.printStackTrace();
		json = "{'result':1,'message':'操作失败！'}";
	}
	return JSON_MESSAGE;
	}
	

	
	
	
	

	
	/**
	 * 订单来源查询条件
	 * @作者 MoChunrun
	 * @创建日期 2014-10-9
	 */
	private void listOrderFrom(){
		order_from_list = getConsts(StypeConsts.SOURCE_FROM);
		if(order_from_list==null){
			order_from_list = new ArrayList<Map>();
		}
	}
	/**
	 * 归属区域查询条件
	 */
	private void listDcModeRegion() {
		dc_MODE_REGIONList = ecsOrdManager.getDcSqlByDcName("DC_MODE_REGION");
		if(dc_MODE_REGIONList==null){
			dc_MODE_REGIONList = new ArrayList<Map>();
		}
		Map m0 = new HashMap();
		m0.put("value", "-1");
		m0.put("value_desc", "--请选择--");
		dc_MODE_REGIONList.add(0,m0);
	}
	/**
	 * 是否闪电送
	 * @作者 MoChunrun
	 * @创建日期 2014-10-9
	 */
	private void listIsQuickShip(){
		is_quick_ship_list = getConsts(StypeConsts.SHIPPING_QUICK);
		if(is_quick_ship_list==null){
			is_quick_ship_list = new ArrayList<Map>();
		}
		Map m0 = new HashMap();
		m0.put("pkey", "-1");
		m0.put("pname", "--请选择--");
		is_quick_ship_list.add(0, m0);
	}
	
	private List<Map> getConsts(String key){
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
        List<Map> list = dcPublicCache.getList(key);
        return list;
	}
	

	

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public IOrderSupplyManager getiOrderSupplyManager() {
		return iOrderSupplyManager;
	}

	public void setiOrderSupplyManager(IOrderSupplyManager iOrderSupplyManager) {
		this.iOrderSupplyManager = iOrderSupplyManager;
	}

	public List<OrderDeliveryBusiRequest> getDeliveryLs() {
		return deliveryLs;
	}

	public void setDeliveryLs(List<OrderDeliveryBusiRequest> deliveryLs) {
		this.deliveryLs = deliveryLs;
	}

	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}

	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}

	public List<Logi> getLogiCompanyList() {
		return logiCompanyList;
	}

	public void setLogiCompanyList(List<Logi> logiCompanyList) {
		this.logiCompanyList = logiCompanyList;
	}

	public IEcsOrdManager getEcsOrdManager() {
		return ecsOrdManager;
	}

	public void setEcsOrdManager(IEcsOrdManager ecsOrdManager) {
		this.ecsOrdManager = ecsOrdManager;
	}

	public IOrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(IOrderServices orderServices) {
		this.orderServices = orderServices;
	}

	public DeliveryItemsQueryResp getDeliveryItemsQueryResp() {
		return deliveryItemsQueryResp;
	}

	public void setDeliveryItemsQueryResp(
			DeliveryItemsQueryResp deliveryItemsQueryResp) {
		this.deliveryItemsQueryResp = deliveryItemsQueryResp;
	}

	public String getDeliveri_item_idArray() {
		return deliveri_item_idArray;
	}

	public void setDeliveri_item_idArray(String deliveri_item_idArray) {
		this.deliveri_item_idArray = deliveri_item_idArray;
	}

	public String getProtect_price() {
		return protect_price;
	}

	public void setProtect_price(String protect_price) {
		if (StringUtils.isBlank(protect_price))
			this.protect_price = "0";
		else
			this.protect_price = protect_price;
	}

	public String getN_shipping_amount() {
		return n_shipping_amount;
	}

	public void setN_shipping_amount(String n_shipping_amount) {
		this.n_shipping_amount = n_shipping_amount;
	}

	public String getShipping_company() {
		return shipping_company;
	}

	public void setShipping_company(String shipping_company) {
		this.shipping_company = shipping_company;
	}

	public String getLogi_receiver_phone() {
		return logi_receiver_phone;
	}

	public void setLogi_receiver_phone(String logi_receiver_phone) {
		this.logi_receiver_phone = logi_receiver_phone;
	}

	public String getLogi_no() {
		return logi_no;
	}

	public void setLogi_no(String logi_no) {
		this.logi_no = logi_no;
	}

	public String getLogi_receiver() {
		return logi_receiver;
	}

	public void setLogi_receiver(String logi_receiver) {
		this.logi_receiver = logi_receiver;
	}

	public String getDelivery_id() {
		return delivery_id;
	}

	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}


	public String getOrder_list_is_his() {
		return order_list_is_his;
	}


	public void setOrder_list_is_his(String order_list_is_his) {
		this.order_list_is_his = order_list_is_his;
	}


	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}


	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}


	public OrderQueryParams getParams() {
		return params;
	}


	public void setParams(OrderQueryParams params) {
		this.params = params;
	}


	public List<Map> getOrder_from_list() {
		return order_from_list;
	}


	public void setOrder_from_list(List<Map> order_from_list) {
		this.order_from_list = order_from_list;
	}


	public List<Map> getIs_quick_ship_list() {
		return is_quick_ship_list;
	}


	public void setIs_quick_ship_list(List<Map> is_quick_ship_list) {
		this.is_quick_ship_list = is_quick_ship_list;
	}


	public List<Map> getDc_MODE_REGIONList() {
		return dc_MODE_REGIONList;
	}


	public void setDc_MODE_REGIONList(List<Map> dc_MODE_REGIONList) {
		this.dc_MODE_REGIONList = dc_MODE_REGIONList;
	}


	public String getOrder_is_his() {
		return order_is_his;
	}


	public void setOrder_is_his(String order_is_his) {
		this.order_is_his = order_is_his;
	}


	public String getPost_type() {
		return post_type;
	}


	public void setPost_type(String post_type) {
		this.post_type = post_type;
	}


	



	
	
}
