package params.member.req;

import params.ZteRequest;
import params.member.resp.MemberLvListResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class MemberLvListReq extends ZteRequest<MemberLvListResp>{
	@ZteSoftCommentAnnotationParam(name="会员等级Id",type="String",isNecessary="Y",desc="member_lv_ids：会员等级Id， 不能为空。")
	private String member_lv_ids;
	@ZteSoftCommentAnnotationParam(name="商品id",type="String",isNecessary="Y",desc="goods_id：商品， 不能为空。")
	private String goods_id;
	public String getMember_lv_ids() {
		return member_lv_ids;
	}

	public void setMember_lv_ids(String member_lv_ids) {
		this.member_lv_ids = member_lv_ids;
	}

	private String member_id; 

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

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.memberService.memberlv.list";
	}

}
