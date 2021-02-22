package params.member.req;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;

/**
 * 会员等级查询入参
 * @作者 MoChunrun
 * @创建日期 2013-10-28 
 * @版本 V 1.0
 */
public class MemberLvQryReq extends ZteRequest {

	private String lv_ids;
	private String member_id;
	
	public String getLv_ids() {
		return lv_ids;
	}

	public void setLv_ids(String lv_ids) {
		this.lv_ids = lv_ids;
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
		// TODO Auto-generated method stub
		return null;
	}
}
