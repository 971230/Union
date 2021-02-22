package zte.net.ecsord.params.bss.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


/**
 * 社会渠道购机券换手机BSS端支持(加锁)
 * 返回对象
 */
public class PurchaseCouponsLockResp extends BaseBSSResp {
	
	@ZteSoftCommentAnnotationParam(name="返回结果",type="String",isNecessary="Y",desc="X_RESULTCODE:0000 查询成功，有记录;其他为查询无记录或SQL失败")
	private String X_RESULTCODE;

	@ZteSoftCommentAnnotationParam(name="返回结果描述",type="String",isNecessary="Y",desc="X_RESULTINFO:如果失败，返回失败的错误描述;后面的字段不再返回或没有意义")
	private String X_RESULTINFO;

	@ZteSoftCommentAnnotationParam(name="记录数",type="String",isNecessary="Y",desc="X_RECORDNUM:执行成功，记录数为 1")
	private String X_RECORDNUM;

	@ZteSoftCommentAnnotationParam(name="用户ID",type="String",isNecessary="Y",desc="USER_ID:只有代金券已经绑定之后才有值")
	private String USER_ID;

	@ZteSoftCommentAnnotationParam(name="服务号码",type="String",isNecessary="Y",desc="SERIAL_NUMBER:只有代金券已经绑定之后才有值")
	private String SERIAL_NUMBER;

	@ZteSoftCommentAnnotationParam(name="代金券状态",type="String",isNecessary="Y",desc="STATE:0-未使用,T-临时占用,1-已绑定,L-已锁定,2-已兑换，C-已作废")
	private String STATE;

	@ZteSoftCommentAnnotationParam(name="预留1",type="String",isNecessary="Y",desc="PARAM_VALUE1:预留1")
	private String PARAM_VALUE1;
	
	@ZteSoftCommentAnnotationParam(name="预留2",type="String",isNecessary="Y",desc="PARAM_VALUE2:预留2")
	private String PARAM_VALUE2;
	
	@ZteSoftCommentAnnotationParam(name="预留3",type="String",isNecessary="Y",desc="PARAM_VALUE3:预留3")
	private String PARAM_VALUE3;
	
	@ZteSoftCommentAnnotationParam(name="预留4",type="String",isNecessary="Y",desc="PARAM_VALUE4:预留4")
	private String PARAM_VALUE4;

	@Override
	public String getBodyContents(){//由子类复写
		StringBuffer bodyContents = new StringBuffer();
//		getRespBody();//保证在此之前调用过getRespBody()方法
		if(null!=bodyArray&&bodyArray.length>0){//有包体
			bodyContents.append("\"X_RESULTCODE\":\"").append(getX_RESULTCODE()).append("\"")
			.append(",\"X_RESULTINFO\":\"").append(getX_RESULTINFO()).append("\"")
			.append(",\"X_RECORDNUM\":\"").append(getX_RECORDNUM()).append("\"")
			.append(",\"USER_ID\":\"").append(getUSER_ID()).append("\"")
			.append(",\"SERIAL_NUMBER\":\"").append(getSERIAL_NUMBER()).append("\"")
			.append(",\"STATE\":\"").append(getSTATE()).append("\"")
			.append(",\"PARAM_VALUE1\":\"").append(getPARAM_VALUE1()).append("\"")
			.append(",\"PARAM_VALUE2\":\"").append(getPARAM_VALUE2()).append("\"")
			.append(",\"PARAM_VALUE3\":\"").append(getPARAM_VALUE3()).append("\"")
			.append(",\"PARAM_VALUE4\":\"").append(getPARAM_VALUE4()).append("\"");
		}
		return bodyContents.toString();
	}
	
	public String getX_RESULTCODE() {
		if(null!=bodyArray&&bodyArray.length>0){
			X_RESULTCODE = bodyArray[0];
		}else{
			X_RESULTCODE = "";
		}
		return X_RESULTCODE;
	}

	public void setX_RESULTCODE(String x_RESULTCODE) {
		X_RESULTCODE = x_RESULTCODE;
	}

	public String getX_RESULTINFO() {
		if(null!=bodyArray&&bodyArray.length>1){
			X_RESULTINFO = bodyArray[1];
		}else{
			X_RESULTINFO = "";
		}
		return X_RESULTINFO;
	}

	public void setX_RESULTINFO(String x_RESULTINFO) {
		X_RESULTINFO = x_RESULTINFO;
	}

	public String getX_RECORDNUM() {
		if(null!=bodyArray&&bodyArray.length>2){
			X_RECORDNUM = bodyArray[2];
		}else{
			X_RECORDNUM = "";
		}
		return X_RECORDNUM;
	}

	public void setX_RECORDNUM(String x_RECORDNUM) {
		X_RECORDNUM = x_RECORDNUM;
	}

	public String getUSER_ID() {
		if(null!=bodyArray&&bodyArray.length>3){
			USER_ID = bodyArray[3];
		}else{
			USER_ID = "";
		}
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getSERIAL_NUMBER() {
		if(null!=bodyArray&&bodyArray.length>4){
			SERIAL_NUMBER = bodyArray[4];
		}else{
			SERIAL_NUMBER= "";
		}
		return SERIAL_NUMBER;
	}

	public void setSERIAL_NUMBER(String sERIAL_NUMBER) {
		SERIAL_NUMBER = sERIAL_NUMBER;
	}

	public String getSTATE() {
		if(null!=bodyArray&&bodyArray.length>5){
			STATE = bodyArray[5];
		}else{
			STATE = "";
		}
		return STATE;
	}

	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}

	public String getPARAM_VALUE1() {
		if(null!=bodyArray&&bodyArray.length>6){
			PARAM_VALUE1 = bodyArray[6];
		}else{
			PARAM_VALUE1 = "";
		}
		return PARAM_VALUE1;
	}

	public void setPARAM_VALUE1(String pARAM_VALUE1) {
		PARAM_VALUE1 = pARAM_VALUE1;
	}

	public String getPARAM_VALUE2() {
		if(null!=bodyArray&&bodyArray.length>7){
			PARAM_VALUE2 = bodyArray[7];
		}else{
			PARAM_VALUE2 = "";
		}
		return PARAM_VALUE2;
	}

	public void setPARAM_VALUE2(String pARAM_VALUE2) {
		PARAM_VALUE2 = pARAM_VALUE2;
	}

	public String getPARAM_VALUE3() {
		if(null!=bodyArray&&bodyArray.length>8){
			PARAM_VALUE3 = bodyArray[8];
		}else{
			PARAM_VALUE3 = "";
		}
		return PARAM_VALUE3;
	}

	public void setPARAM_VALUE3(String pARAM_VALUE3) {
		PARAM_VALUE3 = pARAM_VALUE3;
	}

	public String getPARAM_VALUE4() {
		if(null!=bodyArray&&bodyArray.length>9){
			PARAM_VALUE4 = bodyArray[9];
		}else{
			PARAM_VALUE4 = "";
		}
		return PARAM_VALUE4;
	}

	public void setPARAM_VALUE4(String pARAM_VALUE4) {
		PARAM_VALUE4 = pARAM_VALUE4;
	}
	
}
