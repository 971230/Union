package params;

import com.ztesoft.net.app.base.core.model.Member;

public class LoginUser {

	private Member member;
	private long loginTime;
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}
	
	
}
