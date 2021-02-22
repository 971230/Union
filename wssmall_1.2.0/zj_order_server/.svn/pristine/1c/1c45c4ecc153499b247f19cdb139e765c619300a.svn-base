package com.ztesoft.net.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.powerise.ibss.framework.Const;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.inf.communication.client.bo.ICommClientBO;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.model.inf.InfCompareReq;
import com.ztesoft.net.service.IOrderInfManager;
	
public class OrderInfAction extends WWAction {
	
	@Resource
	private IOrderInfManager orderInfManager;
	@Resource
	private ICommClientBO commClientBO;
	
	private String orderId;
	
	private String ruleId;
	
	private String opCode;
	
	private String outId;
	
	private String rsp_content;
	
	private String new_xml;
	
	private String old_xml;
	
	private String rec_id;
	
	private String qry_cond;
	
	public String getInf(){
		if(!StringUtils.isEmpty(orderId) && !StringUtils.isEmpty(ruleId))
			rsp_content = orderInfManager.getinfContent(orderId, ruleId, opCode);
		
		return "order_inf_content";
	}
	
	public String compareInf(){
		if(!StringUtils.isEmpty(new_xml) && !StringUtils.isEmpty(old_xml)){
			InfCompareReq req = new InfCompareReq();
			req.setNewXml(new_xml);
			req.setOldXml(old_xml);
			req.setOp_code(opCode);
			req.setOrder_id(orderId);
			rec_id = orderInfManager.compareInfContent(req);
		}
		String result_page = "order_inf_compare";
		if(!StringUtils.isEmpty(rec_id)){
			this.webpage = orderInfManager.getInfList(rec_id, orderId, outId, opCode, this.getPage(), this.getPageSize());
			result_page = "inf_compare_list";
		}
		return result_page;
	}
	
	public String refreshInfCommClientInfo(){
		try{
			
			List<Map> reqList=orderInfManager.getReqBlobList();
			List<Map> respList=orderInfManager.getRespBlobList();
			List<Map> endpointList=orderInfManager.getEndpointList();
			List<Map> operationList=orderInfManager.getOperationList();
			if(reqList!=null){
				for (Map map : reqList) {
					String requestId = Const.getStrValue(map, "req_id");
					commClientBO.getRequestFromDB(requestId);
				}
			}
			if(respList!=null){
				for (Map map : respList) {
					String responseId = Const.getStrValue(map, "rsp_id");
					commClientBO.getResponseFromDB(responseId);
				}
			}
			if(endpointList!=null){
				for (Map map : endpointList) {
					String endpointId = Const.getStrValue(map, "ep_id");
					String ep_mall = Const.getStrValue(map, "ep_mall");
					commClientBO.getEndPointFromDB(endpointId, ep_mall);
				}
			}
			if(operationList!=null){
				for (Map map : operationList) {
					String op_code = Const.getStrValue(map, "op_code");
					String ep_mall = Const.getStrValue(map, "ep_mall");
					commClientBO.getOperationByCodeFromDB(op_code, ep_mall);
				}
			}
			
			
			json = "{result:0,msg:'执行成功'}";
		} catch (Exception e) {
			e.printStackTrace();
			json = "{result:1,msg:'执行失败'}";
		}
		
		return this.JSON_MESSAGE;
	}
	public String batchRefund(){	
		
		return "batch_refund_list";
	}
	
	public String comparePage(){
		return "order_inf_compare";
	}
	
	public String getInfList(){
		this.webpage = orderInfManager.getInfList(rec_id, orderId, outId, opCode, this.getPage(), this.getPageSize());
		return "inf_compare_list";
	}
	
	public String infCompareDetail(){
		this.webpage = orderInfManager.getInfDetail(rec_id, qry_cond, this.getPage(), 1000);
		return "inf_compare_detail";
	}
	
	public IOrderInfManager getOrderInfManager() {
		return orderInfManager;
	}

	public void setOrderInfManager(IOrderInfManager orderInfManager) {
		this.orderInfManager = orderInfManager;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	public String getRsp_content() {
		return rsp_content;
	}

	public void setRsp_content(String rsp_content) {
		this.rsp_content = rsp_content;
	}

	public String getNew_xml() {
		return new_xml;
	}

	public void setNew_xml(String new_xml) {
		this.new_xml = new_xml;
	}

	public String getOld_xml() {
		return old_xml;
	}

	public void setOld_xml(String old_xml) {
		this.old_xml = old_xml;
	}

	public String getOutId() {
		return outId;
	}

	public void setOutId(String outId) {
		this.outId = outId;
	}

	public String getRec_id() {
		return rec_id;
	}

	public void setRec_id(String rec_id) {
		this.rec_id = rec_id;
	}

	public String getQry_cond() {
		return qry_cond;
	}

	public void setQry_cond(String qry_cond) {
		this.qry_cond = qry_cond;
	}	
}
