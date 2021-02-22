package zte.net.ecsord.params.wyg.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.drools.core.util.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import zte.net.ecsord.params.busi.req.OrderItemExtvtlBusiRequest;
import zte.net.ecsord.params.wyg.vo.GoodInfo;


/**
 * 
 * @author sguo 
 * 订单管理系统将订单状态同步到新商城
 * 
 */
public class NotifyOrderInfoWYGRequset extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="唯一的接口流水号",type="String",isNecessary="Y",desc="serial_no：订单：EM+ yyyymmddhhmiss+(5位流水号)；非订单：P51+ yyyymmddhhmiss+(5位流水号)；")
	private String serial_no;
	
	@ZteSoftCommentAnnotationParam(name="商城前端订单编码",type="String",isNecessary="Y",desc="OutnotNeedReqStrOrderId：商城前端订单编码")
	private String OutnotNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name="商城来源",type="String",isNecessary="Y",desc="order_source：商城来源")
	private String order_source;	
	
	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="out_order_id：外部订单号")
	private String out_order_id;
	
	@ZteSoftCommentAnnotationParam(name="环节编码",type="String",isNecessary="Y",desc="trace_code：环节编码")
	private String trace_code;		
	
//	@ZteSoftCommentAnnotationParam(name="终端串号",type="String",isNecessary="Y",desc="TerminalNo：终端串号")
//	private String TerminalNo;
	
	@ZteSoftCommentAnnotationParam(name="卡类ICCID",type="String",isNecessary="Y",desc="sim_ICCID：卡类ICCID")
	private String sim_ICCID;	
	
	@ZteSoftCommentAnnotationParam(name="快递公司",type="String",isNecessary="N",desc="logistics：快递公司")
	private String logistics;		
	
	@ZteSoftCommentAnnotationParam(name="物流单编号",type="String",isNecessary="N",desc="logistics_no：物流单编号")
	private String logistics_no;	
	
	@ZteSoftCommentAnnotationParam(name="订单日志信息",type="String",isNecessary="N",desc="order_desc：订单日志信息")
	private String order_desc;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：订单编号")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name="处理结果描述",type="String",isNecessary="Y",desc="deal_desc：处理结果描述")
	private String deal_desc;

	@ZteSoftCommentAnnotationParam(name="货品信息",type="String",isNecessary="Y",desc="good_info：货品信息")
	private List<GoodInfo> good_info;

	public String getOutnotNeedReqStrOrderId() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_TID);
	}

	public void setOutnotNeedReqStrOrderId(String outnotNeedReqStrOrderId) {
		OutnotNeedReqStrOrderId = outnotNeedReqStrOrderId;
	}

	public String getLogistics() {
		String logi_company_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIPPING_COMPANY);
		String logi_company_code = CommonDataFactory.getInstance().getLogiCompanyCode(logi_company_id);
		return logi_company_code==null?EcsOrderConsts.EMPTY_STR_VALUE:logi_company_code;
	}


	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getSerial_no() {
		return CommonDataFactory.getInstance().getActiveNo(AttrConsts.ACTIVE_NO_ON_OFF);
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public String getOrder_source() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);
	}

	public void setOrder_source(String order_source) {
		this.order_source = order_source;
	}

	public String getOut_order_id() {
		out_order_id = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getOut_tid();
		return out_order_id;
	}

	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}

	public String getTrace_code() {
		if(null==trace_code){//环节不是属性
			trace_code = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getFlow_trace_id();
		}
		return trace_code;
	}

	public void setTrace_code(String trace_code) {
		this.trace_code = trace_code;
	}

	public String getSim_ICCID() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID);
	}

	public void setSim_ICCID(String sim_ICCID) {
		this.sim_ICCID = sim_ICCID;
	}

	public String getLogistics_no() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.LOGI_NO);
	}

	public void setLogistics_no(String logistics_no) {
		this.logistics_no = logistics_no;
	}

	public String getOrder_desc() {
		return "";
	}

	public void setOrder_desc(String order_desc) {
		this.order_desc = order_desc;
	}

	public String getDeal_desc() {
		if(StringUtils.isEmpty(deal_desc)){
			deal_desc = "处理结果描述";
		}
		return deal_desc;
	}

	public void setDeal_desc(String deal_desc) {
		this.deal_desc = deal_desc;
	}

	public List<GoodInfo> getGood_info() {
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);//订单来源
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_TYPE);//商品类型
		String order_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_TYPE);//订单类型
		if(EcsOrderConsts.ORDER_FROM_10053.equals(order_from)&&EcsOrderConsts.GOODS_TYPE_PHONE.equals(goods_type)&&EcsOrderConsts.ORDER_TYPE_09.equals(order_type)){//B2B商城,裸机,订单类型B2B
			good_info = new ArrayList<GoodInfo>();
			List<OrderItemExtvtlBusiRequest> goods = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemExtvtlBusiRequests();//订单树为空,则直接让空指针异常抛出
			if(null!=goods&&goods.size()>0){//没有数据时,继续完成接口调用,待爆出业务异常时核查为何没数据
				for(OrderItemExtvtlBusiRequest request:goods){
					GoodInfo good = new GoodInfo();
					String sku = request.getSku();
					String terminal_num = request.getResources_code();
					good.setSku(null==sku?"":sku);
					good.setTerminal_no(null==terminal_num?"":terminal_num);
					good_info.add(good);
				}
			}
		}else{
			good_info = new ArrayList<GoodInfo>();
			GoodInfo good = new GoodInfo();
			Map terminalSpec = CommonDataFactory.getInstance().getProductSpecMap(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10000);
			String sku = (String) terminalSpec.get(SpecConsts.SKU);
			String terminal_num = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TERMINAL_NUM);
			good.setSku(null==sku?"":sku);
			good.setTerminal_no(null==terminal_num?"":terminal_num);
			good_info.add(good);
		}
		return good_info;
	}

	public void setGood_info(List<GoodInfo> good_info) {
		this.good_info = good_info;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.wyg.notifyorderinfo";
	}

}
