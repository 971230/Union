package zte.net.workCustomBean.common;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.inf.communication.client.CommCaller;
import com.ztesoft.net.ecsord.params.ecaop.req.ProductSubReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.ProductSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.PromoteProductResp;
import com.ztesoft.net.service.IEcsOrdManager;

import zte.net.params.resp.RuleTreeExeResp;

public class ProductSubBean extends BasicBusiBean {
	@Resource
	private IEcsOrdManager ecsOrdManager;

	/**
	 * 业务处理方法
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String doBusi() throws Exception{
		String order_id = this.getFlowData().getOrderTree().getOrder_id();
		ProductSubResp resp = new ProductSubResp();
		ProductSubReq req = this.ecsOrdManager.productReqSet(order_id);
		if(StringUtils.isBlank(req.getProduct_id())) {
			throw new Exception("产品ID不能为空");
		}
		if(StringUtils.isBlank(req.getService_num())) {
			throw new Exception("联通业务号码不能为空");
		}
		if(StringUtils.isBlank(req.getOut_operator())) {
			throw new Exception("外部操作员编码不能为空");
		}
		if(StringUtils.isBlank(req.getOut_channel())) {
			throw new Exception("外部渠道编码不能为空");
		}
		if(StringUtils.isBlank(req.getOp_type())) {
			throw new Exception("产品操作类型不能为空");
		}
		
		CommCaller caller = new CommCaller();
		Map<String, Object> param = new HashMap<String, Object>();

		BeanUtils.bean2MapForAiPBSS(param, req);
		param.put("ord_id", req.getNotNeedReqStrOrderId());
		resp = (ProductSubResp) caller.invoke("ecaop.trades.serv.product.promote.sub", param);

		if(!"00000".equals(resp.getCode())) {
			throw new Exception(resp.getMsg());
		}

		return "true";
	}

	/**
	 * 后台等待环节的处理方法，用于后台等待环节的自动判断，
	 * 不是后台等待环节的业务处理代码无需实现该方法
	 * @return 可以继续往下执行，返回true;继续等待返回false
	 * @throws Exception
	 */
	public boolean doBackWaitCheck() throws Exception{
		return true;
	}
}
