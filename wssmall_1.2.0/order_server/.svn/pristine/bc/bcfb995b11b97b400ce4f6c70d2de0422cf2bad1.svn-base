package zte.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.ZteError;
import params.member.req.MemberLvByIdReq;
import params.member.req.MemberPriceByPIdReq;
import services.MemberLvInf;
import services.MemberPriceInf;
import services.OrderInf;
import services.ProductInf;
import services.ServiceBase;
import zte.net.iservice.IOrderApplyService;
import zte.params.order.req.OrderAddReq;
import zte.params.order.req.OrderApplyAddReq;
import zte.params.order.req.OrderApplyPageListReq;
import zte.params.order.resp.OrderAddResp;
import zte.params.order.resp.OrderApplyAddResp;
import zte.params.order.resp.OrderApplyPageListResp;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.net.app.base.core.model.MemberLv;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.GoodsLvPrice;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderApply;
import com.ztesoft.net.mall.core.model.OrderApplyItem;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.model.OrderRel;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.service.IOrderApplyManage;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import commons.CommonTools;
import consts.ConstsCore;

public class OrderApplyService extends ServiceBase implements IOrderApplyService {

	@Resource
	private IOrderApplyManage orderApplyManage;
	@Resource
	private IOrderManager orderManager;
	@Resource
	private OrderInf orderServ;
	@Resource
	private ProductInf proudctServ;

	private MemberLvInf memberLvServ;
	private MemberPriceInf memberPriceServ;
	
	private void init(){
		if(null == memberLvServ) memberLvServ = ApiContextHolder.getBean("memberLvServ");
		if(null == memberPriceServ) memberPriceServ = ApiContextHolder.getBean("memberPriceServ");
	}
	
