package zte.net.workCustomBean.common;

import zte.net.ecsord.params.workCustom.po.WORK_CUSTOM_FLOW_DATA;
import zte.net.workCustomBean.interfaces.IBusiInterface;

public class BasicBusiBean implements IBusiInterface {
	protected String node_id;
	
	protected WORK_CUSTOM_FLOW_DATA flowData;
	
	@Override
	public String getNode_id() {
		return node_id;
	}

	
	@Override
	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}

	
	@Override
	public WORK_CUSTOM_FLOW_DATA getFlowData() {
		return flowData;
	}

	
	@Override
	public void setFlowData(WORK_CUSTOM_FLOW_DATA flowData) {
		this.flowData = flowData;
	}
	
	@Override
	public String doBusi() throws Exception {
		return "";
	}

	@Override
	public boolean doBackWaitCheck() throws Exception {
		return false;
	}
}
