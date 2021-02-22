package zte.net.ecsord.params.sf.req;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.sf.vo.Extra;
import zte.net.ecsord.params.sf.vo.Option;
import zte.net.ecsord.params.sf.vo.OrderOptionAddedService;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.Goods;

/**
 * 
 * @author sguo 
 * 订单信息同步
 * 
 */
public class NotifyOrderInfoSFRequset extends ZteRequest {
	
	//?老系统用的是外部单号,新系统的外部单号全部为数字,重复几率比较大.
	@ZteSoftCommentAnnotationParam(name="客户订单号",type="String",isNecessary="Y",desc="orderid：建议英文字母+YYMMDD（日期）+流水号，如：TB1207300000001")
	private String orderid;

	@ZteSoftCommentAnnotationParam(name="快件产品类别",type="String",isNecessary="Y",desc="express_type：固定值：1 (可根据需要定制扩展)1标准快递2顺丰特惠9顺E宝平邮10顺E宝挂号")
	private String express_type="1";

	@ZteSoftCommentAnnotationParam(name="寄件方公司名称",type="String",isNecessary="Y",desc="j_company：_SYSTEM寄件方公司名称, _SYSTEM表示如果不提供，将从系统配置获取")
	private String j_company;	

	@ZteSoftCommentAnnotationParam(name="寄件方联系人",type="String",isNecessary="Y",desc="j_contact：_SYSTEM寄件方联系人, _SYSTEM表示如果不提供，将从系统配置获取")
	private String j_contact;	

	@ZteSoftCommentAnnotationParam(name="寄件方联系电话",type="String",isNecessary="Y",desc="j_tel：_SYSTEM寄件方联系电话, _SYSTEM表示如果不提供，将从系统配置获取")
	private String j_tel;

	@ZteSoftCommentAnnotationParam(name="寄件方手机",type="String",isNecessary="Y",desc="j_mobile：寄件方手机")
	private String j_mobile;

	@ZteSoftCommentAnnotationParam(name="寄件方详细地址",type="String",isNecessary="Y",desc="j_address：_SYSTEM寄件方详细地址，包括省市区，示例：“广东省深圳市福田区新洲十一街万基商务大厦10楼”, _SYSTEM表示如果不提供，将从系统配置获取")
	private String j_address;

	@ZteSoftCommentAnnotationParam(name="到件方公司名称",type="String",isNecessary="Y",desc="d_company：到件方公司名称")
	private String d_company;

	@ZteSoftCommentAnnotationParam(name="到件方联系人",type="String",isNecessary="Y",desc="d_contact：到件方联系人")
	private String d_contact;

	@ZteSoftCommentAnnotationParam(name="到件方联系电话",type="String",isNecessary="Y",desc="d_tel：到件方联系电话")
	private String d_tel;

	@ZteSoftCommentAnnotationParam(name="到件方手机",type="String",isNecessary="Y",desc="d_mobile：到件方手机")
	private String d_mobile;

	@ZteSoftCommentAnnotationParam(name="到件方详细地址",type="String",isNecessary="Y",desc="d_address：示例：“广东省深圳市福田区新洲十一街万基商务大厦10楼”")
	private String d_address;

	@ZteSoftCommentAnnotationParam(name="包裹数",type="String",isNecessary="Y",desc="parcel_quantity：如果生成运单号码，需要提供此项。一个包裹对应一个运单号，数据必须准确。")
	private String parcel_quantity;

	//老系统为1
	@ZteSoftCommentAnnotationParam(name="付款方式",type="String",isNecessary="Y",desc="pay_method：固定值：3      _SYSTEM付款方式：1:寄方付2:收方付3:第三方付，默认为1。_SYSTEM表示如果不提供，将从系统配置获取")
	private String pay_method="3";

	@ZteSoftCommentAnnotationParam(name="寄件方所在省份",type="String",isNecessary="Y",desc="j_province：_SYSTEM寄件方所在省份字段填写要求：必须是标准的省名称称谓如：广东省如果是直辖市，请直接传北京、上海等")
	private String j_province;

	//老系统为_SYSTEM
	@ZteSoftCommentAnnotationParam(name="寄件方所属城市",type="String",isNecessary="Y",desc="j_city：_SYSTEM寄件方所属城市名称字段填写要求：必须是标准的城市称谓如：深圳市如果是直辖市，请直接传北京、上海等")
	private String j_city;

