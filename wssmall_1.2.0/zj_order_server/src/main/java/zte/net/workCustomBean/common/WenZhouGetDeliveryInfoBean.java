package zte.net.workCustomBean.common;

import org.apache.log4j.Logger;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.service.IOrdOpenAccountTacheManager;

public class WenZhouGetDeliveryInfoBean extends BasicBusiBean{

	private Logger logger = Logger.getLogger(this.getClass());
	
	private IOrdOpenAccountTacheManager getOrdOpenAccountTacheManager() throws Exception{
		return SpringContextHolder.getBean("ordOpenAccountTacheManager");
	}

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
		if(!Const.UPDATE_DELIVERY.equals(this.getFlowData().getJson_param()))
			//不是物流信息接口调用，则等待
			return false;
		
		return true;
	}
}
