package zte.net.ecsord.params.bss.resp;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.common.InfConst;
import zte.net.ecsord.params.base.resp.ZteBusiResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


/**
 * H2接口方式
 * 
 * 返回对象
 */
public class BaseBSSResp extends ZteBusiResponse {

	@ZteSoftCommentAnnotationParam(name="返回原始报文",type="String",isNecessary="Y",desc="respStr:返回原始报文")
	protected String respStr;

	@ZteSoftCommentAnnotationParam(name="包头",type="String",isNecessary="Y",desc="respHead:包头")
	protected String respHead;

	@ZteSoftCommentAnnotationParam(name="包体",type="String",isNecessary="Y",desc="respBody:包体")
	protected String respBody;
	
	@ZteSoftCommentAnnotationParam(name = "包体数组", type = "String", isNecessary = "Y", desc = "将包体以分隔符分为数组")
	protected String[] bodyArray;

	@ZteSoftCommentAnnotationParam(name = "版本号信息", type = "String", isNecessary = "Y", desc = "标识当前使用的协议版本号（2位）")
	protected String a0;

	@ZteSoftCommentAnnotationParam(name = "数据包大小", type = "String", isNecessary = "Y", desc = "计算[包头+包体]以字节为单位，不足右补空格（5位）")
	protected String a1;

	@ZteSoftCommentAnnotationParam(name = "业务流水号", type = "String", isNecessary = "Y", desc = "标志每笔具体交易。数据校验包可以无流水号（20位）")
	protected String a2;

	@ZteSoftCommentAnnotationParam(name = "标志 ", type = "String", isNecessary = "Y", desc = " 1表示成功 0表示失败，仅适用于响应包（1位） ")
	protected String a3;

	@ZteSoftCommentAnnotationParam(name = "服务类型 ", type = "String", isNecessary = "Y", desc = " 具体见接口列表（12位）")
	protected String a4;

	@ZteSoftCommentAnnotationParam(name = "业务号码 ", type = "String", isNecessary = "Y", desc = "请求号码 (例如020xxxxxxxx、0755xxxxxxxx)（20位）")
	protected String a5;

	@ZteSoftCommentAnnotationParam(name = "业务号码类型", type = "String", isNecessary = "Y", desc = "业务号码类型（1位）")
	protected String a6;

	@ZteSoftCommentAnnotationParam(name = "营业点", type = "String", isNecessary = "Y", desc = "业务受理地点（6位）")
	protected String a7;

	@ZteSoftCommentAnnotationParam(name = "营业员", type = "String", isNecessary = "Y", desc = "业务受理人（8位）")
	protected String a8;

	@ZteSoftCommentAnnotationParam(name = "包编号", type = "String", isNecessary = "Y", desc = "标志该笔流水的第几包数据（5位）")
	protected String a9;

	@ZteSoftCommentAnnotationParam(name = "最后一包标志 ", type = "String", isNecessary = "Y", desc = "最后一包标志（1位） 在进行多包发送的情况下，该标志用以标明是否为最后一数据包。1最后一包数据，无后续包；0非最后一包数据，有后续包，连接错误，I/O错误等")
	protected String a10;

	@ZteSoftCommentAnnotationParam(name = "错误码 ", type = "String", isNecessary = "Y", desc = "在标志为失败时需要检查该错误码。错误码包括系统操作错误和业务处理错误（5位）")
	protected String a11;

	public String getRespStr() {
		if(null==respStr){
			respStr = "";
		}
		return respStr;
	}

	public void setRespStr(String respStr) {
		this.respStr = respStr;
	}

	public String getRespHead() {
		if(StringUtils.isEmpty(respHead)&&getRespStr().length()>=87){
			respHead = respStr.substring(0, 86);
		}
		return respHead;
	}

	public void setRespHead(String respHead) {
		this.respHead = respHead;
	}

	public String getRespBody() {
		if((null==respBody||"".equals(respBody))
				&&getRespStr().length()>87){//表示有包体内容
			respBody = respStr.substring(86, respStr.length()-1);//去掉包头和结束符,包体至少有1位
			bodyArray = respBody.split(InfConst.SOCKET_TAB);//包体以分隔符分割为数组
		}
		return respBody;
	}

	public void setRespBody(String respBody) {
		this.respBody = respBody;
	}

	public String getA0() {
		if((null==a0||"".equals(a0))
				&&getRespHead().length()==86){
			a0 = respHead.substring(0, 2);
		}
		return a0;
	}

