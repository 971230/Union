package com.ztesoft.inf.communication.client.vo;

import java.util.Map;

public class ClientGlobalVar {

	private String gvarsId;
	private String gvarsDefinition;

	public ClientGlobalVar(Map map) {
		setValues(map);
	}

	private void setValues(Map map) {
		gvarsId = (String) map.get("gvar_id");
		byte[] tpl = (byte[]) map.get("gvar_def");
		if (tpl != null)
			gvarsDefinition = new String(tpl);
	}

	public String getGvarsId() {
		return gvarsId;
	}

	public void setGvarsId(String gvarsId) {
		this.gvarsId = gvarsId;
	}

	public String getGvarsDefinition() {
		return gvarsDefinition;
	}

	public void setGvarsDefinition(String gvarsDefinition) {
		this.gvarsDefinition = gvarsDefinition;
	}
}
