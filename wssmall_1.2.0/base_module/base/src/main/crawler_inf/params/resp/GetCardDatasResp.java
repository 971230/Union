package params.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class GetCardDatasResp extends ZteResponse{
	
    @ZteSoftCommentAnnotationParam(name="iccid(sim卡号)",type="String",isNecessary="Y",desc="iccid(sim卡号)")
	private String iccid;
    
    @ZteSoftCommentAnnotationParam(name="是否首次读卡，“0”：是 “1”：不是",type="String",isNecessary="Y",desc="是否首次读卡，“0”：是 “1”：不是")
  	private String reDoTag;
    
    @ZteSoftCommentAnnotationParam(name="卡数据",type="String",isNecessary="Y",desc="卡数据")
  	private String imsi;
    
    @ZteSoftCommentAnnotationParam(name="号码",type="String",isNecessary="Y",desc="号码")
  	private String numId;

	@ZteSoftCommentAnnotationParam(name="",type="String",isNecessary="Y",desc="")
  	private String scriptSeq;
    
    public String getNumId() {
		return numId;
	}

	public void setNumId(String numId) {
		this.numId = numId;
	}
    
	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getReDoTag() {
		return reDoTag;
	}

	public void setReDoTag(String reDoTag) {
		this.reDoTag = reDoTag;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getScriptSeq() {
		return scriptSeq;
	}

	public void setScriptSeq(String scriptSeq) {
		this.scriptSeq = scriptSeq;
	}
    
    
}