	//老系统为 [广东省]
	@ZteSoftCommentAnnotationParam(name="到件方所在省份",type="String",isNecessary="Y",desc="d_province：到件方所在省份字段填写要求：必须是标准的省名称称谓如：广东省如果是直辖市，请直接传北京、上海等")
	private String d_province;

	@ZteSoftCommentAnnotationParam(name="到件方所属城市",type="String",isNecessary="Y",desc="d_city：到件方所属城市名称字段填写要求：必须是标准的城市称谓如：深圳市如果是直辖市，请直接传北京、上海等")
	private String d_city;

	@ZteSoftCommentAnnotationParam(name="托寄物声明价值",type="String",isNecessary="Y",desc="declared_value：托寄物声明价值。如果是出口件，则必填（报关使用）。")
	private String declared_value;

	@ZteSoftCommentAnnotationParam(name="托寄物声明价值币别",type="String",isNecessary="Y",desc="declared_value_currency：如果目的地是中国大陆的，则默认为CNY，否则默认为USD托寄物声明价值币别：CNY:人民币HKD:港币USD:美元NTD:新台币RUB:卢布EUR:欧元MOP:澳门元SGD:新元JPY:日元KRW:韩元MYR:马币VND:越南盾THB:泰铢AUD:澳大利亚元MNT:图格里克")
	private String declared_value_currency;

	@ZteSoftCommentAnnotationParam(name="月结卡号",type="String",isNecessary="Y",desc="custid：_SYSTEM 固定值  0200642070")
	private String custid="0200642070";

	@ZteSoftCommentAnnotationParam(name="模板选择",type="String",isNecessary="Y",desc="template：模板选择")
	private String template;

	@ZteSoftCommentAnnotationParam(name="寄方国家",type="String",isNecessary="Y",desc="j_country：寄方国家")
	private String j_country;

	@ZteSoftCommentAnnotationParam(name="寄件人所在县/区 ",type="String",isNecessary="Y",desc="j_county：寄件人所在县/区 ")
	private String j_county;

	@ZteSoftCommentAnnotationParam(name="寄件方代码",type="String",isNecessary="Y",desc="j_shippercode：_SYSTEM寄件方代码如果是国际件，则要传这个字段，用于表示寄方国家的城市。如果此国家整体是以代理商来提供服务的，则此字段可能需要传国家编码。具体商务沟通中双方约定。 ")
	private String j_shippercode;

	@ZteSoftCommentAnnotationParam(name="寄方邮编",type="String",isNecessary="Y",desc="j_post_code：国际件必填，国内（包括港澳台）可不填。 ")
	private String j_post_code;

	@ZteSoftCommentAnnotationParam(name="到方国家",type="String",isNecessary="Y",desc="d_country：到方国家 ")
	private String d_country;

	@ZteSoftCommentAnnotationParam(name="到件人",type="String",isNecessary="Y",desc="d_county：所在县/区，必须是标准的县/区称谓，示例：“福田区”（区字不要省略） ")
	private String d_county;

	@ZteSoftCommentAnnotationParam(name="到件方代码",type="String",isNecessary="Y",desc="d_deliverycode：如果是国际件，则要传这个字段，用于表示到方国家的城市。如果此国家整体是以代理商来提供服务的，则此字段可能需要传国家编码。具体商务沟通中双方约定 ")
	private String d_deliverycode;

	@ZteSoftCommentAnnotationParam(name="到方邮编",type="String",isNecessary="Y",desc="d_post_code：国际件必填，国内（包括港澳台）可不填。 ")
	private String d_post_code;

	@ZteSoftCommentAnnotationParam(name="订单货物总重量",type="String",isNecessary="Y",desc="cargo_total_weight:单位KG，如果提供此值，必须>0  ;重量默认首重 1")
	private String cargo_total_weight="1";

	@ZteSoftCommentAnnotationParam(name="要求上门取件开始时间",type="String",isNecessary="Y",desc="sendstarttime:格式：YYYY-MM-DD HH24:MM:SS，示例：2012-7-30 09:30:00，默认为系统收到订单的系统时间")
	private String sendstarttime;

