package params.checkacctconfig.resp;

import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.mall.cmp.CompBillResult;
import com.ztesoft.net.mall.core.model.TransDetailVo;
import com.ztesoft.net.mall.core.model.TransMainVo;

public class CheckAcctConfigResp extends ZteResponse{
	private List<TransDetailVo> details; //对账数据明细
	private TransMainVo transMainVo;
	
	private CompBillResult compBillResult; //入参数
	
	
	public CheckAcctConfigResp(){
	
	}
	public List<TransDetailVo> getDetails() {
		return details;
	}
	public void setDetails(List<TransDetailVo> details) {
		this.details = details;
	}
	public CompBillResult getCompBillResult() {
		return compBillResult;
	}
	public void setCompBillResult(CompBillResult compBillResult) {
		this.compBillResult = compBillResult;
	}
	public TransMainVo getTransMainVo() {
		return transMainVo;
	}
	public void setTransMainVo(TransMainVo transMainVo) {
		this.transMainVo = transMainVo;
	}
	
	
}
