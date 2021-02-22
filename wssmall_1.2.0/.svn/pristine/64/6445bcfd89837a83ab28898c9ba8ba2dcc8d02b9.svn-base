package zte.net.ecsord.params.busiopen.ordinfo.req;

import java.util.Map;

import params.ZteError;
import params.ZteRequest;
import zte.net.ecsord.params.busiopen.ordinfo.resp.OrderTreesInfoResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

/**
 *  订单分流
 * @作者 wui
 * @创建日期 2014-11-04
 * @版本 V 1.0
 */
public class OrderTreesInfoReq extends ZteRequest<OrderTreesInfoResp> {

	@ZteSoftCommentAnnotationParam(name="外部订单编号",type="String",isNecessary="N",desc="外部订单编号")
	private String 	order_id;
	
	@ZteSoftCommentAnnotationParam(name="扩展参数,目前为空",type="String",isNecessary="N",desc="扩展参数,目前为空")
	private Map extParams;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(order_id))
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "外部订单编号不能为空！"));
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.infservice.order.busi.ordinfo.ordertreesimpinforeq";
	}
	
	public Map getExtParams() {
		return extParams;
	}
	public void setExtParams(Map extParams) {
		this.extParams = extParams;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	
}
