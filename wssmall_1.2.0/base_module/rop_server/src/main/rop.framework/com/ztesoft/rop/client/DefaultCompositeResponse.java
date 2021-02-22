
package com.ztesoft.rop.client;

import com.ztesoft.rop.response.ErrorResponse;

/**
 * <pre>
 * 功能说明：
 * </pre>
 * 
 * @author 
 * @version 1.0
 */
public class DefaultCompositeResponse<T> implements CompositeResponse {

	private boolean successful;

	private ErrorResponse errorResponse;

	private T successRopResponse;

	public DefaultCompositeResponse(boolean successful) {
		this.successful = successful;
	}

	@Override
	public ErrorResponse getErrorResponse() {
		return this.errorResponse;
	}

	@Override
	public T getSuccessResponse() {
		return this.successRopResponse;
	}

	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	public void setSuccessRopResponse(T successRopResponse) {
		this.successRopResponse = successRopResponse;
	}

	@Override
	public boolean isSuccessful() {
		return successful;
	}
}
