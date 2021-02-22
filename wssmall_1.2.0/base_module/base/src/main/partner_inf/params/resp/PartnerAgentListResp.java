package params.resp;

import java.util.List;

import params.ZteResponse;


/**
 * 加载分销商列表返回结果集
 * @author hu.yi
 * @date 2013.12.26
 */
public class PartnerAgentListResp extends ZteResponse{

	private List agentList;

	public List getAgentList() {
		return agentList;
	}

	public void setAgentList(List agentList) {
		this.agentList = agentList;
	}
}
