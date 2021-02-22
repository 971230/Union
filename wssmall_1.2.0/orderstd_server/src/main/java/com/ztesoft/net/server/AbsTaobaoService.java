package com.ztesoft.net.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.drools.core.util.StringUtils;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.resp.GoodsGetResp;
import zte.params.region.req.RegionsGetReq;
import zte.params.region.resp.RegionsGetResp;

import com.powerise.ibss.framework.Const;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;
import com.taobao.api.domain.WtExtResult;
import com.taobao.api.response.TradeGetResponse;
import com.taobao.api.response.TradeWtverticalGetResponse;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.model.Regions;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.model.OuterItem;
import com.ztesoft.net.outter.inf.model.LocalOrderAttr;
import com.ztesoft.net.outter.inf.model.OuterError;
import com.ztesoft.net.outter.inf.model.PublicConst;
import com.ztesoft.net.outter.inf.service.SDBUtils;
import com.ztesoft.net.outter.inf.util.OuterSysConst;

public abstract class AbsTaobaoService extends AbsTaobao{
	private static Logger logger = Logger.getLogger(AbsTaobaoService.class);
	/**
	 * 打包订单项
	 * @作者 MoChunrun
	 * @创建日期 2014-3-13 
	 * @param list
	 * @return
	 */
	public OuterItem packageTaobaoOrderItem(Trade t,Order i,WtExtResult wt,String order_from,TradeGetResponse tradeInfo,String out_package_id,String sku){
		//num_iid	 Number	 否	2342344	商品数字ID
		//item_meal_id	 Number	 是	2564854632	套餐ID
		//outer_iid	 String	 是	152e442aefe88dd41cb0879232c0dcb0	商家外部编码(可与商家外部系统对接)。外部商家自己定义的商品Item的id，可以通过taobao.items.custom.get获取商品的Item的信息
		if(i==null)return null;
		
		OuterItem o = new OuterItem();

		if(wt!=null){
			o.setCert_card_num(wt.getCertCardNum()); //证件号码  --空（回访时），增加字段
			o.setCert_type(wt.getCertType()+"");//证件类型
			/**
			 * 合约编码
			 */
			o.setOut_package_id(wt.getOutPackageId());//合约编码
			
			o.setPhone_num(StringUtil.isEmpty(wt.getPhoneNum())?"":wt.getPhoneNum());//手机号码	开户手机号码 --增加字段
			o.setPhone_owner_name(StringUtil.isEmpty(wt.getPhoneOwnerName())?"":wt.getPhoneOwnerName());//机主姓名
			o.setReliefpres_flag(wt.getPhoneFreeDeposit()+"");//减免预存标记	淘宝没有就空。
		}
		if(StringUtils.isEmpty(o.getOut_package_id()) && !StringUtils.isEmpty(out_package_id)){
			o.setOut_package_id(out_package_id);
		}
		
		String ss [] = null;
		if(tradeInfo!=null && tradeInfo.getTrade()!=null &&!StringUtil.isEmpty(tradeInfo.getTrade().getSellerMemo())){
			ss = tradeInfo.getTrade().getSellerMemo().replace("\r\n", "").replace("\r", "").replace("\n", "").split(",");
		}else if(!StringUtil.isEmpty(t.getSellerMemo())){
			ss = t.getSellerMemo().replace("\r\n", "").replace("\r", "").replace("\n", "").split(",");
		}
		if(!StringUtils.isEmpty(i.getSkuPropertiesName())){
			String skuProNames[] = i.getSkuPropertiesName().replace("\r\n", "").replace("\r", "").replace("\n", "").split(";");
			for(String name:skuProNames){
				String [] s2 = name.trim().split(":");
				if("手机卡类型".equals(s2[0])&&s2.length>1){//卡类型 nano卡，微型卡，普通卡 -- 根据XX解析
					o.setWhite_cart_type(s2[1]);
					break;
				}
			}
		}
		if(ss!=null){
			for(String s:ss){
				if(s!=null){
					String [] s2 = s.trim().split("=");
					if(s2!=null && s2.length==2){
						if("卡类型".equals(s2[0])&&StringUtils.isEmpty(o.getWhite_cart_type())){//上面已经解析到，则这里不用再解析
							o.setWhite_cart_type(s2[1]);//卡类型	大卡、小卡、NANO卡 --根据客服备注解析，实在没有就空
						}else if("号码".equals(s2[0]) && StringUtils.isEmpty(o.getPhone_num())){
							o.setPhone_num(s2[1]);
						}else if("托收".equals(s2[0])){
							o.setCollection(s2[1]) ;//是否托收 字典：0否，1是 --EDB回访补录的，实在没有就空
						}else if("证件类型".equals(s2[0])){
							o.setCert_type(s2[1]);//证件类型
						}else if("开户名".equals(s2[0]) && StringUtils.isEmpty(o.getPhone_owner_name())){
							o.setPhone_owner_name(s2[1]);
						}else if("证件号码".equals(s2[0])){
							o.setCert_card_num(s2[1]); //证件号码 
						}else if("资费方式".equals(s2[0])){
							o.setFirst_payment(s2[1]);
						}
					}
				}
			}
		}
		
		//测试号码占用
		//o.setPhone_num("18575820322");
		
		/*合约机：（订单）outer_sku_id+(网厅)package_id
		号卡2：（订单）out_iid+(网厅)package_id
		号卡1：(网厅)package_id
		上网卡：（订单）out_iid
		裸机：（订单）outer_sku_id*/
		String pagkage_id=null;
		String outer_sku_id = i.getOuterSkuId();//对应sn字段 条型码
		String outer_iid = i.getOuterIid();//对应sku字段 商品编码
		if(StringUtils.isEmpty(outer_sku_id))
			outer_sku_id = outer_iid;
		if(wt!=null)pagkage_id = o.getOut_package_id();
		if(StringUtils.isEmpty(outer_sku_id))outer_sku_id=sku;
		//pagkage_id = "20120426112211050002"; //1   20131021104828019378  号卡 20120426112211050002
		//outer_sku_id = null;//"7400207901"; //mk43535  7400207901  上网卡 6901020100078 裸机 6920140227100
		//if(609702644532460l!=t.getTid())return null;
		//outer_sku_id="6920140227100";pagkage_id=null;
		
		if(pagkage_id!=null)pagkage_id=pagkage_id.trim();
		if(outer_sku_id!=null)outer_sku_id=outer_sku_id.trim();
		if("无".equals(outer_sku_id))outer_sku_id=null;
		Map<String,String> param = new HashMap<String,String>();
		if(!StringUtils.isEmpty(pagkage_id) || !StringUtils.isEmpty(outer_sku_id) || !StringUtils.isEmpty(outer_iid)){// && !StringUtils.isEmpty(i.getOuterSkuId())  
			//ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			try {
				
				/* 
				 * ur:199371 淘宝订单归集时，如果订单里合约计划编码(pcode）和商家编码(sn)均不为空，
				 * 则从es_goods_package表中匹配出goods_id；如果合约计划编码、商家编码中有任一个为空，
				 * 则直接取其作为goods_id，从es_goods表中获取对应的商品。
				*/
				GoodsGetReq req = new GoodsGetReq();
				if (!StringUtils.isEmpty(pagkage_id) && !StringUtils.isEmpty(outer_sku_id)) {
					req.setPackage_id(pagkage_id);
					req.setSn(outer_sku_id);
				} else {
					req.setGoods_id(StringUtils.isEmpty(pagkage_id) ? outer_sku_id : pagkage_id);
				}
				ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
				GoodsGetResp resp = client.execute(req, GoodsGetResp.class);//goodServices.getGoods(req);//
				if("0".equals(resp.getError_code())){
					param = resp.getData();
				}else{
					OuterError ng = new OuterError(pagkage_id, outer_sku_id, outer_iid, i.getTitle(), order_from, t.getTid()+"", "sysdate","nogoods");
					outterStdTmplManager.insertOuterError(ng);
					logger.info("电商没有配置商品=====");
					return null;
				}
			}catch(Exception ex){
				OuterError ng = new OuterError(pagkage_id, outer_sku_id, outer_iid, i.getTitle(), order_from, t.getTid()+"", "sysdate","goodserror");
				outterStdTmplManager.insertOuterError(ng);
				logger.info("调dubbo获取商品失败=====");
				ex.printStackTrace();
				StackTraceElement stacks [] = ex.getStackTrace();
				StringBuffer errMsgBuffer = new StringBuffer();
				for (int k = 0; k < stacks.length; k++) {
					errMsgBuffer.append(stacks[k].toString()).append("\n");
				}
				logger.info(errMsgBuffer.toString());

				return null;
			}
		}else{
			//没有相应的字段查询商品
			OuterError ng = new OuterError(pagkage_id, outer_sku_id, outer_iid, i.getTitle(), order_from, t.getTid()+"", "sysdate","noparam");
			ng.setDeal_flag("-1");
			outterStdTmplManager.insertOuterError(ng);
			logger.info("没有商品相关参数======================");
			return null;
		}
		
