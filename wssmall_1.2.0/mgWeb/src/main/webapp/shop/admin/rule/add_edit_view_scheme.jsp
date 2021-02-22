<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
<!--
	
.noborder {
    border-style: none;
}
-->
</style>

<div class="input">
	<form class="validate" method="post" id="schemeForm" validate="true">
	<div>
		<div style="margin-top:5px;">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tbody>
				<tr style="display: none;">
					<th>方案编码：</th>
					<td><input class="resigterIpt _x_ipt"  type="text" name="ruleObjPlan.plan_code" id="plan_code"  value="${ruleObjPlan.plan_code }" /></td>
				</tr>
				<tr>
					<th>方案名称：</th>
					<td><input class="resigterIpt _x_ipt"  type="text" name="ruleObjPlan.plan_name" id="plan_name" value="${ruleObjPlan.plan_name }" /></td>
				</tr>
				<tr style="display: none;">
					<th>方案类型：</th>
					<td><input class="resigterIpt _x_ipt"  type="text" name="ruleObjPlan.plan_type" id="plan_type"  value="java" /></td>
				</tr>
				<tr style="display: none;">
					<th>方案开始时间：</th>
					<td><input style="width: 150px" dataType="date" class="dateinput ipttxt" class="searchipt" type="text" name="ruleObjPlan.eff_date" id="eff_date"  value="${ruleObjPlan.eff_date }" /></td>
				</tr>
				<tr style="display: none;">
					<th>方案结束时间：</th>
					<td><input style="width: 150px" dataType="date" class="dateinput ipttxt" class="searchipt" type="text" name="ruleObjPlan.exp_date" id="exp_date"  value="${ruleObjPlan.exp_date }" /></td>
				</tr>
				<tr>
					<th>方案顺序：</th>
					<td><input <c:if test="${flag == 'view'}"> disabled </c:if> class="resigterIpt _x_ipt"  type="text" name="ruleObjPlan.col1" id="col1" value="${ruleObjPlan.col1 }" /></td>
				</tr>
				<input type="hidden" id="catalogue_id" value="${ruleObjPlan.catalogue_id}" />
				<tr>
					<th>上级目录：</th>
					<td>
						<select name="ruleObjPlan.catalogue_id" id="selectId"></select>
					</td>
				</tr>
				
			</tbody>	
			</table>
		</div>
		<c:if test="${flag != 'view'}">
				<div class="pop_btn">
					<a href="javascript:void(0);" class="blueBtns" id="savebtn" ><span>保 存</span></a>
				</div>
		</c:if>
	</div>
	<input type="hidden" id="plan_id" name="ruleObjPlan.plan_id" value="${ruleObjPlan.plan_id }" />
	<input type="hidden" id="flag" name="flag" value="${flag}" />
	<input type="hidden" id="nodeid" value="${id }" />
	</form>
</div>

<script>

$(function () {
	
	$.ajax({
		type : "post",
		async : false,
		url : "ruleManager!qryPidList.do?ajax=yes&id=",
		data : {},
		dataType : "json",
		success : function(data) {
			if (data != null && data.length > 0) {
				$("#selectId").empty(); // 清空select
				$("#selectId").append("<option value='-1'>请选择目录</option>");
				var jsondata = eval(data);
				$.each(jsondata, function(index, optiondata) {
					if ($("#flag").val() == "edit" || $("#flag").val() == "view") {
						if ($("#catalogue_id").val() == optiondata.id)
							$("#selectId").append("<option value='"+optiondata.id+"' selected>"+optiondata.name+"</option>");
						else
							$("#selectId").append("<option value='"+optiondata.id+"'>"+optiondata.name+"</option>");
					} else if ($("#flag").val() == "save") {
						if ($("#nodeid").val() == optiondata.id)
							$("#selectId").append("<option value='"+optiondata.id+"' selected>"+optiondata.name+"</option>");
						else
							$("#selectId").append("<option value='"+optiondata.id+"'>"+optiondata.name+"</option>");
					}
				});
			}
		}
	});	
	
	if ($("#flag").val() == "view") {
		$("#plan_code").attr("disabled","disabled")
		$("#plan_name").attr("disabled","disabled")
		$("#eff_date").attr("disabled","disabled")
		$("#exp_date").attr("disabled","disabled")
		$("#selectId").attr("disabled","disabled")
		$("#plan_type").attr("disabled","disabled");
	}
	$("#schemeForm").validate();
	$("#savebtn").click(function() {
		var  url = ctx+ "/shop/admin/ruleManager!addEditViewScheme.do?ajax=yes";
		Cmp.ajaxSubmit('schemeForm', '', url, {}, function(responseText) {
			if (responseText.result == 0) {
				alert(responseText.message);
				Directory.page_close();
				var nodeid = $("#nodeid").val();
				var plan_id = $("#plan_id").val();
				$("a[id='schemenode_id"+plan_id+"']").click();
				$("a[id='dir_id"+$("#nodeid").val()+"']").click();
				$("#schemenode_id"+plan_id).html("<i class='treeic2'></i>"+$("#plan_name").val());
				if ($("#flag").val() == "edit") {
					var dir_id = $("#selectId").val();
					
					var del_dir_id = $("#catalogue_id").val();
					if ($("#catalogue_id").val() != $("#selectId").val()) {
						
						var $li = $("#schemenode_ul"+plan_id).remove();
						
						$li.clone(true).appendTo("dir_li"+dir_id);
						$("a[id='dir_id"+dir_id+"']").click();
					}
					$("li[id=dir_li"+del_dir_id+"] ul").remove();
					$("a[id='dir_id"+del_dir_id+"']").click();
				}
			}
		}, 'json');
	});
});

</script>

