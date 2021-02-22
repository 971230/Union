package com.ztesoft.net.server;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.drools.core.util.StringUtils;

import params.req.HSMallOrderStandardReq;
import params.resp.HSMallOrderStandardResp;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.iservice.IOrderstdService;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;
import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.resp.GoodsGetResp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ztesoft.common.util.MD5Util;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.model.OuterItem;
import com.ztesoft.net.outter.inf.iservice.IOutterECSTmplManager;
import com.ztesoft.net.outter.inf.model.LocalOrderAttr;
import com.ztesoft.net.outter.inf.model.OuterError;
import com.ztesoft.net.outter.inf.service.SDBUtils;
import com.ztesoft.net.server.jsonserver.hspojo.HSMallB2BHeadItems;
import com.ztesoft.net.server.jsonserver.hspojo.HSMallB2BHeader;
import com.ztesoft.net.server.jsonserver.hspojo.HSMallOrderReq;
import com.ztesoft.net.server.jsonserver.hspojo.HSMallOrderReqItems;
import com.ztesoft.net.server.jsonserver.hspojo.HSMallOrderReqItemsDetail;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallUtils;
import com.ztesoft.net.service.IOrderServiceLocal;
import com.ztesoft.orderstd.service.IOrderStandingManager;

import consts.ConstsCore;

public class HSMallOrderUtil {

	private static IOrderServiceLocal localGoodServices;
	private static Logger logger = Logger.getLogger(HSMallOrderUtil.class);
	private static IOutterECSTmplManager outterECSTmplManager;
	private static SDBUtils dbUtils = null;
	private static IOrderstdService orderstdService;
	private static IOrderStandingManager orderStandingManager;
	
	public static final String HS_SOURCE_FROM_B2C = "10061";	//B2C订单
	public static final String HS_SOURCE_FROM_B2B = "10062";	//B2B订单
	public static final String HS_PLAT_TYPE = "10061";	//B2B订单
	public static final String OTHER_PROVINCE_CITY_CODE = "449999";	//非广东省地市
	public static final String HS_VIRTUAL_CHANNEL = "HSVirtualComp";	//华盛虚拟渠道编码
	public static final String HS_VIRTUAL_CHANNEL_MARK = "15";	//渠道标记
	/** * 订单重复性校验NAMESPACE*/
	public static final int ORDER_DUPLICATE_CHECK_NAMESPACE = 5000;
	private static IDaoSupport baseDaoSupport ;
	
	public static void initBean(){
		localGoodServices = SpringContextHolder.getBean("orderStdServiceLocal");
		outterECSTmplManager = SpringContextHolder.getBean("outterStdTmplManager");
		orderstdService = SpringContextHolder.getBean("orderstdService");
		dbUtils = SpringContextHolder.getBean("sdbUtils");
		orderStandingManager = SpringContextHolder.getBean("orderStandingManager");
	}
	
	public static HSMallOrderStandardResp  orderStandardValidate(HSMallOrderStandardReq req) throws Exception{
		initBean();
		HSMallOrderStandardResp resp = new HSMallOrderStandardResp();
		Map extMap = new HashMap<String, String>();
        List<Outer> out_order_List = new ArrayList<Outer>();
        Outer out_order = new Outer();
        String req_content = getReqContent(req.getBase_co_id(), req.isIs_exception());
        if(StringUtil.isEmpty(req_content)){
        	resp.setError_msg("未获取到请求报文");
        	resp.setError_code(ConstsCore.ERROR_FAIL);
        	return resp;
        }
        String hsOrderType = MallUtils.searchValue(req_content, "HSOrderType");
        if(StringUtil.equals(hsOrderType, HS_SOURCE_FROM_B2C)){
        	//B2C订单标准化
    		//json转化为对象
    		HSMallOrderReq mallOrder = JSON.parseObject(req_content,HSMallOrderReq.class);
            mallOrder.setSource_from(HS_SOURCE_FROM_B2C);
        	// 检查必填项
    	    String checkResult = checkMallOrder(mallOrder);
    		if(!StringUtil.isEmpty(checkResult)){
    			//报文校验失败时删除缓存数据
			    removeCacheOrder(mallOrder.getVBELN(), "1", mallOrder.getSource_from());
    			resp.setError_msg(checkResult);
    			resp.setError_code(ConstsCore.ERROR_FAIL);
    		    return resp;
    		}
    		//初始化out_order_List
    		String cancelMsg = setOrderValue(out_order,mallOrder,extMap);;
    		if(!StringUtil.isEmpty(cancelMsg)){
    			resp.setError_msg(cancelMsg);
    			resp.setError_code(ConstsCore.ERROR_FAIL);
    			return resp;
    		}
        }else if(StringUtil.equals(hsOrderType, HS_SOURCE_FROM_B2B)){
        	//B2B订单标准化
    		//json转化为对象
    		HSMallB2BHeader mallOrder = JSON.parseObject(req_content,HSMallB2BHeader.class);
    		mallOrder.setSource_from(HS_SOURCE_FROM_B2B);
        	// 检查必填项
    	    String checkResult = checkMallOrderB2B(mallOrder);
    		if(!StringUtil.isEmpty(checkResult)){
    			//报文校验失败时删除缓存数据
			    removeCacheOrder(mallOrder.getEBELN(), "1", mallOrder.getSource_from());
    			resp.setError_msg(checkResult);
    			resp.setError_code(ConstsCore.ERROR_FAIL);
    		    return resp;
    		}
    		//初始化out_order_List
    		String cancelMsg = setOrderValueB2B(out_order,mallOrder,extMap);
    		if(!StringUtil.isEmpty(cancelMsg)){
    			resp.setError_msg(cancelMsg);
    			resp.setError_code(ConstsCore.ERROR_FAIL);
    			return resp;
    		}
        }
	    out_order_List.add(out_order);
	    resp.setOut_order_List(out_order_List);
	    resp.setError_code(ConstsCore.ERROR_SUCC);
	    return resp;
	}
	
