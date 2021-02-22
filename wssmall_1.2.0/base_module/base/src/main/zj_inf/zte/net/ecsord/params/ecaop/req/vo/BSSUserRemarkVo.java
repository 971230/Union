package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class BSSUserRemarkVo implements Serializable {
	
	private String UserRemarkID;
	private String UserRemarkValue;
	
	public String getUserRemarkId() {
		if (StringUtils.isBlank(UserRemarkID)) return null;
		return UserRemarkID;
	}
	public void setUserRemarkId(String userRemarkId) {
		this.UserRemarkID = userRemarkId;
	}
	public String getUserRemarkValue() {
		if (StringUtils.isBlank(UserRemarkValue)) return null;
		return UserRemarkValue;
	}
	public void setUserRemarkValue(String userRemarkValue) {
		this.UserRemarkValue = userRemarkValue;
	}

}
