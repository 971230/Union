package params.cart.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import params.ZteRequest;
import params.cart.resp.CartAddResp;

import java.util.List;

public class CartAddReq extends ZteRequest<CartAddResp> {

	@ZteSoftCommentAnnotationParam(name="产品编号",type="String",isNecessary="Y",desc="产品编号")
	private String product_id;
	@ZteSoftCommentAnnotationParam(name="购买数量",type="Integer",isNecessary="Y",desc="购买数量,整数")
	private String num;
	@ZteSoftCommentAnnotationParam(name="购买价格",type="Float",isNecessary="N",desc="购买价格")
	private String price;
	@ZteSoftCommentAnnotationParam(name="会员编号",type="String",isNecessary="Y",desc="会员编号")
	private String member_id;
	@ZteSoftCommentAnnotationParam(name="会员等级编号",type="String",isNecessary="Y",desc="会员等级编号")
	private String member_lv_id;
	@ZteSoftCommentAnnotationParam(name="商品配件",type="List",isNecessary="N",desc="商品配件")
	private List<Adjunct> adjuncts;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getMember_lv_id() {
		return member_lv_id;
	}

	public void setMember_lv_id(String member_lv_id) {
		this.member_lv_id = member_lv_id;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public List<Adjunct> getAdjuncts() {
		return adjuncts;
	}

	public void setAdjuncts(List<Adjunct> adjuncts) {
		this.adjuncts = adjuncts;
	}

	public static class Adjunct{
		private String product_id;
		private String num;
		
		public String getProduct_id() {
			return product_id;
		}
		public void setProduct_id(String product_id) {
			this.product_id = product_id;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		return "zte.cartService.cart.add";
	}
}
