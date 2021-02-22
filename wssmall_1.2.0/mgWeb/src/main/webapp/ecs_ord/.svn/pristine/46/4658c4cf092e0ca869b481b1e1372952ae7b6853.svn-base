var queueManage={
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
			
			//单个操作
			$("a[name=single_close]").bind("click",function(){		
				var queueId = $(this).attr("queue_id");
				toQueueSwitch(queueId,"1")
			});
			
			$("a[name=single_open]").bind("click",function(){	
				var queueId = $(this).attr("queue_id");
				if(queueId==""){
					alert("请至少选择一个队列！");
					return;
				}
				openOrClose(queueId,"0","");
			});
			
			$("a[name=single_edit]").bind("click",function(){
				var queueId = $(this).attr("queue_id");		
				removeNew("");
				getOneQueue(queueId,$(this));
			});	
			
			$("a[name=single_reback]").bind("click",function(){	
				if(confirm("回退该队列里所有的订单，确定请点【确认】，否则请点【取消】")){
					var queue_no = $(this).attr("queue_no");
					var deal_reason="";
					$.ajax({
					   url: "shop/admin/queueCardMateManagerAction!rebackQueueManage.do?ajax=yes",
					   type: "POST",
					   dataType :"json",
					   data:"queueManagevo.queue_no="+queue_no+"&queueManagevo.deal_reason="+deal_reason,
					   beforeSend : function(){
						   $.Loading.show("操作中，请稍侯...");
					   },
					   success: function(data){
						   alert(data.message);
						   $("#query_order_s").click();
					   },
						error:function(XMLHttpRequest, textStatus, errorThrown) {
							alert(textStatus)
						},
					   complete: function(){
						   $.Loading.hide();
					   }	   
					 });
				}
			});

			$("#batch_open").bind("click",function(){
				var ids = "";
				$("input[name=orderidArray]:checked").each(function(){
					ids += $(this).val()+","
				});
				if(ids==""){
					alert("请至少选择一个队列！");
					return;
				}
				openOrClose(ids.substring(0,ids.length-1),"0","");	
			});
			
			$("#batch_close").bind("click",function(){
				var ids = "";
				$("input[name=orderidArray]:checked").each(function(){
					ids += $(this).val()+","
				});
//				openOrClose(ids.substring(0,ids.length-1),"0","");		
				toQueueSwitch(ids.substring(0,ids.length-1),"1")
			});
			
			$("#single_add").bind("click",function(){										
				getOneQueue("");
			});	
		}
}

function qry(){
	$.Loading.show("正在加载所需内容，请稍侯...");
	$("#orderListForm").attr("action",ctx+"/shop/admin/queueCardMateManagerAction!toQueueManageList.do").submit();
}


function saveSigle(){
	if($("#queue_switch_edit").val()=="1" && $("#deal_reason_edit").val()==""){
		alert("关闭必须录入原因！");
		return;
	}
	$.ajax({
	   url: "shop/admin/queueCardMateManagerAction!addOrUpdateQueueManage.do?ajax=yes",
	   type: "POST",
	   dataType :"json",
	   beforeSend : function(){
		   $.Loading.show("正在保存，请稍侯...");
	   },
	   data:$("#newForm").serialize() ,
	   success: function(data){
		   alert(data.message);
		   $("#query_order_s").click();
	   },
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus)
		},
	   complete: function(){
		   $.Loading.hide();
	   }	   
	 });
}

function getOneQueue(ids,obj){
	$.ajax({
		   url: "shop/admin/queueCardMateManagerAction!getQueueManageInfo.do?queueManagevo.id="+ids,
		   type: "POST",
		   dataType :"html",
		   beforeSend : function(){
			   $.Loading.show("正在加载数据，请稍侯...");
		   },
		   success: function(data){
			   $("#single_add").attr("disabled","disabled");	
			   $("a[name=single_edit]").attr("class","dobtndisabled");	
			   $("a[name=single_edit]").unbind("click");
			   if(ids==""){
					$("#order_list_fm table").after(data);				   
			   }else{
				   $(obj).parent().parent().before("<tr id='tr"+ids+"'><td colspan='7'>"+data+"</td></tr>")
				   $(obj).parent().parent().hide()
			   }
		   },
			error:function(XMLHttpRequest, textStatus, errorThrown) {
				alert(textStatus)
			},
		   complete: function(){
			   $.Loading.hide();
		   }	   
	 });
}

function openOrClose(queueId,flag,deal_reason){
	$.ajax({
	   url: "shop/admin/queueCardMateManagerAction!openOrCloseQueue.do?ajax=yes",
	   type: "POST",
	   dataType :"json",
	   data:"queueManagevo.id="+queueId+"&queueManagevo.queue_switch="+flag+"&queueManagevo.deal_reason="+deal_reason,
	   beforeSend : function(){
		   $.Loading.show("操作中，请稍侯...");
	   },
	   success: function(data){
		   alert(data.message);
		   $("#query_order_s").click();
	   },
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus)
		},
	   complete: function(){
		   $.Loading.hide();
	   }	   
	 });
}

function removeNew(ids){
	$("#single_add").removeAttr("disabled");	
	//alert(ids)
	if(ids==""){
		$("#newForm").remove();		
	}else{
		$("#tr"+ids).next().show();
		$("#tr"+ids).remove();	
	}
	$("a[name=single_edit]").attr("class","dobtn");	
	$("a[name=single_edit]").bind("click",function(){
		var queueId = $(this).attr("queue_id");		
		removeNew("");
		getOneQueue(queueId,$(this));
	});	
}

function toQueueSwitch(id,flag){
	if(id==""){
		alert("请至少选择一个队列！");
		return;
	}
	var url = "shop/admin/queueCardMateManagerAction!toQueueSwitch.do?queueManagevo.id="+id+"&queueManagevo.queue_switch="+flag;
	abrirCajaVentana("switchDiv",url);
}