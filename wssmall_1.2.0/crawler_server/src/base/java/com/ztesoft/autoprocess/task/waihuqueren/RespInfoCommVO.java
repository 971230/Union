package com.ztesoft.autoprocess.task.waihuqueren;

public class RespInfoCommVO {
    // 外呼确认处理完毕返回对象
    private RetInfoVO retInfo;

    // 校验身份证返回对象
    private RespCertInfoVO respInfo;

    public RetInfoVO getRetInfo() {
        return retInfo;
    }

    public void setRetInfo(RetInfoVO retInfo) {
        this.retInfo = retInfo;
    }

    public RespCertInfoVO getRespInfo() {
        return respInfo;
    }

    public void setRespInfo(RespCertInfoVO respInfo) {
        this.respInfo = respInfo;
    }
}