	@ZteSoftCommentAnnotationParam(name="运单号",type="String",isNecessary="Y",desc="mailno:一个订单只能有一个主单号，如果是子母单的情况，请以半角逗号分隔，主单号在第一个位置，如“755123456789,001123456789,002123456789”")
	private String mailno;

	@ZteSoftCommentAnnotationParam(name="签回单单号",type="String",isNecessary="Y",desc="return_tracking:签回单单号")
	private String return_tracking;

	@ZteSoftCommentAnnotationParam(name="备注",type="String",isNecessary="Y",desc="remark:备注")
	private String remark;

	@ZteSoftCommentAnnotationParam(name="是否需要签回单号",type="String",isNecessary="Y",desc="need_return_tracking_no:1：需要")
	private String need_return_tracking_no;

	//老系统为 ""
	@ZteSoftCommentAnnotationParam(name="是否下call",type="String",isNecessary="Y",desc="is_docall:是否下call")
	private String is_docall;

	@ZteSoftCommentAnnotationParam(name="是否申请运单号",type="String",isNecessary="Y",desc="is_gen_bill_no:_SYSTEM是否申请运单号1-申请运单号，其他否SYSTEM表示如果不提供，将从系统配置获取")
	private String is_gen_bill_no;

	//老系统为""
	@ZteSoftCommentAnnotationParam(name="是否生成电子运单图片",type="String",isNecessary="Y",desc="is_gen_eletric_pic:_SYSTEM是否生成电子运单图片1-生成，其他否SYSTEM表示如果不提供，将从系统配置获取")
	private String is_gen_eletric_pic;

	@ZteSoftCommentAnnotationParam(name="图片格式",type="String",isNecessary="Y",desc="waybill_size:1图片格式：如果需要顺丰系统推送图片，则要传这个值。1：A42：A5......具体图片样式不同客户可能不同，所以具体传什么值要在接入测试时进行沟通。")
	private String waybill_size;
	
	@ZteSoftCommentAnnotationParam(name="货物名称",type="String",isNecessary="Y",desc="货物名称,可有多个货物,如果有多个,以逗号分隔")
	private String cargo;
	
	@ZteSoftCommentAnnotationParam(name="长",type="String",isNecessary="N",desc="长")
	private String cargo_length;
	
	@ZteSoftCommentAnnotationParam(name="宽",type="String",isNecessary="N",desc="宽")
	private String cargo_width;
	
	@ZteSoftCommentAnnotationParam(name="高",type="String",isNecessary="N",desc="高")
	private String cargo_height;

	@ZteSoftCommentAnnotationParam(name="图片格式",type="String",isNecessary="Y",desc="waybill_size:1图片格式：如果需要顺丰系统推送图片，则要传这个值。1：A42：A5......具体图片样式不同客户可能不同，所以具体传什么值要在接入测试时进行沟通。")
	private Option orderOption;
	
	@ZteSoftCommentAnnotationParam(name="扩展字段对象",type="String",isNecessary="Y",desc="扩展字段对象")
	private Extra extra;
	
	@ZteSoftCommentAnnotationParam(name="增值服务对象",type="String",isNecessary="Y",desc="增值服务对象")
	private List<OrderOptionAddedService> AddedService;
	
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getExpress_type() {
		this.express_type="1";
		return express_type;
	}

	public void setExpress_type(String express_type) {
		this.express_type = express_type;
	}

