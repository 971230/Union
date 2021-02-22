<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="mk_div mtt">
	<div class="mk_title">
		<h2>应用列表</h2>
	</div>
	<div class="searchformDiv">
		<form method="post" action="application!getList.do" name="qryForm">
			<table width="90%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>状态:</th>
					<input type="hidden" id="hidden_app_disable" value="${app.disabled}"/>
					<td><select class="searchipt" name="app.disabled"
						id="app_disable">
							<option value="">请选择</option>
							<option value="0">可用</option>
							<option value="1">停用</option>

					</select></td>
					<th>应用名称:</th>
					<td><input type="text" name="app.app_name"
						id="app_name" value="${app.app_name}" class="searchipt" /></td>
					<td style="text-align:center;"><a href="javascript:void;"
						onclick="document.forms.qryForm.submit();" class="searchBtn"><span>搜&nbsp;索</span></a></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="comBtnDiv">
		<a href="application!detail.do" onclick="" class="searchBtn"><span>新&nbsp;建</span></a>
	</div>

	<form id="gridform" class="grid">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell>应用编码</grid:cell>
				<grid:cell>应用名称</grid:cell>
				<grid:cell>应用包名</grid:cell>
				<grid:cell>应用图片路径</grid:cell>
				<grid:cell>状态</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>${obj.app_code}</grid:cell>
				<grid:cell>${obj.app_name }</grid:cell>
				<grid:cell>${obj.app_package }</grid:cell>
				<grid:cell>${obj.app_img_path }</grid:cell>
				<grid:cell>
					<c:if test="${obj.disabled=='0' }">可用</c:if>
					<c:if test="${obj.disabled=='1' }">停用</c:if>
				</grid:cell>
				<grid:cell>
					<a href="application!detail.do?app.app_id=${obj.app_id }"
						name="app_change" style="margin-left: 10px">修改</a>
					<a
						href="application!changeAppState.do?app.app_id=${obj.app_id }&app.disabled=${obj.disabled}"
						name="app_stop" style="margin-left: 10px;"> <c:if
							test="${obj.disabled=='0' }">停用</c:if> <c:if
							test="${obj.disabled=='1' }">启用</c:if></a>
					<a
						href="javascript:void(0);" app_id="${obj.app_id}"
						class="app_delete" style="margin-left: 10px">删除</a>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
</div>
<div id="del_app_div"></div>
<script type="text/javascript">
$(function(){
	$("#app_disable").val($("#hidden_app_disable").val());
	
	$(".app_delete").bind("click",function(){
		if(!confirm("确定删除应用？")){
			return;
		}
		var app_id = $(this).attr("app_id");
		var url = "application!deleteApp.do?ajax=yes&app.app_id="+app_id;
		Cmp.ajaxSubmit('del_app_div', '', url, {}, AppList.delJsonBack,'json');
	});
	
})

var AppList = {
		delJsonBack:function(responseText){
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
