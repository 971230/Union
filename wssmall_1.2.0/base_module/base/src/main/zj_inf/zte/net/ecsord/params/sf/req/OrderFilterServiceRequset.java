package zte.net.ecsord.params.sf.req;

import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.params.sf.vo.FilterOption;
import zte.net.ecsord.params.sf.vo.OrderFilter;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;


/**
 * 
 * @author sguo 
 * 订单信息同步
 * 
 */
public class OrderFilterServiceRequset extends ZteRequest {

	private static final long serialVersionUID = -8000575209659307888L;

	@ZteSoftCommentAnnotationParam(name="筛单类别",type="String",isNecessary="Y",desc="OrderFilterList：1-自动筛单（系统根据地址库进行判断，并返回结果），2-可人工筛单（系统首先根据地址库判断，如果无法自动判断是否收派，系统将生成需要人工判断的任务，后续由人工处理，处理结束后，顺丰可主动推送给客户系统）")
	private List<OrderFilter> OrderFilterList;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：订单编号")
	private String notNeedReqStrOrderId;
	
	public List<OrderFilter> getOrderFilterList() {
		return OrderFilterList;
	}

	public void setOrderFilterList(List<OrderFilter> orderFilterList) {
		OrderFilterList = orderFilterList;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.sf.orderFilterService";
	}

}
