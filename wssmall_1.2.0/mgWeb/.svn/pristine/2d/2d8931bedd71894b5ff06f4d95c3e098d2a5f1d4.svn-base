var OrderGuiji = {
	init : function(){
		var me = this;
		Eop.Dialog.init({id:"order_syn_dialog",modal:true,title:"订单同步",width:"800px"});
		$("#searchQueueBtn").click(function(){
			me.searchGuijiQueue();
		})
		$("#doGuijiBtn").click(function(){
			me.doGuiji();
		})
		$("#openOrderSynDialog").click(function(){
			var url = ctx+"/shop/admin/orderGuijiAction!listOrderSynService.do?ajax=yes";
			Eop.Dialog.open("order_syn_dialog");
			$("#order_syn_service_list").html("loading...");
			$("#order_syn_service_list").load(url,function(){});
		})
		
	},
	searchGuijiQueue : function(){
		var me = this;
		$("#searchOrderGjCoQueueForm").submit();
	},
	doGuiji :  function(){
		var me = this;
		var co_id = $("input[name='co_id']:checked").val();
		if(!co_id){
			alert("请选择归集队列");
			return ;
		}
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/orderGuijiAction!doGuiji.do?ajax=yes",
			data:"params.co_id=" + co_id ,
			dataType:"json",
			success:function(result){
				alert(result.message);
				if(result.result=='0'){
					window.location="orderGuijiAction!searchOrderGjCoQueue.do";
				}
			},
			error :function(res){alert("异步调用失败:" + res);}
		});
	}
}

$(function(){
	OrderGuiji.init();
})