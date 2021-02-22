package params.goods.sales.resp;

import params.ZteResponse;

public class GoodsSalesResp extends ZteResponse {

	double salesP;
	int salesNum;
	int stores;
	public double getSalesP() {
		return salesP;
	}

	public void setSalesP(double salesP) {
		this.salesP = salesP;
	}

	public int getSalesNum() {
		return salesNum;
	}

	public void setSalesNum(int salesNum) {
		this.salesNum = salesNum;
	}

	public int getStores() {
		return stores;
	}

	public void setStores(int stores) {
		this.stores = stores;
	}
	
	
}
