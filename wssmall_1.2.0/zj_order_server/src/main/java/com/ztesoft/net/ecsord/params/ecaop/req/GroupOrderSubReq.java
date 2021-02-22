package com.ztesoft.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.CustInfoVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.GroupInFoVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.KdInFoVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.YwInFoVO;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.util.ZjCommonUtils;

import params.ZteRequest; 
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderAdslBusiRequest;
import zte.net.ecsord.params.busi.req.OrderInternetBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
 
public class GroupOrderSubReq  extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId	;
	@ZteSoftCommentAnnotationParam(name = "操作点", type = "String", isNecessary = "Y", desc = "操作点")
	private String office_id="";
	@ZteSoftCommentAnnotationParam(name = "操作员", type = "String", isNecessary = "Y", desc = "操作员")
	private String operator_id="";
	@ZteSoftCommentAnnotationParam(name = "发展渠道", type = "String", isNecessary = "Y", desc = "发 展渠道")
	private String develop_id="";
	@ZteSoftCommentAnnotationParam(name = "发展人", type = "String", isNecessary = "Y", desc = "发展人")
	private String develop_man="";
	@ZteSoftCommentAnnotationParam(name = "总费用", type = "String", isNecessary = "Y", desc = "总费用")
	private String total_fee="";
	@ZteSoftCommentAnnotationParam(name = "是否支付", type = "String", isNecessary = "Y", desc = "是否已支付,0:未支付,1:已支付,默认已支付")
	private String is_pay="";
	@ZteSoftCommentAnnotationParam(name = "支付方式", type = "String", isNecessary = "Y", desc = "1 现金 40 沃钱包 41 支付宝  42 微信")
    private String fund_source;
	@ZteSoftCommentAnnotationParam(name = "群组信息", type = "String", isNecessary = "Y", desc = "群组信息")
	private GroupInFoVO groupInfo;
	@ZteSoftCommentAnnotationParam(name = "宽带信息", type = "String", isNecessary = "Y", desc = "宽带信息")
	private List<KdInFoVO> kdInfo;
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private List<YwInFoVO> ywInfo;
	/*@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private List<GwInFoVO> gwInfo;
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private List<YxInFoVO> yxInfo;*/
	@ZteSoftCommentAnnotationParam(name = "客户信息", type = "String", isNecessary = "Y", desc = "客户信息")
	private CustInfoVO custInfo;

	public String getIs_pay() {
	   String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);
       String catId = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, SpecConsts.CAT_ID);
	    if("10093".equals(order_from) && StringUtils.equals(catId, EcsOrderConsts.GOODS_CAT_ID_WUXIANKD)){
	           is_pay = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_STATUS);//接口传的是是否已支付
	    }else{
	           is_pay = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.IS_PAY);
	    }
		return is_pay;
	}

	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getOffice_id() {
	    if(StringUtils.isNotEmpty(this.office_id)){
            return office_id;
        }else{
            String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OFFICE);
            if(!StringUtil.isEmpty(OUT_OFFICE)){
                office_id = OUT_OFFICE;
            }else{
                office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
            }
            return office_id;
        }
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getOperator_id() {
	    if(StringUtils.isNotEmpty(this.operator_id)){
	        return operator_id;
	    }else{
	        String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
	        if(!StringUtil.isEmpty(OUT_OPERATOR)){
	            operator_id = OUT_OPERATOR;
	        }else{
	            operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
	        }
	        return operator_id;
	    }
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getDevelop_id() {
		String catId = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, SpecConsts.CAT_ID);
		if(!StringUtils.equals(catId, EcsOrderConsts.GOODS_CAT_ID_WUXIANKD)) {
			develop_id = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getDevelop_point_code();
			if(null == develop_id || "".equals(develop_id)){
				develop_id="";
			}
		}else {
			develop_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "develop_point_code_new");
		}
		return develop_id;
	}

	public void setDevelop_id(String develop_id) {
		this.develop_id = develop_id;
	}

	public String getDevelop_man() {
		String catId = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, SpecConsts.CAT_ID);
		if(!StringUtils.equals(catId, EcsOrderConsts.GOODS_CAT_ID_WUXIANKD)) {
			develop_man = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getDevelop_code();
			if(null == develop_man || "".equals(develop_man)){
				develop_man="";
			}
		} else {
			develop_man = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "develop_code_new");
		}
		return develop_man;
	}

	public void setDevelop_man(String develop_man) {
		this.develop_man = develop_man;
	}

	public String getTotal_fee() {
		//total_fee = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getTotal_fee();
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public GroupInFoVO getGroupInfo() {
//		String scheme_type = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, "scheme_type");
//		groupInfo.setScheme_type(scheme_type);
		return groupInfo;
	}

	public void setGroupInfo(GroupInFoVO groupInfo) {
		this.groupInfo = groupInfo;
	}

	public List<KdInFoVO> getKdInfo() {
		//无线宽带不用传宽带节点
		String catId = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, SpecConsts.CAT_ID);
		if(!StringUtils.equals(catId, EcsOrderConsts.GOODS_CAT_ID_WUXIANKD)) { 
			List<KdInFoVO> new_kd_list = new ArrayList<KdInFoVO>();
			List<OrderAdslBusiRequest> orderAdslBusiRequest = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest();
			String total_fee_new = "0";
			for(int i=0;i<orderAdslBusiRequest.size();i++){
				KdInFoVO new_kd = new KdInFoVO();
				String is_new="1";//是否新装,0.否;1.是
				String service_num1=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(i).getAdsl_account();//宽带账号
				String service_num2=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(i).getAdsl_number();//宽带号码
				String source_flag="0";//号码来源,1:总部;0:省份
				String url_key="";//服务器地址配置标识
				String http_request="";//向资源中心发起的HTTP请求:预占
				String res_number="";//资源中心号码
				String http_request1="";//向资源中心发起的HTTP请求:占用
				String nomalize_flag="1";//用户是否激活,1是,0否
				String goods_id=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getGoods_id();
				String service_type = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, goods_id, "service_type");//宽带服务类型
				String standard_address=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(i).getAdsl_addr();//标准地址
				String Object_id=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(i).getModerm_id();//光猫物品ID
				String speed_level= CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, goods_id, "bss_brand_speed");//速率,沃TV为空,宽带必传
				String product_id = "";//宽带产品
				String exch_code=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(i).getExch_code();//局向编码
				String product_type = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(i).getProduct_type();
				String totalFee="0";//宽带总费用
				String fee_list  = "";//费用清单：科目1&应收费用&减免费用&实收费用；科目2&应收费用&减免费用&实收费用；费用精确到小数点2位
				List<AttrFeeInfoBusiRequest> feeInfoBusiRequests = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getFeeInfoBusiRequests();
				for(int j=0;j<feeInfoBusiRequests.size();j++){
					if(!StringUtils.isEmpty(feeInfoBusiRequests.get(j).getIs_aop_fee())
							&&StringUtil.equals(product_type, feeInfoBusiRequests.get(j).getIs_aop_fee())){
						fee_list += feeInfoBusiRequests.get(j).getFee_item_code()+"&"+feeInfoBusiRequests.get(j).getO_fee_num()+"&"+feeInfoBusiRequests.get(j).getDiscount_fee()+"&"+feeInfoBusiRequests.get(j).getN_fee_num()+"&"+feeInfoBusiRequests.get(j).getFee_category()+";";
						String a = feeInfoBusiRequests.get(j).getO_fee_num()+"";
						String b = Double.parseDouble(totalFee)+"";
						totalFee =Double.parseDouble(totalFee)+feeInfoBusiRequests.get(j).getN_fee_num()+"";
					}
				}
				
				if(!StringUtil.isEmpty(fee_list)){
					fee_list = fee_list.substring(0, fee_list.lastIndexOf(";"));
				}
				String user_kind=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(i).getUser_kind();//用户种类,沃TV默认传11
				if(StringUtil.isEmpty(user_kind)){
					user_kind = "11";
				}
				String accessWay=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(i).getAccess_type();//接入方式
				String cust_grid="";//固网网格
				String grid="";//商企网格
				String cust_building_id="";//聚类市场信息
				String order_from = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getOrder_from();
				String plat_type = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getPlat_type();
				String user_addr = "";
				if(StringUtils.equals(order_from, "10078")|| StringUtils.equals(plat_type, "AO")) {
					user_addr = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getAdsl_addr_desc();
				}else {
					user_addr = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getUser_address();
				}
				//String user_addr=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(i).getUser_address();//装机地址
				String is_must="";//是否必要成员
				String newobject_status = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getObject_status();
				String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
				String county_id = "";
				String countyid = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getCounty_id();
				if(!StringUtil.isEmpty(countyid)){
					county_id = countyid;
				}else{
					county_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getCounty_id();
				}
				//String county_id = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(i).getCounty_id();//bss县份
				String object_status = "";
				
				/**
				 * 新增参数,收单透传
				 */
				String sale_mode = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(i).getSale_mode();
				String devic_gear = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(i).getDevic_gear();
				String is_done_active = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(i).getIs_done_active();
				
				if(!StringUtil.isEmpty(newobject_status)){
					object_status = newobject_status;
				}else{
					object_status = "0";
				}
				
				String is_wotv="";//是否沃TV
				if("TV".equals(product_type)){
					is_wotv="1";
					product_id = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, goods_id, "wotv_product_id");
					service_type ="6130";
				}else{
					is_wotv="0";
					product_id = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, goods_id, "brand_product_id");
				}
				total_fee_new = Double.valueOf(total_fee_new==null?"0":total_fee_new)+Double.valueOf(totalFee==null?"0":totalFee)+"";
				new_kd.setIs_new(is_new);
				new_kd.setService_num1(service_num1);
				new_kd.setService_num2(service_num2);
				new_kd.setSource_flag(source_flag);
				new_kd.setNomalize_flag(nomalize_flag);
				new_kd.setService_type(service_type);
				new_kd.setStandard_address(standard_address);
				new_kd.setObject_id(Object_id);
				new_kd.setSpeed_level(speed_level);
				new_kd.setProduct_id(product_id);
				new_kd.setExch_code(exch_code);
				new_kd.setFee_list(fee_list);
				new_kd.setUser_kind(user_kind);
				new_kd.setAccessWay(accessWay);
				new_kd.setTotalFee(totalFee);
				new_kd.setUser_addr(user_addr);
				new_kd.setIs_wotv(is_wotv);
				new_kd.setObject_status(object_status);
				new_kd.setCounty_id(county_id);//bss县份
				
				new_kd.setSale_mode(sale_mode);
				new_kd.setDevic_gear(devic_gear);
				new_kd.setIs_done_active(is_done_active);
				
				new_kd_list.add(new_kd);
			}
			total_fee = total_fee_new;
			kdInfo = new_kd_list;
		}
		return kdInfo;
	}

	public void setKdInfo(List<KdInFoVO> kdInfo) {
		this.kdInfo = kdInfo;
	}

	public List<YwInFoVO> getYwInfo() {
		String catId = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, SpecConsts.CAT_ID);
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String order_from_is_block =cacheUtil.getConfigInfo("order_from_is_block");

		if(StringUtils.equals(catId, EcsOrderConsts.GOODS_CAT_ID_WUXIANKD)) { 
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
			List<YwInFoVO> ywList = new ArrayList<YwInFoVO>();
			String total_fee_new = "0";
			List<OrderInternetBusiRequest> lists = orderTree.getOrderInternetBusiRequest();
			for (OrderInternetBusiRequest orderInternetBusiRequest : lists) {
				YwInFoVO new_ywinfo = new YwInFoVO();
				String evdoNum = orderInternetBusiRequest.getEvdo_num();
				String serviceNum = orderInternetBusiRequest.getService_num();
				new_ywinfo.setEvdo_num(evdoNum);
				new_ywinfo.setHttp_request(orderInternetBusiRequest.getHttp_request());
				new_ywinfo.setHttp_request1(orderInternetBusiRequest.getHttp_request1());
				if("10093".equals(orderInternetBusiRequest.getOrder_from())){
				    if(StringUtils.isNotEmpty(order_from_is_block) && order_from_is_block.contains(orderInternetBusiRequest.getOrder_from()+",")  && "1".equals(orderInternetBusiRequest.getIs_new())){
				        new_ywinfo.setIs_blankcard("0"); //只有白卡场景，白卡传1
				    }else{
				        new_ywinfo.setIs_blankcard("1"); //只有白卡场景，白卡传1
				    }
				}else{
                    new_ywinfo.setIs_blankcard(orderInternetBusiRequest.getIs_blankcard()); //只有白卡场景，白卡传1
				}
				new_ywinfo.setIs_main_number(orderInternetBusiRequest.getIs_main_number());
				new_ywinfo.setIs_new(StringUtils.isEmpty(orderInternetBusiRequest.getIs_new())?"1":orderInternetBusiRequest.getIs_new());
				new_ywinfo.setNomalize_flag("");
				new_ywinfo.setUser_knd(orderInternetBusiRequest.getUser_kind());
				//add by sgf
				new_ywinfo.setSale_mode(orderInternetBusiRequest.getSale_mode());
				new_ywinfo.setObject_esn(orderInternetBusiRequest.getObject_esn());
				new_ywinfo.setObject_id(orderInternetBusiRequest.getObject_id());
				if(StringUtils.isNotEmpty(order_from_is_block) && order_from_is_block.contains(orderInternetBusiRequest.getOrder_from()+",") &&StringUtils.isNotEmpty(orderInternetBusiRequest.getIs_new()) && "1".equals(orderInternetBusiRequest.getIs_new())){
				    new_ywinfo.setIs_backlog_order("1");//是否压单根据is_new 以及
				}else{
				    new_ywinfo.setIs_backlog_order("0");
				}
				String fee_list  = "";
				String totalFee="0";//总费用
				total_fee_new = "0";
				//科目1&应收费用&减免费用&实收费用
				List<AttrFeeInfoBusiRequest> feeInfoBusiRequests = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getFeeInfoBusiRequests();
				if(null != feeInfoBusiRequests && feeInfoBusiRequests.size() > 0) {
					for(int j=0;j<feeInfoBusiRequests.size();j++){
						if(StringUtils.equals(feeInfoBusiRequests.get(j).getIs_aop_fee(), orderInternetBusiRequest.getIs_new())) {
							//fee_list += feeInfoBusiRequests.get(j).getFee_item_code()+"&"+feeInfoBusiRequests.get(j).getO_fee_num()+"&"+feeInfoBusiRequests.get(j).getDiscount_fee()+"&"+feeInfoBusiRequests.get(j).getN_fee_num()+"&"+feeInfoBusiRequests.get(j).getFee_category()+";";
						    fee_list += feeInfoBusiRequests.get(j).getFee_category()+"&"+feeInfoBusiRequests.get(j).getO_fee_num()+"&"+feeInfoBusiRequests.get(j).getDiscount_fee()+"&"+feeInfoBusiRequests.get(j).getN_fee_num()+";";
						    totalFee =Double.parseDouble(totalFee)+feeInfoBusiRequests.get(j).getN_fee_num()+"";
						}
					}
					if(!StringUtil.isEmpty(fee_list)){
						fee_list = fee_list.substring(0, fee_list.lastIndexOf(";"));
					}
					new_ywinfo.setFeelist(fee_list);
				}
				
				new_ywinfo.setNumber_level(orderInternetBusiRequest.getNumber_level());
				
				new_ywinfo.setRes_number(orderInternetBusiRequest.getRes_number());
				new_ywinfo.setImsi(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "simid"));
				
				String goods_id = orderTree.getOrderItemBusiRequests().get(0).getGoods_id();
				String sql = "select t.p_code,t.sn from es_goods_package t where t.goods_id='" + goods_id + "' and t.source_from='" + ManagerUtils.getSourceFrom() + "'";
				IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
				Map goodsMap = baseDaoSupport.queryForMap(sql, null);
				
				//老用户
				if(StringUtils.equals(orderInternetBusiRequest.getIs_new(), "0")) {
					new_ywinfo.setProduct_id("");
					new_ywinfo.setSubs_id(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "subs_id"));
				}else {
					new_ywinfo.setProduct_id(CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, "brand_product_id"));
					new_ywinfo.setSubs_id("");
				}
				//new_ywinfo.setScheme_id("");
				new_ywinfo.setScheme_id_l(orderInternetBusiRequest.getScheme_id_l());
				new_ywinfo.setService_num(serviceNum);
				new_ywinfo.setSim(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID)); 
				new_ywinfo.setSource_flag("1");
				
				
				new_ywinfo.setUrl_key("AIP_SERVER");
				
				ywList.add(new_ywinfo);
				
				total_fee_new = Double.valueOf(total_fee_new==null?"0":total_fee_new)+Double.valueOf(totalFee==null?"0":totalFee)+"";
			}
			setTotal_fee(total_fee_new);
			ywInfo = ywList ;
		}else {
			String subs_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "subs_id");
			String service_num = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "service_num");
			if(!StringUtil.isEmpty(subs_id)){
				List<YwInFoVO> list = new ArrayList<YwInFoVO>();
				YwInFoVO new_ywinfo = new YwInFoVO();
				new_ywinfo.setSubs_id(subs_id);
				new_ywinfo.setService_num(service_num);
				new_ywinfo.setIs_main_number("1");
				new_ywinfo.setIs_new("0");
				new_ywinfo.setSource_flag("1");

				list.add(new_ywinfo);
				ywInfo = list;
			}
		}
		return ywInfo;
	}

	public void setYwInfo(List<YwInFoVO> ywInfo) {
		this.ywInfo = ywInfo;
	}

	public CustInfoVO getCustInfo() {
		CustInfoVO new_custInfo = new CustInfoVO();
		String customer_name=CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NAME);//客户名称
