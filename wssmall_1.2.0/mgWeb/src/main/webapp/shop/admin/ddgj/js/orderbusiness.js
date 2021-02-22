$(function(){
	//取消订单
	$("#order_cancel_btn").unbind("click").bind("click",function(){
		var order_id=$("#base_order_id").val();
		var url = "orderBusiness!showCancel.do?ajax=yes&order_id="+order_id;
		Eop.Dialog.open("order_cancel_dialog");
		$("#order_cancel_dialog").load(url,function(responseText){
			$("#order_cancel_btn_submit").unbind("click").bind("click",function(){
				var url = "ordcomment!cancelOrder.do?ajax=yes";
				Cmp.ajaxSubmit('order_cancel_form','',url,{},function (responseText){
					if(responseText.result==1){
						alert(responseText.message);
						Eop.Dialog.close("order_cancel_dialog");
						$("#show_click_1").trigger("click");
					}
					if(responseText.result==0){
						alert(responseText.message);
					}
				},'json');
			});
		});
	});
	
	$("#order_return_visit_btn_b").unbind("click").bind("click",JXOrder.showAddReturnVisit);
	
	//$("input[name=flow_user_name]").live("click",searchGroupAdmin);
	$("a[name=user_clear_btn]").live("click",function(){
		var admintr = $(this).closest("p");
		admintr.find("input[name=flow_user_id]").val("");
		admintr.find("input[name=flow_user_name]").val("");
	});
	$("a[name=user_add_btn]").live("click",function(){
		var p = '<p style="height:26px;"><input type="hidden"  name="flow_user_id" value="" /><input name="flow_user_name" type="text" class="formIpt" value="" readonly="readonly" />'+
    	'&nbsp;&nbsp;<a href="javascript:void(0);" name="user_rm_btn" class="dobtn" style="margin-right:5px;">-</a></p>';
		$(this).closest("td").find("span").append(p);
	});
	$("select[name=flow_group_id]").live("change",function(){
		$("input[name=flow_user_id]").val("");
		$("input[name=flow_user_name]").val("");
	});
	$("a[name=user_rm_btn]").live("click",function(){
		$(this).closest("p").remove();
	});
	//抢单
	$("[name='BUTTON_ORDER_ROB']").unbind("click").bind("click",JXOrder.robOrder);
	//订单分派
	$("[name='BUTTON_ORDER_DISPATCH']").unbind("click").bind("click",JXOrder.orderDispatch);
	
	//订单开户
	$("[name='BUTTON_ORDER_OPENACCOUNT']").unbind("click").bind("click",function(){
		var order_id=$("#base_order_id").val();
		var url = "orderBusiness!showOpenAccountDialog.do?ajax=yes&order_id="+order_id;
		Eop.Dialog.open("order_openaccount_dialog");
		$("#order_openaccount_dialog").load(url,function(){
			$("#order_openaccount_btn_submit").unbind("click").bind("click",function(){
				var sub_url ="orderBusiness!openAccount.do?ajax=yes";
				Cmp.ajaxSubmit('order_openaccount_form','',sub_url,{},function (responseText){
					if(responseText.result==1){
						alert(responseText.message);
						Eop.Dialog.close("order_openaccount_dialog");
						$("#show_click_1").trigger("click");
						getOrderCounts("all");
					}
					if(responseText.result==0){
						alert(responseText.message);
					}
				},'json');
			});
		});
	});
	
	//订单确认
	$("[name='BUTTON_ORDER_CONFIRM']").unbind("click").bind("click",function(){
		var order_id=$("#base_order_id").val();
		var url = "orderBusiness!showConfirmDialog.do?ajax=yes&order_id="+order_id;
		Eop.Dialog.open("order_confirm_dialog");
		$("#order_confirm_dialog").load(url,function(){
			
			$("#order_confirm_btn_submit").unbind("click").bind("click",function(){
				var url ="orderBusiness!qyOrder.do?ajax=yes";
				Cmp.ajaxSubmit('order_confirm_form','',url,{},function (responseText){
					if(responseText.result==1){
						alert(responseText.message);
						Eop.Dialog.close("order_confirm_dialog");
						$("#show_click_1").trigger("click");
						getOrderCounts("all");
					}
					if(responseText.result==0){
						alert(responseText.message);
					}
				},'json');
			});
		});
	});
	
	//订单备货
	$("[name='BUTTON_CUST_ACCEPT_C']").unbind("click").bind("click",function(){
		var order_id=$("#base_order_id").val();
		var url = "orderBusiness!showBHDialog.do?ajax=yes&order_id="+order_id;
		Eop.Dialog.open("order_goods_bh_dialog");
		$("#order_goods_bh_dialog").load(url,function(){
			
			$("#order_bh_btn_submit").unbind("click").bind("click",function(){
				var url ="orderBusiness!stokingOrder.do?ajax=yes";
				Cmp.ajaxSubmit('order_bh_form','',url,{},function (responseText){
					if(responseText.result==1){
						alert(responseText.message);
						Eop.Dialog.close("order_goods_bh_dialog");
						$("#show_click_1").trigger("click");
						getOrderCounts("all");
					}
					if(responseText.result==0){
						alert(responseText.message);
					}
				},'json');
			});
		});
	});
	
	//发货处理
	$("[name='BUTTON_SHIPPING_C']").unbind("click").bind("click",function(){
		var order_id=$("#base_order_id").val();
		var url = "orderBusiness!showShippingDialog.do?ajax=yes&order_id="+order_id;
		Eop.Dialog.open("order_goods_shipping_dialog");
		$("#order_goods_shipping_dialog").load(url,function(){
			
			//$("#ship_user_name").unbind("click").bind("click",searchAdmin);
			
			$("#order_fh_btn_submit").unbind("click").bind("click",function(){
				var url_confirm ="orderBusiness!shipOrder.do?ajax=yes";
				var logiid = $("select[name='logi_id_name']").val().split("\|")[0];
				if(logiid=='0'){
					if(!$("input[name='flow.logi_name']").val()){
						alert("请填写物流公司名称。");
						return ;
					}
					if(!$("#delivery_flow_desc").val()){
						alert("请填写物流描述。");
						return ;
					}
				}
				
				Cmp.ajaxSubmit('order_fh_form','',url_confirm,{},function (responseText){
					if(responseText.result==1){
						alert(responseText.message);
						Eop.Dialog.close("order_goods_shipping_dialog");
						$("#show_click_1").trigger("click");
						getOrderCounts("all");
					}
					if(responseText.result==0){
						alert(responseText.message);
					}
				},'json');
			});
		});
	});
	
	
	//收货待确认处理
	$("[name='BUTTON_GET_SHIPPING_C']").unbind("click").bind("click",function(){
		var order_id=$("#base_order_id").val();
		var url = "orderBusiness!showOrderReceive.do?ajax=yes&order_id="+order_id;
		Eop.Dialog.open("order_goods_receive_dialog");
		$("#order_goods_receive_dialog").load(url,function(){
			$("#order_sh_btn_submit").unbind("click").bind("click",function(){
				var url_receive = "orderBusiness!received.do?ajax=yes";
				Cmp.ajaxSubmit('order_sh_form','',url_receive,{},function (responseText){
					if(responseText.result==1){
						alert(responseText.message);
						Eop.Dialog.close("order_goods_receive_dialog");
						$("#show_click_1").trigger("click");
						getOrderCounts("all");
					}
					if(responseText.result==0){
						alert(responseText.message);
					}
				},'json');
			});
		});
	});
	
	//完成订单
	$("[name='BUTTON_FINISHED_C']").unbind("click").bind("click",function(){
		var order_id=$("#base_order_id").val();
		var url = "orderBusiness!showFinish.do?ajax=yes&order_id="+order_id;
		Eop.Dialog.open("order_goods_finish_dialog");
		$("#order_goods_finish_dialog").load(url,function(){
			$("#order_finish_btn_submit").unbind("click").bind("click",function(){
				var url_finish = "orderBusiness!finish.do?ajax=yes";
				Cmp.ajaxSubmit('order_finish_form','',url_finish,{},function (responseText){
					if(responseText.result==1){
						alert(responseText.message);
						Eop.Dialog.close("order_goods_finish_dialog");
						$("#show_click_1").trigger("click");
						getOrderCounts("all");
					}
					if(responseText.result==0){
						alert(responseText.message);
					}
				},'json');
			});
		});
	});
	
	//退货
	$("[name='BUTTON_RETURNED_SHIPPING_C']").unbind("click").bind("click",function(){
		var order_id=$("#base_order_id").val();
		var url = "orderBusiness!showReturnDialog.do?ajax=yes&order_id="+order_id;
		//允许退货
		
		Eop.Dialog.open("order_goods_returned_dialog");
		$("#order_goods_returned_dialog").load(url,function(){
			$("#allowReturnBtn_c").unbind("click").bind("click",function(){
				returnedc('allow');
				return  false;
			});
			
			//不允许退货
			$("#notallowReturnBtn_c").unbind("click").bind("click",function(){
				returnedc('not_allow');
				return  false;
			});
		});
	});
	
	//退费处理
	$("[name='BUTTON_REFUND_C']").unbind("click").bind("click",function(){
		var order_id=$("#base_order_id").val();
		var payment_id = $("#order_payment_id").val();
		var url = "orderBusiness!showRefund.do?ajax=yes&order_id="+order_id;
		if(payment_id)url += "&payment_cfg_id="+payment_id;
		Eop.Dialog.open("order_goods_refund_dialog");
		$("#order_goods_refund_dialog").load(url,function(){
			$("#allowRefundBtn_c").unbind("click").bind("click",function(){
				var url = "paymentn!cancel_pay_c.do?ajax=yes";
				Cmp.ajaxSubmit('common_form','',url,{},function (responseText){
					if(responseText.result==1){
						alert(responseText.message);
						Eop.Dialog.close("order_goods_refund_dialog");
						$("#show_click_1").trigger("click");
						getOrderCounts("all");
					}
					if(responseText.result==0){
						alert(responseText.message);
					}
				},'json');
			});
		}); 
	});
	
	//换货
	$("[name='BUTTON_CHANGE_SHIPPING']").unbind("click").bind("click",function(){
		var order_id=$("#base_order_id").val();
		var url = "orderBusiness!showChange.do?ajax=yes&order_id="+order_id;
		Eop.Dialog.open("order_goods_exchange_dialog");
		$("#order_goods_exchange_dialog").load(url,function(){
			//允许换货
			$("#allowChangeBtn").unbind("click").bind("click",function(){
				changed('allow');
				return  false;
			});
			
			//不允许换货
			//$("#order_w_dialog [name='notallowChangeBtn']").unbind("click").bind("click",function(){
			//	self.changed('not_allow');
			//	return  false;
			//});
			
		});
	});
	
	//支付
	$("[name='BUTTON_CARD_PAY']").unbind("click").bind("click",function(){
		var payment_id = $(this).attr("payment_id");
		var order_id=$("#base_order_id").val();
		var url = "orderBusiness!showOrderPay.do?ajax=yes&order_id="+order_id+"&payment_cfg_id="+payment_id;
		Eop.Dialog.open("order_goods_pay_dialog");
		$("#order_goods_pay_dialog").load(url,function(){
			$("#order_pay_btn_submit").unbind("click").bind("click",function(){
				var flag_status = $("input[name=flag_status]:checked").val();
				if(flag_status==2){
					var success_url = "orderBusiness!paySuccess.do?ajax=yes";
					Cmp.ajaxSubmit('order_pay_form','',success_url,{},function(data){
						alert(data.message);
						if(data.result==1){
							Eop.Dialog.close("order_goods_pay_dialog");
							$("#show_click_1").trigger("click");
							getOrderCounts("all");
						}
					},'json');
				}else{
					pay('inline_pay');
				}
				return  false;
			});
		});
		
	});
	
});

