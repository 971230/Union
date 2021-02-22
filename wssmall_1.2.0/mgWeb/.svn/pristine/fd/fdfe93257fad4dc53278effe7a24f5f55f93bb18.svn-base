var AccNbrList = {
	init:function(){
		
		if (begin_nbr != "") {
			$("#accnbr_query_form [attr='begin_nbr']").val(begin_nbr);
		}
		
		
		if (end_nbr != ""){
			$("#accnbr_query_form [attr='end_nbr']").val(end_nbr);
		}
			
		
		
		$("#accnbr_query_form [attr_code='DC_ACC_TYPE']").unbind("change").bind("change",function(){
			var acc_type =$(this).val();
			if (acc_type == "0"){
				acc_type = "";
			} 
			
			var b_acc_type =acc_type+"00000000000";
			b_acc_type = b_acc_type.substring(0,11);
			$("#accnbr_query_form [attr='begin_nbr']").val(b_acc_type);
			
			var e_acc_type =acc_type+"99999999999";
			e_acc_type = e_acc_type.substring(0,11);
			$("#accnbr_query_form [attr='end_nbr']").val(e_acc_type);
		})
	}
}
$(function() {
	AccNbrList.init();
});