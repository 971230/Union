package zte.params.order.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import params.ZteError;
import params.ZteRequest;
import zte.params.order.resp.OrderAddResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.model.WarehousePurorder;
import commons.CommonTools;

import consts.ConstsCore;

/**
 * 添加订单
 * @作者 MoChunrun
 * @创建日期 2014-1-8 
 * @版本 V 1.0
 */
public class OrderAddReq extends ZteRequest<OrderAddResp> {

	private ZteRequest zteRequest;
	@SuppressWarnings("unused") //
	@ZteSoftCommentAnnotationParam(name="购买信息",type="Map",isNecessary="Y",desc="product_id：购买的产品ID 不能为空。"
		+"goods_num：购买数量   "
		+"spec_id：规格id（团购、秒杀传入活动id）   "
		+"payment_id：支付方式ID"
		+"dlyaddressid：门店取货地址ID。     "
		+"member_id：会员ID。                "
		+"shipping_id：配送方式ID。          "
		+"p_order_amount:订单金额         "
		+"p_ship_amount:物流金额"
		+"item_type:0普通订单,3团购订单,2赠品订单        "
		+"ship_mobile：收货人手机号码。      "
		+"ship_name：收货人姓名。      "
		+"ship_addr：收货人地址。            "
		+"ship_day：收货日期。            "
		+"ship_tel：收货人电话号码。          "
		+"ship_zip：收货人邮编。              "
		+"province_id：收货人省份ID。        "
		+"city_id：收货人城市ID。            "
		+"region_id：收货人地区ID。          "
		+"province：收货人省份名称。         "
		+"city：收货人城市或称。             "
		+"region：收货人地区名称。           "
		+"lan_code：本地网代码。             "
		+"remark：备注。                     "
		+"invoice_content：发票内容 1明细 2办公用品 3电脑配件 4耗材。"
		+"invoice_title：发票台头。                                  "
		+"invoice_title_desc：描述。                                 "
		+"invoice_type：发票类型 1个人 2单位。                       "
		+"house_id:发货仓库ID"
		+"ship_day：收货时间（请填写中文内容） 1、工作日、双休日与假日均可送货 2、只有双休日、假日送货（工作日不用送） 3、只工作日送货（双休日、假日不用送）。 ")
	private List<Map> paramsl = new ArrayList<Map>(); //product_id,uname,name,
	
	private Map params; //product_id,uname,name,
	//map必填字段格式说明：product_id,userid,payment_id,member_id,(参考es_payment_cfg常量表),price(商品价格信息)
	@ZteSoftCommentAnnotationParam(name="订单类型",type="String",isNecessary="Y",desc="可选值：1平台获取、2手工新建、3批量导入、4售货自建、5采购入库,6采购出库")
	private String create_type ="1"; //1平台获取、2手工新建、3批量导入、4售货自建、5采购入订单,采购订单订单写入改造,6采购出订单,采购订单订单写入改造
	@ZteSoftCommentAnnotationParam(name="购买类型",type="String",isNecessary="Y",desc="可选值：1、进货2、铺货3、售卖")
	private String service_code=OrderStatus.SERVICE_CODE_ORDER; //个人购买
	
	private WarehousePurorder warehousePurorder;
	//物流费
	@ZteSoftCommentAnnotationParam(name="物流费用",type="String",isNecessary="N",desc="物流费用")
	private Double ship_amount;
	
	@ZteSoftCommentAnnotationParam(name="推广人ID",type="String",isNecessary="N",desc="推广人ID")
	private String spread_member_id;
	@ZteSoftCommentAnnotationParam(name="推广业务类型",type="String",isNecessary="N",desc="推广业务类型")
	private String service_type;
	@ZteSoftCommentAnnotationParam(name="推广业务ID",type="String",isNecessary="N",desc="推广业务ID")
	private String service_id;
	@ZteSoftCommentAnnotationParam(name="订单状态",type="String",isNecessary="N",desc="订单状态[2 未付款 3 已支付待备货 4 已备货待发货 5 已发货待确认收货 6 已确认收货 7 已完成订单]")
	private String order_status;
	
	private void checkMap(Map map){
		if(map.get("product_id")==null)CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "params-->product_id不能为空"));
		if(map.get("payment_id")==null)
			map.put("payment_id",OrderStatus.PAY_PAYMENT_CFG_2); //add by wui
		if(map.get("goods_num")==null){
			map.put("goods_num","1");
		}
		//CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "params-->payment_id不能为空"));
		//if(map.get("member_id")==null)CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "params-->member_id不能为空"));
	}
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
//		if(StringUtil.isEmpty(this.userSessionId))
//			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "用户session不能为空，请传入http request session值"));
		if(params==null && paramsl.size()==0)CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "商品不能为空"));
		if(StringUtils.isEmpty(create_type))//CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "create_type不能为空"));
		if(StringUtils.isEmpty(service_code))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "service_code不能为空"));
		if(params!=null){
			checkMap(params);
		}
		for(Map map:paramsl){
			checkMap(map);
		}
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.add";
	}

	public List<Map> getParamsl() {
		return paramsl;
	}

	public void setParamsl(List<Map> paramsl) {
		this.paramsl = paramsl;
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	public String getCreate_type() {
		return create_type;
	}

	public void setCreate_type(String create_type) {
		this.create_type = create_type;
	}



	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	public WarehousePurorder getWarehousePurorder() {
		return warehousePurorder;
	}

	public void setWarehousePurorder(WarehousePurorder warehousePurorder) {
		this.warehousePurorder = warehousePurorder;
	}

	public Double getShip_amount() {
		return ship_amount;
	}

	public void setShip_amount(Double ship_amount) {
		this.ship_amount = ship_amount;
	}

	public ZteRequest getZteRequest() {
		return zteRequest;
	}

	public void setZteRequest(ZteRequest zteRequest) {
		this.zteRequest = zteRequest;
	}

	public String getSpread_member_id() {
		return spread_member_id;
	}

	public void setSpread_member_id(String spread_member_id) {
		this.spread_member_id = spread_member_id;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

}
