package zte.net.common.rule;

import javax.annotation.Resource;

import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderStatusQryResp;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.service.IOrdBSSTacheManager;

import consts.ConstsCore;

/**
 * 库管环节
 * @author Liuquan
 */
@ZteServiceAnnontion(trace_name = "ZteZJInventoryKeeperTraceRule", trace_id = "1", version = "1.0", desc = "库管业务处理环节业务通知组件")
public class ZteZJInventoryKeeperTraceRule extends ZteTraceBaseRule {

	@Resource
	private IOrdBSSTacheManager ordBSSTacheManager;

	/**
	 * 库管管理环节入口
	 */
	@Override
	@ZteMethodAnnontion(name = "库管管理环节入口", group_name = "库管", order = "1", page_show = true, path = "ZteZJInventoryKeeperTraceRule.exec")
	public BusiCompResponse engineDo(BusiCompRequest busiCompRequest) {//status转换为15就是待库管
		String orderId = busiCompRequest.getOrder_id();
		OrderBusiRequest orderBusiRequest = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderBusiRequest();
		orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_15);
		orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiRequest.store();
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	/**
	 * 下一步（浙江业务办理）
	 */
	@ZteMethodAnnontion(name = "下一步（库管）", group_name = "库管", order = "2", page_show = true, path = "ZteZJInventoryKeeperTraceRule.nextStep")
	public BusiCompResponse nextStep(BusiCompRequest busiCompRequest) {
		BusiCompResponse resp = this.nextflow(busiCompRequest, false, "MK");
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	public static void main(String[] args){
		OrderStatusQryResp resp = new OrderStatusQryResp();
		resp.setCode("00000");
	}
}
