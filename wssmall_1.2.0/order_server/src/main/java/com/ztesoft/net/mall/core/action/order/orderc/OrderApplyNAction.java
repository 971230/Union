package com.ztesoft.net.mall.core.action.order.orderc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.drools.core.util.StringUtils;

import params.adminuser.req.AdminUserReq;
import params.adminuser.resp.AdminUserResp;
import params.bank.req.BankListReq;
import params.bank.resp.BankListResp;
import services.AdminUserInf;
import services.BankInf;
import services.GoodsInf;
import services.OrderInf;
import zte.net.iservice.IOrderApplyService;
import zte.params.order.req.OrderApplyAddReq;
import zte.params.order.req.OrderApplyPageListReq;
import zte.params.order.resp.OrderApplyAddResp;
import zte.params.order.resp.OrderApplyPageListResp;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.consts.OrderFlowConst;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderApply;
import com.ztesoft.net.mall.core.model.OrderApplyItem;
import com.ztesoft.net.mall.core.model.OrderQueryParam;
import com.ztesoft.net.mall.core.model.OrderRel;
import com.ztesoft.net.mall.core.service.IOrderApplyManage;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.service.IOrderFlowBussManager;
import com.ztesoft.net.mall.service.IOrderGroupManager;
import com.ztesoft.net.model.OrderFlow;
import com.ztesoft.net.model.OrderFlowDef;
import com.ztesoft.net.model.OrderGroup;

/**
 * 订单申请新action
 * @作者 MoChunrun
 * @创建日期 2014-2-18 
 * @版本 V 1.0
 */
public class OrderApplyNAction extends WWAction{

	private String service_type = OrderStatus.ORDER_TYPE_4;
	private String apply_id;
	private String order_id;
	
	/**
	 * 是否可修改 yes不可以修改
	 */
	private String isEdit = "no";
	private String action = "new";
	
	private List<Map> bankList;
	
	private OrderApply orderApply;
	private List<OrderApplyItem> applyItems;
	private List<OrderRel> applyOrderRels;
	private List<Map> orderItems;
	private String [] apply_proof;
	private String bank_account_qr;
	private String [] itemIdArray;
	private Integer [] return_num;
	private Double ship_price;
	private Double depreciation_price;
	
	@Resource
	private IOrderApplyService orderApplyService;
	@Resource
	private BankInf bankServ;
	@Resource
	private IOrderManager orderManager;
	@Resource
	private IOrderApplyManage orderApplyManage;
	@Resource
	private GoodsInf goodsServ;
	@Resource
	private OrderInf orderServ;
	
	private List<OrderApplyItem> exApplyItemList;
	
	private String [] ex_productArray;
	private Integer [] ex_num;
	
	private String goodsName;
	private String apply_status;
	
	private String [] flow_group_id;
	private String [] flow_user_id;
	private String flow_def_id;
	
	private AdminUser user;
	private AdminUserInf adminUserServ;
	private OrderGroup orderGroup;
	
