function checkData() {
	/*var checkbox_val="[";
	$("input:checkbox[name=CheckboxGroup]:checked").each(function(i){
	   if (i == 0) {
		   checkbox_val += "{code:\""+$(this).attr("attr_code")+"\",name:\""+$(this).attr("attr_name")+"\",file_path:\"\"}";
	   } else {
		   checkbox_val += ",{code:\""+$(this).attr("attr_code")+"\",name:\""+$(this).attr("attr_name")+"\",file_path:\"\"}";
	   }
	});
	checkbox_val+="]";
	
	$("#material_retrieve").val(checkbox_val);
	var is_upload = $("select[field_name=is_upload]").val();
	var file_return_type = $("select[field_name=file_return_type]").val();
	$("#is_upload").val(is_upload);
	$("#file_return_type").val(file_return_type);*/
	$("input[name='q_check']").val("no");
	var net_certi=$('input:radio[name="ordReceipt.net_certi"]:checked').val();
    if(net_certi==null){
        alert("入网证件没选中!");
        return false;
    }
    var accept_agree=$('input:radio[name="ordReceipt.accept_agree"]:checked').val();
    if(accept_agree==null){
        alert("受理协议没选中!");
        return false;
    }
    
    var liang_agree=$('input:radio[name="ordReceipt.liang_agree"]:checked').val();
    if(liang_agree==null){
        alert("靓号协议没选中!");
        return false;
    }
    
    var archive_num=$('input[name="ordReceipt.archive_num"]').val();
    if(archive_num==null||archive_num==''){
        alert("存档编号不能为空!");
        return false;
    }
	
	return true;
}
function dealCallback2(e) {
	alert(e.message);
	//window.location.href=ctx+"/shop/admin/ordAuto!ord_receipt.do?order_id="+$("#order_id").val();
}
