package com.ztesoft.net.mall.core.action.order.orderc;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.DeliveryFlow;
import com.ztesoft.net.mall.core.service.IDeliveryFlow;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 物流管理
* @作者 MoChunrun 
* @创建日期 2013-8-13 
* @版本 V 1.0
 */
public class DeliveryFlowAction  extends WWAction{

	private IDeliveryFlow deliveryFlowManager;
	private DeliveryFlow flow;
	private String deliveryID;
	private String logi_id;
	private String logi_no;
	private List<DeliveryFlow> flowList;
	
	public String addFlow(){
		AdminUser user = ManagerUtils.getAdminUser();
		if(flow!=null){
			flow.setCreate_time(DBTUtil.current());
			flow.setOp_id(user.getUserid());
			flow.setOp_name(user.getUsername());
			deliveryFlowManager.addDeliveryFlow(flow);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print("{result:'1',message:'成功'}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String listFlow(){
		if("0".equals(logi_id)){
			flowList = deliveryFlowManager.qryDeliveryFlowByDeliveryID(deliveryID);
		}else{
			//有物流单号的  logi_no
			flowList = deliveryFlowManager.qryFlowInfo(logi_no, logi_id);
		}
		return "list";
	}
	
	
	public IDeliveryFlow getDeliveryFlowManager() {
		return deliveryFlowManager;
	}

	public void setDeliveryFlowManager(IDeliveryFlow deliveryFlowManager) {
		this.deliveryFlowManager = deliveryFlowManager;
	}

	public DeliveryFlow getFlow() {
		return flow;
	}

	public void setFlow(DeliveryFlow flow) {
		this.flow = flow;
	}

	public String getDeliveryID() {
		return deliveryID;
	}

	public void setDeliveryID(String deliveryID) {
		this.deliveryID = deliveryID;
	}

	public List<DeliveryFlow> getFlowList() {
		return flowList;
	}

	public void setFlowList(List<DeliveryFlow> flowList) {
		this.flowList = flowList;
	}

	public String getLogi_id() {
		return logi_id;
	}

	public void setLogi_id(String logi_id) {
		this.logi_id = logi_id;
	}

	public String getLogi_no() {
		return logi_no;
	}

	public void setLogi_no(String logi_no) {
		this.logi_no = logi_no;
	}
	
}
