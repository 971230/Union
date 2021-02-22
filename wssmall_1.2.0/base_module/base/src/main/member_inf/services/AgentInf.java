package services;

import params.member.req.AgentReq;
import params.member.resp.AgentResp;

public interface AgentInf {
	
	public AgentResp getStaffInfo(AgentReq req);
}
