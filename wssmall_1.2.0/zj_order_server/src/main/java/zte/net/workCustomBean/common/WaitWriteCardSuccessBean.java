package zte.net.workCustomBean.common;

import com.ztesoft.ibss.common.util.Const;

public class WaitWriteCardSuccessBean extends BasicBusiBean {

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
		if(Const.WRITE_CARD_SUCCESS.equals(this.getFlowData().getJson_param()))
			// 写卡成功，返回true
			return true;
		else
			return false;
	}

}
