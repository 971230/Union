<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="input" >
<form id="inventori_confirm_form"  method="post"  class="validate">
<div class="tableform">
<h4>盘点确认</h4>
<div class="division">
<table class="form-table"  cellspacing="0" cellpadding="0" border="0" width="100%">
	<tbody>
		<tr>
			<th>备注：</th>
			<td>
				<input type="hidden" name="inventory_id" value="${inventory_id }" />
				<textarea name="remark" style="width: 500px;height: 60px;"></textarea>
			</td>
		</tr>
		<tr>
			<th>确认动作：</th>
			<td>
				<select name="status" style="width: 120px;">
					<option value="1" selected="true">确认</option>
					<option value="-1">作废</option>
				</select>
			</td>
		</tr>
	</tbody>
</table>
</div>
</div>
<div class="submitlist" style="width: 100%;">
 <table style="width: 100%;">
 <tr>
 <td style="text-align: right;">
   <input  type="button" id="inventori_confirm_btn" value=" 提 交   " class="submitBtn" />
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   </td>
   </tr>
 </table>
</div>
</form>
</div>

