package zte.net.iservice;

import params.cart.req.CartAddReq;
import params.cart.req.CartListReq;
import params.cart.req.CartTotalReq;
import params.cart.resp.CartAddResp;
import params.cart.resp.CartListResp;
import params.cart.resp.CartTotalResp;
import zte.params.cart.req.CartDeleteReq;
import zte.params.cart.resp.CartDeleteResp;
import zte.params.order.req.CartBarPriceTotalReq;
import zte.params.order.req.CartUpdateReq;
import zte.params.order.resp.CartBarPriceTotalResp;
import zte.params.order.resp.CartUpdateResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="购物车管理API",summary="购物车管理API[添加购物车、查询购物车、删除购物车]")
public interface ICartServices {

	/**
	 * 添加购物车
	 * @作者 MoChunrun
	 * @创建日期 2014-1-8 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="添加购物车",summary="添加购物车")
	public CartAddResp addCart(CartAddReq req);
	
	/**
	 * 查询购物车
	 * @作者 MoChunrun
	 * @创建日期 2014-1-8 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询购物车",summary="查询购物车")
	public CartListResp listCart(CartListReq req);
	
	/**
	 * 删除购物车
	 * @作者 MoChunrun
	 * @创建日期 2014-1-14 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="删除购物车",summary="删除购物车")
	public CartDeleteResp deleteCart(CartDeleteReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="修改购物车数量",summary="修改购物车数量")
	public CartUpdateResp updateCartNum(CartUpdateReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取购物车总价",summary="获取购物车总价")
	public CartTotalResp getCartTotalPrice(CartTotalReq req);
	
	public CartBarPriceTotalResp getCartBarTotalPrice(CartBarPriceTotalReq req);
}
