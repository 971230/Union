package services;

import java.util.List;

import params.goods.sales.req.GoodsSalesReq;
import params.goods.sales.resp.GoodsSalesResp;
import params.order.req.AddCartAndCreateOrderReq;
import params.order.req.DlyTypeReq;
import params.order.req.MemberOrdReq;
import params.order.req.OrderCountResp;
import params.order.req.OrderReq;
import params.order.req.OrderResp;
import params.order.req.OrderTotalReq;
import params.order.req.PaymentListReq;
import params.order.req.ShowOrderReq;
import params.order.resp.DlyTypeResp;
import params.order.resp.MemberOrdResp;
import params.order.resp.OrderOuterResp;
import params.order.resp.OrderTotalResp;
import params.order.resp.PaymentListResp;
import params.order.resp.ShowOrderResp;
import zte.params.order.req.OrderAddReq;
import zte.params.order.resp.OrderAddResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.OrderQueryParam;

/**
 * 订单
* @作者 MoChunrun 
* @创建日期 2013-9-27 
* @版本 V 1.0
 */
public interface OrderInf {

	
	/**
	 * 查询银行列表
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-25 
	 * @param bp
	 * @return
	 */
	//public BankListResp qryBankList(ZteRequest bp);
	
	/**
	 * 去结算
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-24 
	 * @param json
	 */
	public ShowOrderResp showOrder(ShowOrderReq cp);
	
	/**
	 * 启动流程
	 * @param req
	 * @param orderAddResp
	 * @param orderOuterResps
	 * @throws ApiBusiExceptio
	 */
	public void launchOrder(OrderAddReq req, OrderAddResp orderAddResp,List<OrderOuterResp> orderOuterResps) throws ApiBusiException;
	/**
	 * 下单
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-24 
	 * @param json
	 */
	public OrderAddResp createOrder(OrderOuter orderOuter);
	
	/**
	 * 查看配送方式
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-24 
	 * @param json
	 */
	public DlyTypeResp showDlyType(DlyTypeReq cp);
	
	/**
	 * 显示订单总金额
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-24 
	 * @param json
	 */
	public OrderTotalResp showOrderTotal(OrderTotalReq cp);
	
	
	/**
	 * 下单,通过接口第三方方式调用
	 * @作者 wui 
	 * @创建日期 2013-9-24 
	 * @param json
	 */
	 public OrderAddResp createOuterOrder(OrderAddReq req) throws Exception;
	
	 /**
	  * 加入购物车并购买商品
	  * @作者 MoChunrun
	  * @创建日期 2013-10-25 
	  * @param req
	  * @return
	  */
	 public OrderAddResp addCartAndCreateOrder(AddCartAndCreateOrderReq req);
	 
	 public OrderResp censusState(OrderReq req );
	 public GoodsSalesResp goodsSalseMoney(GoodsSalesReq goodsSalesReq);
	 public GoodsSalesResp goodsSalseCount(GoodsSalesReq goodsSalesReq);
	 /**
	  * 查询支付方式
	  * @作者 MoChunrun
	  * @创建日期 2013-10-28 
	  * @param req
	  * @return
	  */
	 public PaymentListResp qryPaymetList(PaymentListReq req);
	
	 
	 public MemberOrdResp listOrderByMemberId(MemberOrdReq memberOrdReq);
	 
	 public OrderResp get(OrderReq orderReq );
	 
	 public OrderResp listGoodsItems(OrderReq orderReq );
	 
	 public OrderResp listOrderMetas(OrderReq orderReq );
	 
	 public OrderCountResp countOrders(OrderQueryParam ordParam);
	 
	 public  void initParam(OrderAddReq req) throws ApiBusiException ;
	 
	 public List<Order> syOuterOrder(OrderAddReq req, OrderAddResp orderAddResp,List<OrderOuterResp> orderOuterResps) throws ApiBusiException;
}
