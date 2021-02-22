<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="rule_obj_form" >
<div class="searchformDiv">
<table class="form-table" cellspacing="0" cellpadding="0" border="0">
	<thead>
	<tr>
		<th style="text-align: left;font-size: 20px;">
			对
			象
			属
			性
			<input type="hidden" id="obj_attr_select_dlg" value="obj" />
		</th>
	</tr>
	</thead>
	<tbody style="background: white;">
	<tr id="obj_dialog_tr">
		<td>
		<c:forEach items="${objAttrList }" var="obj">
			<input type="radio" name="obj_attr_id" value="${ obj.attr_id}" attr_code="${obj.attr_code }" obj_id="${ obj.obj_id}" ele_type="${obj.ele_type }" stype_code="${obj.stype_code }" attr_name="${ obj.attr_name}"/>${ obj.attr_name}&nbsp;&nbsp;
		</c:forEach>
		</td>
	</tr>
	</tbody>
</table>		
</div>
</form>	