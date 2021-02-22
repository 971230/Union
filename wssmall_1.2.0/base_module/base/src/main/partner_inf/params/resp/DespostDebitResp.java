package params.resp;

import params.ZteResponse;



/**
 * 预存金支付扣款出参
 * @author hu.yi
 * @date 2013.12.26
 */
public class DespostDebitResp extends ZteResponse{

	private boolean flag;

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
