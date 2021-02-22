package params.member.resp;

import params.ZteResponse;

import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class MemberLvPriceResp extends ZteResponse{
	@ZteSoftCommentAnnotationParam(name="会员价格",type="map",isNecessary="Y",desc="memberLVSpecMap：会员价格。")
	private Map memberLVSpecMap;

	public Map getMemberLVSpecMap() {
		return memberLVSpecMap;
	}

	public void setMemberLVSpecMap(Map memberLVSpecMap) {
		this.memberLVSpecMap = memberLVSpecMap;
	}
	
}
