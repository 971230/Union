<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id='modual_list_form' action="javascript:void(0);">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>模块名称:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="modual.modual_name" value="${modual.modual_name}" class="searchipt" /></td>
					<th>模块编码:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="modual.modual_code" value="${modual.modual_code}" class="searchipt" /></td>
					<td><input type="submit" style="margin-right:10px;"
						class="submitButton" value="搜&nbsp;索" type="submit" attr="admin"></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="comBtnDiv">
		<a href="javascript:void(0);" id="select_modual_insure" class="searchBtn"><span>确&nbsp;定</span></a>
	</div>
</form>

<div>
	<div class="grid">
		<form method="POST">
			<grid:grid from="webpage" ajax="yes" formId="modual_list_form">
				<grid:header>
					<grid:cell style="width: 30px;">选择</grid:cell>
					<grid:cell style="width: 100px;">模块名称</grid:cell>
					<grid:cell style="width: 100px;">模块编码</grid:cell>
					<grid:cell style="width: 100px;">创建时间</grid:cell>
				</grid:header>
				<grid:body item="modual">
					<grid:cell>
						<input type="checkbox" name="select_modual_radio"
							modual_name="${modual.modual_name}" modual_id="${modual.modual_id}"
							modual_code="${modual.modual_code}" create_time="<html:dateformat pattern="yyyy-MM-dd" d_time="${modual.create_time}"></html:dateformat>"/>
					</grid:cell>
					<grid:cell>${modual.modual_name}</grid:cell>
					<grid:cell>${modual.modual_code}</grid:cell>
					<grid:cell><html:dateformat pattern="yyyy-MM-dd" d_time="${modual.create_time}"></html:dateformat></grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>
