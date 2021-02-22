package params.addr.resp;

import params.ZteResponse;

import java.util.List;

public class AddrListResp extends ZteResponse {

	private List addressList;

	public List getAddressList() {
		return addressList;
	}

	public void setAddressList(List addressList) {
		this.addressList = addressList;
	}
	
	
}
