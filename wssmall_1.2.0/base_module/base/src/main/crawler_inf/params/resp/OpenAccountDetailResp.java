package params.resp;

import params.ZteResponse;

public class OpenAccountDetailResp extends ZteResponse{
	private String zb_open_type;//总商系统开户类型，1:自动开户,0:手动开户
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getZb_open_type() {
		
		return zb_open_type;
	}
	public void setZb_open_type(String zb_open_type) {
		this.zb_open_type = zb_open_type;
	}

}
