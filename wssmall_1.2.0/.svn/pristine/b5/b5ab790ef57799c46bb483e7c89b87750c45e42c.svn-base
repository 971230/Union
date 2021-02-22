package params.attention.req;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;

public class AttentionReq extends ZteRequest {
	String favorite_id;// 搜藏id
	String fav_obj_id; // 为商品，则商品id；为供货商，则供货商id;为分销商，则分销商id.
	String member_id; // 会员id、或者logined_id;
	String fav_type; // 非必填
	String page_index = "1";
	String page_size = "50";

	public String getFav_obj_id() {
		return fav_obj_id;
	}

	public void setFav_obj_id(String fav_obj_id) {
		this.fav_obj_id = fav_obj_id;
	}

	public String getFav_type() {
		return fav_type;
	}

	public void setFav_type(String fav_type) {
		this.fav_type = fav_type;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getPage_index() {
		return page_index;
	}

	public void setPage_index(String page_index) {
		this.page_index = page_index;
	}

	public String getPage_size() {
		return page_size;
	}

	public void setPage_size(String page_size) {
		this.page_size = page_size;
	}

	public String getFavorite_id() {
		return favorite_id;
	}

	public void setFavorite_id(String favorite_id) {
		this.favorite_id = favorite_id;
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
