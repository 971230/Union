package rule.params.accept.resp;

import params.ZteResponse;

/**
 * 受理规则出参
 * @author wu.i
 *
 */
public class AcceptRuleResp extends ZteResponse {
	private String inst_id;
	private String flag; //受理成功失败标志 yes成功、no失败
	private boolean done = false;
	private ZteResponse zteResponse;
	public String getInst_id() {
		return inst_id;
	}
	public void setInst_id(String inst_id) {
		this.inst_id = inst_id;
	}
	
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public ZteResponse getZteResponse() {
		return zteResponse;
	}
	public void setZteResponse(ZteResponse zteResponse) {
		this.zteResponse = zteResponse;
	}
	
}
