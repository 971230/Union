package zte.net.workCustomBean.interfaces;

import java.util.List;

import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CFG;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CONNECT;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE_INS;

public interface IDoBusiInterface {
	public String getOrder_id();

	public void setOrder_id(String order_id);

	public OrderTreeBusiRequest getOrderTree();

	public void setOrderTree(OrderTreeBusiRequest orderTree);

	public ES_WORK_CUSTOM_CFG getCfg();

	public void setCfg(ES_WORK_CUSTOM_CFG cfg);

	public List<ES_WORK_CUSTOM_NODE> getNodes();

	public void setNodes(List<ES_WORK_CUSTOM_NODE> nodes);

	public List<ES_WORK_CUSTOM_CONNECT> getConnects();

	public void setConnects(List<ES_WORK_CUSTOM_CONNECT> connects) ;

	public List<ES_WORK_CUSTOM_NODE_INS> getInses();

	public void setInses(List<ES_WORK_CUSTOM_NODE_INS> inses);
	
	public void doBusi() throws Exception;
}
