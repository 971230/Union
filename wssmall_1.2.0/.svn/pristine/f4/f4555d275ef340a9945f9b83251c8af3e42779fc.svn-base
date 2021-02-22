package zte.params.member.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.GuestBook;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.member.resp.GuestBookAddResp;

public class GuestBookAddReq extends ZteRequest<GuestBookAddResp> {

	@ZteSoftCommentAnnotationParam(name="留言信息",type="String",isNecessary="Y",desc="留言信息",hasChild=true)
	private GuestBook guestbook;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.memberService.guestbook.add";
	}

	public GuestBook getGuestbook() {
		return guestbook;
	}

	public void setGuestbook(GuestBook guestbook) {
		this.guestbook = guestbook;
	}

}
