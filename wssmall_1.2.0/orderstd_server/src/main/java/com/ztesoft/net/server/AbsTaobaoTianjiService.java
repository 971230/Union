package com.ztesoft.net.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.drools.core.util.StringUtils;

import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.resp.GoodsGetResp;
import zte.params.region.req.RegionsGetReq;
import zte.params.region.resp.RegionsGetResp;

import com.taobao.api.response.AlibabaTianjiSupplierOrderQueryResponse.DistributionOrderInfo;
import com.taobao.api.response.AlibabaTianjiSupplierOrderQueryResponse.DistributionOrderLogisticsInfo;
import com.taobao.api.response.AlibabaTianjiSupplierOrderQueryResponse.IdCardInfo;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.model.Regions;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.model.OuterItem;
import com.ztesoft.net.outter.inf.model.LocalOrderAttr;
import com.ztesoft.net.outter.inf.model.OuterError;
import com.ztesoft.net.service.impl.OrderStdMangerLocal;

import consts.ConstsCore;

public abstract class AbsTaobaoTianjiService extends AbsTaobao{
	private static Logger logger = Logger.getLogger(AbsTaobaoTianjiService.class);
	public static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public Outer packageOuter(DistributionOrderInfo order,String order_from,String end_time,String outer_package_id,String sku){
		try{
			order_from= "10012";//默认淘宝（天猫）分销
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String province_region = cacheUtil.getConfigInfo("PROVINCE_REGION_CODE");
			Outer o = new Outer();
			Map extMap = new HashMap();  //扩展属性
			o.setExtMap(extMap);
			//买家与卖家留言   留言标题，例如：分销商留言，供应商留言，买家留言
			/*if(p.getOrderMessages()!=null){
				for(OrderMessage m:p.getOrderMessages()){
					if("买家留言".equals(m.getMessageTitle())){
						o.setBuyer_message(m.getMessageContent());
					}else if("分销商留言".equals(m.getMessageTitle())){
						o.setService_remarks(m.getMessageTitle());
					}else if("供应商留言".equals(m.getMessageTitle())){
						o.setService_remarks(m.getMessageTitle());
					}
				}
			}*/
			o.setBuyer_message(order.getBuyerMessage());//买家留言
			if(!StringUtils.isEmpty(order.getRemarks()))//备注
				o.setService_remarks(order.getRemarks());
			
			o.setAlipay_id("");//支付宝交易号。 这里要填支付宝账户， 不知道该数据对不对？？？？？？？？？？？？？？？？？？？？？？？
			//o.setBss_order_type(bss_order_type); //BSS产品类型	--传空
			o.setBuyer_id(order.getBuyerName());//买家昵称
			if(StringUtils.isEmpty(o.getBuyer_id())){
				o.setBuyer_id("无");
				//p.setBuyerNick("无");
			}
			//o.setCert_address(cert_address);//没有
			o.setCert_failure_time("2050-12-31 23:59:59");
					
			o.setPost_fee("0");//采购单邮费。
			o.setAbnormal_status("正常");//订单出现异常问题的时候，给予用户的描述,没有异常的时候，此值为空
			//收货人信息
			DistributionOrderLogisticsInfo logisticInfo = order.getLogisticsInfo();
			if(logisticInfo!=null){
				o.setReceiver_mobile(logisticInfo.getContactPhone());
				o.setReceiver_name(logisticInfo.getConsigneeName());
				o.setPost(""); //收货人的邮编
				o.setPhone(logisticInfo.getContactPhone()); //收货电话 
				o.setBuyer_name(order.getBuyerName());//买家姓名，没有所以填了收货人姓名
				o.setAddress(logisticInfo.getDeliveryAddress());//收货人详细地址
				
				RegionsGetReq regionGet = new RegionsGetReq();
				regionGet.setRegion_cond_type(RegionsGetReq.NAME_COND_TYPE);
				if(!StringUtils.isEmpty(logisticInfo.getArea())){
					o.setDistrict(logisticInfo.getArea());//收货区
					regionGet.setLocal_name(logisticInfo.getArea());
					RegionsGetResp regionResp = getRegion(regionGet);//regionService.getRegion(regionGet);//client.execute(regionGet, RegionsGetResp.class);//regionService.getRegion(regionGet);
					Regions region = regionResp.getRegions();
					if(region!=null){
						o.setArea_code(region.getRegion_id());//收货区编码 是中文的需要转为编码
					}
				}
				regionGet.setRegion_cond_type(RegionsGetReq.NAME_COND_TYPE);
				if(!StringUtils.isEmpty(logisticInfo.getCity())){
					o.setCity(logisticInfo.getCity());//中文的
					regionGet.setLocal_name(logisticInfo.getCity());
					RegionsGetResp regionResp = getRegion(regionGet);//client.execute(regionGet, RegionsGetResp.class);//regionService.getRegion(regionGet);
					Regions region = regionResp.getRegions();
					if(region!=null){
						o.setCity_code(region.getRegion_id());////收货城市编码 是中文的需要转为编码
						//o.setCity_id(region.getRegion_id());
						
						//订单归属地市编码	国际标准，详见省市区位码字典    因为没有所以用了收货人地区ID
						//o.setOrder_city_code(region.getRegion_id()); //收货省编码    收货人的所在省份  
					}
				}
				
				/**
				 *  中文  需要转换
				 */
				regionGet.setRegion_cond_type(RegionsGetReq.NAME_COND_TYPE);
				if(!StringUtils.isEmpty(logisticInfo.getProvince())){
					regionGet.setLocal_name(logisticInfo.getProvince());
					o.setProvince(logisticInfo.getProvince());
					RegionsGetResp regionResp = getRegion(regionGet);//client.execute(regionGet, RegionsGetResp.class);//regionService.getRegion(regionGet);
					Regions region = regionResp.getRegions();
					if(region!=null){
						o.setProvinc_code(region.getRegion_id()); //收货省编码    收货人的所在省份  
					}
				}
				//订单归属地市
				String order_privince_code = null;
				regionGet.setRegion_cond_type(RegionsGetReq.NAME_COND_TYPE);
				if(!StringUtils.isEmpty(order.getCityName())){
					//o.setCity(order.getCityName());//中文的
					regionGet.setLocal_name(order.getCityName()+"市");
					RegionsGetResp regionResp = getRegion(regionGet);//client.execute(regionGet, RegionsGetResp.class);//regionService.getRegion(regionGet);
					Regions region = regionResp.getRegions();
					if(region!=null){
						//订单归属地市编码	国际标准，详见省市区位码字典    因为没有所以用了收货人地区ID
						o.setOrder_city_code(region.getRegion_id());
						//order_privince_code = region.getP_region_id();
					}
				}
				//订单归属省份
				regionGet.setRegion_cond_type(RegionsGetReq.NAME_COND_TYPE);
				if(!StringUtils.isEmpty(order.getProvinceName())){
					regionGet.setLocal_name(order.getProvinceName());;
					RegionsGetResp regionResp = getRegion(regionGet);//client.execute(regionGet, RegionsGetResp.class);//regionService.getRegion(regionGet);
					Regions region = regionResp.getRegions();
					if(region!=null){
						o.setOrder_provinc_code(region.getRegion_id());
					}
				}
			}
			//o.setCollection_free(collection_free)//代收邮费	单位元 --传空
			//o.setCouponbatchid(couponbatchid)//代金券批次号	空
			//o.setCouponname(couponname)//代金券名称	空
			/**
			 * 发货状态。可选值：
				1-未发货，2-已发货
			 */
			if("2".equals(order.getLogisticsStatus())){
				o.setDelivery_status("已发货");
			}else{
				o.setDelivery_status("未发货");
			}
			
			o.setGet_time(end_time);
			//o.setInvoice_print_type(invoice_print_type) //发票打印方式 --如果淘宝没有就传空
			/*if(p.getFeatures()!=null){
				//主订单属性信息，key-value形式： orderNovice ：订单发票抬头； orderNoviceContent ：代表发票明细
				for(Feature f:p.getFeatures()){
					if("orderNovice".equalsIgnoreCase(f.getAttrKey())){
						o.setInvoice_title(f.getAttrValue()); //发票抬头	--如果淘宝没有就传空
					}
				}
			}*/
			o.setIs_adv_sale("0"); //是否预售 先付款后准备货发货、字典：0否，1是--默认否
			//o.setIs_bill(is_bill) //是否开发票 --如果淘宝没有就传空
			o.setMerge_status("合并"); //合并状态	合并状态包括：合并、手拆、自动拆分 --默认为合并，送中文。
			//o.setOne_agents_id(one_agents_id)//一级代理商id	--传空
			/**
			 * 交易内部来源。 WAP(手机);HITAO(嗨淘);TOP(TOP平台);TAOBAO(普通淘宝);JHS(聚划算) 一笔订单可能同时有以上多个标记，则以逗号分隔
			 */
			//t.getTradeFrom();TAOBAO_CHANNEL.get(t.getTradeFrom());
			o.setOrder_channel("10012");//订单渠道包括：电子渠道、集客渠道、营业厅、代理商、CPS、手工下单
			Integer tb_order_price = (int) (Double.parseDouble(order.getLedgerAmount().toString()) / 100);
			o.setOrder_realfee(tb_order_price+"");//订单实收总价 实付金额。 t.getPayment()  商品金额t.getTotalFee()（商品价格乘以数量的总金额）
			o.setOrder_totalfee(tb_order_price+""); //订单总金额
			//o.setOrderacccode(orderacccode) //订单接入编码	--传空
			//o.setOrderacctype(orderacctype) //订单接入类型	--传空
			//o.setOut_tid(String.valueOf(p.getTcOrderId()));//外部平台单号
			o.setOut_tid(order.getTbOrderNo());//外部平台单号
			o.setReserve9(order.getPlanGroupId().toString());
			
			/**
			 * 需要在常量表配置对应值
			 * 支付方式：ALIPAY_SURETY（支付宝担保交易）、ALIPAY_CHAIN（分账交易）、
			 * TRANSFER（线下转账）、PREPAY（预存款）、IMMEDIATELY（即时到账）、CASHGOODS（先款后货）
			 */
			/*if(p.getPayType()!=null){
				o.setPay_method(PAY_METHOD.get(p.getPayType())); //没有找到  --根据淘宝的支付方式来对应
			}*/
			if(StringUtils.isEmpty(o.getPay_method())){
				o.setPay_method("ZFB");
			}
			/**
			 * 数据需要在公共常量表配置
			 * 
			 * 采购单交易状态。可选值：
				WAIT_BUYER_PAY(等待付款)
				WAIT_SELLER_SEND_GOODS(已付款，待发货）
				WAIT_BUYER_CONFIRM_GOODS(已付款，已发货)
				TRADE_FINISHED(交易成功)
				TRADE_CLOSED(交易关闭)
				WAIT_BUYER_CONFIRM_GOODS_ACOUNTED(已付款（已分账），已发货。只对代销分账支持)
				PAY_ACOUNTED_GOODS_CONFIRM （已分账发货成功）
				PAY_WAIT_ACOUNT_GOODS_CONFIRM（已付款，确认收货）
			 */
			o.setPayplatformorderid(order.getPayOrderNo());
			o.setPay_status("WAIT_SELLER_SEND_GOODS"); //支付状态   要转中文
			if(order.getTbOrderPayTime()!=null)
				o.setPay_time(DF.format(order.getTbOrderPayTime())); //付款时间
			//o.setPaychannelid(paychannelid) //支付渠道编码	--传空
			//o.setPaychannelname(paychannelname) //支付渠道名称	--传空
			//o.setPaytype(paytype) //支付类型	--传空
			
			o.setPlat_distributor_code("");//平台分销商编号
			//分销方式：AGENT（代销）、DEALER（经销）
			o.setPlat_type("供销");
			//o.setPlat_type(plat_type); //平台类型  默认淘宝
			o.setPlatform_status(order.getStatus()); //外部平台状态   跟订单状态一样
			
			o.setPro_totalfee("0"); //邮费。精确到2位小数;
			//o.setPromotion_area(promotion_area) //促销政策地 01:集团 02:省公司 03:地市公司 --传空
			
			//o.setRecommended_code(recommended_code);//推荐人编码    传空
			//o.setRecommended_name(recommended_name)//推荐人编码    传空
			//o.setRecommended_phone(recommended_phone) ////推荐人编码    传空
			/**
			 * 配送方式，FAST(快速)、EMS、ORDINARY(平邮)、SELLER(卖家包邮)  需要在公共常量表配数据
			 */
			if(order.getLogisticsFlag() == 1){
				//无需配送
				o.setSending_type("NO");//配送方式
			}else{
				o.setSending_type("KD");
			}
			
			
			/**
			 * 订购状态。可选值：
				NOT_ORDER-未订购，ORDER_AUDIT-订购中(无订购接口，提交给供应商，线下受理中)，
				ON_ORDER-订购中(有订购接口，线上受理中),SUCCESS-订购成功，FAILURE-订购失败,CANCEL-订购取消
			 */
			o.setStatus(order.getOrderStatus());//处理状态
			//o.setTid(tid);  //店宝订单编号 城要生成
			if(order.getTbOrderCreateTime()!=null)
				o.setTid_time(DF.format(order.getTbOrderCreateTime())); //订货时间  淘宝下单时间
			//o.setTwo_agents_id(two_agents_id); //二级代理商id	--传空
			/**
			 * 订单类型 包括：等货订单、囤货订单、换货订单、其他订单、预售订单、正常订单、中断订单 --填默认值（正常订单）
			 */
			o.setType("1"); //订单类型
			//o.setVouchers_code(vouchers_code);//代金券编号	传空
			//o.setVouchers_money(vouchers_money);//代金券面值 传空
			//o.setWm_isreservation_from(wm_isreservation_from)//是否预约单	0否1是 --传空
			//o.setWm_order_from(wm_order_from);//网盟订单来源	--传空 
			//o.setWt_paipai_order_id(wt_paipai_order_id);//拍拍网厅的订单号	--传空
			//o.setGoods_num(t.getNum()==null?0:t.getNum().intValue());
			o.setC_order_from("淘宝分销");
			o.setOrder_from(order_from);
			
			//打包订单项
			List<OuterItem> items = new ArrayList<OuterItem>();
			StringBuffer goodsName = new StringBuffer();
			//商品名称
			goodsName.append(order.getPlanGroupName()).append("# #d#");
			OuterItem toi = packageTaobaoOrderItem(order,o,order_from,outer_package_id,sku);
			//如果没有子单就不做同步操作
			if(toi==null)return null;
			items.add(toi);
			o.setTid_category(toi.getTid_category());
			o.setOrder_disfee("0");//淘宝的优惠金额
			o.setDiscountValue("0");//优惠金额	--传空   需要在子订单计算
			
			//获取商品参与活动的活动编码
			/*//应收
			double orderAmount = Double.parseDouble(o.getOrder_totalfee());
			//实收
			double payMoney = Double.parseDouble(o.getOrder_realfee());
			//直降金额
			String relieffee = MallUtils.parseMoneyToLi(orderAmount - payMoney);*/
			//获取商品对应的活动编码
			String sql = "select c.goods_id, c.pmt_id, d.pmta_id, d.pmt_type, d.pmt_solution, " +
					" e.name,e.pmt_code, e.begin_time, e.end_time, e.status_date, f.org_id from es_promotion d," +
					" es_promotion_activity e, es_pmt_goods c, es_activity_co f where " +
					" c.goods_id = '"+toi.getGoods_id()+"' and c.pmt_id = d.pmt_id" +
					" and d.pmta_id = e.id and e.id = f.activity_id and f.org_id = '10001'" +
					" and (e.region = '"+province_region+"' or e.region = '"+o.getOrder_city_code()+"') and " +
					" to_date('"+o.getTid_time()+"','yyyy-mm-dd hh24:mi:ss') between " +
					" e.begin_time and e.end_time and f.source_from = '"+ManagerUtils.getSourceFrom()+"' and f.source_from = d.source_from " +
					" and f.source_from = e.source_from and f.source_from = c.source_from " +
					" and d.pmt_type = '006' and e.enable=1 order by e.status_date";
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			List activityList = baseDaoSupport.queryForList(sql);
			if(null != activityList && activityList.size() > 0){
				StringBuffer strBuffer = new StringBuffer();
				for (int j = 0; j < activityList.size(); j++) {
					Map m = (Map)activityList.get(j);
					strBuffer.append(m.get("pmt_code").toString()).append(",");
				}
				String str = strBuffer.substring(0, strBuffer.lastIndexOf(","));
				if(str.length() > 128){
					o.setReserve9(str.substring(0,128));
				}else{
					o.setReserve9(str);
				}
			}
			o.setItemList(items);
			extMap.put("GoodsName", goodsName.toString());
			
			//o.setDevelopment_code(development_code)//--待局方确认
			//o.setDevelopment_name(development_name)//--待局方确认
			//o.setDiscountid(discountid)//优惠活动编号	--传空
			//o.setDiscountname(discountname)//优惠活动名称	--传空
			//o.setDiscountrange(discountrange)//	可选	优惠幅度	--传空
			
			//o.setDisfee_type(disfee_type)//优惠类型 --传空
			//o.setFile_return_type(file_return_type)//资料回收方式 --传空
			
			
			//新增默认值=============mochunrun========2014-7-23=============================
			if(StringUtils.isEmpty(o.getOrder_city_code())){
				o.setOrder_city_code(cacheUtil.getConfigInfo("TAOBAO_CITY_DEFAULT"));//归属地市：Order_city_code 订单归属属地有可能为空没有的默认为广州（440100）
			}
			//o.setDevelopment_code("-1");//发展人编码：development_code 淘宝获取不到，写死为 -1
			//o.setDevelopment_name("-1");//发展人名称：development_name 淘宝获取不到，写死为 -1
			if(StringUtils.isEmpty(o.getRecommended_phone())){
				o.setRecommended_phone("-1");//(对应推送节点reference_phone)  淘宝获取不到，写死为 -1
			}
			o.setPaytype("ZXZF");//支付类型：paytype（对庆推送节点pay_type） 淘宝的默认为在线去付(ZXZF)
			//====province_code 没获取到时默认填广东(440000)、city_code没获取到时默认填广州(440100)、area_code没获取到时默认填天河(440106)
			if(StringUtils.isEmpty(o.getProvinc_code())){
				o.setProvinc_code(province_region);
			}
			if(StringUtils.isEmpty(o.getCity_code())){
				o.setCity_code(cacheUtil.getConfigInfo("TAOBAO_CITY_DEFAULT"));
			}
			if(StringUtils.isEmpty(o.getArea_code())){
				o.setArea_code(cacheUtil.getConfigInfo("TAOBAO_DISTRICT_DEFAULT"));
			}
			//====province_code 没获取到时默认填广东(440000)、city_code没获取到时默认填广州(440100)、area_code没获取到时默认填天河(440106)
			if(StringUtils.isEmpty(o.getAddress())){
				o.setAddress("无");//收货地址，没有获取到时填"无"
			}
			if(StringUtils.isEmpty(o.getReceiver_mobile())){
				o.setReceiver_mobile("-1");//收货人手机号码：receiver_mobile 没有填 － 1
			}
			if(StringUtils.isEmpty(o.getInvoice_print_type())){
				o.setInvoice_print_type("3");//发票打印方式：invoice_print_type 如果备注没有则会为空 默认“不打印(3)” 字典(1一次性打印 2分月打印 3不打印发票)
			}else{
				//一次性发票/分月发票
				if("一次性发票".equals(o.getInvoice_print_type())){
					o.setInvoice_print_type("1");
				}else if("分月发票".equals(o.getInvoice_print_type())){
					o.setInvoice_print_type("2");
				}else{
					o.setInvoice_print_type("3");
				}
			}
			if(StringUtils.isEmpty(o.getInvoice_title_desc())){
				o.setInvoice_title_desc(order.getBuyerName());//发票抬头：invoice_title  淘宝获取不到。没有填”昵称”
			}
			o.setReserve8(o.getInvoice_title_desc());//发票抬头：
			o.setPlat_type("10012");//1、订单来源系统属性：source_from_system 内容填写编码（10001淘宝网厅、10012淘宝分销） Plat_type
			o.setC_order_from("10012");// 淘宝   改为编码，会填到outer_accept表的 order_from字段    2、订单来源属性：order_source_from  内容填写编码（10001淘宝网厅、10012淘宝分销） Order_from
			o.setReserve2("13");//渠道类型：channel_mark （0：淘宝网厅是自营 1、淘宝分销是代理商）    Reserve2
			o.setReserve1("0");//是否2G、3G升4G：Is_to4g 淘宝获取不到. 填否(0)
			
			o.setType("1"); //订单类型      "正常订单" 对应 "1订购 "  淘宝获取不到，默认类型可填写为订购 (1)   字典(1订购 2退订 3变更 4预约 5预售 6换货) Type
			extMap.put("n_shipping_amount", "0");// 6、实收运费：n_shipping_amount （对应淘宝的字段为yfx_fee）没有运费填写0     extendMap.n_shipping_amount
			/*if("25".equals(p.getShipping())){
				extMap.put("shipping_quick", "02");//7、是否闪电送：shipping_quick  根据淘宝shipping_type来判断如果为(25为次日必达是闪电送填定02 是) 如果不是默认01 否
			}else {
				extMap.put("shipping_quick", "01");
			}*/
			extMap.put("logi_no", order.getExpressNumber());
			extMap.put("shipping_company", order.getExpressCode());
			extMap.put("shipping_company_name", order.getExpressName());
			extMap.put("shipping_quick", "02");//默认非闪电送
			extMap.put("shipping_time", "NOLIMIT");// 8、配送时间：shipping_time 淘宝获取不到，默认填写（NOLIMIT 不限时送）
			extMap.put("uid", "510000"); //9、买家标识：uid 淘宝获取不到，默认填(510000)
			extMap.put("invoice_group_content", "MX");//10、发票内容:Invoice_group_content 淘宝获取不到, 没有填明细 (MX)  extendMap.invoice_group_content
			extMap.put("is_deal", "0");//是否已办理完成：is_deal 淘宝获取不到 填否(0)  
			extMap.put("order_heavy", "0");// 订单重量
			extMap.put("is_change", "0");//是否变更套餐
			extMap.put("package_sale", "0");//套包销售标记
			extMap.put("special_busi_type", order.getBizType());//业务类型
			extMap.put("supplier_order_id", order.getOutOrderNo()); //供应商外部订单号
			extMap.put("seller_message", order.getDistributorNick()); //卖家留言
			
			LocalOrderAttr loa = new LocalOrderAttr();
			loa.setIs_old("0");// 是否老用户：is_old 淘宝填“否”
//			商品上配置卡类型
//			loa.setSim_type("白卡");//成卡/白卡：sim_type       默认填 成卡
			loa.setGuarantor("无");//担保人：guarantor 默认：无
			loa.setBill_mail_type("00");//账单寄送方式：bill_mail_type 是（默认：00）
			loa.setPackage_sale("0");//套包销售标记：package_sale  默认是0 －非套包销售
			loa.setBill_type("10");//账户付费方式：bill_type（默认值10） 字典(10：现金 11：现金支票 12：转账支票 13：信用卡 14：缴费卡 15：托收 16：代收 17：银行代扣)
			loa.setPro_brand(PRODUCT_BRAND.get(o.getItemList().get(0).getProduct_net()+o.getItemList().get(0).getType_id()));
			o.setLocalObject(loa);
			//新增默认值=============mochunrun========2014-7-23=============================
			
			return o;
		}catch(Exception ex){
			ex.printStackTrace();
			OuterError ng = new OuterError("", "", "", "", order_from, order.getTbOrderNo()+"", "sysdate","error");
			outterStdTmplManager.insertOuterError(ng);
			return null;
		}
	}
	
