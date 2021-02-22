$(function() {
	
	$("#luru").bind("click", function() {
		var order_id = $("#order_id").val();
		var reissue_num = $("#reissue_num").val();
		var reissue_info = $("#reissue_info").val();
		var is_his_order =$("#order_is_his").val();
		if(!isNaN(reissue_num)){

			$.ajax({
				type : "post",
				async : false,
				url : "ordAuto!reissue_goods_add.do?ajax=yes&order_id="+order_id,
				data : {
					"reissue_info" : reissue_info,
					"reissue_num" : reissue_num,
	                 "is_his_order" :is_his_order
				},
				dataType : "json",
				success : function(data) {
					var tab = $("#reissue_tr");
					for ( var i = 0; i < data.length; i++) {
						var reissue = data[i];
						var is_tr_load = $("#tr" + reissue.item_id).attr("is_tr_load");
						if (is_tr_load != "yes") {
							var num="";
							if(reissue.num!=undefined){num=reissue.num} 
							var body = "<tr is_tr_load='yes' id='tr"
									+ reissue.item_id
									+ "'>"
									+ "<input type='hidden' id='reissue_id' value='"
									+ reissue.item_id
									+ "'/>"
									+ "<td><input attr_checkbox='attr_checkbox' "
									+ "attr_id='"+reissue.item_id+"' "
									+ "type='checkbox' id='check_" +
									+ reissue.item_id
									+ "'/></td>"
									+ "<td><a href='javascript:void(0);' id='a_"
									+ reissue.item_id
									+ "'"
									+ " attr_sc='"
									+ reissue.item_id
									+ "'>删除</a></td>"
									+ "<td>待发货</td>"
									+ "<td>"
									+ reissue.name
									+ "</td>" 
									
									+ "<td>"
									+ num
									+ "</td>" 
									+ "<td>"
									reissue.delivery_id
									+ "</td>" 
									+ "</tr>";
							tab.after(body);
							$("#a_" + reissue.item_id).bind("click", function() {
								var $this = $(this);
								var item_id = $this.attr("attr_sc");
								$.ajax({
									type : "post",
									async : false,
									url : "ordAuto!reissue_goods_del.do?ajax=yes&reissue_id="+item_id,
									data : {},
									dataType : "json",
									success : function(data) {
										if (data.result == "0") {
											alert(data.message);
											var tr = $this.parent().parent();
											tr.remove();
											$("#zengpinneirong").css("display", "none");
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
		
		}else{
			alert("请输入数字!");
		}
		
	});
	
	$("a[attr_del=attr_del]").bind("click", function(e) {
		var $this = $(this);
		var item_id = $this.attr("attr_sc");
		var is_his_order =$("#order_is_his").val();
		$.ajax({
			type : "post",
			async : false,
			url : "ordAuto!reissue_goods_del.do?ajax=yes&reissue_id="+item_id,
			data : { "is_his_order" :is_his_order},
			dataType : "json",
			success : function(data) {
				if (data.result == "0") {
					alert(data.message);
					var tr = $this.parent().parent();
					tr.remove();
					$("#zengpinneirong").css("display", "none");
				} else {
					alert(data.message);
				}
			}
		});
	});
	
	
	$("#quxiao").bind("click", function() {
		$("#reissue_info").val("");
		$("#reissue_num").val("");
		$("#zengpinneirong").css("display", "none");
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
	
	$("#logi_company").bind("change", function() {
		
		var logi_post_id = $("#logi_company").val();
		if(logi_post_id == "ZT0002" || logi_post_id == "ZY0002" || logi_post_id == "ZY0001") {
			$.ajax({
				type : "post",
				async : false,
				url : "ordAuto!logi_no.do?ajax=yes",
				data : {},
				dataType : "json",
				success : function(data) {
					if (data != null) {
						$("input[field_name=logi_no]").val(data);
						$("#logi_no").val(data);
					}
				}
			});
		} else {
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
					$("#protect_price").val(data[0].protect_fee);
					$("#logi_receiver_phone").val(data[0].tel_num);
					$("#logi_receiver").val(data[0].linkman);
				} else {
					$("input[field_name=n_shipping_amount]").val("0");
					$("#shishoujine").val("0");
					$("#protect_price").val("0");
					$("#logi_receiver_phone").val("");
					$("#logi_receiver").val("");
				}
			}
		});
	});
	

	
	$("#zhengdanchongfadayin").bind("click", function() {
		$("input[attr_checkbox=attr_checkbox]").each(function(i, e){
			var cb = $(e);
			cb.attr("checked", true);
		});
		printAll('2');
	});
	
	
	
	
});


function savePrintInfo(post_type) {
	if(savePrintCheckData (post_type)){
		var is_his_order =$("#order_is_his").val();
		$("#logi_no").val($("input[field_name=logi_no]").val());
		if(post_type==1){//补寄
			var deliveri_item_idArray = "";
			var itNum=0;   
			$("input[attr_checkbox=attr_checkbox]").each(function(i, e){
				if (e.checked) {
					var item_id = $(e).attr("attr_id");
					if (itNum == 0)
						deliveri_item_idArray += item_id;
					else
						deliveri_item_idArray += ","+item_id;
					++itNum;
				}
			});
			$("#deliveri_item_idArray").val(deliveri_item_idArray);
			if(deliveri_item_idArray!=''){
				url = ctx+ "/shop/admin/orderSupplyAction!order_supply_savePrint.do?ajax=yes&post_type=1&is_his_order="+is_his_order;
				Cmp.ajaxSubmit('supplyForm', '', url, {}, function(responseText) {
					alert(responseText.message);
					if (responseText.result == '0') {
						window.location.reload();
						//显示按钮
						//$("#bujidayin").show();
						//$("#zhengdanchongfadayin").show();
						
					}
				}, 'json');
			}else{
				alert("请选择补寄物品！");
			}
		}else{
			$("input[attr_checkbox=attr_checkbox]").each(function(i, e){
				 //var delivery_id = $(e).attr("title");
				});
			url = ctx+ "/shop/admin/orderSupplyAction!order_supply_savePrint.do?ajax=yes&post_type=2&is_his_order="+is_his_order;//重发
			Cmp.ajaxSubmit('supplyForm', '', url, {}, function(responseText) {
				alert(responseText.message);
				if (responseText.result == '0') {
					window.location.reload();
				}
			}, 'json');
		}
		
	}
};

//保存打印校验
function savePrintCheckData (post_type) {
	var logi_company = $("#logi_company").val();
	var logi_no = $("input[field_name=logi_no]").val(); 

	if (logi_company=='') {
		alert("请先选择物流公司！");
		return false;
	}else 	if (logi_no=='') {
		$("input[field_name=logi_no]").focus();
			alert("请先输入物流单号！");
			return false;
		} 
 
	if(post_type==1){
		var flag=false;   
		$("input[attr_checkbox=attr_checkbox]").each(function(i, e){
			if (e.checked) {
				flag=true;  
			}
		});
		if(!flag){
			alert("请选择补寄品");
			return false;
		}
	}
	return true;
}

