<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form  class="validate" method="post" action="" id='common_form' validate="true">
<input type="hidden" id="orderId" name="orderId" value="${orderId}" />
<div class="division">
<table width="100%">
  <tbody>
  <tr>
    <th>订单号：</th>
    <td>${order.order_id} 【${order.orderStatus}】</td>
    <th>订单总金额：</th>
	<td>￥<fmt:formatNumber value="${order.order_amount}" maxFractionDigits="2"  /></td>
  <tr>
  <tr>
    <th>申请人：</th>
    <td colspan="3">${order.user_name}</td>
   </tr>
    <tr>
    <th >申请原因：</th>
	<td colspan="3">${apply_desc}</td>
  <tr>
    <th>审核原因：</th>
        <td colspan="3"><textarea value="" autocomplete="on"  required="true" rows="10" cols="40" name="goodsAudit.audit_desc"></textarea></td>
    </tr>
    <tr>
    <th>处理状态：</th>
       <td colspan="3" required="true">
       		<select  class="ipttxt"  name='goodsAudit.audit_state' autocomplete="on">
       		<option value=''>请选择</option>
       		<option value='1'>审核通过</option>
       		<option value='2'>审核不通过</option>
       		</select>
       </td>
    </tr>
    </tbody>
    </table>
</div>
<div class="submitlist" align="center">
 <table>
	 <tr>
	 	<td >
           <input  type="button"  value="提交" class="submitBtn" name='auditBtn'/>
	   </td>
	</tr>
 </table>
</div> 
</form> 