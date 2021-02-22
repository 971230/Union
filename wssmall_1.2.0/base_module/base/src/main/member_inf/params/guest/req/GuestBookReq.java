package params.guest.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.app.base.core.model.GuestBook;
import params.ZteRequest;

public class GuestBookReq extends ZteRequest {
	
	private GuestBook guestbook;
	private String keyword;
	private Integer pageNo;
	private Integer pageSize;
	
	public GuestBook getGuestbook() {
		return guestbook;
	}

	public void setGuestbook(GuestBook guestbook) {
		this.guestbook = guestbook;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
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
