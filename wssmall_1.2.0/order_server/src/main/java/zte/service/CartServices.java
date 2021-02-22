package zte.service;

import javax.annotation.Resource;

import params.cart.req.CartAddReq;
import params.cart.req.CartDelReq;
import params.cart.req.CartListReq;
import params.cart.req.CartTotalReq;
import params.cart.resp.CartAddResp;
import params.cart.resp.CartListResp;
import params.cart.resp.CartTotalResp;
import services.ServiceBase;
import zte.net.iservice.ICartServices;
import zte.params.cart.req.CartDeleteReq;
import zte.params.cart.resp.CartDeleteResp;
import zte.params.order.req.CartBarPriceTotalReq;
import zte.params.order.req.CartUpdateReq;
import zte.params.order.resp.CartBarPriceTotalResp;
import zte.params.order.resp.CartUpdateResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.mall.core.service.ICartManager;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version = "1.0")
public class CartServices extends ServiceBase implements ICartServices {

    @Resource(name = "cartServ")
    private services.ICartService cartServ;
    @Resource
    private ICartManager cartManager;

    @Override
    public CartAddResp addCart(CartAddReq req) {
        CartAddResp resp = cartServ.add(req);
        addReturn(req, resp);
        return resp;
    }

    @Override
    public CartListResp listCart(CartListReq req) {
        CartListResp resp = cartServ.list(req);
        addReturn(req, resp);
        return resp;
    }

    @Override
    public CartDeleteResp deleteCart(CartDeleteReq req) {
    	if(req.isClean()){
    		cartManager.clean(req.getUserSessionId(), false);
    	}else{
	        CartDelReq creq = new CartDelReq();
	        creq.setUserSessionId(req.getUserSessionId());
	        creq.setCart_ids(req.getCart_ids());
	        cartServ.delete(creq);
    	}
        CartDeleteResp resp = new CartDeleteResp();
        resp.setError_code("0");
        resp.setError_msg("成功");
        addReturn(req, resp);
        return resp;
    }

	@Override
	public CartUpdateResp updateCartNum(CartUpdateReq req) {
		if(CartUpdateReq.ACTION_NUM.equals(req.getAction())){
			String ids = req.getCart_id();
			String [] id_array = ids.split(",");
			for(String id : id_array){
				cartManager.updateNum(req.getUserSessionId(),  Long.valueOf(id),  req.getNum());
			}
		}else if(CartUpdateReq.ACTION_CHECKED_FLAG.equals(req.getAction())){
			String ids = req.getCart_id();
			String [] id_array = ids.split(",");
			for(String id : id_array){
				cartManager.updateCheckedFlag(req.getUserSessionId(),  Long.valueOf(id),  req.getChecked_flag());
			}
		}
		
		CartUpdateResp resp = new CartUpdateResp();
		resp.setError_code("0");
        resp.setError_msg("成功");
        addReturn(req, resp);
        return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取购物车总价", summary = "获取购物车总价")
	public CartTotalResp getCartTotalPrice(CartTotalReq req) {
		return cartServ.getTotal(req);
	}

	@Override
	public CartBarPriceTotalResp getCartBarTotalPrice(CartBarPriceTotalReq req) {
		Double goodsTotal  =cartManager.countGoodsTotal(UserServiceFactory.getUserService().getCurrentMember(),req.getUserSessionId(),req.isCountChecked());
		Double pgkTotal = cartManager.countPgkTotal(req.getUserSessionId());
		CartBarPriceTotalResp resp = new CartBarPriceTotalResp();
		resp.setGoodsTotal(goodsTotal);
		resp.setPgkTotal(pgkTotal);
		resp.setError_code("0");
        resp.setError_msg("成功");
        addReturn(req, resp);
        return resp;
	}

}
