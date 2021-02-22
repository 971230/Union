<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>终端调拨</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>
<body>
<div class="gridWarp">
	<div class="new_right">
	<form method="post">
	<grid:grid from="empOperInfos">
		<grid:header>
			<grid:cell style="width:10px"></grid:cell>
			<grid:cell style="width:100px">工号</grid:cell>
			<grid:cell style="width:100px">地市</grid:cell>
			<grid:cell style="width:100px">地市编码</grid:cell>
		</grid:header>
		<grid:body item="empOperInfo">
			<grid:cell><input type="radio" name="ess_emp_id" value="${empOperInfo.ess_emp_id }"/></grid:cell>
			<grid:cell>${empOperInfo.ess_emp_id }</grid:cell>
			<grid:cell>
				<c:forEach var="city" items="${cityList }">
					<c:if test="${empOperInfo.city==city.other_field_value }">${city.other_field_value_desc }</c:if>
				</c:forEach>
			</grid:cell>
			<grid:cell>${empOperInfo.city }</grid:cell>
		</grid:body>
	</grid:grid>
	<c:if test="${empOperInfosSize==0}"><font color="red">您未绑定bss工号!!!</font></c:if>
	<c:if test="${empOperInfosSize>0}"><font color="blue">请选择调拨工号!!!</font></c:if>
	<table class="tab_form">
		<tr>
			<th>请输入需要调拨的串号,不同串号以逗号","分开：</th>
			<td style="width:180px;"><textarea 
					rows="5" cols="50" id="terminal_nums" name="terminal_nums"></textarea>
			</td>
			<td>
				<a href="javascript:void(0);" id="transferTerminal" class="dobtn" style="margin-left:5px;">终端调拨</a>
			</td>
		</tr>
	</table>
	</form>
 </div>
</div>
</body>
</html>
<script type="text/javascript">

$("#transferTerminal").bind("click", function() {
	
	flag = confirm("确认调拨终端吗？");
	if (!flag)return;
	
	var terminal_nums = $("#terminal_nums").val();
	var ess_emp_id = $("input[name=ess_emp_id]:checked").val();
	if (terminal_nums == "") {
		alert("请输入串号！");
		return;
	}else if(ess_emp_id == "undefined" || ess_emp_id == "" || ess_emp_id == null){
		alert("请选择调拨工号");
		return;
	}else {
		$.ajax({
			type : "post",
			async : false,
			url : "ordAuto!terminalTransfer.do?ajax=yes&terminal_nums="+terminal_nums+"&ess_emp_id="+ess_emp_id,
			data : {},
			dataType : "json",
			success : function(data) {
				alert(data.message);
			}
		});
	}
});
</script>
 