	public String getJ_company() {
		String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.ORDER_CITY_CODE);
		Map postRegion = CommonDataFactory.getInstance().getPostRegion(order_city_code);
		j_company = (String) postRegion.get("post_comp");
		return j_company;
	}

	public void setJ_company(String j_company) {
		this.j_company = j_company;
	}

	public String getJ_contact() {
		String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.ORDER_CITY_CODE);
		Map postRegion = CommonDataFactory.getInstance().getPostRegion(order_city_code);
		j_contact = (String) postRegion.get("poster");
		return j_contact;
	}

	public void setJ_contact(String j_contact) {
		this.j_contact = j_contact;
	}

	public String getJ_tel() {
		String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.ORDER_CITY_CODE);
		Map postRegion = CommonDataFactory.getInstance().getPostRegion(order_city_code);
		j_tel = (String) postRegion.get("post_tel");
		return j_tel;
	}

	public void setJ_tel(String j_tel) {
		this.j_tel = j_tel;
	}

	public String getJ_mobile() {
		j_mobile = "";
		return j_mobile;
	}

	public void setJ_mobile(String j_mobile) {
		this.j_mobile = j_mobile;
	}

	public String getJ_address() {
		String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.ORDER_CITY_CODE);
		Map postRegion = CommonDataFactory.getInstance().getPostRegion(order_city_code);
		j_address = (String) postRegion.get("post_address");
		return j_address;
	}

	public void setJ_address(String j_address) {
		this.j_address = j_address;
	}

	public String getD_company() {
		d_company = "";
		return d_company;
	}

	public void setD_company(String d_company) {
		this.d_company = d_company;
	}

	public String getD_contact() {
		d_contact = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.SHIP_NAME);
		return d_contact;
	}

	public void setD_contact(String d_contact) {
		this.d_contact = d_contact;
	}

	public String getD_tel() {
		d_tel = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.REVEIVER_MOBILE);
		return d_tel;
	}

	public void setD_tel(String d_tel) {
		this.d_tel = d_tel;
	}

	public String getD_mobile() {
		d_mobile = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.REFERENCE_PHONE);
		return d_mobile;
	}

	public void setD_mobile(String d_mobile) {
		this.d_mobile = d_mobile;
	}

	public String getD_address() {
		d_address = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.SHIP_ADDR);
		return d_address;
	}

	public void setD_address(String d_address) {
		this.d_address = d_address;
	}

	public String getParcel_quantity() {
		this.parcel_quantity="1";
		return parcel_quantity;
	}

	public void setParcel_quantity(String parcel_quantity) {
		this.parcel_quantity = parcel_quantity;
	}

	public String getPay_method() {
		
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getJ_province() {
		String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.ORDER_CITY_CODE);
		Map postRegion = CommonDataFactory.getInstance().getPostRegion(order_city_code);
		j_province = (String) postRegion.get("province");
		return j_province;
	}

	public void setJ_province(String j_province) {
		this.j_province = j_province;
	}

	public String getJ_city() {
		String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.ORDER_CITY_CODE);
		Map postRegion = CommonDataFactory.getInstance().getPostRegion(order_city_code);
		j_city = (String) postRegion.get("city");
		return j_city;
	}

	public void setJ_city(String j_city) {
		this.j_city = j_city;
	}

	public String getD_province() {
		d_province = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.PROVINCE);
		return d_province;
	}

	public void setD_province(String d_province) {
		this.d_province = d_province;
	}

	public String getD_city() {
		String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.ORDER_CITY_CODE);
		d_city = CommonDataFactory.getInstance().getLocalName(order_city_code);
		return d_city;
	}

	public void setD_city(String d_city) {
		this.d_city = d_city;
	}

	public String getDeclared_value() {
		return declared_value;
	}

	public void setDeclared_value(String declared_value) {
		this.declared_value = declared_value;
	}

	public String getDeclared_value_currency() {
		return declared_value_currency;
	}

	public void setDeclared_value_currency(String declared_value_currency) {
		this.declared_value_currency = declared_value_currency;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getJ_country() {
		return j_country;
	}

	public void setJ_country(String j_country) {
		this.j_country = j_country;
	}

	public String getJ_county() {
		String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.ORDER_CITY_CODE);
		Map postRegion = CommonDataFactory.getInstance().getPostRegion(order_city_code);
		j_county = (String) postRegion.get("district");
		return j_county;
	}

	public void setJ_county(String j_county) {
		this.j_county = j_county;
	}

	public String getJ_shippercode() {
		j_shippercode = "_SYSTEM";
		return j_shippercode;
	}

	public void setJ_shippercode(String j_shippercode) {
		this.j_shippercode = j_shippercode;
	}

	public String getJ_post_code() {
		String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.ORDER_CITY_CODE);
		Map postRegion = CommonDataFactory.getInstance().getPostRegion(order_city_code);
		j_post_code = (String) postRegion.get("post_code");
		return j_post_code;
	}

	public void setJ_post_code(String j_post_code) {
		this.j_post_code = j_post_code;
	}

	public String getD_country() {
		return d_country;
	}

	public void setD_country(String d_country) {
		this.d_country = d_country;
	}

	public String getD_county() {
		d_county = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.DISTRICT);
		return d_county;
	}

	public void setD_county(String d_county) {
		this.d_county = d_county;
	}

	public String getD_deliverycode() {
		String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.ORDER_CITY_CODE);
		String lan_code = CommonDataFactory.getInstance().getLanCode(order_city_code);
		d_deliverycode = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.DIC_AREA_CODE, lan_code);
		return d_deliverycode;
	}

	public void setD_deliverycode(String d_deliverycode) {
		this.d_deliverycode = d_deliverycode;
	}

	public String getD_post_code() {
		d_post_code = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.SHIP_ZIP);
		return d_post_code;
	}

	public void setD_post_code(String d_post_code) {
		this.d_post_code = d_post_code;
	}

	public String getCargo_total_weight() {
		return cargo_total_weight;
	}

	public void setCargo_total_weight(String cargo_total_weight) {
		this.cargo_total_weight = cargo_total_weight;
	}

	public String getSendstarttime() {
		return sendstarttime;
	}

	public void setSendstarttime(String sendstarttime) {
		this.sendstarttime = sendstarttime;
	}

	public String getMailno() {
		return mailno;
	}

	public void setMailno(String mailno) {
		this.mailno = mailno;
	}

	public String getReturn_tracking() {
		return return_tracking;
	}

	public void setReturn_tracking(String return_tracking) {
		this.return_tracking = return_tracking;
	}

	public String getRemark() {
		this.remark = "内部订单号:"+orderid;
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNeed_return_tracking_no() {
		return need_return_tracking_no;
	}

	public void setNeed_return_tracking_no(String need_return_tracking_no) {
		this.need_return_tracking_no = need_return_tracking_no;
	}

	public String getIs_docall() {
		is_docall = "";
		return is_docall;
	}

	public void setIs_docall(String is_docall) {
		this.is_docall = is_docall;
	}

	public String getIs_gen_bill_no() {
		is_gen_bill_no="1";
		return is_gen_bill_no;
	}

	public void setIs_gen_bill_no(String is_gen_bill_no) {
		this.is_gen_bill_no = is_gen_bill_no;
	}

	public String getIs_gen_eletric_pic() {
		is_gen_eletric_pic = "";
		return is_gen_eletric_pic;
	}

	public void setIs_gen_eletric_pic(String is_gen_eletric_pic) {
		this.is_gen_eletric_pic = is_gen_eletric_pic;
	}

	public String getWaybill_size() {
		waybill_size = "1";
		return waybill_size;
	}

	public void setWaybill_size(String waybill_size) {
		this.waybill_size = waybill_size;
	}

	public String getCargo_length() {
		return cargo_length;
	}

	public void setCargo_length(String cargo_length) {
		this.cargo_length = cargo_length;
	}

	public String getCargo_width() {
		return cargo_width;
	}

	public void setCargo_width(String cargo_width) {
		this.cargo_width = cargo_width;
	}

	public String getCargo_height() {
		return cargo_height;
	}

	public void setCargo_height(String cargo_height) {
		this.cargo_height = cargo_height;
	}

	public Option getOrderOption() {
		Option option = new Option();
		//实物货品名称、数量
		option.setName(CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.GOODSNAME));
		option.setCount(1);
		option.setUnit("u");
		option.setWeight(0D);
		option.setAmount(0D);
		return option;
	}
	
	public String getCargo() {
		this.setCargo(CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.GOODSNAME));
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public void setOrderOption(Option orderOption) {
		this.orderOption = orderOption;
	}

	public Extra getExtra() {
		return extra;
	}

	public void setExtra(Extra extra) {
		this.extra = extra;
	}

	
	
	public List<OrderOptionAddedService> getAddedService() {
		List<OrderOptionAddedService> list=new ArrayList<OrderOptionAddedService>();
			OrderOptionAddedService  ooas=new OrderOptionAddedService();
			ooas.setName("SDELIVERY");
			ooas.setValue("4");
		list.add(ooas);
		return AddedService;
	}

	public void setAddedService(List<OrderOptionAddedService> addedService) {
		AddedService = addedService;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.sf.notifyOrderInfo";
	}

}
