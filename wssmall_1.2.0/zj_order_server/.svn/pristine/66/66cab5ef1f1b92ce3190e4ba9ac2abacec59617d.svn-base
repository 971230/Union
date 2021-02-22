package zte.net.workCustomBean.intent;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomCfgManager;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.workCustomBean.common.BasicBusiBean;

/**
 * 匹配AOP流程--自定义流程cbss副卡开户
 * @author cqq 20181217
 *
 */
public class CustomMatchIsAOPBean extends BasicBusiBean {
	@Resource
	private IWorkCustomCfgManager workCustomCfgManager;
	@Resource
	private IEcsOrdManager ecsOrdManager;

	@Override
	public String doBusi() throws Exception {
		OrderTreeBusiRequest orderTree  = this.flowData.getOrderTree();
		
		String is_aop = "1";
		String order_id = orderTree.getOrder_id();

		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_AOP }, new String[] { is_aop });
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		orderExtBusiRequest.setIs_aop(is_aop);
		String set_sql = " is_aop = '"+is_aop+"'";
		if (!StringUtils.equals(EcsOrderConsts.NO_DEFAULT_VALUE, is_aop)) {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.SEND_ZB }, new String[] { EcsOrderConsts.NO_DEFAULT_VALUE });
			orderExtBusiRequest.setSend_zb(EcsOrderConsts.NO_DEFAULT_VALUE);
			set_sql += ", send_zb = '"+EcsOrderConsts.NO_DEFAULT_VALUE+"'";
		}
		ecsOrdManager.updateOrderTreeCustom(set_sql, "es_order_ext", order_id, orderTree);
		
		return "";
	}
}
