package com.ztesoft.net.outter.inf.taobao;



public class TestOrderData {
	
	public static void main(String[] args) {
		//List<OuterTmplAttr> list = JSON.parseArray(str, OuterTmplAttr.class);
		//System.out.println("===================");
		/*String json = "{aa:1,bb:'222'}";
		Map map = JSON.parseObject(json, Map.class);
		System.out.println(map.get("aa"));*/
		//String reg = "3|4|5|6|7";
		//System.out.println("34".matches(reg));
		String ss [] = "111|bbb".split("\\|");
		System.out.println(ss);
	}
	
	/*public static class A{
		private String a;
		private String b;
		public String getA() {
			return a;
		}
		public void setA(String a) {
			this.a = a;
		}
		public String getB() {
			return b;
		}
		public void setB(String b) {
			this.b = b;
		}
		
	}
	
	public static TaobaoOrderItem packageTaobaoOrderItem(){
		//num_iid	 Number	 否	2342344	商品数字ID
		//item_meal_id	 Number	 是	2564854632	套餐ID
		TaobaoOrderItem o = new TaobaoOrderItem();
		*//**
		 * 规格数所
		 *//*
		o.setAct_protper("12"); //合约期限  12月、18月、24月、36月、48月
		//o.setAdjustment_credit(adjustment_credit)//调整信用度  是根据BSS套餐对应的档次乘以1.5得到的数值（BSS套餐对应待确认） 没有
		*//**
		 * 规格数所
		 *//*
		o.setAtive_type("ggg"); //字典：5购机送费、4存费送机、3存费送费 --合约类型
		//o.setBank_account(bank_account);//EDB回访补录的，实在没有就空
		//o.setBank_code(bank_code); //EDB回访补录的，实在没有就空
		//o.setBank_user(bank_user); //EDB回访补录的，实在没有就空
		*//**
		 * 规格数所
		 *//*
		o.setBrand_name("gaaa"); //品牌
		*//**
		 * 规格数所
		 *//*
		o.setBrand_number("123"); //品牌编码
		o.setCert_card_num("123"); //证件号码  --空（回访时），增加字段
		o.setCert_type("1");//证件类型
		o.setCollection("0") ;//是否托收 字典：0否，1是 --EDB回访补录的，实在没有就空
		*//**
		 * 规格数所
		 *//*
		o.setColor_code("red");//颜色编码
		o.setColor_name("red");
		o.setDisfee_select("ffff");//优惠选项	详见优惠选项字典 --空
		//o.setFamliy_num(famliy_num);//多号码以,号分隔  --空
		o.setFirst_payment("13");//详见首月资费方式字典--EDB回访补录的，实在没有就空
		*//**
		 * 规格数所
		 *//*
		o.setIs_iphone_plan("0");//是否iphone套餐	字典：0否，1是   没有
		*//**
		 * 规格数所
		 *//*
		o.setIs_liang("0");//是否靓号	字典：0否，1是 --待确认
		*//**
		 * 规格数所
		 *//*
		o.setIs_loves_phone("0"); //是否情侣号码	字典：0否，1是 --默认0
		*//**
		 * 规格数所
		 *//*
		o.setIs_stock_phone("0");//是否库存机	字典：0否，1是 --终端属性
		*//**
		 * 规格数所
		 *//*
		o.setIsgifts("0");//是否赠品	字典：0否，1是
		o.setLiang_code("12312312");//靓号单编号	--待确认，老的是人工对应
		*//**
		 * 规格数所
		 *//*
		o.setLiang_price(1000d);//靓号金额	单位元 --待确认
		//o.setLoves_phone_num(loves_phone_num)//情侣号码	空
		*//**
		 * 规格数所
		 *//*
		o.setModel_code("123123123");//机型编码	　---如果要送总部开户，就要对应总部的编码 如果不送总部，就为空。待局方确认
		*//**
		 * 规格数所
		 *//*
		o.setModel_name("ggfgfgfg");//机型名称
		*//**
		 * 货品编码
		 *//*
		o.setOut_package_id("123123");//合约编码
		o.setOut_plan_id_bss("4234324");//BSS套餐编码	BSS套餐编码 --待局方确认
		o.setOut_plan_id_ess("9324i024");//总部套餐编码	总部套餐编码 ---如果要送总部开户，就要对应总部的编码 如果不送总部，就为空。待局方确认
		*//**
		 * 规格数所
		 *//*
		//o.setPhone_deposit(phone_deposit);//活动预存款	单位元
		//o.setPhone_num(phone_num);//手机号码	开户手机号码 --增加字段
		//o.setPhone_owner_name(phone_owner_name);/机主姓名
		*//**
		 * 规格数所
		 *//*
		o.setPlan_title("ksfjlksjf");//套餐名称	　商品名称
		o.setPro_detail_code("1");
		o.setPro_name("mk-测试商品");//产品名称	　商品名称
		o.setPro_num(10);
		o.setPro_origfee(123123d);
		*//**
		 * 规格数所
		 *//*
		o.setPro_type("123");//产品类型
		o.setProduct_net("2G");//产品网别	　2G、3G --填空
		//o.setReliefpres_flag(reliefpres_flag);//减免预存标记	淘宝没有就空。
		o.setSell_price(1111d);//商品单价
		//o.setSimid(simid);//SIM卡号	　空
		//o.setSociety_name(society_name);//社会代理商名称	--空
		//o.setSociety_price(society_price);//代理商终端结算价格	单位元 --空
		*//**
		 * 规格数所
		 *//*
		o.setSpecificatio_nname("oeoeo");//型号名称
		*//**
		 * 规格数所
		 *//*
		//o.setSpecification_code(specification_code);//型号编码	　---如果要送总部开户，就要对应总部的编码 如果不送总部，就为空。待局方确认
		//o.setSuperiors_bankcode(superiors_bankcode);//上级银行编码	--EDB回访补录的，实在没有就空
		//o.setTerminal_num(terminal_num);//终端串号	　空
		//o.setWhite_cart_type(white_cart_type);//卡类型	大卡、小卡、NANO卡 --根据客服备注解析，实在没有就空
		
		return o;
	}

	public static TaobaoOrder packageTaobaoOrder(){
		TaobaoOrder o = new TaobaoOrder();
		o.setAbnormal_status("qqqqqqqqqqqqq");//订单出现异常问题的时候，给予用户的描述,没有异常的时候，此值为空
		o.setAddress("ddddddd");
		o.setGoods_num(1);
		o.setAlipay_id("111111111111");
		o.setArea_code("2222222");//收货区编码 是中文的需要转为编码
		//o.setBss_order_type(bss_order_type); //BSS产品类型	--传空
		o.setBuyer_id("vvvvvvv");//买家昵称
		if(t.getHasBuyerMessage()){
			o.setBuyer_message(buyer_message);//如果有买家留言
		}
		o.setBuyer_name("mkmkm");//买家姓名，没有所以填了收货人姓名
		//o.setCert_address(cert_address);//没有
		//o.setCert_failure_time(cert_failure_time);
		o.setCity("广州");//中文的
		o.setCity_code("111111");////收货城市编码 是中文的需要转为编码
		//o.setCollection_free(collection_free)//代收邮费	单位元 --传空
		//o.setCouponbatchid(couponbatchid)//代金券批次号	空
		//o.setCouponname(couponname)//代金券名称	空
		*//**
		 * 交易状态。可选值: * TRADE_NO_CREATE_PAY(没有创建支付宝交易) * 
		 * WAIT_BUYER_PAY(等待买家付款) * SELLER_CONSIGNED_PART(卖家部分发货) * 
		 * WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款) * 
		 * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货) * 
		 * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) * TRADE_FINISHED(交易成功) * 
		 * TRADE_CLOSED(付款以后用户退款成功，交易自动关闭) * 
		 * TRADE_CLOSED_BY_TAOBAO(付款以前，卖家或买家主动关闭交易)
		 *//*
		o.setDelivery_status("成功");//需要转中文
		//o.setDevelopment_code(development_code)//--待局方确认
		//o.setDevelopment_name(development_name)//--待局方确认
		//o.setDiscountid(discountid)//优惠活动编号	--传空
		//o.setDiscountname(discountname)//优惠活动名称	--传空
		//o.setDiscountrange(discountrange)//	可选	优惠幅度	--传空
		//o.setDiscountValue(discountValue)//优惠金额	--传空
		//o.setDisfee_type(disfee_type)//优惠类型 --传空
		o.setDistrict("ddddddd");
		//o.setFile_return_type(file_return_type)//资料回收方式 --传空
		o.setGet_time("2014-10-10 11:11:11");
		//o.setInvoice_print_type(invoice_print_type) //发票打印方式 --如果淘宝没有就传空
		//o.setInvoice_title(invoice_title) //发票抬头	--如果淘宝没有就传空
		o.setIs_adv_sale("0"); //是否预售 先付款后准备货发货、字典：0否，1是--默认否
		//o.setIs_bill(is_bill) //是否开发票 --如果淘宝没有就传空
		o.setMerge_status("合并"); //合并状态	合并状态包括：合并、手拆、自动拆分 --默认为合并，送中文。
		//o.setOne_agents_id(one_agents_id)//一级代理商id	--传空
		o.setOrder_channel("taobao");//
		*//**
		 * buyer_area 中文 需要转换   浙江省杭州市
		 *//*
		o.setOrder_city_code("111111"); //订单归属地市编码	国际标准，详见省市区位码字典   
		o.setOrder_disfee("200");//淘宝的优惠金额
		*//**
		 * buyer_area 中文 需要转换   浙江省杭州市
		 *//*
		o.setOrder_provinc_code("33333");//订单归属省编码	国际标准，详见省市区位码字典    buyer_area	 	买家下单的地区
		o.setOrder_realfee("1000d");//订单实收总价 实付金额。 t.getPayment()  商品金额t.getTotalFee()（商品价格乘以数量的总金额）
		o.setOrder_totalfee("200"); //订单总金额
		//o.setOrderacccode(orderacccode) //订单接入编码	--传空
		//o.setOrderacctype(orderacctype) //订单接入类型	--传空
		o.setOut_tid("23232313");//外部平台单号
		o.setPay_method("2"); //没有找到  --根据淘宝的支付方式来对应
		*//**
		 * 分阶段付款的订单状态（例如万人团订单等），目前有三返回状态 FRONT_NOPAID_FINAL_NOPAID(定金未付尾款未付)，
		 * FRONT_PAID_FINAL_NOPAID(定金已付尾款未付)，FRONT_PAID_FINAL_PAID(定金和尾款都付)
		 *//*
		o.setPay_status("成功"); //支付状态   要转中文
		o.setPay_time("2014-03-05 11:11:11"); //付款时间
		//o.setPaychannelid(paychannelid) //支付渠道编码	--传空
		//o.setPaychannelname(paychannelname) //支付渠道名称	--传空
		//o.setPaytype(paytype) //支付类型	--传空
		o.setPhone("1590000000000"); //收货电话 
		//o.setPlat_distributor_code(plat_distributor_code)//平台分销商编号
		o.setPlat_type("taobao"); //平台类型  默认淘宝
		o.setPlatform_status("1"); //外部平台状态   跟订单状态一样
		o.setPost("12313"); //收货人的邮编
		o.setPro_totalfee("2000.1"); //邮费。精确到2位小数;
		//o.setPromotion_area(promotion_area) //促销政策地 01:集团 02:省公司 03:地市公司 --传空
		*//**
		 *  中文  需要转换
		 *//*
		o.setProvinc_code("22222"); //收货省编码    收货人的所在省份  
		o.setProvince("gd");
		o.setReceiver_mobile("1590000000");
		o.setReceiver_name("kkkk");
		//o.setRecommended_code(recommended_code);//推荐人编码    传空
		//o.setRecommended_name(recommended_name)//推荐人编码    传空
		//o.setRecommended_phone(recommended_phone) ////推荐人编码    传空
		*//**
		 * 创建交易时的物流方式（交易完成前，物流方式有可能改变，但系统里的这个字段一直不变）。
		 * 可选值：free(卖家包邮),post(平邮),express(快递),ems(EMS),virtual(虚拟发货)，25(次日必达)，26(预约配送)。
		 *//*
		o.setSending_type("4");//配送方式 GSM
		//o.setService_remarks(service_remarks); //系统解析，待局方确认
		*//**
		 * 交易状态。可选值: * TRADE_NO_CREATE_PAY(没有创建支付宝交易) * WAIT_BUYER_PAY(等待买家付款) * 
		 * SELLER_CONSIGNED_PART(卖家部分发货) * WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款) * 
		 * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货) * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) * 
		 * TRADE_FINISHED(交易成功) * TRADE_CLOSED(付款以后用户退款成功，交易自动关闭) * TRADE_CLOSED_BY_TAOBAO(付款以前，卖家或买家主动关闭交易)
		 *//*
		o.setStatus("1");//处理状态
		//o.setTid(tid);  //店宝订单编号 城要生成
		o.setTid_time("2014-10-10 10:10:10"); //订货时间  淘宝下单时间
		//o.setTwo_agents_id(two_agents_id); //二级代理商id	--传空
		*//**
		 * 订单类型 包括：等货订单、囤货订单、换货订单、其他订单、预售订单、正常订单、中断订单 --填默认值（正常订单）
		 *//*
		o.setType("正常订单"); //订单类型
		//o.setVouchers_code(vouchers_code);//代金券编号	传空
		//o.setVouchers_money(vouchers_money);//代金券面值 传空
		//o.setWm_isreservation_from(wm_isreservation_from)//是否预约单	0否1是 --传空
		//o.setWm_order_from(wm_order_from);//网盟订单来源	--传空 
		//o.setWt_paipai_order_id(wt_paipai_order_id);//拍拍网厅的订单号	--传空
		
		List<TaobaoOrderItem> list = new ArrayList<TaobaoOrderItem>();
		list.add(packageTaobaoOrderItem());
		o.setOrderItemList(list);
		return o;
	}*/
	
}
