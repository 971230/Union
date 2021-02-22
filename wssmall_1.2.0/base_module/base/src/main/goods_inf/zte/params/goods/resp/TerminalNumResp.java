package zte.params.goods.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class TerminalNumResp extends ZteResponse {
	
	private static final long serialVersionUID = -5134380226424504632L;
	
	@ZteSoftCommentAnnotationParam(name="终端串号",type="String",isNecessary="Y",desc="terminal_no：终端串号")
	private String terminal_no; 					//终端串号

	public String getTerminal_no() {
		return terminal_no;
	}

	public void setTerminal_no(String terminal_no) {
		this.terminal_no = terminal_no;
	}
}
