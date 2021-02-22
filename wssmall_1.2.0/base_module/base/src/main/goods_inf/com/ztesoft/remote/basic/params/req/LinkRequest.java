package com.ztesoft.remote.basic.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.remote.basic.params.resp.LinkResponse;
import com.ztesoft.remote.basic.utils.BasicConst;
import params.ZteRequest;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-13 11:00
 * To change this template use File | Settings | File Templates.
 */
public class LinkRequest extends ZteRequest<LinkResponse> {
    @Override
    public void check() throws ApiRuleException {

    }

    @Override
    public String getApiMethodName() {
        return BasicConst.API_PREFIX+"queryLink";
    }
}
