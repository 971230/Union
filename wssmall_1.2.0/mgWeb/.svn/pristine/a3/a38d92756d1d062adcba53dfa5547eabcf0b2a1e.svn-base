<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id='template_form' action="template!list.do">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>模版类型:</th>
					<td>
						<select class="searchipt" name="template.template_type" id="template_type">
							<option value="">--请选择--</option>
							<c:forEach items="${typeList }" var="type">
								<option value="${type.codea}"
									${template.template_type == type.codea ? ' selected="selected" ' : ''}>${type.pname
									}</option>
							</c:forEach>
					</select></td>
					<th>模版名称:</th>
					<td><input type="text" name="template.template_name" id="template_name"
					 value="${template.template_name }" class="searchipt" /></td>
				</tr>
				<tr>
					<th>用户APP:</th>
					<td>
						<select class="searchipt" name="app_id" id="app_id">
							<option value="">--请选择--</option>
							<c:forEach items="${appList }" var="app">
								<option value="${app.app_id}"
									${app_id == app.app_id ? ' selected="selected" ' : ''}>${app.app_name
									}</option>
							</c:forEach>
					</select></td>


					<td><input type="submit" style="margin-right:10px;"
						class="comBtn" value="搜&nbsp;索"  id="submitButton"/></td>
					<td><input type="button" style="margin-right:10px;"
						class="comBtn" value="确&nbsp;定"  id="sel_temp"/></td>
				</tr>
			</tbody>
		</table>
	</div>
</form>

<div id="ruleList">
	<div class="grid">
		<form method="POST">
			<grid:grid from="webpage" ajax="yes" formId="template_form">
				<grid:header>
					<grid:cell style="width: 100px;">选择</grid:cell>
					<grid:cell style="width: 100px;">模板名称</grid:cell>
					<grid:cell style="width: 100px;">模板编码</grid:cell>
					<grid:cell style="width: 100px;">创建时间</grid:cell>
				</grid:header>
				<grid:body item="temp">
					<grid:cell>
						<input type="radio" name="choose_temp" template_id="${temp.template_id}" template_name="${temp.template_name}"/>
					</grid:cell>
					<grid:cell>${temp.template_name}</grid:cell>
					<grid:cell>${temp.template_code}</grid:cell>
					<grid:cell>
						<html:dateformat pattern="yyyy-MM-dd" d_time="${temp.create_time}"></html:dateformat>
					</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>
