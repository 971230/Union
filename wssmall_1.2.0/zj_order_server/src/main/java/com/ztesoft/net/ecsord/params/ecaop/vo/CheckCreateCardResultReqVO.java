package com.ztesoft.net.ecsord.params.ecaop.vo;
public class CheckCreateCardResultReqVO {
    private String STAFF_ID; //操作员ID
    private String PROVINCE_CODE;//省份编码
    private String CITY_CODE;//地市编码
    private String DISTRICT_CODE;//县分编码
    private String CHANNEL_ID;//渠道编码
    private String CHANNEL_TYPE;//渠道类型
    private String SYS_CODE;
    private String REQ_NO;//调用方订单编码
    private String ICCID;//卡号
    private String SERIAL_NUMBER;//号码
    private String TRADE_TYPE;//业务类型
    private PARAVO PARA;//保留字段（暂未使用）
    public String getSTAFF_ID() {
        return STAFF_ID;
    }
    public void setSTAFF_ID(String sTAFF_ID) {
        STAFF_ID = sTAFF_ID;
    }
    public String getPROVINCE_CODE() {
        return PROVINCE_CODE;
    }
    public void setPROVINCE_CODE(String pROVINCE_CODE) {
        PROVINCE_CODE = pROVINCE_CODE;
    }
    public String getCITY_CODE() {
        return CITY_CODE;
    }
    public void setCITY_CODE(String cITY_CODE) {
        CITY_CODE = cITY_CODE;
    }
    public String getDISTRICT_CODE() {
        return DISTRICT_CODE;
    }
    public void setDISTRICT_CODE(String dISTRICT_CODE) {
        DISTRICT_CODE = dISTRICT_CODE;
    }
    public String getCHANNEL_ID() {
        return CHANNEL_ID;
    }
    public void setCHANNEL_ID(String cHANNEL_ID) {
        CHANNEL_ID = cHANNEL_ID;
    }
    public String getCHANNEL_TYPE() {
        return CHANNEL_TYPE;
    }
    public void setCHANNEL_TYPE(String cHANNEL_TYPE) {
        CHANNEL_TYPE = cHANNEL_TYPE;
    }
    public String getSYS_CODE() {
        return SYS_CODE;
    }
    public void setSYS_CODE(String sYS_CODE) {
        SYS_CODE = sYS_CODE;
    }
    public String getREQ_NO() {
        return REQ_NO;
    }
    public void setREQ_NO(String rEQ_NO) {
        REQ_NO = rEQ_NO;
    }
    public String getICCID() {
        return ICCID;
    }
    public void setICCID(String iCCID) {
        ICCID = iCCID;
    }
    public String getSERIAL_NUMBER() {
        return SERIAL_NUMBER;
    }
    public void setSERIAL_NUMBER(String sERIAL_NUMBER) {
        SERIAL_NUMBER = sERIAL_NUMBER;
    }
    public String getTRADE_TYPE() {
        return TRADE_TYPE;
    }
    public void setTRADE_TYPE(String tRADE_TYPE) {
        TRADE_TYPE = tRADE_TYPE;
    }
    public PARAVO getPARA() {
        return PARA;
    }
    public void setPARA(PARAVO pARA) {
        PARA = pARA;
    }
}
