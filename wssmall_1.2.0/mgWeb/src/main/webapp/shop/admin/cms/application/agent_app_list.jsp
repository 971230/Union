<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="mk_div mtt">
	<div class="mk_title">
		<h2>行业用户组应用列表</h2>
	</div>
	<div class="mk_content">
		<div class="searchformDiv">
			<form method="post" action="application!getUserApp.do" name="qryForm">
				<table width="90%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th>行业用户组名称:</th>
						<td><input type="text" name="partner_name"
							class="searchipt" value="${partner_name}" /></td>
						<th>应用名称:</th>
						<td><input type="text" name="app.app_name"
							class="searchipt" value="${app.app_name }" /></td>
						<th>状态:</th>
						<td><select name="app.disabled" id="appDisabled"
							class="searchipt">
								<option value="">请选择</option>
								<option value="0">可用</option>
								<option value="1">停用</option>
						</select></td>
						<td style="text-align:center;"><a href="javascript:void;"
							onclick="document.forms.qryForm.submit();" class="searchBtn">
								<span>查&nbsp;询</span>
						</a></td>
				</table>
			</form>
		</div>
		<div class="comBtnDiv">
			<a href="application!addAgentApp.do" onclick="" class="searchBtn"><span>添&nbsp;加</span></a>
		</div>
		<!--列表样式-->
		<div class="grid">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>行业用户组组名称</th>
					<th>应用名称</th>
					<th>用户App名称</th>
					<th>版本号</th>
					<th>是否强制升级</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${webpage.data }" var="data">
					<tr>
						<td>${data.partner_name }</td>
						<td>${data.app_name }</td>
						<td><input type="text" class="searchipt app_user_name" rel_id="${data.rel_id }" value="${data.app_user_name }"/> </td>
						<td>${data.save_level }</td>
						<td class="update_state"><c:if test="${data.app_update=='no' }">否</c:if> <c:if
								test="${data.app_update=='yes' }">是</c:if></td>
						<td><c:if test="${data.disabled=='0' }">可用</c:if> <c:if
								test="${data.disabled=='1' }">停用</c:if></td>
						<td><a href="javascript:void(0);" app_id="${data.app_id}"
							user_id="${data.userid}" class="app_delete"
							style="margin-left: 10px">删除</a> <a href="javascript:void(0);"
							rel_id="${data.rel_id }" app_update="${data.app_update}" class="app_update"
							style="margin-left: 10px">切换升级状态</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<div class="grid">
		<form method="POST">
			<grid:grid from="webpage">
				<grid:body item=""></grid:body>
			</grid:grid>
		</form>
	</div>
</div>
<div id="del_app_div"></div>
<div id="save_name_div"></div>
<div id="save_update_div"></div>
<script type="text/javascript">
	$(function() {
		$("#appDisabled option[value='${app.disabled}']").attr("selected", true);
		
		
		$(".app_delete").bind("click",function(){
			if(!confirm("确定删除应用关联？")){
				return;
			}
			var url = "application!delAppRelUser.do?ajax=yes" +
					"&appRelUser.user_id="+$(this).attr("user_id")+
					"&appRelUser.app_id="+$(this).attr("app_id");
			Cmp.ajaxSubmit('del_app_div', '', url, {}, AppRelUserList.delJsonBack,'json');
		});
		
		$(".app_user_name").bind("change",function(){
			var reg = new RegExp("^[a-zA-Z0-9\u4e00-\u9fa5]+$"); 
			var app_name = $(this).val();
			if(!reg.test(app_name)){
				alert("名称请勿输入特殊字符！");
			}else{
				app_name = encodeURI(encodeURI(app_name));
				var url = "application!updateAppUser.do?ajax=yes"+
					"&appRelUser.rel_id="+$(this).attr("rel_id")+
					"&appRelUser.app_name="+app_name;
				Cmp.ajaxSubmit('save_name_div', '', url, {}, AppRelUserList.saveNameJsonBack,'json');
			}
		});
		
		
		$(".app_update").bind("click",function(){
			var a_tag = $(this);
			var state = a_tag.attr("app_update");
			
			var n_state = "";
			var state_name = "";
			if(state == "yes"){
				n_state = "no";
				state_name = "否";
			}else{
				n_state = "yes";
				state_name = "是";
			}
			
			var url = "application!updateAppUser.do?ajax=yes"+
				"&appRelUser.rel_id="+a_tag.attr("rel_id")+
				"&appRelUser.app_update="+n_state;
			
			Cmp.ajaxSubmit('save_update_div', '', url, {}, function(responseText){
				if (responseText.result == 1) {
					alert("修改成功");
					a_tag.attr("app_update",n_state);
					a_tag.parent().siblings("td[class='update_state']").html(state_name);
				}
				if (responseText.result == 0) {
					alert(responseText.message);
				}	
			},'json');
		});
	});
	
	var AppRelUserList = {
		delJsonBack:function(responseText){
			if (responseText.result == 1) {
				alert("操作成功");
				window.location.reload();
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}	
		},
		saveNameJsonBack:function(responseText){
			if (responseText.result == 1) {
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}	
		}
	}

</script>
