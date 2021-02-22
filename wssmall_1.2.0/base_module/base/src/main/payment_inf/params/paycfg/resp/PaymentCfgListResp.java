package params.paycfg.resp;

import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.mall.core.model.PayCfg;

public class PaymentCfgListResp extends ZteResponse{

	private List<PayCfg> payCfgList;

	public List<PayCfg> getPayCfgList() {
		return payCfgList;
	}

	public void setPayCfgList(List<PayCfg> payCfgList) {
		this.payCfgList = payCfgList;
	}
	
}
