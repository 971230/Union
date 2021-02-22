<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/refund_apply.js"></script>
<div class="division">
 <form  class="validate" method="post" action="" id='apply_form' validate="true">
 <input type="hidden" id="orderId" name="orderId" value="${orderId}" />
	<table width="100%">
	  <tbody>
		  <tr>
		    <th>订单号：</th>
		    <td>${order.order_id} 【${order.orderStatus}】</td>
		    <th>订单总金额：</th>
			<td>￥<fmt:formatNumber value="${order.order_amount}" maxFractionDigits="2"  /></td>
		  </tr>
		  <tr>
		    <th>退费申请原因：</th>
		   	 <td colspan="3"><textarea value="" autocomplete="on"  required="true" rows="10" cols="40" name="orderAudit.apply_message"></textarea></td>
		  </tr>
	    </tbody>
	</table>
	<div class="submitlist" align="center">
	 <table>
		 <tr>
		 	<td >
	           <input  type="button"  value="提交" class="submitBtn" name='submitBtn'/>
	           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
		   </td>
		</tr>
	 </table>
	</div>
</form>
</div>
