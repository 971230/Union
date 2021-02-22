package zte.net.ecsord.params.sf.req;

import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.params.sf.vo.OrderFilterResult;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 *顺丰人工筛选订单接口推送
 */
public class ArtificialSelectRequest extends ZteRequest {

	private static final long serialVersionUID = -8636865716422350576L;
	
	@ZteSoftCommentAnnotationParam(name="顺丰人工筛选订单信息",type="String",isNecessary="Y",desc="顺丰人工筛选信息,每次推送信息不超过6笔")
	public List<OrderFilterResult> orderFilterResultList;
	
	
	public List<OrderFilterResult> getOrderFilterResultList() {
		return orderFilterResultList;
	}

	public void setOrderFilterResultList(List<OrderFilterResult> orderFilterResultList) {
		this.orderFilterResultList = orderFilterResultList;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.sf.artificialSelect";
	}

}
