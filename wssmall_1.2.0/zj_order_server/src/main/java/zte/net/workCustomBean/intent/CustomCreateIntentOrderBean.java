package zte.net.workCustomBean.intent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.CrmConstants;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomCfgManager;

import edu.emory.mathcs.backport.java.util.Arrays;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.workCustom.po.ES_ORDER_INTENT;
import zte.net.workCustomBean.common.BasicBusiBean;

/**
 * 线下模式-生成意向单
 * @author cqq 20181224
 *
 */
public class CustomCreateIntentOrderBean extends BasicBusiBean {
	@Resource
	private IWorkCustomCfgManager workCustomCfgManager;
	@Resource
	private IEcsOrdManager ecsOrdManager;

	@Override
	public String doBusi() throws Exception {
		OrderTreeBusiRequest orderTree  = this.flowData.getOrderTree();
		
		//生成意向单
		this.intentOrderCreate(orderTree.getOrder_id());
		
		return "";
	}
	
	private String getCurrTimeString() throws Exception{
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		String date = format.format(now);
		
		return date;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	private void intentOrderCreate(String order_id) throws Exception {
		//重新生成意向单
		OrderTreeBusiRequest orderTree = this.getFlowData().getOrderTree();
		
		if(orderTree == null)
			throw new Exception("订单树对象为空");
		
		ES_ORDER_INTENT intent = null;
		
		ES_ORDER_INTENT intentParam = new ES_ORDER_INTENT();
		intentParam.setOrder_id(order_id);
		
		List<ES_ORDER_INTENT> intentRet = this.workCustomCfgManager.qryOrderIntentList(intentParam , null);
		
		boolean intent_exist = false;
		
		//没有意向单直接返回
		if(intentRet==null || intentRet.size()==0){
			intent = new ES_ORDER_INTENT();
		}else{
			intent = intentRet.get(0);
			intent_exist = true;
		}
		
		String attr_names = "out_tid,zb_inf_id,order_city_code,district_code,tid_time,"
				+"order_from,phone_num,mainNumber,order_state,if_Send_Photos,refund_status,"
				+"kingcard_status,service_remarks,is_no_modify,is_realname,phone_owner_name,certi_type,"
				+"cert_card_num,cert_address,cert_card_nation,certi_sex,birthday,"
				+"cert_issuer,cert_failure_time,cert_eff_time,phone_num,cust_id,"
				+"CustomerType,group_code,cert_num2,messages_send_lasttime,messages_send_count,"
				+"cert_pic_flag,GoodsID,GoodsName,goods_type,goods_cat_id,sell_price,"
				+"develop_code_new,develop_name_new,develop_point_code_new,development_point_name,"
				+"out_operator,out_operator_name,out_office,out_office_name,order_origfee,"
				+"pay_time,order_realfee,pay_status,paytype,pay_method,pay_sequ,pay_back_sequ,"
				+"logi_no,post_fee,n_shipping_amount,shipping_company,receiver_mobile,shipping_time,"
				+"ship_name,province,city,district,ship_addr,reference_phone,receiver_mobile,"
				+"market_user_id,seed_user_id,top_share_userid,top_share_num,top_seed_professional_line,"
				+"top_seed_type,top_seed_group_id";
		
		String[] arr = attr_names.split(",");
		List<String> attr_name_list = Arrays.asList(arr);
		
		Map attr_map = this.ecsOrdManager.getAttrValuesBatch(order_id, attr_name_list);

		
		intent.setIntent_order_id(Const.getStrValue(attr_map,"out_tid"));
		intent.setOrder_province_code(Const.getStrValue(attr_map,"province"));
		intent.setOrder_city_code(Const.getStrValue(attr_map,"order_city_code"));
		intent.setOrder_county_code("ZJ"+Const.getStrValue(attr_map,"district_code"));
		intent.setGoods_name(Const.getStrValue(attr_map,"goodsname"));
		//不可修改字段落值
		intent.setIs_no_modify(Const.getStrValue(attr_map,"is_no_modify"));
		
		intent.setGoods_id(Const.getStrValue(attr_map,"goodsid"));
		intent.setSource_system(orderTree.getOrderExtBusiRequest().getOrder_channel());
		intent.setSource_system_type(orderTree.getOrderExtBusiRequest().getOrder_from());
		intent.setRemark("线上转线下，后台自动生成意向单");
		intent.setStatus("01");
		
		intent.setSource_from("ECS");
		intent.setOrder_id(order_id);
		intent.setCreate_time(this.getCurrTimeString());
		String county_id = Const.getStrValue(attr_map,"county_id");
		if(StringUtils.isBlank(county_id)){
			county_id = this.ecsOrdManager.queryCountyCoding("ZJ"+Const.getStrValue(attr_map,"district_code"));
		}
		intent.setCounty_id(county_id);
		intent.setProd_offer_code(Const.getStrValue(attr_map,"goodsid"));
		
		intent.setProd_offer_name(Const.getStrValue(attr_map,"goodsname"));
		intent.setGoods_num("1");
		intent.setOffer_price("0");
		intent.setReal_offer_price("0");
		intent.setDevelop_point_code(Const.getStrValue(attr_map,"develop_point_code_new"));
		
		intent.setDevelop_point_name(Const.getStrValue(attr_map,"development_point_name"));
		intent.setDevelop_code(Const.getStrValue(attr_map,"develop_code_new"));
		intent.setDevelop_name(Const.getStrValue(attr_map,"develop_name_new"));
		intent.setShip_name(Const.getStrValue(attr_map,"ship_name"));
		intent.setShip_tel(Const.getStrValue(attr_map,"reference_phone"));
		
		intent.setShip_addr(Const.getStrValue(attr_map,"ship_addr"));
		intent.setShip_tel2(Const.getStrValue(attr_map,"receiver_mobile"));
		
		String lock_id = "";
		String lock_name = "";
		
		String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		OrderLockBusiRequest orderLock = CommonDataFactory.getInstance().
				getOrderLockBusiRequest(orderTree, trace_id);
		
		if(orderLock != null){
			lock_id = orderLock.getLock_user_id();
			lock_name = orderLock.getLock_user_name();
		}
		
		intent.setLock_id(lock_id);
		intent.setLock_name(lock_name);
		intent.setAllot_status("1");
		intent.setIs_real_name(Const.getStrValue(attr_map,"is_realname"));
		intent.setCustomer_name(Const.getStrValue(attr_map,"phone_owner_name"));
		
		intent.setCert_type(Const.getStrValue(attr_map,"certi_type"));
		intent.setCert_num(Const.getStrValue(attr_map,"cert_card_num"));
		intent.setCert_addr(Const.getStrValue(attr_map,"cert_address"));
		intent.setCert_nation(Const.getStrValue(attr_map,"cert_card_nation"));
		intent.setCert_sex(Const.getStrValue(attr_map,"certi_sex"));
		
		intent.setCust_birthday(Const.getStrValue(attr_map,"birthday"));
		intent.setCert_issuedat(Const.getStrValue(attr_map,"cert_issuer"));
		
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		SimpleDateFormat format8 = new SimpleDateFormat(CrmConstants.DATE_FORMAT_8);
		
		if(attr_map.containsKey("cert_failure_time")){
			Object cert_failure_time = attr_map.get("cert_failure_time");
			
			if(cert_failure_time instanceof Date){
				intent.setCert_expire_date(format8.format(cert_failure_time));
			}else if(cert_failure_time instanceof String && StringUtils.isNotBlank(String.valueOf(cert_failure_time))){
				intent.setCert_expire_date(format8.format(format.parse(String.valueOf(cert_failure_time))));
			}
		}
		
		if(attr_map.containsKey("cert_eff_time")){
			Object cert_eff_time = attr_map.get("cert_eff_time");
			
			if(cert_eff_time instanceof Date){
				intent.setCert_effected_date(format8.format(cert_eff_time));
			}else if(cert_eff_time instanceof String && StringUtils.isNotBlank(String.valueOf(cert_eff_time))){
				intent.setCert_effected_date(format8.format(format.parse(String.valueOf(cert_eff_time))));
			}
		}
		
		intent.setCust_tel(Const.getStrValue(attr_map,"phone_num"));
		
		intent.setCustomer_type(Const.getStrValue(attr_map,"customertype"));
		intent.setCust_id(Const.getStrValue(attr_map,"cust_id"));
		intent.setGroup_code(Const.getStrValue(attr_map,"group_code"));
		intent.setReq_swift_num("");
		intent.setCheck_type("03");
		
		intent.setAcc_nbr(Const.getStrValue(attr_map,"phone_num"));
		intent.setMarket_user_id(Const.getStrValue(attr_map, "market_user_id"));
		intent.setSeed_user_id(Const.getStrValue(attr_map, "seed_user_id"));
		intent.setMainnumber(Const.getStrValue(attr_map, "mainnumber"));
		intent.setIs_online_order("1");
		intent.setTop_share_userid(Const.getStrValue(attr_map, "top_share_userid"));
		intent.setTop_share_num(Const.getStrValue(attr_map, "top_share_num"));
		intent.setTop_seed_professional_line(Const.getStrValue(attr_map, "top_seed_professional_line"));
		intent.setTop_seed_type(Const.getStrValue(attr_map, "top_seed_type"));
		intent.setTop_seed_group_id(Const.getStrValue(attr_map, "top_seed_group_id"));
		if(intent_exist){
			this.workCustomCfgManager.updateOrderIntent(intent);
		}else{
			this.workCustomCfgManager.newOrderIntent(intent);
		}

		//更新订单表es_order_extvtl 里的intent_order_id
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
				new String[] { "intent_order_id" }, new String[] { Const.getStrValue(attr_map,"out_tid") });
	}
	
}
