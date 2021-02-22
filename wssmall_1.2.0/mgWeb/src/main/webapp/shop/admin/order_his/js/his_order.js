function clearUlClass(){
	$("#order_header_tag li").removeClass("selected");
}
function showOrderOption(){
	$("#order_list_main").hide();
	$("#order_detail_div").show();
}

$(function(){
	
	$("[name=order_status_sp]").live("click",JXOrder.orderFlowLog);
	
	$("#op_order_flow_btn").bind("click",function(){
		showOrderOption();
		$("#show_click_10").trigger("click");
	});
	
	//关闭详细打开列表
	$("#order_detail_close").bind("click",function(){
		$("#order_list_main").show();
		$("#order_detail_div").hide();
	});
	
	//===========弹出隐藏更多菜单===============
	$("#show_more_menu").hover(function(){
		$("#more_menu").show();
    },function(){
    	$("#more_menu").hide();
    });
	$("#more_menu").hover(function(){
        $(this).show();
    },function(){
        $(this).hide();
    });
	//===========弹出隐藏更多菜单===============
	
	//===========订单状态===============
	$("#order_ststus_select").hover(function(){
		$("#order_ststus_div").show();
    },function(){
    	$("#order_ststus_div").hide();
    });
	$("#order_ststus_div").hover(function(){
        $(this).show();
    },function(){
        $(this).hide();
    });
	$("#order_ststus_div a").bind("click",function(){
		var action = $(this).attr("action_code");
		$("#order_ststus_select").attr("action_code",action);
		$("#order_ststus_select").html($(this).html());
		$("#order_ststus_div").hide();
	});
	//===========订单状态===============
	
	//显示隐藏订间详细
	$("#order_detail_ev").bind("click",function(){
		var obj = $("#order_detail_div");
		if(obj.hasClass("close")){
			obj.removeClass("close");
		}else{
			obj.addClass("close");
		}
	});
	
	//按状态查询订单事件
	$("li[name=list_order_btn]").bind("click",JXOrder.search);
	$("a[name=list_order_btn]").bind("click",JXOrder.search);
	
	//高级搜索弹出
	$("#search_dialog_show").bind("click",function(){
		$("#search_dialog").show();
	});
	//高级搜索关闭
	$("#search_dialog_close_btn").bind("click",function(){
		$("#search_dialog").hide();
	});
	
	//显示商品基本信息
	$("#order_list_dv tbody tr").live("click",function(){
		try{
			if (event.stopPropagation) { 
				event.stopPropagation(); 
			}else if (window.event) { 
				window.event.cancelBubble = true; 
			}
		}catch(e){
			
		}
		$(this).closest("tr").find("input[type=radio]").attr("checked","checked");
		var orderRadio = $("#order_list_dv tbody input[type=radio]:checked");
		var order_id=orderRadio.attr("order_id");
		var apply_id=orderRadio.attr("apply_id");
		var order_status = orderRadio.attr("order_status");
		var query_type="order";
		if(apply_id)query_type="apply";
		if(!order_id)return;
		var url = "ordehis!queryHisOrderInfo.do?ajax=yes&order_id="+order_id+"&query_type="+query_type+"&apply_id="+apply_id;
		$("#right_order_info_div").load(url,function(){
			if("order"==query_type){
				if(6==order_status || 7==order_status){
					$("#returned_btn").show();
					$("#refund_btn").show();
				}else{
					$("#returned_btn").hide();
					$("#refund_btn").hide();
				}
			}else if("apply"==query_type){
				
			}
			$("a[name=deal_order_evn]").unbind("click").bind("click",JXOrder.orderBase);
			$(".op_order").unbind("click").bind("click",OpOrder.orderEvent);
			$("#returned_apply_btn,#refund_apply_btn,#returned_edit_btn").unbind("click").bind("click",function(){
				var service_type=$(this).attr("service_type");
				var action = $(this).attr("action");
				var orderRadio = $("#order_list_dv tbody input[type=radio]:checked");
				var order_id=orderRadio.attr("order_id");
				if(!service_type)service_type=orderRadio.attr("service_type");
				if(!order_id){
					alert("请选择一个订单");
					return ;
				}
				var url = "applyn!showReturnedDialog.do?ajax=yes&service_type="+service_type+"&order_id="+order_id;
				if("edit"==action){
					var applyid=orderRadio.attr("apply_id");
					url += "&action=edit&apply_id="+applyid;
				}
				$("#orderApply_dialog").load(url,function(responseText){
					Eop.Dialog.open("orderApply_dialog");
					$("#order-find-btn").bind("click",OrderApply.selectOrder);
					$("#goods-find-btn").bind("click",OrderApply.selectGoods);
					$("#addApplyOrderBaseBtn").bind("click",OrderApply.addApplySub);
					$("#addApplyFrom").validate();
				});
			});
			$("#returned_audit_btn").unbind("click").bind("click",function(){
				var orderRadio = $("#order_list_dv tbody input[type=radio]:checked");
				var applyid = orderRadio.attr("apply_id");
				var service_type=orderRadio.attr("service_type");
				if(!applyid){
					alert("请选择一个申请单");
					return ;
				}
				var url = "applyn!showReturnedDialog.do?ajax=yes&service_type="+service_type;
				url += "&action=audit&apply_id="+applyid;
				$("#auditOrderApplyDlg").load(url,function(responseText){
					Eop.Dialog.open("auditOrderApplyDlg");
					$("#addApplyOrderBaseBtn").bind("click",OrderApply.addApplySub);
				});
			});
		});
		/*var ship_name=orderRadio.attr("ship_name");
		var ship_addr=orderRadio.attr("ship_addr");
		var pay_status=orderRadio.attr("pay_status");
		var ship_status=orderRadio.attr("ship_status");
		var order_amount=orderRadio.attr("order_amount");
		var ship_mobile=orderRadio.attr("ship_mobile");
		
		$("#d_order_id").html(order_id);
		$("#d_ship_name").html(ship_name);
		$("#d_ship_addr").html(ship_addr);
		$("#d_pay_status").html(pay_status);
		$("#d_ship_status").html(ship_status);
		$("#d_order_amount").html(order_amount+"元");
		$("#d_ship_mobile").html(ship_mobile);*/
	});
	
	$("#show_click_1,#show_click_2,#show_click_3,#show_click_4,#show_click_8,#show_click_6,#show_click_9,#show_click_7,#show_click_5,#show_click_10").bind("click",JXOrder.orderBase);
	
	$("#orderName,#username").bind("focus",function(){
		var obj = $(this);
		var obj_id=obj.attr("id");
		var val = obj.val();
		if(obj_id=='orderName'){
			if(val=='请输入商品名称'){
				obj.val("");
			}
		}else if(val=='请输入订单编号'){
			obj.val("");
		}
	});
	$("#orderName,#username").bind("blur",function(){
		var obj = $(this);
		var obj_id=obj.attr("id");
		var val = obj.val();
		if(obj_id=='orderName'){
			if(!val){
				obj.val("请输入商品名称");
			}
		}else if(!val){
			obj.val("请输入订单编号");
		}
	});
	
	//展示与关闭详细信息tags
	$("a[name=show_close_btn]").live("click",function(){
		var obj = $(this);
		var tags_id = obj.attr("tag_id");
		var className = obj.attr("class");
		var tabsObj = $("#order_tag_"+tags_id);
		if("closeArrow"==className){
			obj.removeClass("closeArrow");
			obj.addClass("openArrow");
			tabsObj.hide();
		}else{
			obj.removeClass("openArrow");
			obj.addClass("closeArrow");
			tabsObj.show();
		}
	});
	
	//查看商品详细
	$("#goods_items_tb tbody tr").live("click",JXOrder.goodsInfo);
	
});

