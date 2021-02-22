<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<style>
</style>
<form method="post" id="closeWorkForm" class="validate">
	<div>
		<table>
			<tr>
				<th>工单回收原因：</th>
				<td>
					<textarea rows="5" cols="55" id="workReason" name="workReason"></textarea>
					<input type="hidden"  name="order_id" value="${order_id}" /></td>
			</tr>
			<tr >
				<th> </th>
				<td style="padding-top: 10px;">
					<a href="javascript:void(0);" value="${order_id}" name="closeWork_Btn" class="dobtn" style="margin-left: 1px;"><span>工单回收</span></a>
				</td>
			</tr>
		</table>
	</div>
</form>

<script type="text/javascript">
$(function() {
	//回收工单
	$("a[name='closeWork_Btn']").bind("click",
			function() {
				var order_id = $(this).attr("value");
				var status = $(this).attr("status");
				if(order_id == null || order_id==""){
					alert("异常：order_id为空");
					return;
				}
				if(confirm("请确认是否回收工单!")){
				}else{
					return;
				}
				var url = ctx + "/shop/admin/ordAuto!closeOrdWork.do?ajax=yes";
				Cmp.ajaxSubmit('closeWorkForm', '', url, {}, function(responseText) {
					debugger;
					if (responseText.result == 0) {
						alert(responseText.message);
						closeWorkForm();					 
					} else {
						alert(responseText.message);
						closeWorkForm();
					}
				}, 'json');
			});
	//关闭挥手工单页面
	function closeWorkForm(){
		Eop.Dialog.close("closeOrdWorkbtn");
	};
	
});
</script>
