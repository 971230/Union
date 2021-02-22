<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
	<title>订单解锁</title>
</head>
<div>
<form method="post">
	<table class="tab_form">
		<tr height="100">
		</tr>
		<tr>
			<td width="35%">请输入需要解锁的外部订单号<br>（订单号与订单号之间用英文逗号','分隔）：</td>
			<td style="width:100px;"><textarea 
					rows="5" cols="50" id="order_ids" name="order_ids"></textarea>
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="right">
				<a href="javascript:void(0);" id="delInp" style="margin-right:10px;" class="graybtn1" ><span>解锁提交</span></a>
			</td>
		</tr>
	</table>
</form>
</div>
</html>
<script type="text/javascript">
$(function(){
	$("#delInp").bind("click", function() {
		flag = confirm("确认解锁订单吗？");
		if (!flag)return;
		var order_ids = $("#order_ids").val();
		if (order_ids == "") {
			alert("请输入外部订单号！");
			return;
		}
		var order_id_array = new Array();
		order_id_array = order_ids.split(",");
		if (order_id_array.length > 50) {
			alert("每次解锁数量不要超过50！");
			return;
		} else {
			$.ajax({
				type : "post",
				async : false,
				url : "ordAuto!unLockOrder.do?ajax=yes&lockDealType=inp&params.out_tid="+order_ids,
				data : {},
				dataType : "json",
				success : function(data) {
					alert(data.message);
				}
			});
		}
	});
});
</script>