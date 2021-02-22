package zte.net.ecsord.params.bss.resp;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;
import zte.net.ecsord.params.bss.vo.StaffList;
import zte.net.ecsord.params.bss.vo.StaffListResp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 查询操作员
 * @author songqi
 */
public class OrderListForWorkResp extends ZteBusiResponse {

	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "“00000”成功，其余为失败")
	private String code;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "返回类型的简单描述")
	private String msg;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "返回")
	private StaffListResp respJson;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public StaffListResp getRespJson() {
		return respJson;
	}

	public void setRespJson(StaffListResp respJson) {
		this.respJson = respJson;
	}
}
