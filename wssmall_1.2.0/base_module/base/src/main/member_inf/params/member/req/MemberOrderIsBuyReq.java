package params.member.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

public class MemberOrderIsBuyReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品id",type="String",isNecessary="Y",desc="goods_id：商品id， 不能为空。")
	private String goods_id;
	@ZteSoftCommentAnnotationParam(name="会员id",type="String",isNecessary="Y",desc="member_id：会员id， 不能为空。")
	private String member_id;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.memberServer.member.current.isBuy";
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

}
