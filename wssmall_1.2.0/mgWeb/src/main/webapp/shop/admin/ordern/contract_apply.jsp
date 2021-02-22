<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/contract_apply.js"></script>
<div class="division">
 <form  class="validate" method="post" action="" id='contract_apply_form' validate="true">
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
		    <th>申请原因：</th>
		     <input type ='hidden' name='from_page' value ='contract_apply' />
		   	 <td colspan="3"><textarea value="" autocomplete="on"  required="true" rows="5" cols="50" name="orderAudit.apply_message"></textarea></td>
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
