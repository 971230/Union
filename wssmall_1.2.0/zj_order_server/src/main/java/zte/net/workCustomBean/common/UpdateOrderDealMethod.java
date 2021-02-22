package zte.net.workCustomBean.common;

import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import consts.ConstsCore;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;

/**
 * 设置订单生产模式为：02--人工集中生产模式
 * @author zhaoc
 *
 */
public class UpdateOrderDealMethod extends BasicBusiBean {

	/**
	 * 业务处理方法
	 * @return
	 * @throws Exception
	 */
	@Override
	public String doBusi() throws Exception{
		OrderExtBusiRequest orderExt = this.getFlowData().getOrderTree().getOrderExtBusiRequest();
		orderExt.setOrder_deal_method(EcsOrderConsts.ORDER_DEAL_METHOD_1);
		
		orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExt.store();
		
		return "false";
	}
	
	/**
	 * 后台等待环节的处理方法，用于后台等待环节的自动判断，
	 * 不是后台等待环节的业务处理代码无需实现该方法
	 * @return 可以继续往下执行，返回true;继续等待返回false
	 * @throws Exception
	 */
	@Override
	public boolean doBackWaitCheck() throws Exception{
		return false;
	}
}
