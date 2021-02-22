<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/js/OrderNDetail.js">
</script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/css/order.css" type="text/css">
<div class="toolbar">
	<c:if test="${ord.status==0 || ord.status==1 || ord.status==2}">
		<a class="sgreenbtn" href="javascript:void(0);" id="order_cancel_btn">
			 <span id="refOrgBtn">订单取消</span>
		</a>
	</c:if>
	<%-- <c:if test="${ord.status!=0 && ord.status!=1 && ord.status!=2 && ord.status!=-10}"> 
	<a class="sgreenbtn" href="javascript:void(0);" id="order_exception_btn">
		 <span id="refOrgBtn">订单异常</span>
	</a>--%>
	<%-- </c:if> --%>
	<%-- <c:if test="${ord.order_record_status==0}">
	<a class="sgreenbtn" href="javascript:void(0);">
		 <span id="refOrgBtn">订单暂停</span>
	</a>
	</c:if>
	<c:if test="${ord.order_record_status==1}">
	<a class="sgreenbtn" href="javascript:void(0);">
		 <span id="refOrgBtn">订单恢复</span>
	</a>
	</c:if>
	<c:if test="${ord.status==-10 || ord.order_record_status==0}">
	<a class="sgreenbtn" href="javascript:void(0);">
		 <span id="refOrgBtn">订单编辑</span>
	</a>
	</c:if> --%>
	<c:if test="${ord.status==2&& ord.canPay=='yes'}">
	<a class="sgreenbtn" href="javascript:void(0);" name="BUTTON_CARD_PAY" payment_id="${ord.payment_id }">
		 <span id="refOrgBtn">支付</span>
	</a>
	</c:if>
	<c:if test="${ord.status==3 && ord.canShip=='yes'}">
	<a class="sgreenbtn" href="javascript:void(0);" name="BUTTON_CUST_ACCEPT_C">
		 <span id="refOrgBtn">备货</span>
	</a>
	</c:if>
	<c:if test="${(ord.status==4 || ord.ship_status==4) && ord.canShip=='yes'}">
	<a class="sgreenbtn" href="javascript:void(0);" name="BUTTON_SHIPPING_C">
		 <span id="refOrgBtn">发货</span>
	</a>
	</c:if>
	<c:if test="${ord.status==5 && ord.ship_status==1}">
	<a class="sgreenbtn" href="javascript:void(0);" name="BUTTON_GET_SHIPPING_C">
		 <span id="refOrgBtn">确认收货</span>
	</a>
	</c:if>
	<c:if test="${ord.status==6}">
	<a class="sgreenbtn" href="javascript:void(0);" name="BUTTON_FINISHED_C">
		 <span id="refOrgBtn">完成</span>
	</a>
	</c:if>
	<c:if test="${returned}">
	<a class="sgreenbtn" href="javascript:void(0);" name="BUTTON_RETURNED_SHIPPING_C">
		 <span id="refOrgBtn">退货</span>
	</a>
	</c:if>
	<c:if test="${refund}">
	<a class="sgreenbtn" href="javascript:void(0);" name="BUTTON_REFUND_C">
		 <span id="refOrgBtn">退费</span>
	</a>
	</c:if>
	<c:if test="${exchange}">
	<a class="sgreenbtn" href="javascript:void(0);" name="BUTTON_CHANGE_SHIPPING">
		 <span id="refOrgBtn">换货</span>
	</a>
	</c:if>
</div>	
<div style="display: block;" class="order_detail">
	<div class="tab-bar" style="position: relative;">
	<ul class="tab">
		<li id="base" class="active">基本信息</li>
		<li id="yuyue">预约信息</li>
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
		<div id="yuyueTab"></div>
		<div id="itemsTab"></div>
		<div id="payLogTab"></div>
		<div id="shipLogTab"></div>
		<div id="pmtTab"></div>
		<div id="logTab"></div>
		<div id="remarkTab"></div>
		<div id="hintTab"></div>
		<div id="exception_hintTab"></div>
	</div>
	<div id='buttons' style='height:70px;margin-left:50px;'>
	</div>
</div>
<!-- 不包裹 -->
<div id="order_w_dialog">
</div>
<div id="pay_dialog">
</div>
<div id='wrapper'>
</div>
<div id='order_msg_dialog'>
</div>
<!-- 统一包裹 -->
<div id="order_dialog">
<form id="order_form" class="validate">
<input type="hidden" id="orderid" name="orderId" value="${orderId}" />
<div class="con">
</div>
<div class="footContent" >
<div style="width: 200px; height: 40px; margin: 0pt auto;"
	class="mainFoot">
<table style="margin: 0pt auto; width: auto;">
	<tbody>
		<tr>
			<td><b class="save">
			<button class="submitBtn">提交</button>
			</b></td>
		</tr>
	</tbody>
</table>
</div>
</div> 
</form>
</div>
<!-- 银行支付 -->
<jsp:include page="/shop/admin/pay/to_pay.jsp" flush="true" >
	<jsp:param name="level_path" value="" />
</jsp:include>
<script>
$(function(){
	$("body").focus();
	OrderDetail.init('${orderId}','${ord.status}','${ord.pay_status}','${ord.ship_status}');
});
function initCity(){
	$("#address_city_id").hide();
	$("#address_region_id").hide();
	$("#address_province_id").empty();
	$("<option value='-1'>请选择...</option>").appendTo($("#address_province_id"));
	<c:forEach items="${provinceList}" var="province" >
		$("<option value='${(province.region_id)}' >${province.local_name}</option>").appendTo($("#address_province_id"));
	</c:forEach>
	$("#address_province_id").change(function(){
		$("#address_province").val($("#address_province_id option:selected").text());
		$("#address_city_id").empty();
		$("#address_city_id").hide();
		$("#address_region_id").empty();
		$("#address_region_id").hide();
		$.ajax({
			method:"get",
			url:"../shop/area!list_city.do?province_id=" + $("#address_province_id").attr("value"),
			dataType:"html",
			success:function(result){
				$("#address_city_id").show();
				$(result).appendTo($("#address_city_id"));
			},
			error:function(){
				alert("异步失败");
			}
		});
	});
	$("#address_city_id").change(function(){
		$("#address_city").val($("#address_city_id option:selected").text());
		$("#address_region_id").empty();
		$("#address_region_id").hide();
		$.ajax({
			method:"get",
			url:"../shop/area!list_region.do?city_id=" + $("#address_city_id").attr("value"),
			dataType:"html",
			success:function(result){
				$("#address_region_id").show();
				$(result).appendTo($("#address_region_id"));
			},
			error:function(){
				alert("异步失败");
			}
		});
	});
	$("#address_region_id").change(function(){
		$("#address_region").val($("#address_region_id option:selected").text());
	});
}
</script>