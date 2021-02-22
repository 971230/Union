<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="mk_div mtt">
	<div class="mk_title">
		<h2>可用模块列表</h2>
	</div>
	<div class="mk_content">
		<div class="searchformDiv">
			<form method="post" action="modual!list.do" name="qryForm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>行业用户组:</th>
					<td>
						<input type="text" name="partner_name" id="partner_name"
					 		value="${partner_name}" class="searchipt" />
					</td>
					<th>模版类型:</th>
					<td>
						<select class="searchipt" name="template_type" id="template_type">
							<option value="">--请选择--</option>
							<c:forEach items="${typeList }" var="type">
								<option value="${type.codea }">${type.pname }</option>
							</c:forEach>
					</select></td>
					<th>应用列表:</th>
					<td>
						<select class="searchipt" name="app_id" id="app_id">
							<option value="">--请选择--</option>
							<c:forEach items="${appList }" var="app">
								<option value="${app.app_id }">${app.app_name }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>状态:</th>
					<td>
						<select class="searchipt" name="modual.state" id="state">
							<option value="">--请选择--</option>
							<option value="1">已审核</option>
							<option value="0">待审核</option>
							<option value="2">审核不通过</option>
						</select>
					</td>
					<th>模块名称:</th>
					<td><input type="text" name="modual.modual_name" id="modual_name"
					 value="${modual.modual_name }" class="searchipt" /></td>
					<td style="text-align:center;"><a href="javascript:void;"
					 onclick="document.forms.qryForm.submit();" class="searchBtn"><span>搜&nbsp;索</span></a></td>
				</tr>
			</table>
			</form>
		</div>
		<div class="mb_list_table mtt">
			<table cellpadding="0" cellspacing="0" width="100%">
				<c:forEach items="${webpage.data }" var="data" varStatus="status">
					<c:if test="${status.first }"><tr></c:if>
						<td>
							<div class="mb_img">
								<img src="${data.preview_img_url }" width="99%" height="99%" style="width:300px;height:200px;"/>
							<p>${data.modual_name } 
							<font style="color: red">(<c:if test="${data.state==1 }">已审核</c:if><c:if test="${data.state==0 }">待审核</c:if><c:if test="${data.state==2 }">审核不通过</c:if>)</font>
							<br>(${data.partner_name })
							</p>
							</div>
							<p style="text-align:left;margin-left: 22px">
								<c:choose>
									<c:when test="${data.state==0 || data.state == 2 || data.ct == 0}">
										<a href="modual!editList.do?modual.modual_id=${data.modual_id }&template.partner_id=${data.partner_id}&template.preview_img_url=${data.preview_img_url}&tpl_id=${data.template_id}&modual.seq=${data.seq}&modual.state=${data.state}" 
											class="searchBtn"><span>修改模块</span></a>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</c:when>
									<c:otherwise>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</c:otherwise>
								</c:choose>
								<a onclick="javascript:return confirm('确认删除吗?');" 
									href="modual!delete.do?modual.modual_id=${data.modual_id }&modual.state=${data.state }" class="searchBtn"><span>删除模块</span></a>
							</p>
						</td>
					<c:if test="${(status.index + 1) % 3 == 0 }"></tr><tr></c:if>
					<c:if test="${status.end }"></tr></c:if>
				</c:forEach>
			</table>
		</div>
	</div>
	<div class="grid" style="margin-top: 0px">
		<form method="POST" >
			<grid:grid from="webpage">
				<grid:body item="group"></grid:body>
			</grid:grid>
		</form>
	</div>
</div>
<script type="text/javascript">
$(function() {
	$("#template_type option[value='${template_type}']").attr("selected", true);
	$("#app_id option[value='${app_id}']").attr("selected", true);
	$("#state option[value='${modual.state}']").attr("selected", true);
});

function clickC(modual_id){//跳转到修改模块的页面
	location.href="modual!editList.do?modual.modual_id="+modual_id;
}
</script>
