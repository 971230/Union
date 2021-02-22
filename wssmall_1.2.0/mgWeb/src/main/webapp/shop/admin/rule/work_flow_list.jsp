<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/shop/admin/rule/js/work_flow_list.js"></script>
<!-- 流程列表页面 -->
<form method="post" id='work_folw_list_form' action="javascript:void(0);">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>查询条件:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="flow_name" value="${flow_name}" class="searchipt" /></td>
					<td><a class="graybtn1" style="margin-left:5px;" id="qry_btn" href="javascript:void(0);">搜索</a></td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
<div>
	<div class="grid" id="flow_list">
		<form method="POST">
			<grid:grid from="webpage" ajax="yes" formId="work_folw_list_form">
				<grid:header>
					<grid:cell style="width: 30px;">选择</grid:cell>
					<grid:cell style="width: 100px;">流程ID</grid:cell>
					<grid:cell style="width: 100px;">流程名称</grid:cell>
				</grid:header>
				<grid:body item="flow">
					<grid:cell>
						<input type="checkbox" name="select_flow" flow_id="${flow.processId}" flow_name="${flow.processName}" />
					</grid:cell>
					<grid:cell>${flow.processId}</grid:cell>
					<grid:cell>${flow.processName}</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>
<div style="text-align: center; padding-top: 50px;">
	<a class="blueBtns" id="save_list_condition" href="javascript:void(0);"><span>确认</span></a>
</div>
