var AccNbrList = {
	accnbr_tansfer:function(){ //云卡调拨
		var len =$("[name='accnbr_checkbox']:checked").length;
		var accnbr_idArr = [];
//		if(len ==0)
//		{
//			alert("请选择调拨的号码");
//			return false;
//		}
		$("[name='accnbr_checkbox']:checked").each(function(){
			var accnbr_id = $(this).val();
			accnbr_idArr.push(accnbr_id);
		})
		var accnbr_idstr =accnbr_idArr.join(",");
		var self= this;
		var url =ctx+"/shop/admin/contract!transfer.do?ajax=yes&accnbr_ids="+accnbr_idstr+"&orderid="+$("#orderid").val()+"&from_page="+$("#from_page").val();
		Cmp.ajaxSubmit('accnbr_query_form','',url,{},self.jsonBack,'json');
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
		$("a[id='accnbr_tansfer_a']").bind("click",function(){
			AccNbrList.accnbr_tansfer();
		});
		
		//号码导入
		$("a[id='phoneNo_import_btn']").bind("click",function(){
			AccNbrList.importPhoneNo();
		});		
		Eop.Dialog.init({id:"importDialog",modal:true,title:"号码导入",width:"750px"});
	},
	
	importPhoneNo:function(){
		Eop.Dialog.open("importDialog");
		var url =ctx + "/shop/admin/contract!importAccNbr.do?ajax=yes";
		$("#importDialog").load(url,function(){
			
		});
	},
	
	returnBatch:function(batchId){
		$("input[name='accNbr.batch_id']").val(batchId);
	},
	
	afterImport:function(){
		Eop.Dialog.close();
		
		//批处理单号
		var batchId = $("input[name='accNbr.batch_id']").val();
		
		//后台同步订单，并展示同步结果		
		var url = ctx+ "/shop/admin/contract!listImport.do?ajax=yes&accNbr.batch_id=" + batchId;
		Cmp.ajaxSubmit('accnbr_list_form', 'accnbrList', url, {}, function(){
			
		},'html');
	}
	
}
$(function() {
	AccNbrList.init();
});