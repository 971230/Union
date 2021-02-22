package com.ztesoft.inf.communication.client.bo;

import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.inf.communication.client.vo.ClientEndPoint;
import com.ztesoft.inf.communication.client.vo.ClientGlobalVar;
import com.ztesoft.inf.communication.client.vo.ClientOperation;
import com.ztesoft.inf.communication.client.vo.ClientRequest;
import com.ztesoft.inf.communication.client.vo.ClientRequestUser;
import com.ztesoft.inf.communication.client.vo.ClientResponse;

public interface ICommClientBO {
	
	public Map getLogColsByOpId(String opId) throws FrameException;
	
	public ClientOperation getOperationByCode(String operationCode,String ep_mall)throws FrameException;
	public ClientOperation getOperationByCodeFromDB(String operationCode,String ep_mall)throws FrameException;

	
	public ClientEndPoint getEndPoint(String endpointId,String ep_mall) throws FrameException ;
	public ClientEndPoint getEndPointFromDB(String endpointId,String ep_mall) throws FrameException ;
	
	public ClientRequest getRequest(String requestId) throws FrameException;
	public ClientRequest getRequestFromDB(String requestId) throws FrameException;
	
	public ClientResponse getResponse(String responseId) throws FrameException;
	public ClientResponse getResponseFromDB(String responseId) throws FrameException;
	
	public ClientRequestUser getRequestUser(String userId)throws FrameException;
	
	public void logCall(final List args) throws Exception;
	
	public void updateCallLog(final Map<String,String> orderMap,final Object o,final String log_id, final String rsp_xml);
	
	public ClientGlobalVar getGlobalVars(String globalVarsId);
	
	public void logINF_CRM_SPS_LOG(final List args) throws Exception;
	
	public List queryForList(String sql);
	
	public Map queryForMap(String sql);
	
	public String queryForString(String sql);
	
	public String queryForString(String sql,String[] strs);
	
	public List getRemoteServiceList();
	
}
