package zte.net.ecsord.params.bss.req;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.drools.core.util.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.InfConst;
import zte.net.ecsord.common.StypeConsts;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
/**
 * H2接口方式
 * @author Rapon
 *
 *以下(位)表示(字节)
 *
 *所有字段get方法支持复写,部分字段get方法必须根据实际业务复写
 */
public class BaseBSSReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	protected String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name = "包头", type = "String", isNecessary = "Y", desc = "包头,由a0~a11组装而成(86位)")
	protected String head;

	@ZteSoftCommentAnnotationParam(name = "包体", type = "String", isNecessary = "Y", desc = "具体业务请求内容,不同业务包体结构有异")
	protected String body;

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

	@ZteSoftCommentAnnotationParam(name = "字段之间间隔符 ", type = "int", isNecessary = "Y", desc = "字段之间间隔符")
	protected String tab = InfConst.SOCKET_TAB;//这个变量改变的可能性最低

	@ZteSoftCommentAnnotationParam(name = "包结束标识 ", type = "int", isNecessary = "Y", desc = "包结束标识")
	protected String end = InfConst.SOCKET_END;//这个变量改变的可能性最低
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getHead() {//包头由a1~a11按序组装
		if(StringUtils.isEmpty(head)){
			head = new StringBuffer().append(getA0()).append(getA1()).append(getA2()).append(getA3()).append(getA4()).append(getA5())
					.append(getA6()).append(getA7()).append(getA8()).append(getA9()).append(getA10()).append(getA11()).toString();
		}
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getBody() {//此方法子类必须复写
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getA0() {
		if(null==a0||"".equals(a0)){
			a0 = InfConst.SOCKET_VERSION;
		}
		return a0;
	}

	public void setA0(String a0) {
		this.a0 = a0;
	}

	public String getA1() {//整个报文长度由包体长度决定,计算前一定要保证包体已经组装完成且不会再变化
		if(null==a1||"".equals(a1)){
			int length = 86//包头长度固定
					+ StringUtil.countOfByte(getBody())//包体长度(字节数)
					+ 1;//结束符
//			if(length >= 100){
//				a1 = length+"  ";
//			}else{
//				a1 = length+"   ";
//			}
			a1 = length + "";
			while(a1.length()<5){//不足右补空格（5位）
				a1 += " ";
			}
		}
		return a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public String getA2() {
		if(null==a2||"".equals(a2)){
			a2 = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime())+"      ";
		}
		return a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA3() {
		if(null==a3||"".equals(a3)){
			a3 = "1";
		}
		return a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	public String getA4() {//服务类型,需要子类复写
		return a4;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}

	public String getA5() {//需要子类复写,保证20位长度
//		if(null==a5||"".equals(a5)){
			a5 = "                    ";
//		}
		return a5;
	}

	public void setA5(String a5) {//部分业务需要子类复写
		this.a5 = a5;
	}

	public String getA6() {//子类可能需要复写
		if(null==a6||"".equals(a6)){
			a6 = "G";
		}
		return a6;
	}

	public void setA6(String a6) {
		this.a6 = a6;
	}

	public String getA7() {
		if(null==a7||"".equals(a7)){//此字段在invoker会根据inf_comm_client_request_user表数据重新匹配
			a7 = InfConst.A7;
		}
		return a7;
	}

	public void setA7(String a7) {
		this.a7 = a7;
	}

	public String getA8() {
		if(null==a8||"".equals(a8)){//此字段在invoker会根据inf_comm_client_request_user表数据重新匹配
			a8 = InfConst.A8;
		}
		return a8;
	}

	public void setA8(String a8) {
		this.a8 = a8;
	}

	public String getA9() {
		if(null==a9||"".equals(a9)){
			a9 = "1    ";
		}
		return a9;
	}

	public void setA9(String a9) {
		this.a9 = a9;
	}

	public String getA10() {
		if(null==a10||"".equals(a10)){
			a10 = "1";
		}
		return a10;
	}

	public void setA10(String a10) {
		this.a10 = a10;
	}

	public String getA11() {
		if(null==a11||"".equals(a11)){
			a11 = "00000";
		}
		return a11;
	}

	public void setA11(String a11) {
		this.a11 = a11;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		
		return "zte.net.iservice.impl.infservices.BSSH2Interface";
	}

	public String getOp_Code(){//这个由子类复写以区分调用的接口
		return "bss.H2Interface";//数据库不配这个op_code，没有实际用处
	}
}
