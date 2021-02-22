package zte.net.ecsord.params.ecaop.req;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ChannelConvertQrySubReq extends ZteRequest {

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
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getApiMethodName() {
        return "ecaop.trades.query.channel.convert.qry";
    }

}
