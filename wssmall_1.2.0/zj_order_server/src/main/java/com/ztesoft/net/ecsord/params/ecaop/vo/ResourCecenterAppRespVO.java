package com.ztesoft.net.ecsord.params.ecaop.vo;

public class ResourCecenterAppRespVO {

	private SELECTEDNUMRSPVO SELECTED_NUM_RSP;
	
	private CHECKCREATECARDRESULTRSPVO CHECK_CREATE_CARD_RESULT_RSP;//成卡卡号返回
    
    private TERMINALCHECKRESRSPVO API_TERMINAL_CHECK_RES_RSP;
    
    private QRYSELECTIONNUMRSPVO QRY_SELECTION_NUM_RSP;
    
    private RELSELECTIONNUMRSP REL_SELECTION_NUM_RSP;
    
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

    public TERMINALCHECKRESRSPVO getAPI_TERMINAL_CHECK_RES_RSP() {
        return API_TERMINAL_CHECK_RES_RSP;
    }

    public void setAPI_TERMINAL_CHECK_RES_RSP(
            TERMINALCHECKRESRSPVO aPI_TERMINAL_CHECK_RES_RSP) {
        API_TERMINAL_CHECK_RES_RSP = aPI_TERMINAL_CHECK_RES_RSP;
    }


	public QRYSELECTIONNUMRSPVO getQRY_SELECTION_NUM_RSP() {
		return QRY_SELECTION_NUM_RSP;
	}

	public void setQRY_SELECTION_NUM_RSP(QRYSELECTIONNUMRSPVO qRY_SELECTION_NUM_RSP) {
		QRY_SELECTION_NUM_RSP = qRY_SELECTION_NUM_RSP;
	}

	public RELSELECTIONNUMRSP getREL_SELECTION_NUM_RSP() {
		return REL_SELECTION_NUM_RSP;
	}

	public void setREL_SELECTION_NUM_RSP(RELSELECTIONNUMRSP rEL_SELECTION_NUM_RSP) {
		REL_SELECTION_NUM_RSP = rEL_SELECTION_NUM_RSP;
	}
    
    
}