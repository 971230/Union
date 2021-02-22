package zte.net.workCustomBean.common;

import java.util.List;

import javax.annotation.Resource;

import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomCfgManager;

import zte.net.ecsord.params.workCustom.po.ES_ORDER_INTENT;

/**
 * 修改意向单状态为转04--转线上
 * @author zhaoc
 *
 */
public class ChangeIntentStatusOnline extends BasicBusiBean {
	
	@Resource
	private IWorkCustomCfgManager workCustomCfgManager;

	/**
	 * 业务处理方法
	 * @return
	 * @throws Exception
	 */
	@Override
	public String doBusi() throws Exception{
		String order_id = this.getFlowData().getOrderTree().getOrder_id();
		
		ES_ORDER_INTENT intent = null;
		
		ES_ORDER_INTENT intentParam = new ES_ORDER_INTENT();
		intentParam.setOrder_id(order_id);
		
		List<ES_ORDER_INTENT> intentRet = this.workCustomCfgManager.qryOrderIntentList(intentParam , null);
		
		//没有意向单直接返回
		if(intentRet==null || intentRet.size()==0)
			return "true";
		
		intent = intentRet.get(0);
		
		intent.setStatus("04");
		
		this.workCustomCfgManager.updateOrderIntent(intent);
		
		return "true";
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
