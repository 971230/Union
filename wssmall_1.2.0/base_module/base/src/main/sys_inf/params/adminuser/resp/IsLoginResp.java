package params.adminuser.resp;

import params.ZteResponse;

public class IsLoginResp extends ZteResponse{
     private boolean flag; //判断是否登录过  false未登录 true是已登录

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
     
     
}
