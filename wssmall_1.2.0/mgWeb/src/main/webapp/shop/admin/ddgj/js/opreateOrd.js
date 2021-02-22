/**
 * 订单归集左侧操作区功能
 */

function getOrderCounts(query_type){
	var monitor = 0;//$("#order_monitor_v").val();
	var url = "orderOp!getOrderCounts.do?ajax=yes&monitor="+monitor;
	if(query_type)url += "&query_type="+query_type
	/**
	 * 查询各个类型的数量
	 */
	$.ajax({
		type:"post",
		url:url,
		dataType:"json",
		success:function(responseText){
			if (responseText.result == 1) {
				var countMap =  eval('(' + responseText.countMap + ')'); 
				for (var key in countMap) {
					$("a[name=list_order_btn],li[name=list_order_btn],a[name=apply_0],a[name=apply_1],a[name=apply_2]").each(function(){
						var attr = $(this).attr("extend_attr");
						if(attr == key){
							$(this).find("span").html(countMap[key]);
						}
					});
				}
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}	
		},
		error:function(){
			alert("查询订单数量失败！");
		}
	});
}

var OpOrder = {
	init:function(){
		
		/**
		 *	操作按钮点击事件
		 */
		//$(".op_order").bind("click",OpOrder.orderEvent);
		
		//查询各个类型的数量
		getOrderCounts("all");
		
		
		/**
		 * 退换货申请 新增申请单按钮
		 */
		$("#order_return_apply").live("click",function(){
			OpOrder.orderReturnAdd("new",4);
		});
		$("#order_back_apply").live("click",function(){
			OpOrder.orderReturnAdd("new",3);
		});
		
		
		/**
		 * 右侧操作按钮列表页面
		 */
		$(".apply_order").bind("click",function(){
			var btn_name = $(this).attr("name");
			$("#order_monitor_v").val(0);
			var url = "";
			var data;
			if(btn_name == "apply_0"){
				url = "orderOp!queryApplyPage.do?ajax=yes&service_type=4";
			}else if(btn_name == "apply_1"){
				url = "orderOp!queryApplyPage.do?ajax=yes&service_type=3";
			}else if(btn_name == "apply_2"){
				url = "orderOp!queryBackPage.do?ajax=yes&service_type=2";
			}
			var obj = $(this);
			$("#order_list_dv").load(url,function(responseText){
				$("#left_menu_dv").find("li").removeClass("curr");
				obj.closest("li").addClass("curr");
				$("#order_btn_div").hide();
				$("#order_apply_btn_div").show();
				$("#order_list_main").show();
				$("#order_detail_div").hide();
				$("a[name=order_detail_apply]").unbind("click").bind("click",function(){
					try{
	 					//event.stopPropagation(); 
		 				if (event.stopPropagation) { 
		 					event.stopPropagation(); 
		 				}else if (window.event) { 
		 					window.event.cancelBubble = true; 
		 				}
	 				}catch(e){
	 					
	 				}
	 				$(this).closest("tr").find("input[type=radio]").attr("checked","checked");
	 				JXOrder.orderBase();
				});
			});
		});
	},
	orderEvent:function(){
		var sel_order = $("#order_list_dv tbody input[name=order_id]:checked");
		if(sel_order.length == 0){
			alert("请选择需要操作的订单！");
			return;
		}
		var btn_name = $(this).attr("name");
		var order_id = sel_order.attr("order_id");
		var url = "";
		var data;
		if(btn_name == "op_order_0"){
			url = "ordergj!orderBase.do?ajax=yes";
			data = "order_id="+order_id+"&page_from=supply";
			
		}else if(btn_name == "op_order_1"){
			
		}else if(btn_name == "op_order_2"){
			url = "orderOp!addOrderComments.do?ajax=yes";
			data = "orderComments.order_id="+order_id;
			Eop.Dialog.open("order_remak_dlg");
			$("#order_remak_dlg").load(url+"&"+data,function(){
				OpOrder.comOrdInit();
			});
			return ;
		}else if(btn_name == "op_order_3"){
			url = "orderOp!addOrderExp.do?ajax=yes";
			data = "orderExcepCollect.order_id="+order_id;
			Eop.Dialog.open("order_exception_dlg");
			$("#order_exception_dlg").load(url+"&"+data,function(){
				OpOrder.expOrdInit();
			});
			return ;
		}
		$.ajax({
			type:"post",
			url:url,
			data:data,
			dataType:"html",
			success:function(data){
				$("#order_info_dv").empty().append(data);
				clearUlClass();
				if(btn_name == "op_order_3"){
					OpOrder.expOrdInit();
					$("#show_click_8").addClass("selected");
				}else if(btn_name == "op_order_0"){
					OpOrder.supOrdInit();
					$("#show_click_1").addClass("selected");
				}else if(btn_name == "op_order_2"){
					OpOrder.comOrdInit();
					$("#show_click_9").addClass("selected");
				}
				showOrderOption();
			},
			error:function(){
				alert("操作失败，请重试！");
			}
		});
	},
	/**
	 * 订单异常
	 */
	expOrdInit:function(){
		$("#order_exception_submit").bind("click",function(){
			var url = ctx+ "/shop/admin/orderOp!saveOrderExp.do?ajax=yes";
			Cmp.ajaxSubmit('order_exp_form', '', url, {}, OpOrder.expOrdJsonBack,'json');
		});
	},
	expOrdJsonBack:function(responseText) { 
		if (responseText.result == 1) {
			alert("操作成功！");
			Eop.Dialog.close("order_exception_dlg");
			Eop.Dialog.close("order_remak_dlg");
			//$("#show_click_1").trigger("click");
			window.location.reload();
		}
		if (responseText.result == 0) {
			alert(responseText.message);
		}	
	},
	/**
	 * 订单补录
	 */
	supOrdInit:function(){
		$("#submit_supply_info").bind("click",function(){
			var url = ctx+ "/shop/admin/orderOp!saveOrderSupply.do?ajax=yes";
			var formJsonA = $("#order_supply_form").serializeArray();
			var params = {};
			$.each(formJsonA,function(idx,item){
				params[item.name]=item.value;
			});
			
			$.ajax({
				type:"post",
				url:url,
				data:jQuery.param(params),
				dataType:"json",
				success:function(responseText){
					if (responseText.result == 1) {
						alert("补录成功！");
						$("#show_click_1").trigger("click");
					}
					if (responseText.result == 0) {
						alert(responseText.message);
					}	
				},
				error:function(){
					alert("补录失败，请重试！");
				}
			});
		});
	},
	/**
	 * 订单备注,通用订单异常的回调
	 */
	comOrdInit:function(){
		$("#order_comments_submit").bind("click",function(){
			var url = ctx+ "/shop/admin/orderOp!saveOrderComments.do?ajax=yes";
			Cmp.ajaxSubmit('order_comments_form', '', url, {}, OpOrder.expOrdJsonBack,'json');
		});
	},
	orderReturnAdd:function(action,service_type){
		var sel_order = $("#order_list_dv tbody input[name=order_id]:checked");
		var order_id = "";
		if(sel_order.length > 0){
			order_id = sel_order.attr("order_id");
		}
		var data = "action="+action+"&service_type="+service_type;
		if(order_id != ""){
			data += "&order_id="+order_id;
		}
		
		$.ajax({
			type:"post",
			url:"orderOp!addOrderReturn.do?ajax=yes",
			data:data,
			dataType:"html",
			success:function(data){
				$("#order_info_dv").empty().append(data);
				OpOrder.orderReturnInit();
			},
			error:function(){
				alert("初始化退货申请界面失败！");
			}
		});
	},
	/**
	 * 退换货申请初始化
	 */
	orderReturnInit:function(){
		
		Eop.Dialog.init({id:"selectgoodsDlg",modal:true,title:"选择商品", height:"500px",width:"800px"});
		
		//数量验证初始化
		$("#goodsDataNode input[name=return_num]").bind("blur",function(){
			if($(this).val()<1){
				alert("退货数量不能小于1");
				$(this).val(1);
			}else if($(this).val()>$(this).attr("buynum")){
				alert("退货数量不能大于发货数量");
				$(this).val($(this).attr("buynum"));
			}
			checkPrice();
		});
		
		
		//点击事件初始化
		$("#goodsDataNode input[name=itemIdArray]").live("click",function(){
			if(!this.checked){
				$(this).closest("tr").find("input[name=return_num]").attr("disabled","disabled");
			}else{
				$(this).closest("tr").find("input[name=return_num]").removeAttr("disabled");
			}
			checkPrice();
		});
		
		
		//保存
		$("#addApplyOrderBaseBtn").bind("click",function(){
			$("#addApplyFrom").validate();
			$.Loading.show('正在响应您的请求，请稍侯...');
			var options = {
				type : "post",
				url : "applyn!applyReturnedGoods.do?ajax=yes",
				dataType : "json",
				success : function(result) {
					alert(result.msg);
					if(result.status==0){
						Eop.Dialog.close("orderApply_dialog");
						//window.location.reload();
					}
					$.Loading.hide();
				},
				error : function(e,b) {
					$.Loading.hide();
					alert("操作失败，请重试"+b);
				}
			};
			$("#addApplyFrom").ajaxSubmit(options);
		});
		
		
		//选择商品
		$("#goods-find-btn").bind("click",function(isSearchBtn){
			var url = "applyn!queryGoods.do?ajax=yes";
			var args = {};
			if(isSearchBtn==1){
				var orderid = $("#s_order__id").val();
				url += "&order_id="+orderid;
				var goodsName = $("#s_goodsName").val();
				args = {goodsName:goodsName};
			}
			$("#selectgoodsDlg").load(url,args,function(responseText){
				if(isSearchBtn!=1)Eop.Dialog.open("selectgoodsDlg");
				$("#goodssearchBtn").bind("click",function(){
					OrderApply.selectGoods(1);
				});
				$("#app_goods_confirm_btn").bind("click",function(){
					var cgs = $("input[name=product_ids]:checked");
					$.each(cgs,function(idx,item){
						var pi = $(item);
						var ptr = '<tr class="selected">'+
							'<td>'+pi.attr("goodsId")+'</td><td visibility="true" class="product-name">'+pi.attr("goodsName")+'</td>'+
							'<td>'+pi.attr("price")+'元</td>'+
							'<td class="count"><input type="text" size="3" value="1" price="'+pi.attr("price")+'" name="ex_num" required="true" dataType="int" onblur="checkPrice();" /></td>'+
							'<td style="text-align: center;"><a href="javascript:void(0);" name="del_ex_btn">删除</a><input type="hidden" name="ex_productArray" value="'+pi.attr("productid")+'" /></td></tr>';
						$("#exgoodsDataNode").append(ptr);
					});
					Eop.Dialog.close("selectgoodsDlg");
					checkPrice();
				});
			});
		});
		
	}
		
};


