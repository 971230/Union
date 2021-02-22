package zte.params.number.req;

import params.ZteRequest;
import zte.params.number.resp.NoInfoByNoResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 获取号码信息
 * @author deng.yx
 *
 */
public class NoInfoByNoReq extends ZteRequest<NoInfoByNoResp> {
	
	@ZteSoftCommentAnnotationParam(name="号码", type="String", isNecessary="Y", desc="no：手机号码")
	private String no;
	
	public String getNo() {
		return no;
	}


	public void setNo(String no) {
		this.no = no;
	}


	public Class<NoInfoByNoResp> getResponseClass() {
		return NoInfoByNoResp.class;
	}


	@Override
    public void check() throws ApiRuleException {
        if (ApiUtils.isBlank(no)) {
            throw new ApiRuleException("-1", "号码不能为空!");
        }
    }

    @Override
    public String getApiMethodName() {
        return "zte.service.no.queryNoInfoByNo";
    }
}
