<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form  class="validate" method="post" action="" id='order_bl_form' validate="true">
<div class="division">
<table cellspacing="0" cellpadding="0">
	<tbody>
		<tr>
			<th>订单号:</th>
			<td>${order.sn } 【${order.payStatus}】</td>
			<th>下单日期:</th>
			<td><html:dateformat pattern="yy-MM-dd hh:mm:ss" d_time="${order.create_time}"></html:dateformat></td>
		</tr>
		<tr>
			<th>订单总价:</th>
			<td>
			<fmt:formatNumber value="${order.order_amount }" type="currency" pattern="￥.00"/>
			</td>
			<th>配送费用:</th>
			<td><fmt:formatNumber value="${order.shipping_amount }" type="currency" pattern="￥.00"/></td>
		</tr>
		<tr>
			<th>收货人姓名:</th>
			<td>${order.ship_name}</td>
			<th>电话:</th>
			<td>${order.ship_tel}</td>
		</tr>
		<tr>
			<th>手机:</th>
			<td>${order.ship_mobile}</td>
			<th>邮政编码:</th>
			<td>${order.ship_zip}</td>
		</tr>
		<tr>
			<th>地址:</th>
			<td colspan="3">${ order.ship_addr}</td>
		</tr>
		<tr>
			<th>备注:</th>
			<td colspan="3"><textarea  style="width: 95%;" name="hint" type="textarea"></textarea></td>
		</tr>
	</tbody>
</table>
</div>

<div class="submitlist" align="center">
 <table>
	 <tr>
	 	<td >
           <input  type="button"  value="审核" class="submitBtn" name='bl'/>
	   </td>
	</tr>
 </table>
</div> 
</form>
<script>
$(function(){
	$("#shiptype").val("${order.shipping_type}");
});
</script>