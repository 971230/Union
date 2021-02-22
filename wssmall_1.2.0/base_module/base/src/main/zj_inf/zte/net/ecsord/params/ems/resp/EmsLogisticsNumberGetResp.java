package zte.net.ecsord.params.ems.resp;

import java.util.List;

import params.ZteResponse;

public class EmsLogisticsNumberGetResp extends ZteResponse {

	private String requestno;
	
	private int count;
	
	private List<String> mailnums;

	public String getRequestno() {
		return requestno;
	}

	public void setRequestno(String requestno) {
		this.requestno = requestno;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<String> getMailnums() {
		return mailnums;
	}

	public void setMailnums(List<String> mailnums) {
		this.mailnums = mailnums;
	}
}