		packageGoodsParam(o, param);
		
		//o.setAdjustment_credit(adjustment_credit)//调整信用度  是根据BSS套餐对应的档次乘以1.5得到的数值（BSS套餐对应待确认） 没有
		
		//o.setBank_account(bank_account)//EDB回访补录的，实在没有就空
		//o.setBank_code(bank_code) //EDB回访补录的，实在没有就空
		//o.setBank_user(bank_user) //EDB回访补录的，实在没有就空
		
		//o.setColor_name(i.getSkuPropertiesName());
		//o.setDisfee_select(disfee_select);//优惠选项	详见优惠选项字典 --空
		//o.setFamliy_num(famliy_num);//多号码以,号分隔  --空
		//o.setFirst_payment(first_payment);//详见首月资费方式字典--EDB回访补录的，实在没有就空
		
		//o.setLiang_code(param.get("liang_code"));//靓号单编号	--待确认，老的是人工对应
		/**
		 * 规格数所
		 */
		//o.setLiang_price(liang_price);//靓号金额	单位元 --待确认
		//o.setLoves_phone_num(loves_phone_num)//情侣号码	空
		
		o.setPro_num(i.getNum().intValue());
		o.setPro_origfee(Double.valueOf(i.getTotalFee()));
		
		
		o.setSell_price(Double.valueOf(i.getPrice()));//商品单价
		//o.setSimid(simid);//SIM卡号	　空
		//o.setSociety_name(society_name);//社会代理商名称	--空
		//o.setSociety_price(society_price);//代理商终端结算价格	单位元 --空
		
