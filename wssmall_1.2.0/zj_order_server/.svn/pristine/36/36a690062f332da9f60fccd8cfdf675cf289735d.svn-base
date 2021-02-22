package zte.net.workCustomBean.intent;

import javax.annotation.Resource;

import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomCfgManager;

import zte.net.ecsord.params.workCustom.po.ES_ORDER_INTENT;
import zte.net.workCustomBean.common.BasicBusiBean;

public class CancelOrderIntentBean extends BasicBusiBean {
	@Resource
	private IWorkCustomCfgManager workCustomCfgManager;

	@Override
	public String doBusi() throws Exception {
		ES_ORDER_INTENT orderIntent = this.flowData.getOrderIntent();
		
		orderIntent.setStatus("00");
		
		this.workCustomCfgManager.updateOrderIntent(orderIntent);
		
		return "";
	}
}