	@Override
	public OrderApplyPageListResp queryOrderApplyForPage(
			OrderApplyPageListReq req) {
		Page page = orderApplyManage.queryOrderApplyByType(req.getService_type(), req.getApply_id(), req.getOrder_id(),null, req.getPageNo(), req.getPageSize());
		OrderApplyPageListResp resp = new OrderApplyPageListResp();
		resp.setPage(page);
		resp.setUserSessionId(req.getUserSessionId());
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public OrderApplyAddResp addOrderApply(OrderApplyAddReq req) {
		//初始化bean
		init();
		
		OrderApplyAddResp oresp = new OrderApplyAddResp();
		if(!"edit".equals(req.getAction()) && orderApplyManage.orderIsOnApply(req.getOrder_id())){
			oresp.setError_code("1");
			oresp.setError_msg("该订单已存在申请单，不能重复申请，请进行修改");
			this.addReturn(req, oresp);
			return oresp;
		}
		OrderApply apply = req.getOrderApply();
		apply.setService_type(req.getService_type());
		double re_price = 0;
		double pay_price = 0;
		List<OrderRel> ors = new ArrayList<OrderRel>();
		
		OrderRel returndOr = null;
		Order order = orderManager.get(req.getOrder_id());
		
		for(OrderApplyItem item:req.getApplyItems()){
			if(OrderStatus.ORDER_TYPE_4.equals(item.getItem_type())){
				OrderItem oi = orderManager.getOrderItemByItemId(item.getOld_order_item_id());
				if(oi==null || oi.getShip_num()<item.getNum()){
					oresp.setError_code("2");
					oresp.setError_msg("申请退货的商品数量大于发货数量");
					this.addReturn(req, oresp);
					return oresp;
				}
				re_price += oi.getCoupon_price()*item.getNum();
				item.setGoods_id(oi.getGoods_id());
				item.setProduct_id(oi.getProduct_id());
				item.setPrice(oi.getCoupon_price());
				item.setName(oi.getName());
				if(returndOr==null){
					//封装退货关联表
					returndOr = new OrderRel();
					returndOr.setA_order_id(oi.getOrder_id());
					returndOr.setCreate_date("sysdate");
					returndOr.setRel_type(OrderStatus.ORDER_TYPE_4);
					returndOr.setComments("退货申请单");
					returndOr.setState(OrderStatus.ORDER_APPLY_REL_STATE_0);
					returndOr.setZ_table_name("ES_ORDER_APPLY");
				}
			}else if(OrderStatus.ORDER_TYPE_3.equals(item.getItem_type())){
				//换货
				Product product = proudctServ.get(item.getProduct_id());
				if(item.getPrice()==null){
					//计算会员价
					MemberLvByIdReq mlreq = new MemberLvByIdReq();
					mlreq.setMember_lv_id(req.getMember_lv_id());
					MemberLv lv =memberLvServ.getMemberLvById(mlreq).getMemberLv();
					double price =  product.getPrice();
					MemberPriceByPIdReq  mpreq = new MemberPriceByPIdReq();
					mpreq.setProduct_id(product.getProduct_id());
					mpreq.setMember_lv_id(req.getMember_lv_id());
					GoodsLvPrice gp = memberPriceServ.getPriceByPid(mpreq).getGoodsLvPrice();
					if(lv!=null){
						double discount = lv.getDiscount()/100.00;
						price = product.getPrice()*discount;
						if(gp!=null){
							price = gp.getPrice();
						}
					}
					item.setPrice(price);
					pay_price += price*item.getNum();
				}else{
					pay_price += item.getPrice()*item.getNum();
				}
				item.setGoods_id(product.getGoods_id());
				item.setProduct_id(product.getProduct_id());
				item.setName(product.getName());
			}
		}
		/**
		 * 计算退换货单支付价 正数为要退款给客户 负数为客户需要去付
		 */
		if(order.getOrder_amount()<re_price)re_price=order.getOrder_amount();
		double order_price = re_price - pay_price - apply.getDepreciation_price() - apply.getShip_price();
		apply.setRefund_value(re_price+"");
		apply.setMember_id(order.getMember_id());
		apply.setPay_price(order_price);
		apply.setA_order_item_id(req.getOrder_id());
		if("edit".equals(req.getAction())){
			orderApplyManage.updateOrderApply(apply);
			orderApplyManage.delApplyItems(apply.getOrder_apply_id());
		}else{
			orderApplyManage.addOrderApply(apply);
		}
		
		if(returndOr!=null){
			returndOr.setZ_order_id(apply.getOrder_apply_id());
			ors.add(returndOr);
		}
		
		/**
		 * 换货生成新的订单
		 */
		if("edit".equals(req.getAction())){
			List<OrderRel> list = orderApplyManage.queryOrderRelByApplyId(apply.getOrder_apply_id(), OrderStatus.ORDER_TYPE_3);
			if(list.size()>0){
				orderManager.cancel(list.get(0).getA_order_id());
			}
			orderApplyManage.delApplyOrderRel(apply.getOrder_apply_id());
		}
		//新增 (没改)改为审核时新增订单
		if(OrderStatus.ORDER_TYPE_3.equals(req.getService_type())){
			List<Map> mapList = new ArrayList<Map>();
			for(OrderApplyItem ci:req.getApplyItems()){
				if(OrderStatus.ORDER_TYPE_3.equals(ci.getItem_type())){
					Map map = new HashMap();
					if(order_price>0){
						map.put("p_order_amount", 0);
					}else{
						map.put("p_order_amount", (-order_price));
					}
					map.put("product_id", ci.getProduct_id());// 150 201310113208001326 //156
					map.put("goods_num", ci.getNum()+"");
					map.put("payment_id", req.getPayment_id());
					map.put("member_id", order.getMember_id());
					map.put("shipping_id", req.getShipping_id());
					map.put("ship_mobile", order.getShip_mobile());
					map.put("ship_addr", order.getShip_addr());
					map.put("ship_tel", order.getShip_tel());
					map.put("ship_zip", order.getShip_zip());
					map.put("ship_name", order.getShip_name());
					//发票信息
					map.put("invoice_content", order.getInvoice_content());
					map.put("invoice_title", order.getInvoice_title());
					map.put("invoice_title_desc", order.getInvoice_title_desc());
					map.put("invoice_type", order.getInvoice_type());
					map.put("ship_day", order.getShip_day());
					//map.put("price", ci.getPrice()+"");
					map.put("goods_name", ci.getName());
					map.put("member_lv_id", req.getMember_lv_id());
					map.put("order_type", OrderStatus.ORDER_TYPE_3);
					map.put("ship_amount", 0);
					map.put("source_from", ManagerUtils.getSourceFrom());
					mapList.add(map);
				}
			}
			OrderAddReq oreq = new OrderAddReq();
			oreq.setParamsl(mapList);
			oreq.setCreate_type("1");
			try {
				OrderAddResp resp = orderServ.createOuterOrder(oreq);
				if ("0".equals(resp.getError_code())) {
					List<Order> orders = resp.getOrderList();
					for(Order o:orders){
						OrderRel oro = new OrderRel();
						oro.setA_order_id(req.getOrder_id());
						oro.setCreate_date("sysdate");
						oro.setRel_type(OrderStatus.ORDER_TYPE_5);
						oro.setComments("退换货申请单");
						oro.setZ_order_id(apply.getOrder_apply_id());
						oro.setState(OrderStatus.ORDER_APPLY_REL_STATE_0);
						oro.setZ_table_name("ES_ORDER_APPLY");
						oro.setN_order_id(o.getOrder_id());
						ors.add(oro);
						
						OrderRel or = new OrderRel();
						or.setA_order_id(o.getOrder_id());
						or.setCreate_date("sysdate");
						or.setRel_type(OrderStatus.ORDER_TYPE_3);
						or.setComments("换货申请单");
						or.setZ_order_id(apply.getOrder_apply_id());
						or.setState(OrderStatus.ORDER_APPLY_REL_STATE_0);
						or.setZ_table_name("ES_ORDER_APPLY");
						ors.add(or);
						
						//设置新生成的订单itemid
						if(o.getOrderItemList()!=null){
							for(OrderItem oi:o.getOrderItemList()){
								for(OrderApplyItem item:req.getApplyItems()){
									if(OrderStatus.ORDER_TYPE_3.equals(item.getItem_type()) && oi.getProduct_id().equals(item.getProduct_id())){
										item.setOld_order_item_id(oi.getItem_id());
									}
								}
							}
						}
						//修改订单记录为无效记录  审核通过时改为有效记录
						orderManager.updateOrderDisabledStatus(o.getOrder_id(), 1);
					}
				}else{
					CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "创建订单失败"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "创建订单失败"));
			}
		}
		
		/**
		 * 插入关申请子表
		 */
		for(OrderApplyItem item:req.getApplyItems()){
			item.setApply_id(apply.getOrder_apply_id());
			orderApplyManage.saveOrderApplyItem(item);
		}
		/**
		 * 插入订单关系表
		 */
		for(OrderRel orl:ors){
			orderApplyManage.insertOrderApplyRel(orl);
		}
		
		//if(order_price>0){
			//需要退货，生成退款单
			//点退货按钮后再生成退款申请单
		//}
		oresp.setError_code("0");
		oresp.setError_msg("成功");
		this.addReturn(req, oresp);
		return oresp;
	}

}
