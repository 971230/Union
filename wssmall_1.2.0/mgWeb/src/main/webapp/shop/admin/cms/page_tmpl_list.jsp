<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" action="template!queryPageTmpl.do" id="page_tmpl_form" >
<div class="mk_div mtt">
	<div class="mk_title">
		<h2>页面列表</h2>
	</div>
	<div class="searchformDiv">
		
			<table width="90%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>模板名称:</th>
					<td><input type="text" name="tmpl_name" 
						value="${tmpl_name}" class="searchipt" /></td>
					<td style="text-align:center;"><input type="submit"
						style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"
						id="submitButton" /></td>
				</tr>
			</table>
	</div>
	<div class="comBtnDiv">
			<a href="template!showPageTmplEdit.do" onclick="" class="searchBtn"><span>新&nbsp;建</span></a>
	</div>
	<div>
		<div class="grid">
				<grid:grid from="webpage" ajax="yes" formId="page_tmpl_form">
					<grid:header>
						<grid:cell>模板编码</grid:cell>
						<grid:cell>模板名称</grid:cell>
						<grid:cell>创建时间</grid:cell>
						<grid:cell>状态</grid:cell>
						<grid:cell>操作</grid:cell>
					</grid:header>

					<grid:body item="obj">
						<grid:cell>${obj.template_id }</grid:cell>
						<grid:cell>${obj.template_name }</grid:cell>
						<grid:cell>${obj.create_time }</grid:cell>
						<grid:cell>
							<c:if test="${obj.disabled=='0' }">可用</c:if>
							<c:if test="${obj.disabled=='1' }">停用</c:if>
						</grid:cell>
						<grid:cell>
								<a href="template!showPageTmplEdit.do?template_id=${obj.template_id}"><span>修改</span></a>
						</grid:cell>
					</grid:body>

				</grid:grid>
		</div>
	</div>
</div>
</form>
<div id="del_page_div"></div>
<script type="text/javascript">
	$(function() {
		$("#app_disable").val($("#hidden_app_disable").val());

		$(".page_del").bind(
				"click",
				function() {
					if (!confirm("确定删除页面？")) {
						return;
					}
					var page_id = $(this).attr("page_id");
					var url = "application!deletePage.do?ajax=yes&appPage.id="
							+ page_id;
					Cmp.ajaxSubmit('del_page_div', '', url, {},
							PageList.delJsonBack, 'json');
				});

	})

	var PageList = {
		delJsonBack : function(responseText) {
			if (responseText.result == 1) {
				alert("操作成功");
				window.location.reload();
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}
		}
	}
</script>