var JXOrder = {
	search:function(){
		var obj = $(this);
		var search = obj.attr("search_flag");
		if("yes"==search){
			
			//var url = "ordergj!listOrder.do?ajax=yes&action=all";
			/*var options = {
				type : "post",
				url : url,
				dataType : "html",
				success : function(result) {
					$("#order_list_dv").empty().html(result);
				},
				error : function(e,b) {
					
				}
			}
			$("#search_form_common").ajaxSubmit(options);*/
			var formJson = $(this).parents("form").serialize();
			formJson = encodeURI(formJson);
			//alert(formJson);
			var params = {};
			/*var formJsonA = $(this).parents("form").serializeArray();
			$.each(formJsonA,function(idx,item){
				params[item.name]=item.value;
			});*/
			var action = "all"
			var common_search = $(this).attr("common_search");
			if("yes"==common_search)action=$("#order_ststus_select").attr("action_code");
			var url = "ordergj!listOrder.do?ajax=yes&action="+action+"&"+formJson;
			var his = $(this).attr("his");
			if(his && "yes"==his)url = "ordehis!listOrderHis.do?ajax=yes&action="+action+"&"+formJson;
			$("#order_list_dv").load(url,params,function(responseText){});
			if($("#search_dialog").is(":visible")){
				$("#search_dialog").hide();
			}
		}else{
			var action="all";
			if(obj.attr("extend_attr"))
				action = obj.attr("extend_attr");
			var url = "ordergj!listOrder.do?ajax=yes&action="+action;
			$("#order_list_dv").load(url,function(responseText){});
			$("#search_btn_ul").find("li").removeClass("curr");
			obj.closest("li").addClass("curr");
		}
		if(!$("#order_list_main").is(":visible")){
			$("#order_list_main").show();
			$("#order_detail_div").hide();
		}
		$("#order_btn_div").show();
		$("#order_apply_btn_div").hide();
	},
	orderBase:function(){
		var orderRadio = $("#order_list_dv tbody input[type=radio]:checked");
		var order_id=orderRadio.attr("order_id");
		if(!order_id){
			alert("请选择一个订单。");
			return;
		}
		var obj = $(this);
		var ev_type=obj.attr("ev_type");
		var url = "ordehis!hisOrderBase.do";
		var objid = "";
		if(ev_type && ev_type=="tag"){
			//url处理
			objid = obj.attr("id");
			if("show_click_2"==objid){
				url="ordehis!hisOrderItems.do";
			}else if("show_click_3"==objid){
				url="ordehis!hisPaymentLog.do";
			}else if("show_click_4"==objid){
				url="ordehis!hisShippingLog.do";
			}else if("show_click_5"==objid){
				url="ordergj!listOrderPromotion.do";
			}else if("show_click_6"==objid){
				url="ordergj!orderLog.do";
			}else if("show_click_7"==objid){
				$("#member_remark textarea").html(orderRadio.attr("remark"));
				var remarkDiv =  $("#member_remark");
				$("#order_info_dv").empty().append(remarkDiv.html());
				clearUlClass();
				obj.addClass("selected");
				return ;
			}else if("show_click_8"==objid){
				url = "orderOp!getOrderExp.do";
			}else if("show_click_9"==objid){
				url = "orderOp!getOrderCom.do";
			}else if("show_click_10"==objid){
				url = "ordergj!orderFlowLog.do";
			}
		}
		url+="?ajax=yes&order_id="+order_id;
		$.ajax({
			url:url,
			dataType:"html",
			success:function(data){
				$("#order_info_dv").empty().append(data);
				clearUlClass();
				if(ev_type && ev_type=="tag"){
					obj.addClass("selected");
				}else{
					$("#show_click_1").addClass("selected");
					//显示隐藏操作窗口
					showOrderOption();
				}
				if("show_click_4"==objid){
					$("#send_goods_body_list tr").bind("click",function(){
						$(this).find("input[type=radio]").attr("checked","checked");
						JXOrder.getDeliveryInfo("send");
					});
					$("#returned_goods_body_list tr").bind("click",function(){
						$(this).find("input[type=radio]").attr("checked","checked");
						JXOrder.getDeliveryInfo("returned");
					});
					$("#exchange_goods_body_list tr").bind("click",function(){
						$(this).find("input[type=radio]").attr("checked","checked");
						JXOrder.getDeliveryInfo("exchange");
					});
					JXOrder.getDeliveryInfo("send");
					JXOrder.getDeliveryInfo("returned");
					JXOrder.getDeliveryInfo("exchange");
				}
				$("a[name=order_detail_close]").unbind("click").bind("click",function(){
					$("#order_list_main").show();
					$("#order_detail_div").hide();
				});
			},error:function(){
				alert("查询失败，请重试");
			}
		});
		//$("#order_info_dv").load(url,function(responseText){});
	},
	goodsInfo:function(){
		var obj = $(this);
		var radio = obj.find("input[name=goods_id_radio]");
		radio.attr("checked","checked");
		var goods_id = radio.attr("goods_id");
		
		var url = "ordergj!goodsInfo.do?ajax=yes&goods_id="+goods_id;
		$.ajax({
			url:url,
			dataType:"html",
			success:function(data){
				$("#order_goods_info_dv").empty().append(data);
			},error:function(){
				alert("查询失败，请重试");
			}
		});
	},orderFlowLog:function(){
		try{
			if (event.stopPropagation) { 
				event.stopPropagation(); 
			}else if (window.event) { 
				window.event.cancelBubble = true; 
			}
		}catch(e){
			
		}
		var orderRadio = $("#order_list_dv tbody input[type=radio]:checked");
		var order_id=orderRadio.attr("order_id");
		url = "ordergj!orderFlowLog.do?ajax=yes&order_id="+order_id;
		Eop.Dialog.open("order_flows_log_dlg");
		$("#order_flows_log_dlg").load(url,{},function(responseText){});
	},
	getDeliveryInfo:function(type){
		var check_delivery=$("#"+type+"_goods_body_list input[name="+type+"_radio]:checked");
		var delivery_id=check_delivery.attr("delivery_id");
		if(!delivery_id){
			$("#"+type+"_goods_tb").empty();
			return ;
		}
		var logi_id=check_delivery.attr("logi_id");
		var logi_no=check_delivery.attr("logi_no");
		var url = "ordergj!deliveryInfo.do?ajax=yes&delivery_id="+delivery_id+"&logi_id="+logi_id+"&logi_no="+logi_no;
		$.ajax({
			url:url,
			dataType:"html",
			success:function(data){
				$("#"+type+"_goods_tb").empty().append(data);
				$("a[name=delivery_info_add_btn]").unbind("click").bind("click",function(){
					var delivery_id = $(this).attr("delivery_id");
					var showAddUrl = "ordergj!showDeliveryInfoAdd.do?ajax=yes&delivery_id="+delivery_id;
					Eop.Dialog.open("order_delivery_info_add_dialog");
					$("#order_delivery_info_add_dialog").load(showAddUrl,function(responseText){
						$("#delivery_info_submit_btn").unbind("click");
						$("#delivery_info_submit_btn").bind("click",function(){
							var name = $("#flowloginame").val();
							var des = $("#flowdescription").val();
							if(!name){
								alert("请填写物流公司名称");
								return ;
							}
							if(!des){
								alert("请填写描述");
								return ;
							}
							var submit_url =basePath+"flown!addFlow.do?ajax=yes";
							Cmp.ajaxSubmit('order_flow_form_add','',submit_url,{},function(rs){
								alert(rs.message);
								if(rs.result==1){
									Eop.Dialog.close("order_delivery_info_add_dialog");
									$("#show_click_4").trigger("click");
									//JXOrder.getDeliveryInfo(type);
								}
							},'json');
						});
					});
				});
			},error:function(){
				alert("查询失败，请重试");
			}
		});
	}
};