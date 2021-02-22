package zte.net.workCustomBean.common;

import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;

public class OrderDealMethodBean extends BasicBusiBean{

	private String order_id;
	
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	/**
	 * 业务处理方法
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public String doBusi() throws Exception{
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		OrderTreeBusiRequest orderTree = this.flowData.getOrderTree();
		String order_deal_method = orderTree.getOrderExtBusiRequest().getOrder_deal_method();
		if("2".equals(order_deal_method)){
			return "线下";
		}else{
			return "线上";
		}
		
	}
	
	/**
	 * 后台等待环节的处理方法，用于后台等待环节的自动判断，
	 * 不是后台等待环节的业务处理代码无需实现该方法
	 * @return 可以继续往下执行，返回true;继续等待返回false
	 * @throws Exception
	 */
	@Override
	public boolean doBackWaitCheck() throws Exception{
		return true;
	}
}
