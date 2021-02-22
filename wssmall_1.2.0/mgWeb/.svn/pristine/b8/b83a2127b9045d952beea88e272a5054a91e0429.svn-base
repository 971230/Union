<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="mk_div mtt">
	<div class="mk_title">
		<h2>页面列表</h2>
	</div>
	<div class="searchformDiv">
		<form method="post" action="application!getPageList.do"
			id="page_info_form" name="qryForm">
			<table width="90%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>状态:</th>
					<input type="hidden" value="${appPage.disable}"
						id="hidden_app_disable" />
					<td><select class="searchipt" name="appPage.disable"
						id="app_disable">
							<option value="">请选择</option>
							<option value="0">可用</option>
							<option value="1">停用</option>
					</select></td>
					<th>页面名称:</th>
					<td><input type="text" name="appPage.page_name" id="app_name"
						value="${appPage.page_name}" class="searchipt" /></td>
					<td style="text-align:center;"><input type="submit"
						style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"
						id="submitButton" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="comBtnDiv">
		<c:if test="${is_select != 'yes'}">
			<a href="application!pagedetail.do" onclick="" class="searchBtn"><span>新&nbsp;建</span></a>
		</c:if>
		<c:if test="${is_select == 'yes'}">
			<a href="javascript:void(0);" id="select_page_insure"
				class="searchBtn"><span>确&nbsp;定</span></a>
		</c:if>
	</div>
	<div>
		<div class="grid">
			<form method="post">
				<input style="display: none;" name="app.app_id"
					value="${app.app_id }">
				<grid:grid from="webpage" ajax="yes" formId="page_info_form">
					<grid:header>
						<grid:cell>选择</grid:cell>
						<grid:cell>页面编码</grid:cell>
						<grid:cell>页面名称</grid:cell>
						<grid:cell>页面url</grid:cell>
						<grid:cell>状态</grid:cell>
						<grid:cell>操作</grid:cell>
					</grid:header>

					<grid:body item="obj">
						<grid:cell>
							<input type="checkbox" name="select_page_radio"
								page_id="${obj.id}" page_code="${obj.page_code}"
								page_url="${obj.page_url}" page_name="${obj.page_name }" />
						</grid:cell>
						<grid:cell>${obj.page_code }</grid:cell>
						<grid:cell>${obj.page_name }</grid:cell>
						<grid:cell>${obj.page_url }</grid:cell>
						<grid:cell>
							<c:if test="${obj.disable=='0' }">可用</c:if>
							<c:if test="${obj.disable=='1' }">停用</c:if>
						</grid:cell>
						<grid:cell>
							<c:if test="${is_select != 'yes'}">
								<a href="application!pagedetail.do?appPage.id=${obj.id}"><span>修改</span></a>|
							<a href="javascript:void(0);" page_id="${obj.id}"
									class="page_del" style="margin-left: 10px">删除</a>
							</c:if>
						</grid:cell>
					</grid:body>

				</grid:grid>
			</form>
		</div>
	</div>
</div>
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
