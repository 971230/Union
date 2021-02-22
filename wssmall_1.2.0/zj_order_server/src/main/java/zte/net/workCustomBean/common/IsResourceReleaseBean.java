package zte.net.workCustomBean.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.ecsord.params.ecaop.req.QuerySelectNumberReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.ResourCecenterAppResp;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import commons.CommonTools;
import consts.ConstsCore;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class IsResourceReleaseBean extends BasicBusiBean {
	/**
	 * 业务处理方法
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public String doBusi() throws Exception {
		BusiCompResponse busiCompResponse = new BusiCompResponse();
		String order_id = this.getFlowData().getOrderTree().getOrder_id();
		OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		if (null == orderTreeBusiRequest) {
			CommonTools.addBusiError(ConstsCore.ERROR_FAIL, "订单OrderId:" + order_id + " 订单树不存在");
		}
		ZteClient zteClient = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		QuerySelectNumberReq req = new QuerySelectNumberReq();
		req.setNotNeedReqStrOrderId(order_id);
		
		ResourCecenterAppResp resp = zteClient.execute(req, ResourCecenterAppResp.class);
		
		//判断第一层节点
		if ( !"00000".equals(resp.getCode())) {
			throw new Exception(resp.getMsg());
		}
		if (resp.getRespJson().getQRY_SELECTION_NUM_RSP() == null) {
			throw new Exception("接口返回为空");
		}
		if (!"0000".equals(resp.getRespJson().getQRY_SELECTION_NUM_RSP().getRESP_CODE())) {
			return "false";
		}
		
		if (!StringUtils.isEmpty(resp.getRespJson().getQRY_SELECTION_NUM_RSP().getSELNUM_LIST().get(0).getNUM_STATUS())
				&& (resp.getRespJson().getQRY_SELECTION_NUM_RSP().getSELNUM_LIST().get(0).getNUM_STATUS().equals("04")
						|| resp.getRespJson().getQRY_SELECTION_NUM_RSP().getSELNUM_LIST().get(0).getNUM_STATUS().equals("08"))) {

			Map map_param = new HashMap();
			map_param.put("SERIAL_NUMBER", resp.getRespJson().getQRY_SELECTION_NUM_RSP().getSELNUM_LIST().get(0).getSERIAL_NUMBER());
			map_param.put("PRO_KEY",resp.getRespJson().getQRY_SELECTION_NUM_RSP().getSELNUM_LIST().get(0).getPRO_KEY());
			map_param.put("CERT_TYPE_CODE", resp.getRespJson().getQRY_SELECTION_NUM_RSP().getSELNUM_LIST().get(0).getCERT_TYPE_CODE());
			map_param.put("CERT_CODE",resp.getRespJson().getQRY_SELECTION_NUM_RSP().getSELNUM_LIST().get(0).getCERT_CODE());

			String jsonString = JSON.toJSONString(map_param);

			this.getFlowData().setJson_param(jsonString);
			return "true";
		} else {
			return "false";
		}
	}

	/**
	 * 后台等待环节的处理方法，用于后台等待环节的自动判断， 不是后台等待环节的业务处理代码无需实现该方法
	 * 
	 * @return 可以继续往下执行，返回true;继续等待返回false
	 * @throws Exception
	 */
	@Override
	public boolean doBackWaitCheck() throws Exception {
		System.out.println("doBackWaitCheck()");
		return true;
	}

}
