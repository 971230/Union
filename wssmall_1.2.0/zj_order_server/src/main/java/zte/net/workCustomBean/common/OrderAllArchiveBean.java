package zte.net.workCustomBean.common;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.ICommonManager;

import consts.ConstsCore;

public class OrderAllArchiveBean extends BasicBusiBean {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private ICommonManager commonManager;

	/**
	 * 业务处理方法
	 * @return
	 * @throws Exception
	 */
	@Override
	public String doBusi() throws Exception{
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
		if(!Const.ORDER_ARCHIVE.equals(this.getFlowData().getJson_param()))
			//订单归档接口调用，则等待
			return false;
		
		String orderId = this.getFlowData().getOrderTree().getOrder_id();
		
		BusiDealResult result = commonManager.setOrderStatusAllArchive(orderId);
		
		if(ConstsCore.ERROR_FAIL.equals(result.getError_code())){
			logger.error(orderId+"订单归档失败："+result.getError_msg());
			throw new Exception(orderId+"订单归档失败："+result.getError_msg());
		}
		
		return true;
	}
}
