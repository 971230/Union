package zte.params.member.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.member.resp.FavPageListResp;

public class FavPageListReq extends ZteRequest<FavPageListResp> {

	public static final String GOODS_TYPE = "goods";
	public static final String SUPPLER_TYPE = "suppler";
	public static final String PARTNER_TYPE = "partner";
	@ZteSoftCommentAnnotationParam(name="会员id",type="String",isNecessary="Y",desc="会员id")
	private String member_id; //会员id、或者logined_id;
	@ZteSoftCommentAnnotationParam(name="第几页",type="String",isNecessary="N",desc="第几页 默认为1")
	private String page_index ="1";
	@ZteSoftCommentAnnotationParam(name="每页记录数",type="String",isNecessary="N",desc="每页记录数 默认50")
	private String page_size ="50";
	@ZteSoftCommentAnnotationParam(name="类型",type="String",isNecessary="Y",desc="商品类型为goods，供货商类型为suppler，分销商类型为partner")
	private String fav_type = GOODS_TYPE; 
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.memberService.fav.page";
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

	public String getFav_type() {
		return fav_type;
	}

	public void setFav_type(String fav_type) {
		this.fav_type = fav_type;
	}

}
