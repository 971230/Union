package zte.params.order.req;

import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.model.Outer;
import commons.CommonTools;

import params.ZteRequest;
import zte.params.order.resp.OrderStandardPushResp;

public class OrderStandardPushReq extends ZteRequest<OrderStandardPushResp> {

	@ZteSoftCommentAnnotationParam(name="订单列表",type="String",isNecessary="Y",desc="订单列表",hasChild=true)
	private List<Outer> outerList;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(outerList==null || outerList.size()==0)CommonTools.addFailError("outerList 不能这空");
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.orderStandard.push";
	}

	public List<Outer> getOuterList() {
		return outerList;
	}

	public void setOuterList(List<Outer> outerList) {
		this.outerList = outerList;
	}

}
