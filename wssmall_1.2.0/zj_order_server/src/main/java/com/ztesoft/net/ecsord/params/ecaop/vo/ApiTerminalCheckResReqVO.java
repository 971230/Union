package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.util.List;

public class ApiTerminalCheckResReqVO {
    private String OPERATOR_ID; //操作员ID
    private String PROVINCE;//省分
    private String CITY;//地市
    private String DISTRICT;//县分编码
    private String CHANNEL_ID;//渠道编码
    private String CHANNEL_TYPE;//渠道类型
    private String SOURCE_SYSTEM;//BSS：南25BSSN6BSS：北六BSS CBSS：cBSS
    private String CHECK_TYPE;//校验的类型：01 可用 04 备机 GX 工单和串码的绑定关系
    private String FIX_ORDER_ID;//施工单ID，校验工单和串码的绑定关系时必填。
    private List<RESOURCES_INFOVO> RESOURCES_INFO;//资源信息
    private List<PARAVO> PARA;//保留字段（暂未使用）
    public String getOPERATOR_ID() {
        return OPERATOR_ID;
    }
    public void setOPERATOR_ID(String oPERATOR_ID) {
        OPERATOR_ID = oPERATOR_ID;
    }
    public String getPROVINCE() {
        return PROVINCE;
    }
    public void setPROVINCE(String pROVINCE) {
        PROVINCE = pROVINCE;
    }
    public String getCITY() {
        return CITY;
    }
    public void setCITY(String cITY) {
        CITY = cITY;
    }
    public String getDISTRICT() {
        return DISTRICT;
    }
    public void setDISTRICT(String dISTRICT) {
        DISTRICT = dISTRICT;
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
    public String getSOURCE_SYSTEM() {
        return SOURCE_SYSTEM;
    }
    public void setSOURCE_SYSTEM(String sOURCE_SYSTEM) {
        SOURCE_SYSTEM = sOURCE_SYSTEM;
    }
    public String getCHECK_TYPE() {
        return CHECK_TYPE;
    }
    public void setCHECK_TYPE(String cHECK_TYPE) {
        CHECK_TYPE = cHECK_TYPE;
    }
    public String getFIX_ORDER_ID() {
        return FIX_ORDER_ID;
    }
    public void setFIX_ORDER_ID(String fIX_ORDER_ID) {
        FIX_ORDER_ID = fIX_ORDER_ID;
    }
    
    public List<RESOURCES_INFOVO> getRESOURCES_INFO() {
        return RESOURCES_INFO;
    }
    public void setRESOURCES_INFO(List<RESOURCES_INFOVO> rESOURCES_INFO) {
        RESOURCES_INFO = rESOURCES_INFO;
    }
    public List<PARAVO> getPARA() {
        return PARA;
    }
    public void setPARA(List<PARAVO> pARA) {
        PARA = pARA;
    }
   
}
