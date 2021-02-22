package params.resp;

import java.util.Map;

import params.ZteResponse;

public class QueryRunThreadStatusrResp extends ZteResponse{
	
	private Map<String, String> runThreadName;//运行线程名称

	public Map<String, String> getRunThreadName() {
		return runThreadName;
	}

	public void setRunThreadName(Map<String, String> runThreadName) {
		this.runThreadName = runThreadName;
	}
	
}
