package com.ztesoft.net.filter.response;

public class ExpFilterResponse extends FilterResponse {
	private String write_flag; //异常写入类型   默认为 write  invisible 不展示     not_write 不写入

	public String getWrite_flag() {
		return write_flag;
	}

	public void setWrite_flag(String write_flag) {
		this.write_flag = write_flag;
	}
	
}
