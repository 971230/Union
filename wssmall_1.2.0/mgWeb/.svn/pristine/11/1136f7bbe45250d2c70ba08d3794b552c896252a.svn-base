var CloudList = {
	cloud_tansfer:function(){ //云卡调拨
		//var len =$("[name='cloud_checkbox']:checked").length;
//		var cloud_idArr = [];
//		if(len ==0)
//		{
//			alert("请选择调拨的云卡");
//			return false;
//		}
//		$("[name='cloud_checkbox']:checked").each(function(){
//			var cloud_id = $(this).val();
//			cloud_idArr.push(cloud_id);
//		})
//		var cloud_idstr =cloud_idArr.join(",");
		var self= this;
		var url =ctx+"/shop/admin/cloud!transfer.do?ajax=yes&orderid="+$("#orderid").val()+"&from_page="+$("#from_page").val();
		Cmp.ajaxSubmit('cloud_query_form','',url,{},self.jsonBack,'json');
	},
	jsonBack:function(responseText){ //json回调函数
		if(responseText.result==1){
			alert(responseText.message);
			try{Eop.Dialog.close("order_w_dialog");}catch(e){}
			window.location.reload();
		}
		if(responseText.result==0){
			alert(responseText.message);
		}
	},
	init:function(){
		$("a[id='cloud_tansfer_a']").bind("click",function(){
			CloudList.cloud_tansfer();
		})
		
		//价格计算处理
		$("[name='cloud_checkbox']").bind("click",function(){
			var checked = $(this).attr("checked");
			var curr_val = new Number($("#all_card_money").text());
			var card_price = new Number($(this).attr("card_price"));
			if(checked)
				$("#all_card_money").text(curr_val+card_price);
			else
				$("#all_card_money").text(curr_val-card_price);
		})
		
		//熟卡导入
		$("a[id='cloudCard_import_btn']").bind("click",function(){
			CloudList.importCloud();
		});		
		Eop.Dialog.init({id:"importDialog",modal:true,title:"云卡导入",width:"750px"});
		
		$("#submitButton").bind("click",function(){
			var price = $("input[name='cloud.pay_money']").val();
		 	var reg = /^\d+$/;
			if(price != ""){
				if(price.indexOf(".") > -1){
					price = price.substring(0,price.indexOf("."));
				}
				if(!reg.test(price)){
					alert("金额请输入数字！");
					$("input[name='cloud.pay_money']").val("").focus();
					return false;
				}else{
					 document.forms["cloud_query_form"].submit(); 
				}
			}else{
				 document.forms["cloud_query_form"].submit(); 
			}
		});
		
	},
	
	importCloud:function(){
		Eop.Dialog.open("importDialog");
		var url =ctx + "/shop/admin/cloud!importCloud.do?ajax=yes";
		$("#importDialog").load(url,function(){
			
		});
	},
	returnBatch:function(batchId){
		$("input[name='cloud.batch_id']").val(batchId);
	},
	afterImport:function(){
		Eop.Dialog.close();
		
		//批处理单号
		var batchId = $("input[name='cloud.batch_id']").val();
		
//		var url = ctx+ "/shop/admin/cloud!listImport.do?ajax=yes&cloud.batch_id=" + batchId;
//		Cmp.ajaxSubmit('cloud_query_form', 'cloudList', url, {}, function(){
//			
//		},'html');
		//后台同步订单，并展示同步结果		
		window.location.href = ctx+ "/shop/admin/cloud!list.do?cloud.batch_id=" + batchId ;
	}
}
$(function() {
	CloudList.init();
});