function searchAdmin(){
	var obj = $(this);
	var id = obj.attr("id");
	var param = {};
	if(id && "admin_search_btn"!=id){
		Eop.Dialog.open("select_admin_dialog");
	}else{
		var uname = $("#search_name").val();
		param["user_name"]=uname;
	}
	var adminUrl = "orderBusiness!queryAdminUser.do?ajax=yes";
	$("#select_admin_dialog").load(adminUrl,param,function(){
		$("#admin_select_btn").unbind("click").bind("click",function(){
			var userRadio = $("input[type=radio][name=userid]:checked");
			var user_id = userRadio.attr("user_id");
			var user_name = userRadio.attr("user_name");
			if(!user_id){
				alert("请选择一个用户");
				return ;
			}
			$("#ship_user_id").val(user_id);
			$("#ship_user_name").val(user_name);
			Eop.Dialog.close("select_admin_dialog");
		});
		
		$("#admin_search_btn").unbind("click").bind("click",searchAdmin);
	});
}

function returnedc(btn_action){ //退货处理
	var flag =true;
	$("input[name=numArray]").each(function(i,v){
		if($.trim( v.value )==''){
			flag =false;
		}else{
			if(!isdigit(v.value) ){
				flag=false;
			}else if(parseInt(v.value)<0){
				flag=false;
			}
		}
	});
	if(!flag){
		alert("请输入正确的退货数量");
		return;
	}
	var self= this;
	var url = "goodsShipn!returned.do?ajax=yes"+"&btn_action="+btn_action;
	Cmp.ajaxSubmit('common_form_c','',url,{},function (responseText){
		if(responseText.result==1){
			alert(responseText.message);
			Eop.Dialog.close("order_goods_returned_dialog");
			$("#show_click_1").trigger("click");
			getOrderCounts("all");
		}
		if(responseText.result==0){
			alert(responseText.message);
		}
	},'json');
}

