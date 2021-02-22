package params.member.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

public class PointConsumeReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="积分类型",type="String",isNecessary="N",desc="积分类型[0等级积分1消费积分]")
	private String pointType;
//	private String member_id;
	public String getPointType() {
		return pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.memberServer.member.point.consume";
	}

//	public String getMember_id() {
//		return member_id;
//	}
//
//	public void setMember_id(String member_id) {
//		this.member_id = member_id;
//	}

}
