package params.resp;

import params.orderqueue.resp.OrderCollectionResp;
import params.resp.vo.DATA;

public class OrderPreCreateCtnStandardResp extends OrderCollectionResp {

	private static final long serialVersionUID = 1L;
	private String CODE;
	private String DESC;
	private DATA DATA;

	public String getCODE() {
		return CODE;
	}

	public void setCODE(String cODE) {
		CODE = cODE;
	}

	public String getDESC() {
		return DESC;
	}

	public void setDESC(String dESC) {
		DESC = dESC;
	}

	public DATA getDATA() {
		return DATA;
	}

	public void setDATA(DATA dATA) {
		DATA = dATA;
	}

}
