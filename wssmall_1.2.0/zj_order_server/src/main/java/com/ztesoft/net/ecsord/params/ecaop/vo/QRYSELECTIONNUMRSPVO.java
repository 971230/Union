package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.util.List;

public class QRYSELECTIONNUMRSPVO {

	private String RESP_CODE;//应答码0000成功9999号码同步失败，没有收录到应答编码中。其他取值具体参考附录-应答编码
	private String RESP_DESC;//应答描述
	private List<SELNUMLISTRESPVO> SELNUM_LIST;
	private List<PARAVO> PARA;//保留字段（暂未使用）
	

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


	public List<SELNUMLISTRESPVO> getSELNUM_LIST() {
		return SELNUM_LIST;
	}


	public void setSELNUM_LIST(List<SELNUMLISTRESPVO> sELNUM_LIST) {
		SELNUM_LIST = sELNUM_LIST;
	}


	public List<PARAVO> getPARA() {
		return PARA;
	}


	public void setPARA(List<PARAVO> pARA) {
		PARA = pARA;
	}


}
