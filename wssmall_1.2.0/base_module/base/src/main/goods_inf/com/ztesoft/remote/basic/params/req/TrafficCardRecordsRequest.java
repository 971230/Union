package com.ztesoft.remote.basic.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.remote.basic.params.resp.TrafficCardRecordsResponse;
import com.ztesoft.remote.basic.utils.BasicConst;
import params.ZteRequest;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-12 09:16
 * To change this template use File | Settings | File Templates.
 */
public class TrafficCardRecordsRequest extends ZteRequest<TrafficCardRecordsResponse>{
    @Override
    public void check() throws ApiRuleException {

    }

    @Override
    public String getApiMethodName() {
        return BasicConst.API_PREFIX+"trafficCardRecords";
    }
}
