<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/js/OrderNDetail.js">

</script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/css/order.css" type="text/css">

	
	
<div style="display: block;" class="order_detail">
	<div class="tab-bar" style="position: relative;">
	<ul class="tab">
		<li id="base" class="active">基本信息</li>
		<li id="items">商品</li> 
		<li id="payLog">收退款记录</li>
		<li id="shipLog">发退货记录</li>
		<!-- 
		<li id="pmt">优惠方案</li>
		 -->
		<li id="log">订单日志</li>
		<!-- <li id="remark">订单附言</li>-->
		<c:if test="${ord.type_code=='CONTRACT'}">
			<li id="auditLog">合约机审核日志</li>
		</c:if>
		<c:if test="${ord.type_code=='CLOUD'}">
			<li id="cloudLi">云卡调拨记录</li>
		</c:if>
		<c:if test="${ord.type_code=='CARD' || ord.type_code=='TIME_CARD' || ord.type_code=='RECHARGE_CARD'}">
			<li id="cardLi">卡调拨记录</li>
		</c:if>
		
	</ul>
	</div>
	
	<div class="tab-page">
		<div id="baseTab"></div>
		<div id="itemsTab"></div>
		<div id="payLogTab"></div>
		<div id="shipLogTab"></div>
		<!-- 
		<div id="pmtTab"></div>
		 -->
		<div id="logTab"></div>
		<!--<div id="remarkTab"></div>-->
		
		<c:if test="${ord.type_code=='CONTRACT'}">
			<div id="auditLogTab"></div>
		</c:if>
		<c:if test="${ord.type_code=='CLOUD'}">
			<div id="cloudLiTab"></div>
		</c:if>
		<c:if test="${ord.type_code=='CARD' || ord.type_code=='TIME_CARD' || ord.type_code=='RECHARGE_CARD'}">
			<div id="cardLiTab"></div>
		</c:if>
		
	</div>
	
	<div id='buttons'style='height:70px;margin-left:50px;'>
		
	</div>
	
</div>
<!-- 不包裹 -->
<div id="order_w_dialog">

</div>
<div id="pay_dialog">

</div>
<div id='wrapper'>
	
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
	OrderDetail.init('${orderId}',${ord.status},${ord.pay_status},${ord.ship_status});
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