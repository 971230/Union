<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="mk_div mtt">
	<div class="mk_title">
		<h2>审核不通过模块列表</h2>
	</div>
	<div class="mk_content">
		<div class="searchformDiv">
			<form method="post" action="modual!list.do" name="qryForm">
			<table width="90%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>模版类型:</th>
					<td>
						<select class="searchipt" name="template_type" id="template_type">
							<option value="">--请选择--</option>
							<c:forEach items="${typeList }" var="type">
								<option value="${type.codea }">${type.pname }</option>
							</c:forEach>
					</select></td>
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
								<img src="${data.preview_img_url }" width="99%" height="99%"/>
							<p>${data.modual_name }</p>
							</div>
							<p style="text-align:left;margin-left: 75px">
								<a href="modual!auditAgain.do?modual.modual_id=${data.modual_id }&modual.state=${data.state}&modual.seq=${data.seq}" 
									 class="searchBtn"><span>重新审核</span></a>
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
});
</script>
