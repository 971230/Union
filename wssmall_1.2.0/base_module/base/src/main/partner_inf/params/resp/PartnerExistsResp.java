package params.resp;

import params.ZteResponse;


/**
 * 判断分销商是否存在出参
 * @author hu.yi
 * @date 2013.12.26
 */
public class PartnerExistsResp extends ZteResponse{

	private boolean flag;

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
