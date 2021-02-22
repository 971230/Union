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
	  	<tr>
	    <th>申请时间：</th>
	   	 <td colspan="1"><span>${orderAudit.create_date}</span></td>
	    <tr>
	    <th>申请原因：</th>
	   	 <td colspan="2"><span>${orderAudit.apply_message}</span></td>
	  </tr>
	  </tr>
	
	  <c:if test="${orderAudit.deal_message!=null}">
		  <tr>
		    <th>审核结果：</th>
		   	 <td colspan="3"><span>${orderAudit.deal_message}</span></td>
		  </tr>
	 </c:if>
	 <c:if test="${orderAudit.mgr_deal_message!=null}">  
		  <tr>
		    <th>审核结果：</th>
		   	 <td colspan="3"><span>${orderAudit.mgr_deal_message}</span></td>
		  </tr>
	  </c:if>
      <tr>
	    <th>审核处理：</th>
	   	 <td colspan="3"><textarea value="" autocomplete="on"  required="true" rows="10" cols="40" name="orderAudit.p_audit_message"></textarea></td>
	  </tr>
	  <tr>
		<th>处理：</th>
		<td colspan="3" required="true">
			<select  class="ipttxt"  name='orderAudit.p_audit_state' autocomplete="on">
				<option value=''>
					请选择
				</option>
				<option value='1'>
					审核通过
				</option>
				<option value='2'>
					审核不通过
				</option>
			</select>
		</td>
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
