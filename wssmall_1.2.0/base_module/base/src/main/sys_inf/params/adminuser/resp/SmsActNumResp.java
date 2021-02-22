package params.adminuser.resp;

import params.ZteResponse;

/**
 * 短信返回实体
 * @author hu.yi
 * @date 2013.12.24
 */
public class SmsActNumResp extends ZteResponse{

	private String actNum;

	public String getActNum() {
		return actNum;
	}

	public void setActNum(String actNum) {
		this.actNum = actNum;
	}

}
