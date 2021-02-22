/*订单操作按钮**/

var OrderBtns ={
	orderid : OrderDetail.orderid,
	orderStatus:OrderDetail.orderStatus,
	payStatus:OrderDetail.payStatus,
	shipStatus:OrderDetail.shipStatus,
	init:function(){
		this.orderid =OrderDetail.orderid;
		this.orderStatus=OrderDetail.orderStatus;
		this.payStatus=OrderDetail.payStatus;
		this.shipStatus=OrderDetail.shipStatus;
		
		this.bindFlowEvent();
		//网银支付界面
		Eop.Dialog.init({id:"payDialog",modal:false,title:"网银支付提示",width:"750px"});
		
	},
	bindFlowEvent:function(){
		
		//商品审核
		var self = this;
		$("[name='BUTTON_AUDIT']").unbind("click");
		$("[name='BUTTON_AUDIT']").bind("click",function(){
			var url =ctx+"/shop/admin/goodsAudit!showGoodsApplyAuditDialog.do?ajax=yes&orderId="+OrderDetail.orderid; //goods_audit.jsp
			Eop.Dialog.open("order_w_dialog");
			$("#order_w_dialog").load(url,function(){
				$("#order_w_dialog [name='auditBtn']").unbind("click");
				$("#order_w_dialog [name='auditBtn']").bind("click",function(){
					self.audit();
					return  false;
				});
			});
		});
		
		//取消订单
		$("[name='BUTTON_CANCEL']").unbind("click");
		$("[name='BUTTON_CANCEL']").bind("click",function(){
			if(confirm("确认取消订单?"))
			{
				var url =ctx+"/shop/admin/ordern!cancel.do?ajax=yes&orderId="+OrderDetail.orderid;
				Cmp.excute('',url,{},self.jsonBack,'json');
				
			}
		});
		
		
		//撤销订单
		$("[name='BUTTON_DRAWBACK']").unbind("click");
		$("[name='BUTTON_DRAWBACK']").bind("click",function(){
			if(confirm("确认撤销订单?"))
			{
				var url =ctx+"/shop/admin/ordern!withdraw.do?ajax=yes&orderId="+OrderDetail.orderid;
				Cmp.excute('',url,{},self.jsonBack,'json');
			}
		});
		
		
		//完成
		$("[name='BUTTON_FINISHED']").unbind("click");
		$("[name='BUTTON_FINISHED']").bind("click",function(){
			
			if(confirm("完成 操作会使该订单归档且不允许再做任何操作，确定要执行吗？")){
				var url =ctx+"/shop/admin/ordern!complete.do?ajax=yes&orderId="+OrderDetail.orderid;
				Cmp.excute('',url,{},self.jsonBack,'json');
			}
			
		});
		
		//作废
		$("[name='BUTTON_INVALID']").unbind("click");
		$("[name='BUTTON_INVALID']").bind("click",function(){
			if(confirm("作废操作会使该订单归档且不允许再做任何操作，确定要执行吗？")){
				var url =ctx+"/shop/admin/ordern!invalid.do?ajax=yes&orderId="+OrderDetail.orderid;
				Cmp.excute('',url,{},self.jsonBack,'json');
			}
		});
		
		
		//云卡调拨
		$("[name='BUTTON_CLOUD_ACCEPT']").unbind("click");
		$("[name='BUTTON_CLOUD_ACCEPT']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			var url =ctx+"/shop/admin/cloud!showRandomCloudDialog.do?ajax=yes&orderId="+OrderDetail.orderid+"&from_page=order"; //goods_audit.jsp
			$("#order_w_dialog").load(url,function(){
				self.cloud_page_init();
			}); 
		});
		
		
		//充值卡调拨
		$("[name='BUTTON_CARD_ACCEPT']").unbind("click");
		$("[name='BUTTON_CARD_ACCEPT']").bind("click",function(){ //按购买数量,面值统计
			Eop.Dialog.open("order_w_dialog");
			var url =ctx+"/shop/admin/card!showCardRandomDialog.do?ajax=yes&orderId="+OrderDetail.orderid+"&from_page=order"; //goods_audit.jsp
			$("#order_w_dialog").load(url,function(){
				self.card_page_init();
			}); 
		});
		
		
		//云卡支付
		$("[name='BUTTON_PAY']").unbind("click");
		$("[name='BUTTON_PAY']").bind("click",function(){
			var url =ctx+"/shop/admin/paymentn!showPayDialog.do?ajax=yes&orderId="+OrderDetail.orderid+"&payment_cfg_id="+2; //goods_audit.jsp

			Eop.Dialog.open("order_w_dialog");
			$("#order_w_dialog").load(url,function(){
				$("#order_w_dialog [name='payBtn']").unbind("click");
				$("#order_w_dialog [name='payBtn']").bind("click",function(){
					self.pay('inline_pay');
					return  false;
				});
			});
			
		});
		
		//支付
		$("[name='BUTTON_CARD_PAY']").unbind("click");
		$("[name='BUTTON_CARD_PAY']").bind("click",function(){
			var payment_id = $(this).attr("payment_id");
			var url =ctx+"/shop/admin/paymentn!showPayDialog.do?ajax=yes&orderId="+OrderDetail.orderid+"&payment_cfg_id="+payment_id; //goods_audit.jsp,充值卡、流量卡走在线支付 
			
			Eop.Dialog.open("order_w_dialog");
			$("#order_w_dialog").load(url,function(){
				$("#order_w_dialog [name='payBtn']").unbind("click");
				$("#order_w_dialog [name='payBtn']").bind("click",function(){
					self.pay('inline_pay');
					return  false;
				});
			});
			
		});
		
		
		//发货
		$("[name='BUTTON_SHIPPING']").unbind("click");
		$("[name='BUTTON_SHIPPING']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			var url =ctx+"/shop/admin/shipn!showShipDialog.do?ajax=yes&orderId="+OrderDetail.orderid; //goods_audit.jsp
			$("#order_w_dialog").load(url,function(){
				$("#order_w_dialog [name='shippingBtn']").unbind("click");
				$("#order_w_dialog [name='shippingBtn']").bind("click",function(){
					self.ship();
					return  false;
				});
			});
		});
		
		
		//确认收货
		$("[name='BUTTON_GET_SHIPPING']").unbind("click");
		$("[name='BUTTON_GET_SHIPPING']").bind("click",function(){
			if(confirm("订单确认收货?"))
			{
				var url =ctx+"/shop/admin/shipn!confirm_ship.do?ajax=yes&orderId="+OrderDetail.orderid;
				Cmp.excute('',url,{},self.jsonBack,'json');
			}
		});
		
		//资料返档
		$("[name='BUTTON_CUST_ACCEPT']").unbind("click");
		$("[name='BUTTON_CUST_ACCEPT']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			var url =ctx+"/shop/admin/custReturned!showCustReturnedDialog.do?ajax=yes&orderId="+OrderDetail.orderid; //goods_audit.jsp
			$("#order_w_dialog").load(url,function(){
				self.custReturn_page_init();
			});
		});
		
		//合约机受理申请
		$("[name='BUTTON_CONTRACT_APPLY']").unbind("click");
		$("[name='BUTTON_CONTRACT_APPLY']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			var url =ctx+"/shop/admin/contract!showContractApplyDialog.do?ajax=yes&orderId="+OrderDetail.orderid; //goods_audit.jsp
			$("#order_w_dialog").load(url,function(){
				
			});
		});
		
		//合约机审核
		$("[name='BUTTON_CONTRACT_AUDIT']").unbind("click");
		$("[name='BUTTON_CONTRACT_AUDIT']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			var url =ctx+"/shop/admin/contract!showContractAuditDialog.do?ajax=yes&orderId="+OrderDetail.orderid; //goods_audit.jsp
			$("#order_w_dialog").load(url,function(){
				
			});
		});
		
		//合约机受理
		$("[name='BUTTON_CONTRACT_ACCEPT']").unbind("click");
		$("[name='BUTTON_CONTRACT_ACCEPT']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			var url =ctx+"/shop/admin/contract!showContractDialog.do?ajax=yes&orderId="+OrderDetail.orderid; //goods_audit.jsp
			$("#order_w_dialog").load(url,function(){
				
			});
		});
		
		//合约机号码调拨
		$("[name='BUTTON_CONTRACT_TANSFER']").unbind("click");
		$("[name='BUTTON_CONTRACT_TANSFER']").bind("click",function(){
		
			Eop.Dialog.open("order_w_dialog");
			var url =ctx+"/shop/admin/contract!showRandomContractTransferDialog.do?ajax=yes&orderId="+OrderDetail.orderid+"&from_page=order"; //goods_audit.jsp
			
			$("#order_w_dialog").load(url,function(){
				self.accnbr_page_init();
			}); 
		});
		
		
		//退货
		$("[name='BUTTON_RETURNED_SHIPPING']").unbind("click");
		$("[name='BUTTON_RETURNED_SHIPPING']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			
			var url =ctx+"/shop/admin/shipn!showReturnDialog.do?ajax=yes&orderId="+OrderDetail.orderid; //goods_audit.jsp
				//允许退货
			$("#order_w_dialog").load(url,function(){
				$("#order_w_dialog [name='allowReturnBtn']").unbind("click");
				$("#order_w_dialog [name='allowReturnBtn']").bind("click",function(){
					self.returned('allow');
					return  false;
				});
				
				//不允许退货
				$("#order_w_dialog [name='notallowReturnBtn']").unbind("click");
				$("#order_w_dialog [name='notallowReturnBtn']").bind("click",function(){
					self.returned('not_allow');
					return  false;
				});
			});
				
		});
		
		
		//换货
		$("[name='BUTTON_CHANGE_SHIPPING']").unbind("click");
		$("[name='BUTTON_CHANGE_SHIPPING']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			var url =ctx+"/shop/admin/goodsShipn!showChangeDialog.do?ajax=yes&orderId="+OrderDetail.orderid; //goods_audit.jsp
			$("#order_w_dialog").load(url,function(){
				//允许换货
				$("#order_w_dialog [name='allowChangeBtn']").unbind("click");
				$("#order_w_dialog [name='allowChangeBtn']").bind("click",function(){
					self.changed('allow');
					return  false;
				});
				
				//不允许换货
				$("#order_w_dialog [name='notallowChangeBtn']").unbind("click");
				$("#order_w_dialog [name='notallowChangeBtn']").bind("click",function(){
					self.changed('not_allow');
					return  false;
				});
				
			});
		});
		
		
		//退费申请
		$("[name='BUTTON_REFUND_APPLY']").unbind("click");
		$("[name='BUTTON_REFUND_APPLY']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			var url =ctx+"/shop/admin/paymentn!showRefundApplyDialog.do?ajax=yes&orderId="+OrderDetail.orderid; //goods_audit.jsp
			$("#order_w_dialog").load(url,function(){
				
			});
		});
		
		//退费审核
		$("[name='BUTTON_REFUND_AUDIT']").unbind("click");
		$("[name='BUTTON_REFUND_AUDIT']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			var url =ctx+"/shop/admin/paymentn!showRefundAuditDialog.do?ajax=yes&orderId="+OrderDetail.orderid; //goods_audit.jsp
			$("#order_w_dialog").load(url,function(){
				
			});
		});
		
		
		//退费处理
		$("[name='BUTTON_REFUND']").unbind("click");
		$("[name='BUTTON_REFUND']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			$("#order_w_dialog").load(basePath+"paymentn!showRefundDialog.do?ajax=yes&orderId="+OrderDetail.orderid+"&payment_cfg_id="+4,function(){
				$("#order_w_dialog [name='allowRefundBtn']").unbind("click");
				$("#order_w_dialog [name='allowRefundBtn']").bind("click",function(){
					self.cancel_pay();
				});
			}); 
		});
		
		
		//大众版订单处理==================================
		//备货处理
		$("[name='BUTTON_CUST_ACCEPT_C']").unbind("click");
		$("[name='BUTTON_CUST_ACCEPT_C']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			$("#order_w_dialog").load(basePath+"orderdw!showCustAcceptDialog.do?ajax=yes&orderId="+OrderDetail.orderid,function(){
				$("#order_w_dialog [name='bl']").unbind("click");
				$("#order_w_dialog [name='bl']").bind("click",function(){
					var url =ctx+"/shop/admin/orderdw!confirmBH.do?ajax=yes";
					Cmp.ajaxSubmit('order_bl_form','',url,{},self.jsonBack,'json');
				});
			});
		});
		//发货处理
		$("[name='BUTTON_SHIPPING_C']").unbind("click");
		$("[name='BUTTON_SHIPPING_C']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			$("#order_w_dialog").load(basePath+"orderdw!showFHDialog.do?ajax=yes&orderId="+OrderDetail.orderid,function(){
				$("#order_w_dialog [name='fh']").unbind("click");
				$("#order_w_dialog [name='fh']").bind("click",function(){
					var url =ctx+"/shop/admin/orderdw!confirmFH.do?ajax=yes";
					var logiid = $("select[name='logi_id_name']").val().split("\|")[0];
					if(logiid=='0'){
						if(!$("input[name='flow.logi_name']").val()){
							alert("请填写物流公司名称。");
							return ;
						}
						if(!$("textarea[name='flow.description']").val()){
							alert("请填写物流描述。");
							return ;
						}
					}
					Cmp.ajaxSubmit('order_fh_form','',url,{},self.jsonBack,'json');
				});
			});
		});
		//收货待确认处理
		$("[name='BUTTON_GET_SHIPPING_C']").unbind("click");
		$("[name='BUTTON_GET_SHIPPING_C']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			$("#order_w_dialog").load(basePath+"orderdw!showSHDialog.do?ajax=yes&orderId="+OrderDetail.orderid,function(){
				$("#order_w_dialog [name='sh']").unbind("click");
				$("#order_w_dialog [name='sh']").bind("click",function(){
					var url =ctx+"/shop/admin/orderdw!confirmSH.do?ajax=yes";
					Cmp.ajaxSubmit('order_sh_form','',url,{},self.jsonBack,'json');
				});
			});
		});
		//完成订单
		$("[name='BUTTON_FINISHED_C']").unbind("click");
		$("[name='BUTTON_FINISHED_C']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			$("#order_w_dialog").load(basePath+"orderdw!showFinishDialog.do?ajax=yes&orderId="+OrderDetail.orderid,function(){
				$("#order_w_dialog [name='ord_finish']").unbind("click");
				$("#order_w_dialog [name='ord_finish']").bind("click",function(){
					var url =ctx+"/shop/admin/orderdw!confirmFinish.do?ajax=yes";
					Cmp.ajaxSubmit('order_finish_form','',url,{},self.jsonBack,'json');
				});
			});
		});
		
		//退货
		$("[name='BUTTON_RETURNED_SHIPPING_C']").unbind("click");
		$("[name='BUTTON_RETURNED_SHIPPING_C']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			
			var url =ctx+"/shop/admin/goodsShipn!showReturnDialog.do?ajax=yes&orderId="+OrderDetail.orderid; //goods_audit.jsp
				//允许退货
			$("#order_w_dialog").load(url,function(){
				$("#order_w_dialog [name='allowReturnBtn_c']").unbind("click");
				$("#order_w_dialog [name='allowReturnBtn_c']").bind("click",function(){
					self.returnedc('allow');
					return  false;
				});
				
				//不允许退货
				$("#order_w_dialog [name='notallowReturnBtn_c']").unbind("click");
				$("#order_w_dialog [name='notallowReturnBtn_c']").bind("click",function(){
					self.returnedc('not_allow');
					return  false;
				});
			});
				
		});
		
		//退费处理
		$("[name='BUTTON_REFUND_C']").unbind("click");
		$("[name='BUTTON_REFUND_C']").bind("click",function(){
			Eop.Dialog.open("order_w_dialog");
			$("#order_w_dialog").load(basePath+"paymentn!showRefundDialogc.do?ajax=yes&orderId="+OrderDetail.orderid+"&payment_cfg_id="+4,function(){
				$("#order_w_dialog [name='allowRefundBtn_c']").unbind("click");
				$("#order_w_dialog [name='allowRefundBtn_c']").bind("click",function(){
					self.cancel_pay_c();
				});
			}); 
		});
		
		//大众版订单处理==================================
		
		
	},
	contractApply:function(){
		var self= this;
		var url =ctx+"/shop/admin/contract!order.do?ajax=yes";
		Cmp.ajaxSubmit('order_form','',url,{},self.jsonBack,'json');
	},
	contractAudit:function(){
		var self= this;
		var url =ctx+"/shop/admin/contract!order.do?ajax=yes";
		Cmp.ajaxSubmit('order_form','',url,{},self.jsonBack,'json');
	},
	contractAccept:function(){
		var self= this;
		var url =ctx+"/shop/admin/custReturned!order.do?ajax=yes";
		Cmp.ajaxSubmit('order_form','',url,{},self.jsonBack,'json');
	},
	custReturned:function(){ //资料返档
		var self= this;
		var url =ctx+"/shop/admin/custReturned!returned.do?ajax=yes";
		Cmp.ajaxSubmit('cust_form','',url,{},self.jsonBack,'json');
	},
	pay:function(pay_type){ //云卡在线支付
		var self= this;
		
		var cfg = $("span[name=paycfgsp]");
		var online_flag = cfg.attr("online_flag");
		var url = ctx+"/shop/admin/commonPay.do?ajax=yes";
		if($("[name='bankId']:checked").length ==0 && "0"==online_flag){
			alert("请选择支付银行");
			return;
		}
		Cmp.ajaxSubmit('common_form','',url,{},function(data){
			if(data.result==1){
				if(data.onlineflag=='0'){
					Cmp.bankPayBackNew(data);
				}else{
					alert(data.message);
					window.parent.location.reload();
				}
				Eop.Dialog.close("order_w_dialog");
			}else{
				alert(data.message);
			}
		},'json');
		
		/**
		var url =ctx+"/shop/admin/paymentn!pay.do?ajax=yes";
		if("ratecard" ==pay_type) //充值卡支付
			Cmp.ajaxSubmit('common_form','',url,{},self.jsonBack,'json');
		else if("inline_pay" ==pay_type) //在线支付
			Cmp.ajaxSubmit('common_form','wrapper',url,{'insert':'yes'},self.payjsonBack,'html');
			*/
	},
	payjsonBack:function(responseText){
		var responseText_str =responseText;
		if(responseText.indexOf("{result:")>-1){
			var responseText = eval('(' + responseText + ')');   
			if (responseText.result == 1) {
				var url =ctx+"/shop/admin/paymentn!showPayTipDialog.do?ajax=yes" //goods_audit.jsp
				Eop.Dialog.close("order_dialog");
				Eop.Dialog.close("order_w_dialog");
				Eop.Dialog.open("pay_dialog");
				Cmp.bankPayBack(responseText_str,url);
			}else{
				alert(responseText.message);
			}
		}
	},
	cancel_pay:function(){ //合约机退费处理
		var self= this;
		var url =ctx+"/shop/admin/paymentn!cancel_pay.do?ajax=yes";
		Cmp.ajaxSubmit('common_form','',url,{},self.jsonBack,'json');
	},
	cancel_pay_c:function(){ //合约机退费处理
		var self= this;
		var url =ctx+"/shop/admin/paymentn!cancel_pay_c.do?ajax=yes";
		Cmp.ajaxSubmit('common_form','',url,{},self.jsonBack,'json');
	},
	ship:function(){ //发货处理
		var self= this;
		var url =ctx+"/shop/admin/shipn!ship.do?ajax=yes";
		Cmp.ajaxSubmit('common_form','',url,{},self.jsonBack,'json');
	},
	changed:function(btn_action){ //换货处理
		/*var self= this;
		var url =ctx+"/shop/admin/shipn!change.do?ajax=yes"+"&btn_action="+btn_action;
		Cmp.ajaxSubmit('common_form','',url,{},self.jsonBack,'json');*/
		
		/*var flag =true;
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
		}*/
		var self= this;
		var url =ctx+"/shop/admin/goodsShipn!exchange.do?ajax=yes"+"&btn_action="+btn_action;
		Cmp.ajaxSubmit('common_form_e','',url,{},self.jsonBack,'json');
		
	},
	returned:function(btn_action){ //退货处理
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
		var url =ctx+"/shop/admin/shipn!returned.do?ajax=yes"+"&btn_action="+btn_action;
		Cmp.ajaxSubmit('common_form','',url,{},self.jsonBack,'json');
	},
	returnedc:function(btn_action){ //退货处理
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
		var url =ctx+"/shop/admin/goodsShipn!returned.do?ajax=yes"+"&btn_action="+btn_action;
		Cmp.ajaxSubmit('common_form_c','',url,{},self.jsonBack,'json');
	},
