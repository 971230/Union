package zte.net.workCustomBean.intent;

import javax.annotation.Resource;

import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomCfgManager;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.rule.mode.ModeFact;
import zte.net.params.req.CataloguePlanExeReq;
import zte.net.workCustomBean.common.BasicBusiBean;

/**
 * 匹配生产模式--自定义流程
 * @author cqq 20181219
 *
 */
public class CustomMatchOrderModelBean extends BasicBusiBean {
	@Resource
	private IWorkCustomCfgManager workCustomCfgManager;
	@Resource
	private IEcsOrdManager ecsOrdManager;

	@Override
	public String doBusi() throws Exception {
		OrderTreeBusiRequest orderTree  = this.flowData.getOrderTree();
		
		CataloguePlanExeReq req = new CataloguePlanExeReq();
		ModeFact fact = new ModeFact();
		fact.setOrder_id(orderTree.getOrder_id());
		req.setCatalogue_id(EcsOrderConsts.ORDER_MODEL_MATCH_DIR);
		req.setFact(fact);
		CommonDataFactory.getInstance().exeCataloguePlan(req);
		
		return "";
	}
}
