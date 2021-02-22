var OrderStatus = {};
var OrderDetail = {
	orderid : undefined,
	orderStatus : undefined,
	payStatus : undefined,
	shipStatus : undefined,
	init : function(orderid, orderStatus, payStatus, shipStatus) {
		// 初始化订单的状态
		this.orderStatus = orderStatus;
		this.payStatus = payStatus;
		this.shipStatus = shipStatus;
		Eop.Dialog.init({id : "order_dialog",modal : true,title : "订单操作",width : "750px"});
		Eop.Dialog.init({id : "order_w_dialog",modal : true,title : "订单操作",	width : "750px"});
		//网银支付界面
		Eop.Dialog.init({id : "pay_dialog",modal : true,title : "订单操作",	width : "600px"});
		Eop.Dialog.init({id : "order_msg_dialog",modal : true,title : "订单操作",	width : "750px"});
		var self = this;
		this.basePath = basePath;
		this.orderid = orderid;
		new Tab(".order_detail");
		this.bindTabEvent();
		self.showBase();
		this.bindOperBtns();

	},
	/**
	 * 绑定tab事件
	 */
	bindTabEvent : function() {
		var self = this;
		$("#base").click(function() {
					self.showBase();
				});
		$("#yuyue").click(function(){
			self.showYuyue();
		});
		$("#items").click(function() {
					self.showItems();
				});
		$("#payLog").click(function() {
					self.showPayLog();
				});
		$("#shipLog").click(function() {
					self.showShipLog();
				});
		$("#pmt").click(function() {
					self.showPmt();
				});
		$("#log").click(function() {
					self.showLog();
				});
		$("#remark").click(function() {
					self.showRemark();
				});
		$("#auditLog").click(function() {
					self.showAuditLog();
				});
		
		$("#cloudLi").length>0 && ($("#cloudLi").click(function() {self.showCloudLog();}));
		$("#cardLi").length>0 && ($("#cardLi").click(function() {self.showCardLog();}));
				
		$("#hint").click(function() {
			self.showOrdComments();
		});
		$("#exception_hint").click(function() {
			self.showOrdUnComments();
		});
		
		$("#order_exception_btn").click(function() {
			self.orderExceptionDialog();
		});
		
		$("#order_cancel_btn").click(function() {
			self.showCancelDialog();
		});
	},
	/**
	 * 显示操作按钮信息
	 */
	bindOperBtns : function() {
		
		$("#buttons").load(
				basePath + "ordern!showbtns.do?ajax=yes&orderId="
						+ this.orderid, {}, function() {
						$("#buttons").show();
						if($("#buttons").html() ==''){
								$("#buttons").hide();
						}
				});
	},

	saveRemark : function(orderId) {
		var self = this;
		$("#remark_form").ajaxSubmit({
					url : basePath + 'order!saveRemark.do?ajax=yes',
					dataType : "json",
					type : 'POST',
					success : function(responseText) {
						if (responseText.result == 1) {
							alert(responseText.message);
						}
						if (responseText.result == 0) {
							alert(responseText.message);
							self.bindFlowEvent();
							self.showRemark(orderId);
						}
					},
					error : function() {
						alert("出错了:(");
					}
				});
	},
	/**
	 * 显示订单基本信息
	 */
	showBase : function() {
		var self = this;
		$("#baseTab").load(
				basePath + "order!base.do?ajax=yes&orderId=" + this.orderid
						+ "&rmd=" + new Date().getTime(), function() {

					$("#editPriceBtn").click(function() {
								self.showpriceinput($(this));
							});
					$("#editShippingPriceBtn").bind("click",function(){
						$("#order_shipping_price_div").hide();
						$("#order_shipping_price_edit_div").show();
					});
					$("#shipping_edit_btn").bind("click",function(){
						var price = $("#shipping_edit_ip").val();
						var orderid = $("#orderid").val();
						$.ajax({
							url : basePath + 'orderdw!updateShipPirce.do?ajax=yes',
							data : "shipPrice=" + price + "&orderId=" + orderid,
							type : 'POST',
							dataType : 'json',
							success : function(result) {
								if (result.result == 1) {
									$("#order_shipping_price_div").show();
									$("#order_shipping_price_edit_div").hide();
									var odm = parseFloat($("#od_amount_val").html());
									var opm = parseFloat($("#od_shipint_val").html());
									$("#order_shipping_price").html("￥"+price);
									var eodm = odm+parseFloat(price-opm);
									$("#order_price_span").html("￥"+eodm);
									$("#od_amount_val").html(eodm);
									$("#od_shipint_val").html(price);
									alert(result.message);
								} else {
									alert(result.message);
								}
							},
							error : function(e,b) {
								alert("修改出错");
							}

						});
					});
				});
	},
	showpriceinput : function(btn) {
		var self = this;
		var orderid = $("#orderid").val();
		var price_span = $("#order_price_span");
		var price = price_span.html().replace('￥', '');
		var price_input = $("<input type='text' style='width:60px' value='"
				+ price + "' id='price_input' name='price' />");
		price_span.empty();
		price_span.append(price_input);
		btn.html("确定");
		btn.unbind("click");
		btn.bind("click", function() {
					self.savePrice(price_input.val(), orderid);
				});
	},
	savePrice : function(price, orderid) {
		var self = this;
		$.ajax({
					url : basePath + 'order!savePrice.do?ajax=yes',
					data : "price=" + price + "&orderId=" + orderid,
					type : 'POST',
					dataType : 'json',
					success : function(result) {
						if (result.result == 1) {
							$("#order_price_span").html("￥" + price);
							$("#editPriceBtn").html("修改");
							$("#editPriceBtn").unbind("click");
							$("#editPriceBtn").bind("click", function() {
										self.showpriceinput($(this));
									});
						} else {
							alert("保存订单价格出错");
						}
					},
					error : function(e) {
						alert("保存订单价格出错" + e);
					}

				});
	},
	/**
	 * 显示预约信息
	 * */
	showYuyue : function(){
		$("#yuyueTab").load(basePath + "ordern!listYuyue.do?ajax=yes");
	},
	/**
	 * 显示订单货物信息
	 */
	showItems : function() {
		$("#itemsTab").load(basePath + "ordern!items.do?ajax=yes&orderId="
				+ this.orderid);
	},
	/**
	 * 显示订单备注
	 */
	showOrdComments : function() {
		$("#hintTab").load(basePath + "ordcomment!list.do?ajax=yes&order_id="+ this.orderid);
	},
	/**
	 * 显示订单备注
	 */
	showOrdUnComments : function() {
		$("#exception_hintTab").load(basePath + "ordcomment!listUnComments.do?ajax=yes&order_id="+ this.orderid);
	},
	orderExceptionDialog : function(price, orderid) {
		Eop.Dialog.open("order_msg_dialog");
		$("#order_msg_dialog").empty().load(basePath + "ordcomment!showExceptionDialog.do?ajax=yes&order_id="+ this.orderid,function(){
			$("#order_exception_btn_submit").unbind("click");
			$("#order_exception_btn_submit").bind("click",function(){
				var url =basePath+"/shop/admin/ordcomment!orderException.do?ajax=yes";
				Cmp.ajaxSubmit('order_exception_form','',url,{},function (responseText){
					if(responseText.result==1){
						alert(responseText.message);
						Eop.Dialog.close("order_msg_dialog");
						//window.location.reload();
					}
					if(responseText.result==0){
						alert(responseText.message);
					}
				},'json');
			});
		});
	},
	showCancelDialog:function(){
		Eop.Dialog.open("order_msg_dialog");
		$("#order_msg_dialog").empty().load(basePath + "ordcomment!showCancelDialog.do?ajax=yes&order_id="+ this.orderid,function(){
			$("#order_cancel_btn_submit").unbind("click");
			$("#order_cancel_btn_submit").bind("click",function(){
				var url =basePath+"/shop/admin/ordcomment!cancelOrder.do?ajax=yes";
				Cmp.ajaxSubmit('order_cancel_form','',url,{},function (responseText){
					if(responseText.result==1){
						alert(responseText.message);
						Eop.Dialog.close("order_msg_dialog");
						window.location.reload();
					}
					if(responseText.result==0){
						alert(responseText.message);
					}
				},'json');
			});
		});
	},
	/**
	 * 显示支付日志
	 */
	showPayLog : function() {
		$("#payLogTab").load(basePath + "ordern!payLog.do?ajax=yes&orderId="
				+ this.orderid);
	},

	/**
	 * 显示货运日志
	 */
	showShipLog : function() {
		$("#shipLogTab").load(basePath + "ordern!shipLog.do?ajax=yes&orderId="
				+ this.orderid);
	},

	/**
	 * 显示优惠方案
	 */
	showPmt : function() {
		$("#pmtTab").load(basePath + "ordern!pmt.do?ajax=yes&orderId="
				+ this.orderid);
	},

	/**
	 * 显示订单日志
	 */
	showLog : function() {
		$("#logTab").load(basePath + "ordern!log.do?ajax=yes&orderId="
				+ this.orderid);
	},
	showAuditLog : function() {
		$("#auditLogTab").load(basePath
				+ "ordern!auditlog.do?ajax=yes&orderId=" + this.orderid);
	},
	showCloudLog : function() { //展示云卡调拨记录
		$("#cloudLiTab").load(basePath
				+ "ordern!cloudlog.do?ajax=yes&orderId=" + this.orderid);
	},
	showCardLog : function() { //展示充值卡调拨记录
		$("#cardLiTab").load(basePath
				+ "ordern!cardlog.do?ajax=yes&orderId=" + this.orderid);
	},		
				
	showRemark : function() {
		$("#remarkTab").load(basePath + "ordern!remark.do?ajax=yes&orderId="
				+ this.orderid);

		$("#save_remark").unbind("click");
		$("#save_remark").bind("click", function() {
					this.saveRemark(this.orderid);
				});
	},
	showFlowList: function(delivery_id,logi_id,log_no,openDialog){
		var self = this;
		$("#order_w_dialog").empty();
		if(openDialog)
			Eop.Dialog.open("order_w_dialog");
		var url = basePath + "flown!listFlow.do?ajax=yes&deliveryID="+delivery_id+"&logi_id="+logi_id+"&logi_no="+log_no;
		$("#order_w_dialog").load(url,function(){
			$("#order_w_dialog [name='flow_add_show']").unbind("click");
			$("#order_w_dialog [name='flow_add_show']").bind("click",function(){
				self.showFlowAdd(delivery_id,logi_id)
			});
		});
	},
	showFlowAdd: function(delivery_id,logi_id,openDialog){
		var self = this;
		$("#order_w_dialog").empty();
		if(openDialog)
			Eop.Dialog.open("order_w_dialog");
		var url = basePath + "ordern/commonage/flow_add.jsp?ajax=yes&deliveryID="+delivery_id+"&logi_id="+logi_id;
		$("#order_w_dialog").load(url,function(){
			$("#order_w_dialog [name='flow_add_bt']").unbind("click");
			$("#order_w_dialog [name='flow_add_bt']").bind("click",function(){
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
				var url =basePath+"flown!addFlow.do?ajax=yes";
				Cmp.ajaxSubmit('order_flow_form','',url,{},function(data){
					alert(data.message);
					self.showFlowList(delivery_id,logi_id);
				},'json');
			});
		});
	}
};

function isdigit(s) {
	var r, re;
	re = /\d*/i; // \d表示数字,*表示匹配多个数字
	r = s.match(re);
	return (r == s);
}