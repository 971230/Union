var queueOrder={
		init:function(){
			var me = this;
			$("#checkAlls").bind("click",function(){
				var flag = $(this).is(':checked');
				$("input[type=checkbox][name=orderidArray]").each(function(){
						$(this).attr('checked',flag);
				});
			});
			$(".gridbody").removeClass("gridbody").addClass("grid_w_div");
			$("#order_list_fm div table").addClass("grid_w").attr("width","100%").attr("border","0").attr("cellspacing","0").attr("cellpadding","0");
			$("#order_list_fm .page").wrap("<form class=\"grid\"></form>");
			
			$("a[name=single_reback]").bind("click",function(){		
				var order_id = $(this).attr("order_id");
				rebakQueueOrder(order_id);	
			});
			
			$("#batch_reback").bind("click",function(){		
				var ids = "";
				$("input[name=orderidArray]:checked").each(function(){
					ids += $(this).val()+","
				});
				rebakQueueOrder(ids);	
			});
		}
}

function qry(){
	$.Loading.show("正在加载所需内容，请稍侯...");
	$("#orderListForm").attr("action",ctx+"/shop/admin/queueCardMateManagerAction!toQueueOrderList.do").submit();
}


function rebakQueueOrder(orderId){
	if(orderId==""){
		alert("请至少选择一个订单！");
		return;
	}
	var url = "shop/admin/queueCardMateManagerAction!tobackQueueOrder.do?order_id="+orderId;
	abrirCajaVentana("rebackDiv",url);
}