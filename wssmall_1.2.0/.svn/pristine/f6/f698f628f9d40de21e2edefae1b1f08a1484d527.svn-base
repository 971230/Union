package zte.params.number.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class NoCoDeleteReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="同步对象", type="String", isNecessary="Y", desc="电话号码：如18600008888。")
	private String dn_no;

	public String getDn_no() {
		return dn_no;
	}

	public void setDn_no(String dn_no) {
		this.dn_no = dn_no;
	}

	@Override
	public void check() throws ApiRuleException {
		if (StringUtils.isEmpty((this.getDn_no()))) {
        	CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "号码不能为空"));
        }
	}

	@Override
	public String getApiMethodName() {
		return "com.ztesoft.remote.number.NoCoDelete";
	}

}
