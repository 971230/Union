package com.ztesoft.rop.response;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Locale;


/**
 * <pre>
 * 功能说明：
 * </pre>
 * 
 * @author 陈雄华
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "error")
public class ErrorResponse {

	@XmlElement
	protected String errorflag = "-100";

	@XmlAttribute
	protected String code;

	@XmlElement
	protected String message;

	@XmlElement
	protected String solution;

	@XmlElementWrapper(name = "subErrors")
	@XmlElement(name = "subError")
	protected List<SubError> subErrors;

	public ErrorResponse() {
	}

	public ErrorResponse(MainError mainError) {
		this.code = mainError.getCode();
		this.message = mainError.getMessage();
		this.solution = mainError.getSolution();
		if (mainError.getSubErrors() != null
				&& mainError.getSubErrors().size() > 0) {
			this.subErrors = mainError.getSubErrors();
		}
	}

	public String getErrorflag() {
		return errorflag;
	}

	public void setErrorflag(String errorflag) {
		this.errorflag = errorflag;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public List<SubError> getSubErrors() {
		return subErrors;
	}

	public void setSubErrors(List<SubError> subErrors) {
		this.subErrors = subErrors;
	}

	protected MainError getInvalidArgumentsError(Locale locale) {
		return MainErrors.getError(MainErrorType.INVALID_ARGUMENTS, locale);
	}

	protected void setMainError(MainError mainError) {
		setCode(mainError.getCode());
		setMessage(mainError.getMessage());
		setSolution(mainError.getSolution());
	}

	/**
	 * 对服务名进行标准化处理：如book.upload转换为book-upload，
	 * 
	 * @param method
	 * @return
	 */
	protected String transform(String method) {
		if (method != null) {
			method = method.replace(".", "-");
			return method;
		} else {
			return "LACK_METHOD";
		}
	}

}
