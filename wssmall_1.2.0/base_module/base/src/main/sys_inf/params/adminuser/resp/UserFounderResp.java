package params.adminuser.resp;

import java.util.Map;

import params.ZteResponse;


/**
 * 用户权限返回对象
 * @author hu.yi
 * @date 2013.12.24
 */
public class UserFounderResp extends ZteResponse{

	
	private Map<String, String> map;

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
}
