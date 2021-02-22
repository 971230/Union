<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<html>
<div>
	<form method="post">
	<table>
		<tr>
			<th>请选择刷新类型：</th>
			<td>
				<select id="refresh_type">
					<option value="-1">---请选择---</option>
					<option value="plan">刷新方案</option>
					<!-- <option value="rule">刷新规则</option> -->				
				</select>
			</td>
		</tr>
		<tr>
			&nbsp;&nbsp;
		</tr>
		<tr>
			<th>请输入方案编号（","分开）：</th>
			<td style="width:180px;"><textarea 
					rows="5" cols="50" id="plan_rule_ids" name="plan_rule_ids"></textarea>
			</td>
			<td>
				<a href="javascript:void(0);" id="refresh_plan_rule_a" class="dobtn" style="margin-left:5px;">刷新方案规则</a>
				<a href="javascript:void(0);" id="reset_plan_rule_a" class="dobtn" style="margin-left:5px;">重置</a>
			</td>
		</tr>
	</table>
	</form>
</div>
</html>

<script>
	$(function() {
		$("#refresh_plan_rule_a").bind("click", function() {
			var refreshType = $("#refresh_type").val();
			var plan_rule_ids = $("#plan_rule_ids").val();
			if (refreshType == "-1") {
				alert("请选择刷新类型！");return;
			}
			if (plan_rule_ids == "") {
				alert("请输入方案规则ID！");
				return;
			} 
			flag = confirm("确认刷新方案规则吗？");
			if (!flag)return;
			$.ajax({
				type : "post",
				async : false,
				url : "orderRefreshTreeAttrAction!refreshPlanRule.do?ajax=yes",
				data : {"plan_rule_ids":plan_rule_ids,"refresh_type":refreshType},
				dataType : "json",
				success : function(data) {
					alert(data.msg);
				}
			});
		});
		$("#reset_plan_rule_a").bind("click", function() {
			$("#refresh_type").val("-1");
			$("#plan_rule_ids").val("");
		});
	});
</script>
