package com.ztesoft.autoprocess.task.waihuqueren;

/**
 * 外呼确认处理完毕,提交参数
 * @author shusx
 */
public class SubmitOfCheckCertVO {
    private String certId;
    private String certType;
    private String certName;
    private String orderId;
    
    public String getCertId() {
        return certId;
    }
    public void setCertId(String certId) {
        this.certId = certId;
    }
    public String getCertType() {
        return certType;
    }
    public void setCertType(String certType) {
        this.certType = certType;
    }
    public String getCertName() {
        return certName;
    }
    public void setCertName(String certName) {
        this.certName = certName;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