/*	    String customer_names=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_card_name();//证件号码,新用户必传
*/		String custType="0";//客户标识;0,新客户,1,老客户
		//String cert_type="11";//证件类型,新用户必传
		String cert_type = CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_BSS_CERT_TYPE", CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERTI_TYPE));
        if(!StringUtil.isEmpty(cert_type)){
        }else {
            cert_type="11";
        }
		String cert_num=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_card_num();//证件号码,新用户必传
		String cert_addr=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_address();//证件地址
		String cert_nation=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_card_nation();//民族
		String cert_issuedat=CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_ISSUER);//证件机关/
		String cert_expire_date="";//证件生效时间
		String cert_effected_date="";//证件失效时间
		String contact_name=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderDeliveryBusiRequests().get(0).getShip_name();//联系人名称
		String contact_phone=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderDeliveryBusiRequests().get(0).getShip_mobile();//联系电话
		String customer_adder=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_address();//客户地址
		String cert_verified="Y";//实名标识
		String num = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_card_num();
		String cert_birthday="";//生日
		String cert_sex="";//性别
	    String cert_num2=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_num2();//证件号码,新用户必传

		if(!StringUtil.isEmpty(num)){
			if(Integer.valueOf(num.substring(16, 17))%2==0){
				cert_sex="1";
			}else{
				cert_sex="0";
			}
			cert_birthday = num.substring(6, 14);
		}
		String req_swift_num="";//拍照流水号
		new_custInfo.setCustomer_name(customer_name);;
		new_custInfo.setCert_type(cert_type);
		new_custInfo.setCert_num(cert_num);
		new_custInfo.setCert_addr(cert_addr);
		new_custInfo.setCert_nation(cert_nation);
		new_custInfo.setCert_issuedat(cert_issuedat);
		new_custInfo.setCert_expire_date(cert_expire_date);
		new_custInfo.setCert_effected_date(cert_effected_date);
		new_custInfo.setContact_name(contact_name);
		new_custInfo.setContact_phone(contact_phone);
		new_custInfo.setCustomer_adder(customer_adder);
		new_custInfo.setCert_verified(cert_verified);
		new_custInfo.setCert_sex(cert_sex);
		new_custInfo.setCert_birthday(cert_birthday);
		new_custInfo.setReq_swift_num(req_swift_num);
		new_custInfo.setCert_num2(cert_num2);
		custInfo = new_custInfo;
		return custInfo;
	} 

	public void setCustInfo(CustInfoVO custInfo) {
		this.custInfo = custInfo;
	}
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.broadband.groupOrderSubReq";
	}
	
    public String getFund_source() {//之前开发遗漏字段，本次需要加上  避免以往业务加判断
        String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);
        String catId = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, SpecConsts.CAT_ID);
        if("1".equals(getIs_pay()) && "10093".equals(order_from) && StringUtils.equals(catId, EcsOrderConsts.GOODS_CAT_ID_WUXIANKD)){
           fund_source = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderPayBusiRequests().get(0).getPay_method();
        }
        return fund_source;
    }

    public void setFund_source(String fund_source) {
        this.fund_source = fund_source;
    }
}
