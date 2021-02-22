<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<style>
<!--
.noborder {
    border-style: none;
}
-->
</style>

<div class="input" >
	<form class="validate" method="post" id="suspendform" validate="true">
	<div>
		<div style="margin-top:5px;">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
				<tr><td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp外呼类型:</td>
				<td><html:selectdict name="outcall_type" id="outcall_type" curr_val="${outcall_type}" style="color:#000" attr_code="DC_OUTCALL_TYPE"></html:selectdict></td></tr>
				<tr>
					<th>外呼申请原因：</th>
					<td>
						<textarea rows="5" cols="88" id="outcall_reason" name="outcall_reason"></textarea>
					</td>
				</tr>
				</tbody>
			</table>
		</div>
		<div class="pop_btn" align="center">
			<a id="savebtn" class="blueBtns"><span>保 存</span></a>
		</div>
		<input id="order_id" type="hidden" value="${order_id }"/>
	</div>
	</form>
</div>

<script>
$(function() {
	$("#savebtn").click(function() {
		if($("#outcall_reason").val() == ""){
			alert("请输入外呼申请原因！");
			return false;
		}
		var order_id = $("#order_id").val();
		var url = ctx+"/shop/admin/ordAuto!save_outCallApply.do?ajax=yes&order_id="+order_id;
		Cmp.ajaxSubmit('suspendform', '', url, {}, function(responseText) {
			if (responseText.result == 0) {
				alert(responseText.message);
			} else {
				alert(responseText.message);
			}
			closeDialog();
		}, 'json');
	});
});
</script>
