package com.ztesoft.remote.basic.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.remote.basic.params.resp.IncomeCountResponse;
import com.ztesoft.remote.basic.utils.BasicConst;
import params.ZteRequest;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-13 14:51
 * To change this template use File | Settings | File Templates.
 */
public class IncomeCountRequest extends ZteRequest<IncomeCountResponse>{
    @Override
    public void check() throws ApiRuleException {

    }

    @Override
    public String getApiMethodName() {
        return BasicConst.API_PREFIX+"queryIncomeCount";
    }
}
