package zte.net.common.rule;

import params.ZteResponse;
import params.req.CallRefundReq;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import consts.ConstsCore;
import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;

@ZteServiceAnnontion(trace_name="ZteOrderRefundTraceRule",trace_id="0",version="1.0",desc="订单退款")
public class ZteOrderRefundTraceRule extends ZteTraceBaseRule {

	@Override
	@ZteMethodAnnontion(name = "退款组件入口", group_name = "退款", order = "1", page_show = true, path = "zteOrderRefundTraceRule.exec")
	public BusiCompResponse engineDo(BusiCompRequest traceRequest) {
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name="接收退款申请",group_name="订单退款",order="1",page_show=true,path="zteOrderRefundTraceRule.refundApply")
	public BusiCompResponse refundApply(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
				new String[]{AttrConsts.BSS_REFUND_STATUS}, new String[]{ZjEcsOrderConsts.BSS_REFUND_STATUS_5});
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}
	
	/**
	 * [爬虫]退款审核
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[爬虫]退款审核",group_name="订单退款",order="2",page_show=true,path="zteOrderRefundTraceRule.refundAudit")
	public BusiCompResponse refundAudit(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
		String is_refund = orderExt.getIs_refund();
		ZteResponse rsp = null;
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_refund)){
			//TODO 爬虫掉总商退款审核
			CallRefundReq req = new CallRefundReq();
			req.setNotNeedReqStrOrderId(order_id);
			req.setOrderId(CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_num());
			req.setOrderNo(CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOut_tid());
			
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			rsp = client.execute(req, ZteResponse.class);
			//如果审核成功，修改退款状态
			if(!ConstsCore.ERROR_SUCC.equals(rsp.getError_code())){
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
						new String[]{AttrConsts.BSS_REFUND_STATUS}, new String[]{EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_FAIL});
			}
			else{
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
						new String[]{AttrConsts.BSS_REFUND_STATUS}, new String[]{EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_SUCC});
			}
			resp.setError_code(rsp.getError_code());
			resp.setError_msg(rsp.getError_msg());
		}
		else{
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		return resp;
	}
	@ZteMethodAnnontion(name="[总商爬虫同步]总商系统申请退款",group_name="退款",order="5",page_show=true,path="zteOrderRefundTraceRule.ZbSystemRefund")
	public BusiCompResponse ZbSystemRefund(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
    			new String[]{AttrConsts.IS_REFUND,AttrConsts.REFUND_DESC,AttrConsts.BSS_REFUND_STATUS}, 
    			new String[]{ZjEcsOrderConsts.IS_DEFAULT_VALUE,ZjEcsOrderConsts.REFUND_CONFIRM_DESC+"总商系统发起退款",ZjEcsOrderConsts.BSS_REFUND_STATUS_5});//更新退款标记
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}
	@ZteMethodAnnontion(name="[总商爬虫同步]总商系统退款确认",group_name="退款",order="5",page_show=true,path="zteOrderRefundTraceRule.ZbSystemRefundConform")
	public BusiCompResponse ZbSystemRefundConform(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
				new String[]{AttrConsts.BSS_REFUND_STATUS}, new String[]{EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_SUCC});
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}
	
}
