function checkPageAttrIsNotNull() {
	var platform_status_name = $("input[field_name=platform_status_name]").val(); // 外部订单状态
	var order_city_code = $("input[field_name=order_city_code]").val(); // 归属区域
	var order_deal_type = $("input[field_name=order_deal_type]").val(); // 订单类型
	var paytype = $("input[field_name=paytype]").val(); // 支付类型
	var pay_method = $("input[field_name=pay_method]").val(); // 支付方式
	var pay_status = $("input[field_name=pay_status]").val(); // 支付状态
	var GoodsName = $("input[field_name=GoodsName]").val(); // 商品名称
	var goods_type = $("input[field_name=goods_type]").val(); // 商品类型
	var house_id = $("input[field_name=house_id]").val(); // 仓库名称
	var certi_type = $("input[field_name=certi_type]").val(); // 证件类型
	var cert_card_num = $("input[field_name=cert_card_num]").val(); // 证件号码
	var cert_address = $("input[field_name=cert_address]").val(); // 证件地址
	var phone_owner_name = $("input[field_name=phone_owner_name]").val(); // 客户名称
	var CustomerType = $("input[field_name=CustomerType]").val(); // 客户类型
	var cert_failure_time = $("input[field_name=cert_failure_time]").val(); // 证件有效期
	var terminal_num = $("input[field_name=terminal_num]").val(); // 串号
	var first_payment = $("input[field_name=first_payment]").val(); // 首月资费方式
	var phone_num = $("input[field_name=phone_num]").val(); // 用户号码
	var white_cart_type = $("input[field_name=white_cart_type]").val(); // 卡类型
	var net_region = $("input[field_name=net_region]").val(); // 入网地区
	var is_liang = $("input[field_name=is_liang]").val(); // 是否靓号
	var liang_price = $("input[field_name=liang_price]").val(); // 靓号预存
}

/**
 * 保存按钮
 * @returns {Boolean}
 */
function checkDataSave() {
	$("#prod_audit_status").val("1");
	var flag   = checkPrintData();
	if(!flag){
	   return false;
	}
	return true;
}

/**
 * 请发货按钮
 * @returns {Boolean}
 */
function checkData () {
	var checkboxlist = $("input[name=checkbox_quality]");
	for (i=0;i<checkboxlist.length;i++) {
		cb = checkboxlist[i];
		var checkboxVal = cb.checked;
		if (checkboxVal == false) {
			alert("请选中所有复选框已确保质检稽核通过！");
			return false;
		}
	}
	var flag   = checkPrintData();
	if(!flag){
	   return false;
	}
	$("#prod_audit_status").val("1");
	return true;
}
function checkPrintData () {
	var logi_company = $("#logi_company").val();
	var logi_no = $("input[field_name=logi_no]").val(); 
    var provinc_code = $("#provinc_code").val();
    var city_code = $("#city_code").val();
    var district_id = $("#district_id").val();
    var key_str0 = $("#logi_company option:selected").attr("key_str");
    var sending_type_id = $("input[name=sending_type_id]").val();
    var flag_send = false;
    
    if (sending_type_id=="EMS" || sending_type_id=="JJPS" || sending_type_id=="KD" 
    	|| sending_type_id=="MJBY" || sending_type_id=="PY" || sending_type_id=="SH" || sending_type_id=="XMPS"){
    	flag_send = true;
    }

    if (key_str0 != undefined && key_str0 != "") {
	    var key_str1 = "/"+key_str0+"/";
	    var key_str2 = eval(key_str1);
	    if (!key_str2.test(logi_no)) {
	    	alert("物流单号不正确！");
	    	return false;
	    }
    }
	if (logi_company=='') {
		alert("请先选择物流公司名称！");
		return false;
	}else if (logi_no=='') {
		$("input[field_name=logi_no]").focus();
		alert("请先输入物流单号！");
		return false;
	}else if((provinc_code==''||provinc_code==null) && flag_send){
		alert("请先选择收货省份");
		return false;
	}else if((city_code==""||city_code==null) && flag_send){
		alert("请先选择收货地市");
		return false;
	}else if((district_id ==""||district_id==null) && flag_send){
		alert("请先选择区县");
		return false;
	}

	return true;
}
function hotFreeCheck(){
	var logi_company = $("#logi_company").val();
    var provinc_code = $("#provinc_code").val();
    var city_code = $("#city_code").val();
	if (logi_company=='') {		
		alert("请先选择物流公司名称！");
		return false;
	}else if(provinc_code==''||provinc_code==null){
		alert("请先选择收货省份");
		return false;
	}else  if(city_code==""||city_code==null){
		alert("请先选择收货地市");
		return false;
	}else if(logi_company=="SF-FYZQYF"||logi_company=="SF0001"||logi_company=="SF0002"||logi_company=="SF0003"||logi_company=="SF-HS"){
		return true;
	}else{
		alert("热敏单打印功能只支持顺丰物流公司");
		return false;
	}
}
function saveBack(e) {
	alert(e.message);
}

