package com.ztesoft.autoprocess.task.waihuqueren;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 异步提交请求,返回Json数据对象(外呼确认处理完毕)
 * @author shusx
 */
public class RetInfoVO {
    @JsonProperty("RespCode")
    private String respCode;
    
    @JsonProperty("RespDesc")
    private String respDesc;
    
    private String retFlag;
    
    @JsonIgnore
    public String getRespCode() {
        return respCode;
    }
    
    @JsonIgnore
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    
    @JsonIgnore
    public String getRespDesc() {
        return respDesc;
    }
    
    @JsonIgnore
    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public String getRetFlag() {
        return retFlag;
    }

    public void setRetFlag(String retFlag) {
        this.retFlag = retFlag;
    }
}