	//获取归集系统请求报文
	public static  String getReqContent(String co_id,boolean is_exception){
		String content = "";
		OrderQueueBusiRequest orderQueue = new OrderQueueBusiRequest();
		if(is_exception){
			orderQueue = CommonDataFactory.getInstance().getOrderQueueFor(null, co_id);
		}else{
			orderQueue = CommonDataFactory.getInstance().getOrderQueue(null, co_id);
		}
		if(orderQueue!=null){
			content = orderQueue.getContents();
		}
		return content;
	}
	
	/**
	 * 把json参数值存入outer对象中
	 * @param outer
	 * @param mallOrder
	 */
	private static String setOrderValue(Outer outer , HSMallOrderReq mallOrder , Map extMap){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		序列号  serial_no
		outer.setPayplatformorderid(MallUtils.getSeqNo());
//		时间  time
		outer.setGet_time(dateFormat.format(new Date()));
//		发起方系统标识  source_system
		outer.setOrder_channel(HS_SOURCE_FROM_B2C);
//		订单编号  order_id
		outer.setOut_tid(mallOrder.getVBELN());
//		订单类型  order_type
		outer.setType("1");
//		订单状态  status
		outer.setStatus(MallUtils.getValues(mallOrder.getStatus()));
//		订单来源系统  source_from_system
		outer.setPlat_type(HS_PLAT_TYPE);
//		订单来源  source_from
		outer.setOrder_from(HS_SOURCE_FROM_B2C);
//		下单时间  create_time
		outer.setTid_time(dateFormat.format(new Date()));
//		支付类型  pay_type
		outer.setPaytype(MallUtils.getValues("HDFK"));
		
//		支付状态
		outer.setPay_status("0");
//		支付方式  payment_type
		outer.setPay_method("XJZF");
//		买家名称  name
		outer.setBuyer_name(MallUtils.getValues(mallOrder.getCompanyName()));
//		买家昵称  uname
		outer.setBuyer_id(MallUtils.getValues(mallOrder.getCompanyName()));
//		外部平台状态  platform_status
		outer.setPlatform_status(MallUtils.getValues(mallOrder.getStatus()));
//		订单总价  order_amount
		outer.setOrder_totalfee(MallUtils.getValues("0"));
//		优惠金额  order_disacount
		outer.setDiscountValue(MallUtils.getValues("0"));
//		实收金额  pay_money
		outer.setOrder_realfee(MallUtils.getValues("0"));
//		应收运费  shipping_amount
		outer.setPost_fee(MallUtils.getValues("0"));
//		配送方式  shipping_type
		outer.setSending_type("KD");
//		收货人姓名  ship_name
		outer.setReceiver_name(MallUtils.getValues(mallOrder.getConsignee()));
//		地址地市  ship_city
		outer.setCity_code(MallUtils.getValues(mallOrder.getCityCode()));
//		地址区县  ship_country
		outer.setArea_code(MallUtils.getValues(mallOrder.getCountyCode()));
		outer.setDistrict(mallOrder.getCounty());
		outer.setCity(mallOrder.getCity());  //收件人地市中文
		outer.setProvinc_code(mallOrder.getProvinceCode());  //收件人省份编码
		outer.setProvince(mallOrder.getProvince());  //收件人省份中文
//		订单归属省份编码  order_provinc_code
		outer.setOrder_provinc_code(MallUtils.getValues(mallOrder.getProvinceCode()));
//		订单归属地市编码  order_city_code
		if(mallOrder.getProvince().contains("广东省")){
			outer.setOrder_city_code(MallUtils.getValues(mallOrder.getCityCode()));
		}else{
			outer.setOrder_city_code(OTHER_PROVINCE_CITY_CODE);
		}
//		地址  ship_addr
		outer.setAddress(MallUtils.getValues(mallOrder.getShipToAddress()));
//		固定电话  ship_tel
		outer.setPhone(MallUtils.getValues(mallOrder.getConsigneePhone()));
//		手机号码  ship_phone
		outer.setReceiver_mobile(MallUtils.getValues(mallOrder.getTELPhone()));
//		买家留言  buyer_message
		outer.setBuyer_message(MallUtils.getValues(mallOrder.getRemark()));
		//保存物流单号
		outer.setReserve1(MallUtils.getValues(mallOrder.getTraficnum()));
		
		outer.setReserve3(HS_VIRTUAL_CHANNEL);
//		渠道类型  channel_mark  Reserve2
		outer.setReserve2(HS_VIRTUAL_CHANNEL_MARK);
		
		
//		库中无对应字段存储的数据
//		接收方系统标识  receive_system  Reserve0
		outer.setReserve0("10011");		
		extMap.put("logi_no", MallUtils.getValues(mallOrder.getTraficnum()));//物流单号
		extMap.put("need_receipt", EcsOrderConsts.NO_DEFAULT_VALUE);//不需要回单
		//配送时间  shipping_time
		extMap.put("shipping_time", "NOLIMIT");
		outer.setExtMap(extMap);
		
//		order_list参数值
		return setOrderListValue(outer , mallOrder , extMap);
		
	}
	