$(function() {
	
	$(".grid_n_cont").each(function(){
		var errorMsgHtml = $(this).find("[class='remind_div']").children("span");
		var errorMsg = "";
	    $(this).find("[check_message]").each(function(){
	    	errorMsg += $(this).attr("check_message");
	    });
	    errorMsgHtml.html(errorMsg);
	});
	
	$("#xinzeng").bind("click", function() {
		var v = $("#zengpinneirong").css("display");
		if (v == "none") {
			$("#reissue_info").val("");
			$("#reissue_num").val("");
			$("#zengpinneirong").css("display", "block");
		} else if (v == "block") {
			$("#zengpinneirong").css("display", "none");
		}
	});
	
	$("a[attr_del=attr_del]").bind("click", function() {
		var $this = $(this);
		var item_id = $this.attr("attr_sc");
		var gift_inst_id = $this.attr("title");
		$.ajax({
			type : "post",
			async : false,
			url : "ordAuto!reissue_goods_del.do?ajax=yes&reissue_id="
					+ item_id,
			data : {},
			dataType : "json",
			success : function(data) {
				if (data.result == "0") {
					alert(data.message);
					var tr = $this.parent().parent();
					tr.remove();
					$("#zengpinneirong").css("display", "none");
					
					 $("#items_add_btn_"+gift_inst_id).show();
	                 $("#items_add_text_"+gift_inst_id).hide();
					
				} else {
					alert(data.message);
				}
			}
		});
	});
	
	$("#luru").bind("click", function() {
	var order_id = $("#order_id").val();
	var reissue_num = $("#reissue_num").val();
	var reissue_info = $("#reissue_info").val();
	
	if(isNaN(reissue_num)){
		alert("请输入数字！");return;
	}
	
	$.ajax({
			type : "post",
			async : false,
			url : "ordAuto!reissue_goods_add.do?ajax=yes&order_id=" + order_id,
			data : {
				"reissue_info" : reissue_info,
				"reissue_num" : reissue_num
			},
			dataType : "json",
			success : function(data) {
				var tab = $("#reissue_tr");
				for ( var i = 0; i < data.length; i++) {
					var reissue = data[i];
					var is_tr_load = $("#tr" + reissue.item_id).attr("is_tr_load");
					if (is_tr_load != "yes") {
						var body = "<tr is_tr_load='yes' id='tr"
								+ reissue.item_id
								+ "'>"
								+ "<input type='hidden' id='reissue_id' value='"
								+ reissue.item_id
								+ "'/>"
								+ "<td><a href='javascript:void(0);' id='a_"
								+ reissue.item_id
								+ "'"
								+ " attr_sc='"
								+ reissue.item_id
								+ "'>删除</a></td>"
								+ "<td>待发货</td>"
								+ "<td>"
								+ reissue.name
								+ "</td>" + "</tr>";
						tab.after(body);
						$("#a_" + reissue.item_id) .bind("click", function(e) {
							var $this = $(this);
							var item_id = $this.attr("attr_sc");
							$.ajax({
								type : "post",
								async : false,
								url : "ordAuto!reissue_goods_del.do?ajax=yes&reissue_id="
										+ item_id,
								data : {},
								dataType : "json",
								success : function(data) {
									if (data.result == "0") {
										alert(data.message);
										var tr = $this.parent().parent();
										tr.remove();
										$("#zengpinneirong").css("display", "none");
										$("#items_add_btn_"+gift_inst_id).show();
						                $("#items_add_text_"+gift_inst_id).hide();
									} else {
										alert(data.message);
									}
								}
							});
						});
					}
				}
			}
		});
	});
	$("#quxiao").bind("click", function() {
		$("#reissue_info").val("");
		$("#reissue_num").val("");
		$("#zengpinneirong").css("display", "none");
	});
	
	$("#provinc_code").bind("change", function() {
		var provinc_code = this	.value;
		$.ajax({
			type : "post",
			async : false,
			url : "ordAuto!changeProvinc.do?ajax=yes&provinc_code="+provinc_code,
			data : {},
			dataType : "json",
			success : function(data) {
				var data = eval(data);
				if (data != null && data.length > 0) {
					$("#city_code").empty();
					$.each(data, function(i, mp) {
						optionStr = "<option value='"+mp.code+"'>"+mp.name+"</option>";
						$("#city_code").append(optionStr);
					});
					$("#city_code").change();
				}
			}
		});
	});
	
	$("#city_code").bind("change", function() {
		var city_code = this.value;
		$.ajax({
			type : "post",
			async : false,
			url : "ordAuto!changeCity.do?ajax=yes&city_code="+city_code,
			data : {},
			dataType : "json",
			success : function(data) {
				var data = eval(data);
				if (data != null && data.length > 0) {
					$("#district_id").empty();
					$.each(data, function(i, mp) {
						optionStr = "<option value='"+mp.code+"'>"+mp.name+"</option>";
						$("#district_id").append(optionStr);
					});
				}
			}
		});
	});
	
	$("#logi_company").bind("change", function() {
		
		var logi_post_id = $("#logi_company").val();
		if(logi_post_id == "ZT0002" || logi_post_id == "ZY0002" || logi_post_id == "ZY0001") {
			$("tr[name='quhuorenxinxi']").css("display", "");
			$.ajax({
				type : "post",
				async : false,
				url : "ordAuto!logi_no.do?ajax=yes",
				data : {},
				dataType : "json",
				success : function(data) {
					if (data != null) {
						$("input[field_name=logi_no]").val(data);
					}
				}
			});
		} else {
			$("tr[name='quhuorenxinxi']").css("display", "none");
			$("input[field_name=logi_no]").val("");
		}
		$.ajax({
			type : "post",
			async : false,
			url : "ordAuto!logi_company_regions.do?ajax=yes&logi_post_id="+logi_post_id,
			data : {},
			dataType : "json",
			success : function(data) {
				if (data != null && data.length > 0) {
					data = eval(data);
					$("input[field_name=n_shipping_amount]").val(data[0].carray_fee);
					$("#shishoujine").val(data[0].carray_fee);
					$("#protect_fee").val(data[0].protect_fee);
					$("#logi_receiver_phone").val(data[0].tel_num);
					$("#logi_receiver").val(data[0].linkman);
				} else {
					$("input[field_name=n_shipping_amount]").val("0");
					$("#shishoujine").val("0");
					$("#protect_fee").val("0");
					$("#logi_receiver_phone").val("");
					$("#logi_receiver").val("");
				}
			}
		});
	});

	$(".items_add_btns").bind("click", function() {
		var gift_inst_id=$(this).attr("title");
		var order_id=$("#order_id").val();
		var goods_name=$("#gif_name_"+gift_inst_id).val();
	    var sku_num=$("#sku_num_"+gift_inst_id).val();
		$.ajax({
			type : "post",
			async : false,
			url : "orderPostPrintAction!addGifToItems.do?ajax=yes&order_id="+order_id,
			data : {
				"reissue_info" : goods_name,
				"reissue_num" : sku_num,
				"gift_inst_id" : gift_inst_id
			},
			dataType : "json",
			success : function(data) {
	            if(data.result==0){
	                 var itemsId=data.item_id;
	                 //隐藏展示按钮
	                 $("#items_add_btn_"+gift_inst_id).hide();
	                 $("#items_add_text_"+gift_inst_id).show();

//	                 var html = "<tr is_tr_load='yes' id='tr"
//	                	 	+ itemsId
//	                	 	+ "'>"
//	                	 	+ "<input type='hidden' id='reissue_id' value='"
//							+ itemsId
//							+ "'/>"
//	                 		+ "<td>" +
//	                 		  "<a href='javascript:void(0);' id='a_"
//	                 		+ itemsId+"' title='"+gift_inst_id+"' "
//	                 		+ " attr_sc='"+itemsId+"'>删除</a></td>"+
//						    + " <td>待补寄</td>"
//						    + " <td>"+goods_name+"</td></tr>";
	                 //添加展示
	                 var html = "<tr is_tr_load='yes' id='tr"
							+ itemsId
							+ "'>"
							+ "<input type='hidden' id='reissue_id' value='"
							+ itemsId
							+ "'/>"
							+ "<td><a href='javascript:void(0);' id='a_"
							+ itemsId
							+ "'"
							+ " attr_sc='"
							+ itemsId
							+ "'>删除</a></td>"
							+ "<td>待补寄</td>"
							+ "<td>"
							+ goods_name
							+ "</td>" + "</tr>";
	                 $("#reissue_tr").after(html);
	                 $("#a_" + itemsId) .bind("click", function(e) {
							var $this = $(this);
							var item_id = $this.attr("attr_sc");
							$.ajax({
								type : "post",
								async : false,
								url : "ordAuto!reissue_goods_del.do?ajax=yes&reissue_id="
										+ item_id,
								data : {},
								dataType : "json",
								success : function(data) {
									if (data.result == "0") {
										alert(data.message);
										var tr = $this.parent().parent();
										tr.remove();
										$("#zengpinneirong").css("display", "none");
										$("#items_add_btn_"+gift_inst_id).show();
						                $("#items_add_text_"+gift_inst_id).hide();
									} else {
										alert(data.message);
									}
								}
							});
						});
	            }else{
	           		alert("保存失败!");
	                }
			}
		});
	});
	
	
});