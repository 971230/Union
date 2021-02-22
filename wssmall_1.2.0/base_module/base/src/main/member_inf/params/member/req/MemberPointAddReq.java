package params.member.req;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;

public class MemberPointAddReq extends ZteRequest{
	private String member_id;
	private int point;
	private String type;
	private String related_id;
	
	
	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRelated_id() {
		return related_id;
	}

	public void setRelated_id(String related_id) {
		this.related_id = related_id;
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
