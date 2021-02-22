<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="alarm/js/alarm_template.js"></script>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>
<div>
	<form action="alarm!alarmTemplateList.do" method="post" id="alarmTaskForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							模板名称:
						</th>
						<td>
							<input type="text" size="18" class="ipttxt" id="templet_name" name="templet_name"
									value="${templet_name}" class="ipttxt"  dataType="string"/>
						</td>
						
						<th>
							模板类型:
						</th>
						<td> 
							<select name="templet_type" id="templet_type" class="ipttxt" style="width:100px;">
								<option value="">全部</option>
								<option value="M">短信模板 </option>
								<option value="T">页面模板</option>
							</select>
						</td>
						<th>
							模板应用场景:
						</th>
						<td>
							<input type="text" size="18" class="ipttxt" id="alarm_task_name" name="alarm_task_name"
									value="${alarm_task_name}" class="ipttxt"  dataType="string"/>
						</td>
						<th>
							状态:
						</th>
						<td> 
							<select name="status" id="status" class="ipttxt"  style="width:100px;">
								<option value="">全部</option>
								<option value="0">有效</option>
								<option value="1">无效</option>
							</select>
							<input type="submit" style="margin-right: 10px;" class="comBtn"
								value="搜&nbsp;索" id="button" name="button">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<div class="comBtnDiv">
			<a href="javascript:;" id="delAlarmTemplateBtn"
				style="margin-right: 10px;" class="graybtn1"><span>删除</span>
			</a>
			<a href="javascript:;" id="addAlarmTemplateBtn"
				style="margin-right: 10px;" class="graybtn1"><span>添加</span>
			</a>
		</div>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" excel="yes">
			<grid:header>
				<grid:cell>
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell>模板名称</grid:cell>
				<grid:cell>模板类型</grid:cell>
				<grid:cell>模板应用场景</grid:cell>
				<grid:cell>生效时间</grid:cell>
				<grid:cell>失效时间</grid:cell>
				<grid:cell>操作时间</grid:cell>
				<grid:cell>状态</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="checkbox" name="id" value="${obj.template_id}" templet_name="${obj.template_name }"/>
				</grid:cell>
				<grid:cell>
					<a title="查看" href="alarm!showAlarmTemplate.do?flag=view&template_id=${obj.template_id }">${obj.template_name }</a>
				</grid:cell>
				<grid:cell>
					<c:if test="${obj.template_type=='M'}">
						短信模板
					</c:if>
					<c:if test="${obj.template_type=='T'}">
						页面模板
					</c:if>
				</grid:cell>
				<grid:cell>${obj.applic_scene_name }</grid:cell>
				<grid:cell><html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${obj.able_time }"></html:dateformat></grid:cell>
				<grid:cell><html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${obj.enable_time }"></html:dateformat></grid:cell>
				<grid:cell><html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${obj.operation_time }"></html:dateformat></grid:cell>
				<grid:cell>
					<c:if test="${obj.status =='0'}">
						有效
					</c:if>
					<c:if test="${obj.status =='1'}">
						无效
					</c:if>
				</grid:cell>
				<grid:cell>
					<a title="编辑"href="alarm!showAlarmTemplate.do?flag=edit&template_id=${obj.template_id }">修改 </a>
					<a title="查看告警日志" href="javascript:void(0);" name="showAlarmLogList" template_id="${obj.template_id }">查看告警日志</a>
				</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<!-- 添加模板对话框 -->
<div id="addTemplate"></div>

<!-- 查看报警日志对话框 -->
<div id="showAlarmLog"></div>
<script type="text/javascript">
$(function(){
    $("#templet_type option[value='${templet_type}']").attr("selected", true);
    $("#status option[value='${status}']").attr("selected", true);
    $("#templet_type").change(function (){
       $("#button").trigger("click")
    });
    $("#status").change(function (){
       $("#button").trigger("click")
    });
});
</script>