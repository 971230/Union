package com.ztesoft.net.ecsord.params.ecaop.vo;

public class ResourCecenterChangeInfoRespVO {

	private SELECTEDNUMRSPVO SELECTED_NUM_RSP;
	
	private CHECKCREATECARDRESULTRSPVO CHECK_CREATE_CARD_RESULT_RSP;//成卡卡号返回
	
	private TERMINALCHECKRESRSPVO TERMINAL_CHECK_RES_RSP;
	
	public SELECTEDNUMRSPVO getSELECTED_NUM_RSP() {
		return SELECTED_NUM_RSP;
	}

	public void setSELECTED_NUM_RSP(SELECTEDNUMRSPVO sELECTED_NUM_RSP) {
		SELECTED_NUM_RSP = sELECTED_NUM_RSP;
	}

    public CHECKCREATECARDRESULTRSPVO getCHECK_CREATE_CARD_RESULT_RSP() {
        return CHECK_CREATE_CARD_RESULT_RSP;
    }

    public void setCHECK_CREATE_CARD_RESULT_RSP(
            CHECKCREATECARDRESULTRSPVO cHECK_CREATE_CARD_RESULT_RSP) {
        CHECK_CREATE_CARD_RESULT_RSP = cHECK_CREATE_CARD_RESULT_RSP;
    }

    public TERMINALCHECKRESRSPVO getTERMINAL_CHECK_RES_RSP() {
        return TERMINAL_CHECK_RES_RSP;
    }

    public void setTERMINAL_CHECK_RES_RSP(
            TERMINALCHECKRESRSPVO tERMINAL_CHECK_RES_RSP) {
        TERMINAL_CHECK_RES_RSP = tERMINAL_CHECK_RES_RSP;
    }
	
}
