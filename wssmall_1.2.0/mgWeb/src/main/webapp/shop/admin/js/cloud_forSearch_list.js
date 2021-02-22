var CloudList = {
	cloud_tansfer:function(){ //云卡调拨
//		
	},
	jsonBack:function(responseText){ //json回调函数
		if(responseText.result==1){
			alert(responseText.message);
			Eop.Dialog.close("order_w_dialog");
			window.location.reload();
		}
		if(responseText.result==0){
			alert(responseText.message);
		}
	},
	init:function(){
		
		var hidden_lan_id = document.getElementById("hidden_lan_id");
		var hidden_b_acc_type = document.getElementById("hidden_b_acc_type");
		var hidden_e_acc_type = document.getElementById("hidden_e_acc_type");
		var hidden_acc_type = document.getElementById("hidden_acc_type");
		
		//init操作
		if (hidden_lan_id.value != '') {
			$("#cloud_forSearch_query_form [name='lan_id']").val(hidden_lan_id.value); // 记录选中发布区域
		}
		if (hidden_acc_type.value != '') {
			$("#cloud_forSearch_query_form [attr_code='DC_ACC_TYPE']").val(hidden_acc_type.value); // 记录选中号码段
		}
		
//		$("#cloud_forSearch_query_form [attr='begin_nbr']").each(function(i){
			if (begin_nbr != "") 
				$("#cloud_forSearch_query_form [attr='begin_nbr']").val(begin_nbr);
			else {
				if (hidden_b_acc_type.value != "") {
					$("#cloud_forSearch_query_form [attr='begin_nbr']").val(hidden_b_acc_type.value); //记录选中号码段开始号码
				}
			}
//		})
//		
//		$("#cloud_forSearch_query_form [attr='end_nbr']").each(function(i){
			if (end_nbr != "") 
				$("#cloud_forSearch_query_form [attr='end_nbr']").val(end_nbr);
			else {
				if (hidden_e_acc_type.value != "") {
					$("#cloud_forSearch_query_form [attr='end_nbr']").val(hidden_e_acc_type.value);  //记录选中号码段结束号码
				}
			}
//			
//		})
		
		$("#cloud_forSearch_query_form [name='lan_id']").unbind("change").bind("change",function(){
			hidden_lan_id.value =  $(this).val();
		});
		
		$("#cloud_forSearch_query_form [attr_code='DC_ACC_TYPE']").unbind("change").bind("change",function(){
			var acc_type =$(this).val();
			hidden_acc_type.value = acc_type;
			
			if (acc_type == "0"){
				acc_type = "";
			} 
			
			var b_acc_type =acc_type+"00000000000";
			b_acc_type = b_acc_type.substring(0,11);
			hidden_b_acc_type.value = b_acc_type;
			$("#cloud_forSearch_query_form [attr='begin_nbr']").val(b_acc_type);
			
			var e_acc_type =acc_type+"99999999999";
			e_acc_type = e_acc_type.substring(0,11);
			hidden_e_acc_type.value =  e_acc_type;
			$("#cloud_forSearch_query_form [attr='end_nbr']").val(e_acc_type);
		})
	}
}
$(function() {
	CloudList.init();
});