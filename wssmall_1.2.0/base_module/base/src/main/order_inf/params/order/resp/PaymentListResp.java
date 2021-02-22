package params.order.resp;

import com.ztesoft.net.mall.core.model.PayCfg;
import params.ZteResponse;

import java.util.List;

/**
 * 支付方式返回信息
 * @作者 MoChunrun
 * @创建日期 2013-10-28 
 * @版本 V 1.0
 */
public class PaymentListResp extends ZteResponse {

	private List<PayCfg> paymentList;

	public List<PayCfg> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<PayCfg> paymentList) {
		this.paymentList = paymentList;
	}
	
}
