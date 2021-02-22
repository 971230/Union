<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
	$(function() {
		$("a[name=rule_link_]").die("click").live( "click", function() {
			Eop.Dialog.close("rule_search_reslut");
			Eop.Dialog.close("show_search_dlg");
			Directory.load_cond_page($(this).attr("a_id"));
		});
		
		$("#query_button").unbind("click").bind("click", function(){
			var url = ctx + "/shop/admin/ruleManager!searchRuleConfig.do?ajax=yes";
			$("#rule_search_reslut").load(url,
					{"searchCond" : $("#searchCond0").val(), 
					"queryRuleName":$("#queryRuleName").val(),
					"plan_id":$("#plan_id").val(),
					"dir_id":$("#dir_id").val()});
		});
	});
</script>

	<form method="POST" id="search_rule_list_form">
	<div class="searchformDiv">
		<%-- <table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>查询条件:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						id="queryRuleName" name="queryRuleName" value="${queryRuleName}" class="searchipt" /></td>
					<td><a class="graybtn1" style="margin-left:5px;" id="query_button" href="javascript:void(0);">搜索</a></td>
				</tr>
			</tbody>
		</table> --%>
	</div>

<!-- 流程列表页面 -->
<div class="grid">
		<grid:grid from="searchPage" ajax="yes" formId="search_rule_list_form">
			<grid:header>
				<grid:cell style="width: 100px;">规则编号</grid:cell>
				<grid:cell style="width: 100px;">规则名称</grid:cell>
				<grid:cell style="width: 100px;">操作</grid:cell>
			</grid:header>
			<grid:body item="rule">
				<grid:cell>${rule.rule_id}</grid:cell>
				<grid:cell>${rule.rule_name}</grid:cell>
				<grid:cell>
					<a href="javascript:void(0);" a_id="${rule.rule_id }" name="rule_link_">配置规则</a>
				</grid:cell>
			</grid:body>
		</grid:grid>
		<input type="hidden" name="searchCond" id="searchCond0" value="${searchCond }"/>
		<input type="hidden" name="is_include" id="is_include0" value="${is_include }"/>
		<input type="hidden" name="plan_id" value="${plan_id }" id="plan_id" />
		<input type="hidden" name="dir_id" value="${dir_id }" id="dir_id" />
</div>
	</form>