	/**
	 * 把order_list参数值set到OuterItem中
	 * @param outer
	 * @param mallOrder
	 */
	private static String setOrderListValue(Outer outer , HSMallOrderReq mallOrder , Map extMap){
		List<HSMallOrderReqItems> items = mallOrder.getItems();
		List<OuterItem> itemList  = new ArrayList<OuterItem>();
		
		//存放产品参数
		Map<String, String> param = new HashMap<String, String>();
		
		int nums = 0;
		JSONArray hsArray = new JSONArray();
		for (HSMallOrderReqItemsDetail it : items.get(0).getItem()) {
			OuterItem item = new OuterItem();
			
			String sn = it.getMATNR().substring(8);
			JSONObject hsObj = new JSONObject();

			Double num = Double.parseDouble(it.getMENGE());
			it.setMENGE(num.intValue() + "");
			//华盛专用表数据
			hsObj.put("VBELN", mallOrder.getVBELN());	//出库单号
			hsObj.put("MJAHR", mallOrder.getMJAHR());	//年度
			hsObj.put("WERKS", mallOrder.getWERKS());	//地点代码
			hsObj.put("COMPANY_ID", mallOrder.getCompanyId());	//客户编码
			hsObj.put("COMPANY_NAME", mallOrder.getCompanyName());	//客户名称
			hsObj.put("DISVENDOR_CODE", mallOrder.getDisvendorCode());	//分销编码
			hsObj.put("SF_DESTCODE", mallOrder.getAreaCode());	//目的地代码
			hsObj.put("SF_ORIGINCODE", "020");	//原寄地代码，在华盛传过来之前写死020
			hsObj.put("ITEM", it.getITEM());	//出库单行项目
			hsObj.put("LGORT", it.getLGORT());	//库位
			hsObj.put("MATNR", it.getMATNR());	//商品编码
			hsObj.put("SOBKZ", it.getSOBKZ());	//经销/代销
			hsObj.put("MENGE", it.getMENGE());	//数量
			hsObj.put("MEINS", it.getMEINS());	//单位
			hsArray.add(hsObj);
			
			GoodsGetReq req = new GoodsGetReq();
			GoodsGetResp resp = null;
			try {
				//根据物料号匹配商品
				req.setMatnr(it.getMATNR());
				resp = localGoodServices.getHSGoodsByMatnr(req);
			} catch (Exception e) {
				logger.info(e.getMessage(), e);
				OuterError ng = new OuterError(null, sn, sn, null,mallOrder.getSource_from(), mallOrder.getVBELN(), "sysdate","goodserror");//noparam  nogoods
				ng.setDeal_flag("-1");
				outterECSTmplManager.insertOuterError(ng);
				logger.info("订单：" + mallOrder.getVBELN() + "获取商品相关参数失败======================");
				continue;
			}
			
			if ("0".equals(resp.getError_code())) {
				param = resp.getData();
				//本地表对象,存放货品参数
				LocalOrderAttr orderAttr = new LocalOrderAttr();
				//商品价格   offer_price
				item.setSell_price(Double.parseDouble("0"));
				//优惠价格（厘）  offer_disacount_price
				//实收价格（厘）  offer_coupon_price
				item.setPro_origfee(Double.parseDouble("0"));
				//商品数量  prod_offer_num
				item.setPro_num(Integer.parseInt(it.getMENGE()));
				//客户名称  cust_name
				item.setPhone_owner_name(mallOrder.getCompanyName());
				//机型编码  resourcestypeid
				item.setModel_code(sn);
				//减免预存标记
				item.setReliefpres_flag("NO");				
				//设置保存货品参数
				outer.setLocalObject(orderAttr);

				//产品参数
				setPackageGoodsParam(item ,param);
				JSONArray array = new JSONArray();
				extMap.put("is_change", "-1");
				//package_sale  套包销售标记
				extMap.put("package_sale", "-1");
				//choose_active  可选活动
				extMap.put("choose_active", "-1");				
				
				if(nums == 0){
					itemList.add(item);
					nums ++;
				}
			}else {
				OuterError ng = new OuterError(null, sn, sn, null, mallOrder.getSource_from(), mallOrder.getVBELN(), "sysdate","goodserror");//noparam  nogoods
				ng.setDeal_flag("-1");
				outterECSTmplManager.insertOuterError(ng);
				logger.info("订单：" + mallOrder.getVBELN() + "电商没有配置商品=====");
				return "电商没有配置商品";
			}
		}
		
		extMap.put("hsOrderInfo", hsArray.toString());		
		//物流公司编码  shipping_company  华盛B2C订单下单后，默认物流公司（顺丰华盛）
		extMap.put("shipping_company", "SF-HS");
		outer.setItemList(itemList);
		return "";
	}
	
