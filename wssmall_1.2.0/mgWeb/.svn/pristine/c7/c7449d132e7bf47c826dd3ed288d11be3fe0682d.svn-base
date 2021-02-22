<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/css/order.css" type="text/css">
<script type="text/javascript" src="js/purchase_order_list.js"></script>

<div class="toolbar">
	<c:if test="${ord.create_type==7 && ord.ship_status!=1}">
	<a class="sgreenbtn" href="javascript:void(0);" name="confirm_returned_wp_7">
		 <span id="refOrgBtn">确认出货</span>
	</a>
	</c:if>
	
	<c:if test="${ord.create_type==5 && ord.ship_status!=1 && warehousePurorder.audit_status==1 && store_action_type!=0}">
	<a class="sgreenbtn" href="javascript:void(0);" name="confirm_returned_wp_7">
	     	<span id="refOrgBtn">入库</span>
	</a>
	</c:if>
	
	<c:if test="${warehousePurorder.audit_status eq '0'}">
		<a class="sgreenbtn" href="javascript:void(0);" name="confirm_audit_state" pru_order_id="${warehousePurorder.pru_order_id}">
			 <span id="auditOrgBtn" >审核</span>
		</a>
	</c:if>
</div>	
	
<div style="display: block;" class="order_detail">
	<div class="tab-bar" style="position: relative;">
	<ul class="tab">
		<li id="base" class="active">基本信息</li>
		<li id="items">订单明细</li> 
		<li id="payLog">收退款记录</li>
		<li id="shipLog">发退货记录</li>
		<li id="pmt">优惠方案</li>
		<li id="log">订单日志</li>
		<li id="remark">订单附言</li>
	</ul>
	</div>
	
	<div class="tab-page">
		<div id="baseTab"></div>
		<div id="itemsTab"></div>
		<div id="payLogTab"></div>
		<div id="shipLogTab"></div>
		
		<div id="pmtTab"></div>
		
		<div id="logTab"></div>
		<div id="remarkTab"></div>
		
	</div>
	
	<div id='buttons'style='height:70px;margin-left:50px;'>
		
	</div>
	
</div>
<!-- 不包裹 -->
<div id="delivery_dialog">

</div>
<div id="showPurchaseOrderDlg"></div>
<script>
var basePath = ctx+"/shop/admin/";
$(function(){
	DeliveryDetial.init('${orderId}','${ord.create_type}');
});

var DeliveryDetial = {
	create_type:'',	
	orderid:'',
	init:function(order_id,create_type){
		this.orderid = order_id;
		this.create_type=create_type;
		var self = this;
		new Tab(".order_detail");
		
		Eop.Dialog.init({id:"delivery_dialog",modal:true,title:"操作",width:"750px"});
		Eop.Dialog.init({id:'showPurchaseOrderDlg',modal:false,title:"审核",width:'850px'});
		
		$("a[name='confirm_returned_wp_7']").bind("click",function(){
			self.showReturnedDialog();
		});
		
		$("a[name='confirm_audit_state']").bind("click",function(){
			var pru_order_id=$(this).attr("pru_order_id");
			
			var url=app_path+'/shop/admin/purchaseOrderAction!showaudit.do?ajax=yes&pru_order_id='+pru_order_id;
	    	purchaseOrderList.toUrlOpen(url,"showPurchaseOrderDlg");
		});
		
		$("#base").bind("click",function(){
			self.showBase();
		});
		$("#items").bind("click",function(){
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
		
		self.showBase();
	},
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
	showItems:function(){
		var self = this;
		//$("#itemsTab").load("wpdelivery!showItems.do?ajax=yes&delivery_id="+self.delivery_id,function(){	});
		$("#itemsTab").load(basePath + "ordern!items.do?ajax=yes&orderId="
				+ this.orderid);
	},
	showPayLog : function() {
		$("#payLogTab").load(basePath + "ordern!payLog.do?ajax=yes&orderId="
				+ this.orderid);
	},
	showShipLog : function() {
		$("#shipLogTab").load(basePath + "ordern!shipLog.do?ajax=yes&orderId="
				+ this.orderid);
	},
	showPmt : function() {
		$("#pmtTab").load(basePath + "ordern!pmt.do?ajax=yes&orderId="
				+ this.orderid);
	},
	showLog : function() {
		$("#logTab").load(basePath + "ordern!log.do?ajax=yes&orderId="
				+ this.orderid);
	},
	showRemark : function() {
		$("#remarkTab").load(basePath + "ordern!remark.do?ajax=yes&orderId="
				+ this.orderid);
		$("#save_remark").unbind("click");
		$("#save_remark").bind("click", function() {
					this.saveRemark(this.orderid);
				});
	},showReturnedDialog:function(){
		var url = ctx+"/shop/admin/wpdelivery!showReturnedDialog.do?ajax=yes&order_id="+DeliveryDetial.orderid+"&create_type="+DeliveryDetial.create_type;
		Eop.Dialog.open("delivery_dialog");
		$("#delivery_dialog").load(url,function(){
			$("input[name='retuned_wp_c']").unbind("click").bind("click",function(){
				var url =ctx+"/shop/admin/wpdelivery!confirmReturned.do?ajax=yes";
				Cmp.ajaxSubmit('wp_fh_form','',url,{},function(data){
					alert(data.msg);
					if(data.status==1){
						Eop.Dialog.close("delivery_dialog");
					}
					$.Loading.hide();
					location.reload();
				},'json');
			});
		});
	}
};
</script>