	public OuterItem packageTaobaoOrderItem(DistributionOrderInfo order,Outer outer,String order_from,String outer_package_id,String sku){
		OuterItem o = new OuterItem();
		/*合约机：（订单）outer_sku_id+(网厅)package_id
		号卡2：（订单）out_iid+(网厅)package_id
		号卡1：(网厅)package_id
		上网卡：（订单）out_iid
		裸机：（订单）outer_sku_id*/
		String pagkage_id = String.valueOf(order.getPlanId()); //天猫分销商品编号
		//String pagkage_id = String.valueOf("20170522"); //天猫分销商品编号
		String outer_sku_id = null;//对应sn字段 条型码
		Map<String,String> param = new HashMap<String,String>();
		
		if(!StringUtils.isEmpty(pagkage_id) || !StringUtils.isEmpty(outer_sku_id)){// && !StringUtils.isEmpty(i.getOuterSkuId())  
			//ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			try{
				/* 
				 * ur:199371 淘宝订单归集时，如果订单里合约计划编码(pcode）和商家编码(sn)均不为空，
				 * 则从es_goods_package表中匹配出goods_id；如果合约计划编码、商家编码中有任一个为空，
				 * 则直接取其作为goods_id，从es_goods表中获取对应的商品。
				*/
				GoodsGetReq req = new GoodsGetReq();
				/*req.setPackage_id(pagkage_id);
				req.setSn(outer_sku_id);*/
				ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
				//GoodsGetResp resp = client.execute(req, GoodsGetResp.class);
				GoodsGetResp resp = null;
				OrderStdMangerLocal orderStdMangerLocal = SpringContextHolder.getBean("orderStdMangerLocal");
				param = orderStdMangerLocal.getHSGoodsByMatnr(pagkage_id);
				if(param!=null){
					//param = resp.getData();
				}else{
					//add by zou.qh 20160930 匹配不到商品，给一个默认的商品，订单匹配独立生产模式 ，一期功能
					ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
					String defaultGoods = cacheUtil.getConfigInfo("ORDER_STD_WITH_GOODS_DEFAULT");
					if(ConstsCore.CONSTS_YES.equals(defaultGoods)){
						String default_sn = cacheUtil.getConfigInfo("ORDER_STD_WITH_GOODS_SN_DEFAULT");
						if(StringUtils.isEmpty(default_sn)){
							OuterError ng = new OuterError(pagkage_id, outer_sku_id, "", order.getItemTitle(), order_from, order.getTbOrderNo()+"", "sysdate","nogoods");
							outterStdTmplManager.insertOuterError(ng);
							throw new Exception("根据商品编码未匹配到商品，且没有配置默认商品");
						}
						req = new GoodsGetReq();
						req.setSn(default_sn);
						resp = client.execute(req, GoodsGetResp.class);
						param = resp.getData();
					}
					else{
						OuterError ng = new OuterError(pagkage_id, outer_sku_id, "", order.getItemTitle(), order_from, order.getTbOrderNo()+"", "sysdate","nogoods");
						outterStdTmplManager.insertOuterError(ng);
						logger.info("电商没有配置商品=====");
						return null;
					}
					if(param == null){
						OuterError ng = new OuterError(pagkage_id, outer_sku_id, "", order.getItemTitle(), order_from, order.getTbOrderNo()+"", "sysdate","nogoods");
						outterStdTmplManager.insertOuterError(ng);
						logger.info("根据默认商品编码获取不到商品信息=====");
						return null;
					}
				}
			}catch(Exception ex){
				OuterError ng = new OuterError(pagkage_id, outer_sku_id, "", order.getItemTitle(), order_from, order.getTbOrderNo()+"", "sysdate","goodserror");
				outterStdTmplManager.insertOuterError(ng);
				logger.info("调dubbo获取商品失败=====");
				ex.printStackTrace();
				return null;
			}
		}else{
			//没有相应的字段查询商品
			OuterError ng = new OuterError(pagkage_id, outer_sku_id, "", order.getItemTitle(), order_from, order.getTbOrderNo()+"", "sysdate","noparam");
			ng.setDeal_flag("-1");
			outterStdTmplManager.insertOuterError(ng);
			logger.info("没有商品相关参数======================");
			return null;
		}
		
