package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class StdChannelConvertReq extends ZteRequest{
    @ZteSoftCommentAnnotationParam(name = "类型", type = "String", isNecessary = "Y", desc = "21：渠道编码(同时支持总部渠道和省份渠道，同时支持发展人和发展点)")
    private String op_type;
    @ZteSoftCommentAnnotationParam(name="编码",type="String",isNecessary="Y",desc="operatorId：操作员ID")
    private String op_value;
    
    public String getOp_type() {
        return op_type;
    }

    public void setOp_type(String op_type) {
        this.op_type = op_type;
    }

    public String getOp_value() {
        return op_value;
    }

    public void setOp_value(String op_value) {
        this.op_value = op_value;
    }

    @Override
    public void check() throws ApiRuleException {
        
    }

    @Override
    public String getApiMethodName() {
        return "ecaop.trades.query.channelStd.convert.qry";
    }
}
