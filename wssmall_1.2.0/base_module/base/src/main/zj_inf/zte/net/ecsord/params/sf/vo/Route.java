package zte.net.ecsord.params.sf.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Route implements Serializable {

	private static final long serialVersionUID = -3167146946575881101L;
	
	@ZteSoftCommentAnnotationParam(name="路由发生的时间",type="String",isNecessary="Y",desc="accept_time：路由发生的时间")
	private String accept_time;
	@ZteSoftCommentAnnotationParam(name="路由发生的地点",type="String",isNecessary="Y",desc="accept_address：路由发生的地点")
	private String accept_address;
	@ZteSoftCommentAnnotationParam(name="路由具体描述",type="String",isNecessary="Y",desc="remark：路由具体描述")
	private String remark;
	@ZteSoftCommentAnnotationParam(name="操作码",type="String",isNecessary="Y",desc="opcode：操作码")
	private String opcode;
	
	public String getAccept_time() {
		return accept_time;
	}
	public void setAccept_time(String accept_time) {
		this.accept_time = accept_time;
	}
	public String getAccept_address() {
		return accept_address;
	}
	public void setAccept_address(String accept_address) {
		this.accept_address = accept_address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOpcode() {
		return opcode;
	}
	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}
	
}
