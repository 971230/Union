package zte.net.workCustomBean.common;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import consts.ConstsCore;

/**
 * 设置订单生产模式为：02--人工集中生产模式
 * @author zhaoc
 *
 */
public class UpdateOrderModel2 extends BasicBusiBean {

	/**
	 * 业务处理方法
	 * @return
	 * @throws Exception
	 */
	@Override
	public String doBusi() throws Exception{
		OrderExtBusiRequest orderExt = this.getFlowData().getOrderTree().getOrderExtBusiRequest();
		String order_from = orderExt.getOrder_from();
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String order_from_info = cacheUtil.getConfigInfo("SHOW_IS_PHOTO");
		String if_send = orderExt.getIf_Send_Photos();
		if(StringUtil.isEmpty(if_send)&&!StringUtil.isEmpty(order_from_info)&&order_from_info.contains(order_from+",")){
			orderExt.setIf_Send_Photos("0");
		}
		orderExt.setOrder_model(EcsOrderConsts.ORDER_MODEL_02);
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
