package params.cart.req;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;

import java.util.List;

public class CartUpdateReq extends ZteRequest{
	
	private List<Cart> cartList;
	private String action;
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<Cart> getCartList() {
		return cartList;
	}

	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}

	public static class Cart{
		private String cart_id;
		private String num;
		private boolean checked;
		public String getCart_id() {
			return cart_id;
		}
		public void setCart_id(String cart_id) {
			this.cart_id = cart_id;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		public boolean isChecked() {
			return checked;
		}
		public void setChecked(boolean checked) {
			this.checked = checked;
		}
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
}
