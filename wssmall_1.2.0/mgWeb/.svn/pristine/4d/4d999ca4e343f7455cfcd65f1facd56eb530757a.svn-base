<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div id="columnList">
	<div class="grid">
		<form method="POST" id="column_form">
			<grid:grid from="webpage" ajax="yes">
				<grid:header>
					<grid:cell style="width: 100px;">栏目名称</grid:cell>
					<grid:cell style="width: 100px;">栏目类型</grid:cell>
				</grid:header>
				<grid:body item="column">
					<grid:cell>${column.title}</grid:cell>
					<grid:cell>${column.type}</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>

	