package zte.net.workCustomBean.cbss;

import org.apache.log4j.Logger;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IOrdOpenAccountTacheManager;

import commons.CommonTools;
import zte.net.workCustomBean.common.BasicBusiBean;

/**
 * AOP开户预提交
 * @author GL
 *
 */
public class AopMobilePreCommitBean extends BasicBusiBean {
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
		
		if(!Const.PRE_COMMIT.equals(this.getFlowData().getJson_param()))
			//不是预提交接口调用，则等待
			return false;
		IOrdOpenAccountTacheManager ordOpenAccountTacheManager = this.getOrdOpenAccountTacheManager();
		String order_id = this.getFlowData().getOrderTree().getOrder_id();
		
		BusiDealResult openActResp = ordOpenAccountTacheManager.openAccountPreCommitApplyAopZJ(order_id);
		if(!StringUtils.equals(openActResp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(openActResp.getError_code(),openActResp.getError_msg());	
			
			logger.error(order_id+"订单预提交失败："+openActResp.getError_msg());
			throw new Exception(order_id+"订单预提交失败："+openActResp.getError_msg());
		}
		return true;
	}
	

}
