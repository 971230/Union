package com.ztesoft.net.mall.core.action.backend;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import params.cart.req.CartAddReq;
import params.cart.req.CartDelReq;
import params.cart.req.CartListReq;
import params.cart.req.CartUpdateReq;
import params.cart.resp.CartAddResp;
import params.cart.resp.CartListResp;
import params.goods.req.GoodsListReq;
import params.member.req.MemberLevelListReq;
import params.member.req.MemberLvQryReq;
import params.member.req.MemberQryPageReq;
import params.member.resp.MemberLevelListResp;
import params.member.resp.MemberQryResp;
import params.order.req.DlyTypeReq;
import params.order.req.OrderTotalReq;
import params.order.req.PaymentListReq;
import params.order.resp.PaymentListResp;
import params.regions.req.RegionChildrenJsonReq;
import params.regions.req.RegionCityListByProvinceReq;
import params.regions.req.RegionListByCityReq;
import params.regions.req.RegionProvinceListReq;
import params.regions.resp.RegionChildrenJsonResp;
import params.regions.resp.RegionCityListByProvinceResp;
import params.regions.resp.RegionListByCityResp;
import params.regions.resp.RegionProvinceListResp;
import services.GoodsInf;
import services.ICartService;
import services.MemberInf;
import services.OrderInf;
import services.RegionsInf;
import zte.net.iservice.IOrderServices;
import zte.params.member.req.MemberIsExistsReq;
import zte.params.member.resp.MemberIsExistsResp;
import zte.params.order.req.OrderStandardPushReq;
import zte.params.order.resp.OrderStandardPushResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.app.base.core.model.MemberLv;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Cart;
import com.ztesoft.net.mall.core.model.DlyType;
import com.ztesoft.net.mall.core.model.PayCfg;
import com.ztesoft.net.mall.core.model.Regions;
import com.ztesoft.net.mall.core.model.support.CartItem;
import com.ztesoft.net.mall.core.model.support.OrderPrice;
import com.ztesoft.net.mall.core.service.ICartManager;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.model.OuterItem;

/**
 * 订单授理
 * 
 * @作者 MoChunrun
 * @创建日期 2013-10-28
 * @版本 V 1.0
 */
public class OrderAcceptAction extends WWAction {
 
	private MemberInf memberServ;
	private GoodsInf goodsServ;
	private OrderInf orderServ;
	private List<PayCfg> paymentList;
	private String uname;
	private Page memberPage;
	private List<MemberLv> memberLvList;
	private String member_id;

	private Page goodsPage;
	private String goodsName;
	private String member_lv_id;

	private ICartManager cartManager;

	private List<Regions> provinces;
	private List citys;
	private List regions;
	private String parentid;
	private String regionid;
	private List<DlyType> dlyTypeList;
	private String dlytype_id;

	private String[] productidArray;
	private ICartService cartServ;

	private String[] cartidArray;
	private List<CartItem> cartList;

	private String cartId;
	private String num;

	private OrderPrice orderPrice;

	private String province_id;// id与名称用|分隔
	private String city_id;// id与名称用|分隔
	private String region_id;// id与名称用|分隔
	private String payment_id;
	private String shipMobile;
	private String shipName;
	private String shipAddr;
	private String shipZip;
	private String remark;
	private IOrderServices orderServices;

	private RegionsInf regionsServ;