		packageGoodsParam(o, param);
		/**
		 * 规格数所
		 */
		//o.setLiang_price(liang_price);//靓号金额	单位元 --待确认
		//o.setLoves_phone_num(loves_phone_num)//情侣号码	空
		o.setSimid(order.getProductSerialNo());
		if(order.getBuyQuantity() == null){
			o.setPro_num(1);
		}else{
			o.setPro_num(order.getBuyQuantity().intValue());
		}
		o.setPro_origfee(Double.valueOf(order.getLedgerAmount())/100);
		o.setPlan_title(order.getPlanName());
		o.setPhone_num(order.getPhoneNo());
		o.setSell_price(0D);//商品单价
		o.setOuter_sku_id(order.getOutPackageId());//外部网店自己定义的Sku编号对应电商es_goods_package表的sn字段
		
		//新增默认值=============mochunrun========2014-7-23=============================
		IdCardInfo card = order.getIdCardInfo();
//		if(StringUtils.isEmpty(card.getName())){
//			if(card.getCardNumber()!=null && card.getCardNumber().length()==15){
//				o.setCert_type("SFZ15");//证件类型：certi_type 没获取到填写(身份证)  证件号码certi_num:没获取到填-1  证件地址certi_address没获取到填-1
//			}else{
//				o.setCert_type("SFZ18");
//			}
//		}else{
//			String str = CERT_TYPE.get(card.getName());
//			if("SFZ".equals(str)){
//				if(card.getCardNumber()!=null && card.getCardNumber().length()==15){
//					o.setCert_type("SFZ15");//证件类型：certi_type 没获取到填写(身份证)  证件号码certi_num:没获取到填-1  证件地址certi_address没获取到填-1
//				}else{
//					o.setCert_type("SFZ18");
//				}
//			}
//		}
		
