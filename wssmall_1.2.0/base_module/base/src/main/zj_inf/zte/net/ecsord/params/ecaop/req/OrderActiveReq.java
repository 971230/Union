/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * cbss订单激活
 * @author duan.shaochu
 *
 */
public class OrderActiveReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "cbssID为激活时使用", type = "String", isNecessary = "Y", desc = "orderId：cbssID为激活时使用")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParamsVo", isNecessary = "N", desc = "para：保留字段")
	private List<ParamsVo> para;

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getOrderId() {
		String orderId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_ESS_ORDER_ID);
		if(StringUtils.isEmpty(orderId)){
			return null;
		}
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<ParamsVo> getPara() {
		return para;
	}

	public void setPara(List<ParamsVo> para) {
		this.para = para;
	}

	@Override
	public String getApiMethodName() {
		return EcsOrderConsts.AOP_ORDER_ACTIVE;
	}	
}
