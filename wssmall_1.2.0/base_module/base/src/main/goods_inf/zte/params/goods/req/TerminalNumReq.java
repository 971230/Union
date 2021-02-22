package zte.params.goods.req;

import params.ZteError;
import params.ZteRequest;
import zte.params.goods.resp.TerminalNumResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

public class TerminalNumReq extends ZteRequest<TerminalNumResp> {
	
	private static final long serialVersionUID = -8028202733264639408L;
	
	@ZteSoftCommentAnnotationParam(name="SN",type="String",isNecessary="Y",desc="SN")
	private String sn;
	
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(sn))
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"SN不能为空"));
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.goodsService.terminalNum.cache";
	}

}
