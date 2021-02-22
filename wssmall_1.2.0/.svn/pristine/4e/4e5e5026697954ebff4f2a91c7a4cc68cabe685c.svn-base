package zte.params.order.req;


import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;
import zte.params.order.resp.OrderOuterSyncResp;

public class TaobaoOrderSyncReq extends ZteRequest<OrderOuterSyncResp> {

	/**
	 * 日期类（订货日期tid_time、付款日期、发货日期、归 档日期、预计归档日期、到货日期、订单修改日期、退款时间、验货日期、称重时间、退货日期、审核日期）
	 * 
	 * 暂时只支持订货日期(默认为订货日期tid_time)
	 */
	@ZteSoftCommentAnnotationParam(name="日期类型",type="String",isNecessary="Y",desc="日期类（订货日期tid_time、付款日期、发货日期、归 档日期、预计归档日期、到货日期、订单修改日期、退款时间、验货日期、称重时间、退货日期、审核日期）。暂时只支持订货日期(默认为订货日期tid_time)")
	private String date_type = "tid_time";
	@ZteSoftCommentAnnotationParam(name="订单id",type="String",isNecessary="Y",desc="如果订单id不为空则按订单id查询订单，其他条件无效")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="开始时间",type="String",isNecessary="Y",desc="开始时间(格式：yyyy-MM-dd HH:mm:ss)")
	private String begin_time;
	@ZteSoftCommentAnnotationParam(name="结束时间",type="String",isNecessary="Y",desc="结束时间(格式：yyyy-MM-dd HH:mm:ss)")
	private String end_time;
	@ZteSoftCommentAnnotationParam(name="第几页码",type="String",isNecessary="Y",desc="第几页码默认1")
	private int page_no=1;
	@ZteSoftCommentAnnotationParam(name="每页大小",type="String",isNecessary="Y",desc="每页大小默认100")
	private int page_size=100;

	
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(date_type))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "date_type不能为空"));
		if(!"tid_time".equals(date_type))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "date_type暂时只支持订货日期tid_time"));
		if(StringUtils.isEmpty(begin_time))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "begin_time不能为空"));
		if(StringUtils.isEmpty(end_time))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "end_time不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.taobao.ordersync";
	}

	public String getDate_type() {
		return date_type;
	}

	public void setDate_type(String date_type) {
		this.date_type = date_type;
	}

	public String getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public int getPage_no() {
		return page_no;
	}

	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}

	public int getPage_size() {
		return page_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

}
