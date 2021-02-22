<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form  class="validate" method="post" action="" id='order_sh_form' validate="true">
<input type="hidden" id="orderId" name="orderId" value="${order.order_id}" />
<div class="division">
<table width="100%">
  <tbody>
  <tr>
    <th>订单号：</th>
    <td>${order.order_id} 【${order.orderStatus}】</td>
    <th>订单总金额：</th>
	<td>￥<fmt:formatNumber value="${order.order_amount}" maxFractionDigits="2"  /></td>
  <tr>
  <!-- tr>
    <th>购买人：</th>
    <td colspan="3">${order.user_name}</td>
   </tr -->
   <tr>
        <td colspan="4">
        	<table style="width: 100%;">
        	<tr>
        	   <th>商品名称</th><th>商品单价</th><th>购买数量</th>
        	</tr>   
        	   <c:forEach var="item" items="${itemList }">
        	   		<tr><td>${item.name }</td><td>${item.price }</td><td>${item.num }</td></tr>
        	   </c:forEach>
        	</table>
        </td>
    </tr>
    <th>备注：</th>
        <td colspan="3">
        	<textarea value="" autocomplete="on"  required="true" rows="10" cols="40" name="hint"></textarea>
        </td>
    </tr>
    </tbody>
    </table>
</div>
<div class="submitlist" align="center">
 <table>
	 <tr>
	 	<td >
           <input  type="button"  value="确收备货" class="submitBtn" name='sh'/>
	   </td>
	</tr>
 </table>
</div> 
</form> 