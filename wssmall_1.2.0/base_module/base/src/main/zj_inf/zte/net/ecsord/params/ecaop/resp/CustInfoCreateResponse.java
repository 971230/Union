package zte.net.ecsord.params.ecaop.resp;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.resp.vo.CustInfoCreateRspVo;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

@SuppressWarnings("all")
public class CustInfoCreateResponse  extends ZteResponse{
    private static final long serialVersionUID = 1L;
    @ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
    private String res_code;
    @ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
    private String res_message;
    @ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
    private CustInfoCreateRspVo resultMsg;
    public String getRes_code() {
        return res_code;
    }
    public void setRes_code(String res_code) {
        this.res_code = res_code;
    }
    public String getRes_message() {
        return res_message;
    }
    public void setRes_message(String res_message) {
        this.res_message = res_message;
    }

    public CustInfoCreateRspVo getResultMsg() {
        return resultMsg;
    }
    public void setResultMsg(CustInfoCreateRspVo resultMsg) {
        this.resultMsg = resultMsg;
    }
}
