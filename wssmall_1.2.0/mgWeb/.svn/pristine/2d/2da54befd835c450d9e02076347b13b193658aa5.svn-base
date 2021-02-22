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
	<form class="validate" method="post" id="saveTemplateNodeForm" validate="true">
	<div>
		<div style="margin-left:10%;margin-top:5px;">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tbody>
				<tr style="display:none">
					<th>目录ID：</th>
					<td>
					<c:choose>
					<c:when test="${flag != 'add'}">
					    <input class="resigterIpt _x_ipt" type="text" dataType="string" name="catalogue.id" id="id" value="${id}"/>
					</c:when>
					<c:otherwise>
					    <input class="resigterIpt _x_ipt" type="text" dataType="string" name="catalogue.id" id="id" value="${catalogue.id}"/>
					</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr>
					<th>目录名称：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="catalogue.name" id="name" value="${catalogue.name}" /></td>
				</tr>
				<tr>
					<th>目录描述：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="catalogue.hint" id="hint" value="${catalogue.hint}"/></td>
				</tr>
				<tr  style="display: none;">
					<th>节点类型：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="catalogue.type" id="type" value="${catalogue.type}"/>
					</td>
				</tr>
				<tr style="display:none">
					<th>上级目录ID：</th>
					<td>
					<c:choose>
					<c:when test="${flag != 'add'}">
					    <input class="resigterIpt _x_ipt" type="text" dataType="string" name="catalogue.pid" id="pid" value="${catalogue.pid}"/>
					</c:when>
					<c:otherwise>
					     <input class="resigterIpt _x_ipt" type="text" dataType="string" name="catalogue.pid" id="pid" value="${id}"/>
					</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr  style="display: none;">
					<th>上级目录：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="node_name" id="node_name" value="${nodeName}"/></td>
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
	<input type="hidden" id="nodeId" value="${id }" />
	</form>
</div>

<script>

$(function (){
	
	$("#saveTemplateNodeForm").validate();
	$("#savebtn").click(function() {
		var  url = ctx+ "/shop/admin/ordTplAction!saveCatalogue.do?ajax=yes";
		Cmp.ajaxSubmit('saveTemplateNodeForm', '', url, {}, function(responseText) {			
			if (responseText.result == 0) {
				alert(responseText.message);
				Directory.page_close();
				var id = $("#id").val();
				var name=$("#name").val();
				var flag=$("#flag").val();
				$("a[id='root_id"+$("#id").val()+"']").click();
				$("#root_id"+id).html("<i class='treeic1'></i>"+$("#name").val());
			} else {
				alert(responseText.message);
			}
		}, 'json');
	});	
});

</script>
