package services;

import com.ztesoft.net.mall.core.model.Cart;

import params.ZteRequest;
import params.ZteResponse;
import params.cart.req.*;
import params.cart.resp.CartAddResp;
import params.cart.resp.CartCleanResp;
import params.cart.resp.CartListResp;
import params.cart.resp.CartTotalResp;

/**
 * 购物车service
* @作者 MoChunrun 
* @创建日期 2013-9-23 
* @版本 V 1.0
 */

public interface ICartService{
	

	/**
	 * 增加购物车商品
	 * {"productId":"111111","num":"3","memberLvId":"1","adjuncts":[{"productId":"1","num":"2"},{"productId":"3","num":"2"}],"logined_id":"123123","session_id":"222222"}
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-23 
	 * @param json
	 * @return
	 */
	public CartAddResp add(CartAddReq cp);
	
	
	public CartCleanResp clean(CartCleanReq cp);
	
	
	/**
	 * 删除购物车商品
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-23 
	 * @param json
	 * @return
	 */
	public ZteResponse delete(CartDelReq cp);
	
	/**
	 * 清空购物车
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-23 
	 * @param json
	 * @return
	 */
	public ZteResponse clear(ZteRequest cp);
	
	/**
	 * 修改购物车商品
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-23 
	 * @param json
	 * @return
	 */
	public ZteResponse update(CartUpdateReq cp);
	
	/**
	 * 查看购物车总价
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-23 
	 * @param json
	 * @return
	 */
	public CartTotalResp getTotal(CartTotalReq cp);
	
	/**
	 * 购物车列表
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-23 
	 * @param json
	 * @return
	 */
	public CartListResp list(CartListReq cp);
	
	public void insertCart(Cart cart);
}