	public String createOrder() {
	        
		String json = "";
		try {
			 /*OrderAddReq orderAddReq = new OrderAddReq();
			 CartListReq req = new CartListReq();
			 req.setMember_id(member_id);
			 req.setUserSessionId(member_id);
			 CartListResp csp = cartServ.list(req);
			 AdminUser adminUser = ManagerUtils.getAdminUser();
			 List<CartItem> cartList = csp.getGoodsItemList();
			 List<Map> mapList = new ArrayList<Map>();
			 for(CartItem c:cartList){
				 Map map = new HashMap();
				 map.put("member_id", member_id);
				 map.put("userid", adminUser.getUserid());
				 map.put("product_id", c.getProduct_id());
				 map.put("goods_num", c.getNum()+"");
				 map.put("payment_id", payment_id);
				 map.put("shipping_id", dlytype_id);
				 map.put("ship_mobile", shipMobile);
				 map.put("ship_name", shipName);
				 map.put("ship_addr", shipAddr);
				 map.put("acc_nbr", shipMobile);
				 map.put("ship_zip", shipZip);
				 map.put("remark", remark);
				 map.put("sex", "1");
				 if(province_id!=null){
					 String [] ss = province_id.split("\\|");
					 map.put("province_id", ss[0]);
					 map.put("province", ss[1]);
				 }
				 if(city_id!=null){
					 String [] ss = city_id.split("\\|");
					 map.put("city_id", ss[0]);
					 map.put("city", ss[1]);
				 }
				 if(region_id!=null){
					 String [] ss = region_id.split("\\|");
					 map.put("region_id", ss[0]);
					 map.put("region", ss[1]);
				 }
				 mapList.add(map);
			 }
			 orderAddReq.setParamsl(mapList);

			orderAddReq.setService_code("3");
			OrderAddResp resp = orderServices.addOrder(orderAddReq);*/
			
			
			logger.info("=============订单推送start=========================");
			OrderStandardPushReq areq = new OrderStandardPushReq();
			Outer outer = new Outer();
			outer.setPay_method(payment_id);
			outer.setPaytype("ZXZF");
			outer.setSending_type(dlytype_id);
			outer.setBuyer_name(shipName);
			outer.setAddress(shipAddr);
			outer.setReceiver_name(shipName);
			outer.setReceiver_mobile(shipMobile);
			outer.setPost(shipZip);
			outer.setBuyer_message(remark);
			outer.setOrder_from(EcsOrderConsts.ORDER_FROM_10000);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			outer.setTid_time(df.format(new Date()));
			
			if(province_id!=null){
				 String [] ss = province_id.split("\\|");
				 outer.setProvinc_code(ss[0]);
				 outer.setProvince(ss[1]);
			 }
			 if(city_id!=null){
				 String [] ss = city_id.split("\\|");
				 outer.setCity_code(ss[0]);
				 outer.setCity(ss[1]);
			 }
			 if(region_id!=null){
				 String [] ss = region_id.split("\\|");
				 outer.setArea_code(ss[0]);
				 outer.setDistrict(ss[1]);
			 }
			
			CartListReq req = new CartListReq();
			req.setMember_id(member_id);
			req.setUserSessionId(member_id);
			CartListResp csp = cartServ.list(req);
			List<CartItem> cartList = csp.getGoodsItemList();
			List<OuterItem> oiList = new ArrayList<OuterItem>();
			for(CartItem c:cartList){
				OuterItem oi = new OuterItem();
				oiList.add(oi);
				outer.setItemList(oiList);
				oi.setProduct_id(c.getProduct_id());
				oi.setGoods_id(c.getGoods_id());
				oi.setPro_num(c.getNum());
			}
			
			List<Outer> outerList = new ArrayList<Outer>();
			outerList.add(outer);
			areq.setOuterList(outerList);
			OrderStandardPushResp aresp = orderServices.pushOrderStandard(areq);
			logger.info("=============订单推送end=========================");
			if ("0".equals(aresp.getError_code())) {
				json = "{status:0,msg:'成功'}";
				cartManager.clean(member_id, false);
			} else {
				json = "{status:1,msg:'失败'}";
			}
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

	/**
	 * 显示添加订单页面
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2013-10-28
	 * @return
	 */
	public String showAddOrder() {
		PaymentListResp resp = orderServ.qryPaymetList(new PaymentListReq());
		paymentList = resp.getPaymentList();

		RegionProvinceListReq req = new RegionProvinceListReq();
		RegionProvinceListResp rResp = regionsServ.getProvinceList(req);
		if (resp != null) {
			provinces = rResp.getProvince_list();
		}

//		RegionCityListByProvinceReq req_1 = new RegionCityListByProvinceReq();
//		req_1.setProvince_id(new Long(provinces.get(0).getRegion_id())
//				.longValue());
//		RegionCityListByProvinceResp resp_1 = regionsServ
//				.getCityListByProvinceId(req_1);
//		if (resp_1 != null) {
//			citys = resp_1.getChild_list();
//		}

		// regions =
		// regionsManager.listCity(Long.parseLong(citys.get(0).getRegion_id()));
		return "addorder";
	}

	public String listCart() {
		String json = "{status:0,msg:'成功',carts:[";
		if (member_id != null && !"".equals(member_id)) {
			CartListReq req = new CartListReq();
			req.setMember_id(member_id);
			req.setUserSessionId(member_id);
			CartListResp csp = cartServ.list(req);
			cartList = csp.getGoodsItemList();
			if (cartList != null && cartList.size() > 0) {
				for (CartItem cart : cartList) {
					json += "{cartid:'" + cart.getId() + "',productName:'"
							+ cart.getName() + "',price:'" + cart.getPrice()
							+ "'},";
				}
				json = json.substring(0, json.length() - 1);
			}
		}
		json += "]}";
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 更新数量
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2013-10-30
	 * @return
	 */
	public String updateCartNum() {
		try {
			CartUpdateReq req = new CartUpdateReq();
			req.setUserSessionId(member_id);
			req.setAction("num");
			List<CartUpdateReq.Cart> cartList = new ArrayList<CartUpdateReq.Cart>();
			CartUpdateReq.Cart c = new CartUpdateReq.Cart();
			c.setCart_id(cartId);
			c.setNum(num);
			cartList.add(c);
			req.setCartList(cartList);
			cartServ.update(req);
			json = "{status:0,msg:'成功'}";
		} catch (Exception ex) {
			ex.printStackTrace();
			json = "{status:2,msg:'失败'}";
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

	/**
	 * 获取订单总额
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2013-10-30
	 * @return
	 */
	public String getOrderTotal() {
		OrderTotalReq req = new OrderTotalReq();
		req.setDlyType_id(dlytype_id);
		req.setRegion_id(regionid);
		req.setUserSessionId(member_id);
		req.setMember_id(member_id);
		req.setIsProtected("0");
		orderPrice = orderServ.showOrderTotal(req).getOrderPrice();
		return "orderTotal";
	}

	/**
	 * 清空购物车
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2013-10-30
	 * @return
	 */
	public String clear() {
		String json = "";
		try {
			MemberLvQryReq req = new MemberLvQryReq();
			req.setUserSessionId(member_id);
			cartServ.clear(req);
			json = "{status:0,msg:'成功'}";
		} catch (Exception ex) {
			ex.printStackTrace();
			json = "{status:2,msg:'失败'}";
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

	/**
	 * 查询会员
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2013-10-28
	 * @return
	 */
	public String qryMember() {
		MemberQryPageReq req = new MemberQryPageReq();
		req.setPageNo(this.getPage());
		req.setPageSize(this.getPageSize());
		req.setUname(uname);
		MemberQryResp resp = memberServ.qryMemberList(req);
		memberPage = resp.getMemberPage();
		return "memberlist";
	}

	/**
	 * 按会员ID查询会员所有等级
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2013-10-28
	 * @return
	 */
	public String qryMemberLv() {
		MemberLevelListReq req = new MemberLevelListReq();
		req.setMember_id(member_id);
		MemberLevelListResp resp = memberServ.qryMemberLvList(req);
		memberLvList = resp.getMemberLvList();
		return "memberLvList";
	}

	/**
	 * 查询商品列表
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2013-10-29
	 * @return
	 */
	public String qryGoods() {
		GoodsListReq req = new GoodsListReq();
		req.setMember_lv_id(member_lv_id);
		req.setKeyword(goodsName);
		req.setPageNo(getPage());
		req.setPageSize(getPageSize());
		goodsPage = goodsServ.qryGoodsList(req).getGoodsPage();
		return "goodslist";
	}

	public String addCart() {
		String json = "{status:0,msg:'成功',carts:[";
		try {
			if (productidArray != null && productidArray.length > 0) {
				for (String prodictid : productidArray) {
					CartAddReq req = new CartAddReq();
					req.setMember_id(member_id);
					req.setUserSessionId(member_id);
					req.setNum("1");
					req.setProduct_id(prodictid);
					req.setMember_lv_id(member_lv_id);
					CartAddResp resp = cartServ.add(req);
					Cart cart = resp.getCart();
					json += "{cartid:'" + cart.getCart_id() + "',productName:'"
							+ cart.getName() + "',price:'" + cart.getPrice()
							+ "'},";
				}
				json = json.substring(0, json.length() - 1);
				json += "]}";
			} else {
				json = "{status:1,msg:'商品不能为空'}";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			json = "{status:2,msg:'失败'}";
		}
		// logger.info(JSONObject.fromObject(json));
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String delCart() {
		String json = "";
		try {
			CartDelReq req = new CartDelReq();
			req.setUserSessionId(member_id);
			req.setCart_ids(cartidArray);
			cartServ.delete(req);
			json = "{status:0,msg:'成功'}";
		} catch (Exception ex) {
			ex.printStackTrace();
			json = "{status:2,msg:'失败'}";
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

	/**
	 * 查询配送方式
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2013-10-29
	 * @return
	 */
	public String qryDlyType() {
		DlyTypeReq req = new DlyTypeReq();
		req.setUserSessionId(member_id);
		req.setRegion_id(regionid);
		dlyTypeList = orderServ.showDlyType(req).getDlyTypeList();
		return "dlyType";
	}

	public String qryProvince() {

		RegionProvinceListReq req = new RegionProvinceListReq();
		RegionProvinceListResp resp = regionsServ.getProvinceList(req);
		if (resp != null) {
			regions = resp.getProvince_list();
		}

		return "province";
	}

	public String qryCity() {

		RegionCityListByProvinceReq req = new RegionCityListByProvinceReq();
		req.setProvince_id(Integer.parseInt(parentid));
		RegionCityListByProvinceResp resp = regionsServ
				.getCityListByProvinceId(req);
		if (resp != null) {
			regions = resp.getChild_list();
		}

		return "city";
	}

	public String qryRegion() {

		RegionListByCityReq req = new RegionListByCityReq();
		req.setCity_id(new Long(parentid).longValue());
		RegionListByCityResp resp = regionsServ.getRegionListByCityId(req);
		if (resp != null) {
			regions = resp.getChild_list();
		}

		return "region";
	}

	public String qryRegionJson() {
		String json = "";

		RegionChildrenJsonReq req = new RegionChildrenJsonReq();
		req.setRegion_id(parentid);
		RegionChildrenJsonResp resp = regionsServ.getChildrenJson(req);
		if (resp != null) {
			json = resp.getChild_json();
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

	public String testMember(){
		MemberIsExistsReq req = new MemberIsExistsReq();
		req.setPhone_no("123123");
		ZteClient client = ClientFactory.getZteDubboClient("FJ");
		MemberIsExistsResp resp = client.execute(req, MemberIsExistsResp.class);
		logger.info(resp.isExists());
		return null;
	}
	
	public MemberInf getMemberServ() {
		return memberServ;
	}

	public void setMemberServ(MemberInf memberServ) {
		this.memberServ = memberServ;
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

	public List<PayCfg> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<PayCfg> paymentList) {
		this.paymentList = paymentList;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Page getMemberPage() {
		return memberPage;
	}

	public void setMemberPage(Page memberPage) {
		this.memberPage = memberPage;
	}

	public List<MemberLv> getMemberLvList() {
		return memberLvList;
	}

	public void setMemberLvList(List<MemberLv> memberLvList) {
		this.memberLvList = memberLvList;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public Page getGoodsPage() {
		return goodsPage;
	}

	public void setGoodsPage(Page goodsPage) {
		this.goodsPage = goodsPage;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getMember_lv_id() {
		return member_lv_id;
	}

	public void setMember_lv_id(String member_lv_id) {
		this.member_lv_id = member_lv_id;
	}

	public List getRegions() {
		return regions;
	}

	public void setRegions(List regions) {
		this.regions = regions;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public List<Regions> getProvinces() {
		return provinces;
	}

	public void setProvinces(List<Regions> provinces) {
		this.provinces = provinces;
	}

	public List getCitys() {
		return citys;
	}

	public void setCitys(List citys) {
		this.citys = citys;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public List<DlyType> getDlyTypeList() {
		return dlyTypeList;
	}

	public void setDlyTypeList(List<DlyType> dlyTypeList) {
		this.dlyTypeList = dlyTypeList;
	}

	public String[] getProductidArray() {
		return productidArray;
	}

	public void setProductidArray(String[] productidArray) {
		this.productidArray = productidArray;
	}

	public ICartService getCartServ() {
		return cartServ;
	}

	public void setCartServ(ICartService cartServ) {
		this.cartServ = cartServ;
	}

	public String[] getCartidArray() {
		return cartidArray;
	}

	public void setCartidArray(String[] cartidArray) {
		this.cartidArray = cartidArray;
	}

	public List<CartItem> getCartList() {
		return cartList;
	}

	public void setCartList(List<CartItem> cartList) {
		this.cartList = cartList;
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public OrderPrice getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(OrderPrice orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getDlytype_id() {
		return dlytype_id;
	}

	public void setDlytype_id(String dlytype_id) {
		this.dlytype_id = dlytype_id;
	}

	public String getProvince_id() {
		return province_id;
	}

	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}

	public String getShipMobile() {
		return shipMobile;
	}

	public void setShipMobile(String shipMobile) {
		this.shipMobile = shipMobile;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getShipAddr() {
		return shipAddr;
	}

	public void setShipAddr(String shipAddr) {
		this.shipAddr = shipAddr;
	}

	public String getShipZip() {
		return shipZip;
	}

	public void setShipZip(String shipZip) {
		this.shipZip = shipZip;
	}

	public ICartManager getCartManager() {
		return cartManager;
	}

	public void setCartManager(ICartManager cartManager) {
		this.cartManager = cartManager;
	}

	public RegionsInf getRegionsServ() {
		return regionsServ;
	}

	public void setRegionsServ(RegionsInf regionsServ) {
		this.regionsServ = regionsServ;
	}

	public IOrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(IOrderServices orderServices) {
		this.orderServices = orderServices;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
