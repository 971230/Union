package zte.net.ecsord.params.bss.vo;

import java.io.Serializable;

import zte.net.ecsord.common.InfConst;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class GDBssSocketHead implements Serializable {

	private static final long serialVersionUID = -2428604904273998719L;

	@ZteSoftCommentAnnotationParam(name = "版本号信息", type = "String", isNecessary = "Y", desc = "标识当前使用的协议版本号（2位）")
	private String a0;

	@ZteSoftCommentAnnotationParam(name = "数据包大小", type = "String", isNecessary = "Y", desc = "计算[包头+包体]以字节为单位，不足右补空格（5位）")
	private String a1;

	@ZteSoftCommentAnnotationParam(name = "业务流水号", type = "String", isNecessary = "Y", desc = "标志每笔具体交易。数据校验包可以无流水号（20位）")
	private String a2;

	@ZteSoftCommentAnnotationParam(name = "标志 ", type = "String", isNecessary = "Y", desc = " 1表示成功 0表示失败，仅适用于响应包（1位） ")
	private String a3;

	@ZteSoftCommentAnnotationParam(name = "服务类型 ", type = "String", isNecessary = "Y", desc = " 具体见接口列表（12位）")
	private String a4;

	@ZteSoftCommentAnnotationParam(name = "业务号码 ", type = "String", isNecessary = "Y", desc = "请求号码 (例如020xxxxxxxx、0755xxxxxxxx)（20位）")
	private String a5;

	@ZteSoftCommentAnnotationParam(name = "业务号码类型", type = "String", isNecessary = "Y", desc = "业务号码类型（1位）")
	private String a6;

	@ZteSoftCommentAnnotationParam(name = "营业点", type = "String", isNecessary = "Y", desc = "业务受理地点（6位）")
	private String a7;

	@ZteSoftCommentAnnotationParam(name = "营业员", type = "String", isNecessary = "Y", desc = "业务受理人（8位）")
	private String a8;

	@ZteSoftCommentAnnotationParam(name = "包编号", type = "String", isNecessary = "Y", desc = "标志该笔流水的第几包数据（5位）")
	private String a9;

	@ZteSoftCommentAnnotationParam(name = "最后一包标志 ", type = "String", isNecessary = "Y", desc = "最后一包标志（1位） 在进行多包发送的情况下，该标志用以标明是否为最后一数据包。1最后一包数据，无后续包；0非最后一包数据，有后续包，连接错误，I/O错误等")
	private String a10;

	@ZteSoftCommentAnnotationParam(name = "错误码 ", type = "String", isNecessary = "Y", desc = "在标志为失败时需要检查该错误码。错误码包括系统操作错误和业务处理错误（5位）")
	private String a11;

	@ZteSoftCommentAnnotationParam(name = "字段之间间隔符 ", type = "int", isNecessary = "Y", desc = "字段之间间隔符")
	private String tab;

	@ZteSoftCommentAnnotationParam(name = "包结束标识 ", type = "int", isNecessary = "Y", desc = "包结束标识")
	private String end;

	public String getA0() {
		return InfConst.SOCKET_VERSION;
	}

	public void setA0(String a0) {
		this.a0 = a0;
	}

	public String getA1() {
		return a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public String getA2() {
		return a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA3() {
		this.setA3("1");
		return this.a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	public String getA4() {
		return this.a4;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}

	public String getA5() {
		return a5;
	}

	public void setA5(String a5) {
		this.a5 = a5;
	}

	public String getA6() {
		this.setA6(" ");
		return this.a6;
	}

	public void setA6(String a6) {
		this.a6 = a6;
	}

	public String getA7() {
		this.setA7(InfConst.A7);
		return this.a7;
	}

	public void setA7(String a7) {
		this.a7 = a7;
	}

	public String getA8() {
		this.setA8(InfConst.A8);
		return this.a8;
	}

	public void setA8(String a8) {
		this.a8 = a8;
	}

	public String getA9() {
		this.setA9("1    ");
		return this.a9;
	}

	public void setA9(String a9) {
		this.a9 = a9;
	}

	public String getA10() {
		this.setA10("1");
		return this.a10;
	}

	public void setA10(String a10) {
		this.a10 = a10;
	}

	public String getA11() {
		this.setA11("00000");
		return this.a11;
	}

	public void setA11(String a11) {
		this.a11 = a11;
	}

	public String getTab() {
		this.setTab(InfConst.SOCKET_TAB);
		return this.tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public String getEnd() {
		this.setEnd(InfConst.SOCKET_END);
		return this.end;
	}

	public void setEnd(String end) {
		this.end = end;
	}



}
