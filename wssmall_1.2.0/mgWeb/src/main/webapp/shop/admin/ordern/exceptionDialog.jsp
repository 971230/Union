<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form  class="validate" method="post" action="" id='order_exception_form' validate="true">
<input type="hidden" id="orderId" name="orderId" value="${order.order_id}" />
<div class="division">
<table width="100%">
  <tbody>
   <tr>
    <th>异常信息：</th>
        <td colspan="3">
        	<input type="hidden" name="order_id" value="${order_id }" />
        	<textarea value="" autocomplete="on"  required="true" rows="10" cols="40" name="unComments.comments"></textarea>
        </td>
    </tr>
    </tbody>
    </table>
</div>
<div class="submitlist" align="center">
 <table>
	 <tr>
	 	<td >
           <input  type="button"  value="确认" class="submitBtn" id='order_exception_btn_submit'/>
	   </td>
	</tr>
 </table>
</div> 
</form> 