//	card_tansfer:function(){ //充值卡调拨 移植到card_list.js页面
//		
//		var len =$("[name='card_checkbox']:checked").length;
//		var card_idArr = [];
//		if(len ==0)
//		{
//			alert("请选择调拨的充值卡");
//			return false;
//		}
//		
//		$("[name='card_checkbox']:checked").each(function(){
//			var card_id = $(this).val();
//			card_idArr.push(card_id);
//			
//		})
//		var card_idstr =card_idArr.join(",");
//		var self= this;
//		var url =ctx+"/shop/admin/card!transfer.do?ajax=yes&cards_ids="+card_idstr;
//		Cmp.ajaxSubmit('order_form','',url,{},self.jsonBack,'json');
//	},
	custReturn_page_init:function(){ //资料返档
		
	},
	card_page_init:function(){ //充值卡页面inti操作
		var self =this;
		$("#card_query_form .comBtn").click(function(){//searchBtn 搜索
			var url =ctx+"/shop/admin/card!list.do?ajax=yes&orderId="+OrderDetail.orderid;
			Cmp.ajaxSubmit('card_query_form','order_w_dialog',url,{},OrderBtns.card_page_init);
			return false;
		});
	},
	cloud_page_init:function(){ //云卡页面inti操作
		var self =this;
		$("#cloud_query_form .comBtn").click(function(){//searchBtn 搜索
			var url =ctx+"/shop/admin/cloud!list.do?ajax=yes&orderId="+OrderDetail.orderid;
			Cmp.ajaxSubmit('cloud_query_form','order_w_dialog',url,{},OrderBtns.cloud_page_init);
			return false;
		});
	},
	accnbr_page_init:function(){ //号码调拨页面inti操作
		var self =this;
		$("#accnbr_query_form .comBtn").click(function(){//searchBtn 搜索
			var url =ctx+"/shop/admin/contract!list.do?ajax=yes&orderId="+OrderDetail.orderid;
			Cmp.ajaxSubmit('accnbr_query_form','order_w_dialog',url,{},OrderBtns.accnbr_page_init);
			return false;
		});
	},
	audit:function(){ //商品审核
		var self= this;
		var url =ctx+"/shop/admin/goodsAudit!audit.do?ajax=yes";
		Cmp.ajaxSubmit('common_form','',url,{},self.jsonBack,'json');
	},
	
	jsonBack:function(responseText){ //json回调函数
		//alert(responseText);
		//console.log(responseText);
		if(responseText.result==1){
			alert(responseText.message);
			Eop.Dialog.close("order_dialog");
			Eop.Dialog.close("order_w_dialog");
			window.parent.location.reload();
		}
		if(responseText.result==0){
			alert(responseText.message);
		}
	},
	initBtnStatus:function(){
	
	
	}
}

$(function(){
	OrderBtns.init();
})