	public void getConfirmAdmin(String user_id){
		if(!StringUtil.isEmpty(user_id)){
			try{
				AdminUserReq req = new AdminUserReq();
				req.setUser_id(user_id);
				AdminUserResp resp = adminUserServ.getAdminUserById(req);
				user = resp.getAdminUser();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	public String queryGoods(){
		webpage = this.goodsServ.listGoodsByUserId(goodsName, this.getPage(), this.getPageSize());
		return "apply_goods_list";
	}
	
	public String applyReturnedGoods(){
		try{
			if("audit".equals(action)){
				orderApplyManage.updateOrderApplyStatus(apply_id, apply_status);
				if("1".equals(apply_status)){
					if(OrderStatus.ORDER_TYPE_3.equals(service_type)){
						//把订单记录改为有效记录
						List<OrderRel> list = orderApplyManage.queryOrderRelByApplyId(apply_id, OrderStatus.ORDER_TYPE_3);
						if(list!=null && list.size()>0){
							orderManager.updateOrderDisabledStatus(list.get(0).getA_order_id(), 0);
						}
						//orderFlowBussManager.create(order_id, flow_user_id, flow_group_id,flow_def_id, OrderFlowConst.FLOW_SERVICE_TYPE_EXCHANGE);
						orderFlowBussManager.createOrderTodo(order_id, null, flow_def_id, "", 1, OrderFlowConst.FLOW_SERVICE_TYPE_EXCHANGE, "next");
					}else{
						//orderFlowBussManager.create(order_id, flow_user_id, flow_group_id,flow_def_id, OrderFlowConst.FLOW_SERVICE_TYPE_RETURNED);
						orderFlowBussManager.createOrderTodo(order_id, null, flow_def_id, "", 1, OrderFlowConst.FLOW_SERVICE_TYPE_RETURNED, "next");
					}
				}
				json = "{status:0,msg:'审核成功'}";
			}else{
				if(itemIdArray==null || itemIdArray.length==0){
					//没有选择商品
					json = "{status:1,msg:'没有选择要退货的商品'}";
				}else{
					if(apply_proof!=null && apply_proof.length>0){
						orderApply.setApply_proof(JSON.toJSONString(apply_proof));
					}
					orderApply.setOrder_apply_id(apply_id);
					orderApply.setRefund_type(OrderStatus.APLY_RETURN_TYPE_3);
					orderApply.setDepreciation_price(depreciation_price);
					orderApply.setShip_price(ship_price);
					orderApply.setApply_state(OrderStatus.ORDER_APPLY_REL_STATE_0);
					List<OrderApplyItem> ois = new ArrayList<OrderApplyItem>();
					for(int i=0;i<itemIdArray.length;i++){
						OrderApplyItem oi = new OrderApplyItem();
						oi.setItem_type(OrderStatus.ORDER_TYPE_4);
						oi.setOld_order_item_id(itemIdArray[i]);
						oi.setNum(return_num[i]);
						ois.add(oi);
					}
					if(OrderStatus.ORDER_TYPE_3.equals(service_type)){
						if(ex_productArray==null || ex_productArray.length==0){
							json = "{status:1,msg:'请选择要换货的商品'}";
							return JSON_MESSAGE;
						}
						for(int i=0;i<ex_productArray.length;i++){
							OrderApplyItem oi = new OrderApplyItem();
							oi.setItem_type(OrderStatus.ORDER_TYPE_3);
							oi.setNum(ex_num[i]);
							oi.setProduct_id(ex_productArray[i]);
							ois.add(oi);
						}
					}
					OrderApplyAddReq req = new OrderApplyAddReq();
					req.setService_type(service_type);
					req.setOrder_id(order_id);
					req.setApplyItems(ois);
					req.setOrderApply(orderApply);
					req.setPayment_id("2");
					req.setShipping_id("1");
					req.setAction(action);
					try{
						OrderApplyAddResp resp = orderApplyService.addOrderApply(req);
						json = "{status:"+resp.getError_code()+",msg:'"+resp.getError_msg()+"'}";
					}catch(Exception ex){
						json = "{status:1,msg:'申请失败["+ex.getMessage()+"]'}";
					}
				}
			}
			/*HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			try {
				response.getWriter().print(json);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{status:1,msg:'失败'}";
		}
		return JSON_MESSAGE;
	}
	
	/**
	 * 查询订单项
	 * @作者 MoChunrun
	 * @创建日期 2014-2-19 
	 * @return
	 */
	public String queryOrderItems(){
		order = orderManager.get(order_id);
		List<Map> itemList = this.orderManager.listGoodsItems(order_id); 
		//HttpServletResponse response = ServletActionContext.getResponse();
		//response.setCharacterEncoding("utf-8");
		json = JSON.toJSONString(itemList);
		json = "{\"order_amount\":"+order.getOrder_amount()+",items:"+json+"}";
		logger.info(json);
		/*try {
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return JSON_MESSAGE;
	}
	
	/**
	 * 选择订单
	 * @作者 MoChunrun
	 * @创建日期 2014-2-19 
	 * @return
	 */
	public String selectOrder(){
		OrderQueryParam ordParam = new OrderQueryParam();
		ordParam.setOrderId(order_id);
		ordParam.setAction("can_returned");
		this.webpage = this.orderManager.listGroupOrder(this.getPage(), this
                .getPageSize(), 0, ordParam, null);
		return "select_orders";
	}
	
	private String apply_proof_1 = "no";
	private String apply_proof_2 = "no";
	private List<OrderGroup> orderGroupList;
	private IOrderGroupManager orderGroupManagerImpl;
	private OrderFlow flow;
	private List<OrderFlowDef> flowDefList;
	private IOrderFlowBussManager orderFlowBussManager;
	private Order order;
	
	/**
	 * 显示添加或修改退货单
	 * @作者 MoChunrun
	 * @创建日期 2014-2-19 
	 * @return
	 */
	public String showReturnedDialog(){
		BankListResp resp = bankServ.qryBankList(new BankListReq());
		bankList = resp.getBankList();
		if(!"new".equals(action)){
			orderApply = orderApplyManage.getOrderApply(apply_id);
			applyItems = orderApplyManage.queryApplyItems(apply_id, null);
			//applyOrderRels = orderApplyManage.queryOrderRelByApplyId(apply_id, null);
			order_id = orderApply.getA_order_item_id();
			order = orderManager.get(order_id);
			List<Map> ois = orderManager.listGoodsItems(order_id);
			orderItems = new ArrayList<Map>();
			for(Map oi:ois){
				int ship_nun = Integer.parseInt(oi.get("ship_num").toString());
				//if(ship_nun<1){
				//	continue;
				//}
				for(OrderApplyItem oai:applyItems){
					if(OrderStatus.ORDER_TYPE_4.equals(oai.getItem_type()) && oi.get("item_id").toString().equals(oai.getOld_order_item_id())){
						oi.put("isChecked", "checked");
						oi.put("return_num", oai.getNum()==null?oi.get("ship_num"):oai.getNum());
					}
				}
				orderItems.add(oi);
			}
			if(!StringUtils.isEmpty(orderApply.getApply_proof())){
				apply_proof = (String[]) JSONArray.toArray(JSONArray.fromObject(orderApply.getApply_proof()), String.class);
				for(String s:apply_proof){
					if("1".equals(s)){
						apply_proof_1 = "yes";
					}
					if("2".equals(s)){
						apply_proof_2 = "yes";
					}
				}
			}
			
			if(OrderStatus.ORDER_TYPE_3.equals(service_type)){
				exApplyItemList = orderApplyManage.queryApplyItems(apply_id, OrderStatus.ORDER_TYPE_3);
			}
			
		}else{
			orderApply = new OrderApply();
			if(!StringUtils.isEmpty(order_id)){
				order = orderManager.get(order_id);
				orderApply.setRefund_value(order.getPaymoney()+"");
				orderApply.setPay_price(order.getPaymoney());
				List<Map> ois = orderManager.listGoodsItems(order_id);
				orderItems = new ArrayList<Map>();
				for(Map oi:ois){
					int ship_nun = Integer.parseInt(oi.get("ship_num").toString());
					if(ship_nun<1){
						continue;
					}
					oi.put("isChecked", "checked");
					oi.put("return_num", oi.get("ship_num"));
					orderItems.add(oi);
				}
			}
		}
		if("audit".equals(action) || (!OrderStatus.ORDER_APPLY_REL_STATE_0.equals(orderApply.getApply_state()) && !StringUtils.isEmpty(orderApply.getApply_state()))){
			isEdit = "yes";
			orderGroupList = orderGroupManagerImpl.listGroupByGroupType(null);
			String goodsid = "";
			if(applyItems!=null && applyItems.size()>0)goodsid = applyItems.get(0).getGoods_id();
			if(OrderStatus.ORDER_TYPE_3.equals(service_type)){
				flow = orderFlowBussManager.getOrderFlowByGoods(goodsid,OrderFlowConst.FLOW_SERVICE_TYPE_EXCHANGE);
				orderGroup = orderFlowBussManager.getFlowUserGroup(OrderFlowConst.FLOW_DEF_TYPE_EXCHANGE);
			}else{
				flow = orderFlowBussManager.getOrderFlowByGoods(goodsid,OrderFlowConst.FLOW_SERVICE_TYPE_RETURNED);
				orderGroup = orderFlowBussManager.getFlowUserGroup(OrderFlowConst.FLOW_DEF_TYPE_RETURNED);
			}
			if(order!=null){
				getConfirmAdmin(order.getShip_user_id());
				flowDefList = orderFlowBussManager.listOrderFlowDef(flow.getFlow_id(),true,order.getOrder_id());
			}else{
				flowDefList = orderFlowBussManager.listOrderFlowDef(flow.getFlow_id(),false,null);
			}
		}
		return "apply_returned_apply";
	}
	
	/**
	 * 查询申请单
	 * @作者 MoChunrun
	 * @创建日期 2014-2-18 
	 * @return
	 */
	public String queryApplyPage(){
		OrderApplyPageListReq req = new OrderApplyPageListReq();
		req.setService_type(service_type);
		req.setApply_id(apply_id);
		req.setOrder_id(order_id);
		req.setPageNo(getPage());
		req.setPageSize(getPageSize());
		OrderApplyPageListResp resp = orderApplyService.queryOrderApplyForPage(req);
		webpage = resp.getPage();
		return "apply_n_page";
	}
	
	public String getService_type() {
		return service_type;
	}
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	public String getApply_id() {
		return apply_id;
	}
	public void setApply_id(String apply_id) {
		this.apply_id = apply_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public List<Map> getBankList() {
		return bankList;
	}

	public void setBankList(List<Map> bankList) {
		this.bankList = bankList;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public OrderApply getOrderApply() {
		return orderApply;
	}

	public void setOrderApply(OrderApply orderApply) {
		this.orderApply = orderApply;
	}

	public String[] getApply_proof() {
		return apply_proof;
	}

	public void setApply_proof(String[] apply_proof) {
		this.apply_proof = apply_proof;
	}

	public String getBank_account_qr() {
		return bank_account_qr;
	}

	public void setBank_account_qr(String bank_account_qr) {
		this.bank_account_qr = bank_account_qr;
	}

	public String[] getItemIdArray() {
		return itemIdArray;
	}

	public void setItemIdArray(String[] itemIdArray) {
		this.itemIdArray = itemIdArray;
	}

	public Integer[] getReturn_num() {
		return return_num;
	}

	public void setReturn_num(Integer[] return_num) {
		this.return_num = return_num;
	}

	public Double getShip_price() {
		return ship_price;
	}

	public void setShip_price(Double ship_price) {
		this.ship_price = ship_price;
	}

	public Double getDepreciation_price() {
		return depreciation_price;
	}

	public void setDepreciation_price(Double depreciation_price) {
		this.depreciation_price = depreciation_price;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<OrderApplyItem> getApplyItems() {
		return applyItems;
	}

	public void setApplyItems(List<OrderApplyItem> applyItems) {
		this.applyItems = applyItems;
	}

	public List<OrderRel> getApplyOrderRels() {
		return applyOrderRels;
	}

	public void setApplyOrderRels(List<OrderRel> applyOrderRels) {
		this.applyOrderRels = applyOrderRels;
	}

	public List<Map> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<Map> orderItems) {
		this.orderItems = orderItems;
	}

	public String getApply_proof_1() {
		return apply_proof_1;
	}

	public void setApply_proof_1(String apply_proof_1) {
		this.apply_proof_1 = apply_proof_1;
	}

	public String getApply_proof_2() {
		return apply_proof_2;
	}

	public void setApply_proof_2(String apply_proof_2) {
		this.apply_proof_2 = apply_proof_2;
	}

	public List<OrderApplyItem> getExApplyItemList() {
		return exApplyItemList;
	}

	public void setExApplyItemList(List<OrderApplyItem> exApplyItemList) {
		this.exApplyItemList = exApplyItemList;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String[] getEx_productArray() {
		return ex_productArray;
	}

	public void setEx_productArray(String[] ex_productArray) {
		this.ex_productArray = ex_productArray;
	}

	public Integer[] getEx_num() {
		return ex_num;
	}

	public void setEx_num(Integer[] ex_num) {
		this.ex_num = ex_num;
	}

	public String getApply_status() {
		return apply_status;
	}

	public void setApply_status(String apply_status) {
		this.apply_status = apply_status;
	}

	public IOrderApplyService getOrderApplyService() {
		return orderApplyService;
	}

	public void setOrderApplyService(IOrderApplyService orderApplyService) {
		this.orderApplyService = orderApplyService;
	}

	public BankInf getBankServ() {
		return bankServ;
	}

	public void setBankServ(BankInf bankServ) {
		this.bankServ = bankServ;
	}

	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public IOrderApplyManage getOrderApplyManage() {
		return orderApplyManage;
	}

	public void setOrderApplyManage(IOrderApplyManage orderApplyManage) {
		this.orderApplyManage = orderApplyManage;
	}

	public GoodsInf getGoodsServ() {
		return goodsServ;
	}

	public void setGoodsServ(GoodsInf goodsServ) {
		this.goodsServ = goodsServ;
	}

	public List<OrderGroup> getOrderGroupList() {
		return orderGroupList;
	}

	public void setOrderGroupList(List<OrderGroup> orderGroupList) {
		this.orderGroupList = orderGroupList;
	}

	public IOrderGroupManager getOrderGroupManagerImpl() {
		return orderGroupManagerImpl;
	}

	public void setOrderGroupManagerImpl(IOrderGroupManager orderGroupManagerImpl) {
		this.orderGroupManagerImpl = orderGroupManagerImpl;
	}

	public OrderFlow getFlow() {
		return flow;
	}

	public void setFlow(OrderFlow flow) {
		this.flow = flow;
	}

	public List<OrderFlowDef> getFlowDefList() {
		return flowDefList;
	}

	public void setFlowDefList(List<OrderFlowDef> flowDefList) {
		this.flowDefList = flowDefList;
	}

	public IOrderFlowBussManager getOrderFlowBussManager() {
		return orderFlowBussManager;
	}

	public void setOrderFlowBussManager(IOrderFlowBussManager orderFlowBussManager) {
		this.orderFlowBussManager = orderFlowBussManager;
	}

	public String[] getFlow_group_id() {
		return flow_group_id;
	}

	public void setFlow_group_id(String[] flow_group_id) {
		this.flow_group_id = flow_group_id;
	}

	public String[] getFlow_user_id() {
		return flow_user_id;
	}

	public void setFlow_user_id(String[] flow_user_id) {
		this.flow_user_id = flow_user_id;
	}

	public String getFlow_def_id() {
		return flow_def_id;
	}

	public void setFlow_def_id(String flow_def_id) {
		this.flow_def_id = flow_def_id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public AdminUser getUser() {
		return user;
	}

	public void setUser(AdminUser user) {
		this.user = user;
	}

	public AdminUserInf getAdminUserServ() {
		return adminUserServ;
	}

	public void setAdminUserServ(AdminUserInf adminUserServ) {
		this.adminUserServ = adminUserServ;
	}

	public OrderGroup getOrderGroup() {
		return orderGroup;
	}

	public void setOrderGroup(OrderGroup orderGroup) {
		this.orderGroup = orderGroup;
	}
	
}
