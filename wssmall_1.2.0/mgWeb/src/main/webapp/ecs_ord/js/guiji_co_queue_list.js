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
		$("#moveDataBtn").click(function(){
			me.moveData();
		})
		$("#testRule").click(function(){
			me.testRule();
		})
		$("#moveOrderDataBtn").click(function(){
			me.moveOrderData();
		})
		$("#cacheOrderDataBtn").click(function(){
			me.cacheOrderData();
		})
		$("#openOrderSynDialog").click(function(){
			var url = ctx+"/shop/admin/orderGuijiAction!listOrderSynService.do?ajax=yes";
			Eop.Dialog.open("order_syn_dialog");
			$("#order_syn_service_list").html("loading...");
			$("#order_syn_service_list").load(url,function(){});
		})
		
	},
	cacheOrderData : function(){
		var me = this;
		var out_tid = $("#ext_order_id").val();
		var tb_name= $("select[name=params.tb_name] option[selected]").val(); 
		if(!out_tid && !tb_name){
			alert("请输入队列ID或外部订单号");
			return ;
		}
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/orderGuijiAction!cacheGoodsData.do?ajax=yes&params.tb_name="+tb_name+"&params.out_tid="+out_tid,
			dataType:"json",
			success:function(result){
				alert(result.message);
			},
			error :function(res){alert("异步调用失败:" + res);}
		});
	}, 
	moveOrderData : function(){
		var me = this;
		var out_tid = $("#ext_order_id").val();
		var tb_name= $("#tb_name").val(); 
		var service_code = $("#service_code").val(); 
		if(!out_tid){
			alert("请输入队列ID或外部订单号");
			return ;
		}
		$.ajax({
			url : app_path+"/shop/admin/orderGuijiAction!moveOrderAndQueue.do?ajax=yes&params.ext_order_id="+out_tid+"&params.tb_name="+tb_name+"&params.service_code="+service_code,
			type : "get",
			dataType : 'json',
			success : function(result) {	
				alert(result.message);
			},
			error : function(res) {
				alert("出现错误 ，请重试"+res);
			}
		});
	},
	testRule : function(){ 
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/orderGuijiAction!testRule.do?ajax=yes",
			data:"params.co_id=123" ,
			dataType:"json",
			success:function(result){
				alert("完成");
			},
			error :function(res){alert("异步调用失败:" + res);}
		});
	},
	moveData : function(){
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/orderGuijiAction!moveData.do?ajax=yes",
			dataType:"json",
			success:function(result){
				alert(result.message);
				if(result.result=='0'){
					window.location="orderGuijiAction!searchOrderGjCoQueue.do";
				}
			},
			error :function(res){alert("异步调用失败:" + res);}
		});
	},
	searchGuijiQueue : function(){
		var me = this;
		$("#searchOrderGjCoQueueForm").attr("action","orderGuijiAction!searchOrderGjCoQueue.do");
		$("#searchOrderGjCoQueueForm").submit();
	},
	doGuiji :  function(){
		var me = this;
		var co_id = $("input[name='co_id']:checked").val();
		var out_tid = $("input[name='co_id']:checked").attr("outid");
		var tb_name= $("#tb_name").val(); 
		if(!co_id){
		  alert("请选择归集队列");
		  return ;
		}
		$.ajax({
			url : app_path+"/shop/admin/orderGuijiAction!doGuiji.do?ajax=yes&params.co_id="+co_id+"&params.out_tid="+out_tid,
			type : "get",
			dataType : 'json',
			success : function(result) {	
				alert(result.message);
			},
			error : function(res) {
				alert("出现错误 ，请重试");
			}
		});
	}
}

$(function(){
	OrderGuiji.init();
})