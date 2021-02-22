package zte.params.number.resp;


import params.ZteResponse;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.NumeroMsg;
/**
 * 
 * @author deng.yx
 * 号码状态修改消息返回
 *
 */
public class NumberChangeStatusResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="号码发布信息", type="NumeroMsg", isNecessary="N", desc="号码发布状态信息")
	private NumeroMsg msg;

	public NumeroMsg getMsg() {
		return msg;
	}

	public void setMsg(NumeroMsg msg) {
		this.msg = msg;
	}
	
}
