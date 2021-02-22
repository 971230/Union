package zte.net.workCustomBean.bss;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IOrdWriteCardTacheManager;

import zte.net.workCustomBean.common.BasicBusiBean;

public class BssWriteCardResultBean extends BasicBusiBean {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private IOrdWriteCardTacheManager ordWriteCardTacheManager;
	
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
		if(!Const.WRITE_CARD_RESULT.equals(this.getFlowData().getJson_param()))
			//不是写卡结果通知接口调用，则等待
			return false;
		
		String order_id = this.getFlowData().getOrderTree().getOrder_id();
		
		BusiDealResult dealResult = ordWriteCardTacheManager.CardDataSyncBss(order_id);
		
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_0000)){
			logger.error(order_id+"订单卡数据同步失败："+dealResult.getError_msg());
			throw new Exception(order_id+"订单卡数据同步失败："+dealResult.getError_msg());
		}

		return true;
	}
}
