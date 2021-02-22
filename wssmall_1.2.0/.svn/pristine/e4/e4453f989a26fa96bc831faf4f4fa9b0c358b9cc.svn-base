package params.member.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class MemberLVCanBuyReq extends ZteRequest{
	
	public static enum BUY_TYPE {MEMBER_BUY_PRODUCT, MEMBER_BUY_GOODS};
	@ZteSoftCommentAnnotationParam(name="产品ID",type="String",isNecessary="N",desc="产品ID")
	private String product_id;
	@ZteSoftCommentAnnotationParam(name="会员等级",type="String",isNecessary="Y",desc="会员等级")
	private String member_lv_id;
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="N",desc="商品ID")
	private String member_good_id;
	@ZteSoftCommentAnnotationParam(name="购买类型",type="String",isNecessary="Y",desc="购买类型:MEMBER_BUY_PRODUCT产品，MEMBER_BUY_GOODS商品")
	private BUY_TYPE buy_type = BUY_TYPE.MEMBER_BUY_PRODUCT; 
	
	public BUY_TYPE getBuy_type() {
		return buy_type;
	}

	public void setBuy_type(BUY_TYPE buy_type) {
		this.buy_type = buy_type;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getMember_good_id() {
		return member_good_id;
	}

	public void setMember_good_id(String member_good_id) {
		this.member_good_id = member_good_id;
	}

	public String getMember_lv_id() {
		return member_lv_id;
	}

	public void setMember_lv_id(String member_lv_id) {
		this.member_lv_id = member_lv_id;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {		
		return "zte.memberServer.memberLV.isCanBuy";
	}

}
