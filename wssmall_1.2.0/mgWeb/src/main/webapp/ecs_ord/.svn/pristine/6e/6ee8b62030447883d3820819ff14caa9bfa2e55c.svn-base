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
	<form class="validate" method="post" id="exceptionform" validate="true">
	<div>
		<div style="margin-top:5px;">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
				<tr>
					<th>异常类型：</th>
					<td>
						<select name="exception_id" id="exception_id">
							<option value="-1">请选择异常类型</option>
							<c:forEach var="exception" items="${exceptionList }" varStatus="var_status">
								<c:if test="${exception.codea!='hide' }">
									<option value="${exception.pkey }">${exception.pname }</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<th>是否需要回访：</th>
					<td>
						<select name="need_customer_visit" id="need_customer_visit">
							<c:forEach var="is_or_no" items="${is_or_no_list }">
								<option value="${is_or_no.pkey }" ${is_or_no.pkey=='0'?'selected':'' }>${is_or_no.pname }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>备注：</th>
					<td colspan="3">
						<textarea rows="5" cols="88" name="exception_remark" id="exception_remark"></textarea>
					</td>
				</tr>
				</tbody>
			</table>
		</div>
		<div class="pop_btn" align="center">
			<a id="savebtn" class="blueBtns"><span>保 存</span></a>
		</div>
		<input id="order_ids" type="hidden" value="${order_ids }"/>
		<input id="abnormal_type" name="abnormal_type" type="hidden" value="1"/>
		<input id="is_rg_exception" name="is_rg_exception" type="hidden" value="${is_rg_exception }"/>
	</div>
	</form>
</div>

<script>
$(function(){
	$("#savebtn").bind("click", function() {
		var exception_id = $("#exception_id").val();
		var exception_remark = $("#exception_remark").val();
		var abnormal_type = $("#abnormal_type").val();
		var order_ids = $("#order_ids").val();
		if (exception_id == "-1") {
			alert("请选择异常类型！");return false;
		}
		//shop/admin/orderExp!list.do?eiqInner.record_status=0
		var url = ctx+"/shop/admin/ordAuto!addBatchException_save.do?ajax=yes&order_ids="+order_ids;
		Cmp.ajaxSubmit('exceptionform', '', url, {}, function(responseText) {
			if (responseText.result==0) {
				alert(responseText.message+"，请到【异常订单】菜单中处理");
				window.location.href="shop/admin/ordAuto!showBatchPreOrderList.do?is_return_back=1";
			} else {
				alert(responseText.message);
			}
			closeDialog();
		}, 'json');
	});
});

</script>


