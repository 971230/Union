package zte.net.ecsord.params.taobao.resp;

import com.taobao.api.domain.Trade;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class GetTaobaoOrderInfoTAOBAOResponse  extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="交易结构",type="String",isNecessary="Y",desc="交易结构")
	private Trade dataTrade;

	public Trade getDataTrade() {
		return dataTrade;
	}

	public void setDataTrade(Trade dataTrade) {
		this.dataTrade = dataTrade;
	}
	
	
}
