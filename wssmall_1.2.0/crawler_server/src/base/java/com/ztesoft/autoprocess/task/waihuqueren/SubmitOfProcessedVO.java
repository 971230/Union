package com.ztesoft.autoprocess.task.waihuqueren;

/**
 * 外呼确认处理完毕,提交参数
 * @author shusx
 */
public class SubmitOfProcessedVO {
    private String orderId;
    private String tmplId;
    private String goodsInstId;
    private String certType;
    private String certNum;
    
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getTmplId() {
        return tmplId;
    }
    public void setTmplId(String tmplId) {
        this.tmplId = tmplId;
    }
    public String getGoodsInstId() {
        return goodsInstId;
    }
    public void setGoodsInstId(String goodsInstId) {
        this.goodsInstId = goodsInstId;
    }
    public String getCertType() {
        return certType;
    }
    public void setCertType(String certType) {
        this.certType = certType;
    }
    public String getCertNum() {
        return certNum;
    }
    public void setCertNum(String certNum) {
        this.certNum = certNum;
    }
}
