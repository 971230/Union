package zte.net.iservice.params.goodsOrg.req;

import params.ZteRequest;
import zte.net.iservice.params.goodsOrg.resp.GoodsOrgResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.utils.SysConst;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;

/**
 * 
 * @author deng.yx
 * 发布组织请求实体
 *
 */
public class GoodsOrgReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="发布组织标识", type="String", isNecessary="Y", desc="party_id：发布组织标识")
	private String party_id;
	
	public Class<GoodsOrgResp> getResponseClass() {
		return GoodsOrgResp.class;
	}


	@Override
    public void check() throws ApiRuleException {
        if (ApiUtils.isBlank(party_id)) {
            throw new ApiRuleException("-1","发布组织标识【party_id】不能为空!");
        }
    }

    @Override
    public String getApiMethodName() {
        return SysConst.API_PREFIX + "queryGoodsOrg";
    }


	public String getParty_id() {
		return party_id;
	}


	public void setParty_id(String party_id) {
		this.party_id = party_id;
	}




}