	/**
	 * 把产品参数添加在OuterItem对象中
	 * @param item
	 * @param param
	 */
	private static void setPackageGoodsParam(OuterItem item , Map<String, String> param){
		
		//活动预存款 单位元
		if (MallUtils.isEmpty(param.get("deposit_fee"))) {
			item.setPhone_deposit("0");
		} else {
			item.setPhone_deposit(param.get("deposit_fee"));
		}
		//产品ID
		item.setProduct_id(param.get("product_id"));
		item.setGoods_id(param.get("goods_id"));
		
		//合约期限 12月、18月、24月、36月、48月
		if (MallUtils.isEmpty(param.get("package_limit"))) {
			item.setAct_protper("0");
		} else {
			item.setAct_protper(param.get("package_limit"));
		}
		
		//字典：5购机送费、4存费送机、3存费送费 --合约类型
		if (MallUtils.isNotEmpty(param.get("ative_type"))) {
			item.setAtive_type(param.get("ative_type"));
		}
		
		//品牌
		if (MallUtils.isNotEmpty(param.get("brand_name"))) {
			item.setBrand_name(param.get("brand_name"));
		}
		
		//品牌编码
		if (MallUtils.isNotEmpty(param.get("brand_number"))) {
			item.setBrand_number(param.get("brand_number"));
		}
		//颜色编码
		if (MallUtils.isNotEmpty(param.get("color_code"))) {
			item.setColor_code(param.get("color_code"));
		}
		if (MallUtils.isNotEmpty(param.get("color_name"))) {
			item.setColor_name(param.get("color_name"));
		}
		//是否iphone套餐 字典：0否，1是
		if (MallUtils.isNotEmpty(param.get("is_iphone_plan"))) {
			item.setIs_iphone_plan(param.get("is_iphone_plan"));
		}
		//是否靓号 字典：0否，1是
		if (MallUtils.isNotEmpty(param.get("is_liang"))) {
			item.setIs_liang(param.get("is_liang"));
		}
		//是否情侣号码 字典：0否，1是 --默认0
		if (MallUtils.isNotEmpty(param.get("is_loves_phone"))) {
			item.setIs_loves_phone(param.get("is_loves_phone"));
		}
		//是否库存机 字典：0否，1是 --终端属性
		if (MallUtils.isNotEmpty(param.get("is_stock_phone"))) {
			item.setIs_stock_phone(param.get("is_stock_phone"));
		}
		//是否赠品 字典：0否，1是
		if (MallUtils.isNotEmpty(param.get("isgifts"))) {
			item.setIsgifts(param.get("isgifts"));
		}
		//机型编码
		if (MallUtils.isNotEmpty(param.get("model_code"))) {
			item.setModel_code(param.get("model_code"));
		}
		//机型名称
		if (MallUtils.isNotEmpty(param.get("model_name"))) {
			item.setModel_name(param.get("model_name"));
		}
		//BSS套餐编码 BSS套餐编码
		if (MallUtils.isNotEmpty(param.get("out_plan_id_bss"))) {
			item.setOut_plan_id_bss(param.get("out_plan_id_bss"));
		}
		//总部套餐编码 总部套餐编码
		if (MallUtils.isNotEmpty(param.get("out_plan_id_ess"))) {
			item.setOut_plan_id_ess(param.get("out_plan_id_ess"));
		}
		//套餐名称
		if (MallUtils.isNotEmpty(param.get("goods_name"))) {
			item.setPlan_title(param.get("goods_name"));
			item.setPro_name(param.get("goods_name"));
		}
		//产品类型
		if (MallUtils.isNotEmpty(param.get("product_type"))) {
			item.setPro_type(param.get("product_type"));
		}
		//产品网别
		if (MallUtils.isNotEmpty(param.get("product_net"))) {
			item.setProduct_net(param.get("product_net"));
		}
		//型号名称
		if (MallUtils.isNotEmpty(param.get("specification_name"))) {
			item.setSpecificatio_nname(param.get("specification_name"));
		}
		//型号编码
		if (MallUtils.isNotEmpty(param.get("specification_code"))) {
			item.setSpecification_code(param.get("specification_code"));
		}
		//商品大类
		if (MallUtils.isNotEmpty(param.get("type_id"))) {
			item.setType_id(param.get("type_id"));
		}
		
	}
	
