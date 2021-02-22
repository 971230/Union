package params.ask.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.app.base.core.model.Ask;
import params.ZteRequest;

import java.util.Date;

public class AskReq extends ZteRequest {
	
	private Ask ask;
	private String askid;
	private String keyword;
	private Date startTime;
	private Date endTime;
	private int pageNo;
	private int pageSize;
	
	
	public String getAskid() {
		return askid;
	}

	public void setAskid(String askid) {
		this.askid = askid;
	}
	public Ask getAsk() {
		return ask;
	}

	public void setAsk(Ask ask) {
		this.ask = ask;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
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
