<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String deliveryID = request.getParameter("deliveryID");
%>
<form  class="validate" method="post" action="" id='order_flow_form' validate="true">
<input type="hidden" id="delivery_id" name="flow.delivery_id" value="<%=deliveryID %>" />
<div class="division">
<table cellspacing="0" cellpadding="0" style="width:100%;">
	<tbody>
		<tr id="flowInfo">
			<th>物流公司名称:</th>
			<td><input id="flowloginame" type="text" class="ipttxt"  style="width: 95%;" value="" name="flow.logi_name" autocomplete="off"></td>
		</tr>
		<tr id="flowInfo">
			<th>物流描述:</th>
			<td>
				<textarea id="flowdescription" style="width: 95%;" name="flow.description" type="textarea"></textarea>
			</td>
		</tr>
	</tbody>
</table>
</div>

<div class="submitlist" align="center">
 <table>
	 <tr>
	 	<td >
           <input  type="button"  value="新增" class="submitBtn" name='flow_add_bt'/>
	   </td>
	</tr>
 </table>
</div> 
</form> 