	/**
	 * 商城json参数中必填项校验
	 * @param mallOrder
	 * @return
	 */
	private static String checkMallOrder(HSMallOrderReq mallOrder) throws Exception{
		StringBuffer stringBuffer = new StringBuffer();		
		//出库单号
		if (MallUtils.isEmpty(mallOrder.getVBELN())) {
			stringBuffer.append("缺少必填字段:VBELN.");
		}
		//B2C订单编号
		if(MallUtils.isEmpty(mallOrder.getVBESO())){
			stringBuffer.append("缺少必填字段:VBESO.");
		}
		//年度
		if (MallUtils.isEmpty(mallOrder.getMJAHR())) {
			stringBuffer.append("缺少必填字段:MJAHR.");
		}
		//地点代码
		if (MallUtils.isEmpty(mallOrder.getWERKS())) {
			stringBuffer.append("缺少必填字段:WERKS.");
		}
		//客户编码
		if (MallUtils.isEmpty(mallOrder.getCompanyId())) {
			stringBuffer.append("缺少必填字段:companyId.");
		}
		//客户名称
		if (MallUtils.isEmpty(mallOrder.getCompanyName())) {
			stringBuffer.append("缺少必填字段:companyName.");
		}
		//物流单号
		if (MallUtils.isEmpty(mallOrder.getTraficnum())) {
			stringBuffer.append("缺少必填字段:Traficnum.");
		}
		//目的地代码
		if (MallUtils.isEmpty(mallOrder.getAreaCode())) {
			stringBuffer.append("缺少必填字段:AreaCode.");
		}
		//收货省分
		if (MallUtils.isEmpty(mallOrder.getProvince())) {
			stringBuffer.append("缺少必填字段:province.");
		}else{
			String provinceCode = queryProvince(mallOrder.getProvince());
			if("error".equals(provinceCode)){
				stringBuffer.append("province值["+mallOrder.getProvince()+"]在订单系统未配置映射.");
			}else{
				mallOrder.setProvinceCode(provinceCode);
			}
		}
		//收货地市
		if (MallUtils.isEmpty(mallOrder.getCity())) {
			stringBuffer.append("缺少必填字段:city.");
		}else{
			String cityCode = queryCity(mallOrder.getCity(), mallOrder.getProvinceCode());
			if("error".equals(cityCode)){
				stringBuffer.append("city值["+mallOrder.getCity()+"]在订单系统未配置.");
			}else{
				mallOrder.setCityCode(cityCode);
			}
		}
		//区县
		if(MallUtils.isNotEmpty(mallOrder.getCounty())){
			String countyCode = queryCounty(mallOrder.getCounty(), mallOrder.getCityCode());
			if("error".equals(countyCode)){
				stringBuffer.append("county值["+mallOrder.getCounty()+"]在订单系统未配置.");
			}else{
				mallOrder.setCountyCode(countyCode);
			}
		}
		//收货人姓名
		if (MallUtils.isEmpty(mallOrder.getConsignee())) {
			stringBuffer.append("缺少必填字段:consignee.");
		}
		//收货人电话
		if (MallUtils.isEmpty(mallOrder.getConsigneePhone())) {
			stringBuffer.append("缺少必填字段:consigneePhone.");	
		}
		//收货人手机
		if(MallUtils.isEmpty(mallOrder.getTELPhone())){
			stringBuffer.append("缺少必填字段:TELPhone.");	
		}
		//收货地址
		if(MallUtils.isEmpty(mallOrder.getShipToAddress())){
			stringBuffer.append("缺少必填字段:shipToAddress.");	
		}
		//出库指令传输行项目
		List<HSMallOrderReqItems> itemsList = mallOrder.getItems();
		if(null == itemsList || itemsList.size() == 0){
			stringBuffer.append("缺少必填字段:ITEMS.");
		}else{
			for (HSMallOrderReqItemsDetail item : itemsList.get(0).getItem()) {
				//检查order_list列表中必填项
				stringBuffer.append(checkOrderList(mallOrder,item));
			}
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 检查ITEMS列表中必填项
	 * @return
	 */
	private static String checkOrderList(HSMallOrderReq mallOrder ,HSMallOrderReqItemsDetail item){
		StringBuffer strBuf = new StringBuffer();
		//出库单行项目
		if(MallUtils.isEmpty(item.getITEM())){
			strBuf.append("缺少必填字段:ITEM.");	
		}
		//库位
		if(MallUtils.isEmpty(item.getLGORT())){
			strBuf.append("缺少必填字段:LGORT.");	
		}
		//商品编码
		if(MallUtils.isEmpty(item.getMATNR())){
			strBuf.append("缺少必填字段:MATNR.");	
		}
		//数量
		if(MallUtils.isEmpty(item.getMENGE())){
			strBuf.append("缺少必填字段:MENGE.");	
		}
		//单位
		if(MallUtils.isEmpty(item.getMEINS())){
			strBuf.append("缺少必填字段:MEINS.");	
		}
		return strBuf.toString();
	}
	
	/**
	 * 获取省份编码
	 * @param province_name
	 * @return
	 */
	private static String queryProvince(String provinceName){
		String provinceCode = "";
		String sql = "select c.field_value from es_mall_config c where c.field_name = 'hs_province' "
				+ " and c.field_desc = '"+provinceName+"' and rownum < 2";
		provinceCode = dbUtils.querySqlResult(sql);
		if(StringUtils.isEmpty(provinceCode)){
			return "error";
		}
		return provinceCode;
	}
	
	/**
	 * 根据地市名、省份编码获取地市编码
	 * @param cityName
	 * @param provinceCode
	 * @return
	 */
	private static String queryCity(String cityName , String provinceCode){
		String cityCode = "";
		String sql = "select c.field_value from es_mall_config c where "
				+ "c.field_name = 'hs_city' and c.field_desc = '"+cityName
				+"' and c.field_content_desc = '"+provinceCode+"' and rownum < 2";
		cityCode = dbUtils.querySqlResult(sql);
		if(StringUtils.isEmpty(cityCode)){
			return "error";
		}
		return cityCode;
	}
	
	/**
	 * 根据区县名、地市编码获取区县编码
	 * @param countyName
	 * @param cityCode
	 * @return
	 */
	private static String queryCounty(String countyName , String cityCode){
		String countyCode = "";
		String sql = "select c.field_value from es_mall_config c where "
				+ "c.field_name = 'hs_county' and c.field_desc = '"+countyName
				+"' and c.field_content_desc = '"+cityCode+"' and rownum < 2";
		countyCode = dbUtils.querySqlResult(sql);
		if(StringUtils.isEmpty(countyCode)){
			return "error";
		}
		return countyCode;
	}
	
	/**
	 * 商城json参数中必填项校验
	 * @param mallOrder
	 * @return
	 */
	private static String checkMallOrderB2B(HSMallB2BHeader mallOrder) throws Exception{
		StringBuffer stringBuffer = new StringBuffer();		
		//订单编号
		if (MallUtils.isEmpty(mallOrder.getEBELN())) {
			stringBuffer.append("缺少必填字段:EBELN.");
		}
		//地点
		if(MallUtils.isEmpty(mallOrder.getWERKS())){
			stringBuffer.append("缺少必填字段:WERKS.");
		}
		//供应商编码
		if (MallUtils.isEmpty(mallOrder.getLIFNR())) {
			stringBuffer.append("缺少必填字段:LIFNR.");
		}
		//名称
		if (MallUtils.isEmpty(mallOrder.getNAME1())) {
			stringBuffer.append("缺少必填字段:NAME1.");
		}
		//电话1
		if (MallUtils.isEmpty(mallOrder.getTELF1())) {
			stringBuffer.append("缺少必填字段:TELF1.");
		}
		//收货省分
		if (MallUtils.isEmpty(mallOrder.getBezei())) {
			stringBuffer.append("缺少必填字段:bezei.");
		}else{
			String provinceCode = queryProvince(mallOrder.getBezei());
			if("error".equals(provinceCode)){
				stringBuffer.append("bezei值["+mallOrder.getBezei()+"]在订单系统未配置.");
			}else{
				mallOrder.setProvinceCode(provinceCode);
			}
		}
		//收货地市
		if (MallUtils.isEmpty(mallOrder.getORT01())) {
			stringBuffer.append("缺少必填字段:ORT01.");
		}else{
			String cityCode = queryCity(mallOrder.getORT01(), mallOrder.getProvinceCode());
			if("error".equals(cityCode)){
				stringBuffer.append("city值["+mallOrder.getORT01()+"]在订单系统未配置.");
			}else{
				mallOrder.setCityCode(cityCode);
			}
		}
		//区县
		if(MallUtils.isNotEmpty(mallOrder.getORT02())){
			String countyCode = queryCounty(mallOrder.getORT02(), mallOrder.getCityCode());
			if("error".equals(countyCode)){
				stringBuffer.append("ORT02值["+mallOrder.getORT02()+"]在订单系统未配置.");
			}else{
				mallOrder.setCountyCode(countyCode);
			}
		}
		//收货地址
		if (MallUtils.isEmpty(mallOrder.getSTRAS())) {
			stringBuffer.append("缺少必填字段:STRAS.");
		}
		//收货联系人
		if (MallUtils.isEmpty(mallOrder.getZRECEIVER1())) {
			stringBuffer.append("缺少必填字段:ZRECEIVER1.");
		}
		//仓储商接口类别
		if (MallUtils.isEmpty(mallOrder.getZJKLB())) {
			stringBuffer.append("缺少必填字段:ZJKLB.");
		}
		
		//出库指令传输行项目
		List<HSMallB2BHeadItems> itemsList = mallOrder.getITEMS();
		if(null == itemsList || itemsList.size() == 0){
			stringBuffer.append("缺少必填字段:ITEMS.");
		}else{
			for(HSMallB2BHeadItems item : itemsList){
				stringBuffer.append(checkOrderListB2B(mallOrder,item));
			}
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 检查ITEMS列表中必填项
	 * @return
	 */
	private static String checkOrderListB2B(HSMallB2BHeader mallOrder ,HSMallB2BHeadItems item){
		StringBuffer strBuf = new StringBuffer();
		//出库单行项目
		//行项目
		if(MallUtils.isEmpty(item.getEBELP())){
			strBuf.append("缺少必填字段:EBELP.");	
		}
		//库位编码
		if(MallUtils.isEmpty(item.getLGORT())){
			strBuf.append("缺少必填字段:LGORT.");	
		}
		//商品编码
		if(MallUtils.isEmpty(item.getMATNR())){
			strBuf.append("缺少必填字段:MATNR.");	
		}
		//描述
		if(MallUtils.isEmpty(item.getMAKTX())){
			strBuf.append("缺少必填字段:MAKTX.");	
		}
		//数量
		if(MallUtils.isEmpty(item.getMENGE())){
			strBuf.append("缺少必填字段:MENGE.");	
		}
		return strBuf.toString();
	}
	
	/**
	 * 把json参数值存入outer对象中
	 * @param outer
	 * @param mallOrder
	 */
	private static String setOrderValueB2B(Outer outer , HSMallB2BHeader mallOrder , Map extMap){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		序列号  serial_no
		outer.setPayplatformorderid(MallUtils.getSeqNo());
//		时间  time
		outer.setGet_time(dateFormat.format(new Date()));
//		发起方系统标识  source_system
		outer.setOrder_channel(HS_SOURCE_FROM_B2B);
//		订单编号  order_id
		outer.setOut_tid(mallOrder.getEBELN());
//		订单类型  order_type
		outer.setType("1");
//		订单状态  status
		outer.setStatus("");
//		订单来源系统  source_from_system
		outer.setPlat_type(HS_PLAT_TYPE);
//		订单来源  source_from
		outer.setOrder_from(HS_SOURCE_FROM_B2B);
//		下单时间  create_time
		outer.setTid_time(dateFormat.format(new Date()));
//		支付类型  pay_type
		outer.setPaytype(MallUtils.getValues("HDFK"));
		//渠道标识  channel_id  Reserve3
		outer.setReserve3(HS_VIRTUAL_CHANNEL);
//		渠道类型  channel_mark  Reserve2
		outer.setReserve2(HS_VIRTUAL_CHANNEL_MARK);
		
//		支付状态
		outer.setPay_status("0");
//		支付方式  payment_type
		outer.setPay_method("XJZF");
//		买家名称  name
		outer.setBuyer_name(MallUtils.getValues(mallOrder.getNAME1()));
//		买家昵称  uname
		outer.setBuyer_id(MallUtils.getValues(mallOrder.getNAME1()));
//		外部平台状态  platform_status
		outer.setPlatform_status("");
//		订单总价  order_amount
		outer.setOrder_totalfee(MallUtils.getValues("0"));
//		优惠金额  order_disacount
		outer.setDiscountValue(MallUtils.getValues("0"));
//		实收金额  pay_money
		outer.setOrder_realfee(MallUtils.getValues("0"));
//		应收运费  shipping_amount
		outer.setPost_fee(MallUtils.getValues("0"));
//		配送方式  shipping_type
		outer.setSending_type("KD");
//		收货人姓名  ship_name
		outer.setReceiver_name(MallUtils.getValues(mallOrder.getZRECEIVER1()));
//		地址地市  ship_city
		outer.setCity_code(MallUtils.getValues(mallOrder.getCityCode()));
//		地址区县  ship_country
		outer.setArea_code(MallUtils.getValues(mallOrder.getCountyCode()));
		outer.setDistrict(mallOrder.getORT02());
		outer.setCity(mallOrder.getORT01());  //收件人地市中文
		outer.setProvinc_code(mallOrder.getProvinceCode());  //收件人省份编码
		outer.setProvince(mallOrder.getBezei());  //收件人省份中文
//		订单归属省份编码  order_provinc_code
		outer.setOrder_provinc_code(MallUtils.getValues(mallOrder.getProvinceCode()));
//		订单归属地市编码  order_city_code
		if(mallOrder.getBezei().contains("广东省")){
			outer.setOrder_city_code(MallUtils.getValues(mallOrder.getCityCode()));
		}else{
			outer.setOrder_city_code(OTHER_PROVINCE_CITY_CODE);	
		}
//		地址  ship_addr
		outer.setAddress(MallUtils.getValues(mallOrder.getSTRAS()));
//		固定电话  ship_tel
		outer.setPhone(MallUtils.getValues(mallOrder.getTELF2()));
//		手机号码  ship_phone
		outer.setReceiver_mobile(MallUtils.getValues(mallOrder.getTELF1()));
//		买家留言  buyer_message
		outer.setBuyer_message("");
		
		
//		库中无对应字段存储的数据
//		接收方系统标识  receive_system  Reserve0
		outer.setReserve0("10011");		
		//配送时间  shipping_time
		extMap.put("shipping_time", "NOLIMIT");
		outer.setExtMap(extMap);
		
//		order_list参数值
		return setOrderListValueB2B(outer , mallOrder , extMap);
		
	}
	
	/**
	 * 把order_list参数值set到OuterItem中
	 * @param outer
	 * @param mallOrder
	 */
	private static String setOrderListValueB2B(Outer outer , HSMallB2BHeader mallOrder , Map extMap){
		List<HSMallB2BHeadItems> items = mallOrder.getITEMS();
		List<OuterItem> itemList  = new ArrayList<OuterItem>();
		
		//存放产品参数
		Map<String, String> param = new HashMap<String, String>();
		
		int nums = 0;	//传给标准化核心逻辑中只取第一个Items
		JSONArray hsArray = new JSONArray();
		for (HSMallB2BHeadItems it : items) {
			OuterItem item = new OuterItem();
			
			String sn = it.getMATNR().substring(8);
			JSONObject hsObj = new JSONObject();

			Double num = Double.parseDouble(it.getMENGE());
			it.setMENGE(num.intValue() + "");
			//华盛专用表数据
			hsObj.put("VBELN", mallOrder.getEBELN());	//出库单号
			hsObj.put("MJAHR", "");	//年度
			hsObj.put("WERKS", mallOrder.getWERKS());	//地点代码
			hsObj.put("COMPANY_ID", mallOrder.getLIFNR());	//客户编码
			hsObj.put("COMPANY_NAME", mallOrder.getNAME1());	//客户名称
			hsObj.put("DISVENDOR_CODE", "");	//分销编码
			hsObj.put("SF_DESTCODE", mallOrder.getORT01());	//目的地代码
			hsObj.put("ITEM", it.getEBELP());	//出库单行项目
			hsObj.put("LGORT", it.getLGORT());	//库位
			hsObj.put("MATNR", it.getMATNR());	//商品编码
			hsObj.put("SOBKZ", it.getSOBKZ());	//经销/代销
			hsObj.put("MENGE", it.getMENGE());	//数量
			hsObj.put("MEINS", it.getMEINS());	//单位
			hsObj.put("EBELP", it.getEBELP());	//采购凭证的项目编号
			hsObj.put("ZJKLB", mallOrder.getZJKLB());	//仓储商接口类别
			hsObj.put("LIFNR", mallOrder.getLIFNR());	//供应商编码
			hsArray.add(hsObj);
			
			GoodsGetReq req = new GoodsGetReq();
			GoodsGetResp resp = null;
			try {
				//根据物料号匹配商品
				req.setMatnr(it.getMATNR());
				resp = localGoodServices.getHSGoodsByMatnr(req);
			} catch (Exception e) {
				logger.info(e.getMessage(), e);
				OuterError ng = new OuterError(null, sn, sn, null,mallOrder.getSource_from(), mallOrder.getEBELN(), "sysdate","goodserror");//noparam  nogoods
				ng.setDeal_flag("-1");
				outterECSTmplManager.insertOuterError(ng);
				logger.info("订单：" + mallOrder.getEBELN() + "获取商品相关参数失败======================");
				continue;
			}
			
			if ("0".equals(resp.getError_code())) {
				param = resp.getData();
				//本地表对象,存放货品参数
				LocalOrderAttr orderAttr = new LocalOrderAttr();
				//商品价格   offer_price
				item.setSell_price(Double.parseDouble("0"));
				//优惠价格（厘）  offer_disacount_price
				//实收价格（厘）  offer_coupon_price
				item.setPro_origfee(Double.parseDouble("0"));
				//商品数量  prod_offer_num
				item.setPro_num(Integer.parseInt(it.getMENGE()));
				//客户名称  cust_name
				item.setPhone_owner_name(mallOrder.getZRECEIVER1());
				//机型编码  resourcestypeid
				item.setModel_code(sn);
				//减免预存标记
				item.setReliefpres_flag("NO");				
				//设置保存货品参数
				outer.setLocalObject(orderAttr);

				//产品参数
				setPackageGoodsParam(item ,param);
				JSONArray array = new JSONArray();
				extMap.put("is_change", "-1");
				//package_sale  套包销售标记
				extMap.put("package_sale", "-1");
				//choose_active  可选活动
				extMap.put("choose_active", "-1");				
				
				if(nums == 0){
					itemList.add(item);
					nums ++;
				}
			}else {
				OuterError ng = new OuterError(null, sn, sn, null, mallOrder.getSource_from(), mallOrder.getEBELN(), "sysdate","goodserror");//noparam  nogoods
				ng.setDeal_flag("-1");
				outterECSTmplManager.insertOuterError(ng);
				logger.info("订单：" + mallOrder.getEBELN() + "电商没有配置商品=====");
				return "电商没有配置商品";
			}
		}
		
		extMap.put("hsOrderInfo", hsArray.toString());
		outer.setItemList(itemList);
		return "";
	}
	
	/**
	 * 删除缓存中订单数据
	 * @param outId
	 * @param is_remove_outer_data
	 * @param source_from
	 */
	private static void removeCacheOrder(String outTid,String is_remove_outer_data,String source_from){
		//删除缓存
		INetCache cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
		String key = new StringBuffer().append(source_from).append(outTid).toString();
		try {
			logger.info("删除外部订单["+outTid+"]缓存数据");
			key = MD5Util.MD5(key);
			int NAMESPACE = ORDER_DUPLICATE_CHECK_NAMESPACE;
			cache.delete(NAMESPACE, key);
			
			if("1".equals(is_remove_outer_data)){
				String sql = "delete from es_order_outer where source_from ='"+ManagerUtils.getSourceFrom()+"' and  old_sec_order_id = ?";
				IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
				baseDaoSupport.execute(sql, outTid);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		String json = "{\"Items\":[{\"Item\":[{\"MENGE\":\"2.000\",\"ITEM\":\"0001\",\"LGORT\":\"1001\",\"delivDate\":\"20160229150239\",\"MEINS\":\"PCS\",\"MATNR\":\"000000007400209754\"},{\"MENGE\":\"2.000\",\"SOBKZ\":\"K\",\"ITEM\":\"0002\",\"LGORT\":\"1001\",\"delivDate\":\"20160229150239\",\"MEINS\":\"PCS\",\"MATNR\":\"000000007400209754\"}]}],\"TELPhone\":\"13722222222\",\"MJAHR\":\"2016\",\"shipToAddress\":\"前进街道南湖广场气象宿舍乙栋3门107室\",\"remark\":\"1\",\"status\":\"单据状态\",\"consignee\":\"赵东方\",\"VBESO\":\"20150311099916\",\"Invoice\":[{\"InvoiceItem\":[{\"TC_ISB09X\":\"29.06\",\"TC_ISB11W\":\"0.00\",\"TC_ISB09T\":\"200.00\",\"TC_ISA03\":\"20160229\",\"TC_ISA04\":\"301602290004\",\"ZCHLB\":\"X\",\"TC_ISB09\":\"170.94\",\"TC_ISA05\":\"0001000191\",\"TC_ISB08\":\"2.000\",\"BUKRS\":\"X000\",\"TC_ISB04\":\"000000007400200487\",\"TC_ISB05\":\"3G手机华为_3G手机华为_U1270白色\",\"TC_ISB06\":\"3G手机华为_3G手机华为_U1270白\",\"TC_ISB07\":\"PCS\",\"TC_ISB02\":\"301602290004\",\"TC_ISB03\":\"00001\",\"LINE_INDEX\":\"1\",\"TC_ISB11X\":\"0.00\",\"TC_ISA061\":\"17.0000\",\"TC_ISA062\":\"B\",\"TC_ISA053\":\"前进街道南湖广场气象宿舍乙栋3门107室13722222222\",\"TC_ISA051\":\"最爱田老板\",\"TC_ISA10\":\"4900111290;444502527824\",\"ZWLDH\":\"444502527824\",\"WERKS\":\"X0001001\",\"ZJSX1\":\"0000\",\"TC_ISA055\":\"N\",\"ZCHDH\":\"4900111290\",\"ZCKTIME\":\"150244\",\"TC_ISB11\":\"0.00\",\"ZCKDATE\":\"20160229\"}]}],\"companyName\":\"赵东方\",\"WERKS\":\"X000\",\"Traficnum\":\"444502527824\",\"city\":\"长春市\",\"disvendorCode\":\"1\",\"AreaCode\":\"431\",\"county\":\"朝阳区\",\"province\":\"吉林省\",\"companyId\":\"0001000191\",\"createDate\":\"15023900000000\",\"VBELN\":\"4900111290\",\"consigneePhone\":\"13722222222\"}";
		JSONObject jsonObj = JSON.parseObject(json);
		jsonObj.put("orderType", "10061");
		logger.info(jsonObj.toString());
		String orderType = MallUtils.searchValue(jsonObj.toString(), "orderType");
		logger.info("orderTypeorderType" + orderType);
	}

}
