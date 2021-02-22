package services;

import params.member.req.AgentReq;
import params.member.resp.AgentResp;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.service.IAgentManager;

public class AgentServ extends ServiceBase implements AgentInf{
	
	private IAgentManager agentManager;

	public IAgentManager getAgentManager() {
		return agentManager;
	}

	public void setAgentManager(IAgentManager agentManager) {
		this.agentManager = agentManager;
	}
	
	@Override
	public AgentResp getStaffInfo(AgentReq req){
		AgentResp resp = new AgentResp();
		
		Page page = agentManager.searchStaff3(req.getInfoMap(), req.getPage_index(), req.getPage_size());
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setStaffInfoPage(page);
		
		addReturn(req, resp);
		return resp;
	}
}
