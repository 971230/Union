<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/css/order.css" type="text/css">

<div class="toolbar">
	<a class="sgreenbtn" href="javascript:void(0);" id="order_exception_btn">
	    <c:if test="${create_type==6 }">
	     	<span id="return_wp">出库</span>
	    </c:if>
	    <c:if test="${create_type==5 }">
	     	<span id="return_wp">入库</span>
	    </c:if>
	</a>
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
		<li id="hint">订单备注</li>
		<li id="exception_hint">订单异常备注</li>
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
		
		<div id="hintTab"></div>
		<div id="exception_hintTab"></div>
		
	</div>
	
	<div id='buttons'style='height:70px;margin-left:50px;'>
		
	</div>
	
</div>
<!-- 不包裹 -->
<div id="delivery_dialog">

</div>

<script>
var basePath = ctx+"/shop/admin/";
$(function(){
	DeliveryDetial.init("${delivery.delivery_id}","${delivery.order_id}","${create_type}");
});

var DeliveryDetial = {
	delivery_id:'',	
	orderid:'',
	create_type:'',
	init:function(delivery_id,order_id,create_type){
		this.delivery_id=delivery_id;
		this.orderid = order_id;
		this.create_type = create_type;
		var self = this;
		Eop.Dialog.init({id:"delivery_dialog",modal:true,title:"操作",width:"750px"});
		new Tab(".order_detail");
		
		$("#return_wp").bind("click",self.showReturnedDialog);
		
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
		$("#hint").click(function() {
			self.showOrdComments();
		});
		$("#exception_hint").click(function() {
			self.showOrdUnComments();
		});
		
		self.showBase();
	},showBase:function(){
		var self = this;
		$("#baseTab").load("wpdelivery!showBase.do?ajax=yes&delivery_id="+self.delivery_id,function(){
			
		});
	},showItems:function(){
		var self = this;
		//$("#itemsTab").load("wpdelivery!showItems.do?ajax=yes&delivery_id="+self.delivery_id,function(){	});
		$("#itemsTab").load(basePath + "ordern!items.do?ajax=yes&orderId="
				+ this.orderid);
	},showPayLog : function() {
		$("#payLogTab").load(basePath + "ordern!payLog.do?ajax=yes&orderId="
				+ this.orderid);
	},showShipLog : function() {
		$("#shipLogTab").load(basePath + "ordern!shipLog.do?ajax=yes&orderId="
				+ this.orderid);
	},showPmt : function() {
		$("#pmtTab").load(basePath + "ordern!pmt.do?ajax=yes&orderId="
				+ this.orderid);
	},showLog : function() {
		$("#logTab").load(basePath + "ordern!log.do?ajax=yes&orderId="
				+ this.orderid);
	},showRemark : function() {
		$("#remarkTab").load(basePath + "ordern!remark.do?ajax=yes&orderId="
				+ this.orderid);
		$("#save_remark").unbind("click");
		$("#save_remark").bind("click", function() {
					this.saveRemark(this.orderid);
				});
	},showOrdComments : function() {
		$("#hintTab").load(basePath + "ordcomment!list.do?ajax=yes&order_id="+ this.orderid);
	},showOrdUnComments : function() {
		$("#exception_hintTab").load(basePath + "ordcomment!listUnComments.do?ajax=yes&order_id="+ this.orderid);
	},showReturnedDialog:function(){
		var url = "wpdelivery!showReturnedDialog.do?ajax=yes&order_id="+DeliveryDetial.orderid+"&delivery_id="+DeliveryDetial.delivery_id+"&create_type="+DeliveryDetial.create_type;
		Eop.Dialog.open("delivery_dialog");
		$("#delivery_dialog").load(url,function(){
			$("input[name='retuned_wp_c']").unbind("click").bind("click",function(){
				var url =ctx+"/shop/admin/wpdelivery!confirmReturned.do?ajax=yes";
				Cmp.ajaxSubmit('wp_fh_form','',url,{},function(data){
					alert(data.msg);
					if(data.status==1){
						Eop.Dialog.close("delivery_dialog");
						$("#order_exception_btn").hide();
					}
					$.Loading.hide();
				},'json');
			});
		});
	}
};
</script>