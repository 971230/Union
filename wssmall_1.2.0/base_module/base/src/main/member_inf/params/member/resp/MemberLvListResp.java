package params.member.resp;

import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.MemberLv;

public class MemberLvListResp extends ZteResponse{
	
	@ZteSoftCommentAnnotationParam(name="会员等级列表",type="String",isNecessary="Y",desc="会员等级列表",hasChild=true)
	private List<MemberLv> MemberLvs;

	public List<MemberLv> getMemberLvs() {
		return MemberLvs;
	}

	public void setMemberLvs(List<MemberLv> memberLvs) {
		MemberLvs = memberLvs;
	}

}
