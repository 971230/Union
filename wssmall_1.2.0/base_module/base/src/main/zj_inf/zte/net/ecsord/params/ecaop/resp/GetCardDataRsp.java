package zte.net.ecsord.params.ecaop.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 
 * 获取写卡数据响应结果
 *
 */
public class GetCardDataRsp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="应答码",type="String",isNecessary="Y",desc="answerCode：应答码")
	private String AnswerCode;
	@ZteSoftCommentAnnotationParam(name="应答信息",type="String",isNecessary="Y",desc="desc：应答信息")
	private String Desc;
	@ZteSoftCommentAnnotationParam(name="IMSI预占结果",type="String",isNecessary="Y",desc="reservStatus：IMSI预占结果 1：预占成功0：预占IMSI失败")
	private String ReservStatus;
	@ZteSoftCommentAnnotationParam(name="说明",type="String",isNecessary="Y",desc="comments：说明")
	private String Comments;
	@ZteSoftCommentAnnotationParam(name="大卡卡号",type="String",isNecessary="Y",desc="ICCID：大卡卡号")
	private String ICCID;
	@ZteSoftCommentAnnotationParam(name="IMSI号",type="String",isNecessary="Y",desc="IMSI号")
	private String IMSI;
	@ZteSoftCommentAnnotationParam(name="scriptSeq",type="String",isNecessary="Y",desc="scriptSeq:写卡脚本")
	private String ScriptSeq;
	@ZteSoftCommentAnnotationParam(name="cardData",type="String",isNecessary="Y",desc="cardData:卡数据")
	private String CardData;
	@ZteSoftCommentAnnotationParam(name="procId",type="String",isNecessary="Y",desc="procId")
	private String ProcId;
	@ZteSoftCommentAnnotationParam(name="para1",type="String",isNecessary="Y",desc="para1")
	private String Para1;
	@ZteSoftCommentAnnotationParam(name="para2",type="String",isNecessary="Y",desc="para2")
	private String Para2;
	public String getAnswerCode() {
		return AnswerCode;
	}
	public void setAnswerCode(String answerCode) {
		this.AnswerCode = answerCode;
	}
	public String getDesc() {
		return Desc;
	}
	public void setDesc(String desc) {
		this.Desc = desc;
	}
	public String getReservStatus() {
		return ReservStatus;
	}
	public void setReservStatus(String reservStatus) {
		this.ReservStatus = reservStatus;
	}
	public String getComments() {
		return Comments;
	}
	public void setComments(String comments) {
		this.Comments = comments;
	}
	public String getICCID() {
		return ICCID;
	}
	public void setICCID(String ICCID) {
		this.ICCID = ICCID;
	}
	public String getIMSI() {
		return IMSI;
	}
	public void setIMSI(String IMSI) {
		this.IMSI = IMSI;
	}
	public String getScriptSeq() {
		return ScriptSeq;
	}
	public void setScriptSeq(String scriptSeq) {
		this.ScriptSeq = scriptSeq;
	}
	public String getCardData() {
		return CardData;
	}
	public void setCardData(String cardData) {
		this.CardData = cardData;
	}
	public String getProcId() {
		return ProcId;
	}
	public void setProcId(String procId) {
		this.ProcId = procId;
	}
	public String getPara1() {
		return Para1;
	}
	public void setPara1(String para1) {
		this.Para1 = para1;
	}
	public String getPara2() {
		return Para2;
	}
	public void setPara2(String para2) {
		this.Para2 = para2;
	}
	
	
	
}
