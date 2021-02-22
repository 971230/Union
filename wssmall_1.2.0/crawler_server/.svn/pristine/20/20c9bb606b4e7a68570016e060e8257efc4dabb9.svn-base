package com.ztesoft.autoprocess.task.waihuqueren;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 异步提交请求,返回Json数据对象(身份证校验)
 * @author shusx
 */
public class RespCertInfoVO {
    @JsonProperty("RespCode")
    private String respCode;
    
    @JsonProperty("RespDesc")
    private String respDesc;
    
    private String certId;
    private String certName;
    private String province;
    private String city;
    private Integer age;
    @JsonProperty("CERT_CHECK_RSP_DETAIL")
    private String certCheckRspDetail;
    @JsonProperty("CERT_CHECK_RSP_CODE")
    private String certCheckRspCode;
    
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

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @JsonIgnore
    public String getCertCheckRspDetail() {
        return certCheckRspDetail;
    }

    @JsonIgnore
    public void setCertCheckRspDetail(String certCheckRspDetail) {
        this.certCheckRspDetail = certCheckRspDetail;
    }

    @JsonIgnore
    public String getCertCheckRspCode() {
        return certCheckRspCode;
    }

    @JsonIgnore
    public void setCertCheckRspCode(String certCheckRspCode) {
        this.certCheckRspCode = certCheckRspCode;
    }
}
