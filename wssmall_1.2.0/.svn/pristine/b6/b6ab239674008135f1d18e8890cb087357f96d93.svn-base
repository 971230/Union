package com.ztesoft.common.exception;

import java.io.Serializable;

public class ExceptionVO implements Serializable {

	private String errorCode;
	private String errorMessage;
	private String errorResolve;
	private int level;
	private String stackInfo;

	private ExceptionVO() {

	}

	public ExceptionVO(CrmException ex) {
		CrmError error = ex.getError();
		this.errorCode = error.getErrorCode();
		this.errorMessage = error.getErrorMessage();
		this.errorResolve = error.getErrorResolve();
		this.level = error.getLevel();
		this.stackInfo = ex.getStackTraceAsString();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorResolve() {
		return errorResolve;
	}

	public void setErrorResolve(String errorResolve) {
		this.errorResolve = errorResolve;
	}

	public String getStackInfo() {
		return stackInfo;
	}

	public void setStackInfo(String stackInfo) {
		this.stackInfo = stackInfo;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public String toJsObject(){
		return "<script>jspTaglibErrors[jspTaglibErrors.length]=new JspTaglibError('"+this.getErrorCode()+"','"+this.getErrorMessage()+"','"+this.getErrorResolve()+"','"+this.getLevel()+"','"+encodeToHTML(this.getStackInfo())+"');</script>";
	}
	
	private String encodeToHTML(String str){
		return encodeStr(str);
	}
	
	/**
	 * Buffalo encode str 
	 * From com.ztesoft.buffalo.burlap.NFBurlapOutput
	 * @param strIn
	 * @return
	 */
	protected String encodeStr(String strIn) {
		int intLen = strIn.length();
		String strOut = "";
		char strTemp;

		for (int i = 0; i < intLen; i++) {
			strTemp = strIn.charAt(i);
			if (strTemp > 255) {
				String tmp = Integer.toHexString(strTemp);
				for (int j = tmp.length(); j < 4; j++)
					tmp = "0" + tmp;
				strOut = strOut + "^" + tmp;
			} else {
				if (strTemp < 48 || (strTemp > 57 && strTemp < 65) || (strTemp > 90 && strTemp < 97) || strTemp > 122) {
					String tmp = Integer.toHexString(strTemp);
					for (int j = tmp.length(); j < 2; j++)
						tmp = "0" + tmp;
					strOut = strOut + "~" + tmp;
				} else {
					strOut = strOut + strIn.charAt(i);
				}
			}
		}
		return (strOut);
	}

	/**
	 * Buffalo decode str 
	 * From com.ztesoft.buffalo.burlap.NFBurlapOutput
	 * @param strIn
	 * @return
	 */
	protected String decodeStr(String strIn) {
		int intLen = strIn.length();
		StringBuffer strOut = new StringBuffer();
		char strTemp;

		for (int i = 0; i < intLen; i++) {
			strTemp = strIn.charAt(i);
			switch (strTemp) {
			case 126: {

				String sTemp = strIn.substring(i + 1, i + 3);
				int iTemp = Integer.parseInt(sTemp, 16);
				sTemp = new Character((char) iTemp).toString();
				strOut.append(sTemp);

				// js
				/*
				 * strTemp = strIn.substring(i+1, i+3); strTemp =
				 * parseInt(strTemp, 16); strTemp =
				 * String.fromCharCode(strTemp); strOut = strOut+strTemp;
				 */

				i += 2;
				break;
			}
			case 94: {

				String sTemp = strIn.substring(i + 1, i + 5);
				int iTemp = Integer.parseInt(sTemp, 16);
				sTemp = new Character((char) iTemp).toString();
				strOut.append(sTemp);

				// js
				/*
				 * strTemp = strIn.substring(i + 1, i + 5); strTemp =
				 * parseInt(strTemp, 16); strTemp =
				 * String.fromCharCode(strTemp); strOut = strOut + strTemp;
				 */

				i += 4;
				break;
			}
			default: {

				strOut.append("" + strTemp);

				break;
			}
			}
		}
		return strOut.toString();
	}

}
