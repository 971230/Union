package zte.params.member.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.member.resp.FavAddResp;

public class FavAddReq extends ZteRequest<FavAddResp> {
	
	public static final String GOODS_TYPE = "goods";
	public static final String SUPPLER_TYPE = "suppler";

	@ZteSoftCommentAnnotationParam(name="商品id或供货商id",type="String",isNecessary="Y",desc="商品id或供货商id")
	private String fav_obj_id; //为商品，则商品id；为供货商，则供货商id
	@ZteSoftCommentAnnotationParam(name="会员id",type="String",isNecessary="Y",desc="会员id")
	private String member_id; //会员id、或者logined_id;
	@ZteSoftCommentAnnotationParam(name="类型",type="String",isNecessary="N",desc="商品类型为goods，供货商类型为suppler")
	private String fav_type = GOODS_TYPE; //非必填
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.memberService.fav.add";
	}

	public String getFav_obj_id() {
		return fav_obj_id;
	}

	public void setFav_obj_id(String fav_obj_id) {
		this.fav_obj_id = fav_obj_id;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getFav_type() {
		return fav_type;
	}

	public void setFav_type(String fav_type) {
		this.fav_type = fav_type;
	}


}