function checkPrice(){
	var checkgoods = $("input[name=itemIdArray]:checked");
	var depreciation_price = $("input[name=depreciation_price]").val();
	var ship_price = $("input[name=ship_price]").val();
	var totalPrice = 0;
	if(checkgoods && checkgoods.length>0){
		$.each(checkgoods,function(idx,item){
			var num = $(item).closest("tr").find("input[name=return_num]").val();
			var cprice = $(item).attr("price");
			totalPrice += num*cprice;
		});
	}
	var order_amount = $("#order_amount_price").val();
	if(order_amount<totalPrice)totalPrice=order_amount;
	var pay_price = totalPrice-depreciation_price-ship_price;
	var service_type = $("input[type=hidden][name=service_type]").val();
	if(service_type==3){
		var exno = $("input[name=ex_num]");
		var exprice = 0;
		if(exno && exno.length>0){
			$.each(exno,function(idx,item){
				exprice += $(item).attr("price")*$(item).val();
			});
			pay_price -= exprice;
		}
	}
	$("#returnTotalPrice").html(totalPrice);
	$("input[name=pay_price]").val(pay_price);
}

$(function(){
	Eop.Dialog.init({id:"order_exception_dlg",modal:true,title:"订单异常", height:"500px",width:"500px"});
	Eop.Dialog.init({id:"order_remak_dlg",modal:true,title:"订单备注", height:"500px",width:"500px"});
	Eop.Dialog.init({id:"orderApply_dialog",modal:true,title:"退/换货申请", height:"600px",width:"900px"});
	Eop.Dialog.init({id:"selectOrderDlg",modal:true,title:"选择订单", height:"500px",width:"800px"});
	Eop.Dialog.init({id:"selectgoodsDlg",modal:true,title:"选择商品", height:"500px",width:"800px"});
	Eop.Dialog.init({id:"auditOrderApplyDlg",modal:true,title:"退/换货审核", height:"600px",width:"900px"});
	Eop.Dialog.init({id:"order_flows_log_dlg",modal:true,title:"订单处理流程日志", height:"600px",width:"900px"});
	OpOrder.init();
	OrderApply.init();
});

