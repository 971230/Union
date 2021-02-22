package params.resp;

import java.util.List;

import params.ZteResponse;



/**
 * 预存款日志出参
 * @author hu.yi
 * @date 2013.12.26
 */
public class PartnerAdvLogsResp extends ZteResponse{

	private List advLogsList;

	public List getAdvLogsList() {
		return advLogsList;
	}

	public void setAdvLogsList(List advLogsList) {
		this.advLogsList = advLogsList;
	}
}
