package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.util.List;

public class CHECKCREATECARDRESULTRSPVO {
    private String RESP_CODE;//应答编码 0000 成功
    private String  RESP_DESC;//应答描述
    private List<PARAVO>  PARA;
    private String ICCID;
    private String CARD_TYPE;
    private String CARD_BIG_TYPE;
    private String MATERIAL_CODE;
    private String IMSI;
    private String CARD_NAME;
    public String getRESP_CODE() {
        return RESP_CODE;
    }
    public void setRESP_CODE(String rESP_CODE) {
        RESP_CODE = rESP_CODE;
    }
    public String getRESP_DESC() {
        return RESP_DESC;
    }
    public void setRESP_DESC(String rESP_DESC) {
        RESP_DESC = rESP_DESC;
    }
    public List<PARAVO> getPARA() {
        return PARA;
    }
    public void setPARA(List<PARAVO> pARA) {
        PARA = pARA;
    }
    public String getICCID() {
        return ICCID;
    }
    public void setICCID(String iCCID) {
        ICCID = iCCID;
    }
    public String getCARD_TYPE() {
        return CARD_TYPE;
    }
    public void setCARD_TYPE(String cARD_TYPE) {
        CARD_TYPE = cARD_TYPE;
    }
    public String getCARD_BIG_TYPE() {
        return CARD_BIG_TYPE;
    }
    public void setCARD_BIG_TYPE(String cARD_BIG_TYPE) {
        CARD_BIG_TYPE = cARD_BIG_TYPE;
    }
    public String getMATERIAL_CODE() {
        return MATERIAL_CODE;
    }
    public void setMATERIAL_CODE(String mATERIAL_CODE) {
        MATERIAL_CODE = mATERIAL_CODE;
    }
    public String getIMSI() {
        return IMSI;
    }
    public void setIMSI(String iMSI) {
        IMSI = iMSI;
    }
    public String getCARD_NAME() {
        return CARD_NAME;
    }
    public void setCARD_NAME(String cARD_NAME) {
        CARD_NAME = cARD_NAME;
    }
   
}
