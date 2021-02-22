function clickPrintBtn(){
	
	var upUrl=ctx+"/shop/admin/ordPrt!saveInvoicePrintDetail.do?ajax=yes";
	
	var name = $("#name").val();
	var tel = $("#tel").val();
	var yingshou = $("#yingshou").val();
	var shishou = $("#shishou").val();
	var chuanma = $("#chuanma").val();
	var daxie = $("#daxie").val();
	var order_id = $("#order_id").val();
	var order_is_his = $("#order_is_his").val();
	var memo_info = $("#memo_info").val();
	var invoice_num = $("#invoice_num").val();
	var invoice_num_value = $.trim(invoice_num);
	if(invoice_num_value!=''){
		$("#invoice").hide();//发票号3个字隐藏
		$("#memo").hide();//备注信息4个字隐藏
		$("#invoice_num_print").after(invoice_num);
		$("#invoice_num").hide();
		$("#memo_info").after(memo_info);
		$("#memo_info").hide();
		$.ajax({
			type : "post",
			async : false,
			url : upUrl,
			data : {name:name, tel:tel, yingshou:yingshou, shishou:shishou, chuanma:chuanma, daxie:daxie, order_id:order_id,order_is_his :order_is_his,invoice_num : invoice_num,memo_info : memo_info},
			dataType : "json",
			success : function(data) {
				if(data.result != '0'){
					alert(data.message);
					return;
				}else{
					window.print();
					window.close();
				}
			}
		});
	}else{
		alert("请输入发票号!");
	}
	
	
}


	




