package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;
import consts.ConstsCore;
import params.ZteError;
import params.ZteRequest;
import zte.params.order.resp.OrderShipResp;

public class OrderShipReq extends ZteRequest<OrderShipResp> {
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="订单编号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="物流公司编号",type="String",isNecessary="N",desc="物流公司编号，需选获取物流公司列表查询物流公司编号")
	private String logi_id;//物流公司ID
	@ZteSoftCommentAnnotationParam(name="物流公司名称",type="String",isNecessary="N",desc="物流公司名称")
	private String logi_name;//物流公司名称
	@ZteSoftCommentAnnotationParam(name="物流单号",type="String",isNecessary="N",desc="物流单号")
	private String logi_no;//物流单号
	@ZteSoftCommentAnnotationParam(name="创库编号",type="String",isNecessary="N",desc="创库编号")
	private String house_id;//创库ID
	@ZteSoftCommentAnnotationParam(name="发货人编号",type="String",isNecessary="N",desc="发货人编号")
	private String confirm_userid;
	@ZteSoftCommentAnnotationParam(name="发货人名称",type="String",isNecessary="N",desc="发货人名称")
	private String confirm_username;
	@ZteSoftCommentAnnotationParam(name="收货人手机号码",type="String",isNecessary="N",desc="收货人手机号码")
	private String ship_mobile;
	@ZteSoftCommentAnnotationParam(name="收货人名称",type="String",isNecessary="N",desc="收货人名称")
	private String ship_name;
	@ZteSoftCommentAnnotationParam(name="收货人地址",type="String",isNecessary="N",desc="收货人地址")
	private String ship_addr;
	@ZteSoftCommentAnnotationParam(name="收货人邮编",type="String",isNecessary="N",desc="收货人邮编")
	private String ship_zip;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(order_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "order_id不能为空"));
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.ship";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getConfirm_userid() {
		return confirm_userid;
	}

	public void setConfirm_userid(String confirm_userid) {
		this.confirm_userid = confirm_userid;
	}

	public String getConfirm_username() {
		return confirm_username;
	}

	public void setConfirm_username(String confirm_username) {
		this.confirm_username = confirm_username;
	}

	public String getLogi_id() {
		return logi_id;
	}

	public void setLogi_id(String logi_id) {
		this.logi_id = logi_id;
	}

	public String getLogi_name() {
		return logi_name;
	}

	public void setLogi_name(String logi_name) {
		this.logi_name = logi_name;
	}

	public String getLogi_no() {
		return logi_no;
	}

	public void setLogi_no(String logi_no) {
		this.logi_no = logi_no;
	}

	public String getHouse_id() {
		return house_id;
	}

	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}

	public String getShip_mobile() {
		return ship_mobile;
	}

	public void setShip_mobile(String ship_mobile) {
		this.ship_mobile = ship_mobile;
	}

	public String getShip_name() {
		return ship_name;
	}

	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}

	public String getShip_addr() {
		return ship_addr;
	}

	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}

	public String getShip_zip() {
		return ship_zip;
	}

	public void setShip_zip(String ship_zip) {
		this.ship_zip = ship_zip;
	}

}