var OrderApply = {
		init:function(){
			var self = this;
			$("a[name=del_ex_btn]").live("click",function(){
				$(this).closest("tr").remove();
				checkPrice();
			});
			
			$("#goodsDataNode input[name=itemIdArray]").live("click",function(){
				if(!this.checked){
					$(this).closest("tr").find("input[name=return_num]").attr("disabled","disabled");
				}else{
					$(this).closest("tr").find("input[name=return_num]").removeAttr("disabled");
				}
				checkPrice();
			});
			
			$("a[name=audit_btn]").bind("click",self.auditApply);
			
		},
		auditApply:function(){
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
		},
		showApplyDialog:function(){
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
		},
		selectOrder:function(isSearchBtn){
			var url = "applyn!selectOrder.do?ajax=yes";
			if(isSearchBtn==1){
				var orderid = $("#s_order__id").val();
				url += "&order_id="+orderid;
			}
			$("#selectOrderDlg").load(url,function(responseText){
				if(isSearchBtn!=1)Eop.Dialog.open("selectOrderDlg");
				$("#searchorderbtn").bind("click",function(){
					OrderApply.selectOrder(1);
				});
				$("#confirmaddorderBtn").bind("click",OrderApply.queryOrderItems);
			});
		},
		selectGoods:function(isSearchBtn){
			var url = "applyn!queryGoods.do?ajax=yes";
			var args = {};
			if(isSearchBtn==1){
				var orderid = $("#s_order__id").val();
				url += "&order_id="+orderid;
				var goodsName = $("#s_goodsName").val();
				args = {goodsName:goodsName};
			}
			$("#selectgoodsDlg").load(url,args,function(responseText){
				if(isSearchBtn!=1)Eop.Dialog.open("selectgoodsDlg");
				$("#goodssearchBtn").bind("click",function(){
					OrderApply.selectGoods(1);
				});
				$("#app_goods_confirm_btn").bind("click",function(){
					var cgs = $("input[name=product_ids]:checked");
					$.each(cgs,function(idx,item){
						var pi = $(item);
						var ptr = '<tr class="selected">'+
							'<td>'+pi.attr("goodsId")+'</td><td visibility="true" class="product-name">'+pi.attr("goodsName")+'</td>'+
							'<td>'+pi.attr("price")+'元</td>'+
							'<td class="count"><input type="text" size="3" value="1" price="'+pi.attr("price")+'" name="ex_num" required="true" dataType="int" onblur="checkPrice();" /></td>'+
							'<td style="text-align: center;"><a href="javascript:void(0);" name="del_ex_btn">删除</a><input type="hidden" name="ex_productArray" value="'+pi.attr("productid")+'" /></td></tr>';
						$("#exgoodsDataNode").append(ptr);
					});
					Eop.Dialog.close("selectgoodsDlg");
					checkPrice();
				});
			});
		},
		queryOrderItems:function(){
			var order_id = $("input[name=select_order_id]:checked").val();
			if(!order_id){
				alert("请选择一个订单");
				return ;
			}
			$.ajax({url : "applyn!queryOrderItems.do?ajax=yes",
				type : "POST",
				data:{order_id:order_id},
				dataType : 'json',
				success : function(data) {
					$("#goodsDataNode").empty();
					$("input[name=order_id]").val(order_id);
					$("#order_amount_price").val(data.order_amount);
					//var totalPrice = 0;
					$.each(data.items,function(idx,item){
						var tr = '<tr class="selected"><td><input type="checkbox" checked="checked" name="itemIdArray" value="'+item.item_id+'" price="'+item.coupon_price+'" /></td><td>'+item.goods_id+'</td>'+
							'<td visibility="true" class="product-name">'+item.name+'</td>'+
							'<td>'+item.num+'</td>'+
							'<td>'+item.ship_num+'</td>'+
							'<td>'+item.coupon_price+'元</td>'+
							'<td class="count"><input type="text" size="3" buynum="'+item.ship_num+'" value="'+item.ship_num+'" name="return_num" required="true" dataType="int" /></td></tr>';
						$("#goodsDataNode").append(tr);
						//totalPrice += item.num*item.coupon_price;
					});
					
					$("#goodsDataNode input[name=return_num]").bind("blur",function(){
						if($(this).val()<1){
							alert("退货数量不能小于1");
							$(this).val(1);
						}else if($(this).val()>$(this).attr("buynum")){
							alert("退货数量不能大于购买数量");
							$(this).val($(this).attr("buynum"));
						}
						checkPrice();
					});
					//$("#returnTotalPrice").html(totalPrice);
					checkPrice();
					Eop.Dialog.close("selectOrderDlg");
					Eop.Dialog.close("auditOrderApplyDlg");
				},error : function(a,b) {
					alert(a+b);
				}
			});
		},
		addApplySub:function(){
			$.Loading.show('正在响应您的请求，请稍侯...');
			var options = {
				type : "post",
				url : "applyn!applyReturnedGoods.do?ajax=yes",
				dataType : "json",
				success : function(result) {
					alert(result.msg);
					if(result.status==0){
						Eop.Dialog.close("orderApply_dialog");
						//window.location.reload();
					}
					$.Loading.hide();
				},
				error : function(e,b) {
					$.Loading.hide();
					alert("操作失败，请重试"+b);
				}
			};
			$("#addApplyFrom").ajaxSubmit(options);
		}
};
