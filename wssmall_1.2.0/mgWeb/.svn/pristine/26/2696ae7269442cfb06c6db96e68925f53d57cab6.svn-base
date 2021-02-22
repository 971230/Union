var OrderOuter = {
	sysOrder:function(){ //云卡调拨
		var len =$("[name='orderouter_checkbox']:checked").length;
		var order_idArr = [];
		if(len ==0)
		{
			alert("请选择同步的订单");
			return false;
		}
		$("[name='orderouter_checkbox']:checked").each(function(){
			var order_id = $(this).val();
			order_idArr.push(order_id);
		})
		var order_idstr =order_idArr.join(",");
		var self= this;
		var url =ctx+"/shop/admin/orderouter!syorder.do?ajax=yes&orderIds="+order_idstr;
		Cmp.ajaxSubmit('query_form','',url,{},self.jsonBack,'json');
	},
	
	deleteOrder:function(){ //订单删除
		var len =$("[name='orderouter_checkbox']:checked").length;
		var order_idArr = [];
		if(len ==0)
		{
			alert("请选择需要删除的订单");
			return false;
		}
		$("[name='orderouter_checkbox']:checked").each(function(){
			var order_id = $(this).val();
			order_idArr.push(order_id);
		})
		var order_idstr =order_idArr.join(",");
		var self= this;
		var url =ctx+"/shop/admin/orderouter!deleteOrder.do?ajax=yes&orderIds="+order_idstr;
		Cmp.ajaxSubmit('query_form','',url,{},self.jsonBack,'json');
	},
	
	
	jsonBack:function(responseText){ //json回调函数
		if(responseText.result==1){
			alert(responseText.message);
			window.location.reload();
		}
		if(responseText.result==0){
			alert(responseText.message);
		}
	},
	importOrder:function(){		
		Eop.Dialog.open("importDialog");
		var url =ctx + "/shop/admin/orderouter!imported.do?ajax=yes";
		$("#importDialog").load(url,function(){
			
		});
	},
	returnBatch:function(batchId){
		$("input[name='ordParam.batch_id']").val(batchId);
	},
	afterImport:function(){
		Eop.Dialog.close();
		//批处理单号
		var batchId = $("input[name='ordParam.batch_id']").val();
		
//		var url = ctx+ "/shop/admin/orderouter!importList.do?ajax=yes&ordParam.batch_id=" + batchId;
//		Cmp.ajaxSubmit('orderOutForm', 'orderList', url, {}, function(){
//			
//		},'html');
		
		//后台同步订单，并展示同步结果		
		window.location.href = ctx+ "/shop/admin/orderouter!list.do?ordParam.batch_id=" + batchId ;
	},
	init:function(){
		$("a[id='order_sys_btn']").bind("click",function(){
			OrderOuter.sysOrder();
		});
		
		$("a[id='order_import_btn']").bind("click",function(){
			OrderOuter.importOrder();
		});		
		
		$("a[id='order_delete_btn']").bind("click",function(){
			OrderOuter.deleteOrder();
		});	
		Eop.Dialog.init({id:"importDialog",modal:true,title:"订单导入",width:"750px"});
	}
}
$(function() {
	OrderOuter.init();
});
