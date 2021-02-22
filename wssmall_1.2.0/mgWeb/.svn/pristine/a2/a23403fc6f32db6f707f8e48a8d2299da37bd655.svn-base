<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!-- <script src=js/template_column_relate.js ></script> -->
<div class="mk_div mtt">
	<div class="mk_title">
		<h2>可用模版</h2>
	</div>
	<div class="mk_content">
			<form method="post" action="template!list.do" name="qryForm">
			<div class="searchformDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>模版类型:</th>
					<td>
						<select class="searchipt" name="template.template_type" id="template_type">
							<option value="">--请选择--</option>
							<c:forEach items="${typeList }" var="type">
								<option value="${type.codea }">${type.pname }</option>
							</c:forEach>
					</select></td>
					<th>模版名称:</th>
					<td><input type="text" name="template.template_name" id="template_name"
					 value="${template.template_name }" class="searchipt" /></td>
					<th>用户APP:</th>
					<td>
						<select class="searchipt" name="app_id" id="app_id">
							<option value="">--请选择--</option>
							<c:forEach items="${appList }" var="app">
								<option value="${app.app_id }">${app.app_name }</option>
							</c:forEach>
					</select></td>
					<td style="text-align:center;"><a href="javascript:void;"
					 onclick="checkSubmit();" class="searchBtn"><span>搜&nbsp;索</span></a></td>
				</tr>
			</table>
			</div>
			</form>
		
		<div class="mb_list_table mtt">
			<table cellpadding="0" cellspacing="0" width="100%">
				<c:forEach items="${webpage.data }" var="data" varStatus="status">
					<c:if test="${status.first }"><tr></c:if>
						<td>
							<div class="mb_img">
								<img src="${data.preview_img_url }" width="99%" height="99%"/>
							<p>${data.template_name }</p>
							</div>
							<p style="text-align:left;margin-left: 80px">
								<a href="javascript:void(0);" onclick="clickC('${data.template_id}','${data.page_id }');" class="searchBtn"><span>新增模块</span></a>
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
<div id="count_user_div"></div>
<script type="text/javascript">
$(function() {
	var t_id;
	var p_id;
	$("#template_type option[value='${template.template_type}']").attr("selected", true);
	$("#app_id option[value='${app_id}']").attr("selected", true);
});

function checkSubmit(){
	if($("#app_id option").length >= 2){
		if($("#app_id").val() == ""){
			alert("请选择用户APP");
			return;
		}
	}
	document.forms.qryForm.submit();
}

function clickC(tpl_id,page_id){//跳转到新增模块的页面
	t_id = tpl_id;
	p_id = page_id;
	var url = ctx + "/shop/admin/cms/modual!countUserTpl.do?ajax=yes&tpl_id="+tpl_id+"&page_id="+page_id;
	Cmp.ajaxSubmit('count_user_div', '', url, {}, TplList.jsonBack,'json');
}

var TplList = {
	jsonBack:function(responseText){
		if (responseText.result == 1) {
			window.location.href= "modual!queryList.do?tpl_id="+t_id+"&page_id="+p_id;
		}
		if (responseText.result == 0) {
			alert(responseText.message);
		}	
	}
}
</script>
