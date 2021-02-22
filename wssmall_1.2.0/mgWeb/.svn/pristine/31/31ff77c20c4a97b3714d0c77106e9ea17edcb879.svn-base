<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="mk_div mtt">
<div class="mk_title">
	<h2>插件桩列表</h2>
</div>
<form method="post" id='widget_form' action="modual!listWidget.do">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>所属模板:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 120px;"
						name="cmsWidget.template_name" value="${cmsWidget.template_name}" class="searchipt" /></td>
					<th>前台处理bean:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 120px;"
						name="cmsWidget.type" value="${cmsWidget.type}" class="searchipt" /></td>
					<th>后台处理bean:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 120px;"
						name="cmsWidget.cms_type" value="${cmsWidget.cms_type}" class="searchipt" /></td>
				</tr>
				<tr>
					<th>开始时间:</th>
					<td><input type="text" class="ipttxt" dataType="date"
						style="width: 120px" name="cmsWidget.start_time"
						value="${cmsWidget.start_time}" class="searchipt" /></td>
					<th>结束时间:</th>
					<td><input type="text" class="ipttxt" dataType="date"
						style="width: 120px" name="cmsWidget.end_time"
						value="${cmsWidget.end_time}" class="searchipt" /></td>
					<td><input type="submit" style="margin-right:10px;"
						class="comBtn" value="搜&nbsp;索" type="submit" id="submitButton"/></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="comBtnDiv">
		<a style="margin-right:10px;" class="graybtn1"
			href="${ctx}/shop/admin/cms/modual!addWidget.do"><span>添加</span></a>
	</div>
</form>

<div id="ruleList">
	<div class="grid">
		<form method="POST" id="widget_form">
			<grid:grid from="webpage" ajax="yes">
				<grid:header>
					<grid:cell style="width: 100px;">所属模板</grid:cell>
					<grid:cell style="width: 80px;">插件编码</grid:cell>
					<grid:cell style="width: 120px;">前台处理bean</grid:cell>
					<grid:cell style="width: 120px;">后台处理bean</grid:cell>
					<grid:cell style="width: 80px;">启用状态</grid:cell>
					<grid:cell style="width: 100px;">创建时间</grid:cell>
					<grid:cell style="width: 80px;">操作</grid:cell>
				</grid:header>
				<grid:body item="cmsWidget">
					<grid:cell>${cmsWidget.template_name}</grid:cell>
					<grid:cell>${cmsWidget.code}</grid:cell>
					<grid:cell>${cmsWidget.type}</grid:cell>
					<grid:cell>${cmsWidget.cms_type}</grid:cell>
					<grid:cell>
						<c:if test="${cmsWidget.state == 'yes'}">
							已启用
						</c:if>
						<c:if test="${cmsWidget.state == 'no'}">
							未启用
						</c:if>
					</grid:cell>
					<grid:cell>
						<html:dateformat pattern="yyyy-MM-dd" d_time="${cmsWidget.create_time}"></html:dateformat>
					</grid:cell>
					<grid:cell>
						<a href="modual!addWidget.do?cmsWidget.widget_id=${cmsWidget.widget_id}">修改 </a>
					</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>
</div>
<script>
	$(function() {
		$("input[dataType='date']").datepicker();
	});
</script>