	public void setA0(String a0) {
		this.a0 = a0;
	}

	public String getA1() {
		if((null==a1||"".equals(a1))
				&&getRespHead().length()==86){
			a1 = respHead.substring(2, 7);
		}
		return a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public String getA2() {
		if((null==a2||"".equals(a2))
				&&getRespHead().length()==86){
			a2 = respHead.substring(7, 27);
		}
		return a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA3() {
		if((null==a3||"".equals(a3))
				&&getRespHead().length()==86){
			a3 = respHead.substring(27, 28);
		}
		return a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	public String getA4() {
		if((null==a4||"".equals(a4))
				&&getRespHead().length()==86){
			a4 = respHead.substring(28, 40);
		}
		return a4;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}

	public String getA5() {
		if((null==a5||"".equals(a5))
				&&getRespHead().length()==86){
			a5 = respHead.substring(40, 60);
		}
		return a5;
	}

	public void setA5(String a5) {
		this.a5 = a5;
	}

	public String getA6() {
		if((null==a6||"".equals(a6))
				&&getRespHead().length()==86){
			a6 = respHead.substring(60, 61);
		}
		return a6;
	}

	public void setA6(String a6) {
		this.a6 = a6;
	}

	public String getA7() {
		if((null==a7||"".equals(a7))
				&&getRespHead().length()==86){
			a7 = respHead.substring(61, 67);
		}
		return a7;
	}

	public void setA7(String a7) {
		this.a7 = a7;
	}

	public String getA8() {
		if((null==a8||"".equals(a8))
				&&getRespHead().length()==86){
			a8 = respHead.substring(67, 75);
		}
		return a8;
	}

	public void setA8(String a8) {
		this.a8 = a8;
	}

	public String getA9() {
		if((null==a9||"".equals(a9))
				&&getRespHead().length()==86){
			a9 = respHead.substring(75, 80);
		}
		return a9;
	}

	public void setA9(String a9) {
		this.a9 = a9;
	}

	public String getA10() {
		if((null==a10||"".equals(a10))
				&&getRespHead().length()==86){
			a10 = respHead.substring(80, 81);
		}
		return a10;
	}

	public void setA10(String a10) {
		this.a10 = a10;
	}

	public String getA11() {
		if((null==a11||"".equals(a11))
				&&getRespHead().length()==86){
			a11 = respHead.substring(81, 86);
		}
		return a11;
	}

	public void setA11(String a11) {
		this.a11 = a11;
	}

	@Override
	public String toString() {//Invoker对象实例化后,首先调用toString方法
		StringBuffer returnStr = new StringBuffer();
		returnStr.append("{")
		.append("\"respStr\":\"").append(getRespStr()).append("\"")//返回原始报文
		.append(",").append("\"head\":\"").append(getRespHead()).append("\"")//包头完整报文
		.append(",").append("\"body\":\"").append(getRespBody()).append("\"")//包体完整报文
		.append(",\"headContents\":{").append(getHeadContents()).append("}")//包头拆分
		.append(",\"bodyContents\":{").append(getBodyContents()).append("}");//包体拆分
		returnStr.append("}");
		return returnStr.toString();
	}

	private String getHeadContents(){
		StringBuffer headContents = new StringBuffer();
//		getRespHead();//保证在此之前调用过getRespHead()方法
		if(null!=respHead&&respHead.length()==86){//包头不符合协议则拆分为空
			headContents
			.append("\"A0\":\"").append(getA0()).append("\"")
			.append(",\"A1\":\"").append(getA1()).append("\"")
			.append(",\"A2\":\"").append(getA2()).append("\"")
			.append(",\"A3\":\"").append(getA3()).append("\"")
			.append(",\"A4\":\"").append(getA4()).append("\"")
			.append(",\"A5\":\"").append(getA5()).append("\"")
			.append(",\"A6\":\"").append(getA6()).append("\"")
			.append(",\"A7\":\"").append(getA7()).append("\"")
			.append(",\"A8\":\"").append(getA8()).append("\"")
			.append(",\"A9\":\"").append(getA9()).append("\"")
			.append(",\"A10\":\"").append(getA10()).append("\"")
			.append(",\"A11\":\"").append(getA11()).append("\"");
		}
		return headContents.toString();
	}

	public String getBodyContents(){//由子类复写
		return "";
	}
}
