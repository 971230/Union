package zte.net.ecsord.params.sf.vo;

import params.ZteRequest;
import zte.net.ecsord.params.sf.vo.FilterOption;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;


/**
 * 
 * @author sguo 
 * 订单信息同步
 * 
 */
public class OrderFilter extends ZteRequest {

	private static final long serialVersionUID = -8000575209659307888L;

	@ZteSoftCommentAnnotationParam(name="筛单类别",type="String",isNecessary="Y",desc="filter_type：1-自动筛单（系统根据地址库进行判断，并返回结果），2-可人工筛单（系统首先根据地址库判断，如果无法自动判断是否收派，系统将生成需要人工判断的任务，后续由人工处理，处理结束后，顺丰可主动推送给客户系统）")
	private String filter_type;

	@ZteSoftCommentAnnotationParam(name="订单号",type="String",isNecessary="N",desc="orderid：如果filter_type=2，则必须提供客户订单号")
	private String orderid;	

	@ZteSoftCommentAnnotationParam(name="到件方详细地址",type="String",isNecessary="N",desc="d_address：需要包括省市区，如：广东省深圳市福田区新洲十一街万基商务大厦。")
	private String d_address;

	@ZteSoftCommentAnnotationParam(name="到件方详细地址",type="String",isNecessary="N",desc="OrderFilterOption：需要包括省市区，如：广东省深圳市福田区新洲十一街万基商务大厦。")
	private FilterOption OrderFilterOption;	
	
	
	public String getFilter_type() {
		return filter_type;
	}

	public void setFilter_type(String filter_type) {
		this.filter_type = filter_type;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getD_address() {
		return d_address;
	}

	public void setD_address(String d_address) {
		this.d_address = d_address;
	}
	
	public FilterOption getOrderFilterOption() {
		return OrderFilterOption;
	}

	public void setOrderFilterOption(FilterOption orderFilterOption) {
		OrderFilterOption = orderFilterOption;
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
