<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id='app_list_form' action="javascript:void(0);">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>应用名称:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="app.app_name" value="${app.app_name}" class="searchipt" /></td>
					<td><input type="submit" style="margin-right:10px;"
						class="comBtn userQryBtn" value="搜&nbsp;索" type="submit" attr="admin"></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="comBtnDiv">
		<a href="javascript:void(0);" id="select_app_insure" class="searchBtn"><span>确&nbsp;定</span></a>
	</div>
</form>

<div>
	<div class="grid">
		<form method="POST">
			<grid:grid from="webpage" ajax="yes" formId="app_list_form">
				<grid:header>
					<grid:cell style="width: 30px;">选择</grid:cell>
					<grid:cell style="width: 100px;">应用名称</grid:cell>
					<grid:cell style="width: 100px;">应用编码</grid:cell>
					<grid:cell style="width: 100px;">外系统编码</grid:cell>
				</grid:header>
				<grid:body item="app">
					<grid:cell>
						<input type="checkbox" name="select_app_radio"
							app_name="${app.app_name}" app_id="${app.app_id}"
							outer_app_code="${app.outer_app_code}" app_code="${app.app_code}"/>
					</grid:cell>
					<grid:cell>${app.app_name}</grid:cell>
					<grid:cell>${app.app_code}</grid:cell>
					<grid:cell>${app.outer_app_code}</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>
