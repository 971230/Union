package zte.net.workCustomBean.interfaces;

import zte.net.ecsord.params.workCustom.po.WORK_CUSTOM_FLOW_DATA;

public interface IBusiInterface {
	public String getNode_id();
	
	public void setNode_id(String node_id);
	
	public WORK_CUSTOM_FLOW_DATA getFlowData();

	public void setFlowData(WORK_CUSTOM_FLOW_DATA flowData);
	
	/**
	 * 业务处理方法
	 * @return
	 * @throws Exception
	 */
	public String doBusi() throws Exception;
	
	/**
	 * 后台等待环节的处理方法，用于后台等待环节的自动判断，
	 * 不是后台等待环节的业务处理代码无需实现该方法
	 * @return 可以继续往下执行，返回true;继续等待返回false
	 * @throws Exception
	 */
	public boolean doBackWaitCheck() throws Exception;
}