		//o.setSuperiors_bankcode(superiors_bankcode);//上级银行编码	--EDB回访补录的，实在没有就空
		//o.setTerminal_num(terminal_num);//终端串号	　空
		
		o.setOuter_sku_id(i.getOuterSkuId());//外部网店自己定义的Sku编号对应电商es_goods_package表的sn字段

		String c_order_from = "";//之前写死10001都换成配置的c_order_from
		List<Map> relations = CommonDataFactory.getInstance().getDictRelationListData(StypeConsts.ISTBORDER);
		if(relations!=null && relations.size()>0){
			for(Map relation : relations){
				if(order_from.equals(Const.getStrValue(relation, "field_value"))){
					c_order_from = Const.getStrValue(relation, "other_field_value");
					break ;
				}
			}
		}
		//设置发货仓库ID
		o.setOut_house_id(i.getStoreCode());
		if(!StringUtils.isEmpty(o.getOut_house_id())){
			PublicConst pc = outterStdTmplManager.queryPublicConst(order_from, OuterSysConst.CONST_OBJECT_TYPE_HOUSE, o.getOut_house_id());
			if(pc!=null)o.setHouse_id(pc.getZte_code());
			o.setOrg_id(c_order_from);
		}
		
		//新增默认值=============mochunrun========2014-7-23=============================
		if(!StringUtils.isEmpty(o.getCert_type()))o.setCert_type(CERT_TYPE.get(o.getCert_type()));
		if(StringUtils.isEmpty(o.getCert_type())){
			if(o.getCert_card_num()!=null && o.getCert_card_num().length()==15){
				o.setCert_type("SFZ15");//证件类型：certi_type 没获取到填写(身份证)  证件号码certi_num:没获取到填-1  证件地址certi_address没获取到填-1
			}else{
				o.setCert_type("SFZ18");
			}
		}else{
			if("SFZ".equals(o.getCert_type())){
				if(o.getCert_card_num()!=null && o.getCert_card_num().length()==15){
					o.setCert_type("SFZ15");//证件类型：certi_type 没获取到填写(身份证)  证件号码certi_num:没获取到填-1  证件地址certi_address没获取到填-1
				}else{
					o.setCert_type("SFZ18");
				}
			}
		}
		if(StringUtils.isEmpty(o.getCert_card_num())){
			o.setCert_card_num("-1");
		}
		if(StringUtils.isEmpty(o.getCert_address())){
			o.setCert_address("-1");
		}
		if(StringUtils.isEmpty(o.getPhone_owner_name())){
			o.setPhone_owner_name(t.getBuyerNick());//客户名称(cust_name)：对应机主姓名(phone_owner_name)没获取到填写昵称
		}
		if(!StringUtils.isEmpty(o.getFirst_payment())){
			o.setFirst_payment(FIRST_PAYMENT_MAP.get(o.getFirst_payment()));
		}
		if(StringUtils.isEmpty(o.getFirst_payment())){
			//如果淘宝的备注没按规范填写或者没有备注按支付时间判断（10号之前是全月，10号到20号是半月，20号之后是次月）。
			//ALLM当月生效 HALF半月生效 COMM次月生效
			if(t.getPayTime()!=null){
				DateFormat df = new SimpleDateFormat("d");
				String str = df.format(t.getPayTime());
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
	/**
	 * 打包订单信息
	 * @作者 MoChunrun
	 * @创建日期 2014-3-13 
	 * @param t
	 * @param end_time
	 * @return
	 * @throws ApiException 
	 */
	public Outer packageTaobaoOrder(Trade t,String end_time,String json,String order_from,TradeGetResponse tradeInfo,boolean isJXT,String package_id,String sku) throws ApiException{
		try{
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String province_region = cacheUtil.getConfigInfo("PROVINCE_REGION_CODE");
			WtExtResult wt = null;
			//是否网厅订单
			if(isJXT || (tradeInfo==null && !isJXT) || (t.getIsWt()!=null && t.getIsWt())){
				try{
					TradeWtverticalGetResponse wtresp = getTradeWtvertical(t.getTid()+"",json);
					if(wtresp!=null && wtresp.getErrorCode()==null){
						if(wtresp.getWtextResults().size()>0){
							wt = wtresp.getWtextResults().get(0);
						}
					}
				}catch(Exception ex){ 
					//OuterError ng = new OuterError("", "", "", "", order_from, t.getTid()+"", "sysdate","wterror");
					//outterStdTmplManager.insertOuterError(ng);
				}
			}
			Outer o = new Outer();
			Map extMap = new HashMap();  //扩展属性
			o.setExtMap(extMap);
			
			/*if(wt!=null){
				o.setOut_package_id(wt.getOutPackageId());
			}*/
			if(tradeInfo!=null && tradeInfo.getTrade()!=null){
				o.setService_remarks(StringUtil.isEmpty(tradeInfo.getTrade().getSellerMemo())?"":tradeInfo.getTrade().getSellerMemo()); //系统解析，待局方确认
				o.setBuyer_message(StringUtil.isEmpty(tradeInfo.getTrade().getBuyerMessage())?"":tradeInfo.getTrade().getBuyerMessage());//如果有买家留言   tradeInfo.getTrade().getBuyerMemo()备注
			}else{
				o.setService_remarks(t.getSellerMemo()); //系统解析，待局方确认
				o.setBuyer_message(t.getBuyerMessage());
			}
			
			
			
			o.setPost_fee(t.getPostFee());
			o.setAbnormal_status(StringUtil.isEmpty(t.getMarkDesc())?"正常":t.getMarkDesc());//订单出现异常问题的时候，给予用户的描述,没有异常的时候，此值为空
			o.setAddress(t.getReceiverAddress());
			o.setAlipay_id(String.valueOf(t.getAlipayId()));
			RegionsGetReq regionGet = new RegionsGetReq();
			regionGet.setRegion_cond_type(RegionsGetReq.NAME_COND_TYPE);
			//ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			if(!StringUtils.isEmpty(t.getReceiverDistrict())){
				o.setDistrict(t.getReceiverDistrict());//收货区
				regionGet.setLocal_name(t.getReceiverDistrict());
				RegionsGetResp regionResp = getRegion(regionGet);//getRegion(regionGet);//client.execute(regionGet, RegionsGetResp.class);//getRegion(regionGet);
				Regions region = regionResp.getRegions();
				if(region!=null){
					//o.setArea_code(region.getRegion_code());//收货区编码 是中文的需要转为编码
					o.setArea_code(region.getRegion_id());//收货区编码 是中文的需要转为编码
					//o.setRegion_id(region.getRegion_id());
				}
			}
			//o.setBss_order_type(bss_order_type); //BSS产品类型	--传空
			o.setBuyer_id(t.getBuyerNick());//买家昵称
			
			o.setBuyer_name(t.getReceiverName());//买家姓名，没有所以填了收货人姓名
			//o.setCert_address(cert_address);//没有
			o.setCert_failure_time("2050-12-31 23:59:59");
			regionGet.setRegion_cond_type(RegionsGetReq.NAME_COND_TYPE);
			if(!StringUtils.isEmpty(t.getReceiverCity())){
				o.setCity(t.getReceiverCity());//中文的
				regionGet.setLocal_name(t.getReceiverCity());
				RegionsGetResp regionResp = getRegion(regionGet);//client.execute(regionGet, RegionsGetResp.class);//getRegion(regionGet);
				Regions region = regionResp.getRegions();
				if(region!=null){
					o.setCity_code(region.getRegion_id());////收货城市编码 是中文的需要转为编码
					//o.setCity_id(region.getRegion_id());
				}
			}
			
			//o.setCollection_free(collection_free)//代收邮费	单位元 --传空
			//o.setCouponbatchid(couponbatchid)//代金券批次号	空
			//o.setCouponname(couponname)//代金券名称	空
			/**
			 * 交易状态。可选值: * TRADE_NO_CREATE_PAY(没有创建支付宝交易) * 
			 * WAIT_BUYER_PAY(等待买家付款) * SELLER_CONSIGNED_PART(卖家部分发货) * 
			 * WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款) * 
			 * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货) * 
			 * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) * TRADE_FINISHED(交易成功) * 
			 * TRADE_CLOSED(付款以后用户退款成功，交易自动关闭) * 
			 * TRADE_CLOSED_BY_TAOBAO(付款以前，卖家或买家主动关闭交易)
			 */
			//o.setDelivery_status(t.getStatus());//需要转中文 未发货、已发货、发货到货、已取消、 已退货、退货到货
			if("SELLER_CONSIGNED_PART".equals(t.getStatus())){
				o.setDelivery_status("部分发货");
			}else if("WAIT_BUYER_CONFIRM_GOODS".equals(t.getStatus())){
				o.setDelivery_status("已发货");
			}else if("TRADE_BUYER_SIGNED".equals(t.getStatus())){
				o.setDelivery_status("发货到货");
			}else if("TRADE_FINISHED".equals(t.getStatus())){
				o.setDelivery_status("发货到货");
			}else{
				o.setDelivery_status("未发货");
			}
			//o.setDevelopment_code(development_code)//--待局方确认
			//o.setDevelopment_name(development_name)//--待局方确认
			//o.setDiscountid(discountid)//优惠活动编号	--传空
			//o.setDiscountname(discountname)//优惠活动名称	--传空
			//o.setDiscountrange(discountrange)//	可选	优惠幅度	--传空
			o.setDiscountValue(t.getDiscountFee());//优惠金额	--传空
			//o.setDisfee_type(disfee_type)//优惠类型 --传空
			
			o.setGet_time(end_time);
			
			o.setIs_adv_sale("0"); //是否预售 先付款后准备货发货、字典：0否，1是--默认否
			//o.setIs_bill(is_bill) //是否开发票 --如果淘宝没有就传空
			o.setMerge_status("合并"); //合并状态	合并状态包括：合并、手拆、自动拆分 --默认为合并，送中文。
			//o.setOne_agents_id(one_agents_id)//一级代理商id	--传空
			/**
			 * 交易内部来源。 WAP(手机);HITAO(嗨淘);TOP(TOP平台);TAOBAO(普通淘宝);JHS(聚划算) 一笔订单可能同时有以上多个标记，则以逗号分隔
			 */
			String c_order_from = "";//之前写死10001都换成配置的c_order_from
			List<Map> relations = CommonDataFactory.getInstance().getDictRelationListData(StypeConsts.ISTBORDER);
			if(relations!=null && relations.size()>0){
				for(Map relation : relations){
					if(order_from.equals(Const.getStrValue(relation, "field_value"))){
						c_order_from = Const.getStrValue(relation, "other_field_value");
						break ;
					}
				}
			}
			//t.getTradeFrom();TAOBAO_CHANNEL.get(t.getTradeFrom());
			o.setOrder_channel(c_order_from);//订单渠道包括：电子渠道、集客渠道、营业厅、代理商、CPS、手工下单
			/**
			 * buyer_area 中文 需要转换   浙江省杭州市
			 */
			regionGet.setRegion_cond_type(RegionsGetReq.NAME_COND_TYPE);
			
			
			
			o.setOrder_disfee(t.getDiscountFee());//淘宝的优惠金额
			o.setOrder_realfee(t.getPayment());//订单实收总价 实付金额。 t.getPayment()  商品金额t.getTotalFee()（商品价格乘以数量的总金额）
			o.setOrder_totalfee(t.getTotalFee()); //订单总金额
			//o.setOrderacccode(orderacccode) //订单接入编码	--传空
			//o.setOrderacctype(orderacctype) //订单接入类型	--传空
			o.setOut_tid(String.valueOf(t.getTid()));//外部平台单号
			o.setPay_method("ZFB"); //alipay没有找到  --支付方式：pay_method（对庆推送节点payment_type）淘宝获取不到支付方式，现在写死是支付宝(ZFB)
			
			/**
			 * 分阶段付款的订单状态（例如万人团订单等），
			 * 目前有三返回状态 FRONT_NOPAID_FINAL_NOPAID(定金未付尾款未付)，
			 * FRONT_PAID_FINAL_NOPAID(定金已付尾款未付)，FRONT_PAID_FINAL_PAID(定金和尾款都付)
			 */
			o.setPay_status(t.getStepTradeStatus()); //支付状态   要转中文
			if(t.getPayTime()!=null)
				o.setPay_time(DF.format(t.getPayTime())); //付款时间
			//o.setPaychannelid(paychannelid) //支付渠道编码	--传空
			//o.setPaychannelname(paychannelname) //支付渠道名称	--传空
			//o.setPaytype(paytype) //支付类型	--传空
			o.setPhone(t.getReceiverPhone()); //收货电话 
			//o.setPlat_distributor_code(plat_distributor_code)//平台分销商编号
			o.setPlat_type(plat_type); //平台类型  默认淘宝
			o.setPlatform_status(t.getStatus()); //外部平台状态   跟订单状态一样
			o.setPost(t.getReceiverZip()); //收货人的邮编
			o.setPro_totalfee(t.getPostFee()); //邮费。精确到2位小数;
			//o.setPromotion_area(promotion_area) //促销政策地 01:集团 02:省公司 03:地市公司 --传空
			/**
			 *  中文  需要转换
			 */
			regionGet.setRegion_cond_type(RegionsGetReq.NAME_COND_TYPE);
			if(!StringUtils.isEmpty(t.getReceiverState())){
				regionGet.setLocal_name(t.getReceiverState());
				o.setProvince(t.getReceiverState());
				RegionsGetResp regionResp = getRegion(regionGet);//client.execute(regionGet, RegionsGetResp.class);//getRegion(regionGet);
				Regions region = regionResp.getRegions();
				if(region!=null){
					o.setProvinc_code(region.getRegion_id()); //收货省编码    收货人的所在省份  
					//o.setProvince_id(region.getRegion_id());
				}
			}
			
			o.setReceiver_mobile(t.getReceiverMobile());
			o.setReceiver_name(t.getReceiverName());
			//o.setRecommended_code(recommended_code);//推荐人编码    传空
			//o.setRecommended_name(recommended_name)//推荐人编码    传空
			//o.setRecommended_phone(recommended_phone) ////推荐人编码    传空
			/**
			 * 创建交易时的物流方式（交易完成前，物流方式有可能改变，但系统里的这个字段一直不变）。
			 * 可选值：free(卖家包邮),post(平邮),express(快递),ems(EMS),virtual(虚拟发货)，25(次日必达)，26(预约配送)。
			 */
			o.setSending_type(t.getShippingType());//配送方式
			
			/**
			 * 交易状态。可选值: * TRADE_NO_CREATE_PAY(没有创建支付宝交易) * WAIT_BUYER_PAY(等待买家付款) * 
			 * SELLER_CONSIGNED_PART(卖家部分发货) * WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款) * 
			 * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货) * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) * 
			 * TRADE_FINISHED(交易成功) * TRADE_CLOSED(付款以后用户退款成功，交易自动关闭) * TRADE_CLOSED_BY_TAOBAO(付款以前，卖家或买家主动关闭交易)
			 */
			o.setStatus(t.getStatus());//处理状态
			//o.setTid(tid);  //店宝订单编号 城要生成
			if(t.getCreated()!=null)
				o.setTid_time(DF.format(t.getCreated())); //订货时间  淘宝下单时间
			//o.setTwo_agents_id(two_agents_id); //二级代理商id	--传空
			//o.setVouchers_code(vouchers_code);//代金券编号	传空
			//o.setVouchers_money(vouchers_money);//代金券面值 传空
			//o.setWm_isreservation_from(wm_isreservation_from)//是否预约单	0否1是 --传空
			//o.setWm_order_from(wm_order_from);//网盟订单来源	--传空 
			//o.setWt_paipai_order_id(wt_paipai_order_id);//拍拍网厅的订单号	--传空
			//o.setGoods_num(t.getNum()==null?0:t.getNum().intValue());
			
			o.setOrder_from(order_from);
			
			if(wt!=null){
				if(!StringUtils.isEmpty(wt.getPhoneCityCode())){
					o.setOrder_city_code(wt.getPhoneCityCode());
				}
				if(!StringUtils.isEmpty(wt.getPhoneProvinceCode())){
					o.setOrder_provinc_code(wt.getPhoneProvinceCode());
				}
			}
			if(!StringUtils.isEmpty(o.getService_remarks())){
				String [] ss = o.getService_remarks().replace("#", "").replace("\r\n", "").replace("\r", "").replace("\n", "").split(",");
				String phoneNo = null;
				for(String s:ss){
					if(s!=null){
						String [] s2 = s.trim().split("=");
						if(s2!=null && s2.length==2){
							if("地市".equals(s2[0])){
								String city = s2[1];
								if(!StringUtils.isEmpty(city)){
									if(city.indexOf("市")==-1)city += "市";
									regionGet.setLocal_name(city);
									RegionsGetResp regionResp = getRegion(regionGet);//client.execute(regionGet, RegionsGetResp.class);//getRegion(regionGet);
									Regions region = regionResp.getRegions();
									if(region!=null){
										if(StringUtils.isEmpty(o.getOrder_city_code()))
											o.setOrder_city_code(region.getRegion_id());
										if(StringUtils.isEmpty(o.getOrder_provinc_code()))
											o.setOrder_provinc_code(region.getP_region_id()); //收货省编码    收货人的所在省份  
									}
								}
								o.setReserve0(city);
							}else if("发票".equals(s2[0])){
								o.setInvoice_print_type(s2[1]); //发票打印方式 --如果淘宝没有就传空
								//o.setInvoice_title(invoice_title) //发票抬头	--如果淘宝没有就传空
							}else if("证件地址".equals(s2[0])){
								o.setReserve2(s2[1]);
							}else if("合约类型".equals(s2[0])){
								o.setReserve1(s2[1]);
							}else if("资费方式".equals(s2[0])){
								o.setReserve4(s2[1]);
							}else if("资料上传".equals(s2[0])){
								//o.setReserve5(s2[1]);
								o.setFile_return_type(s2[1]);//资料回收方式 --传空
							}else if("号码".equals(s2[0])){
								phoneNo = s2[1];
							}
						}
					}
				}
				if(StringUtils.isEmpty(o.getOrder_city_code()) && !StringUtils.isEmpty(phoneNo)){
					//按号码查询归属地
					try{
						//NoInfoByNoReq numReq=new NoInfoByNoReq();
						//numReq.setNo(phoneNo);
						//NoInfoByNoResp nunResp = numberService.queryNoInfoByNo(numReq);
						Map map = outterStdTmplManager.getPhoneInfo(phoneNo);
						//Map map = nunResp.getNoMap();
						if(map!=null){
							Object regionid=map.get("region_id");
							if(regionid!=null)o.setOrder_city_code(regionid.toString());
						}
					}catch(Exception ex){
						
					}
				}
			}/*else if(!StringUtils.isEmpty(t.getBuyerArea()) && t.getBuyerArea().length()>=4){
				
				//数据格式  广东广州联通
				/*String pv = t.getBuyerArea().substring(0,2)+"省";
				String cv = t.getBuyerArea().substring(2,4)+"市";
				regionGet.setLocal_name(pv);
				RegionsGetResp regionResp = getRegion(regionGet);//client.execute(regionGet, RegionsGetResp.class);//getRegion(regionGet);
				Regions region = regionResp.getRegions();
				if(region!=null){
					*//**
					 * buyer_area 中文 需要转换   浙江省杭州市
					 *//*
					o.setOrder_provinc_code(region.getRegion_id()); //收货省编码    收货人的所在省份  
				}
				regionGet.setLocal_name(cv);
				regionResp = getRegion(regionGet);//client.execute(regionGet, RegionsGetResp.class);//getRegion(regionGet);
				region = regionResp.getRegions();
				if(region!=null){
					//订单归属地市编码	国际标准，详见省市区位码字典   
					o.setOrder_city_code(region.getRegion_id()); //收货省编码    收货人的所在省份  
				}
				o.setReserve0(t.getBuyerArea());
				
			}*/
			//取收货人地址作为号码归属地
			if(StringUtils.isEmpty(o.getOrder_city_code()))
				o.setOrder_city_code(o.getCity_code());
			if(StringUtils.isEmpty(o.getOrder_provinc_code()))
				o.setOrder_provinc_code(o.getProvinc_code());
			//订包订单项
			List<Order> os = t.getOrders();
			if(os!=null && os.size()>0){
				List<OuterItem> items = new ArrayList<OuterItem>();
				StringBuffer goodsName = new StringBuffer();
				for(Order i:os){
					//商品名称
					goodsName.append(i.getTitle()).append("# #d#");
					OuterItem toi = packageTaobaoOrderItem(t,i,wt,order_from,tradeInfo,package_id,sku);
					//如果没有子单就不做同步操作
					if(toi==null)return null;
					items.add(toi);
					o.setTid_category(toi.getTid_category());
//					logger.info("================================="+toi.getPro_type());
					if("20003".equals(toi.getType_id())&&!StringUtil.equals("69050100",toi.getPro_type())){
						o.setOrder_city_code("445300");
//						logger.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+toi.getPro_type());
					}
					
					//获取商品参与活动的活动编码
					/*//应收
					double orderAmount = Double.parseDouble(o.getOrder_totalfee());
					//实收
					double payMoney = Double.parseDouble(o.getOrder_realfee());
					//直降金额
					String relieffee = MallUtils.parseMoneyToLi(orderAmount - payMoney);*/
//					String relieffee = "";
//					Pattern pattern = Pattern.compile("直降[0-9]+元");
//					Matcher mat = pattern.matcher(goodsName.toString());
//					if (mat.find()) {
//						try {
//							relieffee = MallUtils.parseMoneyToLi(Double.parseDouble(goodsName.toString().split("直降")[1].split("元")[0]));
//						} catch (Exception e) {
//						}
//					}
					//获取商品对应的活动编码
					String sql = "select c.goods_id, c.pmt_id, d.pmta_id, d.pmt_type, d.pmt_solution, " +
							" e.name,e.pmt_code, e.begin_time, e.end_time, e.status_date, f.org_id from es_promotion d," +
							" es_promotion_activity e, es_pmt_goods c, es_activity_co f where " +
							" c.goods_id = '"+toi.getGoods_id()+"' and c.pmt_id = d.pmt_id" +
							" and d.pmta_id = e.id and e.id = f.activity_id and f.org_id = '10001' and f.status='1' " +
							" and (e.region = '"+province_region+"' or instr(e.region,'"+o.getOrder_city_code()+"')>0) and " +
							" to_date('"+o.getTid_time()+"','yyyy-mm-dd hh24:mi:ss') between " +
							" e.begin_time and e.end_time and f.source_from = '"+ManagerUtils.getSourceFrom()+"' and f.source_from = d.source_from " +
							" and f.source_from = e.source_from and f.source_from = c.source_from " +
							" and d.pmt_type in ('006','011') and e.enable=1 and e.user_type in (1,2) order by e.status_date";
					SDBUtils dbUtils = SpringContextHolder.getBean("sdbUtils");
					List activityList = dbUtils.queryListBySql(sql);
					if(null != activityList && activityList.size() > 0){
						StringBuffer strBuffer = new StringBuffer();
						for (int j = 0; j < activityList.size(); j++) {
							Map m = (Map)activityList.get(j);
							strBuffer.append(m.get("pmt_code").toString()).append(",");
						}
						String str = strBuffer.substring(0, strBuffer.lastIndexOf(","));
						o.setReserve9(str);
						
						logger.info("淘宝订单:"+o.getOut_tid()+"匹配到的活动有:"+str);
						
//						com.ztesoft.net.outter.inf.util.ZhuanDuiBaoUtil.save(o.getOut_tid(), str);
						o.getExtMap().put("proactivity", str);
						
						/*if(str.length() > 128){
							o.setReserve9(str.substring(0,128));
						}else{
							o.setReserve9(str);
						}*/
					}
					
					//增加扩展属性（没有1张订单对应多个商品，支持1对1） UR: 185009
					o.getExtMap().putAll(toi.getExtMap());
				}
				o.setItemList(items);
				extMap.put("GoodsName", goodsName.toString());
			}
			//o.setInvoice_title("测试发票台头"); // 测试发票台头
			
			//新增默认值=============mochunrun========2014-7-23=============================
			if(StringUtils.isEmpty(o.getOrder_city_code())){
				o.setOrder_city_code(cacheUtil.getConfigInfo("TAOBAO_CITY_DEFAULT"));//归属地市：Order_city_code 订单归属属地有可能为空没有的默认为广州（440100）
			}
			o.setDevelopment_code("-1");//发展人编码：development_code 淘宝获取不到，写死为 -1
			o.setDevelopment_name("-1");//发展人名称：development_name 淘宝获取不到，写死为 -1
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
				o.setInvoice_title_desc(t.getBuyerNick());//发票抬头：invoice_title  淘宝获取不到。没有填”昵称”
			}
			o.setReserve8(o.getInvoice_title_desc());//发票抬头：
			o.setPlat_type(c_order_from);//1、订单来源系统属性：source_from_system 内容填写编码（select a.* from es_dc_public_dict_relation a where a.stype='istborder';） Plat_type
			o.setC_order_from(c_order_from);// 淘宝   改为编码，会填到outer_accept表的 order_from字段    2、订单来源属性：order_source_from  内容填写编码（select a.* from es_dc_public_dict_relation a where a.stype='istborder';） Order_from
			o.setReserve2("13");//渠道类型：channel_mark （0：淘宝网厅是自营 1、淘宝分销是代理商）    Reserve2
			o.setReserve1("0");//是否2G、3G升4G：Is_to4g 淘宝获取不到. 填否(0)
			
			o.setType("1"); //订单类型      "正常订单" 对应 "1订购 "  淘宝获取不到，默认类型可填写为订购 (1)   字典(1订购 2退订 3变更 4预约 5预售 6换货) Type
			extMap.put("n_shipping_amount", StringUtils.isEmpty(t.getYfxFee())?"0":t.getYfxFee());// 6、实收运费：n_shipping_amount （对应淘宝的字段为yfx_fee）没有运费填写0     extendMap.n_shipping_amount
			if("25".equals(t.getShippingType())){
				extMap.put("shipping_quick", "02");//7、是否闪电送：shipping_quick  根据淘宝shipping_type来判断如果为(25为次日必达是闪电送填定02 是) 如果不是默认01 否
			}else {
				extMap.put("shipping_quick", "01");
			}
			extMap.put("shipping_time", "NOLIMIT");// 8、配送时间：shipping_time 淘宝获取不到，默认填写（NOLIMIT 不限时送）
			extMap.put("uid", "510000"); //9、买家标识：uid 淘宝获取不到，默认填(510000)
			extMap.put("invoice_group_content", "MX");//10、发票内容:Invoice_group_content 淘宝获取不到, 没有填明细 (MX)  extendMap.invoice_group_content
			extMap.put("is_deal", "0");//是否已办理完成：is_deal 淘宝获取不到 填否(0)  
			extMap.put("order_heavy", "0");// 订单重量
			extMap.put("is_change", "0");//是否变更套餐
			extMap.put("package_sale", "0");//套包销售标记
			extMap.put("is_examine_card", "1");
			
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
			OuterError ng = new OuterError("", "", "", "", order_from, t.getTid()+"", "sysdate","error");
			outterStdTmplManager.insertOuterError(ng);
			return null;
		}
	}

}
