package com.ztesoft.net.ecsord.params.ecaop.vo;
public class RESOURCES_INFO {
    private String RESOURCES_TYPE;//资源类型01 ONU终端 02 IPTV机顶盒 03 传统家庭网关 04 互联网电视机顶盒

    private String RESP_CODE;//资源状态编码： 0000 资源验证成功 0001 资源已被占 0002 无此资源信息0003 资源不是备机
       // 0004 库存状态是非在库
       // 0005 操作/装维人员不存在  
       // 0006  备机领用人信息不一致    
       //  0007 工单和串码不一致
       // 9999 其它失败原因
    private String RESP_DESC;//资源状态描述
    private String RESOURCES_CODE;//资源唯一标识
    private String RESOURCES_BRAND;//品牌
    private String RESOURCES_BRAND_DESC;//品牌描述
    private String RESOURCES_MODEL;//型号
    private String RESOURCES_MODEL_DESC;//型号描述
    private PARAVO PARA;
    public String getRESOURCES_TYPE() {
        return RESOURCES_TYPE;
    }
    public void setRESOURCES_TYPE(String rESOURCES_TYPE) {
        RESOURCES_TYPE = rESOURCES_TYPE;
    }
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
    public String getRESOURCES_CODE() {
        return RESOURCES_CODE;
    }
    public void setRESOURCES_CODE(String rESOURCES_CODE) {
        RESOURCES_CODE = rESOURCES_CODE;
    }
    public String getRESOURCES_BRAND() {
        return RESOURCES_BRAND;
    }
    public void setRESOURCES_BRAND(String rESOURCES_BRAND) {
        RESOURCES_BRAND = rESOURCES_BRAND;
    }
    public String getRESOURCES_BRAND_DESC() {
        return RESOURCES_BRAND_DESC;
    }
    public void setRESOURCES_BRAND_DESC(String rESOURCES_BRAND_DESC) {
        RESOURCES_BRAND_DESC = rESOURCES_BRAND_DESC;
    }
    public String getRESOURCES_MODEL() {
        return RESOURCES_MODEL;
    }
    public void setRESOURCES_MODEL(String rESOURCES_MODEL) {
        RESOURCES_MODEL = rESOURCES_MODEL;
    }
    public String getRESOURCES_MODEL_DESC() {
        return RESOURCES_MODEL_DESC;
    }
    public void setRESOURCES_MODEL_DESC(String rESOURCES_MODEL_DESC) {
        RESOURCES_MODEL_DESC = rESOURCES_MODEL_DESC;
    }
    public PARAVO getPARA() {
        return PARA;
    }
    public void setPARA(PARAVO pARA) {
        PARA = pARA;
    }
}
