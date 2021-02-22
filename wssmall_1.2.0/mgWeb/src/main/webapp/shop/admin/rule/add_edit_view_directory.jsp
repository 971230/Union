<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
<!--
	
.noborder {
    border-style: none;
}
-->
</style>

<div class="input" >
	<form class="validate" method="post" id="saveDirectoryForm" validate="true">
	<div>
		<div style="margin-top:5px;">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tbody>
				<tr style="display: none;">
					<th>目录编码：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="directoryVo.id" id="mulubianma" value="${directoryVo.id}" /></td>
				</tr>
				<tr>
					<th>目录名称：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="directoryVo.name" id="mulumingcheng" value="${directoryVo.name}"/></td>
				</tr>
				<tr style="display: none;">
					<th>上级目录：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="directoryVo.pid" id="shangjimulu" value="0"/></td>
				</tr>
				<tr>
					<th>描述：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="directoryVo.hint" id="miaoshu" value="${directoryVo.hint}"/></td>
				</tr>
				</tbody>	
			</table>
		</div>
		<div class="pop_btn" align="center">
		 	<c:if test="${flag != 'view'}">
			<a id="savebtn" class="blueBtns"><span>保 存</span></a>
	     	<a id="resetbtn" class="blueBtns"><span>重 置</span></a>
	     	</c:if>
		</div>
	</div>
	<input type="hidden" id="flag" name="flag" value="${flag}" />
	<input type="hidden" id="nodeid" value="${id }" />
	</form>
</div>

<script>

$(function (){
	
	if ($("#flag").val() == "view"){
		$("#mulubianma").attr("disabled","disabled")
		$("#mulumingcheng").attr("disabled","disabled")
		$("#shangjimulu").attr("disabled","disabled")
		$("#miaoshu").attr("disabled","disabled")
	}
	if ($("#flag").val() == "edit") {
		$("#mulubianma").attr("readonly","readonly");
		$("#shangjimulu").attr("readonly","readonly");
	}
	
	$("#saveDirectoryForm").validate();
	$("#savebtn").click(function() {
		var  url = ctx+ "/shop/admin/ruleManager!saveDirectory.do?ajax=yes";
		Cmp.ajaxSubmit('saveDirectoryForm', '', url, {}, function(responseText) {			
			if (responseText.result == 0) {
				alert(responseText.message);
				Directory.page_close();
				var nodeid = $("#nodeid").val();
				var flag = $("#flag").val();
				$("a[dir_tree='dir_tree"+nodeid+"']").click();
				if (flag == "edit") {
					$("a[dir_tree='dir_tree"+nodeid+"']").html("<i class='treeic1'></i>"+$("#mulumingcheng").val());
				}
			} else {
				alert(responseText.message);
			}
		}, 'json');
	});	
});

</script>
