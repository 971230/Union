package params.orderqueue.resp;

import params.ZteResponse;

public class CheckOpenServiceResp extends ZteResponse {
	

	private  boolean flag=false;//true-配置存在，false-服务不存在,默认不存在

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
}
