package zte.net.workCustomBean.common;

import java.util.List;

import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CFG;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CONNECT;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE_INS;
import zte.net.workCustomBean.interfaces.IDoBusiInterface;

public class CommonDoBusiBean implements IDoBusiInterface {
	protected String order_id;
	
	protected OrderTreeBusiRequest orderTree;

	protected ES_WORK_CUSTOM_CFG cfg;
	
	protected List<ES_WORK_CUSTOM_NODE> nodes;
	
	protected List<ES_WORK_CUSTOM_CONNECT> connects;
	
	protected List<ES_WORK_CUSTOM_NODE_INS> inses;
	
	@Override
	public String getOrder_id() {
		return order_id;
	}

	@Override
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	@Override
	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}

	@Override
	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}

	@Override
	public ES_WORK_CUSTOM_CFG getCfg() {
		return cfg;
	}

	@Override
	public void setCfg(ES_WORK_CUSTOM_CFG cfg) {
		this.cfg = cfg;
	}

	@Override
	public List<ES_WORK_CUSTOM_NODE> getNodes() {
		return nodes;
	}

	@Override
	public void setNodes(List<ES_WORK_CUSTOM_NODE> nodes) {
		this.nodes = nodes;
	}

	@Override
	public List<ES_WORK_CUSTOM_CONNECT> getConnects() {
		return connects;
	}

	@Override
	public void setConnects(List<ES_WORK_CUSTOM_CONNECT> connects) {
		this.connects = connects;
	}

	@Override
	public List<ES_WORK_CUSTOM_NODE_INS> getInses() {
		return inses;
	}

	@Override
	public void setInses(List<ES_WORK_CUSTOM_NODE_INS> inses) {
		this.inses = inses;
	}
	
	@Override
	public void doBusi() throws Exception{
		
	}
}