		if(!StringUtils.isEmpty(card.getName())){
			if(StringUtils.isEmpty(o.getPhone_owner_name())){
				o.setPhone_owner_name(card.getName());//客户名称(cust_name)
			}
		}
		
		if(card.getCardNumber()!=null && card.getCardNumber().length()==15){
			o.setCert_type("SFZ15");//证件类型：certi_type 没获取到填写(身份证)  证件号码certi_num:没获取到填-1  证件地址certi_address没获取到填-1
		}else{
			o.setCert_type("SFZ18");
		}
		
		
		o.setCert_card_num(card.getCardNumber());
		if(StringUtils.isEmpty(o.getCert_card_num())){
			o.setCert_card_num("-1");
		}
		/*if(StringUtils.isEmpty(o.getCert_address())){
			o.setCert_address("-1");
		}*/
//		if(StringUtils.isEmpty(o.getPhone_owner_name())){
//			o.setPhone_owner_name(order.getBuyerName());//客户名称(cust_name)：对应机主姓名(phone_owner_name)没获取到填写昵称
//		}
		if(StringUtils.isEmpty(o.getFirst_payment())){
			o.setFirst_payment(FIRST_PAYMENT_MAP.get(o.getFirst_payment()));
		}
		if(StringUtils.isEmpty(o.getFirst_payment())){
			//如果淘宝的备注没按规范填写或者没有备注按支付时间判断（10号之前是全月，10号到20号是半月，20号之后是次月）。
			//ALLM当月生效 HALF半月生效 COMM次月生效
			DateFormat df = new SimpleDateFormat("d");
			if(order.getTbOrderPayTime()!=null){
				String str = df.format(order.getTbOrderPayTime());
				int day = Integer.parseInt(str);
				if(day<10){
					o.setFirst_payment("ALLM");
				}else if(day>=10 && day<=20){
					o.setFirst_payment("HALF");
				}else{
					o.setFirst_payment("COMM");
				}
			}
		}
		if(StringUtils.isEmpty(o.getFirst_payment())){
			o.setFirst_payment("ALLM");//首月资费方式：First_payment （对庆推送api节点offer_eff_type）没有获取到填写全月(ALLM)，获取到了需要转换为编码
                                       //字典(ALLM全月套餐 HALF套餐减半 COMM套餐包外资费  ALLM当月生效 HALF半月生效 COMM次月生效)
		}
		if(StringUtils.isEmpty(o.getProduct_net())){
			o.setProduct_net("3G");//网别：Product_net （对庆推送api节点net_type）如果为空就填3G
		}
		if(!StringUtils.isEmpty(o.getWhite_cart_type())){
			o.setWhite_cart_type(CARD_TYPE.get(o.getWhite_cart_type()));
		}
		if(StringUtils.isEmpty(o.getWhite_cart_type())){
			o.setWhite_cart_type("NM");//卡类型：White_cart_type（对庆推送api节点card_type）（默认填 大卡）
		}
		//新增默认值=============mochunrun========2014-7-23=============================
				
		return o;
	}
	
}
