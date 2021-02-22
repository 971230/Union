package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class WhiteCardInfoVO implements Serializable{
	@ZteSoftCommentAnnotationParam(name="IMSI值",type="String",isNecessary="Y",desc="白卡获取->imsi:IMSI值")
	private String imsi = "";
	@ZteSoftCommentAnnotationParam(name="SCRIPTSEQ值",type="String",isNecessary="Y",desc="白卡获取->scriptseq:SCRIPTSEQ值")
	private String scriptseq = "";
	@ZteSoftCommentAnnotationParam(name="发起方业务流水",type="String",isNecessary="Y",desc="白卡获取->proc_id:发起方业务流水")
	private String proc_id = "";
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getScriptseq() {
		return scriptseq;
	}
	public void setScriptseq(String scriptseq) {
		this.scriptseq = scriptseq;
	}
	public String getProc_id() {
		return proc_id;
	}
	public void setProc_id(String proc_id) {
		this.proc_id = proc_id;
	}
	
	

	
	/**
	* get the value from Map
	*/
	public void fromMap(Map map) {
		setImsi((map.get("imsi").toString().equals("null")?"":(map.get("imsi").toString())));
		setScriptseq((map.get("scriptseq").toString().equals("null")?"":(map.get("scriptseq").toString())));
		setProc_id((map.get("proc_id").toString().equals("null")?"":(map.get("proc_id").toString())));
	}
	/**
	* set the value from Map
	*/
	public Map toMap() {
		Map map = new HashMap();
		map.put("imsi",getImsi());
		map.put("scriptseq",getScriptseq());
		map.put("proc_id",getProc_id());
		return map;
	}
}