function changed(btn_action){ //换货处理
	var url = "goodsShipn!exchange.do?ajax=yes"+"&btn_action="+btn_action;
	Cmp.ajaxSubmit('common_form_e','',url,{},function (responseText){
		if(responseText.result==1){
			alert(responseText.message);
			Eop.Dialog.close("order_goods_exchange_dialog");
			$("#show_click_1").trigger("click");
			getOrderCounts("all");
		}
		if(responseText.result==0){
			alert(responseText.message);
		}
	},'json');
}

//支付
function pay(pay_type){ 
	var cfg = $("span[name=paycfgsp]");
	var online_flag = cfg.attr("online_flag");
	var url = "commonPay.do?ajax=yes";
	if($("[name='bankId']:checked").length ==0 && "0"==online_flag){
		alert("请选择支付银行");
		return;
	}
	Cmp.ajaxSubmit('order_pay_form','',url,{},function(data){
		if(data.result==1){
			if(data.onlineflag=='0'){
				Cmp.bankPayBackNew(data);
			}else{
				alert(data.message);
				//$("#show_click_1").trigger("click");
			}
			//$("#pay_success_div").show();
			Eop.Dialog.open("pay_success_div");
			$("#order_pay_fail_btn_submit").unbind("click").bind("click",function(){Eop.Dialog.close("pay_success_div");});
			$("#order_pay_success_btn_submit").unbind("click").bind("click",function(){
				var success_url = "orderBusiness!paySuccess.do?ajax=yes";
				Cmp.ajaxSubmit('order_pay_form','',success_url,{},function(data){
					alert(data.message);
					if(data.result==1){
						Eop.Dialog.close("pay_success_div");
						Eop.Dialog.close("order_goods_pay_dialog");
						$("#show_click_1").trigger("click");
						getOrderCounts("all");
					}
				},'json');
			});
		}else{
			alert(data.message);
		}
	},'json');
}

function isdigit(s) {
	var r, re;
	re = /\d*/i; // \d表示数字,*表示匹配多个数字
	r = s.match(re);
	return (r == s);
}